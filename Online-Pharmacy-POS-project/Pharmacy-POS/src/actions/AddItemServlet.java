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

import models.Card;
import models.Item;
import utils.Cell;
import utils.LoggedUser;
import utils.PostgreSQLJDBC;

@WebServlet("/add-item")
public class AddItemServlet extends HttpServlet implements PosLogger
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
	    Float disCount;
	    synchronized(session)
	    {
	    	Card card = (Card) session.getAttribute("card");
	    	disCount = (Float) session.getAttribute("discountPercent");
	    	
	    	List<Item> itemList = (List<Item>) session.getAttribute("itemList");
		    if (itemList == null)
		    {
		    	itemList = new ArrayList<Item>();
		    }
		    String barcode = request.getParameter("barcode");
		    String serial = request.getParameter("serial");
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
		    		item = findItem(barcode , serial, itemList, assetAcc);
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
		    			if (serial == null)
		    			{
		    				serial = "";
		    			}
		    			item = getNewItem(barcode, assetAcc, serial, quantity);
		    			if (card != null ||disCount !=null)
			    		{
			    			item.setDiscountPercent(disCount);
			    		}
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
	    		str = str + "<td class='text-right'>" + format.format(itemList.get(i).getDiscountPercent())  + "</td>";
	    		str = str + "<td class='hidden discountTotal'>" + format.format(itemList.get(i).getDiscountTotal())  + "</td>";
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
	
	private Item findItem(String barcode, String serial, List<Item> itemList , String assetAcc) throws SQLException
	{
		PostgreSQLJDBC db = new PostgreSQLJDBC();
		String[] parameter = { barcode };
		Cell cell = db.getCell("bh_getItemId", parameter);
		if (cell != null)
		{
			int id = Integer.parseInt(cell.getValue());
			for (Item item : itemList)
			{
				if (item.getId() == id && item.getSerial().equals(serial))
				{
					return(item);
				}
				if (item.getId() == id && serial.equals(""))
				{
					Cell tmpCell = null;
					String[] params = { assetAcc, String.valueOf(item.getId()) };
					tmpCell = db.getCell("bh_getLastSerialId", params);
					String[] param = { tmpCell.getValue() };
					tmpCell = db.getCell("bh_getSerial", param);
					serial = tmpCell.getValue();
					if (item.getSerial().equals(serial))
					{
						return(item);
					}
				}
			}
		}
		return(null);
	}
	
	private Item getNewItem(String barcode, String assetAcc, String serial, double quantity) throws SQLException
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
						case "facturename":
							item.setFactureName(cell.getValue());
							break;
						case "facturecode":
							item.setFactureCode(cell.getValue());
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
					String[] params = { assetAcc, String.valueOf(item.getId()) };
					tmpCell = db.getCell("bh_getLastSerialId", params);
					item.setSerialID(Integer.parseInt(tmpCell.getValue()));
					
					String[] param = { tmpCell.getValue() };
					tmpCell = db.getCell("bh_getSerial", param);
					item.setSerial(tmpCell.getValue());
				}
				else
				{
					String[] params = { serial, assetAcc, String.valueOf(item.getId()) };
					tmpCell = db.getCell("bh_getSerialId", params);
					item.setSerialID(Integer.parseInt(tmpCell.getValue()));
					item.setSerial(serial);
				}
				
				String[] params = { assetAcc, String.valueOf(item.getId()), String.valueOf(item.getSerialID()) };
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
		log(item, serial);
		return item;
	}

	@Override
	public void log(Object obj, String message) {
		Item item = (Item)obj;
		LOG.info("AddItem -- "+" ID: "+ item.getId() + sep + " AssetAcc: " +item.getAssetAcc() + sep+
				" SerialId: " + item.getSerialID() +sep+ " Serial: " + message
				);
	}

	
}
