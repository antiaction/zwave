package com.antiaction.zwave;

import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TimeZone;

import com.antiaction.common.bind.BFSignal;
import com.antiaction.zwave.constants.Constants;
import com.antiaction.zwave.constants.MessageType;
import com.antiaction.zwave.messages.ApplicationCommandHandlerResp;
import com.antiaction.zwave.messages.ApplicationUpdateResp;
import com.antiaction.zwave.messages.command.ApplicationCommandHandlerData;
import com.antiaction.zwave.messages.command.BasicCommand;
import com.antiaction.zwave.messages.command.BatteryCommand;
import com.antiaction.zwave.messages.command.ClockCommand;
import com.antiaction.zwave.messages.command.ConfigurationCommand;
import com.antiaction.zwave.messages.command.ManufacturerSpecificCommand;
import com.antiaction.zwave.messages.command.ProtectionCommand;
import com.antiaction.zwave.messages.command.BinarySensorCommand;
import com.antiaction.zwave.messages.command.SensorMultiLevelCommand;
import com.antiaction.zwave.messages.command.BinarySwitchCommand;
import com.antiaction.zwave.messages.command.MultiLevelSwitchCommand;
import com.antiaction.zwave.messages.command.ThermostatSetpointCommand;
import com.antiaction.zwave.messages.command.VersionCommand;
import com.antiaction.zwave.messages.command.WakeUpCommand;
import com.antiaction.zwave.transport.SerialTransport;

public class Communicator {

	protected OutputStream out;

	protected InputStream in;

	protected GenericQueue<byte[]> queueIn;

	protected GenericQueue<Request> queueOut;

	protected TransmitterThread transmitterThread;

	protected ReceiverThread receiverThread;

	protected Request request;

	protected Response response;

	protected ApplicationUpdateThread applicationUpdateThread; 

	protected ApplicationCommandHandlerThread applicationCommandHandlerThread;

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
		applicationUpdateThread = new ApplicationUpdateThread();
		applicationUpdateThread.start();
		applicationCommandHandlerThread = new ApplicationCommandHandlerThread();
		applicationCommandHandlerThread.start();
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
	public static final int BF_RESPONSE_RECEIVED = 1 << 3;

	protected BFSignal signal = new BFSignal();

	public class TransmitterThread implements Runnable {

		private static final int S_TRABSMIT_SEBD_FRAME = 0;
		private static final int S_TRABSMIT_WAITFOR_ACK = 1;
		private static final int S_TRABSMIT_WAITFOR_RESPONSE = 2;

