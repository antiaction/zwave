package com.antiaction.zwave;

import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TransmitterThread implements Runnable {

	protected Thread thread;

	protected GenericQueue<byte[]> queue;

	protected OutputStream out;

	public TransmitterThread(GenericQueue<byte[]> queue, OutputStream out) {
		this.queue = queue;
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
				frame = queue.remove();
				// debug
				System.out.println(dateFormat.format(new Date()) + " < " + HexUtils.hexString(frame));
				out.write(frame);
				out.flush();
			}
		}
		catch (Throwable t) {
			t.printStackTrace();
		}
	}

}
