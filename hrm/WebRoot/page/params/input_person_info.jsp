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
    
	<script type="text/javascript" src="<%=path%>/logic/input_person_info.js"></script>
  </head>
  
  <body>
    <table>
  		<tr>
  			<td valign="top"><div id="input_person_info_name"></div></td>
  			<td valign="top" rowspan="2"><div id="input_person_info_list"></div></td>
  		</tr>
  	</table>
  </body>
</html>
