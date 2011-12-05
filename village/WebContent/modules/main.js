var viewpath = "modules/view/";
$(function(){
	main.refresh();
	main.menuChange();
});
var main = {
	refresh : function(){
		var url = window.location.hash;
		if(url==""){
			url = "#userinfo";
			$("#main").load(viewpath + $.parseWindowUrl(url));
			return;
		}
		$("#main").load(viewpath + $.parseWindowUrl(url));
		$("#mainNav li .active, #sidebar li .active").removeClass("active");
		$("#mainNav li a,#sidebar li a").each(function(){
			if($(this).attr("href")==url){
				$(this).addClass("active");
				return;
			}
		});
	},
	menuChange : function(){
		$("#mainNav li a,#sidebar li a").click(function(){
			$("#main").load(viewpath + $.parseWindowUrl($(this).attr("href")));
			$("#mainNav li .active, #sidebar li .active").removeClass("active");
			$(this).addClass("active");
			$("#user_item").text($(this).text());
		});
	}
};