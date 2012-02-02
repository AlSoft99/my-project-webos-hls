var indexArticle = {
	queryComment: function(current){
		var rownumber = 10;
		var articleid = $("#article-index").attr("articleid");
		var userid = $("#loginname").attr("loginid");
		var option = {start:current,row:rownumber,logout:true,sql:"SQL1",where:articleid};
		var reg=new RegExp("\n","g"); 
		var regBlank=new RegExp(" ","g"); 
		$("#discuss-item").empty();
		$("#discuss-item").append("<div class='loading' style='width:31px;height:31px;margin:200px auto;'></div>");
		$.queryData(option,function(data){
			$("#discuss-item").find(".loading").remove();
			$("#discuss-item").hide();
			$("#discuss-item").fadeIn(500);
			var foot = data.pop();
			for(var i=0; i<data.length;i++){
				var userpicture = "";
				var userlist = "";
				var usercontent = "";
				
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
					userlist = '<dt><span class="index-article-commentuser" style="font-weight: bold;color:#069;">'+data[i].commentuser+'</span>&nbsp;<span>'+data[i].currentDate+'</span><a class="float-right index-article-reply" commentid="'+data[i].id+'" href="#">回复</a></dt>';
				}else{
					userlist = '<dt><a class="index-article-commentuser" href="author?id='+data[i].userid+'" style="font-weight: bold;">'+data[i].commentuser+'</a>&nbsp;<span>'+data[i].currentDate+'</span>';
					if(data[i].userid==userid){
						userlist += '<a class="float-right index-article-edit index-article-edit-delete" commentid="'+data[i].id+'" href="#">删除</a><a class="float-right index-article-edit index-article-edit-modify" href="#">编辑</a><a class="float-right index-article-modify index-article-modify-cancel" style="display:none;" href="#">取消</a><a class="float-right index-article-modify index-article-modify-edit" commentid="'+data[i].id+'" style="display:none;" href="#">修改</a>';
					}else{
						userlist += '<a class="float-right index-article-reply" commentid="'+data[i].id+'" href="#">回复</a>';
					}
					userlist += "</dt>";
				}
				if(data[i].userid==userid){
					usercontent = "<dd class=\"discuss-content index-article-content\">"+data[i].comment.replace(reg,"<br/>").replace(regBlank,"&nbsp;")+"</dd><dd class=\"discuss-content index-article-content-edit\" style=\"display:none;\"><textarea maxlength=\"100\" style=\"width:100%;height:60px;\">"+data[i].comment+"</textarea></dd>";
				}else{
					usercontent = "<dd class=\"discuss-content index-article-content\">"+data[i].comment.replace(reg,"<br/>").replace(regBlank,"&nbsp;")+"</dd>";
				}
				var content = "<dl>"+ userpicture+ userlist+ usercontent + "<dd class=\"clear\"></dd>";
				$("#discuss-item").append(content);
			}
			$("#discuss-item").pageFoot({total:foot.total,current:(foot.start/rownumber+1),pagenumber:rownumber},function(o){
				indexArticle.queryComment((o.current-1)*rownumber);
				return false;
			});
			$("#comment-count").text(foot.total);
			$(".index-article-edit-modify").click(function(){
				var parent = $(this).parent().parent();
				parent.find(".index-article-content,.index-article-edit").hide();
				parent.find(".index-article-content-edit,.index-article-modify").show();
				return false;
			});
			$(".index-article-modify-edit").click(function(){
				var parent = $(this).parent().parent();
				var textarea = parent.find("textarea");
				var id = $(this).attr("commentid");
				var param = {comment:textarea.val(),id:id};
				if($.trim(textarea.val())==""){
					$.toast("请不要为空!");
					return false;
				}
				$.get("modify-comment.do",param,function(data){
					if(data=="success"){
						$.toast("更新成功!");
						parent.find(".index-article-content,.index-article-edit").show();
						parent.find(".index-article-content-edit,.index-article-modify").hide();
						parent.find(".index-article-content").html(textarea.val().replace(reg,"<br/>").replace(regBlank,"&nbsp;"));
					}else{
						$.toast("更新失败!请重新登陆后再尝试!");
					}
				});
				return false;
			});
			$(".index-article-modify-cancel").click(function(){
				var parent = $(this).parent().parent();
				parent.find(".index-article-content,.index-article-edit").show();
				parent.find(".index-article-content-edit,.index-article-modify").hide();
				return false;
			});
			$(".index-article-edit-delete").click(function(){
				var _this = $(this);
				$( "#dialog-confirm" ).dialog({ buttons: [{
	                      text: "删除",
	                      click: function() { 
	                    	  	$(this).dialog("close");
	                    	  	var id = _this.attr("commentid");
	          					var articleid = $("#article-index").attr("articleid");
	          					$.get("delete-comment.do?id="+id+"&articleid="+articleid,function(data){
	          						if(data=="success"){
	          							$.toast("删除成功!");
	          							var current = $("#discuss-item").getCurrentPageNo();
	          							indexArticle.queryComment((current*1-1));
	          						}else{
	          							$.toast("删除失败!");
	          						}
	          					}
	          				  );
	                      }
	                  },{
	                      text: "取消",
	                      click: function() { 
	                    	  $(this).dialog("close"); 
	                      }
	                  }
	              ]});
				$( "#dialog-confirm" ).dialog("open");
				/*var id = $(this).attr("commentid");
				var articleid = $("#article-index").attr("articleid");
				$.get("delete-comment.do?id="+id+"&articleid="+articleid,function(data){
					if(data=="success"){
						$.toast("删除成功!");
						var current = $("#discuss-item").getCurrentPageNo();
						indexArticle.queryComment((current*1-1));
					}else{
						$.toast("删除失败!");
					}
				});*/
				return false;
			});
			$(".index-article-reply").click(function(){
				var blank=new RegExp("\n","g"); 
				var parent = $(this).parent().parent();
				var content = parent.find(".index-article-content").text();
				var commentuser = parent.find(".index-article-commentuser").text();
				content = "-----------------回复: "+commentuser+"--------------------------------\n"+content;
				content = content.replace(blank,"\n|  ");
				content += "\n------------------------------------------------------------------\n";
				$("#comment").val(content);
				return false;
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
	$( "#dialog-confirm" ).dialog({
		resizable: false,
		autoOpen: false,
		height:150,
		modal: true
	});
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
			$.cookie("uxspeaker-article-love-"+articleid,"1",{expires:30});
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
				$("#comment-form").reset();
			});
		}
	});
	indexArticle.queryComment(0);
	$('body').ajaxSuccess(function(e, xhr, settings) {
		if(settings.url.indexOf("register.do?method=login")>=0){
			var loginname=$("#loginname").text();
			$("#commentuser").val(loginname);
			$("#commentuser").removeClass("default-msg");
			var current = $("#discuss-item").getCurrentPageNo();
			indexArticle.queryComment((current*1-1));
		}else if(settings.url.indexOf("register.do?method=logout")>=0){
			$("#commentuser").val("");
			var current = $("#discuss-item").getCurrentPageNo();
			indexArticle.queryComment((current*1-1));
		}
	});
});
