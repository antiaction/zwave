package com.antiaction.zwave.messages;

import java.util.Optional;

import com.antiaction.zwave.FrameUtils;
import com.antiaction.zwave.HexUtils;
import com.antiaction.zwave.constants.CommandClass;
import com.antiaction.zwave.constants.ControllerMessageType;
import com.antiaction.zwave.constants.MessageType;

public class GetCapabilities {

	 // 0x01 0x03 0x00 0x07 0xFB
	public static byte[] buidGetCapabilitiesReq() {
		byte[] data = new byte[] {
				(byte)ControllerMessageType.GetCapabilities	.getId()
		};
		byte[] bytes = FrameUtils.assemble(MessageType.Request, data);
		return bytes;
	}

	// 0x01 0x2B 0x01 0x07 0x01 0x00 0x00 0x86 0x00 0x01 0x00 0x5A 0xFE 0x81 0xFF 0x88 0x4F 0x1F 0x00 0x00 0xFB 0x9F 0x7D 0xA0 0x67 0x00 0x00 0x80 0x00 0x80 0x86 0x00 0x00 0x00 0xE8 0x73 0x00 0x00 0x0E 0x00 0x00 0x60 0x00 0x00 0xFB
	public static void parseGetCapabilitiesResp(byte[] frame) {
		byte[] data = FrameUtils.disassemble(frame);
		int idx = 0;
		int majorVersion = data[idx++] & 255;
		int minorVersion = data[idx++] & 255;
        int manufactureId = ((data[idx++] & 255) << 8) | (data[idx++] & 255);
        int deviceType = ((data[idx++] & 255) << 8) | (data[idx++] & 255);
        int deviceId = ((data[idx++] & 255) << 8) | (data[idx++] & 255);

        System.out.println(majorVersion + "." + minorVersion);
        System.out.println(HexUtils.hexString2(manufactureId));
        System.out.println(HexUtils.hexString2(deviceType));
        System.out.println(HexUtils.hexString2(deviceId));
		int bits;
		int bit = 1;
		Optional<CommandClass> commandClass;
		while (idx < data.length) {
			bits = data[idx++] & 255;
	        System.out.println(HexUtils.hexString((byte)bits));
			for (int i=0; i<8; ++i) {
				if ((bits & 1) == 1) {
					commandClass = CommandClass.getType(bit);
					if (commandClass.isPresent()) {
						System.out.println(commandClass.get().getLabel());
					}
					else {
						System.out.println(bit);
					}
			        System.out.println(HexUtils.hexString((byte)bits));
					bits >>= 1;
			        System.out.println(HexUtils.hexString((byte)bits));
				}
				++bit;
			}
		}
	}

}
