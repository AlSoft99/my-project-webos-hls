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
    
	<script type="text/javascript" src="<%=path%>/logic/stock_finance_confirm.js"></script>
  </head>
  
  <body>
    <table>
  		<tr>
  			<td valign="top"><div id="stock_finance_confirm_name"></div></td>
  			<td valign="top" rowspan="2"><div id="stock_finance_confirm_list"></div></td>
  		</tr>
  	</table>
  </body>
</html>
