package com.antiaction.zwave.constants;

import java.util.Optional;

public abstract class SensorType {

	protected int id;

	protected String label;

	protected int version;

	protected SensorScale[] sensorScales;

	protected SensorType(int id, String label, int version, SensorScale ... sensorScales) {
		this.id = id;
		this.label = label;
		this.version = version;
		this.sensorScales = sensorScales;
	}

	/*
	 * Air Temperature.
	 */

	public static AirTemperature AIR_TEMPERATURE = new AirTemperature();

	public static class AirTemperature extends SensorType {
		public AirTemperature() {
			super(0x01, "Air Temperature", 1, CELSIUS, FAHRENHEIT);
		}
		public static SensorScale CELSIUS = new Celsius();
		public static SensorScale FAHRENHEIT = new Fahrenheit();
		public static class Celsius extends SensorScale {
			public Celsius() {
				super(0x00, "C", "Celsius", 1);
			}
		}
		public static class Fahrenheit extends SensorScale {
			public Fahrenheit() {
				super(0x01, "F", "Fahrenheit", 1);	
			}
		}
	}

	/*
	 * General Purpose.
	 */

	public static GeneralPurpose GENERAL_PURPOSE = new GeneralPurpose();

	public static class GeneralPurpose extends SensorType {
		public GeneralPurpose() {
			super(0x02, "General Purpose", 1, PERCENTAGE_VALUE, DIMENSIONLESS_VALUE);
		}
		public static SensorScale PERCENTAGE_VALUE = new PercentageValue();
		public static SensorScale DIMENSIONLESS_VALUE = new DimensionlessValue();
		public static class PercentageValue extends SensorScale {
			public PercentageValue() {
				super(0x00, "%", "Percentage value", 1);
			}
		}
		public static class DimensionlessValue extends SensorScale {
			public DimensionlessValue() {
				super(0x01, "", "Dimensionless value", 1);
			}
		}
	}

	/*
	 * Luminance.
	 */

	public static Luminance LUMINANCE = new Luminance();

	public static class Luminance extends SensorType {
		public Luminance() {
			super(0x03, "Luminance", 1, PERCENTAGE_VALUE, LUX);
		}
		public static SensorScale PERCENTAGE_VALUE = new PercentageValue();
		public static SensorScale LUX = new Lux();
		public static class PercentageValue extends SensorScale {
			public PercentageValue() {
				super(0x00, "%", "Percentage value", 1);
			}
		}
		public static class Lux extends SensorScale {
			public Lux() {
				super(0x01, "Lux", null, 1);
			}
		}
	}

	/*
	 * Power.
	 */

	public static Power POWER = new Power();

	public static class Power extends SensorType {
		public Power() {
			super(0x04, "Power", 2, WATT, BTU_PER_HOUR);
		}
		public static SensorScale WATT = new Watt();
		public static SensorScale BTU_PER_HOUR = new BtuPerHour();
		public static class Watt extends SensorScale {
			public Watt() {
				super(0x00, "W", "Watt", 2);
			}
		}
		public static class BtuPerHour extends SensorScale {
			public BtuPerHour() {
				super(0x01, "Btu/h", "British thermal unit/hour", 2);
			}
		}
	}

	/*
	 * Humidity.
	 */

	public static Humidity HUMIDITY = new Humidity();

	public static class Humidity extends SensorType {
		public Humidity() {
			super(0x05, "Humidity", 2, PERCENTAGE_VALUE, ABSOLUTE_HUMIDITY);
		}
		public static SensorScale PERCENTAGE_VALUE = new PercentageValue();
		public static SensorScale ABSOLUTE_HUMIDITY = new AbsoluteHumidity();
		public static class PercentageValue extends SensorScale {
			public PercentageValue() {
				super(0x00, "%", "Percentage value", 1);
			}
		}
		public static class AbsoluteHumidity extends SensorScale {
			public AbsoluteHumidity() {
				super(0x01, "g/m³", "Absolute humidity", 5);
			}
		}
	}

	/*
	 * Velocity.
	 */

	public static Velocity VELOCITY = new Velocity();

	public static class Velocity extends SensorType {
		public Velocity() {
			super(0x06, "Velocity", 2, MPS, MPH);
		}
		public static SensorScale MPS = new MetersPerSecond();
		public static SensorScale MPH = new MilesPerHour();
		public static class MetersPerSecond extends SensorScale {
			public MetersPerSecond() {
				super(0x00, "m/s", "meter/second", 2);
			}
		}
		public static class MilesPerHour extends SensorScale {
			public MilesPerHour() {
				super(0x01, "Mph", "miles/hour", 2);
			}
		}
	}

	/*
	 * Direction.
	 */

	public static Direction DIRECTION = new Direction();

	public static class Direction extends SensorType {
		public Direction() {
			super(0x07, "Direction", 2, DEGREES);
		}
		public static SensorScale DEGREES = new Degrees();
		public static class Degrees extends SensorScale {
			public Degrees() {
				// TODO
				super(0x00, "", "0 to 360 degrees. 0 = no wind, 90 = east, 180 = south, 270 = west, and 360 = north", 2);
			}
		}
	}

	/*
	 * Atmospheric Pressure.
	 */

	public static AtmoephericPressure ATMOSPHERIC_PRESSURE = new AtmoephericPressure();

	public static class AtmoephericPressure extends SensorType {
		public AtmoephericPressure() {
			super(0x08, "Atmospheric Pressure", 2, KILO_PASCAL, INCHES_OF_MERCURY);
		}
		public static SensorScale KILO_PASCAL = new KiloPascal();
		public static SensorScale INCHES_OF_MERCURY = new InchesOfMercury();
		public static class KiloPascal extends SensorScale {
			public KiloPascal() {
				super(0x00, "kPa", "kilopascal", 2);
			}
		}
		public static class InchesOfMercury extends SensorScale {
			public InchesOfMercury() {
				super(0x01, "inHg", "Inches of Mercury", 2);
			}
		}
	}

