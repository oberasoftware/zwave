package com.oberasoftware.home.zwave.core.utils;

import com.oberasoftware.home.zwave.messages.types.CommandClass;
import com.oberasoftware.home.zwave.messages.types.ControllerMessageType;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author renarj
 */
public class MessageUtil {
    private static final Logger LOG = getLogger(MessageUtil.class);

    private static final MessageUtil INSTANCE = new MessageUtil();

    private final Map<Integer, ControllerMessageType> messagesMap = new HashMap<>();
    private final Map<Integer, CommandClass> commandClassMap = new HashMap<>();

    private MessageUtil() {
        newArrayList(ControllerMessageType.values()).forEach(m -> messagesMap.put(m.getKey(), m));
        newArrayList(CommandClass.values()).forEach(m -> commandClassMap.put(m.getClassCode(), m));
    }

    /**
     * Lookup function based on the generic device class code.
     * @param i the code to lookup
     * @return enumeration value of the generic device class.
     */
    public static ControllerMessageType getMessageClass(int i) {
        LOG.trace("Getting controller message type for device class: {}", Integer.toHexString(i));
        return INSTANCE.messagesMap.get(i);
    }

    public static CommandClass getCommandClass(int commandClassCode) {
        LOG.trace("Getting command class for type: {}", commandClassCode);
        return INSTANCE.commandClassMap.get(commandClassCode);
    }
}
