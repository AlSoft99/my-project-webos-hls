
$(function(){
	/*var th = {'id':'ID','typename':'类型','title':'标题','love':'喜爱数','brower':'浏览数','commentsum':"评论数",'{template}':function(o){
		console.log(o);
		if(o=="thead"){
			return "操作";
		}else{
			var button = '<a class="grid-table-link" href="#">修改</a>&nbsp;<a class="grid-table-link" href="#">删除</a>';
			return button;
		}
		
	}};*/
	var th = [{id:"id",text:"ID",width:20,align:"center"},{id:"typename",text:"类型",width:70,align:"center"},{id:"title",text:"标题",width:300},{id:"love",text:"喜爱数",width:70,align:"center"},{id:"brower",text:"浏览数",width:70,align:"center"},{id:"commentsum",text:"评论数",width:70,align:"center"},
	          {id:'{template}',align:"center",callback:function(o,data){
	        	  if(o=="thead"){
	        		  return "操作";
	        	  }else{
	        		  var button = '<a articleid="'+data.id+'" class="grid-table-link edit" href="#">修改</a>&nbsp;<a articleid="'+data.id+'" class="grid-table-link delete" href="#">删除</a>';
	        		  return button;
	        	  }
	          }}];
	var op = {start:0,row:10,sql:"SQL7",logout:false,where:" and a.userid='"+main.session.id+"'"};
	$("#article-query-table").queryData(th,op,function(_this){
		_this.find("a.edit").click(function(){
			//$("#article-tabs").tabs("enable");
			//$( "#article-tabs" ).tabs( "option", "enabled", true );
			$("#article-tabs").tabs( "option", "disabled", false );
			$("#article-tabs").tabs( "option", "selected", 2 );
			
			article.currentArticleid = $(this).attr("articleid");
			return false;
		});
		_this.find("a.delete").click(function(){
			
			return false;
		});
	});
});