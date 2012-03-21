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
	var toolbar = new Ext.Toolbar({
		items:["-",
			{
	            text: '货物统计',
	            tooltip: '点击货物将统计,再次点击将取消统计',
	            iconCls:"icon-params",
	            handler: function(){
	            	summary.toggleSummaries();
	            }
	        },"-",comboBoxDate,{
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
    		{name: "footname", type: 'string'},
    		{name: "lossmaterialsum", type: 'float'},
    		{name: "materialcostsum", type: 'float'},
    		{name: "sellpay", type: 'float'},
    		{name: "lossmaterialcostsum", type: 'float'},
    		{name: "sellamount", type: 'float'},
    		{name: "materialcost", type: 'float'},
    		{name: "lossamount", type: 'float'},
    		{name: "materialamount", type: 'float'},
    		{name: "materialsum", type: 'float'}
 		]),
 		baseParams:{
 			startDate:curentStartDate,
 			endDate:curentEndDate
 		},
 		groupField: 'footname'
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
	            header: '菜品销售总价格',
	            dataIndex: 'sellpay',
	            summaryType: 'sum',
	            sortable: true,
	            format:"￥0,0.00",
	            hidden:true,
	            renderer:filterNumber
	        },{
	            id: 'footname',
	            header: '菜品名称',
	            dataIndex: 'footname',
	            sortable: true,
	            summaryType: 'count',
	            summaryRenderer: function(v, params, data){
	                return ((v === 0 || v > 1) ? '(原材料' + v +' 个)' : '(原材料1 个)');
	            },
	            renderer: function(value,data,o){
	            	var unitname = o.data.unitname;
	            	var temp = "";
	            	if(typeof(unitname)!="undefined"){
	            		temp = ""+unitname+"";
	            	}
	            	return value+" 销售量:<font color='red' style='font-weight:normal;line-height:10px;'>"+o.data.sellamount+"</font>&nbsp;&nbsp;&nbsp;&nbsp;月销售:<font color='red' style='font-weight:normal;line-height:10px;'>"+Ext.util.Format.number(o.data.sellpay,"￥0,0.00")+"</font>";
	            }
	        },{
	            header: '原材料',
	            dataIndex: 'materialname',
	            summaryType: 'sum',
	            sortable: true,
	            summaryType: 'count',
	            summaryRenderer: function(v, params, data){
	                return ((v === 0 || v > 1) ? '(原材料' + v +' 个)' : '(原材料1 个)');
	            }
	        },{
	        	xtype: 'numbercolumn',
	            header: '原材料单价',
	            dataIndex: 'materialcost',
	            summaryType: 'sum',
	            sortable: true,
	            format:"￥0,0.00",
	            renderer:filterUnit
	        },{
	            header: '销售原材料量',
	            dataIndex: 'materialsum',
	            summaryType: 'sum',
	            sortable: true,
	            renderer:filterNumber
	        },{
	        	xtype: 'numbercolumn',
	            header: '销售原材料总价格',
	            dataIndex: 'materialcostsum',
	            summaryType: 'sum',
	            sortable: true,
	            format:"￥0,0.00",
	            renderer:filterUnit
	        },{
	        	xtype: 'numbercolumn',
	            header: '退菜原材料量/单',
	            dataIndex: 'materialamount',
	            summaryType: 'sum',
	            sortable: true,
	            renderer:filterUnit
	        },{
	        	xtype: 'numbercolumn',
	            header: '退菜原材料量',
	            dataIndex: 'lossamount',
	            summaryType: 'sum',
	            sortable: true,
	            hidden: true,
	            renderer:filterUnit
	        },{
	            header: '退菜原材料总量',
	            dataIndex: 'lossmaterialsum',
	            summaryType: 'sum',
	            sortable: true,
	            renderer:filterNumber
	        },{
	        	xtype: 'numbercolumn',
	            header: '退菜原材料总价格',
	            dataIndex: 'lossmaterialcostsum',
	            summaryType: 'sum',
	            sortable: true,
	            format:"￥0,0.00",
	            renderer:filterUnit
	        },{
	        	xtype: 'numbercolumn',
	            header: '菜品销售量',
	            dataIndex: 'sellamount',
	            summaryType: 'sum',
	            sortable: true,
	            hidden:true,
	            renderer:filterUnit
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
        title: '菜品月销售报表',
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
    		var temp = (new Date()).add(Date.MONTH,-1).format('Ym');
    		var parseDate = Date.parseDate(currentDate+'01','Ymd');
    		var startDate = parseDate.format('Y-m-d');
    		var endDate = parseDate.add(Date.MONTH,1).format('Y-m-d');
    		store.baseParams.sql = "SQL-3";
    		store.baseParams.action = "sql";
    		store.baseParams.type = "map";
    		store.baseParams["{0}"] = startDate;
    		store.baseParams["{1}"] = endDate;
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
    layout.render("order_material_stat_div");
    grid_info.getSelectionModel().on('selectionchange', function(sm){
//        grid_info.removeBtn.setDisabled(sm.getCount() < 1);
    });
});


