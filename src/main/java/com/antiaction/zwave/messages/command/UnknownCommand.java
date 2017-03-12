package com.antiaction.zwave.messages.command;

import com.antiaction.zwave.messages.ApplicationCommandHandlerData;

public class UnknownCommand extends ApplicationCommandHandlerData {

	public byte[] data;

	public UnknownCommand(byte[] data) {
		this.data = data;
	}

}
