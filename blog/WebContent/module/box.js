define(function(require, exports, module){
	var $ = require("jquery");
	/*exports.sayhello = function(){
		alert("helloworld");
	};*/
	var Box = function(){
		this.width = 100;
		this.height = 100;
		this.className = "box";
	};
	exports.creatBox = function(dom){
		var one = new Box();
		dom.append("<div class="+one.className+"></div>");
	};
});