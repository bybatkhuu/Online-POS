package models;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Item implements Serializable
{
	private int id;
	private String name;
	private int unitID;
	private String unit;
	private String assetAcc;
	private double quantity;
	private double price;
	private double discountPrice;
	private float discountPercent;
	private double total;
	private double discountTotal;
	private String description;
	/*private double salePrice;
	private double saleQuantity;*/
	
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
	
	public double getPrice()
	{
		if (discountPrice > 0)
		{
			return discountPrice;
		}
		return price;
	}
	public void setPrice(double price)
	{
		this.price = price;
		this.total = this.quantity * this.price;
		if (this.discountPrice <= 0)
		{
			this.discountTotal = this.total - (((this.price * (100 - this.discountPercent)) / 100) * this.quantity);
		}
	}

	public double getQuantity()
	{
		return quantity;
	}
	public void setQuantity(double quantity)
	{
		this.quantity = quantity;
		if (this.discountPrice <= 0)
		{
			this.total = this.quantity * this.price;
			this.discountTotal = this.total - (((this.price * (100 - this.discountPercent)) / 100) * this.quantity);
		}
		else
		{
			this.total = this.quantity * this.discountPrice;
			this.discountTotal = this.total - (((this.discountPrice * (100 - this.discountPercent)) / 100) * this.quantity);
		}
	}
	public void addQuantity(double quantity)
	{
		this.quantity = this.quantity + quantity;
		if (this.discountPrice <= 0)
		{
			this.total = this.quantity * this.price;
			this.discountTotal = this.total - (((this.price * (100 - this.discountPercent)) / 100) * this.quantity);
		}
		else
		{
			this.total = this.quantity * this.discountPrice;
			this.discountTotal = this.total - (((this.discountPrice * (100 - this.discountPercent)) / 100) * this.quantity);
		}
	}

	public String getAssetAcc()
	{
		return assetAcc;
	}
	public void setAssetAcc(String assetAcc)
	{
		this.assetAcc = assetAcc;
	}

	public double getDiscountPrice()
	{
		return discountPrice;
	}
	public void setDiscountPrice(double discountPrice)
	{
		if (discountPrice > 0)
		{
			this.discountPrice = discountPrice;
			this.total = this.quantity * this.discountPrice;
			this.discountTotal = this.total - (((this.discountPrice * (100 - this.discountPercent)) / 100) * this.quantity);
		}
	}
	
	public float getDiscountPercent()
	{
		return discountPercent;
	}
	public void setDiscountPercent(float discountPercent)
	{
		if (0 <= discountPercent && discountPercent <= 100)
		{
			this.discountPercent = discountPercent;
			if (this.discountPrice <= 0)
			{
				this.discountTotal = this.total - (((this.price * (100 - this.discountPercent)) / 100) * this.quantity);
			}
			else
			{
				this.discountTotal = this.total - (((this.discountPrice * (100 - this.discountPercent)) / 100) * this.quantity);
			}
		}
	}

	public double getTotal()
	{
		return total;
	}
	
	public double getDiscountTotal()
	{
		return discountTotal;
	}
	
	public boolean hasDiscountPrice()
	{
		if (discountPrice > 0)
		{
			return true;
		}
		return false;
	}

	public double getFirstPrice()
	{
		return price;
	}
}