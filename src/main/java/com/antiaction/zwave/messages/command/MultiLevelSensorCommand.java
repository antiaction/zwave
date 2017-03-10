package com.antiaction.zwave.messages.command;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import com.antiaction.zwave.constants.CommandClass;
import com.antiaction.zwave.constants.SensorScale;
import com.antiaction.zwave.constants.SensorType;

/**
 * 
 * [2016-05-28 22:57:36.746 +0200] < 0x01 0x08 0x00 0x13 0x02 0x02 0x31 0x01 0x05 0xD1
 * [2016-05-28 22:57:36.787 +0200] > 0x01 0x0C 0x00 0x04 0x00 0x02 0x06 0x31 0x02 0x15 0x00 0x00 0x04 0xD1
 * [2016-05-22 00:59:16.509 +0200] > 0x01 0x0C 0x00 0x04 0x00 0x02 0x06 0x31 0x05 0x01 0x22 0x01 0x0C 0xE9
 * [2016-05-22 00:59:16.925 +0200] > 0x01 0x0B 0x00 0x04 0x00 0x02 0x05 0x31 0x05 0x05 0x01 0x27 0xE0
 * [2016-05-22 00:59:17.805 +0200] > 0x01 0x0C 0x00 0x04 0x00 0x02 0x06 0x31 0x05 0x03 0x0A 0x00 0x00 0xCE
 * [2016-05-22 00:59:17.925 +0200] > 0x01 0x0B 0x00 0x04 0x00 0x02 0x05 0x31 0x05 0x1B 0x01 0x00 0xD9
 * @author nicl
 *
 */
// TODO Bitmask.
public class MultiLevelSensorCommand {

	private static final int COMMAND_CLASS = CommandClass.SENSOR_MULTILEVEL.getClassCode() & 255;

	public static final int SENSOR_MULTILEVEL_GET = 0x04;
	public static final int SENSOR_MULTILEVEL_REPORT = 0x05;
	public static final int SENSOR_MULTILEVEL_SUPPORTED_GET_SENSOR_V5 = 0x01;
	public static final int SENSOR_MULTILEVEL_SUPPORTED_SENSOR_REPORT_V5 = 0x02;
	public static final int SENSOR_MULTILEVEL_SUPPORTED_GET_SCALE_V5 = 0x03;
	public static final int SENSOR_MULTILEVEL_SUPPORTED_SCALE_REPORT_V5 = 0x06;

	public static final int SENSOR_MULTILEVEL_SIZE_MASK = 0x07;
	public static final int SENSOR_MULTILEVEL_SCALE_MASK = 0x18;
	public static final int SENSOR_MULTILEVEL_SCALE_SHIFT = 0x03;
	public static final int SENSOR_MULTILEVEL_PRECISION_MASK = 0xE0;
	public static final int SENSOR_MULTILEVEL_PRECISION_SHIFT = 0x05;

	protected MultiLevelSensorCommand() {
	}

	private static final byte[] SENSOR_MULTILEVEL_GET_REQ;

	static {
		SENSOR_MULTILEVEL_GET_REQ = new byte[] {
				(byte)COMMAND_CLASS,
				(byte)SENSOR_MULTILEVEL_GET
		};
	}

	public static byte[] assembleSensorMultiLevelGetReq() {
		return SENSOR_MULTILEVEL_GET_REQ;
	}

	public static byte[] assembleSensorMultiLevelGetReqV5(int sensorTypeId, int scale) {
		return new byte[] {
				(byte)COMMAND_CLASS,
				(byte)SENSOR_MULTILEVEL_GET,
				(byte)sensorTypeId,
				(byte)scale
		};
	}

	private static final byte[] SENSOR_MULTILEVEL_SUPPORTED_GET_SENSOR_REQ_V5;

	static {
		SENSOR_MULTILEVEL_SUPPORTED_GET_SENSOR_REQ_V5 = new byte[] {
				(byte)COMMAND_CLASS,
				(byte)SENSOR_MULTILEVEL_SUPPORTED_GET_SENSOR_V5
		};
	}

	public static byte[] assembleSensorMultiLevelSupportedGetReqSensorV5() {
		return SENSOR_MULTILEVEL_SUPPORTED_GET_SENSOR_REQ_V5;
	}

	public static byte[] assembleSensorMultiLevelSupportedScaleGetReqV5(int sensorTypeId) {
		return new byte[] {
				(byte)COMMAND_CLASS,
				(byte)SENSOR_MULTILEVEL_SUPPORTED_GET_SCALE_V5,
				(byte)sensorTypeId
		};
	}

