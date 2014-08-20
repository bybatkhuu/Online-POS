package actions;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Cash;
import models.User;
import utils.Cell;
import utils.LoggedUser;
import utils.PostgreSQLJDBC;
import utils.Row;
import utils.Utilities;

@WebServlet("/login")
public class LoginServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
    
	public LoginServlet()
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("Warning: User try to login by GET method!");
		
		response.sendRedirect("login.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		int status = LoggedUser.checkLogin(session);
		
		synchronized(session)
		{
			if (status != 1)
			{
				String userName = (String) request.getParameter("userName");
				String password = (String) request.getParameter("password");
				if (!Utilities.isEmpty(userName) && !Utilities.isEmpty(password))
				{
					if (!Utilities.isLongString(userName) && !Utilities.isLongString(userName))
					{
						if (!Utilities.hasSpecialChars(userName) && !Utilities.hasSpecialChars(password))
						{
							PostgreSQLJDBC db = new PostgreSQLJDBC();
							if (db.createConnection())
							{
								List<String> parameters = new ArrayList<String>();
								parameters.add(userName);
								parameters.add(password);
								try
								{
									List<Row> rowList = new ArrayList<Row>();
									rowList = db.getRowList("bh_getUser", parameters);
									if (rowList != null && rowList.size() == 1)
									{
										parameters = new ArrayList<String>();
										parameters.add(rowList.get(0).getCellList().get(0).getValue());
										if (db.execute("bh_isLogout", parameters))
										{
											String ip = LoggedUser.getIpAddress(request);
											parameters.add(ip);
											System.out.println(ip);
											if (db.execute("bh_checkBranchUsers", parameters))
											{
												if (db.execute("bh_login", parameters))
												{
													User user = new User();
													for (Cell cell : rowList.get(0).getCellList())
													{
														switch (cell.getColumn())
														{
															case "id":
																user.setId(Integer.parseInt(cell.getValue()));
																break;
															case "user_name":
																user.setUserName(cell.getValue());
																break;
															case "password":
																user.setPassword(cell.getValue());
																break;
															case "status":
																user.setStatus(cell.getValue());
																break;
															case "role_id":
																user.setRoleID(Integer.parseInt(cell.getValue()));
																break;
															case "cash_name":
																user.setCashName(cell.getValue());
																break;
															case "asset_type":
																user.setAssetType(cell.getValue());
																break;
															case "asset_acc":
																user.setAssetAcc(cell.getValue());
																break;
															default:
																break;
														}
													}
													
													parameters = new ArrayList<>();
													parameters.add(ip);
													List<Cell> cellList = new ArrayList<Cell>();
													cellList = db.getCellList("bh_getCash", parameters);
													Cash cash = new Cash();
													for (Cell cell : cellList)
													{
														switch (cell.getColumn())
														{
															case "id":
																cash.setId(Integer.parseInt(cell.getValue()));
																break;
															case "ip_address":
																cash.setIp(cell.getValue());
																break;
															case "barnch_id":
																cash.setBranchId(Integer.parseInt(cell.getValue()));
																break;
															case "pos_num":
																cash.setPosNum(Integer.parseInt(cell.getValue()));
																break;
															case "asset_type":
																cash.setAssetType(cell.getValue());
																break;
															case "asset_acc":
																cash.setAssetAcc(cell.getValue());
																break;
															case "human_type":
																cash.setHumanType(cell.getValue());
																break;
															default:
																break;
														}
													}
												if(cash.getHumanType() != null && cash.getHumanType().trim().equals("Human")){
													cash.setAssetAcc(user.getAssetAcc());
													cash.setAssetType(user.getAssetType());
												}
													session.setAttribute("cash", cash);
													session.setAttribute("user", user);
													status = 1;
												}
												else
												{
													System.out.println("Error: Can't login with bh_login() function!");
													status = 401;
												}
											}
											else
											{
												System.out.println("Info: Branch users reached limit!");
												status = 202;
											}
										}
										else
										{
											System.out.println("Info: Logged user try to login again!");
											status = 201;
										}
									}
									else
									{
										status = 200;
									}
								}
								catch (SQLException ex)
								{
									System.out.println("Error: Something wrong in login to execute Function!\nMessage: " + ex.toString());
									status = 400;
								}
							}
							else
							{
								System.out.println("Server: Can't connect to database server!");
								status = 500;
							}
						}
						else
						{
							System.out.println("Alert: User try to login with special characters!");
							status = 602;
						}
					}
					else
					{
						System.out.println("Alert: User try to login with long characters!");
						status = 601;
					}
				}
				else
				{
					System.out.println("Alert: User try to access without username or password!");
					status = 600;
				}
			}
			
			if (status == 1)
			{
				response.sendRedirect("cash.jsp");
			}
			else
			{
				response.sendRedirect("login.jsp?message=" + status);
			}
		}
	}
}