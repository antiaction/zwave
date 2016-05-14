package com.antiaction.zwave;

import gnu.io.NRSerialPort;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import com.antiaction.zwave.constants.Constants;
import com.antiaction.zwave.constants.ControllerMessageType;
import com.antiaction.zwave.constants.GenericDeviceClass;
import com.antiaction.zwave.constants.MessageType;
import com.antiaction.zwave.messages.GetCapabilities;
import com.antiaction.zwave.transport.SerialTransport;

public class TestZWave {

	public static void main(String[] args) {
		for(String s:NRSerialPort.getAvailableSerialPorts()){
			System.out.println("Availible port: "+s);
		}

		String portName = "/dev/ttyACM0";

		SerialTransport transport = new SerialTransport();
		Controller controller = new Controller();
		Parameter parameter;

		byte[] data;
		byte[] frame;

		try {
			transport.open(portName);
			controller.open(transport);
		}
		catch (NoSuchPortException e) {
			e.printStackTrace();
			System.exit(1);
		}
		catch (PortInUseException e) {
			e.printStackTrace();
			System.exit(1);
		}
		catch (UnsupportedCommOperationException e) {
			e.printStackTrace();
			System.exit(1);
		}
		catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

		Optional<GenericDeviceClass> gdc;

		List<Integer> registeredDevices = controller.getSerialApiGetInitData();
		for (int i=0; i<registeredDevices.size(); ++i) {
			int nodeId = registeredDevices.get(i);
			int type = controller.getIdentifyNode(nodeId);
			gdc = GenericDeviceClass.getType(type);
			System.out.println(Constants.INDENT + "Node " + nodeId + " Type: " + gdc.get().getLabel());
		}

		parameter = new Parameter((byte)0x51, new byte[] {(byte)0x01});
		int success = controller.set(parameter);
		System.out.println(success);

		LinkedHashMap<Integer, Parameter> parameters = controller.get(new byte[] {(byte)0x51, (byte)0xDC, (byte)0xF2, (byte)0xFC});
		Iterator<Parameter> iter = parameters.values().iterator();
		while (iter.hasNext()) {
			parameter = iter.next();
			// debug
			System.out.println(Constants.INDENT + HexUtils.hexString(parameter.id)+ " " + parameter.size + " " + HexUtils.hexString(parameter.value) + " / " + HexUtils.byteString(parameter.value));
		}

		parameter = new Parameter((byte)0x20, new byte[] {(byte)0x00});
		success = controller.sendData(2, parameter);
		System.out.println(Constants.INDENT + success);

		parameter = new Parameter((byte)0x20, new byte[] {(byte)0xFF});
		success = controller.sendData(2, parameter);
		System.out.println(Constants.INDENT + success);

		parameter = new Parameter((byte)0x20, new byte[] {(byte)0x00});
		success = controller.sendData(20, parameter);
		System.out.println(Constants.INDENT + success);

		parameter = new Parameter((byte)0x20, new byte[] {(byte)0xFF});
		success = controller.sendData(20, parameter);
		System.out.println(Constants.INDENT + success);

		//01 0D 00 13 0C 06 84 04 00 01 68 01 05 03 05

		//parameter = new Parameter((byte)CommandClass.MANUFACTURER_SPECIFIC.getClassCode(), new byte[] {(byte)MANUFACTURER_SPECIFIC_GET});
		//success = controller.sendData(2, parameter);

		/*
		data = new byte[] {
				(byte)ControllerMessageType.GetCapabilities	.getId()
		};
		frame = FrameUtils.assemble(MessageType.Request, data);
		controller.sendRaw(frame);
		controller.receiveRaw();
		*/

		controller.sendRaw(GetCapabilities.buidGetCapabilitiesReq());
		GetCapabilities.parseGetCapabilitiesResp(controller.receiveRaw());

		/*
		data = new byte[] {
				(byte)ControllerMessageType.GetCapabilities	.getId(),
				(byte)0x02
		};
		frame = FrameUtils.assemble(MessageType.Request, data);
		controller.sendRaw(frame);
		controller.receiveRaw();
		*/

		controller.loop();

		controller.close();

		transport.close();
	}

    private static final int MANUFACTURER_SPECIFIC_GET = 0x04;

}
