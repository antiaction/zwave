package com.antiaction.zwave.messages;

import com.antiaction.zwave.FrameUtils;
import com.antiaction.zwave.constants.MessageType;

public class SetControllerParam {

	private static final byte[] SET_HEADER = {(byte)0xF2};
	private static final byte[] SET_FOOTER = {(byte)0x05, (byte)0x01};

	// {(byte)0x00, (byte)0xF2, parameter, (byte)0x05, (byte)0x01}
	public static byte[] set(byte[] parameter) {
		byte[] bytes = FrameUtils.assemble(MessageType.Request, SET_HEADER, parameter, SET_FOOTER);
		return bytes;
	}

	// 0x01 0x04 0x01 0xF2 0x01 0x09
	public static int parseSet(byte[] frame) {
		return frame[4] & 255;
	}

}
