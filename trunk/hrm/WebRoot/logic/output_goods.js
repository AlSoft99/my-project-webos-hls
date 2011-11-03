Ext.onReady(function() {
	Ext.QuickTips.init();
	var height = Ext.getBody().getHeight()-20;
	var width = Ext.getBody().getWidth();
	/**
	 * 树形
	 */
	var currentId = "";
	var curentStartDate = (new Date()).clearTime().format("Y-m-d");
	var curentEndDate = (new Date()).clearTime().format("Y-m-d");
	var currentSalaryList ;
	var toolbar = new Ext.Toolbar([
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
			handler:function(){
				if(toolbar.getComponent(1).getValue()!=""){
					curentStartDate = toolbar.getComponent(1).getValue().format("Y-m-d");
				}
				if(toolbar.getComponent(3).getValue()!=""){
					curentEndDate = toolbar.getComponent(3).getValue().format("Y-m-d");
				}
				tree_menu.loader.baseParams.start = curentStartDate;
				tree_menu.loader.baseParams.end = curentEndDate;
				grid.addBtn.setDisabled(true);
				tree_menu.getRootNode().reload();
			}
		}
	]);
	var tree_menu = new Ext.tree.TreePanel({
		title:"出货账单选择",
		width : 350,
		autoScroll:true,
		height : height-500,
		tbar:toolbar,
		loader : new Ext.tree.TreeLoader({
			dataUrl : "treeInfoQuery.do?action=singledate",
			baseParams :{
				start:curentStartDate,//对应{1}
				end:curentEndDate,//对应{2}
				parent: "select new map(id as id,id||' ['||goodsdesc||']' as text,goodsdesc as qtip) from GoodsOutputInfo where outuser='"+document.getElementById("user_id").value+"' and outdate>'{1}' and outdate<'{2}'"
//				child: "select new map(id as id,goodsname as text,goodsdesc as qtip,typeid as nodeId,goodsnumber as number,price as price) from GoodsList"
			}
		})
	});
	var root = new Ext.tree.AsyncTreeNode({
		id : "0",
		text : "销售账单"
	});
	tree_menu.setRootNode(root);
	tree_menu.getRootNode().expand();// 2315
	tree_menu.on("click",function(node){
		if(node.leaf){
			grid.addBtn.setDisabled(false);
			currentId = node.attributes.id;
			ds.load({
		  		params:{
		  			start:-1, 
		  			limit:-1,
		  			action:"hql",
					type:"entity",
					sql:"from GoodsOutputList where outid='"+currentId+"'"
		  		}
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
	  	url:"goodsOutputInfoVo.do",
	  	frame:true,
	  	width:200,
	  	items:[{
	    	name:"tree_tips",
	    	fieldLabel:"账单备注",
	    	allowBlank:false,
	    	width:150,
	    	maxLength:200,
	    	emptyText:"请输入账单的必要备注"
	  	}]
	});
	var node_menu = null;
	var contextmenu = new Ext.menu.Menu({
  		items:[{
    		text:"添加新销售单",
    		iconCls:"add",
    		handler:function(event,mouse){//bcescvr,110400
    			var win = new Ext.Window({
	                layout      : 'fit',
	                width       : 300,
	                title		: "销售单添加",
	                height  	: 120,
	                modal		: true,
	                closeAction : 'hide',
	                allowDomMove: true,
	                bodyBorder 	: false,
	                plain       : true,
					items:[form],
	                buttons: [{
	                    text     : '保存',
	                    handler	 : function(){
	                    	if(form.getForm().isValid()){
		                    	var tree_tips = form.getComponent(0).getValue();//x-tree-node-icon
		                    	var tree_node = null;
		                    	var isRoot = false;
		                    	if(node_menu.isRoot){
		                    		isRoot = true;
		                    		tree_node = new Ext.tree.TreeNode({id:Ext.id(),text:'',qtip:tree_tips,leaf:true});
		                    	}else{
		                    		tree_node = new Ext.tree.TreeNode({id:Ext.id(),text:'',qtip:tree_tips,leaf:true});
		                    	}
		                    	var parentNodeId = node_menu.attributes.id;
	                    		form.getForm().submit({
	                    			success:function(form,action){
	                    				tree_node.attributes.id = action.result.msg;
	                    				tree_node.attributes.text = action.result.msg;
	                    				tree_node.setText(action.result.msg+" ["+tree_tips+"]");
				                    	node_menu.appendChild(tree_node);
				                    	form.reset();
				                        win.hide();
	                    			},
	                    			failure:function(form,action){
	                    				Ext.MessageBox.show({
											title:"错误提示",
											msg:action.result.msg,
											buttons:Ext.MessageBox.OK,
											icon:Ext.MessageBox.ERROR
								   		});
								   		form.reset();
				                        win.hide();
	                    			},params:{
	                    				action:"insert",
	                    				isRoot:isRoot,
	                    				parentNodeId:parentNodeId
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
      			return true;
    		}
  		},{
    		text:"修改销售单",
    		iconCls:"save",
    		handler:function(event,mouse){//bcescvr,110400
            	form.getComponent(0).setValue(node_menu.attributes.qtip);
    			var win = new Ext.Window({
	                layout      : 'fit',
	                width       : 300,
	                title		: "销售单修改",
	                height  	: 120,
	                modal		: true,
	                closeAction : 'hide',
	                allowDomMove: true,
	                bodyBorder 	: false,
	                plain       : true,
					items:[form],
	                buttons: [{
	                    text     : '保存',
	                    handler	 : function(){
	                    	if(form.getForm().isValid()){
		                    	var tree_tips = form.getComponent(0).getValue();//x-tree-node-icon
	                    		form.getForm().submit({
	                    			success:function(form,action){
				                    	node_menu.attributes.qtip = tree_tips;
				                    	form.reset();
				                        win.hide();
	                    			},
	                    			failure:function(form,action){
	                    				Ext.MessageBox.show({
											title:"错误提示",
											msg:action.result.msg,
											buttons:Ext.MessageBox.OK,
											icon:Ext.MessageBox.ERROR
								   		});
								   		form.reset();
				                        win.hide();
	                    			},params:{
	                    				action:"update",
	                    				menu_id:node_menu.attributes.id,
	                    				parent_id:node_menu.parentNode.attributes.id,
	                    				leaf:node_menu.leaf
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
      			return true;
    		}
  		},{
  			text:"刷新",
  			iconCls:"option",
  			handler:function(event,mouse){
  				tree_menu.getRootNode().reload();
  			}
  		},"-",{
  			text:"删除销售单",
  			iconCls:"remove",
  			handler:function(event,mouse){
  				Ext.MessageBox.show({
					title:"警告提示",
					msg:"请确认是否要删除?",
					buttons:Ext.MessageBox.OKCANCEL,
					icon:Ext.MessageBox.WARNING,
					fn:function(btn){
						if(btn=="ok"){
							var isRoot = false;
							if(node_menu.isRoot){
		                    	isRoot = true;
							}
							Ext.Ajax.request({
								url: "goodsOutputInfoVo.do",
							   	success: function(action){
//							   		tree_menu.remove(node_menu);
							   		tree_menu.getRootNode().removeChild(node_menu);
							   		listenerEvent.loadGrid();
							   		grid.addBtn.setDisabled(true);
							   	},
							   	failure: function(action){
							   		Ext.MessageBox.show({
										title:"错误提示",
										msg:"删除失败,请查看日志寻找问题!!",
										buttons:Ext.MessageBox.OK,
										icon:Ext.MessageBox.ERROR
							   		});
							   	},
							   	params: { 
							   		isRoot: isRoot,
							   		isLeaf: node_menu.leaf,
							   		action:"delete",
							   		menu_code:node_menu.attributes.id
							   	}
							});
						}
					}
				});
  			}
  		}]
	});
	tree_menu.on("contextmenu",function(node,e){
  		e.preventDefault();
  		node.select();
  		if(node.isRoot){
  			node_menu = node;
  			contextmenu.items.items[0].show();
  			contextmenu.items.items[1].hide();
  			contextmenu.items.items[2].show();
  			contextmenu.items.items[4].hide();
  			contextmenu.showAt(e.getXY());
  		}else{
  			node_menu = node;
  			contextmenu.items.items[0].hide();
  			contextmenu.items.items[1].show();
  			contextmenu.items.items[2].show();
  			contextmenu.items.items[4].show();
  			contextmenu.showAt(e.getXY());
  		}
	});
	var comboBox = new Ext.form.ComboBox({
  		name: "role_code",
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
	  	readOnly :true,
	  	triggerAction: "all",
	  	valueField: "value",
	  	displayField: "text",
	  	hiddenName: "role_code"
	});
	/**
	 * 参数明细表
	 */
	var editor = new Ext.ux.grid.RowEditor({
        saveText: 'Update'
    });
    editor.slideHide();
	var goods = Ext.data.Record.create([
		{name: "id", type: 'string'},
		{name: "outid", type: 'string'},
		{name: "goodsid", type: 'string'},
		{name: "goodsnumber", type: 'int'},
		{name: "returnnumber", type: 'int'}
    ]);

    var comboBoxType = comboBoxList.comboBoxSql("select distinct a.id,a.typename || '[' || a.storelist || ']' from StoreType a,OutUserList c where a.storelist=c.id.userlist and c.id.tag='2'","","");
	var comboBoxName = comboBoxList.comboBoxSql("select b.id.id,c.goodsname from GoodsList b,StoreList c where b.id.id=c.id and (select d.id.userlist from OutUserList d where d.id.userlist=b.id.userlist and d.id.tag='1' and d.id.outuser='"+Ext.getDom("user_id").value+"') is not null","","");
	comboBoxType.emptyText = "过滤货物";
	comboBoxType.allowBlank = true;
	comboBoxName.allowBlank = false;
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sm,{
			header:"账单编号",
			dataIndex:"outid",
			width:150
		},{
    		header:"货物种类",
    		dataIndex:"goodstype",
    		width:150,
    		sortable: true,
    		renderer:filter,
    		editor: comboBoxType

    	},{
    		header:"货物名称",
    		dataIndex:"goodsid",
    		width:100,
    		sortable: true,
    		editor: comboBoxName,
    		renderer:transform

    	},{
    		header:"出货数量",
    		dataIndex:"goodsnumber",
    		sortable: true,
    		editor: {
                xtype: 'numberfield',
                minValue: 0,
                maxValue: 150000,
                allowBlank: false
            }

    	},{
    		header:"退货数量",
    		dataIndex:"returnnumber",
    		sortable: true,
    		editor: {
                xtype: 'numberfield',
                minValue: 0,
                maxValue: 150000,
                allowBlank: false
            }

    	},{
			header:"明细id",
			dataIndex:"id",
			hidden:true
		}
  	]);
  	cm.defaultSortable = true;
  	function transform(value,record){
  		return trans.comboBox(comboBoxName,value);
  	}
  	function filter(){
		return "<font color='red'>过滤货物</font>";
	}
	comboBoxType.on("select",function(obj,option){
  		var roleCode = option.data.value;
		comboBoxName.getStore().load({
			params:{
				sql:"select b.id.id,c.goodsname from GoodsList b,StoreList c where b.id.id=c.id and b.id.userlist in (select d.id.userlist from OutUserList d where d.id.tag='1' and d.id.outuser='"+Ext.getDom("user_id").value+"') and c.typeid='"+roleCode+"'"
			}
		});
		comboBoxName.setValue("");
  	});
  	var ds = new Ext.data.Store({
    	proxy: new Ext.data.HttpProxy({
    		url:"queryInfoVo.do"
    	}),
		reader: new Ext.data.JsonReader({
			totalProperty:"totalProperty",
			root:"root"
		},goods)
  	});
  	var grid = new Ext.grid.GridPanel({
    	ds:ds,
    	cm:cm,
    	sm:sm,
    	loadMask:true,
    	width : width-595,
    	height : height-180,
    	plugins: [editor],
    	iconCls:"icon-grid",
    	tbar:[{
    		ref: '../addBtn',
            text:'增加货物',
            tooltip:'添加一条新的货物记录',
            iconCls:'add',
            disabled: true,
            handler:function(){
            	/**
            	 * {name: "id", type: 'string'},
		{name: "outid", type: 'string'},
		{name: "goodsid", type: 'string'},
		{name: "goodsnumber", type: 'int'},
		{name: "returnnumber", type: 'int'}
            	 */
            	loading.loadMask().show();
            	editor.setDisabled(true);
            	var record = {
                    id: '',
                    outid: currentId,
                    goodsid:'',
                    goodsnumber:0,
                    returnnumber:0
                };
                Ext.Ajax.request({
	        		url:"goodsOutputInfoVo.do?action=insertRecord",
	        		params:record,
	        		success:function(action){
	        			loading.loadMask().hide();
	        			editor.setDisabled(false);
	        			var json = eval("("+action.responseText+")");
	        			record.id = json.msg;
	    			},
	    			failure:function(action){
	    				var json = eval("("+action.responseText+")");
	    				Ext.MessageBox.show({
							title:"错误提示",
							msg:json.msg,
							buttons:Ext.MessageBox.OK,
							icon:Ext.MessageBox.ERROR
				   		});
	    			}
	        	});
            	var e = new goods(record);
                editor.stopEditing();
                ds.insert(0, e);
                grid.getView().refresh();
                grid.getSelectionModel().selectRow(0);
                editor.startEditing(0);
            }
        }, '-',{
        	ref: '../removeBtn',
            text:'删除货物',
            tooltip:'删除一条货物记录',
            iconCls:'remove',
            disabled: true,
            handler:function(){
            	editor.stopEditing();
                var s = grid.getSelectionModel().getSelections();
                var id = "";
                Ext.MessageBox.show({
					title:"警告提示",
					msg:"请确认是否要删除?",
					buttons:Ext.MessageBox.OKCANCEL,
					icon:Ext.MessageBox.WARNING,
					fn:function(btn){
						if(btn=="ok"){
							for(var i = 0, r; r = s[i]; i++){
			                    ds.remove(r);
			                    id += r.data.id+",";
			                }
							id = id.substring(0,id.length-1);
							Ext.Ajax.request({
				        		url:"goodsOutputInfoVo.do?action=deleteRecord",
				        		params:{
				        			id:id,
				        			outid:currentId
				        		},
				        		success:function(action){
				        			loading.loadMask().hide();
				        			editor.setDisabled(false);
				        			ds.load({
								  		params:{
								  			start:-1, 
								  			limit:-1,
								  			action:"hql",
											type:"entity",
											sql:"from GoodsOutputList where outid='"+currentId+"'"
								  		}
								  	});
				    			},
				    			failure:function(action){
				    				var json = eval("("+action.responseText+")");
				    				Ext.MessageBox.show({
										title:"错误提示",
										msg:json.msg,
										buttons:Ext.MessageBox.OK,
										icon:Ext.MessageBox.ERROR
							   		});
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

	var listenerEvent = {
    	update:function(updateObj,jsonRecord){
    		var recordData = editor.record.data;
    		loading.loadMask().show();
    		Ext.Ajax.request({
    			url:"goodsOutputInfoVo.do?action=updateRecord",
    			params:recordData,
    			success:function(action){
    				var json = eval("("+action.responseText+")");
    				if(json.failure){
    					Ext.MessageBox.show({
							title:"错误提示",
							msg:json.msg,
							buttons:Ext.MessageBox.OK,
							icon:Ext.MessageBox.ERROR
				   		});
				   		listenerEvent.loadGrid();
    				}
    				loading.loadMask().hide();
    			},
    			failure:function(action){
    				var json = eval("("+action.responseText+")");
    				Ext.MessageBox.show({
						title:"错误提示",
						msg:json.msg,
						buttons:Ext.MessageBox.OK,
						icon:Ext.MessageBox.ERROR
			   		});
    			}
    		});
    		comboBoxName.getStore().load({
				params:{
					sql:"select b.id.id,c.goodsname from GoodsList b,StoreList c where b.id.id=c.id and (select d.id.userlist from OutUserList d where d.id.userlist=b.id.userlist and d.id.tag='1' and d.id.outuser='"+Ext.getDom("user_id").value+"') is not null"
				}
			});
    	},
    	loadGrid:function(){
    		ds.load({
		  		params:{
		  			start:-1, 
		  			limit:-1,
		  			action:"hql",
					type:"entity",
					sql:"from GoodsOutputList where outid='"+currentId+"'"
		  		}
		  	});
    	}
    }
	editor.addListener("afteredit",listenerEvent.update);
	editor.addListener("canceledit",function(){
		comboBoxName.getStore().load({
			params:{
				sql:"select b.id.id,c.goodsname from GoodsList b,StoreList c where b.id.id=c.id and (select d.id.userlist from OutUserList d where d.id.userlist=b.id.userlist and d.id.tag='1' and d.id.outuser='"+Ext.getDom("user_id").value+"') is not null"
			}
		});
	});
	new Ext.Panel({
        title: '货物参数维护',
        collapsible:true,
        renderTo: 'output_goods_name',
        width : 350,
        height : height-100,
        items:[
	        new Ext.TabPanel({
		        border:false,
		        activeTab:0,
		        width : 350,
		        height : height-180,
		        tabPosition:'bottom',
		        items:[
		        	tree_menu
		        ]
		    })
		]
    });
    
    new Ext.Panel({
        title: '货物明细列表',
        collapsible:true,
        width : width-495,
        renderTo: 'output_goods_list',
        height : height-100,
        items:[grid]
    });
    grid.getSelectionModel().on('selectionchange', function(sm){
        grid.removeBtn.setDisabled(sm.getCount() < 1);
    });

});