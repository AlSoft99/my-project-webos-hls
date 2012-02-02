<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.frame.servlet.ServletFactory" %>
<%@ page import="com.ux.dao.ArticleDao" %>
<%@ page import="com.ux.entity.ArticleInfo" %>
<% 
ArticleDao dao = ServletFactory.newInstant().getFactory().getBean("articleDao",ArticleDao.class);
int rownumber = 5;
String currentPage = request.getParameter("current");
if(currentPage==null || "".equals(currentPage)){
	currentPage = "1";
};
List<Map<String,Object>> list = dao.queryArticleMap((Integer.parseInt(currentPage)-1)*rownumber,rownumber);
int count = (int)dao.getCount();

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
	<div class="index-body" id="index" total="<%=count%>" currentPage="<%=currentPage%>" rownumber="<%=rownumber%>">
		<div class="float index-content" id="index-content">
			<% 
			for(Map<String,Object> info : list){
				String userpicture = (String)info.get("userpicture");
				if(userpicture==null || "".equals(userpicture)){
					userpicture = "stylesheet/img/120_0_0.gif";
				}
			%>
			<!-- 文章内容开始 -->
			<div class="index-content-item">
				<div>
					<dl class="dl-user">
						<dd class="dl-user-photo"><a href="author?id=<%=info.get("userid")%>"><img src="<%=userpicture %>" /></a></dd>
						<dt><a href="article?id=<%=info.get("id")%>"><%=info.get("title") %></a></dt>
						<dd class="dl-user-tips"><a href="author?id=<%=info.get("userid")%>"><%=info.get("username")%></a>&nbsp;<span>/</span>&nbsp;<a href="type"><%=info.get("type")%></a>&nbsp;<span>/</span>&nbsp;<span><%=info.get("firstDate") %></span></dd>
						<dd class="clear"></dd>
					</dl>
				</div>
				<% 
				if(info.get("picture")!=null && !info.get("picture").equals("")){
				%>
				<div class="index-content-photo">
					<a href="article?id=<%=info.get("id")%>"><img width="720" height="255" src="<%=info.get("picture") %>" /></a>
				</div>
				<% 
				}
				%>
				<div class="index-content-text">
					<%=info.get("text") %>
				</div>
				<div class="index-toolbar">
					<ul class="float">
						<li><span class="ux-icon index-search float"></span>&nbsp;<span><%=info.get("brower") %></span></li>
						<li><span class="ux-icon index-like float"></span>&nbsp;<span><%=info.get("commentsum") %></span></li>
						<li><span class="ux-icon index-comment float"></span>&nbsp;<span><%=info.get("love") %></span></li>
					</ul>
					<a href="article?id=<%=info.get("id")%>" class="float-right">> 阅读全文</a>
					<div class="clear"></div>
				</div>
				<div class="index-foot-line"></div>
			</div>
			<!-- 文章内容结束 -->
			<%
			}
			%>
			
			
			
		</div>
		<div id="page-foot" style="margin-top: 20px;"></div>
		<div class="float-right index-tips">
			<%@ include file="modules/view/index/index-tips.jsp" %>
		</div>
		<div class="clear"></div>
	</div>
	<%@ include file="modules/view/foot.html" %>
</body>
</html>