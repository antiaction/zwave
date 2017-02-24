package com.antiaction.zwave.constants.sigma;

public class ClockCommandConstants {

	/* Clock command class commands */
	public static final int CLOCK_VERSION = 0x01;
	public static final int CLOCK_GET = 0x05;
	public static final int CLOCK_REPORT = 0x06;
	public static final int CLOCK_SET = 0x04;
	/* Values used for Clock Report command */
	public static final int CLOCK_REPORT_LEVEL_HOUR_MASK = 0x1F;
	public static final int CLOCK_REPORT_LEVEL_WEEKDAY_MASK = 0xE0;
	public static final int CLOCK_REPORT_LEVEL_WEEKDAY_SHIFT = 0x05;
	/* Values used for Clock Set command */
	public static final int CLOCK_SET_LEVEL_HOUR_MASK = 0x1F;
	public static final int CLOCK_SET_LEVEL_WEEKDAY_MASK = 0xE0;
	public static final int CLOCK_SET_LEVEL_WEEKDAY_SHIFT = 0x05;

}
