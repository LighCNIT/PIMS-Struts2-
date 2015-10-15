package edu.login.Action;

import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import DBJavaBean.DB;

import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport implements ServletRequestAware {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String userName;
	private String password;
	private ResultSet rs = null;
	private String message = ERROR;
	private HttpServletRequest request;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setServletRequest(HttpServletRequest hsr) {
		request = hsr;
	}

	public void validate() {
		if (this.getUserName() == null || this.getUserName().length() == 0) {
			addFieldError("username", "�������¼���֣�");
		} else {
			try {
				DB mysql = new DB();
				rs = mysql.selectMess(request, this.getUserName());
				if (!rs.next()) {
					addFieldError("username", "���û���δע�ᣡ");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (this.getPassword() == null || this.getPassword().length() == 0) {
			addFieldError("password", "�������¼���룡");
		} else {
			try {
				DB mysql = new DB();
				rs = mysql.selectMess(request, this.getUserName());
				if (rs.next()) {
					rs = mysql.selectLogin(request, this.getUserName(),
							this.getPassword());
					if (!rs.next()) {
						addFieldError("password", "��¼�������");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public String execute() throws Exception {
		DB mysql = new DB();
		String add = mysql.addList(request, this.getUserName());
		if (add.equals("ok")) {
			message = SUCCESS;
		}
		return message;
	}

}
