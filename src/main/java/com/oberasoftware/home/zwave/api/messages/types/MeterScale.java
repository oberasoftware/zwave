package com.oberasoftware.home.zwave.api.messages.types;

import static java.util.Arrays.stream;

/**
 * @author renarj
 */
public enum MeterScale {
    E_KWh(0, MeterType.ELECTRIC, "kWh", "Energy"),
    E_KVAh(1, MeterType.ELECTRIC, "kVAh", "Energy"),
    E_W(2, MeterType.ELECTRIC, "W", "Power"),
    E_Pulses(3, MeterType.ELECTRIC, "Pulses", "Count"),
    E_V(4, MeterType.ELECTRIC, "V", "Voltage"),
    E_A(5, MeterType.ELECTRIC, "A", "Current"),
    E_Power_Factor(6, MeterType.ELECTRIC, "Power Factor", "Power Factor"),
    G_Cubic_Meters(0, MeterType.GAS, "Cubic Meters", "Volume"),
    G_Cubic_Feet(1, MeterType.GAS, "Cubic Feet", "Volume"),
    G_Pulses(3, MeterType.GAS, "Pulses", "Count"),
    W_Cubic_Meters(0, MeterType.WATER, "Cubic Meters", "Volume"),
    W_Cubic_Feet(1, MeterType.WATER, "Cubic Feet", "Volume"),
    W_Gallons(2, MeterType.WATER, "US gallons", "Volume"),
    W_Pulses(3, MeterType.WATER, "Pulses", "Count");

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
