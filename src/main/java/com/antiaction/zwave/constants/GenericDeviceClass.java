package com.antiaction.zwave.constants;

/**
 * Generic and Specific Device Class identifiers.
*
 * @author nicl
 */
public abstract class GenericDeviceClass {

	protected int id;

	protected String label;

	protected SpecificDeviceClass[] specificDeviceClasses;

	protected GenericDeviceClass(int id, String label, SpecificDeviceClass ... specificDeviceClasses) {
		this.id = id;
		this.label = label;
		this.specificDeviceClasses = specificDeviceClasses;
	}

	public SpecificDeviceClass getSpecificDeviceClass(int id) {
		SpecificDeviceClass specificDeviceClass = null;
		if (specificDeviceClasses != null) {
			int idx = 0;
			while (specificDeviceClass == null && idx <specificDeviceClasses.length) {
				if (specificDeviceClasses[idx].id == id) {
					specificDeviceClass = specificDeviceClasses[idx];
				}
				++idx;
			}
		}
		return specificDeviceClass;
	}

	/*
	 * Device class Av Control Point.
	 */

	public static GenericType_AVControlPoint GENERIC_TYPE_AV_CONTROL_POINT = new GenericType_AVControlPoint();

	public static class GenericType_AVControlPoint extends GenericDeviceClass {
		public GenericType_AVControlPoint() {
			super(0x03, "AV Control Point", SPECIFIC_TYPE_NOT_USED, SPECIFIC_TYPE_SATELLITE_RECEIVER, SPECIFIC_TYPE_SATELLITE_RECEIVER_V2, SPECIFIC_TYPE_DOORBELL);
		}
		public static SpecificDeviceClass SPECIFIC_TYPE_NOT_USED = new SpecificType_NotUsed();
		public static SpecificDeviceClass SPECIFIC_TYPE_SATELLITE_RECEIVER = new SpecificType_SatelliteReceiver();
		public static SpecificDeviceClass SPECIFIC_TYPE_SATELLITE_RECEIVER_V2 = new SpecificType_SatelliteReceiverV2();
		public static SpecificDeviceClass SPECIFIC_TYPE_DOORBELL = new SpecificType_Doorbell();
		public static class SpecificType_NotUsed extends SpecificDeviceClass {
			public SpecificType_NotUsed() {
				super(0x00, "Specific Device Class Not Used");
			}
		}
		public static class SpecificType_SatelliteReceiver extends SpecificDeviceClass {
			public SpecificType_SatelliteReceiver() {
				super(0x04, "Satellite Receiver");
			}
		}
		public static class SpecificType_SatelliteReceiverV2 extends SpecificDeviceClass {
			public SpecificType_SatelliteReceiverV2() {
				super(0x11, "Satellite Receiver V2");
			}
		}
		public static class SpecificType_Doorbell extends SpecificDeviceClass {
			public SpecificType_Doorbell() {
				super(0x12, "Zoned Security Panel");
			}
		}
	}

	/*
	 * Device class Display.
	 */

	public static GenericType_Display GENERIC_TYPE_DISPLAY = new GenericType_Display();

	public static class GenericType_Display extends GenericDeviceClass {
		public GenericType_Display() {
			super(0x04, "Display", SPECIFIC_TYPE_NOT_USED, SPECIFIC_TYPE_SIMPLE_DISPLAY);
		}
		public static SpecificDeviceClass SPECIFIC_TYPE_NOT_USED = new SpecificType_NotUsed();
		public static SpecificDeviceClass SPECIFIC_TYPE_SIMPLE_DISPLAY = new SpecificType_SimpleDisplay();
		public static class SpecificType_NotUsed extends SpecificDeviceClass {
			public SpecificType_NotUsed() {
				super(0x00, "Specific Device Class Not Used");
			}
		}
		public static class SpecificType_SimpleDisplay extends SpecificDeviceClass {
			public SpecificType_SimpleDisplay() {
				super(0x01, "Display (simple) Device Type");
			}
		}
	}

	/*
	 * Device class Entry Control.
	 */

	public static GenericType_EntryControl GENERIC_TYPE_ENTRY_CONTROL = new GenericType_EntryControl();

	public static class GenericType_EntryControl extends GenericDeviceClass {
		public GenericType_EntryControl() {
			super(0x40, "Entry Control", SPECIFIC_TYPE_NOT_USED, SPECIFIC_TYPE_DOOR_LOCK, SPECIFIC_TYPE_ADVANCED_DOOR_LOCK, SPECIFIC_TYPE_SECURE_KEYPAD_DOOR_LOCK, SPECIFIC_TYPE_SECURE_KEYPAD_DOOR_LOCK_DEADBOLT, SPECIFIC_TYPE_SECURE_DOOR, SPECIFIC_TYPE_SECURE_GATE, SPECIFIC_TYPE_SECURE_BARRIER_ADDON, SPECIFIC_TYPE_SECURE_BARRIER_OPEN_ONLY, SPECIFIC_TYPE_SECURE_BARRIER_CLOSE_ONLY, SPECIFIC_TYPE_SECURE_LOCKBOX, SPECIFIC_TYPE_SECURE_KEYPAD);
		}
		public static SpecificDeviceClass SPECIFIC_TYPE_NOT_USED = new SpecificType_NotUsed();
		public static SpecificDeviceClass SPECIFIC_TYPE_DOOR_LOCK = new SpecificType_DoorLock();
		public static SpecificDeviceClass SPECIFIC_TYPE_ADVANCED_DOOR_LOCK = new SpecificType_AdvancedDoorLock();
		public static SpecificDeviceClass SPECIFIC_TYPE_SECURE_KEYPAD_DOOR_LOCK = new SpecificType_SecureKeypadDoorLock();
		public static SpecificDeviceClass SPECIFIC_TYPE_SECURE_KEYPAD_DOOR_LOCK_DEADBOLT = new SpecificType_SecureKeypadDoorLockDeadbolt();
		public static SpecificDeviceClass SPECIFIC_TYPE_SECURE_DOOR = new SpecificType_SecureDoor();
		public static SpecificDeviceClass SPECIFIC_TYPE_SECURE_GATE = new SpecificType_SecureGate();
		public static SpecificDeviceClass SPECIFIC_TYPE_SECURE_BARRIER_ADDON = new SpecificType_SecureBarrierAddon();
		public static SpecificDeviceClass SPECIFIC_TYPE_SECURE_BARRIER_OPEN_ONLY = new SpecificType_SecureBarrierOpenOnly();
		public static SpecificDeviceClass SPECIFIC_TYPE_SECURE_BARRIER_CLOSE_ONLY = new SpecificType_SecureBarrierCloseOnly();
		public static SpecificDeviceClass SPECIFIC_TYPE_SECURE_LOCKBOX = new SpecificType_SecureLockBox();
		public static SpecificDeviceClass SPECIFIC_TYPE_SECURE_KEYPAD = new SpecificType_SecureKeypad();
		public static class SpecificType_NotUsed extends SpecificDeviceClass {
			public SpecificType_NotUsed() {
				super(0x00, "Specific Device Class Not Used");
			}
		}
		public static class SpecificType_DoorLock extends SpecificDeviceClass {
			public SpecificType_DoorLock() {
				super(0x01, "Door Lock");
			}
		}
		public static class SpecificType_AdvancedDoorLock extends SpecificDeviceClass {
			public SpecificType_AdvancedDoorLock() {
				super(0x02, "Advanced Door Lock");
			}
		}
		public static class SpecificType_SecureKeypadDoorLock extends SpecificDeviceClass {
			public SpecificType_SecureKeypadDoorLock() {
				super(0x03, "Door Lock (keypad –lever) Device Type");
			}
		}
		public static class SpecificType_SecureKeypadDoorLockDeadbolt extends SpecificDeviceClass {
			public SpecificType_SecureKeypadDoorLockDeadbolt() {
				super(0x04, "Door Lock (keypad – deadbolt) Device Type");
			}
		}
		public static class SpecificType_SecureDoor extends SpecificDeviceClass {
			public SpecificType_SecureDoor() {
				super(0x05, "Barrier Operator Specific Device Class");
			}
		}
		public static class SpecificType_SecureGate extends SpecificDeviceClass {
			public SpecificType_SecureGate() {
				super(0x06, "Barrier Operator Specific Device Class");
			}
		}
		public static class SpecificType_SecureBarrierAddon extends SpecificDeviceClass {
			public SpecificType_SecureBarrierAddon() {
				super(0x07, "Barrier Operator Specific Device Class");
			}
		}
		public static class SpecificType_SecureBarrierOpenOnly extends SpecificDeviceClass {
			public SpecificType_SecureBarrierOpenOnly() {
				super(0x08, "Barrier Operator Specific Device Class");
			}
		}
		public static class SpecificType_SecureBarrierCloseOnly extends SpecificDeviceClass {
			public SpecificType_SecureBarrierCloseOnly() {
				super(0x09, "Barrier Operator Specific Device Class");
			}
		}
		public static class SpecificType_SecureLockBox extends SpecificDeviceClass {
			public SpecificType_SecureLockBox() {
				super(0x0A, "SDS12724");
			}
		}
		public static class SpecificType_SecureKeypad extends SpecificDeviceClass {
			public SpecificType_SecureKeypad() {
				super(0x0B, "Secure Keypad");
			}
		}
	}

