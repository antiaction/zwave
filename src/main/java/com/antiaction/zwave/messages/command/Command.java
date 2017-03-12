package com.antiaction.zwave.messages.command;

import com.antiaction.zwave.messages.ApplicationCommandHandlerData;
import com.antiaction.zwave.messages.ApplicationCommandHandlerResp;

public abstract class Command {

	@SuppressWarnings("deprecation")
	public static ApplicationCommandHandlerData disassemble(ApplicationCommandHandlerResp applicationCommandHandlerResp) {
		byte[] data = applicationCommandHandlerResp.payload;
		ApplicationCommandHandlerData achData = null;
		// CommandClass.
		switch (data[0]) {
		case (byte)0x20:
			achData = BasicCommand.disassemble(data);
			applicationCommandHandlerResp.data = achData;
			break;
		case (byte)0x25:
			achData = BinarySwitchCommand.disassemble(data);
			applicationCommandHandlerResp.data = achData;
			break;
		case (byte)0x26:
			achData = MultiLevelSwitchCommand.disassemble(data);
			applicationCommandHandlerResp.data = achData;
			break;
		case (byte)0x30:
			achData = BinarySensorCommand.disassemble(data);
			applicationCommandHandlerResp.data = achData;
			break;
		case (byte)0x31:
			achData = MultiLevelSensorCommand.disassemble(data);
			applicationCommandHandlerResp.data = achData;
			break;
		case (byte)0x43:
			achData = ThermostatSetpointCommand.disassemble(data);
			applicationCommandHandlerResp.data = achData;
			break;
		case (byte)0x46:
			achData = ClimateControlScheduleCommand.disassemble(data);
			applicationCommandHandlerResp.data = achData;
			break;
		case (byte)0x70:
			achData = ConfigurationCommand.disassemble(data);
			applicationCommandHandlerResp.data = achData;
			break;
		case (byte)0x71:
			achData = AlarmCommand.disassemble(data);
			applicationCommandHandlerResp.data = achData;
			break;
		case (byte)0x72:
			achData = ManufacturerSpecificCommand.disassemble(data);
			applicationCommandHandlerResp.data = achData;
			break;
		case (byte)0x75:
			achData = ProtectionCommand.disassemble(data);
			applicationCommandHandlerResp.data = achData;
			break;
		case (byte)0x80:
			achData = BatteryCommand.disassemble(data);
			applicationCommandHandlerResp.data = achData;
			break;
		case (byte)0x81:
			achData = ClockCommand.disassemble(data);
			applicationCommandHandlerResp.data = achData;
			break;
		case (byte)0x84:
			achData = WakeUpCommand.disassemble(data);
			applicationCommandHandlerResp.data = achData;
			break;
		case (byte)0x86:
			achData = VersionCommand.disassemble(data);
			applicationCommandHandlerResp.data = achData;
			break;
		case (byte)0x8F:
			achData = MultiCmdCommand.disassemble(data);
			applicationCommandHandlerResp.data = achData;
			break;
		default:
			achData = new UnknownCommand(data);
			applicationCommandHandlerResp.data = achData;
			break;
		}
		return achData;
	}

}
