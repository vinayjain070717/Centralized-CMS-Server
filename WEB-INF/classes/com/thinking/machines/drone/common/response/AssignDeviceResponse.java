package com.thinking.machines.drone.common.response;
public class AssignDeviceResponse
	{
	private String status;
	private String reason;
	public AssignDeviceResponse()
	{
		status=null;
		reason=null;
	}
	public void setStatus(String status)
	{
		this.status=status;
	}
	public String getStatus()
	{
		return this.status;
	}
	public void setReason(String reason)
	{
		this.reason=reason;
	}
	public String getReason()
	{
		return this.reason;
	}
}