	/*
	 * Barometric Pressure.
	 */

	public static BarometricPressure BAROMETRIC_PRESSURE = new BarometricPressure();

	public static class BarometricPressure extends SensorType {
		public BarometricPressure() {
			super(0x09, "Barometric Pressure", 2, KILO_PASCAL, INCHES_OF_MERCURY);
		}
		public static SensorScale KILO_PASCAL = new KiloPascal();
		public static SensorScale INCHES_OF_MERCURY = new InchesOfMercury();
		public static class KiloPascal extends SensorScale {
			public KiloPascal() {
				super(0x00, "kPa", "kilopascal", 2);
			}
		}
		public static class InchesOfMercury extends SensorScale {
			public InchesOfMercury() {
				super(0x01, "inHg", "Inches of Mercury", 2);
			}
		}
	}

	/*
	 * Solar Radiation.
	 */

	public static SolarRadiation SOLAR_RADIATION = new SolarRadiation();

	public static class SolarRadiation extends SensorType {
		public SolarRadiation() {
			super(0x0A, "Solar Radiation", 2, WATT_PER_SQUAREMETER);
		}
		public static SensorScale WATT_PER_SQUAREMETER = new WattPerSqrMeter();
		public static class WattPerSqrMeter extends SensorScale {
			public WattPerSqrMeter() {
				super(0x00, "W/m²", "watt/square meter", 2);
			}
		}
	}

	/*
	 * Dew point.
	 */

	public static DewPoint DEW_POINT = new DewPoint();

	public static class DewPoint extends SensorType {
		public DewPoint() {
			super(0x0B, "Dew point", 2, CELSIUS, FAHRENHEIT);
		}
		public static SensorScale CELSIUS = new Celsius();
		public static SensorScale FAHRENHEIT = new Fahrenheit();
		public static class Celsius extends SensorScale {
			public Celsius() {
				super(0x00, "C", "Celsius", 2);
			}
		}
		public static class Fahrenheit extends SensorScale {
			public Fahrenheit() {
				super(0x01, "F", "Fahrenheit", 2);	
			}
		}
	}

	/*
	 * Rain rate.
	 */

	public static RainRate RAIN_RATE = new RainRate();

	public static class RainRate extends SensorType {
		public RainRate() {
			super(0x0C, "Rain rate", 2, MMPH, IPH);
		}
		public static SensorScale MMPH = new MilliMeterPerHour();
		public static SensorScale IPH = new InchPerHour();
		public static class MilliMeterPerHour extends SensorScale {
			public MilliMeterPerHour() {
				super(0x00, "mm/h", "millimeter/hour", 2);
			}
		}
		public static class InchPerHour extends SensorScale {
			public InchPerHour() {
				super(0x01, "in/h", "inch/hour", 2);
			}
		}
	}

	/*
	 * Tide level.
	 */

	public static TideLevel TIDE_LEVEL = new TideLevel();

	public static class TideLevel extends SensorType {
		public TideLevel() {
			super(0x0D, "Tide level", 2, METER, FEET);
		}
		public static SensorScale METER = new Meter();
		public static SensorScale FEET = new Feet();
		public static class Meter extends SensorScale {
			public Meter() {
				super(0x00, "m", "meter", 2);
			}
		}
		public static class Feet extends SensorScale {
			public Feet() {
				super(0x01, "ft", "feet", 2);
			}
		}
	}

	/*
	 * Weight.
	 */

	public static Weight WEIGHT = new Weight();

	public static class Weight extends SensorType {
		public Weight() {
			super(0x0E, "Weight", 3, KG, POUND);
		}
		public static SensorScale KG = new Kilogram();
		public static SensorScale POUND = new Pound();
		public static class Kilogram extends SensorScale {
			public Kilogram() {
				super(0x00, "kg", "kilogram", 3);
			}
		}
		public static class Pound extends SensorScale {
			public Pound() {
				super(0x01, "lb", "Pound", 3);
			}
		}
	}

	/*
	 * Voltage.
	 */

	public static Voltage VOLTAGE = new Voltage();

	public static class Voltage extends SensorType {
		public Voltage() {
			super(0x0F, "Voltage", 3, V, MV);
		}
		public static SensorScale V = new Volts();
		public static SensorScale MV = new MilliVolts();
		public static class Volts extends SensorScale {
			public Volts() {
				super(0x00, "V", "volt", 3);
			}
		}
		public static class MilliVolts extends SensorScale {
			public MilliVolts() {
				super(0x01, "mV", "millivolt", 3);
			}
		}
	}

	/*
	 * Current.
	 */

	public static Current CURRENT = new Current();

	public static class Current extends SensorType {
		public Current() {
			super(0x10, "Current", 3, A, MA);
		}
		public static SensorScale A = new Ampere();
		public static SensorScale MA = new MilliAmpere();
		public static class Ampere extends SensorScale {
			public Ampere() {
				super(0x00, "A", "ampere", 3);
			}
		}
		public static class MilliAmpere extends SensorScale {
			public MilliAmpere() {
				super(0x01, "mA", "milliampere", 3);
			}
		}
	}

	/*
	 * Carbon Dioxide CO2-level.
	 */

	public static CarbonDioxideLevel CO2 = new CarbonDioxideLevel();

	public static class CarbonDioxideLevel extends SensorType {
		public CarbonDioxideLevel() {
			super(0x11, "Carbon Dioxide CO2-level", 3, PPM);
		}
		public static SensorScale PPM = new PartsPerMillion();
		public static class PartsPerMillion extends SensorScale {
			public PartsPerMillion() {
				super(0x00, "Ppm", "parts/million", 3);
			}
		}
	}

