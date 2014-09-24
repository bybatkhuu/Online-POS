package actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
import models.User;
import utils.Cell;
import utils.LoggedUser;
import utils.PostgreSQLJDBC;

@WebServlet("/purchase-itemsFacture")
public class PurchaseFactureServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

    public PurchaseFactureServlet()
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
		
		boolean isPurchased = false;
		String resultOrderNum = "0";
		
		User user = (User) session.getAttribute("user");
		String orderNum = request.getParameter("orderNum");
		String type = request.getParameter("type");
		String corporationName = request.getParameter("nameShow");
		String corporationReg = request.getParameter("numberShow");
		if (type != null && !type.trim().equalsIgnoreCase(""))
		{
			String otherId = request.getParameter("otherId");
			if (otherId == null)
			{
				otherId = "0";
			}
			
			synchronized(session)
		    {
				if (session.getAttribute("itemList") != null)
		    	{
					Card card = (Card) session.getAttribute("card");
					List<Item> itemList = (List<Item>) session.getAttribute("itemList");
			    	if (itemList != null)
			    	{
			    		PostgreSQLJDBC db = new PostgreSQLJDBC();
			    		if (db.createConnection())
			    		{
			    			try
			    			{
			    				String cardId = "0";
					    		if (card != null)
					    		{
					    			cardId = String.valueOf(card.getId());
					    		}
			    				String[] parameters = { Integer.toString(user.getId()), orderNum, type, otherId, cardId ,"true",corporationName,corporationReg};
			    				Cell orderId = db.getCell("bh_purchase_facture", parameters);
			    				Cell cell = null;
			    				for (Item item : itemList)
			    				{
			    					List<Cell> cellList = new ArrayList<Cell>();
			    					cell = new Cell("item_id", Integer.toString(item.getId()));
			    					cellList.add(cell);
			    					cell = new Cell("order_id", orderId.getValue());
			    					cellList.add(cell);
			    					cell = new Cell("quantity", Double.toString(item.getQuantity()));
			    					cellList.add(cell);
			    					cell = new Cell("price", Double.toString(item.getFirstPrice()));
			    					cellList.add(cell);
			    					cell = new Cell("total", Double.toString(item.getTotal()));
			    					cellList.add(cell);
			    					cell = new Cell("card_discount_percent", Float.toString(item.getDiscountPercent()));
			    					cellList.add(cell);
			    					if (item.hasDiscountPrice())
			    					{
			    						cell = new Cell("discount_price", Double.toString(item.getDiscountPrice()));
			    						cellList.add(cell);
			    					}
			    					cell = new Cell("asset_acc", item.getAssetAcc());
			    					cellList.add(cell);
			    					db.insert("pos_transactions", cellList);
			    				}
			    				cell = null;
			    				String[] parameter = { Integer.toString(user.getId()) };
			    				cell = db.getCell("bh_getLastOrderNum", parameter);
			    				resultOrderNum = cell.getValue();
							}
			    			catch (SQLException e)
			    			{
								System.out.println("Error: Can't insert transaction in purchase!\nMessage: " + e.toString());
							}
			    			isPurchased = true;
			    			session.removeAttribute("card");
			    			session.removeAttribute("discountPercent");
			    		}
			    	}
		    	}
		    }
		}
		
		String jsonString =
			"{" +
				"isPurchased: '" + isPurchased + "'," +
				"talon: '" + resultOrderNum + "'" +
			"}";
		
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
		out.println(jsonString);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
}