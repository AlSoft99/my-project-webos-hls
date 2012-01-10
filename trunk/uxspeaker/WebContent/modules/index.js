$(function(){
	$("#index-menu li").click(function(){
		$("#index-menu .select").removeClass("select");
		$(this).addClass("select");
	});
	$("#page-foot").pageFoot({createdate:"20110802",total:330,current:6},function(o){
		//console.log(o.current);
	});
	$("body").ajaxError(function(e, xhr, settings, exception){
		var error = exception+" "+xhr.status;
		$.toast(error);
		$("body").loading("close");
	});
});