package com.thinking.machines.drone.common;
import java.util.*;
import com.google.gson.*;
import com.thinking.machines.drone.common.request.*;

public class Message
{
    private int typeId;
    private String id;
    private String message;
    private String dateTime;
    private JsonObject payload;
    public Message()
    {
        this.typeId=0;
        this.id="";
        this.message=null;
        this.dateTime=null;
        this.payload=null;
    }
    public void setTypeId(int typeId)
    {
        this.typeId=typeId;
    }
    public int getTypeId()
    {
        return this.typeId;
    }
    public void setId(String meesageId)
    {
        this.id=meesageId;
    }
    public String getId()
    {
        return this.id;
    }
    public void setMessage(String message)
    {
        this.message=message;
    }
    public String getMessage()
    {
        return this.message;
    }
    public void setDateTime(String dateTime)
    {
        this.dateTime=dateTime;
    }
    public String getDateTime()
    {
        return this.dateTime;
    }
    public void setPayload(JsonObject payload)
    {
        this.payload=payload;
    }
    public JsonObject getPayload()
    {
        return this.payload;
    }
    public Object getRequest()
    {
        Object object=null;
        try
        {
        Gson gson=new Gson();
        System.out.println("Message class message "+message);
        System.out.println("Message class payload "+payload);
        String commonPackageName="com.thinking.machines.drone.common.";
        if(typeId==1) commonPackageName=commonPackageName+"request.";
        if(typeId==2) commonPackageName=commonPackageName+"response.";
        Class c=Class.forName(commonPackageName+message);
        object=gson.fromJson(payload,c);
        // Command command=(Command)o;
        }catch(Exception exception)
        {
            System.out.println("Message getRequest exception "+exception);
        }
        return object;
    }

}