var viewpath = "modules/view/";

var main = {
	session : {},
	sessionParse : function(str){
		this.session = $.parseJSON(str);
	},
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
				$("#user_item").attr("href",url);
				return;
			}
		});
	},
	menuChange : function(){
		$("#mainNav li a,#sidebar li a").click(function(){
			$("#main").css("opacity",1);
			main.loadingWay(viewpath + main.parseWindowUrl($(this).attr("href")));
			$("#mainNav li .active, #sidebar li .active").removeClass("active");
			$(this).addClass("active");
			$("#user_item").text($(this).text());
			$("#user_item").attr("href",$(this).attr("href"));
		});
	},
	loadingWay : function(url){
		$("#main").empty();
		$("#main").append("<div class='loading' style='width:31px;height:31px;margin:200px auto;'></div>");
		$("body > *[id!=wrapper]").remove();
		$("#main").load(url+"?noloading=true",function(){
			$("#main").css("opacity",0);
			$(this).animate({opacity:1},500);
			$("input[type=button],button").button();
			$('[title]').colorTip({color:'yellow'});
		});
	},
};
$(function(){ 
	main.refresh();
	main.menuChange();
	main.sessionParse(sessionStr);
	$("#user_page,#user_item").click(function(){
		main.loadingWay(viewpath + main.parseWindowUrl($(this).attr("href")));
	});
});