package com.antiaction.zwave.constants;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public enum BasicDeviceClass {

	NOT_KNOWN(0, "Not Known"),
	CONTROLLER(1, "Controller"),
	STATIC_CONTROLLER(2, "Static Controller"),
	SLAVE(3, "Slave"),
	ROUTING_SLAVE(4, "Routing Slave");

	protected static final Map<Integer, BasicDeviceClass> enumMap = new TreeMap<Integer, BasicDeviceClass>();

	static {
		BasicDeviceClass[] values = BasicDeviceClass.values();
		BasicDeviceClass value;
		for (int i=0; i<values.length; ++i) {
			value = values[i];
			enumMap.put(value.id, value);
		}
	}

	protected int id;
	protected String label;

	private BasicDeviceClass(int id, String label) {
		this.id = id;
		this.label = label;
	}

	public static Optional<BasicDeviceClass> getType(int id) {
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