	/*
	 * Air flow.
	 */

	public static AirFlow AIR_FLOW = new AirFlow();

	public static class AirFlow extends SensorType {
		public AirFlow() {
			super(0x12, "Air flow", 3, CMH, CFM);
		}
		public static SensorScale CMH = new CubicMeterPerHour();
		public static SensorScale CFM = new CubicFeetPerMinute();
		public static class CubicMeterPerHour extends SensorScale {
			public CubicMeterPerHour() {
				super(0x00, "m³/h", "cubic meter/hour", 3);
			}
		}
		public static class CubicFeetPerMinute extends SensorScale {
			public CubicFeetPerMinute() {
				super(0x01, "cfm", "cubic feet/minute", 3);
			}
		}
	}

	/*
	 * Tank capacity.
	 */

	public static TankCapacity TANK_CAPACITY = new TankCapacity();

	public static class TankCapacity extends SensorType {
		public TankCapacity() {
			super(0x13, "Tank capacity", 3, LITER, CUBICMETER, GALLONS);
		}
		public static SensorScale LITER = new Liter();
		public static SensorScale CUBICMETER = new CubicMeter();
		public static SensorScale GALLONS = new Gallons();
		public static class Liter extends SensorScale {
			public Liter() {
				super(0x00, "l", "liter", 3);
			}
		}
		public static class CubicMeter extends SensorScale {
			public CubicMeter() {
				super(0x01, "m³", "(cubic meter", 3);
			}
		}
		public static class Gallons extends SensorScale {
			public Gallons() {
				super(0x02, "gal", "gallons", 3);
			}
		}
	}

	/*
	 * Distance.
	 */

	public static Distance DISTANCE = new Distance();

	public static class Distance extends SensorType {
		public Distance() {
			super(0x14, "Distance", 3, METER, CENTIMETER, FEET);
		}
		public static SensorScale METER = new Meter();
		public static SensorScale CENTIMETER = new Centimeter();
		public static SensorScale FEET = new Feet();
		public static class Meter extends SensorScale {
			public Meter() {
				super(0x00, "m", "meter", 3);
			}
		}
		public static class Centimeter extends SensorScale {
			public Centimeter() {
				super(0x01, "cm", "centimeter", 3);
			}
		}
		public static class Feet extends SensorScale {
			public Feet() {
				super(0x02, "ft", "feet", 3);
			}
		}
	}

	/*
	 * Angle Position.
	 *
	 * [DEPRECATED by v8]
	 * The use of this Sensor Type is NOT RECOMMENDED.
	 * The Direction (0x07) Sensor Type SHOULD be used for reporting polar positions.
	 * A device implementing the Angle Position Sensor Type SHOULD also implement the Direction (0x07) Sensor Type.
	 */

	public static AnglePosition ANGLE_POSITION = new AnglePosition();

	public static class AnglePosition extends SensorType {
		public AnglePosition() {
			super(0x15, "Angle Position", 4, PERCENTAGE_VALUE, DEGREES_REL_NORTH_POLE, DEGRESS_REL_SOUTH_POLE);
		}
		public static SensorScale PERCENTAGE_VALUE = new PercentageValue();
		public static SensorScale DEGREES_REL_NORTH_POLE = new DegressRelNorthPole();
		public static SensorScale DEGRESS_REL_SOUTH_POLE = new DegressRelSouthPole();
		public static class PercentageValue extends SensorScale {
			public PercentageValue() {
				super(0x00, "%", "Percentage value", 4);
			}
		}
		public static class DegressRelNorthPole extends SensorScale {
			public DegressRelNorthPole() {
				// TODO
				super(0x01, "", "Degrees relative to north pole of standing eye view", 4);
			}
		}
		public static class DegressRelSouthPole extends SensorScale {
			public DegressRelSouthPole() {
				// TODO
				super(0x02, "", "Degrees relative to south pole of standing eye view", 4);
			}
		}
	}

	/*
	 * Rotation.
	 */

	public static Rotation ROTATION = new Rotation();

	public static class Rotation extends SensorType {
		public Rotation() {
			super(0x16, "Rotation", 5, RPM, HERTZ);
		}
		public static SensorScale RPM = new RevolutionsPerMinute();
		public static SensorScale HERTZ = new Hertz();
		public static class RevolutionsPerMinute extends SensorScale {
			public RevolutionsPerMinute() {
				super(0x00, "rpm", "revolutions per minute", 5);
			}
		}
		public static class Hertz extends SensorScale {
			public Hertz() {
				super(0x01, "Hz", "Hertz", 5);
			}
		}
	}

	/*
	 * Water Temperature.
	 */
	public static WaterTemperature WATER_TEMPERATURE = new WaterTemperature();

	public static class WaterTemperature extends SensorType {
		public WaterTemperature() {
			super(0x17, "Water Temperature", 5, CELSIUS, FAHRENHEIT);
		}
		public static SensorScale CELSIUS = new Celsius();
		public static SensorScale FAHRENHEIT = new Fahrenheit();
		public static class Celsius extends SensorScale {
			public Celsius() {
				super(0x00, "C", "Celsius", 5);
			}
		}
		public static class Fahrenheit extends SensorScale {
			public Fahrenheit() {
				super(0x01, "F", "Fahrenheit", 5);	
			}
		}
	}

	/*
	 * Soil Temperature.
	 */

	public static SoilTemperature SOIL_TEMPERATURE = new SoilTemperature();

