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
	var currentDate = (new Date()).format('Ym');
	var comboBoxDate = new Ext.form.ComboBox({
  		name: "month",
  		fieldLabel: '月份',
	  	store: new Ext.data.Store({
            proxy:new Ext.data.HttpProxy({url:"optionServlet.do?option=month"}),
            autoLoad:true,
			reader:new Ext.data.ArrayReader({},[
			    {name:"value"},
			    {name:"text"}
			])
        }),
        dateFormat:'Y-m',
        width:150,
	  	emptyText: "默认为当月",
	  	mode: "local",
	  	forceSelection :true,
	  	triggerAction: "all",
	  	valueField: "value",
	  	displayField: "text",
	  	hiddenName: "goodstype"
	});
	var comboBoxType = comboBoxList.comboBoxSql("select a.id,a.typename from MaterialTypeKtv a ", "", "");
	comboBoxType.allowBlank = true;
	comboBoxType.emptyText = "过滤酒水种类";
	var toolbar = new Ext.Toolbar({
		items:["-",
			{
	            text: '酒水统计',
	            tooltip: '点击酒水将统计,再次点击将取消统计',
	            iconCls:"icon-params",
	            handler: function(){
	            	summary.toggleSummaries();
	            }
	        },
	        "-",
	        comboBoxType,
	        {
				text:"查询",
				iconCls:"icon-grid",
				tooltip:'默认为当天查询',
				handler:function(event,mouse){
					
					listenerEvent.loadGrid();
				}
			}
	    ]
	});
    var store = new Ext.data.GroupingStore({
    	proxy: new Ext.data.HttpProxy({
    		url:"queryInfoVo.do"
    	}),
        reader: new Ext.data.JsonReader({
			totalProperty:"totalProperty",
			root:"root"
		},[
		   	{name: "id", type: 'string'},
    		{name: "typeid", type: 'string'},
    		{name: "materialname", type: 'string'},
    		{name: "typename", type: 'string'},
    		{name: "count", type: 'float'},
    		{name: "savecount", type: 'float'},
    		{name: "takeoncount", type: 'float'},
    		{name: "takeovercount", type: 'float'},
    		{name: "takeallcount", type: 'float'}
 		]),
 		baseParams:{
 			
 		},
 		groupField: 'typename'
        /*,sortInfo: {field: 'lastnumber', direction: 'DESC'}*/
    });
    
    var grid_info = new Ext.grid.GridPanel({
        store: store,
        columnWidth:.8,
        margins: '0 5 5 5',
        height:Ext.getBody().getHeight()-190,
        //autoHeight: true,
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
	            header: '酒水名称',
	            dataIndex: 'materialname',
	            sortable: true,
	            summaryType: 'count',
	            summaryRenderer: function(v, params, data){
	                return ((v === 0 || v > 1) ? '(酒水' + v +' 个)' : '(酒水1 个)');
	            }
	        },{
	        	xtype: 'numbercolumn',
	            header: '卡已过期入库',
	            dataIndex: 'count',
	            summaryType: 'sum',
	            sortable: true
	        },{
	        	xtype: 'numbercolumn',
	            header: '卡未到期寄放',
	            dataIndex: 'savecount',
	            summaryType: 'sum',
	            sortable: true
	        },{
	        	xtype: 'numbercolumn',
	            header: '正常还卡取货',
	            dataIndex: 'takeoncount',
	            summaryType: 'sum',
	            sortable: true
	        },{
	        	xtype: 'numbercolumn',
	            header: '过期还卡取货',
	            dataIndex: 'takeovercount',
	            summaryType: 'sum',
	            sortable: true
	        },{
	        	xtype: 'numbercolumn',
	            header: '总取货数',
	            dataIndex: 'takeallcount',
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
    var property = new Ext.grid.PropertyGrid({
        title: '酒水卡统计',
        closable: false,
        autoHeight: true,
        columnWidth:.2
    });
    property.isEditor = false;
    property.clicksToEdit = -1;
    
    var layout = new Ext.Panel({
        title: '酒水寄放报表',
        layout: 'column',
        //layout: 'border',
        layoutConfig: {
            columns: 1
        },
        //height: Ext.getBody().getHeight()-148-20, 
        items: [grid_info,property]
    });
    var listenerEvent = { 
    	loadGrid : function(){
    		
    		var where = "";
    		if(comboBoxType.getValue()!=""){
    			where = " and b.id='"+comboBoxType.getValue()+"' ";
    		}
    		
    		store.baseParams.sql = "SQL-5";
    		store.baseParams.action = "sql";
    		store.baseParams.type = "map";
    		store.baseParams["{0}"] = where;
    		store.load({
		  		params:{
		  			start:-1, 
		  			limit:-1/*,
		  			action:"sql",
					type:"map",
					sql:"SQL-2",
					"{0}":startDate,
					"{1}":endDate,
					"{2}":temp,
					"{3}":currentDate*/
		  		}
		  	});
    		
    		properties.ajax("select b.paramsname,count(*) from KtvStayInfo a,ParamsList b where a.state=b.paramscode and b.typeid='KTV_STATE' group by a.state", function (o) {
    	        var json = eval("(" + o.responseText + ")");
    	        property.setSource(json);
    	    });
    	}
    }
//    store.load({params:{start:-1, limit:-1,outuser:Ext.getDom("user_id").value}});
    listenerEvent.loadGrid();
    layout.render("ktv_material_stat_div");
    grid_info.getSelectionModel().on('selectionchange', function(sm){
//        grid_info.removeBtn.setDisabled(sm.getCount() < 1);
    });
});


