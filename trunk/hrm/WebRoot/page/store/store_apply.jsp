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
    
	<script type="text/javascript" src="<%=path%>/logic/store_apply.js"></script>
  </head>
  
  <body>
    <table>
  		<tr>
  			<td valign="top"><div id="store_apply_name"></div></td>
  			<td valign="top" rowspan="2"><div id="store_apply_list"></div></td>
  		</tr>
  	</table>
  </body>
</html>
