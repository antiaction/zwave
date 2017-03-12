package com.antiaction.zwave.constants.sigma;

public class ClimateControlScheduleConstants {

	/* Climate Control Schedule command class commands */
	public static final int CLIMATE_CONTROL_SCHEDULE_VERSION = 0x01;
	public static final int SCHEDULE_CHANGED_GET = 0x04;
	public static final int SCHEDULE_CHANGED_REPORT = 0x05;
	public static final int SCHEDULE_GET = 0x02;
	public static final int SCHEDULE_OVERRIDE_GET = 0x07;
	public static final int SCHEDULE_OVERRIDE_REPORT = 0x08;
	public static final int SCHEDULE_OVERRIDE_SET = 0x06;
	public static final int SCHEDULE_REPORT = 0x03;
	public static final int SCHEDULE_SET = 0x01;
	/* Values used for Schedule Get command */
	public static final int SCHEDULE_GET_PROPERTIES1_WEEKDAY_MASK = 0x07;
	public static final int SCHEDULE_GET_PROPERTIES1_RESERVED_MASK = 0xF8;
	public static final int SCHEDULE_GET_PROPERTIES1_RESERVED_SHIFT = 0x03;
	/* Values used for Schedule Override Report command */
	public static final int SCHEDULE_OVERRIDE_REPORT_PROPERTIES1_OVERRIDE_TYPE_MASK = 0x03;
	public static final int SCHEDULE_OVERRIDE_REPORT_PROPERTIES1_RESERVED_MASK = 0xFC;
	public static final int SCHEDULE_OVERRIDE_REPORT_PROPERTIES1_RESERVED_SHIFT = 0x02;
	public static final int SCHEDULE_OVERRIDE_REPORT_NO_OVERRIDE = 0x00;
	public static final int SCHEDULE_OVERRIDE_REPORT_TEMPORARY_OVERRIDE = 0x01;
	public static final int SCHEDULE_OVERRIDE_REPORT_PERMANENT_OVERRIDE = 0x02;
	public static final int SCHEDULE_OVERRIDE_REPORT_RESERVED = 0x03;
	/* Values used for Schedule Override Set command */
	public static final int SCHEDULE_OVERRIDE_SET_PROPERTIES1_OVERRIDE_TYPE_MASK = 0x03;
	public static final int SCHEDULE_OVERRIDE_SET_PROPERTIES1_RESERVED_MASK = 0xFC;
	public static final int SCHEDULE_OVERRIDE_SET_PROPERTIES1_RESERVED_SHIFT = 0x02;
	public static final int SCHEDULE_OVERRIDE_SET_NO_OVERRIDE = 0x00;
	public static final int SCHEDULE_OVERRIDE_SET_TEMPORARY_OVERRIDE = 0x01;
	public static final int SCHEDULE_OVERRIDE_SET_PERMANENT_OVERRIDE = 0x02;
	public static final int SCHEDULE_OVERRIDE_SET_RESERVED = 0x03;
	/* Values used for Schedule Report command */
	public static final int SCHEDULE_REPORT_PROPERTIES1_WEEKDAY_MASK = 0x07;
	public static final int SCHEDULE_REPORT_PROPERTIES1_RESERVED_MASK = 0xF8;
	public static final int SCHEDULE_REPORT_PROPERTIES1_RESERVED_SHIFT = 0x03;
	/* Values used for Schedule Set command */
	public static final int SCHEDULE_SET_PROPERTIES1_WEEKDAY_MASK = 0x07;
	public static final int SCHEDULE_SET_PROPERTIES1_RESERVED_MASK = 0xF8;
	public static final int SCHEDULE_SET_PROPERTIES1_RESERVED_SHIFT = 0x03;

}
