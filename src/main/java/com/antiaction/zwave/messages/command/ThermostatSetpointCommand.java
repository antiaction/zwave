package com.antiaction.zwave.messages.command;

import com.antiaction.zwave.constants.CommandClass;

/**
 * [2016-07-03 02:40:26.759 +0200]  > 0x01 0x0C 0x00 0x04 0x00 0x03 0x06 0x43 0x03 0x01 0x42 0x0A 0x28 0xD3
 * [2016-07-03 02:40:26.924 +0200]  > 0x01 0x09 0x00 0x04 0x00 0x03 0x03 0x43 0x05 0x02 0xB6
 * 
 * @author nicl
 */
public class ThermostatSetpointCommand {

	private static final int COMMAND_CLASS = CommandClass.THERMOSTAT_SETPOINT.getClassCode() & 255;

	private static final byte THERMOSTAT_SETPOINT_SET = 0x1;
	private static final byte THERMOSTAT_SETPOINT_GET = 0x2;
	private static final byte THERMOSTAT_SETPOINT_REPORT = 0x3;
	private static final byte THERMOSTAT_SETPOINT_SUPPORTED_GET = 0x4;
	private static final byte THERMOSTAT_SETPOINT_SUPPORTED_REPORT = 0x5;

	protected ThermostatSetpointCommand() {
	}

	private static final byte[] THERMOSTAT_SETPOINT_GET_REQ;

	static {
		THERMOSTAT_SETPOINT_GET_REQ = new byte[] {
				(byte)COMMAND_CLASS,
				(byte)THERMOSTAT_SETPOINT_GET
		};
	}

	public static byte[] assembleThermostatSetpointGetReq() {
		return THERMOSTAT_SETPOINT_GET_REQ;
	}

	private static final byte[] THERMOSTAT_SETPOINT_SUPPORTED_GET_REQ;

	static {
		THERMOSTAT_SETPOINT_SUPPORTED_GET_REQ = new byte[] {
				(byte)COMMAND_CLASS,
				(byte)THERMOSTAT_SETPOINT_SUPPORTED_GET
		};
	}

	public static byte[] assembleThermostatSetpointSupportedGetReq() {
		return THERMOSTAT_SETPOINT_SUPPORTED_GET_REQ;
	}

	public static ApplicationCommandHandlerData disassemble(byte[] data) {
		if (data.length >= 2) {
			int idx = 0;
			int commandClass = data[idx++] & 255;
			int command = data[idx++] & 255;
			if (commandClass == COMMAND_CLASS) {
				switch (command) {
				case THERMOSTAT_SETPOINT_REPORT:
					ThermostatSetpointReport thermostatSetpointReport = new ThermostatSetpointReport();
					return thermostatSetpointReport;
				case THERMOSTAT_SETPOINT_SUPPORTED_REPORT:
					ThermostatSetpointSupportedReport thermostatSetpointSupportedReport = new ThermostatSetpointSupportedReport();
					return thermostatSetpointSupportedReport;
				case THERMOSTAT_SETPOINT_SET:
				case THERMOSTAT_SETPOINT_GET:
				case THERMOSTAT_SETPOINT_SUPPORTED_GET:
				default:
					break;
				}
			}
		}
		return null;
	}

	public static class ThermostatSetpointReport extends ApplicationCommandHandlerData {
	}

	public static class ThermostatSetpointSupportedReport extends ApplicationCommandHandlerData {
	}

}
