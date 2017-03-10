package com.antiaction.zwave.messages.command;

public class ConfigurationValue {

	public int size;
	public int value;

	public static ConfigurationValue disassemble(byte[] data, int idx) {
		ConfigurationValue configurationValue = new ConfigurationValue();
		configurationValue.size = data[idx++] & ConfigurationCommand.CONFIGURATION_SIZE_MASK;
		switch (configurationValue.size) {
		case 4:
			configurationValue.value = data[idx++] << 24 | (data[idx++] & 255) << 16 | (data[idx++] & 255) << 8 | (data[idx++] & 255);
			break;
		case 2:
			configurationValue.value = data[idx++] << 8 | (data[idx++] & 255);
			break;
		case 1:
			configurationValue.value = data[idx++];
			break;
		default:
			break;
		}
		return configurationValue;
	}

}
