Ext.onReady(function(){
	
    Ext.QuickTips.init();
    /**
	 * 最新的福利参数Json对象
	 */
	var currentWalfareParams;
	/**
	 * 表格统计
	 */
    var summary = new Ext.ux.grid.GroupSummary();
    /**
     * 表格构造
     */
    var curentStartDate = (new Date()).clearTime().format("Y-m-d");
	var curentEndDate = (new Date()).clearTime().format("Y-m-d");
	/*var comboBoxUser = comboBoxList.comboBoxSql("select distinct a.outuser,b.userName from StoreType a,UserInfo b where a.outuser=b.userId","货物人","outuser");
	comboBoxUser.allowBlank = true;
	comboBoxUser.emptyText = "默认为全部货物统计";
	var toolbar = new Ext.Toolbar({
		items:["-",
			{
	            text: '货物统计',
	            tooltip: '点击货物将统计,再次点击将取消统计',
	            iconCls:"icon-params",
	            handler: function(){
	            	summary.toggleSummaries();
	            }
	        },"-","销售人:",comboBoxUser,{
	        	text:"查询",
	        	tooltip: '点击统计该用户',
	            iconCls:"icon-grid",
	            handler: function(){
	            	listenerEvent.loadGrid();
	            }
	        },"-"
	    ]
	});*/
	var comboBoxUser = comboBoxList.comboBoxSql("select distinct a.id.userlist,a.id.userlist from OutUserList a where a.id.tag='2'","货物人","userlist");
	comboBoxUser.allowBlank = false;
	comboBoxUser.emptyText = "请选择仓库进行统计";
	comboBoxUser.on("change",function(a,b,c){
//		Ext.getCmp("store_data_all_stat_query").setDisabled(b=="");
	});
	var toolbar = new Ext.Toolbar({
		items:["-",
			{
	            text: '货物统计',
	            tooltip: '点击货物将统计,再次点击将取消统计',
	            iconCls:"icon-params",
	            handler: function(){
	            	summary.toggleSummaries();
	            }
	        },"-","仓库编号:",comboBoxUser,
	        {
	        	text:"查询",
	        	id:"store_data_all_stat_query",
//	        	disabled:true,
	        	tooltip: '点击统计该仓库',
	            iconCls:"icon-grid",
	            handler: function(){
	            	listenerEvent.loadGrid();
	            }
	        },"-"]
	});
    var store = new Ext.data.GroupingStore({
    	proxy: new Ext.data.HttpProxy({
    		url:"queryInfoVo.do"
    	}),
        reader: new Ext.data.JsonReader({
			totalProperty:"totalProperty",
			root:"root"
		},[
    		{name: "goodsid", type: 'string'},
    		{name: "goodsname", type: 'string'},
    		{name: "username", type: 'string'},
    		{name: "storenumber", type: 'int'},
    		{name: "typeid", type: 'string'},
    		{name: "typename", type: 'string'},
    		{name: "initnumber", type: 'int'},
    		{name: "allnumber", type: 'int'},
    		{name: "outnumber", type: 'int'},
    		{name: "returnnumber", type: 'int'},
    		{name: "lastnumber", type: 'int'},
    		{name: "price", type: 'int'}
 		]),
 		baseParams:{
 			startDate:curentStartDate,
 			endDate:curentEndDate
 		},
 		groupField: 'typename',
        sortInfo: {field: 'lastnumber', direction: 'DESC'}
    });
    
    var grid_info = new Ext.grid.GridPanel({
        store: store,
        autoWidth:true,
        region:'center',
        margins: '0 5 5 5',
        autoExpandColumn: 'goodsname',
        plugins: [summary],
        view: new Ext.grid.GroupingView({
            markDirty: false

        }),
        view: new Ext.grid.GroupingView({
            forceFit: true,
            showGroupName: false,
            enableNoGroups: false,
			enableGroupingMenu: false,
            hideGroupedColumn: true
        }),

		tbar : toolbar,
        columns: [
	        new Ext.grid.RowNumberer(),
	        {
	            id: 'goodsid',
	            header: '货物ID',
	            dataIndex: 'goodsid',
	            sortable: true,
	            summaryType: 'count',
	            summaryRenderer: function(v, params, data){
	                return ((v === 0 || v > 1) ? '(货物种类' + v +' 名)' : '(货物种类1 个)');
	            },
	            hidden:true
	        },{
	            id: 'goodsname',
	            header: '货物名称',
	            dataIndex: 'goodsname',
	            sortable: true,
	            summaryType: 'count',
	            summaryRenderer: function(v, params, data){
	                return ((v === 0 || v > 1) ? '(货物种类' + v +' 个)' : '(货物种类1 个)');
	            }
	        },{
	            id: 'username',
	            header: '货物人',
	            dataIndex: 'username',
	            sortable: true,
	            summaryType: 'count',
	            summaryRenderer: function(v, params, data){
	                return '(货物人1 个)';
	            }
	        },{
	            xtype: 'numbercolumn',
	            header: '进仓数量',
	            dataIndex: 'storenumber',
	            summaryType: 'sum',
	            sortable: true
	        },{
	            id: 'typeid',
	            header: '种类ID',
	            dataIndex: 'typeid',
	            sortable: true,
	            summaryType: 'count',
	            summaryRenderer: function(v, params, data){
	                return ((v === 0 || v > 1) ? '(种类' + v +' 个)' : '(种类1 个)');
	            },
	            hidden:true
	        },{
	            id: 'typename',
	            header: '种类名称',
	            dataIndex: 'typename',
	            sortable: true,
	            summaryType: 'count',
	            summaryRenderer: function(v, params, data){
	                return ((v === 0 || v > 1) ? '(种类' + v +' 个)' : '(种类1 个)');
	            }
	        },{
	            xtype: 'numbercolumn',
	            header: '初始数量',
	            dataIndex: 'initnumber',
	            summaryType: 'sum',
	            sortable: true
	        },{
	            xtype: 'numbercolumn',
	            header: '货物总数量',
	            dataIndex: 'allnumber',
	            summaryType: 'sum',
	            sortable: true
	        },{
	            xtype: 'numbercolumn',
	            header: '出仓数量',
	            dataIndex: 'outnumber',
	            summaryType: 'sum',
	            sortable: true
	        },{
	            xtype: 'numbercolumn',
	            header: '剩余数量',
	            dataIndex: 'lastnumber',
	            summaryType: 'sum',
	            sortable: true
	        },{
	            xtype: 'numbercolumn',
	            header: '预计价格',
	            format:"￥0,0.00",
	            dataIndex: 'price',
	            summaryType: 'sum',
	            sortable: true
	        }
	        /**
	        	 * {name: "goodsid", type: 'string'},
    		{name: "goodsname", type: 'string'},
    		{name: "storenumber", type: 'int'},
    		{name: "typeid", type: 'string'},
    		{name: "typename", type: 'string'},
    		{name: "initnumber", type: 'int'},
    		{name: "allnumber", type: 'int'},
    		{name: "outnumber", type: 'int'},
    		{name: "returnnumber", type: 'int'},
    		{name: "lastnumber", type: 'int'}
	        	 */
	        
	    ],
	    bbar: new Ext.PagingToolbar({
//            pageSize: 10,
            store: store,
            displayInfo: true,
            plugins: new Ext.ux.ProgressBarPager()
        })

    });
    
    var layout = new Ext.Panel({
        title: '仓库数据统计',
        layout: 'border',
        layoutConfig: {
            columns: 1
        },
        autoWidth:true,
        height: Ext.getBody().getHeight()-148-20, 
        items: [grid_info]
    });
    var listenerEvent = {
    	loadGrid:function(){
    		var where = "";
    		if(comboBoxUser.getValue()!=""){
    			where = " and b.storelist='"+comboBoxUser.getValue()+"'";
    		}
    		store.load({
		  		params:{
		  			start:-1, 
		  			limit:-1,
		  			action:"hql",
					type:"map",
					sql:"select new map(a.id as goodsid,a.goodsname as goodsname,b.storelist as outuser,b.storelist as username,a.innumber as storenumber,a.initnumber as initnumber,a.innumber as innumber,a.outnumber as outnumber,a.initnumber+a.innumber as allnumber,a.initnumber+a.innumber-a.outnumber as lastnumber,(a.initnumber+a.innumber-a.outnumber)*a.price as price,b.id as typeid,b.typename as typename) from StoreList a,StoreType b where a.typeid=b.id "+where
		  		}
		  	});
    	}
    }
//    store.load({params:{start:-1, limit:-1,outuser:Ext.getDom("user_id").value}});
    listenerEvent.loadGrid();
    layout.render("store_data_all_stat_div");
    grid_info.getSelectionModel().on('selectionchange', function(sm){
//        grid_info.removeBtn.setDisabled(sm.getCount() < 1);
    });
});


