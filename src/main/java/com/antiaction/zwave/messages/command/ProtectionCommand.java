package com.antiaction.zwave.messages.command;

import com.antiaction.zwave.constants.CommandClass;

// TODO Support version 2.
public class ProtectionCommand {

	private static final int COMMAND_CLASS = CommandClass.PROTECTION.getClassCode() & 255;

	public static final int PROTECTION_SET = 0x01;
	public static final int PROTECTION_GET = 0x02;
	public static final int PROTECTION_REPORT = 0x03;

	public static final int PROTECTION_UNPROTECTED = 0x00;
	public static final int PROTECTION_PROTECTION_BY_SEQUENCE = 0x01;
	public static final int PROTECTION_NO_OPERATION_POSSIBLE = 0x02;

	public static byte[] assembleProtectionSetReq(int protectionState) {
		byte[] data = {
				(byte)COMMAND_CLASS,
				(byte)PROTECTION_SET,
				(byte)(protectionState)
		};
		return data;
	}

	private static final byte[] PROTECTION_GET_REQ;

	static {
		PROTECTION_GET_REQ = new byte[] {
				(byte)COMMAND_CLASS,
				(byte)PROTECTION_GET
		};
	}

	public static byte[] assembleProtectionGetReq() {
		return PROTECTION_GET_REQ;
	}

	public static ApplicationCommandHandlerData disassemble(byte[] data) {
		if (data.length >= 2) {
			int idx = 0;
			int commandClass = data[idx++] & 255;
			int command = data[idx++] & 255;
			if (commandClass == COMMAND_CLASS) {
				switch (command) {
				case PROTECTION_REPORT:
					ProtectionReport protectedReport = new ProtectionReport();
					protectedReport.protectionState = data[idx++] & 255;
					return protectedReport;
				case PROTECTION_SET:
				case PROTECTION_GET:
				default:
					break;
				}
			}
		}
		return null;
	}

	public static class ProtectionReport extends ApplicationCommandHandlerData {
		public int protectionState;
	}

}
