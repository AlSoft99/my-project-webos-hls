<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.frame.servlet.ServletFactory" %>
<%@ page import="com.ux.dao.ArticleDao" %>
<%@ page import="com.ux.entity.ArticleInfo" %>
<% 
ArticleDao dao = ServletFactory.newInstant().getFactory().getBean("articleDao",ArticleDao.class);
List<ArticleInfo> list = dao.queryArticle(0,5);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
<title>UXSPEAKER</title>
<%@ include file="modules/view/include.html" %>
<!-- CSS -->
<link type="text/css" rel="stylesheet" href="stylesheet/css/index.css" media="screen" />
<!-- JavaScripts-->
<script type="text/javascript" src="modules/index.js"></script>
</head>
<body>
	<%@ include file="modules/view/index/index-menu.jsp" %>
	<!-- div class="index-title" style="background-color: #3E5819;">
		<div style="margin: auto;width:990px;"><img src="stylesheet/img/index/title.png" style="width:990px;height:340px;"/></div>
	</div> -->
	<div class="index-body">
		<div class="float index-content">
			<% 
			for(ArticleInfo info : list){
				
			%>
			<!-- 文章内容开始 -->
			<div class="index-content-item">
				<div>
					<dl class="dl-user">
						<dd class="dl-user-photo"><a href="author"><img src="stylesheet/img/120_0_0.gif" /></a></dd>
						<dt><a href="article?id=<%=info.getId()%>"><%=info.getTitle() %></a></dt>
						<dd class="dl-user-tips"><a href="author">xiaoT</a>&nbsp;<span>/</span>&nbsp;<a href="type">视觉设计</a>&nbsp;<span>/</span>&nbsp;<span><%=info.getFirstDate() %></span></dd>
						<dd class="clear"></dd>
					</dl>
				</div>
				<div class="index-content-photo">
					<a href="article?id=<%=info.getId()%>"><img width="720" height="255" src="<%=info.getPicture() %>" /></a>
				</div>
				<div class="index-content-text">
					<%=info.getText() %>
				</div>
				<div class="index-toolbar">
					<ul class="float">
						<li><span class="ux-icon index-search float"></span>&nbsp;<span><%=info.getBrower() %></span></li>
						<li><span class="ux-icon index-like float"></span>&nbsp;<span><%=info.getLove() %></span></li>
						<li><span class="ux-icon index-comment float"></span>&nbsp;<span>400400400400</span></li>
					</ul>
					<a href="article?id=<%=info.getId()%>" class="float-right">> 阅读全文</a>
					<div class="clear"></div>
				</div>
				<div class="index-foot-line"></div>
			</div>
			<!-- 文章内容结束 -->
			<%
			}
			%>
			
			
			<div id="page-foot" style="margin-top: 20px;"></div>
		</div>
		<div class="float-right index-tips">
			<%@ include file="modules/view/index/index-tips.jsp" %>
		</div>
		<div class="clear"></div>
	</div>
	<%@ include file="modules/view/foot.html" %>
</body>
</html>