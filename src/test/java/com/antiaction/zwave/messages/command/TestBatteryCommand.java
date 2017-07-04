package com.antiaction.zwave.messages.command;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.antiaction.zwave.messages.ApplicationCommandHandlerData;
import com.antiaction.zwave.messages.ApplicationCommandHandlerResp;
import com.antiaction.zwave.messages.command.BatteryCommand.BatteryReport;

@RunWith(JUnit4.class)
public class TestBatteryCommand {

	@Test
	public void test_battery_commandclass() {
		byte[] frame;
		ApplicationCommandHandlerResp applicationCommandHandlerResp;
		ApplicationCommandHandlerData applicationCommandHandlerData;

		BatteryReport batteryReport;

		frame = new byte[] {
				(byte)0x01, (byte)0x09, (byte)0x00, (byte)0x04, (byte)0x00, (byte)0x02, (byte)0x03, (byte)0x80,
				(byte)0x03, (byte)0x64, (byte)0x14
		};

		applicationCommandHandlerResp = ApplicationCommandHandlerResp.getInstance();
		Assert.assertNotNull(applicationCommandHandlerResp);

		applicationCommandHandlerResp.disassemble(frame);
		Assert.assertEquals(frame.length - 8, applicationCommandHandlerResp.payload.length);

		applicationCommandHandlerData = Command.disassemble(applicationCommandHandlerResp);
		Assert.assertNotNull(applicationCommandHandlerData);

		Assert.assertTrue("Expected BatteryReport class", applicationCommandHandlerData instanceof BatteryReport);
		batteryReport = (BatteryReport)applicationCommandHandlerData;
		Assert.assertEquals(100, batteryReport.level);

		frame = new byte[] {
				(byte)0x01, (byte)0x09, (byte)0x00, (byte)0x04, (byte)0x00, (byte)0x03, (byte)0x03, (byte)0x80,
				(byte)0x03, (byte)0x48, (byte)0x39
		};

		applicationCommandHandlerResp = ApplicationCommandHandlerResp.getInstance();
		Assert.assertNotNull(applicationCommandHandlerResp);

		applicationCommandHandlerResp.disassemble(frame);
		Assert.assertEquals(frame.length - 8, applicationCommandHandlerResp.payload.length);

		applicationCommandHandlerData = Command.disassemble(applicationCommandHandlerResp);
		Assert.assertNotNull(applicationCommandHandlerData);

		Assert.assertTrue("Expected BatteryReport class", applicationCommandHandlerData instanceof BatteryReport);
		batteryReport = (BatteryReport)applicationCommandHandlerData;
		Assert.assertEquals(72, batteryReport.level);
	}

}
