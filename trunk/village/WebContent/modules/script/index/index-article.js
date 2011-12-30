$(function(){
	$("input[type=button]").button();
	$("#checkcode").click(function(e){
		var p = $(this).position();
		var _this = $("#index-checkcode");
		_this.css("top",(p.top+30)+"px").css("left",p.left+"px");
		_this.fadeIn(500);
	});
	$("#refresh-checkcode").click(function(e){
		$("#checkcode-image").get(0).src = "imageServlet?"+Math.random();
		$(this).hide();
		$("#checkcode-image").fadeIn(500);
		return false;
	});
	$("#checkcode-image").click(function(){
		$(this).get(0).src = "imageServlet?"+Math.random();
	});
	$(document).click(function(){
		$("#index-checkcode").fadeOut(500);
	});
	$("#checkcode-field,#index-checkcode").click(function(e){
		e.stopPropagation();
	});
});