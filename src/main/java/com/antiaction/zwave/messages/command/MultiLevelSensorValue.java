package com.antiaction.zwave.messages.command;

import java.math.BigDecimal;

public class MultiLevelSensorValue {

	public static final int SENSOR_MULTILEVEL_SIZE_MASK = 0x07;
	public static final int SENSOR_MULTILEVEL_SCALE_MASK = 0x18;
	public static final int SENSOR_MULTILEVEL_SCALE_SHIFT = 0x03;
	public static final int SENSOR_MULTILEVEL_PRECISION_MASK = 0xE0;
	public static final int SENSOR_MULTILEVEL_PRECISION_SHIFT = 0x05;

	/**
	 * Extract a decimal value from a byte array.
	 * @param buffer the buffer to be parsed.
	 * @param offset the offset at which to start reading
	 * @return the extracted decimal value
	 */
	public static BigDecimal extractValue(byte[] buffer, int offset) {
		int size = buffer[offset] & SENSOR_MULTILEVEL_SIZE_MASK;
		int precision = (buffer[offset] & SENSOR_MULTILEVEL_PRECISION_MASK) >> SENSOR_MULTILEVEL_PRECISION_SHIFT;

		if ((size + offset) >= buffer.length) {
			throw new NumberFormatException();
		}

		int value = 0;
		int i;
		for (i = 0; i < size; ++i) {
			value <<= 8;
			value |= buffer[offset + i + 1] & 0xFF;
		}

		// Deal with sign extension. All values are signed
		BigDecimal result;
		if ((buffer[offset + 1] & 0x80) == 0x80) {
			// MSB is signed
			if (size == 1) {
				value |= 0xffffff00;
			}
			else if (size == 2) {
				value |= 0xffff0000;
			}
		}

		result = BigDecimal.valueOf(value);

		BigDecimal divisor = BigDecimal.valueOf(Math.pow(10, precision));
		return result.divide(divisor);
	}

}
