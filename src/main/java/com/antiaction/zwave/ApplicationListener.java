package com.antiaction.zwave;

import com.antiaction.zwave.messages.ApplicationCommandHandlerResp;
import com.antiaction.zwave.messages.ApplicationUpdateResp;

public interface ApplicationListener {

	public void onApplicationUpdate(ApplicationUpdateResp applicationUpdateResp);

	public void onApplicationCommandHandler(ApplicationCommandHandlerResp applicationCommandHandlerResp);

}
