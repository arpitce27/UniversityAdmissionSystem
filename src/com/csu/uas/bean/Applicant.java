package com.csu.uas.bean;

import java.util.Date;

public class Applicant 
{
	private String applicantID;
	private String applicantName;
	private java.util.Date applicantDob;
	private String highestQualification;
	private double marksObtained;
	private String goals;
	private String emailID;
	private String scheduledProgramID;
	private String status;
	private java.util.Date interviewDate;
	
	/**
	 * @return the interviewDate
	 */
	public Date getInterviewDate() {
		return interviewDate;
	}
	/**
	 * @param interviewDate the interviewDate to set
	 */
	public void setInterviewDate(Date interviewDate) {
		this.interviewDate = interviewDate;
	}
	/**
	 * @return the applicantID
	 */
	public String getApplicantID() 
	{
		return applicantID;
	}
	/**
	 * @param applicantID the applicantID to set
	 */
	public void setApplicantID(String applicantID) 
	{
		this.applicantID = applicantID;
	}
	/**
	 * @return the applicantName
	 */
	public String getApplicantName() 
	{
		return applicantName;
	}
	/**
	 * @param applicantName the applicantName to set
	 */
	public void setApplicantName(String applicantName) 
	{
		this.applicantName = applicantName;
	}
	/**
	 * @return the applicantDob
	 */
	public Date getApplicantDob() 
	{
		return applicantDob;
	}
	/**
	 * @param applicantDob the applicantDob to set
	 */
	public void setApplicantDob(Date applicantDob) 
	{
		this.applicantDob = applicantDob;
	}
	/**
	 * @return the highestQualification
	 */
	public String getHighestQualification() 
	{
		return highestQualification;
	}
	/**
	 * @param highestQualification the highestQualification to set
	 */
	public void setHighestQualification(String highestQualification) 
	{
		this.highestQualification = highestQualification;
	}
	/**
	 * @return the marksObtained
	 */
	public double getMarksObtained() 
	{
		return marksObtained;
	}
	/**
	 * @param marksObtained the marksObtained to set
	 */
	public void setMarksObtained(double marksObtained) 
	{
		this.marksObtained = marksObtained;
	}
	/**
	 * @return the goals
	 */
	public String getGoals() 
	{
		return goals;
	}
	/**
	 * @param goals the goals to set
	 */
	public void setGoals(String goals) 
	{
		this.goals = goals;
	}
	/**
	 * @return the emailID
	 */
	public String getEmailID() 
	{
		return emailID;
	}
	/**
	 * @param emailID the emailID to set
	 */
	public void setEmailID(String emailID) 
	{
		this.emailID = emailID;
	}
	/**
	 * @return the scheduledProgramID
	 */
	public String getScheduledProgramID() 
	{
		return scheduledProgramID;
	}
	/**
	 * @param scheduledProgramID the scheduledProgramID to set
	 */
	public void setScheduledProgramID(String scheduledProgramID) 
	{
		this.scheduledProgramID = scheduledProgramID;
	}
	/**
	 * @return the status
	 */
	public String getStatus() 
	{
		return status;
	}
	
	/**
	 * @param status the status to set
	 */
	
	public void setStatus(String status) 
	{
		this.status = status;
	}
}
