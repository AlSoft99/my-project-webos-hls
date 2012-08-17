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
		_this.transformCellMove = function(cellLeft, cellTop){
			_this.transformPositionMove(cellLeft*_this.width, cellTop*_this.height);
		};
		_this.transformPositionMove = function(moveLeft, moveTop){
			var rotate = 90;
			if($.random(0,2)==0){
				rotate = -rotate;
			}
			_this.dom.css("-webkit-transform", "rotate("+rotate+"deg)");
			_this.dom.animation({
				"-webkit-transform": "rotate({0}deg)",
				left: moveLeft,
				top: moveTop,
				params: [0]
			},100);
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
				params.onclick($(this));
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
		if(dom){
			this.dom.append(dom);
		}
		this.dom.addClass("cell").css("z-index",1).css("opacity",1);
	};
	
	exports.dropAllItems = function(){
		var _this = this;
		var all = this.getItems();
		$(all).each(function(i){
			var elem = all[i];
			_this.dropItem(elem);
		});
	};
	exports.dropItem = function(item){
		var height = item.dom.height();
		var position = item.dom.position();
		item.dom.css("top",-height);
		this.shock(item, position);
	};
	exports.shock = function(item, position, callback){
		var queue = item.dom.queue("fx");
		item.dom.stop(true, true);
		item.dom.animate({
			top: position.top
		},100);
		for ( var int = 7; int >=0; int--) {
			item.dom.animate({
				top: position.top-int*1.5
			},50);
			item.dom.animate({
				top: position.top+int*1.5
			},50);
		};
		item.dom.promise().done(function(){
			if($.isFunction(callback)){
				callback();
			}
		});
	};
	
	exports.openChild = function(dom, list,__this,_this){
		var ___this = this;
		var childList = [];
		var dir = [[0,-1],[1,-1],[1,0],[1,1],[0,1],[-1,1],[-1,0],[-1,-1]];
		$(list).each(function(i){
			if(i<=8){
				Child.prototype = new Box();
				var child = new Child(list[i]);
				var cellLeft = parseInt(__this.get(0).left);
				var cellTop = parseInt(__this.get(0).top);
				dom.append(child.dom);
				child.move(cellLeft, cellTop);
				var random = $.random(0,dir.length);
				var dirRandom = dir.splice(random,1);
				child.transformMove(dirRandom[0][0], dirRandom[0][1]);
				childList.push(child);
			}else{
				console.log("the child should be <= 8 in length");
			}
		});
		//close button
		var close = (function(){
			var div = $("<div class='close'>close</div>");
			div.on("click",function(){
				$(childList).each(function(i){
					var rotate = 30;
					if($.random(0,2)==0){
						rotate = -rotate;
					}
					childList[i].dom.css("-webkit-transform", "rotate("+rotate+"deg)");
					/*childList[i].dom.addClass("drop");
					var p = childList[i].dom.position();
					childList[i].dom.css("top", (p.top+500)+"px");*/
					childList[i].dom.animate({
						opacity: 0,
						top: '+=500'
					},250,function(){
						$(this).remove();
					});
				});
				$(this).remove();
				_this.parent().show();
				___this.dropAllItems();
			});
			return div;
		})();
		
		(function(){
			var body = _this.parent();
			var cell = body.parent();
			body.hide();
			cell.append(close);
		})();
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
	exports.openDialog = function(dom, dialog, $this){
		console.log(2);
		for ( var int = 0; int < dialog.row; int++) {
			var tempLeft = dialog.left;
			for ( var int2 = 0; int2 < dialog.cell; int2++) {
				Child.prototype = new Box();
				var child = new Child();
				child.move($this[0].left, $this[0].top)
				dom.append(child.dom);
				child.transformCellMove(tempLeft, dialog.top);
				tempLeft++;
			}
			dialog.top = dialog.top + 1;
		}
	};
});