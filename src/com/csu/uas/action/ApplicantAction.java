/**
 * 
 */
package com.csu.uas.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.csu.uas.bean.Applicant;
import com.csu.uas.exception.UasException;
import com.csu.uas.service.AdminService;
import com.csu.uas.service.ApplicantService;
import com.csu.uas.service.MacService;
/*****************************************************************
                Date Created :  Aug 30, 2015
        		File Name    :  ApplicantAction.java    
                Package Name :  com.csu.uas.action
                ProjectName  :  UniversityAdmissionSystem
                User         :  Ravi Theja V
 *******************************************************************/

@SuppressWarnings("serial")
public class ApplicantAction extends ActionSupport implements ModelDriven<Applicant>
{
	Applicant applicant=new Applicant();
	private String statusApplicant;
	static private String statusApplicantDup;
	List<String> retrievedStatus=new ArrayList<String>(); 
	
	/**
	 * @return the retrievedStatus
	 */
	public List<String> getRetrievedStatus() {
		return retrievedStatus;
	}



	/**
	 * @param retrievedStatus the retrievedStatus to set
	 */
	public void setRetrievedStatus(List<String> retrievedStatus) {
		this.retrievedStatus = retrievedStatus;
	}



	/**
	 * @return the statusApplicant
	 */
	public String getStatusApplicant() {
		return statusApplicant;
	}



	/**
	 * @param statusApplicant the statusApplicant to set
	 */
	public void setStatusApplicant(String statusApplicant) {
		this.statusApplicant = statusApplicant;
	}



	/**
	 * @return the statusApplicantDup
	 */
	public String getStatusApplicantDup() {
		return statusApplicantDup;
	}



	/**
	 * @param statusApplicantDup the statusApplicantDup to set
	 */
	public void setStatusApplicantDup(String statusApplicantDup) 
	{
		ApplicantAction.statusApplicantDup = statusApplicantDup;
	}
	
	
	
	
	/**
	 * @return the applicant
	 */
	public Applicant getApplicant() {
		return applicant;
	}



	/**
	 * @param applicant the applicant to set
	 */
	public void setApplicant(Applicant applicant) {
		this.applicant = applicant;
	}



	
	public Applicant getModel() {
		// TODO Auto-generated method stub
		return applicant;
	}
	/******************************************
	* Return Type     : String
	* Method Name     : viewAcceptedRedirect
	* Input Parameters:@return
	 * @throws UasException 
	*******************************************/
	public String viewAcceptedRedirect() throws UasException
	{
		System.out.println("get program id");
		AdminService adminService=new AdminService();
		setStatusApplicantDup(getStatusApplicant());
		System.out.println("in the redirect method");
		System.out.println(getStatusApplicant());
		System.out.println(getStatusApplicantDup());
		List<String> list=new ArrayList<String>();
		
			list=adminService.getScheduledProgramId();
	
		Map<String,Object> session=ActionContext.getContext().getSession();
		session.put("ScheduledprogramIDlist",list);
		return SUCCESS;
	}
	/******************************************
	* Return Type     : String
	* Method Name     : viewApplicantStatusAction
	* Input Parameters:@return
	 * @throws UasException 
	*******************************************/
	public String viewApplicantStatusAction() throws UasException
	{
		System.out.println("**********************");
		System.out.println(getStatusApplicantDup());
		System.out.println(applicant.getScheduledProgramID());
		
		MacService macService=new MacService();
		
		List<Applicant> retrievedApplications=new ArrayList<Applicant>();
		try 
		{
			retrievedApplications=macService.getApplicantions(applicant.getScheduledProgramID(),getStatusApplicantDup());
		} 
		catch (UasException e) 
		{
			throw new UasException(e.getMessage());
		}
		
		
		Map<String,Object> session=ActionContext.getContext().getSession();
		session.put("applicantsList",retrievedApplications);
		return SUCCESS;
	}
	public String submitApplicationAction() throws UasException
			{
		
		System.out.println();
		
		ApplicantService applicantService=new ApplicantService();
				
					String appID=applicantService.submitApplication(applicant);
					
					if(appID!="")
					{
						
						Map<String,Object> session=ActionContext.getContext().getSession();
						session.put("sessionApplicantID",appID);
						return SUCCESS;
					}
				return SUCCESS;
			}
	public String redirectApplicationForm() throws UasException
	{
		
		ApplicantService applicantService=new ApplicantService();
		
		List<String> scheduledApplicantList=new ArrayList<String>();
		try 
		{
			scheduledApplicantList=applicantService.getScheduledProgramIDService();
		} 
		catch (UasException e) {
			throw new UasException(e.getMessage());
		}
		if(scheduledApplicantList!=null)
		{
			Map<String,Object> session=ActionContext.getContext().getSession();
			session.put("scheduledApplicantList",scheduledApplicantList);
			return SUCCESS;
		}
		else
		{
			Map<String,Object> session=ActionContext.getContext().getSession();
			session.put("scheduledApplicantList",scheduledApplicantList);
			return SUCCESS;
		}
	}
	public String viewApplicationStatusAction() throws UasException
	{
		ApplicantService applicantService=new ApplicantService();

			setRetrievedStatus(applicantService.checkApplicationStatus(applicant.getApplicantID()));
		return SUCCESS;
	}

}