	/*
	 * Device class Generic Controller.
	 */

	public static GenericType_RemoteController GENERIC_TYPE_GENERIC_CONTROLLER = new GenericType_RemoteController();

	public static class GenericType_RemoteController extends GenericDeviceClass {
		public GenericType_RemoteController() {
			super(0x01, "Security Panel", SPECIFIC_TYPE_NOT_USED, SPECIFIC_TYPE_PORTABLE_REMOTE_CONTROLLER, SPECIFIC_TYPE_PORTABLE_SCENE_CONTROLLER, SPECIFIC_TYPE_PORTABLE_INSTALLER_TOOL, SPECIFIC_TYPE_REMOTE_CONTROL_AV, SPECIFIC_TYPE_REMOTE_CONTROL_SIMPLE);
		}
		public static SpecificDeviceClass SPECIFIC_TYPE_NOT_USED = new SpecificType_NotUsed();
		public static SpecificDeviceClass SPECIFIC_TYPE_PORTABLE_REMOTE_CONTROLLER = new SpecificType_PortableRemoteController();
		public static SpecificDeviceClass SPECIFIC_TYPE_PORTABLE_SCENE_CONTROLLER = new SpecificType_PortableSceneController();
		public static SpecificDeviceClass SPECIFIC_TYPE_PORTABLE_INSTALLER_TOOL = new SpecificType_PortableInstallerTool();
		public static SpecificDeviceClass SPECIFIC_TYPE_REMOTE_CONTROL_AV = new SpecificType_RemoteControlAV();
		public static SpecificDeviceClass SPECIFIC_TYPE_REMOTE_CONTROL_SIMPLE = new SpecificType_RemoteControlSimple();
		public static class SpecificType_NotUsed extends SpecificDeviceClass {
			public SpecificType_NotUsed() {
				super(0x00, "Specific Device Class Not Used");
			}
		}
		public static class SpecificType_PortableRemoteController extends SpecificDeviceClass {
			public SpecificType_PortableRemoteController() {
				super(0x01, "Remote Control (Multi Purpose) Device Type");
			}
		}
		public static class SpecificType_PortableSceneController extends SpecificDeviceClass {
			public SpecificType_PortableSceneController() {
				super(0x02, "Portable Scene Controller");
			}
		}
		public static class SpecificType_PortableInstallerTool extends SpecificDeviceClass {
			public SpecificType_PortableInstallerTool() {
				super(0x03, "");
			}
		}
		public static class SpecificType_RemoteControlAV extends SpecificDeviceClass {
			public SpecificType_RemoteControlAV() {
				super(0x04, "Remote Control (AV) Device Type");
			}
		}
		public static class SpecificType_RemoteControlSimple extends SpecificDeviceClass {
			public SpecificType_RemoteControlSimple() {
				super(0x06, "Remote Control (Simple) Device Type");
			}
		}
	}

	/*
	 * Device class Meter.
	 */

	public static GenericType_Meter GENERIC_TYPE_METER = new GenericType_Meter();

	public static class GenericType_Meter extends GenericDeviceClass {
		public GenericType_Meter() {
			super(0x31, "Meter", SPECIFIC_TYPE_NOT_USED, SPECIFIC_TYPE_SIMPLE_METER, SPECIFIC_TYPE_ADV_ENERGY_CONTROL, SPECIFIC_TYPE_WHOLE_HOME_METER_SIMPLE);
		}
		public static SpecificDeviceClass SPECIFIC_TYPE_NOT_USED = new SpecificType_NotUsed();
		public static SpecificDeviceClass SPECIFIC_TYPE_SIMPLE_METER = new SpecificType_SimpleMeter();
		public static SpecificDeviceClass SPECIFIC_TYPE_ADV_ENERGY_CONTROL = new SpecificType_AdvEnergyMeter();
		public static SpecificDeviceClass SPECIFIC_TYPE_WHOLE_HOME_METER_SIMPLE = new SpecificType_WholeHomeMeterSimple();
		public static class SpecificType_NotUsed extends SpecificDeviceClass {
			public SpecificType_NotUsed() {
				super(0x00, "Specific Device Class Not Used");
			}
		}
		public static class SpecificType_SimpleMeter extends SpecificDeviceClass {
			public SpecificType_SimpleMeter() {
				super(0x01, "Sub Energy Meter Device Type");
			}
		}
		public static class SpecificType_AdvEnergyMeter extends SpecificDeviceClass {
			public SpecificType_AdvEnergyMeter() {
				super(0x02, "Whole Home Energy Meter (Advanced) Device Type");
			}
		}
		public static class SpecificType_WholeHomeMeterSimple extends SpecificDeviceClass {
			public SpecificType_WholeHomeMeterSimple() {
				super(0x03, "Whole Home Meter (Simple) Device Type");
			}
		}
	}

	/*
	 * Device class Meter Pulse.
	 */

	public static GenericType_PulseMeter GENERIC_TYPE_METER_PULSE = new GenericType_PulseMeter();

	public static class GenericType_PulseMeter extends GenericDeviceClass {
		public GenericType_PulseMeter() {
			super(0x30, "Pulse Meter", SPECIFIC_TYPE_NOT_USED);
		}
		public static SpecificDeviceClass SPECIFIC_TYPE_NOT_USED = new SpecificType_NotUsed();
		public static class SpecificType_NotUsed extends SpecificDeviceClass {
			public SpecificType_NotUsed() {
				super(0x00, "Specific Device Class Not Used");
			}
		}
	}

	/*
	 * Device class Non Interoperable.
	 */

	public static GenericType_Noninteroperable GENERIC_TYPE_NON_INTEROPERABLE = new GenericType_Noninteroperable();

	public static class GenericType_Noninteroperable extends GenericDeviceClass {
		public GenericType_Noninteroperable() {
			super(0xFF, "Binary Sensor", SPECIFIC_TYPE_NOT_USED);
		}
		public static SpecificDeviceClass SPECIFIC_TYPE_NOT_USED = new SpecificType_NotUsed();
		public static class SpecificType_NotUsed extends SpecificDeviceClass {
			public SpecificType_NotUsed() {
				super(0x00, "Specific Device Class Not Used");
			}
		}
	}

	/*
	 * Device class Repeater Slave.
	 */

	public static GenericType_RepeaterSlave GENERIC_TYPE_REPEATER_SLAVE = new GenericType_RepeaterSlave();

	public static class GenericType_RepeaterSlave extends GenericDeviceClass {
		public GenericType_RepeaterSlave() {
			super(0x0F, "Repeater Slave", SPECIFIC_TYPE_NOT_USED, SPECIFIC_TYPE_REPEATER_SLAVE, SPECIFIC_TYPE_VIRTUAL_NODE);
		}
		public static SpecificDeviceClass SPECIFIC_TYPE_NOT_USED = new SpecificType_NotUsed();
		public static SpecificDeviceClass SPECIFIC_TYPE_REPEATER_SLAVE = new SpecificType_RepeaterSlave();
		public static SpecificDeviceClass SPECIFIC_TYPE_VIRTUAL_NODE = new SpecificType_VirtualNode();
		public static class SpecificType_NotUsed extends SpecificDeviceClass {
			public SpecificType_NotUsed() {
				super(0x00, "Specific Device Class Not Used");
			}
		}
		public static class SpecificType_RepeaterSlave extends SpecificDeviceClass {
			public SpecificType_RepeaterSlave() {
				super(0x01, "Basic Repeater Slave");
			}
		}
		public static class SpecificType_VirtualNode extends SpecificDeviceClass {
			public SpecificType_VirtualNode() {
				super(0x02, "Virtual Node");
			}
		}
	}

