package com.thinking.machines.drone.common.response;
public class AuthenticateResponse
{
	private String status;
	private String reason;
	public AuthenticateResponse()
	{
		status=null;
		reason=null;
	}
	public void setStatus(String status)
	{
		this.status=status;
	}
	public void setReason(String reason)
	{
		this.reason=reason;
	}
	public String getStatus()
	{
		return this.status;
	}
	public String getReason()
	{
		return this.reason;
	}
}