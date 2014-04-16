package models;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Item implements Serializable
{
	private int id;
	private String name;
	private double price;
	private String description;
	private int unitID;
	private String unit;
	private double quantity;
	/*private double salePrice;
	private double saleQuantity;*/
	private double total;
	
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

	public double getQuantity()
	{
		return quantity;
	}
	public void setQuantity(double quantity)
	{
		this.quantity = quantity;
		this.total = this.quantity * this.price;
	}
	
	public void addQuantity(double quantity)
	{
		this.quantity = this.quantity + quantity;
		this.total = this.quantity * this.price;
	}

	public double getTotal()
	{
		return total;
	}
}
