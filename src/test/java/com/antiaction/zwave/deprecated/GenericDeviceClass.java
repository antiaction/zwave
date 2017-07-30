package com.antiaction.zwave.deprecated;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public enum GenericDeviceClass {

	NOT_KNOWN(0, "Not Known"),
	REMOTE_CONTROLLER(1, "Remote Controller"),
	STATIC_CONTOLLER(2, "Static Controller"),
	AV_CONTROL_POINT(3, "A/V Control Point"),
	DISPLAY(4, "Display"),
	THERMOSTAT(8, "Thermostat"),
	WINDOW_COVERING(9, "Window Covering"),
	REPEATER_SLAVE(15, "Repeater Slave"),
	BINARY_SWITCH(16, "Binary Switch"),
	MULTILEVEL_SWITCH(17, "Multi-Level Switch"),
	REMOTE_SWITCH(18, "Remote Switch"),
	TOGGLE_SWITCH( 19, "Toggle Switch"),
	Z_IP_GATEWAY(20, "Z/IP Gateway"),
	Z_IP_NODE(21, "Z/IP Node"),
	VENTILATION(22, "Ventilation"),
	BINARY_SENSOR( 32, "Binary Sensor"),
	MULTILEVEL_SENSOR(33, "Multi-Level Sensor"),
	PULSE_METER(48, "Pulse Meter"),
	METER(49, "Meter"),
	ENTRY_CONTROL(64, "Entry Control"),
	SEMI_INTEROPERABLE( 80, "Semi-Interoperable"),
	ALARM_SENSOR(161, "Alarm Sensor"),
	NON_INTEROPERABLE(255, "Non-Interoperable");

	protected static final Map<Integer, GenericDeviceClass> enumMap = new TreeMap<Integer, GenericDeviceClass>();

	static {
		GenericDeviceClass[] values = GenericDeviceClass.values();
		GenericDeviceClass value;
		for (int i=0; i<values.length; ++i) {
			value = values[i];
			enumMap.put(value.id, value);
		}
	}

	protected final int id;

	protected final String label;

	private GenericDeviceClass(int id, String label) {
		this.id = id;
		this.label = label;
	}

	public static Optional<GenericDeviceClass> getType(int id) {
		return Optional.ofNullable(enumMap.get(id));
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

}
