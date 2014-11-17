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

import utils.LoggedUser;
import utils.PostgreSQLJDBC;

@WebServlet("/check-card-number")
public class CheckCardNumber extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
    public CheckCardNumber()
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
		
		int hasCardNumber = 0;
		String cardNumber = (String) request.getParameter("cardNumber");
		if (cardNumber != null && !cardNumber.equalsIgnoreCase(""))
		{
			PostgreSQLJDBC db = new PostgreSQLJDBC();
			if (db.createConnection())
			{
				String[] parameter = { cardNumber };
				try
				{
					if (db.execute("bh_checkCardNumber", parameter))
					{
						hasCardNumber = 1;
					}
				}
				catch (SQLException e)
				{
					System.out.println("Error: Can't execute bh_checkCardNumber() function!\nMessage: " + e.toString());
				}
			}
		}
		
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
		out.print(hasCardNumber);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
}