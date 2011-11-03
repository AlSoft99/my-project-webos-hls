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
					grid.addBtn.setDisabled(true);
					tree_role.getRootNode().reload();
				}
			}
		]
	});
	var tree_role = new Ext.tree.TreePanel({
		width : 350,
//		height : height-500,
		height : (height-200)/2,
		autoScroll: true,
		tbar:toolbar,
		loader : new Ext.tree.TreeLoader({
			dataUrl : "treeInfoQuery.do?action=singledate",
			baseParams :{
				parent: "select new map(a.id as id,a.id || '[' || b.paramsname || ']' as text,b.paramsname as qtip,a.manguser as manguser) from StoreInputInfo a,ParamsList b,StoreParamsInfo c where b.typeid='CONFIRM_STATUS' and a.confirmstatus='3' and b.paramscode=a.confirmstatus and a.applydate>'{1}' and a.applydate<'{2}' and c.outuser='"+Ext.getDom("user_id").value+"' and c.stocklevel='3' and a.preprice>=c.min and a.preprice<c.max",
//				child: "select new map(userId as id,userName as text,userName as qtip,roleInfo.roleCode as nodeId) from UserInfo"
//				parent: "select new map(a.id as id,a.id || '[' || b.paramsname || ']' as text,b.paramsname as qtip,a.manguser as manguser) from StoreInputInfo a,ParamsList b where b.typeid='CONFIRM_STATUS' and a.confirmstatus='1' and b.paramscode=a.confirmstatus and a.applydate>'{1}' and a.applydate<'{2}' and a.manguser='"+Ext.getDom("user_id").value+"' and a.confirmstatus='1' ",  
				start:curentStartDate,
				end:curentEndDate
			}
		})
	});
	var root = new Ext.tree.AsyncTreeNode({
		id : "0",
		text : "采购单"
	});
	tree_role.setRootNode(root);
	tree_role.getRootNode().expand();// 2315
	tree_role.on("click",function(node){
		if(node.leaf){
			grid.addBtn.setDisabled(false);
			currentId = node.attributes.id;
			Ext.Ajax.request({
        		url:"storeInputInfoVo.do",
        		params:{
        			action:"infolist",
        			id:currentId
        		},
        		success:function(action){
        			currentSalaryList = eval("("+action.responseText+")");
    				salaryList.setSource(currentSalaryList);
					listenerEvent.loadGrid();
    			},
    			waitMsg:"Loading"
        	});
		}else{
			grid.addBtn.setDisabled(true);
		}
	});
	
	/**
	 * 右键菜单
	 */
    var form = new Ext.form.FormPanel({
	  	defaultType:"textfield",
	  	labelAlign:"right",
	  	labelWidth:80,
	  	url:"storeInputInfoVo.do",
	  	frame:true,
	  	width:200,
	  	items:[{
		    	name:"preprice",
		    	fieldLabel:"预计金额",
		    	xtype:"numberfield",
		    	listeners:{
                	change:function(itemObj,inputData){
			  			form.getComponent(1).getStore().load({
			  				params:{sql:"select a.outuser,b.userName  from StoreParamsInfo a,UserInfo b where a.outuser=b.userId and a.min<="+inputData+" and a.max>"+inputData+" and a.stocklevel='1'"}
			  			});
                	}
                }
	  		},new Ext.form.ComboBox({
		  		name: "manguser",
		  		fieldLabel: '申报主管',
		        width:140,
			  	emptyText: "请选择",
			  	allowBlank:false,
			  	store: new Ext.data.Store({
		            proxy:new Ext.data.HttpProxy({
		            	url:"optionSqlServlet.do"
		            }),
		            baseParams:{
	            		sql:""
	            	},
		            autoLoad:true,
					reader:new Ext.data.ArrayReader({},[
					    {name:"value"},
					    {name:"text"}
					])
		        }),
			  	mode: "local",
			  	forceSelection :true,
			  	triggerAction: "all",
			  	valueField: "value",
			  	displayField: "text",
			  	hiddenName: "manguser"
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
  				currentId = "";
				salaryList.setSource({});
		   		listenerEvent.loadGrid();
  			}
  		}]
	});
	tree_role.on("contextmenu",function(node,e){
  		e.preventDefault();
  		node.select();
  		contextmenu.items.items[0].show();
  		contextmenu.showAt(e.getXY());
	});
	
	var Employee = Ext.data.Record.create([
		{name: "id", type: 'string'},
		{name: "inid", type: 'string'},
		{name: "goodstype", type: 'string'},
		{name: "goodsid", type: 'string'},
		{name: "goodsnumber", type: 'int'},
		{name: "updtuser", type: 'string'},
    	{name: "updttime",type:"date",dateFormat:"Y-m-d H:i:s.u"}
    ]);
    /*var comboBoxType = comboBoxList.comboBoxSql("select id,typename from StoreType where outuser='"+Ext.getDom("user_id").value+"'","","");
	
	comboBoxType.emptyText = "过滤货物";
	comboBoxType.allowBlank = true;*/
	var comboBoxName = comboBoxList.comboBoxSql("select b.id,b.goodsname from StoreType a,StoreList b where a.id = b.typeid ","","");
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
			header:"采购单ID",
			dataIndex:"inid",
			sortable: true,
			hidden:true
		},{
    		header:"货物种类",
    		dataIndex:"goodstype",
    		width:150,
    		sortable: true,
    		renderer:filter

    	},{
    		header:"货物名称",
    		dataIndex:"goodsid",
    		width:150,
    		sortable: true,
    		renderer:transform

    	},{
			xtype: 'numbercolumn',
    		header:"货物数量",
    		dataIndex:"goodsnumber",
    		sortable: true
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
  		return trans.comboBox(comboBoxName,value);
  	}
  	function transformName(value,record){
  		return trans.comboBox(comboBoxName,value);
  	}
  	/*comboBoxType.on("select",function(obj,option){
  		var roleCode = option.data.value;
		comboBoxName.getStore().load({
			params:{
				sql:"select b.id,b.goodsname from StoreType a,StoreList b where a.id = b.typeid and a.outuser='"+Ext.getDom("user_id").value+"' and b.typeid='"+roleCode+"'"
			}
		});
		comboBoxName.setValue("");
  	});*/
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
    	tbar:[{
    		ref: '../addBtn',
            text:'确认货物入仓',
            tooltip:'确认货物已入仓, 入仓货物将不能回滚, 请确认货物已入仓',
            iconCls:'save',
            disabled: true,
            handler:function(){
            	Ext.MessageBox.show({
					title:"警告提示",
					msg:"请确认采购单单号为<font color='red'>"+currentId+"</font>的单子已入仓,确定后将不能回滚!!",
					buttons:Ext.MessageBox.OKCANCEL,
					icon:Ext.MessageBox.WARNING,
					fn:function(btn){
						if(btn=="ok"){
							loading.loadMask().show();
			                Ext.Ajax.request({
				        		url:"storeInputInfoVo.do?action=confirmstore",
				        		params:{
				        			id:currentId
				        		},
				        		success:function(action){
//				        			var json = eval("("+action.responseText+")");
				        			loading.loadMask().hide();
				        			grid.addBtn.setDisabled(true);
				        			tree_role.getRootNode().reload();
				        			salaryList.setSource({});
				        			currentId = '';
				        			listenerEvent.loadGrid();
				    			},
				    			failure:function(action){
				    				var json = eval("("+action.responseText+")");
				    				Ext.MessageBox.show({
										title:"警告提示",
										msg:json.msg,
										buttons:Ext.MessageBox.OK,
										icon:Ext.MessageBox.ERROR
				    				});
				    				return false;
				    			}
				        	});
						}
					}
				});
            	
            }
        }],
    	bbar:new Ext.PagingToolbar({
//		    pageSize:10,
		    store:ds,
		    displayInfo:true,
		    displayMsg:"显示第{0}条到{1}条记录,一共{2}条记录",
		    emptyMsg:"无记录"
		})

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
        title: '单笔采购单明细', 
