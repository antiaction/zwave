package com.antiaction.zwave.messages;

import com.antiaction.zwave.FrameUtils;

/**
 * TODO
 * Resp: 0x01 0x16 0x00 0x49 0x84 0x03 0x10 0x04 0x08 0x04 0x80 0x46 0x81 0x72 0x8F 0x75 0x43 0x86 0x84 0xEF 0x46 0x81 0x8F 0x16
 * @author nicl
 */
public class ApplicationUpdateResp {

	protected byte[] frame;

	public int status;

	public int nodeId;

	public byte[] payload;

	public ApplicationUpdateData data;

	protected ApplicationUpdateResp() {
	}

	public static ApplicationUpdateResp getInstance() {
		return new ApplicationUpdateResp();
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
