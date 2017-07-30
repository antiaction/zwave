package com.antiaction.zwave;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Optional;

import com.antiaction.zwave.constants.CommandClass;
import com.antiaction.zwave.constants.Constants;
import com.antiaction.zwave.constants.GenericDeviceClass;
import com.antiaction.zwave.constants.SensorType;
import com.antiaction.zwave.messages.ApplicationCommandHandlerData;
import com.antiaction.zwave.messages.ApplicationCommandHandlerResp;
import com.antiaction.zwave.messages.ApplicationUpdateResp;
import com.antiaction.zwave.messages.GetCapabilitiesReq.GetCapabilitiesResp;
import com.antiaction.zwave.messages.GetControllerParamsReq.GetControllerParamsResp;
import com.antiaction.zwave.messages.IdentifyNodeReq.IdentifyNodeResp;
import com.antiaction.zwave.messages.MemoryGetIdReq.MemoryGetIdResp;
import com.antiaction.zwave.messages.SendDataReq.SendDataResp;
import com.antiaction.zwave.messages.SerialApiGetInitDataReq.SerialApiGetInitDataResp;
import com.antiaction.zwave.messages.SetControllerParamReq.SetControllerParamResp;
import com.antiaction.zwave.messages.command.AlarmCommand.AlarmReport;
import com.antiaction.zwave.messages.command.AlarmCommand.AlarmReportV2;
import com.antiaction.zwave.messages.command.AlarmCommand.AlarmTypeSupportedReport;
import com.antiaction.zwave.messages.command.BasicCommand.BasicReport;
import com.antiaction.zwave.messages.command.BasicCommand.BasicReportV2;
import com.antiaction.zwave.messages.command.BatteryCommand.BatteryReport;
import com.antiaction.zwave.messages.command.BinarySensorCommand.BinarySensorReport;
import com.antiaction.zwave.messages.command.BinarySensorCommand.BinarySensorReportV2;
import com.antiaction.zwave.messages.command.BinarySensorCommand.BinarySensorSupportedSensorReport;
import com.antiaction.zwave.messages.command.BinarySwitchCommand.BinarySwitchReport;
import com.antiaction.zwave.messages.command.BinarySwitchCommand.BinarySwitchReportV2;
import com.antiaction.zwave.messages.command.ClockCommand.ClockReport;
import com.antiaction.zwave.messages.command.ConfigurationCommand.ConfigurationReport;
import com.antiaction.zwave.messages.command.ManufacturerSpecificCommand;
import com.antiaction.zwave.messages.command.ManufacturerSpecificCommand.ManufacturerSpecificReport;
import com.antiaction.zwave.messages.command.MultiLevelSensorCommand;
import com.antiaction.zwave.messages.command.MultiLevelSensorCommand.MultiLevelSensorReport;
import com.antiaction.zwave.messages.command.MultiLevelSensorCommand.MultiLevelSensorSupportedSensorReport;
import com.antiaction.zwave.messages.command.MultiLevelSwitchCommand.MultiLevelSwitchReport;
import com.antiaction.zwave.messages.command.ProtectionCommand.ProtectionReport;
import com.antiaction.zwave.messages.command.ThermostatSetpointCommand.ThermostatSetpointCapabilitiesReportV3;
import com.antiaction.zwave.messages.command.ThermostatSetpointCommand.ThermostatSetpointReport;
import com.antiaction.zwave.messages.command.ThermostatSetpointCommand.ThermostatSetpointSupportedReport;
import com.antiaction.zwave.messages.command.UnknownCommand;
import com.antiaction.zwave.messages.command.VersionCommand;
import com.antiaction.zwave.messages.command.VersionCommand.VersionCommandClassReport;
import com.antiaction.zwave.messages.command.VersionCommand.VersionReportV1;
import com.antiaction.zwave.messages.command.VersionCommand.VersionReportV2;
import com.antiaction.zwave.messages.command.WakeUpCommand.WakeUpIntervalCapabilitiesReport;
import com.antiaction.zwave.messages.command.WakeUpCommand.WakeUpIntervalReport;
import com.antiaction.zwave.messages.command.WakeUpCommand.WakeUpNotification;
import com.antiaction.zwave.transport.SerialTransport;

import gnu.io.NRSerialPort;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;

public class TestZWave implements ApplicationListener {

	public static void main(String[] args) {
		TestZWave p = new TestZWave();
		p.Main(args);
	}

	protected Controller controller;

