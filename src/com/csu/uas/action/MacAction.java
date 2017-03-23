package com.csu.uas.action;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.csu.uas.bean.Applicant;
import com.csu.uas.exception.UasException;
import com.csu.uas.service.MacService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class MacAction extends ActionSupport
{
	
	String selectedProgramId;
	public String getSelectedProgramId() {
		return selectedProgramId;
	}
	public void setSelectedProgramId(String selectedProgramId) {
		this.selectedProgramId = selectedProgramId;
	}
	public String getSelectedStatus() {
		return selectedStatus;
	}
	public void setSelectedStatus(String selectedStatus) {
		this.selectedStatus = selectedStatus;
	}
	String selectedStatus;
	
	
	
	@SuppressWarnings( "unchecked" )
	public String execute()throws UasException
	{
		MacService macService=new MacService();
		List<Applicant> retrievedApplications=new ArrayList<Applicant>();
		try 
		{
			retrievedApplications=macService.getApplicantions(selectedProgramId,selectedStatus);
		} 
		catch (UasException e) 
		{
			throw new UasException(e.getMessage());
		}
		Map session = ActionContext.getContext().getSession();

		session.put("retrievedApplications",retrievedApplications);
		if(retrievedApplications!=null)
		{
		if(selectedStatus.equalsIgnoreCase("Applied"))
		{		
		return "AppliedListPage";
		}
		else if(selectedStatus.equalsIgnoreCase("Accepted"))
		{
		return "AcceptedListPage";
		}
		else if(selectedStatus.equalsIgnoreCase("Rejected"))
		{
		
		return "RejectedListPage";
		}
		if(selectedStatus.equalsIgnoreCase("Confirmed"))
		{
			
		return "ConfirmedListPage";
		}
		
		}
		else
		{
			System.out.println("HA HA HA ERROR");
			return "error";
		}	
		return "error";
	}
	
	String updateApplicationId;
	Date updateDate;
	
	
	public String getUpdateApplicationId() {
		return updateApplicationId;
	}
	public void setUpdateApplicationId(String updateApplicationId) {
		this.updateApplicationId = updateApplicationId;
	}
	
	String updatescheduledProgramID;
	
	public String getUpdatescheduledProgramID() {
		return updatescheduledProgramID;
	}
	public void setUpdatescheduledProgramID(String updatescheduledProgramID) {
		this.updatescheduledProgramID = updatescheduledProgramID;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	@SuppressWarnings( "unchecked")
	public String AppliedToAccept() throws UasException
	{
		
		
		if(updateDate==null)
		{
			addActionError("EnterDate");
			return INPUT;
		}
		MacService macService=new MacService();
		macService.updateStatusToAccepted("Accepted",updateDate,updateApplicationId);
		List<Applicant> retrievedApplications=new ArrayList<Applicant>();
		retrievedApplications=macService.getApplicantions(updatescheduledProgramID,"Applied");
		Map session = ActionContext.getContext().getSession();
		session.put("retrievedApplications",retrievedApplications);
		
		return "success";
	}
	
	@SuppressWarnings("unchecked")
	public String AppliedToReject() throws UasException
	{
		MacService macService=new MacService();
		macService.updateStatusToReject("Rejected",updateApplicationId);
		List<Applicant> retrievedApplications=new ArrayList<Applicant>();
		retrievedApplications=macService.getApplicantions(updatescheduledProgramID,"Applied");
		Map session = ActionContext.getContext().getSession();
		session.put("retrievedApplications",retrievedApplications);
		return "success";
	}
	
	String confirmApplicationId;
	public String getConfirmApplicationId() {
		return confirmApplicationId;
	}
	public void setConfirmApplicationId(String confirmApplicationId) {
		this.confirmApplicationId = confirmApplicationId;
	}
	
	@SuppressWarnings("unchecked")
	public String AcceptedToConform()
	{
		
		
		try 
		{
			MacService macService=new MacService();
			macService.updateToConfirm(confirmApplicationId);
			List<Applicant> retrievedApplications=new ArrayList<Applicant>();
			retrievedApplications=macService.getApplicantions(updatescheduledProgramID,"Accepted");
			System.out.println("in the method accepted to confirm");
			System.out.println(retrievedApplications.size());
		
			Map session = ActionContext.getContext().getSession();
			session.put("retrievedApplications",retrievedApplications);
			return "success";
			
			
		}
		catch (UasException e) 
		{
			addActionError(e.getMessage());
		}
		return "success";
	}
	
	private String rejectApplicationId;
	@SuppressWarnings("unchecked")
	public String AcceptedToReject()
	{
		MacService macService=new MacService();
		try 
		{
			macService.updateAcceptToReject(rejectApplicationId);
			List<Applicant> retrievedApplications=new ArrayList<Applicant>();
			retrievedApplications=macService.getApplicantions(updatescheduledProgramID,"Accepted");
			Map session = ActionContext.getContext().getSession();
			session.put("retrievedApplications",retrievedApplications);
			return "success";
			
		} 
		catch (UasException e)
		{
			addActionError(e.getMessage());
		}
		return "success";
	}
	public String getRejectApplicationId() {
		return rejectApplicationId;
	}
	public void setRejectApplicationId(String rejectApplicationId) {
		this.rejectApplicationId = rejectApplicationId;
	}
	
	
}
