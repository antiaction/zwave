package com.antiaction.zwave.messages.command;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.antiaction.zwave.messages.ApplicationCommandHandlerData;
import com.antiaction.zwave.messages.ApplicationCommandHandlerResp;
import com.antiaction.zwave.messages.command.BasicCommand.BasicReport;

@RunWith(JUnit4.class)
public class TestBasicCommand {

	@Test
	public void test_basic_commandclass() {
		byte[] frame;
		ApplicationCommandHandlerResp applicationCommandHandlerResp;
		ApplicationCommandHandlerData applicationCommandHandlerData;

		BasicReport basicReport;

		frame = new byte[] {
				(byte)0x01, (byte)0x09, (byte)0x00, (byte)0x04, (byte)0x00, (byte)0x02, (byte)0x03, (byte)0x20,
				(byte)0x01, (byte)0xFF, (byte)0x2D
		};

		applicationCommandHandlerResp = ApplicationCommandHandlerResp.getInstance();
		Assert.assertNotNull(applicationCommandHandlerResp);

		applicationCommandHandlerResp.disassemble(frame);
		Assert.assertEquals(frame.length - 8, applicationCommandHandlerResp.payload.length);

		applicationCommandHandlerData = Command.disassemble(applicationCommandHandlerResp);
		Assert.assertNotNull(applicationCommandHandlerData);

		Assert.assertTrue("Expected BasicReport class", applicationCommandHandlerData instanceof BasicReport);
		basicReport = (BasicReport)applicationCommandHandlerData;
		Assert.assertEquals(255, basicReport.value);

		frame = new byte[] {
				(byte)0x01, (byte)0x09, (byte)0x00, (byte)0x04, (byte)0x00, (byte)0x02, (byte)0x03, (byte)0x20,
				(byte)0x01, (byte)0x00, (byte)0xD2
		};

		applicationCommandHandlerResp = ApplicationCommandHandlerResp.getInstance();
		Assert.assertNotNull(applicationCommandHandlerResp);

		applicationCommandHandlerResp.disassemble(frame);
		Assert.assertEquals(frame.length - 8, applicationCommandHandlerResp.payload.length);

		applicationCommandHandlerData = Command.disassemble(applicationCommandHandlerResp);
		Assert.assertNotNull(applicationCommandHandlerData);

		Assert.assertTrue("Expected BasicReport class", applicationCommandHandlerData instanceof BasicReport);
		basicReport = (BasicReport)applicationCommandHandlerData;
		Assert.assertEquals(0, basicReport.value);
	}

}
