package com.oberasoftware.home.zwave.api.messages.types;

import java.util.Optional;

import static java.util.Arrays.stream;

public enum BasicDeviceClass {
    NOT_KNOWN(0, "Not Known"),
    CONTROLLER(1, "Controller"),
    STATIC_CONTROLLER(2, "Static Controller"),
    SLAVE(3, "Slave"),
    ROUTING_SLAVE(4, "Routing Slave");

    private int key;
    private String label;

    private BasicDeviceClass(int key, String label) {
        this.key = key;
        this.label = label;
    }

    public static Optional<BasicDeviceClass> getType(int i) {
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
