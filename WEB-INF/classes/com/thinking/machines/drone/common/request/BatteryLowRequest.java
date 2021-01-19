package com.thinking.machines.drone.common.request;
public class BatteryLowRequest
{
    private int batteryLeft;
    public BatteryLowRequest()
    {
        this.batteryLeft=0;
    }      
    public void setBatteryLeft(int timeLeft)
    {
        this.batteryLeft=timeLeft;
    }
    public int getBatteryLeft()
    {
        return this.batteryLeft;
    }
}