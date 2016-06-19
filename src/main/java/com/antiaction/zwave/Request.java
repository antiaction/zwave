package com.antiaction.zwave;

public abstract class Request {

	public abstract Response send();

	public abstract byte[] getFrame();

	public abstract Response getResponse();

}
