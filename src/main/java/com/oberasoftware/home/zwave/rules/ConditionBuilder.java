package com.oberasoftware.home.zwave.rules;

import com.oberasoftware.home.zwave.api.ZWaveAction;
import com.oberasoftware.home.zwave.api.ZWaveDeviceAction;
import com.oberasoftware.home.zwave.api.events.devices.DeviceEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author renarj
 */
public class ConditionBuilder {
    private List<Predicate<DeviceEvent>> devicePredicates = new ArrayList<>();

    private List<ConditionSupplier> conditionSuppliers = new ArrayList<>();

    private List<ZWaveAction> actions = new ArrayList<>();

    private ConditionBuilder(Predicate<DeviceEvent> devicePredicate) {
        devicePredicates.add(devicePredicate);
    }

    public static ConditionBuilder when(Predicate<DeviceEvent> predicate) {
        return new ConditionBuilder(predicate);
    }

    public ConditionBuilder alsoWhen(Predicate<DeviceEvent> predicate) {
        devicePredicates.add(predicate);
        return this;
    }

    public <T> ConditionBuilder alsoWhen(Supplier<T> supplier, Predicate<T> predicate) {
        conditionSuppliers.add(new ConditionSupplier<>(supplier, predicate));
        return this;
    }

    public ConditionBuilder thenDo(ZWaveDeviceAction action) {
        actions.add(action);
        return this;
    }

    public Rule build() {
        return new Rule(devicePredicates, conditionSuppliers, actions);
    }
}
