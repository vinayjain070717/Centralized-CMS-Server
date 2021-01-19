package com.thinking.machines.drone.common.response;
import java.util.*;
public class SendListOfDevicesResponse
{
	private HashMap<String,String> devices;
	public SendListOfDevicesResponse()
	{
		this.devices=null;
	}
	public void setDevices(HashMap<String,String> devices)
	{
		this.devices=devices;
	}
	public HashMap<String,String> getDevices()
	{
		return this.devices;
	}
}
