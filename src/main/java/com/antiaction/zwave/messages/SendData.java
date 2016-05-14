package com.antiaction.zwave.messages;

import com.antiaction.zwave.FrameUtils;
import com.antiaction.zwave.Parameter;
import com.antiaction.zwave.constants.ControllerMessageType;
import com.antiaction.zwave.constants.MessageType;

public class SendData {

	private static final byte[] SENDDATA_HEADER = {(byte)ControllerMessageType.SendData.getId()};
	private static final byte[] SENDDATA_FOOTER = {(byte)0x05};

	// {(byte)0x00, (byte)0x13, nodeId, (byte)0x03, (byte)0x20, (byte)0x01, state, (byte)0x05}
	public static byte[] buildSendDataReq(int nodeId, Parameter parameter) {
		byte[] data2 = parameter.getBytes();
		byte[] data1 = {(byte)nodeId, (byte)data2.length};
		byte[] bytes = FrameUtils.assemble(MessageType.Request, SENDDATA_HEADER, data1, data2, SENDDATA_FOOTER);
		return bytes;
	}

	// 0x01 0x04 0x01 0x13 0x01 0xE8
	public static int parseSendDataResp(byte[] frame) {
		return frame[4] & 255;
	}

}
