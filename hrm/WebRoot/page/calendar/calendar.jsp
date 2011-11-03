<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN""http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<html> 
<head> 

<script type='text/javascript' src='<%=path%>/logic/calendar.js'></script> 

</head> 
<body> 
<div id='calendar_div'></div> 
<div id="calendar_div_btn"></div>
</body> 
</html>