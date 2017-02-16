package com.antiaction.zwave.messages;

import java.util.concurrent.Semaphore;

import com.antiaction.zwave.Controller;
import com.antiaction.zwave.FrameUtils;
import com.antiaction.zwave.Request;
import com.antiaction.zwave.Response;
import com.antiaction.zwave.constants.ControllerMessageType;
import com.antiaction.zwave.constants.MessageType;

/**
 * Simple steps to add a device
 * S 01 04 00 4A 01 B0             Request the z-trolle to START adding a device
 * R 06
 * R 01 07 00 4A 02 01 00 00 B1
 * S 06
 * R 01 07 00 4A 02 02 00 00 B2
 * S 06   
 * R 01 16 00 4A 02 03 12 0F 04 10 03 25 27 2B 2C 85 72 86 91 77 73 EF 82 24  The button was pushed
 * S 06
 * R 01 07 00 4A 02 06 12 00 A4    The device 12h was added
 * S 06
 * S 01 04 00 4A 05 B4             Send the z-troller a adding device completion
 * R 06
 *
 * 0x01 0x07 0x00 0x4A 0x03 0x01 0x00 0x00 0xB0
 *
 * @author nicl
 *
 */
public class AddNodeToNetworkReq extends Request {

	private static final int CONTROLLER_MESSAGE_TYPE = ControllerMessageType.AddNodeToNetwork.getId() & 255;

	public static final int START = 0x01;

	public static final int COMPLETE = 0x05;

	protected Controller controller;

	protected byte[] frame;

	protected AddNodeToNetworkResp response;

	protected AddNodeToNetworkReq(Controller controller) {
		this.controller = controller;
	}

	public static AddNodeToNetworkReq getInstance(Controller controller) {
		return new AddNodeToNetworkReq(controller);
	}

	public AddNodeToNetworkReq build() {
		byte[] data = {
				(byte)CONTROLLER_MESSAGE_TYPE,
				//(byte)START
				(byte)COMPLETE
		};
		frame = FrameUtils.assemble(MessageType.Request, data);
		return this;
	}

	@Override
	public AddNodeToNetworkResp send() {
		if (frame == null) {
			throw new IllegalStateException("frame not built!");
		}
		response = AddNodeToNetworkResp.getInstance(controller);
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
	public AddNodeToNetworkResp getResponse() {
		return response;
	}

	public static class AddNodeToNetworkResp extends Response {

		protected Controller controller;

		protected Semaphore semaphore = new Semaphore(0);

		protected byte[] frame;

		public int success;

		protected AddNodeToNetworkResp(Controller controller) {
			this.controller = controller;
		}

		public static AddNodeToNetworkResp getInstance(Controller controller) {
			return new AddNodeToNetworkResp(controller);
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
/*
[2016-07-13 00:16:13.772 +0200] <  0x01 0x04 0x00 0x4A 0x01 0xB0
[2016-07-13 00:16:13.774 +0200]  > 0x01 0x07 0x00 0x4A 0x03 0x07 0x00 0x00 0xB6
[2016-07-13 00:19:18.285 +0200]  > 0x01 0x07 0x00 0x4A 0x03 0x02 0x00 0x00 0xB3
[2016-07-13 00:19:18.440 +0200]  > 0x01 0x18 0x00 0x4A 0x03 0x03 0x04 0x11 0x04 0x10 0x00 0x72 0x86 0x70 0x85 0x8E 0x25 0x73 0x32 0x31 0x7A 0xEF 0x25 0x32 0x31 0xC5
[2016-07-13 00:19:26.658 +0200]  > 0x01 0x07 0x00 0x4A 0x03 0x05 0x04 0x00 0xB0

[2016-07-15 23:19:30.696 +0200] <  0x01 0x04 0x00 0x4A 0x05 0xB4
[2016-07-15 23:19:30.747 +0200]  > 0x01 0x07 0x00 0x4A 0x03 0x06 0x04 0x00 0xB3
*/
