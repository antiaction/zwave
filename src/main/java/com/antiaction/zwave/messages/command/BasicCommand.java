package com.antiaction.zwave.messages.command;

import com.antiaction.zwave.constants.CommandClass;

/**
 * > 0x01 0x09 0x00 0x04 0x00 0x02 0x03 0x20 0x01 0xFF 0x2D
 * > 0x01 0x09 0x00 0x04 0x00 0x02 0x03 0x20 0x01 0x00 0xD2
 * @author nicl
 */
public class BasicCommand {

	private static final int COMMAND_CLASS = CommandClass.BASIC.getClassCode() & 255;

	private static final int BASIC_SET = 0x01;
	private static final int BASIC_GET = 0x02;
	private static final int BASIC_REPORT = 0x03;

	protected BasicCommand() {
	}

	private static final byte[] BASIC_REQ;

	static {
		BASIC_REQ = new byte[] {
				(byte)COMMAND_CLASS,
				(byte)BASIC_GET
		};
	}

	public static byte[] assembleBasicReq() {
		return BASIC_REQ;
	}

	public static Basic disassemble(byte[] data) {
		if (data.length >= 2) {
			int idx = 0;
			int commandClass = data[idx++] & 255;
			int command = data[idx++] & 255;
			if (commandClass == COMMAND_CLASS) {
				switch (command) {
				case BASIC_SET:
				case BASIC_REPORT:
					Basic basic = new Basic();
					basic.value = data[idx++] & 255;
					return basic;
				case BASIC_GET:
				default:
					break;
				}
			}
		}
		return null;
	}

	public static class Basic extends ApplicationCommandHandlerData {
		public int value;
	}

}
