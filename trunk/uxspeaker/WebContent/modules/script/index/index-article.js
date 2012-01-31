var indexArticle = {
	queryComment: function(current){
		var rownumber = 5;
		var articleid = $("#article-index").attr("articleid");
		var userid = $("#loginname").attr("loginid");
		var option = {start:current,row:rownumber,logout:true,sql:"SQL1",where:articleid};
		$.queryData(option,function(data){
			$("#discuss-item").empty();
			var foot = data.pop();
			for(var i=0; i<data.length;i++){
				var userpicture = "";
				var userlist = "";
				if($.type(data[i].picture)=="undefined" || data[i].picture==""){
					if($.type(data[i].userid)=="undefined" || data[i].userid==""){
						userpicture = "<dd class=\"discuss-photo float\"><img src=\"stylesheet/img/120_0_0.gif\" /></dd>";
					}else{
						userpicture = "<dd class=\"discuss-photo float\"><a href=\"author?id="+data[i].userid+"\"><img src=\"stylesheet/img/120_0_0.gif\" /></a></dd>";
					}
				}else{
					userpicture = "<dd class=\"discuss-photo float\"><a href=\"author?id="+data[i].userid+"\"><img src=\""+data[i].picture+"\" /></a></dd>";
				}
				if($.type(data[i].userid)=="undefined" || data[i].userid==""){
					userlist = '<dt><span style="font-weight: bold;color:#069;">'+data[i].commentuser+'</span>&nbsp;<span>'+data[i].currentDate+'</span><a class="float-right" href="#">回复</a></dt>';
				}else{
					userlist = '<dt><a href="author?id='+data[i].userid+'" style="font-weight: bold;">'+data[i].commentuser+'</a>&nbsp;<span>'+data[i].currentDate+'</span>';
					if(data[i].userid==userid){
						userlist += '<a class="float-right" href="#">删除</a><a class="float-right" href="#">编辑</a>';
					}else{
						userlist += '<a class="float-right" href="#">回复</a>';
					}
					userlist += "</dt>";
				}
				var content = "<dl>"+ userpicture+ userlist+ "<dd class=\"discuss-content\">"+data[i].comment+"</dd><dd class=\"clear\"></dd>";
				$("#discuss-item").append(content);
			}
			$("#discuss-item").pageFoot({total:foot.total,current:(foot.start/rownumber+1),pagenumber:rownumber},function(o){
				indexArticle.queryComment((o.current-1)*rownumber);
			});
		});
	}
};
$(function(){
	var articleid = $("#article-index").attr("articleid");
	var cookie = {};
	var cookieStr = $.cookie("uxspeaker-article-love-"+articleid);
	if(cookieStr=="1"){
		$("#have-love").show();
		$("#add-love").hide();
	}
	$("input[type=button]").button();
	$("#checkcode").click(function(e){
		var p = $(this).position();
		var _this = $("#index-checkcode");
		_this.css("top",(p.top+30)+"px").css("left",p.left+"px");
		_this.fadeIn(500);
	});
	$("#refresh-checkcode").click(function(e){
		$("#checkcode-image").get(0).src = "imageServlet?"+Math.random();
		$(this).hide();
		$("#checkcode-image").fadeIn(500);
		return false;
	});
	$("#checkcode-image").click(function(){
		$(this).get(0).src = "imageServlet?"+Math.random();
	});
	$(document).click(function(){
		$("#index-checkcode").fadeOut(500);
	});
	$("#checkcode-field,#index-checkcode").click(function(e){
		e.stopPropagation();
	});
	$("#add-love").click(function(){
		$.get("add-love-logout.do",{id:articleid},function(){
			var text = $("#add-love-number").text();
			$("#add-love-number").text((text*1+1));
			$.toast("已成功加心!");
			$.cookie("uxspeaker-article-love-"+articleid,"1");
			$("#have-love").show();
			$("#add-love").hide();
		});
		return false;
	});
	$("#comment-form").validateInit();
	$("#comment-submit").click(function(){
		var validate = $("#comment-form").validateForm();
		if(validate){
			$.get("add-comment-logout.do",$("#comment-form").serialize(),function(){
				$.toast("提交成功!");
				indexArticle.queryComment(0);
				$("#comment-form #checkcode, #comment-form textarea").val("");
			});
		}
	});
	indexArticle.queryComment(0);
});