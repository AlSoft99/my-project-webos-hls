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
	var roleList = comboBoxList.roleList;
	var userList = comboBoxList.userList;
	userList.allowBlank = true;
	userList.emptyText = "不选择为所有仓库统计";
	roleList.on("select",function(obj,option){
		var roleCode = option.data.value;
		userList.getStore().load({
			params:{option:"userInfo",change:roleCode}
		});
		userList.setValue("");
	});
	var toolbar = new Ext.Toolbar({
		items:["-","开始:",
			new Ext.form.DateField({
				emptyText:"默认当天"
			}),
			"结束:",
			new Ext.form.DateField({
				emptyText:"默认当天"
			}),
			roleList,
			userList,{
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
    		{name: "outnumber", type: 'int'},
    		{name: "returnnumber", type: 'int'},
    		{name: "actualnumber", type: 'int'}
 		]),
        sortInfo: {field: 'actualnumber', direction: 'DESC'}
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
	            header: '实际销售数量',
	            dataIndex: 'actualnumber',
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
    		var where = "";
    		if(userList.getValue()!=""){
    			where = " and a.updtuser='"+userList.getValue()+"' "
    		}
    		store.baseParams.sql = "select new map(a.goodsid as goodsid,sum(a.goodsnumber) as outnumber,sum(a.returnnumber) as returnnumber,sum(a.goodsnumber)-sum(a.returnnumber) as actualnumber,b.goodsname as goodsname) from GoodsOutputList a,StoreList b where a.goodsid=b.id "+where+" and a.updttime>'{1}' and a.updttime<'{2}' group by a.goodsid,b.goodsname";
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
    };
    listenerEvent.loadGrid();
    layout.render("day_goods_all_stat_div");
    grid_info.getSelectionModel().on('selectionchange', function(sm){
//        grid_info.removeBtn.setDisabled(sm.getCount() < 1);
    });
});


