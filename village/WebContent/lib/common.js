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
			$(this).val(value);
			$(this).addClass(className);
		});
		$(this).focus(function(){
			if($(this).hasClass(className)){
				$(this).val("");
				$(this).removeClass(className);
			}
		}).focusout(function(){
			if($.trim($(this).val())==""){
				var value = parseVal($(this).attr(attr));
				$(this).val(value);
				$(this).addClass(className);
			}
		});
	},
	searchInput: function(){
		$(this).wrap("<div class='ui-input-search'></div>");
	}
});