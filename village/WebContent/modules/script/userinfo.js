$(function(){
	$("input").button(); 
	$("#button1").click(function(){
		$("#button2").click();
	});
});