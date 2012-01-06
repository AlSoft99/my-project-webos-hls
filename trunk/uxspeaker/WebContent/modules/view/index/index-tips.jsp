<%@page import="com.test.entity.UserInfo"%>
<%@page import="com.test.dao.UserInfoDao"%>
<%@page import="com.frame.servlet.ServletFactory"%>
<% 
//UserInfoDao dao = ServletFactory.newInstant().getFactory().getBean("userInfoDao",UserInfoDao.class);

%>
<style type="text/css">
.lastest-comment{
	
}
.index-tips > ul{
	margin: 5px 0;
}
.index-tips .lastest-comment > dt > a, .index-tips .lastest-comment > dt > span{
	height: auto;
	line-height: normal;color:#464646;
}
.index-tips .lastest-comment > dd{
	margin-left: 0;margin-top: 5px;color:#464646;
}
.index-tips .lastest-comment > dd > a{
	font-size: 12px;text-decoration: none;line-height: 14px;height: auto;color:#464646;
}
</style>
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
<div class="index-type"><h1>最新评论</h1></div>
<ul>
	<li>
		<dl class="lastest-comment">
			<dt><a href="#" style="font-weight: bold;">rayln</a>&nbsp;<span class="float-right">2011-12-28 09:22</span></dt>
			<dd class="discuss-content"><a href="#">离线编辑器开放接口所使用的。其中...</a></dd>
			<dd class="clear"></dd>
		</dl>
	</li>
	<li>
		<dl class="lastest-comment">
			<dt><a href="#" style="font-weight: bold;">rayln</a>&nbsp;<span class="float-right">2011-12-28 09:22</span></dt>
			<dd class="discuss-content"><a href="#">离线编辑器开放接口所使用的。其中...</a></dd>
			<dd class="clear"></dd>
		</dl>
	</li>
	<li>
		<dl class="lastest-comment">
			<dt><a href="#" style="font-weight: bold;">rayln</a>&nbsp;<span class="float-right">2011-12-28 09:22</span></dt>
			<dd class="discuss-content"><a href="#">离线编辑器开放接口所使用的。其中...</a></dd>
			<dd class="clear"></dd>
		</dl>
	</li>
	<li>
		<dl class="lastest-comment">
			<dt><a href="#" style="font-weight: bold;">rayln</a>&nbsp;<span class="float-right">2011-12-28 09:22</span></dt>
			<dd class="discuss-content"><a href="#">离线编辑器开放接口所使用的。其中...</a></dd>
			<dd class="clear"></dd>
		</dl>
	</li>
	<li>
		<dl class="lastest-comment">
			<dt><a href="#" style="font-weight: bold;">rayln</a>&nbsp;<span class="float-right">2011-12-28 09:22</span></dt>
			<dd class="discuss-content"><a href="#">离线编辑器开放接口所使用的。其中...</a></dd>
			<dd class="clear"></dd>
		</dl>
	</li>
</ul>
<div class="index-type"><h1>友情链接</h1></div>
<ul>
	<li><a href="#">百度XDC</a></li>
	<li><a href="#">腾讯CDC</a></li>
	<li><a href="#">腾讯ISUX</a></li>
	<li><a href="#">腾讯GDC</a></li>
	<li><a href="#">腾讯ISD</a></li>
	<li><a href="#">淘宝UED</a></li>
</ul>
<script>
$(function(){
	$("#search").searchInput();
	$("input[type=text]").defaultMsg();
});
</script>