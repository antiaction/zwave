package com.antiaction.zwave.messages.command;

import com.antiaction.zwave.constants.CommandClass;

// FIXME Support V3 and V4.
public class MultiLevelSwitchCommand {

	private static final int COMMAND_CLASS = CommandClass.SWITCH_MULTILEVEL.getClassCode() & 255;

	public static final int SWITCH_MULTILEVEL_SET = 0x01;
	public static final int SWITCH_MULTILEVEL_GET = 0x02;
	public static final int SWITCH_MULTILEVEL_REPORT = 0x03;
	public static final int SWITCH_MULTILEVEL_START_LEVEL_CHANGE = 0x04;
	public static final int SWITCH_MULTILEVEL_STOP_LEVEL_CHANGE = 0x05;
	public static final int SWITCH_MULTILEVEL_SUPPORTED_GET_V3 = 0x06;
	public static final int SWITCH_MULTILEVEL_SUPPORTED_REPORT_V3 = 0x07;

	public static final int SWITCH_MULTILEVEL_START_LEVEL_CHANGE_LEVEL_IGNORE_START_LEVEL_BIT_MASK = 0x20;
	public static final int SWITCH_MULTILEVEL_START_LEVEL_CHANGE_LEVEL_UP_DOWN_BIT_MASK = 0x40;

	protected MultiLevelSwitchCommand() {
	}

	public static byte[] assembleSwitchMultiLevelSetReq(int value) {
		byte[] data = new byte[] {
				(byte)COMMAND_CLASS,
				(byte)SWITCH_MULTILEVEL_SET,
				(byte)(value & 255)
		};
		return data;
	}

	public static byte[] assembleSwitchMultiLevelSetReqV2(int value, int duration) {
		byte[] data = new byte[] {
				(byte)COMMAND_CLASS,
				(byte)SWITCH_MULTILEVEL_SET,
				(byte)(value & 255),
				(byte)(duration & 255)
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

	public static byte[] assembleSwitchMultiLevelGetReq() {
		return SWITCH_MULTILEVEL_GET_REQ;
	}

	public static byte[] assembleSwitchMultiLevelStartLevelChangeReq(boolean ignoreStartLevel, int upDown, int startLevel) {
		byte[] data = new byte[] {
				(byte)COMMAND_CLASS,
				(byte)SWITCH_MULTILEVEL_START_LEVEL_CHANGE,
				(byte)((ignoreStartLevel ? SWITCH_MULTILEVEL_START_LEVEL_CHANGE_LEVEL_IGNORE_START_LEVEL_BIT_MASK : 0) | (upDown == 0 ? 0 : SWITCH_MULTILEVEL_START_LEVEL_CHANGE_LEVEL_UP_DOWN_BIT_MASK)),
				(byte)(startLevel & 255)
		};
		return data;
	}

	public static byte[] assembleSwitchMultiLevelStartLevelChangeReqV2(boolean ignoreStartLevel, int upDown, int startLevel, int duration) {
		byte[] data = new byte[] {
				(byte)COMMAND_CLASS,
				(byte)SWITCH_MULTILEVEL_START_LEVEL_CHANGE,
				(byte)((ignoreStartLevel ? SWITCH_MULTILEVEL_START_LEVEL_CHANGE_LEVEL_IGNORE_START_LEVEL_BIT_MASK : 0) | (upDown == 0 ? 0 : SWITCH_MULTILEVEL_START_LEVEL_CHANGE_LEVEL_UP_DOWN_BIT_MASK)),
				(byte)(startLevel & 255),
				(byte)(duration & 255)
		};
		return data;
	}

	private static final byte[] SWITCH_MULTILEVEL_STOP_LEVEL_CHANGE_REQ;

	static {
		SWITCH_MULTILEVEL_STOP_LEVEL_CHANGE_REQ = new byte[] {
				(byte)COMMAND_CLASS,
				(byte)SWITCH_MULTILEVEL_STOP_LEVEL_CHANGE
		};
	}

	public static byte[] assembleSwitchMultiLevelStopLevelChangeReq() {
		return SWITCH_MULTILEVEL_STOP_LEVEL_CHANGE_REQ;
	}

	public static ApplicationCommandHandlerData disassemble(byte[] data) {
		if (data.length >= 2) {
			int idx = 0;
			int commandClass = data[idx++] & 255;
			int command = data[idx++] & 255;
			if (commandClass == COMMAND_CLASS) {
				switch (command) {
				case SWITCH_MULTILEVEL_REPORT:
					SwitchMultiLevelReport switchMultiLevelReport = new SwitchMultiLevelReport();
					switchMultiLevelReport.value = data[idx++];
					return switchMultiLevelReport;
				case SWITCH_MULTILEVEL_SUPPORTED_REPORT_V3:
				case SWITCH_MULTILEVEL_SET:
				case SWITCH_MULTILEVEL_GET:
				case SWITCH_MULTILEVEL_START_LEVEL_CHANGE:
				case SWITCH_MULTILEVEL_STOP_LEVEL_CHANGE:
				case SWITCH_MULTILEVEL_SUPPORTED_GET_V3:
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
