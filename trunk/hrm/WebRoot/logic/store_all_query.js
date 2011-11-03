Ext.onReady(function() {
	Ext.QuickTips.init();
	var height = Ext.getBody().getHeight()-20;
	var width = Ext.getBody().getWidth();
	/**
	 * 树形
	 */
	var currentId = "";
	var currentSalaryList ;
	var curentStartDate = (new Date()).clearTime().format("Y-m-d");
	var curentEndDate = (new Date()).clearTime().format("Y-m-d");
	var toolbar = new Ext.Toolbar({
		items:[
			"开始:",
			new Ext.form.DateField({
				emptyText:"默认当天"
			}),
			"结束:",
			new Ext.form.DateField({
				emptyText:"默认当天"
			}),
			{
				text:"查询",
				iconCls:"icon-grid",
				tooltip:'默认为当天查询',
				handler:function(event,mouse){
					if(toolbar.getComponent(1).getValue()!=""){
						curentStartDate = toolbar.getComponent(1).getValue().format("Y-m-d");
					}
					if(toolbar.getComponent(3).getValue()!=""){
						curentEndDate = toolbar.getComponent(3).getValue().format("Y-m-d");
					}
					tree_role.loader.baseParams.start = curentStartDate;
					tree_role.loader.baseParams.end = curentEndDate;
					tree_role.getRootNode().reload();
				}
			}
		]
	});
	var tree_role = new Ext.tree.TreePanel({
		width : 350,
		height : (height-200)/2,
		autoScroll: true,
		tbar:toolbar,
		loader : new Ext.tree.TreeLoader({
			dataUrl : "treeInfoQuery.do?action=singledate",
			baseParams :{
				parent: "select new map(a.id as id,a.id || '[' || b.paramsname || ']' as text,b.paramsname as qtip,a.recuser as recuser) from StoreOutputInfo a,ParamsList b where b.typeid='STORE_STATUS' and b.paramscode=a.status and outdate>'{1}' and outdate<'{2}'",
//				child: "select new map(userId as id,userName as text,userName as qtip,roleInfo.roleCode as nodeId) from UserInfo"
				start:curentStartDate,
				end:curentEndDate
			}
		})
	});
	var root = new Ext.tree.AsyncTreeNode({
		id : "0",
		text : "出仓单"
	});
	tree_role.setRootNode(root);
	tree_role.getRootNode().expand();// 2315
	tree_role.on("click",function(node){
		if(node.leaf){
			currentId = node.attributes.id;
			Ext.Ajax.request({
        		url:"storeOutputInfoVo.do",
        		params:{
        			action:"infolist",
        			id:currentId
        		},
        		success:function(action){
        			currentSalaryList = eval("("+action.responseText+")");
    				salaryList.setSource(currentSalaryList);
    				ds.load({
				  		params:{
				  			start:-1, 
				  			limit:-1,
				  			action:"hql",
							type:"entity",
							sql:"from StoreOutputList where outid='"+currentId+"'"
				  		}
				  	});
    			},
    			waitMsg:"Loading"
        	});
		}else{
		}
	});
	/**
	 * 右键菜单
	 */
    var form = new Ext.form.FormPanel({
	  	defaultType:"textfield",
	  	labelAlign:"right",
	  	labelWidth:80,
	  	url:"storeOutputInfoVo.do",
	  	frame:true,
	  	width:200,
	  	items:[
		  	new Ext.form.ComboBox({
		  		name: "roleCode",
		  		fieldLabel: '角色选择',
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
			  	mode: "local",
			  	forceSelection :true,
			  	triggerAction: "all",
			  	valueField: "value",
			  	displayField: "text",
			  	hiddenName: "roleCode",
			  	listeners:{
			  		select:function(obj,option){
			  			var roleCode = option.data.value;
			  			form.getComponent(1).getStore().load({
			  				params:{option:"userInfo",change:roleCode}
			  			});
			  			form.getComponent(1).setValue("");
			  		}
			  	}
			}),new Ext.form.ComboBox({
		  		name: "recuser",
		  		fieldLabel: '仓库人',
			  	store: new Ext.data.Store({
		            proxy:new Ext.data.HttpProxy({
		            	url:"optionsChangeServlet.do?option=userInfo"
		            }),
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
			  	hiddenName: "recuser"
			})
		]
	});
	var node_menu = null;
	var contextmenu = new Ext.menu.Menu({
  		items:[{
  			text:"刷新",
  			iconCls:"option",
  			handler:function(event,mouse){
  				tree_role.getRootNode().reload();
  			}
  		}]
	});
	tree_role.on("contextmenu",function(node,e){
  		e.preventDefault();
  		node.select();
  		contextmenu.showAt(e.getXY());
	});
	var goodsListBox = comboBoxList.goodsList;
	/**
	 * 员工工资明细表
	 */
	var editor = new Ext.ux.grid.RowEditor({
        saveText: 'Update'
    });
	var Employee = Ext.data.Record.create([
		{name: "id", type: 'string'},
		{name: "outid", type: 'string'},
		{name: "goodstype", type: 'string'},
		{name: "goodsid", type: 'string'},
		{name: "goodsnumber", type: 'int'},
		{name: "updtuser", type: 'string'},
    	{name: "updttime",type:"date",dateFormat:"Y-m-d H:i:s.u"}
    ]);
	comboBoxList.goodsType.emptyText = "过滤货物";
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sm,
		{
			header:"明细ID",
			dataIndex:"id",
			sortable: true,
			hidden:true
		},{
			header:"出仓单ID",
			dataIndex:"outid",
			sortable: true,
			hidden:true
		},{
    		header:"货物种类",
    		dataIndex:"goodstype",
    		width:150,
    		sortable: true,
    		renderer:filter,
    		editor: comboBoxList.goodsType

    	},{
    		header:"货物名称",
    		dataIndex:"goodsid",
    		width:150,
    		sortable: true,
    		editor: goodsListBox,
    		renderer:transform

    	},{
			xtype: 'numbercolumn',
    		header:"货物数量",
    		dataIndex:"goodsnumber",
    		sortable: true,
    		editor: {
                xtype: 'numberfield',
                minValue: 0,
                maxValue: 150000,
                allowBlank: false
            }
    	},{
			header:"最后更新人",
			dataIndex:"updtuser",
			hidden:true,
			sortable: true
		},{
			xtype: 'datecolumn',
			header:"最后更新时间",
			dataIndex:"updttime",
			format: 'Y-m-d H:i:s.u',
			width:150,
			sortable: true
		}
  	]);
  	function transform(value,record){
  		return trans.comboBox(goodsListBox,value);
  	}
  	function transformName(value,record){
  		return trans.comboBox(goodsListBox,value);
  	}
  	comboBoxList.goodsType.on("select",function(obj,option){
  		var roleCode = option.data.value;
		goodsListBox.getStore().load({
			params:{option:"goodsList",change:roleCode}
		});
		goodsListBox.setValue("");
  	});
  	cm.defaultSortable = true;
  	var ds = new Ext.data.Store({
    	proxy: new Ext.data.HttpProxy({
    		url:"queryInfoVo.do"
    	}),
		reader: new Ext.data.JsonReader({
			totalProperty:"totalProperty",
			root:"root"
		},Employee)
  	});
  	
  	var grid = new Ext.grid.GridPanel({
    	ds:ds,
    	cm:cm,
    	sm:sm,
    	loadMask:true,
    	height : height-180,
    	iconCls:"icon-grid",
    	bbar:new Ext.PagingToolbar({
//		    pageSize:10,
		    store:ds,
		    displayInfo:true,
		    displayMsg:"显示第{0}条到{1}条记录,一共{2}条记录",
		    emptyMsg:"无记录"
		})

  	});
  	ds.load({
  		params:{
  			start:-1, 
  			limit:-1,
  			action:"hql",
			type:"entity",
			sql:"from StoreOutputList where outid='"+currentId+"'"
  		}
  	});
	/**
	 * 福利明细
	 */
//  	var propertySource = {
//		名字:"官瑞林",
//    	创建时间:new Date(Date.parse("12/15/2007")),
//    	是否有效:false,
//    	版本号:.01
//	};
//	propertySource = {姓名:'官建明',工作天数:'6',基本工资:'20000.0',上岗工资:'30000.0',福利补贴:'1000.0',个人所得税:'2000.0',公积金:'2200.0',养老保险金:'2400.0',医疗保险金:'2600.0',综合保险金:'1000.0',其他福利保险:'0.0'};
  	var propertySource = {};
	var salaryList = new Ext.grid.PropertyGrid({
        title: '单笔出仓单明细', 
        height:(height-200)/2,
        closable: false
    });
    salaryList.setSource(propertySource);
	salaryList.on("beforeedit",function(e){
	  	e.cancel = true;
	  	return false;
	});
	function filter(){
		return "<font color='red'>过滤货物</font>";
	}
	var listenerEvent = {
    	update:function(updateObj,jsonRecord){
    		var recordData = editor.record.data;
    		Ext.Ajax.request({
    			url:"storeOutputInfoVo.do?action=updateRecord",
    			params:recordData
    		});
    		goodsListBox.getStore().load();
    	},
    	resetForm:function(){
    		form.getComponent(0).setValue("");
    		form.getComponent(1).setValue("");
    	}
    }
	editor.addListener("afteredit",listenerEvent.update);
	new Ext.Panel({
        title: '出仓单查询',
        collapsible:true,
        hideBorders:true,
        renderTo: 'store_all_query_name',
        width : 350,
        height : height-100,
        items:[
        	tree_role,
        	salaryList
		]
    });
    
    new Ext.Panel({
        title: '出仓单货物明细',
        collapsible:true,
        width : width-595,
        renderTo: 'store_all_query_list',
        height : height-100,
        items:[grid]
    });

});