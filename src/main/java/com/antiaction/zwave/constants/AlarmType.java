package com.antiaction.zwave.constants;

public abstract class AlarmType {

	protected int id;

	protected String label;

	protected AlarmEvent[] alarmEvents;

	protected AlarmType(int id, String label, AlarmEvent ... alarmEvents) {
		this.id = id;
		this.label = label;
		this.alarmEvents = alarmEvents;
	}

	public AlarmEvent getAlarmEvent(int id) {
		AlarmEvent alarmEvent = null;
		if (alarmEvents != null) {
			int idx = 0;
			while (alarmEvent == null && idx <alarmEvents.length) {
				if (alarmEvents[idx].id == id) {
					alarmEvent = alarmEvents[idx];
				}
				++idx;
			}
		}
		return alarmEvent;
	}

	/*
	 * Smoke Alarm.
	 */

	public static SmokeAlarm SMOKE_ALARM = new SmokeAlarm();

	public static class SmokeAlarm extends AlarmType {
		public SmokeAlarm() {
			super(0x01, "Smoke Alarm", SMOKE_DETECTED, SMOKE_DETECTED_UNKNOWN_LOCATION, UNKNOWN_EVENT);
		}
		public static AlarmEvent SMOKE_DETECTED = new SmokeDetected();
		public static AlarmEvent SMOKE_DETECTED_UNKNOWN_LOCATION = new SmokeDetectedUnknownLocation();
		public static AlarmEvent UNKNOWN_EVENT = new UnknownEvent();
		public static class SmokeDetected extends AlarmEvent {
			public SmokeDetected() {
				super(0x01, "Smoke detected");
				// Node Location Report (Node Naming and Location Command Class, version 1)
			}
		}
		public static class SmokeDetectedUnknownLocation extends AlarmEvent {
			public SmokeDetectedUnknownLocation() {
				super(0x02, "Smoke detected, Unknown Location");
			}
		}
		public static class UnknownEvent extends AlarmEvent {
			public UnknownEvent() {
				super(0xFE, "Unknown Event");
			}
		}
	}

	/*
	 * CO Alarm.
	 */

	public static COAlarm CO_ALARM = new COAlarm();

	public static class COAlarm extends AlarmType {
		public COAlarm() {
			super(0x02, "CO Alarm", CARBON_MONOXIDE_DETECTED, CARBON_MONOXIDE_DETECTED_UNKNOWN_LOCATION, UNKNOWN_EVENT);
		}
		public static AlarmEvent CARBON_MONOXIDE_DETECTED = new CarbonMonoxideDetected();
		public static AlarmEvent CARBON_MONOXIDE_DETECTED_UNKNOWN_LOCATION = new CarbonMonoxideDetectedUnknownLocation();
		public static AlarmEvent UNKNOWN_EVENT = new UnknownEvent();
		public static class CarbonMonoxideDetected extends AlarmEvent {
			public CarbonMonoxideDetected() {
				super(0x01, "Carbon monoxide detected");
				// Node Location Report (Node Naming and Location Command Class, version 1)
			}
		}
		public static class CarbonMonoxideDetectedUnknownLocation extends AlarmEvent {
			public CarbonMonoxideDetectedUnknownLocation() {
				super(0x02, "Carbon monoxide detected, Unknown Location");
			}
		}
		public static class UnknownEvent extends AlarmEvent {
			public UnknownEvent() {
				super(0xFE, "Unknown Event");
			}
		}
	}

	/*
	 * CO2 Alarm.
	 */

	public static CO2Alarm CO2_ALARM = new CO2Alarm();

	public static class CO2Alarm extends AlarmType {
		public CO2Alarm() {
			super(0x03, "CO2 Alarm", CARBON_DIOXIDE_DETECTED, CARBON_DIOXIDE_DETECTED_UNKNOWN_LOCATION, UNKNOWN_EVENT);
		}
		public static AlarmEvent CARBON_DIOXIDE_DETECTED = new CarbonDioxideDetected();
		public static AlarmEvent CARBON_DIOXIDE_DETECTED_UNKNOWN_LOCATION = new CarbonDioxideDetectedUnknownLocation();
		public static AlarmEvent UNKNOWN_EVENT = new UnknownEvent();
		public static class CarbonDioxideDetected extends AlarmEvent {
			public CarbonDioxideDetected() {
				super(0x01, "Carbon dioxide detected");
				// Node Location Report (Node Naming and Location Command Class, version 1)
			}
		}
		public static class CarbonDioxideDetectedUnknownLocation extends AlarmEvent {
			public CarbonDioxideDetectedUnknownLocation() {
				super(0x02, "Carbon dioxide detected, Unknown Location");
			}
		}
		public static class UnknownEvent extends AlarmEvent {
			public UnknownEvent() {
				super(0xFE, "Unknown Event");
			}
		}
	}

