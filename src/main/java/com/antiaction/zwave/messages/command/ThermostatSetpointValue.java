package com.antiaction.zwave.messages.command;

public class ThermostatSetpointValue {

	public int precision;
	public int scale;
	public int size;
	public int value;

	public static ThermostatSetpointValue disassemble(byte[] data, int idx) {
		ThermostatSetpointValue thermostatSetpointValue = new ThermostatSetpointValue();
		thermostatSetpointValue.precision = (data[idx] & ThermostatSetpointCommand.THERMOSTAT_SETPOINT_PRECISION_MASK) >> ThermostatSetpointCommand.THERMOSTAT_SETPOINT_PRECISION_SHIFT;
		thermostatSetpointValue.scale = (data[idx] & ThermostatSetpointCommand.THERMOSTAT_SETPOINT_SCALE_MASK) >> ThermostatSetpointCommand.THERMOSTAT_SETPOINT_SCALE_SHIFT;
		thermostatSetpointValue.size = data[idx] & ThermostatSetpointCommand.THERMOSTAT_SETPOINT_SIZE_MASK;
		++idx;
		switch (thermostatSetpointValue.size) {
		case 4:
			thermostatSetpointValue.value = data[idx++] << 24 | (data[idx++] & 255) << 16 | (data[idx++] & 255) << 8 | (data[idx++] & 255);
			break;
		case 2:
			thermostatSetpointValue.value = data[idx++] << 8 | (data[idx++] & 255);
			break;
		case 1:
			thermostatSetpointValue.value = data[idx++];
			break;
		default:
			break;
		}
		return thermostatSetpointValue;
	}

}
