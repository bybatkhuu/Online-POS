package actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

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

@WebServlet("/get-max-price")
public class GetMaxPriceServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
    public GetMaxPriceServlet()
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
		
		Cell cell = null;
		User user = (User) session.getAttribute("user");
		if (user != null)
		{
			String branchID = Integer.toString(user.getBranchID());
			PostgreSQLJDBC db = new PostgreSQLJDBC();
			if (db.createConnection())
			{
				String[] parameter = { branchID };
				try
				{
					cell = db.getCell("bh_getMaxPrice", parameter);
				}
				catch (SQLException e)
				{
					System.out.println("Error: Can't execute bh_getMaxPrice function!\nMessage: " + e.toString());
				}
			}
		}
		
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    if (cell != null)
	    {
	    	out.print(cell.getValue());
	    }
	    else
	    {
	    	out.print("0");
	    }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
}