	/*
	 * Heat Alarm.
	 */

	public static HeatAlarm HEAT_ALARM = new HeatAlarm();

	public static class HeatAlarm extends AlarmType {
		public HeatAlarm() {
			super(0x04, "Heat Alarm", OVERHEAT_DETECTED, OVERHEAT_DETECTED_UNKNOWN_LOCATION, RAPID_TEMPERATURE_RISE, RAPID_TEMPERATURE_RISE_UNKNOWN_LOCATION, UNDERHEAT_DETECTED, UNDERHEAT_DETECTED_UNKNOWN_LOCATION, UNKNOWN_EVENT);
		}
		public static AlarmEvent OVERHEAT_DETECTED = new OverheatDetected();
		public static AlarmEvent OVERHEAT_DETECTED_UNKNOWN_LOCATION = new OverheatDetectedUnknownLocation();
		public static AlarmEvent RAPID_TEMPERATURE_RISE = new RapidTemperatureRise();
		public static AlarmEvent RAPID_TEMPERATURE_RISE_UNKNOWN_LOCATION = new RapidTemperatureRiseUnknownLocation();
		public static AlarmEvent UNDERHEAT_DETECTED = new UnderheatDetected();
		public static AlarmEvent UNDERHEAT_DETECTED_UNKNOWN_LOCATION = new UnderheatDetectedUnknownLocation();
		public static AlarmEvent UNKNOWN_EVENT = new UnknownEvent();
		public static class OverheatDetected extends AlarmEvent {
			public OverheatDetected() {
				super(0x01, "Overheat detected");
				// Node Location Report (Node Naming and Location Command Class, version 1)
			}
		}
		public static class OverheatDetectedUnknownLocation extends AlarmEvent {
			public OverheatDetectedUnknownLocation() {
				super(0x02, "Overheat detected, Unknown Location");
			}
		}
		public static class RapidTemperatureRise extends AlarmEvent {
			public RapidTemperatureRise() {
				super(0x03, "Rapid Temperature Rise");
				// Node Location Report (Node Naming and Location Command Class, version 1)
			}
		}
		public static class RapidTemperatureRiseUnknownLocation extends AlarmEvent {
			public RapidTemperatureRiseUnknownLocation() {
				super(0x04, "Rapid Temperature Rise, Unknown Location");
			}
		}
		public static class UnderheatDetected extends AlarmEvent {
			public UnderheatDetected() {
				super(0x05, "Underheat detected");
				// Node Location Report (Node Naming and Location Command Class, version 1)
			}
		}
		public static class UnderheatDetectedUnknownLocation extends AlarmEvent {
			public UnderheatDetectedUnknownLocation() {
				super(0x06, "Underheat detected, Unknown Location");
			}
		}
		public static class UnknownEvent extends AlarmEvent {
			public UnknownEvent() {
				super(0xFE, "Unknown Event");
			}
		}
	}

	/*
	 * Water Alarm.
	 */

	public static WaterAlarm WATER_ALARM = new WaterAlarm();

