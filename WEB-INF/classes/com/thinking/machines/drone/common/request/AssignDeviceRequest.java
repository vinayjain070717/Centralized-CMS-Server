package com.thinking.machines.drone.common.request;
public class AssignDeviceRequest
{
    private String deviceId;
    public AssignDeviceRequest()
    {
        this.deviceId="";
    }
    public void setDeviceId(String deviceId)
    {
        this.deviceId=deviceId;
    }
    public String getDeviceId()
    {
        return this.deviceId;
    }
}