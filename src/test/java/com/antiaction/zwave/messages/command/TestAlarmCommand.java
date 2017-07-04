package com.antiaction.zwave.messages.command;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.antiaction.zwave.constants.AlarmType;
import com.antiaction.zwave.messages.ApplicationCommandHandlerData;
import com.antiaction.zwave.messages.ApplicationCommandHandlerResp;
import com.antiaction.zwave.messages.command.AlarmCommand.AlarmReportV2;

@RunWith(JUnit4.class)
public class TestAlarmCommand {

	@Test
	public void test_alarm_commandclass() {
		byte[] frame;
		ApplicationCommandHandlerResp applicationCommandHandlerResp;
		ApplicationCommandHandlerData applicationCommandHandlerData;

		AlarmReportV2 alarmReportV2;

		frame = new byte[] {
				(byte)0x01, (byte)0x11, (byte)0x00, (byte)0x04, (byte)0x04, (byte)0x02, (byte)0x0B, (byte)0x71,
				(byte)0x05, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0xFF, (byte)0x07, (byte)0x03, (byte)0x00,
				(byte)0x00, (byte)0x00, (byte)0x68
		};

		applicationCommandHandlerResp = ApplicationCommandHandlerResp.getInstance();
		Assert.assertNotNull(applicationCommandHandlerResp);

		applicationCommandHandlerResp.disassemble(frame);
		Assert.assertEquals(frame.length - 8, applicationCommandHandlerResp.payload.length);

		applicationCommandHandlerData = Command.disassemble(applicationCommandHandlerResp);
		Assert.assertNotNull(applicationCommandHandlerData);

		Assert.assertTrue("Expected AlarmReportV2 class", applicationCommandHandlerData instanceof AlarmReportV2);
		alarmReportV2 = (AlarmReportV2)applicationCommandHandlerData;
		Assert.assertEquals(0, alarmReportV2.alarmType);
		Assert.assertEquals(0, alarmReportV2.alarmLevel);
		Assert.assertEquals(0, alarmReportV2.zenzorNetSourceNodeId);
		Assert.assertEquals(255, alarmReportV2.zwaveAlarmStatus);
		Assert.assertEquals(7, alarmReportV2.zwaveAlarmTypeId);
		Assert.assertEquals(AlarmType.BURGLAR_ALARM, alarmReportV2.zwaveAlarmType);
		Assert.assertEquals(3, alarmReportV2.zwaveAlarmEventId);
		Assert.assertEquals(AlarmType.BurglarAlarm.TAMPERING_PRODUCT_COVERING_REMOVED, alarmReportV2.zwaveAlarmEvent);
	}

}
