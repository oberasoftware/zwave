package com.oberasoftware.home.zwave.api.messages.types;

import static java.util.Arrays.stream;

/**
 * @author renarj
 */
public enum MeterType {
    UNKNOWN(0, "Unknown"),
    ELECTRIC(1, "Electric"),
    GAS(2, "Gas"),
    WATER(3, "Water");

    private int key;
    private String label;

    MeterType(int key, String label) {
        this.key = key;
        this.label = label;
    }

    public static MeterType getMeterType(int i) {
        return stream(values()).filter(v -> v.getKey() == i).findFirst().get();
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
