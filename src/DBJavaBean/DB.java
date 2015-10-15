package DBJavaBean;

import JavaBean.UserNameBean;
import JavaBean.MyDayBean;
import JavaBean.MyFileBean;
import JavaBean.MyFriBean;
import JavaBean.MyMessBean;
import java.sql.*;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;
import org.apache.struts2.interceptor.ServletRequestAware;

//ʵ��ServletRequestAware ͨ��IoC��ʽֱ�ӷ���Servlet����ͨ�� request��ȡ session����
public class DB implements ServletRequestAware {
	private String driverName = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/personmessage?useUnicode=true&characterEncoding=gbk";
	private String user = "root";
	private String password = "admin";
	private Connection con = null;
	private Statement st = null;
	private ResultSet rs = null;
	private HttpServletRequest request;

	public DB() {
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
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

	// ����������ݿ��������������������
	public Statement getStatement() {
		try {
			Class.forName(getDriverName());
			con = DriverManager.getConnection(getUrl(), getUser(), getPassword());
			return con.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * ������Ϣ����ģ��
	 * 
	 */
	// ���ע�ᣬ���û���ע����Ϣ¼�뵽���ݿ���
	public String insertMess(HttpServletRequest request, String userName, String password, String name, String sex,
			String birth, String nation, String edu, String work, String phone, String place, String email) {
		try {
			String sure = null;
			rs = selectMess(request, userName);
			// �ж��Ƿ��û����Ѵ��ڣ�������ڷ���one
			if (rs.next()) {
				sure = "one";
			} else {
				String sql = "insert into user" + "(userName,password,name,sex,birth,nation,edu,work,phone,place,email)"
						+ "values(" + "'" + userName + "'" + "," + "'" + password + "'" + "," + "'" + name + "'" + ","
						+ "'" + sex + "'" + "," + "'" + birth + "'" + "," + "'" + nation + "'" + "," + "'" + edu + "'"
						+ "," + "'" + work + "'" + "," + "'" + phone + "'" + "," + "'" + place + "'" + "," + "'" + email
						+ "'" + ")";
				st = getStatement();
				int row = st.executeUpdate(sql);
				if (row == 1) {
					// ���ã�myMessage����������session�б�����û���Ϣ
					String mess = myMessage(request, userName);
					if (mess.equals("ok")) {
						sure = "ok";
					} else {
						sure = null;
					}
				} else {
					sure = null;
				}
			}
			return sure;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// ����ע��ĸ�����Ϣ
	public String updateMess(HttpServletRequest request, String userName, String name, String sex, String birth,
			String nation, String edu, String work, String phone, String place, String email) {
		try {
			String sure = null;
			String sql = "update user set name='" + name + "',sex='" + sex + "',birth='" + birth + "',nation='" + nation
					+ "',edu='" + edu + "',work='" + work + "',phone='" + phone + "',place='" + place + "',email='"
					+ email + "' where userName='" + userName + "'";
			st = getStatement();
			int row = st.executeUpdate(sql);
			if (row == 1) {
				// ���ã�myMessage����������session�б�����û���Ϣ
				String mess = myMessage(request, userName);
				if (mess.equals("ok")) {
					sure = "ok";
				} else {
					sure = null;
				}
			} else {
				sure = null;
			}
			return sure;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// ��ѯ������Ϣ�������� rs
	public ResultSet selectMess(HttpServletRequest request, String userName) {
		try {
			String sql = "select * from user where userName='" + userName + "'";
			st = getStatement();
			return st.executeQuery(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// �Ѹ�����Ϣͨ��MyMessBean�����浽session������
	public String myMessage(HttpServletRequest request, String userName) {
		try {
			ArrayList<MyMessBean> listName = null;
			HttpSession session = request.getSession();
			listName = new ArrayList<MyMessBean>();
			rs = selectMess(request, userName);
			while (rs.next()) {
				MyMessBean mess = new MyMessBean();
				mess.setName(rs.getString("name"));
				mess.setSex(rs.getString("sex"));
				mess.setBirth(rs.getString("birth"));
				mess.setNation(rs.getString("nation"));
				mess.setEdu(rs.getString("edu"));
				mess.setWork(rs.getString("work"));
				mess.setPhone(rs.getString("phone"));
				mess.setPlace(rs.getString("place"));
				mess.setEmail(rs.getString("email"));
				listName.add(mess);
				session.setAttribute("MyMess", listName);
			}
			return "ok";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * ��ϵ�˹���ģ��
	 * 
	 */
	// �����ϵ��
	public String insertFri(HttpServletRequest request, String userName, String name, String phone, String email,
			String workplace, String place, String QQ) {
		try {
			String sure = null;
			rs = selectFri(request, userName, name);
			// �ж�ͨѶ�������Ƿ��Ѵ���
			if (rs.next()) {
				sure = "one";
			} else {
				String sql = "insert into friends" + "(userName,name,phone,email,workplace,place,QQ)" + "values(" + "'"
						+ userName + "'" + "," + "'" + name + "'" + "," + "'" + phone + "'" + "," + "'" + email + "'"
						+ "," + "'" + workplace + "'" + "," + "'" + place + "'" + "," + "'" + QQ + "'" + ")";
				st = getStatement();
				int row = st.executeUpdate(sql);
				if (row == 1) {
					// ����myFridnds����������session�б����ͨѶ¼�е���Ϣ
					String fri = myFriends(request, userName);
					if (fri.equals("ok")) {
						sure = "ok";
					} else {
						sure = null;
					}
				} else {
					sure = null;
				}
			}
			return sure;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// ɾ����ϵ��
	public String deleteFri(HttpServletRequest request, String userName, String name) {
		try {
			String sure = null;
			String sql = "delete from friends where userName='" + userName + "' and name='" + name + "'";
			st = getStatement();
			int row = st.executeUpdate(sql);
			if (row == 1) {
				// ����myFridnds����������session�б����ͨѶ¼�е���Ϣ
				String fri = myFriends(request, userName);
				if (fri.equals("ok")) {
					sure = "ok";
				} else {
					sure = null;
				}
			} else {
				sure = null;
			}
			return sure;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// �޸���ϵ��
	public String updateFri(HttpServletRequest request, String userName, String friendName, String name, String phone,
			String email, String workplace, String place, String QQ) {
		try {
			String sure = null;
			// ��ɾ������ϵ�˵���Ϣ
			String del = deleteFri(request, userName, friendName);
			if (del.equals("ok")) {
				// ����¼���޸ĺ����Ϣ
				String in = insertFri(request, userName, name, phone, email, workplace, place, QQ);
				if (in.equals("ok")) {
					// ����myFridnds����������session�б����ͨѶ¼�е���Ϣ
					String fri = myFriends(request, userName);
					if (fri.equals("ok")) {
						sure = "ok";
					} else {
						sure = null;
					}
				} else {
					sure = null;
				}
			} else {
				sure = null;
			}
			return sure;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// ��ѯ��ϵ��
	public ResultSet selectFri(HttpServletRequest request, String userName, String name) {
		try {
			String sql = "select * from friends where userName='" + userName + "' and name='" + name + "'";
			st = getStatement();
			return st.executeQuery(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// ��ȡͨѶ¼��������ϵ�˵���Ϣ
	public ResultSet selectFriAll(HttpServletRequest request, String userName) {
		try {
			String sql = "select * from friends where userName='" + userName + "'";
			st = getStatement();
			return st.executeQuery(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// ��ȡͨѶ¼��������ϵ�˵���Ϣ���������Ǳ��浽session������
	public String myFriends(HttpServletRequest request, String userName) {
		try {
			ArrayList<MyFriBean> listName = null;
			HttpSession session = request.getSession();
			listName = new ArrayList<MyFriBean>();
			rs = selectFriAll(request, userName);
			if (rs.next()) {
				rs = selectFriAll(request, userName);
				while (rs.next()) {
					MyFriBean mess = new MyFriBean();
					mess.setName(rs.getString("name"));
					mess.setPhone(rs.getString("phone"));
					mess.setEmail(rs.getString("email"));
					mess.setWorkplace(rs.getString("workplace"));
					mess.setPlace(rs.getString("place"));
					mess.setQQ(rs.getString("QQ"));
					listName.add(mess);
					session.setAttribute("friends", listName);
				}
			} else {
				session.setAttribute("friends", listName);
			}
			return "ok";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * �ճ̹���ģ��
	 * 
	 */
	// ����ճ�
	public String insertDay(HttpServletRequest request, String userName, String date, String thing) {
		try {
			String sure = null;
			rs = selectDay(request, userName, date);
			// �ж��Ƿ��ճ����а���
			if (rs.next()) {
				sure = "one";
			} else {
				String sql = "insert into date" + "(userName,date,thing)" + "values(" + "'" + userName + "'" + "," + "'"
						+ date + "'" + "," + "'" + thing + "'" + ")";
				st = getStatement();
				int row = st.executeUpdate(sql);
				if (row == 1) {
					// ����myDayTime����������session�����б�����ճ���Ϣ
					String day = myDayTime(request, userName);
					if (day.equals("ok")) {
						sure = "ok";
					} else {
						sure = null;
					}
				} else {
					sure = null;
				}
			}
			return sure;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// ɾ���ճ�
	public String deleteDay(HttpServletRequest request, String userName, String date) {
		try {
			String sure = null;
			String sql = "delete from date where userName='" + userName + "' and date='" + date + "'";
			st = getStatement();
			int row = st.executeUpdate(sql);
			if (row == 1) {
				// ����myDayTime����������session�����б�����ճ���Ϣ
				String day = myDayTime(request, userName);
				if (day.equals("ok")) {
					sure = "ok";
				} else {
					sure = null;
				}
			} else {
				sure = null;
			}
			return sure;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// �޸��ճ�
	public String updateDay(HttpServletRequest request, String userName, String Day, String date, String thing) {
		try {
			String sure = null;
			// ��ɾ�����ճ�
			String del = deleteDay(request, userName, Day);
			if (del.equals("ok")) {
				// ����¼���޸ĺ����Ϣ
				String in = insertDay(request, userName, date, thing);
				if (in.equals("ok")) {
					// ����myDayTime����������session�����б�����ճ���Ϣ
					String day = myDayTime(request, userName);
					if (day.equals("ok")) {
						sure = "ok";
					} else {
						sure = null;
					}
				} else {
					sure = null;
				}
			} else {
				sure = null;
			}
			return sure;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// ��ѯ�ճ�
	public ResultSet selectDay(HttpServletRequest request, String userName, String date) {
		try {
			String sql = "select * from date where userName='" + userName + "' and date='" + date + "'";
			st = getStatement();
			return st.executeQuery(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// ��ѯ���е��ճ���Ϣ
	public ResultSet selectDayAll(HttpServletRequest request, String userName) {
		try {
			String sql = "select * from date where userName='" + userName + "'";
			st = getStatement();
			return st.executeQuery(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// ��ѯ���е��ճ���Ϣ���������Ǳ��浽session������
	public String myDayTime(HttpServletRequest request, String userName) {
		try {
			ArrayList<MyDayBean> listName = null;
			HttpSession session = request.getSession();
			listName = new ArrayList<MyDayBean>();
			rs = selectDayAll(request, userName);
			if (rs.next()) {
				rs = selectDayAll(request, userName);
				while (rs.next()) {
					MyDayBean mess = new MyDayBean();
					mess.setDay(rs.getString("date"));
					mess.setThing(rs.getString("thing"));
					listName.add(mess);
					session.setAttribute("day", listName);
				}
			} else {
				session.setAttribute("day", listName);
			}
			return "ok";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * �ļ�����ģ��
	 * 
	 */
	// �����ϴ��ļ�����Ϣ
	public String insertFile(HttpServletRequest request, String userName, String title, String name, String contentType,
			String size, String filePath) {
		try {
			String sure = null;
			// ��ѯ�ļ������Ƿ��Ѵ���
			rs = selectFile(request, userName, "title", title);
			if (rs.next()) {
				sure = "title";
			} else {
				// ��ѯ�ļ����Ƿ��Ѵ���
				rs = selectFile(request, userName, "name", name);
				if (rs.next()) {
					sure = "name";
				} else {
					String sql = "insert into file" + "(userName,title,name,contentType,size,filePath)" + "values("
							+ "'" + userName + "'" + "," + "'" + title + "'" + "," + "'" + name + "'" + "," + "'"
							+ contentType + "'" + "," + "'" + size + "'" + "," + "'" + filePath + "'" + ")";
					st = getStatement();
					int row = st.executeUpdate(sql);
					if (row == 1) {
						// ����myFile����������session�б�����ļ���Ϣ
						String file = myFile(request, userName);
						if (file.equals("ok")) {
							sure = "ok";
						} else {
							sure = null;
						}
					} else {
						sure = null;
					}
				}
			}
			return sure;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// ɾ���ļ�
	public String deleteFile(HttpServletRequest request, String userName, String title) {
		try {
			String sure = null;
			String sql = "delete from file where userName='" + userName + "' and title='" + title + "'";
			st = getStatement();
			int row = st.executeUpdate(sql);
			if (row == 1) {
				// ����myFile����������session�б�����ļ���Ϣ
				String file = myFile(request, userName);
				if (file.equals("ok")) {
					sure = "ok";
				} else {
					sure = null;
				}
			} else {
				sure = null;
			}
			return sure;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// �޸��ļ�
	public String updateFile(HttpServletRequest request, String userName, String Title, String title, String name,
			String contentType, String size, String filePath) {
		try {
			String sure = null;
			// ��ɾ�����ļ�
			String del = deleteFile(request, userName, Title);
			if (del.equals("ok")) {
				// ����¼���޸ĺ����Ϣ
				String in = insertFile(request, userName, title, name, contentType, size, filePath);
				if (in.equals("ok")) {
					// ����myFile����������session�б�����ļ���Ϣ
					String file = myFile(request, userName);
					if (file.equals("ok")) {
						sure = "ok";
					} else {
						sure = null;
					}
				} else {
					sure = null;
				}
			} else {
				sure = null;
			}
			return sure;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// ��ѯ�ļ�
	public ResultSet selectFile(HttpServletRequest request, String userName, String type, String name) {
		try {
			String sql = "select * from file where userName='" + userName + "' and " + type + "='" + name + "'";
			st = getStatement();
			return st.executeQuery(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// ��ѯ���е��ļ���Ϣ
	public ResultSet selectFileAll(HttpServletRequest request, String userName) {
		try {
			String sql = "select * from file where userName='" + userName + "'";
			st = getStatement();
			return st.executeQuery(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// ��ѯ���е��ļ���Ϣ���������Ǳ��浽session������
	public String myFile(HttpServletRequest request, String userName) {
		try {
			ArrayList<MyFileBean> listName = null;
			HttpSession session = request.getSession();
			listName = new ArrayList<MyFileBean>();
			rs = selectFileAll(request, userName);
			if (rs.next()) {
				rs = selectFileAll(request, userName);
				while (rs.next()) {
					MyFileBean mess = new MyFileBean();
					mess.setTitle(rs.getString("title"));
					mess.setName(rs.getString("name"));
					mess.setContentType(rs.getString("contentType"));
					mess.setSize(rs.getString("size"));
					listName.add(mess);
					session.setAttribute("file", listName);
				}
			} else {
				session.setAttribute("file", listName);
			}
			return "ok";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * ��½ģ��
	 * 
	 */
	// ��ѯ��¼���������Ƿ����
	public ResultSet selectLogin(HttpServletRequest request, String userName, String password) {
		try {
			String sql = "select * from user where userName='" + userName + "' and password='" + password + "'";
			st = getStatement();
			return st.executeQuery(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// �ѵ�¼�˵���Ϣ���浽session������
	public String myLogin(HttpServletRequest request, String userName) {
		try {
			ArrayList<UserNameBean> listName = null;
			HttpSession session = request.getSession();
			listName = new ArrayList<UserNameBean>();
			rs = selectMess(request, userName);
			if (rs.next()) {
				rs = selectMess(request, userName);
				while (rs.next()) {
					UserNameBean mess = new UserNameBean();
					mess.setUserName(rs.getString("userName"));
					mess.setPassword(rs.getString("password"));
					listName.add(mess);
					session.setAttribute("userName", listName);
				}
			} else {
				session.setAttribute("userName", listName);
			}
			return "ok";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// ���ص�¼�û����û���
	public String returnLogin(HttpServletRequest request) {
		String LoginName = null;
		HttpSession session = request.getSession();
		ArrayList<?> login = (ArrayList<?>) session.getAttribute("userName");
		if (login == null || login.size() == 0) {
			LoginName = null;
		} else {
			for (int i = login.size() - 1; i >= 0; i--) {
				UserNameBean nm = (UserNameBean) login.get(i);
				LoginName = nm.getUserName();
			}
		}
		return LoginName;
	}

	/*
	 * ����myLogin��myMessage��myFriends��myDayTime��myFile������
	 * �����еĺ��û��йص���Ϣȫ�����浽session������ �÷�������¼�ɹ������
	 */
	public String addList(HttpServletRequest request, String userName) {
		String sure = null;
		String login = myLogin(request, userName);
		String mess = myMessage(request, userName);
		String fri = myFriends(request, userName);
		String day = myDayTime(request, userName);
		String file = myFile(request, userName);
		if (login.equals("ok") && mess.equals("ok") && fri.equals("ok") && day.equals("ok") && file.equals("ok")) {
			sure = "ok";
		} else {
			sure = null;
		}
		return sure;
	}

	// �޸��û�����
	public String updatePass(HttpServletRequest request, String userName, String password) {
		try {
			String sure = null;
			String sql = "update user set password='" + password + "' where userName='" + userName + "'";
			st = getStatement();
			int row = st.executeUpdate(sql);
			if (row == 1) {
				String mess = myLogin(request, userName);
				if (mess.equals("ok")) {
					sure = "ok";
				} else {
					sure = null;
				}
			} else {
				sure = null;
			}
			return sure;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// ������ϵ�ˣ�����������Ϣ���浽session������
	public String findFri(HttpServletRequest request, String userName, String name) {
		try {
			ArrayList<MyFriBean> listName = null;
			HttpSession session = request.getSession();
			listName = new ArrayList<MyFriBean>();
			rs = selectFri(request, userName, name);
			if (rs.next()) {
				rs = selectFri(request, userName, name);
				while (rs.next()) {
					MyFriBean mess = new MyFriBean();
					mess.setName(rs.getString("name"));
					mess.setPhone(rs.getString("phone"));
					mess.setEmail(rs.getString("email"));
					mess.setWorkplace(rs.getString("workplace"));
					mess.setPlace(rs.getString("place"));
					mess.setQQ(rs.getString("QQ"));
					listName.add(mess);
					session.setAttribute("findfriend", listName);
				}
			} else {
				session.setAttribute("findfriend", listName);
			}

			return "ok";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// �Ӳ��ҵ�����ϵ��session�����л�ȡ��ϵ��������������
	public String returnFri(HttpServletRequest request) {
		String FriendName = null;
		HttpSession session = request.getSession();
		ArrayList<?> login = (ArrayList<?>) session.getAttribute("findfriend");
		if (login == null || login.size() == 0) {
			FriendName = null;
		} else {
			for (int i = login.size() - 1; i >= 0; i--) {
				MyFriBean nm = (MyFriBean) login.get(i);
				FriendName = nm.getName();
			}
		}
		return FriendName;
	}

	// �����ճ̣������ճ���Ϣ���浽session������
	public String findDay(HttpServletRequest request, String userName, String date) {
		try {
			ArrayList<MyDayBean> listName = null;
			HttpSession session = request.getSession();
			listName = new ArrayList<MyDayBean>();
			rs = selectDay(request, userName, date);
			if (rs.next()) {
				rs = selectDay(request, userName, date);
				while (rs.next()) {
					MyDayBean mess = new MyDayBean();
					mess.setDay(rs.getString("date"));
					mess.setThing(rs.getString("thing"));
					listName.add(mess);
					session.setAttribute("findday", listName);
				}
			} else {
				session.setAttribute("findday", listName);
			}

			return "ok";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// �Ӳ��ҵ����ճ�session�л�ȡ�ճ���Ϣ��������
	public String returnDay(HttpServletRequest request) {
		String date = null;
		HttpSession session = request.getSession();
		ArrayList<?> login = (ArrayList<?>) session.getAttribute("findday");
		if (login == null || login.size() == 0) {
			date = null;
		} else {
			for (int i = login.size() - 1; i >= 0; i--) {
				MyDayBean nm = (MyDayBean) login.get(i);
				date = nm.getDay();
			}
		}
		return date;
	}

	// �����ļ���Ϣ�������ļ�����Ϣ���浽session������
	public String findFile(HttpServletRequest request, String userName, String title) {
		try {
			ArrayList<MyFileBean> listName = null;
			HttpSession session = request.getSession();
			listName = new ArrayList<MyFileBean>();
			rs = selectFile(request, userName, "title", title);
			if (rs.next()) {
				rs = selectFile(request, userName, "title", title);
				while (rs.next()) {
					MyFileBean mess = new MyFileBean();
					mess.setTitle(rs.getString("title"));
					mess.setName(rs.getString("name"));
					mess.setContentType(rs.getString("contentType"));
					mess.setSize(rs.getString("size"));
					mess.setFilePath(rs.getString("filePath"));
					listName.add(mess);
					session.setAttribute("findfile", listName);
				}
			} else {
				session.setAttribute("findfile", listName);
			}

			return "ok";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// ���ݲ�ͬ���������Ӳ��ҵ����ļ�session�����л�ȡ�ļ���Ӧ���ļ���Ϣ
	public String returnFile(HttpServletRequest request, String face) {
		String file = null;
		HttpSession session = request.getSession();
		ArrayList<?> login = (ArrayList<?>) session.getAttribute("findfile");
		if (login == null || login.size() == 0) {
			file = null;
		} else {
			for (int i = login.size() - 1; i >= 0; i--) {
				MyFileBean nm = (MyFileBean) login.get(i);
				if (face.equals("title")) {
					file = nm.getTitle();
				} else if (face.equals("filePath")) {
					file = nm.getFilePath();
				}
				if (face.equals("fileName")) {
					file = nm.getName();
				}
			}
		}
		return file;
	}

	// һ������������Ϣ��ʾ�򣬹��Ҵ���
	public void message(String msg) {
		int type = JOptionPane.YES_NO_OPTION;
		String title = "��Ϣ��ʾ";
		JOptionPane.showMessageDialog(null, msg, title, type);
	}
}
