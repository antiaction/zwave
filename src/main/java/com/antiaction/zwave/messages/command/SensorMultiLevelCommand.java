package com.antiaction.zwave.messages.command;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import com.antiaction.zwave.constants.CommandClass;
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
public class SensorMultiLevelCommand {

	private static final int COMMAND_CLASS = CommandClass.SENSOR_MULTILEVEL.getClassCode() & 255;

	private static final int SENSOR_MULTI_LEVEL_SUPPORTED_GET = 0x01;
	private static final int SENSOR_MULTI_LEVEL_SUPPORTED_REPORT = 0x02;
	private static final int SENSOR_MULTI_LEVEL_GET = 0x04;
	private static final int SENSOR_MULTI_LEVEL_REPORT = 0x05;

	protected SensorMultiLevelCommand() {
	}

	private static final byte[] SENSOR_MULTI_LEVEL_SUPPORTED_GET_REQ;

	static {
		SENSOR_MULTI_LEVEL_SUPPORTED_GET_REQ = new byte[] {
				(byte)COMMAND_CLASS,
				(byte)SENSOR_MULTI_LEVEL_SUPPORTED_GET
		};
	}

	public static byte[] assembleSensorMultiLevelSupportedGetReq() {
		return SENSOR_MULTI_LEVEL_SUPPORTED_GET_REQ;
	}

	public static byte[] assembleSensorMultiLevelGetReq(int sensorTypeId) {
		return new byte[] {
				(byte)COMMAND_CLASS,
				(byte)SENSOR_MULTI_LEVEL_GET,
				(byte)sensorTypeId
		};
	}

	public static ApplicationCommandHandlerData disassemble(byte[] data) {
		if (data.length >= 2) {
			int idx = 0;
			int commandClass = data[idx++] & 255;
			int command = data[idx++] & 255;
			if (commandClass == COMMAND_CLASS) {
				switch (command) {
				case SENSOR_MULTI_LEVEL_SUPPORTED_REPORT:
					SensorMultiLevelSupportedReport sensorMultiLevelSupportedReport = new SensorMultiLevelSupportedReport();
					int bits;
					int bit = 1;
					while (idx < data.length) {
						bits = data[idx++] & 255;
						for (int i=0; i<8; ++i) {
							if ((bits & 1) == 1) {
								Optional<SensorType> sensorType = SensorType.getSensorType(bit);
								if (sensorType.isPresent()) {
									sensorMultiLevelSupportedReport.supportedSensorTypeSet.put(bit, sensorType.get());
									sensorMultiLevelSupportedReport.supportedSensorTypeList.add(sensorType.get());
								}
							}
							bits >>= 1;
							++bit;
						}
					}
					return sensorMultiLevelSupportedReport;
				case SENSOR_MULTI_LEVEL_REPORT:
					int sensorTypeCode = data[idx++];
					int sensorScale = (data[idx] >> 3) & 0x03;
					Optional<SensorType> sensorType = SensorType.getSensorType(sensorTypeCode);
					if (sensorType.isPresent()) {
						SensorMultiLevelReport sensorMultiLevelReport = new SensorMultiLevelReport();
						sensorMultiLevelReport.sensorType = sensorType.get();
						sensorMultiLevelReport.value = extractValue(data, idx);
						return sensorMultiLevelReport;
					}
					else {
						// FIXME
						System.out.println("Unknown sensortype!");
						System.out.println(sensorScale);
					}
					/*
					if (data[1] == 0x05 && data[2] == 0x01) {
						Float temp = new Float(data[4] & 255);
						switch (data[3]) {
						case 1:
							   temp = ((temp - 170.0f) / 10.0f) + 41.1f;
							break;
						case 2:
							   temp = ((temp - 170.0f) / 10.0f) + 68.2f;
							break;
						case 3:
							   temp = ((temp - 25.0f) / 10.0f) + 79.3f;
							break;
						}
						Float temp2 = (temp -32) * 5.0f / 9.0f;

						// debug
						System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " temperature: " + temp + "F / " + temp2 + "C");
					}
					else {
						// debug
						System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " unsupported data: " + HexUtils.byteArrayToHexString(data));
					}
					*/
					break;
				case SENSOR_MULTI_LEVEL_SUPPORTED_GET:
				case SENSOR_MULTI_LEVEL_GET:
				default:
					break;
				}
			}
		}
		return null;
	}

	public static class SensorMultiLevelSupportedReport extends ApplicationCommandHandlerData {
		public Map<Integer, SensorType> supportedSensorTypeSet = new TreeMap<>();;
		public List<SensorType> supportedSensorTypeList = new LinkedList<>();
	}

	public static class SensorMultiLevelReport extends ApplicationCommandHandlerData {
		public SensorType sensorType;
		public BigDecimal value;
	}

	private static final int SIZE_MASK = 0x07;
	private static final int PRECISION_MASK = 0xe0;
	private static final int PRECISION_SHIFT = 0x05;

	/**
	 * Extract a decimal value from a byte array.
	 * @param buffer the buffer to be parsed.
	 * @param offset the offset at which to start reading
	 * @return the extracted decimal value
	 */
	public static BigDecimal extractValue(byte[] buffer, int offset) {
		int size = buffer[offset] & SIZE_MASK;
		int precision = (buffer[offset] & PRECISION_MASK) >> PRECISION_SHIFT;

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
