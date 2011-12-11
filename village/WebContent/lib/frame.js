var include_file = {
	jqueryui: [
	    /*"lib/ui/css/ui-lightness/jquery-ui-1.8.16.custom.css",*/
	    "lib/ui/js/jquery-ui-1.8.16.custom.min.js"
	],
	plugin: [
     	"lib/plugin/jquery.lazyload.min.js",
     	/*"lib/plugin/jquery.combobox.js",*/
    	"lib/common.js"
    ],
    modules: [
        "modules/main.js"
	]
};
var frame = {
	//可动态加载css,js文件,支持同步和异步
	include: function(file,sync,loading) {
		//sync判断同步还是异步, true为同步, False为异步
		if(typeof(sync)=="undefined" || sync==null){
			sync = false;
		}
		//加载方式,true为jquery.getScript方式, false为document.write
		if(typeof(loading)=="undefined" || loading==null){
			loading = false;
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
            var url = "<" + tag + attr + link + "></" + tag + ">";
            if(!loading){
            	frame.write(url, sync);
            }else{
            	frame.script(name, sync);
            }
        }
    },
    write: function(url,sync){
    	if(!sync){
        	//异步加载
        	document.write(url);
        }else{
        	//同步加载
        	$(document).append(url);
        }
    },
    script: function(url,sync){
    	//sync判断同步还是异步, true为同步, False为异步
    	$.ajax({
    		url: url,
    		async: !sync,
    		dataType: 'script'
		});
		
    }
};
$(function(){
	
	frame.include(include_file.plugin,true,true);
	frame.include(include_file.jqueryui,true,true);
	frame.include(include_file.modules,true,true);
});