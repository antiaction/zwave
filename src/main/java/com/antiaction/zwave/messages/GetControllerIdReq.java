package com.antiaction.zwave.messages;

import java.util.concurrent.Semaphore;

import com.antiaction.zwave.CallbackResponse;
import com.antiaction.zwave.Controller;
import com.antiaction.zwave.FrameUtils;
import com.antiaction.zwave.constants.ControllerMessageType;
import com.antiaction.zwave.constants.MessageType;

/**
 * < 0x01 0x03 0x00 0x20 0xDC
 * > 0x01 0x08 0x01 0x20 0xF1 0xB7 0x9F 0x87 0x01 0x89
 * @author nicl
 */
public class GetControllerIdReq {

	protected Controller controller;

	protected Integer nodeId;

	protected byte[] frame;

	protected GetControllerIdReq(Controller controller) {
		this.controller = controller;
	}

	public static GetControllerIdReq getInstance(Controller controller) {
		return new GetControllerIdReq(controller);
	}

	public GetControllerIdReq build() {
		byte[] data = new byte[] {
				(byte)ControllerMessageType.MemoryGetId.getId()
		};
		frame = FrameUtils.assemble(MessageType.Request, data );
		return this;
	}

	public GetControllerIdResp send() {
		if (frame == null) {
			throw new IllegalStateException("frame not built!");
		}
		GetControllerIdResp resp = GetControllerIdResp.getInstance(controller);
		controller.callback(0x20, resp);
		controller.sendMessage(frame);
		return resp;
	}

	public static class GetControllerIdResp implements CallbackResponse {

		protected Controller controller;

		protected Semaphore semaphore = new Semaphore(0);

		protected byte[] frame;

		public int homeId;

		public int controllerId;

        protected GetControllerIdResp(Controller controller) {
			this.controller = controller;
		}

		public static GetControllerIdResp getInstance(Controller controller) {
			return new GetControllerIdResp(controller);
		}

		public void disassemble(byte[] frame) {
			this.frame = frame;
			byte[] data = FrameUtils.disassemble(frame);
			int idx = 0;
	        homeId = ((data[idx++] & 255) << 24) | ((data[idx++] & 255) << 16) | ((data[idx++] & 255) << 8) | (data[idx++] & 255);
	        controllerId = data[idx++] & 255;
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
