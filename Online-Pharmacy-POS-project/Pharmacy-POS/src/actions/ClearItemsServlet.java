package actions;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utils.LoggedUser;

@WebServlet("/clear-items")
public class ClearItemsServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

    public ClearItemsServlet()
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
		
		synchronized(session)
	    {
			if (session.getAttribute("itemList") != null)
	  		{
				session.removeAttribute("itemList");
	  		}
	    }
		
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
		out.print(" ");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
}
