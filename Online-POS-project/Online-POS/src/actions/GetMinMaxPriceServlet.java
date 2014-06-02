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

@WebServlet("/get-min-max-price")
public class GetMinMaxPriceServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
    public GetMinMaxPriceServlet()
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
		
		List<Cell> cellList = null;
		PostgreSQLJDBC db = new PostgreSQLJDBC();
		if (db.createConnection())
		{
			try
			{
				cellList = db.getCellList("bh_getMinMaxPrice");
			}
			catch (SQLException e)
			{
				System.out.println("Error: Can't execute bh_getMaxPrice function!\nMessage: " + e.toString());
			}
		}
		
		String jsonString = "";
		if (cellList != null && cellList.size() > 0)
		{
		    jsonString =
		    	"{" +
		    		"hasPrice: '" + true + "'," +
			    	"minPrice: '" + cellList.get(0).getValue() + "'," +
			    	"maxPrice: '" + cellList.get(1).getValue() + "'" +
			    "}";
		}
		else
		{
			jsonString = "{ hasPrice: '" + false + "' }";
		}
	    
	    response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    out.print(jsonString);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
}