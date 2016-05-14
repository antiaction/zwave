package com.antiaction.zwave;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.antiaction.zwave.constants.Constants;
import com.antiaction.zwave.constants.MessageType;
import com.antiaction.zwave.messages.GetControllerParams;
import com.antiaction.zwave.messages.IdentifyNode;
import com.antiaction.zwave.messages.SendData;
import com.antiaction.zwave.messages.SerialApiGetInitData;
import com.antiaction.zwave.messages.SetControllerParam;
import com.antiaction.zwave.transport.SerialTransport;

public class Controller {

	protected SerialTransport transport;

	protected OutputStream out;

	protected InputStream in;

	protected GenericQueue<byte[]> queueIn;

	protected GenericQueue<byte[]> queueOut;

	protected TransmitterThread transmitterThread;

	protected ReceiverThread receiverThread;

	public Controller() {
	}

	public void open(SerialTransport transport) {
		queueIn = new GenericQueue<byte[]>();
		queueOut = new GenericQueue<byte[]>();
		transmitterThread = new TransmitterThread(queueOut, transport.getOutputStream());
		transmitterThread.start();
		receiverThread = new ReceiverThread(transport.getInputStream(), queueIn, queueOut);
		receiverThread.start();
	}

	public void close() {
	}

	public void sendRaw(byte[] frame) {
		queueOut.insert(frame);
	}

	public byte[] receiveRaw() {
		return queueIn.remove();
	}

	public List<Integer> getSerialApiGetInitData() {
		queueOut.insert(SerialApiGetInitData.buildSerialApiGetInitDataReq());
		byte[] frame = queueIn.remove();
		List<Integer> registeredDevices = null;
		if (frame[Constants.PKT_IDX_PACKET_TYPE] == Constants.SOF && frame[Constants.PKT_IDX_MSG_TYPE] == MessageType.Response.ordinal()) {
			if (frame[Constants.PKT_IDX_COMMAND] == Constants.FUNCTION_REGISTERED_DEVICES) {
				registeredDevices = SerialApiGetInitData.parseSerialApiGetInitDataResp(frame);
			}
		}
		return registeredDevices;
	}

	public int getIdentifyNode(int nodeId) {
		queueOut.insert(IdentifyNode.buildIdentifyNodeReq(nodeId));
		byte[] frame = queueIn.remove();
		return IdentifyNode.parseIdentifyNodeResp(frame);
	}

	public int sendData(int nodeId, Parameter parameter) {
		queueOut.insert(SendData.buildSendDataReq(nodeId, parameter));
		byte[] frame = queueIn.remove();
		return SendData.parseSendDataResp(frame);
	}

	public int set(Parameter parameter) {
		queueOut.insert(SetControllerParam.set(parameter.getBytes()));
		byte[] frame = queueIn.remove();
		return SetControllerParam.parseSet(frame);
	}

	public LinkedHashMap<Integer, Parameter> get(byte[] parameterIds) {
		queueOut.insert(GetControllerParams.get(parameterIds));
		byte[] frame = queueIn.remove();
		LinkedHashMap<Integer, Parameter> parameters = GetControllerParams.parseGet(frame);
		return parameters;
	}

	protected Map<Integer, List<byte[]>> callbacks;

	public void loop() {
		byte[] frame;
		int nodeId;
		int len;
		byte[] data;
		boolean bLoop = true;
		while (bLoop) {
			frame = queueIn.remove();

			switch (frame[3]) {
			case 0x49:
				// Wake up event.
				nodeId = frame[5];
				data = new byte[] {(byte)0x13, (byte)nodeId, (byte)0x06, (byte)0x84, (byte)0x04, (byte)0x00, (byte)0x01, (byte)0x68, (byte)0x01, (byte)0x05, (byte)0x03};
				queueOut.insert(FrameUtils.assemble(MessageType.Request, data));
				break;
			case 0x04:
				// Sensor data.
				nodeId = (byte)(frame[5] & 255);
				len = (byte)(frame[6] & 255);
				data = new byte[len];
				System.arraycopy(frame, 7, data, 0, len);
				switch (data[0]) {
				case (byte)0x80:
					System.out.println(Constants.INDENT + "Node " + nodeId + " battery level: " + (data[2] & 255) + "%");
					break;
				case 0x31:
					if (data[1] == 0x05 && data[2] == 0x01) {
						Float temp = new Float(data[4] & 255);
						switch (data[3]) {
						case 1:
		    	               temp = ((temp - 170.0f) / 10.0f) + 41.1f;
							break;
						case 2:
		    	               temp = ((temp - 170.0f) / 10.0f) + 68.2f;
							break;
						case 3:
		    	               temp = ((temp - 25.0f) / 10.0f) + 79.3f;
							break;
						}
						Float temp2 = (temp -32) * 5.0f / 9.0f;

						System.out.println(Constants.INDENT + "Node " + nodeId + " temperature: " + temp + "F / " + temp2 + "C");
					}
					else {
						System.out.println(Constants.INDENT + "Node " + nodeId + " unsupported data: " + HexUtils.hexString(data));
					}
					break;
				default:
					System.out.println(Constants.INDENT + "Node " + nodeId + " unsupported data: " + HexUtils.hexString(data));
					break;
				}
				break;
			}
			// debug
			//System.out.println(pkt.length);
		}
	}

}
