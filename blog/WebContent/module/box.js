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
			_this.dom.animate({
				"left": diffLeft*_this.width + left,
				"top": diffTop*_this.height + top
			},{
				easing: 'linear',
				duration: 0,
				queue: false
			});
		};
		_this.transformMove = function(diffLeft, diffTop){
			var left = parseInt(_this.dom.css("left"));
			var top = parseInt(_this.dom.css("top"));
			_this.transformPositionMove(diffLeft*_this.width + left, diffTop*_this.height + top);
		};
		_this.transformPositionMove = function(moveLeft, moveTop){
			var domLeft = parseInt(_this.dom.css("left"));
			var domTop = parseInt(_this.dom.css("top"));
			var diffLeft = parseInt(moveLeft/domLeft < 1 ? domLeft/moveLeft : moveLeft/domLeft) - 1;
			var diffTop = parseInt(moveTop/domTop < 1 ? domTop/moveTop : moveTop/domTop) - 1;
			var valid = function(movePosition, domPosition){
				var value = movePosition-domPosition;
				if(value == 0){
					return value;
				}else{
					return value < 0 ? -1 : 1;
				}
			};
			var dirDom = {
				dirLeft: valid(moveLeft, domLeft),
				dirTop: valid(moveTop, domTop)
			};
			if(dirDom.dirLeft!=0 && dirDom.dirTop!=0){
				_this.dom.css("-webkit-transform-origin", "center center");
				_this.dom.css("-webkit-transform", "rotate(180deg)");
			};
			_this.dom.animation({
				"-webkit-transform": "rotate({0}deg)",
				params: [50]
			},500);
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
		this.dom.addClass("cell").css("z-index",1).css("opacity",1).css("-webkit-transform", "rotate("+random(0,0)+"deg)").addClass("transform");
	};
	
	exports.openChild = function(dom, list,__this){
		$(list).each(function(i){
			Child.prototype = new Box();
			var child = new Child(list[i]);
			var cellLeft = parseInt(__this.get(0).left);
			var cellTop = parseInt(__this.get(0).top);
			dom.append(child.dom);
			child.move(cellLeft, cellTop);
			child.transformMove(i-1, 1);
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