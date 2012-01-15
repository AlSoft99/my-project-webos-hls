
/*KindEditor.ready(function(K) {
	article.editor = K.create('textarea[name="article"]', {
		allowFileManager : true
	});
});*/
var article = {
	editor : "",
	basePath : projectname+'/lib/kindeditor/',
	initKindEditor: function(){
		$.getScript('lib/kindeditor/kindeditor-min.js', function() {
			KindEditor.basePath = article.basePath;
			article.editor = KindEditor.create('textarea[id="articlecontent"]');
		});
	}
};

$(function(){
	article.initKindEditor();
	$("input[type=button],button").button(); 
	$("textarea, input[type=text]").defaultMsg();
	/*$("#articledate").datepicker();*/
	//$("#articletype").createCombobox("#articletype");
	$("#type").selectmenu();
	var cutpicture = {};
	$.swfupload({
		upload_url : "uploadphoto.head",
		upload_progress_handler : uploadProgress,
		upload_success_handler : uploadSuccess,
		file_queue_limit : "1",
	});
	
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
		console.log("returnVal.uploadname:"+returnVal.uploadname);
		cutpicture["uploadname"] = returnVal.uploadname;
		imgLoad(returnVal.uploadurl + returnVal.uploadname,function(image){
			cutpicture["w"] = 0;
			image.id = "upload-photo";
			if(image.width < image.height){
				image.height = 300;
			}else{
				image.width = 300;
			}
			
			$("#bl-article-loading").hide();
			$("#bl-article-head").append(image);
			createJcrop();
		});
		
	}
	function uploadProgress(file, bytesLoaded, bytesTotal) {
		try {
			$("#upload-photo,.jcrop-holder").remove();
			$("#bl-article-head").fadeIn(500);
			var percent = Math.ceil((bytesLoaded / bytesTotal) * 100);
			$("#bl-article-loading").show();
			$("#update-loading").text(percent+"%");
		} catch (ex) {
			this.debug(ex);
		}
		
	}
	
	function createJcrop(){
		// Create variables (in this scope) to hold the API and image size
	    var jcrop_api, boundx, boundy;
		$('#upload-photo').Jcrop({
			onChange : updatePreview,
			onSelect : updatePreview,
			aspectRatio : 2.82
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
	$( "#dialog-upload" ).dialog({
		resizable: true,
		height:450,
		width:565,
		autoOpen: false,
		modal: true,
		buttons: {
			"上传": function() {
				//$( this ).dialog( "close" );
				if(typeof(cutpicture.uploadname)=="undefined" || cutpicture.uploadname==""){
					$.toast("请选择图片再上传");
					return;
				}
				console.log("cutpicture.uploadname:==="+cutpicture.uploadname);
				$("body").loading("open");
				var cut = $.param(cutpicture);
				$.get("article-upload.do?"+cut,function(data){
					$("#article-photo-info").attr("src",data).attr("width",226).attr("height",80);
					$("body").loading("close");
					$.toast("保存成功!");
					cutpicture.showname = data;
					$( "#dialog-upload" ).dialog( "close" );
				});
			},
			"关闭": function() {
				$( this ).dialog( "close" );
			}
		},
		close: function() {
			
		}
	});
	$("#article-photo-info").click(function(){
		$( "#dialog-upload" ).dialog("open");
	});
	$("#submitarticle").click(function(){
		var param = $(".ui-form").serialize();
		var editor = {};
		editor["content"] = article.editor.html();
		if(typeof(cutpicture.showname)!="undefined" && cutpicture.showname!=""){
			editor.picture = cutpicture.showname;
		}else{
			editor.picture = "";
		}
		param = param +"&"+$.param(editor);
		/*$(".ui-form").submit();*/
		$.post("article-add.do",param,function(data){
			console.log(data);
		});
	});
});