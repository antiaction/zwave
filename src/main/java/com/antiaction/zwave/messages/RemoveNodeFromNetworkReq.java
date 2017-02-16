package com.antiaction.zwave.messages;

import java.util.concurrent.Semaphore;

import com.antiaction.zwave.Controller;
import com.antiaction.zwave.FrameUtils;
import com.antiaction.zwave.Request;
import com.antiaction.zwave.Response;
import com.antiaction.zwave.constants.ControllerMessageType;
import com.antiaction.zwave.constants.MessageType;

/**
 * Simple steps to remove device 10h
 * S 01 04 00 4B 01 B1              Send to the z-troller a START to remove a device
 * R 06
 * R 01 07 00 4B 03 01 00 00 B1
 * S 06
 * R 01 07 00 4B 03 02 00 00 B2
 * S 06
 * R 01 16 00 4B 03 03 10 0F 04 10 03 25 27 2B 2C 85 72 86 91 77 73 EF 82 26  The button on the device was pushed
 * S 06
 * R 01 07 00 4B 03 06 00 00 B6
 * S 06
 * S 01 04 00 4B 05 B4              Send the z-troller a END remove device
 * R 06
 *
 * @author nicl
 *
 */
public class RemoveNodeFromNetworkReq extends Request {

	private static final int CONTROLLER_MESSAGE_TYPE = ControllerMessageType.RemoveNodeFromNetwork.getId() & 255;

	public static final int START = 0x01;
	public static final int PROGRESS = 0x03;
	public static final int END = 0x05;

	public static final int PROGRESS_INIT = 0x01;
	public static final int PROGRESS_PREPARE = 0x02;
	public static final int PROGRESS_PUSHED = 0x03;
	public static final int PROGRESS_ADDED = 0x06;

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
