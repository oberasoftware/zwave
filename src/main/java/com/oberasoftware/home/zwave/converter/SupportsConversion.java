package com.oberasoftware.home.zwave.converter;

import com.oberasoftware.home.zwave.messages.types.CommandClass;
import com.oberasoftware.home.zwave.messages.types.ControllerMessageType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author renarj
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SupportsConversion {
    ControllerMessageType controllerMessage() default ControllerMessageType.ALL;

    CommandClass commandClass() default CommandClass.ALL;
}
