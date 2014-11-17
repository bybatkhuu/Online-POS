package actions;


import org.apache.log4j.Logger;
public interface PosLogger {
	Logger LOG = Logger.getLogger(PosLogger.class);
	String sep = " | ";
	
	public void log(Object obj,String message);
}
