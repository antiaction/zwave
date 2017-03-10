package com.antiaction.zwave.constants;

public abstract class SensorScale {

	protected int id;

	protected String unit;

	protected String description;

	protected int version;

	protected SensorScale(int id, String unit, String description, int version) {
		this.id = id;
		this.unit = unit;
		this.description = description;
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
	public String getUnit() {
		return unit;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the version
	 */
	public int getVersion() {
		return version;
	}

}
