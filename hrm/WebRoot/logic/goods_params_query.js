Ext.onReady(function() {
	Ext.QuickTips.init();
	var height = Ext.getBody().getHeight()-20;
	var width = Ext.getBody().getWidth();
	/**
	 * 树形
	 */
	var currentId = "";
	var currentSalaryList ;
	var comboBoxName = comboBoxList.comboBoxSql("select distinct id.userlist,id.userlist from OutUserList where id.tag='2'","","");
	var comboBoxName1 = comboBoxList.comboBoxSql("select distinct id.userlist,id.userlist from OutUserList where id.tag='1'","","");
	comboBoxName1.getStore().on("load",function(a,json){
		if(json.length>0){
			var value = json[0].data.value;
			comboBoxName1.setValue(value);
		}
	});
	var toolbar = new Ext.Toolbar({
		items:[
			"仓库货物:",
			comboBoxName,
			"销售仓库:",
			comboBoxName1,
			{
				text:"查询",
				disabled:true,
				iconCls:"icon-grid",
				tooltip:'请选择仓库查询',
				handler:function(event,mouse){
					tree_menu.loader.baseParams.parent = "select new map(a.id as id,a.typename as text,a.typedesc as qtip) from StoreType a where a.storelist='"+comboBoxName.getValue()+"'"
					tree_menu.getRootNode().setText("参数种类 ["+toolbar.getComponent(1).getRawValue()+"]");
					tree_menu.getRootNode().reload();
					currentId = "";
					listenerEvent.loadGrid();
				}
			}
		]//||' ['||c.userName||']'
	});
	/*comboBoxName.on("select",function(){
		toolbar.getComponent(2).setDisabled(toolbar.getComponent(1).getValue()=="");
	});*/
	comboBoxName.on("change",function(a,b,c){
		toolbar.getComponent(4).setDisabled(toolbar.getComponent(1).getValue()=="");
	});
	var tree_menu = new Ext.tree.TreePanel({
		title:"货物类型选择",
		width : 450, 
		autoScroll:true,
		height : height-500,
		tbar:toolbar,
		loader : new Ext.tree.TreeLoader({
			dataUrl : "treeInfoQuery.do?action=single",
			baseParams :{
				parent: "select new map(a.id as id,a.typename as text,a.typedesc as qtip) from StoreType a,OutUserList b where a.storelist=b.id.userlist and b.id.tag='2' and b.id.outuser='"+toolbar.getComponent(1).getValue()+"'"
//				child: "select new map(id as id,goodsname as text,goodsdesc as qtip,typeid as nodeId,goodsnumber as number,price as price) from GoodsList"
			}
		})
	});
	var root = new Ext.tree.AsyncTreeNode({
		id : "0",
		text : "参数种类"
	});
	tree_menu.setRootNode(root);
	tree_menu.getRootNode().expand();// 2315
	tree_menu.on("click",function(node){
		if(node.leaf){
			currentId = node.attributes.id;
//			comboBoxName = comboBoxList.comboBoxSql("select b.id,b.goodsname||' ['||c.userName||']' from StoreType a,StoreList b,UserInfo c where a.id = b.typeid and c.userId = a.outuser and a.id='"+currentId+"'","","",loadComboData);
//			grid.getColumnModel().setEditor(4,comboBoxName);
//			grid.addBtn.setDisabled(false);
			listenerEvent.loadGrid();
		}else{
//			grid.addBtn.setDisabled(true);
		}
	});
/*	var loadComboData = function(){
		grid.getColumnModel().config[4].editor = comboBoxName;
		
	}*/
	/**
	 * 右键菜单
	 */
    var form = new Ext.form.FormPanel({
	  	defaultType:"textfield",
	  	labelAlign:"right",
	  	labelWidth:80,
	  	url:"goodsTypeVo.do",
	  	frame:true,
	  	width:200,
	  	items:[{
	    	name:"tree_name",
	    	fieldLabel:"节点名称",
	    	allowBlank:false,
	    	width:150,
	    	maxLength:50
	  	},{
	    	name:"tree_tips",
	    	fieldLabel:"节点提示",
	    	allowBlank:false,
	    	width:150,
	    	maxLength:100,
	    	emptyText:"默认和节点名称一致"
	  	}]
	});
	var node_menu = null;
	var contextmenu = new Ext.menu.Menu({
  		items:[{
    		text:"添加新种类",
    		iconCls:"add",
    		handler:function(event,mouse){//bcescvr,110400
    			if(toolbar.getComponent(1).getValue()==""){
    				Ext.MessageBox.show({
						title:"错误提示",
						msg:"请选择一个仓库再进行添加!",
						buttons:Ext.MessageBox.OK,
						icon:Ext.MessageBox.ERROR
					});
					return false;
    			}
    			var win = new Ext.Window({
	                layout      : 'fit',
	                width       : 300,
	                title		: "节点添加",
	                height  	: 160,
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
	                    		var tree_name = form.getComponent(0).getValue();
		                    	var tree_tips = form.getComponent(1).getValue()==""?tree_name:form.getComponent(1).getValue();//x-tree-node-icon
		                    	var tree_node = null;
		                    	var isRoot = false;
		                    	if(node_menu.isRoot){
		                    		isRoot = true;
		                    		tree_node = new Ext.tree.TreeNode({id:Ext.id(),text:tree_name,qtip:tree_tips,leaf:true});
		                    	}else{
		                    		tree_node = new Ext.tree.TreeNode({id:Ext.id(),text:tree_name,qtip:tree_tips,leaf:true});
		                    	}
		                    	var parentNodeId = node_menu.attributes.id;
	                    		form.getForm().submit({
	                    			success:function(form,action){
	                    				tree_node.attributes.id = action.result.msg;
				                    	node_menu.appendChild(tree_node);
				                    	form.reset();
				                        win.hide();
	                    			},params:{
	                    				action:"insert",
	                    				isRoot:isRoot,
	                    				parentNodeId:parentNodeId,
	                    				outuser:toolbar.getComponent(1).getValue()
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
    		text:"修改节点",
    		iconCls:"save",
    		handler:function(event,mouse){//bcescvr,110400
    			form.getComponent(0).setValue(node_menu.attributes.text);
            	form.getComponent(1).setValue(node_menu.attributes.qtip);
    			var win = new Ext.Window({
	                layout      : 'fit',
	                width       : 300,
	                title		: "节点修改",
	                height  	: 160,
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
	                    		var tree_name = form.getComponent(0).getValue();
		                    	var tree_tips = form.getComponent(1).getValue()==""?tree_name:form.getComponent(1).getValue();//x-tree-node-icon
	                    		form.getForm().submit({
	                    			success:function(form,action){
				                    	node_menu.attributes.text = tree_name;
				                    	node_menu.attributes.qtip = tree_tips;
				                    	node_menu.setText(tree_name);
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
  			text:"删除节点",
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
								url: "goodsTypeVo.do",
							   	success: function(action){
//							   		tree_menu.remove(node_menu);
							   		tree_menu.getRootNode().removeChild(node_menu);
							   		listenerEvent.loadGrid();
//							   		grid.addBtn.setDisabled(true);
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
  			contextmenu.items.items[0].hide();
  			contextmenu.items.items[1].hide();
  			contextmenu.items.items[2].show();
  			contextmenu.items.items[4].hide();
  			contextmenu.showAt(e.getXY());
  		}else{
  			node_menu = node;
  			contextmenu.items.items[0].hide();
  			contextmenu.items.items[1].hide();
  			contextmenu.items.items[2].show();
  			contextmenu.items.items[4].hide();
  			contextmenu.showAt(e.getXY());
  		}
	});
	
	/**
	 * 参数明细表
	 */
	var editor = new Ext.ux.grid.RowEditor({
        saveText: 'Update'
    });
	var goods = Ext.data.Record.create([
		{name: "id", type: 'string'},
		{name: "typeid", type: 'string'},
		{name: "goodsname", type: 'string'},
		{name: "goodsdesc", type: 'string'},
		{name: "goodsnumber", type: 'int'},
		{name: "price", type: 'float'},
		{name: "updtuser", type: 'float'},
    	{name: "updttime",type:"date",dateFormat:"Y-m-d H:i:s.u"}
    ]);

	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sm,
		{
			header:"参数id",
			dataIndex:"id",
			hidden:true
		},{
			header:"父节点参数id",
			dataIndex:"typeid",
			hidden:true
		},{
    		header:"货物名称",
    		dataIndex:"goodsname",
    		sortable: true
//    		renderer:transform,
//    		editor:comboBoxName
    	},{
    		header:"货物描述",
    		dataIndex:"goodsdesc",
    		width:150,
    		sortable: true
    	},{
    		header:"初始数量",
    		dataIndex:"goodsnumber",
    		sortable: true,
    		editor: {
                xtype: 'numberfield',
                minValue: 0,
                maxValue: 150000,
                allowBlank: true
            },
            renderer:function(data){
            	if(isNaN(data)){
            		return 0;
            	}else{
            		return data;
            	}	
            }

    	},{
			xtype: 'datecolumn',
			header:"最后更新时间",
			sortable: true,
			dataIndex:"updttime",
			width:150,
			format: 'Y-m-d H:i:s.u',
			hidden:true
		},{
			header:"最后更新人",
			sortable: true,
			width:150,
			hidden:true,
			dataIndex:"updtuser"
		},{
    		xtype: 'numbercolumn',
    		header:"货物价格",
    		dataIndex:"price",
    		sortable: true,
    		format: '￥0,0.00'
    	}
  	]);
  	cm.defaultSortable = true;
  	var ds = new Ext.data.Store({
    	proxy: new Ext.data.HttpProxy({
    		url:"queryInfoVo.do"
    	}),
    	baseParams:{
    		action:"hql",
    		type:"map",
    		sql:""
    	},
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
    	height : height-180,
    	plugins: [editor],
    	iconCls:"icon-grid",
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
    			url:"goodsTypeVo.do?action=updateRecord&userlist="+comboBoxName1.getValue(),
    			params:recordData,
    			success:function(){
    				loading.loadMask().hide();
    			}
    		});
    	},//coalesce
    	loadGrid:function(){
    		ds.baseParams.sql = "select new map(a.id as id,a.goodsname as goodsname,a.outnumber as outnumber,a.innumber as innumber,(select b.goodsnumber from GoodsList b where b.id.id=a.id and b.id.userlist='"+toolbar.getComponent(3).getValue()+"') as goodsnumber,a.goodsdesc as goodsdesc,a.price as price) from StoreList a where a.typeid='"+currentId+"'";
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
        title: '货物参数查询',
        collapsible:true,
        renderTo: 'goods_params_query_name',
        width : 500,
        height : height-100,
        items:[
	        new Ext.TabPanel({
		        border:false,
		        activeTab:0,
		        width : 500,
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
        width : width-745,
        renderTo: 'goods_params_query_list',
        height : height-100,
        items:[grid]
    });
    grid.getSelectionModel().on('selectionchange', function(sm){
//        grid.removeBtn.setDisabled(sm.getCount() < 1);
    });

});