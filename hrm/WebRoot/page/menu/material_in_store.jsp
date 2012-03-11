<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
	<script type="text/javascript" src="<%=path%>/logic/material_in_store.js"></script>
  </head>
  
  <body>
    <table>
  		<tr>
  			<td valign="top"><div id="material_in_store_name"></div></td>
  			<td valign="top" rowspan="2"><div id="material_in_store_list"></div></td>
  		</tr>
  	</table>
  </body>
</html>
