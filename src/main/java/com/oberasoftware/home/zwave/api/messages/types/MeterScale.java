package com.oberasoftware.home.zwave.api.messages.types;

import static java.util.Arrays.stream;

/**
 * @author renarj
 */
public enum MeterScale {
    Electric_KWH(0, MeterType.ELECTRIC, "kWh", "Energy"),
    Electric_KVAh(1, MeterType.ELECTRIC, "kVAh", "Energy"),
    Electric_WATT(2, MeterType.ELECTRIC, "W", "Power"),
    Electric_Pulses(3, MeterType.ELECTRIC, "Pulses", "Count"),
    Electric_Volt(4, MeterType.ELECTRIC, "V", "Voltage"),
    Electric_Amp(5, MeterType.ELECTRIC, "A", "Current"),
    Electric_Power_Factor(6, MeterType.ELECTRIC, "Power Factor", "Power Factor"),
    Gas_Cubic_Meters(0, MeterType.GAS, "Cubic Meters", "Volume"),
    Gas_Cubic_Feet(1, MeterType.GAS, "Cubic Feet", "Volume"),
    Gas_Pulses(3, MeterType.GAS, "Pulses", "Count"),
    Water_Cubic_Meters(0, MeterType.WATER, "Cubic Meters", "Volume"),
    Water_Cubic_Feet(1, MeterType.WATER, "Cubic Feet", "Volume"),
    Water_Gallons(2, MeterType.WATER, "US gallons", "Volume"),
    Water_Pulses(3, MeterType.WATER, "Pulses", "Count");

    private final int scale;
    private final MeterType meterType;
    private final String unit;
    private final String label;

    MeterScale(int scale, MeterType meterType, String unit, String label) {
        this.scale = scale;
        this.meterType = meterType;
        this.unit = unit;
        this.label = label;
    }

    /**
     * Lookup function based on the meter type and code. Returns null if the
     * code does not exist.
     *
     * @param meterType the meter type to use to lookup the scale
     * @param i the code to lookup
     * @return enumeration value of the meter scale.
     */
    public static MeterScale getMeterScale(MeterType meterType, int i) {
        return stream(values()).filter(v -> v.getMeterType() == meterType && v.getScale() == i).findFirst().get();
    }

    /**
     * Returns the scale code.
     * @return the scale code.
     */
    public int getScale() {
        return scale;
    }

    /**
     * Returns the meter type.
     * @return the meterType
     */
    public MeterType getMeterType() {
        return meterType;
    }

    /**
     * Returns the unit as string.
     * @return the unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Returns the label (category).
     * @return the label
     */
    public String getLabel() {
        return label;
    }

}
