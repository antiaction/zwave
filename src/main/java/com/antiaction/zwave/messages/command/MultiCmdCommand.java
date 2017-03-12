package com.antiaction.zwave.messages.command;

import com.antiaction.zwave.constants.CommandClass;
import com.antiaction.zwave.messages.ApplicationCommandHandlerData;

public class MultiCmdCommand {

	private static final int COMMAND_CLASS = CommandClass.MULTI_CMD.getClassCode() & 255;

	public static final int MULTI_CMD_ENCAP = 0x01;

	protected MultiCmdCommand() {
	}

	public static byte[] assembleMultiCmdEncapReq(int parameterNumber) {
		byte[] data = {
				(byte)COMMAND_CLASS,
				(byte)MULTI_CMD_ENCAP
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
				case MULTI_CMD_ENCAP:
					int numberOfCommands = data[idx++] & 255;
					int commandLength;
					byte[] commandData;
					while (numberOfCommands > 0) {
						commandLength = data[idx++] & 255;
						commandData = new byte[commandLength];
						System.arraycopy(data, idx, commandData, 0, commandLength);
						idx += commandLength;
						--numberOfCommands;
					}
				default:
					break;
				}
			}
		}
		return null;
	}

}
