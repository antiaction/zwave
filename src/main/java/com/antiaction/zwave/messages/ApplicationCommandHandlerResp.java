package com.antiaction.zwave.messages;

import com.antiaction.zwave.Controller;
import com.antiaction.zwave.FrameUtils;
import com.antiaction.zwave.messages.command.ApplicationCommandHandlerData;

/**
 * Resp: 0x01 0x0E 0x00 0x04 0x00 0x02 0x08 0x72 0x05 0x00 0x86 0x00 0x02 0x00 0x64 0x68
 * @author nicl
 */
public class ApplicationCommandHandlerResp {

	protected Controller controller;

	protected byte[] frame;

	public int status;

	public int nodeId;

	public byte[] payload;

	public ApplicationCommandHandlerData data;

	protected ApplicationCommandHandlerResp(Controller controller) {
		this.controller = controller;
	}

	public static ApplicationCommandHandlerResp getInstance(Controller controller) {
		return new ApplicationCommandHandlerResp(controller);
	}

	public void disassemble(byte[] frame) {
		this.frame = frame;
		byte[] data = FrameUtils.disassemble(frame);
		int idx = 0;
		status = data[idx++] & 255;
		nodeId = data[idx++] & 255;
		int len = data[idx++] & 255;
		payload = new byte[len];
		System.arraycopy(data, idx, payload, 0, len);
	}

}
