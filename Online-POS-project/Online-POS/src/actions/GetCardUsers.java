package actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Card;
import models.Customer;

import utils.Cell;
import utils.LoggedUser;
import utils.PostgreSQLJDBC;
import utils.Row;

@WebServlet("/get-card-users")
public class GetCardUsers extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
    public GetCardUsers()
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
		
		String responseString = "";	
		String cardNumber = (String) request.getParameter("cardNumber");
		if (cardNumber != null && !cardNumber.equalsIgnoreCase(""))
		{
			PostgreSQLJDBC db = new PostgreSQLJDBC();
			if (db.createConnection())
			{
				List<Row> rowList = new ArrayList<Row>();
				String[] parameter = { cardNumber };
				try
				{
					rowList = db.getRowList("bh_getCardUsers", parameter);
					if (rowList != null)
					{
						List<Card> cardList = new ArrayList<Card>();
						for (Row row : rowList)
						{
							Card card = new Card();
							for (Cell cell : row.getCellList())
							{
								switch (cell.getColumn())
								{
									case "id":
										card.setId(Integer.parseInt(cell.getValue()));
										break;
									case "customer_id":
										Customer customer = new Customer();
										List<Cell> cellList = new ArrayList<Cell>();
										String[] customerIdParam = { cell.getValue() };
										cellList = db.getCellList("bh_getCustomer", customerIdParam);
										for (Cell tmpCell : cellList)
										{
											switch (tmpCell.getColumn())
											{
												case "id":
													customer.setId(Integer.parseInt(tmpCell.getValue()));
													break;
												case "name":
													customer.setName(tmpCell.getValue());
													break;
												case "description":
													customer.setDesciption(tmpCell.getValue());
													break;
												default:
													break;
											}
										}
										card.setCustomer(customer);
										break;
									case "card_number":
										card.setCardNumber(cell.getValue());
										break;
									case "discount_percent":
										card.setDiscountPercent(Float.parseFloat(cell.getValue()));
										break;
									case "part_owner":
										card.setPartOwner(cell.getValue());
										break;
									case "type":
										card.setType(cell.getValue());
										break;
									default:
										break;
								}
							}
							cardList.add(card);
						}
						
						for (Card card : cardList)
						{
							responseString = responseString +
								"<tr id='card-" + card.getId() + "'>" +
									"<td id='customer-" + card.getCustomer().getId() + "'>" + card.getCustomer().getName() + "</td>" +
									"<td>" + card.getCardNumber() + "</td>" +
									"<td>" + card.getType() + "</td>" +
									"<td>" + card.getDiscountPercent() + "</td>" +
									"<td>" + card.getPartOwner() + "</td>" +
								"</tr>";
						}
					}
				}
				catch (SQLException e)
				{
					System.out.println("Error: Can't execute bh_getCardUsers() function!\nMessage: " + e.toString());
				}
			}
		}
		
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
		out.print(responseString);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
}