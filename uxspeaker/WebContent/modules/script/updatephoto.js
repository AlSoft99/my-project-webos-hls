$(function(){
	var cutpicture = {};
	$.swfupload({
		upload_url : "uploadphoto.head",
		upload_progress_handler : uploadProgress,
		upload_success_handler : uploadSuccess,
		file_queue_limit : "1",
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