	/*
	 * Device class Security Panel.
	 */

	public static GenericType_SecurityPanel GENERIC_TYPE_SECURITY_PANEL = new GenericType_SecurityPanel();

	public static class GenericType_SecurityPanel extends GenericDeviceClass {
		public GenericType_SecurityPanel() {
			super(0x17, "Security Panel", SPECIFIC_TYPE_NOT_USED, SPECIFIC_TYPE_ZONED_SECURITY_PANEL);
		}
		public static SpecificDeviceClass SPECIFIC_TYPE_NOT_USED = new SpecificType_NotUsed();
		public static SpecificDeviceClass SPECIFIC_TYPE_ZONED_SECURITY_PANEL = new SpecificType_ZonedSecurityPanel();
		public static class SpecificType_NotUsed extends SpecificDeviceClass {
			public SpecificType_NotUsed() {
				super(0x00, "Specific Device Class Not Used");
			}
		}
		public static class SpecificType_ZonedSecurityPanel extends SpecificDeviceClass {
			public SpecificType_ZonedSecurityPanel() {
				super(0x01, "Zoned Security Panel");
			}
		}
	}

	/*
	 * Device class Semi Interoperable.
	 */

	public static GenericType_SemiInteroperable GENERIC_TYPE_SEMI_INTEROPERABLE = new GenericType_SemiInteroperable();

	public static class GenericType_SemiInteroperable extends GenericDeviceClass {
		public GenericType_SemiInteroperable() {
			super(0x50, "Semi Interoperable", SPECIFIC_TYPE_NOT_USED, SPECIFIC_TYPE_ENERGY_PRODUCTION);
		}
		public static SpecificDeviceClass SPECIFIC_TYPE_NOT_USED = new SpecificType_NotUsed();
		public static SpecificDeviceClass SPECIFIC_TYPE_ENERGY_PRODUCTION = new SpecificType_EnergyProduction();
		public static class SpecificType_NotUsed extends SpecificDeviceClass {
			public SpecificType_NotUsed() {
				super(0x00, "Specific Device Class Not Used");
			}
		}
		public static class SpecificType_EnergyProduction extends SpecificDeviceClass {
			public SpecificType_EnergyProduction() {
				super(0x01, "Energy Production");
			}
		}
	}

	/*
	 * Device class Sensor Alarm.
	 */

	public static GenericType_AlarmSensor GENERIC_TYPE_SENSOR_ALARM = new GenericType_AlarmSensor();

	public static class GenericType_AlarmSensor extends GenericDeviceClass {
		public GenericType_AlarmSensor() {
			super(0xA1, "Alarm Sensor", SPECIFIC_TYPE_NOT_USED, SPECIFIC_TYPE_BASIC_ROUTING_ALARM_SENSOR, SPECIFIC_TYPE_ROUTING_ALARM_SENSOR, SPECIFIC_TYPE_BASIC_ZENSOR_NET_ALARM_SENSOR, SPECIFIC_TYPE_ZENSOR_NET_ALARM_SENSOR, SPECIFIC_TYPE_ADV_ZENSOR_NET_ALARM_SENSOR, SPECIFIC_TYPE_BASIC_ROUTING_SMOKE_SENSOR, SPECIFIC_TYPE_ROUTING_SMOKE_SENSOR, SPECIFIC_TYPE_BASIC_ZENSOR_NET_SMOKE_SENSOR, SPECIFIC_TYPE_ZENSOR_NET_SMOKE_SENSOR, SPECIFIC_TYPE_ADV_ZENSOR_NET_SMOKE_SENSOR, SPECIFIC_TYPE_ALARM_SENSOR);
		}
		public static SpecificDeviceClass SPECIFIC_TYPE_NOT_USED = new SpecificType_NotUsed();
		public static SpecificDeviceClass SPECIFIC_TYPE_BASIC_ROUTING_ALARM_SENSOR = new SpecificType_BasicRoutingAlarmSensor();
		public static SpecificDeviceClass SPECIFIC_TYPE_ROUTING_ALARM_SENSOR = new SpecificType_RoutingAlarmSensor();
		public static SpecificDeviceClass SPECIFIC_TYPE_BASIC_ZENSOR_NET_ALARM_SENSOR = new SpecificType_BasicZensorNetAlarmSensor();
		public static SpecificDeviceClass SPECIFIC_TYPE_ZENSOR_NET_ALARM_SENSOR = new SpecificType_ZensorNetAlarmSensor();
		public static SpecificDeviceClass SPECIFIC_TYPE_ADV_ZENSOR_NET_ALARM_SENSOR = new SpecificType_AdvZensorNetAlarmSensor();
		public static SpecificDeviceClass SPECIFIC_TYPE_BASIC_ROUTING_SMOKE_SENSOR = new SpecificType_BasicRoutingSmokeSensor();
		public static SpecificDeviceClass SPECIFIC_TYPE_ROUTING_SMOKE_SENSOR = new SpecificType_RoutingSmokeSensor();
		public static SpecificDeviceClass SPECIFIC_TYPE_BASIC_ZENSOR_NET_SMOKE_SENSOR = new SpecificType_BasicZensorNetSmokeSensor();
		public static SpecificDeviceClass SPECIFIC_TYPE_ZENSOR_NET_SMOKE_SENSOR = new SpecificType_ZensorNetSmokeSensor();
		public static SpecificDeviceClass SPECIFIC_TYPE_ADV_ZENSOR_NET_SMOKE_SENSOR = new SpecificType_AdvZensorNetSmokeSensor();
		public static SpecificDeviceClass SPECIFIC_TYPE_ALARM_SENSOR = new SpecificType_AlarmSensor();
		public static class SpecificType_NotUsed extends SpecificDeviceClass {
			public SpecificType_NotUsed() {
				super(0x00, "Specific Device Class Not Used");
			}
		}
		public static class SpecificType_BasicRoutingAlarmSensor extends SpecificDeviceClass {
			public SpecificType_BasicRoutingAlarmSensor() {
				super(0x01, "");
			}
		}
		public static class SpecificType_RoutingAlarmSensor extends SpecificDeviceClass {
			public SpecificType_RoutingAlarmSensor() {
				super(0x02, "");
			}
		}
		public static class SpecificType_BasicZensorNetAlarmSensor extends SpecificDeviceClass {
			public SpecificType_BasicZensorNetAlarmSensor() {
				super(0x03, "");
			}
		}
		public static class SpecificType_ZensorNetAlarmSensor extends SpecificDeviceClass {
			public SpecificType_ZensorNetAlarmSensor() {
				super(0x04, "");
			}
		}
		public static class SpecificType_AdvZensorNetAlarmSensor extends SpecificDeviceClass {
			public SpecificType_AdvZensorNetAlarmSensor() {
				super(0x05, "");
			}
		}
		public static class SpecificType_BasicRoutingSmokeSensor extends SpecificDeviceClass {
			public SpecificType_BasicRoutingSmokeSensor() {
				super(0x06, "");
			}
		}
		public static class SpecificType_RoutingSmokeSensor extends SpecificDeviceClass {
			public SpecificType_RoutingSmokeSensor() {
				super(0x07, "");
			}
		}
		public static class SpecificType_BasicZensorNetSmokeSensor extends SpecificDeviceClass {
			public SpecificType_BasicZensorNetSmokeSensor() {
				super(0x08, "");
			}
		}
		public static class SpecificType_ZensorNetSmokeSensor extends SpecificDeviceClass {
			public SpecificType_ZensorNetSmokeSensor() {
				super(0x09, "");
			}
		}
		public static class SpecificType_AdvZensorNetSmokeSensor extends SpecificDeviceClass {
			public SpecificType_AdvZensorNetSmokeSensor() {
				super(0x0A, "");
			}
		}
		public static class SpecificType_AlarmSensor extends SpecificDeviceClass {
			public SpecificType_AlarmSensor() {
				super(0x0B, "Sensor (Alarm) Device Type");
			}
		}
	}

