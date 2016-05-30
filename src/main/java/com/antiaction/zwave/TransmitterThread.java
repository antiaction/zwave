package com.antiaction.zwave;

import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TransmitterThread implements Runnable {

	protected Thread thread;

	protected GenericQueue<byte[]> queueOut;

	protected OutputStream out;

	protected Object ackObj = new Object();

	public TransmitterThread(GenericQueue<byte[]> queueOut, OutputStream out) {
		this.queueOut = queueOut;
		this.out = out;
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
		byte[] frame = null;
		try {
			while (true) {
				frame = queueOut.remove();
				// debug
				System.out.println(dateFormat.format(new Date()) + " < " + HexUtils.byteArrayToHexString(frame));
				out.write(frame);
				out.flush();
				// FIXME
				/*
				synchronized (ackObj) {
					ackObj.wait(1000);
				}
				*/
			}
		}
		catch (Throwable t) {
			t.printStackTrace();
		}
	}

}
