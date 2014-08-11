package actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Item;

import utils.LoggedUser;

@WebServlet("/updateDisCount-item")
public class UpdateDisCountItemServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
    public UpdateDisCountItemServlet()
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
		
		int id = 0;
		float newDisCount = 0;
		try
		{
			id = Integer.parseInt(request.getParameter("id"));
			newDisCount = Float.parseFloat(request.getParameter("newDisCount"));
		}
		catch (Exception e)
		{
			id = 0;
			newDisCount = 0;
		}
		
		if (id != 0 && newDisCount > 0)
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
			    				itemList.get(i).setDiscountPercent(newDisCount);
			    			}
			    		}
			    		session.setAttribute("itemList", itemList);
			    	}
		    	}
		    }
		}
		
		List<Item> itemList = (List<Item>) session.getAttribute("itemList");
		String str = "";
	    if (itemList != null && itemList.size() > 0)
	    {
	    	DecimalFormat format = new DecimalFormat("###############.##");
	    	for (int i = 0; itemList.size() > i ; i++)
	    	{
	    		if (itemList.get(i).getId() == id)
		    	{
		    		str = str + "<tr class='success' id='" + itemList.get(i).getId() + "'>";
		    	}
		    	else
		    	{
		    		str = str + "<tr id='" + itemList.get(i).getId() + "'>";
		    	}
	    		str = str + "<td>" + itemList.get(i).getName() + "</td>";
	    		str = str + "<td class='text-right'>" + format.format(itemList.get(i).getQuantity()) + "</td>";
	    		str = str + "<td>" + itemList.get(i).getUnit() + "</td>";
	    		str = str + "<td class='text-right'>" + format.format(itemList.get(i).getPrice())  + "</td>";
	    		str = str + "<td class='text-right'>" + format.format(itemList.get(i).getTotal())  + "</td>";
	    		str = str + "<td class='text-right'>" + format.format(itemList.get(i).getDiscountPercent())  + "</td>";
	    		str = str + "<td class='hidden discountTotal'>" + format.format(itemList.get(i).getDiscountTotal())  + "</td>";
	    		str = str + "</tr>";
	    	}
	    }
		
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
		out.print(str);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
}
