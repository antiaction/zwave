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
import com.antiaction.zwave.messages.ApplicationCommandHandlerData;

/**
 * MultiLevelSensor command class.
 *
 * @author nicl
 */
public class MultiLevelSensorCommand {

	private static final int COMMAND_CLASS = CommandClass.SENSOR_MULTILEVEL.getClassCode() & 255;

	public static final int SENSOR_MULTILEVEL_GET = 0x04;
	public static final int SENSOR_MULTILEVEL_REPORT = 0x05;
	public static final int SENSOR_MULTILEVEL_SUPPORTED_GET_SENSOR_V5 = 0x01;
	public static final int SENSOR_MULTILEVEL_SUPPORTED_SENSOR_REPORT_V5 = 0x02;
	public static final int SENSOR_MULTILEVEL_SUPPORTED_GET_SCALE_V5 = 0x03;
	public static final int SENSOR_MULTILEVEL_SUPPORTED_SCALE_REPORT_V5 = 0x06;

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
						sensorMultiLevelReport.value = MultiLevelSensorValue.extractValue(data, idx);
						return sensorMultiLevelReport;
					}
					else {
						MultiLevelSensorReport sensorMultiLevelReport = new MultiLevelSensorReport();
						sensorMultiLevelReport.sensorTypeId = sensorTypeId;
						sensorMultiLevelReport.sensorScaleId = sensorScaleId;
						sensorMultiLevelReport.value = MultiLevelSensorValue.extractValue(data, idx);
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
					// TODO Bitmask.
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

}
