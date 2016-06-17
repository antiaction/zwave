package com.antiaction.zwave.messages;

import java.util.LinkedHashMap;
import java.util.concurrent.Semaphore;

import com.antiaction.zwave.CallbackResponse;
import com.antiaction.zwave.Controller;
import com.antiaction.zwave.FrameUtils;
import com.antiaction.zwave.Parameter;
import com.antiaction.zwave.Request;
import com.antiaction.zwave.constants.MessageType;

/**
 * 	Req: {(byte)0x00, (byte)0xF3, parameters, (byte)0x05, (byte)0x00}
 * Resp: 0x01 0x0A 0x01 0xF3 0x06 0x51 0x01 0x01 0xDC 0x01 0x0A 0x87
 * @author nicl
 */
public class GetControllerParamsReq extends Request {

	private static final int CONTROLLER_MESSAGE_TYPE = 0xF3 & 255;

	private static final byte[] GET_FOOTER = {(byte)0x05, (byte)0x00};

	protected Controller controller;

	protected byte[] parameterIds;

	protected byte[] frame;

	protected GetControllerParamsReq(Controller controller) {
		this.controller = controller;
	}

	public static GetControllerParamsReq getInstance(Controller controller) {
		return new GetControllerParamsReq(controller);
	}

	public GetControllerParamsReq setParameterIds(byte[] parameterIds) {
		this.parameterIds = parameterIds;
		return this;
	}

	public GetControllerParamsReq build() {
		if (parameterIds == null) {
			throw new IllegalStateException("parameterIds not set!");
		}
		byte[] data = {
				(byte)CONTROLLER_MESSAGE_TYPE
		};
		frame = FrameUtils.assemble(MessageType.Request, data, parameterIds, GET_FOOTER);
		return this;
	}

	public GetControllerParamsResp send() {
		if (frame == null) {
			throw new IllegalStateException("frame not built!");
		}
		GetControllerParamsResp resp = GetControllerParamsResp.getInstance(controller);
		controller.callback(0xF3, resp);
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

	public static class GetControllerParamsResp implements CallbackResponse {

		protected Controller controller;

		protected Semaphore semaphore = new Semaphore(0);

		protected byte[] frame;

		public LinkedHashMap<Integer, Parameter> parameters;

		protected GetControllerParamsResp(Controller controller) {
			this.controller = controller;
		}

		public static GetControllerParamsResp getInstance(Controller controller) {
			return new GetControllerParamsResp(controller);
		}

		public void disassemble(byte[] frame) {
			this.frame = frame;
			parameters = new LinkedHashMap<Integer, Parameter>();
			Parameter parameter;
			int idx = 4;
			int frameLen = (byte)(frame[idx++] & 255);
			int len;
			while (frameLen > 0) {
				parameter = new Parameter();
				if (frameLen > 0) {
					parameter.id = (byte)(frame[idx++] & 255);
					--frameLen;
					if (frameLen > 0) {
						parameter.size = (byte)(frame[idx++] & 255);
						--frameLen;
					}
					if (frameLen >= 0) {
						len = parameter.size;
						parameter.value = new byte[len];
						System.arraycopy(frame, idx, parameter.value, 0, len);
						idx += len;
						frameLen -= len;
						parameters.put((int)parameter.id, parameter);
					}
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
