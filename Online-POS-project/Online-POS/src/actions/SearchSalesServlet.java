package actions;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
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

@WebServlet("/search-sales")
public class SearchSalesServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

    public SearchSalesServlet()
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
		
		User user = (User) session.getAttribute("user");
		Date startDate = null;
		String startDateStr = (String) request.getParameter("startDate");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try
		{
			if (startDateStr != null && !startDateStr.equals(""))
				startDate = formatter.parse(startDateStr);
		}
		catch (ParseException e)
		{
			System.out.println("Error: Can't parse startDate!\nMessage: " + e.toString());
		}
		
		Date endDate = null;
		String endDateStr = (String) request.getParameter("endDate");
		try
		{
			if (endDateStr != null && !endDateStr.equals(""))
				endDate = formatter.parse(endDateStr);
			
		}
		catch (ParseException e)
		{
			System.out.println("Error: Can't parse startDate!\nMessage: " + e.toString());
		}
		
		//String type = (String) request.getParameter("type");
		List<Row> rowList = null;
		if (startDate != null && !startDateStr.trim().equalsIgnoreCase("") && endDate != null && !endDateStr.trim().equalsIgnoreCase(""))
		{
			PostgreSQLJDBC db = new PostgreSQLJDBC();
			if (db.createConnection())
			{
				String[] params = { Integer.toString(user.getId()), startDateStr + " 00:00:00", endDateStr + " 23:59:59" };
				try
				{
					rowList = db.getRowList("bh_searchSales", params);
				}
				catch (SQLException e)
				{
					System.out.println("Error: Can't execute bh_searchSales()!\nMessage: " + e.toString());
				}
			}
		}
		
		if (rowList != null && rowList.size() > 0)
		{
			String tableBody = "";
			DecimalFormat format = new DecimalFormat("###############.###");
			for (Row row : rowList)
	    	{
				if (row.getCellList() != null && row.getCellList().size() > 0)
	    		{
					tableBody = tableBody + "<tr>";
		    		for (Cell cell : row.getCellList())
		    		{
		    			switch (cell.getColumn())
		    			{
		    				case "order_date":
		    					tableBody = tableBody + "<td>" + cell.getValue() + "</td>";
		    					break;
		    				case "order_num":
		    					tableBody = tableBody + "<td>" + cell.getValue() + "</td>";
		    					break;
		    				case "name":
		    					tableBody = tableBody + "<td>" + cell.getValue() + "</td>";
		    					break;
		    				case "quantity":
		    					tableBody = tableBody + "<td>" + format.format(BigDecimal.valueOf(Double.parseDouble(cell.getValue()))) + "</td>";
		    					break;
		    				case "price":
		    					tableBody = tableBody + "<td>" + cell.getValue() + "</td>";
		    					break;
		    				case "total":
		    					tableBody = tableBody + "<td>" + cell.getValue() + "</td>";
		    					break;
		    				default:
		    					System.out.println("More unknown columns from bh_searchSales()!");
		    					break;
		    			}
		    		}
		    		tableBody = tableBody + "<td>Бэлнээр</td>";
		    		tableBody = tableBody + "</tr>";
	    		}
	    	}
			request.setAttribute("tableBody", tableBody);
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("report.jsp");
		dispatcher.include(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
}