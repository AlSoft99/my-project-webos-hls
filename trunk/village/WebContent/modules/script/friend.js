$(function(){
	$("#friend-tabs").tabs({ spinner:'Retrieving data...'});
	$("#search_friend").defaultMsg();
	$("#search_friend").searchInput();
	$(".combobox-frame").combobox({
		list:[{key:'student',value:'现在同事'},{key:'student',value:'现在同事'},{key:'student',value:'现在同事',select:true},{key:'student',value:'现在同事'},{key:'student',value:'现在同事'}],
		title:"修改分组"
	},function(){
		//选择别表项的点击事件
	});
});