	public static class SoilTemperature extends SensorType {
		public SoilTemperature() {
			super(0x18, "Soil Temperature", 5, CELSIUS, FAHRENHEIT);
		}
		public static SensorScale CELSIUS = new Celsius();
		public static SensorScale FAHRENHEIT = new Fahrenheit();
		public static class Celsius extends SensorScale {
			public Celsius() {
				super(0x00, "C", "Celsius", 5);
			}
		}
		public static class Fahrenheit extends SensorScale {
			public Fahrenheit() {
				super(0x01, "F", "Fahrenheit", 5);	
			}
		}
	}

	/*
	 * Seismic Intensity.
	 */

	public static SeismicIntensity SEISMIC_INTENSITY = new SeismicIntensity();

	public static class SeismicIntensity extends SensorType {
		public SeismicIntensity() {
			super(0x19, "Seismic Intensity", 5, MERCALLI, EUROPEAN_MACROSEISMIC, LIEDU, SHINDO);
		}
		public static SensorScale MERCALLI = new Mercalli();
		public static SensorScale EUROPEAN_MACROSEISMIC = new EuropeanMacroseismic();
		public static SensorScale LIEDU = new Liedu();
		public static SensorScale SHINDO = new Shindo();
		public static class Mercalli extends SensorScale {
			public Mercalli() {
				super(0x00, null, "Mercalli", 5);
			}
		}
		public static class EuropeanMacroseismic extends SensorScale {
			public EuropeanMacroseismic() {
				super(0x01, null, "European Macroseismic", 5);
			}
		}
		public static class Liedu extends SensorScale {
			public Liedu() {
				super(0x02, null, "Liedu", 5);
			}
		}
		public static class Shindo extends SensorScale {
			public Shindo() {
				super(0x03, null, "Shindo", 5);
			}
		}
	}

	/*
	 * Seismic Magnitude.
	 */

	public static SeismicMagnitude SEISMIC_MAGNITUDE = new SeismicMagnitude();

	public static class SeismicMagnitude extends SensorType {
		public SeismicMagnitude() {
			super(0x1A, "Seismic Magnitude", 5, LOCAL, MOMENT, SURFACE_WAVE, BODY_WAVE);
		}
		public static SensorScale LOCAL = new Local();
		public static SensorScale MOMENT = new Moment();
		public static SensorScale SURFACE_WAVE = new SurfaceWave();
		public static SensorScale BODY_WAVE = new BodyWave();
		public static class Local extends SensorScale {
			public Local() {
				super(0x00, null, "Local (ML)", 5);
			}
		}
		public static class Moment extends SensorScale {
			public Moment() {
				super(0x01, null, "Moment (MW)", 5);
			}
		}
		public static class SurfaceWave extends SensorScale {
			public SurfaceWave() {
				super(0x02, null, "Surface wave (MS)", 5);
			}
		}
		public static class BodyWave extends SensorScale {
			public BodyWave() {
				super(0x03, null, "Body wave (MB)", 5);
			}
		}
	}

	/*
	 * Ultraviolet.
	 */

	public static UltraViolet ULTRAVIOLET = new UltraViolet();

	public static class UltraViolet extends SensorType {
		public UltraViolet() {
			super(0x1B, "Ultraviolet", 5, UV_INDEX);
		}
		public static SensorScale UV_INDEX = new UVIndex();
		public static class UVIndex extends SensorScale {
			public UVIndex() {
				// TODO
				super(0x00, "UV index", null, 5);
			}
		}
	}

	/*
	 * Electrical Resistivity.
	 */

	public static ElectricalResistivity ELECTRICAL_RESISTIVITY = new ElectricalResistivity();

	public static class ElectricalResistivity extends SensorType {
		public ElectricalResistivity() {
			super(0x1C, "Electrical Resistivity", 5, OHM_METRE);
		}
		public static SensorScale OHM_METRE = new OhmMetre();
		public static class OhmMetre extends SensorScale {
			public OhmMetre() {
				super(0x00, "Om", "ohm metre", 5);
			}
		}
	}

	/*
	 * Electrical Conductivity.
	 */

	public static ElectricalConductivity ELECTRICAL_CONDUCTIVITY = new ElectricalConductivity();

	public static class ElectricalConductivity extends SensorType {
		public ElectricalConductivity() {
			super(0x1D, "Electrical Conductivity", 5, SEIMENS_PER_METRE);
		}
		public static SensorScale SEIMENS_PER_METRE = new SiemensPerMetre();
		public static class SiemensPerMetre extends SensorScale {
			public SiemensPerMetre() {
				super(0x00, "S·m-1", "siemens per metre", 5);
			}
		}
	}

	/*
	 * Loudness.
	 */

	public static Loudness LOUDNESS = new Loudness();

	public static class Loudness extends SensorType {
		public Loudness() {
			super(0x1E, "Loudness", 5, ABSOLUTE_LOUDNESS, A_WEIGHTED_DECIBELS);
		}
		public static SensorScale ABSOLUTE_LOUDNESS = new AbsoluteLoudness();
		public static SensorScale A_WEIGHTED_DECIBELS = new AWeightedDecibels();
		public static class AbsoluteLoudness extends SensorScale {
			public AbsoluteLoudness() {
				super(0x00, "dB", "Absolute loudness", 5);
			}
		}
		public static class AWeightedDecibels extends SensorScale {
			public AWeightedDecibels() {
				super(0x01, "dBA", "A-weighted decibels", 5);
			}
		}
	}

	/*
	 * Moisture.
	 */

	public static Moisture MOISTURE = new Moisture();

