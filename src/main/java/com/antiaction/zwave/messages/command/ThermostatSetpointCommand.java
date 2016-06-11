package com.antiaction.zwave.messages.command;

import com.antiaction.zwave.constants.CommandClass;

public class ThermostatSetpointCommand {

	private static final int COMMAND_CLASS = CommandClass.THERMOSTAT_SETPOINT.getClassCode() & 255;

	private static final byte THERMOSTAT_SETPOINT_SET = 0x1;
	private static final byte THERMOSTAT_SETPOINT_GET = 0x2;
	private static final byte THERMOSTAT_SETPOINT_REPORT = 0x3;
	private static final byte THERMOSTAT_SETPOINT_SUPPORTED_GET = 0x4;
	private static final byte THERMOSTAT_SETPOINT_SUPPORTED_REPORT = 0x5;

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

}
