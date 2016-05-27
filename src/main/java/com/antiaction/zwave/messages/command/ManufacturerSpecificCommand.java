package com.antiaction.zwave.messages.command;

import com.antiaction.zwave.constants.CommandClass;

/**
 * < 0x01 0x08 0x00 0x13 0x02 0x02 0x72 0x04 0x05 0x97
 * > 0x01 0x04 0x01 0x13 0x01 0xE8
 * > 0x01 0x07 0x00 0x13 0x68 0x00 0x00 0x00 0x83
 * > 0x01 0x07 0x00 0x13 0x05 0x00 0x00 0x03 0xED
 * > 0x01 0x0E 0x00 0x04 0x00 0x02 0x08 0x72 0x05 0x00 0x86 0x00 0x02 0x00 0x64 0x68
 * @author nicl
 */
public class ManufacturerSpecificCommand {

	private static final int COMMAND_CLASS = CommandClass.MANUFACTURER_SPECIFIC.getClassCode() & 255;

	private static final int MANUFACTURER_SPECIFIC_GET = 0x04;
    private static final int MANUFACTURER_SPECIFIC_REPORT = 0x05;

    protected ManufacturerSpecificCommand() {
    }

    private static final byte[] MANUFACTURER_SPECIFIC_GET_REQ;

    static {
    	MANUFACTURER_SPECIFIC_GET_REQ = new byte[] {
    			(byte)COMMAND_CLASS,
    			MANUFACTURER_SPECIFIC_GET
    	};
    }

    public static byte[] getManufacturerSpecificGetReq() {
    	return MANUFACTURER_SPECIFIC_GET_REQ;
    }

    public static ManufacturerSpecific disassemble(byte[] data) {
		if (data.length >= 2) {
			int idx = 0;
			int commandClass = data[idx++] & 255;
			int command = data[idx++] & 255;
			if (commandClass == COMMAND_CLASS) {
				switch (command) {
				case MANUFACTURER_SPECIFIC_REPORT:
					ManufacturerSpecific manufacturerSpecific = new ManufacturerSpecific();
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

    public static class ManufacturerSpecific extends ApplicationCommandHandlerData {
    	public int manufactureId;
        public int deviceType;
        public int deviceId;
    }

}