	public static class Moisture extends SensorType {
		public Moisture() {
			super(0x1F, "Moisture", 5, PERCENTAGE_VALUE, VOLUME_WATER_CONTENT, IMPEDANCE, WATER_ACTIVITY);
		}
		public static SensorScale PERCENTAGE_VALUE = new PercentageValue();
		public static SensorScale VOLUME_WATER_CONTENT = new VolumeWaterContent();
		public static SensorScale IMPEDANCE = new Impedance();
		public static SensorScale WATER_ACTIVITY = new WaterActivity();
		public static class PercentageValue extends SensorScale {
			public PercentageValue() {
				super(0x00, "%", "Percentage value", 5);
			}
		}
		public static class VolumeWaterContent extends SensorScale {
			public VolumeWaterContent() {
				super(0x01, "m3/m3", "Volume water content", 5);
			}
		}
		public static class Impedance extends SensorScale {
			public Impedance() {
				super(0x02, "kO", "Impedance", 5);
			}
		}
		public static class WaterActivity extends SensorScale {
			public WaterActivity() {
				super(0x03, "aw", "Water activity", 5);
			}
		}
	}

	/*
	 * Frequency.
	 */

	public static Frequency FREQUENCY = new Frequency();

	public static class Frequency extends SensorType {
		public Frequency() {
			super(0x20, "Frequency", 6, HERTZ, KILOHERTZ);
		}
		public static SensorScale HERTZ = new Hertz();
		public static SensorScale KILOHERTZ = new KiloHertz();
		public static class Hertz extends SensorScale {
			public Hertz() {
				// MUST be used until 4.294967295 GHz
				super(0x00, "Hz", "hertz", 6);
			}
		}
		public static class KiloHertz extends SensorScale {
			public KiloHertz() {
				// MUST be used after 4.294967295 GHz
				super(0x01, "kHz", "kilohertz", 6);
			}
		}
		
		
	}

	/*
	 * Time.
	 */

	public static Time TIME = new Time();

	public static class Time extends SensorType {
		public Time() {
			super(0x21, "Time", 6, SECONDS);
		}
		public static SensorScale SECONDS = new Seconds();
		public static class Seconds extends SensorScale {
			public Seconds() {
				super(0x00, "s", "Second", 6);
			}
		}
	}

	/*
	 * Target Temperature.
	 */

	public static TargetTemperature TARGET_TEMPERATURE = new TargetTemperature();

	public static class TargetTemperature extends SensorType {
		public TargetTemperature() {
			super(0x22, "Target Temperature", 6, CELSIUS, FAHRENHEIT);
		}
		public static SensorScale CELSIUS = new Celsius();
		public static SensorScale FAHRENHEIT = new Fahrenheit();
		public static class Celsius extends SensorScale {
			public Celsius() {
				super(0x00, "C", "Celsius", 6);
			}
		}
		public static class Fahrenheit extends SensorScale {
			public Fahrenheit() {
				super(0x01, "F", "Fahrenheit", 6);	
			}
		}
	}

	/*
	 * Particulate Matter 2.5.
	 */

	public static ParticulateMatter25 PARTICULATE_MATTER_25 = new ParticulateMatter25();

	public static class ParticulateMatter25 extends SensorType {
		public ParticulateMatter25() {
			super(0x23, "Particulate Matter 2.5", 7, MOLE_PER_CUBIC_METER, ABSOLUTE_MICROGRAM_PER_CUBIC_METER);
		}
		public static SensorScale MOLE_PER_CUBIC_METER = new MolePerCubicMeter();
		public static SensorScale ABSOLUTE_MICROGRAM_PER_CUBIC_METER = new AbsoluteMicrogramPerCubicMeter();
		public static class MolePerCubicMeter extends SensorScale {
			public MolePerCubicMeter() {
				super(0x00, "mol/m3", "mole per cubic meter", 7);
			}
		}
		public static class AbsoluteMicrogramPerCubicMeter extends SensorScale {
			public AbsoluteMicrogramPerCubicMeter() {
				super(0x01, "Absolute µg/m3", "microgram/cubic meter", 7);
			}
		}
	}

	/*
	 * Formaldehyde CH2O-level.
	 */

	public static FormaldehydeCH2OLevel FORMALDEHYDE_CH2O_LEVEL = new FormaldehydeCH2OLevel();

	public static class FormaldehydeCH2OLevel extends SensorType {
		public FormaldehydeCH2OLevel() {
			super(0x24, "Formaldehyde CH2O-level", 7, MOLE_PER_CUBIC_METER);
		}
		public static SensorScale MOLE_PER_CUBIC_METER = new MolePerCubicMeter();
		public static class MolePerCubicMeter extends SensorScale {
			public MolePerCubicMeter() {
				super(0x00, "mol/m3", "mole per cubic meter", 7);
			}
		}
	}

	/*
	 * Radon Concentration.
	 */

	public static RadonConcentration RADON_CONCENTRATION = new RadonConcentration();

	public static class RadonConcentration extends SensorType {
		public RadonConcentration() {
			super(0x25, "Radon Concentration", 7, BEQUEREL_PER_CUBIC_METER, PICO_CURIES_PER_CUBIC_METER);
		}
		public static SensorScale BEQUEREL_PER_CUBIC_METER = new BecquerelPerCubicMeter();
		public static SensorScale PICO_CURIES_PER_CUBIC_METER = new PicoCuriesPerLiter();
		public static class BecquerelPerCubicMeter extends SensorScale {
			public BecquerelPerCubicMeter() {
				super(0x00, "bq/m3", "Becquerel/cubic meter", 7);
			}
		}
		public static class PicoCuriesPerLiter extends SensorScale {
			public PicoCuriesPerLiter() {
				super(0x01, "pCi/L", "picocuries/liter", 7);
			}
		}
	}

	/*
	 * Methane Density CH4.
	 */

	public static MethaneDensityCH4 METHANE_DENSITY_CH4 = new MethaneDensityCH4();

