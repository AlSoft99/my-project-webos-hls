var viewpath = "modules/view/";

var main = {
	parseWindowUrl : function(url){
		return url.substring(url.indexOf("#")+1)+".html";
	},
	refresh : function(){
		var url = window.location.hash;
		if(url==""){
			url = "#userinfo";
			main.loadingWay(viewpath + main.parseWindowUrl(url));
			return;
		}
		this.loadingWay(viewpath + main.parseWindowUrl(url));
		$("#mainNav li .active, #sidebar li .active").removeClass("active");
		$("#mainNav li a,#sidebar li a").each(function(){
			if($(this).attr("href")==url){
				$(this).addClass("active");
				$("#user_item").text($(this).text());
				return;
			}
		});
	},
	menuChange : function(){
		$("#mainNav li a,#sidebar li a").click(function(){
			$("#main").css("opacity",0);
			main.loadingWay(viewpath + main.parseWindowUrl($(this).attr("href")));
			$("#mainNav li .active, #sidebar li .active").removeClass("active");
			$(this).addClass("active");
			$("#user_item").text($(this).text());
		});
	},
	loadingWay : function(url){
		$("#main").empty();
		$("#main").load(url,function(){
			$(this).animate({opacity:1},500);
		});
	}
};
$(function(){ 
	main.refresh();
	main.menuChange();
});