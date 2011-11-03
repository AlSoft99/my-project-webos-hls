/**
 * 销售总查询
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	var height = Ext.getBody().getHeight()-20;
	var width = Ext.getBody().getWidth();
	/**
	 * 树形
	 */
	var currentId = "";
	var curentStartDate = (new Date()).add(Date.DAY,-1).clearTime().format("Y-m-d");
	var curentEndDate = (new Date()).add(Date.DAY,-1).clearTime().format("Y-m-d");
	var currentSalaryList ;
	var toolbar = new Ext.Toolbar([
		"开始:",
		new Ext.form.DateField({
			emptyText:"默认昨天"
		}),
		"结束:",
		new Ext.form.DateField({
			emptyText:"默认昨天"
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
				tree_menu.getRootNode().reload();
			}
		}
	]);
	var tree_menu = new Ext.tree.TreePanel({
		title:"出货账单选择",
		width : 350,
		height : height-500,
		autoScroll:true,
		tbar:toolbar,
		loader : new Ext.tree.TreeLoader({
			dataUrl : "treeInfoQuery.do?action=singledate",
			baseParams :{
				start:curentStartDate,//对应{1}
				end:curentEndDate,//对应{2}
				parent: "select new map(id as id,id||' ['||goodsdesc||']' as text,goodsdesc as qtip) from GoodsOutputInfo where outdate>'{1}' and outdate<'{2}'"
//				child: "select new map(id as id,goodsname as text,goodsdesc as qtip,typeid as nodeId,goodsnumber as number,price as price) from GoodsList"
			}
		})
	});
	var root = new Ext.tree.AsyncTreeNode({
		id : "0",
		text : "出货账单"
	});
	tree_menu.setRootNode(root);
	tree_menu.getRootNode().expand();// 2315
	tree_menu.on("click",function(node){
		if(node.leaf){
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
    		text:"添加新节点",
    		iconCls:"add",
    		handler:function(event,mouse){//bcescvr,110400
    			var win = new Ext.Window({
	                layout      : 'fit',
	                width       : 300,
	                title		: "节点添加",
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
	                    				tree_node.setText(action.result.msg);
				                    	node_menu.appendChild(tree_node);
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
    		text:"修改节点",
    		iconCls:"save",
    		handler:function(event,mouse){//bcescvr,110400
            	form.getComponent(0).setValue(node_menu.attributes.qtip);
    			var win = new Ext.Window({
	                layout      : 'fit',
	                width       : 300,
	                title		: "节点修改",
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
								url: "goodsOutputInfoVo.do",
							   	success: function(action){
//							   		tree_menu.remove(node_menu);
							   		tree_menu.getRootNode().removeChild(node_menu);
							   		listenerEvent.loadGrid();
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
	  	forceSelection :true,
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
	var goods = Ext.data.Record.create([
		{name: "id", type: 'string'},
		{name: "outid", type: 'string'},
		{name: "goodsid", type: 'string'},
		{name: "goodsnumber", type: 'int'},
		{name: "returnnumber", type: 'int'}
    ]);
	var comboBoxName = comboBoxList.comboBoxSql("select b.id.id,c.goodsname from GoodsList b,StoreList c where b.id.id=c.id ","","");
	comboBoxName.readOnly =true;
	var goodsListBox = comboBoxList.goodsList;
    goodsListBox.allowBlank=true;
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sm,
		{
			header:"明细id",
			dataIndex:"id",
			hidden:true
		},{
			header:"账单编号",
			dataIndex:"outid",
			width:150
		},{
    		header:"货物种类",
    		dataIndex:"goodstype",
    		sortable: true,
    		renderer:filter

    	},{
    		header:"货物名称",
    		dataIndex:"goodsid",
    		sortable: true,
    		renderer:transform,
    		editor:comboBoxName

    	},{
    		header:"出货数量",
    		dataIndex:"goodsnumber",
    		sortable: true,
    		editor: {
                xtype: 'numberfield',
                allowBlank: false,
                minValue: 0,
                maxValue: 1500000
            }

    	},{
    		header:"退货数量",
    		dataIndex:"returnnumber",
    		sortable: true,
    		editor: {
                xtype: 'numberfield',
                allowBlank: false,
                minValue: 0,
                maxValue: 1500000
            }

    	}
  	]);
  	cm.defaultSortable = true;
  	function transform(value,record){
  		return trans.comboBox(goodsListBox,value);
  	}
  	function filter(){
		return "<font color='red'>过滤货物</font>";
	}
	comboBoxList.goodsType.on("select",function(obj,option){
  		var roleCode = option.data.value;
		goodsListBox.getStore().load({
			params:{option:"goodsList",change:roleCode}
		});
		goodsListBox.setValue("");
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
    	iconCls:"icon-grid",
    	plugins: [editor],
    	
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
    			url:"goodsOutputInfoVo.do?action=updateAfterRecord",
    			params:recordData,
    			success:function(){
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
	
	new Ext.Panel({
        title: '货物销售单',
        collapsible:true,
        renderTo: 'goods_all_query_name',
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
        renderTo: 'goods_all_query_list',
        height : height-100,
        items:[grid]
    });
    grid.getSelectionModel().on('selectionchange', function(sm){
    });

});