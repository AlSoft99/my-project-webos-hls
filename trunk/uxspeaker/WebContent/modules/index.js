$(function(){
	$("#index-menu li").click(function(){
		$("#index-menu .select").removeClass("select");
		$(this).addClass("select");
	});
	$("#page-foot").pageFoot({createdate:"20110802",total:1,current:1},function(o){
		//console.log(o.current);
	});
	$("body").ajaxError(function(e, xhr, settings, exception){
		var error = exception+" status: "+xhr.status;
		$.toast(error);
		$("body").loading("close");
	});
});