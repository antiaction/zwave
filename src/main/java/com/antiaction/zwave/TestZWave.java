package com.antiaction.zwave;

import gnu.io.NRSerialPort;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;

import com.antiaction.zwave.Controller.UnknownApplicationCommandHandlerData;
import com.antiaction.zwave.constants.Constants;
import com.antiaction.zwave.constants.GenericDeviceClass;
import com.antiaction.zwave.constants.SensorType;
import com.antiaction.zwave.messages.ApplicationCommandHandlerResp;
import com.antiaction.zwave.messages.ApplicationUpdateResp;
import com.antiaction.zwave.messages.GetCapabilitiesReq.GetCapabilitiesResp;
import com.antiaction.zwave.messages.GetControllerIdReq.GetControllerIdResp;
import com.antiaction.zwave.messages.GetControllerParamsReq.GetControllerParamsResp;
import com.antiaction.zwave.messages.IdentifyNodeReq.IdentifyNodeResp;
import com.antiaction.zwave.messages.SendDataReq.SendDataResp;
import com.antiaction.zwave.messages.SerialApiGetInitDataReq.SerialApiGetInitDataResp;
import com.antiaction.zwave.messages.SetControllerParamReq.SetControllerParamResp;
import com.antiaction.zwave.messages.command.ApplicationCommandHandlerData;
import com.antiaction.zwave.messages.command.BasicCommand;
import com.antiaction.zwave.messages.command.BasicCommand.Basic;
import com.antiaction.zwave.messages.command.BatteryCommand;
import com.antiaction.zwave.messages.command.BatteryCommand.Battery;
import com.antiaction.zwave.messages.command.ManufacturerSpecificCommand;
import com.antiaction.zwave.messages.command.ManufacturerSpecificCommand.ManufacturerSpecific;
import com.antiaction.zwave.messages.command.SensorMultiLevelCommand;
import com.antiaction.zwave.messages.command.SensorMultiLevelCommand.SensorMultiLevelReport;
import com.antiaction.zwave.messages.command.SensorMultiLevelCommand.SensorMultiLevelSupportedReport;
import com.antiaction.zwave.messages.command.ThermostatSetpointCommand;
import com.antiaction.zwave.messages.command.WakeUpCommand;
import com.antiaction.zwave.messages.command.WakeUpCommand.WakeUpIntervalCapabilitiesReport;
import com.antiaction.zwave.messages.command.WakeUpCommand.WakeUpIntervalReport;
import com.antiaction.zwave.messages.command.WakeUpCommand.WakeUpNotification;
import com.antiaction.zwave.transport.SerialTransport;

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

		controller.addListener(this);
		controller.start();

		Optional<GenericDeviceClass> gdc;

		GetControllerIdResp getGetControllerIdResp = controller.getGetControllerIdReq().build().send();
		getGetControllerIdResp.waitFor();
		// debug
		System.out.println("      homeId: " + HexUtils.byteIntToHexString(getGetControllerIdResp.homeId));
		System.out.println("controllerId: " + getGetControllerIdResp.controllerId);

		GetCapabilitiesResp getCapabilitiesResp = controller.getGetCapabilitiesReq().build().send();
		getCapabilitiesResp.waitFor();
		// debug
		System.out.println("      Version: " + getCapabilitiesResp.majorVersion + "." + getCapabilitiesResp.minorVersion);
		System.out.println("manufactureId: " + HexUtils.byteCharToHexString(getCapabilitiesResp.manufactureId));
		System.out.println("   deviceType: " + HexUtils.byteCharToHexString(getCapabilitiesResp.deviceType));
		System.out.println("     deviceId: " + HexUtils.byteCharToHexString(getCapabilitiesResp.deviceId));

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
			System.out.println(Constants.INDENT + "Node " + nodeId + "      basicDeviceClass: " + identifyNodeResp.basicDeviceClass.getLabel());
			System.out.println(Constants.INDENT + "Node " + nodeId + "    genericDeviceClass: " + identifyNodeResp.genericDeviceClass.getLabel());
			if (identifyNodeResp.optionalSpecificClass.isPresent()) {
				System.out.println(Constants.INDENT + "Node " + nodeId + " optionalSpecificClass: " + identifyNodeResp.optionalSpecificClass.get().getLabel());
			}
			else {
				System.out.println(Constants.INDENT + "Node " + nodeId + " optionalSpecificClass: Unknown");
			}
			System.out.println(Constants.INDENT + "Node " + nodeId + "           listening: " + identifyNodeResp.listening);
			System.out.println(Constants.INDENT + "Node " + nodeId + "             routing: " + identifyNodeResp.routing);
			System.out.println(Constants.INDENT + "Node " + nodeId + "             version: " + identifyNodeResp.version);
			System.out.println(Constants.INDENT + "Node " + nodeId + " frequentlyListening: " + identifyNodeResp.frequentlyListening);
		}

		parameter = new Parameter((byte)0x51, new byte[] {(byte)0x01});
		SetControllerParamResp setControllerParamResp = controller.getSetControllerParam().setParameter(parameter).build().send();
		setControllerParamResp.waitFor();
		// debug
		System.out.println(setControllerParamResp.success);

		byte[] parameterIds = new byte[] {(byte)0x51, (byte)0xDC, (byte)0xF2, (byte)0xFC};
		GetControllerParamsResp getControllerParamsResp = controller.getGetControllerParams().setParameterIds(parameterIds).build().send();
		getControllerParamsResp.waitFor();
		Iterator<Parameter> iter = getControllerParamsResp.parameters.values().iterator();
		while (iter.hasNext()) {
			parameter = iter.next();
			// debug
			System.out.println(Constants.INDENT + HexUtils.byteToHexString(parameter.id)+ " " + parameter.size + " " + HexUtils.byteArrayToHexString(parameter.value) + " / " + HexUtils.byteString(parameter.value));
		}

		SendDataResp sendDataResp;

		sleep(10000);

		parameter = new Parameter((byte)0x20, new byte[] {(byte)0x00});
		sendDataResp = controller.getSendDataReq().setNodeId(2).setParameter(parameter).build().send();
		sendDataResp.waitFor();
		// debug
		System.out.println(Constants.INDENT + sendDataResp.success);

		sleep(10000);

		parameter = new Parameter((byte)0x20, new byte[] {(byte)0xFF});
		sendDataResp = controller.getSendDataReq().setNodeId(2).setParameter(parameter).build().send();
		sendDataResp.waitFor();
		// debug
		System.out.println(Constants.INDENT + sendDataResp.success);

		sleep(10000);

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

		sendDataResp = controller.getSendDataReq().setNodeId(2).setPayload(BatteryCommand.assembleBatteryReq()).build().send();
		sendDataResp.waitFor();
		// debug
		System.out.println(Constants.INDENT + sendDataResp.success);

		sleep(10000);

		sendDataResp = controller.getSendDataReq().setNodeId(3).setPayload(BatteryCommand.assembleBatteryReq()).build().send();
		sendDataResp.waitFor();
		// debug
		System.out.println(Constants.INDENT + sendDataResp.success);

		sleep(10000);

		sendDataResp = controller.getSendDataReq().setNodeId(2).setPayload(BasicCommand.assembleBasicReq()).build().send();
		sendDataResp.waitFor();
		// debug
		System.out.println(Constants.INDENT + sendDataResp.success);

		sleep(10000);

		sendDataResp = controller.getSendDataReq().setNodeId(3).setPayload(BasicCommand.assembleBasicReq()).build().send();
		sendDataResp.waitFor();
		// debug
		System.out.println(Constants.INDENT + sendDataResp.success);

		sleep(10000);

		sendDataResp = controller.getSendDataReq().setNodeId(2).setPayload(WakeUpCommand.assembleWakeUpIntervalCapabilitiesGetReq()).build().send();
		sendDataResp.waitFor();
		// debug
		System.out.println(Constants.INDENT + sendDataResp.success);

		sleep(10000);

		sendDataResp = controller.getSendDataReq().setNodeId(3).setPayload(WakeUpCommand.assembleWakeUpIntervalCapabilitiesGetReq()).build().send();
		sendDataResp.waitFor();
		// debug
		System.out.println(Constants.INDENT + sendDataResp.success);

		sleep(10000);

		sendDataResp = controller.getSendDataReq().setNodeId(2).setPayload(WakeUpCommand.assembleWakeUpIntervalGetReq()).build().send();
		sendDataResp.waitFor();
		// debug
		System.out.println(Constants.INDENT + sendDataResp.success);

		sleep(10000);

		sendDataResp = controller.getSendDataReq().setNodeId(3).setPayload(WakeUpCommand.assembleWakeUpIntervalGetReq()).build().send();
		sendDataResp.waitFor();
		// debug
		System.out.println(Constants.INDENT + sendDataResp.success);

		sleep(10000);

		sendDataResp = controller.getSendDataReq().setNodeId(2).setPayload(SensorMultiLevelCommand.assembleSensorMultiLevelSupportedGetReq()).build().send();
		sendDataResp.waitFor();
		// debug
		System.out.println(Constants.INDENT + sendDataResp.success);

		sleep(10000);

		sendDataResp = controller.getSendDataReq().setNodeId(2).setPayload(SensorMultiLevelCommand.assembleSensorMultiLevelGetReq(0x01)).build().send();
		sendDataResp.waitFor();
		// debug
		System.out.println(Constants.INDENT + sendDataResp.success);

		sleep(10000);

		sendDataResp = controller.getSendDataReq().setNodeId(2).setPayload(SensorMultiLevelCommand.assembleSensorMultiLevelGetReq(0x03)).build().send();
		sendDataResp.waitFor();
		// debug
		System.out.println(Constants.INDENT + sendDataResp.success);

		sleep(10000);

		sendDataResp = controller.getSendDataReq().setNodeId(2).setPayload(SensorMultiLevelCommand.assembleSensorMultiLevelGetReq(0x05)).build().send();
		sendDataResp.waitFor();
		// debug
		System.out.println(Constants.INDENT + sendDataResp.success);

		sleep(10000);

		sendDataResp = controller.getSendDataReq().setNodeId(2).setPayload(SensorMultiLevelCommand.assembleSensorMultiLevelGetReq(0x1B)).build().send();
		sendDataResp.waitFor();
		// debug
		System.out.println(Constants.INDENT + sendDataResp.success);

		sleep(10000);

		sendDataResp = controller.getSendDataReq().setNodeId(3).setPayload(ThermostatSetpointCommand.assembleThermostatSetpointSupportedGetReq()).build().send();
		sendDataResp.waitFor();
		// debug
		System.out.println(Constants.INDENT + sendDataResp.success);

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
			Thread.sleep(millis);
		}
		catch (InterruptedException e) {
		}
	}

	public void onApplicationUpdate(ApplicationUpdateResp applicationUpdateResp) {
		// debug
		//System.out.println("ApplicationUpdate nodeId: " + applicationUpdateResp.nodeId);
		//byte[] data = new byte[] {(byte)0x13, (byte)applicationUpdateResp.nodeId, (byte)0x06, (byte)0x84, (byte)0x04, (byte)0x00, (byte)0x01, (byte)0x68, (byte)0x01, (byte)0x05, (byte)0x03};
		//controller.queueOut.insert(FrameUtils.assemble(MessageType.Request, data));
	}

	public void onApplicationCommandHandler(ApplicationCommandHandlerResp applicationCommandHandlerResp) {
		ApplicationCommandHandlerData data = applicationCommandHandlerResp.data;
		if (data != null) {
			if (data instanceof Basic) {
				Basic basicResp = (Basic)data;
				System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " basic value: " + basicResp.value);
			}
			if (data instanceof SensorMultiLevelSupportedReport) {
				SensorMultiLevelSupportedReport sensorMultiLevelSupportedReport = (SensorMultiLevelSupportedReport)data;
				Iterator<SensorType> sensorTypesIter = sensorMultiLevelSupportedReport.supportedSensorTypeList.iterator();
				SensorType sensorType;
				while (sensorTypesIter.hasNext()) {
					sensorType = sensorTypesIter.next();
					System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " SensorType: " + sensorType.getLabel() + "(" + HexUtils.byteToHexString(sensorType.getId()) + ")");
				}
			}
			if (data instanceof SensorMultiLevelReport) {
				SensorMultiLevelReport sensorMultiLevelReport = (SensorMultiLevelReport)data;
		        System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " SensorType: " + sensorMultiLevelReport.sensorType.getLabel() + "(" + HexUtils.byteToHexString(sensorMultiLevelReport.sensorType.getId()) + ") = " + sensorMultiLevelReport.value.toPlainString());
			}
			if (data instanceof ManufacturerSpecific) {
				ManufacturerSpecific manufacturerSpecificResp = (ManufacturerSpecific)data;
		        System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " manufactureId: " + HexUtils.byteCharToHexString(manufacturerSpecificResp.manufactureId));
		        System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + "    deviceType: " + HexUtils.byteCharToHexString(manufacturerSpecificResp.deviceType));
		        System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + "      deviceId: " + HexUtils.byteCharToHexString(manufacturerSpecificResp.deviceId));
			}
			if (data instanceof Battery) {
				Battery batteryResp = (Battery)data;
				System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " battery level: " + batteryResp.level + "%");
			}
			if (data instanceof WakeUpNotification) {
				System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " wake up notification");
			}
			if (data instanceof WakeUpIntervalReport) {
				WakeUpIntervalReport wakeUpIntervalReport = (WakeUpIntervalReport)data;
				System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " wake up intercal: " + wakeUpIntervalReport.interval + ", " + wakeUpIntervalReport.scale);
			}
			if (data instanceof WakeUpIntervalCapabilitiesReport) {
				WakeUpIntervalCapabilitiesReport wakeUpIntervalCapabilitiesReport = (WakeUpIntervalCapabilitiesReport)data;
				System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " wake up minInterval: " + wakeUpIntervalCapabilitiesReport.minInterval);
				System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " wake up maxInterval: " + wakeUpIntervalCapabilitiesReport.maxInterval);
				System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " wake up defaultInterval: " + wakeUpIntervalCapabilitiesReport.defaultInterval);
				System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " wake up intervalStep: " + wakeUpIntervalCapabilitiesReport.intervalStep);
			}
			if (data instanceof UnknownApplicationCommandHandlerData) {
				UnknownApplicationCommandHandlerData unknownApplicationCommandHandlerData = (UnknownApplicationCommandHandlerData)data;
				System.out.println(Constants.INDENT + "Node " + applicationCommandHandlerResp.nodeId + " unsupported data: " + HexUtils.byteArrayToHexString(unknownApplicationCommandHandlerData.data));
			}
		}
	}

}
