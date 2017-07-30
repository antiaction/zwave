package com.antiaction.zwave.constants;

public abstract class SpecificDeviceClass {

	protected int id;

	protected String label;

	protected SpecificDeviceClass(int id, String label) {
		this.id = id;
		this.label = label;
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
