/**
 * Copyright (c) 2010-2014, openHAB.org and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.oberasoftware.home.zwave.messages;

import com.oberasoftware.home.zwave.core.utils.MessageUtil;
import com.oberasoftware.home.zwave.messages.types.ControllerMessageType;
import com.oberasoftware.home.zwave.messages.types.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import static java.lang.Integer.toHexString;

/**
 * This class represents a message which is used in serial API 
 * interface to communicate with usb Z-Wave stick
 * 
 * A ZWave serial message frame is made up as follows
 * Byte 0 : SOF (Start of Frame) 0x01
 * Byte 1 : Length of frame - number of bytes to follow
 * Byte 2 : Request (0x00) or Response (0x01)
 * Byte 3 : Message Class (see SerialMessageClass)
 * Byte 4+: Message Class data                             >> Message Payload
 * Byte x : Last byte is checksum
 * 
 * @author Victor Belov
 * @author Brian Crosby
 * @author Chris Jackson
 * @since 1.3.0
 */
public class ZWaveRawMessage implements ZWaveMessage {
	private static final Logger LOG = LoggerFactory.getLogger(ZWaveRawMessage.class);

	private byte[] message;
	private MessageType messageType;
	private ControllerMessageType controllerMessageType;

	private int messageNode = 255;
	
	private int transmitOptions = 0;
	private int callbackId = 0;

	private int retries = 0;

	/**
	 * Indicates whether the serial message is valid.
	 */
	private boolean isValid = false;

	/**
	 * Constructor. Creates a new instance of the SerialMessage class using the 
	 * specified message class and message type. An expected reply can be given
	 * to indicate that a transaction is complete. The priority indicates the
	 * priority to send the message with. Higher priority messages are taken from
	 * the send queue earlier than lower priority messages.
	 * @param messageClass the message class to use
	 * @param messageType the message type to use
	 */
	public ZWaveRawMessage(ControllerMessageType messageClass, MessageType messageType) {
		this(255, messageClass, messageType);
	}
	
	/**
	 * Constructor. Creates a new instance of the SerialMessage class using the 
	 * specified message class and message type. An expected reply can be given
	 * to indicate that a transaction is complete. The priority indicates the
	 * priority to send the message with. Higher priority messages are taken from
	 * the send queue earlier than lower priority messages.
	 * @param nodeId the node the message is destined for
	 * @param controllerMessageType the message class to use
	 * @param messageType the message type to use
	 */
	public ZWaveRawMessage(int nodeId, ControllerMessageType controllerMessageType, MessageType messageType) {
		this(nodeId, controllerMessageType, messageType, new byte[] {});
	}

	public ZWaveRawMessage(int nodeId, ControllerMessageType controllerMessageType, MessageType messageType, byte[] message) {
		this(nodeId, controllerMessageType, messageType, message, 0);
	}

	public ZWaveRawMessage(int nodeId, ControllerMessageType controllerMessageType, MessageType messageType, byte[] message, int callbackId) {
		LOG.debug("Node Message: {} Creating message type: {} {}, type = {}",
				nodeId, controllerMessageType, toHexString(controllerMessageType.getKey()), messageType);
		this.controllerMessageType = controllerMessageType;
		this.messageType = messageType;
		this.message = message;
		this.messageNode = nodeId;
		this.callbackId = callbackId;
	}


	/**
	 * Constructor. Creates a new instance of the SerialMessage class from a
	 * specified buffer.
	 * @param buffer the buffer to create the SerialMessage from.
	 */
	public ZWaveRawMessage(byte[] buffer) {
		this(255, buffer);
	}
	
	/**
	 * Constructor. Creates a new instance of the SerialMessage class from a
	 * specified buffer, and subsequently sets the node ID.
	 * @param nodeId the node the message is destined for
	 * @param buffer the buffer to create the SerialMessage from.
	 */
	public ZWaveRawMessage(int nodeId, byte[] buffer) {
		LOG.trace("NODE {}: Creating new SerialMessage from buffer = {}", nodeId, ZWaveRawMessage.bb2hex(buffer));
		int messageLength = buffer.length - 2; // buffer[1];
		byte messageCheckSumm = calculateChecksum(buffer);
		byte messageCheckSummReceived = buffer[messageLength+1];
		LOG.trace(String.format("NODE %d: Message checksum calculated = 0x%02X, received = 0x%02X", nodeId, messageCheckSumm, messageCheckSummReceived));
		if (messageCheckSumm == messageCheckSummReceived) {
			LOG.trace("NODE {}: Checksum matched", nodeId);
			isValid = true;
		} else {
			LOG.trace("NODE {}: Checksum error", nodeId);
			isValid = false;
			return;
		}
		this.messageType = buffer[2] == 0x00 ? MessageType.Request : MessageType.Response;
		this.controllerMessageType = MessageUtil.getMessageClass(buffer[3] & 0xFF);
		this.message = Arrays.copyOfRange(buffer, 4, messageLength + 1);
		this.messageNode = nodeId;
		LOG.trace("NODE {}: Message payload = {}", getNodeId(), ZWaveRawMessage.bb2hex(message));
	}

