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

@WebServlet("/barcode-has-item")
public class BarcodeHasItemServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

    public BarcodeHasItemServlet()
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
		
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    int barcodeHasItem = 0;
		String barcode = (String) request.getParameter("barcode");
		if (barcode != null)
		{
			if (!Utilities.isEmpty(barcode))
			{
				if (!Utilities.hasSpecialChars(barcode))
				{
					PostgreSQLJDBC db = new PostgreSQLJDBC();
					if (db.createConnection())
					{
						String[] parameter = {barcode};
						try
						{
							if (db.execute("bh_barcodeHasItem", parameter))
							{
								barcodeHasItem = 1;
							}
						}
						catch (SQLException e)
						{
							System.out.println("Error: Can't execute bh_checkBarcode() function!\nMessage: " + e.toString());
						}
					}
				}
			}
		}
		out.println(barcodeHasItem);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
}
