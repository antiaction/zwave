package com.antiaction.zwave.messages;

import java.util.concurrent.Semaphore;

import com.antiaction.zwave.Controller;
import com.antiaction.zwave.FrameUtils;
import com.antiaction.zwave.Parameter;
import com.antiaction.zwave.Request;
import com.antiaction.zwave.Response;
import com.antiaction.zwave.constants.MessageType;

/**
 *  Req: {(byte)0x00, (byte)0xF2, parameter, (byte)0x05, (byte)0x01}
 * Resp: 0x01 0x04 0x01 0xF2 0x01 0x09
 * @author nicl
 */
public class SetControllerParamReq extends Request {

	private static final int CONTROLLER_MESSAGE_TYPE = 0xF2 & 255;

	private static final byte[] SET_FOOTER = {(byte)0x05, (byte)0x01};

	protected Controller controller;

	protected Parameter parameter;

	protected byte[] frame;

	protected SetControllerParamResp response;

	protected SetControllerParamReq(Controller controller) {
		this.controller = controller;
	}

	public static SetControllerParamReq getInstance(Controller controller) {
		return new SetControllerParamReq(controller);
	}

	public SetControllerParamReq setParameter(Parameter parameter) {
		this.parameter = parameter;
		return this;
	}

	public SetControllerParamReq build() {
		if (parameter == null) {
			throw new IllegalStateException("parameter not set!");
		}
		byte[] data = {
				(byte)CONTROLLER_MESSAGE_TYPE
		};
		frame = FrameUtils.assemble(MessageType.Request, data, parameter.getBytes(), SET_FOOTER);
		return this;
	}

	@Override
	public SetControllerParamResp send() {
		if (frame == null) {
			throw new IllegalStateException("frame not built!");
		}
		response = SetControllerParamResp.getInstance(controller);
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
	public SetControllerParamResp getResponse() {
		return response;
	}

	public static class SetControllerParamResp extends Response {

		protected Controller controller;

		protected Semaphore semaphore = new Semaphore(0);

		protected byte[] frame;

		public int success;

		protected SetControllerParamResp(Controller controller) {
			this.controller = controller;
		}

		public static SetControllerParamResp getInstance(Controller controller) {
			return new SetControllerParamResp(controller);
		}

		@Override
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
