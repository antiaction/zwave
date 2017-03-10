package com.antiaction.zwave.messages.command;

import com.antiaction.zwave.constants.CommandClass;

public class BinarySwitchCommand {

	private static final int COMMAND_CLASS = CommandClass.SWITCH_BINARY.getClassCode() & 255;

	public static final int SWITCH_BINARY_SET = 0x01;
	public static final int SWITCH_BINARY_GET = 0x02;
	public static final int SWITCH_BINARY_REPORT = 0x03;

    protected BinarySwitchCommand() {
	}

	public static byte[] assembleSwitchBinarySetReq(int value) {
		byte[] data = new byte[] {
				(byte)COMMAND_CLASS,
				(byte)SWITCH_BINARY_SET,
				(byte)(value & 255)
		};
		return data;
	}

	public static byte[] assembleSwitchBinarySetReqV2(int targetValue, int duration) {
		byte[] data = new byte[] {
				(byte)COMMAND_CLASS,
				(byte)SWITCH_BINARY_SET,
				(byte)(targetValue & 255),
				(byte)(duration & 255)
		};
		return data;
	}

	private static final byte[] SWITCH_BINARY_GET_REQ;

	static {
		SWITCH_BINARY_GET_REQ = new byte[] {
				(byte)COMMAND_CLASS,
				(byte)SWITCH_BINARY_GET
		};
	}

	public static byte[] assembleSwitchBinaryGetReq() {
		return SWITCH_BINARY_GET_REQ;
	}

	public static ApplicationCommandHandlerData disassemble(byte[] data) {
		if (data.length >= 2) {
			int idx = 0;
			int commandClass = data[idx++] & 255;
			int command = data[idx++] & 255;
			if (commandClass == COMMAND_CLASS) {
				switch (command) {
				case SWITCH_BINARY_REPORT:
					if ((data.length - idx) == 1) {
						BinarySwitchReport binarySwitchReport = new BinarySwitchReport();
						binarySwitchReport.value = data[idx++] & 255;
						return binarySwitchReport;
					}
					else {
						BinarySwitchReportV2 binarySwitchReport = new BinarySwitchReportV2();
						binarySwitchReport.currentValue = data[idx++] & 255;
						binarySwitchReport.targetValue = data[idx++] & 255;
						binarySwitchReport.duration = data[idx++] & 255;
						return binarySwitchReport;
					}
				case SWITCH_BINARY_SET:
				case SWITCH_BINARY_GET:
				default:
					break;
				}
			}
		}
		return null;
	}

	public static class BinarySwitchReport extends ApplicationCommandHandlerData {
		public int value;
	}

	public static class BinarySwitchReportV2 extends ApplicationCommandHandlerData {
		public int currentValue;
		public int targetValue;
		public int duration;
	}

}
