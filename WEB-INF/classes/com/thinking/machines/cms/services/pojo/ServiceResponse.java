package com.thinking.machines.cms.services.pojo;
public class ServiceResponse implements java.io.Serializable
{
    public Boolean success=true;
    public Boolean isException=false;
    public Boolean isError=false;
    public Boolean hasResult=false;
    public String exception="";
    public String error="";
    public Object result=null;
}