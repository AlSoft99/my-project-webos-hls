<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'employee_list.jsp' starting page</title>
    
	<script type="text/javascript" src="<%=path%>/logic/employee_list.js"></script>
  </head>
  
  <body>
    <table>
  		<tr>
  			<td valign="top"><div id="employee_list_name"></div></td>
  			<td valign="top" rowspan="2"><div id="employee_list_salary"></div></td>
  		</tr>
  	</table>
  </body>
</html>
