$(function(){
	$.get("register.do?method=logout",function(val){
		if(val=="success"){
			window.location = "index";
		}
	});
});