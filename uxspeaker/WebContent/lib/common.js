$(function(){
	$("body").ajaxError(function(e, xhr, settings, exception){
		var error = exception+" "+xhr.status;
		var content = xhr.responseText;
		if(content.indexOf("session")>=0){
			$.toast("请求已过期, 请重新登陆!");
		}else{
			$.toast(error);
		}
		
		$("body").loading("close");
	});
	$('body').ajaxSend(function(e, xhr, settings) {
		if(settings.url.indexOf("noloading=true")<0){
			$("body").loading("open");
		}
		
	});
	$('body').ajaxComplete(function(e, xhr, settings) {
		//console.log("close");
		$("body").loading("close");
	});
});
$.extend({
	/*parseWindowUrl: function(url){
		return url.substring(url.indexOf("#")+1)+".html";
	}*/
	toast : function(msg,duration){
		var time = 2000;
		if(typeof(duration)!="undefined"){
			time = duration;
		}
		var obj = $("<div style='position: fixed;top:50%;left:48%;z-index:99999;border:1px solid #EADFCF;padding:10px;background-color:#F9F9F5;border-radius:5px;box-shadow:1px 1px 3px #F2EDE4;visibility: hidden;'>"+msg+"</div>");
		$("body").append(obj);
		var left = ($(document).width()-obj.innerWidth())/2;
		obj.css("left",left+"px").css("display","none").css("visibility","visible");
		obj.fadeIn(500,function(){
			setTimeout(function(){
				obj.fadeOut(500,function(){
					obj.remove();
				});
			},time);
		});
	},
	//判断中文长度
	getLength:function(strTemp){
    	var i,sum;  
    	sum=0;  
    	for(i=0;i<strTemp.length;i++) {  
    		if ((strTemp.charCodeAt(i)>=0) && (strTemp.charCodeAt(i)<=255)){
    			sum=sum+1;  
    		}else{
    			sum=sum+2;
    		}
    	}  
    	return sum;  
    },
    swfupload : function(option){
    	if($("#SWFUpload_"+SWFUpload.movieCount).length>0){
    		$("#SWFUpload_"+SWFUpload.movieCount).remove();
    	}
    	var v = {
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
			button_width : 60,
			button_height : 25,
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
		};
    	$.extend(true,v,option);
    	var upload = new SWFUpload(v);
    	return upload;
    },
    queryData: function(option,fn){
    	/**
    	 * {start:1,row:5,sql:xxx,logout:true}
    	 */
    	var url = "query";
    	if(option.logout!=null && $.type(option.logout)!="undefined" && option.logout){
    		url += "-logout";
    	}
    	$.ajax({
    		url:url+".do",
    		dataType: 'json',
    		data: option,
    		async : false,
    		success: function(data){
    			if($.isFunction(fn)){
    				fn(data);
    			}
    		}
    	});
    }
});
$.fn.extend({
	/**
	 * th		{'column1':'innerText1','column2':'innerText2','column3':'innerText3'}
	 * option 	{start:1,row:5,sql:xxx,logout:true}
	 */
	queryData: function(th,option){
		var _this = $(this);
		if(!_this.hasClass("grid-table")){
			_this.addClass("grid-table");
		}
		_this.empty();
		_this.append("<thead><tr></tr></thead>");
		_this.hide();
		_this.fadeIn(500);
		(function createThead(){
			var thead = _this.find("thead").find("tr");
			for ( var key in th) {
				if(key!='{template}'){
					thead.append("<td>"+th[key]+"</td>");
				}else{
					var content = th[key]("thead");
					thead.append("<td>"+content+"</td>");
				}
				
			}
		})();
		$.queryData(option,function(data){
			var foot = data.pop();
			_this.append("<tbody></tbody>");
			var tbody = _this.find("tbody");
			for(var i=0; i<data.length;i++){
				tbody.append("<tr></tr>");
				var tr = tbody.find("tr:last");
				for ( var key in th) {
					if(key!='{template}'){
						tr.append("<td>"+data[i][key]+"</td>");
					}else{
						var content = th[key]("tbody");
						tr.append("<td>"+content+"</td>");
					}
				}
			};
			_this.find("input[type=button]").button();
			_this.append("<tfoot><tr><td colspan='99'></td></tr></tfoot>");
			_this.find("tfoot").find("td").pageFoot({total:foot.total,current:(foot.start/foot.rowcount+1),pagenumber:foot.rowcount},function(o){
				var start = (o.current-1)*foot.rowcount;
				option.start = start;
				_this.queryData(th,option);
				return false;
			});
		});
	},
	loading : function(fn){
		if(fn=="open"){
			var obj = $("<div class='loading wait-loading' style='width:100%;height:100%;position: fixed;z-index:9999;background-position:center center;filter:Alpha(opacity=40);background-color:white;top:0;opacity:.4;background-repeat:no-repeat;'></div>");
			$("body").append(obj);
		}else if(fn=="close"){
			$("body").find(".wait-loading").remove();
		}
		
	},
	defaultMsg: function(){
		var attr = "placeholder";
		var className = "default-msg";
		var parseVal = function(val){
			if(typeof(val)=="undefined"){
				val = "";
			}
			return val;
		};
		$(this).each(function(){
			var value = parseVal($(this).attr(attr));
			if(value!="" && $(this).val()==""){
				$(this).val(value);
				$(this).addClass(className);
			}
		});
		$(this).focus(function(){
			if($(this).hasClass(className)){
				$(this).val("");
				$(this).removeClass(className);
			}
		}).focusout(function(){
			var value = parseVal($(this).attr(attr));
			if($.trim($(this).val())=="" && value!=""){
				var value = parseVal($(this).attr(attr));
				$(this).val(value);
				$(this).addClass(className);
			}
		});
		/*$(this).bind("change",function(){
			var value = parseVal($(this).attr(attr));
			if($.trim($(this).val())=="" && value!=""){
				var value = parseVal($(this).attr(attr));
				$(this).val(value);
				$(this).addClass(className);
			}else{
				$(this).removeClass(className);
			}
		});*/
	},
	searchInput: function(){
		$(this).wrap("<div class='ui-input-search'></div>");
	},
	/**
	 * 参数说明:
	 * 1. list, 显示的list
	 * 2. title, 标题
	 * 3. position参数,格式{}
	 */
	comboboxType: function(o,callback){
		var combobox = this;
		var option = {
			list:[],//显示的列表,格式:[{key:'11',value:'同事'},{key:'11',value:'同事',select:true}]
			title:"title",//显示的标题
			position:{//位置, 默认值
				left:0,
				right:32,
				top:0,
				bottom:0,
			},
			zIndex:99,
			defaultPostion:"right",//默认列表是left还是right
			createNumber:1 //创建列表次数,比如$('.combobox-frame'), 也只创建一个list. 
							//-1的话,是按照元素的个数循环创建
		};
		$.extend(true,option,o);
		/**************方法***************/
		var id = "combo_"+(new Date()).getTime();
		var createElement = function(){
			var v = '<div id="'+id+'" class="combobox-list" position="'+option.defaultPostion+'" style="display:none;"><div class="combobox-title" style="color:black;">'+option.title+'</div><div class="combobox-content">';
			for(var i = 0 ; i < option.list.length ; i++){
				v += '<a href="#" key='+option.list[i].key+' class="'+(typeof(option.list[i].select)!='undefined'?'select combobox-element':'combobox-element')+'">'+option.list[i].value+'</a>';
			}
			v += '</div><div style="clear: both;float: none;"></div></div>';
			return v;
		};
		var appendList = function(index){
			if(typeof(index)=="undefined"){
				$(combobox).after(createElement());
			}else{
				$(combobox).eq(index).after(createElement());
			}
			
		};
		/******************************/
		if(option.createNumber>=0){
			for(var i = 0 ; i < option.createNumber; i++){
				appendList(i);
			}
		}else{
			appendList();
		}
		$(this).click(function(e){
			var p = $(this).position();
			$("#"+id).hide();
			$("#"+id).fadeIn(500);
			if(option.defaultPostion=="right"){
				$("#"+id).css("right",option.position.right+"px");
			}else{
				$("#"+id).css("left",option.position.left+"px");
			}
			$("#"+id).css("top",(p.top+17)+"px");
			e.stopPropagation();
		});
		if(typeof(callback)!="undefined"){
			$("#"+id+" .combobox-element").click(callback);
			$("#"+id+" .combobox-element").click(function(){
				$("#"+id+" .select").removeClass("select");
				$(this).addClass("select");
				$("#"+id).fadeOut(500);
			});
		};
		$(document).click(function(){
			$("#"+id).fadeOut(500);
		});
	},
	createCombobox: function(){
		var id = $(this).attr("id");
		$.getScript("lib/plugin/jquery.combobox.js",function(){
			$("#"+id).combobox();
		});
	},
	pageFoot : function(o,callback){
		var _this = $(this);
		var option = {
			createdate : "",
			total:0,
			current:0,
			pagenumber : 30,
			shownumber : 7
		};
		$.extend(option,o);
		function clearFoot(){
			_this.find(".common-page-foot").remove();
		}
		function bindEvent(){
			_this.find(".common-page-foot").find("span").bind("click",function(){
				var current = 0;
				var number = Math.ceil(option.total / option.pagenumber);
				if($(this).hasClass("select")){
					return;
				}else if($(this).hasClass("common-page-no")){
					current = $(this).text();
				}else if($(this).hasClass("common-page-frist")){
					current = 1;
				}else if($(this).hasClass("common-page-previous")){
					current = eval(option.current-1);
					if(current<1){
						return;
					}
				}else if($(this).hasClass("common-page-next")){
					current = eval(Number(option.current)+1);
					if(current>number){
						return;
					}
				}else if($(this).hasClass("common-page-end")){
					current = number;
				}
				var tmp = option;
				tmp.current = current;
				var returnVal = callback(tmp);
				if(returnVal || typeof returnVal == "undefined"){
					option.current = current;
					goCurrent(option);
				}
				
			});
		}
		function hideButton(hideFrist,hideLast){
			if(hideFrist){
				_this.find(".common-page-frist").hide();
			}else{
				_this.find(".common-page-frist").show();
			}
			if(hideLast){
				_this.find(".common-page-end").hide();
			}else{
				_this.find(".common-page-end").show();
			}
		}
		function goCurrent(o){
			clearFoot();
			//当前页的分割点
			var splitnumber = Math.floor(o.shownumber/2);
			var createString = '<div class="float" '+(o.createdate==""?'style="display:none;"':"")+' ><div class="float">创建日期:</div><div class="float">'+o.createdate+'</div></div>';
			var footStart = '<span class="common-page-frist">首页</span><span class="common-page-previous">上一页</span>';
			var footEnd = '<span class="common-page-next">下一页</span><span class="common-page-end">末页</span>';
			var footBody = "";
			//页数
			var number = Math.ceil(o.total / o.pagenumber);
			var flag = 0;
			//是否隐藏首页按钮
			var hideFrist = false;
			//是否隐藏最末页按钮
			var hideLast = false;
			for ( var int = 1; int <= number; int++) {
				var temp = "";
				if(int+splitnumber>=o.current || (o.current+o.shownumber>=number && number-int<o.shownumber)){
					if(int==o.current){
						temp = '<span class="common-page-no select">'+int+'</span>';
					}else{
						temp = '<span class="common-page-no">'+int+'</span>';
					}
					footBody += temp;
					flag++;
					if(int == 1){
						hideFrist = true;
					}
					if(int == number){
						hideLast = true;
					}
				};
				if(flag==o.shownumber){
					break;
				}
			}
			var foot = "<div class='float-right'>"+footStart+footBody+footEnd+"</div>";
			var last = $('<div class="common-page-foot"></div>').append(createString).append(foot);
			_this.append(last);
			//处理首页和下一页按钮是否隐藏
			hideButton(hideFrist,hideLast);
			bindEvent();
		}
		if(option.total==0){
			return;
		}
		goCurrent(option);
	},
	getCurrentPageNo:function(){
		var o = $(this).find(".common-page-no.select");
		return o.text();
	},
	//判断中文长度
	getLength:function(strTemp){
    	var i,sum;  
    	sum=0;  
    	for(i=0;i<strTemp.length;i++) {  
    		if ((strTemp.charCodeAt(i)>=0) && (strTemp.charCodeAt(i)<=255)){
    			sum=sum+1;  
    		}else{
    			sum=sum+2;
    		}
    	}  
    	return sum;  
    }
});