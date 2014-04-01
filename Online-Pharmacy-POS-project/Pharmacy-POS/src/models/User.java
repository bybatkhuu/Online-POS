package models;

import java.io.Serializable;

@SuppressWarnings("serial")
public class User implements Serializable
{
	private int id;
	private String userName;
	private String password;
	private String status;
	private int branchID;
	private String ipAddress;
	private int pos;
	private int roleID;
	
	public User()
	{
	}
	public User(User user)
	{
		this.id = user.getId();
		this.userName = user.getUserName();
		this.password = user.getPassword();
		this.status = user.getStatus();
		this.branchID = user.getBranchID();
		this.ipAddress = user.getIpAddress();
		this.pos = user.getPos();
		this.roleID = user.getRoleID();
	}
	public User(int id, String userName, String password, String status, int branchID, int pos, int roleID)
	{
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.status = status;
		this.branchID = branchID;
		this.pos = pos;
		this.roleID = roleID;
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
	
	public int getBranchID()
	{
		return branchID;
	}
	public void setBranchID(int branchID)
	{
		this.branchID = branchID;
	}
	
	public String getIpAddress()
	{
		return ipAddress;
	}
	public void setIpAddress(String ipAddress)
	{
		this.ipAddress = ipAddress;
	}
	
	public int getPos()
	{
		return pos;
	}
	public void setPos(int pos)
	{
		this.pos = pos;
	}
	
	public int getRoleID()
	{
		return roleID;
	}
	public void setRoleID(int roleID)
	{
		this.roleID = roleID;
	}
}
