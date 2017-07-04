package com.antiaction.zwave.messages.command;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.antiaction.zwave.messages.ApplicationCommandHandlerData;
import com.antiaction.zwave.messages.ApplicationCommandHandlerResp;
import com.antiaction.zwave.messages.command.WakeUpCommand.WakeUpIntervalCapabilitiesReport;
import com.antiaction.zwave.messages.command.WakeUpCommand.WakeUpIntervalReport;
import com.antiaction.zwave.messages.command.WakeUpCommand.WakeUpNotification;

@RunWith(JUnit4.class)
public class TestWakeUpCommand {

	@Test
	public void test_wakeup_command() {
		byte[] frame;
		ApplicationCommandHandlerResp applicationCommandHandlerResp;
		ApplicationCommandHandlerData applicationCommandHandlerData;

		WakeUpNotification wakeUpNotification;
 		WakeUpIntervalReport wakeUpIntervalReport;
 		WakeUpIntervalCapabilitiesReport wakeUpIntervalCapabilitiesReport;

		frame = new byte[] {
				(byte)0x01, (byte)0x08, (byte)0x00, (byte)0x04, (byte)0x00, (byte)0x03, (byte)0x02, (byte)0x84,
				(byte)0x07, (byte)0x71
		};

		applicationCommandHandlerResp = ApplicationCommandHandlerResp.getInstance();
		Assert.assertNotNull(applicationCommandHandlerResp);

		applicationCommandHandlerResp.disassemble(frame);
		Assert.assertEquals(frame.length - 8, applicationCommandHandlerResp.payload.length);

		applicationCommandHandlerData = Command.disassemble(applicationCommandHandlerResp);
		Assert.assertNotNull(applicationCommandHandlerData);

		Assert.assertTrue("Expected WakeUpNotification class", applicationCommandHandlerData instanceof WakeUpNotification);
		wakeUpNotification = (WakeUpNotification)applicationCommandHandlerData;
		Assert.assertNotNull(wakeUpNotification);

		frame = new byte[] {
				(byte)0x01, (byte)0x0C, (byte)0x00, (byte)0x04, (byte)0x00, (byte)0x02, (byte)0x06, (byte)0x84,
				(byte)0x06, (byte)0x00, (byte)0x01, (byte)0x68, (byte)0x01, (byte)0x19
		};

		applicationCommandHandlerResp = ApplicationCommandHandlerResp.getInstance();
		Assert.assertNotNull(applicationCommandHandlerResp);

		applicationCommandHandlerResp.disassemble(frame);
		Assert.assertEquals(frame.length - 8, applicationCommandHandlerResp.payload.length);

		applicationCommandHandlerData = Command.disassemble(applicationCommandHandlerResp);
		Assert.assertNotNull(applicationCommandHandlerData);

		Assert.assertTrue("Expected WakeUpIntervalReport class", applicationCommandHandlerData instanceof WakeUpIntervalReport);
		wakeUpIntervalReport = (WakeUpIntervalReport)applicationCommandHandlerData;
		Assert.assertEquals(360, wakeUpIntervalReport.seconds);
		Assert.assertEquals(1, wakeUpIntervalReport.nodeId);

		frame = new byte[] {
				(byte)0X01, (byte)0x14, (byte)0x00, (byte)0x04, (byte)0x00, (byte)0x02, (byte)0x0E, (byte)0x84,
				(byte)0x0A, (byte)0x00, (byte)0x00, (byte)0xF0, (byte)0x00, (byte)0x0E, (byte)0x10, (byte)0x00, (byte)0x0E, (byte)0x10, (byte)0x00, (byte)0x00, (byte)0x3C, (byte)0xA1
		};

		applicationCommandHandlerResp = ApplicationCommandHandlerResp.getInstance();
		Assert.assertNotNull(applicationCommandHandlerResp);

		applicationCommandHandlerResp.disassemble(frame);
		Assert.assertEquals(frame.length - 8, applicationCommandHandlerResp.payload.length);

		applicationCommandHandlerData = Command.disassemble(applicationCommandHandlerResp);
		Assert.assertNotNull(applicationCommandHandlerData);

		Assert.assertTrue("Expected WakeUpIntervalCapabilitiesReport class", applicationCommandHandlerData instanceof WakeUpIntervalCapabilitiesReport);
		wakeUpIntervalCapabilitiesReport = (WakeUpIntervalCapabilitiesReport)applicationCommandHandlerData;
		Assert.assertEquals(240, wakeUpIntervalCapabilitiesReport.minimumIntervalSeconds);
		Assert.assertEquals(3600, wakeUpIntervalCapabilitiesReport.maximumIntervalSeconds);
		Assert.assertEquals(3600, wakeUpIntervalCapabilitiesReport.defaultIntervalSeconds);
		Assert.assertEquals(60, wakeUpIntervalCapabilitiesReport.intervalStepSeconds);

		// TODO 0X01 0X0D 0X00 0X13 0X0C 0X06 0X84 0X04 0X00 0X01 0X68 0X01 0X05 0X03 0X05
	}

}
