package com.antiaction.zwave.messages.command;

import com.antiaction.zwave.constants.CommandClass;

public class SwitchMultiLevelCommand {

	private static final int COMMAND_CLASS = CommandClass.SWITCH_MULTILEVEL.getClassCode() & 255;

	public static final int SWITCH_MULTILEVEL_SET = 0x01;
	public static final int SWITCH_MULTILEVEL_GET = 0x02;
	public static final int SWITCH_MULTILEVEL_REPORT = 0x03;
	public static final int SWITCH_MULTILEVEL_START_LEVEL_CHANGE = 0x04;
	public static final int SWITCH_MULTILEVEL_STOP_LEVEL_CHANGE = 0x05;
	public static final int SWITCH_MULTILEVEL_SUPPORTED_GET = 0x06;
	public static final int SWITCH_MULTILEVEL_SUPPORTED_REPORT = 0x07;

	protected SwitchMultiLevelCommand() {
	}

	public static byte[] assembleSwitchMultiLevelSetReq(int value) {
		byte[] data = new byte[] {
				(byte)COMMAND_CLASS,
				(byte)SWITCH_MULTILEVEL_SET,
				(byte)(value & 255)
		};
		return data;
	}

	private static final byte[] SWITCH_MULTILEVEL_GET_REQ;

	static {
		SWITCH_MULTILEVEL_GET_REQ = new byte[] {
				(byte)COMMAND_CLASS,
				(byte)SWITCH_MULTILEVEL_GET
		};
	}

	public static byte[] assembleSwitchBinaryGetReq() {
		return SWITCH_MULTILEVEL_GET_REQ;
	}

	public static ApplicationCommandHandlerData disassemble(byte[] data) {
		if (data.length >= 2) {
			int idx = 0;
			int commandClass = data[idx++] & 255;
			int command = data[idx++] & 255;
			if (commandClass == COMMAND_CLASS) {
				switch (command) {
				case SWITCH_MULTILEVEL_REPORT:
					int value = data[idx++];
					SwitchMultiLevelReport switchMultiLevelReport = new SwitchMultiLevelReport();
					switchMultiLevelReport.value = value;
					return switchMultiLevelReport;
				case SWITCH_MULTILEVEL_SET:
				case SWITCH_MULTILEVEL_GET:
				case SWITCH_MULTILEVEL_START_LEVEL_CHANGE:
				case SWITCH_MULTILEVEL_STOP_LEVEL_CHANGE:
				case SWITCH_MULTILEVEL_SUPPORTED_GET:
				case SWITCH_MULTILEVEL_SUPPORTED_REPORT:
				default:
					break;
				}
			}
		}
		return null;
	}

	public static class SwitchMultiLevelReport extends ApplicationCommandHandlerData {
		public int value;
	}

}
