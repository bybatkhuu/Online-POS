package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PostgreSQLJDBC
{	
	private Connection connection;
	private String server;
    private String port;
    private String database;
    private String user;
    private String password;
    
    public PostgreSQLJDBC()
	{
		init();
	}
	
	public void init()
    {
        server = "localhost";
        port = "5432";
        database = "OnlinePOS";
        user = "postgres";
        password = "123asd90@";
    }
	
	//Create connection
	public boolean createConnection()
    {
		if (Utilities.isEmpty(server) || Utilities.isEmpty(database) || Utilities.isEmpty(user) || Utilities.isEmpty(password))
		{
			System.out.println("Warning: Some variables empty in connection!");
			return false;
		}
		
		if (!openConnection())
        {
        	return false;
        }
        if(!closeConnection())
        {
        	return false;
        }
        return true;
    }
	public boolean createConnection(String connectionString)
    {
		if (Utilities.isEmpty(connectionString))
		{
			System.out.println("Warning: Connection string is empty!");
			return false;
		}
		
		if (!openConnection(connectionString))
        {
        	return false;
        }
        if(!closeConnection())
        {
        	return false;
        }
        return true;
    }
	public boolean createConnection(String server, String database, String user, String password)
    {
		if (Utilities.isEmpty(server) || Utilities.isEmpty(database) || Utilities.isEmpty(user) || Utilities.isEmpty(password))
		{
			System.out.println("Warning: Some parameters empty in connection!");
			return false;
		}
		
        this.server = server;
        this.database = database;
        this.user = user;
        this.password = password;
        String connectionString = "jdbc:postgresql://" + this.server + "/" + this.database + "?user=" + this.user + "&password=" + this.password + "&charSet=UTF-8";
        if (!openConnection(connectionString))
        {
        	return false;
        }
        if(!closeConnection())
        {
        	return false;
        }
        return true;
    }
	public boolean createConnection(String server, String port, String database, String user, String password)
    {
		if (Utilities.isEmpty(server) || Utilities.isEmpty(port) || Utilities.isEmpty(database) || Utilities.isEmpty(user) || Utilities.isEmpty(password))
		{
			System.out.println("Warning: Some parameters empty in connection!");
			return false;
		}
		
        this.server = server;
        this.port = port;
        this.database = database;
        this.user = user;
        this.password = password;
        String connectionString = "jdbc:postgresql://" + this.server + ":" + this.port + "/" + this.database + "?user=" + this.user + "&password=" + this.password + "&charSet=UTF-8";
        if (!openConnection(connectionString))
        {
        	return false;
        }
        if(!closeConnection())
        {
        	return false;
        }
        return true;
    }
	
	//Open connection
	private boolean openConnection()
	{
		if (Utilities.isEmpty(port))
		{
			port = "5432";
		}
        String connectionString = "jdbc:postgresql://" + server + ":" + port + "/" + database + "?user=" + user + "&password=" + password + "&charSet=UTF-8";
		try
        {
        	Class.forName("org.postgresql.Driver");
        	connection = DriverManager.getConnection(connectionString);
        	
        }
        catch (Exception ex)
        {
            //throw new Exception(ex.toString());
        	System.out.println("Error: Can't connect to database!\nMessage: " + ex.toString());
            return false;
        }
        return true;
	}
	private boolean openConnection(String connectionString)
	{
		try
        {
        	Class.forName("org.postgresql.Driver");
        	connection = DriverManager.getConnection(connectionString);
        	
        }
        catch (Exception ex)
        {
            //throw new Exception(ex.toString());
        	System.out.println("Error: Can't connect to database!\nMessage: " + ex.toString());
            return false;
        }
        return true;
	}
	
	//Close onnection
	private boolean closeConnection()
	{
	    try
	    {
	        connection.close();
	    }
	    catch (SQLException ex)
	    {
	    	System.out.println("Error: Can't close connection!\nMessage: " + ex.toString());
	        return false;
	    }
	    return true;
	}
	
	//Insert statement
    public boolean insert(String table, List<Cell> cellList) throws SQLException
    {
    	boolean isInserted = false;
    	if (hasEmptyColumn(cellList) || Utilities.isEmpty(table))
    	{
    		System.out.println("Warning: Can't insert - Some columns name empty or table name is empty!");
    		return isInserted;
    	}
    	
        if (openConnection())
        {
        	String query = "INSERT INTO " + table + "(";
            for (int i = 0; i < cellList.size(); i++)
            {
                query = query + cellList.get(i).getColumn();
                if ((cellList.size() - 1) != i)
                {
                    query = query + ", ";
                }
            }
            query = query + ") VALUES(";
            for (int i = 0; i < cellList.size(); i++)
            {
                query = query + "'" + cellList.get(i).getValue();
                if ((cellList.size() - 1) != i)
                {
                    query = query + "', ";
                }
                else
                {
                    query = query + "');";
                }
            }
            connection.setAutoCommit(false);
        	Statement statement = connection.createStatement();
            try
            {
            	writeToConsole(query);
            	if (statement.executeUpdate(query) == 1)
            	{
            		isInserted = true;
            	}
            	else
            	{
            		System.out.println("Warning: Something wrong in insert!");
            	}
            }
            catch(Exception ex)
            {
            	System.out.println("Error: Can't insert!\nMessage: " + ex.toString());
            }
            statement.close();
            connection.commit();
            closeConnection();
        }
        else
        {
        	System.out.println("Error: Can't connect to server!");
        }
        return isInserted;
    }
    
    //Update statement
    public boolean update(String table, int id, List<Cell> cellList) throws SQLException
    {
    	boolean isUpdated = false;
    	if (hasEmptyColumn(cellList) || Utilities.isEmpty(table))
    	{
    		System.out.println("Error: Can't update - Some column name's empty!");
    		return isUpdated;
    	}
    	
        if (openConnection())
        {
        	String query = "UPDATE " + table + " SET ";
            for (int i = 0; cellList.size() > i; i++)
            {
                query = query + cellList.get(i).getColumn() + "='" + cellList.get(i).getValue() + "'";
                if ((cellList.size() - 1) != i)
                {
                    query = query + ", ";
                }
            }
            query = query + " WHERE id='" + id + "';";
        	connection.setAutoCommit(false);
        	Statement statement = connection.createStatement();
            try
            {
            	writeToConsole(query);
            	if (statement.executeUpdate(query) == 1)
            	{
            		isUpdated = true;
            	}
            	else
            	{
            		System.out.println("Warning: There isn't any id - " + id + "!");
            	}
            }
            catch (Exception ex)
            {
            	System.out.println("Error: Can't update!\nMessage: " + ex.toString());
            }
            statement.close();
        	connection.commit();
            closeConnection();
        }
        else
        {
        	System.out.println("Error: Can't connect to server!");
        }
        return isUpdated;
    }
    
    //Delete statement
    public boolean delete(String table, int id) throws SQLException
    {
    	boolean isDeleted = false;
    	if (Utilities.isEmpty(table))
		{
			System.out.println("Warning: Table name empty!");
			return isDeleted;
		}
    	
        if (openConnection())
        {
        	connection.setAutoCommit(false);
        	Statement statement = connection.createStatement();
            try
            {
            	writeToConsole("DELETE FROM " + table + " WHERE id='" + id + "';");
            	if (statement.executeUpdate("DELETE FROM " + table + " WHERE id='" + id + "';") == 1)
            	{
            		isDeleted = true;
            	}
            	else
            	{
            		System.out.println("Warning: Something wrong in delete!");
            	}
            }
            catch (Exception ex)
            {
            	System.out.println("Error: Can't delete!\nMessage: " + ex.toString());
            }
            statement.close();
        	connection.commit();
            closeConnection();
        }
        else
        {
        	System.out.println("Error: Can't connect to server!");
        }
        return isDeleted;
    }
    
    //Select all row statement
    public List<Row> select(String table) throws SQLException
    {
        List<Row> rowList = null;
        if (Utilities.isEmpty(table))
		{
			System.out.println("Warning: Table name empty!");
			return rowList;
		}
        
        if (openConnection())
        {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            ResultSet resultSet = null;
            try
            {
            	writeToConsole("SELECT * FROM " + table + ";");
            	resultSet = statement.executeQuery("SELECT * FROM " + table + ";");
            }
            catch (Exception ex)
            {
            	System.out.println("Error: Can't select!\nMessage: " + ex.toString());
            	statement.close();
                closeConnection();
                return rowList;
            }
            rowList = new ArrayList<Row>();
            ResultSetMetaData resultSetMetadata = resultSet.getMetaData();
            while (resultSet.next())
            {
                List<Cell> cellList = new ArrayList<Cell>();
                for (int c = 1; resultSetMetadata.getColumnCount() >= c; c++)
                {
                    cellList.add(new Cell(resultSetMetadata.getColumnName(c), resultSet.getString(c)));
                }
                rowList.add(new Row(cellList));
            }
            resultSet.close();
        	statement.close();
            closeConnection();
        }
        else
        {
        	System.out.println("Error: Can't connect to server!");
        }
        return rowList;
    }
    //Select one row statement
    public List<Cell> select(String table, int id) throws SQLException
    {
        List<Cell> cellList = null;
        if (Utilities.isEmpty(table))
		{
			System.out.println("Warning: Table name empty!");
			return cellList;
		}
        
        if (openConnection())
        {
        	connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            ResultSet resultSet = null;
            try
            {
            	writeToConsole("SELECT * FROM " + table + " WHERE id = '" + id + "';");
            	resultSet = statement.executeQuery("SELECT * FROM " + table + " WHERE id = '" + id + "';");
            }
            catch (Exception ex)
            {
            	System.out.println("Error: Can't select!\nMessage: " + ex.toString());
            	statement.close();
                closeConnection();
                return cellList;
            }
            cellList = new ArrayList<Cell>();
            ResultSetMetaData resultSetMetadata = resultSet.getMetaData();
            while (resultSet.next())
            {
            	for (int c = 1; resultSetMetadata.getColumnCount() >= c; c++)
            	{
                    cellList.add(new Cell(resultSetMetadata.getColumnName(c), resultSet.getString(c)));
            	}
            }
            resultSet.close();
        	statement.close();
            closeConnection();
        }
        else
        {
        	System.out.println("Error: Can't connect to server!");
        }
        return cellList;
    }
    
    //Count statement
    public int count(String table) throws SQLException
    {
        int count = -1;
        if (Utilities.isEmpty(table))
		{
			System.out.println("Warning: Table name empty!");
			return count;
		}
        
        if (openConnection())
        {
        	connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            ResultSet resultSet = null;
            try
            {
            	writeToConsole("SELECT Count(*) AS count FROM " + table + ";");
            	resultSet = statement.executeQuery("SELECT Count(*) AS count FROM " + table + ";");
            }
            catch (Exception ex)
            {
            	System.out.println("Error: Can't count!\nMessage: " + ex.toString());
            	statement.close();
                closeConnection();
                return count;
            }
            while (resultSet.next())
            {
            	try
            	{
            		count = Integer.parseInt(resultSet.getString(1));
            	}
            	catch (Exception ex)
            	{
            		System.out.println("Warning: Can't parse String to int in count!\nMessage: " + ex.toString());
            		count = 0;
            	}
            }
            resultSet.close();
        	statement.close();
            closeConnection();
        }
        else
        {
        	System.out.println("Error: Can't connect to server!");
        }
        return count;
    }
    
    //Execute function and get rows
    public List<Row> getRowList(String functionName) throws SQLException
    {
    	List<Row> rowList = null;
    	if (Utilities.isEmpty(functionName))
		{
			System.out.println("Warning: Function name empty!");
			return rowList;
		}
    	
    	if (openConnection())
    	{
    		connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            ResultSet resultSet = null;
            try
            {
            	writeToConsole("SELECT * FROM " + functionName + "();");
            	resultSet = statement.executeQuery("SELECT * FROM " + functionName + "();");
            }
            catch (Exception ex)
            {
            	System.out.println("Error: Can't execute function!\nMessage: " + ex.toString());
            	statement.close();
                closeConnection();
                return rowList;
            }
            rowList = new ArrayList<Row>();
            ResultSetMetaData resultSetMetadata = resultSet.getMetaData();
            while (resultSet.next())
            {
                int nullCell = 0;
                for (int c = 1; resultSetMetadata.getColumnCount() >= c; c++)
                {
                	if (resultSet.getString(c) == null || resultSet.getString(c).trim().equalsIgnoreCase("null"))
                	{
                		nullCell++;
                	}
                }
                if (nullCell != resultSetMetadata.getColumnCount())
                {
                	List<Cell> cellList = new ArrayList<Cell>();
	                for (int c = 1; resultSetMetadata.getColumnCount() >= c; c++)
	                {
	                    cellList.add(new Cell(resultSetMetadata.getColumnName(c), resultSet.getString(c)));
	                }
	                rowList.add(new Row(cellList));
                }
                else
                {
                	System.out.println("Info: Null row returned from " + functionName + "()!");
                }
            }
            resultSet.close();
        	statement.close();
        	connection.commit();
            closeConnection();
        }
    	else
        {
        	System.out.println("Error: Can't connect to server!");
        }
    	return rowList;
    }
    //Execute function with parameters and get rows
    public List<Row> getRowList(String functionName, List<String> parameterList) throws SQLException
    {
    	List<Row> rowList = null;
    	if (hasEmptyString(parameterList) || Utilities.isEmpty(functionName))
    	{
    		System.out.println("Warning: Function name empty or parameters are empty!");
    		return rowList;
    	}
    	
    	if (openConnection())
    	{
    		String query = "SELECT * FROM " + functionName + "(";
            for (int i = 0; parameterList.size() > i; i++)
            {
            	query = query + "'" + parameterList.get(i);
            	if ((parameterList.size() - 1) != i)
                {
                    query = query + "', ";
                }
                else
                {
                    query = query + "');";
                }
            }
    		connection.setAutoCommit(false);
    		Statement statement = connection.createStatement();
            ResultSet resultSet = null;
            try
            {
            	writeToConsole(query);
            	resultSet = statement.executeQuery(query);
            }
            catch (Exception ex)
            {
            	System.out.println("Error: Can't execute function!\nMessage: " + ex.toString());
            	statement.close();
                closeConnection();
                return rowList;
            }
            rowList = new ArrayList<Row>();
            ResultSetMetaData resultSetMetadata = resultSet.getMetaData();
            while (resultSet.next())
            {
            	int nullCell = 0;
                for (int c = 1; resultSetMetadata.getColumnCount() >= c; c++)
                {
                	if (resultSet.getString(c) == null || resultSet.getString(c).trim().equalsIgnoreCase("null"))
                	{
                		nullCell++;
                	}
                }
                if (nullCell != resultSetMetadata.getColumnCount())
                {
                	List<Cell> cellList = new ArrayList<Cell>();
	                for (int c = 1; resultSetMetadata.getColumnCount() >= c; c++)
	                {
	                    cellList.add(new Cell(resultSetMetadata.getColumnName(c), resultSet.getString(c)));
	                }
	                rowList.add(new Row(cellList));
                }
                else
                {
                	System.out.println("Info: Null row returned from " + functionName + "()!");
                }
            }
            resultSet.close();
        	statement.close();
        	connection.commit();
            closeConnection();
        }
    	else
        {
        	System.out.println("Error: Can't connect to server!");
        }
    	return rowList;
    }
    //Execute function with parameters and get rows
    public List<Row> getRowList(String functionName, String[] parameters) throws SQLException
    {
    	List<Row> rowList = null;
    	/*if (Utilities.isEmpty(functionName) || hasEmptyString(parameters))
    	{
    		System.out.println("Warning: Function name empty or parameters are empty!");
    		return rowList;
    	}*/
    	
    	if (openConnection())
    	{
    		String query = "SELECT * FROM " + functionName + "(";
            for (int i = 0; parameters.length > i; i++)
            {
            	query = query + "'" + parameters[i];
            	if ((parameters.length - 1) != i)
                {
                    query = query + "', ";
                }
                else
                {
                    query = query + "');";
                }
            }
    		connection.setAutoCommit(false);
    		Statement statement = connection.createStatement();
            ResultSet resultSet = null;
            try
            {
            	writeToConsole(query);
            	resultSet = statement.executeQuery(query);
            }
            catch (Exception ex)
            {
            	System.out.println("Error: Can't execute function!\nMessage: " + ex.toString());
            	statement.close();
                closeConnection();
                return rowList;
            }
            rowList = new ArrayList<Row>();
            ResultSetMetaData resultSetMetadata = resultSet.getMetaData();
            while (resultSet.next())
            {
            	int nullCell = 0;
                for (int c = 1; resultSetMetadata.getColumnCount() >= c; c++)
                {
                	if (resultSet.getString(c) == null || resultSet.getString(c).trim().equalsIgnoreCase("null"))
                	{
                		nullCell++;
                	}
                }
                if (nullCell != resultSetMetadata.getColumnCount())
                {
                	List<Cell> cellList = new ArrayList<Cell>();
	                for (int c = 1; resultSetMetadata.getColumnCount() >= c; c++)
	                {
	                    cellList.add(new Cell(resultSetMetadata.getColumnName(c), resultSet.getString(c)));
	                }
	                rowList.add(new Row(cellList));
                }
                else
                {
                	System.out.println("Info: Null row returned from " + functionName + "()!");
                }
            }
            resultSet.close();
        	statement.close();
        	connection.commit();
            closeConnection();
        }
    	else
        {
        	System.out.println("Error: Can't connect to server!");
        }
    	return rowList;
    }
    
    //Execute function and get one row
    public List<Cell> getCellList(String functionName) throws SQLException
    {
    	List<Cell> cellList = null;
    	if (Utilities.isEmpty(functionName))
		{
			System.out.println("Warning: Function name empty!");
			return cellList;
		}
    	
    	if (openConnection())
    	{
    		connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            ResultSet resultSet = null;
            try
            {
            	writeToConsole("SELECT * FROM " + functionName + "();");
            	resultSet = statement.executeQuery("SELECT * FROM " + functionName + "();");
            }
            catch (Exception ex)
            {
            	System.out.println("Error: Can't execute function!\nMessage: " + ex.toString());
            	statement.close();
                closeConnection();
                return cellList;
            }
            cellList = new ArrayList<Cell>();
            ResultSetMetaData resultSetMetadata = resultSet.getMetaData();
            for (int r = 0; resultSet.next(); r++)
            {
            	if (r == 0)
            	{
            		int nullCell = 0;
                    for (int c = 1; resultSetMetadata.getColumnCount() >= c; c++)
                    {
                    	if (resultSet.getString(c) == null || resultSet.getString(c).trim().equalsIgnoreCase("null"))
                    	{
                    		nullCell++;
                    	}
                    }
                    if (nullCell != resultSetMetadata.getColumnCount())
                    {
	            		cellList = new ArrayList<Cell>();
		                for (int c = 1; resultSetMetadata.getColumnCount() >= c; c++)
		                {
		                    cellList.add(new Cell(resultSetMetadata.getColumnName(c), resultSet.getString(c)));
		                }
                    }
                    else
                    {
                    	System.out.println("Info: Null row returned from " + functionName + "()!");
                    }
            	}
            	else
            	{
            		System.out.println("Warning: Function " + functionName + "() return rows! (r) = " + (r + 1));
            	}
            }
            resultSet.close();
        	statement.close();
        	connection.commit();
            closeConnection();
        }
    	else
        {
        	System.out.println("Error: Can't connect to server!");
        }
    	return cellList;
    }
    //Execute function with parameters and get one row
    public List<Cell> getCellList(String functionName, List<String> parameterList) throws SQLException
    {
    	List<Cell> cellList = null;
    	if (hasEmptyString(parameterList) || Utilities.isEmpty(functionName))
    	{
    		System.out.println("Warning: Function name empty or parameters are empty!");
    		return cellList;
    	}
    	
    	if (openConnection())
    	{
    		String query = "SELECT * FROM " + functionName + "(";
            for (int i = 0; parameterList.size() > i; i++)
            {
            	query = query + "'" + parameterList.get(i);
            	if ((parameterList.size() - 1) != i)
                {
                    query = query + "', ";
                }
                else
                {
                    query = query + "');";
                }
            }
    		connection.setAutoCommit(false);
    		Statement statement = connection.createStatement();
            ResultSet resultSet = null;
            try
            {
            	writeToConsole(query);
            	resultSet = statement.executeQuery(query);
            }
            catch (Exception ex)
            {
            	System.out.println("Error: Can't execute function!\nMessage: " + ex.toString());
            	statement.close();
                closeConnection();
                return cellList;
            }
            ResultSetMetaData resultSetMetadata = resultSet.getMetaData();
            for (int r = 0; resultSet.next(); r++)
            {
            	if (r == 0)
            	{
            		int nullCell = 0;
                    for (int c = 1; resultSetMetadata.getColumnCount() >= c; c++)
                    {
                    	if (resultSet.getString(c) == null || resultSet.getString(c).trim().equalsIgnoreCase("null"))
                    	{
                    		nullCell++;
                    	}
                    }
                    if (nullCell != resultSetMetadata.getColumnCount())
                    {
	            		cellList = new ArrayList<Cell>();
		                for (int c = 1; resultSetMetadata.getColumnCount() >= c; c++)
		                {
		                    cellList.add(new Cell(resultSetMetadata.getColumnName(c), resultSet.getString(c)));
		                }
                    }
                    else
                    {
                    	System.out.println("Info: Null row returned from " + functionName + "()!");
                    }
            	}
            	else
            	{
            		System.out.println("Warning: Function " + functionName + "() return rows! (r) = " + (r + 1));
            	}
            }
            resultSet.close();
        	statement.close();
        	connection.commit();
            closeConnection();
        }
    	else
        {
        	System.out.println("Error: Can't connect to server!");
        }
    	return cellList;
    }
    //Execute function with parameters and get one row
    public List<Cell> getCellList(String functionName, String[] parameters) throws SQLException
    {
    	List<Cell> cellList = null;
    	if (hasEmptyString(parameters) || Utilities.isEmpty(functionName))
    	{
    		System.out.println("Warning: Function name empty or parameters are empty!");
    		return cellList;
    	}
    	
    	if (openConnection())
    	{
    		String query = "SELECT * FROM " + functionName + "(";
            for (int i = 0; parameters.length > i; i++)
            {
            	query = query + "'" + parameters[i];
            	if ((parameters.length - 1) != i)
                {
                    query = query + "', ";
                }
                else
                {
                    query = query + "');";
                }
            }
    		connection.setAutoCommit(false);
    		Statement statement = connection.createStatement();
            ResultSet resultSet = null;
            try
            {
            	writeToConsole(query);
            	resultSet = statement.executeQuery(query);
            }
            catch (Exception ex)
            {
            	System.out.println("Error: Can't execute function!\nMessage: " + ex.toString());
            	statement.close();
                closeConnection();
                return cellList;
            }
            ResultSetMetaData resultSetMetadata = resultSet.getMetaData();
            for (int r = 0; resultSet.next(); r++)
            {
            	if (r == 0)
            	{
            		int nullCell = 0;
                    for (int c = 1; resultSetMetadata.getColumnCount() >= c; c++)
                    {
                    	if (resultSet.getString(c) == null || resultSet.getString(c).trim().equalsIgnoreCase("null"))
                    	{
                    		nullCell++;
                    	}
                    }
                    if (nullCell != resultSetMetadata.getColumnCount())
                    {
	            		cellList = new ArrayList<Cell>();
		                for (int c = 1; resultSetMetadata.getColumnCount() >= c; c++)
		                {
		                    cellList.add(new Cell(resultSetMetadata.getColumnName(c), resultSet.getString(c)));
		                }
                    }
                    else
                    {
                    	System.out.println("Info: Null row returned from " + functionName + "()!");
                    }
            	}
            	else
            	{
            		System.out.println("Warning: Function " + functionName + "() return rows! (r) = " + (r + 1));
            	}
            }
            resultSet.close();
        	statement.close();
        	connection.commit();
            closeConnection();
        }
    	else
        {
        	System.out.println("Error: Can't connect to server!");
        }
    	return cellList;
    }
    
    //Execute function and get one cell
    public Cell getCell(String functionName) throws SQLException
    {
    	Cell cell = null;
    	if (Utilities.isEmpty(functionName))
		{
			System.out.println("Warning: Function name empty!");
			return cell;
		}
    	
    	if (openConnection())
    	{
    		connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            ResultSet resultSet = null;
            try
            {
            	writeToConsole("SELECT " + functionName + "();");
            	resultSet = statement.executeQuery("SELECT " + functionName + "();");
            }
            catch (Exception ex)
            {
            	System.out.println("Error: Can't execute function!\nMessage: " + ex.toString());
            	statement.close();
                closeConnection();
                return cell;
            }
            ResultSetMetaData resultSetMetadata = resultSet.getMetaData();
            while (resultSet.next())
            {
            	int nullCell = 0;
                for (int c = 1; resultSetMetadata.getColumnCount() >= c; c++)
                {
                	if (resultSet.getString(c) == null || resultSet.getString(c).trim().equalsIgnoreCase("null"))
                	{
                		nullCell++;
                	}
                }
                if (nullCell != resultSetMetadata.getColumnCount())
                {
	            	for (int c = 1; resultSetMetadata.getColumnCount() >= c; c++)
	            	{
	            		cell = new Cell(resultSetMetadata.getColumnName(c), resultSet.getString(c));
	            	}
                }
                else
                {
                	System.out.println("Info: Null cell returned from " + functionName + "()!");
                }
            }
            resultSet.close();
        	statement.close();
        	connection.commit();
            closeConnection();
        }
    	else
        {
        	System.out.println("Error: Can't connect to server!");
        }
    	return cell;
    }
    //Execute function with parameters and get one cell
    public Cell getCell(String functionName, List<String> parameterList) throws SQLException
    {
    	Cell cell = null;
    	if (hasEmptyString(parameterList) || Utilities.isEmpty(functionName))
    	{
    		System.out.println("Warning: Function name empty or parameters are empty!");
    		return cell;
    	}
    	
    	if (openConnection())
    	{
    		String query = "SELECT " + functionName + "(";
            for (int i = 0; parameterList.size() > i; i++)
            {
            	query = query + "'" + parameterList.get(i);
            	if ((parameterList.size() - 1) != i)
                {
                    query = query + "', ";
                }
                else
                {
                    query = query + "');";
                }
            }
    		connection.setAutoCommit(false);
    		Statement statement = connection.createStatement();
            ResultSet resultSet = null;
            try
            {
            	writeToConsole(query);
            	resultSet = statement.executeQuery(query);
            }
            catch (Exception ex)
            {
            	System.out.println("Error: Can't execute function!\nMessage: " + ex.toString());
            	statement.close();
                closeConnection();
                return cell;
            }
            ResultSetMetaData resultSetMetadata = resultSet.getMetaData();
            while (resultSet.next())
            {
            	int nullCell = 0;
                for (int c = 1; resultSetMetadata.getColumnCount() >= c; c++)
                {
                	if (resultSet.getString(c) == null || resultSet.getString(c).trim().equalsIgnoreCase("null"))
                	{
                		nullCell++;
                	}
                }
                if (nullCell != resultSetMetadata.getColumnCount())
                {
	            	for (int c = 1; resultSetMetadata.getColumnCount() >= c; c++)
	            	{
	            		cell = new Cell(resultSetMetadata.getColumnName(c), resultSet.getString(c));
	            	}
                }
                else
                {
                	System.out.println("Info: Null cell returned from " + functionName + "()!");
                }
            }
            resultSet.close();
        	statement.close();
        	connection.commit();
            closeConnection();
        }
    	else
        {
        	System.out.println("Error: Can't connect to server!");
        }
    	return cell;
    }
    //Execute function with parameters and get one cell
    public Cell getCell(String functionName, String[] parameters) throws SQLException
    {
    	Cell cell = null;
    	if (hasEmptyString(parameters) || Utilities.isEmpty(functionName))
    	{
    		System.out.println("Warning: Function name empty or parameters are empty!");
    		return cell;
    	}
    	
    	if (openConnection())
    	{
    		String query = "SELECT " + functionName + "(";
            for (int i = 0; parameters.length > i; i++)
            {
            	query = query + "'" + parameters[i];
            	if ((parameters.length - 1) != i)
                {
                    query = query + "', ";
                }
                else
                {
                    query = query + "');";
                }
            }
    		connection.setAutoCommit(false);
    		Statement statement = connection.createStatement();
            ResultSet resultSet = null;
            try
            {
            	writeToConsole(query);
            	resultSet = statement.executeQuery(query);
            }
            catch (Exception ex)
            {
            	System.out.println("Error: Can't execute function!\nMessage: " + ex.toString());
            	statement.close();
                closeConnection();
                return cell;
            }
            ResultSetMetaData resultSetMetadata = resultSet.getMetaData();
            while (resultSet.next())
            {
            	int nullCell = 0;
                for (int c = 1; resultSetMetadata.getColumnCount() >= c; c++)
                {
                	if (resultSet.getString(c) == null || resultSet.getString(c).trim().equalsIgnoreCase("null"))
                	{
                		nullCell++;
                	}
                }
                if (nullCell != resultSetMetadata.getColumnCount())
                {
	            	for (int c = 1; resultSetMetadata.getColumnCount() >= c; c++)
	            	{
	            		cell = new Cell(resultSetMetadata.getColumnName(c), resultSet.getString(c));
	            	}
                }
                else
                {
                	System.out.println("Info: Null cell returned from " + functionName + "()!");
                }
            }
            resultSet.close();
        	statement.close();
        	connection.commit();
            closeConnection();
        }
    	else
        {
        	System.out.println("Error: Can't connect to server!");
        }
    	return cell;
    }
    
    //Execute non query function
    public boolean execute(String functionName) throws SQLException
    {
    	boolean isExecuted = false;
    	if (Utilities.isEmpty(functionName))
    	{
    		System.out.println("Warning: Function name is empty!");
    		return isExecuted;
    	}
    	
    	if (openConnection())
    	{
            connection.setAutoCommit(false);
        	Statement statement = connection.createStatement();
        	ResultSet resultSet = null;
            try
            {
            	writeToConsole("SELECT " + functionName + "();");
            	resultSet = statement.executeQuery("SELECT " + functionName + "();");
            }
            catch (Exception ex)
            {
            	System.out.println("Error: Can't execute function!\nMessage: " + ex.toString());
            	statement.close();
                closeConnection();
                return isExecuted;
            }
            ResultSetMetaData resultSetMetadata = resultSet.getMetaData();
            while (resultSet.next())
            {
            	for (int c = 1; resultSetMetadata.getColumnCount() >= c; c++)
            	{
            		if (!Utilities.isEmpty(resultSet.getString(c)))
            		{
            			if (resultSet.getString(c).trim().equalsIgnoreCase("t"))
            			{
            				isExecuted = true;
            			}
            			else if(resultSet.getString(c).trim().equalsIgnoreCase("f"))
            			{
            				System.out.println("Info: Execute non query function returns false value!");
            				isExecuted = false;
            			}
            			else
            			{
            				System.out.println("Warning: Execute non query function returns string value!");
            			}
            		}
            		else
            		{
            			System.out.println("Info: Execute non query function returns void value!");
            			isExecuted = true;
            		}
            	}
            }
            resultSet.close();
            statement.close();
            connection.commit();
            closeConnection();
    	}
    	else
        {
        	System.out.println("Error: Can't connect to server!");
        }
    	return isExecuted;
    }
    //Execute non query function
    public boolean execute(String functionName, List<String> parameterList) throws SQLException
    {
    	boolean isExecuted = false;
    	if (Utilities.isEmpty(functionName) || hasEmptyString(parameterList))
    	{
    		System.out.println("Warning: Function name is empty or parameters are empty!");
    		return isExecuted;
    	}
    	
    	if (openConnection())
    	{
    		String query = "SELECT " + functionName + "(";
            for (int i = 0; parameterList.size() > i; i++)
            {
            	query = query + "'" + parameterList.get(i);
            	if ((parameterList.size() - 1) != i)
                {
                    query = query + "', ";
                }
                else
                {
                    query = query + "');";
                }
            }
            connection.setAutoCommit(false);
        	Statement statement = connection.createStatement();
        	ResultSet resultSet = null;
            try
            {
            	writeToConsole(query);
            	resultSet = statement.executeQuery(query);
            }
            catch (Exception ex)
            {
            	System.out.println("Error: Can't execute function!\nMessage: " + ex.toString());
            	statement.close();
                closeConnection();
                return isExecuted;
            }
            ResultSetMetaData resultSetMetadata = resultSet.getMetaData();
            while (resultSet.next())
            {
            	for (int c = 1; resultSetMetadata.getColumnCount() >= c; c++)
            	{
            		if (!Utilities.isEmpty(resultSet.getString(c)))
            		{
            			if (resultSet.getString(c).trim().equalsIgnoreCase("t"))
            			{
            				isExecuted = true;
            			}
            			else if(resultSet.getString(c).trim().equalsIgnoreCase("f"))
            			{
            				System.out.println("Info: Execute non query function returns false value!");
            				isExecuted = false;
            			}
            			else
            			{
            				System.out.println("Warning: Execute non query function returns string value!");
            			}
            		}
            		else
            		{
            			System.out.println("Info: Execute non query function returns void value!");
            			isExecuted = true;
            		}
            	}
            }
            resultSet.close();
            statement.close();
            connection.commit();
            closeConnection();
    	}
    	else
        {
        	System.out.println("Error: Can't connect to server!");
        }
    	return isExecuted;
    }
    //Execute non query function
    public boolean execute(String functionName, String[] parameters) throws SQLException
    {
    	boolean isExecuted = false;
    	if (Utilities.isEmpty(functionName) || hasEmptyString(parameters))
    	{
    		System.out.println("Warning: Function name is empty or parameters are empty!");
    		return isExecuted;
    	}
    	
    	if (openConnection())
    	{
    		String query = "SELECT " + functionName + "(";
            for (int i = 0; parameters.length > i; i++)
            {
            	query = query + "'" + parameters[i];
            	if ((parameters.length - 1) != i)
                {
                    query = query + "', ";
                }
                else
                {
                    query = query + "');";
                }
            }
            connection.setAutoCommit(false);
        	Statement statement = connection.createStatement();
        	ResultSet resultSet = null;
            try
            {
            	writeToConsole(query);
            	resultSet = statement.executeQuery(query);
            }
            catch (Exception ex)
            {
            	System.out.println("Error: Can't execute function!\nMessage: " + ex.toString());
            	statement.close();
                closeConnection();
                return isExecuted;
            }
            ResultSetMetaData resultSetMetadata = resultSet.getMetaData();
            while (resultSet.next())
            {
            	for (int c = 1; resultSetMetadata.getColumnCount() >= c; c++)
            	{
            		if (!Utilities.isEmpty(resultSet.getString(c)))
            		{
            			if (resultSet.getString(c).trim().equalsIgnoreCase("t"))
            			{
            				isExecuted = true;
            			}
            			else if(resultSet.getString(c).trim().equalsIgnoreCase("f"))
            			{
            				System.out.println("Info: Execute non query function returns false value!");
            				isExecuted = false;
            			}
            			else
            			{
            				System.out.println("Warning: Execute non query function returns string value!");
            			}
            		}
            		else
            		{
            			System.out.println("Info: Execute non query function returns void value!");
            			isExecuted = true;
            		}
            	}
            }
            resultSet.close();
            statement.close();
            connection.commit();
            closeConnection();
    	}
    	else
        {
        	System.out.println("Error: Can't connect to server!");
        }
    	return isExecuted;
    }
	
	public Connection getConnection()
	{
		return connection;
	}
	public void setConnection(Connection connection)
	{
		this.connection = connection;
	}

	public String getServer()
	{
		return server;
	}
	public void setServer(String server)
	{
		this.server = server;
	}

	public String getPort()
	{
		return port;
	}
	public void setPort(String port)
	{
		this.port = port;
	}

	public String getDatabase()
	{
		return database;
	}
	public void setDatabase(String database)
	{
		this.database = database;
	}

	public String getUser()
	{
		return user;
	}
	public void setUser(String user)
	{
		this.user = user;
	}

	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	private boolean hasEmptyColumn(List<Cell> cellList)
	{
		if (cellList != null && cellList.size() > 0)
		{
			for (Cell cell : cellList)
			{
				if (Utilities.isEmpty(cell.getColumn()))
				{
					return true;
				}
			}
		}
		else
		{
			return true;
		}
		return false;
	}
	
	private boolean hasEmptyString(List<String> stringList)
	{
		if (stringList != null && stringList.size() > 0)
		{
			for (String string : stringList)
			{
				if (Utilities.isEmpty(string))
				{
					return true;
				}
			}
		}
		else
		{
			return true;
		}
		return false;
	}
	private boolean hasEmptyString(String[] strings)
	{
		if (strings != null && strings.length > 0)
		{
			for (String string : strings)
			{
				if (Utilities.isEmpty(string))
				{
					return true;
				}
			}
		}
		else
		{
			return true;
		}
		return false;
	}
	
	// - Not necessary OR Write to log...
	private void writeToConsole(String query)
	{
		System.out.println(query);
	}
}
