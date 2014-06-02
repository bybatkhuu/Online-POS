package utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import models.User;

public class LoggedUser
{
	private LoggedUser()
	{
	}
	
	public static int checkLogin(HttpSession session)
	{
		int status = 0;
		synchronized(session)
		{
			if (session.getAttribute("user") != null)
			{
				User user = null;
				try
				{
					user = (User) session.getAttribute("user");
				}
				catch (Exception ex)
				{
					System.out.println("Error: Can't parse user from session in checkLogin!\nMessage: " + ex.toString());
					session.removeAttribute("user");
					status = -1;
				}
				
				if (user != null && status == 0)
				{
					status = 1;
				}
			}
		}
		return status;
	}
	
	public static String getIpAddress(HttpServletRequest request)
	{
		String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {  
            ip = request.getHeader("Proxy-Client-IP");  
        }
        
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {  
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }  
        
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getRemoteAddr();
        }
        return ip;
    }  
}