$.extend({
	parseWindowUrl: function(url){
		return url.substring(url.indexOf("#")+1)+".html";
	}
});