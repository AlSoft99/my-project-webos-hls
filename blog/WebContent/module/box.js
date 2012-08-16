define(function(require, exports, module){
	var $ = require("jquery");
	/*exports.sayhello = function(){
		alert("helloworld");
	};*/
	var items = [];
	function random(min,max){
		return Math.floor(min+Math.random()*(max-min));
	}
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
			var randomnumber = random(50,90);
			_this.dom.css("-webkit-transform", "rotate("+randomnumber+"deg)");
			_this.dom.animate({
				"left": diffLeft*_this.width + left,
				"top": diffTop*_this.height + top
			},{
				easing: 'linear',
				duration: 100,
				queue: false
			});
			_this.dom.css("-webkit-transform", "rotate(0deg)");
		};
	};
	var Grid = function(){
		this.dom.on("mouseleave",function(){
			$(this).animate({
				opacity: 0
			},1000);
		}).on("mouseenter",function(){
			$(this).stop();
			$(this).css("opacity",1);
		});
	};
	
	var Item = function(params){
		var _this = this;
		var bodyDom = $("<div class='body'></div>");
		var titleDom = $("<span class='title'></span>");
		var hoverDom = $("<span class='hover'></span>");
		var coverDom = $("<div class='cover'></div>").data("index",items.length).data("cell-left",params.left).data("cell-top",params.top);
		_this.dom.css("background-color",params.color).css("z-index",1).css("opacity",1).addClass("cell");
		_this.move(params.left,params.top);

		bodyDom.append(titleDom);
		bodyDom.append(hoverDom);
		bodyDom.append(coverDom);
		_this.dom.append(bodyDom);
		_this.setTitle = (function(title){
			titleDom.text(title);
		})(params.title);
		_this.setHover = (function(title){
			hoverDom.text(title);
		})(params.hover);
		
		function opacity(dom,number){
			dom.animate({
				opacity: number
			},300);
		};
		if(params.onclick){
			coverDom.on("click",function(){
				params.onclick();
			});
		};
		coverDom.on("mouseleave",function(){
			opacity(hoverDom, 0);
			$(items).each(function(i){
				var index = parseInt(coverDom.data("index"));
				if(i!=index){
					opacity(items[i].dom, 1);
				}
			});
		}).on("mouseenter",function(){
			opacity(hoverDom, 1);
			$(items).each(function(i){
				var index = parseInt(coverDom.data("index"));
				if(i!=index){
					opacity(items[i].dom, .5);
				}
			});
		});
		
	};
	
	var Child = function(dom){
		this.dom.append(dom);
		this.dom.addClass("cell").css("z-index",1).css("opacity",1);
	};
	
	exports.openChild = function(dom, list,__this){
		$(list).each(function(i){
			Child.prototype = new Box();
			var child = new Child(list[i]);
			var cellLeft = parseInt(__this.get(0).left);
			var cellTop = parseInt(__this.get(0).top);
			dom.append(child.dom);
			child.move(cellLeft, cellTop);
			child.animateMove(i-1, 1);
		});
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