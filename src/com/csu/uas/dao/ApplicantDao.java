/**
 * 
 */
package com.csu.uas.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import com.csu.uas.bean.Applicant;
import com.csu.uas.bean.Program;
import com.csu.uas.exception.UasException;
/**
 * @author Ravi Theja V
 *
 */
public class ApplicantDao 
{
	static Connection con;
	PreparedStatement psmt;
	ResultSet resultset;
	
	/******************************************
	* Return Type     : Applicant
	* Method Name     : viewApplicationStatusDao
	* Input Parameters:@param applicationID
	* Input Parameters:@return
	* Input Parameters:@throws UasException
	*******************************************/
	public List<String> viewApplicationStatusDao(String applicationID) throws UasException
	{
		List<String> list=new ArrayList<String>();
		
		try 
		{
			con=DBConnection.getConnection();
			
			String query="select applicantname,status  from applicant  where applicantid=?";
			psmt=con.prepareStatement(query);
			psmt.setInt(1,Integer.parseInt(applicationID));
			resultset=psmt.executeQuery();
			while(resultset.next())
			{
				list.add(resultset.getString(1));
				list.add(resultset.getString(2));
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
		return list;
	}
	
	/******************************************
	* Return Type     : String
	* Method Name     : submitApplicationDao
	* Input Parameters:@param applicant
	* Input Parameters:@return
	* Input Parameters:@throws UasException
	*******************************************/
	public String submitApplicationDao(Applicant applicant) throws UasException
	{
		int appSequence=0;
		String applicantid="";
		try 
		{
			con=DBConnection.getConnection();
			
			System.out.println("in dao ra rai");
			String querySequence="select next value FOR applicantSequence";
			psmt=con.prepareStatement(querySequence);
			applicant.setStatus("Applied");
			
			resultset= psmt.executeQuery();
			if(resultset.next())
			{
				appSequence=resultset.getInt(1);
			}
			applicantid="App"+appSequence;
					
			
			String query="insert into applicant values(?,?,?,?,?,?,?,?,?,?)";

			psmt=con.prepareStatement(query);
			
			java.sql.Date dateOfBirth=new java.sql.Date(applicant.getApplicantDob().getTime());
			
			System.out.println("2 after date");
			
			java.util.Date sysInterViewDate=new java.util.Date();
			java.sql.Date dateOfInterview=new java.sql.Date(sysInterViewDate.getTime());
			
			psmt.setInt(1,appSequence);
			psmt.setString(2,applicant.getApplicantName());
			psmt.setDate(3,dateOfBirth);
			psmt.setString(4,applicant.getHighestQualification());
			psmt.setDouble(5,applicant.getMarksObtained());
			psmt.setString(6,applicant.getGoals());
			psmt.setString(7,applicant.getEmailID());
			psmt.setInt(8,Integer.parseInt(applicant.getScheduledProgramID()));
			psmt.setString(9,applicant.getStatus());
			psmt.setDate(10,dateOfInterview);
			
			
			
			int i=psmt.executeUpdate();
				if(i>0)
				{
					return applicantid;
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
		return applicantid;
	}
	/******************************************
	* Return Type     : List<Program>
	* Method Name     : viewProgramInfo
	* Input Parameters:@return
	* Input Parameters:@throws UasException
	*******************************************/
	public List<Program> viewProgramInfo() throws UasException
	{
		List<Program> list=new ArrayList<Program>();
		
		try 
		{
			con=DBConnection.getConnection();
			
			String query="select * from programscheduled ps,programoffered po  where po.programid= ps.programid1";
			psmt=con.prepareStatement(query);
			resultset=psmt.executeQuery();
			while(resultset.next())
			{
					Program program=new Program();
					program.setScheduledProgramID(String.valueOf(resultset.getInt("PROGRAMID")));
					program.setProgramName(resultset.getString("PROGRAMNAME"));
					program.setProgramDescrption(resultset.getString("ProgramDESCRIPTION"));
					program.setApplicantEligibily(resultset.getString("applicantEligibility"));
					program.setDegreeCertificateOffered(resultset.getString("DEGREECERTIFICATIONOFFERED"));
					program.setProgramDuration(Integer.parseInt(resultset.getString("ProgramDURATION")));
					program.setProgramStartDate(resultset.getDate("ProgramSTARTDATE").toString());
					program.setProgramEndDate(resultset.getDate("ProgramENDDATE").toString());
					program.setSessionsPerWeek(resultset.getString("SESSIONSPERWEEK"));
					program.setProgramLocaiton(resultset.getString("LOCATION"));
					
					list.add(program);
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
		
		return list;
		
	}
	public List<String> getScheduledProgramID() throws UasException
	{
			List<String> list=new ArrayList<String>();
		
		try 
		{
			con=DBConnection.getConnection();
			
			String selectProgramIDquery="select programid from programscheduled";
			
			PreparedStatement psmt=con.prepareStatement(selectProgramIDquery);
			
			ResultSet resultSet=psmt.executeQuery();
			while(resultSet.next())
			{
				list.add(String.valueOf(resultSet.getInt(1)));
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
		return list;
	}
}
