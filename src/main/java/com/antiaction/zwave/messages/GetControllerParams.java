package com.antiaction.zwave.messages;

import java.util.LinkedHashMap;

import com.antiaction.zwave.FrameUtils;
import com.antiaction.zwave.Parameter;
import com.antiaction.zwave.constants.MessageType;

public class GetControllerParams {

	private static final byte[] GET_HEADER = {(byte)0xF3};
	private static final byte[] GET_FOOTER = {(byte)0x05, (byte)0x00};

	// {(byte)0x00, (byte)0xF3, parameters, (byte)0x05, (byte)0x00}
	public static byte[] get(byte[] parameterIds) {
		byte[] bytes = FrameUtils.assemble(MessageType.Request, GET_HEADER, parameterIds, GET_FOOTER);
		return bytes;
	}

	// 0x01 0x0A 0x01 0xF3 0x06 0x51 0x01 0x01 0xDC 0x01 0x0A 0x87
	public static LinkedHashMap<Integer, Parameter> parseGet(byte[] frame) {
		LinkedHashMap<Integer, Parameter> parameters = new LinkedHashMap<Integer, Parameter>();
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
		return parameters;
	}

}
