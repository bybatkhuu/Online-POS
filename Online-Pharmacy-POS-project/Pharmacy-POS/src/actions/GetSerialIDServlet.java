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

import utils.Cell;
import utils.LoggedUser;
import utils.PostgreSQLJDBC;


@WebServlet("/get-serialID")
public class GetSerialIDServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
    public GetSerialIDServlet()
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
		
		int serialID = 0;
		Cell cell = null;
		String serial = request.getParameter("serial");
		if (serial != null && !serial.trim().equalsIgnoreCase(""))
		{
			PostgreSQLJDBC db = new PostgreSQLJDBC();
			if (db.createConnection())
			{
				String[] param = { serial };
				try
				{
					cell = db.getCell("bh_getSerialID", param);
				}
				catch (SQLException e)
				{
					System.out.println("Error: Can't get serialID from bh_getSerialID()!\nMessage: " + e.toString());
				}
				
				if (cell != null)
				{
					serialID = Integer.parseInt(cell.getValue());
				}
			}
		}
		
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    out.print(serialID);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
}
