/**
 * 
 */
package com.csu.uas.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;

import com.csu.uas.bean.LoginBean;
import com.csu.uas.exception.UasException;

/**
 * @author Ravi Theja V
 *
 */
/*****************************************************************
                Date Created :  Sep 2, 2015
        		File Name    :  LoginDao.java    
                Package Name :  com.csu.uas.dao
                ProjectName  :  UniversityAdmissionSystem
                User         :  Ravi Theja V
*******************************************************************/
public class LoginDao 
{
	static Connection con=null;
	PreparedStatement psmt=null;
	
	/******************************************
	* Return Type     : String
	* Method Name     : isValidUserCredentialsDao
	* Input Parameters:@param loginBean
	* Input Parameters:@return
	 * @throws UasException 
	*******************************************/
	public String isValidUserCredentialsDao(LoginBean loginBean) throws UasException
	{
		try 
		{
			System.out.println(loginBean.getUserName());
			con=DBConnection.getConnection();
			String querySelect="select userRole from userTable where username=? and password=?";
			
			PreparedStatement psmt=con.prepareStatement(querySelect);
			psmt.setString(1,loginBean.getUserName());
			psmt.setString(2,loginBean.getPassword());
			ResultSet rs=psmt.executeQuery();
			if(rs.next())
			{
				loginBean.setRole(rs.getString(1));
			}
			else
			{
				loginBean.setRole(null);
			}
		}
		catch (ClassNotFoundException e) 
		{
			throw new UasException("CLASS NOT FOUND EXCEPTION");
		} 
		catch (SQLException e) 
		{
			throw new UasException("SQL EXCEPTION RAISED");
		} 
		catch (IOException e) 
		{
			throw new UasException("IO EXCEPTION RAISED");
		} 
		catch (NamingException e) 
		{
			throw new UasException("NAMING EXCEPTION");
		}
		catch (Exception e)
		{
			throw new UasException(e.getMessage());
		}
		finally
		{
			try 
			{
				con.close();
			} 
			catch (SQLException e) 
			{
				throw new UasException("SQL EXCEPTION");
			}
		}
		return loginBean.getRole();
	}
}
