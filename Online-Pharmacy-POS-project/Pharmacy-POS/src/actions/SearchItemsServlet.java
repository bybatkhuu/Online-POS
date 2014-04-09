package actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.User;

import utils.Cell;
import utils.LoggedUser;
import utils.PostgreSQLJDBC;
import utils.Row;

@WebServlet("/searchItems")
public class SearchItemsServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
    public SearchItemsServlet()
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		int status = LoggedUser.checkLogin(session);
		if (status != 1)
		{
			response.sendRedirect("login.jsp");
		}
		
		String itemName = request.getParameter("itemName");
		if (itemName == null)
		{
			itemName = "";
		}
	    String barcode = request.getParameter("barcode");
	    if (barcode == null)
		{
	    	barcode = "";
		}
	    String minPrice = request.getParameter("minPrice");
	    if (minPrice == null)
		{
	    	minPrice = "";
		}
	    String maxPrice = request.getParameter("maxPrice");
	    if (maxPrice == null)
		{
	    	maxPrice = "";
		}
	    List<Row> rowList = null;
		if (!itemName.trim().equalsIgnoreCase("") || !barcode.trim().equalsIgnoreCase(""))
		{
			User user = (User) session.getAttribute("user");
			PostgreSQLJDBC db = new PostgreSQLJDBC();
			if (db.createConnection())
			{
				String[] params = { String.valueOf(user.getBranchID()), itemName, barcode, minPrice, maxPrice };
				try
				{
					rowList = db.getRowList("bh_searchItems", params);
				}
				catch(SQLException e)
				{
					System.out.println("Error: Can't get Items by bh_searchItems()!\nMessage: " + e.toString());
				}
			}
		}
		
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    if (rowList != null && rowList.size() > 0)
	    {
	    	for (Row row : rowList)
	    	{
	    		if (row.getCellList() != null && row.getCellList().size() > 0)
	    		{
	    			out.println("<tr>");
		    		for (Cell cell : row.getCellList())
		    		{
		    			switch (cell.getColumn())
		    			{
		    				case "name":
		    					out.println("<td>" + cell.getValue() + "</td>");
		    					break;
		    				case "barcode":
		    					out.println("<td>" + cell.getValue() + "</td>");
		    					break;
		    				case "serial_number":
		    					out.println("<td>" + cell.getValue() + "</td>");
		    					break;
		    				case "price":
		    					out.println("<td>" + cell.getValue() + "</td>");
		    					break;
		    				case "item_id":
		    					out.println("<td class='hidden'>" + cell.getValue() + "</td>");
		    					break;
		    				default:
		    					System.out.println("More unknown columns from bh_searchItems()!");
		    					break;
		    			}
		    		}
		    		out.println("</tr>");
	    		}
	    	}
	    }
	    out.print("");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
}
