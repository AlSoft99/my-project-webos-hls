var script = ["lib/plugin/jquery.lazyload.min.js"];
$(function(){
	/* 
	 *动态导入JS文件 
	 **/  
	function jsImport(path) {  
	    var i;  
	    var ss = document.getElementsByTagName("script");  
	    for (i = 0; i < ss.length; i++) {  
	        if (ss[i].src && ss[i].src.indexOf(path) != -1) {  
	            return;  
	        }  
	    }  
	    var s = document.createElement("script");  
	    s.type = "text/javascript";  
	    s.src = path;  
	    var head = document.getElementsByTagName("head")[0];  
	    head.appendChild(s);  
	}
	for ( var int = 0; int < script.length; int++) {
		jsImport(script[int]);
	}
	
	
});