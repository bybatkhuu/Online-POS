package models;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import utils.Cell;
import utils.PostgreSQLJDBC;

@SuppressWarnings("serial")
public class Company implements Serializable
{	
	private int id;
	private String name;
	private String type;
	private String description;
	
	public Company()
	{
	}
	
	public static Company getInstance() throws SQLException
	{
		int id = 1;
		Company company = null;
		PostgreSQLJDBC db = new PostgreSQLJDBC();
		if (db.createConnection())
		{
			String[] parameter = { String.valueOf(id) };
			List<Cell> cellList = db.getCellList("bh_getCompany", parameter);
			if (cellList != null && cellList.size() > 0)
			{
				company = new Company();
				company.setId(id);
				for (Cell cell : cellList)
				{
					switch (cell.getColumn())
					{
						case "name":
							company.setName(cell.getValue());
							break;
						case "type":
							company.setType(cell.getValue());
							break;
						case "description":
							company.setDescription(cell.getValue());
							break;
						default:
							break;
					}
				}
			}
		}
		return company;
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
	
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
}