package com.antiaction.zwave.messages.command;

import com.antiaction.zwave.constants.CommandClass;
import com.antiaction.zwave.messages.ApplicationCommandHandlerData;
import com.antiaction.zwave.messages.command.BatteryCommand.BatteryReport;

public class AlarmCommand {

	private static final int COMMAND_CLASS = CommandClass.ALARM.getClassCode() & 255;

	public static final int ALARM_GET = 0x04;
	public static final int ALARM_REPORT = 0x05;

	protected AlarmCommand() {
	}

	private static final byte[] ALARM_GET_REQ;

	static {
		ALARM_GET_REQ = new byte[] {
				(byte)COMMAND_CLASS,
				(byte)ALARM_GET
		};
	}

	public static byte[] assembleBatteryReq() {
		return ALARM_GET_REQ;
	}

	public static ApplicationCommandHandlerData disassemble(byte[] data) {
		if (data.length >= 2) {
			int idx = 0;
			int commandClass = data[idx++] & 255;
			int command = data[idx++] & 255;
			if (commandClass == COMMAND_CLASS) {
				switch (command) {
				case ALARM_REPORT:
					BatteryReport battery = new BatteryReport();
					battery.level = data[idx++] & 255;
					return battery;
				case ALARM_GET:
				default:
					break;
				}
			}
		}
		return null;
	}

	public static class AlarmReport extends ApplicationCommandHandlerData {
		public int level;
	}

}
