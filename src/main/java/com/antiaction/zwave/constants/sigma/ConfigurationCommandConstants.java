package com.antiaction.zwave.constants.sigma;

public class ConfigurationCommandConstants {

	/* Configuration command class commands */
	public static final int CONFIGURATION_VERSION = 0x01;
	public static final int CONFIGURATION_GET = 0x05;
	public static final int CONFIGURATION_REPORT = 0x06;
	public static final int CONFIGURATION_SET = 0x04;
	/* Values used for Configuration Report command */
	public static final int CONFIGURATION_REPORT_LEVEL_SIZE_MASK = 0x07;
	public static final int CONFIGURATION_REPORT_LEVEL_RESERVED_MASK = 0xF8;
	public static final int CONFIGURATION_REPORT_LEVEL_RESERVED_SHIFT = 0x03;
	/* Values used for Configuration Set command */
	public static final int CONFIGURATION_SET_LEVEL_SIZE_MASK = 0x07;
	public static final int CONFIGURATION_SET_LEVEL_RESERVED_MASK = 0x78;
	public static final int CONFIGURATION_SET_LEVEL_RESERVED_SHIFT = 0x03;
	public static final int CONFIGURATION_SET_LEVEL_DEFAULT_BIT_MASK = 0x80;
	/* Configuration command class commands */
	public static final int CONFIGURATION_VERSION_V2 = 0x02;
	public static final int CONFIGURATION_BULK_GET_V2 = 0x08;
	public static final int CONFIGURATION_BULK_REPORT_V2 = 0x09;
	public static final int CONFIGURATION_BULK_SET_V2 = 0x07;
	public static final int CONFIGURATION_GET_V2 = 0x05;
	public static final int CONFIGURATION_REPORT_V2 = 0x06;
	public static final int CONFIGURATION_SET_V2 = 0x04;
	/* Values used for Configuration Bulk Report command */
	public static final int CONFIGURATION_BULK_REPORT_PROPERTIES1_SIZE_MASK_V2 = 0x07;
	public static final int CONFIGURATION_BULK_REPORT_PROPERTIES1_RESERVED_MASK_V2 = 0x38;
	public static final int CONFIGURATION_BULK_REPORT_PROPERTIES1_RESERVED_SHIFT_V2 = 0x03;
	public static final int CONFIGURATION_BULK_REPORT_PROPERTIES1_HANDSHAKE_BIT_MASK_V2 = 0x40;
	public static final int CONFIGURATION_BULK_REPORT_PROPERTIES1_DEFAULT_BIT_MASK_V2 = 0x80;
	/* Values used for Configuration Bulk Set command */
	public static final int CONFIGURATION_BULK_SET_PROPERTIES1_SIZE_MASK_V2 = 0x07;
	public static final int CONFIGURATION_BULK_SET_PROPERTIES1_RESERVED_MASK_V2 = 0x38;
	public static final int CONFIGURATION_BULK_SET_PROPERTIES1_RESERVED_SHIFT_V2 = 0x03;
	public static final int CONFIGURATION_BULK_SET_PROPERTIES1_HANDSHAKE_BIT_MASK_V2 = 0x40;
	public static final int CONFIGURATION_BULK_SET_PROPERTIES1_DEFAULT_BIT_MASK_V2 = 0x80;
	/* Values used for Configuration Report command */
	public static final int CONFIGURATION_REPORT_LEVEL_SIZE_MASK_V2 = 0x07;
	public static final int CONFIGURATION_REPORT_LEVEL_RESERVED_MASK_V2 = 0xF8;
	public static final int CONFIGURATION_REPORT_LEVEL_RESERVED_SHIFT_V2 = 0x03;
	/* Values used for Configuration Set command */
	public static final int CONFIGURATION_SET_LEVEL_SIZE_MASK_V2 = 0x07;
	public static final int CONFIGURATION_SET_LEVEL_RESERVED_MASK_V2 = 0x78;
	public static final int CONFIGURATION_SET_LEVEL_RESERVED_SHIFT_V2 = 0x03;
	public static final int CONFIGURATION_SET_LEVEL_DEFAULT_BIT_MASK_V2 = 0x80;
	/* Configuration command class commands */
	public static final int CONFIGURATION_VERSION_V3 = 0x03;
	public static final int CONFIGURATION_BULK_GET_V3 = 0x08;
	public static final int CONFIGURATION_BULK_REPORT_V3 = 0x09;
	public static final int CONFIGURATION_BULK_SET_V3 = 0x07;
	public static final int CONFIGURATION_GET_V3 = 0x05;
	public static final int CONFIGURATION_REPORT_V3 = 0x06;
	public static final int CONFIGURATION_SET_V3 = 0x04;
	public static final int CONFIGURATION_NAME_GET_V3 = 0x0A;
	public static final int CONFIGURATION_NAME_REPORT_V3 = 0x0B;
	public static final int CONFIGURATION_INFO_GET_V3 = 0x0C;
	public static final int CONFIGURATION_INFO_REPORT_V3 = 0x0D;
	public static final int CONFIGURATION_PROPERTIES_GET_V3 = 0x0E;
	public static final int CONFIGURATION_PROPERTIES_REPORT_V3 = 0x0F;
	/* Values used for Configuration Bulk Report command */
	public static final int CONFIGURATION_BULK_REPORT_PROPERTIES1_SIZE_MASK_V3 = 0x07;
	public static final int CONFIGURATION_BULK_REPORT_PROPERTIES1_RESERVED_MASK_V3 = 0x38;
	public static final int CONFIGURATION_BULK_REPORT_PROPERTIES1_RESERVED_SHIFT_V3 = 0x03;
	public static final int CONFIGURATION_BULK_REPORT_PROPERTIES1_HANDSHAKE_BIT_MASK_V3 = 0x40;
	public static final int CONFIGURATION_BULK_REPORT_PROPERTIES1_DEFAULT_BIT_MASK_V3 = 0x80;
	/* Values used for Configuration Bulk Set command */
	public static final int CONFIGURATION_BULK_SET_PROPERTIES1_SIZE_MASK_V3 = 0x07;
	public static final int CONFIGURATION_BULK_SET_PROPERTIES1_RESERVED_MASK_V3 = 0x38;
	public static final int CONFIGURATION_BULK_SET_PROPERTIES1_RESERVED_SHIFT_V3 = 0x03;
	public static final int CONFIGURATION_BULK_SET_PROPERTIES1_HANDSHAKE_BIT_MASK_V3 = 0x40;
	public static final int CONFIGURATION_BULK_SET_PROPERTIES1_DEFAULT_BIT_MASK_V3 = 0x80;
	/* Values used for Configuration Report command */
	public static final int CONFIGURATION_REPORT_LEVEL_SIZE_MASK_V3 = 0x07;
	public static final int CONFIGURATION_REPORT_LEVEL_RESERVED_MASK_V3 = 0xF8;
	public static final int CONFIGURATION_REPORT_LEVEL_RESERVED_SHIFT_V3 = 0x03;
	/* Values used for Configuration Set command */
	public static final int CONFIGURATION_SET_LEVEL_SIZE_MASK_V3 = 0x07;
	public static final int CONFIGURATION_SET_LEVEL_RESERVED_MASK_V3 = 0x78;
	public static final int CONFIGURATION_SET_LEVEL_RESERVED_SHIFT_V3 = 0x03;
	public static final int CONFIGURATION_SET_LEVEL_DEFAULT_BIT_MASK_V3 = 0x80;
	/* Values used for Configuration Properties Report command */
	public static final int CONFIGURATION_PROPERTIES_REPORT_PROPERTIES1_SIZE_MASK_V3 = 0x07;
	public static final int CONFIGURATION_PROPERTIES_REPORT_PROPERTIES1_FORMAT_MASK_V3 = 0x38;
	public static final int CONFIGURATION_PROPERTIES_REPORT_PROPERTIES1_FORMAT_SHIFT_V3 = 0x03;
	public static final int CONFIGURATION_PROPERTIES_REPORT_FORMAT_SIGNED_INTEGER_V3 = 0x00;
	public static final int CONFIGURATION_PROPERTIES_REPORT_FORMAT_UNSIGNED_INTEGER_V3 = 0x01;
	public static final int CONFIGURATION_PROPERTIES_REPORT_FORMAT_ENUMERATED_V3 = 0x02;
	public static final int CONFIGURATION_PROPERTIES_REPORT_FORMAT_BIT_FIELD_V3 = 0x03;
	public static final int CONFIGURATION_PROPERTIES_REPORT_PROPERTIES1_RESERVED_MASK_V3 = 0xC0;
	public static final int CONFIGURATION_PROPERTIES_REPORT_PROPERTIES1_RESERVED_SHIFT_V3 = 0x06;
	/* Configuration command class commands */
	public static final int CONFIGURATION_VERSION_V4 = 0x04;
	public static final int CONFIGURATION_BULK_GET_V4 = 0x08;
	public static final int CONFIGURATION_BULK_REPORT_V4 = 0x09;
	public static final int CONFIGURATION_BULK_SET_V4 = 0x07;
	public static final int CONFIGURATION_GET_V4 = 0x05;
	public static final int CONFIGURATION_REPORT_V4 = 0x06;
	public static final int CONFIGURATION_SET_V4 = 0x04;
	public static final int CONFIGURATION_NAME_GET_V4 = 0x0A;
	public static final int CONFIGURATION_NAME_REPORT_V4 = 0x0B;
	public static final int CONFIGURATION_INFO_GET_V4 = 0x0C;
	public static final int CONFIGURATION_INFO_REPORT_V4 = 0x0D;
	public static final int CONFIGURATION_PROPERTIES_GET_V4 = 0x0E;
	public static final int CONFIGURATION_PROPERTIES_REPORT_V4 = 0x0F;
	public static final int CONFIGURATION_DEFAULT_RESET_V4 = 0x01;
	/* Values used for Configuration Bulk Report command */
	public static final int CONFIGURATION_BULK_REPORT_PROPERTIES1_SIZE_MASK_V4 = 0x07;
	public static final int CONFIGURATION_BULK_REPORT_PROPERTIES1_RESERVED_MASK_V4 = 0x38;
	public static final int CONFIGURATION_BULK_REPORT_PROPERTIES1_RESERVED_SHIFT_V4 = 0x03;
	public static final int CONFIGURATION_BULK_REPORT_PROPERTIES1_HANDSHAKE_BIT_MASK_V4 = 0x40;
	public static final int CONFIGURATION_BULK_REPORT_PROPERTIES1_DEFAULT_BIT_MASK_V4 = 0x80;
	/* Values used for Configuration Bulk Set command */
	public static final int CONFIGURATION_BULK_SET_PROPERTIES1_SIZE_MASK_V4 = 0x07;
	public static final int CONFIGURATION_BULK_SET_PROPERTIES1_RESERVED_MASK_V4 = 0x38;
	public static final int CONFIGURATION_BULK_SET_PROPERTIES1_RESERVED_SHIFT_V4 = 0x03;
	public static final int CONFIGURATION_BULK_SET_PROPERTIES1_HANDSHAKE_BIT_MASK_V4 = 0x40;
	public static final int CONFIGURATION_BULK_SET_PROPERTIES1_DEFAULT_BIT_MASK_V4 = 0x80;
	/* Values used for Configuration Report command */
	public static final int CONFIGURATION_REPORT_LEVEL_SIZE_MASK_V4 = 0x07;
	public static final int CONFIGURATION_REPORT_LEVEL_RESERVED_MASK_V4 = 0xF8;
	public static final int CONFIGURATION_REPORT_LEVEL_RESERVED_SHIFT_V4 = 0x03;
	/* Values used for Configuration Set command */
	public static final int CONFIGURATION_SET_LEVEL_SIZE_MASK_V4 = 0x07;
	public static final int CONFIGURATION_SET_LEVEL_RESERVED_MASK_V4 = 0x78;
	public static final int CONFIGURATION_SET_LEVEL_RESERVED_SHIFT_V4 = 0x03;
	public static final int CONFIGURATION_SET_LEVEL_DEFAULT_BIT_MASK_V4 = 0x80;
	/* Values used for Configuration Properties Report command */
	public static final int CONFIGURATION_PROPERTIES_REPORT_PROPERTIES1_SIZE_MASK_V4 = 0x07;
	public static final int CONFIGURATION_PROPERTIES_REPORT_PROPERTIES1_FORMAT_MASK_V4 = 0x38;
	public static final int CONFIGURATION_PROPERTIES_REPORT_PROPERTIES1_FORMAT_SHIFT_V4 = 0x03;
	public static final int CONFIGURATION_PROPERTIES_REPORT_FORMAT_SIGNED_INTEGER_V4 = 0x00;
	public static final int CONFIGURATION_PROPERTIES_REPORT_FORMAT_UNSIGNED_INTEGER_V4 = 0x01;
	public static final int CONFIGURATION_PROPERTIES_REPORT_FORMAT_ENUMERATED_V4 = 0x02;
	public static final int CONFIGURATION_PROPERTIES_REPORT_FORMAT_BIT_FIELD_V4 = 0x03;
	public static final int CONFIGURATION_PROPERTIES_REPORT_PROPERTIES1_READONLY_BIT_MASK_V4 = 0x40;
	public static final int CONFIGURATION_PROPERTIES_REPORT_PROPERTIES1_RE_INCLUSION_REQUIRED_BIT_MASK_V4 = 0x80;
	public static final int CONFIGURATION_PROPERTIES_REPORT_PROPERTIES2_ADVANCED_BIT_MASK_V4 = 0x01;
	public static final int CONFIGURATION_PROPERTIES_REPORT_PROPERTIES2_NO_BULK_SUPPORT_BIT_MASK_V4 = 0x02;
	public static final int CONFIGURATION_PROPERTIES_REPORT_PROPERTIES2_RESERVED1_MASK_V4 = 0xFC;
	public static final int CONFIGURATION_PROPERTIES_REPORT_PROPERTIES2_RESERVED1_SHIFT_V4 = 0x02;

}
