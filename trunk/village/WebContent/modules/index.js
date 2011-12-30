$(function(){
	$("#index-menu li").click(function(){
		$("#index-menu .select").removeClass("select");
		$(this).addClass("select");
	});
});