package com.antiaction.zwave.messages;

import java.util.concurrent.Semaphore;

import com.antiaction.zwave.Controller;
import com.antiaction.zwave.FrameUtils;
import com.antiaction.zwave.Request;
import com.antiaction.zwave.Response;
import com.antiaction.zwave.constants.ControllerMessageType;
import com.antiaction.zwave.constants.MessageType;

public class RemoveNodeFromNetworkReq extends Request {

	private static final int CONTROLLER_MESSAGE_TYPE = ControllerMessageType.RemoveNodeFromNetwork.getId() & 255;

	public static final int START = 0x01;

	public static final int COMPLETE = 0x05;

	protected Controller controller;

	protected byte[] frame;

	protected RemoveNodeFromNetworkResp response;

	protected RemoveNodeFromNetworkReq(Controller controller) {
		this.controller = controller;
	}

	public static RemoveNodeFromNetworkReq getInstance(Controller controller) {
		return new RemoveNodeFromNetworkReq(controller);
	}

	public RemoveNodeFromNetworkReq build() {
		byte[] data = {
				(byte)CONTROLLER_MESSAGE_TYPE,
				(byte)START
		};
		frame = FrameUtils.assemble(MessageType.Request, data);
		return this;
	}

	@Override
	public RemoveNodeFromNetworkResp send() {
		if (frame == null) {
			throw new IllegalStateException("frame not built!");
		}
		response = RemoveNodeFromNetworkResp.getInstance(controller);
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
	public RemoveNodeFromNetworkResp getResponse() {
		return response;
	}

	public static class RemoveNodeFromNetworkResp extends Response {

		protected Controller controller;

		protected Semaphore semaphore = new Semaphore(0);

		protected byte[] frame;

		public int success;

		protected RemoveNodeFromNetworkResp(Controller controller) {
			this.controller = controller;
		}

		public static RemoveNodeFromNetworkResp getInstance(Controller controller) {
			return new RemoveNodeFromNetworkResp(controller);
		}

		@Override
		public void disassemble(byte[] frame) {
			this.frame = frame;
			byte[] data = FrameUtils.disassemble(frame);
			int idx = 0;
			//success = data[idx++] & 255;
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
