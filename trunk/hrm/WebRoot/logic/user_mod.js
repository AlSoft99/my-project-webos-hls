Ext.onReady(function(){
  	var cm = new Ext.grid.ColumnModel([
    	{header:"编号",dataIndex:"id"},
    	{header:"名称",dataIndex:"name"},
    	{header:"描述",dataIndex:"descn"}
  	]);
  	var data = [
    	['1','name1','desc1'],
    	['2','name2','desc2'],
    	['3','name3','desc3'],
    	['4','name4','desc4'],
    	['5','name5','desc5']
  	];
  	var ds = new Ext.data.Store({
    	proxy: new Ext.data.MemoryProxy(data),
    	reader: new Ext.data.ArrayReader({},[
      		{name:"id"},
      		{name:"name"},
      		{name:"descn"}
    	])
  	});
  	ds.load();
  	var grid = new Ext.grid.GridPanel({
    	el:"user_mod_table",
    	ds:ds,
    	cm:cm,
    	autoHeight:true,
    	title:'Array Grid',
    	// 自动填充表格宽度
    	viewConfig: {
      		forceFit: true
    	}
  	});
  	grid.render();
});
