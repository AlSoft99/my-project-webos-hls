$(function(){
	function injectContent(){
		$("#username").text(main.session.username);
		$("#sign-content").text(main.session.sign);
	}
	$("input[type=button],button").button(); 
	$("#button1").click(function(){
		$("#button2").click();
	});
	$("textarea, input[type=text]").defaultMsg();
	$("#sign-body").focusin(function(){
		$(this).children("textarea").height(65);
		$("#sign-bottom").show();
	}).focusout(function(e){
		$(this).children("textarea").height(40);
		$("#sign-bottom").hide();
	});
	$("#sign-bottom").mousedown(function(e){
		/*window.event.returnValue = false;
		return false;*/
		e.stopImmediatePropagation();
		e.preventDefault();
	});
	$("#sign-submit").click(function(){
		var maxlength = eval($("#sign").attr("maxlength"));
		if($.getLength($("#sign").val()) > maxlength){
			$.toast("请输入小于"+maxlength+"的字符");
			return;
		}
		$.get("userinfo.do?method=sign&"+$("#sign").serialize(),function(data){
			if(data=="success"){
				$.toast("更新成功!");
				main.session.sign = $("#sign").val();
				$("#sign-content").text($("#sign").val());
				$("#sign").val("");
			}
		});
	});
	$("#userinfo-tabs").tabs();
	$("div[id^='ui-tabs']").append("<div class='loading' style='width:31px;height:31px;margin:200px auto;'></div>");
	injectContent();
	$("#userinformation a").click(function(){
		main.loadingWay(viewpath + main.parseWindowUrl($(this).attr("href")));
	});
	if(typeof(main.session["picture"])!="undefined" && main.session["picture"]!=""){
		$("#user-picture").attr("src",main.session["picture"]);
	};
	
});