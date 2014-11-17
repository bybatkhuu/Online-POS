package models;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Cash implements Serializable
{
	private int id;
	private String ip;
	private int posNum;
	private String assetType;
	private String assetAcc;
	private String humanType;
	
	public Cash()
	{
	}
	public Cash(Cash cash)
	{
		this.id = cash.getId();
		this.ip = cash.getIp();
		this.posNum = cash.getPosNum();
		this.humanType = cash.getHumanType();
	}
	public Cash(int id, String ip, int posNum)
	{
		this.id = id;
		this.ip = ip;
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
	
	public int getPosNum()
	{
		return posNum;
	}
	public void setPosNum(int posNum)
	{
		this.posNum = posNum;
	}
	
	public String getAssetType()
	{
		return assetType;
	}
	public void setAssetType(String assetType)
	{
		this.assetType = assetType;
	}
	
	public String getAssetAcc()
	{
		return assetAcc;
	}
	public void setAssetAcc(String assetAcc)
	{
		this.assetAcc = assetAcc;
	}
	public String getHumanType() {
		return humanType;
	}
	public void setHumanType(String humanType) {
		this.humanType = humanType;
	}
	
}