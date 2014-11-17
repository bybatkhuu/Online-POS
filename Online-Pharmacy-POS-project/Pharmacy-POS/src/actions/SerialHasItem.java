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
import utils.Utilities;

@WebServlet("/serial-has-item")
public class SerialHasItem extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public SerialHasItem()
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
		
		int hasSerial = 0;
		String barcode = (String) request.getParameter("barcode");
		String serial = (String) request.getParameter("serial");
		if (barcode != null && serial != null)
		{
			if (!Utilities.isEmpty(barcode) && !Utilities.isEmpty(serial))
			{
				PostgreSQLJDBC db = new PostgreSQLJDBC();
				if (db.createConnection())
				{
					String[] parameter = { serial, barcode, request.getParameter("assetAcc") };
					try
					{
						if (db.execute("bh_serialHasItem", parameter))
						{
							hasSerial = 1;
						}
					}
					catch (SQLException e)
					{
						System.out.println("Error: Can't execute bh_checkBarcode() function!\nMessage: " + e.toString());
					}
				}
			}
		}
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
		out.println(hasSerial);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
}
