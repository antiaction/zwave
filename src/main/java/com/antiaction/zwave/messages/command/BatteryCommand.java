package com.antiaction.zwave.messages.command;

import com.antiaction.zwave.constants.CommandClass;

/**
 * < 0x01 0x09 0x00 0x04 0x00 0x02 0x03 0x80 0x03 0x64 0x14
 * < 0x01 0x09 0x00 0x04 0x00 0x03 0x03 0x80 0x03 0x48 0x39
 * @author nicl
 */
public class BatteryCommand {

	private static final int COMMAND_CLASS = CommandClass.BATTERY.getClassCode() & 255;

	private static final int BATTERY_GET = 0x02;
	private static final int BATTERY_REPORT = 0x03;

	protected BatteryCommand() {
	}

	private static final byte[] BATTERY_REQ;

	static {
		BATTERY_REQ = new byte[] {
				(byte)COMMAND_CLASS,
				(byte)BATTERY_GET
		};
	}

	public static byte[] assembleBatteryReq() {
		return BATTERY_REQ;
	}

	public static Battery disassemble(byte[] data) {
		if (data.length >= 2) {
			int idx = 0;
			int commandClass = data[idx++] & 255;
			int command = data[idx++] & 255;
			if (commandClass == COMMAND_CLASS) {
				switch (command) {
				case BATTERY_REPORT:
					Battery battery = new Battery();
					battery.level = data[idx++] & 255;
					return battery;
				case BATTERY_GET:
				default:
					break;
				}
			}
		}
		return null;
	}

	public static class Battery extends ApplicationCommandHandlerData {
		public int level;
	}

}
