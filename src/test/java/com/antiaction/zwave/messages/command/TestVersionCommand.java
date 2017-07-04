package com.antiaction.zwave.messages.command;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.antiaction.zwave.messages.ApplicationCommandHandlerData;
import com.antiaction.zwave.messages.ApplicationCommandHandlerResp;
import com.antiaction.zwave.messages.command.VersionCommand.VersionReportV1;
import com.antiaction.zwave.messages.command.VersionCommand.VersionReportV2;

@RunWith(JUnit4.class)
public class TestVersionCommand {

	@Test
	public void test_version_command() {
		byte[] frame;
		ApplicationCommandHandlerResp applicationCommandHandlerResp;
		ApplicationCommandHandlerData applicationCommandHandlerData;

		VersionReportV1 versionReportV1;
		VersionReportV2 versionReportV2;

		frame = new byte[] {
				(byte)0x01, (byte)0x0D, (byte)0x00, (byte)0x04, (byte)0x00, (byte)0x04, (byte)0x07, (byte)0x86,
				(byte)0x12, (byte)0x03, (byte)0x03, (byte)0x34, (byte)0x19, (byte)0x19, (byte)0x55
		};

		applicationCommandHandlerResp = ApplicationCommandHandlerResp.getInstance();
		Assert.assertNotNull(applicationCommandHandlerResp);

		applicationCommandHandlerResp.disassemble(frame);
		Assert.assertEquals(frame.length - 8, applicationCommandHandlerResp.payload.length);

		applicationCommandHandlerData = Command.disassemble(applicationCommandHandlerResp);
		Assert.assertNotNull(applicationCommandHandlerData);

		Assert.assertTrue("Expected VersionReportV1 class", applicationCommandHandlerData instanceof VersionReportV1);
		versionReportV1 = (VersionReportV1)applicationCommandHandlerData;
		Assert.assertEquals(3, versionReportV1.libraryType);
		Assert.assertEquals(3, versionReportV1.protocolVersion.version);
		Assert.assertEquals(52, versionReportV1.protocolVersion.subVersion);
		Assert.assertEquals(25, versionReportV1.applicationVersion.version);
		Assert.assertEquals(25, versionReportV1.applicationVersion.subVersion);

		frame = new byte[] {
				(byte)0x01, (byte)0x0D, (byte)0x00, (byte)0x04, (byte)0x00, (byte)0x03, (byte)0x07, (byte)0x86,
				(byte)0x12, (byte)0x06, (byte)0x03, (byte)0x43, (byte)0x01, (byte)0x01, (byte)0x20
		};

		applicationCommandHandlerResp = ApplicationCommandHandlerResp.getInstance();
		Assert.assertNotNull(applicationCommandHandlerResp);

		applicationCommandHandlerResp.disassemble(frame);
		Assert.assertEquals(frame.length - 8, applicationCommandHandlerResp.payload.length);

		applicationCommandHandlerData = Command.disassemble(applicationCommandHandlerResp);
		Assert.assertNotNull(applicationCommandHandlerData);

		Assert.assertTrue("Expected VersionReportV1 class", applicationCommandHandlerData instanceof VersionReportV1);
		versionReportV1 = (VersionReportV1)applicationCommandHandlerData;
		Assert.assertEquals(6, versionReportV1.libraryType);
		Assert.assertEquals(3, versionReportV1.protocolVersion.version);
		Assert.assertEquals(67, versionReportV1.protocolVersion.subVersion);
		Assert.assertEquals(1, versionReportV1.applicationVersion.version);
		Assert.assertEquals(1, versionReportV1.applicationVersion.subVersion);

		frame = new byte[] {
				(byte)0x01, (byte)0x0F, (byte)0x00, (byte)0x04, (byte)0x00, (byte)0x02, (byte)0x09, (byte)0x86,
				(byte)0x12, (byte)0x03, (byte)0x04, (byte)0x05, (byte)0x01, (byte)0x07, (byte)0x64, (byte)0x00, (byte)0x0B
		};

		applicationCommandHandlerResp = ApplicationCommandHandlerResp.getInstance();
		Assert.assertNotNull(applicationCommandHandlerResp);

		applicationCommandHandlerResp.disassemble(frame);
		Assert.assertEquals(frame.length - 8, applicationCommandHandlerResp.payload.length);

		applicationCommandHandlerData = Command.disassemble(applicationCommandHandlerResp);
		Assert.assertNotNull(applicationCommandHandlerData);

		Assert.assertTrue("Expected VersionReportV2 class", applicationCommandHandlerData instanceof VersionReportV2);
		versionReportV2 = (VersionReportV2)applicationCommandHandlerData;
		Assert.assertEquals(3, versionReportV2.libraryType);
		Assert.assertEquals(4, versionReportV2.protocolVersion.version);
		Assert.assertEquals(5, versionReportV2.protocolVersion.subVersion);
		Assert.assertEquals(1, versionReportV2.firmwareVersions.get(0).version);
		Assert.assertEquals(7, versionReportV2.firmwareVersions.get(0).subVersion);
		Assert.assertEquals(100, versionReportV2.hardwareVersion);
		Assert.assertEquals(1, versionReportV2.firmwareVersions.size());
	}
}
