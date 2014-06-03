package models;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Cash implements Serializable
{
	private int id;
	private String ip;
	private int branchID;
	private int posNum;
	
	public Cash()
	{
	}
	public Cash(Cash cash)
	{
		this.id = cash.getId();
		this.ip = cash.getIp();
		this.branchID = cash.getBranchID();
		this.posNum = cash.getPosNum();
	}
	public Cash(int id, String ip, int branchID, int posNum)
	{
		this.id = id;
		this.ip = ip;
		this.branchID = branchID;
		this.posNum = posNum;
	}
	
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	
	public String getIp()
	{
		return ip;
	}
	public void setIp(String ip)
	{
		this.ip = ip;
	}
	
	public int getBranchID()
	{
		return branchID;
	}
	public void setBranchID(int branchID)
	{
		this.branchID = branchID;
	}
	
	public int getPosNum()
	{
		return posNum;
	}
	public void setPosNum(int posNum)
	{
		this.posNum = posNum;
	}
}