define(function(require, exports, module){ 
	var $ = require("jquery");
	$.fn.extend({
		flip: function(param){
			var params = {
				btnWidth: 100,
				btnHeight: 100,
				initLeft: 30,
				opacity: .7
			};
			$.extend(true, params, param);
			params.center = params.width/2;
			params.endLeft = -params.imgWidth + params.width - params.initLeft;
			var _this = $(this);
			var children = _this.children();
			var prev = $("<div class='prev' ><div>").width(params.btnWidth).height(params.btnHeight);
			var next = $("<div class='next'><div>").width(params.btnWidth).height(params.btnHeight);
			var top = (params.imgHeight - params.btnHeight)/2 + (params.height-params.imgHeight)/2;
			var cover = $("<div class='flip-cover'></div>");
			_this.addClass("flip");
			_this.append(cover);
			(function init(){
				prev.css("top", top+"px").css("left", params.initLeft+"px").css("opacity", params.opacity);
				next.css("top", top+"px").css("right", params.initLeft+"px").css("opacity", params.opacity);
				children.css("position", "absolute");
			})();
			
			(function setCenter(){
				children.css("top",(params.height-params.imgHeight)/2+"px").css("left",params.initLeft+"px");
			})();
			_this.append(prev);
			_this.append(next);
			var value = params.initLeft;
			cover.on("mousemove",function(e){
				value = (parseInt(params.center) - parseInt(e.offsetX))/100;
			});
			var flipVal = setInterval(function(){
				var position = children.position();
				var progress = (params.endLeft-position.left)/params.endLeft*params.opacity;
				prev.css("opacity", (params.opacity-progress));
				next.css("opacity", progress);
				if(position.left >= params.initLeft && value >= 0){
					children.css("left",params.initLeft);
					return ;
				}else if(position.left <= params.endLeft && value < 0){
					children.css("left",params.endLeft);
					return ;
				}
				children.css("left",position.left + value);
			},10);
			
			setInterval(function(){
				if($(document).find(".flip").length == 0){
					clearInterval(flipVal);
					_this = null;
				}
			},1000);
		}
	});
	$.extend({
		
	});
});