define(function(require, exports, module){ 
	var $ = require("jquery");
	$.fn.extend({
		test: function(){
			alert(1234);
		},
		animation: function (end,time,callback) {
            var start = {};
            var unit = {};
            var _this = $(this);
            var doc = _this.get(0);
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
                		
                		if(doc.style[key].indexOf("(")<0 && doc.style[key].indexOf(")")<0){
                			var styleValue = parseFloat(doc.style[key]);
                			if(isNaN(styleValue)){
                				styleValue = 0;
                			}
                			temp[key] = parseFloat(end[key]) - styleValue;
                			start[key] = doc.style[key];
                		}else{
                			var styleKey = doc.style[key];
                			var part = getOtherPart(end[key],end.params);
                			var startNumber = getNumber(part.part,styleKey);
                			var endNumber = end.params[part.index];
                			temp[key] = endNumber-startNumber;
                			part.key = key;
                			indexList.push(part);
                			start[key] = startNumber;
                		};
                		
                        if ((doc.style[key]+"").indexOf("px") != -1) {
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
            var startTime = (new Date()).getTime();
            var animationFunction = function (nowTime) {
                var process = (function () {
                    
                    return process = (parseFloat(nowTime) - parseFloat(startTime)) / parseFloat(time);
                })();
                if (process >= 1) {
                    for (var key in change) {
                    	var exist = isExist(indexList, key);
                    	if(!exist.exist){
                    		doc.style[key] = end[key]+unit[key];
                    	}else{
                    		
                    		doc.style[key] = exist.part.part[0]+end.params[exist.part.index]+exist.part.part[1];
                    	}
                        
                    }
                    if ($.isFunction(callback)) {
                        callback();
                    }
                    window.webkitCancelRequestAnimationFrame(number);

                } else {
                    for (var key in change) {
                    	var exist = isExist(indexList, key);
                    	if(!exist.exist){
                    		doc.style[key] = (change[key] * process + parseFloat(start[key])) + unit[key];
                    	}else{
                    		var changeValue = change[key] * process + parseFloat(start[key]);
                    		doc.style[key] = exist.part.part[0]+ changeValue + unit[key]+ exist.part.part[1];
                    	}
                    }
                    window.webkitRequestAnimationFrame(animationFunction);
                }
                
            };
            number = window.webkitRequestAnimationFrame(animationFunction);
        }
	});
	$.extend({
		random: function(min, max){
			return Math.floor(min+Math.random()*(max-min));
		}
		
	});
});