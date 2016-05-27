package com.antiaction.zwave.messages;

import java.util.concurrent.Semaphore;

import com.antiaction.zwave.CallbackResponse;
import com.antiaction.zwave.Controller;
import com.antiaction.zwave.FrameUtils;
import com.antiaction.zwave.constants.Constants;
import com.antiaction.zwave.constants.ControllerMessageType;
import com.antiaction.zwave.constants.MessageType;

/**
 *  Req: 0x01 0x04 0x00 0x41 0x01 0xBB
 * Resp: 0x01 0x09 0x01 0x41 0x93 0x16 0x01 0x02 0x02 0x01 0x33
 * @author nicl
 */
public class IdentifyNodeReq {

	protected Controller controller;

	protected Integer nodeId;

	protected byte[] frame;

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
				(byte)ControllerMessageType.IdentifyNode.getId(),
				(byte)nodeId.intValue()
		};
		frame = FrameUtils.assemble(MessageType.Request, data );
		return this;
	}

	public IdentifyNodeResp send() {
		if (frame == null) {
			throw new IllegalStateException("frame not built!");
		}
		IdentifyNodeResp resp = IdentifyNodeResp.getInstance(controller);
		controller.callback(0x41, resp);
		controller.sendMessage(frame);
		return resp;
	}

	public static class IdentifyNodeResp implements CallbackResponse {

		protected Controller controller;

		protected Semaphore semaphore = new Semaphore(0);

		protected byte[] frame;

		public int type;

		protected IdentifyNodeResp(Controller controller) {
			this.controller = controller;
		}

		public static IdentifyNodeResp getInstance(Controller controller) {
			return new IdentifyNodeResp(controller);
		}

		public void disassemble(byte[] frame) {
			this.frame = frame;
			type = frame[Constants.PKT_IDX_TYPE] & 255;
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
