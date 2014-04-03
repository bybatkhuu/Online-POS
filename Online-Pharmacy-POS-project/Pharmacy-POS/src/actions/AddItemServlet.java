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
import models.User;
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
		
	    Item item = null;
	    boolean hasItem = false;
	    synchronized(session)
	    {
	    	List<Item> itemList = (List<Item>) session.getAttribute("itemList");
		    if (itemList == null)
		    {
		    	itemList = new ArrayList<Item>();
		    }
		    User user = (User) session.getAttribute("user");
		    String barcode = request.getParameter("barcode");
		    String serial = request.getParameter("serial");
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
		    if ((barcode != null) && (!barcode.trim().equals("")))
		    {
		    	try
		    	{
		    		if ((serial == null) || (serial.trim().equalsIgnoreCase("")))
		    		{
		    			item = findItem(barcode, itemList);
		    		}
		    		else
		    		{
		    			item = findItem(barcode, serial, itemList);
		    		}
				}
		    	catch (SQLException e)
		    	{
		    		System.out.print("Error: Can't findItem!\nMessage: " + e.toString());
				}
		    	
		    	if (item != null)
		    	{
		    		item.addQuantity(quantity);
		    		hasItem = true;
		    	}
		    	else
		    	{
		    		try
		    		{
		    			if (serial == null)
		    			{
		    				serial = "";
		    			}
		    			item = getItem(barcode, user.getBranchID(), serial, quantity);
		    			//item = new Item(barcode, user.getBranchID(), serial, quantity);
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
	    
	    DecimalFormat format = new DecimalFormat("###############.##");
	    String jsonString = "";
	    jsonString =
	    	"{" +
	    		"hasItem: '" + hasItem + "'," +
	    		"ID: '" + item.getId() + "'," +
	    		"name: '" + item.getName() + "'," +
	    		"price: '" + format.format(item.getPrice()) + "'," +
	    		"unit: '" + item.getUnit() + "'," +
	    		"serialID: '" + item.getSerialID() + "'," +
	    		"quantity: '" + format.format(item.getQuantity()) + "'," +
	    		"total: '" + format.format(item.getTotal()) + "'," +
	    		"calTotal: '" + format.format(item.getCalTotal()) + "'" +
	    	"}";
	    
	    response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    out.print(jsonString);
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
	private Item findItem(String barcode, String serial, List<Item> itemList) throws SQLException
	{
		PostgreSQLJDBC db = new PostgreSQLJDBC();
		String[] parameter = { barcode };
		Cell cell = db.getCell("bh_getItemId", parameter);
		if (cell != null)
		{
			int id = Integer.parseInt(cell.getValue());
			for (Item item : itemList)
			{
				if ((item.getId() == id) && (item.getSerial().equalsIgnoreCase(serial)))
				{
					return(item);
				}
			}
		}
		return(null);
	}
	
	private Item getItem(String barcode, int branchID, String serial, double quantity) throws SQLException
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
						case "ins_discount_price":
							if (cell.getValue() != null && !cell.getValue().trim().equalsIgnoreCase(""))
							{
								item.setInsDiscountPrice(Double.parseDouble(cell.getValue()));
							}
							else
							{
								item.setInsDiscountPrice(0);
							}
							break;
						default:
							break;
					}
				}
				
				if (serial.trim().equalsIgnoreCase(""))
				{
					String[] params = { String.valueOf(branchID), String.valueOf(item.getId()) };
					tmpCell = db.getCell("bh_getLastSerialId", params);
					item.setSerialID(Integer.parseInt(tmpCell.getValue()));
					
					String[] param = { tmpCell.getValue() };
					tmpCell = db.getCell("bh_getSerial", param);
					item.setSerial(tmpCell.getValue());
				}
				else
				{
					String[] params = { serial };
					tmpCell = db.getCell("bh_getSerialId", params);
					item.setSerialID(Integer.parseInt(tmpCell.getValue()));
					item.setSerial(serial);
				}
				
				String[] params = { String.valueOf(branchID), String.valueOf(item.getId()), String.valueOf(item.getSerialID()) };
				tmpCell = db.getCell("bh_getPrice", params);
				item.setPrice(Double.parseDouble(tmpCell.getValue()));
				item.setQuantity(quantity);
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
