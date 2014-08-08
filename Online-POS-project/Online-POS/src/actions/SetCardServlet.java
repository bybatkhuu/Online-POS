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

import models.Card;
import models.Customer;
import models.Item;
import utils.LoggedUser;

@WebServlet("/set-discount-card")
public class SetCardServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
    public SetCardServlet()
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
		
		String customerId = request.getParameter("customerId");
	    String customerName = request.getParameter("customerName");
	    Customer customer = new Customer();
		customer.setId(Integer.parseInt(customerId));
		customer.setName(customerName);
		
		String cardId = request.getParameter("cardId");
		String cardNumber = request.getParameter("cardNumber");
	    String type = request.getParameter("type");
	    String discountPercent = request.getParameter("discountPercent");
	    String partOwner = request.getParameter("partOwner");
	    Card card = new Card();
	    card.setId(Integer.parseInt(cardId));
	    card.setCardNumber(cardNumber);
	    card.setType(type);
	    card.setDiscountPercent(Float.parseFloat(discountPercent));
	    card.setPartOwner(partOwner);
	    card.setCustomer(customer);
	    synchronized(session)
	    {
	    	session.setAttribute("card", card);
	    	session.setAttribute("discountPercent", card.getDiscountPercent());
	    }
	    
	    List<Item> itemList = (List<Item>) session.getAttribute("itemList");
		String str = "";
	    if (itemList != null && itemList.size() > 0)
	    {
	    	DecimalFormat format = new DecimalFormat("###############.##");
	    	for (int i = 0; itemList.size() > i ; i++)
	    	{
	    		itemList.get(i).setDiscountPercent(card.getDiscountPercent());
	    		if (i == (itemList.size() - 1))
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
	    session.setAttribute("itemList", itemList);
	    
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
		out.print(str);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
}