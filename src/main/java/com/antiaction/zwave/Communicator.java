package com.antiaction.zwave;

import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.antiaction.common.bind.BFSignal;
import com.antiaction.zwave.constants.Constants;
import com.antiaction.zwave.transport.SerialTransport;

public class Communicator {

	protected OutputStream out;

	protected InputStream in;

	protected GenericQueue<byte[]> queueIn;

	protected GenericQueue<Request> queueOut;

	protected TransmitterThread transmitterThread;

	protected ReceiverThread receiverThread;

	public Communicator() {
	}

	public void open(SerialTransport transport) {
		out = transport.getOutputStream();
		in = transport.getInputStream();
		queueIn = new GenericQueue<byte[]>();
		queueOut = new GenericQueue<Request>();
		transmitterThread = new TransmitterThread();
		transmitterThread.start();
		receiverThread = new ReceiverThread();
		receiverThread.start();
	}

	public void close() {
	}

	public void sendMessage(Request req) {
		queueOut.insert(req);
		signal.bfSignal(BF_SEND_MESSAGE);
	}

	public byte[] recvMessage() {
		return queueIn.remove();
	}

	public static final int BF_SEND_MESSAGE = 1 << 0;
	public static final int BF_ACK_RECEIVED = 1 << 1;
	public static final int BF_SEND_ACK = 1 << 2;

	protected BFSignal signal = new BFSignal();

	public class TransmitterThread implements Runnable {

		private static final int S_TRABSMIT_SEBD = 0;
		private static final int S_TRABSMIT_WAIT = 1;

		protected Thread thread;

		protected Object ackObj = new Object();

		public TransmitterThread() {
		}

		public void start() {
			thread = new Thread(this, this.getClass().getSimpleName());
			thread.start();
		}

		@Override
		public void run() {
			DateFormat dateFormat = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss.SSS Z']'");
			dateFormat.setLenient(false);
			dateFormat.setTimeZone(TimeZone.getDefault());
			Request req;
			byte[] frame;
			int bfSignal;
			int state = S_TRABSMIT_SEBD;
			try {
				while (true) {
					switch (state) {
					case S_TRABSMIT_SEBD:
						bfSignal = signal.bfPeek(BF_SEND_ACK);
						if ((bfSignal & BF_SEND_ACK) != 0) {
							System.out.println(dateFormat.format(new Date()) + " <  " + HexUtils.byteArrayToHexString(Constants.ackMsg));
							out.write(Constants.ackMsg);
							out.flush();
						}
						if (queueOut.items() > 0) {
							req = queueOut.remove();
							frame = req.getFrame();
							// debug
							System.out.println(dateFormat.format(new Date()) + " <  " + HexUtils.byteArrayToHexString(frame));
							out.write(frame);
							out.flush();
							state = S_TRABSMIT_WAIT;
						}
						else {
							bfSignal = signal.bfWait(BF_SEND_MESSAGE | BF_SEND_ACK, 1000);
							// debug
							//System.out.println(state + " " + (BF_SEND_MESSAGE | BF_SEND_ACK) + " " + bfSignal + " " + queueOut.items());
							if ((bfSignal & BF_SEND_ACK) != 0) {
								System.out.println(dateFormat.format(new Date()) + " <  " + HexUtils.byteArrayToHexString(Constants.ackMsg));
								out.write(Constants.ackMsg);
								out.flush();
							}
							if ((bfSignal & BF_SEND_MESSAGE) != 0) {
								if (queueOut.items() > 0) {
									req = queueOut.remove();
									frame = req.getFrame();
									// debug
									System.out.println(dateFormat.format(new Date()) + " <  " + HexUtils.byteArrayToHexString(frame));
									out.write(frame);
									out.flush();
									state = S_TRABSMIT_WAIT;
								}
							}
						}
						break;
					case S_TRABSMIT_WAIT:
						bfSignal = signal.bfWait(BF_ACK_RECEIVED | BF_SEND_ACK, 1000);
						// debug
						//System.out.println(state + " " + (BF_ACK_RECEIVED | BF_SEND_ACK) + " " + bfSignal + " " + queueOut.items());
						if ((bfSignal & BF_SEND_ACK) != 0) {
							System.out.println(dateFormat.format(new Date()) + " <  " + HexUtils.byteArrayToHexString(Constants.ackMsg));
							out.write(Constants.ackMsg);
							out.flush();
						}
						else {
							state = S_TRABSMIT_SEBD;
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

	public class ReceiverThread implements Runnable {

		private static final int S_RECEIVE_TYPE = 0;
		private static final int S_RECEIVE_LEN = 1;
		private static final int S_RECEIVE_PAYLOAD = 2;

		protected Thread thread;

		public ReceiverThread() {
		}

		public void start() {
			thread = new Thread(this, this.getClass().getSimpleName());
			thread.start();
		}

		@Override
		public void run() {
			DateFormat dateFormat = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss.SSS Z']'");
			dateFormat.setLenient(false);
			dateFormat.setTimeZone(TimeZone.getDefault());
			int state = S_RECEIVE_TYPE;
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
					case S_RECEIVE_TYPE:
						switch (c) {
						case Constants.SOF:
							pktType = (byte)(c & 255);
							// debug
							//System.out.println("Packet start");
							state = S_RECEIVE_LEN;
							break;
						case Constants.ACK:
							signal.bfSignal(BF_ACK_RECEIVED);
							// debug
							System.out.println(dateFormat.format(new Date()) + "  > 0x06");
							break;
						case Constants.NAK:
						case Constants.CAN:
						default:
							break;
						}
						break;
					case S_RECEIVE_LEN:
						len = (byte)(c & 255);
						frame = new byte[len + 2];
						idx = 0;
						frame[idx++] = (byte)pktType;
						frame[idx++] = (byte)(len);
						state = S_RECEIVE_PAYLOAD;
						break;
					case S_RECEIVE_PAYLOAD:
						frame[idx++] = (byte)(c & 255);
						--len;
						if (len == 0) {
							checksum = FrameUtils.calculateChecksum(frame);
							state = 0;
							// debug
							//System.out.println("Packet end: " + packetLen + " " + idx);
							System.out.println(dateFormat.format(new Date()) + "  > " + HexUtils.byteArrayToHexString(frame));
							// debug
							if (checksum != frame[frame.length - 1]) {
								// debug
								System.out.println(dateFormat.format(new Date()) + " Checksum error : " + checksum + " " + frame[frame.length - 1]);
							}
							signal.bfSignal(BF_SEND_ACK);
							queueIn.insert(frame);
							state = S_RECEIVE_TYPE;
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

}