//        height:290,
        height : (height-200)/2,
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
    			url:"storeInputInfoVo.do?action=updateRecord",
    			params:recordData,
    			success:function(action){
    				var json = eval("("+action.responseText+")");
    				if(json.msg!=""){
    					var json = eval("("+action.responseText+")");
	    				Ext.MessageBox.show({
							title:"错误提示",
							msg:json.msg,
							buttons:Ext.MessageBox.OK,
							icon:Ext.MessageBox.ERROR
	    				});
	    				return false;
    				}
    			},
    			failure:function(action){
    			}
    		});
//    		comboBoxList.goodsList.getStore().load();
    	},
    	resetForm:function(){
    		form.getComponent(0).setValue("");
    		form.getComponent(1).setValue("");
    	},
    	loadGrid:function(){
    		ds.load({
		  		params:{
		  			start:-1, 
		  			limit:-1,
		  			action:"hql",
					type:"map",
					sql:"select new map(a.goodsid as goodsid,a.goodsnumber as goodsnumber,a.goodsprice as goodsprice,a.goodsdesc as goodsdesc,b.goodsname as goodsname,a.updttime as updttime,a.id as id,a.inid as inid,a.updtuser as updtuser) from StoreInputList a,StoreList b where a.inid='"+currentId+"' and a.goodsid=b.id"
		  		}
		  	});
    	}
    }
	listenerEvent.loadGrid();
	
	new Ext.Panel({
        title: '采购单查询',
        collapsible:true,
        hideBorders:true,
        renderTo: 'stock_store_confirm_name',
        width : 350,
        height : height-100,
        items:[
        	tree_role,
        	salaryList
		]
    });
    
    new Ext.Panel({
        title: '采购单货物明细',
        collapsible:true,
        width : width-595,
        renderTo: 'stock_store_confirm_list',
        height : height-100,
        items:[grid]
    });

});