	/**
     * Converts a byte array to a hexadecimal string representation    
     * @param buffer the byte array to convert
     * @return string the string representation
     */
    static public String bb2hex(byte[] buffer) {
		String result = "";
		for (byte b : buffer) {
			result = result + String.format("%02X ", b);
		}
		return result;
	}
	
	/**
	 * Calculates a checksum for the specified buffer.
	 * @param buffer the buffer to calculate.
	 * @return the checksum value.
	 */
	private static byte calculateChecksum(byte[] buffer) {
		byte checkSum = (byte)0xFF;
		for (int i=1; i<buffer.length-1; i++) {
			checkSum = (byte) (checkSum ^ buffer[i]);
		}
		LOG.trace(String.format("Calculated checksum = 0x%02X", checkSum));
		return checkSum;
	}

	/**
	 * Gets the SerialMessage as a byte array.
	 * @return the message
	 */
	public byte[] getMessageBuffer() {
		ByteArrayOutputStream resultByteBuffer = new ByteArrayOutputStream();
		byte[] result;
		resultByteBuffer.write((byte)0x01);
		int messageLength = message.length +
				(this.controllerMessageType == ControllerMessageType.SendData &&
				this.messageType == MessageType.Request ? 5 : 3); // calculate and set length
		
		resultByteBuffer.write((byte) messageLength);
		resultByteBuffer.write((byte) messageType.ordinal());
		resultByteBuffer.write((byte) controllerMessageType.getKey());
		
		try {
			resultByteBuffer.write(message);
		} catch (IOException e) {
			LOG.error("", e);
		}

		// callback ID and transmit options for a Send Data message.
		if (this.controllerMessageType == ControllerMessageType.SendData && this.messageType == MessageType.Request) {
			resultByteBuffer.write(transmitOptions);
			resultByteBuffer.write(callbackId);
		}
		
		resultByteBuffer.write((byte) 0x00);
		result = resultByteBuffer.toByteArray();
		result[result.length - 1] = 0x01;
		result[result.length - 1] = calculateChecksum(result);
		LOG.debug("Assembled message buffer = " + ZWaveRawMessage.bb2hex(result));
		return result;
	}
	
	/**
	 * Gets the message class. This is the function it represents.
	 * @return The type of controller message
	 */
	public ControllerMessageType getControllerMessageType() {
		return controllerMessageType;
	}

	/**
	 * Returns the Node Id for / from this message.
	 * @return the messageNode
	 */
	public int getNodeId() {
		return messageNode;
	}

	/**
	 * Gets the message payload.
	 * @return the message payload
	 */
	public byte[] getMessage() {
		return message;
	}
	
	/**
	 * Gets a byte of the message payload at the specified index.
	 * The byte is returned as an integer between 0x00 (0) and 0xFF (255).
	 * @param index the index of the byte to return.
	 * @return an integer between 0x00 (0) and 0xFF (255).
	 */
	public int getMessageByte(int index) {
		return message[index] & 0xFF;
	}
	
	/**
	 * Sets the message payload.
	 * @param message The byte message
	 */
	public void setMessage(byte[] message) {
		this.message = message;
	}

	/**
	 * Gets the transmit options for this SendData Request.
	 * @return the transmitOptions
	 */
	public int getTransmitOptions() {
		return transmitOptions;
	}

	public boolean isValid() {
		return isValid;
	}

	/**
	 * Sets the transmit options for this SendData Request.
	 * @param transmitOptions the transmitOptions to set
	 */
	public void setTransmitOptions(int transmitOptions) {
		this.transmitOptions = transmitOptions;
	}

	public int getRetries() {
		return retries;
	}

	public void incrementRetry() {
		retries++;
	}

	/**
	 * Gets the callback ID for this SendData Request.
	 * @return the callbackId
	 */
	public int getCallbackId() {
		return callbackId;
	}

	/**
	 * Sets the callback ID for this SendData Request
	 * @param callbackId the callbackId to set
	 */
	public void setCallbackId(int callbackId) {
		this.callbackId = callbackId;
	}

	public MessageType getMessageType() {
		return messageType;
	}

	@Override
	public String toString() {
		return "ZWaveRawMessage{" +
				"message=" + bb2hex(this.getMessage()) +
				", messageType=" + messageType +
				", controllerMessageType=" + controllerMessageType +
				", messageNode=" + messageNode +
				'}';
	}
}
