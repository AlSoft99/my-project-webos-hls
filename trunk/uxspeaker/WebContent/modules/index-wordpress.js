$(function(){
	$("input[type=button],button").button();
	$("input[type=text]").defaultMsg();
	$( "#dialog-register" ).dialog({
		resizable: true,
		height:280,
		width:565,
		autoOpen: false,
		modal: true,
		buttons: {
			"注册": function() {
				//$( this ).dialog( "close" );
			},
			"关闭": function() {
				$( this ).dialog( "close" );
			}
		},
		close: function() {
			
		}
	});
	$("#register").click(function(){
		$( "#dialog-register" ).dialog("open");
		return false;
	});
});