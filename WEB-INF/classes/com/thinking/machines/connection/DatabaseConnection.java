package com.thinking.machines.connection;
import java.sql.*;
public class DatabaseConnection
{
	private Connection connection;
	public DatabaseConnection()
	{
		this.connection=null;
	}
	public Connection getConnection()
	{
		try
		{
		Class.forName("com.mysql.jdbc.Driver");
		if(this.connection==null) this.connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/cmsdb","cmsuser","cmsuser");
		}catch(Exception exception)
		{
			System.out.println("Database connection get connection exception "+exception);
		}
		return this.connection;
	}
}