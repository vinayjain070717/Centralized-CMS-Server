package com.thinking.machines.cms.services.utility;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import com.google.gson.*;

public class SMSUtility
{
    private long generationTime;
    public static String sendCampaign(String apiKey,String secretKey,String useType, String phone, String message, String senderId){
      try{
          // construct data
        JsonObject urlParameters = new JsonObject();
        urlParameters.addProperty("apikey",apiKey);
        urlParameters.addProperty("secret",secretKey);
        urlParameters.addProperty("usetype",useType);
        urlParameters.addProperty("phone", phone);
        urlParameters.addProperty("message", URLEncoder.encode(message,"UTF-8"));
        urlParameters.addProperty("senderid", senderId);
        String url = "https://www.sms4india.com";
        URL obj = new URL(url + "/api/v1/sendCampaign");
          // send data
        HttpURLConnection httpConnection = (HttpURLConnection) obj.openConnection();
        httpConnection.setDoOutput(true);
        httpConnection.setRequestMethod("POST");
        DataOutputStream wr = new DataOutputStream(httpConnection.getOutputStream());
        wr.write(urlParameters.toString().getBytes());
        // get the response  
        BufferedReader bufferedReader = null;
        if (httpConnection.getResponseCode() == 200) {
            bufferedReader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
        } else {
            bufferedReader = new BufferedReader(new InputStreamReader(httpConnection.getErrorStream()));
        }
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            content.append(line).append("\n");
        }
        bufferedReader.close();
        return content.toString();
      }catch(Exception ex){
        System.out.println("Exception at:"+ex);
        return "{'status':500,'message':'Internal Server Error'}";
      }
        
    }
}