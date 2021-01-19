package com.thinking.machines.drone.common.request;
public class StatusNotificationUserResponse
{
    private double distanceTravelled;
    private double longitude;
    private double latitude;
    private double speed;
    private double altitude;
    private double timeElapsed;
    private double remainingFlightTime;
    private double airSpeed;
    private int interval;
    public StatusNotificationUserResponse()
    {
        this.distanceTravelled=0.00;
        this.longitude=0.00;
        this.latitude=0.00;
        this.speed=0.00;
        this.altitude=0.00;
        this.timeElapsed=0.00;
        this.remainingFlightTime=0.00;
        this.airSpeed=0.00;
        this.interval=0;
    }
    public void setDistanceTravelled( double distanceTravelled)
    {
    	this.distanceTravelled=distanceTravelled;
    }
    public double getDistanceTravelled()
    {
        return this.distanceTravelled;
    }
    public void setLongitude( double longitude)
    {
        this.longitude=longitude;
    }
    public double getLongitude()
    {
        return this.longitude;
    }
    public void setLatitude( double latitude)
    {
        this.latitude=latitude;
    }
    public double getLatitude()
    {
        return this.latitude;
    }
    public void setSpeed( double speed)
    {
        this.speed=speed;
    }
    public double getSpeed()
    {
        return this.speed;
    }
    public void setAltitude( double altitude)
    {
        this.altitude=altitude;
    }
    public double getAltitude()
    {
        return this.altitude;
    }
    public void setTimeElapsed( double timeElapsed)
    {
        this.timeElapsed=timeElapsed;
    }
    public double getTimeElapsed()
    {
        return this.timeElapsed;
    }
    public void setRemainingFlightTime( double remainingFlightTime)
    {
        this.remainingFlightTime=remainingFlightTime;
    }
    public double getRemainingFlightTime()
    {
        return this.remainingFlightTime;
    }
    public void setAirSpeed( double airSpeed)
    {
        this.airSpeed=airSpeed;
    }
    public double getAirSpeed()
    {
        return this.airSpeed;
    }
    public void setInterval( int interval)
    {
        this.interval=interval;
    }
    public int getInterval()
    {
        return this.interval;
    }

}