package com.antiaction.zwave;

public class HexUtils {

	protected HexUtils() {
	}

	protected static final char[] hexAsciiTab = "0123456789ABCDEF".toCharArray();

	public static String byteArrayToHexString(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		int c;
		for (int i=0; i<bytes.length; ++i) {
			c = bytes[i] & 255;
			if (i > 0) {
				sb.append(' ');
			}
			sb.append("0x");
			sb.append(hexAsciiTab[c >> 4]);
			sb.append(hexAsciiTab[c & 15]);
		}
		return sb.toString();
	}

	public static String byteToHexString(int b) {
		StringBuilder sb = new StringBuilder(4);
		sb.append("0x");
		sb.append(hexAsciiTab[(b & 255) >> 4]);
		sb.append(hexAsciiTab[b & 15]);
		return sb.toString();
	}

	public static String byteCharToHexString(int b) {
		StringBuilder sb = new StringBuilder(4);
		sb.append("0x");
		sb.append(hexAsciiTab[(b >> 12) & 15]);
		sb.append(hexAsciiTab[(b >> 8) & 15]);
		sb.append(hexAsciiTab[(b >> 4) & 15]);
		sb.append(hexAsciiTab[b & 15]);
		return sb.toString();
	}

	public static String byteIntToHexString(int b) {
		StringBuilder sb = new StringBuilder(4);
		sb.append("0x");
		sb.append(hexAsciiTab[(b >> 28) & 15]);
		sb.append(hexAsciiTab[(b >> 24) & 15]);
		sb.append(hexAsciiTab[(b >> 20) & 15]);
		sb.append(hexAsciiTab[(b >> 16) & 15]);
		sb.append(hexAsciiTab[(b >> 12) & 15]);
		sb.append(hexAsciiTab[(b >> 8) & 15]);
		sb.append(hexAsciiTab[(b >> 4) & 15]);
		sb.append(hexAsciiTab[b & 15]);
		return sb.toString();
	}

	public static String byteString(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		int c;
		for (int i=0; i<bytes.length; ++i) {
			c = bytes[i] & 255;
			if (i > 0) {
				sb.append(' ');
			}
			sb.append(c);
		}
		return sb.toString();
	}

}
