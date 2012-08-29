define(function(require, exports, module){ 
	var $ = require("jquery");
	$.fn.extend({
		flip: function(param){
			var params = {
				btnWidth: 100,
				btnHeight: 100
			};
			$.extend(true, params, param);
			var _this = $(this);
			var object = _this.find("img");
			var prev = $("<div class='prev' ><div>").width(params.btnWidth).height(params.btnHeight);
			var next = $("<div class='next'><div>").width(params.btnWidth).height(params.btnHeight);
			var top = (object.get(0).height - params.btnHeight)/2;
			prev.css("top", top+"px").css("left", "30px");
			next.css("top", top+"px").css("right", "30px");
			_this.append(prev);
			_this.append(next);
			
		}
	});
	$.extend({
		
	});
});