	public static class WaterAlarm extends AlarmType {
		public WaterAlarm() {
			super(0x05, "Water Alarm", WATER_LEAK_DETECTED, WATER_LEAK_DETECTED_UNKNOWN_LOCATION, WATER_LEVEL_DROPPED, WATER_LEVEL_DROPPED_UNKNOWN_LOCATION, UNKNOWN_EVENT);
		}
		public static AlarmEvent WATER_LEAK_DETECTED = new WaterLeakDetected();
		public static AlarmEvent WATER_LEAK_DETECTED_UNKNOWN_LOCATION = new WaterLeakDetectedUnknownLocation();
		public static AlarmEvent WATER_LEVEL_DROPPED = new WaterLevelDropped();
		public static AlarmEvent WATER_LEVEL_DROPPED_UNKNOWN_LOCATION = new WaterLevelDroppedUnknownLocation();
		public static AlarmEvent UNKNOWN_EVENT = new UnknownEvent();
		public static class WaterLeakDetected extends AlarmEvent {
			public WaterLeakDetected() {
				super(0x01, "Water Leak detected");
				// TODO Node Location Report (Node Naming and Location Command Class, version 1)
			}
		}
		public static class WaterLeakDetectedUnknownLocation extends AlarmEvent {
			public WaterLeakDetectedUnknownLocation() {
				super(0x02, "Water Leak detected, Unknown Location");
			}
		}
		public static class WaterLevelDropped extends AlarmEvent {
			public WaterLevelDropped() {
				super(0x03, "Water Level Dropped");
				// TODO Node Location Report (Node Naming and Location Command Class, version 1)
			}
		}
		public static class WaterLevelDroppedUnknownLocation extends AlarmEvent {
			public WaterLevelDroppedUnknownLocation() {
				super(0x04, "Water Level Dropped, Unknown Location");
			}
		}
		public static class UnknownEvent extends AlarmEvent {
			public UnknownEvent() {
				super(0xFE, "Unknown Event");
			}
		}
	}

	/*
	 * Access Control Alarm.
	 */

	public static AccessControlAlarm ACCESS_CONTROL_ALARM = new AccessControlAlarm();

	public static class AccessControlAlarm extends AlarmType {
		public AccessControlAlarm() {
			super(0x06, "Access Control Alarm", MANUAL_LOCK_OPERATION, MANUAL_UNLOCK_OPERATION, RF_LOCK_OPERATION, RF_UNLOCK_OPERATION, KEYPAD_LOCK_OPERATION, KEYPAD_UNLOCK_OPERATION, UNKNOWN_EVENT);
		}
		public static AlarmEvent MANUAL_LOCK_OPERATION = new ManualLockOperation();
		public static AlarmEvent MANUAL_UNLOCK_OPERATION = new ManualUnlockOperation();
		public static AlarmEvent RF_LOCK_OPERATION = new RfLockOperation();
		public static AlarmEvent RF_UNLOCK_OPERATION = new RfUnlockOperation();
		public static AlarmEvent KEYPAD_LOCK_OPERATION = new KeypadLockOperation();
		public static AlarmEvent KEYPAD_UNLOCK_OPERATION = new KeypadUnlockOperation();
		public static AlarmEvent UNKNOWN_EVENT = new UnknownEvent();
		public static class ManualLockOperation extends AlarmEvent {
			public ManualLockOperation() {
				super(0x01, "Manual Lock Operation");
			}
		}
		public static class ManualUnlockOperation extends AlarmEvent {
			public ManualUnlockOperation() {
				super(0x02, "Manual Unlock Operation");
			}
		}
		public static class RfLockOperation extends AlarmEvent {
			public RfLockOperation() {
				super(0x03, "RF Lock Operation");
			}
		}
		public static class RfUnlockOperation extends AlarmEvent {
			public RfUnlockOperation() {
				super(0x04, "RF Unlock Operation");
			}
		}
		public static class KeypadLockOperation extends AlarmEvent {
			public KeypadLockOperation() {
				super(0x05, "Keypad Lock Operation");
				// TODO User Identifier of User Code Report (User Code Command Class)
			}
		}
		public static class KeypadUnlockOperation extends AlarmEvent {
			public KeypadUnlockOperation() {
				super(0x06, "Keypad Unlock Operation");
				// TODO User Identifier of User Code Report (User Code Command Class)
			}
		}
		public static class UnknownEvent extends AlarmEvent {
			public UnknownEvent() {
				super(0xFE, "Unknown Event");
			}
		}
	}

	/*
	 * Burglar Alarm.
	 */

	public static BurglarAlarm BURGLAR_ALARM = new BurglarAlarm();

