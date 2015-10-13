<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title><s:text name="个人信息管理系统"></s:text>
</title>
</head>
<body bgcolor="CCCFFF">
	<s:form action="loginAction" method="post">
		<table align="center" width="100%">
			<tr>
				<td align="right" width="50%"><img alt="为之则易,不为则难"
					src="/images/cc.gif" height="80"></td>
				<td align="left" width="50%">
					<h1>个人信息管理系统</h1>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<hr align="center" width="100%" size="20" color="green" /></td>
				<td width=70%>
					<table align="center" border="5" bgcolor="#99aadd">
						<tr>
							<td><s:textfield name="userName" label="登录名" size="16"></s:textfield>
							</td>
						</tr>
						<tr>
							<td><s:textfield name="password" label="登陆密码" size="18"></s:textfield>
							</td>
						</tr>
						<tr>
							<td colspan="2" align="center"><input type="submit"
								value="确定" /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input
								type="reset" value="清空" /></td>
						</tr>
						<tr>
							<td colspan="2" align="center"><s:a
									href="http://locahost:8080/PIMS(Struts2)/login/register.jsp">
								</s:a></td>
						</tr>
					</table>
			</tr>
		</table>
	</s:form>
</body>
</html>
