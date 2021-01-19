package com.thinking.machines.drone.common.response;
public class HeartBeatResponse
{
	private String status;
	private long interval;
	public HeartBeatResponse()
	{
		this.status="";
		this.interval=2000;
	}
	public void setStatus(String status)
	{
		this.status=status;
	}
	public String getStatus()
	{
		return this.status;
	}
	public void setInterval(long interval)
	{
		this.interval=interval;
	}
	public long getInterval()
	{
		return this.interval;
	}
}