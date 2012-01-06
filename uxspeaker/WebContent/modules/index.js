$(function(){
	$("#index-menu li").click(function(){
		$("#index-menu .select").removeClass("select");
		$(this).addClass("select");
	});
	$("#page-foot").pageFoot({createdate:"20110802",total:330,current:6},function(o){
		//console.log(o.current);
	});
});