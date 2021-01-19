package com.thinking.machines.drone.common.request;
public class CommandRequest
{
    private String move;
    private String roll;
    private String pitch;
    private String yaw;
    private String throttle;
    private boolean land;
    public CommandRequest()
    {
        this.move=null;
        this.roll=null;
        this.pitch=null;
        this.yaw=null;
        this.throttle=null;
        this.land=false;
    }
    public void setMove(String move)
    {
        this.move=move;
    }
    public void setRoll(String roll)
    {
        this.roll=roll;
    }
    public void setPitch(String pitch)
    {
        this.pitch=pitch;
    }
    public void setYaw(String yaw)
    {
        this.yaw=yaw;
    }
    public void setThrottle(String throttle)
    {
        this.throttle=throttle;
    }
    public void setLand(boolean land)
    {
        this.land=land;
    }
    public String getMove()
    {
        return this.move;
    }
    public String getRoll()
    {
        return this.roll;
    }
    public String getPitch()
    {
        return this.pitch;
    }
    public String getYaw()
    {
        return this.yaw;
    }
    public String getThrottle()
    {
        return this.throttle;
    }
    public boolean getLand()
    {
        return this.land;
    }
}