	public static class MethaneDensityCH4 extends SensorType {
		public MethaneDensityCH4() {
			super(0x26, "Methane Density CH4", 7, MOLE_PER_CUBIC_METER);
		}
		public static SensorScale MOLE_PER_CUBIC_METER = new MolePerCubicMeter();
		public static class MolePerCubicMeter extends SensorScale {
			public MolePerCubicMeter() {
				super(0x00, "mol/m3", "mole per cubic meter", 7);
			}
		}
	}

	/*
	 * Volatile Organic Compound.
	 */

	public static VolatileOrganicCompound VOLATILE_ORGANIC_COMPOUND = new VolatileOrganicCompound();

	public static class VolatileOrganicCompound extends SensorType {
		public VolatileOrganicCompound() {
			super(0x27, "Volatile Organic Compound", 7, MOLE_PER_CUBIC_METER, PPM);
		}
		public static SensorScale MOLE_PER_CUBIC_METER = new MolePerCubicMeter();
		public static SensorScale PPM = new PartsPerMillion();
		public static class MolePerCubicMeter extends SensorScale {
			public MolePerCubicMeter() {
				super(0x00, "mol/m3", "mole per cubic meter", 7);
			}
		}
		public static class PartsPerMillion extends SensorScale {
			public PartsPerMillion() {
				super(0x01, "Ppm", "parts/million", 10);
			}
		}
	}

	/*
	 * Carbon Monoxide CO-level.
	 */

	public static CarbonMonoxideCOLevel CARBON_MONOXIDE_CO_LEVEL = new CarbonMonoxideCOLevel();

	public static class CarbonMonoxideCOLevel extends SensorType {
		public CarbonMonoxideCOLevel() {
			super(0x28, "Carbon Monoxide CO-level", 7, MOLE_PER_CUBIC_METER, PPM);
		}
		public static SensorScale MOLE_PER_CUBIC_METER = new MolePerCubicMeter();
		public static SensorScale PPM = new PartsPerMillion();
		public static class MolePerCubicMeter extends SensorScale {
			public MolePerCubicMeter() {
				super(0x00, "mol/m3", "mole per cubic meter", 7);
			}
		}
		public static class PartsPerMillion extends SensorScale {
			public PartsPerMillion() {
				super(0x01, "Ppm", "parts/million", 10);
			}
		}
	}

	/*
	 * Soil Humidity.
	 */

	public static SoilHumidity SOIL_HUMIDITY = new SoilHumidity();
	public static class SoilHumidity extends SensorType {
		public SoilHumidity() {
			super(0x29, "Soil Humidity", 7, PERCENTAGE_VALUE);
		}
		public static SensorScale PERCENTAGE_VALUE = new PercentageValue();
		public static class PercentageValue extends SensorScale {
			public PercentageValue() {
				super(0x00, "%", "Percentage value", 7);
			}
		}
	}

	/*
	 * Soil Reactivity.
	 */

	public static SoilReactivity SOIL_REACTIVITY = new SoilReactivity();
	public static class SoilReactivity extends SensorType {
		public SoilReactivity() {
			super(0x2A, "Soil Reactivity", 7, ACIDITY);
		}
		public static SensorScale ACIDITY = new Acidity();
		public static class Acidity extends SensorScale {
			public Acidity() {
				super(0x00, "pH", "acidity", 7);
			}
		}
	}

	/*
	 * Soil Salinity.
	 */

	public static SoilSalinity SOIL_SALINITY = new SoilSalinity();

	public static class SoilSalinity extends SensorType {
		public SoilSalinity() {
			super(0x2B, "Soil Salinity", 7, MOLE_PER_CUBIC_METER);
		}
		public static SensorScale MOLE_PER_CUBIC_METER = new MolePerCubicMeter();
		public static class MolePerCubicMeter extends SensorScale {
			public MolePerCubicMeter() {
				super(0x00, "mol/m3", "mole per cubic meter", 7);
			}
		}
	}

	/*
	 * Heart Rate.
	 */

	public static HeartRate HEART_RATE = new HeartRate();

	public static class HeartRate extends SensorType {
		public HeartRate() {
			super(0x2C, "Heart Rate", 7, BEATS_PER_MINUTE);
		}
		public static SensorScale BEATS_PER_MINUTE = new BeatsPerMinute();
		public static class BeatsPerMinute extends SensorScale {
			public BeatsPerMinute() {
				super(0x00, "Bpm", "beats/minute", 7);
			}
		}
	}

	/*
	 * Blood Pressure.
	 */

	public static BloodPressure BLOOD_PRESSURE = new BloodPressure();

	public static class BloodPressure extends SensorType {
		public BloodPressure() {
			super(0x2D, "Blood Pressure", 7, SYSTOLIC_MMHG, DIASTOLIC_MMHG);
		}
		public static SensorScale SYSTOLIC_MMHG = new SystolicMmHg();
		public static SensorScale DIASTOLIC_MMHG = new DiastolicMmHg();
		public static class SystolicMmHg extends SensorScale {
			public SystolicMmHg() {
				super(0x00, "Systolic mmHg", "upper #", 7);
			}
		}
		public static class DiastolicMmHg extends SensorScale {
			public DiastolicMmHg() {
				super(0x01, "Diastolic mmHg", "lower #", 7);
			}
		}
	}

	/*
	 * Muscle Mass.
	 */

	public static MuscleMass MUSCLE_MASS = new MuscleMass();

	public static class MuscleMass extends SensorType {
		public MuscleMass() {
			super(0x2E, "Muscle Mass", 7, KG);
		}
		public static SensorScale KG = new Kilogram();
		public static class Kilogram extends SensorScale {
			public Kilogram() {
				super(0x00, "kg", "kilogram", 7);
			}
		}
	}

	/*
	 * Fat Mass.
	 */

	public static FatMass FAT_MASS = new FatMass();

