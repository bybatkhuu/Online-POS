package actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Item;
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
		Double insuranceTotal =0d;
		String hasInsurance = (String) request.getParameter("hasInsurance");
		if (hasInsurance != null && !hasInsurance.trim().equalsIgnoreCase(""))
		{
			if (hasInsurance.trim().equalsIgnoreCase("true"))
			{
				@SuppressWarnings("unchecked")
				List<Item> itemList = (List<Item>) session.getAttribute("itemList");
				if(itemList !=null && !itemList.isEmpty()){
					for (Item i :itemList){
						insuranceTotal +=i.getCalTotal();
					}
				}
				session.setAttribute("hasInsurance", "true");
			}else if (hasInsurance.trim().equalsIgnoreCase("false"))
			{
				session.removeAttribute("hasInsurance");
			}
		}
		
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    out.print(insuranceTotal);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
}
