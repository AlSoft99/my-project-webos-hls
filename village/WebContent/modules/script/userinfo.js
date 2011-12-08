$(function(){
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
		
	});
	$("#userinfo-tabs").tabs();
});