$.extend({
	/*parseWindowUrl: function(url){
		return url.substring(url.indexOf("#")+1)+".html";
	}*/
});
$.fn.extend({
	defaultMsg: function(){
		var attr = "placeholder";
		var className = "default-msg";
		var parseVal = function(val){
			if(typeof(val)=="undefined"){
				val = "";
			}
			return val;
		};
		$(this).each(function(){
			var value = parseVal($(this).attr(attr));
			if(value!=""){
				$(this).val(value);
				$(this).addClass(className);
			}
		});
		$(this).focus(function(){
			if($(this).hasClass(className)){
				$(this).val("");
				$(this).removeClass(className);
			}
		}).focusout(function(){
			var value = parseVal($(this).attr(attr));
			if($.trim($(this).val())=="" && value!=""){
				var value = parseVal($(this).attr(attr));
				$(this).val(value);
				$(this).addClass(className);
			}
		});
	},
	searchInput: function(){
		$(this).wrap("<div class='ui-input-search'></div>");
	},
	/**
	 * 参数说明:
	 * 1. list, 显示的list
	 * 2. title, 标题
	 * 3. position参数,格式{}
	 */
	comboboxType: function(o,callback){
		var combobox = this;
		var option = {
			list:[],//显示的列表,格式:[{key:'11',value:'同事'},{key:'11',value:'同事',select:true}]
			title:"title",//显示的标题
			position:{//位置, 默认值
				left:0,
				right:32,
				top:0,
				bottom:0,
			},
			zIndex:99,
			defaultPostion:"right",//默认列表是left还是right
			createNumber:1 //创建列表次数,比如$('.combobox-frame'), 也只创建一个list. 
							//-1的话,是按照元素的个数循环创建
		};
		$.extend(true,option,o);
		/**************方法***************/
		var id = "combo_"+(new Date()).getTime();
		var createElement = function(){
			var v = '<div id="'+id+'" class="combobox-list" position="'+option.defaultPostion+'" style="display:none;"><div class="combobox-title" style="color:black;">'+option.title+'</div><div class="combobox-content">';
			for(var i = 0 ; i < option.list.length ; i++){
				v += '<a href="#" key='+option.list[i].key+' class="'+(typeof(option.list[i].select)!='undefined'?'select combobox-element':'combobox-element')+'">'+option.list[i].value+'</a>';
			}
			v += '</div><div style="clear: both;float: none;"></div></div>';
			return v;
		};
		var appendList = function(index){
			if(typeof(index)=="undefined"){
				$(combobox).after(createElement());
			}else{
				$(combobox).eq(index).after(createElement());
			}
			
		};
		/******************************/
		if(option.createNumber>=0){
			for(var i = 0 ; i < option.createNumber; i++){
				appendList(i);
			}
		}else{
			appendList();
		}
		$(this).click(function(e){
			var p = $(this).position();
			$("#"+id).hide();
			$("#"+id).fadeIn(500);
			if(option.defaultPostion=="right"){
				$("#"+id).css("right",option.position.right+"px");
			}else{
				$("#"+id).css("left",option.position.left+"px");
			}
			$("#"+id).css("top",(p.top+17)+"px");
			e.stopPropagation();
		});
		if(typeof(callback)!="undefined"){
			$("#"+id+" .combobox-element").click(callback);
			$("#"+id+" .combobox-element").click(function(){
				$("#"+id+" .select").removeClass("select");
				$(this).addClass("select");
				$("#"+id).fadeOut(500);
			});
		};
		$(document).click(function(){
			$("#"+id).fadeOut(500);
		});
	},
	createCombobox: function(){
		var id = $(this).attr("id");
		$.getScript("lib/plugin/jquery.combobox.js",function(){
			$("#"+id).combobox();
		});
	},
	pageFoot : function(o,callback){
		var _this = $(this);
		var option = {
			createdate : "",
			total:0,
			current:0,
			pagenumber : 30,
			shownumber : 7
		};
		$.extend(option,o);
		function clearFoot(){
			_this.find(".common-page-foot").remove();
		}
		function bindEvent(){
			_this.find("span").bind("click",function(){
				var current = 0;
				var number = Math.ceil(option.total / option.pagenumber);
				if($(this).hasClass("select")){
					return;
				}else if($(this).hasClass("common-page-no")){
					current = $(this).text();
				}else if($(this).hasClass("common-page-frist")){
					current = 1;
				}else if($(this).hasClass("common-page-previous")){
					current = eval(option.current-1);
					if(current<1){
						return;
					}
				}else if($(this).hasClass("common-page-next")){
					current = eval(Number(option.current)+1);
					if(current>number){
						return;
					}
				}else if($(this).hasClass("common-page-end")){
					current = number;
				}
				var tmp = option;
				tmp.current = current;
				var returnVal = callback(tmp);
				if(returnVal || typeof returnVal == "undefined"){
					option.current = current;
					goCurrent(option);
				}
				
			});
		}
		function hideButton(hideFrist,hideLast){
			if(hideFrist){
				_this.find(".common-page-frist").hide();
			}else{
				_this.find(".common-page-frist").show();
			}
			if(hideLast){
				_this.find(".common-page-end").hide();
			}else{
				_this.find(".common-page-end").show();
			}
		}
		function goCurrent(o){
			clearFoot();
			//当前页的分割点
			var splitnumber = Math.floor(o.shownumber/2);
			var createString = '<div class="float" '+(o.createdate==""?'style="display:none;"':"")+' ><div class="float">创建日期:</div><div class="float">'+o.createdate+'</div></div>';
			var footStart = '<span class="common-page-frist">首页</span><span class="common-page-previous">上一页</span>';
			var footEnd = '<span class="common-page-next">下一页</span><span class="common-page-end">末页</span>';
			var footBody = "";
			//页数
			var number = Math.ceil(o.total / o.pagenumber);
			var flag = 0;
			//是否隐藏首页按钮
			var hideFrist = false;
			//是否隐藏最末页按钮
			var hideLast = false;
			for ( var int = 1; int <= number; int++) {
				var temp = "";
				if(int+splitnumber>=o.current || (o.current+o.shownumber>=number && number-int<o.shownumber)){
					if(int==o.current){
						temp = '<span class="common-page-no select">'+int+'</span>';
					}else{
						temp = '<span class="common-page-no">'+int+'</span>';
					}
					footBody += temp;
					flag++;
					if(int == 1){
						hideFrist = true;
					}
					if(int == number){
						hideLast = true;
					}
				};
				if(flag==o.shownumber){
					break;
				}
			}
			var foot = "<div class='float-right'>"+footStart+footBody+footEnd+"</div>";
			var last = $('<div class="common-page-foot"></div>').append(createString).append(foot);
			_this.append(last);
			//处理首页和下一页按钮是否隐藏
			hideButton(hideFrist,hideLast);
			bindEvent();
		}
		if(option.total==0){
			return;
		}
		goCurrent(option);
	},
	//判断中文长度
	getLength:function(strTemp){
    	var i,sum;  
    	sum=0;  
    	for(i=0;i<strTemp.length;i++) {  
    		if ((strTemp.charCodeAt(i)>=0) && (strTemp.charCodeAt(i)<=255)){
    			sum=sum+1;  
    		}else{
    			sum=sum+2;
    		}
    	}  
    	return sum;  
    },
});