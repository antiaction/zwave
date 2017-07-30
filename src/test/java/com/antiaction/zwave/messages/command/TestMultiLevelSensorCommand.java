package com.antiaction.zwave.messages.command;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.antiaction.zwave.constants.SensorType;
import com.antiaction.zwave.constants.SensorType.AirTemperature;
import com.antiaction.zwave.constants.SensorType.Humidity;
import com.antiaction.zwave.constants.SensorType.Luminance;
import com.antiaction.zwave.constants.SensorType.UltraViolet;
import com.antiaction.zwave.messages.ApplicationCommandHandlerData;
import com.antiaction.zwave.messages.ApplicationCommandHandlerResp;
import com.antiaction.zwave.messages.command.MultiLevelSensorCommand.MultiLevelSensorReport;
import com.antiaction.zwave.messages.command.MultiLevelSensorCommand.MultiLevelSensorSupportedSensorReport;

@RunWith(JUnit4.class)
public class TestMultiLevelSensorCommand {

	@Test
	public void test_multilevelsensor_command() {
		byte[] frame;
		ApplicationCommandHandlerResp applicationCommandHandlerResp;
		ApplicationCommandHandlerData applicationCommandHandlerData;

		MultiLevelSensorSupportedSensorReport multiLevelSensorSupportedSensorReport;
		MultiLevelSensorReport multiLevelSensorReport;
		SensorType sensorType;

		frame = new byte[] {
				(byte)0x01, (byte)0x0C, (byte)0x00, (byte)0x04, (byte)0x00, (byte)0x02, (byte)0x06, (byte)0x31,
				(byte)0x02, (byte)0x15, (byte)0x00, (byte)0x00, (byte)0x04, (byte)0xD1
		};

		applicationCommandHandlerResp = ApplicationCommandHandlerResp.getInstance();
		Assert.assertNotNull(applicationCommandHandlerResp);

		applicationCommandHandlerResp.disassemble(frame);
		Assert.assertEquals(frame.length - 8, applicationCommandHandlerResp.payload.length);

		applicationCommandHandlerData = Command.disassemble(applicationCommandHandlerResp);
		Assert.assertNotNull(applicationCommandHandlerData);

		Assert.assertTrue("Expected MultiLevelSensorSupportedSensorReport class", applicationCommandHandlerData instanceof MultiLevelSensorSupportedSensorReport);
		multiLevelSensorSupportedSensorReport = (MultiLevelSensorSupportedSensorReport)applicationCommandHandlerData;
		Map<Integer, SensorType> supportedSensorTypeMap = multiLevelSensorSupportedSensorReport.supportedSensorTypeMap;
		List<SensorType> supportedSensorTypeList = multiLevelSensorSupportedSensorReport.supportedSensorTypeList;
		Assert.assertEquals(4, supportedSensorTypeMap.size());
		Assert.assertEquals(4, supportedSensorTypeList.size());

		sensorType = supportedSensorTypeMap.get(SensorType.AIR_TEMPERATURE.getId());
		Assert.assertEquals(SensorType.AIR_TEMPERATURE.getId(), sensorType.getId());
		Assert.assertTrue(sensorType == supportedSensorTypeList.get(0));

		sensorType = supportedSensorTypeMap.get(SensorType.LUMINANCE.getId());
		Assert.assertEquals(SensorType.LUMINANCE.getId(), sensorType.getId());
		Assert.assertTrue(sensorType == supportedSensorTypeList.get(1));

		sensorType = supportedSensorTypeMap.get(SensorType.HUMIDITY.getId());
		Assert.assertEquals(SensorType.HUMIDITY.getId(), sensorType.getId());
		Assert.assertTrue(sensorType == supportedSensorTypeList.get(2));

		sensorType = supportedSensorTypeMap.get(SensorType.ULTRAVIOLET.getId());
		Assert.assertEquals(SensorType.ULTRAVIOLET.getId(), sensorType.getId());
		Assert.assertTrue(sensorType == supportedSensorTypeList.get(3));

		frame = new byte[] {
				(byte)0x01, (byte)0x0C, (byte)0x00, (byte)0x04, (byte)0x00, (byte)0x02, (byte)0x06, (byte)0x31,
				(byte)0x05, (byte)0x01, (byte)0x22, (byte)0x01, (byte)0x0C, (byte)0xE9
		};

		applicationCommandHandlerResp = ApplicationCommandHandlerResp.getInstance();
		Assert.assertNotNull(applicationCommandHandlerResp);

		applicationCommandHandlerResp.disassemble(frame);
		Assert.assertEquals(frame.length - 8, applicationCommandHandlerResp.payload.length);

		applicationCommandHandlerData = Command.disassemble(applicationCommandHandlerResp);
		Assert.assertNotNull(applicationCommandHandlerData);

		Assert.assertTrue("Expected MultiLevelSensorReport class", applicationCommandHandlerData instanceof MultiLevelSensorReport);
		multiLevelSensorReport = (MultiLevelSensorReport)applicationCommandHandlerData;

		Assert.assertEquals(SensorType.AIR_TEMPERATURE.getId(), multiLevelSensorReport.sensorTypeId);
		Assert.assertTrue(multiLevelSensorReport.sensorType == SensorType.AIR_TEMPERATURE);
		Assert.assertEquals(AirTemperature.CELSIUS.getId(), multiLevelSensorReport.sensorScaleId);
		Assert.assertTrue(multiLevelSensorReport.sensorScale == AirTemperature.CELSIUS);
		Assert.assertTrue("Was: " + multiLevelSensorReport.value, new BigDecimal("26.8").compareTo(multiLevelSensorReport.value) == 0);

		frame = new byte[] {
				(byte)0x01, (byte)0x0C, (byte)0x00, (byte)0x04, (byte)0x00, (byte)0x02, (byte)0x06, (byte)0x31,
				(byte)0x05, (byte)0x03, (byte)0x0A, (byte)0x00, (byte)0x00, (byte)0xCE
		};

		applicationCommandHandlerResp = ApplicationCommandHandlerResp.getInstance();
		Assert.assertNotNull(applicationCommandHandlerResp);

		applicationCommandHandlerResp.disassemble(frame);
		Assert.assertEquals(frame.length - 8, applicationCommandHandlerResp.payload.length);

		applicationCommandHandlerData = Command.disassemble(applicationCommandHandlerResp);
		Assert.assertNotNull(applicationCommandHandlerData);

		Assert.assertTrue("Expected MultiLevelSensorReport class", applicationCommandHandlerData instanceof MultiLevelSensorReport);
		multiLevelSensorReport = (MultiLevelSensorReport)applicationCommandHandlerData;

		Assert.assertEquals(SensorType.LUMINANCE.getId(), multiLevelSensorReport.sensorTypeId);
		Assert.assertTrue(multiLevelSensorReport.sensorType == SensorType.LUMINANCE);
		Assert.assertEquals(Luminance.LUX.getId(), multiLevelSensorReport.sensorScaleId);
		Assert.assertTrue(multiLevelSensorReport.sensorScale == Luminance.LUX);
		Assert.assertTrue("Was: " + multiLevelSensorReport.value, new BigDecimal("0").compareTo(multiLevelSensorReport.value) == 0);

		frame = new byte[] {
				(byte)0x01, (byte)0x0B, (byte)0x00, (byte)0x04, (byte)0x00, (byte)0x02, (byte)0x05, (byte)0x31,
				(byte)0x05, (byte)0x05, (byte)0x01, (byte)0x27, (byte)0xE0
		};

		applicationCommandHandlerResp = ApplicationCommandHandlerResp.getInstance();
		Assert.assertNotNull(applicationCommandHandlerResp);

		applicationCommandHandlerResp.disassemble(frame);
		Assert.assertEquals(frame.length - 8, applicationCommandHandlerResp.payload.length);

		applicationCommandHandlerData = Command.disassemble(applicationCommandHandlerResp);
		Assert.assertNotNull(applicationCommandHandlerData);

		Assert.assertTrue("Expected MultiLevelSensorReport class", applicationCommandHandlerData instanceof MultiLevelSensorReport);
		multiLevelSensorReport = (MultiLevelSensorReport)applicationCommandHandlerData;

		Assert.assertEquals(SensorType.HUMIDITY.getId(), multiLevelSensorReport.sensorTypeId);
		Assert.assertTrue(multiLevelSensorReport.sensorType == SensorType.HUMIDITY);
		Assert.assertEquals(Humidity.PERCENTAGE_VALUE.getId(), multiLevelSensorReport.sensorScaleId);
		Assert.assertTrue(multiLevelSensorReport.sensorScale == Humidity.PERCENTAGE_VALUE);
		Assert.assertTrue("Was: " + multiLevelSensorReport.value, new BigDecimal("39").compareTo(multiLevelSensorReport.value) == 0);

		frame = new byte[] {
				(byte)0x01, (byte)0x0B, (byte)0x00, (byte)0x04, (byte)0x00, (byte)0x02, (byte)0x05, (byte)0x31,
				(byte)0x05, (byte)0x1B, (byte)0x01, (byte)0x00, (byte)0xD9
		};

		applicationCommandHandlerResp = ApplicationCommandHandlerResp.getInstance();
		Assert.assertNotNull(applicationCommandHandlerResp);

		applicationCommandHandlerResp.disassemble(frame);
		Assert.assertEquals(frame.length - 8, applicationCommandHandlerResp.payload.length);

		applicationCommandHandlerData = Command.disassemble(applicationCommandHandlerResp);
		Assert.assertNotNull(applicationCommandHandlerData);

		Assert.assertTrue("Expected MultiLevelSensorReport class", applicationCommandHandlerData instanceof MultiLevelSensorReport);
		multiLevelSensorReport = (MultiLevelSensorReport)applicationCommandHandlerData;

		Assert.assertEquals(SensorType.ULTRAVIOLET.getId(), multiLevelSensorReport.sensorTypeId);
		Assert.assertTrue(multiLevelSensorReport.sensorType == SensorType.ULTRAVIOLET);
		Assert.assertEquals(UltraViolet.UV_INDEX.getId(), multiLevelSensorReport.sensorScaleId);
		Assert.assertTrue(multiLevelSensorReport.sensorScale == UltraViolet.UV_INDEX);
		Assert.assertTrue("Was: " + multiLevelSensorReport.value, new BigDecimal("0").compareTo(multiLevelSensorReport.value) == 0);

		//0x01 0x08 0x00 0x13 0x02 0x02 0x31 0x01 0x05 0xD1
	}

}
