package com.antiaction.zwave.constants.sigma;

public class BinarySwitchCommandConstants {

	/* Switch Binary command class commands */
	public static final int SWITCH_BINARY_VERSION = 0x01;
	public static final int SWITCH_BINARY_GET = 0x02;
	public static final int SWITCH_BINARY_REPORT = 0x03;
	public static final int SWITCH_BINARY_SET = 0x01;
	/* Switch Binary command class commands */
	public static final int SWITCH_BINARY_VERSION_V2 = 0x02;
	public static final int SWITCH_BINARY_GET_V2 = 0x02;
	public static final int SWITCH_BINARY_REPORT_V2 = 0x03;
	public static final int SWITCH_BINARY_SET_V2 = 0x01;
	/* Values used for Switch Binary Report command */
	public static final int SWITCH_BINARY_REPORT_OFF_DISABLE_V2 = 0x00;
	public static final int SWITCH_BINARY_REPORT_ON_ENABLE_V2 = 0xFF;
	public static final int SWITCH_BINARY_REPORT_ALREADY_AT_THE_TARGET_VALUE_V2 = 0x00;
	public static final int SWITCH_BINARY_REPORT_UNKNOWN_DURATION_V2 = 0xFE;
	public static final int SWITCH_BINARY_REPORT_RESERVED_V2 = 0xFF;
	/* Values used for Switch Binary Set command */
	public static final int SWITCH_BINARY_SET_OFF_DISABLE_V2 = 0x00;
	public static final int SWITCH_BINARY_SET_ON_ENABLE_V2 = 0xFF;
	public static final int SWITCH_BINARY_SET_INSTANTLY_V2 = 0x00;
	public static final int SWITCH_BINARY_SET_DEFAULT_V2 = 0xFF;

}
