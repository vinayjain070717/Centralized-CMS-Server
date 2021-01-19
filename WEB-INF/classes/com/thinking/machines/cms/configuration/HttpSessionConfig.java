package com.thinking.machines.cms.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.*;
import org.springframework.beans.factory.annotation.*;

@Configuration
public class HttpSessionConfig implements HttpSessionListener
{
	@Autowired
	private ServletContext servletContext;
    @Override
    public void sessionCreated(HttpSessionEvent se) 
    {
    	System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++");
    	System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++");
    	System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++");
    	System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++");
    	System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("Session Created with session id+" + se.getSession().getId());
        // se.getSession().setAttribute("hello","abcd");
    }
    @Override
    public void sessionDestroyed(HttpSessionEvent se)
    {
    	try
    	{
    	System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++");
    	System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++");
    	System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++");
    	System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++");
    	System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++");
    	// servletContext.removeAttribute("admin");
    	se.getSession().getServletContext().removeAttribute("admin");
    	se.getSession().removeAttribute("admin");
        System.out.println("Session Destroyed, Session id:" + se.getSession().getId());
    	}catch(Exception exception)
    	{
    		System.out.println("session destroy exception "+exception);
    	}
    }
}
