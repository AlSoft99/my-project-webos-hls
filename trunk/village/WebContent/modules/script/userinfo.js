$(function(){
	$("input[type=button],button").button(); 
	$("#button1").click(function(){
		$("#button2").click();
	});
	$("textarea, input[type=text]").enmuDefaultMsg();
	$("#sign").focusin(function(){
		$(this).height(65);
		$("#sign-bottom").show();
	}).focusout(function(e){
		$(this).height(40);
		$("#sign-bottom").hide();
	});
	$("#sign-bottom").mousedown(function(e){
		e.preventDefault();
	});
	$("#sign-submit").click(function(){
		
	});
});