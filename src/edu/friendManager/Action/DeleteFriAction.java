package edu.friendManager.Action;

import DBJavaBean.DB;
import com.opensymphony.xwork2.ActionSupport;
import javax.servlet.http.HttpServletRequest;
//import javax.swing.JOptionPane;
import org.apache.struts2.interceptor.ServletRequestAware;

public class DeleteFriAction extends ActionSupport implements ServletRequestAware {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String message = ERROR;
	private String userName;
	private String name;
	private HttpServletRequest request;

	public void setServletRequest(HttpServletRequest hsr) {
		request = hsr;
	}

	public String execute() throws Exception {
		DB mysql = new DB();
		userName = mysql.returnLogin(request);
		name = mysql.returnFri(request);
		String del = mysql.deleteFri(request, userName, name);
		if (del.equals("ok")) {
			message = SUCCESS;
		}
		return message;
	}
}
