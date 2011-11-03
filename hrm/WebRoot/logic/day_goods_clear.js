Ext.onReady(function() {
	Ext.QuickTips.init();
	var height = Ext.getBody().getHeight()-20;
	var width = Ext.getBody().getWidth();
	/**
	 * 树形
	 */
	var currentUserId = "";
	var currentSalaryList ;
	var startDate = (new Date()).clearTime().add(Date.DAY,-1).format("Y-m-d");
	var endDate = (new Date()).clearTime().format("Y-m-d");
	var comboBoxUser = comboBoxList.comboBoxSql("select distinct a.id.userlist,a.id.userlist from OutUserList a where a.id.tag='1'","货物人","userlist");
	comboBoxUser.allowBlank = false;
	comboBoxUser.emptyText = "请选择仓库进行统计";
	comboBoxUser.getStore().on("load",function(a,json){
		if(json.length>0){
			var value = json[0].data.value;
			comboBoxUser.setValue(value);
			listenerEvent.loadGrid();
		}
	});
	/**
	 * 员工工资明细表
	 */
	var editor = new Ext.ux.grid.RowEditor({
        saveText: 'Update'
    });
	var Employee = Ext.data.Record.create([
		{name: "userlist", type: 'string'},
		{name: "goodsid", type: 'string'},
		{name: "goodsname", type: 'string'},
		{name: "cleardate",type:"date",dateFormat:"Y-m-d H:i:s.u"},
		{name: "username", type: 'string'},
		{name: "goodsname", type: 'string'},
    	
    	{name: "outnumber", type: 'int'},
    	{name: "returnnumber", type: 'int'},
    	{name: "lastnumber", type: 'int'},
    	{name: "goodsdesc", type: 'string'},
    	{name: "equals", type: 'bool'}
    ]);

	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sm,
		{
			header:"仓库编号",
			dataIndex:"userlist",
			width:150,
			sortable: true
		},{
			header:"货物ID",
			dataIndex:"goodsid",
			sortable: true,
			hidden:true
		},{
			header:"货物名称",
			dataIndex:"goodsname",
			sortable: true
		},{
			xtype: 'datecolumn',
			header:"清算日期",
			dataIndex:"cleardate",
			sortable: true,
			format: 'Y-m-d',
			hidden:true
		},{
			header:"货物名称",
			dataIndex:"goodsname",
			sortable: true
		},{
			xtype: 'numbercolumn',
    		header:"销售数量",
    		dataIndex:"outnumber",
    		sortable: true

    	},{
			xtype: 'numbercolumn',
    		header:"退货数量",
    		dataIndex:"returnnumber",
    		sortable: true

    	},{
			xtype: 'numbercolumn',
    		header:"实际销售数量",
    		dataIndex:"lastnumber",
    		sortable: true

    	},{
    		header:"备注",
    		dataIndex:"goodsdesc",
    		width:150,
    		sortable: true,
    		editor: {
                xtype: 'textfield',
                maxLength:200,
                allowBlank: false
            }
    	},{
    		xtype: 'booleancolumn',
    		header:"是否匹配",
    		align: 'center',
    		dataIndex:"equals",
    		trueText: 'Yes',
            falseText: 'No',
            sortable: true,
            editor: {
                xtype: 'checkbox'
            }
    	}
  	]);
  	cm.defaultSortable = true;
  	var ds = new Ext.data.Store({
    	proxy: new Ext.data.HttpProxy({
    		url:"dayGoodsClearVo.do"
    	}),
		reader: new Ext.data.JsonReader({
			totalProperty:"totalProperty",
			root:"root"
		},Employee)
  	});
  	var dataField = new Ext.form.DateField({
		emptyText:"默认为昨日"
	});
  	var grid = new Ext.grid.GridPanel({
    	ds:ds,
    	cm:cm,
    	sm:sm,
    	loadMask:true,
    	height : height-180,
    	plugins: [editor],
    	iconCls:"icon-grid",
    	bbar:new Ext.PagingToolbar({
//		    pageSize:10,
		    store:ds,
		    displayInfo:true,
		    displayMsg:"显示第{0}条到{1}条记录,一共{2}条记录",
		    emptyMsg:"无记录"
		}),
		tbar:[
			"清算日期:",
			dataField,
			"仓库编号:",
			comboBoxUser,
			{
				text:"查询",
				iconCls:"icon-grid",
				tooltip:'默认为当天查询',
				handler:function(event,mouse){
					if(dataField.getValue()!=""){
						startDate = dataField.getValue();
						endDate = dataField.getValue().add(Date.DAY,1);
					}else{
						startDate = (new Date()).clearTime().add(Date.DAY,-1);
						endDate = (new Date()).clearTime();
					}
					startDate = (startDate.format("Y-m-d"));
					endDate = (endDate.format("Y-m-d"));
					listenerEvent.loadGrid();
				}
			}
		]
  	});

	var listenerEvent = {
    	update:function(updateObj,jsonRecord){
    		var recordData = editor.record.data;
    		recordData.cleardate = recordData.cleardate.format('Y-m-d');
    		Ext.Ajax.request({
    			url:"dayGoodsClearVo.do?action=update",
    			params:recordData
    		});
    	},
    	loadGrid:function(){
    		var where = "";
    		if(comboBoxUser.getValue()!=""){
    			where = " and a.id.userlist='"+comboBoxUser.getValue()+"' ";
    		}
    		ds.baseParams.sql = "select new map(a.id.goodsid as goodsid,b.goodsname as goodsname,a.id.cleardate as cleardate,a.id.userlist as userlist,a.outnumber as outnumber,a.returnnumber as returnnumber,a.lastnumber as lastnumber,a.equals as equals,a.goodsdesc as goodsdesc)  from DayGoodsClear a,StoreList b where a.id.goodsid=b.id "+where+"  and a.id.cleardate>='"+startDate+"' and a.id.cleardate<'"+endDate+"'";
    		ds.baseParams.action = "hql";
    		ds.baseParams.type = "map";
    		ds.load({
		  		params:{   
		  			start:-1, 
		  			limit:-1
		  		}
		  	});
    	}
    	
    }
    
	editor.addListener("afteredit",listenerEvent.update);
    new Ext.Panel({
        title: '日终货物清算列表',
        collapsible:true,
        width : width-240,
        renderTo: 'day_goods_clear_salary',
        height : height-100,
        items:[grid]
    });
    grid.getSelectionModel().on('selectionchange', function(sm){
    });

});