	/*
	 * Device class Sensor Binary.
	 */

	public static GenericType_BinarySensor GENERIC_TYPE_SENSOR_BINARY = new GenericType_BinarySensor();

	public static class GenericType_BinarySensor extends GenericDeviceClass {
		public GenericType_BinarySensor() {
			super(0x20, "Binary Sensor", SPECIFIC_TYPE_NOT_USED, SPECIFIC_TYPE_ROUTING_SENSOR_BINARY);
		}
		public static SpecificDeviceClass SPECIFIC_TYPE_NOT_USED = new SpecificType_NotUsed();
		public static SpecificDeviceClass SPECIFIC_TYPE_ROUTING_SENSOR_BINARY = new SpecificType_RoutingSensorBinary();
		public static class SpecificType_NotUsed extends SpecificDeviceClass {
			public SpecificType_NotUsed() {
				super(0x00, "Specific Device Class Not Used");
			}
		}
		public static class SpecificType_RoutingSensorBinary extends SpecificDeviceClass {
			public SpecificType_RoutingSensorBinary() {
				super(0x01, "Routing Binary Sensor");
			}
		}
	}

	/*
	 * Device class Sensor Multilevel.
	 */

	public static GenericType_MultilevelSensor GENERIC_TYPE_SENSOR_MULTILEVEL = new GenericType_MultilevelSensor();

	public static class GenericType_MultilevelSensor extends GenericDeviceClass {
		public GenericType_MultilevelSensor() {
			super(0x21, "Multilevel Sensor", SPECIFIC_TYPE_NOT_USED, SPECIFIC_TYPE_ROUTING_SENSOR_MULTILEVEL, SPECIFIC_TYPE_CHIMNEY_FAN);
		}
		public static SpecificDeviceClass SPECIFIC_TYPE_NOT_USED = new SpecificType_NotUsed();
		public static SpecificDeviceClass SPECIFIC_TYPE_ROUTING_SENSOR_MULTILEVEL = new SpecificType_RoutingSensorMultilevel();
		public static SpecificDeviceClass SPECIFIC_TYPE_CHIMNEY_FAN = new SpecificType_ChimneyFan();
		public static class SpecificType_NotUsed extends SpecificDeviceClass {
			public SpecificType_NotUsed() {
				super(0x00, "Specific Device Class Not Used");
			}
		}
		public static class SpecificType_RoutingSensorMultilevel extends SpecificDeviceClass {
			public SpecificType_RoutingSensorMultilevel() {
				super(0x01, "Sensor (Multilevel) Device Type");
			}
		}
		public static class SpecificType_ChimneyFan extends SpecificDeviceClass {
			public SpecificType_ChimneyFan() {
				super(0x02, "Chimney Fan");
			}
		}
	}

	/*
	 * Device class Static Controller.
	 */

	public static GenericType_StaticController GENERIC_TYPE_STATIC_CONTROLLER = new GenericType_StaticController();

	public static class GenericType_StaticController extends GenericDeviceClass {
		public GenericType_StaticController() {
			super(0x02, "Static Controller", SPECIFIC_TYPE_NOT_USED, SPECIFIC_TYPE_PC_CONTROLLER, SPECIFIC_TYPE_SCENE_CONTROLLER, SPECIFIC_TYPE_STATIC_INSTALLER_TOOL, SPECIFIC_TYPE_SET_TOP_BOX, SPECIFIC_TYPE_SUB_SYSTEM_CONTROLLER, SPECIFIC_TYPE_TV, SPECIFIC_TYPE_GATEWAY);
		}
		public static SpecificDeviceClass SPECIFIC_TYPE_NOT_USED = new SpecificType_NotUsed();
		public static SpecificDeviceClass SPECIFIC_TYPE_PC_CONTROLLER = new SpecificType_PCController();
		public static SpecificDeviceClass SPECIFIC_TYPE_SCENE_CONTROLLER = new SpecificType_SceneController();
		public static SpecificDeviceClass SPECIFIC_TYPE_STATIC_INSTALLER_TOOL = new SpecificType_StaticInstallerTool();
		public static SpecificDeviceClass SPECIFIC_TYPE_SET_TOP_BOX = new SpecificType_SetTopBox();
		public static SpecificDeviceClass SPECIFIC_TYPE_SUB_SYSTEM_CONTROLLER = new SpecificType_SubSystemController();
		public static SpecificDeviceClass SPECIFIC_TYPE_TV = new SpecificType_TV();
		public static SpecificDeviceClass SPECIFIC_TYPE_GATEWAY = new SpecificType_Gateway();
		public static class SpecificType_NotUsed extends SpecificDeviceClass {
			public SpecificType_NotUsed() {
				super(0x00, "Specific Device Class Not Used");
			}
		}
		public static class SpecificType_PCController extends SpecificDeviceClass {
			public SpecificType_PCController() {
				super(0x01, "Central Controller Device Type");
			}
		}
		public static class SpecificType_SceneController extends SpecificDeviceClass {
			public SpecificType_SceneController() {
				super(0x02, "Scene Controller");
			}
		}
		public static class SpecificType_StaticInstallerTool extends SpecificDeviceClass {
			public SpecificType_StaticInstallerTool() {
				super(0x03, "");
			}
		}
		public static class SpecificType_SetTopBox extends SpecificDeviceClass {
			public SpecificType_SetTopBox() {
				super(0x04, "Set Top Box Device Type");
			}
		}
		public static class SpecificType_SubSystemController extends SpecificDeviceClass {
			public SpecificType_SubSystemController() {
				super(0x05, "Sub System Controller Device Type");
			}
		}
		public static class SpecificType_TV extends SpecificDeviceClass {
			public SpecificType_TV() {
				super(0x06, "TV Device Type");
			}
		}
		public static class SpecificType_Gateway extends SpecificDeviceClass {
			public SpecificType_Gateway() {
				super(0x07, "Gateway Device Type");
			}
		}
	}

	/*
	 * Device class Switch Binary.
	 */

	public static GenericType_BinarySwitch GENERIC_TYPE_SWITCH_BINARY = new GenericType_BinarySwitch();

	public static class GenericType_BinarySwitch extends GenericDeviceClass {
		public GenericType_BinarySwitch() {
			super(0x10, "Binary Switch", SPECIFIC_TYPE_NOT_USED, SPECIFIC_TYPE_POWER_SWITCH_BINARY, SPECIFIC_TYPE_COLOR_TUNABLE_BINARY, SPECIFIC_TYPE_SCENE_SWITCH_BINARY, SPECIFIC_TYPE_POWER_STRIP, SPECIFIC_TYPE_SIREN, SPECIFIC_TYPE_VALVE_OPEN_CLOSE, SPECIFIC_TYPE_IRRIGATION_CONTROLLER);
		}
		public static SpecificDeviceClass SPECIFIC_TYPE_NOT_USED = new SpecificType_NotUsed();
		public static SpecificDeviceClass SPECIFIC_TYPE_POWER_SWITCH_BINARY = new SpecificType_PowerSwitchBinary();
		public static SpecificDeviceClass SPECIFIC_TYPE_COLOR_TUNABLE_BINARY = new SpecificType_ColorTunableBinary();
		public static SpecificDeviceClass SPECIFIC_TYPE_SCENE_SWITCH_BINARY = new SpecificType_SceneSwitchBinary();
		public static SpecificDeviceClass SPECIFIC_TYPE_POWER_STRIP = new SpecificType_PowerStrip();
		public static SpecificDeviceClass SPECIFIC_TYPE_SIREN = new SpecificType_Siren();
		public static SpecificDeviceClass SPECIFIC_TYPE_VALVE_OPEN_CLOSE = new SpecificType_ValveOpenClose();
		public static SpecificDeviceClass SPECIFIC_TYPE_IRRIGATION_CONTROLLER = new SpecificType_IrrigationController();
		public static class SpecificType_NotUsed extends SpecificDeviceClass {
			public SpecificType_NotUsed() {
				super(0x00, "Specific Device Class Not Used");
			}
		}
		public static class SpecificType_PowerSwitchBinary extends SpecificDeviceClass {
			public SpecificType_PowerSwitchBinary() {
				super(0x01, "On/Off Power Switch Device Type");
			}
		}
		public static class SpecificType_ColorTunableBinary extends SpecificDeviceClass {
			public SpecificType_ColorTunableBinary() {
				super(0x02, "Color Tunable Binary");
			}
		}
		public static class SpecificType_SceneSwitchBinary extends SpecificDeviceClass {
			public SpecificType_SceneSwitchBinary() {
				super(0x03, "Binary Scene Switch");
			}
		}
		public static class SpecificType_PowerStrip extends SpecificDeviceClass {
			public SpecificType_PowerStrip() {
				super(0x04, "Power Strip Device Type");
			}
		}
		public static class SpecificType_Siren extends SpecificDeviceClass {
			public SpecificType_Siren() {
				super(0x05, "Siren Device Type");
			}
		}
		public static class SpecificType_ValveOpenClose extends SpecificDeviceClass {
			public SpecificType_ValveOpenClose() {
				super(0x06, "Valve (open/close) Device Type");
			}
		}
		public static class SpecificType_IrrigationController extends SpecificDeviceClass {
			public SpecificType_IrrigationController() {
				super(0x07, "Irrigation Controller");
			}
		}
	}

