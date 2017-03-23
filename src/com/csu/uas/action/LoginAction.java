/**
 * 
 */
package com.csu.uas.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.csu.uas.bean.LoginBean;
import com.csu.uas.exception.UasException;
import com.csu.uas.service.LoginService;
import com.csu.uas.service.MacService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/*****************************************************************
                Date Created :  Sep 30, 2015
        		File Name    :  LoginAction.java    
                Package Name :  com.csu.uas.action
                ProjectName  :  UniversityAdmissionSystem
                User         :  Ravi Theja V
 *******************************************************************/

@SuppressWarnings("serial")
public class LoginAction extends ActionSupport implements ModelDriven<LoginBean> 
{

	LoginBean loginBean=new LoginBean();
	/**
	 * @return the loginBean
	 */
	public LoginBean getLoginBean() {
		return loginBean;
	}
	/**
	 * @param loginBean the loginBean to set
	 */
	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public LoginBean getModel() {
		// TODO Auto-generated method stub
		return loginBean;
	}
	
	public String execute()
	{
		return SUCCESS;
	}
	
	public String validateLogin() throws UasException
	{
		LoginService loginService=new LoginService();
		
		String role=loginService.isValidLoginService(loginBean);
		Map<String,Object> session=ActionContext.getContext().getSession();
		session.put("loginMsg",loginBean.getUserName());
		if(role!=null)
		{
			if(role.equalsIgnoreCase("mac"))
			{
				MacService macService= new MacService();
				List<String> retrievedSheduledProgramIds=new ArrayList<String>();
				retrievedSheduledProgramIds=macService.populateScheduledProgramIds();
				session.put("retrievedSheduledProgramIds",retrievedSheduledProgramIds);
				session.put("role",role);
				return "mac";
			}
			else if(role.equalsIgnoreCase("admin"))
			{
				session.put("role",role);
				return "admin";   
			}
		}
		else
		{
			session.put("loginMsg","Invalid Login Credentials");
			return INPUT;
		}
		return INPUT;
	}
	public String logout()
	{
		Map<String,Object> session=ActionContext.getContext().getSession();
		session.clear();
		return SUCCESS;
	}

}
