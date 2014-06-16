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

@WebServlet("/check-barcode")
public class CheckBarcode extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
    public CheckBarcode()
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
		
	    int hasBarcode = 0;
		String barcode = (String) request.getParameter("barcode");
		String assetAcc = (String) request.getParameter("assetAcc");
		if (barcode != null && assetAcc != null)
		{
			if (!Utilities.isEmpty(barcode) && !Utilities.isEmpty(assetAcc))
			{
				PostgreSQLJDBC db = new PostgreSQLJDBC();
				if (db.createConnection())
				{
					String[] parameters = { barcode, assetAcc };
					try
					{
						if (db.execute("bh_checkBarcode", parameters))
						{
							hasBarcode = 1;
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
		out.print(hasBarcode);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
}
