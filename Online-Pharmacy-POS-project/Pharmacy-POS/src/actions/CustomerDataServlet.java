package actions;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.CustomerData;
import utils.LoggedUser;

@WebServlet("/add-customerData")
public class CustomerDataServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public CustomerDataServlet()
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
		String prescription = (String)request.getParameter("prescription");
		String firstName = (String)request.getParameter("firstName");
		String name = (String)request.getParameter("name");
		String regNumber = (String)request.getParameter("regNumber");
		String eMDnumber = (String)request.getParameter("emdNumber");
		String addressOrNumber = (String)request.getParameter("addressOrNumber");
		String cipher = (String)request.getParameter("cipher");
		synchronized (session) {
			CustomerData customerData = new CustomerData();
			customerData.setPrescription(prescription);
			customerData.setFirstName(firstName);
			customerData.setName(name);
			customerData.setRegNumber(regNumber);
			customerData.setEmdNumber(eMDnumber);
			customerData.setAddressOrNumber(addressOrNumber);
			customerData.setCipher(cipher);
			session.setAttribute("customerData",customerData);
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
}
