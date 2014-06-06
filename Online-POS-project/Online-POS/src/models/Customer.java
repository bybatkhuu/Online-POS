package models;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Customer implements Serializable
{
	private int id;
	private String name;
	private String desciption;
	
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getDesciption()
	{
		return desciption;
	}
	public void setDesciption(String desciption)
	{
		this.desciption = desciption;
	}
}