	public static class FatMass extends SensorType {
		public FatMass() {
			super(0x2F, "Fat Mass", 7, KG);
		}
		public static SensorScale KG = new Kilogram();
		public static class Kilogram extends SensorScale {
			public Kilogram() {
				super(0x00, "kg", "kilogram", 7);
			}
		}
	}

	/*
	 * Bone Mass.
	 */

	public static BoneMass BONE_MASS = new BoneMass();

	public static class BoneMass extends SensorType {
		public BoneMass() {
			super(0x30, "Bone Mass", 7, KG);
		}
		public static SensorScale KG = new Kilogram();
		public static class Kilogram extends SensorScale {
			public Kilogram() {
				super(0x00, "kg", "kilogram", 7);
			}
		}
	}

	/*
	 * Total Body Water, TBW.
	 */

	public static TotalBodyWater TOTAL_BODY_WATER = new TotalBodyWater();

	public static class TotalBodyWater extends SensorType {
		public TotalBodyWater() {
			super(0x31, "Total Body Water, TBW", 7, KG);
		}
		public static SensorScale KG = new Kilogram();
		public static class Kilogram extends SensorScale {
			public Kilogram() {
				super(0x00, "kg", "kilogram", 7);
			}
		}
	}

	/*
	 * Basic Metabolic Rate, BMR.
	 */

	public static BasicMetabolicRate BASIC_METABOLIC_RATE = new BasicMetabolicRate();

	public static class BasicMetabolicRate extends SensorType {
		public BasicMetabolicRate() {
			super(0x32, "Basic Metabolic Rate, BMR", 7, JOULE);
		}
		public static SensorScale JOULE = new Joule();
		public static class Joule extends SensorScale {
			public Joule() {
				super(0x00, "J", "joule", 7);
			}
		}
	}

	/*
	 * Body Mass Index, BMI.
	 */

	public static BodyMassIndex BODY_BASS_INDEX = new BodyMassIndex();

	public static class BodyMassIndex extends SensorType {
		public BodyMassIndex() {
			super(0x33, "Body Mass Index, BMI", 7, BODY_MASS_INDEX);
		}
		public static SensorScale BODY_MASS_INDEX = new BodyMassIndexIndex();
		public static class BodyMassIndexIndex extends SensorScale {
			public BodyMassIndexIndex() {
				super(0x00, "BMI", "Index", 7);
			}
		}
	}

	/*
	 * Acceleration, X-axis.
	 */

	public static AccelerationXAxis ACCELERATION_X_AXIS = new AccelerationXAxis();

	public static class AccelerationXAxis extends SensorType {
		public AccelerationXAxis() {
			super(0x34, "Acceleration, X-axis", 8, METER_PER_SECOND_SQUARED);
		}
		public static SensorScale METER_PER_SECOND_SQUARED = new MeterPerSecondSquared();
		public static class MeterPerSecondSquared extends SensorScale {
			public MeterPerSecondSquared() {
				super(0x00, "m/s2", "meter/seconds squared", 8);
			}
		}
	}

	/*
	 * Acceleration, Y-axis.
	 */

	public static AccelerationYAxis ACCELERATION_Y_AXIS = new AccelerationYAxis();

	public static class AccelerationYAxis extends SensorType {
		public AccelerationYAxis() {
			super(0x35, "Acceleration, Y-axis", 8, METER_PER_SECOND_SQUARED);
		}
		public static SensorScale METER_PER_SECOND_SQUARED = new MeterPerSecondSquared();
		public static class MeterPerSecondSquared extends SensorScale {
			public MeterPerSecondSquared() {
				super(0x00, "m/s2", "meter/seconds squared", 8);
			}
		}
	}

	/*
	 * Acceleration, Z-axis.
	 */

	public static AccelerationZAxis ACCELERATION_Z_AXIS = new AccelerationZAxis();

	public static class AccelerationZAxis extends SensorType {
		public AccelerationZAxis() {
			super(0x36, "Acceleration, Z-axis", 8, METER_PER_SECOND_SQUARED);
		}
		public static SensorScale METER_PER_SECOND_SQUARED = new MeterPerSecondSquared();
		public static class MeterPerSecondSquared extends SensorScale {
			public MeterPerSecondSquared() {
				super(0x00, "m/s2", "meter/seconds squared", 8);
			}
		}
	}

	/*
	 * Smoke Density.
	 */

	public static SmokeDensity SMOKE_DENSITY = new SmokeDensity();

	public static class SmokeDensity extends SensorType {
		public SmokeDensity() {
			super(0x37, "Smoke Density", 8, PERCENTAGE_VALUE);
		}
		public static SensorScale PERCENTAGE_VALUE = new PercentageValue();
		public static class PercentageValue extends SensorScale {
			public PercentageValue() {
				super(0x00, "%", "Percentage value", 8);
			}
		}
	}

	/*
	 * Water Flow.
	 */

	public static WaterFlow WATER_FLOW = new WaterFlow();

	public static class WaterFlow extends SensorType {
		public WaterFlow() {
			super(0x38, "Water Flow", 9, LITER_PER_HOUR);
		}
		public static SensorScale LITER_PER_HOUR = new LiterPerHour();
		public static class LiterPerHour extends SensorScale {
			public LiterPerHour() {
				super(0x00, "l/h", "liter/hour", 9);
			}
		}
	}

	/*
	 * Water Pressure.
	 */

	public static WaterPressure WATER_PRESSURE = new WaterPressure();

	public static class WaterPressure extends SensorType {
		public WaterPressure() {
			super(0x39, "Water Pressure", 9, KILO_PASCAL);
		}
		public static SensorScale KILO_PASCAL = new KiloPascal();
		public static class KiloPascal extends SensorScale {
			public KiloPascal() {
				super(0x00, "kPa", "kilopascal", 9);
			}
		}
	}

	/*
	 * RF Signal Strength.
	 */

