var viewpath = "modules/view/";
$(function(){
	$("#mainNav li a,#sidebar li a").click(function(){
		$("#main").load(viewpath + $.parseWindowUrl($(this).attr("href")));
		$("#mainNav li .active, #sidebar li .active").removeClass("active");
		$(this).addClass("active");
		$("#user_item").text($(this).text());
	});
});