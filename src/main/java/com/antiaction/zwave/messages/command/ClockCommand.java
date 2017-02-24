package com.antiaction.zwave.messages.command;

import com.antiaction.zwave.constants.CommandClass;

public class ClockCommand {

	private static final int COMMAND_CLASS = CommandClass.CLOCK.getClassCode() & 255;

	public static final int CLOCK_SET = 0x04;
	public static final int CLOCK_GET = 0x05;
	public static final int CLOCK_REPORT = 0x06;

	public static final int CLOCK_HOUR_MASK = 0x1F;
	public static final int CLOCK_WEEKDAY_MASK = 0xE0;
	public static final int CLOCK_WEEKDAY_SHIFT = 0x05;

	public static byte[] assembleClockSetReq(int weekday, int hour, int minute) {
		byte[] data = {
				(byte)COMMAND_CLASS,
				(byte)CLOCK_SET,
				(byte)(((weekday << CLOCK_WEEKDAY_SHIFT) & CLOCK_WEEKDAY_MASK) | (hour & CLOCK_HOUR_MASK)),
				(byte)(minute)
		};
		return data;
	}

	private static final byte[] CLOCK_GET_REQ;

	static {
		CLOCK_GET_REQ = new byte[] {
				(byte)COMMAND_CLASS,
				(byte)CLOCK_GET
		};
	}

	public static byte[] assembleClockGetReq() {
		return CLOCK_GET_REQ;
	}

	public static ApplicationCommandHandlerData disassemble(byte[] data) {
		if (data.length >= 2) {
			int idx = 0;
			int commandClass = data[idx++] & 255;
			int command = data[idx++] & 255;
			if (commandClass == COMMAND_CLASS) {
				switch (command) {
				case CLOCK_REPORT:
					ClockReport clockReport = new ClockReport();
					clockReport.weekday = data[idx] & CLOCK_WEEKDAY_MASK >> CLOCK_WEEKDAY_SHIFT;
					clockReport.weekday = data[idx] & CLOCK_HOUR_MASK;
					++idx;
					clockReport.weekday = data[idx++] & 255;
					return clockReport;
				case CLOCK_SET:
				case CLOCK_GET:
				default:
					break;
				}
			}
		}
		return null;
	}

	public static class ClockReport extends ApplicationCommandHandlerData {
		public int weekday;
		public int hour;
		public int minute;
	}

}
