<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.frame.servlet.ServletFactory" %>
<%@ page import="com.ux.dao.ArticleDao" %>
<%@ page import="com.ux.entity.ArticleInfo" %>
<% 
String id = request.getParameter("id");
ArticleDao dao = ServletFactory.newInstant().getFactory().getBean("articleDao",ArticleDao.class); 
Map<String,Object> info = dao.queryMapById(id);
String[] tagname = info.get("tagname").toString().split(",");
dao.updateBrower(id);
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
<link type="text/css" rel="stylesheet" href="modules/css/index/index-article.css" media="screen" />
<link type="text/css" rel="stylesheet" href="modules/css/index/index-article-wordpress.css" media="screen" />
<!-- JavaScripts-->
<script type="text/javascript" src="modules/script/index/index-article.js"></script>
</head>
<body>
	<%@ include file="index-menu.jsp" %>
	<div id="dialog-confirm" title="删除?">
		<p>是否确定删除该评论?</p>
	</div>
	<div class="index-body" id="article-index" articleid="<%=id %>">
		<div class="index-navigation" style="padding-top:25px;">
			<a href="index">首页</a> > <a href="type?typeid=<%=info.get("type")%>"><%=info.get("typename")%></a> > <span><%=info.get("title")%></span>
		</div>
		<div class="float index-content">
			<!-- 文章内容开始 -->
			<div class="index-content-item">
				<div>
					<dl class="dl-user">
						<% 
						if(info.get("userpicture")==null || "".equals(info.get("userpicture"))){
						%>
						<dd class="dl-user-photo"><a href="author?id=<%=info.get("userid")%>"><img src="stylesheet/img/120_0_0.gif" /></a></dd>
						<% 
						}else{
						%>
						<dd class="dl-user-photo"><a href="author?id=<%=info.get("userid")%>"><img src="<%=info.get("userpicture")%>" /></a></dd>
						<%
						}
						%>
						<dt><span class="dl-user-title"><%=info.get("title")%></span></dt>
						<dd class="dl-user-tips">
							<a href="author?id=<%=info.get("userid")%>"><%=info.get("username")%></a>&nbsp;<span>/</span>&nbsp;<a href="type?typeid=<%=info.get("type")%>"><%=info.get("typename")%></a>&nbsp;<span>/</span>&nbsp;<span><%=info.get("firstDate")%></span>&nbsp;<span>/</span>
							&nbsp;<span>相关标签</span>
							<% 
							for(int i=0;i<tagname.length;i++){
							%>
							&nbsp;<a href="#"><%=tagname[i]%></a>
							<%
							}
							%>
						</dd>
						<dd class="clear"></dd>
					</dl>
				</div>
				<% 
				if(info.get("picture")!=null && !"".equals(info.get("picture"))){
				%>
					<div class="index-content-photo">
						<img width="720" height="255" src="<%=info.get("picture")%>" />
					</div>
				<%
				}
				%>
				
				<div class="index-content-text">
					<%=info.get("content")%>
				</div>
				<div class="index-toolbar">
					<ul class="float">
						<li><span style="color:red;">本文出自HP UX Blog，转载时请注明出处</span></li>
					</ul>
					<a href="#" id="add-love" class="float-right" style="font-size: 12px;line-height: 20px;padding:0 5px;">给好文加心!</a><div id="have-love" class="float-right" style="display:none;font-size: 12px;line-height: 20px;padding:0 5px;color:#777;">该文章已加心!</div>
					<span id="add-love-number" class="float-right"><%=info.get("love") %></span>
					<span class="icon index-love float-right"></span>
					<div class="clear"></div>
				</div>
				<div class="bl-index-content-discuss">
					<div class="discuss-frame">
						<h5 class="discuss-sum">评论 (<span id="comment-count">1</span> 个评论)</h5>
						<div class="discuss-list">
							<div class="discuss-item" id="discuss-item">
								<!-- <dl>
									<dd class="discuss-photo float"><a href="#"><img src="stylesheet/img/120_0_0.gif" /></a></dd>
									<dt><a href="#" style="font-weight: bold;">rayln</a>&nbsp;<span>2011-12-28 09:22</span><a class="float-right index-article-edit" href="#">删除</a><a class="float-right index-article-edit" href="#">编辑</a></dt>
									<dd class="discuss-content index-article-content" style="display:none;">离线编辑器开放接口所使用的。其中RSD是一个广义的接口，wlwmanifest是针对微软Live Writer编辑器的。有了这两个接口，在使用离线编辑器撰写博客的时候，就可以直接在软件中选择分类，标签等等内容了</dd>
									<dd class="discuss-content index-article-content-edit"><textarea style="width:100%;height:60px;">离线编辑器开放接口所使用的。其中RSD是一个广义的接口，wlwmanifest是针对微软Live Writer编辑器的。有了这两个接口，在使用离线编辑器撰写博客的时候，就可以直接在软件中选择分类，标签等等内容了</textarea></dd>
									<dd class="clear"></dd>
								</dl>
								<dl>
									<dd class="discuss-photo float"><a href="#"><img src="stylesheet/img/120_0_0.gif" /></a></dd>
									<dt><a href="#" style="font-weight: bold;">rayln</a>&nbsp;<span>2011-12-28 09:22</span><a class="float-right" href="#">删除</a><a class="float-right" href="#">编辑</a></dt>
									<dd class="discuss-content">离线编辑器开放接口所使用的。其中RSD是一个广义的接口，wlwmanifest是针对微软Live Writer编辑器的。有了这两个接口，在使用离线编辑器撰写博客的时候，就可以直接在软件中选择分类，标签等等内容了</dd>
									<dd class="clear"></dd>
								</dl> -->
							</div>
						</div>
						<form id="comment-form">
							<input type="hidden" name="articleid" id="articleid" value="<%=id %>"/>
							<input type="hidden" name="receiver" id="receiver" value=""/>
							<div class="discuss-submit">
								<h5 class="discuss-sum">提交评论 </h5>
								<p id="callname" ><input id="commentuser" name="commentuser" placeholder="称呼" type="text" validate="default" isnull=true maxlength="30" minlength="0" class="ui-input" value="<%=username%>" <%if(userInfo!=null){%>userid="<%=userInfo.getId()%>"<%} %>/>&nbsp;&nbsp;<span style="color:red;">(如果您未登陆, 请填写称呼)</span></p>
								<p><textarea id="comment" name="comment" style="width: 96%;height:80px;" validate="default" isnull=true maxlength="1000" minlength="0"></textarea></p>
								<p id="checkcode-field">
									<span>验证码  </span><span style="padding: 0 7px;"><input type="text" class="ui-input" id="checkcode" name="checkcode" validate="default" validateAjax="checkcode-logout.do" isnull=true maxlength="4" minlength="0"/></span><input id="comment-submit" type="button" value="提交"/>
								</p>
								<div class="checkcode-frame" id="index-checkcode" style="position: absolute;z-index: 100;display:none;">
									<a href="#" id="refresh-checkcode">刷新验证码</a>
									<img id="checkcode-image" src="" style="display:none;"/>
								</div>
							</div>
						</form>
					</div>
					
				</div>
				<div class="index-foot-line"></div>
			</div>
			<!-- 文章内容结束 -->
		</div>
		<div class="float-right index-tips">
			<%@ include file="index-tips.jsp" %>
		</div>
		<div class="clear"></div>
	</div>
	<%@ include file="../foot.html" %>
</body>
</html>