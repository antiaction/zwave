package com.antiaction.zwave.messages;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Semaphore;

import com.antiaction.zwave.CallbackResponse;
import com.antiaction.zwave.Controller;
import com.antiaction.zwave.FrameUtils;
import com.antiaction.zwave.Request;
import com.antiaction.zwave.constants.ControllerMessageType;
import com.antiaction.zwave.constants.MessageType;

/**
 * 	Req: 0x01 0x03 0x00 0x07 0xFB
 * Resp: 0x01 0x2B 0x01 0x07 0x01 0x00 0x00 0x86 0x00 0x01 0x00 0x5A 0xFE 0x81 0xFF 0x88 0x4F 0x1F 0x00 0x00 0xFB 0x9F 0x7D 0xA0 0x67 0x00 0x00 0x80 0x00 0x80 0x86 0x00 0x00 0x00 0xE8 0x73 0x00 0x00 0x0E 0x00 0x00 0x60 0x00 0x00 0xFB
 * @author nicl
 */
public class GetCapabilitiesReq extends Request {

	private static final int CONTROLLER_MESSAGE_TYPE = ControllerMessageType.GetCapabilities.getId() & 255;

	protected Controller controller;

	protected byte[] frame;

	protected GetCapabilitiesReq(Controller controller) {
		this.controller = controller;
	}

	public static GetCapabilitiesReq getInstance(Controller controller) {
		return new GetCapabilitiesReq(controller);
	}

	public GetCapabilitiesReq build() {
		byte[] data = new byte[] {
				(byte)CONTROLLER_MESSAGE_TYPE
		};
		frame = FrameUtils.assemble(MessageType.Request, data);
		return this;
	}

	public GetCapabilitiesResp send() {
		if (frame == null) {
			throw new IllegalStateException("frame not built!");
		}
		GetCapabilitiesResp resp = GetCapabilitiesResp.getInstance(controller);
		controller.callback(0x07, resp);
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

	public static class GetCapabilitiesResp implements CallbackResponse {

		protected Controller controller;

		protected Semaphore semaphore = new Semaphore(0);

		protected byte[] frame;

		public int majorVersion;

		public int minorVersion;

		public int manufactureId;

		public int deviceType;

		public int deviceId;

		public Set<Integer> commandSet;

		public List<Integer> commandList;

		protected GetCapabilitiesResp(Controller controller) {
			this.controller = controller;
		}

		public static GetCapabilitiesResp getInstance(Controller controller) {
			return new GetCapabilitiesResp(controller);
		}

		public void disassemble(byte[] frame) {
			this.frame = frame;
			byte[] data = FrameUtils.disassemble(frame);
			int idx = 0;
			majorVersion = data[idx++] & 255;
			minorVersion = data[idx++] & 255;
			manufactureId = ((data[idx++] & 255) << 8) | (data[idx++] & 255);
			deviceType = ((data[idx++] & 255) << 8) | (data[idx++] & 255);
			deviceId = ((data[idx++] & 255) << 8) | (data[idx++] & 255);
			commandSet = new TreeSet<>();
			commandList = new LinkedList<>();
			int bits;
			int bit = 1;
			//Optional<CommandClass> commandClass;
			while (idx < data.length) {
				bits = data[idx++] & 255;
				for (int i=0; i<8; ++i) {
					if ((bits & 1) == 1) {
						commandSet.add(bit);
						commandList.add(bit);
						/*
						commandClass = CommandClass.getType(bit);
						if (commandClass.isPresent()) {
							System.out.println(commandClass.get().getLabel());
						}
						else {
							System.out.println(bit);
						}
						*/
					}
					bits >>= 1;
					++bit;
				}
			}
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
