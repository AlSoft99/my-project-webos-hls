var plugin = [
	"lib/plugin/jquery.lazyload.min.js",
	"js/common.js",
];
var modules = [
];
var frame = {
	//可动态加载css,js文件,支持同步和异步
	include: function(file,sync) {
		//sync判断同步还是异步, true为同步, False为异步
		if(typeof(sync)=="undefined" || sync==null){
			sync = false;
		}
        var files = typeof file == "string" ? [file] : file;
        for (var i = 0; i < files.length; i++) {
            var name = files[i].replace(/^\s|\s$/g, "");
            var att = name.split('.');
            var ext = att[att.length - 1].toLowerCase();
            var isCSS = ext == "css";
            var tag = isCSS ? "link" : "script";
            var attr = isCSS ? " type='text/css' rel='stylesheet' " : " language='javascript' type='text/javascript' ";
            var link = (isCSS ? "href" : "src") + "='" + name + "'";
            var name = "<" + tag + attr + link + "></" + tag + ">";
            if(!sync){
            	//异步加载
            	document.write(name);
            }else{
            	//同步加载
            	$(document).append(name);
            }
        }
    }
}
$(function(){
	frame.include(plugin,true);
});