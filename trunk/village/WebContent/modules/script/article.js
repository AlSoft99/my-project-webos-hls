
/*KindEditor.ready(function(K) {
	article.editor = K.create('textarea[name="article"]', {
		allowFileManager : true
	});
});*/
var article = {
	editor : "",
	basePath : '/village/lib/kindeditor/',
	initKindEditor: function(){
		$.getScript('lib/kindeditor/kindeditor-min.js', function() {
			KindEditor.basePath = article.basePath;
			article.editor = KindEditor.create('textarea[name="articlecontent"]');
		});
	}
};

$(function(){
	article.initKindEditor();
	$("input[type=button],button").button(); 
	$("textarea, input[type=text]").defaultMsg();
	$("#articledate").datepicker();
	$("#articletype").createCombobox("#articletype");
});