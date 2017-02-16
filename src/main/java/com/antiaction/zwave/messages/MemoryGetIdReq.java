package com.antiaction.zwave.messages;

import java.util.concurrent.Semaphore;

import com.antiaction.zwave.Controller;
import com.antiaction.zwave.FrameUtils;
import com.antiaction.zwave.Request;
import com.antiaction.zwave.Response;
import com.antiaction.zwave.constants.ControllerMessageType;
import com.antiaction.zwave.constants.MessageType;

/**
 * < 0x01 0x03 0x00 0x20 0xDC
 * > 0x01 0x08 0x01 0x20 0xF1 0xB7 0x9F 0x87 0x01 0x89
 * @author nicl
 */
public class MemoryGetIdReq extends Request {

	private static final int CONTROLLER_MESSAGE_TYPE = ControllerMessageType.MemoryGetId.getId() & 255;

	protected Controller controller;

	protected Integer nodeId;

	protected byte[] frame;

	protected MemoryGetIdResp response;

	protected MemoryGetIdReq(Controller controller) {
		this.controller = controller;
	}

	public static MemoryGetIdReq getInstance(Controller controller) {
		return new MemoryGetIdReq(controller);
	}

	public MemoryGetIdReq build() {
		byte[] data = new byte[] {
				(byte)CONTROLLER_MESSAGE_TYPE
		};
		frame = FrameUtils.assemble(MessageType.Request, data );
		return this;
	}

	@Override
	public MemoryGetIdResp send() {
		if (frame == null) {
			throw new IllegalStateException("frame not built!");
		}
		response = MemoryGetIdResp.getInstance(controller);
		controller.sendMessage(this);
		return response;
	}

	@Override
	public byte[] getFrame() {
		if (frame == null) {
			throw new IllegalStateException("frame not built!");
		}
		return frame;
	}

	@Override 
	public MemoryGetIdResp getResponse() {
		return response;
	}

	public static class MemoryGetIdResp extends Response {

		protected Controller controller;

		protected Semaphore semaphore = new Semaphore(0);

		protected byte[] frame;

		public int homeId;

		public int controllerId;

		protected MemoryGetIdResp(Controller controller) {
			this.controller = controller;
		}

		public static MemoryGetIdResp getInstance(Controller controller) {
			return new MemoryGetIdResp(controller);
		}

		@Override
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
