<%@page import="com.test.entity.UserInfo"%>
<%@page import="com.test.dao.UserInfoDao"%>
<%@page import="com.frame.servlet.ServletFactory"%>
<% 
//UserInfoDao dao = ServletFactory.newInstant().getFactory().getBean("userInfoDao",UserInfoDao.class);

%>
<p><input type="text" class="ui-input" id="search" style="width:195px;" placeholder="搜索文章"/></p>
<div class="index-type"><h1>文章分类</h1></div>
<ul>
	<li><a href="type">交互设计 +</a></li>
	<li><a href="#">视觉设计 +</a></li>
	<li><a href="#">摄影印象 +</a></li>
	<li><a href="#">用户研究 +</a></li>
	<li><a href="#">新人Show +</a></li>
	<li><a href="#">最近折腾 +</a></li>
</ul>
<div class="index-type"><h1>热门标签</h1></div>
<ul>
	<li><a href="#">理论</a></li>
	<li><a href="#">创意</a></li>
	<li><a href="#">作品</a></li>
	<li><a href="#">案例</a></li>
	<li><a href="#">互联网</a></li>
	<li><a href="#">用户习惯</a></li>
</ul>
<div class="index-type"><h1>友情链接</h1></div>
<ul>
	<li><a href="#">交互设计 +</a></li>
	<li><a href="#">视觉设计 +</a></li>
	<li><a href="#">摄影印象 +</a></li>
	<li><a href="#">用户研究 +</a></li>
	<li><a href="#">新人Show +</a></li>
	<li><a href="#">最近折腾 +</a></li>
</ul>
<script>
$(function(){
	$("#search").searchInput();
	$("input[type=text]").defaultMsg();
});
</script>