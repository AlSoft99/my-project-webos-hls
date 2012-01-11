$(function(){
	var cutpicture = {};
	var upload = new SWFUpload({
		// 处理文件上传的url  ${pageContext.request.contextPath}
		upload_url : "uploadphoto.head", // 路径写全，否则Firefox下会出现404错误。自由修改处一：处理文件上传的url路径，注意还要写全部

		// 上传文件限制设置
		file_size_limit : "10240", // 10MB
		file_types : "*.jpg;*.gif;*.png", //此处也可以修改成你想限制的类型，比如：*.doc;*.wpd;*.pdf
		file_types_description : "Image Files",
		//file_upload_limit : "1",
		file_queue_limit : "1",

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
	function createJcrop(){
		// Create variables (in this scope) to hold the API and image size
	    var jcrop_api, boundx, boundy;
		$('#upload-photo').Jcrop({
			onChange : updatePreview,
			onSelect : updatePreview,
			aspectRatio : 1
		}, function() {
			// Use the API to get the real image size
			var bounds = this.getBounds();
			boundx = bounds[0];
			boundy = bounds[1];
			// Store the API in the jcrop_api variable
			jcrop_api = this;
		});
		function updatePreview(c) {
			if (parseInt(c.w) > 0) {
				$.extend(cutpicture,c);
				/*var rx = 100 / c.w;
				var ry = 100 / c.h;

				$('#preview').css({
					width : Math.round(rx * boundx) + 'px',
					height : Math.round(ry * boundy) + 'px',
					marginLeft : '-' + Math.round(rx * c.x) + 'px',
					marginTop : '-' + Math.round(ry * c.y) + 'px'
				});*/
			}
		};
	}
	var imgLoad = function (url, callback) {  
	    var img = new Image();  
	    img.src = url; 
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
		var returnVal = $.parseJSON(serverData);
		cutpicture["uploadname"] = returnVal.uploadname;
		imgLoad(returnVal.uploadurl + returnVal.uploadname,function(image){
			cutpicture["w"] = 0;
			image.id = "upload-photo";
			if(image.width < image.height){
				image.height = 300;
			}else{
				image.width = 300;
			}
			
			$("#bl-updatephoto-loading").hide();
			$("#bl-updatephoto-head").append(image);
			createJcrop();
		});
		
	}
	function uploadProgress(file, bytesLoaded, bytesTotal) {
		try {
			$("#upload-photo,.jcrop-holder").remove();
			$("#bl-updatephoto-head").fadeIn(500);
			var percent = Math.ceil((bytesLoaded / bytesTotal) * 100);
			$("#bl-updatephoto-loading").show();
			$("#update-loading").text(percent+"%");
		} catch (ex) {
			this.debug(ex);
		}
		
	}
	$("#save-photo").click(function(){
		if(typeof(cutpicture.uploadname)=="undefined" || cutpicture.uploadname==""){
			$.toast("请选择图片再上传");
			return;
		}
		$("body").loading("open");
		var cut = $.param(cutpicture);
		$.get("cuthead.do?"+cut,function(data){
			$("#preview").attr("src",data);
			$("body").loading("close");
			$.toast("保存成功!");
			main.session["picture"] = data;
		});
	});
	if(typeof(main.session["picture"])!="undefined" && main.session["picture"]!=""){
		$("#preview").attr("src",main.session["picture"]);
	}
});