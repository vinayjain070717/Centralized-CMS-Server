import java.util.*;
import java.io.*;
import java.util.regex.*;
import com.google.gson.*;
import com.thinking.machines.drone.common.*;

public class Parser2
{
    private String jsonString;
    public Parser2(String jsonString)
    {
        this.jsonString=jsonString;
    }
    // public void example()
    // {
    //     try
    //     {
    //         Message m=getMessageObject(this.jsonString);
    //         int typeId;
    //         String id,message;
    //         Date dateTime;
    //         typeId=m.getTypeId();
    //         id=m.getId();
    //         dateTime=m.getDateTime();
    //         message=m.getMessage();
    //         // System.out.println(typeId);
    //         // System.out.println(message);
    //         if(typeId==1)
    //         {
    //             if(message.equalsIgnoreCase("CommandRequest"))
    //             {
    //                 CommandRequest commandRequest=(CommandRequest)m.getRequest();
    //                 System.out.println("Move "+commandRequest.getMove());
    //                 System.out.println("Yaw "+commandRequest.getYaw()+"\nPitch "+commandRequest.getPitch());
    //                 System.out.println("Throttle "+commandRequest.getThrottle());
    //             }
    //         }
    //         // parse(content);
    //      }catch(Exception exception)
    //     {
    //         System.out.println("Parser main Exception "+exception);
    //     }
    // }
    
    public static Message parse(String json)
    {
        Message message=null;
        try
        {
        String payload=json.substring(json.indexOf("{"),json.indexOf("}")+1);
        // System.out.println(payload);
        Pattern p1 = Pattern.compile("(\\w+):((?:[\\w-:. ]+)|(?:\\{.*\\}))");

        Matcher m=p1.matcher(json);
        // Map<String,Object> map=new LinkedHashMap<>();
        String key,value;
        String makeJsonString="{";
        while(m.find())
        {
            key=m.group(1);
            value=m.group(2);
            key="\""+key+"\"";
            // if(!Pattern.matches("^[\\d.]+$",value) && !Pattern.matches("^\\{.*\\}$",value) && Pattern.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d{3}",value))
            if(!Pattern.matches("^[\\d.]+$",value) && !Pattern.matches("^\\{.*\\}$",value))
            {
                value="\""+value+"\"";
            }
            
            makeJsonString=makeJsonString+key+":"+value+",";
        }
        makeJsonString=makeJsonString.substring(0,makeJsonString.length()-1);
        makeJsonString=makeJsonString+"}";
        System.out.println(makeJsonString);
        Gson gson=new Gson();
        message=gson.fromJson(makeJsonString,Message.class);
        // String className=message.getMessage();
        // Class c=Class.forName(className);
        // ClassLoader cl=c.getClassLoader();
        // Object o=gson.fromJson(payload,c);
        // Command command=(Command)o;
        // System.out.println(command.getMove());
        // System.out.println(makeJsonString);
        // System.out.println(message.getTypeId());
        }catch(Exception exception)
        {
            System.out.println("Parser parse exception "+exception);
        }
        return message;
        // return message;

        // Object o2=new Command();
        // System.out.println()
        // System.out.println(o);
        // System.out.println(o.getMove());
        // System.out.println(o.getThrottle());
        // // System.out.println(message);
        // System.out.println(message.getMessageTypeId());
        // System.out.println(message.getMessage());
        // return map;
    }
    
}