	/*
	 * Device class Switch Multilevel.
	 */

	public static GenericType_MultilevelSwitch GENERIC_TYPE_SWITCH_MULTILEVEL = new GenericType_MultilevelSwitch();

	public static class GenericType_MultilevelSwitch extends GenericDeviceClass {
		public GenericType_MultilevelSwitch() {
			super(0x11, "Multilevel Switch", SPECIFIC_TYPE_NOT_USED, SPECIFIC_TYPE_POWER_SWITCH_MULTILEVEL, SPECIFIC_TYPE_COLOR_TUNABLE_MULTILEVEL, SPECIFIC_TYPE_MOTOR_MULTIPOSITION, SPECIFIC_TYPE_SCENE_SWITCH_MULTILEVEL, SPECIFIC_TYPE_CLASS_A_MOTOR_CONTROL, SPECIFIC_TYPE_CLASS_B_MOTOR_CONTROL, SPECIFIC_TYPE_CLASS_C_MOTOR_CONTROL, SPECIFIC_TYPE_FAN_SWITCH);
		}
		public static SpecificDeviceClass SPECIFIC_TYPE_NOT_USED = new SpecificType_NotUsed();
		public static SpecificDeviceClass SPECIFIC_TYPE_POWER_SWITCH_MULTILEVEL = new SpecificType_PowerSwitchMultilevel();
		public static SpecificDeviceClass SPECIFIC_TYPE_COLOR_TUNABLE_MULTILEVEL = new SpecificType_ColorTunableMultilevel();
		public static SpecificDeviceClass SPECIFIC_TYPE_MOTOR_MULTIPOSITION = new SpecificType_MotorMultiposition();
		public static SpecificDeviceClass SPECIFIC_TYPE_SCENE_SWITCH_MULTILEVEL = new SpecificType_SceneSwitchMultilevel();
		public static SpecificDeviceClass SPECIFIC_TYPE_CLASS_A_MOTOR_CONTROL = new SpecificType_ClassAMotorControl();
		public static SpecificDeviceClass SPECIFIC_TYPE_CLASS_B_MOTOR_CONTROL = new SpecificType_classBMotorControl();
		public static SpecificDeviceClass SPECIFIC_TYPE_CLASS_C_MOTOR_CONTROL = new SpecificType_ClassCMotorControl();
		public static SpecificDeviceClass SPECIFIC_TYPE_FAN_SWITCH = new SpecificType_FanSwitch();
		public static class SpecificType_NotUsed extends SpecificDeviceClass {
			public SpecificType_NotUsed() {
				super(0x00, "Specific Device Class Not Used");
			}
		}
		public static class SpecificType_PowerSwitchMultilevel extends SpecificDeviceClass {
			public SpecificType_PowerSwitchMultilevel() {
				super(0x01, "Light Dimmer Switch Device Type");
			}
		}
		public static class SpecificType_ColorTunableMultilevel extends SpecificDeviceClass {
			public SpecificType_ColorTunableMultilevel() {
				super(0x02, "Color Tunable Multilevel");
			}
		}
		public static class SpecificType_MotorMultiposition extends SpecificDeviceClass {
			public SpecificType_MotorMultiposition() {
				super(0x03, "Multiposition Motor");
			}
		}
		public static class SpecificType_SceneSwitchMultilevel extends SpecificDeviceClass {
			public SpecificType_SceneSwitchMultilevel() {
				super(0x04, "Multilevel Scene Switch");
			}
		}
		public static class SpecificType_ClassAMotorControl extends SpecificDeviceClass {
			public SpecificType_ClassAMotorControl() {
				super(0x05, "Window Covering No Position/Endpoint Device Type");
			}
		}
		public static class SpecificType_classBMotorControl extends SpecificDeviceClass {
			public SpecificType_classBMotorControl() {
				super(0x06, "Window Covering Endpoint Aware Device Type");
			}
		}
		public static class SpecificType_ClassCMotorControl extends SpecificDeviceClass {
			public SpecificType_ClassCMotorControl() {
				super(0x07, "Window Covering Position/Endpoint Aware Device Type");
			}
		}
		public static class SpecificType_FanSwitch extends SpecificDeviceClass {
			public SpecificType_FanSwitch() {
				super(0x08, "Fan Switch Device Type");
			}
		}
	}

	/*
	 * Device class Switch Remote.
	 */

	public static GenericType_SwitchRemote GENERIC_TYPE_SWITCH_REMOTE = new GenericType_SwitchRemote();

	public static class GenericType_SwitchRemote extends GenericDeviceClass {
		public GenericType_SwitchRemote() {
			super(0x12, "Remote Switch", SPECIFIC_TYPE_NOT_USED, SPECIFIC_TYPE_SWITCH_REMOTE_BINARY, SPECIFIC_TYPE_SWITCH_REMOTE_MULTILEVEL, SPECIFIC_TYPE_SWITCH_REMOTE_TOGGLE_BINARY, SPECIFIC_TYPE_SWITCH_REMOTE_TOGGLE_MULTILEVEL);
		}
		public static SpecificDeviceClass SPECIFIC_TYPE_NOT_USED = new SpecificType_NotUsed();
		public static SpecificDeviceClass SPECIFIC_TYPE_SWITCH_REMOTE_BINARY = new SpecificType_SwitchRemoteBinary();
		public static SpecificDeviceClass SPECIFIC_TYPE_SWITCH_REMOTE_MULTILEVEL = new SpecificType_SwitchRemoteMultilevel();
		public static SpecificDeviceClass SPECIFIC_TYPE_SWITCH_REMOTE_TOGGLE_BINARY = new SpecificType_SwitchRemoteToggleBinary();
		public static SpecificDeviceClass SPECIFIC_TYPE_SWITCH_REMOTE_TOGGLE_MULTILEVEL = new SpecificType_SwitchRemoteToggleMultilevel();
		public static class SpecificType_NotUsed extends SpecificDeviceClass {
			public SpecificType_NotUsed() {
				super(0x00, "Specific Device Class Not Used");
			}
		}
		public static class SpecificType_SwitchRemoteBinary extends SpecificDeviceClass {
			public SpecificType_SwitchRemoteBinary() {
				super(0x01, "Binary Remote Switch");
			}
		}
		public static class SpecificType_SwitchRemoteMultilevel extends SpecificDeviceClass {
			public SpecificType_SwitchRemoteMultilevel() {
				super(0x02, "Multilevel Remote Switch");
			}
		}
		public static class SpecificType_SwitchRemoteToggleBinary extends SpecificDeviceClass {
			public SpecificType_SwitchRemoteToggleBinary() {
				super(0x03, "Binary Toggle Remote Switch");
			}
		}
		public static class SpecificType_SwitchRemoteToggleMultilevel extends SpecificDeviceClass {
			public SpecificType_SwitchRemoteToggleMultilevel() {
				super(0x04, "Multilevel Toggle Remote Switch");
			}
		}
	}

