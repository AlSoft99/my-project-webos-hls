<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.frame.servlet.ServletFactory" %>
<%@ page import="com.ux.dao.ArticleDao" %>
<%@ page import="com.ux.dao.UserInfoDao" %>
<%@ page import="com.ux.entity.ArticleInfo" %>
<%@ page import="com.ux.entity.UserInfo" %>
<% 
String id = request.getParameter("id");
String picture = "";
ArticleDao dao = ServletFactory.newInstant().getFactory().getBean("articleDao",ArticleDao.class);
UserInfoDao userdao = ServletFactory.newInstant().getFactory().getBean("userInfoDao",UserInfoDao.class);
int rownumber = com.ux.util.Constant.ROW_NUMBER;
String currentPage = request.getParameter("current");
if(currentPage==null || "".equals(currentPage)){
	currentPage = "1";
};
List<Map<String,Object>> list = dao.queryArticleMap((Integer.parseInt(currentPage)-1)*rownumber,rownumber," and a.userid='"+id+"' ");
int count = (int)dao.getCount();
UserInfo userinfo = userdao.getUserInfo(Integer.valueOf(id));
String jobname = userdao.getJobname(userinfo.getJob());
if("".equals(userinfo.getPicture()) || userinfo.getPicture()==null){
	picture = "stylesheet/img/120_0_0.gif";
}else{
	picture = userinfo.getPicture();
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" />
<title>UXSPEAKER</title>
<!-- CSS ../../../-->
<%@ include file="../include.html" %>
<link type="text/css" rel="stylesheet" href="stylesheet/css/index.css" media="screen" />
<link type="text/css" rel="stylesheet" href="modules/css/index/index-type.css" media="screen" />
<!-- JavaScripts-->
<script type="text/javascript" src="modules/script/index/index-author.js"></script>
</head>
<body>
	<%@ include file="index-menu.jsp" %>
	<div class="index-body" id="index-author" userid="<%=userinfo.getId() %>" total="<%=count%>" currentPage="<%=currentPage%>" rownumber="<%=rownumber%>">
		<div class="index-navigation" style="padding-top:25px;">
			<a href="index">首页</a> > <span><%=userinfo.getUsername() %></span>
		</div>
		<div class="float index-content">
			<!-- 文章内容开始 -->
			<div class="index-content-item">
				<div>
					<dl class="dl-user">
						<dd class="dl-user-photo"><a href="author?id=<%=userinfo.getId()%>"><img src="<%=picture %>" /></a></dd>
						<dt><a href="author?id=<%=userinfo.getId()%>"><%=userinfo.getUsername() %></a></dt>
						<dd class="dl-user-tips"><span><%=jobname %></span>&nbsp;</dd>
						<dd class="clear"></dd>
					</dl>
				</div>
				
				<div class="index-foot-line"></div>
			</div>
			<!-- 文章内容结束 -->
			<div id="index-content">
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
						<dl class="dl-user dl-author" style="">
							<dd class="float-right dl-user-comment"><%=info.get("commentsum") %></dd>
							<dt><a href="article?id=<%=info.get("id")%>"><%=info.get("title") %></a></dt>
							<dd class="dl-user-tips"><span><%=info.get("firstDate") %></span></dd>
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
							<li><span class="ux-icon index-like float"></span>&nbsp;<span><%=info.get("love") %></span></li>
							<li><span class="ux-icon index-comment float"></span>&nbsp;<span><%=info.get("commentsum") %></span></li>
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
			<!-- 文章内容开始 -->
			<!-- <div class="index-content-item">
				<div>
					<dl class="dl-user dl-author" style="">
						<dd class="float-right dl-user-comment">5</dd>
						<dt><a href="article">情感化设计中的手绘应用表现</a></dt>
						<dd class="dl-user-tips"><span>2011.12.28</span></dd>
						<dd class="clear"></dd>
					</dl>
				</div>
				<div class="index-content-photo">
					<a href="#"><img src="stylesheet/img/index/banner4.jpg" /></a>
				</div>
				<div class="index-content-text">
					<p>    这篇分享是我最近读《情感化设计》的一点小感悟及关注的那些事儿。在这里与大家分享下。欢迎大家多多指教和交流。...</p>
				</div>
				<div class="index-toolbar">
					<ul class="float">
						<li><span class="icon index-search float"></span>浏览:<span>400400400400</span></li>
						<li><span class="icon index-like float"></span>喜爱:<span>3</span></li>
					</ul>
					<a href="#" class="float-right">阅读全文</a>
					<div class="clear"></div>
				</div>
				<div class="index-foot-line"></div>
			</div> -->
			<!-- 文章内容结束 -->
			</div>
		</div>
		<div class="float-right index-tips">
			<%@ include file="index-tips.jsp" %>
		</div>
		<div class="clear"></div>
	</div>
	<%@ include file="../foot.html" %>
</body>
</html>