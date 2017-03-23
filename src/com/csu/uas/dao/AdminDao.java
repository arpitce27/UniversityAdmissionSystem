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

import com.csu.uas.bean.Program;
import com.csu.uas.exception.UasException;

/**
 * @author Ravi Theja V
 *
 */
public class AdminDao 
{
	Connection con;
	PreparedStatement psmt;
	ResultSet resultset;
	
	

	/******************************************
	* Return Type     : boolean
	* Method Name     : updateProgramOffered
	* Input Parameters:@param program
	* Input Parameters:@return
	* Input Parameters:@throws UasException
	*******************************************/
	public boolean updateProgramOffered(Program program) throws UasException
	{
		try 
		{
			con=DBConnection.getConnection();
			
			String updateQuery="update programofferedteam6 set description=?, applicanteligibilty=? where programid=?";
			
			
			System.out.println("***********");
			System.out.println(program.getApplicantEligibily());
			System.out.println(program.getProgramDescrption());
			System.out.println(program.getProgramID());
			psmt=con.prepareStatement(updateQuery);
			psmt.setString(1,program.getProgramDescrption());
			psmt.setString(2,program.getApplicantEligibily());
			psmt.setString(3,program.getProgramID());
			int i=psmt.executeUpdate();
			if(i>0)
			{
				return true;
			}
			else
			{
				return false;
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
	}
	/******************************************
	* Return Type     : List<Program>
	* Method Name     : viewProgramsOfferedDao
	* Input Parameters:@return
	* Input Parameters:@throws UasException
	*******************************************/
	public List<Program> viewProgramsOfferedDao() throws UasException
	{
		
		List<Program> list=new ArrayList<Program>();
		try 
		{
			con=DBConnection.getConnection();
			String querySelect="select * from programoffered";
			psmt=con.prepareStatement(querySelect);
			ResultSet rs=psmt.executeQuery();
			while(rs.next())
			{
				Program program=new Program();
				program.setProgramName(rs.getString(2));
				program.setProgramDescrption(rs.getString(3));
				program.setApplicantEligibily(rs.getString(4));
				program.setProgramDuration(rs.getInt(5));
				program.setDegreeCertificateOffered(rs.getString(6));
				int id=rs.getInt(1);
				program.setProgramID(String.valueOf(id));
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

	/******************************************
	* Return Type     : boolean
	* Method Name     : addProgramDao
	* Input Parameters:@param program
	* Input Parameters:@return
	* Input Parameters:@throws UasException
	*******************************************/
	public boolean addProgramDao(Program program) throws UasException 
	{
		try 
		{
			con=DBConnection.getConnection();
			int programSequence=0;
			String programID="";
			String queryProgram="select next value FOR programofferedteam6 ";
			psmt=con.prepareStatement(queryProgram);
			ResultSet rs=psmt.executeQuery();
			if(rs.next())
			{
				programSequence=rs.getInt(1);
			}
			
			programID="program"+programSequence;
			String queryInsert="insert into programoffered values(?,?,?,?,?,?)";
			psmt=con.prepareStatement(queryInsert);
			psmt.setString(2,program.getProgramName());
			psmt.setString(3,program.getProgramDescrption());
			psmt.setString(4,program.getApplicantEligibily());
			psmt.setInt(5,program.getProgramDuration());
			psmt.setString(6,program.getDegreeCertificateOffered());
			psmt.setInt(1,programSequence);
			int result=psmt.executeUpdate();
			if(result>0)
			{
				return true;
			}
			else
			{
				return false;
			}
			
		}
		catch (ClassNotFoundException e) 
		{
			throw new UasException("CLASS NOT FOUND EXCEPTION");
		} 
		catch (SQLException e) 
		{
			throw new UasException("SQL EXCEPTION");
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
	}
	
	/******************************************
	* Return Type     : boolean
	* Method Name     : deleteProgramOfferedDao
	* Input Parameters:@param programID
	* Input Parameters:@return
	* Input Parameters:@throws UasException
	*******************************************/
	public boolean deleteProgramOfferedDao(String programID) throws UasException
	{
		try 
		{
			con=DBConnection.getConnection();
			
			String deleteQuery="delete programofferedteam6 where programid=?";
			System.out.println("in dao");
			System.out.println(programID);
			psmt=con.prepareStatement(deleteQuery);
			psmt.setString(1,programID);
			int result=psmt.executeUpdate();
			
			if(result>0)
			{
				return true;
			}
			else
			{
				return false;
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
		
	}
	
	/******************************************
	* Return Type     : boolean
	* Method Name     : scheduleProgramDao
	* Input Parameters:@param program
	* Input Parameters:@return
	* Input Parameters:@throws UasException
	*******************************************/
	public boolean scheduleProgramDao(Program program) throws UasException
	{
		try 
		{
			con=DBConnection.getConnection();
			int programSequence=0;
			String scheduledprogramID="";
			String queryProgram="select next value FOR scheduled_program"; //query for scheduled program id- sequence here
			psmt=con.prepareStatement(queryProgram);
			ResultSet rs=psmt.executeQuery();
			if(rs.next())
			{
				programSequence=rs.getInt(1);
			}
			
			
			String queryProgramname="select programname from programoffered where programid=?";
			psmt=con.prepareStatement(queryProgramname);
			psmt.setString(1,program.getProgramID());
			ResultSet result=psmt.executeQuery();
			while(result.next())
			{
				System.out.println("*************");
				program.setProgramName(result.getString(1));
				System.out.println(result.getString(1));
				System.out.println(program.getProgramName());
			}
			
			
			scheduledprogramID="scheduled"+programSequence+program.getProgramID();
			String queryInsert="insert into programscheduled values(?,?,?,?,?,?,?)"; // query for scheduling a program here
			psmt=con.prepareStatement(queryInsert);
				
			psmt.setInt(1,programSequence);
			psmt.setString(2,program.getProgramName());
			
			psmt.setString(3,program.getProgramStartDate());
			psmt.setString(4,program.getProgramEndDate());
			psmt.setString(5,program.getSessionsPerWeek());
			psmt.setInt(6,Integer.parseInt(program.getProgramID()));
			psmt.setString(7,program.getProgramLocaiton());
			int resultMain=psmt.executeUpdate();
			
			if(resultMain>0)
			{
				return true;
			}
			else
			{
				return false;
			}
			
		}
		catch (ClassNotFoundException e) 
		{
			throw new UasException("CLASS NOT FOUND EXCEPTION");
		} 
		catch (SQLException e) 
		{
			throw new UasException(e.toString());
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
	}
	/******************************************
	* Return Type     : List<Program>
	* Method Name     : viewScheduledProgramDao
	* Input Parameters:@return
	* Input Parameters:@throws UasException
	*******************************************/
	public List<Program> viewScheduledProgramDao() throws UasException
	{
		List<Program> list=new ArrayList<Program>();
		
		
		try 
		{
			con=DBConnection.getConnection();
			String querySelect="select * from programscheduled";  //select  query for retrieving the scheduled programs 
			psmt=con.prepareStatement(querySelect);
			ResultSet rs=psmt.executeQuery();
			while(rs.next())
			{
				Program program=new Program();
					
				program.setScheduledProgramID(String.valueOf(rs.getInt(1)));
				program.setProgramName(rs.getString(2));
				program.setProgramLocaiton(rs.getString(7));
				program.setProgramStartDate(rs.getDate(3).toString());
				program.setProgramEndDate(rs.getDate(4).toString());
				program.setSessionsPerWeek(rs.getString(5));
				program.setProgramID(String.valueOf(rs.getInt(6)));
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
	/******************************************
	* Return Type     : List<String>
	* Method Name     : getProgramIDDao
	* Input Parameters:@return
	 * @throws UasException 
	*******************************************/
	public List<String> getProgramIDDao() throws UasException
	{
		List<String> list=new ArrayList<String>();
		
		try 
		{
			con=DBConnection.getConnection();
			
			String selectProgramIDquery="select programid from programoffered";
			
			PreparedStatement psmt=con.prepareStatement(selectProgramIDquery);
			
			ResultSet resultSet=psmt.executeQuery();
			while(resultSet.next())
			{
				list.add(resultSet.getString(1));
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
	* Return Type     : boolean
	* Method Name     : updateScheduledProgramDao
	* Input Parameters:@param program
	* Input Parameters:@return
	* Input Parameters:@throws UasException
	*******************************************/
	public boolean updateScheduledProgramDao(Program program) throws UasException
	{
		try 
		{
			con=DBConnection.getConnection();
			
			String updateQuery="update program_scheduledteam6 set startdate=?, enddate=?, sessions_per_week=? where scheduled_program_id=?";
			psmt=con.prepareStatement(updateQuery);
			
			System.out.println(program.getProgramStartDate());
			System.out.println(program.getProgramEndDate());
			System.out.println(program.getSessionsPerWeek());
			System.out.println(program.getScheduledProgramID());
			

			psmt.setString(1,program.getProgramStartDate());
			psmt.setString(2,program.getProgramEndDate());
			psmt.setString(3,program.getSessionsPerWeek());
			psmt.setString(4,program.getScheduledProgramID());
			int i=psmt.executeUpdate();
			if(i>0)
			{
				System.out.println("true");
				return true;
			}
			else
			{
				System.out.println("false");
				return false;
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
	}
	/******************************************
	* Return Type     : boolean
	* Method Name     : deleteProgramScheduled
	* Input Parameters:@param schduledProgramID
	* Input Parameters:@return
	* Input Parameters:@throws UasException
	*******************************************/
	public boolean deleteProgramScheduled(String schduledProgramID) throws UasException
	{
		try 
		{
			con=DBConnection.getConnection();
			String deleteQuery="delete program_scheduledteam6 where scheduled_program_id=?";
			psmt=con.prepareStatement(deleteQuery);
			psmt.setString(1,schduledProgramID);
			int result=psmt.executeUpdate();
			
			if(result>0)
			{
				return true;
			}
			else
			{
				return false;
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
	}
	/******************************************
	* Return Type     : boolean
	* Method Name     : isProgramScheduled
	* Input Parameters:@param programID
	* Input Parameters:@return
	* Input Parameters:@throws UasException
	*******************************************/
	public boolean isProgramScheduled(String programID) throws UasException
	{
		try 
		{
			con=DBConnection.getConnection();
			String selectProgramScheduledID="select scheduled_program_id  from program_scheduledteam6 where programid=?";
			psmt=con.prepareStatement(selectProgramScheduledID);
			psmt.setString(1,programID);
			ResultSet result=null;
			result=psmt.executeQuery();
			if(result.next() && result!=null)
			{
				return true;
			}
			else
			{
				return false;
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
	}
	/******************************************
	* Return Type     : boolean
	* Method Name     : isParticipants
	* Input Parameters:@param scheduledProgramID
	* Input Parameters:@return
	* Input Parameters:@throws UasException
	*******************************************/
	public boolean isParticipantsExistDao(String scheduledProgramID) throws UasException
	{
		try 
		{
			con=DBConnection.getConnection();
			String selectCountOfparticipants="select count(*) from applicantteam6 where scheduledprogramid=?";
			psmt=con.prepareStatement(selectCountOfparticipants);
			psmt.setString(1,scheduledProgramID);
			ResultSet result=null;
			result=psmt.executeQuery();
			int count=0;
			if(result.next() && result!=null)
			{
				count=result.getInt(1);
				if(count>0)
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			else
			{
				return false;
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
	}
	
	public List<String> getScheduledProgramIDDao() throws UasException
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