package com.antiaction.zwave.transport;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SerialTransport {

	public int rate = 115200;

	public int databits = SerialPort.DATABITS_8;

	public int stopbits = SerialPort.STOPBITS_1;

	public int parity = SerialPort.PARITY_NONE;

	protected CommPortIdentifier portIdentifier;

	protected SerialPort serialPort;

	protected OutputStream out;

	protected InputStream in;

	public SerialTransport() {
	}

	public void open(String portName) throws NoSuchPortException, PortInUseException, UnsupportedCommOperationException, IOException {
		portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
		if (portIdentifier.getPortType() == CommPortIdentifier.PORT_SERIAL) {
			if (portIdentifier.getName().equals(portName)) {
				serialPort = portIdentifier.open(portName, 10000);
				serialPort.setSerialPortParams(rate, databits, stopbits, parity);
				out = serialPort.getOutputStream();
				in = serialPort.getInputStream();
			}
		}
	}

	public OutputStream getOutputStream() {
		return out;
	}

	
	public InputStream getInputStream() {
		return in;
	}

	public void close() {
		serialPort.close();
	}

}
