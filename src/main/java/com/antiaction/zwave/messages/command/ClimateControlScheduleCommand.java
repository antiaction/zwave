package com.antiaction.zwave.messages.command;

import com.antiaction.zwave.constants.CommandClass;
import com.antiaction.zwave.messages.command.BatteryCommand.BatteryReport;

public class ClimateControlScheduleCommand {

	private static final int COMMAND_CLASS = CommandClass.CLIMATE_CONTROL_SCHEDULE.getClassCode() & 255;

	protected ClimateControlScheduleCommand() {
	}

	public static ApplicationCommandHandlerData disassemble(byte[] data) {
		if (data.length >= 2) {
			int idx = 0;
			int commandClass = data[idx++] & 255;
			int command = data[idx++] & 255;
			if (commandClass == COMMAND_CLASS) {
				switch (command) {
				default:
					break;
				}
			}
		}
		return null;
	}

}
