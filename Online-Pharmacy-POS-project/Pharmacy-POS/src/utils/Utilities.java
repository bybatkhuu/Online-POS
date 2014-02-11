package utils;

import javax.servlet.http.*;


public class Utilities
{
	private Utilities(){ }
	
	public static int getIntParameter(HttpServletRequest request, String paramName, int defaultValue)
	{
	    String paramString = request.getParameter(paramName);
	    int paramValue;
	    try
	    {
	    	paramValue = Integer.parseInt(paramString);
	    }
	    catch (Exception nfe)
	    {
	    	paramValue = defaultValue;
	    }
	    return(paramValue);
	}
  
	public static double getDoubleParameter(HttpServletRequest request, String paramName, double defaultValue)
	{
		String paramString = request.getParameter(paramName);
		double paramValue;
		try
		{
			paramValue = Double.parseDouble(paramString);
		}
		catch (Exception nfe)
		{
			paramValue = defaultValue;
		}
		return(paramValue);
	}

	public static String filter(String input)
	{
		if (!hasSpecialChars(input))
		{
			return(input);
		}
		StringBuilder filtered = new StringBuilder(input.length());
		char c;
		for (int i = 0; i < input.length(); i++)
		{
			c = input.charAt(i);
			switch(c)
			{
				case '<':
					filtered.append("&lt;");
					break;
				case '>':
					filtered.append("&gt;");
					break;
				case '"':
					filtered.append("&quot;");
					break;
				case '&':
					filtered.append("&amp;");
					break;
				default:
					filtered.append(c);
					break;
			}
		}
		return(filtered.toString());
	}

	public static boolean hasSpecialChars(String input)
	{
		boolean flag = false;
		if ((input != null) && (input.length() > 0))
		{
			char c;
			for(int i = 0; i < input.length(); i++)
			{
				c = input.charAt(i);
				switch(c)
				{
					case '<':
						flag = true;
						break;
					case '>':
						flag = true;
						break;
					case '"':
						flag = true;
						break;
					case '\'':
						flag = true;
						break;
					case '&':
						flag = true;
						break;
					case '|':
						flag = true;
						break;
					case '[':
						flag = true;
						break;
					case ']':
						flag = true;
						break;
					case '{':
						flag = true;
						break;
					case '}':
						flag = true;
						break;
					case '(':
						flag = true;
						break;
					case ')':
						flag = true;
						break;
					case '/':
						flag = true;
						break;
					case '\\':
						flag = true;
						break;
				}
			}
		}
		return(flag);
	}
	
	public static boolean isEmpty(String val)
	{
		if (val == null || val.trim().equalsIgnoreCase(""))
		{
			return true;
		}
		return false;
	}
	
	public static boolean isLongString(String val)
	{
		if (val.length() >= 64)
		{
			return true;
		}
		return false;
	}
}
