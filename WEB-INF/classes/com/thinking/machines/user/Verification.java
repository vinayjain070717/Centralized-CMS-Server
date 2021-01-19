package com.thinking.machines.user;
import java.sql.*;
import java.util.*;
import com.thinking.machines.connection.*;
public class Verification
{
	private Set<Integer> ids=Collections.synchronizedSet(new HashSet<>());
	public Verification()
	{
		try
		{
			Connection connection=new DatabaseConnection().getConnection();
			Statement s=connection.createStatement();
			ResultSet rs=s.executeQuery("select * from devices");
			while(rs.next())
			{
				ids.add(rs.getInt("id"));
			}
		}catch(Exception exception)
		{
			System.out.println("Verification exception : "+exception);
		}
	}
	public boolean verifyId(int id)
	{
		return ids.contains(id);
	}
}