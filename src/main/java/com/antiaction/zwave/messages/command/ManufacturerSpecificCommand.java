package com.antiaction.zwave.messages.command;

import com.antiaction.zwave.constants.CommandClass;
import com.antiaction.zwave.messages.ApplicationCommandHandlerData;

/**
 * ManufacturerSpecific command class.
 *
 * @author nicl
 */
//TODO Support version 2.
public class ManufacturerSpecificCommand {

	private static final int COMMAND_CLASS = CommandClass.MANUFACTURER_SPECIFIC.getClassCode() & 255;

	public static final int MANUFACTURER_SPECIFIC_GET = 0x04;
	public static final int MANUFACTURER_SPECIFIC_REPORT = 0x05;
	public static final int DEVICE_SPECIFIC_GET_V2 = 0x06;
	public static final int DEVICE_SPECIFIC_REPORT_V2 = 0x07;

	protected ManufacturerSpecificCommand() {
	}

	private static final byte[] MANUFACTURER_SPECIFIC_GET_REQ;

	static {
		MANUFACTURER_SPECIFIC_GET_REQ = new byte[] {
				(byte)COMMAND_CLASS,
				(byte)MANUFACTURER_SPECIFIC_GET
		};
	}

	public static byte[] getManufacturerSpecificGetReq() {
		return MANUFACTURER_SPECIFIC_GET_REQ;
	}

	public static ApplicationCommandHandlerData disassemble(byte[] data) {
		if (data.length >= 2) {
			int idx = 0;
			int commandClass = data[idx++] & 255;
			int command = data[idx++] & 255;
			if (commandClass == COMMAND_CLASS) {
				switch (command) {
				case MANUFACTURER_SPECIFIC_REPORT:
					ManufacturerSpecificReport manufacturerSpecific = new ManufacturerSpecificReport();
					manufacturerSpecific.manufactureId = ((data[idx++] & 255) << 8) | (data[idx++] & 255);
					manufacturerSpecific.deviceType = ((data[idx++] & 255) << 8) | (data[idx++] & 255);
					manufacturerSpecific.deviceId = ((data[idx++] & 255) << 8) | (data[idx++] & 255);
					return manufacturerSpecific;
				case MANUFACTURER_SPECIFIC_GET:
				default:
					break;
				}
			}
		}
		return null;
	}

	public static class ManufacturerSpecificReport extends ApplicationCommandHandlerData {
		public int manufactureId;
		public int deviceType;
		public int deviceId;
	}

}
