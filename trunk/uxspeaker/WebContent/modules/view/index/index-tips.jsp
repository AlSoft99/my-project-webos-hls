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
List<Map<String,Object>> taglist = tagInfoDao.getTagList();
String tagtmp = "";
for(int i=0; i < taglist.size();i++){
	Map<String,Object> flaglist = taglist.get(i);
	String tagname = flaglist.get("tagname").toString();
	tagtmp += tagname+",";
}
if(!"".equals(tagtmp)){
	tagtmp = tagtmp.substring(0, tagtmp.length()-1);
}

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
	font-size:16px;line-height: 25px;height:20px;padding:0 4px;
}
.index-tips ul .index-tag-level-2 a{
	font-size:14px;line-height: 22px;height:18px;padding:0 4px;
}
.index-tips ul .index-tag-level-3 a{
	font-size:12px;line-height: 20px;height:16px;padding:0 4px;
}
</style>
<p><input type="text" class="ui-input float" id="search" style="width:185px;" placeholder="搜索文章"/></p>
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
<ul class="index-tag" id="index-tag"></ul>
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
	function createTag(){
		var tag = '<%=tagtmp%>';
		var taglist = tag.split(",");
		var isNext = true;
		var rownumber = 0;
		var max_rownumber = 7;
		for(var i=0;i<taglist.length;i++){
			if(isNext){
				if(rownumber<2){
					var o = $('<li class="index-tag-level-1" style="display:table;"></li>');
					$("#index-tag").append(o);
				}else if(rownumber>=2 && rownumber<=5){
					var o = $('<li class="index-tag-level-2" style="display:table;"></li>');
					$("#index-tag").append(o);
				}else if(rownumber<=max_rownumber){
					var o = $('<li class="index-tag-level-3" style="display:table;"></li>');
					$("#index-tag").append(o);
				}else{
					return;
				}
				isNext = false;
				rownumber++;
			}
			var o = $('<a href="tag?tagname='+taglist[i]+'">'+taglist[i]+'</a>');
			$("#index-tag li:last").append(o);
			if($("#index-tag li:last").innerWidth()>210){
				$("#index-tag li:last a:last").remove();
				i--;
				isNext=true;
			}
		}
	}
	$("#search").searchInputBtn(function(value){
		if($.trim(value)==""){
			$.toast("搜索请不要为空!");
			return false;
		}
		var obj = {search:value};
		var hreObj = $.param(obj, true);
		window.location = "search?"+hreObj;
	});
	$("input[type=text]").defaultMsg();
	createTag();
	
});
</script>