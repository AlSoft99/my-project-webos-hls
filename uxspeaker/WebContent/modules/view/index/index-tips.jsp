<%@ page import="java.util.*"%>
<%@ page import="com.frame.servlet.ServletFactory" %>
<%@ page import="com.ux.dao.QueryDao"%>
<%@ page import="com.ux.dao.TagInfoDao"%>
<%@ page import="com.ux.vo.TagInfoVo"%>
<% 
QueryDao queryTips = ServletFactory.newInstant().getFactory().getBean("queryDao",QueryDao.class);
List<Map<String,Object>> paramsTips = queryTips.queryByPage("SQL3", "2");
List<Map<String,Object>> paramsComment = queryTips.queryByPage("SQL4", "",0,5);
TagInfoDao tagInfoDao = ServletFactory.newInstant().getFactory().getBean("tagInfoDao",TagInfoDao.class);
List<List<String>> taglist = tagInfoDao.getTagList();
System.out.println(taglist);
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
.index-tips .index-tag > li{
	border-bottom:0px;line-height: 20px;
}
.index-tips ul .index-tag-level-1 a{
	font-size:16px;line-height: 20px;height:20px;
}
.index-tips ul .index-tag-level-2 a{
	font-size:14px;line-height: 18px;height:18px;
}
.index-tips ul .index-tag-level-3 a{
	font-size:12px;line-height: 16px;height:16px;
}
</style>
<p><input type="text" class="ui-input" id="search" style="width:195px;" placeholder="搜索文章"/></p>
<div class="index-type"><h1>文章分类</h1></div>
<ul>
	<%
	for(Map<String,Object> map : paramsTips){
	%>
	<li><a href="type?typeid=<%=map.get("id")%>"><%=map.get("typename")%> +</a></li>
	<%
	}
	%>
</ul>
<div class="index-type"><h1>热门标签</h1></div>
<ul class="index-tag">
	<% 
	for(int i=0; i < taglist.size();i++){
		List<String> flaglist = taglist.get(i);
		String classname = "";
		if(i<=1){
			classname = "index-tag-level-1";
		}else if(i>=taglist.size()-2){
			classname = "index-tag-level-3";
		}else{
			classname = "index-tag-level-2";
		}
	%>
		<li class='<%=classname %>'>
		<% 
		for(String tagname: flaglist){
		%>
			&nbsp;<a href="#"><%=tagname %></a>&nbsp;
		<%	
		}
		%>
		</li>
	<%
	}
	%>
</ul>
<div class="index-type"><h1>最新评论</h1></div>
<ul>
	<% 
	for(Map<String,Object> map : paramsComment){
		String comment = map.get("comment").toString();
		if(comment.length()>15){
			comment = comment.substring(0, 15)+"...";
		}
	%>
	<li>
		<dl class="lastest-comment">
			<dt><span style="font-weight: bold;"><%=map.get("commentuser") %></span>&nbsp;<span class="float-right"><%=map.get("currentDate") %></span></dt>
			<dd class="discuss-content"><a href="article?id=<%=map.get("articleid") %>"><%=comment %></a></dd>
			<dd class="clear"></dd>
		</dl>
	</li>
	<% 
	}
	%>
	
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