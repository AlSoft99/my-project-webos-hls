<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>ExtTop - Desktop Sample App</title>

    <link rel="stylesheet" type="text/css" href="<%=path%>/ext/resources/css/ext-all.css" />
    <link rel="stylesheet" type="text/css" href="<%=path%>/ext/desktop/css/desktop.css" />

   	<script type="text/javascript" src="<%=path%>/ext/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="<%=path%>/ext/ext-all.js"></script>
	<script type="text/javascript" src="<%=path%>/ext/src/locale/ext-lang-zh_CN.js"></script>

    <!-- DESKTOP -->
    <script type="text/javascript" src="<%=path%>/ext/desktop/js/StartMenu.js"></script>
    <script type="text/javascript" src="<%=path%>/ext/desktop/js/TaskBar.js"></script>
    <script type="text/javascript" src="<%=path%>/ext/desktop/js/Desktop.js"></script>

    <script type="text/javascript" src="<%=path%>/ext/desktop/js/App.js"></script>
    <script type="text/javascript" src="<%=path%>/ext/desktop/js/Module.js"></script>
    <script type="text/javascript" src="<%=path%>/logic/desktop.js"></script>
</head>
<body scroll="no">

<div id="x-desktop">
    <a href="http://extjs.com" target="_blank" style="margin:5px; float:right;"><img src="<%=path%>/ext/desktop/images/powered.gif" /></a>

    <dl id="x-shortcuts">

        <dt id="grid-win-shortcut">
            <a href="#"><img src="<%=path%>/ext/desktop/images/s.gif" />
            <div>财务管理</div></a>
        </dt>
        
        <dt id="acc-win-shortcut">
            <a href="#"><img src="<%=path%>/ext/desktop/images/s.gif" />
            <div>Accordion Window</div></a>
        </dt>

    </dl>
</div>

<div id="ux-taskbar">
	<div id="ux-taskbar-start"></div>
	<div id="ux-taskbuttons-panel"></div>
	<div class="x-clear"></div>
</div>

</body>
</html>
