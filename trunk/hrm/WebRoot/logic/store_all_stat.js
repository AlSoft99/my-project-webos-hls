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
	var combo = comboBoxList.comboBoxSql("select paramscode,paramsname from ParamsList where typeid='STORE_STATUS'","仓库状态","store_status");
	combo.allowBlank = true;
	combo.emptyText = "默认为全状态统计";
	var where = "";
	var toolbar = new Ext.Toolbar({
		items:["-","开始:",
			new Ext.form.DateField({
				emptyText:"默认当天"
			}),
			"结束:",
			new Ext.form.DateField({
				emptyText:"默认当天"
			}),
			combo,
			{
				text:"查询",
				iconCls:"icon-grid",
				tooltip:'默认为当天查询',
				handler:function(event,mouse){
					if(toolbar.getComponent(2).getValue()!=""){
						curentStartDate = toolbar.getComponent(2).getValue().format("Y-m-d");
					}
					if(toolbar.getComponent(4).getValue()!=""){
						curentEndDate = toolbar.getComponent(4).getValue().format("Y-m-d");
					}
					
					var store_status = toolbar.getComponent(5).getValue();
					if(store_status!=""){
						where = " and c.status='"+store_status+"'"
					}else{
						where = "";
					}
					listenerEvent.loadGrid();
				}
			},"-"]
	});
    var store = new Ext.data.GroupingStore({
    	proxy: new Ext.data.HttpProxy({url:"queryInfoVo.do"}),
        reader: new Ext.data.JsonReader({
			totalProperty:"totalProperty",
			root:"root"
		},[
    		{name: "goodsid", type: 'string'},
    		{name: "goodsname", type: 'string'},
    		{name: "outnumber", type: 'int'}
 		]),
 		
        sortInfo: {field: 'outnumber', direction: 'DESC'}
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
	            xtype: 'numbercolumn',
	            header: '出仓数量',
	            dataIndex: 'outnumber',
	            summaryType: 'sum',
	            sortable: true
	        }
	        
	    ],
	    bbar: new Ext.PagingToolbar({
//            pageSize: 200,
            store: store,
            displayInfo: true,
            plugins: new Ext.ux.ProgressBarPager()
        })

    });
    
    var layout = new Ext.Panel({
        title: '货物报表',
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
    		store.baseParams.sql = "select new map(sum(a.goodsnumber) as outnumber,b.goodsname as goodsname) from StoreOutputList a,StoreList b,StoreOutputInfo c where a.goodsid=b.id and a.outid=c.id and a.updttime>'{1}' and a.updttime<'{2}' "+where+" group by a.goodsid,b.goodsname";
    		store.baseParams.startDate = curentStartDate;
    		store.baseParams.endDate = curentEndDate;
    		store.baseParams.action = "hql";
    		store.baseParams.type = "mapdate";
    		store.load({
		  		params:{  
		  			start:-1, 
		  			limit:-1
		  		}
		  	});
    	}
    }
    listenerEvent.loadGrid();
    layout.render("store_all_stat_div");
    grid_info.getSelectionModel().on('selectionchange', function(sm){
//        grid_info.removeBtn.setDisabled(sm.getCount() < 1);
    });
});


