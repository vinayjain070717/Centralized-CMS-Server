package com.thinking.machines.drone.common.response;
public class ReleaseDroneResponse
{
    private String disconnect;
    private String reason;
    public ReleaseDroneResponse()
    {
        disconnect=null;
        reason=null;
    }
    public void setDisconnect(String disconnect)
    {
    this.disconnect=disconnect;
    }
    public String getDisconnect()
    {
    return this.disconnect;
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