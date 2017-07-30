package com.antiaction.zwave.constants;

/**
 * Basic Device Class identifiers.
 *
 * @author nicl
 */
public abstract class BasicDeviceClass {

	protected int id;

	protected String label;

	protected String description;

	protected BasicDeviceClass(int id, String label, String description) {
		this.id = id;
		this.label = label;
		this.description = description;
	}

	/*
	 * Controller.
	 */

	public static Controller CONTROLLER = new Controller();

	public static class Controller extends BasicDeviceClass {
		public Controller() {
			super(0x01, "Controller", "Node is a portable controller");
		}
	}

	/*
	 * Static Controller
	 */

	public static StaticController STATIC_CONTROLLER = new StaticController();

	public static class StaticController extends BasicDeviceClass {
		public StaticController() {
			super(0x02, "Static Controller", "Node is a static controller");
		}
	}

	/*
	 * Slave.
	 */

	public static Slave SLAVE = new Slave();

	public static class Slave extends BasicDeviceClass {
		public Slave() {
			super(0x03, "Slave", "Node is a slave");
		}
	}

	/*
	 * Routing Slave.
	 */

	public static RoutingSlave ROUTING_SLAVE = new RoutingSlave();

	public static class RoutingSlave extends BasicDeviceClass {
		public RoutingSlave() {
			super(0x04, "Routing Slave", "Node is a slave with routing capabilities");
		}
	}

	static BasicDeviceClass[] basicDeviceClasses = new BasicDeviceClass[256];

	static {
		BasicDeviceClass[] basicDeviceClassesDefined = {
				CONTROLLER,
				STATIC_CONTROLLER,
				SLAVE,
				ROUTING_SLAVE
		};
		BasicDeviceClass basicDeviceClass;
		for (int i=0; i<basicDeviceClassesDefined.length; ++i) {
			basicDeviceClass = basicDeviceClassesDefined[i];
			basicDeviceClasses[basicDeviceClass.id] = basicDeviceClass;
		}
	}

	public static BasicDeviceClass getType(int id) {
		BasicDeviceClass basicDeviceClass = null;
		if (id > 0 && id < 256) {
			basicDeviceClass = basicDeviceClasses[id];
		}
		return basicDeviceClass;
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

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

}
