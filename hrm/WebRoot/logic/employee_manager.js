Ext.onReady(function(){
	
    Ext.QuickTips.init();
    /**
	 * 最新的福利参数Json对象
	 */
	var currentWalfareParams; 
    /**
     * 福利参数设置表单
     */
    var form = new Ext.form.FormPanel({
	  	defaultType:"textfield",
	  	labelAlign:"right",
	  	url:"welfareParamsVo.do",
	  	labelWidth:80,
	  	frame:true,
	  	width:300,
	  	items:[new Ext.form.ComboBox({
	  		name: "id",
	  		fieldLabel: '用户角色',
		  	store: new Ext.data.Store({
	            proxy:new Ext.data.HttpProxy({url:"optionServlet.do?option=roleInfo"}),
	            autoLoad:true,
				reader:new Ext.data.ArrayReader({},[
				    {name:"value"},
				    {name:"text"}
				])
	        }),
	        width:150,
		  	emptyText: "请选择",
		  	allowBlank:false,
		  	mode: "local",
		  	forceSelection :true,
		  	triggerAction: "all",
		  	valueField: "value",
		  	displayField: "text",
		  	hiddenName: "id",
		  	listeners :{
		  		select:function(selectObj,valueObj,beforeValue){
		  			var value = valueObj.data.value;
		  			if(typeof(currentWalfareParams[value])!="undefined"){
						form.getComponent(1).setValue(currentWalfareParams[value].workday);
						form.getComponent(2).setValue(currentWalfareParams[value].welfare);
						form.getComponent(3).setValue(currentWalfareParams[value].pubfund);
		            	form.getComponent(4).setValue(currentWalfareParams[value].safefund);
		            	form.getComponent(5).setValue(currentWalfareParams[value].curefund);
		            	form.getComponent(6).setValue(currentWalfareParams[value].integfund);
		            	form.getComponent(7).setValue(currentWalfareParams[value].tax);
		  			}else{
						for(var i = 1 ; i <= 7 ; i++ ){
							form.getComponent(i).setValue(0);
						}
		  			}
		  		}
		  	}
		}),{
	    	name:"workday",
	    	fieldLabel:"工作天数/周",
	    	allowBlank:false,
	    	xtype:"numberfield",
	    	allowNegative:false,
	    	width:150,
	    	decimalPrecision:0,
	    	maxValue:7,
	    	maxLength:50
	  	},{
	    	name:"welfare",
	    	fieldLabel:"福利补贴",
	    	allowBlank:false,
	    	xtype:"numberfield",
	    	allowNegative:false,
	    	width:150,
	    	decimalPrecision:10,
	    	maxValue:150000,
	    	maxLength:50
	  	},{
	    	name:"pubfund",
	    	fieldLabel:"公积金比例",
	    	allowBlank:false,
	    	xtype:"numberfield",
	    	allowNegative:false,
	    	width:150,
	    	decimalPrecision:10,
	    	maxValue:1,
	    	maxLength:50
	  	},{
	    	name:"safefund",
	    	fieldLabel:"养老保险金",
	    	allowBlank:false,
	    	xtype:"numberfield",
	    	allowNegative:false,
	    	width:150,
	    	maxValue:1,
	    	decimalPrecision:10,
	    	maxLength:50
	  	},{
	    	name:"curefund",
	    	fieldLabel:"医疗保险金",
	    	allowBlank:false,
	    	xtype:"numberfield",
	    	allowNegative:false,
	    	width:150,
	    	decimalPrecision:10,
	    	maxValue:1,
	    	maxLength:50
	  	},{
	    	name:"integfund",
	    	fieldLabel:"综合保险",
	    	allowBlank:false,
	    	xtype:"numberfield",
	    	allowNegative:false,
	    	width:150,
	    	decimalPrecision:10,
	    	maxValue:1,
	    	maxLength:50
	  	},{
	    	name:"tax",
	    	fieldLabel:"个人所得税",
	    	allowBlank:false,
	    	xtype:"numberfield",
	    	allowNegative:false,
	    	width:150,
	    	decimalPrecision:10,
	    	maxValue:1,
	    	maxLength:50
	  	}]
	});
	
	/**
	 * 得到福利初始化数据
	 */
	var welfareData = function(){
		Ext.Ajax.request({
			url:"welfareParamsVo.do",
			sync:true,
			params:{
				action:"select"
			},
			success:function(action){
				currentWalfareParams = eval(action.responseText);
			},
			failure:function(action){
			}
		});
	}
	welfareData();
	/**
	 * 表格统计
	 */
    var summary = new Ext.ux.grid.GroupSummary();
    /**
     * 表格构造
     */
    var store = new Ext.data.GroupingStore({
    	proxy: new Ext.data.HttpProxy({url:"employeeInfoQueryVo.do"}),
        reader: new Ext.data.JsonReader({
			totalProperty:"totalProperty",
			root:"root"
		},[
    		{name: "user_id", type: 'string'},
    		{name: "user_name", type: 'string'},
    		{name: "workday", type: 'int'},
    		{name: "salary", type: 'float'},
    		{name: "worksalary", type: 'float'},
    		{name: "welfare", type: 'float'},
    		{name: "tax", type: 'float'},
    		{name: "pubfund", type: 'float'},
    		{name: "safefund", type: 'float'},
    		{name: "curefund", type: 'float'},
    		{name: "otherfund", type: 'float'},
    		{name: "integfund", type: 'float'},
    		{name: "role_name", type: 'string'},
    		{name: "role_code", type: 'string'}
 		]),
 		groupField: 'role_name',
        sortInfo: {field: 'role_code', direction: 'ASC'}
    });
    var editor = new Ext.ux.grid.RowEditor({
        saveText: 'Update'
    });
    
    var grid_info = new Ext.grid.GridPanel({
        store: store,
        autoWidth:true,
        region:'center',
        margins: '0 5 5 5',
        autoExpandColumn: 'user_name',
        plugins: [editor,summary],
        view: new Ext.grid.GroupingView({
            markDirty: false

        }),
		tbar : ["-",{
            text: '员工工资统计',
            tooltip: '点击员工工资将统计,再次点击将取消统计',
            iconCls:"icon-user-add",
            handler: function(){
            	summary.toggleSummaries();
            }
        },"-",{
            text: '福利参数设置',
            tooltip: '设置福利参数,这样写入员工工资将自动计算需要缴纳的福利!',
            iconCls:"icon-params",
            handler: function(){
            	var win = new Ext.Window({
	                layout      : 'fit',
	                width       : 300,
	                title		: "福利参数设置",
	                height  	: 300,
	                modal		: true,
	                closeAction : 'hide',
	                allowDomMove: true,
	                bodyBorder 	: false,
	                plain       : true,
	                items       : [form],
	                buttons: [{
	                    text     : "保存",
	                    handler	 : function(){
	                    	var basicForm = form.getForm();
	                    	if(basicForm.isValid()){
	                    		basicForm.submit({
	                    			success:function(form,action){
	                    				Ext.MessageBox.show({
	                    					title:"操作提示",
	                    					msg:action.result.msg,
	                    					buttons:Ext.MessageBox.OK,
	                    					icon:Ext.MessageBox.INFO
	                    				});
	                    				welfareData();
	                    			},params:{
	                    				action:"insert"
	                    			},
	                    			waitMsg:"Loading"
	                    		});
	                    	}
	                    }
	                },{
	                    text     : '关闭',
	                    handler  : function(){
	                    	form.getForm().reset();
	                        win.hide();
	                    }
	                }]
	            });
				win.show();
            }
        }],
        columns: [
	        new Ext.grid.RowNumberer(),
	        {
	            id: 'user_id',
	            header: '用户id',
	            dataIndex: 'user_id',
	            sortable: true,
	            summaryType: 'count',
	            summaryRenderer: function(v, params, data){
	                return ((v === 0 || v > 1) ? '(员工' + v +' 名)' : '(员工1 名)');
	            },
	            hidden:true
	        },{
	            id: 'user_name',
	            header: '员工姓名',
	            dataIndex: 'user_name',
	            sortable: true,
	            summaryType: 'count',
	            summaryRenderer: function(v, params, data){
	                return ((v === 0 || v > 1) ? '(员工' + v +' 名)' : '(员工1 名)');
	            }
	        },{
	            xtype: 'numbercolumn',
	            header: '基本工资/月',
	            dataIndex: 'salary',
	            format: '￥0,0.00',
	            summaryType: 'sum',
	            sortable: true,
	            editor: {
	                xtype: 'numberfield',
	                allowBlank: false,
	                minValue: 0,
	                maxValue: 1500000,
	                listeners:{
	                	change:function(itemObj,inputData){
	                		var selection = grid_info.getSelectionModel().getSelected().json;
	                		if(typeof(currentWalfareParams[selection.role_code])!="undefined"){
		                		editor.items.items[5].setValue(currentWalfareParams[selection.role_code].workday);
		                		editor.items.items[6].setValue(currentWalfareParams[selection.role_code].welfare);//
		                		editor.items.items[7].setValue(inputData*currentWalfareParams[selection.role_code].tax);//个人所得税
		                		editor.items.items[8].setValue(inputData*currentWalfareParams[selection.role_code].pubfund);//公积金
		                		editor.items.items[9].setValue(inputData*currentWalfareParams[selection.role_code].safefund);
		                		editor.items.items[10].setValue(inputData*currentWalfareParams[selection.role_code].curefund);
		                		editor.items.items[11].setValue(inputData*currentWalfareParams[selection.role_code].integfund);
	                		}
	                	}
	                }
	            }
	        },{
	            xtype: 'numbercolumn',
	            header: '上岗工资/月',
	            dataIndex: 'worksalary',
	            summaryType: 'sum',
	            sortable: true,
	            editor: {
	                xtype: 'numberfield',
	                allowBlank: false,
	                minValue: 0,
	                maxValue: 1500000
	            }
	        },{
	            xtype: 'numbercolumn',
	            header: '工作天数/周',
	            width:70,
	            dataIndex: 'workday',
	            summaryType: 'max',
	            sortable: true,
	            editor: {
	                xtype: 'numberfield',
	                allowBlank: false,
	                minValue: 0,
	                maxValue: 7
	            }
	        },{
	            xtype: 'numbercolumn',
	            header: '福利补贴',
	            dataIndex: 'welfare',
	            width:90,
	            summaryType: 'sum',
	            format: '￥0,0.00',
	            sortable: true,
	            editor: {
	                xtype: 'numberfield',
	                allowBlank: false,
	                minValue: 0,
	                maxValue: 150000
	            }
	        },{
	            xtype: 'numbercolumn',
	            header: '个人所得税',
	            width:90,
	            summaryType: 'sum',
	            dataIndex: 'tax',
	            format: '￥0,0.00',
	            sortable: true,
	            editor: {
	                xtype: 'numberfield',
	                allowBlank: false,
	                minValue: 0,
	                maxValue: 150000
	            }
	        },{
	            xtype: 'numbercolumn',
	            header: '公积金',
	            width:90,
	            dataIndex: 'pubfund',
	            summaryType: 'sum',
	            format: '￥0,0.00',
	            sortable: true,
	            editor: {
	                xtype: 'numberfield',
	                allowBlank: false,
	                minValue: 0,
	                maxValue: 150000
	            }
	        },{
	            xtype: 'numbercolumn',
	            header: '养老保险金',
	            dataIndex: 'safefund',
	            width:90,
	            summaryType: 'sum',
	            format: '￥0,0.00',
	            sortable: true,
	            editor: {
	                xtype: 'numberfield',
	                allowBlank: false,
	                minValue: 0,
	                maxValue: 150000
	            }
	        },{
	            xtype: 'numbercolumn',
	            header: '医疗保险金',
	            width:90,
	            dataIndex: 'curefund',
	            summaryType: 'sum',
	            format: '￥0,0.00',
	            sortable: true,
	            editor: {
	                xtype: 'numberfield',
	                allowBlank: false,
	                minValue: 0,
	                maxValue: 150000
	            }
	        },{
	            xtype: 'numbercolumn',
	            header: '综合保险金',
	            width:90,
	            dataIndex: 'integfund',
	            summaryType: 'sum',
	            format: '￥0,0.00',
	            sortable: true,
	            editor: {
	                xtype: 'numberfield',
	                allowBlank: false,
	                minValue: 0,
	                maxValue: 150000
	            }
	        },{
	            header: '角色名称',
	            dataIndex: 'role_name',
	            sortable: true,
	            hidden:true
	        },{
	            header: '角色id',
	            dataIndex: 'role_code',
	            sortable: true,
	            hidden:true
	        },{
	            xtype: 'numbercolumn',
	            header: '其他福利保险',
	            dataIndex: 'otherfund',
	            width:90,
	            summaryType: 'sum',
	            format: '￥0,0.00',
	            sortable: true,
	            editor: {
	                xtype: 'numberfield',
	                allowBlank: false,
	                minValue: 0,
	                maxValue: 150000
	            }
	        }
	    ],
	    bbar: new Ext.PagingToolbar({
            pageSize: 10,
            store: store,
            displayInfo: true,

            plugins: new Ext.ux.ProgressBarPager()
        })

    });
    var listenerEvent = {
    	"update":function(updateObj,jsonRecord){
    		var recordData = editor.record.data;
    		Ext.Ajax.request({
    			url:"employeeInfoVo.do?action=update",
    			params:recordData
    		});
    	}
    	
    }
    editor.addListener("afteredit",listenerEvent.update);
   /* var cstore = new Ext.data.JsonStore({
        fields:['month', 'employees', 'salary'],
        data:[],
        refreshData: function(){
            
        }
    });
    cstore.refreshData();
    store.on('add', cstore.refreshData, cstore);
    store.on('remove', cstore.refreshData, cstore);
    store.on('update', cstore.refreshData, cstore);*/
    
    var layout = new Ext.Panel({
        title: '员工工资福利表',
        layout: 'border',
        layoutConfig: {
            columns: 1
        },
        autoWidth:true,
        height: Ext.getBody().getHeight()-148-20,
        items: [grid_info]
    });
    store.load({params:{start:0, limit:10}});
    layout.render("employee_manager_div");
    grid_info.getSelectionModel().on('selectionchange', function(sm){
//        grid_info.removeBtn.setDisabled(sm.getCount() < 1);
    });
});


