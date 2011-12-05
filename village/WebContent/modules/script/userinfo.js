$(function(){
	$("input[type=button],button").button();
	$("#button1").click(function(){
		$("#button2").click();
	});
	$("textarea, input[type=text]").enmuDefaultMsg();
	$("#sign").focusin(function(){
		$(this).height(65);
		$("#sign-bottom").show();
	});
	$("#sign-body").focusout(function(){
		$(this).children("textarea").height(40);
		$("#sign-bottom").hide();
	});
});