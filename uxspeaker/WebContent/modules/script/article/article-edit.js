window.pictureNow = "";
$(function(){
	if(article.currentArticleid==""){
		$.toast("请选择一条修改的记录");
		return false;
	}
	article.initEditKindEditor();
	$("input[type=button],button").button(); 
	//$("textarea, input[type=text]").defaultMsg();
	/*$("#articledate").datepicker();*/
	//$("#articletype").createCombobox("#articletype");
	$("#bl-article-head-edit img,#bl-article-head-edit .jcrop-holder").remove();
	$("#bl-article-head-edit").hide();
	$("#article-edit-form").validateInit();
	var createSelect = function(type,id,where){
		var option = {logout:true,sql:"SQL3",where:where};
		$.queryData(option,function(data){
			$("#article-edit-form #"+id).empty();
			data.pop();
			for (var i = 0; i < data.length; i++) {
				if(type==data[i].id){
					$("#article-edit-form #"+id).append("<option value='"+data[i].id+"' selected>"+data[i].typename+"</option>");
				}else{
					$("#article-edit-form #"+id).append("<option value='"+data[i].id+"'>"+data[i].typename+"</option>");
				}
			}
			$("#article-edit-form #"+id).selectmenu();
		});
	};
	var option = {logout:true,sql:"SQL9",where:" a.articleid='"+article.currentArticleid+"' "};
	$.queryData(option,function(data){
		data.pop();
		var tagname = "";
		for(var i=0; i<data.length;i++){
			tagname += data[i].tagname+",";
		}
		tagname = tagname.substring(0,tagname.length-1);
		$("#article-edit-form #tag").val(tagname);
	});
	var cutpictureEdit = {};
	var option = {logout:true,sql:"SQL10",where:" and a.id="+article.currentArticleid+" "};
	$.queryData(option,function(data){
		var type = data[0].type;
		createSelect(type,"type","2");
		createSelect(data[0].status,"status","4");
		$("#article-edit-form #title").val(data[0].title);
		$("#article-edit-form #article-photo img").attr("src", data[0].picture);
		cutpictureEdit.showname = data[0].picture;
		article.editorModify.html(data[0].content);
		$("#article-edit-id").text(data[0].id);
	});
	
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
		var returnVal = $.parseJSON(serverData);
		cutpictureEdit["uploadname"] = returnVal.uploadname;
		imgLoad(returnVal.uploadurl + returnVal.uploadname,function(image){
			cutpictureEdit["w"] = 0;
			image.id = "upload-photo-edit";
			var imageWidth = image.width;
			var imageHeight = image.height;
			if(image.width < image.height){
				image.height = 300;
				cutpictureEdit["pro"] = 300/imageHeight;
			}else{
				image.width = 300;
				cutpictureEdit["pro"] = 300/imageWidth;
			}
			
			$("#dialog-edit-upload #bl-article-loading-edit").hide();
			$("#dialog-edit-upload #bl-article-head-edit").append(image);
			createJcrop();
		});
		
	}
	function createJcrop(){
		// Create variables (in this scope) to hold the API and image size
	    var jcrop_api, boundx, boundy;
		$('#upload-photo-edit').Jcrop({
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
				$.extend(cutpictureEdit,c);
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
	function uploadProgress(file, bytesLoaded, bytesTotal) {
		try {
			$("#upload-photo-edit,.jcrop-holder").remove();
			$("#dialog-edit-upload #bl-article-head-edit").fadeIn(500);
			var percent = Math.ceil((bytesLoaded / bytesTotal) * 100);
			$("#dialog-edit-upload #bl-article-loading-edit").show();
			$("#dialog-edit-upload #update-loading-edit").text(percent+"%");
		} catch (ex) {
			this.debug(ex);
		}
		
	}
	$( "#dialog-edit-upload" ).dialog({
		resizable: true,
		height:450,
		width:565,
		autoOpen: false,
		modal: true,
		buttons: {
			"上传": function() {
				//$( this ).dialog( "close" );
				if(typeof(cutpictureEdit.uploadname)=="undefined" || cutpictureEdit.uploadname==""){
					$.toast("请选择图片再上传");
					return;
				}
				$("body").loading("open");
				if(cutpictureEdit.w!=0){
					cutpictureEdit.w = Math.round(cutpictureEdit.w/cutpictureEdit.pro);
					cutpictureEdit.h = Math.round(cutpictureEdit.h/cutpictureEdit.pro);
					cutpictureEdit.x = Math.round(cutpictureEdit.x/cutpictureEdit.pro);
					cutpictureEdit.y = Math.round(cutpictureEdit.y/cutpictureEdit.pro);
				}
				var cut = $.param(cutpictureEdit);
				$.get("article-upload.do?"+cut,function(data){
					$("#article-edit-form #article-photo-info").attr("src",data).attr("width",226).attr("height",80);
					$("body").loading("close");
					$.toast("保存成功!");
					cutpictureEdit.showname = data;
					window.pictureNow = data;
					$( "#dialog-edit-upload" ).dialog( "close" );
				});
			},
			"关闭": function() {
				$( this ).dialog( "close" );
			}
		},
		close: function() {
			
		}
	});
	$("#article-edit-form #article-photo-info").click(function(){
		$( "#dialog-edit-upload" ).dialog("open");
	});
	$("#submitarticle-edit").click(function(){
		var validate = $("#article-edit-form").validateForm();
		var validate_content = $.trim(article.editorModify.html());
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
		var param = $("#article-edit-form").serialize();
		var editor = {};
		var text = "";
		if(article.editorModify.text().length>sublength){
			text = article.editorModify.text().substring(0,sublength)+"......";
		}else{
			text = article.editorModify.text();
		}
		text = text.replace(/<[^>]+>/g,"");
		editor["content"] = article.editorModify.html();
		editor["text"] = text;
		if(typeof(window.pictureNow)!="undefined" && window.pictureNow!=""){
			editor.picture = window.pictureNow;
		}else{
			editor.picture = "";
		}
		window.pictureNow = "";
		param = param +"&"+$.param(editor);
		/*$(".ui-form").submit();*/
		$.post("article-edit.do?id="+article.currentArticleid,param,function(data){
			$.toast("文章修改成功!");
			$("#article-edit-form input[type=text]").val("");
			$("#article-edit-form #type").selectmenu("index",0);
			$("#article-edit-form #status").selectmenu("index",0);
			$("#article-edit-form").reset();
			article.editorModify.html("");
			$("#article-tabs").tabs( "option", "selected", 1 );
			$("#article-tabs").tabs( "option", "disabled", [2] );
			//$("#article-tabs").tabs("remove",2);
			//$( "#dialog-edit-upload" ).dialog( "destroy" );
		});
	});
});