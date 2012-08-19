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
		_this.realwidth = 101;
		_this.realheight = 101;
		_this.className = "box";
		_this.move = function(cellLeft,cellTop){
			_this.dom.css("left", cellLeft*_this.realwidth+"px")
					 .css("top", cellTop*_this.realheight+"px")
					 .data("cell-left",cellLeft)
					 .data("cell-top",cellTop);
		};
		_this.dom = $("<div class="+_this.className+"></div>");
		_this.animateMove = function(cellLeft,cellTop,time){
			time = time || 200
			_this.dom.animate({
				"left": cellLeft*_this.realwidth,
				"top": cellTop*_this.realheight
			},{
				easing: 'linear',
				duration: time,
				queue: false
			});
		};
		_this.reduction = function(){
			var cellLeft = _this.dom.data("cell-left");
			var cellTop = _this.dom.data("cell-top");
			_this.animateMove(cellLeft, cellTop,300);
		};
		_this.transformMove = function(diffLeft, diffTop, time){
			var left = parseInt(_this.dom.css("left"));
			var top = parseInt(_this.dom.css("top"));
			_this.transformPositionMove(diffLeft*_this.realwidth + left, diffTop*_this.realheight + top, time);
		};
		_this.transformCellMove = function(cellLeft, cellTop, time){
			_this.transformPositionMove(cellLeft*_this.realwidth, cellTop*_this.realheight, time);
		};
		_this.transformPositionMove = function(moveLeft, moveTop, time){
			time = time || 100;
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
			},time);
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
		_this.id = params.left + "" + params.top;
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
				params.onclick($(this), _this);
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
	
	exports.dropAllItems = function(item){
		var _this = this;
		var all = this.getItems();
		item = item || {
			id: 0
		};
		var currentId = item.id;
		$(all).each(function(i){
			var elem = all[i];
			var elemId = elem.id;
			if(currentId!=elemId){
				_this.dropItem(elem);
			}else{
				item.reduction();
			}
			
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
		},200);
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
	
	exports.openChild = function(dom, list,__this,_this, item){
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
		
		var close = this.createClose(childList, item);
		(function(){
			var body = _this.parent();
			var cell = body.parent();
			body.hide();
			cell.append(close);
		})();
	};
	exports.getMaxNumber = function(){
		var one = new Box();
		var width = $(window).width();
		var height = $(window).height();
		return {
			cell: Math.ceil(width / one.width),
			row: Math.ceil(height / one.height)
		};
	};
	exports.createBackground = function(dom){
		var maxnumber = this.getMaxNumber();
		var cellnumber = maxnumber.cell;
		var rownumber = maxnumber.row;
		
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
	exports.createClose = function(childList, item){
		var _this = this;
		var div = $("<div class='close'>close</div>");
		div.on("click",function(){
			$(childList).each(function(i){
				/*var rotate = 30;
				if($.random(0,2)==0){
					rotate = -rotate;
				}
				childList[i].dom.css("-webkit-transform", "rotate("+rotate+"deg)");*/
				var rotate = $.random(-20,20);
				var time = $.random(100,300);
				childList[i].dom.css("-webkit-transform", "rotate("+rotate+"deg)");
				childList[i].dom.animate({
					top: '+='+(10+Math.abs(rotate))
				},time).animate({
					top: '+=600'
				},time,function(){
					$(this).remove();
				});
				/*var p = childList[i].dom.position();
				childList[i].dom.animation({
					top: p.top+(10+Math.abs(rotate))
				},time,function(){
					childList[i].dom.animation({
						top: childList[i].dom.position().top+500
					},time,function(){
						childList[i].dom.remove();
					});
				});*/
			});
			$(this).remove();
			item.dom.find(".body").show();
			setTimeout(function(){
				_this.dropAllItems(item);
			},200);
			
		});
		item.dom.find(".body").hide();
		return div;
	};
	exports.openDialog = function(dom, dialog, $this, item){
		var childList = [];
		var time = 100;
		item.dom.stop(true,true);
		/*for ( var int = 0; int < dialog.row; int++) {
			var tempLeft = dialog.left;
			for ( var int2 = 0; int2 < dialog.cell; int2++) {
				if(int == 0 && int2 == 0){
					console.log("==dialog.left:"+dialog.left+"   dialog.top:"+dialog.top);
					item.transformCellMove(dialog.left, dialog.top);
				}else{
					Child.prototype = new Box();
					var child = new Child();
					child.move($this[0].left, $this[0].top);
					
					for(var key in dialog.style){
						child.dom.css(key, dialog.style[key]);
					}
					child.dom.addClass("dialog");
					child.dom.css("background-position", (-int2*child.width)+"px " + (-int*child.height)+"px");
					dom.append(child.dom);
					child.transformCellMove(tempLeft, dialog.top,time);
					childList.push(child);
					
				}
				tempLeft++;
			}
			time+=50;
			dialog.top = dialog.top + 1;
		}*/
		for ( var int = 0; int < dialog.cell; int++) {
			var tempTop = dialog.top;
			for ( var int2 = 0; int2 < dialog.row; int2++) {
				if(int == 0 && int2 == 0){
					console.log("==dialog.left:"+dialog.left+"   dialog.top:"+dialog.top);
					item.transformCellMove(dialog.left, dialog.top);
				}else{
					Child.prototype = new Box();
					var child = new Child();
					child.move($this[0].left, $this[0].top);
					
					for(var key in dialog.style){
						child.dom.css(key, dialog.style[key]);
					}
					child.dom.addClass("dialog");
					child.dom.css("background-position", (-int*child.width)+"px " + (-int2*child.height)+"px");
					dom.append(child.dom);
					child.transformCellMove(dialog.left, tempTop,time);
					childList.push(child);
					
				}
				tempTop++;
			}
			time+=50;
			dialog.left = dialog.left + 1;
		}
		var closeBtn = this.createClose(childList,item);
		item.dom.append(closeBtn);
	};
});