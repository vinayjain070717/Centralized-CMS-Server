package com.thinking.machines.user;
import javax.websocket.*;
import javax.websocket.server.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.lang.reflect.*;
import java.util.*;
import com.thinking.machines.drone.pojo.*;
public class Authenticator extends ServerEndpointConfig.Configurator 
{
		// static List<Drone> drones=new LinkedList<>();
	public void modifyHandshake(ServerEndpointConfig conf,HandshakeRequest req,HandshakeResponse res)
	{
		ServletContext context=null;
		try
		{
			System.out.println("Authenticator chali");
			Field field=req.getClass().getDeclaredField("request");
			field.setAccessible(true);
			HttpServletRequest request=(HttpServletRequest) field.get(req);
			context=request.getServletContext();
			System.out.println("Servlet Context "+context);
		}catch(Exception exception)
		{
			System.out.println("Modify handshake Field exception : "+exception);
		}
		conf.getUserProperties().put("servletContext",context);
		System.out.println(context.getAttribute("webSocketDrones"));
		List<Drone> drones=(LinkedList<Drone>)context.getAttribute("webSocketDrones");
		if(req.getParameterMap().get("typeOfDevice").get(0).equalsIgnoreCase("drone"))
		{
			boolean isPresent=false;
			String inputDroneId=req.getParameterMap().get("id").get(0);
			for(Drone d:drones)
			{
				if(d.getId().equalsIgnoreCase(inputDroneId))
				{
					isPresent=true;
					break;
				}
			}
			if(!isPresent)
			{
				throw new RuntimeException("Invalid id");
			}
		}
		
	}
}
