var temp = [{id:"001",name:"ralyn1",number:"1",date:"2011-12-13"},{id:"002",name:"ralyn2",number:"6",date:"2011-12-13"},{id:"003",name:"ralyn3",number:"11",date:"2011-12-13"},{id:"004",name:"ralyn4",number:"41",date:"2011-12-13"},{id:"005",name:"ralyn5",number:"13",date:"2011-12-13"},{id:"006",name:"ralyn5",number:"13",date:"2011-12-13"},{id:"007",name:"ralyn5",number:"13",date:"2011-12-13"}];
var picture = {
	dropobject : null,
	upload:null,
	dropable : function(){
		$( ".bl-picture-cell" ).draggable({ revert: "invalid" });
		this.dropobject = $( "#picture-delete-drag" ).droppable({
			accept: ".bl-picture-cell",
			activeClass: "bl-picture-drag",
			drop: function( event, ui ) {
				var $item = ui.draggable;
				$item.animate({width:"0px",height:"0px",opacity:"0"},500,function(){
					$item.attr("delete","true").hide();
					var deleteid = $item.attr("deleteid");
					var name = $item.find(".picture-name").text();
					var number = $item.find(".picture-number").text();
					var cell = $('<div class="float bl-picture-delete-frame" deleteid="'+deleteid+'"><div class="bl-picture-delete page-title"><a class="close bl-picture-delete-close" ></a><span class="bl-picture-delete-title">'+name+'</span><span class="bl-picture-delete-foot">'+number+'张照片</span></div></div>');
					cell.find(".close").bind("click",function(){
						$(this).parents(".bl-picture-delete-frame").animate({width:"0px",opacity:"0"},500,function(){
							$(this).remove();
							var id = $item.attr("deleteid");
							var o = $("#picture-list").find("div[deleteid="+id+"]");
							o.removeAttr("style").css("position","relative").attr("delete","false");
						});
					});
					$("#picture-delete-list").append(cell);
				
				});
			}
		});
	},
	createlist : function(){
		$("#picture-list").empty();
		for ( var int = 0; int < temp.length; int++) {
			var o = temp[int];
			var cell = '<div class="float bl-picture-cell" delete="false" deleteid="'+o.id+'"><a href="#" class="bl-picture-title-page page-title" style="background-position: 0 -160px"><img alt="" src="stylesheet/img/160.gif"></a><div class="bl-picture-title-name"><span class="picture-name">'+o.name+'</span></div><div class="bl-picture-title-detail"><span class="picture-number">'+o.number+'</span><span>张相片</span><span class="picture-date">'+o.date+'</span></div></div>';
			var element = $(cell);
			$("#picture-list").append(element);
		}
	}
};
$(function(){
	//picture.swfupload();
	picture.createlist();
	$( "#picture-dialog" ).dialog({
		resizable: true,
		height:320,
		width:450,
		autoOpen: false,
		modal: true,
		buttons: {
			"创建": function() {
				$( this ).dialog( "close" );
			},
			"取消": function() {
				$( this ).dialog( "close" );
			}
		}
	});
	$("#picture-create").click(function(){
		picture.createlist();
		$("#picturename").val("");
		$("#picturedescribe").val("");
		$( "#picture-dialog" ).dialog("open");
		$("#picture-sort-drag").slideUp(500);
		$("#picture-delete-drag").slideUp(500);
		return false;
	});
	$("#picture-edit").click(function(){
		picture.createlist();
		$("#picture-sort-drag").slideUp(500);
		$("#picture-delete-drag").slideUp(500);
		$(".bl-picture-title-page").bind("click",function(){
			var picturedescribe = $(this).parent().find(".picture-describe");
			var picturename = $(this).parent().find(".picture-name");
			$( "#picture-dialog" ).dialog("open");
			$("#picturename").val(picturename.text());
			$("#picturedescribe").val(picturedescribe.text());
			return false;
		});
		return false;
	});
	$("#picture-sort").click(function(){
		picture.createlist();
		$("#picture-list").sortable();
		$("#picture-sort-drag").slideDown(500);
		$("#picture-delete-drag").slideUp(500);
		return false;
	});
	$("#picture-delete").click(function(){
		$("#picture-delete-drag").slideDown(500);
		$("#picture-sort-drag").slideUp(500);
		$("#picture-delete-list").empty();
		picture.createlist();
		picture.dropable();
		return false;
	});
	$("#picture-sort-drag").click(function(){
		
	});
	$("#picture-delete-drag").click(function(){
		
	});
	$(".bl-picture-icon").click(function(){
		$(".bl-picture-icon-select").removeClass("bl-picture-icon-select");
		$(this).addClass("bl-picture-icon-select");
	});
	$("#vistor").buttonset();
	$("input[type='button']").button();
	/********************/
	$("#picture-tabs").tabs({ spinner:'Retrieving data...'});
});
