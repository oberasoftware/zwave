package com.oberasoftware.home.zwave.core.utils;

import com.oberasoftware.home.zwave.api.messages.types.CommandClass;
import com.oberasoftware.home.zwave.api.messages.types.ControllerMessageType;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author renarj
 */
public final class MessageUtil {
    private static final Logger LOG = getLogger(MessageUtil.class);

    private static final int SIZE_MASK = 0x07;
    private static final int PRECISION_MASK = 0xe0;
    private static final int PRECISION_SHIFT = 0x05;


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

    /**
     * Extract a decimal value from a byte array.
     * @param buffer the buffer to be parsed.
     * @param offset the offset at which to start reading
     * @return the extracted decimal value
     */
    public static BigDecimal extractValue(byte[] buffer, int offset) {
        int size = buffer[offset] & SIZE_MASK;
        int precision = (buffer[offset] & PRECISION_MASK) >> PRECISION_SHIFT;

        if((size+offset) >= buffer.length) {
            LOG.error("Error extracting value - length={}, offset={}, size={}.",
                    buffer.length, offset, size);
            throw new NumberFormatException();
        }

        int value = 0;
        int i;
        for (i = 0; i < size; ++i) {
            value <<= 8;
            value |= buffer[offset + i + 1] & 0xFF;
        }

        // Deal with sign extension. All values are signed
        BigDecimal result;
        if ((buffer[offset + 1] & 0x80) == 0x80) {
            // MSB is signed
            if (size == 1) {
                value |= 0xffffff00;
            } else if (size == 2) {
                value |= 0xffff0000;
            }
        }

        result = BigDecimal.valueOf(value);

        BigDecimal divisor = BigDecimal.valueOf(Math.pow(10, precision));
        return result.divide(divisor);
    }
}
