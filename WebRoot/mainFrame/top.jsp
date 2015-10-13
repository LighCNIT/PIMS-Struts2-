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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:text name="个人管理系统" />
</title>
</head>
<body bgcolor="#CCCCFF">
	<table width="100%" align="center">
		<tr align="center">
			<td align="right"><img src="../images/cc.gif" alt="为之则易，不为则难！"
				height="80"></td>
			<td align="left">
				<h1>
					<font color="blue">欢迎使用个人信息管理平台</font>
				</h1></td>
		</tr>
	</table>
</body>
</html>
