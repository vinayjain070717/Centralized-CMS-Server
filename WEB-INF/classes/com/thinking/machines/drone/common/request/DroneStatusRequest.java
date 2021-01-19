package com.thinking.machines.drone.common.request;
public class DroneStatusRequest
{
	private String status;
	public DroneStatusRequest()
	{
		this.status="";
	}
	public void setStatus(String status)
	{
		this.status=status;
	}
	public String getStatus()
	{
		return this.status;
	}
}