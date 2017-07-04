package com.antiaction.zwave.messages.command;

import com.antiaction.zwave.constants.CommandClass;
import com.antiaction.zwave.messages.ApplicationCommandHandlerData;

/**
 * Battery command class.
 *
 * @author nicl
 */
public class BatteryCommand {

	private static final int COMMAND_CLASS = CommandClass.BATTERY.getClassCode() & 255;

	public static final int BATTERY_GET = 0x02;
	public static final int BATTERY_REPORT = 0x03;

	protected BatteryCommand() {
	}

	private static final byte[] BATTERY_GET_REQ;

	static {
		BATTERY_GET_REQ = new byte[] {
				(byte)COMMAND_CLASS,
				(byte)BATTERY_GET
		};
	}

	public static byte[] assembleBatteryGetReq() {
		return BATTERY_GET_REQ;
	}

	public static ApplicationCommandHandlerData disassemble(byte[] data) {
		if (data.length >= 2) {
			int idx = 0;
			int commandClass = data[idx++] & 255;
			int command = data[idx++] & 255;
			if (commandClass == COMMAND_CLASS) {
				switch (command) {
				case BATTERY_REPORT:
					BatteryReport batteryReport = new BatteryReport();
					batteryReport.level = data[idx++] & 255;
					return batteryReport;
				case BATTERY_GET:
				default:
					break;
				}
			}
		}
		return null;
	}

	public static class BatteryReport extends ApplicationCommandHandlerData {
		public int level;
	}

}
