package com.antiaction.zwave.messages.command;

import com.antiaction.zwave.constants.CommandClass;

/**
 * WAKE_UP
 * > 0x01 0x08 0x00 0x04 0x00 0x03 0x02 0x84 0x07 0x71
 * < 0x01 0x0C 0x00 0x04 0x00 0x02 0x06 0x84 0x06 0x00 0x01 0x68 0x01 0x19
 * > 0x01 0x14 0x00 0x04 0x00 0x02 0x0E 0x84 0x0A 0x00 0x00 0xF0 0x00 0x0E 0x10 0x00 0x0E 0x10 0x00 0x00 0x3C 0xA1
 *
 * < 01 0D 00 13 0C 06 84 04 00 01 68 01 05 03 05
 * @author nicl
 */
public class WakeUpCommand {

	private static final int COMMAND_CLASS = CommandClass.WAKE_UP.getClassCode() & 255;

	private static final int WAKE_UP_INTERVAL_SET = 0x04;
	private static final int WAKE_UP_INTERVAL_GET = 0x05;
	private static final int WAKE_UP_INTERVAL_REPORT = 0x06;
	private static final int WAKE_UP_NOTIFICATION = 0x07;
	private static final int WAKE_UP_NO_MORE_INFORMATION = 0x08;
	private static final int WAKE_UP_INTERVAL_CAPABILITIES_GET = 0x09;
	private static final int WAKE_UP_INTERVAL_CAPABILITIES_REPORT = 0x0A;

	protected WakeUpCommand() {
	}

	public static byte[] assembleWakeUpIntervalSetReq(int interval, int scale) {
		byte[] data = {
				(byte)COMMAND_CLASS,
				(byte)WAKE_UP_INTERVAL_SET,
				(byte)((interval >> 16) & 255),
				(byte)((interval >> 8) & 255),
				(byte)(interval & 255),
				(byte)(scale & 255)
		};
		return data;
	}

	private static final byte[] WAKE_UP_INTERVAL_GET_REQ;

	static {
		WAKE_UP_INTERVAL_GET_REQ = new byte[] {
				(byte)COMMAND_CLASS,
				(byte)WAKE_UP_INTERVAL_GET
		};
	}

	public static byte[] assembleWakeUpIntervalGetReq() {
		return WAKE_UP_INTERVAL_GET_REQ;
	}

	private static final byte[] WAKE_UP_INTERVAL_CAPABILITIES_GET_REQ;

	static {
		WAKE_UP_INTERVAL_CAPABILITIES_GET_REQ = new byte[] {
				(byte)COMMAND_CLASS,
				(byte)WAKE_UP_INTERVAL_CAPABILITIES_GET
		};
	}

	public static byte[] assembleWakeUpIntervalCapabilitiesGetReq() {
		return WAKE_UP_INTERVAL_CAPABILITIES_GET_REQ;
	}

	private static final byte[] WAKE_UP_NO_MORE_INFORMATION_REQ;

	static {
		WAKE_UP_NO_MORE_INFORMATION_REQ = new byte[] {
				(byte)COMMAND_CLASS,
				(byte)WAKE_UP_NO_MORE_INFORMATION
		};
	}

	public static byte[] assembleWakeUpNoMoreInformationReq() {
		return WAKE_UP_NO_MORE_INFORMATION_REQ;
	}

	public static ApplicationCommandHandlerData disassemble(byte[] data) {
		if (data.length >= 2) {
			int idx = 0;
			int commandClass = data[idx++] & 255;
			int command = data[idx++] & 255;
			if (commandClass == COMMAND_CLASS) {
				switch (command) {
				case WAKE_UP_NOTIFICATION:
					return new WakeUpNotification();
				case WAKE_UP_INTERVAL_REPORT:
					WakeUpIntervalReport wakeUpIntervalReport = new WakeUpIntervalReport();
					wakeUpIntervalReport.interval = ((data[idx++] & 255) << 16) | ((data[idx++] & 255) << 8) | (data[idx++] & 255);
					wakeUpIntervalReport.scale = data[idx++] & 255;
					return wakeUpIntervalReport;
				case WAKE_UP_INTERVAL_CAPABILITIES_REPORT:
					WakeUpIntervalCapabilitiesReport wakeUpIntervalCapabilitiesReport = new WakeUpIntervalCapabilitiesReport();
					wakeUpIntervalCapabilitiesReport.minInterval = ((data[idx++] & 255) << 16) | ((data[idx++] & 255) << 8) | (data[idx++] & 255);
					wakeUpIntervalCapabilitiesReport.maxInterval = ((data[idx++] & 255) << 16) | ((data[idx++] & 255) << 8) | (data[idx++] & 255);
					wakeUpIntervalCapabilitiesReport.defaultInterval = ((data[idx++] & 255) << 16) | ((data[idx++] & 255) << 8) | (data[idx++] & 255);
					wakeUpIntervalCapabilitiesReport.intervalStep = ((data[idx++] & 255) << 16) | ((data[idx++] & 255) << 8) | (data[idx++] & 255);
					return wakeUpIntervalCapabilitiesReport;
				case WAKE_UP_INTERVAL_SET:
				case WAKE_UP_INTERVAL_GET:
				case WAKE_UP_NO_MORE_INFORMATION:
				case WAKE_UP_INTERVAL_CAPABILITIES_GET:
				default:
					break;
				}
			}
		}
		return null;
	}

	public static class WakeUpNotification extends ApplicationCommandHandlerData {
	}

	public static class WakeUpIntervalReport extends ApplicationCommandHandlerData {
		public int interval;
		public int scale;
	}

	public static class WakeUpIntervalCapabilitiesReport extends ApplicationCommandHandlerData {
		public int minInterval;
		public int maxInterval;
		public int defaultInterval;
		public int intervalStep;
	}

}
