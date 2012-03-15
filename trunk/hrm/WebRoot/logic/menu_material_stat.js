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
		   	{name: "id", type: 'string'},
    		{name: "materialname", type: 'string'},
    		{name: "unitname", type: 'string'},
    		{name: "storenumber", type: 'int'},
    		{name: "typeid", type: 'string'},
    		{name: "typename", type: 'string'},
    		{name: "sum", type: 'float'},
    		{name: "input", type: 'float'},
    		{name: "storedate", type: 'string'},
    		{name: "initsum", type: 'float'},
    		{name: "output", type: 'float'},
    		{name: "updttime", type: 'string'}
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
	        new Ext.grid.RowNumberer(),{
	            id: 'materialname',
	            header: '原材料名称',
	            dataIndex: 'materialname',
	            sortable: true,
	            summaryType: 'count',
	            summaryRenderer: function(v, params, data){
	                return ((v === 0 || v > 1) ? '(原材料种类' + v +' 个)' : '(原材料种类1 个)');
	            }
	        },{
	            header: '月末实际库存',
	            dataIndex: 'initsum',
	            summaryType: 'sum',
	            sortable: true,
	            renderer:filterNumber
	        },{
	            header: '月末理论库存',
	            dataIndex: 'sum',
	            summaryType: 'sum',
	            sortable: true,
	            renderer:filterNumber
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
	            header: '原材料月进货量',
	            dataIndex: 'input',
	            summaryType: 'sum',
	            sortable: true
	        },{
	            xtype: 'numbercolumn',
	            header: '原材料消耗量',
	            dataIndex: 'output',
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
    function filterNumber(value){
  		if(value>=0){
    		return "<font color='green'>"+Ext.util.Format.number(value,"0,0.00")+"</font>";
    	}else{
    		return "<font color='red'>"+Ext.util.Format.number(value,"0,0.00")+"</font>";
    	}
  	}
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
		  			action:"sql",
					type:"map",
					sql:"SQL-2",
					"{0}":"2012-03-01",
					"{1}":"2012-04-01",
					"{2}":"201202",
					"{3}":"MT13316265523972552688483"
		  		}
		  	});
    	}
    }
//    store.load({params:{start:-1, limit:-1,outuser:Ext.getDom("user_id").value}});
    listenerEvent.loadGrid();
    layout.render("menu_material_stat_div");
    grid_info.getSelectionModel().on('selectionchange', function(sm){
//        grid_info.removeBtn.setDisabled(sm.getCount() < 1);
    });
});


