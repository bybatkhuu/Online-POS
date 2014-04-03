package models;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Item implements Serializable
{
	private int id;
	private String name;
	private double price;
	private double calPrice;
	private String description;
	private int unitID;
	private String unit;
	private int serialID;
	private String serial;
	private double insDiscountPrice;
	private double quantity;
	private double total;
	private double calTotal;
	
	public Item()
	{
	}
	
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
	
	public double getPrice()
	{
		return price;
	}
	public void setPrice(double price)
	{
		this.price = price;
		this.total = this.quantity * this.price;
		this.calPrice = this.price - this.insDiscountPrice;
		this.calTotal = this.quantity * this.calPrice;
	}
	
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	public int getUnitID()
	{
		return unitID;
	}
	public void setUnitID(int unitID)
	{
		this.unitID = unitID;
	}
	
	public String getUnit()
	{
		return unit;
	}
	public void setUnit(String unit)
	{
		this.unit = unit;
	}
	
	public int getSerialID()
	{
		return serialID;
	}
	public void setSerialID(int serialID)
	{
		this.serialID = serialID;
	}
	
	public String getSerial()
	{
		return serial;
	}
	public void setSerial(String serial)
	{
		this.serial = serial;
	}

	public double getInsDiscountPrice()
	{
		return insDiscountPrice;
	}
	public void setInsDiscountPrice(double insDiscountPrice)
	{
		this.insDiscountPrice = insDiscountPrice;
		this.calPrice = this.price - this.insDiscountPrice;
		this.calTotal = this.quantity * this.calPrice;
	}
	
	public double getCalPrice()
	{
		return calPrice;
	}

	public double getQuantity()
	{
		return quantity;
	}
	public void setQuantity(double quantity)
	{
		this.quantity = quantity;
		this.total = this.quantity * this.price;
		this.calTotal = this.quantity * this.calPrice;
	}
	
	public void addQuantity(double quantity)
	{
		this.quantity = this.quantity + quantity;
		this.total = this.quantity * this.price;
		this.calTotal = this.quantity * this.calPrice;
	}

	public double getTotal()
	{
		return total;
	}
	
	public double getCalTotal()
	{
		return calTotal;
	}
}
