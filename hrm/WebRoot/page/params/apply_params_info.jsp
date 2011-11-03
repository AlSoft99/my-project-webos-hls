<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'user_manager.jsp' starting page</title>
    
	<script type="text/javascript" src="<%=path%>/logic/apply_params_info.js"></script>
  </head>
  
  <body>
    
	<table>
		<tr>
			<td valign="top"><div id="apply_params_info_table"></div></td>
			<td valign="top"><div id="apply_params_info_panel"></div></td>
		</tr>
	</table>    
  </body>
</html>
