package com.antiaction.zwave.messages.command;

import com.antiaction.zwave.constants.CommandClass;

public class SwitchBinaryCommand {

	private static final int COMMAND_CLASS = CommandClass.SWITCH_BINARY.getClassCode() & 255;

    private static final int SWITCH_BINARY_SET = 0x01;
    private static final int SWITCH_BINARY_GET = 0x02;
    private static final int SWITCH_BINARY_REPORT = 0x03;

    protected SwitchBinaryCommand() {
	}

	public static byte[] assembleSwitchBinarySetReq(int value) {
		byte[] data = new byte[] {
				(byte)COMMAND_CLASS,
				(byte)SWITCH_BINARY_SET,
				(byte)(value & 255)
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
					int value = data[idx++];
					SwitchBinaryReport switchBinaryReport = new SwitchBinaryReport();
					switchBinaryReport.value = value;
					return switchBinaryReport;
				case SWITCH_BINARY_SET:
				case SWITCH_BINARY_GET:
				default:
					break;
				}
			}
		}
		return null;
	}

	public static class SwitchBinaryReport extends ApplicationCommandHandlerData {
		public int value;
	}

}
