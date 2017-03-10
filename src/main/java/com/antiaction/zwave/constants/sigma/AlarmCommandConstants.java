package com.antiaction.zwave.constants.sigma;

public class AlarmCommandConstants {

	/* Alarm command class commands */
	public static final int ALARM_VERSION  = 0x01;
	public static final int ALARM_GET  = 0x04;
	public static final int ALARM_REPORT  = 0x05;
	/* Alarm command class commands */
	public static final int ALARM_VERSION_V2  = 0x02;
	public static final int ALARM_GET_V2  = 0x04;
	public static final int ALARM_REPORT_V2  = 0x05;
	public static final int ALARM_SET_V2  = 0x06;
	public static final int ALARM_TYPE_SUPPORTED_GET_V2  = 0x07;
	public static final int ALARM_TYPE_SUPPORTED_REPORT_V2  = 0x08;
	/* Values used for Alarm Get command */
	public static final int ALARM_GET_RESERVED_V2  = 0x00;
	public static final int ALARM_GET_SMOKE_V2  = 0x01;
	public static final int ALARM_GET_CO_V2  = 0x02;
	public static final int ALARM_GET_CO2_V2  = 0x03;
	public static final int ALARM_GET_HEAT_V2  = 0x04;
	public static final int ALARM_GET_WATER_V2  = 0x05;
	public static final int ALARM_GET_ACCESS_CONTROL_V2  = 0x06;
	public static final int ALARM_GET_BURGLAR_V2  = 0x07;
	public static final int ALARM_GET_POWER_MANAGEMENT_V2  = 0x08;
	public static final int ALARM_GET_SYSTEM_V2  = 0x09;
	public static final int ALARM_GET_EMERGENCY_V2  = 0x0A;
	public static final int ALARM_GET_CLOCK_V2  = 0x0B;
	public static final int ALARM_GET_FIRST_V2  = 0xFF;
	/* Values used for Alarm Report command */
	public static final int ALARM_REPORT_RESERVED_V2  = 0x00;
	public static final int ALARM_REPORT_SMOKE_V2  = 0x01;
	public static final int ALARM_REPORT_CO_V2  = 0x02;
	public static final int ALARM_REPORT_CO2_V2  = 0x03;
	public static final int ALARM_REPORT_HEAT_V2  = 0x04;
	public static final int ALARM_REPORT_WATER_V2  = 0x05;
	public static final int ALARM_REPORT_ACCESS_CONTROL_V2  = 0x06;
	public static final int ALARM_REPORT_BURGLAR_V2  = 0x07;
	public static final int ALARM_REPORT_POWER_MANAGEMENT_V2  = 0x08;
	public static final int ALARM_REPORT_SYSTEM_V2  = 0x09;
	public static final int ALARM_REPORT_EMERGENCY_V2  = 0x0A;
	public static final int ALARM_REPORT_CLOCK_V2  = 0x0B;
	public static final int ALARM_REPORT_FIRST_V2  = 0xFF;
	/* Values used for Alarm Set command */
	public static final int ALARM_SET_RESERVED_V2  = 0x00;
	public static final int ALARM_SET_SMOKE_V2  = 0x01;
	public static final int ALARM_SET_CO_V2  = 0x02;
	public static final int ALARM_SET_CO2_V2  = 0x03;
	public static final int ALARM_SET_HEAT_V2  = 0x04;
	public static final int ALARM_SET_WATER_V2  = 0x05;
	public static final int ALARM_SET_ACCESS_CONTROL_V2  = 0x06;
	public static final int ALARM_SET_BURGLAR_V2  = 0x07;
	public static final int ALARM_SET_POWER_MANAGEMENT_V2  = 0x08;
	public static final int ALARM_SET_SYSTEM_V2  = 0x09;
	public static final int ALARM_SET_EMERGENCY_V2  = 0x0A;
	public static final int ALARM_SET_CLOCK_V2  = 0x0B;
	public static final int ALARM_SET_FIRST_V2  = 0xFF;
	/* Values used for Alarm Type Supported Report command */
	public static final int ALARM_TYPE_SUPPORTED_REPORT_PROPERTIES1_NUMBER_OF_BIT_MASKS_MASK_V2  = 0x1F;
	public static final int ALARM_TYPE_SUPPORTED_REPORT_PROPERTIES1_RESERVED_MASK_V2  = 0x60;
	public static final int ALARM_TYPE_SUPPORTED_REPORT_PROPERTIES1_RESERVED_SHIFT_V2  = 0x05;
	public static final int ALARM_TYPE_SUPPORTED_REPORT_PROPERTIES1_V1_ALARM_BIT_MASK_V2  = 0x80;

}
