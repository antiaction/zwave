package com.antiaction.zwave.messages.command;

import com.antiaction.zwave.constants.CommandClass;
import com.antiaction.zwave.messages.ApplicationCommandHandlerData;

/**
 * [2016-07-03 02:40:26.759 +0200]  > 0x01 0x0C 0x00 0x04 0x00 0x03 0x06 0x43 0x03 0x01 0x42 0x0A 0x28 0xD3
 * [2016-07-03 02:40:26.924 +0200]  > 0x01 0x09 0x00 0x04 0x00 0x03 0x03 0x43 0x05 0x02 0xB6
 * 
 * @author nicl
 */
public class ThermostatSetpointCommand {

	private static final int COMMAND_CLASS = CommandClass.THERMOSTAT_SETPOINT.getClassCode() & 255;

	public static final int THERMOSTAT_SETPOINT_SET = 0x01;
	public static final int THERMOSTAT_SETPOINT_GET = 0x02;
	public static final int THERMOSTAT_SETPOINT_REPORT = 0x03;
	public static final int THERMOSTAT_SETPOINT_SUPPORTED_GET = 0x04;
	public static final int THERMOSTAT_SETPOINT_SUPPORTED_REPORT = 0x05;
	public static final int THERMOSTAT_SETPOINT_CAPABILITIES_GET_V3 = 0x09;
	public static final int THERMOSTAT_SETPOINT_CAPABILITIES_REPORT_V3 = 0x0A;

	public static final int THERMOSTAT_SETPOINT_TYPE_HEATING_1 = 0x01;
	public static final int THERMOSTAT_SETPOINT_TYPE_COOLING_1 = 0x02;
	public static final int THERMOSTAT_SETPOINT_TYPE_FURNACE = 0x07;
	public static final int THERMOSTAT_SETPOINT_TYPE_DRY_AIR = 0x08;
	public static final int THERMOSTAT_SETPOINT_TYPE_MOIST_AIR = 0x09;
	public static final int THERMOSTAT_SETPOINT_TYPE_AUTO_CHANGEOVER = 0x0A;
	public static final int THERMOSTAT_SETPOINT_TYPE_ENERGY_SAVE_HEATING_V2 = 0x0B;
	public static final int THERMOSTAT_SETPOINT_TYPE_ENERGY_SAVE_COOLING_V2 = 0x0C;
	public static final int THERMOSTAT_SETPOINT_TYPE_AWAY_HEATING_V2 = 0x0D;
	public static final int THERMOSTAT_SETPOINT_TYPE_AWAY_COOLING_V3 = 0x0E;
	public static final int THERMOSTAT_SETPOINT_TYPE_FULL_POWER_V3 = 0x0F;

	public static final int THERMOSTAT_SETPOINT_SIZE_MASK = 0x07;
	public static final int THERMOSTAT_SETPOINT_SCALE_MASK = 0x18;
	public static final int THERMOSTAT_SETPOINT_SCALE_SHIFT = 0x03;
	public static final int THERMOSTAT_SETPOINT_PRECISION_MASK = 0xE0;
	public static final int THERMOSTAT_SETPOINT_PRECISION_SHIFT = 0x05;

	public static final int THERMOSTAT_SETPOINT_SCALE_CELCIUS = 0;
	public static final int THERMOSTAT_SETPOINT_SCALE_FARHENHEIT = 1;

	protected ThermostatSetpointCommand() {
	}

