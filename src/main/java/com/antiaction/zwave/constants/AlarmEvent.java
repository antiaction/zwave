package com.antiaction.zwave.constants;

public class AlarmEvent {

	protected int id;

	protected String description;

	protected AlarmEvent(int id, String description) {
		this.id = id;
		this.description = description;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

}
