package com.antiaction.zwave.messages.command;

import com.antiaction.zwave.constants.CommandClass;

public class SensorBinaryCommand {

	private static final int COMMAND_CLASS = CommandClass.SENSOR_BINARY.getClassCode() & 255;

    private static final int SENSOR_BINARY_REPORT = 0x03;

	protected SensorBinaryCommand() {
	}

	private static final byte[] SENSOR_BINARY_REPORT_REQ;

	static {
		SENSOR_BINARY_REPORT_REQ = new byte[] {
				(byte)COMMAND_CLASS,
				(byte)SENSOR_BINARY_REPORT
		};
	}

	public static byte[] assembleSensorBinaryReportReq() {
		return SENSOR_BINARY_REPORT_REQ;
	}

	public static ApplicationCommandHandlerData disassemble(byte[] data) {
		if (data.length >= 2) {
			int idx = 0;
			int commandClass = data[idx++] & 255;
			int command = data[idx++] & 255;
			if (commandClass == COMMAND_CLASS) {
				switch (command) {
				case SENSOR_BINARY_REPORT:
					int value = data[idx++];
					SensorBinaryReport sensorBinaryReport = new SensorBinaryReport();
					sensorBinaryReport.value = value;
					return sensorBinaryReport;
				default:
					break;
				}
			}
		}
		return null;
	}

	public static class SensorBinaryReport extends ApplicationCommandHandlerData {
		public int value;
	}

}
