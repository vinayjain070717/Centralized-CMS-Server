package com.thinking.machines.drone.common.response;
public class StatusResponse
{
	private double distanceTravelled;
	private double longitude;
	private double latitude;
	private double speed;
	private double altitude;
	private double timeElapsed;
	private double remainingFlightTime;
	public StatusResponse()
	{
	distanceTravelled=0.00;
	longitude=0.00;
	latitude=0.00;
	speed=0.00;
	altitude=0.00;
	timeElapsed=0.00;
	remainingFlightTime=0.00;
	}
	public void setDistanceTravelled(double distanceTravelled)
	{
		this.distanceTravelled=distanceTravelled;
	}
	public double getDistanceTravelled()
	{
		return this.distanceTravelled;
	}
	public void setLongitude(double longitude)
	{
		this.longitude=longitude;
	}
	public double getLongitude()
	{
		return this.longitude;
	}
	public void setLatitude(double latitude)
	{
		this.latitude=latitude;
	}
	public double getLatitude()
	{
		return this.latitude;
	}
	public void setSpeed(double speed)
	{
		this.speed=speed;
	}
	public double getSpeed()
	{
		return this.speed;
	}
	public void setAltitude(double altitude)
	{
		this.altitude=altitude;
	}
	public double getAltitude()
	{
		return this.altitude;
	}
	public void setTimeElapsed(double timeElapsed)
	{
		this.timeElapsed=timeElapsed;
	}
	public double getTimeElapsed()
	{
		return this.timeElapsed;
	}
	public void setRemainingFlightTime(double remainingFlightTime)
	{
		this.remainingFlightTime=remainingFlightTime;
	}
	public double getRemainingFlightTime()
	{
		return this.remainingFlightTime;
	}

}