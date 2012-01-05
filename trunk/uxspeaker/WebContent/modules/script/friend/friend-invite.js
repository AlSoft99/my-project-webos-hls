$(function() {

	$( "#search-user" ).autocomplete({
		source: function( request, response ) {
			$.ajax({
				url: "test.jsp",
				dataType: "json",
				data: {
					
				},
				success: function( data ) {
					response( $.map( data, function( item ) {
						return {
							label: item.name + " (" + item.usercode+")",
							value: item.name + " #" + item.usercode
						};
					}));
				}
			});
		},
		minLength: 1,
		select: function( event, ui ) {
			
		},
		open: function() {
			//$( this ).removeClass( "ui-corner-all" ).addClass( "ui-corner-top" );
		},
		close: function() {
			//$( this ).removeClass( "ui-corner-top" ).addClass( "ui-corner-all" );
		}
	});
	var isGo = function(){
		var flag = true;
		$(".bl-friend-item").each(function(){
			if($(this).text()==$("#search-user").val()){
				flag = false;
				$("#search-user").val("");
			}
		});
		return flag;
	};
	$("input[type=text]").defaultMsg();
	$("#search-user").searchInput();
	$("input[type=button]").button();
	$("#add-user-btn").click(function(){
		if(!isGo()){
			return;
		}
		$("#invite-list").append('<div class="bl-friend-list"><div class="bl-friend-item">'+$("#search-user").val()+'</div><a href="#" class="close bl-friend-close"></a><div class="clear"></div></div>');
		$("#search-user").val("");
	});
});