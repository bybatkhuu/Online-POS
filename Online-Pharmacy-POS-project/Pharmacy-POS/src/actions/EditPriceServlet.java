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

@WebServlet("/edit-price")
public class EditPriceServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
    public EditPriceServlet()
    {
        super();
    }
    
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		int status = LoggedUser.checkLogin(session);
		if (status != 1)
		{
			response.sendRedirect("login.jsp");
		}
		
		boolean isPriceEdited = false;
		int id = 0;
		double price = 0;
		try
		{
			id = Integer.parseInt(request.getParameter("id"));
			price = Double.parseDouble(request.getParameter("price"));
		}
		catch (Exception e)
		{
			id = 0;
			price = 0;
		}
		
		if (id > 0 && price > 0)
		{
			synchronized(session)
		    {
				if (session.getAttribute("itemList") != null)
		    	{
		    		List<Item> itemList = (List<Item>) session.getAttribute("itemList");
			    	if (itemList != null)
			    	{
			    		for (int i = 0; i < itemList.size() ; i++)
			    		{
			    			if (itemList.get(i).getId() == id)
			    			{
			    				itemList.get(i).setPrice(price);
			    			}
			    		}
			    		session.setAttribute("itemList", itemList);
			    		isPriceEdited = true;
			    	}
		    	}
		    }
		}
		
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
		out.println(isPriceEdited);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
}
