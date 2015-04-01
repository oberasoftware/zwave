package com.oberasoftware.home.zwave.converter.events;

import java.util.Optional;

import static java.util.Arrays.stream;

/**
 * @author renarj
 */
public enum SensorType {
    TEMPERATURE(1, "Temperature"),
    GENERAL(2, "General"),
    LUMINANCE(3, "Luminance"),
    POWER(4, "Power"),
    RELATIVE_HUMIDITY(5, "RelativeHumidity"),
    VELOCITY(6, "Velocity"),
    DIRECTION(7, "Direction"),
    ATMOSPHERIC_PRESSURE(8, "AtmosphericPressure"),
    BAROMETRIC_PRESSURE(9, "BarometricPressure"),
    SOLAR_RADIATION(10, "SolarRadiation"),
    DEW_POINT(11, "DewPoint"),
    RAIN_RATE(12, "RainRate"),
    TIDE_LEVEL(13, "TideLevel"),
    WEIGHT(14, "Weight"),
    VOLTAGE(15, "Voltage"),
    CURRENT(16, "Current"),
    CO2(17, "CO2"),
    AIR_FLOW(18, "AirFlow"),
    TANK_CAPACITY(19, "TankCapacity"),
    DISTANCE(20, "Distance"),
    ANGLE_POSITION(21, "AnglePosition"),
    ROTATION(22, "Rotation"),
    WATER_TEMPERATURE(23, "WaterTemperature"),
    SOIL_TEMPERATURE(24, "SoilTemperature"),
    SEISMIC_INTENSITY(25, "SeismicIntensity"),
    SEISMIC_MAGNITUDE(26, "SeismicMagnitude"),
    ULTRAVIOLET(27, "Ultraviolet"),
    ELECTRICAL_RESISTIVITY(28, "ElectricalResistivity"),
    ELECTRICAL_CONDUCTIVITY(29, "ElectricalConductivity"),
    LOUDNESS(30, "Loudness"),
    MOISTURE(31, "Moisture"),
    MAX_TYPE(32, "MaxType");

    private int key;
    private String label;

    private SensorType(int key, String label) {
        this.key = key;
        this.label = label;
    }

    /**
     * Lookup function based on the sensor type code.
     * Returns null if the code does not exist.
     * @param i the code to lookup
     * @return enumeration value of the sensor type.
     */
    public static Optional<SensorType> getSensorType(int i) {
        return stream(values()).filter(v -> v.getKey() == i).findFirst();
    }

    /**
     * @return the key
     */
    public int getKey() {
        return key;
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }
}
