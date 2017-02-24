package com.antiaction.zwave.constants.sigma;

public class ManufacturerSpecificCommandConstants {

	/* Manufacturer Specific command class commands */
	public static final int MANUFACTURER_SPECIFIC_VERSION = 0x01;
	public static final int MANUFACTURER_SPECIFIC_GET = 0x04;
	public static final int MANUFACTURER_SPECIFIC_REPORT = 0x05;
	/* Manufacturer Specific command class commands */
	public static final int MANUFACTURER_SPECIFIC_VERSION_V2 = 0x02;
	public static final int MANUFACTURER_SPECIFIC_GET_V2 = 0x04;
	public static final int MANUFACTURER_SPECIFIC_REPORT_V2 = 0x05;
	public static final int DEVICE_SPECIFIC_GET_V2 = 0x06;
	public static final int DEVICE_SPECIFIC_REPORT_V2 = 0x07;
	/* Values used for Device Specific Get command */
	public static final int DEVICE_SPECIFIC_GET_PROPERTIES1_DEVICE_ID_TYPE_MASK_V2 = 0x07;
	public static final int DEVICE_SPECIFIC_GET_DEVICE_ID_TYPE_RESERVED_V2 = 0x00;
	public static final int DEVICE_SPECIFIC_GET_DEVICE_ID_TYPE_SERIAL_NUMBER_V2 = 0x01;
	public static final int DEVICE_SPECIFIC_GET_PROPERTIES1_RESERVED_MASK_V2 = 0xF8;
	public static final int DEVICE_SPECIFIC_GET_PROPERTIES1_RESERVED_SHIFT_V2 = 0x03;
	/* Values used for Device Specific Report command */
	public static final int DEVICE_SPECIFIC_REPORT_PROPERTIES1_DEVICE_ID_TYPE_MASK_V2 = 0x07;
	public static final int DEVICE_SPECIFIC_REPORT_DEVICE_ID_TYPE_RESERVED_V2 = 0x00;
	public static final int DEVICE_SPECIFIC_REPORT_DEVICE_ID_TYPE_SERIAL_NUMBER_V2 = 0x01;
	public static final int DEVICE_SPECIFIC_REPORT_PROPERTIES1_RESERVED_MASK_V2 = 0xF8;
	public static final int DEVICE_SPECIFIC_REPORT_PROPERTIES1_RESERVED_SHIFT_V2 = 0x03;
	public static final int DEVICE_SPECIFIC_REPORT_PROPERTIES2_DEVICE_ID_DATA_LENGTH_INDICATOR_MASK_V2 = 0x1F;
	public static final int DEVICE_SPECIFIC_REPORT_PROPERTIES2_DEVICE_ID_DATA_FORMAT_MASK_V2 = 0xE0;
	public static final int DEVICE_SPECIFIC_REPORT_PROPERTIES2_DEVICE_ID_DATA_FORMAT_SHIFT_V2 = 0x05;
	public static final int DEVICE_SPECIFIC_REPORT_DEVICE_ID_DATA_FORMAT_RESERVED_V2 = 0x00;
	public static final int DEVICE_SPECIFIC_REPORT_DEVICE_ID_DATA_FORMAT_BINARY_V2 = 0x01;

}
