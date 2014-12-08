package com.oberasoftware.home.zwave.rules;

import com.oberasoftware.home.zwave.api.ZWaveAction;
import com.oberasoftware.home.zwave.api.events.devices.DeviceEvent;

import java.util.List;
import java.util.function.Predicate;

/**
 * @author renarj
 */
public class Rule {

    private final List<Predicate<DeviceEvent>> predicates;
    private final List<ConditionSupplier> conditions;
    private final List<ZWaveAction> actions;

    public Rule(List<Predicate<DeviceEvent>> predicates, List<ConditionSupplier> conditions, List<ZWaveAction> actions) {
        this.predicates = predicates;
        this.conditions = conditions;
        this.actions = actions;
    }

    public boolean evaluate(DeviceEvent deviceEvent) {
        return predicates.stream().allMatch(p -> p.test(deviceEvent)) && conditions.stream().allMatch(ConditionSupplier::evaluate);
    }

    public List<ZWaveAction> getActions() {
        return actions;
    }

    @Override
    public String toString() {
        return "Rule{" +
                "predicates=" + predicates +
                ", conditions=" + conditions +
                ", actions=" + actions +
                '}';
    }
}
