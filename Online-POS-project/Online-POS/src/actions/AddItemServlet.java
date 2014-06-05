package actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Item;
import utils.Cell;
import utils.LoggedUser;
import utils.PostgreSQLJDBC;

@WebServlet("/add-item")
public class AddItemServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
    
	public AddItemServlet()
	{
        super();
    }

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		int status = LoggedUser.checkLogin(session);
		if (status != 1)
		{
			response.sendRedirect("login.jsp");
		}
		
		boolean itemIncrement = false;
		int itemID = 0;
	    Item item = null;
	    synchronized(session)
	    {
	    	List<Item> itemList = (List<Item>) session.getAttribute("itemList");
		    if (itemList == null)
		    {
		    	itemList = new ArrayList<Item>();
		    }
		    //User user = (User) session.getAttribute("user");
		    String barcode = request.getParameter("barcode");
		    String assetAcc = request.getParameter("assetAcc");
		    double quantity = 0;
		    try
		    {
		    	quantity = Double.parseDouble(request.getParameter("quantity"));
		    }
		    catch(Exception e)
		    {
		    	quantity = 0;
		    	System.out.println(e.toString());
		    }
		    
		    if ((barcode != null) && (!barcode.trim().equals("")) && (assetAcc != null) && (!assetAcc.trim().equals("")))
		    {
		    	try
		    	{
		    		item = findItem(barcode, itemList);
				}
		    	catch (SQLException e)
		    	{
		    		System.out.print("Error: Can't findItem!\nMessage: " + e.toString());
				}
		    	
		    	if (item != null)
		    	{
		    		item.addQuantity(quantity);
		    		itemIncrement = true;
		    		itemID = item.getId();
		    	}
		    	else
		    	{
		    		try
		    		{
		    			item = getNewItem(barcode, assetAcc, quantity);
					}
		    		catch (SQLException e)
		    		{
		    			System.out.println("Error: Can't create Item!\nMessage: " + e.toString());
					}
		    		itemList.add(item);
		    	}
		    }
		    session.setAttribute("itemList", itemList);
	    }
	    
	    List<Item> itemList = (List<Item>) session.getAttribute("itemList");
	    String str = "";
	    if (itemList != null && itemList.size() > 0)
	    {
	    	DecimalFormat format = new DecimalFormat("###############.##");
	    	for (int i = 0; itemList.size() > i ; i++)
	    	{
	    		if (!itemIncrement)
	    		{
		    		if (itemList.size() == i + 1)
		    		{
		    			str = str + "<tr class='success' id='" + itemList.get(i).getId() + "'>";
		    		}
		    		else
		    		{
		    			str = str + "<tr id='" + itemList.get(i).getId() + "'>";
		    		}
	    		}
	    		else
	    		{
	    			if (itemList.get(i).getId() == itemID)
		    		{
		    			str = str + "<tr class='success' id='" + itemList.get(i).getId() + "'>";
		    		}
		    		else
		    		{
		    			str = str + "<tr id='" + itemList.get(i).getId() + "'>";
		    		}
	    		}
	    		str = str + "<td>" + itemList.get(i).getName() + "</td>";
	    		str = str + "<td class='text-right'>" + format.format(itemList.get(i).getQuantity()) + "</td>";
	    		str = str + "<td>" + itemList.get(i).getUnit() + "</td>";
	    		str = str + "<td class='text-right'>" + format.format(itemList.get(i).getPrice())  + "</td>";
	    		str = str + "<td class='text-right'>" + format.format(itemList.get(i).getTotal())  + "</td>";
	    		str = str + "</tr>";
	    	}
	    }
	    
	    response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    out.print(str);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
	
	private Item findItem(String barcode, List<Item> itemList) throws SQLException
	{
		PostgreSQLJDBC db = new PostgreSQLJDBC();
		String[] parameter = { barcode };
		Cell cell = db.getCell("bh_getItemId", parameter);
		if (cell != null)
		{
			int id = Integer.parseInt(cell.getValue());
			for (Item item : itemList)
			{
				if (item.getId() == id)
				{
					return(item);
				}
			}
		}
		return(null);
	}
	
	private Item getNewItem(String barcode, String assetAcc, double quantity) throws SQLException
	{
		Item item = null;
		PostgreSQLJDBC db = new PostgreSQLJDBC();
		if (db.createConnection())
		{
			Cell tmpCell = null;
			String[] barcodeParam = { barcode };
			List<Cell> cellList = db.getCellList("bh_getItem", barcodeParam);
			if (cellList != null)
			{
				item = new Item();
				for (Cell cell : cellList)
				{
					switch (cell.getColumn())
					{
						case "id":
							item.setId(Integer.parseInt(cell.getValue()));
							break;
						case "name":
							item.setName(cell.getValue());
							break;
						case "description":
							item.setDescription(cell.getValue());
							break;
						case "item_unit_id":
							item.setUnitID(Integer.parseInt(cell.getValue()));
							break;
						default:
							break;
					}
				}
				
				String[] params = { assetAcc, String.valueOf(item.getId()) };
				tmpCell = db.getCell("bh_getPrice", params);
				item.setPrice(Double.parseDouble(tmpCell.getValue()));
				item.setQuantity(quantity);
				item.setAssetAcc(assetAcc);
			}
			
			tmpCell = null;
			String[] unitIdParam = { Integer.toString(item.getUnitID()) };
			tmpCell = db.getCell("bh_getUnit", unitIdParam);
			if (tmpCell != null && !tmpCell.getValue().trim().equalsIgnoreCase(""))
			{
				item.setUnit(tmpCell.getValue());
			}
			else
			{
				item.setUnit("ш");
			}
		}
		return item;
	}
}
