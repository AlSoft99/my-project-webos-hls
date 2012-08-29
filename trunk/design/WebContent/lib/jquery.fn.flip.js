define(function(require, exports, module){ 
	var $ = require("jquery");
	$.fn.extend({
		flip: function(){
			console.log("flip");
			var _this = $(this);
			var object = _this.find("img");
			var prev = $("<div class='prev'><div>");
			var next = $("<div class='next'><div>");
			_this.append(prev);
			object.get(0).onload = function(){
				console.log("object.outerHeight():"+object.height()+"   prev.outerHeight():"+prev.height());
				prev.top = (object.outerHeight() - prev.outerHeight())/2;
			};
			
			
		}
	});
	$.extend({
		
	});
});