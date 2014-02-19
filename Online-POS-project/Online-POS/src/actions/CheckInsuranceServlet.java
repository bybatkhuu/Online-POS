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

@WebServlet("/check-insurance")
public class CheckInsuranceServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
       
    public CheckInsuranceServlet()
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
		
		String hasInsurance = (String) request.getParameter("hasInsurance");
		if (hasInsurance != null && !hasInsurance.trim().equalsIgnoreCase(""))
		{
			if (hasInsurance.trim().equalsIgnoreCase("true"))
			{
				session.setAttribute("hasInsurance", true);
			}
			else if (hasInsurance.trim().equalsIgnoreCase("false"))
			{
				session.removeAttribute("hasInsurance");
			}
		}
		
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    out.print(hasInsurance);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
}
