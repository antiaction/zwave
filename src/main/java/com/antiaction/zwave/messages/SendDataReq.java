package com.antiaction.zwave.messages;

import java.util.concurrent.Semaphore;

import com.antiaction.zwave.CallbackResponse;
import com.antiaction.zwave.Controller;
import com.antiaction.zwave.FrameUtils;
import com.antiaction.zwave.Parameter;
import com.antiaction.zwave.Request;
import com.antiaction.zwave.constants.ControllerMessageType;
import com.antiaction.zwave.constants.MessageType;

/**
 * 	Req: {(byte)0x00, (byte)0x13, nodeId, (byte)0x03, (byte)0x20, (byte)0x01, state, (byte)0x05}
 * Resp: 0x01 0x04 0x01 0x13 0x01 0xE8
 * @author nicl
 */
public class SendDataReq extends Request {

	private static final int CONTROLLER_MESSAGE_TYPE = ControllerMessageType.SendData.getId() & 255;

	protected Controller controller;

	protected Integer nodeId;

	protected byte[] payload;

	protected Byte callbackId;

	protected byte[] frame;

	protected SendDataReq(Controller controller) {
		this.controller = controller;
	}

	public static SendDataReq getInstance(Controller controller) {
		return new SendDataReq(controller);
	}

	public SendDataReq setNodeId(int nodeId) {
		this.nodeId = nodeId;
		return this;
	}

	public SendDataReq setParameter(Parameter parameter) {
		this.payload = parameter.getBytes();
		return this;
	}

	public SendDataReq setPayload(byte[] payload) {
		this.payload = new byte[payload.length];
		System.arraycopy(payload, 0, this.payload, 0, payload.length);
		return this;
	}

	public SendDataReq setCallbackId(Byte callbackId) {
		this.callbackId = callbackId;
		return this;
	}

	public SendDataReq build() {
		if (nodeId == null) {
			throw new IllegalStateException("nodeId not set!");
		}
		if (payload == null) {
			throw new IllegalStateException("payload not set!");
		}
		byte[] data = {
				(byte)CONTROLLER_MESSAGE_TYPE,
				(byte)nodeId.intValue(),
				(byte)payload.length
		};
		if (callbackId == null) {
			frame = FrameUtils.assemble(MessageType.Request, data, payload);
		}
		else {
			byte[] callbackIdArr = {callbackId};
			frame = FrameUtils.assemble(MessageType.Request, data, payload, callbackIdArr);
		}
		return this;
	}

	public SendDataResp send() {
		if (frame == null) {
			throw new IllegalStateException("frame not built!");
		}
		SendDataResp resp = SendDataResp.getInstance(controller);
		controller.callback(0x13, resp);
		controller.sendMessage(this);
		return resp;
	}

	@Override
	public byte[] getFrame() {
		if (frame == null) {
			throw new IllegalStateException("frame not built!");
		}
		return frame;
	}

	public static class SendDataResp implements CallbackResponse {

		protected Controller controller;

		protected Semaphore semaphore = new Semaphore(0);

		protected byte[] frame;

		public int success;

		protected SendDataResp(Controller controller) {
			this.controller = controller;
		}

		public static SendDataResp getInstance(Controller controller) {
			return new SendDataResp(controller);
		}

		public void disassemble(byte[] frame) {
			this.frame = frame;
			byte[] data = FrameUtils.disassemble(frame);
			int idx = 0;
			success = data[idx++] & 255;
			semaphore.release();
		}

		public void waitFor() {
			try {
				semaphore.acquire();
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
