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

import utils.Cell;
import utils.LoggedUser;
import utils.PostgreSQLJDBC;
import utils.Row;

@WebServlet("/get-barcodes")
public class GetBarcodesServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
       
    public GetBarcodesServlet()
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
		List<Row> rowList = null;
		PostgreSQLJDBC db = new PostgreSQLJDBC();
		if (db.createConnection())
		{
			try
			{
				rowList = db.getRowList("bh_getBarcodes");	
			}
			catch (SQLException e)
			{
				rowList = null;
				System.out.println("Error: can't execute bh_getBarcodes()!\nMessage: " + e.toString());
			}
		}
		
		if (rowList != null)
		{
			for (int i = 0; i < rowList.size(); i++)
			{
				for (Cell cell : rowList.get(i).getCellList())
				{
					out.print(cell.getValue() + ",");
				}
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}

}
