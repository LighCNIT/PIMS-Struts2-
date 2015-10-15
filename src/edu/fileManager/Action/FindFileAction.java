package edu.fileManager.Action;

import DBJavaBean.DB;
import com.opensymphony.xwork2.ActionSupport;
import java.sql.ResultSet;
import javax.servlet.http.HttpServletRequest;
import javax.swing.JOptionPane;
import org.apache.struts2.interceptor.ServletRequestAware;

public class FindFileAction extends ActionSupport implements ServletRequestAware {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title;
	private String userName;
	private ResultSet rs = null;
	private String message = ERROR;
	private HttpServletRequest request;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void message(String msg) {
		int type = JOptionPane.YES_NO_OPTION;
		String title = "��Ϣ��ʾ";
		JOptionPane.showMessageDialog(null, msg, title, type);
	}

	@Override
	public void setServletRequest(HttpServletRequest hsr) {
		request = hsr;
	}

	@Override
	public void validate() {
		if (this.getTitle() == null || this.getTitle().length() == 0) {
			message("�ļ����ⲻ����Ϊ�գ�");
			addFieldError("title", "�ļ����ⲻ����Ϊ�գ�");
		} else {
			try {
				DB mysql = new DB();
				userName = mysql.returnLogin(request);
				rs = mysql.selectFile(request, userName, "title", this.getTitle());
				if (!rs.next()) {
					message("���ļ����ⲻ���ڣ�");
					addFieldError("title", "���ļ����ⲻ���ڣ�");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public String execute() throws Exception {
		DB mysql = new DB();
		userName = mysql.returnLogin(request);
		String file = mysql.findFile(request, userName, this.getTitle());
		if (file.equals("ok")) {
			message = SUCCESS;
		}
		return message;
	}
}
