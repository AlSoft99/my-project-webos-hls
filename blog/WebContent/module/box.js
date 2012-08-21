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
		_this.width = 100;
		_this.height = 100;
		_this.realwidth = 101;
		_this.realheight = 101;
		_this.className = "box";
		_this.move = function(cellLeft,cellTop){
			_this.dom.css("left", this.calcCell(cellLeft, _this.realwidth)+"px")
					 .css("top", this.calcCell(cellTop,_this.realheight)+"px")
					 .data("cell-left",cellLeft)
					 .data("cell-top",cellTop);
		};
		_this.dom = $("<div class="+_this.className+"></div>");
		_this.animateMove = function(cellLeft,cellTop,time){
			time = time || 200;
			_this.dom.animate({
				"left": this.calcCell(cellLeft, _this.realwidth),
				"top": this.calcCell(cellTop, _this.realheight)
			},{
				easing: 'linear',
				duration: time,
				queue: false
			});
		};
		_this.calcCell = function(cell,size){
			return parseInt(cell)*parseInt(size) + 1;
		};
		_this.calcContent = function(cell,size){
			return parseInt(cell)*parseInt(size) - 1;
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
			_this.transformPositionMove(this.calcCell(cellLeft, _this.realwidth), this.calcCell(cellTop, _this.realheight), time);
		};
		_this.transformPositionMove = function(moveLeft, moveTop, time){
			time = time || 100;
			var rotate = 90;
			if($.random(0,2)==0){
				rotate = -rotate;
			}
			_this.dom.css("transform", "rotate("+rotate+"deg)");
			var transformStr = $.getBrower()+"transform";
			var paramsAnimation = {
				left: moveLeft,
				top: moveTop,
				params: [0]
			};
			paramsAnimation[transformStr] = "rotate({0}deg)";
			_this.dom.animation(paramsAnimation,time);
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
		_this.params = params;
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
			//dom.stop(true, true);
			//this.stopAllShock();
			dom.animate({
				opacity: number
			},300);
		};
		if(params.onclick){
			coverDom.on("click",function(){
				console.log("click dom");
				params.onclick($(this), _this);
			});
		};
		function stopOpacity(items){
			$(items).each(function(i){
				//items[i].css("opacity",1).stop(true, true);;
			});
		}
		coverDom.on("mouseleave",function(e){
			opacity(hoverDom, 0);
			$(items).each(function(i){
				var index = parseInt(coverDom.data("index"));
				if(i!=index){
					opacity(items[i].dom, 1);
				}
			});
		}).on("mouseenter",function(e){
			//opacity(hoverDom, 1);
			hoverDom.css("opacity",1);
			//stopOpacity(items);
			$(items).each(function(i){
				var index = parseInt(coverDom.data("index"));
				items[i].dom.css("opacity",1).stop(true, true);
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
	exports.hideAllItems = function(item){
		var _this = this;
		_this.actionItems(item, function(elem){
			elem.dom.hide();
		}, function(item){
			
		});
	};
	exports.actionItems = function(item, callback, callbackItem){
		var _this = this;
		var all = _this.getItems();
		item = item || {
			id: 0
		};
		var currentId = item.id;
		$(all).each(function(i){
			var elem = all[i];
			var elemId = elem.id;
			if(currentId!=elemId){
				if($.isFunction(callback)){
					callback(elem);
				}
			}else{
				//item.reduction();
				if($.isFunction(callbackItem)){
					callbackItem(item);
				}
			}
			
		});
	};
	exports.dropAllItems = function(item, isReduction){
		var _this = this;
		isReduction = isReduction || true;
		if(isReduction==true){
			_this.actionItems(item, function(elem){
				_this.dropItem(elem);
			}, function(item){
				item.reduction();
			});
		}
	};
	
	exports.dropItem = function(item){
		var height = item.dom.height();
		var position = {
			top: parseInt(item.dom.css("top")),
			left: parseInt(item.dom.css("left"))
		};
		item.dom.css("top",-height).show();
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
	exports.stopAllShock = function(){
		var items = this.getItems();
		$(items).each(function(i){
			var item = items[i];
			item.dom.stop(true, true);
			item.move(item.params.left, item.params.top);
		});
	};
	exports.openChild = function(dom, list, item, params){
		var ___this = this;
		var childList = [];
		var dir = [[0,-1],[1,-1],[1,0],[1,1],[0,1],[-1,1],[-1,0],[-1,-1]];
		params = params || {};
		item.dom.css("opacity",1);
		___this.stopAllShock();
		___this.hideAllItems(item);
		$(list).each(function(i){
			if(i<=8){
				Child.prototype = new Box();
				var child = new Child(list[i]);
				var cellLeft = parseInt(item.params.left);
				var cellTop = parseInt(item.params.top);
				dom.append(child.dom);
				child.move(cellLeft, cellTop);
				var random = $.random(0,dir.length);
				var dirRandom = dir.splice(random,1);
				child.transformMove(dirRandom[0][0], dirRandom[0][1]);
				child.dom.on("click",function(){
					___this.closeItem(childList, false, item, "y");
					___this.hideAllItems(item);
				});
				childList.push(child);
			}else{
				console.error("the child should be <= 8 in length");
			}
		});
		var close = this.createClose(childList, item,params);
		(function(){
			item.dom.append(close);
			item.dom.find(".body").hide();
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
	exports.createClose = function(childList, item, params){
		var _this = this;
		var div = $("<div class='close'></div>");
		params = params || {};
		div.on("click",function(){
			var counitus = true;
			if($.isFunction(params.close)){
				counitus = params.close(item);
				if($.type(counitus)=="undefined" || counitus==null){
					counitus = true;
				}
			}
			if(!counitus){
				return;
			}
			_this.closeItem(childList, $(this), item);
			
		});
		if($.type(params.dom)!="undefined" && params.dom!=null){
			div.append(params.dom);
		}
		item.dom.find(".body").hide();
		return div;
	};
	exports.closeItem = function(childList,self,item ,isReduction){
		var dropTop = 100;
		var dropTime = 100;
		var _this = this;
		isReduction = isReduction|| true;
		for ( var int = childList.length-1; int >= 0; int--) {
			
			var time = $.random(100,300);
			var childTmp = childList[int];
			if(dropTop>=0){
				dropTop-=5;
			}
			if(dropTime<=300){
				dropTime+=20;
			}
			childTmp.dom.animate({
				top: '+='+dropTop
			},time,function(){
				var isrotate = $.random(0,3);
				var rotate = 0;
				if(isrotate==0){
					rotate = 0;
				}else{
					rotate = $.random(-10,10);
				}
				$(this).css("transform", "rotate("+rotate+"deg)");
			}).animate({
				top: '+=600'
			},dropTime,function(){
				$(this).remove();
			});
		}
		if(self){
			self.remove();
		}
		if(item && isReduction==true){
			item.dom.find(".body").show();
		}
		setTimeout(function(){
			_this.dropAllItems(item,isReduction);
		},200);
	};
	exports.createBtn = function(dialogDom, childList, item, dialog){
		var _this = this;
		function getCounitus(o){
			var counitus = true;
			if(o.btn.event){
				counitus = o.btn.event(o.child);
			}
			if($.type(counitus)=="undefined" || counitus==null){
				counitus = true;
			}
			return counitus;
		}
		$(dialogDom).each(function(i){
			var o = dialogDom[i];
			if(o.child){
				o.child.dom.on("click",function(){
					var counitus = getCounitus(o);
					if(counitus){
						var closeDom = item.dom.find(".close");
						_this.closeItem(childList,closeDom,item);
						if(dialog.content){
							dialog.content.remove();
						}
					}
				});
				o.child.dom.append(o.btn.dom);
			}else if(o.close){
				o.close.dom.find(".body").hide();
				o.btn.dom.on("click",function(){
					var counitus = getCounitus(o);
					if(counitus){
						_this.closeItem(childList,$(this),item);
						if(dialog.content){
							dialog.content.remove();
						}
					}
				});
				o.close.dom.append(o.btn.dom);
			}
			
		});
	};
	exports.setDefaultClose = function(btn){
		btn.close = $.extend(true,{
			dom: $("<div>close</div>"),
			left: 0,
			top: 0
		},btn.close);
		btn.close.dom.addClass("close");
	};
	exports.openDialog = function(dom, dialog, item){
		var _this = this;
		var childList = new Array();
		var time = 100;
		this.hideAllItems(item);
		item.dom.find(".close").remove();
		item.dom.css("opacity",1);
		item.dom.stop(true,true);
		var dialogDom = new Array();
		dialog.btn = dialog.btn || {};
		this.setDefaultClose(dialog.btn);
		(function handlerParams(dialog,maxNumber){
			dialog.row = dialog.row==-1 ? maxNumber.row : dialog.row;
			dialog.cell = dialog.cell==-1 ? maxNumber.cell : dialog.cell;
			dialog.left = dialog.left=="center" ? parseInt((maxNumber.cell-dialog.cell)/2) : dialog.left;
			dialog.top = dialog.top=="center" ? parseInt((maxNumber.row-dialog.row)/2) : dialog.top;
		})(dialog,_this.getMaxNumber());
		var tempLeft = dialog.left;
		for ( var int = 0; int < dialog.cell; int++) {
			var tempTop = dialog.top;
			for ( var int2 = 0; int2 < dialog.row; int2++) {
				if(int == 0 && int2 == 0){
					item.transformCellMove(dialog.left, dialog.top);
					dialogDom.push({
						close: item,
						btn: dialog.btn.close
					});
				}else{
					Child.prototype = new Box();
					var child = new Child();
					child.move(item.params.left, item.params.top);
					for(var key in dialog.style){
						child.dom.css(key, dialog.style[key]);
					}
					child.dom.css("background-position", (-int*child.realwidth)+"px " + (-int2*child.realheight)+"px");
					child.dom.addClass("dialog");
					if((int == 0 && int2 == 1) || (int == 1 && int2 == 0)){
						child.dom.addClass("dialog-lt");
					}
					if((int == 0 && int2 == dialog.row-1) || (dialog.row==1 && int==1)){
						child.dom.addClass("dialog-lb");
					}
					if(int == dialog.cell-1 && int2 == 0){
						child.dom.addClass("dialog-rt");
					}
					if(int == dialog.cell-1 && int2 == dialog.row-1){
						child.dom.addClass("dialog-rb");
					}
					
					for(var key in dialog.btn){
						var o = dialog.btn[key];
						if(o.left==int && o.top==int2){
							dialogDom.push({
								child: child,
								btn: o
							});
						}
					};
					dom.append(child.dom);
					child.transformCellMove(tempLeft, tempTop,time);
					childList.push(child);
					
				}
				tempTop++;
			}
			time+=50;
			tempLeft = tempLeft + 1;
		}
		this.createBtn(dialogDom,childList, item, dialog);
		(function createContent(dialog, item){
			
			if(dialog.content){
				if(dialog.row == 1){
					var left = item.calcCell(dialog.left+1, item.realwidth)+1;
					var top = item.calcCell(dialog.top, item.realheight)+1;
					var width = item.calcContent((dialog.cell-1), item.realwidth);
					var height = item.calcContent(dialog.row, item.realheight);
					dialog.content.css("width", width+"px").css("height", height+"px").css("left",left+"px").css("top",top+"px").addClass("dialog-content");
				}else if(dialog.row > 1){
					var left = item.calcCell(dialog.left, item.realwidth)+1;
					var top = item.calcCell(dialog.top+1, item.realheight)+1;
					var width = item.calcContent(dialog.cell, item.realwidth);
					var height = item.calcContent(dialog.row-1, item.realheight);
					dialog.content.css("width", width+"px").css("height", height+"px").css("left",left+"px").css("top",top+"px").addClass("dialog-content");
				}
				dom.append(dialog.content);
			}
		})(dialog, item);
	};
});