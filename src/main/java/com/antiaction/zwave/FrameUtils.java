package com.antiaction.zwave;

import com.antiaction.zwave.constants.Constants;
import com.antiaction.zwave.constants.MessageType;

public class FrameUtils {

	protected FrameUtils() {
	}

	public static byte calculateChecksum(byte[] frame) {
		int offset = 1;
		byte ret = frame[offset];
		for (int i = offset + 1; i < frame.length - 1; i++) {
			// Xor bytes
			ret ^= frame[i];
		}
		// Not result
		ret = (byte)(~ret);
		return ret;
	}

	public static byte[] assemble(MessageType msgType, byte[]... data) {
		int len = 0;
		for (int i=0; i<data.length; ++i) {
			len += data[i].length;
		}
		int idx = 0;
		byte[] frame = new byte[len + 4];
		frame[idx++] = (byte)Constants.SOF;
		// data + msgType + Checksum
		frame[idx++] = (byte)(len + 2);
		frame[idx++] = (byte)msgType.ordinal();
		for (int i=0; i<data.length; ++i) {
			len = data[i].length;
			System.arraycopy(data[i], 0, frame, idx, len);
			idx += len;
		}
		frame[idx] = calculateChecksum(frame);
		return frame;
	}

	public static byte[] disassemble(byte[] frame) {
		byte[] data = new byte[frame.length - 5];
		System.arraycopy(frame, 4, data, 0, frame.length - 5);
		return data;
	}

	/*
	public static byte[] build(byte[] data) {
		byte[] frame = new byte[data.length + 3];
		frame[0] = (byte)0x01;
		frame[1] = (byte)(data.length + 1);
		System.arraycopy(data, 0, frame, 2, data.length);
		frame[frame.length - 1] = calculateChecksum(frame);
		return frame;
	}
	*/

	/*
	public static byte[] build(byte[]... data) {
		int len = 0;
		for (int i=0; i<data.length; ++i) {
			len += data[i].length;
		}
		int idx = 0;
		byte[] frame = new byte[len + 3];
		frame[idx++] = (byte)0x01;
		frame[idx++] = (byte)(len + 1);
		for (int i=0; i<data.length; ++i) {
			len = data[i].length;
			System.arraycopy(data[i], 0, frame, idx, len);
			idx += len;
		}
		frame[idx] = calculateChecksum(frame);
		return frame;
	}
	*/

}
