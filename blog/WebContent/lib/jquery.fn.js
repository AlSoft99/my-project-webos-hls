define(function(require, exports, module){ 
	var $ = require("jquery");
	$.fn.extend({
		test: function(){
			alert(1234);
		}
	});
});