package com.thinking.machines.cms.services.model;
public class Drone implements java.io.Serializable
{
	private Integer id;
    private String modelName;
    private String modelNumber;
    private String brand;
    private String droneKey;
    private String flightTime;
    private String loadCapacity;
    private String speed;
    private String altitude;
    public Drone()
    {
    	this.id=0;
        this.modelName="";
        this.modelNumber="";
        this.brand="";
        this.droneKey="";
        this.flightTime="";
        this.loadCapacity="";
        this.speed="";
        this.altitude="";
    }
    public void setId(Integer id)
    {
        this.id=id;
    }
    public Integer getId()
    {
        return this.id;
    }
    public void setModelName(String modelName)
    {
        this.modelName=modelName;
    }
    public String getModelName()
    {
        return this.modelName;
    }
    public void setModelNumber(String modelNumber)
    {
        this.modelNumber=modelNumber;
    }
    public String getModelNumber()
    {
        return this.modelNumber;
    }
    public void setBrand(String brand)
    {
        this.brand=brand;
    }
    public String getBrand()
    {
        return this.brand;
    }
    public void setDroneKey(String droneKey)
    {
        this.droneKey=droneKey;
    }
    public String getDroneKey()
    {
        return this.droneKey;
    }
    public void setFlightTime(String flightTime)
    {
        this.flightTime=flightTime;
    }
    public String getFlightTime()
    {
        return this.flightTime;
    }
    public void setLoadCapacity(String loadCapacity)
    {
        this.loadCapacity=loadCapacity;
    }
    public String getLoadCapacity()
    {
        return this.loadCapacity;
    }
    public void setSpeed(String speed)
    {
        this.speed=speed;
    }
    public String getSpeed()
    {
        return this.speed;
    }
    public void setAltitude(String altitude)
    {
        this.altitude=altitude;
    }
    public String getAltitude()
    {
        return this.altitude;
    }
}