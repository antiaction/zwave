package com.antiaction.zwave.messages.command;

import com.antiaction.zwave.constants.CommandClass;
import com.antiaction.zwave.messages.ApplicationCommandHandlerData;

public class AlarmCommand {

	private static final int COMMAND_CLASS = CommandClass.ALARM.getClassCode() & 255;

	public static final int ALARM_GET = 0x04;
	public static final int ALARM_REPORT = 0x05;
	public static final int ALARM_SET_V2  = 0x06;
	public static final int ALARM_TYPE_SUPPORTED_GET_V2  = 0x07;
	public static final int ALARM_TYPE_SUPPORTED_REPORT_V2  = 0x08;

	public static final int ALARM_TYPE_SUPPORTED_REPORT_NUMBER_OF_BIT_MASKS_MASK_V2  = 0x1F;
	public static final int ALARM_TYPE_SUPPORTED_REPORT_V1_ALARM_BIT_MASK_V2  = 0x80;

	protected AlarmCommand() {
	}

	public static byte[] assembleAlarmGetReq(int alarmType) {
		byte[] data = {
				(byte)COMMAND_CLASS,
				(byte)ALARM_GET,
				(byte)(alarmType & 255)
		};
		return data;
	}

	public static byte[] assembleAlarmSetReqV2(int alarmType, int alarmStatus) {
		byte[] data = {
				(byte)COMMAND_CLASS,
				(byte)ALARM_GET,
				(byte)(alarmType & 255),
				(byte)(alarmStatus & 255)
		};
		return data;
	}

	public static byte[] assembleAlarmGetReqV2(int alarmType, int zwaveAlarmType) {
		byte[] data = {
				(byte)COMMAND_CLASS,
				(byte)ALARM_GET,
				(byte)(alarmType & 255),
				(byte)(zwaveAlarmType & 255)
		};
		return data;
	}

	private static final byte[] ALARM_TYPE_SUPPORTED_GET_REQ;

	static {
		ALARM_TYPE_SUPPORTED_GET_REQ = new byte[] {
				(byte)COMMAND_CLASS,
				(byte)ALARM_TYPE_SUPPORTED_GET_V2
		};
	}

	public static byte[] assembleAlarmTypeSupportedGetReq() {
		return ALARM_TYPE_SUPPORTED_GET_REQ;
	}

	public static ApplicationCommandHandlerData disassemble(byte[] data) {
		if (data.length >= 2) {
			int idx = 0;
			int commandClass = data[idx++] & 255;
			int command = data[idx++] & 255;
			if (commandClass == COMMAND_CLASS) {
				switch (command) {
				case ALARM_REPORT:
					if ((data.length - idx) == 2) {
						AlarmReport alarmReport = new AlarmReport();
						alarmReport.alarmType = data[idx++] & 255;
						alarmReport.alarmLevel = data[idx++] & 255;
						return alarmReport;
					}
					else {
						AlarmReportV2 alarmReport = new AlarmReportV2();
						alarmReport.alarmType = data[idx++] & 255;
						alarmReport.alarmLevel = data[idx++] & 255;
						alarmReport.zenzoeNetSourceNodeId = data[idx++] & 255;
						alarmReport.zwaveAlarmStatus = data[idx++] & 255;
						alarmReport.zwaveAlarmType = data[idx++] & 255;
						alarmReport.zwaveAlarmEvent = data[idx++] & 255;
						// TODO
						int numberOfEventParams = data[idx++] & 255;
						return alarmReport;
					}
				case ALARM_TYPE_SUPPORTED_REPORT_V2:
					AlarmTypeSupportedReport alarmTypeSupportedReport = new AlarmTypeSupportedReport();
					alarmTypeSupportedReport.v1Alarm = (data[idx] & ALARM_TYPE_SUPPORTED_REPORT_V1_ALARM_BIT_MASK_V2) != 0;
					int numberOfBitMasks = data[idx++] & ALARM_TYPE_SUPPORTED_REPORT_NUMBER_OF_BIT_MASKS_MASK_V2;
					// TODO Bitmask.
					alarmTypeSupportedReport.bitmask = new byte[numberOfBitMasks];
					System.arraycopy(data, idx, alarmTypeSupportedReport.bitmask, 0, numberOfBitMasks);
					return alarmTypeSupportedReport;
				case ALARM_GET:
				case ALARM_SET_V2:
				case ALARM_TYPE_SUPPORTED_GET_V2:
				default:
					break;
				}
			}
		}
		return null;
	}

	public static class AlarmReport extends ApplicationCommandHandlerData {
		public int alarmType;
		public int alarmLevel;
	}

	public static class AlarmReportV2 extends ApplicationCommandHandlerData {
		public int alarmType;
		public int alarmLevel;
		public int zenzoeNetSourceNodeId;
		public int zwaveAlarmStatus;
		public int zwaveAlarmType;
		public int zwaveAlarmEvent;
	}

	public static class AlarmTypeSupportedReport extends ApplicationCommandHandlerData {
		public boolean v1Alarm;
		public byte[] bitmask;
	}

}
