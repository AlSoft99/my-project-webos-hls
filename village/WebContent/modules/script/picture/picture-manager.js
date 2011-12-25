var picturemanager = {
	imageUrl : "upload/guanrl/picture/",
	getImage : function(id){
		var tempphote = [{big:"9c2d4c01-c816-4710-84e2-d41d3a498016.png",small:"min/9c2d4c01-c816-4710-84e2-d41d3a498016.png",title:"fist image"},{big:"20b0eaf9-17c9-447e-acca-ac748e33f7f8.png",small:"min/20b0eaf9-17c9-447e-acca-ac748e33f7f8.png",title:"second image"},
		                 {big:"161372fc-f83b-4b05-9bde-966d07d87282.jpg",small:"min/161372fc-f83b-4b05-9bde-966d07d87282.jpg",title:"fist image"},{big:"f9e9d47c-d580-4ef8-96c5-7f58495227c7.png",small:"min/f9e9d47c-d580-4ef8-96c5-7f58495227c7.png",title:"second image"},
		                 {big:"9c2d4c01-c816-4710-84e2-d41d3a498016.png",small:"min/9c2d4c01-c816-4710-84e2-d41d3a498016.png",title:"fist image"},{big:"20b0eaf9-17c9-447e-acca-ac748e33f7f8.png",small:"min/20b0eaf9-17c9-447e-acca-ac748e33f7f8.png",title:"second image"},
		                 {big:"161372fc-f83b-4b05-9bde-966d07d87282.jpg",small:"min/161372fc-f83b-4b05-9bde-966d07d87282.jpg",title:"fist image"},{big:"f9e9d47c-d580-4ef8-96c5-7f58495227c7.png",small:"min/f9e9d47c-d580-4ef8-96c5-7f58495227c7.png",title:"second image"},
		                 {big:"9c2d4c01-c816-4710-84e2-d41d3a498016.png",small:"min/9c2d4c01-c816-4710-84e2-d41d3a498016.png",title:"fist image"},{big:"20b0eaf9-17c9-447e-acca-ac748e33f7f8.png",small:"min/20b0eaf9-17c9-447e-acca-ac748e33f7f8.png",title:"second image"},
		                 {big:"161372fc-f83b-4b05-9bde-966d07d87282.jpg",small:"min/161372fc-f83b-4b05-9bde-966d07d87282.jpg",title:"fist image"},{big:"f9e9d47c-d580-4ef8-96c5-7f58495227c7.png",small:"min/f9e9d47c-d580-4ef8-96c5-7f58495227c7.png",title:"second image"}];
		return tempphote;
	},
	swfupload : function(){
		picture.upload = new SWFUpload({
			// 处理文件上传的url  ${pageContext.request.contextPath}
			upload_url : "pictureUploadVo.photo", // 路径写全，否则Firefox下会出现404错误。自由修改处一：处理文件上传的url路径，注意还要写全部

			// 上传文件限制设置
			file_size_limit : "10240", // 10MB
			file_types : "*.jpg;*.gif;*.png", //此处也可以修改成你想限制的类型，比如：*.doc;*.wpd;*.pdf
			file_types_description : "Image Files",
			file_upload_limit : "12",
			//file_queue_limit : "12",

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
		/*function uploadStart(a){
			console.log(a);
		}*/
		var imgLoad = function (url, callback) {  
		    var img = new Image();  
		    img.src = url; 
		    img.width = 100;
		    img.height = 100;
		    if (img.complete) {  
		        callback(img);  
		    } else {  
		        img.onload = function () {  
		            callback(img);  
		            img.onload = null;  
		        };  
		    };  
		};
		function uploadSuccess(file, serverData){
			//console.log(a);
			var returnVal = $.parseJSON(serverData)[0];
			var index = file.index;
			var _this = $($(".bl-phote-upload-item").get(index));
			_this.find(".bl-phote-loading").hide();
			imgLoad(returnVal.uploadurl+"min/"+returnVal.uploadname, function(img){
				_this.find(".bl-phote-show").append(img).fadeIn(500);
			});
		}
		function uploadProgress(file, bytesLoaded, bytesTotal) {
			try {
				var index = file.index;
				var _this = $($(".bl-phote-upload-item").get(index));
				var percent = Math.ceil((bytesLoaded / bytesTotal) * 100);
				_this.find(".bl-phote-loading").show();
				_this.find(".bl-phote-loading-progress").text(percent+"%");
			} catch (ex) {
				this.debug(ex);
			}
			
		}
	},
	emptyItem : function(){
		$("#phote-item-list").empty();
	},
	createItem: function(){
		var image = this.getImage();
		for ( var int = 0; int < image.length; int++) {
			var o = image[int];
			var id = o.big.substring(0,o.big.indexOf("."));
			var cell = '<div class="bl-phote-frame"><div class="bl-phote-frame-choice" ><div ></div><a class="icon bl-phote-frame-edit" title="编辑" imageid="'+id+'" href="#"></a><a imageid="'+id+'" title="删除" class="icon bl-phote-frame-delete" href="#"></a></div><a href="'+this.imageUrl+o.big+'"><img src="'+this.imageUrl+o.small+'" style="width:100px;height:100px;" alt="'+o.title+'" title="'+o.title+'" /></a></div>';
			$("#phote-item-list").append(cell);
		};
		alert(11);
		$("#phote-item-list").append("<div class='clear'></div>");
		$("#phote-item-list").fadeIn(500);
		$('#phote-item-list [title]').colorTip({color:'yellow'});
		$(".bl-phote-frame-edit").click(function(){
			var id = $(this).attr("imageid");
			$( "#photo-dialog-edit" ).dialog("open");
			return false;
		});
		$(".bl-phote-frame-delete").click(function(){
			var id = $(this).attr("imageid");
			$( "#photo-dialog-delete" ).dialog("open");
			return false;
		});
	},
	startYoxview : function(id){
		$(id).yoxview({
		    //backgroundColor: 'Blue',
		    playDelay: 5000
		});
	},
	bindEvent : function(){
		$(".bl-phote-frame").mouseenter(function(){
			$(this).find(".bl-phote-frame-choice").stop(true, true).animate({"margin-top":"0","opacity":1},500);
		}).mouseleave(function(e){
			$(this).find(".bl-phote-frame-choice").stop(true, true).animate({"margin-top":"-25px","opacity":0},500);
		});
	}
};
$(function(){
	picturemanager.swfupload();
	var temp = [{id:"001",name:"ralyn1",number:"1",date:"2011-12-13"},{id:"002",name:"ralyn2",number:"6",date:"2011-12-13"},{id:"003",name:"ralyn3",number:"11",date:"2011-12-13"},{id:"004",name:"ralyn4",number:"41",date:"2011-12-13"},{id:"005",name:"ralyn5",number:"13",date:"2011-12-13"},{id:"006",name:"ralyn5",number:"13",date:"2011-12-13"},{id:"007",name:"ralyn5",number:"13",date:"2011-12-13"}];
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
		picturemanager.bindEvent();
		picturemanager.startYoxview("#phote-item-list");
		$("#phote-foot").pageFoot({createdate:"20110802",total:330,current:6},function(o){
			//console.log(o.current);
		});
	});
	$( "#photo-dialog-add" ).dialog({
		resizable: true,
		height:470,
		width:765,
		autoOpen: false,
		modal: true,
		buttons: {
			"完成": function() {
				$( this ).dialog( "close" );
			}
		},
		close: function() {
			$(this).find(".bl-phote-show").empty();
			$(this).find("#phototitle").val("官家部落格的图片秀秀");
		}
	});
	$( "#photo-dialog-edit" ).dialog({
		resizable: false,
		width:550,
		height:190,
		modal: true,
		autoOpen: false,
		buttons: {
			"修改": function() {
				$( this ).dialog( "close" );
			},
			"取消": function() {
				$( this ).dialog( "close" );
			}
		}
	});
	$( "#photo-dialog-delete" ).dialog({
		resizable: false,
		height:160,
		modal: true,
		autoOpen: false,
		buttons: {
			"删除": function() {
				$( this ).dialog( "close" );
			},
			"取消": function() {
				$( this ).dialog( "close" );
			}
		}
	});
	$("#phote-create").click(function(){
		$( "#photo-dialog-add" ).dialog("open");
		return false;
	});
	
});