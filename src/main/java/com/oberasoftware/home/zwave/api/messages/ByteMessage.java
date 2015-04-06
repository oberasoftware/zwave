package com.oberasoftware.home.zwave.api.messages;

/**
 * @author renarj
 */
public class ByteMessage implements ZWaveMessage {
    private int singleByte;

    public ByteMessage(int singleByte) {
        this.singleByte = singleByte;
    }

    public int getSingleByte() {
        return singleByte;
    }

    @Override
    public String toString() {
        return "ByteMessage{" +
                "singleByte=" + singleByte +
                '}';
    }
}
