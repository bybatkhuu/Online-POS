package actions;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Card;
import models.Customer;

import utils.LoggedUser;

@WebServlet("/set-card")
public class SetCardServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
    public SetCardServlet()
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
	    }
	    
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
		out.print("");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
}