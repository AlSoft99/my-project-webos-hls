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
		}
	});
});