	public static class BurglarAlarm extends AlarmType {
		public BurglarAlarm() {
			super(0x07, "Burglar Alarm", INTRUSION, INTRUSION_UNKNOWN_LOCATION, TAMPERING_PRODUCT_COVERING_REMOVED, TAMPERING_INVALID_CODE, GLASS_BREAKAGE, GLASS_BREAKAGE_UNKNOWN_LOCATION, UNKNOWN_EVENT);
		}
		public static AlarmEvent INTRUSION = new Intrusion();
		public static AlarmEvent INTRUSION_UNKNOWN_LOCATION = new IntrusionUnknownLocation();
		public static AlarmEvent TAMPERING_PRODUCT_COVERING_REMOVED = new TamperingProductCoveringRemoved();
		public static AlarmEvent TAMPERING_INVALID_CODE = new TamperingInvalidCode();
		public static AlarmEvent GLASS_BREAKAGE = new GlassBreakage();
		public static AlarmEvent GLASS_BREAKAGE_UNKNOWN_LOCATION = new GlassBreakageUnknownLocation();
		public static AlarmEvent UNKNOWN_EVENT = new UnknownEvent();
		public static class Intrusion extends AlarmEvent {
			public Intrusion() {
				super(0x01, "Intrusion - Node Location Report");
                // TODO Node Location Report (Node Naming and Location Command Class, version 1)
			}
		}
		public static class IntrusionUnknownLocation extends AlarmEvent {
			public IntrusionUnknownLocation() {
				super(0x02, "Intrusion, Unknown Location");
			}
		}
		public static class TamperingProductCoveringRemoved extends AlarmEvent {
			public TamperingProductCoveringRemoved() {
				super(0x03, "Tampering, product covering removed");
			}
		}
		public static class TamperingInvalidCode extends AlarmEvent {
			public TamperingInvalidCode() {
				super(0x04, "Tampering, Invalid Code");
			}
		}
		public static class GlassBreakage extends AlarmEvent {
			public GlassBreakage() {
				super(0x05, "Glass Breakage, Node Location Report");
				// TODO Node Location Report (Node Naming and Location Command Class, version 1)
			}
		}
		public static class GlassBreakageUnknownLocation extends AlarmEvent {
			public GlassBreakageUnknownLocation() {
				super(0x06, "Glass Breakage, Unknown Location");
			}
		}
		public static class UnknownEvent extends AlarmEvent {
			public UnknownEvent() {
				super(0xFE, "Unknown Event");
			}
		}
	}

	/*
	 * Power Management Alarm.
	 */

	public static PowerManagementAlarm POWER_MANAGEMENT_ALARM = new PowerManagementAlarm();

	public static class PowerManagementAlarm extends AlarmType {
		public PowerManagementAlarm() {
			super(0x08, "Power Management Alarm", POWER_HAS_BEEN_APPLIED, AC_MAINS_DISCONNECTED, AC_MAINS_RECONNECTED, SURGE_DETECTION, VOLTAGE_DROP_DRIFT, UNKNOWN_EVENT);
		}
		public static AlarmEvent POWER_HAS_BEEN_APPLIED = new PowerHasBeenApplied();
		public static AlarmEvent AC_MAINS_DISCONNECTED = new ACMainsDisconnected();
		public static AlarmEvent AC_MAINS_RECONNECTED = new ACMainsReconnected();
		public static AlarmEvent SURGE_DETECTION = new SurgeDetection();
		public static AlarmEvent VOLTAGE_DROP_DRIFT = new VoltageDropDrift();
		public static AlarmEvent UNKNOWN_EVENT = new UnknownEvent();
		public static class PowerHasBeenApplied extends AlarmEvent {
			public PowerHasBeenApplied() {
				super(0x01, "Power has been applied");
			}
		}
		public static class ACMainsDisconnected extends AlarmEvent {
			public ACMainsDisconnected() {
				super(0x02, "AC mains disconnected");
			}
		}
		public static class ACMainsReconnected extends AlarmEvent {
			public ACMainsReconnected() {
				super(0x03, "AC mains re-connected");
			}
		}
		public static class SurgeDetection extends AlarmEvent {
			public SurgeDetection() {
				super(0x04, "Surge Detection");
			}
		}
		public static class VoltageDropDrift extends AlarmEvent {
			public VoltageDropDrift() {
				super(0x05, "Voltage Drop/Drift");
			}
		}
		public static class UnknownEvent extends AlarmEvent {
			public UnknownEvent() {
				super(0xFE, "Unknown Event");
			}
		}
	}

	/*
	 * System Alarm.
	 */

	public static SystemAlarm SYSTEM_ALARM = new SystemAlarm();

