package com.oberasoftware.home.zwave.rules;

import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author renarj
 */
public class ConditionSupplier<T> {
    private Supplier<T> supplier;
    private Predicate<T> predicate;

    public ConditionSupplier(Supplier<T> supplier, Predicate<T> predicate) {
        this.supplier = supplier;
        this.predicate = predicate;
    }

    public boolean evaluate() {
        return predicate.test(supplier.get());
    }

    @Override
    public String toString() {
        return "ConditionSupplier{" +
                "supplier=" + supplier +
                ", predicate=" + predicate +
                '}';
    }
}