	public static byte[] assembleThermostatSetpointSetReq(int setpointType, ThermostatSetpointValue value) {
		byte[] data;
		switch (value.size) {
		case 4:
			data = new byte[] {
					(byte)COMMAND_CLASS,
					(byte)THERMOSTAT_SETPOINT_SET,
					(byte)(setpointType & 15),
					(byte)(((value.precision << THERMOSTAT_SETPOINT_PRECISION_SHIFT) & THERMOSTAT_SETPOINT_PRECISION_MASK) | ((value.scale << THERMOSTAT_SETPOINT_SCALE_SHIFT) & THERMOSTAT_SETPOINT_SCALE_MASK) | (value.size & THERMOSTAT_SETPOINT_SIZE_MASK)),
					(byte)((value.value >> 24) & 255),
					(byte)((value.value >> 16) & 255),
					(byte)((value.value >> 8) & 255),
					(byte)(value.value & 255)
			};
			return data;
		case 2:
			data = new byte[] {
					(byte)COMMAND_CLASS,
					(byte)THERMOSTAT_SETPOINT_SET,
					(byte)(setpointType & 15),
					(byte)(((value.precision << THERMOSTAT_SETPOINT_PRECISION_SHIFT) & THERMOSTAT_SETPOINT_PRECISION_MASK) | ((value.scale << THERMOSTAT_SETPOINT_SCALE_SHIFT) & THERMOSTAT_SETPOINT_SCALE_MASK) | (value.size & THERMOSTAT_SETPOINT_SIZE_MASK)),
					(byte)((value.value >> 8) & 255),
					(byte)(value.value & 255)
			};
			return data;
		case 1:
			data = new byte[] {
					(byte)COMMAND_CLASS,
					(byte)THERMOSTAT_SETPOINT_SET,
					(byte)(setpointType & 15),
					(byte)(((value.precision << THERMOSTAT_SETPOINT_PRECISION_SHIFT) & THERMOSTAT_SETPOINT_PRECISION_MASK) | ((value.scale << THERMOSTAT_SETPOINT_SCALE_SHIFT) & THERMOSTAT_SETPOINT_SCALE_MASK) | (value.size & THERMOSTAT_SETPOINT_SIZE_MASK)),
					(byte)(value.value & 255)
			};
			return data;
		default:
			return null;
		}
	}

	public static byte[] assembleThermostatSetpointGetReq(int setpointType) {
		byte[] data = {
				(byte)COMMAND_CLASS,
				(byte)THERMOSTAT_SETPOINT_GET,
				(byte)(setpointType & 15)
		};
		return data;
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

	public static byte[] assembleThermostatSetpointCapabilitiesGetReq(int setpointType) {
		byte[] data = {
				(byte)COMMAND_CLASS,
				(byte)THERMOSTAT_SETPOINT_CAPABILITIES_GET_V3,
				(byte)(setpointType & 15)
		};
		return data;
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
					thermostatSetpointReport.setpointType = data[idx++] & 15;
					thermostatSetpointReport.value = ThermostatSetpointValue.disassemble(data, idx);
					//idx += thermostatSetpointReport.value.size + 1;
					return thermostatSetpointReport;
				case THERMOSTAT_SETPOINT_SUPPORTED_REPORT:
					ThermostatSetpointSupportedReport thermostatSetpointSupportedReport = new ThermostatSetpointSupportedReport();
					thermostatSetpointSupportedReport.bitmask = new byte[data.length - idx];
					// TODO Bitmask.
					System.arraycopy(data, idx, thermostatSetpointSupportedReport.bitmask, 0, data.length - idx);
					return thermostatSetpointSupportedReport;
				case THERMOSTAT_SETPOINT_CAPABILITIES_REPORT_V3:
					ThermostatSetpointCapabilitiesReportV3 thermostatSetpointCapabilitiesReportV3 = new ThermostatSetpointCapabilitiesReportV3();
					thermostatSetpointCapabilitiesReportV3.setpointType = data[idx++] & 15;
					thermostatSetpointCapabilitiesReportV3.minValue = ThermostatSetpointValue.disassemble(data, idx);
					idx += thermostatSetpointCapabilitiesReportV3.minValue.size + 1;
					thermostatSetpointCapabilitiesReportV3.maxValue = ThermostatSetpointValue.disassemble(data, idx);
					idx += thermostatSetpointCapabilitiesReportV3.maxValue.size + 1;
					return thermostatSetpointCapabilitiesReportV3;
				case THERMOSTAT_SETPOINT_SET:
				case THERMOSTAT_SETPOINT_GET:
				case THERMOSTAT_SETPOINT_SUPPORTED_GET:
				case THERMOSTAT_SETPOINT_CAPABILITIES_GET_V3:
				default:
					break;
				}
			}
		}
		return null;
	}

	public static class ThermostatSetpointReport extends ApplicationCommandHandlerData {
		public int setpointType;
		public ThermostatSetpointValue value;
	}

	public static class ThermostatSetpointSupportedReport extends ApplicationCommandHandlerData {
		public byte[] bitmask;
	}

	public static class ThermostatSetpointCapabilitiesReportV3 extends ApplicationCommandHandlerData {
		public int setpointType;
		public ThermostatSetpointValue minValue;
		public ThermostatSetpointValue maxValue;
	}

}