	/*
	 * Device class Switch Toggle.
	 */

	public static GenericType_ToggleSwitch GENERIC_TYPE_SWITCH_TOGGLE = new GenericType_ToggleSwitch();

	public static class GenericType_ToggleSwitch extends GenericDeviceClass {
		public GenericType_ToggleSwitch() {
			super(0x13, "Toggle Switch", SPECIFIC_TYPE_NOT_USED, SPECIFIC_TYPE_SWITCH_TOGGLE_BINARY, SPECIFIC_TYPE_SWITCH_TOGGLE_MULTILEVEL);
		}
		public static SpecificDeviceClass SPECIFIC_TYPE_NOT_USED = new SpecificType_NotUsed();
		public static SpecificDeviceClass SPECIFIC_TYPE_SWITCH_TOGGLE_BINARY = new SpecificType_SwitchToggleBinary();
		public static SpecificDeviceClass SPECIFIC_TYPE_SWITCH_TOGGLE_MULTILEVEL = new SpecificType_SwitchToggleMultilevel();
		public static class SpecificType_NotUsed extends SpecificDeviceClass {
			public SpecificType_NotUsed() {
				super(0x00, "Specific Device Class Not Used");
			}
		}
		public static class SpecificType_SwitchToggleBinary extends SpecificDeviceClass {
			public SpecificType_SwitchToggleBinary() {
				super(0x01, "Binary Toggle Switch");
			}
		}
		public static class SpecificType_SwitchToggleMultilevel extends SpecificDeviceClass {
			public SpecificType_SwitchToggleMultilevel() {
				super(0x02, "Multilevel Toggle Switch");
			}
		}
	}

	/*
	 * Device class Thermostat.
	 */

	public static GenericType_Thermostat GENERIC_TYPE_THERMOSTAT = new GenericType_Thermostat();

	public static class GenericType_Thermostat extends GenericDeviceClass {
		public GenericType_Thermostat() {
			super(0x08, "Thermostat", SPECIFIC_TYPE_NOT_USED, SPECIFIC_TYPE_THERMOSTAT_HEATING, SPECIFIC_TYPE_THERMOSTAT_GENERAL, SPECIFIC_TYPE_SETBACK_SCHEDULE_THERMOSTAT, SPECIFIC_TYPE_SETBACK_SCHEDULE_THERMOSTAT, SPECIFIC_TYPE_SETPOINT_THERMOSTAT, SPECIFIC_TYPE_SETBACK_THERMOSTAT, SPECIFIC_TYPE_THERMOSTAT_GENERAL_V2);
		}
		public static SpecificDeviceClass SPECIFIC_TYPE_NOT_USED = new SpecificType_NotUsed();
		public static SpecificDeviceClass SPECIFIC_TYPE_THERMOSTAT_HEATING = new SpecificType_ThermostatHeating();
		public static SpecificDeviceClass SPECIFIC_TYPE_THERMOSTAT_GENERAL = new SpecificType_ThermostatGeneral();
		public static SpecificDeviceClass SPECIFIC_TYPE_SETBACK_SCHEDULE_THERMOSTAT = new SpecificType_SetbackScheduleThermostat();
		public static SpecificDeviceClass SPECIFIC_TYPE_SETPOINT_THERMOSTAT = new SpecificType_SetpointThermostat();
		public static SpecificDeviceClass SPECIFIC_TYPE_SETBACK_THERMOSTAT = new SpecificType_SetbackThermostat();
		public static SpecificDeviceClass SPECIFIC_TYPE_THERMOSTAT_GENERAL_V2 = new SpecificType_ThermostatGeneralV2();
		public static class SpecificType_NotUsed extends SpecificDeviceClass {
			public SpecificType_NotUsed() {
				super(0x00, "Specific Device Class Not Used");
			}
		}
		public static class SpecificType_ThermostatHeating extends SpecificDeviceClass {
			public SpecificType_ThermostatHeating() {
				super(0x01, "Thermostat Heating");
			}
		}
		public static class SpecificType_ThermostatGeneral extends SpecificDeviceClass {
			public SpecificType_ThermostatGeneral() {
				super(0x02, "Thermostat General");
			}
		}
		public static class SpecificType_SetbackScheduleThermostat extends SpecificDeviceClass {
			public SpecificType_SetbackScheduleThermostat() {
				super(0x03, "Setback Schedule Thermostat");
			}
		}
		public static class SpecificType_SetpointThermostat extends SpecificDeviceClass {
			public SpecificType_SetpointThermostat() {
				super(0x04, "Setpoint Thermostat");
			}
		}
		public static class SpecificType_SetbackThermostat extends SpecificDeviceClass {
			public SpecificType_SetbackThermostat() {
				super(0x05, "Thermostat (Setback) Device Type");
			}
		}
		public static class SpecificType_ThermostatGeneralV2 extends SpecificDeviceClass {
			public SpecificType_ThermostatGeneralV2() {
				super(0x06, "Thermostat (HVAC) Device Type");
			}
		}
	}

	/*
	 * Device class Ventilation.
	 */

	public static GenericType_Ventilation GENERIC_TYPE_VENTILATION = new GenericType_Ventilation();

	public static class GenericType_Ventilation extends GenericDeviceClass {
		public GenericType_Ventilation() {
			super(0x16, "Ventilation", SPECIFIC_TYPE_NOT_USED, SPECIFIC_TYPE_RESIDENTIAL_HRV);
		}
		public static SpecificDeviceClass SPECIFIC_TYPE_NOT_USED = new SpecificType_NotUsed();
		public static SpecificDeviceClass SPECIFIC_TYPE_RESIDENTIAL_HRV = new SpecificType_ResidentialHrv();
		public static class SpecificType_NotUsed extends SpecificDeviceClass {
			public SpecificType_NotUsed() {
				super(0x00, "Specific Device Class Not Used");
			}
		}
		public static class SpecificType_ResidentialHrv extends SpecificDeviceClass {
			public SpecificType_ResidentialHrv() {
				super(0x01, "Residential Hrv");
			}
		}
	}

	/*
	 * Device class Window Covering.
	 */

	public static GenericType_WindowCovering GENERIC_TYPE_WINDOW_COVERING = new GenericType_WindowCovering();

	public static class GenericType_WindowCovering extends GenericDeviceClass {
		public GenericType_WindowCovering() {
			super(0x09, "Window Covering", SPECIFIC_TYPE_NOT_USED, SPECIFIC_TYPE_SIMPLE_WINDOW_COVERING);
		}
		public static SpecificDeviceClass SPECIFIC_TYPE_NOT_USED = new SpecificType_NotUsed();
		public static SpecificDeviceClass SPECIFIC_TYPE_SIMPLE_WINDOW_COVERING = new SpecificType_SimpleWindowCovering();
		public static class SpecificType_NotUsed extends SpecificDeviceClass {
			public SpecificType_NotUsed() {
				super(0x00, "Specific Device Class Not Used");
			}
		}
		public static class SpecificType_SimpleWindowCovering extends SpecificDeviceClass {
			public SpecificType_SimpleWindowCovering() {
				super(0x01, "Simple Window Covering Control");
			}
		}
	}

	/*
	 * Device class Zip Node.
	 */

	public static GenericType_ZipNode GENERIC_TYPE_ZIP_NODE = new GenericType_ZipNode();

	public static class GenericType_ZipNode extends GenericDeviceClass {
		public GenericType_ZipNode() {
			super(0x15, "Wall Controller", SPECIFIC_TYPE_NOT_USED, SPECIFIC_TYPE_ZIP_TUN_NODE, SPECIFIC_TYPE_ZIP_ADV_NODE);
		}
		public static SpecificDeviceClass SPECIFIC_TYPE_NOT_USED = new SpecificType_NotUsed();
		public static SpecificDeviceClass SPECIFIC_TYPE_ZIP_TUN_NODE = new SpecificType_ZipTunNode();
		public static SpecificDeviceClass SPECIFIC_TYPE_ZIP_ADV_NODE = new SpecificType_ZipAdvNode();
		public static class SpecificType_NotUsed extends SpecificDeviceClass {
			public SpecificType_NotUsed() {
				super(0x00, "Specific Device Class Not Used");
			}
		}
		public static class SpecificType_ZipTunNode extends SpecificDeviceClass {
			public SpecificType_ZipTunNode() {
				super(0x01, "Zip Tun Node");
			}
		}
		public static class SpecificType_ZipAdvNode extends SpecificDeviceClass {
			public SpecificType_ZipAdvNode() {
				super(0x02, "Zip Adv Node");
			}
		}
	}

