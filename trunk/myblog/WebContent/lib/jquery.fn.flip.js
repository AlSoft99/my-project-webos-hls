define(function(require, exports, module){ 
	var $ = require("jquery");
	$.fn.extend({
		flip: function(param){
			var params = {
				btnWidth: 100,
				btnHeight: 100,
				initLeft: 0,
				btnLeft: 0,
				opacity: .7
			};
			window.flip = {};
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
				prev.css("top", top+"px").css("left", params.btnLeft+"px").css("opacity", params.opacity);
				next.css("top", top+"px").css("right", params.btnLeft+"px").css("opacity", params.opacity);
				children.css("position", "absolute");
				prev.on("click", move);
				next.on("click", move);
				function move(){
					var left = 600;
					var dir = $(this).hasClass("prev");
					var position = children.position();
					children.stop();
					
					if(dir){
						if(position.left+600 > params.initLeft){
							left = params.initLeft - position.left;
						}
						children.animate({
							left: "+="+left
						},200);
					}else{
						if(position.left-600 < params.endLeft){
							left = position.left - params.endLeft;
						}
						console.log("left:"+left+"   params.endLeft:"+params.endLeft);
						children.animate({
							left: "-="+left
						},200);
					}
					//console.log("dir:"+dir+"  left:"+left+"   position.left:"+position.left+"   params.endLeft:"+params.endLeft + "   params.initLeft:"+params.initLeft);
				};
			})();
			
			(function setCenter(){
				if(params.width >= (params.imgWidth + params.initLeft)){
					children.css("top",(params.height-params.imgHeight)/2+"px").css("left",(params.width-params.imgWidth)/2+"px");
				}else{
					children.css("top",(params.height-params.imgHeight)/2+"px").css("left",params.initLeft+"px");
				}
			})();
			_this.append(prev);
			_this.append(next);
			if(params.width < (params.imgWidth + params.initLeft)){
				var value = params.initLeft;
				cover.on("mousemove",function(e){
					var offsetX = e.offsetX || (e.clientX - $(this).offset().left);
					value = (parseInt(params.center) - parseInt(offsetX))/100;
				});
				var flipVal = setInterval(function(){
					var position = children.position();
					var progress = (params.endLeft-position.left)/params.endLeft*params.opacity;
					prev.css("opacity", (params.opacity-progress));
					next.css("opacity", progress);
					if(position.left >= params.initLeft && value >= 0){
						window.flip.left = params.initLeft;
						children.css("left",params.initLeft);
						return ;
					}else if(position.left <= params.endLeft && value < 0){
						window.flip.left = params.endLeft;
						children.css("left",params.endLeft);
						return ;
					}
					window.flip.left = position.left + value;
					children.css("left",position.left + value);
				},10);
				
				setInterval(function(){
					if($(document).find(".flip").length == 0){
						clearInterval(flipVal);
						_this = null;
					}
				},1000);
			}else{
				prev.hide();
				next.hide();
			}
			
		},
		flipMove: function(params,time,callback){
			var percent = {
				paramsTime: .8,
				moveTime: .5
			};
			var animateParamsOne = {};
			var animateParamsTwo = {};
			for(var key in params){
				var value = params[key];
				if(value<0){
					animateParamsOne[key] = "-="+Math.abs(value*percent.paramsTime);
					animateParamsTwo[key] = "-="+Math.abs(value*(1-percent.paramsTime));
				}else{
					animateParamsOne[key] = "+="+Math.abs(value*percent.paramsTime);
					animateParamsTwo[key] = "+="+Math.abs(value*(1-percent.paramsTime));
				}
			}
			$(this).animate(animateParamsOne, time*percent.moveTime);
			$(this).animate(animateParamsTwo, time*(1-percent.moveTime));
		}
	});
	$.extend({
		
	});
});