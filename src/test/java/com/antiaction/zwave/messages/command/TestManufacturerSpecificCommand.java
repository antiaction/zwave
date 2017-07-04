package com.antiaction.zwave.messages.command;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.antiaction.zwave.messages.ApplicationCommandHandlerData;
import com.antiaction.zwave.messages.ApplicationCommandHandlerResp;
import com.antiaction.zwave.messages.command.ManufacturerSpecificCommand.ManufacturerSpecificReport;

@RunWith(JUnit4.class)
public class TestManufacturerSpecificCommand {

	@Test
	public void test_manufacturerspecific_command() {
		byte[] frame;
		ApplicationCommandHandlerResp applicationCommandHandlerResp;
		ApplicationCommandHandlerData applicationCommandHandlerData;

		ManufacturerSpecificReport manufacturerSpecificReport;

		frame = new byte[] {
				(byte)0x01, (byte)0x0E, (byte)0x00, (byte)0x04, (byte)0x00, (byte)0x02, (byte)0x08, (byte)0x72,
				(byte)0x05, (byte)0x00, (byte)0x86, (byte)0x00, (byte)0x02, (byte)0x00, (byte)0x64, (byte)0x68
		};

		applicationCommandHandlerResp = ApplicationCommandHandlerResp.getInstance();
		Assert.assertNotNull(applicationCommandHandlerResp);

		applicationCommandHandlerResp.disassemble(frame);
		Assert.assertEquals(frame.length - 8, applicationCommandHandlerResp.payload.length);

		applicationCommandHandlerData = Command.disassemble(applicationCommandHandlerResp);
		Assert.assertNotNull(applicationCommandHandlerData);

		Assert.assertTrue("Expected ManufacturerSpecificReport class", applicationCommandHandlerData instanceof ManufacturerSpecificReport);
		manufacturerSpecificReport = (ManufacturerSpecificReport)applicationCommandHandlerData;
		Assert.assertEquals(0x0086, manufacturerSpecificReport.manufactureId);
		Assert.assertEquals(0x0002, manufacturerSpecificReport.deviceType);
		Assert.assertEquals(0x0064, manufacturerSpecificReport.deviceId);

		// 0x01 0x08 0x00 0x13 0x02 0x02 0x72 0x04 0x05 0x97
		// 0x01 0x04 0x01 0x13 0x01 0xE8
		// 0x01 0x07 0x00 0x13 0x68 0x00 0x00 0x00 0x83
		// 0x01 0x07 0x00 0x13 0x05 0x00 0x00 0x03 0xED
	}

}
