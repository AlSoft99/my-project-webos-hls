define(function(require, exports, module){
	var s = this;
	var $ = require("jquery");
	/*exports.sayhello = function(){
		alert("helloworld");
	};*/
	var Box = function(){
		var _this = this;
		_this.width = 102;
		_this.height = 102;
		_this.className = "box";
		_this.index = 0;
		_this.top = 0;
		_this.left = 0;
		_this.move = function(cellLeft,cellTop){
			_this.dom.css("left", cellLeft*_this.width+"px").css("top", cellTop*_this.height+"px");
		};
		_this.dom = $("<div class="+_this.className+"></div>");
		_this.mouseleave = (function(){
			_this.dom.on("mouseleave",function(){
				$(this).animate({
					opacity: 0
				},1000);
			});
		})();
		_this.mouseenter = (function(){
			_this.dom.on("mouseenter",function(){
				$(this).stop();
				$(this).css("opacity","1");
			});
		})();
	};
	var Item = function(color,cellLeft,cellRight){
		var _this = this;
		_this.dom.css("background-color",color).css("z-index",1);
		console.log(1);
		_this.move(cellLeft,cellRight);console.log(2);
	};
	Item.prototype = new Box();
	
	exports.creatBox = function(dom){
		var one = new Box();
		dom.append(one.dom);
	};
	exports.createBackground = function(dom){
		var one = new Box();
		var width = $(window).width();
		var height = $(window).height();
		var cellnumber = Math.ceil(width / one.width);
		var rownumber = Math.ceil(height / one.height);
		
		for ( var int = 0; int < cellnumber; int++) {
			for ( var int2 = 0; int2 < rownumber; int2++) {
				var cell = new Box();
				cell.move(int, int2);
				dom.append(cell.dom);
			}
		}
	};
	exports.createItem = function(dom,position){
		var item = new Item("red",position.left,position.right);
		dom.append(item);
	};
});