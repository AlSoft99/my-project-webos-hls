<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'employee_manager.jsp' starting page</title>
    
	<script type="text/javascript" src="<%=path%>/logic/ktv_material_stat.js"></script>
	
	
  </head>
  
  <body>
    <div id="ktv_material_stat_div"></div>
  </body>
</html>
