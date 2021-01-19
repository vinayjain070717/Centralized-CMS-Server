package com.thinking.machines.cms.services.model;
public class Administrator implements java.io.Serializable
{
    private String username;
    private String firstName;
    private String lastName;
    private String pwd;
    private String pwdKey;
    public Administrator()
    {
        this.username="";
        this.firstName="";
        this.lastName="";
        this.pwd="";
        this.pwdKey="";
    }
    public void setUsername(String username)
    {
        this.username=username;
    }
    public String getUsername()
    {
        return this.username;
    }
    public void setFirstName(String firstName)
    {
        this.firstName=firstName;
    }
    public String getFirstName()
    {
        return this.firstName;
    }
    public void setLastName(String lastName)
    {
        this.lastName=lastName;
    }
    public String getLastName()
    {
        return this.lastName;
    }
    public void setPwd(String pwd)
    {
        this.pwd=pwd;
    }
    public String getPwd()
    {
        return this.pwd;
    }
    public void setPwdKey(String pwdKey)
    {
        this.pwdKey=pwdKey;
    }
    public String getPwdKey()
    {
        return this.pwdKey;
    }
}