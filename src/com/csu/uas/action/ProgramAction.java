/**
 * 
 */
package com.csu.uas.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.csu.uas.bean.Program;
import com.csu.uas.exception.UasException;
import com.csu.uas.service.AdminService;
import com.csu.uas.service.ApplicantService;
import com.csu.uas.validations.ProgramValidation;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/*****************************************************************
                Date Created :  Aug 30, 2015
        		File Name    :  ProgramAction.java    
                Package Name :  com.csu.uas.action
                ProjectName  :  UniversityAdmissionSystem
                User         :  Ravi Theja V
 *******************************************************************/

@SuppressWarnings("serial")
public class ProgramAction extends ActionSupport implements ModelDriven<Program> 
{

	private String pgID;
	static private String pgIDdup;
	
	private String pgSchduleID;
	static private String pgSchduleIDdup;
	
	/**
	 * @return the pgSchduleID
	 */
	public String getPgSchduleID() {
		return pgSchduleID;
	}



	/**
	 * @param pgSchduleID the pgSchduleID to set
	 */
	public void setPgSchduleID(String pgSchduleID) {
		this.pgSchduleID = pgSchduleID;
	}



	/**
	 * @return the pgSchduleIDdup
	 */
	public static String getPgSchduleIDdup() {
		return pgSchduleIDdup;
	}



	/**
	 * @param pgSchduleIDdup the pgSchduleIDdup to set
	 */
	public static void setPgSchduleIDdup(String pgSchduleIDdup) {
		ProgramAction.pgSchduleIDdup = pgSchduleIDdup;
	}



	/**
	 * @return the pgIDdup
	 */
	public String getPgIDdup() {
		return pgIDdup;
	}



	/**
	 * @param pgIDdup the pgIDdup to set
	 */
	public void setPgIDdup(String pgIDdup) 
	{
		ProgramAction.pgIDdup = pgIDdup;
	}



	/**
	 * @return the pgID
	 */
	public String getPgID() {
		return pgID;
	}



	/**
	 * @param pgID the pgID to set
	 */
	public void setPgID(String pgID) {
		this.pgID = pgID;
	}
	Program program=new Program();
	/**
	 * @return the program
	 */
	public Program getProgram() {
		return program;
	}



	/**
	 * @param program the program to set
	 */
	public void setProgram(Program program) {
		this.program = program;
	}