	public static ApplicationCommandHandlerData disassemble(byte[] data) {
		Optional<SensorType> sensorType;
		Optional<SensorScale> sensorScale;
		if (data.length >= 2) {
			int idx = 0;
			int commandClass = data[idx++] & 255;
			int command = data[idx++] & 255;
			if (commandClass == COMMAND_CLASS) {
				switch (command) {
				case SENSOR_MULTILEVEL_REPORT:
					int sensorTypeId = data[idx++];
					int sensorScaleId = (data[idx] >> 3) & 0x03;
					sensorType = SensorType.getSensorType(sensorTypeId);
					if (sensorType.isPresent()) {
						MultiLevelSensorReport sensorMultiLevelReport = new MultiLevelSensorReport();
						sensorMultiLevelReport.sensorTypeId = sensorTypeId;
						sensorMultiLevelReport.sensorType = sensorType.get();
						sensorMultiLevelReport.sensorScaleId = sensorScaleId;
						sensorMultiLevelReport.sensorScale = sensorMultiLevelReport.sensorType.getScale(sensorScaleId);
						sensorMultiLevelReport.value = extractValue(data, idx);
						return sensorMultiLevelReport;
					}
					else {
						MultiLevelSensorReport sensorMultiLevelReport = new MultiLevelSensorReport();
						sensorMultiLevelReport.sensorTypeId = sensorTypeId;
						sensorMultiLevelReport.sensorScaleId = sensorScaleId;
						sensorMultiLevelReport.value = extractValue(data, idx);
						return sensorMultiLevelReport;
					}
				case SENSOR_MULTILEVEL_SUPPORTED_SENSOR_REPORT_V5:
					MultiLevelSensorSupportedSensorReport sensorMultiLevelSupportedReport = new MultiLevelSensorSupportedSensorReport();
					int bits;
					int bit = 1;
					while (idx < data.length) {
						bits = data[idx++] & 255;
						for (int i=0; i<8; ++i) {
							if ((bits & 1) == 1) {
								sensorType = SensorType.getSensorType(bit);
								if (sensorType.isPresent()) {
									sensorMultiLevelSupportedReport.supportedSensorTypeMap.put(bit, sensorType.get());
									sensorMultiLevelSupportedReport.supportedSensorTypeList.add(sensorType.get());
								}
							}
							bits >>= 1;
							++bit;
						}
					}
					return sensorMultiLevelSupportedReport;
				case SENSOR_MULTILEVEL_SUPPORTED_SCALE_REPORT_V5:
					SensorMultiLevelSupportedScaleReport sensorMultiLevelSupportedScaleReport = new SensorMultiLevelSupportedScaleReport();
					sensorMultiLevelSupportedScaleReport.scaleBitMask = data[idx++] & 255;
					return sensorMultiLevelSupportedScaleReport;
				case SENSOR_MULTILEVEL_GET:
				case SENSOR_MULTILEVEL_SUPPORTED_GET_SENSOR_V5:
				case SENSOR_MULTILEVEL_SUPPORTED_GET_SCALE_V5:
				default:
					break;
				}
			}
		}
		return null;
	}

	public static class MultiLevelSensorReport extends ApplicationCommandHandlerData {
		public int sensorTypeId;
		public SensorType sensorType;
		public int sensorScaleId;
		public SensorScale sensorScale;
		public BigDecimal value;
	}

	public static class MultiLevelSensorSupportedSensorReport extends ApplicationCommandHandlerData {
		public Map<Integer, SensorType> supportedSensorTypeMap = new TreeMap<>();;
		public List<SensorType> supportedSensorTypeList = new LinkedList<>();
	}

	public static class SensorMultiLevelSupportedScaleReport extends ApplicationCommandHandlerData {
		public int scaleBitMask;
	}

	/**
	 * Extract a decimal value from a byte array.
	 * @param buffer the buffer to be parsed.
	 * @param offset the offset at which to start reading
	 * @return the extracted decimal value
	 */
	public static BigDecimal extractValue(byte[] buffer, int offset) {
		int size = buffer[offset] & SENSOR_MULTILEVEL_SIZE_MASK;
		int precision = (buffer[offset] & SENSOR_MULTILEVEL_PRECISION_MASK) >> SENSOR_MULTILEVEL_PRECISION_SHIFT;

		if ((size + offset) >= buffer.length) {
			throw new NumberFormatException();
		}

		int value = 0;
		int i;
		for (i = 0; i < size; ++i) {
			value <<= 8;
			value |= buffer[offset + i + 1] & 0xFF;
		}

		// Deal with sign extension. All values are signed
		BigDecimal result;
		if ((buffer[offset + 1] & 0x80) == 0x80) {
			// MSB is signed
			if (size == 1) {
				value |= 0xffffff00;
			}
			else if (size == 2) {
				value |= 0xffff0000;
			}
		}

		result = BigDecimal.valueOf(value);

		BigDecimal divisor = BigDecimal.valueOf(Math.pow(10, precision));
		return result.divide(divisor);
	}

}
