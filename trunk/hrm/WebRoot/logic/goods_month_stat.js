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
	var toolbar = new Ext.Toolbar({
		items:["-",
			{
	            text: '货物统计',
	            tooltip: '点击货物将统计,再次点击将取消统计',
	            iconCls:"icon-params",
	            handler: function(){
	            	summary.toggleSummaries();
	            }
	        },"-",new Ext.form.DateField({
				emptyText:"默认当天"
			}),{
				text:"查询",
				iconCls:"icon-grid",
				tooltip:'默认为当天查询',
				handler:function(event,mouse){
					if(toolbar.getComponent(3).getValue()!=""){
						curentStartDate = toolbar.getComponent(3).getValue().format("Y-m-d");
					}else{
						curentStartDate = (new Date()).clearTime().format("Y-m-d");
					}
					listenerEvent.loadGrid();
				}
			}
	    ]
	});
    var store = new Ext.data.GroupingStore({
    	proxy: new Ext.data.HttpProxy({
    		url:"monthStatVo.do"
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
    		{name: "perprice", type: 'int'},
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
	            header: '仓库编号',
	            dataIndex: 'username',
	            width:150,
	            sortable: true,
	            summaryType: 'count',
	            summaryRenderer: function(v, params, data){
	                return '(仓库编号1 个)';
	            }
	        },{
	            xtype: 'numbercolumn',
	            header: '月初数量',
	            dataIndex: 'initnumber',
	            summaryType: 'sum',
	            sortable: true
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
	            header: '货物总数量',
	            dataIndex: 'allnumber',
	            summaryType: 'sum',
	            sortable: true
	        },{
	            xtype: 'numbercolumn',
	            header: '销售数量',
	            dataIndex: 'outnumber',
	            summaryType: 'sum',
	            sortable: true
	        },{
	            xtype: 'numbercolumn',
	            header: '退货数量',
	            dataIndex: 'returnnumber',
	            summaryType: 'sum',
	            sortable: true
	        },{
	            xtype: 'numbercolumn',
	            header: '月末数量',
	            dataIndex: 'lastnumber',
	            summaryType: 'sum',
	            sortable: true
	        },{
	            xtype: 'numbercolumn',
	            header: '货物单价',
	            dataIndex: 'perprice',
	            format:"￥0,0.00",
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
        title: '货物月销售报表',
        layout: 'border',
        layoutConfig: {
            columns: 1
        },
        autoWidth:true,
        height: Ext.getBody().getHeight()-148-20, 
        items: [grid_info]
    });
    var listenerEvent = { 
    	loadGrid : function(){
    		var where = "";
    		store.load({
		  		params:{
		  			start:-1, 
		  			limit:-1,
		  			action:"hql",
					type:"map",
					sql:"select new map(a.id.goodsid as goodsid,d.goodsname as goodsname,a.id.userlist as outuser,a.id.userlist as username,a.innumber as storenumber,a.startnumber as initnumber,a.innumber as innumber,a.outnumber as outnumber,a.startnumber+a.innumber as allnumber,a.lastnumber as lastnumber,a.lastnumber*d.price as price,d.price as perprice,b.id as typeid,b.typename as typename,a.returnnumber as returnnumber) from GoodsMonthStat a,StoreType b,StoreList d where startdate<'"+curentStartDate+"' and enddate>'"+curentStartDate+"' and d.typeid=b.id and a.id.goodsid=d.id and a.id.userlist=(select id.userlist from OutUserList where id.outuser='"+Ext.getDom("user_id").value+"' and id.tag='1')"
		  		}
		  	});
    	}
    }
//    store.load({params:{start:-1, limit:-1,outuser:Ext.getDom("user_id").value}});
    listenerEvent.loadGrid();
    layout.render("goods_month_stat_div");
    grid_info.getSelectionModel().on('selectionchange', function(sm){
//        grid_info.removeBtn.setDisabled(sm.getCount() < 1);
    });
});


