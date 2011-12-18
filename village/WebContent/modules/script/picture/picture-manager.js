var temp = [{id:"001",name:"ralyn1",number:"1",date:"2011-12-13"},{id:"002",name:"ralyn2",number:"6",date:"2011-12-13"},{id:"003",name:"ralyn3",number:"11",date:"2011-12-13"},{id:"004",name:"ralyn4",number:"41",date:"2011-12-13"},{id:"005",name:"ralyn5",number:"13",date:"2011-12-13"},{id:"006",name:"ralyn5",number:"13",date:"2011-12-13"},{id:"007",name:"ralyn5",number:"13",date:"2011-12-13"}];
var tempphote = [{big:"221bfa11-4d75-4c09-bfc7-e97329d41829.jpg",small:"221bfa11-4d75-4c09-bfc7-e97329d41829.jpg",title:"fist image"},{big:"734c363f-3aaf-41df-a22a-d9e047425942.png",small:"734c363f-3aaf-41df-a22a-d9e047425942.png",title:"second image"},
                 {big:"221bfa11-4d75-4c09-bfc7-e97329d41829.jpg",small:"221bfa11-4d75-4c09-bfc7-e97329d41829.jpg",title:"fist image"},{big:"734c363f-3aaf-41df-a22a-d9e047425942.png",small:"734c363f-3aaf-41df-a22a-d9e047425942.png",title:"second image"},
                 {big:"221bfa11-4d75-4c09-bfc7-e97329d41829.jpg",small:"221bfa11-4d75-4c09-bfc7-e97329d41829.jpg",title:"fist image"},{big:"734c363f-3aaf-41df-a22a-d9e047425942.png",small:"734c363f-3aaf-41df-a22a-d9e047425942.png",title:"second image"},
                 {big:"221bfa11-4d75-4c09-bfc7-e97329d41829.jpg",small:"221bfa11-4d75-4c09-bfc7-e97329d41829.jpg",title:"fist image"},{big:"734c363f-3aaf-41df-a22a-d9e047425942.png",small:"734c363f-3aaf-41df-a22a-d9e047425942.png",title:"second image"},
                 {big:"221bfa11-4d75-4c09-bfc7-e97329d41829.jpg",small:"221bfa11-4d75-4c09-bfc7-e97329d41829.jpg",title:"fist image"},{big:"734c363f-3aaf-41df-a22a-d9e047425942.png",small:"734c363f-3aaf-41df-a22a-d9e047425942.png",title:"second image"},
                 {big:"221bfa11-4d75-4c09-bfc7-e97329d41829.jpg",small:"221bfa11-4d75-4c09-bfc7-e97329d41829.jpg",title:"fist image"},{big:"734c363f-3aaf-41df-a22a-d9e047425942.png",small:"734c363f-3aaf-41df-a22a-d9e047425942.png",title:"second image"}];
var picturemanager = {
	imageUrl : "upload/picture/",
	getImage : function(id){
		return tempphote;
	},
	swfupload : function(){
		picture.upload = new SWFUpload({
			// 处理文件上传的url  ${pageContext.request.contextPath}
			upload_url : "upload.file", // 路径写全，否则Firefox下会出现404错误。自由修改处一：处理文件上传的url路径，注意还要写全部

			// 上传文件限制设置
			file_size_limit : "10240", // 10MB
			file_types : "*.jpg;*.gif;*.png", //此处也可以修改成你想限制的类型，比如：*.doc;*.wpd;*.pdf
			file_types_description : "Image Files",
			//file_upload_limit : "5",
			file_queue_limit : "12",

			// 事件处理设置（所有的自定义处理方法都在handler.js文件里）
			file_dialog_start_handler : fileDialogStart,
			file_queued_handler : fileQueued,
			file_queue_error_handler : fileQueueError,
			file_dialog_complete_handler : fileDialogComplete,
			upload_start_handler : uploadStart,
			upload_progress_handler : uploadProgress,
			upload_error_handler : uploadError,
			upload_success_handler : uploadSuccess,
			upload_complete_handler : uploadComplete,

			// 按钮设置
			button_image_url : "lib/swfupload/xpbutton.png", // 按钮图标
			button_placeholder_id : "spanButtonPlaceholder",
			button_width : 61,
			button_height : 22,
			//button_text : "<span>1234567</span>",
			button_disabled: false,

			// swf设置
			flash_url : "lib/swfupload/swfupload.swf",

			custom_settings : {
				progressTarget : "fsUploadProgress",
				cancelButtonId : "btnCancel"
			},

			// Debug 设置
			debug : false
		});
		function uploadStart(a){
			//console.log(a);
		}
		function uploadSuccess(a){
			//console.log(a);
		}
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
	picturemanager.swfupload();
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
	$( "#photo-dialog-add" ).dialog({
		resizable: true,
		height:450,
		width:750,
		autoOpen: false,
		modal: true,
		buttons: {
			"完成": function() {
				$( this ).dialog( "close" );
			}
		}
	});
	$("#phote-create").click(function(){
		$( "#photo-dialog-add" ).dialog("open");
		return false;
	});
	$("#phote-foot").pageFoot({createdate:"20110802",total:330,current:6},function(o){
		console.log(o.current);
	});
});