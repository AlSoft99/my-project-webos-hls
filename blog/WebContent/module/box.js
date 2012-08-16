define(function(require, exports, module){
	var _this = this;
	var $ = require("jquery");
	/*exports.sayhello = function(){
		alert("helloworld");
	};*/
	var Box = function(){
		this.width = 102;
		this.height = 102;
		this.className = "box";
		this.index = 0;
		this.top = 0;
		this.left = 0;
		this.move = function(cellLeft,cellTop){
			
		};
	};
	exports.creatBox = function(dom){
		var one = new Box();
		dom.append("<div class="+one.className+"></div>");
	};
	exports.test = function(){
		alert(1234);
	};
	exports.createBackground = function(dom){
		
		for ( var int = 0; int < 10; int++) {
			this.creatBox(dom);
		}
	};
});