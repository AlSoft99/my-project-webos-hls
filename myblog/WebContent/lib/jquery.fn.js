define(function(require, exports, module){ 
	var $ = require("jquery");
	$.fn.extend({
		test: function(){
			alert(1234);
		},
		animation: function (end,time,callback) {
			var requestAnimationFrame = window.webkitRequestAnimationFrame 
										|| window.mozRequestAnimationFrame 
										|| window.oRequestAnimationFrame
										|| window.msRequestAnimationFrame
										|| 'jquery';
			var cancelRequestAnimationFrame = window.webkitCancelRequestAnimationFrame 
										|| window.mozCancelRequestAnimationFrame 
										|| window.oCancelRequestAnimationFrame
										|| window.msCancelRequestAnimationFrame
										|| 'jquery';
			if(requestAnimationFrame == "jquery" || cancelRequestAnimationFrame == "jquery"){
				for(var key in end){
					var value = end[key];
					if((value+"").indexOf("(")>=0 && (value+"").indexOf(")")>=0){
						if(end.params){
							for(var i = 0; i< end.params.length; i++){
								value = value.replace("{"+i+"}",end.params[i]);
							}
						}
						$(this).css(key, value);
					}
				};
				$(this).animate(end, time, function(){
					if($.isFunction(callback)){
						callback();
					}
				});
			}else{
				this.animationFrame(end, time, callback);
			}
        },
        animationFrame: function(end,time,callback){
        	var start = {};
            var unit = {};
            var _this = $(this);
            var doc = _this.get(0);
            var requestAnimationFrame = window.webkitRequestAnimationFrame 
										|| window.mozRequestAnimationFrame 
										|| window.oRequestAnimationFrame
										|| window.msRequestAnimationFrame
										|| 'jquery';
            var cancelRequestAnimationFrame = window.webkitCancelRequestAnimationFrame 
										|| window.mozCancelRequestAnimationFrame 
										|| window.oCancelRequestAnimationFrame
										|| window.msCancelRequestAnimationFrame
										|| 'jquery';
            if(requestAnimationFrame=='jquery' || cancelRequestAnimationFrame=='jquery'){
            	console.log("please use animation function, this function need brower provide!");
            	return ;
            }
            var getOtherPart = function(str, array){
            	var temp = {};
            	var arrayList = (function(){
            		$(array).each(function(i){
            			var index = str.indexOf("{"+i+"}");
            			if(index >= 0){
            				temp.part = str.split("{"+i+"}");
            				temp.index = i;
            			};
            		});
            	})();
            	return temp;
            };
            var getNumber = function(part,str){
            	$(part).each(function(i){
            		var value = part[i];
            		str = str.replace(value, "");
            	});
            	return parseInt(str);
            };
            var indexList = [];
            var change = (function () {
                var temp = {};
                for (var key in end) {
                	if(key != "params"){
            			if(doc.style.getPropertyValue(key).indexOf("(")<0 && doc.style.getPropertyValue(key).indexOf(")")<0){
                			var styleValue = parseFloat(doc.style[key]);
                			if(isNaN(styleValue)){
                				styleValue = 0;
                			}
                			temp[key] = parseFloat(end[key]) - styleValue;
                			start[key] = doc.style.getPropertyValue(key);
                		}else{
                			var styleKey = doc.style.getPropertyValue(key);
                			var part = getOtherPart(end[key],end.params);
                			var startNumber = getNumber(part.part,styleKey);
                			var endNumber = end.params[part.index];
                			temp[key] = endNumber-startNumber;
                			part.key = key;
                			indexList.push(part);
                			start[key] = startNumber;
                		};
                		
                        if ((doc.style.getPropertyValue(key)+"").indexOf("px") != -1) {
                            unit[key] = "px";
                        } else {
                            unit[key] = "";
                        };
                	};
                }
                
                return temp;
            })();
            function isExist(array, str){
            	var bool = {
            		exist: false
            	};
            	$(array).each(function(i){
            		if(array[i].key==str){
            			bool.exist = true;
            			bool.part = array[i];
            		}
            	});
            	return bool;
            }
            var number = 0;
            //var startTime = window.webkitAnimationStartTime;
            var startTime = window.msAnimationStartTime || (new Date()).getTime();
            var animationFunction = function (nowTime) {
                var process = (function () {
                    
                    return process = (parseFloat(nowTime) - parseFloat(startTime)) / parseFloat(time);
                })();
                if (process >= 1) {
                    for (var key in change) {
                    	var exist = isExist(indexList, key);
                    	if(!exist.exist){
                    		doc.style.setProperty(key, (end[key]+unit[key]),null);
                    	}else{
                    		
                    		doc.style.setProperty(key, (exist.part.part[0]+end.params[exist.part.index]+exist.part.part[1]),null);
                    	}
                        
                    }
                    if ($.isFunction(callback)) {
                        callback();
                    }
                    cancelRequestAnimationFrame(number);

                } else {
                    for (var key in change) {
                    	var exist = isExist(indexList, key);
                    	if(!exist.exist){
                    		var propertyValue = (change[key] * process + parseFloat(start[key])) + unit[key];
                    		doc.style.setProperty(key, propertyValue,null);
                    	}else{
                    		var changeValue = change[key] * process + parseFloat(start[key]);
                    		var propertyValue = exist.part.part[0]+ changeValue + unit[key]+ exist.part.part[1];
                    		doc.style.setProperty(key, propertyValue,null);
                    	}
                    }
                    requestAnimationFrame(animationFunction);
                }
                
            };
            number = requestAnimationFrame(animationFunction);
        }
	});
	$.extend({
		random: function(min, max){
			return Math.floor(min+Math.random()*(max-min));
		},
		getBrower : function(){
			var result = false;
			result = (!result && $.browser.chrome) ? "-webkit-":false 
					|| (!result && $.browser.mozilla) ? "-moz-":false 
					|| (!result && $.browser.msie) ? "-ms-":false
					|| "";
			return result;
		},
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
});