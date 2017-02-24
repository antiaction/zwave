package com.antiaction.zwave.messages.command;

import com.antiaction.zwave.constants.CommandClass;

// TODO Support more versions...
public class ConfigurationCommand {

	private static final int COMMAND_CLASS = CommandClass.CONFIGURATION.getClassCode() & 255;

	public static final int CONFIGURATION_SET = 0x04;
	public static final int CONFIGURATION_GET = 0x05;
	public static final int CONFIGURATION_REPORT = 0x06;

	public static final int CONFIGURATION_SIZE_MASK = 0x07;
	public static final int CONFIGURATION_RESERVED_MASK = 0xF8;
	public static final int CONFIGURATION_RESERVED_SHIFT = 0x03;
	public static final int CONFIGURATION_DEFAULT_BIT_MASK = 0x80;

	protected ConfigurationCommand() {
	}

	public static byte[] assembleConfiguratinSetReq(int parameterNumber, int size, int value) {
		byte[] data;
		switch (size) {
		case 4:
			data = new byte[] {
					(byte)COMMAND_CLASS,
					(byte)CONFIGURATION_SET,
					(byte)(parameterNumber),
					(byte)(size & CONFIGURATION_SIZE_MASK),
					(byte)((value >> 24) & 255),
					(byte)((value >> 16) & 255),
					(byte)((value >> 8) & 255),
					(byte)(value & 255)
			};
			return data;
		case 2:
			data = new byte[] {
					(byte)COMMAND_CLASS,
					(byte)CONFIGURATION_SET,
					(byte)(parameterNumber),
					(byte)(size & CONFIGURATION_SIZE_MASK),
					(byte)((value >> 8) & 255),
					(byte)(value & 255)
			};
			return data;
		case 1:
			data = new byte[] {
					(byte)COMMAND_CLASS,
					(byte)CONFIGURATION_SET,
					(byte)(parameterNumber),
					(byte)(size & CONFIGURATION_SIZE_MASK),
					(byte)(value & 255)
			};
			return data;
		default:
			return null;
		}
	}

	public static byte[] assembleConfigurationGetReq(int parameterNumber) {
		byte[] data = {
				(byte)COMMAND_CLASS,
				(byte)CONFIGURATION_GET,
				(byte)(parameterNumber)
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
				case CONFIGURATION_REPORT:
					ConfigurationReport configurationReport = new ConfigurationReport();
					configurationReport.parameterNumber = data[idx++] & 255;
					configurationReport.size = data[idx++] & CONFIGURATION_SIZE_MASK;
					switch (configurationReport.size) {
					case 4:
						configurationReport.value = data[idx++] << 24 | (data[idx++] & 255) << 16 | (data[idx++] & 255) << 8 | (data[idx++] & 255);
						break;
					case 2:
						configurationReport.value = data[idx++] << 8 | (data[idx++] & 255);
						break;
					case 1:
						configurationReport.value = data[idx++];
						break;
					default:
						break;
					}
					return configurationReport;
				case CONFIGURATION_SET:
				case CONFIGURATION_GET:
				default:
					break;
				}
			}
		}
		return null;
	}

	public static class ConfigurationReport extends ApplicationCommandHandlerData {
		public int parameterNumber;
		public int size;
		public int value;
	}

}
