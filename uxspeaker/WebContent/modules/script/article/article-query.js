$(function(){
	var th = {'id':'ID','typename':'类型','title':'标题','love':'喜爱数','brower':'浏览数','commentsum':"评论数",'{template}':function(o){
		console.log(o);
		if(o=="thead"){
			return "操作";
		}else{
			var button = '<a class="grid-table-link" href="#">修改</a>&nbsp;<a class="grid-table-link" href="#">删除</a>';
			return button;
		}
		
	}};
	var op = {start:0,row:10,sql:"SQL7",logout:false,where:" and a.userid='"+main.session.id+"'"};
	$("#article-query-table").queryData(th,op);
});