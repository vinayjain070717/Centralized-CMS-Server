package com.thinking.machines.drone.common.response;
public class CommandResponse
{
	private String status;
	private String reason;
	private String error;
	public CommandResponse()
	{
		status=null;
		reason=null;
		error=null;
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
	public void setError(String error)
	{
		this.error=error;
	}
	public String getError()
	{
		return this.error;
	}
}