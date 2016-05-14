package com.antiaction.zwave.messages;

import java.util.ArrayList;
import java.util.List;

import com.antiaction.zwave.FrameUtils;
import com.antiaction.zwave.constants.ControllerMessageType;
import com.antiaction.zwave.constants.ControllerMode;
import com.antiaction.zwave.constants.ControllerType;
import com.antiaction.zwave.constants.MessageType;

public class SerialApiGetInitData {

	// 0x01 0x03 0x00 0x02 0xFE
	public static byte[] buildSerialApiGetInitDataReq() {
		byte[] data = new byte[] {
				(byte)ControllerMessageType.SerialApiGetInitData.getId()
		};
		byte[] bytes = FrameUtils.assemble(MessageType.Request, data );
		return bytes;
	}

	// 0x01 0x25 0x01 0x02 0x05 0x00 0x1D 0x07 0x00 0x00 0x00 0x00 0x00 0x00 0x00 0x00 0x00 0x00 0x00 0x00 0x00 0x00 0x00 0x00 0x00 0x00 0x00 0x00 0x00 0x00 0x00 0x00 0x00 0x00 0x00 0x00 0x05 0x00 0xC3
	public static List<Integer> parseSerialApiGetInitDataResp(byte[] frame) {
		byte[] data = FrameUtils.disassemble(frame);
		List<Integer> registeredDevices = new ArrayList<Integer>(232);
        ControllerMode controllerMode = ((data[1] & 0x01) == 1) ? ControllerMode.SLAVE : ControllerMode.CONTROLLER;
        ControllerType controllerType = ((data[1] & 0x04) == 1) ? ControllerType.SECONDARY : ControllerType.PRIMARY;
        System.out.println(controllerMode.name());
        System.out.println(controllerType.name());
		int idx = 2;
		int len = data[idx++] & 255;
		int bits;
		int bit = 1;
		while (len > 0) {
			bits = data[idx++] & 255;
			--len;
			for (int i=0; i<8; ++i) {
				if ((bits & 1) == 1) {
					// debug
					//System.out.println("id: " + bit);
					registeredDevices.add(bit);
					bits >>= 1;
				}
				++bit;
			}
		}
		return registeredDevices;
	}

}