	public void Main(String[] args) {
		for(String s:NRSerialPort.getAvailableSerialPorts()){
			System.out.println("Availible port: "+s);
		}

		String portName = "/dev/ttyACM0";

		SerialTransport transport = new SerialTransport();
		controller = new Controller();
		Parameter parameter;

		IdentifyNodeResp identifyNodeResp;

		try {
			transport.open(portName);
			controller.addListener(this);
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

		MemoryGetIdResp memoryGetIdResp = controller.getMemoryGetIdReq().build().send();
		memoryGetIdResp.waitFor();
		// debug
		System.out.println("      homeId: " + HexUtils.byteIntToHexString(memoryGetIdResp.homeId));
		System.out.println("controllerId: " + memoryGetIdResp.controllerId);

		GetCapabilitiesResp getCapabilitiesResp = controller.getGetCapabilitiesReq().build().send();
		getCapabilitiesResp.waitFor();
		// debug
		System.out.println("      Version: " + getCapabilitiesResp.majorVersion + "." + getCapabilitiesResp.minorVersion);
		System.out.println("manufactureId: " + HexUtils.byteCharToHexString(getCapabilitiesResp.manufactureId));
		System.out.println("   deviceType: " + HexUtils.byteCharToHexString(getCapabilitiesResp.deviceType));
		System.out.println("     deviceId: " + HexUtils.byteCharToHexString(getCapabilitiesResp.deviceId));

		Iterator<Entry<Integer, Optional<CommandClass>>> commandLinkedMapIter = getCapabilitiesResp.commandLinkedMap.entrySet().iterator();
		Entry<Integer, Optional<CommandClass>> commandClassEntry;
		Optional<CommandClass> optionalCommandClass;
		while (commandLinkedMapIter.hasNext()) {
			commandClassEntry = commandLinkedMapIter.next();
			optionalCommandClass = commandClassEntry.getValue();
			if (optionalCommandClass.isPresent()) {
				System.out.println(HexUtils.byteToHexString(commandClassEntry.getKey()) + " (" + optionalCommandClass.get().getLabel() + ")");
			}
			else {
				System.out.println(HexUtils.byteToHexString(commandClassEntry.getKey()));
			}
		}

		SerialApiGetInitDataResp serialApiGetInitDataResp = controller.getSerialApiGetInitData().build().send();
		serialApiGetInitDataResp.waitFor();
		// debug
		System.out.println(serialApiGetInitDataResp.controllerMode.name());
		System.out.println(serialApiGetInitDataResp.controllerType.name());

		for (int i=0; i<serialApiGetInitDataResp.registeredDevices.size(); ++i) {
			int nodeId = serialApiGetInitDataResp.registeredDevices.get(i);
			identifyNodeResp = controller.getIdentifyNode().setNodeId(nodeId).build().send();
			identifyNodeResp.waitFor();
			// debug
			System.out.println(Constants.INDENT + "Node " + nodeId + "           listening: " + identifyNodeResp.listening);
			System.out.println(Constants.INDENT + "Node " + nodeId + "             routing: " + identifyNodeResp.routing);
			System.out.println(Constants.INDENT + "Node " + nodeId + "             version: " + identifyNodeResp.version);
			System.out.println(Constants.INDENT + "Node " + nodeId + "         maxBaudRate: " + identifyNodeResp.maxBaudRate);
			System.out.println(Constants.INDENT + "Node " + nodeId + " frequentlyListening: " + identifyNodeResp.frequentlyListening);
			System.out.println(Constants.INDENT + "Node " + nodeId + "             beaming: " + identifyNodeResp.beaming);
			System.out.println(Constants.INDENT + "Node " + nodeId + "            security: " + identifyNodeResp.security);
			if (identifyNodeResp.basicDeviceClass != null) {
				System.out.println(Constants.INDENT + "Node " + nodeId + "      basicDeviceClass: " + identifyNodeResp.basicDeviceClass.getLabel() + "(" + HexUtils.byteToHexString(identifyNodeResp.basicDeviceClassId) + ")");
			}
			else {
				System.out.println(Constants.INDENT + "Node " + nodeId + "      basicDeviceClass: Unknown" + "(" + HexUtils.byteToHexString(identifyNodeResp.basicDeviceClassId) + ")");
			}
			if (identifyNodeResp.genericDeviceClass != null) {
				System.out.println(Constants.INDENT + "Node " + nodeId + "    genericDeviceClass: " + identifyNodeResp.genericDeviceClass.getLabel() + "(" + HexUtils.byteToHexString(identifyNodeResp.genericDeviceClassId) + ")");
			}
			else {
				System.out.println(Constants.INDENT + "Node " + nodeId + "    genericDeviceClass: Unknown" + "(" + HexUtils.byteToHexString(identifyNodeResp.genericDeviceClassId) + ")");
			}
			if (identifyNodeResp.optionalSpecificClass != null) {
				System.out.println(Constants.INDENT + "Node " + nodeId + " optionalSpecificClass: " + identifyNodeResp.optionalSpecificClass.getLabel() + "(" + HexUtils.byteToHexString(identifyNodeResp.optionalSpecificClassId) + ")");
			}
			else {
				System.out.println(Constants.INDENT + "Node " + nodeId + " optionalSpecificClass: Unknown" + "(" + HexUtils.byteToHexString(identifyNodeResp.optionalSpecificClassId) + ")");
			}
		}

		parameter = new Parameter((byte)0x51, new byte[] {(byte)0x01});
		SetControllerParamResp setControllerParamResp = controller.getSetControllerParam().setParameter(parameter).build().send();
		setControllerParamResp.waitFor();
		// debug
		System.out.println(setControllerParamResp.success);

		byte[] parameterIds;
		GetControllerParamsResp getControllerParamsResp;
		Iterator<Parameter> iter;

		parameterIds = new byte[] {(byte)0x51, (byte)0xDC, (byte)0xF2, (byte)0xFC};
		getControllerParamsResp = controller.getGetControllerParams().setParameterIds(parameterIds).build().send();
		getControllerParamsResp.waitFor();
		iter = getControllerParamsResp.parameters.values().iterator();
		while (iter.hasNext()) {
			parameter = iter.next();
			// debug
			System.out.println(Constants.INDENT + HexUtils.byteToHexString(parameter.id)+ " " + parameter.size + " " + HexUtils.byteArrayToHexString(parameter.value) + " / " + HexUtils.byteString(parameter.value));
		}

		sleep(10000);

		/*
		parameterIds = new byte[] {(byte)0x51, (byte)0xDC, (byte)0xF2, (byte)0xFC};
		getControllerParamsResp = controller.getGetControllerParams().setParameterIds(parameterIds).build().send();
		getControllerParamsResp.waitFor();
		iter = getControllerParamsResp.parameters.values().iterator();
		while (iter.hasNext()) {
			parameter = iter.next();
			// debug
			System.out.println(Constants.INDENT + HexUtils.byteToHexString(parameter.id)+ " " + parameter.size + " " + HexUtils.byteArrayToHexString(parameter.value) + " / " + HexUtils.byteString(parameter.value));
		}
		*/

		SendDataResp sendDataResp;

		sleep(10000);

		/*
		parameter = new Parameter((byte)0x20, new byte[] {(byte)0xFF});
		sendDataResp = controller.getSendDataReq().setNodeId(3).setParameter(parameter).build().send();
		sendDataResp.waitFor();
		// debug
		System.out.println(Constants.INDENT + sendDataResp.success);
		sleep(10000);

		parameter = new Parameter((byte)0x20, new byte[] {(byte)0x00});
		sendDataResp = controller.getSendDataReq().setNodeId(3).setParameter(parameter).build().send();
		sendDataResp.waitFor();
		// debug
		System.out.println(Constants.INDENT + sendDataResp.success);
		sleep(10000);
		*/

		/*
		sendDataResp = controller.getSendDataReq().setNodeId(3).setPayload(BinarySwitchCommand.assembleSwitchBinarySetReq(0xff)).build().send();
		sendDataResp.waitFor();
		// debug
		System.out.println(Constants.INDENT + sendDataResp.success);
		sleep(10000);

		sendDataResp = controller.getSendDataReq().setNodeId(3).setPayload(BinarySwitchCommand.assembleSwitchBinarySetReq(0x00)).build().send();
		sendDataResp.waitFor();
		// debug
		System.out.println(Constants.INDENT + sendDataResp.success);
		sleep(10000);
		*/

		/*
		parameter = new Parameter((byte)0x20, new byte[] {(byte)0x00});
		sendDataResp = controller.getSendDataReq().setNodeId(20).setParameter(parameter).build().send();
		sendDataResp.waitFor();
		// debug
		System.out.println(Constants.INDENT + sendDataResp.success);

		sleep(10000);

		parameter = new Parameter((byte)0x20, new byte[] {(byte)0xFF});
		sendDataResp = controller.getSendDataReq().setNodeId(20).setParameter(parameter).build().send();
		sendDataResp.waitFor();
		// debug
		System.out.println(Constants.INDENT + sendDataResp.success);
		sleep(10000);
		*/

		sendDataResp = controller.getSendDataReq().setNodeId(2).setPayload(ManufacturerSpecificCommand.getManufacturerSpecificGetReq()).build().send();
		sendDataResp.waitFor();
		// debug
		System.out.println(Constants.INDENT + sendDataResp.success);
		sleep(10000);

		sendDataResp = controller.getSendDataReq().setNodeId(3).setPayload(ManufacturerSpecificCommand.getManufacturerSpecificGetReq()).build().send();
		sendDataResp.waitFor();
		// debug
		System.out.println(Constants.INDENT + sendDataResp.success);
		sleep(10000);

		sendDataResp = controller.getSendDataReq().setNodeId(4).setPayload(ManufacturerSpecificCommand.getManufacturerSpecificGetReq()).build().send();
		sendDataResp.waitFor();
		// debug
		System.out.println(Constants.INDENT + sendDataResp.success);
		sleep(10000);

		/*
		sendDataResp = controller.getSendDataReq().setNodeId(2).setPayload(BatteryCommand.assembleBatteryGetReq()).build().send();
		sendDataResp.waitFor();
		// debug
		System.out.println(Constants.INDENT + sendDataResp.success);
		sleep(10000);
		*/

		/*
		sendDataResp = controller.getSendDataReq().setNodeId(3).setPayload(BatteryCommand.assembleBatteryReq()).build().send();
		sendDataResp.waitFor();
		// debug
		System.out.println(Constants.INDENT + sendDataResp.success);
		sleep(10000);
		*/

		/*
		sendDataResp = controller.getSendDataReq().setNodeId(2).setPayload(BasicCommand.assembleBasicGetReq()).build().send();
		sendDataResp.waitFor();
		// debug
		System.out.println(Constants.INDENT + sendDataResp.success);
		sleep(10000);
		*/

		/*
		sendDataResp = controller.getSendDataReq().setNodeId(3).setPayload(BasicCommand.assembleBasicReq()).build().send();
		sendDataResp.waitFor();
		// debug
		System.out.println(Constants.INDENT + sendDataResp.success);
		sleep(10000);
		*/

		/*
		sendDataResp = controller.getSendDataReq().setNodeId(3).setPayload(WakeUpCommand.assembleWakeUpIntervalCapabilitiesGetReq()).build().send();
		sendDataResp.waitFor();
		// debug
		System.out.println(Constants.INDENT + sendDataResp.success);

		sleep(10000);
		*/

		/*
		sendDataResp = controller.getSendDataReq().setNodeId(2).setPayload(WakeUpCommand.assembleWakeUpIntervalCapabilitiesGetReq()).build().send();
		sendDataResp.waitFor();
		// debug
		System.out.println(Constants.INDENT + sendDataResp.success);
		sleep(10000);

		sendDataResp = controller.getSendDataReq().setNodeId(2).setPayload(WakeUpCommand.assembleWakeUpIntervalSetReq(300, 1)).build().send();
		sendDataResp.waitFor();
		// debug
		System.out.println(Constants.INDENT + sendDataResp.success);
		sleep(10000);

		sendDataResp = controller.getSendDataReq().setNodeId(2).setPayload(WakeUpCommand.assembleWakeUpIntervalGetReq()).build().send();
		sendDataResp.waitFor();
		// debug
		System.out.println(Constants.INDENT + sendDataResp.success);
		sleep(10000);
		*/

		/*
		sendDataResp = controller.getSendDataReq().setNodeId(3).setPayload(WakeUpCommand.assembleWakeUpIntervalGetReq()).build().send();
		sendDataResp.waitFor();
		// debug
		System.out.println(Constants.INDENT + sendDataResp.success);
		*/
		sleep(10000);

		/*
		sendDataResp = controller.getSendDataReq().setNodeId(4).setPayload(SensorMultiLevelCommand.assembleSensorMultiLevelSupportedGetReq()).build().send();
		sendDataResp.waitFor();
		// debug
		System.out.println(Constants.INDENT + sendDataResp.success);
		sleep(10000);
		*/

		sendDataResp = controller.getSendDataReq().setNodeId(2).setPayload(MultiLevelSensorCommand.assembleSensorMultiLevelSupportedGetReqSensorV5()).build().send();
		sendDataResp.waitFor();
		// debug
		System.out.println(Constants.INDENT + sendDataResp.success);
		sleep(10000);

		sendDataResp = controller.getSendDataReq().setNodeId(2).setPayload(MultiLevelSensorCommand.assembleSensorMultiLevelGetReqV5(0x01, 0)).build().send();
		sendDataResp.waitFor();
		// debug
		System.out.println(Constants.INDENT + sendDataResp.success);
		sleep(10000);

		sendDataResp = controller.getSendDataReq().setNodeId(2).setPayload(MultiLevelSensorCommand.assembleSensorMultiLevelGetReqV5(0x03, 0)).build().send();
		sendDataResp.waitFor();
		// debug
		System.out.println(Constants.INDENT + sendDataResp.success);
		sleep(10000);

		sendDataResp = controller.getSendDataReq().setNodeId(2).setPayload(MultiLevelSensorCommand.assembleSensorMultiLevelGetReqV5(0x05, 0)).build().send();
		sendDataResp.waitFor();
		// debug
		System.out.println(Constants.INDENT + sendDataResp.success);
		sleep(10000);

		sendDataResp = controller.getSendDataReq().setNodeId(2).setPayload(MultiLevelSensorCommand.assembleSensorMultiLevelGetReqV5(0x1B, 0)).build().send();
		sendDataResp.waitFor();
		// debug
		System.out.println(Constants.INDENT + sendDataResp.success);
		sleep(10000);

		/*
		sendDataResp = controller.getSendDataReq().setNodeId(3).setPayload(ThermostatSetpointCommand.assembleThermostatSetpointSupportedGetReq()).build().send();
		sendDataResp.waitFor();
		// debug
		System.out.println(Constants.INDENT + sendDataResp.success);
		*/

		/*
		parameter = new Parameter((byte)0x20, new byte[] {(byte)0x01});
		sendDataResp = controller.getSendDataReq().setNodeId(4).setParameter(parameter).build().send();
		sendDataResp.waitFor();
		// debug
		System.out.println(Constants.INDENT + sendDataResp.success);
		*/

		/*
		sendDataResp = controller.getSendDataReq().setNodeId(4).setPayload(SwitchBinaryCommand.assembleSwitchBinarySetReq(1)).build().send();
		//sendDataResp = controller.getSendDataReq().setNodeId(4).setPayload(SwitchBinaryCommand.assembleSwitchBinaryGetReq()).build().send();
		sendDataResp.waitFor();
		// debug
		System.out.println(Constants.INDENT + sendDataResp.success);
		sleep(10000);
		*/

		// debug
		System.out.println("Here comes thunder...");

		sleep(10000);

		sendDataResp = controller.getSendDataReq().setNodeId(2).setPayload(VersionCommand.assembleVersionCommandClassGetReq((byte)0x29)).build().send();
		sendDataResp.waitFor();
		// debug
		System.out.println(Constants.INDENT + sendDataResp.success);
		sleep(10000);

		sendDataResp = controller.getSendDataReq().setNodeId(2).setPayload(VersionCommand.assembleVersionCommandClassGetReq((byte)0x39)).build().send();
		sendDataResp.waitFor();
		// debug
		System.out.println(Constants.INDENT + sendDataResp.success);
		sleep(10000);

		/*
		sendDataResp = controller.getSendDataReq().setNodeId(2).setPayload(AlarmCommand.assembleBatteryReq()).build().send();
		sendDataResp.waitFor();
		// debug
		System.out.println(Constants.INDENT + sendDataResp.success);
		sleep(10000);
		*/

		/*
		sendDataResp = controller.getSendDataReq().setNodeId(2).setPayload(VersionCommand.assembleVersionGetReq()).build().send();
		sendDataResp.waitFor();
		// debug
		System.out.println(Constants.INDENT + sendDataResp.success);
		sleep(10000);

		sendDataResp = controller.getSendDataReq().setNodeId(3).setPayload(VersionCommand.assembleVersionGetReq()).build().send();
		sendDataResp.waitFor();
		// debug
		System.out.println(Constants.INDENT + sendDataResp.success);
		sleep(10000);

		sendDataResp = controller.getSendDataReq().setNodeId(4).setPayload(VersionCommand.assembleVersionGetReq()).build().send();
		sendDataResp.waitFor();
		// debug
		System.out.println(Constants.INDENT + sendDataResp.success);
		sleep(10000);
		*/

		/*
		AddNodeToNetworkResp addNodeToNetworkResp = controller.getAddNodeToNetworkReq().build().send();
		addNodeToNetworkResp.waitFor();
		// debug
		System.out.println(Constants.INDENT + sendDataResp.success);
		sleep(10000);
		*/

		/*
		System.out.println("Reset.");

		parameter = new Parameter((byte)0xff, new byte[] {(byte)0x01});
		setControllerParamResp = controller.getSetControllerParam().setParameter(parameter).build().send();
		setControllerParamResp.waitFor();
		// debug
		System.out.println(setControllerParamResp.success);
		sleep(10000);
		*/

		try {
			Thread.sleep(24 * 60 * 60 * 1000);
		}
		catch (InterruptedException e) {
		}

		controller.removeListener(this);

		controller.close();

		transport.close();
	}

	public static void sleep(long millis) {
		try {
			Thread.sleep(1000);
		}
		catch (InterruptedException e) {
		}
	}

	/*
	 * [2017-02-22 22:39:58.012 +0100]  > 0x01 0x16 0x00 0x49 0x84 0x07 0x10 0x04 0x08 0x04 0x80 0x46 0x81 0x72 0x8F 0x75 0x43 0x86 0x84 0xEF 0x46 0x81 0x8F 0x12
	 * [2017-02-22 22:39:58.013 +0100] <  0x06
	 * [2017-02-22 22:46:16.187 +0100]  > 0x01 0x18 0x00 0x49 0x84 0x02 0x12 0x04 0x21 0x01 0x5E 0x86 0x72 0x59 0x85 0x73 0x71 0x84 0x80 0x30 0x31 0x70 0x7A 0x5A 0xEF 0xD0
	 * [2017-02-22 22:46:16.188 +0100] <  0x06
	 * [2017-02-22 22:47:07.752 +0100]  > 0x01 0x16 0x00 0x49 0x84 0x04 0x10 0x04 0x08 0x04 0x80 0x46 0x81 0x72 0x8F 0x75 0x43 0x86 0x84 0xEF 0x46 0x81 0x8F 0x11
	 * [2017-02-22 22:47:07.752 +0100] <  0x06
	 */
	public void onApplicationUpdate(ApplicationUpdateResp applicationUpdateResp) {
		// debug
		System.out.println(Constants.INDENT + "Node " + applicationUpdateResp.nodeId + " ApplicationUpdate");
		if (applicationUpdateResp.basicDeviceClass != null) {
			System.out.println(Constants.INDENT + "Node " + applicationUpdateResp.nodeId + "      basicDeviceClass: " + applicationUpdateResp.basicDeviceClass.getLabel() + "(" + HexUtils.byteToHexString(applicationUpdateResp.basicDeviceClassId) + ")");
		}
		else {
			System.out.println(Constants.INDENT + "Node " + applicationUpdateResp.nodeId + "      basicDeviceClass: Unknown" + "(" + HexUtils.byteToHexString(applicationUpdateResp.basicDeviceClassId) + ")");
		}
		if (applicationUpdateResp.genericDeviceClass != null) {
			System.out.println(Constants.INDENT + "Node " + applicationUpdateResp.nodeId + "    genericDeviceClass: " + applicationUpdateResp.genericDeviceClass.getLabel() + "(" + HexUtils.byteToHexString(applicationUpdateResp.genericDeviceClassId) + ")");
		}
		else {
			System.out.println(Constants.INDENT + "Node " + applicationUpdateResp.nodeId + "    genericDeviceClass: Unknown" + "(" + HexUtils.byteToHexString(applicationUpdateResp.genericDeviceClassId) + ")");
		}
		if (applicationUpdateResp.optionalSpecificClass != null) {
			System.out.println(Constants.INDENT + "Node " + applicationUpdateResp.nodeId + " optionalSpecificClass: " + applicationUpdateResp.optionalSpecificClass.getLabel() + "(" + HexUtils.byteToHexString(applicationUpdateResp.optionalSpecificClassId) + ")");
		}
		else {
			System.out.println(Constants.INDENT + "Node " + applicationUpdateResp.nodeId + " optionalSpecificClass: Unknown" + "(" + HexUtils.byteToHexString(applicationUpdateResp.optionalSpecificClassId) + ")");
		}
		Iterator<CommandClass> iter = applicationUpdateResp.supportedCommandClassList.iterator();
		CommandClass commandClass;
		while (iter.hasNext()) {
			commandClass = iter.next();
			System.out.println(Constants.INDENT + "Node " + applicationUpdateResp.nodeId + "          commandClass: " + commandClass.getLabel() + " (" + HexUtils.byteToHexString( + commandClass.ordinal()) + ")");
		}
		//byte[] data = new byte[] {(byte)0x13, (byte)applicationUpdateResp.nodeId, (byte)0x06, (byte)0x84, (byte)0x04, (byte)0x00, (byte)0x01, (byte)0x68, (byte)0x01, (byte)0x05, (byte)0x03};
		//controller.queueOut.insert(FrameUtils.assemble(MessageType.Request, data));
	}

	@SuppressWarnings("deprecation")
	public void onApplicationCommandHandler(ApplicationCommandHandlerResp applicationCommandHandlerResp) {
		ApplicationCommandHandlerData data = applicationCommandHandlerResp.data;
		if (data != null) {
			if (data instanceof AlarmReport) {
				AlarmReport alarmResp = (AlarmReport)data;
				System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " alarm report type: " + alarmResp.alarmType);
				System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " alarm report level: " + alarmResp.alarmLevel);
			}
			else if (data instanceof AlarmReportV2) {
				AlarmReportV2 alarmResp = (AlarmReportV2)data;
				System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " alarm report type: " + alarmResp.alarmType);
				System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " alarm report level: " + alarmResp.alarmLevel);
				System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " alarm report zenzor net source nodeid: " + alarmResp.zenzorNetSourceNodeId);
				System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " alarm report status: " + alarmResp.zwaveAlarmStatus);
				//System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " alarm report type: " + alarmResp.zwaveAlarmType);
				//System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " alarm report event: " + alarmResp.zwaveAlarmEvent);
				System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " alarm report type: " + alarmResp.zwaveAlarmType.getLabel());
				System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " alarm report event: " + alarmResp.zwaveAlarmEvent.getDescription());
			}
			else if (data instanceof AlarmTypeSupportedReport) {
				AlarmTypeSupportedReport alarmTypeSupportedResp = (AlarmTypeSupportedReport)data;
				System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " alarm type supported v1: " + alarmTypeSupportedResp.v1Alarm);
				// TODO
				//public byte[] bitmask;
			}
			else if (data instanceof BasicReport) {
				BasicReport basicResp = (BasicReport)data;
				System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " basic value: " + basicResp.value);
			}
			else if (data instanceof BasicReportV2) {
				BasicReportV2 basicResp = (BasicReportV2)data;
				System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " basic current value: " + basicResp.currentValue);
				System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " basic target value: " + basicResp.targetValue);
				System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " basic duration: " + basicResp.duration);
			}
			else if (data instanceof BatteryReport) {
				BatteryReport batteryResp = (BatteryReport)data;
				System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " battery level: " + batteryResp.level + "%");
			}
			else if (data instanceof BinarySensorReport) {
				BinarySensorReport binarySensorResp = (BinarySensorReport)data;
				System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " binary sensor value: " + binarySensorResp.sensorValue);
			}
			else if (data instanceof BinarySensorReportV2) {
				BinarySensorReportV2 binarySensorResp = (BinarySensorReportV2)data;
				System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " binary sensor value: " + binarySensorResp.sensorValue);
				System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " binary sensor type: " + binarySensorResp.sensorType);
			}
			else if (data instanceof BinarySensorSupportedSensorReport) {
				BinarySensorSupportedSensorReport binarySensorSupportedSensorResp = (BinarySensorSupportedSensorReport)data;
				// TODO
			}
			else if (data instanceof BinarySwitchReport) {
				BinarySwitchReport switchBinaryReport = (BinarySwitchReport)data;
				System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " binary switch value: " + switchBinaryReport.value);
			}
			else if (data instanceof BinarySwitchReportV2) {
				BinarySwitchReportV2 switchBinaryReport = (BinarySwitchReportV2)data;
				System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " binary switch curremt value: " + switchBinaryReport.currentValue);
				System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " binary switch target value: " + switchBinaryReport.targetValue);
				System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " binary switch duration: " + switchBinaryReport.duration);
			}
			else if (data instanceof ClockReport) {
				ClockReport clockReport = (ClockReport)data;
				System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " clock weekday: " + clockReport.weekday);
				System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " clock hour: " + clockReport.hour);
				System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " clock minute: " + clockReport.minute);
			}
			else if (data instanceof ConfigurationReport) {
				ConfigurationReport configurationReport = (ConfigurationReport)data;
				System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " configuration number: " + configurationReport.parameterNumber);
				System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " configuration value: " + configurationReport.value.value);
			}
			else if (data instanceof ManufacturerSpecificReport) {
				ManufacturerSpecificReport manufacturerSpecificResp = (ManufacturerSpecificReport)data;
		        System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " manufactureId: " + HexUtils.byteCharToHexString(manufacturerSpecificResp.manufactureId));
		        System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + "    deviceType: " + HexUtils.byteCharToHexString(manufacturerSpecificResp.deviceType));
		        System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + "      deviceId: " + HexUtils.byteCharToHexString(manufacturerSpecificResp.deviceId));
			}
			else if (data instanceof MultiLevelSensorReport) {
				MultiLevelSensorReport sensorMultiLevelReport = (MultiLevelSensorReport)data;
				if (sensorMultiLevelReport.sensorScale != null) {
			        System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " SensorType: " + sensorMultiLevelReport.sensorType.getLabel() + "(" + HexUtils.byteToHexString(sensorMultiLevelReport.sensorType.getId()) + ") = " + sensorMultiLevelReport.value.toPlainString() + " " + sensorMultiLevelReport.sensorScale.getUnit());
				}
				else {
			        System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " SensorType: " + sensorMultiLevelReport.sensorType.getLabel() + "(" + HexUtils.byteToHexString(sensorMultiLevelReport.sensorType.getId()) + ") = " + sensorMultiLevelReport.value.toPlainString());
				}
			}
			else if (data instanceof MultiLevelSensorSupportedSensorReport) {
				MultiLevelSensorSupportedSensorReport sensorMultiLevelSupportedReport = (MultiLevelSensorSupportedSensorReport)data;
				Iterator<SensorType> sensorTypesIter = sensorMultiLevelSupportedReport.supportedSensorTypeList.iterator();
				SensorType sensorType;
				while (sensorTypesIter.hasNext()) {
					sensorType = sensorTypesIter.next();
					System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " SensorType: " + sensorType.getLabel() + "(" + HexUtils.byteToHexString(sensorType.getId()) + ")");
				}
			}
			else if (data instanceof MultiLevelSwitchReport) {
				MultiLevelSwitchReport switchMultiLevelReport = (MultiLevelSwitchReport)data;
				System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " multilevel switch value: " + switchMultiLevelReport.value);
			}
			else if (data instanceof ProtectionReport) {
				ProtectionReport protectionlReport = (ProtectionReport)data;
				System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " protection state: " + protectionlReport.protectionState);
			}
			else if (data instanceof ThermostatSetpointReport) {
				ThermostatSetpointReport thermostatSetpointReport = (ThermostatSetpointReport)data;
				System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " thermostat setpoint type: " + thermostatSetpointReport.setpointType);
				System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " thermostat setpoint value: " + thermostatSetpointReport.value.value);
			}
			else if (data instanceof ThermostatSetpointSupportedReport) {
				ThermostatSetpointSupportedReport thermostatSetpointSupportedReport = (ThermostatSetpointSupportedReport)data;
				// TODO
			}
			else if (data instanceof ThermostatSetpointCapabilitiesReportV3) {
				ThermostatSetpointCapabilitiesReportV3 thermostatSetpointReport = (ThermostatSetpointCapabilitiesReportV3)data;
				System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " thermostat setpoint type: " + thermostatSetpointReport.setpointType);
				System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " thermostat setpoint min value: " + thermostatSetpointReport.minValue.value);
				System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " thermostat setpoint max value: " + thermostatSetpointReport.maxValue.value);
			}
			else if (data instanceof VersionReportV1) {
				VersionReportV1 versionReport = (VersionReportV1)data;
				System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " version library type: " + versionReport.libraryType);
				System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " version protocol version: " + versionReport.protocolVersion.string);
				System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " version application version: " + versionReport.applicationVersion.string);
			}
			else if (data instanceof VersionReportV2) {
				VersionReportV2 versionReport = (VersionReportV2)data;
				System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " version library type: " + versionReport.libraryType);
				System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " version protocol version: " + versionReport.protocolVersion.string);
				int idx = 0;
				System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " version firmware 0 version: " + versionReport.firmwareVersions.get(idx++).string);
				System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " version hardware version: " + versionReport.hardwareVersion);
				while (idx < versionReport.firmwareVersions.size()) {
					System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " version firmware " + idx + " version: " + versionReport.firmwareVersions.get(idx++).string);
				}
			}
			else if (data instanceof VersionCommandClassReport) {
				VersionCommandClassReport versionCommandClassReport = (VersionCommandClassReport)data;
				System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " version command class: " + versionCommandClassReport.requestedCommandClass);
				System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " version class version: " + versionCommandClassReport.commandClassVersion);
			}
			else if (data instanceof WakeUpNotification) {
				System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " wake up notification");

				System.out.println("WakeUpQuery...");
				SendDataResp sendDataResp;
				/*
				sendDataResp = controller.getSendDataReq().setNodeId(applicationCommandHandlerResp.nodeId).setPayload(ThermostatSetpointCommand.assembleThermostatSetpointSupportedGetReq()).build().send();
				sendDataResp.waitFor();
				// debug
				System.out.println(Constants.INDENT + sendDataResp.success);

				sleep(1000);
				*/
				//sendDataResp = controller.getSendDataReq().setNodeId(applicationCommandHandlerResp.nodeId).setPayload(ThermostatSetpointCommand.assembleThermostatSetpointGetReq()).build().send();
				//sendDataResp.waitFor();
				// debug
				//System.out.println(Constants.INDENT + sendDataResp.success);
			}
			else if (data instanceof WakeUpIntervalReport) {
				WakeUpIntervalReport wakeUpIntervalReport = (WakeUpIntervalReport)data;
				System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " wake up seconds: " + wakeUpIntervalReport.seconds + ", " + wakeUpIntervalReport.nodeId);
			}
			else if (data instanceof WakeUpIntervalCapabilitiesReport) {
				WakeUpIntervalCapabilitiesReport wakeUpIntervalCapabilitiesReport = (WakeUpIntervalCapabilitiesReport)data;
				System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " wake up minimum interval seconds: " + wakeUpIntervalCapabilitiesReport.minimumIntervalSeconds);
				System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " wake up maximum interval seconds: " + wakeUpIntervalCapabilitiesReport.maximumIntervalSeconds);
				System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " wake up default interval seconds: " + wakeUpIntervalCapabilitiesReport.defaultIntervalSeconds);
				System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " wake up interval step seconds: " + wakeUpIntervalCapabilitiesReport.intervalStepSeconds);
			}
			else if (data instanceof UnknownCommand) {
				UnknownCommand unknownApplicationCommandHandlerData = (UnknownCommand)data;
				System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " unsupported data: " + HexUtils.byteArrayToHexString(unknownApplicationCommandHandlerData.data));
			}
		}
	}

}
