package com.antiaction.zwave.messages.command;

import java.lang.invoke.SwitchPoint;
import java.util.List;

import com.antiaction.zwave.constants.CommandClass;
import com.antiaction.zwave.messages.ApplicationCommandHandlerData;

public class ClimateControlScheduleCommand {

	private static final int COMMAND_CLASS = CommandClass.CLIMATE_CONTROL_SCHEDULE.getClassCode() & 255;

	public static final int SCHEDULE_SET  = 0x01;
	public static final int SCHEDULE_GET  = 0x02;
	public static final int SCHEDULE_REPORT  = 0x03;
	public static final int SCHEDULE_CHANGED_GET  = 0x04;
	public static final int SCHEDULE_CHANGED_REPORT  = 0x05;
	public static final int SCHEDULE_OVERRIDE_SET  = 0x06;
	public static final int SCHEDULE_OVERRIDE_GET  = 0x07;
	public static final int SCHEDULE_OVERRIDE_REPORT  = 0x08;

	public static final int SCHEDULE_WEEKDAY_MASK = 0x07;

	public static final int SCHEDULE_WEEKDAY_MONDAY = 1;
	public static final int SCHEDULE_WEEKDAY_TUESDAY = 2;
	public static final int SCHEDULE_WEEKDAY_WEDNESDAY = 3;
	public static final int SCHEDULE_WEEKDAY_THURSDAY = 4;
	public static final int SCHEDULE_WEEKDAY_FRIDAY = 5;
	public static final int SCHEDULE_WEEKDAY_SATURDAY = 6;
	public static final int SCHEDULE_WEEKDAY_SUNDAY = 7;

	public static final int SCHEDULE_STATE_FROST_PROTECTION = 121;
	public static final int SCHEDULE_STATE_ENERGY_SAVING_MODE = 122;
	public static final int SCHEDULE_STATE_UNUSED_STATE = 127;

	public static final int SCHEDULE_NO_OVERRIDE = 0;
	public static final int SCHEDULE_TEMPORARY_OVERRIDE = 1;
	public static final int SCHEDULE_PERMANENT_OVERRIDE = 2;

	protected ClimateControlScheduleCommand() {
	}

	public static byte[] assembleScheduleSetReq(int weekday, List<SwitchPoint> switchpointList) {
		byte[] data = {
				(byte)COMMAND_CLASS,
				(byte)SCHEDULE_SET,
				(byte)(weekday & SCHEDULE_WEEKDAY_MASK),
				// TODO
				0, 0, (byte)SCHEDULE_STATE_UNUSED_STATE,
				0, 0, (byte)SCHEDULE_STATE_UNUSED_STATE,
				0, 0, (byte)SCHEDULE_STATE_UNUSED_STATE,
				0, 0, (byte)SCHEDULE_STATE_UNUSED_STATE,
				0, 0, (byte)SCHEDULE_STATE_UNUSED_STATE,
				0, 0, (byte)SCHEDULE_STATE_UNUSED_STATE,
				0, 0, (byte)SCHEDULE_STATE_UNUSED_STATE,
				0, 0, (byte)SCHEDULE_STATE_UNUSED_STATE,
				0, 0, (byte)SCHEDULE_STATE_UNUSED_STATE
		};
		return data;
	}

	public static byte[] assembleScheduleGetReq(int weekday) {
		byte[] data = {
				(byte)COMMAND_CLASS,
				(byte)SCHEDULE_GET,
				(byte)(weekday & SCHEDULE_WEEKDAY_MASK)
		};
		return data;
	}

	private static final byte[] SCHEDULE_CHANGED_GET_REQ;

	static {
		SCHEDULE_CHANGED_GET_REQ = new byte[] {
				(byte)COMMAND_CLASS,
				(byte)SCHEDULE_CHANGED_GET
		};
	}

	public static byte[] assembleScheduleChangedGetReq() {
		return SCHEDULE_CHANGED_GET_REQ;
	}

	public static byte[] assembleScheduleOverrideSetReq(int overrideType, int overrideState) {
		byte[] data = {
				(byte)COMMAND_CLASS,
				(byte)SCHEDULE_OVERRIDE_SET,
				(byte)(overrideType & 0x03),
				(byte)overrideState
		};
		return data;
	}

	private static final byte[] SCHEDULE_OVERRIDE_GET_REQ;

	static {
		SCHEDULE_OVERRIDE_GET_REQ = new byte[] {
				(byte)COMMAND_CLASS,
				(byte)SCHEDULE_OVERRIDE_GET
		};
	}

	public static byte[] assembleScheduleOverrideGetReq() {
		return SCHEDULE_OVERRIDE_GET_REQ;
	}

	public static ApplicationCommandHandlerData disassemble(byte[] data) {
		if (data.length >= 2) {
			int idx = 0;
			int commandClass = data[idx++] & 255;
			int command = data[idx++] & 255;
			if (commandClass == COMMAND_CLASS) {
				switch (command) {
				case SCHEDULE_REPORT:
					ScheduleReport scheduleReport = new ScheduleReport();
					scheduleReport.weekday = data[idx++] & SCHEDULE_WEEKDAY_MASK;
					Switchpoint switchpoint;
					for (int i=0; i<9; ++i) {
						switchpoint = new Switchpoint();
						scheduleReport.switchpoints[i] = switchpoint;
						idx = switchpoint.disassemble(data, idx);
					}
					return scheduleReport;
				case SCHEDULE_CHANGED_REPORT:
					ScheduleChangedReport scheduleChangedReport = new ScheduleChangedReport();
					scheduleChangedReport.changeCounter = data[idx++] & SCHEDULE_WEEKDAY_MASK;
					return scheduleChangedReport;
				case SCHEDULE_OVERRIDE_REPORT:
					ScheduleOverrideReport scheduleOverrideReport = new ScheduleOverrideReport();
					scheduleOverrideReport.overrideType = data[idx++] & 0x03;
					scheduleOverrideReport.overrideState = data[idx++] & 255;
					return scheduleOverrideReport;
				case SCHEDULE_SET:
				case SCHEDULE_GET:
				case SCHEDULE_CHANGED_GET:
				case SCHEDULE_OVERRIDE_SET:
				case SCHEDULE_OVERRIDE_GET:
				default:
					break;
				}
			}
		}
		return null;
	}

	public static class Switchpoint {
		public int hour;
		public int minute;
		public int scheduleState;
		public int disassemble(byte[] data, int idx) {
			hour = data[idx++] & 0x1F;
			minute = data[idx++] & 0x3F;
			scheduleState = data[idx++] & 255;
			return idx;
		}
	}

	public static class ScheduleReport extends ApplicationCommandHandlerData {
		public int weekday;
		public Switchpoint[] switchpoints;
	}

	public static class ScheduleChangedReport extends ApplicationCommandHandlerData {
		public int changeCounter;
	}

	public static class ScheduleOverrideReport extends ApplicationCommandHandlerData {
		public int overrideType;
		public int overrideState;
	}

}
