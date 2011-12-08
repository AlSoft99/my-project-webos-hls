<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>官家部落格</title>
<!-- CSS -->
<link type="text/css" rel="stylesheet" href="lib/transdmin/css/transdmin.css" media="screen" />
<!--[if IE 6]><link rel="stylesheet" type="text/css" media="screen" href="lib/transdmin/css/ie6.css" /><![endif]-->
<!--[if IE 7]><link rel="stylesheet" type="text/css" media="screen" href="lib/transdmin/css/ie7.css" /><![endif]-->

<!-- JavaScripts-->
<script type="text/javascript" src="lib/ui/js/jquery.min.js"></script>
<script type="text/javascript" src="lib/frame.js"></script>
</head>
<body>
	<div id="wrapper">
    	<!-- h1 tag stays for the logo, you can use the a tag for linking the index page -->
    	<h1><a href="#"><span>Transdmin Light</span></a></h1>
        
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
			<div id="container">
        		<div id="sidebar">
                	<ul class="sideNav">
                    	<li><a href="#userinfo4">照片</a></li>
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
                <h2><a href="#" id="user_page">我的首页</a> &raquo; <a href="#" class="active" id="user_item">首页</a></h2>
                
                <div id="main" style="opacity:0;">
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