	public Program getModel() {
		
		return program;
	}
	@Override
	public String execute()
	{
		return SUCCESS;
	}
	public String viewProgramsOffered()
	{
		AdminService adminservice=new AdminService();
		List<Program> list =new ArrayList<Program>();
		System.out.println("view program offered");
			try 
			{
				
				list=adminservice.viewProgramsOfferedService();
				Map<String,Object> session=ActionContext.getContext().getSession();
				if(list!=null)
				{
					session.put("listObj",list);
					return SUCCESS;
				}
				else
				{
					return ERROR;
				}
			} 
			catch (UasException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return SUCCESS;
	}
	
	/******************************************
	* Return Type     : String
	* Method Name     : addProgramOffered
	* Input Parameters:@return
	*******************************************/
	public String addProgramOffered()
	{
		System.out.println("add program offered");
		AdminService adminservice=new AdminService();
		System.out.println("************"+program.getProgramName());
		System.out.println(program.getProgramDescrption());
		System.out.println(program.getProgramDuration());
		System.out.println(program.getApplicantEligibily());
		System.out.println(program.getDegreeCertificateOffered());
		try 
		{
			if(adminservice.addProgramService(this.program))
			{
				
				
				Map<String,Object> session=ActionContext.getContext().getSession();
				session.put("prgMsg","PROGRAM ADDED");
				return SUCCESS;
			}
			else
			{
				Map<String,Object> session=ActionContext.getContext().getSession();
				session.put("prgMsg","UN SUCCESS");
				return INPUT;
			}
		} 
		catch (UasException e) 
		{
			System.out.println(e.getMessage());
		}
		return SUCCESS;
	}
	/******************************************
	* Return Type     : String
	* Method Name     : deleteProgramOffered
	* Input Parameters:@return
	*******************************************/
	public String deleteProgramOffered()
	{
		System.out.println("delete program offered");
		System.out.println("IN DELTE PROGRAM OFFERED");
		System.out.println(getPgID());
		AdminService adminservice=new AdminService();
		ProgramValidation programValidation=new ProgramValidation();
		try 
		{
			if(!programValidation.isScheduledProgram(getPgID()))
			{
				if(adminservice.deleteProgramOfferedService(getPgID()))
				{
					Map<String,Object> session=ActionContext.getContext().getSession();
					session.put("prgDeleteMsg","PROGRAM DELETED SUCCESSFULLY");
					return SUCCESS;
				}
				else
				{
					Map<String,Object> session=ActionContext.getContext().getSession();
					session.put("prgDeleteMsg","PROGRAM NOT DELETED");
					return INPUT;
				}
			}
			else
			{
				Map<String,Object> session=ActionContext.getContext().getSession();
				session.put("prgDeleteMsg","CANNOT DELETE A SCHEDULED PROGRAM");
			}
		} 
		catch (UasException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;	
	}
	
	/******************************************
	* Return Type     : String
	* Method Name     : updateProgramOfferedView
	* Input Parameters:@return
	*******************************************/
	@SuppressWarnings("unchecked")
	public String updateProgramOfferedView()
	{
		System.out.println("update program offered");
		List<Program> list =new ArrayList<Program>();
				Map<String,Object> session=ActionContext.getContext().getSession();
				list=(List<Program>) session.get("listObj");
				System.out.println(list.size());
				Iterator<Program> iter=list.iterator();
				setPgIDdup(getPgID());
				System.out.println("IN METHOD");
				System.out.println(getPgIDdup());
				while(iter.hasNext())
				{
					Program dummy=iter.next();
					if(dummy.getProgramID().equals(getPgID()))
					{
						program.setProgramName(dummy.getProgramName());
						program.setProgramDescrption(dummy.getProgramDescrption());
						program.setApplicantEligibily(dummy.getApplicantEligibily());
						program.setProgramDuration(dummy.getProgramDuration());
						program.setDegreeCertificateOffered(dummy.getDegreeCertificateOffered());
						
					}
				}
				
		return SUCCESS;
	}
	
	/******************************************
	* Return Type     : String
	* Method Name     : updateProgramOfferedUpdate
	* Input Parameters:@return
	*******************************************/
	public String updateProgramOfferedUpdate()
	{
		System.out.println("update program  offered");
		AdminService adminservice=new AdminService();
		try {
			System.out.println("^^^^");
			System.out.println(getPgIDdup());	
			program.setProgramID(getPgIDdup());
			if(adminservice.updateProgramOffered(program))
				{
					Map<String,Object> session=ActionContext.getContext().getSession();
					session.put("prgUpdateMsg","PROGRAM UPDATED SUCCESSFULLY");
					return SUCCESS;
				}
				else 
				{
					Map<String,Object> session=ActionContext.getContext().getSession();
					session.put("prgUpdateMsg","PROGRAM UPDATION UNSUCCESSFUL");
					return INPUT;
				}
			} 
			catch (UasException e) 
			{
				e.printStackTrace();
			}
		
		return SUCCESS;
	}
	/******************************************
	* Return Type     : String
	* Method Name     : getProgramID
	* Input Parameters:@return
	 * @throws UasException 
	*******************************************/
	public String getProgramID() throws UasException
	{
		System.out.println("get program id");
		AdminService adminService=new AdminService();
		
		List<String> list=new ArrayList<String>();
		list=adminService.getProgramIDService();
		Map<String,Object> session=ActionContext.getContext().getSession();
		session.put("programIDlist",list);
		return SUCCESS;
	}
	/******************************************
	* Return Type     : String
	* Method Name     : scheduleProgram
	* Input Parameters:@return
	*******************************************/
	public String scheduleProgram()
	{
		System.out.println("schedule program offered");
		System.out.println("**********************");
		System.out.println(program.getProgramID());
		System.out.println(program.getProgramLocaiton());
		System.out.println(program.getProgramStartDate());
		System.out.println(program.getProgramEndDate());
		System.out.println(program.getSessionsPerWeek());
		
		ProgramValidation programValidation=new ProgramValidation();
		if(programValidation.isValidEndDate(program))
		{
			String startDateFirst=program.getProgramStartDate();
			String endDateFirst=program.getProgramEndDate();
			
			SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");
			
			Date startDate=new Date();
			Date endDate=new Date();
			
		
			try 
			{
				startDate=sdf.parse(startDateFirst);
				endDate=sdf.parse(endDateFirst);
			}
			catch (ParseException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			SimpleDateFormat sdf1=new SimpleDateFormat("dd/MMM/yyyy");
			
			program.setProgramStartDate(sdf1.format(startDate));
			program.setProgramEndDate(sdf1.format(endDate));
			
			
			AdminService adminService=new AdminService();
			try 
			{
				if(adminService.scheduleProgramService(program))
				{
					Map<String,Object> session=ActionContext.getContext().getSession();
					session.put("programScheduleMsg", "PROGRAM SCHEDULED");
					return SUCCESS;
				}
				else
				{
					Map<String,Object> session=ActionContext.getContext().getSession();
					session.put("programScheduleMsg", "PROGRAM NOTSCHEDULED");
					return SUCCESS;
				}
			} 
			catch (UasException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		else
		{
			Map<String,Object> session=ActionContext.getContext().getSession();
			session.put("programScheduleMsg", "PROGRAM SCHEDULED-INVALID DATA");
		}
		return SUCCESS;
	}
	
	/******************************************
	* Return Type     : String
	* Method Name     : viewScheduledProgramAction
	* Input Parameters:@return
	*******************************************/
	public String viewScheduledProgramAction()
	{
		System.out.println("view program scheduled");
		
		AdminService adminservice=new AdminService();
		List<Program> list =new ArrayList<Program>();
		
			try 
			{
				
				list=adminservice.viewScheduledProgramService();
				Map<String,Object> session=ActionContext.getContext().getSession();
				if(list!=null)
				{
					session.put("programScheduleList",list);
					return SUCCESS;
				}
				else
				{
					return ERROR;
				}
			} 
			catch (UasException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return SUCCESS;
	}
	
	/******************************************
	* Return Type     : String
	* Method Name     : updateProgramScheduledView
	* Input Parameters:@return
	*******************************************/
	@SuppressWarnings("unchecked")
	public String updateProgramScheduledView()
	{
		System.out.println("update program offered view");
		List<Program> list =new ArrayList<Program>();
		Map<String,Object> session=ActionContext.getContext().getSession();
		list=(List<Program>) session.get("programScheduleList");
		System.out.println(list.size());
		Iterator<Program> iter=list.iterator();
		setPgSchduleIDdup(getPgSchduleID());
		while(iter.hasNext())
		{
			Program dummy=iter.next();
			if(dummy.getScheduledProgramID().equals(getPgSchduleID()))
			{
				program.setProgramLocaiton(dummy.getProgramLocaiton());
				program.setSessionsPerWeek(dummy.getSessionsPerWeek());
			}
		}
		return SUCCESS;
	}
	/******************************************
	* Return Type     : String
	* Method Name     : updateProgramSchduledUpdate
	* Input Parameters:@return
	*******************************************/
	public String updateProgramSchduledUpdate()
	{
		System.out.println("view program scheduled update");
		
		ProgramValidation programValidation=new ProgramValidation();
		System.out.println("before the validation");
		if(programValidation.isValidEndDate(program))
		{
			System.out.println("after the validation");
			String startDateFirst=program.getProgramStartDate();
			String endDateFirst=program.getProgramEndDate();
			
			SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");
			
			Date startDate=new Date();
			Date endDate=new Date();
			
		
			try 
			{
				startDate=sdf.parse(startDateFirst);
				endDate=sdf.parse(endDateFirst);
			}
			catch (ParseException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			SimpleDateFormat sdf1=new SimpleDateFormat("dd/MMM/yyyy");
			
			program.setProgramStartDate(sdf1.format(startDate));
			program.setProgramEndDate(sdf1.format(endDate));
			
			System.out.println("start date"+program.getProgramStartDate());
			System.out.println("end date"+program.getProgramEndDate());
			AdminService adminService=new AdminService();
			program.setScheduledProgramID(getPgSchduleIDdup());
			try 
			{
				if(!programValidation.isParticipantsExistValidation(program.getScheduledProgramID()))
				{	
					if(adminService.updateScheduledProgramService(program))
					{
						System.out.println("in action true");
						Map<String,Object> session=ActionContext.getContext().getSession();
						session.put("programScheduleUpdateMsg", "SCHEDULED PROGRAM UPDATED");
						return SUCCESS;
					}
					else
					{
						System.out.println("in action false");
						Map<String,Object> session=ActionContext.getContext().getSession();
						session.put("programScheduleUpdateMsg", "SCHEDULED PROGRAM NOT SCHEDULED");
						return INPUT;
					}
				}
				else
				{
					Map<String,Object> session=ActionContext.getContext().getSession();
					session.put("programScheduleUpdateMsg", "CANNOT UPDATE A PROGRAM, WHICH HAS APPLICANTS");
					return INPUT;
				}
			} 
			catch (UasException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		else
		{
			System.out.println("in action nothing");
			Map<String,Object> session=ActionContext.getContext().getSession();
			session.put("programScheduleUpdateMsg", "PROGRAM SCHEDULED-INVALID DATA");
			return INPUT;
		}

		return INPUT;
	}
	/******************************************
	* Return Type     : String
	* Method Name     : deleteProgramScheduled
	* Input Parameters:@return
	*******************************************/
	public String deleteProgramScheduled()
	{
		System.out.println("delete program offered");
		System.out.println("IN DELTE PROGRAM OFFERED");
		System.out.println(getPgID());
		AdminService adminservice=new AdminService();	
		try 
		{
			if(adminservice.deleteProgramScheduledService(getPgSchduleID()))
			{
				Map<String,Object> session=ActionContext.getContext().getSession();
				session.put("prgDeleteMsg","PROGRAM DELETED SUCCESSFULLY");
				return SUCCESS;
			}
			else
			{
				Map<String,Object> session=ActionContext.getContext().getSession();
				session.put("prgDeleteMsg","PROGRAM NOT DELETED");
				return SUCCESS;
			}
		} 
		catch (UasException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String viewProgramInfoAction()
	{
		ApplicantService applicantService=new ApplicantService();
		try 
		{
			List<Program> retrievedScheduledProgramsList=new ArrayList<Program>();
			
			retrievedScheduledProgramsList=applicantService.viewProgramInfoService();
			
			if(retrievedScheduledProgramsList!=null)
			{
			
				System.out.println("ikkada");
				System.out.println(retrievedScheduledProgramsList.size());
				
				Map<String,Object> session=ActionContext.getContext().getSession();
			
				session.put("retrievedScheduledProgramsList", retrievedScheduledProgramsList);
				return SUCCESS;
			}
			else
			{
				Map<String,Object> session=ActionContext.getContext().getSession();

				session.put("noProgramMsg", "NO DATA AVAILABLE");
				return SUCCESS;

			}
		} 
		catch (UasException e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}
}