	public static RFSignalStrength RF_SIGNAL_STRENGTH = new RFSignalStrength();

	public static class RFSignalStrength extends SensorType {
		public RFSignalStrength() {
			super(0x3A, "RF Signal Strength", 9, RSSI, DBM);
		}
		public static SensorScale RSSI = new Rssi();
		public static SensorScale DBM = new DBm();
		public static class Rssi extends SensorScale {
			public Rssi() {
				// TODO
				super(0x00, "RSSI", "Percentage value", 9);
			}
		}
		public static class DBm extends SensorScale {
			public DBm() {
				// TODO
				super(0x01, "dBm", null, 9);
			}
		}
	}

	/*
	 * Particulate Matter 10.
	 */

	public static ParticulateMatter10 PARTICULATE_MATTER_10 = new ParticulateMatter10();

	public static class ParticulateMatter10 extends SensorType {
		public ParticulateMatter10() {
			super(0x3B, "Particulate Matter 10", 10, MOLE_PER_CUBIC_METER, MICROGRAM_PER_CUBIC_METER);
		}
		public static SensorScale MOLE_PER_CUBIC_METER = new MolePerCubicMeter();
		public static SensorScale MICROGRAM_PER_CUBIC_METER = new MicrogramPerCubicMeter();
		public static class MolePerCubicMeter extends SensorScale {
			public MolePerCubicMeter() {
				super(	0x00, "mol/m3", "mole per cubic meter", 10);
			}
		}
		public static class MicrogramPerCubicMeter extends SensorScale {
			public MicrogramPerCubicMeter() {
				super(0x01, "Absolute µg/m3", "microgram/cubic meter", 10);
			}
		}
	}

	/*
	 * Respiratory Rate.
	 */

	public static RespiratoryRate RESPIRATORY_RATE = new RespiratoryRate();

	public static class RespiratoryRate extends SensorType {
		public RespiratoryRate() {
			super(0x3C, "Respiratory Rate", 10, BREATHS_PER_MINUTE);
		}
		public static SensorScale BREATHS_PER_MINUTE = new BreathsPerMinute();
		public static class BreathsPerMinute extends SensorScale {
			public BreathsPerMinute() {
				super(0x00, "Bpm", "Breaths/minute", 10);
			}
		}
	}

	/*
	public static SensorType xxx = new xxx();
	public static class xxx extends SensorType {
		public xxx() {
			super
		}
	}
	public static SensorScale xxx = new xxx();
	public static class xxx extends SensorScale {
		public xxx() {
			super
		}
	}
	*/

	protected static final SensorType[] sensorTypes = new SensorType[256];

	static {
		SensorType[] sensorTypesDefined = {
				AIR_TEMPERATURE,
				GENERAL_PURPOSE,
				LUMINANCE,
				POWER,
				HUMIDITY,
				VELOCITY,
				DIRECTION,
				ATMOSPHERIC_PRESSURE,
				BAROMETRIC_PRESSURE,
				SOLAR_RADIATION,
				DEW_POINT,
				RAIN_RATE,
				TIDE_LEVEL,
				WEIGHT,
				VOLTAGE,
				CURRENT,
				CO2,
				AIR_FLOW,
				TANK_CAPACITY,
				DISTANCE,
				ANGLE_POSITION,
				ROTATION,
				WATER_TEMPERATURE,
				SOIL_TEMPERATURE,
				SEISMIC_INTENSITY,
				SEISMIC_MAGNITUDE,
				ULTRAVIOLET,
				ELECTRICAL_RESISTIVITY,
				ELECTRICAL_CONDUCTIVITY,
				LOUDNESS,
				MOISTURE,
				FREQUENCY,
				TIME,
				TARGET_TEMPERATURE,
				PARTICULATE_MATTER_25,
				FORMALDEHYDE_CH2O_LEVEL,
				RADON_CONCENTRATION,
				METHANE_DENSITY_CH4,
				VOLATILE_ORGANIC_COMPOUND,
				CARBON_MONOXIDE_CO_LEVEL,
				SOIL_HUMIDITY,
				SOIL_REACTIVITY,
				SOIL_SALINITY,
				HEART_RATE,
				BLOOD_PRESSURE,
				MUSCLE_MASS,
				FAT_MASS,
				BONE_MASS,
				TOTAL_BODY_WATER,
				BASIC_METABOLIC_RATE,
				BODY_BASS_INDEX,
				ACCELERATION_X_AXIS,
				ACCELERATION_Y_AXIS,
				ACCELERATION_Z_AXIS,
				SMOKE_DENSITY,
				WATER_FLOW,
				WATER_PRESSURE,
				RF_SIGNAL_STRENGTH,
				PARTICULATE_MATTER_10,
				RESPIRATORY_RATE
		};
		SensorType sensorType;
		for (int i=0; i<sensorTypesDefined.length; ++i) {
			sensorType = sensorTypesDefined[i];
			sensorTypes[sensorType.id] = sensorType;
		}
	}

	/**
	 * Lookup function based on the sensor type code.
	 * Returns null if the code does not exist.
	 * @param id the code to lookup
	 * @return enumeration value of the sensor type.
	 */
	public static Optional<SensorType> getSensorType(int id) {
		SensorType sensorType = null;
		if (id > 0 && id < 256) {
			sensorType = sensorTypes[id];
		}
		return Optional.ofNullable(sensorType);
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
	 * @return the version
	 */
	public int getVersion() {
		return version;
	}

	public SensorScale getScale(int sensorScaleId) {
		SensorScale sensorScale = null;
		if (sensorScales != null && sensorScaleId >= 0 && sensorScaleId < sensorScales.length) {
			sensorScale = sensorScales[sensorScaleId];
		}
		return sensorScale;
	}

}
