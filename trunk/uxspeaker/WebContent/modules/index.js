$(function(){
	$("#index-menu li").click(function(){
		$("#index-menu .select").removeClass("select");
		$(this).addClass("select");
	});
	var total = $("#index").attr("total");
	var currentPage = $("#index").attr("currentPage");
	$("#page-foot").pageFoot({total:total,current:currentPage},function(o){
		//console.log(o.current);
	});
	$("body").ajaxError(function(e, xhr, settings, exception){
		var error = exception+" status: "+xhr.status;
		$.toast(error);
		$("body").loading("close");
	});
});