	/*
	 * Device class Wall Controller.
	 */

	public static GenericType_WallController GENERIC_TYPE_WALL_CONTROLLER = new GenericType_WallController();

	public static class GenericType_WallController extends GenericDeviceClass {
		public GenericType_WallController() {
			super(0x18, "Wall Controller", SPECIFIC_TYPE_NOT_USED, SPECIFIC_TYPE_BASIC_WALL_CONTROLLER);
		}
		public static SpecificDeviceClass SPECIFIC_TYPE_NOT_USED = new SpecificType_NotUsed();
		public static SpecificDeviceClass SPECIFIC_TYPE_BASIC_WALL_CONTROLLER = new SpecificType_BasicWallController();
		public static class SpecificType_NotUsed extends SpecificDeviceClass {
			public SpecificType_NotUsed() {
				super(0x00, "Specific Device Class Not Used");
			}
		}
		public static class SpecificType_BasicWallController extends SpecificDeviceClass {
			public SpecificType_BasicWallController() {
				super(0x01, "Wall Controller Device Type");
			}
		}
	}

	/*
	 * Device class Network Extender.
	 */

	public static GenericType_NetworkExtender GENERIC_TYPE_NETWORK_EXTENDER = new GenericType_NetworkExtender();

	public static class GenericType_NetworkExtender extends GenericDeviceClass {
		public GenericType_NetworkExtender() {
			super(0x05, "Network Extender Generic Device Class", SPECIFIC_TYPE_NOT_USED, SPECIFIC_TYPE_SECURE_EXTENDER);
		}
		public static SpecificDeviceClass SPECIFIC_TYPE_NOT_USED = new SpecificType_NotUsed();
		public static SpecificDeviceClass SPECIFIC_TYPE_SECURE_EXTENDER = new SpecificType_SecureExtender();
		public static class SpecificType_NotUsed extends SpecificDeviceClass {
			public SpecificType_NotUsed() {
				super(0x00, "Specific Device Class Not Used");
			}
		}
		public static class SpecificType_SecureExtender extends SpecificDeviceClass {
			public SpecificType_SecureExtender() {
				super(0x01, "Specific Device Secure Extender");
			}
		}
	}

	/*
	 * Device class Appliance.
	 */

	public static GenericType_Appliance GENERIC_TYPE_APPLIANCE = new GenericType_Appliance();

	public static class GenericType_Appliance extends GenericDeviceClass {
		public GenericType_Appliance() {
			super(0x06, "Appliance", SPECIFIC_TYPE_NOT_USED, SPECIFIC_TYPE_GENERAL_APPLIANCE, SPECIFIC_TYPE_KITCHEN_APPLIANCE, SPECIFIC_TYPE_LAUNDRY_APPLIANCE);
		}
		public static SpecificDeviceClass SPECIFIC_TYPE_NOT_USED = new SpecificType_NotUsed();
		public static SpecificDeviceClass SPECIFIC_TYPE_GENERAL_APPLIANCE = new SpecificType_GeneralAppliance();
		public static SpecificDeviceClass SPECIFIC_TYPE_KITCHEN_APPLIANCE = new SpecificType_KitchenAppliance();
		public static SpecificDeviceClass SPECIFIC_TYPE_LAUNDRY_APPLIANCE = new SpecificType_LaundryAppliance();
		public static class SpecificType_NotUsed extends SpecificDeviceClass {
			public SpecificType_NotUsed() {
				super(0x00, "Specific Device Class Not Used");
			}
		}
		public static class SpecificType_GeneralAppliance extends SpecificDeviceClass {
			public SpecificType_GeneralAppliance() {
				super(0x01, "Notification Sensor");
			}
		}
		public static class SpecificType_KitchenAppliance extends SpecificDeviceClass {
			public SpecificType_KitchenAppliance() {
				super(0x02, "Notification Sensor");
			}
		}
		public static class SpecificType_LaundryAppliance extends SpecificDeviceClass {
			public SpecificType_LaundryAppliance() {
				super(0x03, "Notification Sensor");
			}
		}
	}

	/*
	 * Device class Sensor Notification.
	 */

	public static GenericType_SensorNotification GENERIC_TYPE_SENSOR_NOTIFICATION = new GenericType_SensorNotification();

	public static class GenericType_SensorNotification extends GenericDeviceClass {
		public GenericType_SensorNotification() {
			super(0x07, "Sensor Notification", SPECIFIC_TYPE_NOT_USED, SPECIFIC_TYPE_NOTIFICATION_SENSOR);
		}
		public static SpecificDeviceClass SPECIFIC_TYPE_NOT_USED = new SpecificType_NotUsed();
		public static SpecificDeviceClass SPECIFIC_TYPE_NOTIFICATION_SENSOR = new SpecificType_NotificationSensor();
		public static class SpecificType_NotUsed extends SpecificDeviceClass {
			public SpecificType_NotUsed() {
				super(0x00, "Specific Device Class not used");
			}
		}
		public static class SpecificType_NotificationSensor extends SpecificDeviceClass {
			public SpecificType_NotificationSensor() {
				super(0x01, "Notification Sensor");
			}
		}
	}

