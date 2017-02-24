package com.antiaction.zwave.constants.sigma;

public class BinarySensorCommandConstants {

	/* Sensor Binary command class commands */
	public static final int SENSOR_BINARY_VERSION = 0x01;
	public static final int SENSOR_BINARY_GET = 0x02;
	public static final int SENSOR_BINARY_REPORT = 0x03;
	/* Values used for Sensor Binary Report command */
	public static final int SENSOR_BINARY_REPORT_IDLE = 0x00;
	public static final int SENSOR_BINARY_REPORT_DETECTED_AN_EVENT = 0xFF;
	/* Sensor Binary command class commands */
	public static final int SENSOR_BINARY_VERSION_V2 = 0x02;
	public static final int SENSOR_BINARY_GET_V2 = 0x02;
	public static final int SENSOR_BINARY_REPORT_V2 = 0x03;
	public static final int SENSOR_BINARY_SUPPORTED_GET_SENSOR_V2 = 0x01;
	public static final int SENSOR_BINARY_SUPPORTED_SENSOR_REPORT_V2 = 0x04;
	/* Values used for Sensor Binary Report command */
	public static final int SENSOR_BINARY_REPORT_IDLE_V2 = 0x00;
	public static final int SENSOR_BINARY_REPORT_DETECTED_AN_EVENT_V2 = 0xFF;

}
