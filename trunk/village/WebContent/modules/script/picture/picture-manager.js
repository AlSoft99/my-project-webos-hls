var temp = [{id:"001",name:"ralyn1",number:"1",date:"2011-12-13"},{id:"002",name:"ralyn2",number:"6",date:"2011-12-13"},{id:"003",name:"ralyn3",number:"11",date:"2011-12-13"},{id:"004",name:"ralyn4",number:"41",date:"2011-12-13"},{id:"005",name:"ralyn5",number:"13",date:"2011-12-13"},{id:"006",name:"ralyn5",number:"13",date:"2011-12-13"},{id:"007",name:"ralyn5",number:"13",date:"2011-12-13"}];
var tempphote = [{big:"a36f0420-81aa-490d-8fe8-04e3ba48b78a.png",small:"a36f0420-81aa-490d-8fe8-04e3ba48b78a.png",title:"fist image"},{big:"c434d9b3-6764-4cba-8299-5f26eccf861b.jpg",small:"c434d9b3-6764-4cba-8299-5f26eccf861b.jpg",title:"second image"},
                 {big:"a36f0420-81aa-490d-8fe8-04e3ba48b78a.png",small:"a36f0420-81aa-490d-8fe8-04e3ba48b78a.png",title:"fist image"},{big:"c434d9b3-6764-4cba-8299-5f26eccf861b.jpg",small:"c434d9b3-6764-4cba-8299-5f26eccf861b.jpg",title:"second image"},
                 {big:"a36f0420-81aa-490d-8fe8-04e3ba48b78a.png",small:"a36f0420-81aa-490d-8fe8-04e3ba48b78a.png",title:"fist image"},{big:"c434d9b3-6764-4cba-8299-5f26eccf861b.jpg",small:"c434d9b3-6764-4cba-8299-5f26eccf861b.jpg",title:"second image"}];
var picturemanager = {
	imageUrl : "upload/picture/",
	getImage : function(id){
		return tempphote;
	},
	emptyItem : function(){
		$("#phote-item-list").empty();
	},
	createItem: function(){
		var image = this.getImage();
		for ( var int = 0; int < image.length; int++) {
			var o = image[int];
			var cell = '<div class="bl-phote-frame"><a href="'+this.imageUrl+o.big+'"><img src="'+this.imageUrl+o.small+'" style="width:100px;height:100px;" alt="'+o.title+'" title="'+o.title+'" /></a></div>';
			$("#phote-item-list").append(cell);
		};
		$("#phote-item-list").append("<div class='clear'></div>");
	},
	startYoxview : function(id){
		$(id).yoxview({
		    backgroundColor: 'Blue',
		    playDelay: 5000
		});
	}
};
$(function(){
	for ( var int = 0; int < temp.length; int++) {
		var o = temp[int];
		var cell = $('<div class="float bl-phote-frame-item" id="'+o.id+'"><div class="bl-picture-delete page-title bl-phote-title"><span class="bl-picture-delete-title">'+o.name+'</span><span class="bl-picture-delete-foot">'+o.number+'张照片</span></div></div>');
		$("#phote-sort-drag").append(cell);
	};
	$("#phote-sort-drag").append('<div class="clear"></div>');
	$(".bl-phote-title").click(function(){
		$(".bl-phote-frame-item-select").removeClass("bl-phote-frame-item-select");
		$(this).parent().addClass("bl-phote-frame-item-select");
		picturemanager.emptyItem();
		picturemanager.createItem();
		picturemanager.startYoxview("#phote-item-list");
	});
});