	/*
	NOT_USED(0, GenericDeviceClass.NOT_KNOWN, "Not Known"),
	PORTABLE_REMOTE_CONTROLLER(1, GenericDeviceClass.REMOTE_CONTROLLER, "Portable Remote Controller"),
	PORTABLE_SCENE_CONTROLLER(2, GenericDeviceClass.REMOTE_CONTROLLER, "Portable Scene Controller"),
	PORTABLE_INSTALLER_TOOL(3, GenericDeviceClass.REMOTE_CONTROLLER, "Portable Installer Tool"),
	PC_CONTROLLER(1, GenericDeviceClass.STATIC_CONTOLLER, "PC Controller"),
	SCENE_CONTROLLER(2, GenericDeviceClass.STATIC_CONTOLLER, "Scene Controller"),
	INSTALLER_TOOL(3, GenericDeviceClass.STATIC_CONTOLLER, "Static Installer Tool"),
	SATELLITE_RECEIVER(4, GenericDeviceClass.AV_CONTROL_POINT, "Satellite Receiver"),
	SATELLITE_RECEIVER_V2(17, GenericDeviceClass.AV_CONTROL_POINT, "Satellite Receiver V2"),
	DOORBELL(18, GenericDeviceClass.AV_CONTROL_POINT, "Doorbell"),
	SIMPLE_DISPLAY(1, GenericDeviceClass.DISPLAY, "Simple Display"),
	THERMOSTAT_HEATING(1, GenericDeviceClass.THERMOSTAT, "Heating Thermostat"),
	THERMOSTAT_GENERAL(2, GenericDeviceClass.THERMOSTAT, "General Thermostat"),
	SETBACK_SCHEDULE_THERMOSTAT(3, GenericDeviceClass.THERMOSTAT, "Setback Schedule Thermostat"),
	SETPOINT_THERMOSTAT(4, GenericDeviceClass.THERMOSTAT, "Setpoint Thermostat"),
	SETBACK_THERMOSTAT(5, GenericDeviceClass.THERMOSTAT, "Setback Thermostat"),
	THERMOSTAT_GENERAL_V2(6, GenericDeviceClass.THERMOSTAT, "General Thermostat V2"),
	SIMPLE_WINDOW_COVERING(1, GenericDeviceClass.WINDOW_COVERING, "Simple Window Covering Control"),
	BASIC_REPEATER_SLAVE(1, GenericDeviceClass.REPEATER_SLAVE, "Basic Repeater Slave"),
	POWER_SWITCH_BINARY(1, GenericDeviceClass.BINARY_SWITCH, "Binary Power Switch"),
	SCENE_SWITCH_BINARY_DISCONTINUED(2, GenericDeviceClass.BINARY_SWITCH, "Binary Scene Switch (Discontinued)"),
	SCENE_SWITCH_BINARY(3, GenericDeviceClass.BINARY_SWITCH, "Binary Scene Switch"),
	POWER_SWITCH_MULTILEVEL(1, GenericDeviceClass.MULTILEVEL_SWITCH, "Multilevel Power Switch"),
	SCENE_SWITCH_MULTILEVEL_DISCONTINUED(2, GenericDeviceClass.MULTILEVEL_SWITCH, "Multilevel Scene Switch (Discontinued)"),
	MOTOR_MULTIPOSITION(3, GenericDeviceClass.MULTILEVEL_SWITCH, "Multiposition Motor"),
	SCENE_SWITCH_MULTILEVEL(4, GenericDeviceClass.MULTILEVEL_SWITCH, "Multilevel Scene Switch"),
	MOTOR_CONTROL_CLASS_A(5, GenericDeviceClass.MULTILEVEL_SWITCH, "Motor Control Class A"),
	MOTOR_CONTROL_CLASS_B(6, GenericDeviceClass.MULTILEVEL_SWITCH, "Motor Control Class B"),
	MOTOR_CONTROL_CLASS_C(7, GenericDeviceClass.MULTILEVEL_SWITCH, "Motor Control Class C"),
	SWITCH_REMOTE_BINARY(1, GenericDeviceClass.REMOTE_SWITCH, "Binary Remote Switch"),
	SWITCH_REMOTE_MULTILEVEL(2, GenericDeviceClass.REMOTE_SWITCH, "Multilevel Remote Switch"),
	SWITCH_REMOTE_TOGGLE_BINARY(3, GenericDeviceClass.REMOTE_SWITCH, "Binary Toggle Remote Switch"),
	SWITCH_REMOTE_TOGGLE_MULTILEVEL(4, GenericDeviceClass.REMOTE_SWITCH, "Multilevel Toggle Remote Switch"),
	SWITCH_TOGGLE_BINARY(1, GenericDeviceClass.TOGGLE_SWITCH, "Binary Toggle Switch"),
	SWITCH_TOGGLE_MULTILEVEL(2, GenericDeviceClass.TOGGLE_SWITCH, "Multilevel Toggle Switch"),
	Z_IP_TUNNELING_GATEWAY(1, GenericDeviceClass.Z_IP_GATEWAY, "Z/IP Tunneling Gateway"),
	Z_IP_ADVANCED_GATEWAY(2, GenericDeviceClass.Z_IP_GATEWAY, "Z/IP Advanced Gateway"),
	Z_IP_TUNNELING_NODE(1, GenericDeviceClass.Z_IP_NODE, "Z/IP Tunneling Node"),
	Z_IP_ADVANCED_NODE(2, GenericDeviceClass.Z_IP_NODE, "Z/IP Advanced Node"),
	RESIDENTIAL_HEAT_RECOVERY_VENTILATION(1, GenericDeviceClass.VENTILATION, "Residential Heat Recovery Ventilation"),
	ROUTING_SENSOR_BINARY(1, GenericDeviceClass.BINARY_SENSOR, "Routing Binary Sensor"),
	ROUTING_SENSOR_MULTILEVEL(1, GenericDeviceClass.MULTILEVEL_SENSOR, "Routing Multilevel Sensor"),
	SIMPLE_METER(1, GenericDeviceClass.METER, "Simple Meter"),
	DOOR_LOCK(1, GenericDeviceClass.ENTRY_CONTROL, "Door Lock"),
	ADVANCED_DOOR_LOCK(2, GenericDeviceClass.ENTRY_CONTROL, "Advanced Door Lock"),
	SECURE_KEYPAD_DOOR_LOCK(3, GenericDeviceClass.ENTRY_CONTROL, "Secure Keypad Door Lock"),
	ENERGY_PRODUCTION(1, GenericDeviceClass.SEMI_INTEROPERABLE, "Energy Production"),
	ALARM_SENSOR_ROUTING_BASIC(1, GenericDeviceClass.ALARM_SENSOR, "Basic Routing Alarm Sensor"),
	ALARM_SENSOR_ROUTING(2, GenericDeviceClass.ALARM_SENSOR, "Routing Alarm Sensor"),
	ALARM_SENSOR_ZENSOR_BASIC(3, GenericDeviceClass.ALARM_SENSOR, "Basic Zensor Alarm Sensor"),
	ALARM_SENSOR_ZENSOR(4, GenericDeviceClass.ALARM_SENSOR, "Zensor Alarm Sensor"),
	ALARM_SENSOR_ZENSOR_ADVANCED(5, GenericDeviceClass.ALARM_SENSOR, "Advanced Zensor Alarm Sensor"),
	SMOKE_SENSOR_ROUTING_BASIC(6, GenericDeviceClass.ALARM_SENSOR, "Basic Routing Smoke Sensor"),
	SMOKE_SENSOR_ROUTING(7, GenericDeviceClass.ALARM_SENSOR, "Routing Smoke Sensor"),
	SMOKE_SENSOR_ZENSOR_BASIC(8, GenericDeviceClass.ALARM_SENSOR, "Basic Zensor Smoke Sensor"),
	SMOKE_SENSOR_ZENSOR(9, GenericDeviceClass.ALARM_SENSOR, "Zensor Smoke Sensor"),
	SMOKE_SENSOR_ZENSOR_ADVANCED(10, GenericDeviceClass.ALARM_SENSOR, "Advanced Zensor Smoke Sensor");
	 */

	protected static class Reserved extends GenericDeviceClass {
		public Reserved(int id, String label) {
			super(id, label);
		}
	}


	protected static final GenericDeviceClass[] genericDeviceClasses = new GenericDeviceClass[256];

	static {
		GenericDeviceClass[] genericDeviceClassDefined = {
				GENERIC_TYPE_AV_CONTROL_POINT,
				GENERIC_TYPE_DISPLAY,
				GENERIC_TYPE_ENTRY_CONTROL,
				GENERIC_TYPE_GENERIC_CONTROLLER,
				GENERIC_TYPE_METER,
				GENERIC_TYPE_METER_PULSE,
				GENERIC_TYPE_NON_INTEROPERABLE,
				GENERIC_TYPE_REPEATER_SLAVE,
				GENERIC_TYPE_SECURITY_PANEL,
				GENERIC_TYPE_SEMI_INTEROPERABLE,
				GENERIC_TYPE_SENSOR_ALARM,
				GENERIC_TYPE_SENSOR_BINARY,
				GENERIC_TYPE_SENSOR_MULTILEVEL,
				GENERIC_TYPE_STATIC_CONTROLLER,
				GENERIC_TYPE_SWITCH_BINARY,
				GENERIC_TYPE_SWITCH_MULTILEVEL,
				GENERIC_TYPE_SWITCH_REMOTE,
				GENERIC_TYPE_SWITCH_TOGGLE,
				GENERIC_TYPE_THERMOSTAT,
				GENERIC_TYPE_VENTILATION,
				GENERIC_TYPE_WINDOW_COVERING,
				GENERIC_TYPE_ZIP_NODE,
				GENERIC_TYPE_WALL_CONTROLLER,
				GENERIC_TYPE_NETWORK_EXTENDER,
				GENERIC_TYPE_APPLIANCE,
				GENERIC_TYPE_SENSOR_NOTIFICATION
		};
		GenericDeviceClass genericDeviceClass;
		for (int i=0; i<genericDeviceClassDefined.length; ++i) {
			genericDeviceClass = genericDeviceClassDefined[i];
			genericDeviceClasses[genericDeviceClass.id] = genericDeviceClass;
		}
	}

	/**
	 * Lookup function based on the alarm type code.
	 * Returns null if the code does not exist.
	 * @param id the code to lookup
	 * @return enumeration value of the alarn type.
	 */
	public static GenericDeviceClass getType(int id) {
		GenericDeviceClass genericDeviceClass = null;
		if (id > 0 && id < 256) {
			genericDeviceClass = genericDeviceClasses[id];
		}
		if (genericDeviceClass == null) {
			genericDeviceClass = new Reserved(id, "Unknown");
			if (id < 256) {
				genericDeviceClasses[id] = genericDeviceClass;
			}
		}
		return genericDeviceClass;
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
