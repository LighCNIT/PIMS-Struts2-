package edu.friendManager.Action;

import DBJavaBean.DB;
import com.opensymphony.xwork2.ActionSupport;
import java.sql.ResultSet;
import javax.servlet.http.HttpServletRequest;
import javax.swing.JOptionPane;
import org.apache.struts2.interceptor.ServletRequestAware;

public class FindFriAction extends ActionSupport implements ServletRequestAware {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String friendname;
	private String userName;
	private ResultSet rs = null;
	private String message = ERROR;
	private HttpServletRequest request;

	public String getFriendname() {
		return friendname;
	}

	public void setFriendname(String friendname) {
		this.friendname = friendname;
	}

	public void setServletRequest(HttpServletRequest hsr) {
		request = hsr;
	}

	public void message(String msg) {
		int type = JOptionPane.YES_NO_OPTION;
		String title = "��Ϣ��ʾ";
		JOptionPane.showMessageDialog(null, msg, title, type);
	}

	public void validate() {
		if (this.getFriendname().equals("") || this.getFriendname().length() == 0) {
			message("��ϵ������������Ϊ�գ�");
			addFieldError("friendname", "��ϵ������������Ϊ�գ�");
		} else {
			try {
				DB mysql = new DB();
				userName = mysql.returnLogin(request);
				rs = mysql.selectFri(request, userName, this.getFriendname());
				if (!rs.next()) {
					message("��ϵ�����������ڣ�");
					addFieldError("friendname", "��ϵ�����������ڣ�");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public String execute() throws Exception {
		DB mysql = new DB();
		userName = mysql.returnLogin(request);
		String fri = mysql.findFri(request, userName, this.getFriendname());
		if (fri.equals("ok")) {
			message = SUCCESS;
		}
		return message;
	}
}