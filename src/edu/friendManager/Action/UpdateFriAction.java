package edu.friendManager.Action;

import DBJavaBean.DB;
import com.opensymphony.xwork2.ActionSupport;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;

public class UpdateFriAction extends ActionSupport implements ServletRequestAware {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String phone;
	private String email;
	private String workplace;
	private String place;
	private String QQ;
	private String message = ERROR;
	private HttpServletRequest request;
	private String userName;
	private String friendname;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWorkplace() {
		return workplace;
	}

	public void setWorkplace(String workplace) {
		this.workplace = workplace;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getQQ() {
		return QQ;
	}

	public void setQQ(String QQ) {
		this.QQ = QQ;
	}

	@Override
	public void setServletRequest(HttpServletRequest hsr) {
		request = hsr;
	}

	@Override
	public void validate() {
		if (getName() == null || getName().length() == 0) {
			addFieldError("name", "�û�����������Ϊ��");
		}
		if (getPhone() == null || getPhone().length() == 0) {
			addFieldError("phone", "�û��绰������Ϊ��");
		}
		if (getEmail() == null || getEmail().length() == 0) {
			addFieldError("email", "�����ַ������Ϊ��");
		}
		if (getWorkplace() == null || getWorkplace().length() == 0) {
			addFieldError("workplace", "������λ������Ϊ��");
		}
		if (getPlace() == null || getPlace().length() == 0) {
			addFieldError("place", "��ͥסַ������Ϊ��");
		}
		if (getQQ() == null || getQQ().length() == 0) {
			addFieldError("QQ", "�û�QQ������Ϊ��");
		}
	}

	public String execute() throws Exception {
		DB mysql = new DB();
		userName = mysql.returnLogin(request);
		friendname = mysql.returnFri(request);
		String fri = mysql.updateFri(request, userName, friendname, this.getName(), this.getPhone(), this.getEmail(),
				this.getWorkplace(), this.getPlace(), this.getQQ());
		if (fri.equals("ok")) {
			message = SUCCESS;
		}
		return message;
	}
}
