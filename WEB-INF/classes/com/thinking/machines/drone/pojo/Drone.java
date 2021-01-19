package com.thinking.machines.drone.pojo;
import javax.websocket.*;
import com.thinking.machines.drone.common.response.*;
import com.thinking.machines.drone.common.request.*;
public class Drone
{
	private String id;
	private String name;
	private Session session;
	private StatusNotificationRequest droneStatus;
	private boolean isConnected;
	public Drone()
	{
		this.id="";
		this.name="";
		this.session=null;
		this.droneStatus=null;
		this.isConnected=false;
	}
	public void setId(String id)
	{
		this.id=id;
	}
	public String getId()
	{
		return this.id;
	}
	public void setName(String name)
	{
		this.name=name;
	}
	public String getName()
	{
		return this.name;
	}
	public void setSession(Session session)
	{
		this.session=session;
	}
	public Session getSession()
	{
		return this.session;
	}
	public void setDroneStatus(StatusNotificationRequest droneStatus)
	{
		this.droneStatus=droneStatus;
	}
	public StatusNotificationRequest getDroneStatus()
	{
		return this.droneStatus;
	}
	public void setIsConnected(boolean isConnected)
	{
		this.isConnected=isConnected;
	}
	public boolean getIsConnected()
	{
		return this.isConnected;
	}
}