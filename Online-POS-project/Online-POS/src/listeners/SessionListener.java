package listeners;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionListener implements HttpSessionListener, HttpSessionAttributeListener
{
	private static int totalActiveSessions;
	
	public SessionListener()
	{
        // TODO Auto-generated constructor stub
    }
	
	public void sessionCreated(HttpSessionEvent arg)
    {
		
		totalActiveSessions++;
		//System.out.println("Total sessions: " + totalActiveSessions);
    }

    public void sessionDestroyed(HttpSessionEvent arg)
    {
    	
    	totalActiveSessions--;
		//System.out.println("Total sessions: " + totalActiveSessions);
    }

    public void attributeAdded(HttpSessionBindingEvent arg)
    {
    }
    
    public void attributeReplaced(HttpSessionBindingEvent arg)
    {
    }
    
    public void attributeRemoved(HttpSessionBindingEvent arg)
    {
    }
    
    public static int getTotalActiveSession()
    {
    	return totalActiveSessions;
    }
}
