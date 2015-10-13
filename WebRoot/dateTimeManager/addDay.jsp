<%@ page language="java" contentType="text/html" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><s:text name="个人信息管理系统->添加日程"></s:text></title>
</head>
<body bgcolor="gray">
<hr noshade="noshade"/>
	<s:div align="center">
		<s:form action="findDayAction" method="post">
			<table border="0" cellspacing="0" cellpadding="0" align="center" width="100%">
				<tr>
					<td width="30%">
						<s:text name="增加日程"></s:text>
					</td>
					<td width="30%">
						<s:a href="http://localhost:8080/PIMS-Struts2/dateTimeManager
						/lookDay.jsp">查看日程</s:a>
					</td>
				</tr>
			</table>
		</s:form>
	</s:div>
	<hr noshade/>
      <s:form action="addDayAction" method="post">
          <table border="5" cellspacing="0" cellpadding="0" bgcolor="#95BDFF" width="60%" align="center">
                <tr>
                     <td height="30" width="50%" align="right">日程时间</td>
                     <td width="50%">
                         20<input type="text" size="1" name="year"/>年
                         <input type="text" size="1" name="month"/>月
                         <input type="text" size="1" name="day"/>日
                     </td>
                </tr>
                <tr>
                     <td height="30" width="50%" align="right">日程内容</td>
                     <td width="50%"><input type="text" size="30" name="thing"/></td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <input type="submit" value="确 定" size="12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <input type="reset" value="清 除" size="12">
                    </td>
                </tr>
            </table>
        </s:form>
</body>
</html>