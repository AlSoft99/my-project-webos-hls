<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
	<script type="text/javascript" src="<%=path%>/logic/output_order_back.js"></script>
  </head>
  
  <body>
    <table>
  		<tr>
  			<td valign="top"><div id="output_order_back_name"></div></td>
  			<td valign="top" rowspan="2"><div id="output_order_back_list"></div></td>
  			<td valign="top"><div id="output_order_back_second_list"></div></td>
  		</tr>
  	</table>
  </body>
</html>
