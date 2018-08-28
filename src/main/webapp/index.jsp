<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basepath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<body>
<% String sessionID = session.getId();
   int port = request.getServerPort();
%>
<h1><%=sessionID%></h1>
<h2>tomcat 1</h2>
<h2>Hello World!</h2>
</body>
</html>