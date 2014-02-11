package actions;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utils.PostgreSQLJDBC;

import models.User;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

    public LogoutServlet()
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		synchronized(session)
		{
			if (session.getAttribute("user") != null)
			{
				User user = null;
				try
				{
					user = (User) session.getAttribute("user");
					if (user != null)
					{
						PostgreSQLJDBC db = new PostgreSQLJDBC();
						if (db.createConnection())
						{
							try
							{
								String id = Integer.toString(user.getId());
								String[] parameter = {id};
								if (db.execute("bh_hasUser", parameter))
								{
									if (db.execute("bh_logout", parameter))
									{
										session.invalidate();
									}
									else
									{
										System.out.println("Error: Something wrong in bh_logout function!");
										session.invalidate();
									}
								}
								else
								{
									System.out.println("Alert: Database hasn't (id)=" + id + " user!");
									session.invalidate();
								}
							}
							catch (Exception ex)
							{
								System.out.println("Warning: Can't parse user id into string in Logout!\nMessage: " + ex.toString());
								session.invalidate();
							}
						}
						else
						{
							System.out.println("Error: Can't connect server!");
						}
					}
					else
					{
						System.out.println("Error: Can't get user from session in Logout!");
						session.invalidate();
					}
				}
				catch(Exception ex)
				{
					System.out.println("Error: Can't parse user from session in Logout!\nMessage: " + ex.toString());
					session.invalidate();
				}
			}
			response.sendRedirect("index.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
}
