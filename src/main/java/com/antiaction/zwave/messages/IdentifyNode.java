package com.antiaction.zwave.messages;

import com.antiaction.zwave.Controller;
import com.antiaction.zwave.FrameUtils;
import com.antiaction.zwave.constants.Constants;
import com.antiaction.zwave.constants.ControllerMessageType;
import com.antiaction.zwave.constants.MessageType;

public class IdentifyNode {

	/*
	protected Controller controller;

	protected 

	protected IdentifyNodeReq(Controller controller) {
		this.controller = controller;
	}

	public static IdentifyNodeReq getInstance(Controller controller) {
		return new IdentifyNodeReq(controller);
	}

	public void setNodeId() {
	}
	*/

	// 0x01 0x04 0x00 0x41 0x01 0xBB
	public static byte[] buildIdentifyNodeReq(int nodeId) {
		byte[] data = new byte[] {
				(byte)ControllerMessageType.IdentifyNode.getId(),
				(byte)nodeId};
		byte[] bytes = FrameUtils.assemble(MessageType.Request, data );
		return bytes;
	}

	// 0x01 0x09 0x01 0x41 0x93 0x16 0x01 0x02 0x02 0x01 0x33
	public static int parseIdentifyNodeResp(byte[] frame) {
		return frame[Constants.PKT_IDX_TYPE] & 255;
	}

}
