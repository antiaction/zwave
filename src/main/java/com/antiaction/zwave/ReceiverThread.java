package com.antiaction.zwave;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.antiaction.zwave.constants.Constants;

public class ReceiverThread implements Runnable {

	private static final int S_TYPE = 0;
	private static final int S_LEN = 1;
	private static final int S_PAYLOAD = 2;

	protected Thread thread;

	protected InputStream in;

	protected GenericQueue<byte[]> queueIn;

	protected GenericQueue<byte[]> queueOut;

	public ReceiverThread(InputStream in, GenericQueue<byte[]> queueIn, GenericQueue<byte[]> queueOut) {
		this.in = in;
		this.queueIn = queueIn;
		this.queueOut = queueOut;
	}

	public void start() {
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
        DateFormat dateFormat = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss.SSS Z']'");
        dateFormat.setLenient(false);
        dateFormat.setTimeZone(TimeZone.getDefault());
		int state = S_TYPE;
		int c;
		int idx = 0;
		int pktType = 0;
		byte[] frame = null;
		int len = 0;
		byte checksum;
		try {
			while ((c = in.read()) != -1) {
				// debug
				//System.out.println(state + " - " + c);
				switch (state) {
				case S_TYPE:
					switch (c) {
					case Constants.SOF:
						pktType = (byte)(c & 255);
						// debug
						//System.out.println("Packet start");
						state = S_LEN;
						break;
					case Constants.ACK:
						// debug
						System.out.println(dateFormat.format(new Date()) + " > 0x06");
						break;
					case Constants.NAK:
					case Constants.CAN:
					default:
						break;
					}
					break;
				case S_LEN:
					len = (byte)(c & 255);
					frame = new byte[len + 2];
					idx = 0;
					frame[idx++] = (byte)pktType;
					frame[idx++] = (byte)(len);
					state = S_PAYLOAD;
					break;
				case S_PAYLOAD:
					frame[idx++] = (byte)(c & 255);
					--len;
					if (len == 0) {
						checksum = FrameUtils.calculateChecksum(frame);
						state = 0;
						// debug
						//System.out.println("Packet end: " + packetLen + " " + idx);
						System.out.println(dateFormat.format(new Date()) + " > " + HexUtils.hexString(frame));
						// debug
						if (checksum != frame[frame.length - 1]) {
							// debug
							System.out.println(dateFormat.format(new Date()) + " Checksum error : " + checksum + " " + frame[frame.length - 1]);
						}
						queueOut.insert(Constants.ackMsg);
						queueIn.insert(frame);
						state = S_TYPE;
					}
					break;
				}
			}
		}
		catch (Throwable t) {
			t.printStackTrace();
		}
	}

}
