<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <link rel="stylesheet" type="text/css" href="ext/resources/css/ext-all.css" />
	<link rel="stylesheet" type="text/css" href="css/file-upload.css" />
    <title>系统登录</title>
    <script type="text/javascript" src="<%=path%>/ext/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="<%=path%>/ext/ext-all.js"></script>
	<script type="text/javascript" src="<%=path%>/ext/build/locale/ext-lang-zh_CN.js"></script>
	<script type="text/javascript" src="<%=path%>/logic/index.js"></script>
	<STYLE   type=text/css>  
	  body   {	
	  			background-image:   url(<%=path%>/ext/desktop/wallpapers/desktop2.jpg);  
	  			background-position:   center;  
	  			background-repeat:   no-repeat;  
	  			background-attachment:   fixed;
	  	}  
	  </STYLE>
  </head>
  
  <body >
    <div id="login"></div>
  </body>
</html>
