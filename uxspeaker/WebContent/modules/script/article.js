
/*KindEditor.ready(function(K) {
	article.editor = K.create('textarea[name="article"]', {
		allowFileManager : true
	});
});*/
var article = {
	editor : "",
	editorModify : "",
	basePath : projectname+'/lib/kindeditor/',
	initKindEditor: function(){
		$.getScript('lib/kindeditor/kindeditor-min.js', function() {
			KindEditor.basePath = article.basePath;
			article.editor = KindEditor.create('textarea[id="articlecontent"]',{
				uploadJson : 'upload_json.php',
			});
		});
	},
	initEditKindEditor: function(){
		/*$.getScript('lib/kindeditor/kindeditor-min.js', function() {
			KindEditor.basePath = article.basePath;
			article.editorModify = KindEditor.create('textarea[id="articlecontent-edit"]',{
				uploadJson : 'upload_json.php',
			});
		});*/
		$.ajax({
			url: 'lib/kindeditor/kindeditor-min.js',
			dataType: 'script',
			async: false,
			success: function(){
				KindEditor.basePath = article.basePath;
				article.editorModify = KindEditor.create('textarea[id="articlecontent-edit"]',{
					uploadJson : 'upload_json.php',
				});
			}
		});
	},
	currentArticleid:""
};

$(function(){
	article.initKindEditor();
	$("#article-tabs").tabs();
	$("#article-tabs").tabs( "option", "disabled", [2] );
	$("input[type=button],button").button(); 
	$("textarea, input[type=text]").defaultMsg();
	/*$("#articledate").datepicker();*/
	//$("#articletype").createCombobox("#articletype");
	$("#article-add-form").validateInit();
	var option = {logout:true,sql:"SQL3",where:"2"};
	$.queryData(option,function(data){
		$("#type").empty();
		data.pop();
		for (var i = 0; i < data.length; i++) {
			$("#type").append("<option value='"+data[i].id+"'>"+data[i].typename+"</option>");
		}
		$("#type").selectmenu();
	});
	var optionStatus = {logout:true,sql:"SQL3",where:"4"};
	$.queryData(optionStatus,function(data){
		$("#status").empty();
		data.pop();
		for (var i = 0; i < data.length; i++) {
			$("#status").append("<option value='"+data[i].id+"'>"+data[i].typename+"</option>");
		}
		$("#status").selectmenu();
	});
	var cutpicture = {};
	$.swfupload({
		upload_url : "uploadphoto.head?reduce=false",
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
		cutpicture["uploadname"] = returnVal.uploadname;
		imgLoad(returnVal.uploadurl + returnVal.uploadname,function(image){
			cutpicture["w"] = 0;
			image.id = "upload-photo";
			var imageWidth = image.width;
			var imageHeight = image.height;
			if(image.width < image.height){
				image.height = 300;
				cutpicture["pro"] = 300/imageHeight;
			}else{
				image.width = 300;
				cutpicture["pro"] = 300/imageWidth;
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
				$("body").loading("open");
				if(cutpicture.w!=0){
					cutpicture.w = Math.round(cutpicture.w/cutpicture.pro);
					cutpicture.h = Math.round(cutpicture.h/cutpicture.pro);
					cutpicture.x = Math.round(cutpicture.x/cutpicture.pro);
					cutpicture.y = Math.round(cutpicture.y/cutpicture.pro);
				}
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
		var validate = $("#article-add-form").validateForm();
		var validate_content = $.trim(article.editor.html());
		if(validate){
			if(!validate_content){
				$.toast("文章请不要为空!");
				return false;
			}
			var tag = $("#tag").val();
			if(tag!=""){
				var tmp = tag.split(",");
				for(var i = 0 ; i < tmp.length; i++){
					if($.getLength(tmp[i])>30){
						$.toast("每个标签请不要超过30个字母!");
						return false;
					}
				}
			}
		}else{
			return false;
		}
		var sublength = 250;
		var param = $("#article-add-form").serialize();
		var editor = {};
		var text = "";
		if(article.editor.text().length>sublength){
			text = article.editor.text().substring(0,sublength)+"......";
		}else{
			text = article.editor.text();
		}
		text = text.replace(/<[^>]+>/g,"");
		editor["content"] = article.editor.html();
		editor["text"] = text;
		if(typeof(cutpicture.showname)!="undefined" && cutpicture.showname!=""){
			editor.picture = cutpicture.showname;
		}else{
			editor.picture = "";
		}
		param = param +"&"+$.param(editor);
		/*$(".ui-form").submit();*/
		$.post("article-add.do",param,function(data){
			$.toast("文章提交成功!");
			$("#article-form input[type=text]").val("");
			$("#type").selectmenu("index",0);
			$("#article-add-form").reset();
			article.editor.html("");
		});
	});
});