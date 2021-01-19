package com.thinking.machines.cms.services;
import com.thinking.machines.cms.services.model.*;
import com.thinking.machines.cms.services.pojo.*;
import java.util.*;
import com.thinking.machines.cms.dl.exceptions.*;
import com.thinking.machines.cms.dl.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;
import java.io.*;
import java.net.*;
import com.google.gson.*;
import java.sql.*;
class LogRecords
{
	public String key;
	public List<String> value;
}

@RestController
public class LoggingService
{
    @Autowired
    private CMSModel cmsModel;
    @PostMapping("cms/retrieve")
    public ServiceResponse retrieveLogs(@RequestParam(name="applicationName")String applicationName,@RequestParam(name="date")String date)
    {
    	ServiceResponse serviceResponse=new ServiceResponse();
    	try
    	{
    		String req=applicationName+","+date+"#";
			System.out.println(req);
			int portNumber=3000;
			OutputStream outputStream;
			Socket socket;
			OutputStreamWriter outputStreamWriter;
			InputStream inputStream;
			InputStreamReader inputStreamReader;
			StringBuffer stringBuffer;
			socket=new Socket("localhost",portNumber);
			outputStream=socket.getOutputStream();
			outputStreamWriter=new OutputStreamWriter(outputStream);
			outputStreamWriter.write(req);
			outputStreamWriter.flush();
			// System.out.println("1");
			inputStream=socket.getInputStream();
			// System.out.println("2");
			inputStreamReader=new InputStreamReader(inputStream);
			// System.out.println("3");
			stringBuffer=new StringBuffer();
			// System.out.println("4");
			int x;
			// System.out.println("5");
			while(true)
			{
			// System.out.println("6");
				x=inputStreamReader.read();
				if(x==-1)break;
				stringBuffer.append((char)x);
			}
			// System.out.println("7");
			String jsonString=stringBuffer.toString();
			// System.out.println("8");
			Gson gson=new Gson();
			// System.out.println("9");
			LogRecords logRecords=gson.fromJson(jsonString,LogRecords.class);
			// System.out.println("10");
			socket.close();
			// System.out.println("11");
			// System.out.println(jsonString);
			// System.out.println("12");
			List<String> lr=logRecords.value;
			serviceResponse.hasResult=true;
            serviceResponse.result=lr;
            return serviceResponse;
    	}catch(Exception exception)
    	{
    		System.out.println("Retrieve logs exception"+exception);
    		serviceResponse.success=false;
            serviceResponse.isException=true;
            serviceResponse.error=exception.getMessage();
            return serviceResponse;  
    	}
	}
	public void forwardRequestToLoggingServer(String payload)
	{
		try
		{
			System.out.println("Forwarding request to logging server");
			OutputStream outputStream;
			OutputStreamWriter outputStreamWriter;
			InputStream inputStream;
			InputStreamReader inputStreamReader;
			int portNumber=1000;
			String serverName="localhost";
			Timestamp currentTime;
			currentTime=new Timestamp(new java.util.Date().getTime());
			String request="Web Application,"+currentTime.toString()+","+payload+"#";
			System.out.println(request);
			Socket socket=new Socket(serverName,portNumber);
			outputStream=socket.getOutputStream();
			outputStreamWriter=new OutputStreamWriter(outputStream);
			outputStreamWriter.write(request);
			outputStreamWriter.flush();
			socket.close();
		}catch(Exception exception)
		{
			System.out.println("Forward request to server exception "+exception);
		}
	}

}