		protected Thread thread;

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
			byte[] frame;
			int bfSignal;
			int state = S_TRABSMIT_SEBD_FRAME;
			try {
				while (true) {
					switch (state) {
					case S_TRABSMIT_SEBD_FRAME:
						bfSignal = signal.bfPeek(BF_SEND_ACK);
						if ((bfSignal & BF_SEND_ACK) != 0) {
							System.out.println(dateFormat.format(new Date()) + " <  " + HexUtils.byteArrayToHexString(Constants.ackMsg));
							out.write(Constants.ackMsg);
							out.flush();
						}
						if (queueOut.items() > 0) {
							request = queueOut.remove();
							frame = request.getFrame();
							response = request.getResponse();
							// debug
							System.out.println(dateFormat.format(new Date()) + " <  " + HexUtils.byteArrayToHexString(frame));
							out.write(frame);
							out.flush();
							state = S_TRABSMIT_WAITFOR_ACK;
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
									request = queueOut.remove();
									frame = request.getFrame();
									response = request.getResponse();
									// debug
									System.out.println(dateFormat.format(new Date()) + " <  " + HexUtils.byteArrayToHexString(frame));
									out.write(frame);
									out.flush();
									state = S_TRABSMIT_WAITFOR_ACK;
								}
							}
						}
						break;
					case S_TRABSMIT_WAITFOR_ACK:
						bfSignal = signal.bfWait(BF_ACK_RECEIVED | BF_SEND_ACK, 1000);
						// debug
						//System.out.println(state + " " + (BF_ACK_RECEIVED | BF_SEND_ACK) + " " + bfSignal + " " + queueOut.items());
						if ((bfSignal & BF_SEND_ACK) != 0) {
							System.out.println(dateFormat.format(new Date()) + " <  " + HexUtils.byteArrayToHexString(Constants.ackMsg));
							out.write(Constants.ackMsg);
							out.flush();
						}
						else if ((bfSignal & BF_ACK_RECEIVED) != 0) {
							state = S_TRABSMIT_WAITFOR_RESPONSE;
						}
						else {
							// TODO No ACK - add retry code...
							response = null;
							state = S_TRABSMIT_SEBD_FRAME;
						}
						break;
					case S_TRABSMIT_WAITFOR_RESPONSE:
						bfSignal = signal.bfWait(BF_RESPONSE_RECEIVED | BF_SEND_ACK, 1000);
						// debug
						//System.out.println(state + " " + (BF_RESPONSE_RECEIVED | BF_SEND_ACK) + " " + bfSignal + " " + queueOut.items());
						if ((bfSignal & BF_SEND_ACK) != 0) {
							System.out.println(dateFormat.format(new Date()) + " <  " + HexUtils.byteArrayToHexString(Constants.ackMsg));
							out.write(Constants.ackMsg);
							out.flush();
						}
						else {
							response = null;
							state = S_TRABSMIT_SEBD_FRAME;
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
			int messageType;
			int command;
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
							messageType = frame[2] & 255;
							command = frame[3] & 255;
							if (messageType == MessageType.Response.ordinal()) {
								if (response != null) {
									response.disassemble(frame);
									response = null;
									signal.bfSignal(BF_RESPONSE_RECEIVED);
								}
								else {
									System.out.println("Unexpected response frame: " + HexUtils.byteArrayToHexString(frame));
								}
							}
							else if (messageType == MessageType.Request.ordinal()) {
								switch (command) {
								case 0x04:
									// ApplicationCommandHandler: Sensor data etc.
									ApplicationCommandHandlerResp applicationCommandHandlerResp = ApplicationCommandHandlerResp.getInstance();
									applicationCommandHandlerResp.disassemble(frame);
									onApplicationCommandHandler(applicationCommandHandlerResp);
									break;
								case 0x49:
									// ApplicationUpdate: Wake up event etc.
									ApplicationUpdateResp applicationUpdateResp = ApplicationUpdateResp.getInstance();
									applicationUpdateResp.disassemble(frame);
									onApplicationUpdate(applicationUpdateResp);
									break;
								case 0x4a:
									// AddNodeToNetwork.
								case 0x4b:
									// RemoveNodeFromNetwork.
								default:
									System.out.println("Unsupported request frame: " + HexUtils.byteArrayToHexString(frame));
									break;
								}
								// debug
								//System.out.println(pkt.length);
							}
							else {
								System.out.println("Unsupported response frame: " + HexUtils.byteArrayToHexString(frame));
							}
							//queueIn.insert(frame);
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

	/** Set of registered listeners. */
	private Set<ApplicationListener> listenerSet = new HashSet<ApplicationListener>();

	/**
	 * Adds a listener to the list that is notified each time a change
	 * to the data model occurs.
	 * @param l	the ApplicationListener
	 */
	public void addListener(ApplicationListener l) {
		synchronized ( listenerSet ) {
			listenerSet.add( l );
		}
	}

	/**
	 * Removes a listener from the list that is notified each time a
	 * change to the data model occurs.
	 * @param l the ApplicationListener
	 */
	public void removeListener(ApplicationListener l) {
		synchronized ( listenerSet ) {
			listenerSet.remove( l );
		}
	}

	public void onApplicationUpdate(ApplicationUpdateResp applicationUpdateResp) {
		queueApplicationUpdate.insert(applicationUpdateResp);
	}

	protected GenericQueue<ApplicationUpdateResp> queueApplicationUpdate = new GenericQueue<ApplicationUpdateResp>();

	public class ApplicationUpdateThread implements Runnable {
		protected Thread thread;
		public ApplicationUpdateThread() {
		}
		public void start() {
			thread = new Thread(this, this.getClass().getSimpleName());
			thread.start();
		}
		@Override
		public void run() {
			ApplicationUpdateResp applicationUpdateResp;
			try {
				while (true) {
					applicationUpdateResp = queueApplicationUpdate.remove();
					synchronized ( listenerSet ) {
						Iterator<ApplicationListener> listeners = listenerSet.iterator();
						while ( listeners.hasNext() ) {
							listeners.next().onApplicationUpdate(applicationUpdateResp);
						}
					}
				}
			}
			catch (Throwable t) {
				t.printStackTrace();
			}
		}
	}

	public void onApplicationCommandHandler(ApplicationCommandHandlerResp applicationCommandHandlerResp) {
		// debug
		/*
		Optional<CommandClass> optionalCommandClass = CommandClass.getType(applicationCommandHandlerResp.payload[0]);
		String commandClassStr;
		if (optionalCommandClass.isPresent()) {
			CommandClass commandClass = optionalCommandClass.get();
			commandClassStr = commandClass.getLabel() + " (" + HexUtils.hexString(applicationCommandHandlerResp.payload[0]) + ")";
		}
		else {
			commandClassStr = "Unknown (" + HexUtils.hexString(applicationCommandHandlerResp.payload[0]) + ")";
		}
		*/
		// debug
		//System.out.println("ApplicationCommandHandlerResp nodeId: " + applicationCommandHandlerResp.nodeId + " CommandClass: " + commandClassStr );

		byte[] data = applicationCommandHandlerResp.payload;
		ApplicationCommandHandlerData achData;

		// CommandClass.
		switch (data[0]) {
		case (byte)0x20:
			achData = BasicCommand.disassemble(data);
			applicationCommandHandlerResp.data = achData;
			break;
		case (byte)0x25:
			achData = BinarySwitchCommand.disassemble(data);
			applicationCommandHandlerResp.data = achData;
			break;
		case (byte)0x26:
			achData = MultiLevelSwitchCommand.disassemble(data);
			applicationCommandHandlerResp.data = achData;
			break;
		case (byte)0x30:
			achData = BinarySensorCommand.disassemble(data);
			applicationCommandHandlerResp.data = achData;
			break;
		case (byte)0x31:
			achData = SensorMultiLevelCommand.disassemble(data);
			applicationCommandHandlerResp.data = achData;
			break;
		case (byte)0x43:
			achData = ThermostatSetpointCommand.disassemble(data);
			applicationCommandHandlerResp.data = achData;
			break;
		case (byte)0x70:
			achData = ConfigurationCommand.disassemble(data);
			applicationCommandHandlerResp.data = achData;
			break;
		case (byte)0x72:
			achData = ManufacturerSpecificCommand.disassemble(data);
			applicationCommandHandlerResp.data = achData;
			break;
		case (byte)0x75:
			achData = ProtectionCommand.disassemble(data);
			applicationCommandHandlerResp.data = achData;
			break;
		case (byte)0x80:
			achData = BatteryCommand.disassemble(data);
			applicationCommandHandlerResp.data = achData;
			break;
		case (byte)0x81:
			achData = ClockCommand.disassemble(data);
			applicationCommandHandlerResp.data = achData;
			break;
		case (byte)0x84:
			achData = WakeUpCommand.disassemble(data);
			applicationCommandHandlerResp.data = achData;
			break;
		case (byte)0x86:
			achData = VersionCommand.disassemble(data);
			applicationCommandHandlerResp.data = achData;
			break;
		default:
			achData = new UnknownApplicationCommandHandlerData(data);
			applicationCommandHandlerResp.data = achData;
			break;
		}

		queueApplicationCommandHandler.insert(applicationCommandHandlerResp);
	}

	protected GenericQueue<ApplicationCommandHandlerResp> queueApplicationCommandHandler = new GenericQueue<ApplicationCommandHandlerResp>();

	public class ApplicationCommandHandlerThread implements Runnable {
		protected Thread thread;
		public ApplicationCommandHandlerThread() {
		}
		public void start() {
			thread = new Thread(this, this.getClass().getSimpleName());
			thread.start();
		}
		@Override
		public void run() {
			ApplicationCommandHandlerResp applicationCommandHandlerResp;
			try {
				while (true) {
					applicationCommandHandlerResp = queueApplicationCommandHandler.remove();
					synchronized ( listenerSet ) {
						Iterator<ApplicationListener> listeners = listenerSet.iterator();
						while ( listeners.hasNext() ) {
							listeners.next().onApplicationCommandHandler(applicationCommandHandlerResp);
						}
					}
				}
			}
			catch (Throwable t) {
				t.printStackTrace();
			}
		}
	}

	public static class UnknownApplicationCommandHandlerData extends ApplicationCommandHandlerData {
		public byte[] data;
		public UnknownApplicationCommandHandlerData(byte[] data) {
			this.data = data;
		}
	}

}
