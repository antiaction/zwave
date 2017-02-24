package com.antiaction.zwave.messages.command;

import com.antiaction.zwave.constants.CommandClass;

@Deprecated
public class BinarySensorCommand {

	private static final int COMMAND_CLASS = CommandClass.SENSOR_BINARY.getClassCode() & 255;

	public static final int SENSOR_BINARY_GET = 0x02;
	public static final int SENSOR_BINARY_REPORT = 0x03;
	public static final int SENSOR_BINARY_SUPPORTED_GET_SENSOR_V2 = 0x01;
	public static final int SENSOR_BINARY_SUPPORTED_SENSOR_REPORT_V2 = 0x04;

	protected BinarySensorCommand() {
	}

	private static final byte[] SENSOR_BINARY_GET_REQ;

	static {
		SENSOR_BINARY_GET_REQ = new byte[] {
				(byte)COMMAND_CLASS,
				(byte)SENSOR_BINARY_GET
		};
	}

	public static byte[] assembleSensorBinaryGetReq() {
		return SENSOR_BINARY_GET_REQ;
	}

	public static byte[] assembleSensorBinaryGetReqV2(int sensorType) {
		byte[] data = new byte[] {
				(byte)COMMAND_CLASS,
				(byte)SENSOR_BINARY_GET,
				(byte)sensorType
		};
		return data;
	}

	private static final byte[] SENSOR_BINARY_SUPPORTED_GET_SENSOR_REQ_V2;

	static {
		SENSOR_BINARY_SUPPORTED_GET_SENSOR_REQ_V2 = new byte[] {
				(byte)COMMAND_CLASS,
				(byte)SENSOR_BINARY_SUPPORTED_GET_SENSOR_V2
		};
	}

	public static byte[] assembleSensorBinaryGetSupportedSensorReportReq() {
		return SENSOR_BINARY_SUPPORTED_GET_SENSOR_REQ_V2;
	}

	public static ApplicationCommandHandlerData disassemble(byte[] data) {
		if (data.length >= 2) {
			int idx = 0;
			int commandClass = data[idx++] & 255;
			int command = data[idx++] & 255;
			if (commandClass == COMMAND_CLASS) {
				switch (command) {
				case SENSOR_BINARY_REPORT:
					if ((data.length - idx) == 1) {
						BinarySensorReport binarySensorReport = new BinarySensorReport();
						binarySensorReport.sensorValue = data[idx++];
						return binarySensorReport;
					}
					else {
						BinarySensorReportV2 binarySensorReport = new BinarySensorReportV2();
						binarySensorReport.sensorValue = data[idx++];
						binarySensorReport.sensorType = data[idx++];
						return binarySensorReport;
					}
				case SENSOR_BINARY_SUPPORTED_SENSOR_REPORT_V2:
					BinarySensorSupportedSensorReport binarySensorSupportedSensorReport = new BinarySensorSupportedSensorReport();
					binarySensorSupportedSensorReport.bitmask = new byte[data.length - idx];
					System.arraycopy(data, idx, binarySensorSupportedSensorReport.bitmask, 0, data.length - idx);
					return binarySensorSupportedSensorReport;
				case SENSOR_BINARY_GET:
				case SENSOR_BINARY_SUPPORTED_GET_SENSOR_V2:
				default:
					break;
				}
			}
		}
		return null;
	}

	public static class BinarySensorReport extends ApplicationCommandHandlerData {
		public int sensorValue;
	}

	public static class BinarySensorReportV2 extends ApplicationCommandHandlerData {
		public int sensorValue;
		public int sensorType;
	}

	public static class BinarySensorSupportedSensorReport extends ApplicationCommandHandlerData {
		public byte[] bitmask;
	}

}
