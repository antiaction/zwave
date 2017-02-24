package com.antiaction.zwave.constants.sigma;

public class ProtectionCommandConstants {

	/* Protection command class commands */
	public static final int PROTECTION_VERSION = 0x01;
	public static final int PROTECTION_GET = 0x02;
	public static final int PROTECTION_REPORT = 0x03;
	public static final int PROTECTION_SET = 0x01;
	/* Values used for Protection Report command */
	public static final int PROTECTION_REPORT_UNPROTECTED = 0x00;
	public static final int PROTECTION_REPORT_PROTECTION_BY_SEQUENCE = 0x01;
	public static final int PROTECTION_REPORT_NO_OPERATION_POSSIBLE = 0x02;
	/* Values used for Protection Set command */
	public static final int PROTECTION_SET_UNPROTECTED = 0x00;
	public static final int PROTECTION_SET_PROTECTION_BY_SEQUENCE = 0x01;
	public static final int PROTECTION_SET_NO_OPERATION_POSSIBLE = 0x02;
	/* Protection command class commands */
	public static final int PROTECTION_VERSION_V2 = 0x02;
	public static final int PROTECTION_EC_GET_V2 = 0x07;
	public static final int PROTECTION_EC_REPORT_V2 = 0x08;
	public static final int PROTECTION_EC_SET_V2 = 0x06;
	public static final int PROTECTION_GET_V2 = 0x02;
	public static final int PROTECTION_REPORT_V2 = 0x03;
	public static final int PROTECTION_SET_V2 = 0x01;
	public static final int PROTECTION_SUPPORTED_GET_V2 = 0x04;
	public static final int PROTECTION_SUPPORTED_REPORT_V2 = 0x05;
	public static final int PROTECTION_TIMEOUT_GET_V2 = 0x0A;
	public static final int PROTECTION_TIMEOUT_REPORT_V2 = 0x0B;
	public static final int PROTECTION_TIMEOUT_SET_V2 = 0x09;
	/* Values used for Protection Report command */
	public static final int PROTECTION_REPORT_LEVEL_LOCAL_PROTECTION_STATE_MASK_V2 = 0x0F;
	public static final int PROTECTION_REPORT_LEVEL_RESERVED1_MASK_V2 = 0xF0;
	public static final int PROTECTION_REPORT_LEVEL_RESERVED1_SHIFT_V2 = 0x04;
	public static final int PROTECTION_REPORT_LEVEL2_RF_PROTECTION_STATE_MASK_V2 = 0x0F;
	public static final int PROTECTION_REPORT_LEVEL2_RESERVED2_MASK_V2 = 0xF0;
	public static final int PROTECTION_REPORT_LEVEL2_RESERVED2_SHIFT_V2 = 0x04;
	/* Values used for Protection Set command */
	public static final int PROTECTION_SET_LEVEL_LOCAL_PROTECTION_STATE_MASK_V2 = 0x0F;
	public static final int PROTECTION_SET_LEVEL_RESERVED1_MASK_V2 = 0xF0;
	public static final int PROTECTION_SET_LEVEL_RESERVED1_SHIFT_V2 = 0x04;
	public static final int PROTECTION_SET_LEVEL2_RF_PROTECTION_STATE_MASK_V2 = 0x0F;
	public static final int PROTECTION_SET_LEVEL2_RESERVED2_MASK_V2 = 0xF0;
	public static final int PROTECTION_SET_LEVEL2_RESERVED2_SHIFT_V2 = 0x04;
	/* Values used for Protection Supported Report command */
	public static final int PROTECTION_SUPPORTED_REPORT_LEVEL_TIMEOUT_BIT_MASK_V2 = 0x01;
	public static final int PROTECTION_SUPPORTED_REPORT_LEVEL_EXCLUSIVE_CONTROL_BIT_MASK_V2 = 0x02;
	public static final int PROTECTION_SUPPORTED_REPORT_LEVEL_RESERVED_MASK_V2 = 0xFC;
	public static final int PROTECTION_SUPPORTED_REPORT_LEVEL_RESERVED_SHIFT_V2 = 0x02;

}
