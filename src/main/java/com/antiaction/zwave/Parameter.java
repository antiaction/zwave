package com.antiaction.zwave;

public class Parameter {

	public byte id;

	public byte size;

	public byte[] value;

	public Parameter() {
	}

	public Parameter(byte id, byte[] value) {
		this.id = id;
		this.size = (byte)value.length;
		this.value = new byte[value.length];
		System.arraycopy(value, 0, this.value, 0, value.length);
	}

	public byte[] getBytes() {
		byte[] bytes = new byte[2 + value.length];
		bytes[0] = id;
		bytes[1] = size;
		System.arraycopy(value, 0, bytes, 2, value.length);
		return bytes;
	}

}
