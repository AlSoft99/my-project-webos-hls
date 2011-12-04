var viewpath = "modules/view/";
$(function(){
	$("#mainNav li a").click(function(){
		var path = $(this).attr("data-url");
		$("#main").load(viewpath + path);
	});
});