	public static class SystemAlarm extends AlarmType {
		public SystemAlarm() {
			super(0x0A, "System Alarm", SYSTEM_HARDWARE_FAILURE, SYSTEM_SOFTWARE_FAILURE, UNKNOWN_EVENT);
		}
		public static AlarmEvent SYSTEM_HARDWARE_FAILURE = new SystemHardwareFailure();
		public static AlarmEvent SYSTEM_SOFTWARE_FAILURE = new SystemSoftwareFailure();
		public static AlarmEvent UNKNOWN_EVENT = new UnknownEvent();
		public static class SystemHardwareFailure extends AlarmEvent {
			public SystemHardwareFailure() {
				super(0x01, "System hardware failure");
			}
		}
		public static class SystemSoftwareFailure extends AlarmEvent {
			public SystemSoftwareFailure() {
				super(0x02, "System software failure");
			}
		}
		public static class UnknownEvent extends AlarmEvent {
			public UnknownEvent() {
				super(0xFE, "Unknown Event");
			}
		}
	}

	/*
	 * Emergency Alarm.
	 */

	public static EmergencyAlarm EMERGENCY_ALARM = new EmergencyAlarm();

	public static class EmergencyAlarm extends AlarmType {
		public EmergencyAlarm() {
			super(0x0A, "Emergency Alarm", CONTACT_POLICE, CONTACT_FIRE_SERVICE, CONTACT_MEDICAL_SERVICE, UNKNOWN_EVENT);
		}
		public static AlarmEvent CONTACT_POLICE = new ContactPolice();
		public static AlarmEvent CONTACT_FIRE_SERVICE = new ContactFireService();
		public static AlarmEvent CONTACT_MEDICAL_SERVICE = new ContactMedicalService();
		public static AlarmEvent UNKNOWN_EVENT = new UnknownEvent();
		public static class ContactPolice extends AlarmEvent {
			public ContactPolice() {
				super(0x01, "Contact Police");
			}
		}
		public static class ContactFireService extends AlarmEvent {
			public ContactFireService() {
				super(0x02, "Contact Fire Service");
			}
		}
		public static class ContactMedicalService extends AlarmEvent {
			public ContactMedicalService() {
				super(0x03, "Contact Medical Service");
			}
		}
		public static class UnknownEvent extends AlarmEvent {
			public UnknownEvent() {
				super(0xFE, "Unknown Event");
			}
		}
	}

	/*
	 * Alarm clock.
	 */

	public static AlarmClock ALARM_CLOCK = new AlarmClock();

	public static class AlarmClock extends AlarmType {
		public AlarmClock() {
			super(0x0B, "Alarm Clock", WAKE_UP);
		}
		public static AlarmEvent WAKE_UP = new WakeUp();
		public static class WakeUp extends AlarmEvent {
			public WakeUp() {
				super(0x01, "Wake Up");
			}
		}
	}

	protected static class Reserved extends AlarmType {
		public Reserved(int id, String label) {
			super(id, label);
		}
	}

	protected static final AlarmType[] alarmTypes = new AlarmType[256];

	static {
		AlarmType[] alarmTypesDefined = {
				SMOKE_ALARM,
				CO_ALARM,
				CO2_ALARM,
				HEAT_ALARM,
				WATER_ALARM,
				ACCESS_CONTROL_ALARM,
				BURGLAR_ALARM,
				POWER_MANAGEMENT_ALARM,
				SYSTEM_ALARM,
				EMERGENCY_ALARM
		};
		AlarmType alarmType;
		for (int i=0; i<alarmTypesDefined.length; ++i) {
			alarmType = alarmTypesDefined[i];
			alarmTypes[alarmType.id] = alarmType;
		}
	}

	/**
	 * Lookup function based on the alarm type code.
	 * Returns null if the code does not exist.
	 * @param id the code to lookup
	 * @return enumeration value of the alarn type.
	 */
	public static AlarmType getAlarmType(int id) {
		AlarmType alarmType = null;
		if (id > 0 && id < 256) {
			alarmType = alarmTypes[id];
		}
		if (alarmType == null) {
			alarmType = new Reserved(id, "Unknown");
			if (id < 256) {
				alarmTypes[id] = alarmType;
			}
		}
		return alarmType;
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
