package com.oberasoftware.home.zwave.api.messages.types;

import java.util.Optional;

import static java.util.Arrays.stream;

public enum SpecificDeviceClass {
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

    private int key;
    private GenericDeviceClass genericDeviceClass;
    private String label;

    private SpecificDeviceClass(int key, GenericDeviceClass genericDeviceClass, String label) {
        this.key = key;
        this.label = label;
        this.genericDeviceClass = genericDeviceClass;
    }

    public static Optional<SpecificDeviceClass> getType(GenericDeviceClass genericDeviceClass, int i) {
		return stream(values()).filter(v -> v.getKey() == i && v.genericDeviceClass == genericDeviceClass).findFirst();
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
