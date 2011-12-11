$.extend({
	parseWindowUrl: function(url){
		return url.substring(url.indexOf("#")+1)+".html";
	}
});
$.fn.extend({
	defaultMsg: function(){
		var attr = "defaultMsg";
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
	}
});