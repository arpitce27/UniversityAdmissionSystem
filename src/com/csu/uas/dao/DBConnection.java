/**
 * 
 */
package com.csu.uas.dao;
import java.io.IOException;
import java.sql.*;

import javax.naming.NamingException;


/**
 * @author Ravi Theja V
 *
 */
/*****************************************************************
                Date Created :  Sep 2, 2015
        		File Name    :  DBConnection.java    
                Package Name :  com.csu.uas.dao
                ProjectName  :  UniversityAdmissionSystem
                User         :  Ravi Theja V
*******************************************************************/
public class DBConnection 
{
	/******************************************
	* Return Type     : Connection
	* Method Name     : getConnection
	* Input Parameters:@return
	* Input Parameters:@throws ClassNotFoundException
	* Input Parameters:@throws SQLException
	* Input Parameters:@throws IOException
	* Input Parameters:@throws NamingException
	*******************************************/
	/*public static Connection getConnection() throws ClassNotFoundException, SQLException, IOException, NamingException
	{
		InitialContext ic;
	
			ic = new InitialContext();
			DataSource dataSource=(DataSource) ic.lookup("java:/OracleDS");
			System.out.println(dataSource);
			Connection con=dataSource.getConnection();
			System.out.println(con);
		return con;
		Context initContext = new InitialContext();
        Context envContext  = (Context)initContext.lookup("java:/comp/env");
        DataSource ds = (DataSource)envContext.lookup("jdbc/myoracle");
        Connection con = ds.getConnection();
        return con;
	}*/
	
	
	
	public static Connection getConnection()throws ClassNotFoundException, SQLException, IOException, NamingException {
		// TODO Auto-generated method stub
		// Create a variable for the connection string.
				String connectionUrl = "jdbc:sqlserver://localhost:1433;" +
					"databaseName=UAS;integratedSecurity=false;username=sa;password=sa";

				// Declare the JDBC objects.
				Connection con = null;
				
		        	try {
		        		// Establish the connection.
		        		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		            		con = DriverManager.getConnection(connectionUrl);
		            
		            		
		        	}
		        
				// Handle any errors that may have occurred.
				catch (Exception e) {
					e.printStackTrace();
				}

				
		        	return con;

	}
}
