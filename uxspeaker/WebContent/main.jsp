<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.ux.entity.UserInfo"%>
<%@page import="com.google.gson.Gson"%>
<% 
UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userinfo");
String sessionStr = "";
if(userInfo==null){
	%>
	<p>用户已超时,请重新登陆...5秒后跳转, 或者点击此处直接跳转<a href="index">跳转</a></p>
	<script>
	setTimeout(function(){
		window.location = "index";
	},5000);
	</script>
	<%
	return;
}else{
	Gson gson = new Gson();
	java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<UserInfo>() {}.getType();    
    sessionStr = gson.toJson(userInfo,type);
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
<title>UXSPEAKER</title>
<!-- CSS -->
<link type="text/css" rel="stylesheet" href="lib/transdmin/css/transdmin.css" media="screen" />
<!--[if IE 6]><link rel="stylesheet" type="text/css" media="screen" href="lib/transdmin/css/ie6.css" /><![endif]-->
<!--[if IE 7]><link rel="stylesheet" type="text/css" media="screen" href="lib/transdmin/css/ie7.css" /><![endif]-->

<!-- JavaScripts-->
<script type="text/javascript" src="lib/ui/js/jquery.min.js"></script>
<script type="text/javascript" src="lib/frame.js"></script>
</head>
<script>
window.sessionStr = '<%=sessionStr%>';
</script>
<body>
	<div id="wrapper">
    	<!-- h1 tag stays for the logo, you can use the a tag for linking the index page -->
    	<div style="height: 55px;"><a class="logo float" style="margin-top: 10px;" href="#"></a><span class="clear"></span></div>
        
        <!-- You can name the links with lowercase, they will be transformed to uppercase by CSS, we prefered to name them with uppercase to have the same effect with disabled stylesheet -->
        <div class="sideTitle">
	        <ul id="mainNav">
	        	<li><a href="#userinfo" class="active" >首页</a></li> <!-- Use the "active" class for the active menu item  -->
	        	<li><a href="#friend" >好友</a></li>
	        	<li><a href="#userinfo2" >消息</a></li>
	        	<li><a href="#userinfo3" >资料</a></li>
	        	<li class="logout"><a href="#">登出</a></li>
	        </ul>
        </div>
        <!-- // #end mainNav -->
        
        <div id="containerHolder">
			<div id="container" style="min-height: 500px;">
        		<div id="sidebar">
                	<ul class="sideNav">
                		<li><a href="#article">文章</a></li>
                    	<li><a href="#picture">照片</a></li>
                    	<li><a href="#userinfo5">日记</a></li>
                    	<li><a href="#userinfo6">音乐</a></li>
                    	<li><a href="#userinfo7">电影</a></li>
                    	<li><a href="#userinfo8">书籍</a></li>
                    	<li><a href="#userinfo9">网盘</a></li>
                    </ul>
                    <!-- // .sideNav -->
                </div>    
                <!-- // #sidebar -->
                
                <!-- h2 stays for breadcrumbs --> 
                <h2><span id="user_page">我的首页</span> &raquo; <a href="#userinfo" class="active" id="user_item">首页</a></h2>
                
                <div id="main" style="opacity:1;">
                	<div class="body"></div>
                </div>
                <!-- // #main -->
                
                <div class="clear"></div>
            </div>
            <!-- // #container -->
        </div>	
        <!-- // #containerHolder -->
        
        <p id="footer">Feel free to use and customize it. <a href="http://www.perspectived.com">Credit is appreciated.</a></p>
    </div>
    <!-- // #wrapper -->
</body>
</html>