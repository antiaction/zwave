package com.antiaction.zwave.messages;

import java.util.concurrent.Semaphore;

import com.antiaction.zwave.Controller;
import com.antiaction.zwave.FrameUtils;
import com.antiaction.zwave.Request;
import com.antiaction.zwave.Response;
import com.antiaction.zwave.constants.BasicDeviceClass;
import com.antiaction.zwave.constants.ControllerMessageType;
import com.antiaction.zwave.constants.GenericDeviceClass;
import com.antiaction.zwave.constants.MessageType;
import com.antiaction.zwave.constants.SpecificDeviceClass;

/**
 *  Req: 0x01 0x04 0x00 0x41 0x01 0xBB
 * Resp: 0x01 0x09 0x01 0x41 0x93 0x16 0x01 0x02 0x02 0x01 0x33
 * @author nicl
 */
public class IdentifyNodeReq extends Request {

	private static final int CONTROLLER_MESSAGE_TYPE = ControllerMessageType.IdentifyNode.getId() & 255;

	protected Controller controller;

	protected Integer nodeId;

	protected byte[] frame;

	protected IdentifyNodeResp response;

	protected IdentifyNodeReq(Controller controller) {
		this.controller = controller;
	}

	public static IdentifyNodeReq getInstance(Controller controller) {
		return new IdentifyNodeReq(controller);
	}

	public IdentifyNodeReq setNodeId(int nodeId) {
		this.nodeId = nodeId;
		return this;
	}

	public IdentifyNodeReq build() {
		if (nodeId == null) {
			throw new IllegalStateException("nodeId not set!");
		}
		byte[] data = new byte[] {
				(byte)CONTROLLER_MESSAGE_TYPE,
				(byte)nodeId.intValue()
		};
		frame = FrameUtils.assemble(MessageType.Request, data );
		return this;
	}

	@Override 
	public IdentifyNodeResp send() {
		if (frame == null) {
			throw new IllegalStateException("frame not built!");
		}
		response = IdentifyNodeResp.getInstance(controller);
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
	public IdentifyNodeResp getResponse() {
		return response;
	}

	public static class IdentifyNodeResp extends Response {

		protected Controller controller;

		protected Semaphore semaphore = new Semaphore(0);

		protected byte[] frame;

		public boolean listening;

		public boolean routing;

		public int version;

		public int maxBaudRate;

		public boolean frequentlyListening;

		public boolean beaming;

		public boolean security;

		public int basicDeviceClassId;

		public BasicDeviceClass basicDeviceClass;

		public int genericDeviceClassId;

		public GenericDeviceClass genericDeviceClass;

		public int optionalSpecificClassId;

		public SpecificDeviceClass optionalSpecificClass;

		protected IdentifyNodeResp(Controller controller) {
			this.controller = controller;
		}

		public static IdentifyNodeResp getInstance(Controller controller) {
			return new IdentifyNodeResp(controller);
		}

		@Override
		public void disassemble(byte[] frame) {
			this.frame = frame;
			byte[] data = FrameUtils.disassemble(frame);
			listening = (data[0] & 0x80) != 0;
			routing = (data[0] & 0x40) != 0;
			version = (data[0] & 0x07) + 1;
			maxBaudRate = 9600;
			if ((data[0] & 0x38) == 0x10) {
				maxBaudRate = 40000;
			}
			frequentlyListening = (data[1] & 0x60) != 0;
			beaming = ((data[1] & 0x10) != 0);
			security = ((data[1] & 0x01) != 0);
			int idx = 3;
			basicDeviceClassId = data[idx++] & 255;
			genericDeviceClassId = data[idx++] & 255;
			optionalSpecificClassId = data[idx++] & 255;
			basicDeviceClass = BasicDeviceClass.getType(basicDeviceClassId);
			genericDeviceClass = GenericDeviceClass.getType(genericDeviceClassId);
			if (genericDeviceClass != null) {
				optionalSpecificClass = genericDeviceClass.getSpecificDeviceClass(optionalSpecificClassId);
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
