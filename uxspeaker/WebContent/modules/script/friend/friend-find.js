$(function() {

	$( "#find-user" ).autocomplete({
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
	$("input[type=text]").defaultMsg();
	$("#find-user").searchInput();
});