package com.oberasoftware.home.zwave.api.events.controller;

import com.oberasoftware.home.zwave.api.messages.types.CommandClass;

import java.util.Set;

import static java.lang.Integer.toHexString;

/**
 * @author renarj
 */
public class ControllerInformationEvent implements ControllerEvent {
    private final String apiVersion;
    private final int manufactorId;
    private final int deviceType;
    private final int deviceId;
    private final Set<CommandClass> supportedCommandClasses;

    public ControllerInformationEvent(String apiVersion, int manufactorId, int deviceType, int deviceId, Set<CommandClass> supportedCommandClasses) {
        this.apiVersion = apiVersion;
        this.manufactorId = manufactorId;
        this.deviceType = deviceType;
        this.deviceId = deviceId;
        this.supportedCommandClasses = supportedCommandClasses;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public int getManufactorId() {
        return manufactorId;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public Set<CommandClass> getSupportedCommandClasses() {
        return supportedCommandClasses;
    }

    @Override
    public boolean isTransactionCompleted() {
        return true;
    }

    @Override
    public String toString() {
        return "ControllerInformationEvent{" +
                "apiVersion='" + apiVersion + '\'' +
                ", manufactorId=" + toHexString(manufactorId) +
                ", deviceType=" + toHexString(deviceType) +
                ", deviceId=" + toHexString(deviceId) +
                ", supportedCommandClasses=" + supportedCommandClasses +
                '}';
    }
}
