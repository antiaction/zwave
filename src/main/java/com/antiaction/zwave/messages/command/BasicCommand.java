package com.antiaction.zwave.messages.command;

import com.antiaction.zwave.constants.CommandClass;

/**
 * > 0x01 0x09 0x00 0x04 0x00 0x02 0x03 0x20 0x01 0xFF 0x2D
 * > 0x01 0x09 0x00 0x04 0x00 0x02 0x03 0x20 0x01 0x00 0xD2
 * @author nicl
 */
public class BasicCommand {

	private static final int COMMAND_CLASS = CommandClass.BASIC.getClassCode() & 255;

	public static final int BASIC_SET = 0x01;
	public static final int BASIC_GET = 0x02;
	public static final int BASIC_REPORT = 0x03;

	protected BasicCommand() {
	}

	private static final byte[] BASIC_REQ;

	static {
		BASIC_REQ = new byte[] {
				(byte)COMMAND_CLASS,
				(byte)BASIC_GET
		};
	}

	public static byte[] assembleBasicGetReq() {
		return BASIC_REQ;
	}

	public static ApplicationCommandHandlerData disassemble(byte[] data) {
		if (data.length >= 2) {
			int idx = 0;
			int commandClass = data[idx++] & 255;
			int command = data[idx++] & 255;
			if (commandClass == COMMAND_CLASS) {
				switch (command) {
				case BASIC_SET:
				case BASIC_REPORT:
					if ((data.length - idx) == 1) {
						BasicReport basicReport = new BasicReport();
						basicReport.value = data[idx++] & 255;
						return basicReport;
					}
					else {
						BasicReportV2 basicReport = new BasicReportV2();
						basicReport.currentValue = data[idx++] & 255;
						basicReport.targetValue = data[idx++] & 255;
						basicReport.duration = data[idx++] & 255;
						return basicReport;
					}
				case BASIC_GET:
				default:
					break;
				}
			}
		}
		return null;
	}

	public static class BasicReport extends ApplicationCommandHandlerData {
		public int value;
	}

	public static class BasicReportV2 extends ApplicationCommandHandlerData {
		public int currentValue;
		public int targetValue;
		public int duration;
	}

}
