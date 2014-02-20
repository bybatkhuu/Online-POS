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

import models.Item;
import models.User;

import utils.Cell;
import utils.LoggedUser;
import utils.PostgreSQLJDBC;

@WebServlet("/purchase-items")
public class PurchaseServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

    public PurchaseServlet()
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
		
		User user = (User) session.getAttribute("user");
		String orderNum = request.getParameter("orderNum");
		try
		{
			Integer.parseInt(orderNum);
		}
		catch (Exception e)
		{
			
		}
		
	    String result = "0";
		synchronized(session)
	    {
			if (session.getAttribute("itemList") != null)
	    	{
				List<Item> itemList = (List<Item>) session.getAttribute("itemList");
		    	if (itemList != null)
		    	{
		    		PostgreSQLJDBC db = new PostgreSQLJDBC();
		    		if (db.createConnection())
		    		{
		    			try
		    			{
		    				String[] parameter = { Integer.toString(user.getId()), orderNum };
		    				Cell orderId = db.getCell("bh_getOrderId", parameter);
		    				Cell cell = null;
		    				for (Item item : itemList)
		    				{
		    					List<Cell> cellList = new ArrayList<>();
		    					cell = new Cell("item_id", Integer.toString(item.getId()));
		    					cellList.add(cell);
		    					cell = new Cell("order_id", orderId.getValue());
		    					cellList.add(cell);
		    					cell = new Cell("quantity", Double.toString(item.getQuantity()));
		    					cellList.add(cell);
		    					cell = new Cell("price", Double.toString(item.getPrice()));
		    					cellList.add(cell);
		    					cell = new Cell("total", Double.toString(item.getTotal()));
		    					cellList.add(cell);
		    					cell = new Cell("serial_id", Integer.toString(item.getSerialID()));
		    					cellList.add(cell);
		    					db.insert("pos_transactions", cellList);
		    				}
		    				cell = null;
		    				cell = db.getCell("bh_purchase", parameter);
		    				result = cell.getValue();
						}
		    			catch (SQLException e)
		    			{
							e.printStackTrace();
						}
		    		}
		    		session.removeAttribute("itemList");
		    	}
	    	}
	    }
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
		out.println(result);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
}
