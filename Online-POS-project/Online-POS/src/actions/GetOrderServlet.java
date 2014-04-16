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

@WebServlet("/get-order-num")
public class GetOrderServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
       
    public GetOrderServlet()
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
		Cell cell = null;
		PostgreSQLJDBC db = new PostgreSQLJDBC();
		if (db.createConnection())
		{
			try
			{
				String[] parameter = { Integer.toString(user.getId()) };
				cell = db.getCell("bh_getLastOrderNum", parameter);
			}
			catch (SQLException e)
			{
				cell = null;
				System.out.println("Error: can't execute bh_getLastOrderNum()!\nMessage: " + e.toString());
			}
		}
		
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
		if (cell != null)
		{
			out.println(cell.getValue());
		}
		else
		{
			out.println(0);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
}
