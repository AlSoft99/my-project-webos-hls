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
	var comboBoxType = comboBoxList.comboBoxSql("select a.id,a.typename from MaterialType a", "", "");
	comboBoxType.allowBlank = true;
	comboBoxType.emptyText = "过滤原材料种类";
	var toolbar = new Ext.Toolbar({
		items:["-",
			{
	            text: '货物统计',
	            tooltip: '点击货物将统计,再次点击将取消统计',
	            iconCls:"icon-params",
	            handler: function(){
	            	summary.toggleSummaries();
	            }
	        },
	        "-",
	        comboBoxDate,
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
    		{name: "materialname", type: 'string'},
    		{name: "unitname", type: 'string'},
    		{name: "storenumber", type: 'int'},
    		{name: "typeid", type: 'string'},
    		{name: "typename", type: 'string'},
    		{name: "sum", type: 'float'},
    		{name: "cost", type: 'float'},
    		{name: "input", type: 'float'},
    		{name: "spend", type: 'float'},
    		{name: "storedate", type: 'string'},
    		{name: "initsum", type: 'float'},
    		{name: "output", type: 'float'},
    		{name: "monthinit", type: 'float'},
    		
    		{name: "updttime", type: 'string'}
 		]),
 		baseParams:{
 			startDate:curentStartDate,
 			endDate:curentEndDate
 		},
 		groupField: 'typename'
        /*,sortInfo: {field: 'lastnumber', direction: 'DESC'}*/
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
	            },
	            renderer: function(value,data,o){
	            	var unitname = o.data.unitname;
	            	var temp = "";
	            	if(typeof(unitname)!="undefined"){
	            		temp = ""+unitname+"";
	            	}
	            	return value+" / <font color='blue'>"+temp+"</font>";
	            }
	        },{
	        	xtype: 'numbercolumn',
	            header: '原材料月进货量',
	            dataIndex: 'input',
	            summaryType: 'sum',
	            sortable: true,
	            renderer:filterUnit
	        },{
	        	xtype: 'numbercolumn',
	            header: '原材料月初实际库存',
	            dataIndex: 'monthinit',
	            summaryType: 'sum',
	            sortable: true,
	            renderer:filterUnit
	        },{
	        	xtype: 'numbercolumn',
	            header: '原材料消耗量',
	            dataIndex: 'output',
	            summaryType: 'sum',
	            sortable: true,
	            renderer:filterUnit
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
	            header: '损耗率%',
	            dataIndex: 'loss',
	            summaryType: 'sum',
	            sortable: true,
	            summaryType: 'count',
	            summaryRenderer: function(v, params, data){
	                return "损耗率统计";
	            },
	            renderer:function(v,params,data){
	            	var lossrate = (data.data.sum-data.data.initsum)/(data.data.input+data.data.monthinit);
	            	if(isNaN(lossrate)){
	            		lossrate = 0;
	            	}
	            	if(lossrate<0 || lossrate>0.5){
	            		return "<font color='red'>"+Ext.util.Format.number(lossrate*100,"00.00")+"%</font>";
	            	}else{
	            		return "<font color='green'>"+Ext.util.Format.number(lossrate*100,"00.00")+"%</font>";
	            	}
	            }
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
	            header: '原材料单价',
	            dataIndex: 'cost',
	            format:"￥0,0.00",
	            summaryType: 'sum',
	            sortable: true
	        },{
	        	xtype: 'numbercolumn',
	            header: '原材料月消耗总价',
	            dataIndex: 'spend',
	            summaryType: 'sum',
	            sortable: true,
	            format:"￥0,0.00",
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
    function filterNumber(value,data,o){
    	var unitname = o.data.unitname;
    	var temp = "";
    	if(typeof(unitname)!="undefined"){
    		temp = ""+unitname+"";
    	}
    	
  		if(value>=0){
    		return "<font color='green'>"+Ext.util.Format.number(value,"0,0.00")+"</font>";
    	}else{
    		return "<font color='red'>"+Ext.util.Format.number(value,"0,0.00")+"</font>";
    	}
  	}
    function filterUnit(value,data,o){
    	var unitname = o.data.unitname;
    	var temp = "";
    	if(typeof(unitname)!="undefined"){
    		temp = ""+unitname+"";
    	}
    	return value;
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
    		
    		if(comboBoxDate.getValue()!=""){
    			currentDate = comboBoxDate.getValue();
    		}else{
    			currentDate = (new Date()).format('Ym');
    		}
    		var where = "";
    		if(comboBoxType.getValue()!=""){
    			where = " and b.id='"+comboBoxType.getValue()+"' ";
    		}
    		var temp = (new Date()).add(Date.MONTH,-1).format('Ym');
    		var parseDate = Date.parseDate(currentDate+'01','Ymd');
    		var startDate = parseDate.format('Y-m-d');
    		var endDate = parseDate.add(Date.MONTH,1).format('Y-m-d');
    		var preCurrentDate = parseDate.add(Date.MONTH,-1).format('Ym');
    		store.baseParams.sql = "SQL-2";
    		store.baseParams.action = "sql";
    		store.baseParams.type = "map";
    		store.baseParams["{0}"] = startDate;
    		store.baseParams["{1}"] = endDate;
    		store.baseParams["{2}"] = preCurrentDate;
    		store.baseParams["{3}"] = currentDate;
    		store.baseParams["{4}"] = where;
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
    	}
    }
//    store.load({params:{start:-1, limit:-1,outuser:Ext.getDom("user_id").value}});
    listenerEvent.loadGrid();
    layout.render("menu_material_stat_div");
    grid_info.getSelectionModel().on('selectionchange', function(sm){
//        grid_info.removeBtn.setDisabled(sm.getCount() < 1);
    });
});


