package com.oberasoftware.home.zwave;

/**
 * @author renarj
 */
public interface ZWAVE_CONSTANTS {
    static final int SOF = 0x01;
    static final int ACK = 0x06;
    static final int NAK = 0x15;
    static final int CAN = 0x18;

    /**
     * BINARY SWITCH CONSTANTS
     */
    static final int SWITCH_BINARY_SET = 0x01; //set a value
    static final int SWITCH_BINARY_GET = 0x02; //get a value
    static final int SWITCH_BINARY_REPORT = 0x03; //report status


    static final int ZWAVE_RESPONSE_TIMEOUT = 5000;		// 5000 ms ZWAVE_RESPONSE TIMEOUT

    static final int ZWAVE_RECEIVE_TIMEOUT = 1000;		// 1000 ms ZWAVE_RECEIVE_TIMEOUT
    String RECEIVER_TOPIC = "receiver";
    String SENDER_TOPIC = "sender";
}
