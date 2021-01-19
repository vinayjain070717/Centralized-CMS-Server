package com.thinking.machines.user.pojo;
import javax.websocket.*;
import com.thinking.machines.drone.common.response.*;
public class User
{
	private String id;
	private String username;
	private Session session;
	private String droneId;
	public User()
	{
		this.id="";
		this.username="";
		this.session=null;
		this.droneId=null;
	}
	public void setId(String id)
	{
		this.id=id;
	}
	public String getId()
	{
		return this.id;
	}
	public void setUsername(String username)
	{
		this.username=username;
	}
	public String getUsername()
	{
		return this.username;
	}
	public void setSession(Session session)
	{
		this.session=session;
	}
	public Session getSession()
	{
		return this.session;
	}
	public void setDroneId(String droneId)
	{
		this.droneId=droneId;
	}
	public String getDroneId()
	{
		return this.droneId;
	}
}