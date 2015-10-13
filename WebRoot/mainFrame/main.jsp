<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><s:text name="个人信息管理系统"/></title>
    </head>
    <frameset cols="20%,*" framespacing="0" border="no" frameborder="no">
        <frame src="../mainFrame/left.jsp" name="left" scrolling="no">
        <frameset rows="20%,10%,*">
            <frame src="../mainFrame/top.jsp" name="top" scrolling="no">
            <frame src="../mainFrame/toop.jsp" name="toop" scrolling="no">
            <frame src="../mainFrame/about.jsp" name="main">
        </frameset>
    </frameset>

</html>

