define(function(require, exports, module){
	var s = this;
	var $ = require("jquery");
	/*exports.sayhello = function(){
		alert("helloworld");
	};*/
	var items = [];
	var Box = function(){
		var _this = this;
		_this.width = 102;
		_this.height = 102;
		_this.className = "box";
		_this.move = function(cellLeft,cellTop){
			_this.dom.css("left", cellLeft*_this.width+"px")
					 .css("top", cellTop*_this.height+"px")
					 .data("cell-left",cellLeft)
					 .data("cell-top",cellTop);
		};
		_this.dom = $("<div class="+_this.className+"></div>");
		_this.animateMove = function(diffLeft,diffTop){
			var left = parseInt(_this.dom.css("left"));
			var top = parseInt(_this.dom.css("top"));
			_this.dom.animate({
				"left": diffLeft*_this.width + left,
				"top": diffTop*_this.height + top,
			},{
				easing: 'linear',
				duration: 100,
				queue: false
			});
		};
	};
	var Grid = function(){
		this.dom.on("mouseleave",function(){
			$(this).animate({
				opacity: 0
			},1000);
		});
		this.dom.on("mouseenter",function(){
			$(this).stop();
			$(this).css("opacity",1);
		});
	};
	
	var Item = function(params){
		var _this = this;
		_this.dom.css("background-color",params.color).css("z-index",1).css("opacity",1).addClass("cell");
		_this.move(params.left,params.top);
		if(params.onclick){
			_this.dom.on("click",function(){
				params.onclick();
			});
		};
		var textDom = $("<div class='body'></div>");
		var titleDom = $("<span class='title'></span>");
		var hoverDom = $("<span class='index'></span>");
		textDom.append(titleDom);
		textDom.append(hoverDom);
		_this.dom.append(textDom);
		_this.setTitle = (function(title){
			titleDom.text(title);
		})(params.title);
		_this.setHover = (function(title){
			hoverDom.text(title);
		})(params.hover);
	};
	
	
	exports.createBackground = function(dom){
		var one = new Box();
		var width = $(window).width();
		var height = $(window).height();
		var cellnumber = Math.ceil(width / one.width);
		var rownumber = Math.ceil(height / one.height);
		
		for ( var int = 0; int < cellnumber; int++) {
			for ( var int2 = 0; int2 < rownumber; int2++) {
				Grid.prototype = new Box();
				var cell = new Grid();
				
				cell.move(int, int2);
				dom.append(cell.dom);
			}
		}
	};
	exports.createItem = function(dom,params){
		Item.prototype = new Box();
		var item = new Item(params);
		dom.append(item.dom);
		items.push(item);
		return item;
	};
	exports.getItems = function(){
		return items;
	};
});