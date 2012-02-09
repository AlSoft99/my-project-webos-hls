var index = {
	queryArticle: function(current){
		var rownumber = $("#index").attr("rownumber");
		var typename = $("#index").attr("tagname");
		var option = {start:current,row:rownumber,logout:true,sql:"SQL6",where:" and d.tagname='"+typename+"' "};
		$("#index-content").empty();
		$("#index-content").append("<div class='loading' style='width:31px;height:31px;margin:200px auto;'></div>");
		$.queryData(option,function(data){
			$("#index-content").find(".loading").remove();
			$("#index-content").hide();
			$("#index-content").fadeIn(500);
			var foot = data.pop();
			for(var i=0; i<data.length;i++){
				var userpicture = data[i].userpicture;
				if(userpicture=="" || userpicture==null || $.type(userpicture)=="undefined"){
					userpicture = "stylesheet/img/120_0_0.gif";
				}
				var picturecontent = "";
				if(data[i].picture!="" && data[i].picture!=null && typeof(data[i].picture)!="undefined"){
					picturecontent = '<div class="index-content-photo">'+
					  					'<a href="article?id='+data[i].id+'"><img width="720" height="255" src="'+data[i].picture+'" /></a>'+
					  				 '</div>';
				}
				var content = '<div class="index-content-item">'+
								'<div>'+
								   '<dl class="dl-user">'+
								     '<dd class="dl-user-photo"><a href="author?id='+data[i].userid+'"><img src="'+userpicture+'" /></a></dd>'+
								     '<dt><a href="article?id='+data[i].id+'">'+data[i].title+'</a></dt>'+
								     '<dd class="dl-user-tips"><a href="author?id='+data[i].userid+'">'+data[i].username+'</a>&nbsp;<span>/</span>&nbsp;<a href="type?typeid='+data[i].type+'">'+data[i].typename+'</a>&nbsp;<span>/</span>&nbsp;<span>'+data[i].firstDate+'</span></dd>'+
								     '<dd class="clear"></dd>'+
								   '</dl>'+
								'</div>'+
								picturecontent+
								'<div class="index-content-text">'+
									data[i].text+
								'</div>'+
								'<div class="index-toolbar">'+
								  '<ul class="float">'+
								    '<li><span class="ux-icon index-search float"></span>&nbsp;<span>'+data[i].brower+'</span></li>'+
								    '<li><span class="ux-icon index-like float"></span>&nbsp;<span>'+data[i].love+'</span></li>'+
								    '<li><span class="ux-icon index-comment float"></span>&nbsp;<span>'+data[i].commentsum+'</span></li>'+
								  '</ul>'+
								  '<a href="article?id='+data[i].id+'" class="float-right">> 阅读全文</a>'+
								  '<div class="clear"></div>'+
								'</div>'+
								'<div class="index-foot-line"></div>'+
							  '</div>';
				$("#index-content").append(content);
			};
			$("#index-content").pageFoot({total:foot.total,current:(foot.start/rownumber+1),pagenumber:rownumber},function(o){
				index.queryArticle((o.current-1)*rownumber);
			});
		});
	}
};
$(function(){
	$("#index-menu li").click(function(){
		$("#index-menu .select").removeClass("select");
		$(this).addClass("select");
	});
	var total = $("#index").attr("total");
	var currentPage = $("#index").attr("currentPage");
	var rownumber = $("#index").attr("rownumber");
	$("#index-content").pageFoot({total:total,current:currentPage,pagenumber:rownumber},function(o){
		index.queryArticle((o.current-1)*rownumber);
	});
});