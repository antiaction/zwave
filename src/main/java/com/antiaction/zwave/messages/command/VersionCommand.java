package com.antiaction.zwave.messages.command;

import com.antiaction.zwave.constants.CommandClass;

/**
 * 0x01 0x0F 0x00 0x04 0x00 0x02 0x09 0x86 0x12 0x03 0x04 0x05 0x01 0x07 0x64 0x00 0x0B
 * 0x01 0x0D 0x00 0x04 0x00 0x03 0x07 0x86 0x12 0x06 0x03 0x43 0x01 0x01 0x20
 * 0x01 0x0D 0x00 0x04 0x00 0x04 0x07 0x86 0x12 0x03 0x03 0x34 0x19 0x19 0x55
 */
public class VersionCommand {

	private static final int COMMAND_CLASS = CommandClass.VERSION.getClassCode() & 255;

	private static final byte VERSION_GET = 0x11;
	private static final byte VERSION_REPORT = 0x12;
	private static final byte VERSION_COMMAND_CLASS_GET = 0x13;
	private static final byte VERSION_COMMAND_CLASS_REPORT = 0x14;

	protected VersionCommand() {
	}

	private static final byte[] VERSION_GET_REQ;

	static {
		VERSION_GET_REQ = new byte[] {
				(byte)COMMAND_CLASS,
				(byte)VERSION_GET
		};
	}

	public static byte[] assembleVersionGetReq() {
		return VERSION_GET_REQ;
	}

	public static byte[] assembleVersionCommandClassGetReq(byte commandClass) {
		byte[] data = {
				(byte)COMMAND_CLASS,
				(byte)VERSION_COMMAND_CLASS_GET,
				(byte)commandClass
		};
		return data;
	}

	public static ApplicationCommandHandlerData disassemble(byte[] data) {
		if (data.length >= 2) {
			int idx = 0;
			int commandClass = data[idx++] & 255;
			int command = data[idx++] & 255;
			if (commandClass == COMMAND_CLASS) {
				switch (command) {
				case VERSION_REPORT:
					VersionReport versionReport = new VersionReport();
					versionReport.library = String.format("%d", data[idx++] & 255);
					versionReport.protocol = String.format("%d.%2d", data[idx++] & 255, data[idx++] & 255);
					versionReport.application = String.format("%d.%2d", data[idx++] & 255, data[idx++] & 255);
					return versionReport;
				case VERSION_COMMAND_CLASS_REPORT:
					break;
				case VERSION_GET:
				case VERSION_COMMAND_CLASS_GET:
				default:
					break;
				}
			}
		}
		return null;
	}

	public static class VersionReport extends ApplicationCommandHandlerData {
		public String library;
		public String protocol;
		public String application;
	}

	public static class VersionCommandClassReport extends ApplicationCommandHandlerData {
	}

}
