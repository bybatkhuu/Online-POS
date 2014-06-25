package models;

import java.io.Serializable;

@SuppressWarnings("serial")
public class User implements Serializable
{
	private int id;
	private String userName;
	private String password;
	private String status;
	private int roleID;
	private String cashName;
	
	public User()
	{
	}
	public User(User user)
	{
		this.id = user.getId();
		this.userName = user.getUserName();
		this.password = user.getPassword();
		this.status = user.getStatus();
		this.roleID = user.getRoleID();
		this.cashName = user.getCashName();
	}
	public User(int id, String userName, String password, String status, int roleID, String cashName)
	{
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.status = status;
		this.roleID = roleID;
		this.cashName = cashName;
	}
	
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	
	public String getUserName()
	{
		return userName;
	}
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
	
	public int getRoleID()
	{
		return roleID;
	}
	public void setRoleID(int roleID)
	{
		this.roleID = roleID;
	}
	
	public String getCashName()
	{
		return cashName;
	}
	public void setCashName(String value)
	{
		this.cashName = value;
	}
}