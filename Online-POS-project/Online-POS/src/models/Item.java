package models;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import utils.Cell;
import utils.PostgreSQLJDBC;

@SuppressWarnings("serial")
public class Item implements Serializable
{
	private int id;
	private String name;
	private double price;
	private double calPrice;
	private String description;
	private String unit;
	private double insDiscountPrice;
	private double quantity;
	private double total;
	private double calTotal;
	
	public Item()
	{
	}
	
	public Item(String barcode, int branchID, double quantity) throws SQLException
	{
		PostgreSQLJDBC db = new PostgreSQLJDBC();
		Cell tmpCell = null;
		if (db.createConnection())
		{
			int unitId = 0;
			String[] barcodeParam = { barcode };
			List<Cell> cellList = db.getCellList("bh_getItem", barcodeParam);
			if (cellList != null)
			{
				for (Cell cell : cellList)
				{
					switch (cell.getColumn())
					{
						case "id":
							this.id = Integer.parseInt(cell.getValue());
							break;
						case "name":
							this.name = cell.getValue();
							break;
						case "description":
							this.description = cell.getValue();
							break;
						case "item_unit_id":
							unitId = Integer.parseInt(cell.getValue());
							break;
						case "ins_discount_price":
							if (cell.getValue() != null && !cell.getValue().trim().equalsIgnoreCase(""))
							{
								this.insDiscountPrice = Double.parseDouble(cell.getValue());
							}
							else
							{
								this.insDiscountPrice = 0;
							}
							break;
						default:
							break;
					}
				}
				
				String[] params = { String.valueOf(branchID), String.valueOf(this.id) };
				tmpCell = db.getCell("bh_getPrice", params);
				this.price = Double.parseDouble(tmpCell.getValue());
				
				this.quantity = quantity;
				this.total = this.quantity * this.price;
				this.calPrice = this.price - this.insDiscountPrice;
				this.calTotal = this.quantity * this.calPrice;
			}
			
			tmpCell = null;
			String[] unitIdParam = { Integer.toString(unitId) };
			tmpCell = db.getCell("bh_getUnit", unitIdParam);
			if (tmpCell != null)
			{
				this.unit = tmpCell.getValue();
			}
			else
			{
				this.unit = "ш";
			}
		}
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
	
	public String getUnit()
	{
		return unit;
	}
	public void setUnit(String unit)
	{
		this.unit = unit;
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
