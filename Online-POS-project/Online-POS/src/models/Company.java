package models;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpSession;

import utils.Cell;
import utils.PostgreSQLJDBC;

@SuppressWarnings("serial")
public class Company implements Serializable
{
	private int id = 1;
	private String name;
	private String type;
	private String description;
	
	public Company(HttpSession session) throws SQLException
	{
		PostgreSQLJDBC db = new PostgreSQLJDBC();
		if (db.createConnection())
		{
			String[] parameter = { String.valueOf(id) };
			List<Cell> cellList = db.getCellList("bh_getCompany", parameter);
			if (cellList != null && cellList.size() > 0)
			{
				for (Cell cell : cellList)
				{
					switch (cell.getColumn())
					{
						case "name":
							name = cell.getValue();
							break;
						case "type":
							type = cell.getValue();
							break;
						case "description":
							description = cell.getValue();
							break;
						default:
							break;
					}
					session.setAttribute("company", this);
				}
			}
			else
			{
				name = "Company";
			}
		}
		else
		{
			name = "Company";
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