/**
 * 
 */
package com.csu.uas.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.naming.NamingException;

import com.csu.uas.bean.*;
import com.csu.uas.exception.UasException;

/**
 * @author Ravi Theja V
 * 
 */
public class MacDao {
	Connection con;
	PreparedStatement psmt;
	ResultSet resultset;

	public List<String> populateScheduledProgramIds() throws UasException {

		List<String> list = new ArrayList<String>();
		try {
			con = DBConnection.getConnection();
			psmt = con
					.prepareStatement("select programId from programscheduled");
			resultset = psmt.executeQuery();
			while (resultset.next()) {
				list.add(String.valueOf(resultset.getInt(1)));
			}
			con.close();

			return list;
		} catch (ClassNotFoundException e) {
			throw new UasException("CLASS NOT FOUND EXCEPTION");
		} catch (SQLException e) {
			throw new UasException("SQL EXCEPTION RAISED");
		} catch (IOException e) {
			throw new UasException("IO EXCEPTION RAISED");
		} catch (NamingException e) {
			throw new UasException("NAMING EXCEPTION");
		} catch (Exception e) {
			throw new UasException(e.getMessage());
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				throw new UasException("SQL EXCEPTION");
			}
		}
	}

	public List<Applicant> getApplicantions(String selectedProgramId,
			String selectedStatus) throws UasException {
		try {

			List<Applicant> list = new ArrayList<Applicant>();
			con = DBConnection.getConnection();
			psmt = con
					.prepareStatement("select * from applicant where scheduledprogramid=? and status=?");
			psmt.setInt(1, Integer.parseInt(selectedProgramId));
			psmt.setString(2, selectedStatus);
			resultset = psmt.executeQuery();
			while (resultset.next()) {
				java.util.Date interviewDate = null;
				Applicant applicant = new Applicant();
				java.util.Date applicantDob = new Date(resultset.getDate(3)
						.getTime());
				if (resultset.getDate(10) != null) {
					interviewDate = new Date(resultset.getDate(10).getTime());
				}
				applicant.setApplicantID(String.valueOf(resultset.getInt(1)));
				applicant.setApplicantName(resultset.getString(2));
				applicant.setApplicantDob(applicantDob);
				applicant.setHighestQualification(resultset.getString(4));
				applicant.setMarksObtained(resultset.getFloat(5));
				applicant.setGoals(resultset.getString(6));
				applicant.setEmailID(resultset.getString(7));
				applicant.setScheduledProgramID(String.valueOf(resultset.getInt(8)));
				applicant.setStatus(resultset.getString(9));
				applicant.setInterviewDate(interviewDate);
				list.add(applicant);

			}
			return list;

		} catch (ClassNotFoundException e) {
			throw new UasException("CLASS NOT FOUND EXCEPTION");
		} catch (SQLException e) {
			throw new UasException("SQL EXCEPTION RAISED");
		} catch (IOException e) {
			throw new UasException("IO EXCEPTION RAISED");
		} catch (NamingException e) {
			throw new UasException("NAMING EXCEPTION");
		} catch (Exception e) {
			throw new UasException(e.getMessage());
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				throw new UasException("SQL EXCEPTION");
			}
		}

	}

	public void updateStatusToAccepted(String status, Date updateDate,
			String updateApplicationId) throws UasException {
		try {
			con = DBConnection.getConnection();
			psmt = con
					.prepareStatement("update applicantteam6 set status=?,interviewdate=? where applicantid=?");
			psmt.setString(1, status);
			psmt.setDate(2, updateDate);
			psmt.setString(3, updateApplicationId);
			psmt.executeUpdate();
			con.close();
		} catch (ClassNotFoundException e) {
			throw new UasException("CLASS NOT FOUND EXCEPTION");
		} catch (SQLException e) {
			throw new UasException("SQL EXCEPTION RAISED");
		} catch (IOException e) {
			throw new UasException("IO EXCEPTION RAISED");
		} catch (NamingException e) {
			throw new UasException("NAMING EXCEPTION");
		} catch (Exception e) {
			throw new UasException(e.getMessage());
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				throw new UasException("SQL EXCEPTION");
			}
		}

	}

	public void updateStatusToReject(String status, String updateApplicationId)
			throws UasException {
		try {
			con = DBConnection.getConnection();
			psmt = con
					.prepareStatement("update applicantteam6 set status=? where applicantid=?");
			psmt.setString(1, status);
			psmt.setString(2, updateApplicationId);
			psmt.executeUpdate();
			con.close();
		} catch (ClassNotFoundException e) {
			throw new UasException("CLASS NOT FOUND EXCEPTION");
		} catch (SQLException e) {
			throw new UasException("SQL EXCEPTION RAISED");
		} catch (IOException e) {
			throw new UasException("IO EXCEPTION RAISED");
		} catch (NamingException e) {
			throw new UasException("NAMING EXCEPTION");
		} catch (Exception e) {
			throw new UasException(e.getMessage());
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				throw new UasException("SQL EXCEPTION");
			}
		}

	}

	public void updateToConfirm(String confirmApplicationId)
			throws UasException {
		String programId = null;
		String emailId = null;
		try {
			con = DBConnection.getConnection();

			psmt = con
					.prepareStatement("select SCHEDULEDPROGRAMID,EMAILID,INTERVIEWDATE from applicantteam6 where applicantid=?");
			psmt.setString(1, confirmApplicationId);
			resultset = psmt.executeQuery();
			if (resultset.next()) {
				java.util.Date currentDate = new java.util.Date();
				java.sql.Date interviewDate = resultset.getDate(3);
				Calendar cal = Calendar.getInstance();
				cal.setTime(currentDate);
				Calendar cal1 = Calendar.getInstance();
				cal1.setTime(interviewDate);
				int res = cal.compareTo(cal1);
				if (res < 0) {
					throw new UasException("Interview yet to be done");
				}

				programId = resultset.getString(1);
				emailId = resultset.getString(2);

			}

			con.setAutoCommit(false);
			psmt = con
					.prepareStatement("insert into participant_team6 values(?,?,?,?)");
			psmt.setString(1, "roll" + confirmApplicationId);
			psmt.setString(2, confirmApplicationId);
			psmt.setString(3, programId);
			psmt.setString(4, emailId);
			psmt.executeUpdate();

			System.out.println("in the dao method");
			psmt = con
					.prepareStatement("update applicantteam6 set status=? where applicantid=?");
			psmt.setString(1, "Confirmed");
			psmt.setString(2, confirmApplicationId);
			psmt.executeUpdate();

			con.setAutoCommit(true);
			con.commit();
			con.close();

		} catch (ClassNotFoundException e) {
			throw new UasException("CLASS NOT FOUND EXCEPTION");
		} catch (SQLException e) {
			throw new UasException("SQL EXCEPTION RAISED");
		} catch (IOException e) {
			throw new UasException("IO EXCEPTION RAISED");
		} catch (NamingException e) {
			throw new UasException("NAMING EXCEPTION");
		} catch (Exception e) {
			throw new UasException(e.getMessage());
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				throw new UasException("SQL EXCEPTION");
			}
		}
	}

	public void updateAcceptToReject(String rejectApplicationId)
			throws UasException {
		try {
			con = DBConnection.getConnection();
			psmt = con
					.prepareStatement("select INTERVIEWDATE from applicantteam6 where applicantid=?");
			psmt.setString(1, rejectApplicationId);
			resultset = psmt.executeQuery();
			if (resultset.next()) {
				java.util.Date currentDate = new java.util.Date();
				java.sql.Date interviewDate = resultset.getDate(1);
				Calendar cal = Calendar.getInstance();
				cal.setTime(currentDate);
				Calendar cal1 = Calendar.getInstance();
				cal1.setTime(interviewDate);
				int res = cal.compareTo(cal1);
				if (res < 0) {
					throw new UasException("Interview yet to be done");
				} else {
					psmt = con
							.prepareStatement("update applicantteam6 set status=? where applicantid=? ");
					psmt.setString(1, "Rejected");
					psmt.setString(2, rejectApplicationId);
					psmt.executeUpdate();
				}
			}

		} catch (ClassNotFoundException e) {
			throw new UasException("CLASS NOT FOUND EXCEPTION");
		} catch (SQLException e) {
			throw new UasException("SQL EXCEPTION RAISED");
		} catch (IOException e) {
			throw new UasException("IO EXCEPTION RAISED");
		} catch (NamingException e) {
			throw new UasException("NAMING EXCEPTION");
		} catch (Exception e) {
			throw new UasException(e.getMessage());
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				throw new UasException("SQL EXCEPTION");
			}
		}
	}

}
