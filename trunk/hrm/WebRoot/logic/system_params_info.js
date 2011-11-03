Ext.onReady(function() {
	Ext.QuickTips.init();
	var height = Ext.getBody().getHeight()-20;
	var width = Ext.getBody().getWidth();
	/**
	 * 树形
	 */
	var currentId = "";
	var currentSalaryList ;
	var tree_menu = new Ext.tree.TreePanel({
		title:"参数类型选择",
		width : 300,
		autoScroll:true,
		height : height-500,
		loader : new Ext.tree.TreeLoader({
			dataUrl : "treeInfoQuery.do?action=single",
			baseParams :{
				parent: "select new map(id as id,typename as text,typedesc as qtip) from ParamsType"
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
			grid.addBtn.setDisabled(false);
			currentId = node.attributes.id;
			ds.load({
		  		params:{
		  			start:0, 
		  			limit:10,
		  			action:"hql",
					type:"entity",
					sql:"from ParamsList where typeid='"+currentId+"'"
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
	  	url:"paramsTypeVo.do",
	  	frame:true,
	  	width:200,
	  	items:[{
	    	name:"tree_code",
	    	fieldLabel:"参数编号",
	    	allowBlank:false,
	    	width:150,
	    	maxLength:50
	  	},{
	    	name:"tree_name",
	    	fieldLabel:"参数名称",
	    	allowBlank:false,
	    	width:150,
	    	maxLength:50
	  	},{
	    	name:"tree_tips",
	    	fieldLabel:"参数提示",
	    	allowBlank:false,
	    	width:150,
	    	maxLength:100,
	    	emptyText:"默认和参数名称一致"
	  	}]
	});
	var node_menu = null;
	var contextmenu = new Ext.menu.Menu({
  		items:[{
    		text:"添加新节点",
    		iconCls:"add",
    		handler:function(event,mouse){//bcescvr,110400
    			form.getComponent(0).setDisabled(false);
    			var win = new Ext.Window({
	                layout      : 'fit',
	                width       : 300,
	                title		: "参数添加",
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
	                    		var tree_name = form.getComponent(1).getValue();
		                    	var tree_tips = form.getComponent(2).getValue()==""?tree_name:form.getComponent(1).getValue();//x-tree-node-icon
		                    	var tree_node = null;
		                    	var isRoot = false;
		                    	if(node_menu.isRoot){
		                    		isRoot = true;
		                    		tree_node = new Ext.tree.TreeNode({id:form.getComponent(0).getValue(),text:tree_name,qtip:tree_tips,leaf:true});
		                    	}else{
		                    		tree_node = new Ext.tree.TreeNode({id:form.getComponent(0).getValue(),text:tree_name,qtip:tree_tips,leaf:true});
		                    	}
		                    	var parentNodeId = node_menu.attributes.id;
	                    		form.getForm().submit({
	                    			success:function(form,action){
				                    	node_menu.appendChild(tree_node);
				                    	form.reset();
				                        win.hide();
	                    			},failure:function(form,action){
	                    				Ext.MessageBox.show({
											title:"错误提示",
											msg:action.result.msg,
											buttons:Ext.MessageBox.OKCANCEL,
											icon:Ext.MessageBox.ERROR
	                    				});
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
    			form.getComponent(0).setDisabled(true);
    			form.getComponent(0).setValue(node_menu.attributes.id);
    			form.getComponent(1).setValue(node_menu.attributes.text);
            	form.getComponent(2).setValue(node_menu.attributes.qtip);
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
	                    		var tree_name = form.getComponent(1).getValue();
		                    	var tree_tips = form.getComponent(2).getValue()==""?tree_name:form.getComponent(1).getValue();//x-tree-node-icon
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
								url: "paramsTypeVo.do",
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
	
	/**
	 * 参数明细表
	 */
	var editor = new Ext.ux.grid.RowEditor({
        saveText: 'Update'
    });
	var params = Ext.data.Record.create([
		{name: "id", type: 'string'},
		{name: "typeid", type: 'string'},
		{name: "paramscode", type: 'string'},
		{name: "paramsname", type: 'string'},
		{name: "paramsdesc", type: 'string'},
		{name: "updtuser", type: 'float'},
    	{name: "updttime",type:"date",dateFormat:"Y-m-d H:i:s.u"},
    ]);

	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sm,
		{
			header:"ID",
			dataIndex:"id",
			hidden:true,
			sortable: true
		},{
			header:"父节点参数编号",
			width:150,
			sortable: true,
			dataIndex:"typeid"
		},{
    		header:"参数编号",
    		dataIndex:"paramscode",
    		sortable: true,
    		width:150,
    		editor: {
                xtype: 'textfield',
                maxLength:50,
                allowBlank: false
            }
    	},{
    		header:"参数名称",
    		dataIndex:"paramsname",
    		sortable: true,
    		width:150,
    		editor: {
                xtype: 'textfield',
                maxLength:50,
                allowBlank: false
            }
    	},{
    		header:"参数描述",
    		dataIndex:"paramsdesc",
    		width:250,
    		sortable: true,
    		editor: {
                xtype: 'textfield',
                maxLength:200,
                allowBlank: false
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
		}
  	]);
  	cm.defaultSortable = true;
  	var ds = new Ext.data.Store({
    	proxy: new Ext.data.HttpProxy({
    		url:"queryInfoVo.do"
    	}),
		reader: new Ext.data.JsonReader({
			totalProperty:"totalProperty",
			root:"root"
		},params)
  	});
  	
  	var grid = new Ext.grid.GridPanel({
    	ds:ds,
    	cm:cm,
    	sm:sm,
    	loadMask:true,
    	height : height-180,
    	plugins: [editor],
    	iconCls:"icon-grid",
    	tbar:[{
    		ref: '../addBtn',
            text:'增加参数',
            tooltip:'添加一条新的参数记录',
            iconCls:'add',
            disabled: true,
            handler:function(){
            	loading.loadMask().show();
            	editor.setDisabled(true);
            	var record = {
                    id: '',
                    typeid: currentId,
                    paramscode:'0',
                    paramsname:'',
                    paramsdesc:''
                };
                Ext.Ajax.request({
	        		url:"paramsTypeVo.do?action=insertRecord",
	        		params:record,
	        		success:function(action){
	        			loading.loadMask().hide();
	        			editor.setDisabled(false);
	        			var json = eval("("+action.responseText+")");
	        			record.id = json.msg;
	        			var e = new params(record);
		                editor.stopEditing();
		                ds.insert(0, e);
		                grid.getView().refresh();
		                grid.getSelectionModel().selectRow(0);
		                editor.startEditing(0);
	    			},
	    			failure:function(action){
	    				return false;
	    			}
	        	});
            	
            }
        }, '-',{
        	ref: '../removeBtn',
            text:'删除参数',
            tooltip:'删除一条参数记录',
            iconCls:'remove',
            disabled: true,
            handler:function(){
            	editor.stopEditing();
                var s = grid.getSelectionModel().getSelections();
                Ext.MessageBox.show({
					title:"警告提示",
					msg:"请确认是否要删除?",
					buttons:Ext.MessageBox.OKCANCEL,
					icon:Ext.MessageBox.WARNING,
					fn:function(btn){
						if(btn=="ok"){
							var id = "";
			                for(var i = 0, r; r = s[i]; i++){
			                    ds.remove(r);
			                    id += r.data.id+",";
			                }
							id = id.substring(0,id.length-1);
//							loading.loadMask().show();
							Ext.Ajax.request({
				        		url:"paramsTypeVo.do?action=deleteRecord",
				        		params:{
				        			id:id,
				        			typeid:currentId
				        		},
				        		success:function(action){
//				        			loading.loadMask().hide();
				        			editor.setDisabled(false);
				        			ds.load({
								  		params:{
								  			start:0, 
								  			limit:10,
								  			action:"hql",
											type:"entity",
											sql:"from ParamsList where typeid='"+currentId+"'"
								  		}
								  	});
				    			},
				    			failure:function(action){
				    				return false;
				    			}
				        	});
						}
					}
                });
                
            }
        }],
    	bbar:new Ext.PagingToolbar({
		    pageSize:10,
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
    			url:"paramsTypeVo.do?action=updateRecord",
    			params:recordData,
    			success:function(){
    				loading.loadMask().hide();
    			}
    		});
    	},
    	loadGrid:function(){
    		ds.load({
		  		params:{
		  			start:0, 
		  			limit:10,
		  			action:"hql",
					type:"entity",
					sql:"from ParamsList where typeid='"+currentId+"'"
		  		}
		  	});
    	}
    	
    }
	editor.addListener("afteredit",listenerEvent.update);
	
	new Ext.Panel({
        title: '参数参数维护',
        collapsible:true,
        renderTo: 'system_params_info_name',
        width : 250,
        height : height-100,
        items:[
	        new Ext.TabPanel({
		        border:false,
		        activeTab:0,
		        width : 250,
		        height : height-180,
		        tabPosition:'bottom',
		        items:[
		        	tree_menu
		        ]
		    })
		]
    });
    
    new Ext.Panel({
        title: '参数明细列表',
        collapsible:true,
        width : width-495,
        renderTo: 'system_params_info_list',
        height : height-100,
        items:[grid]
    });
    grid.getSelectionModel().on('selectionchange', function(sm){
        grid.removeBtn.setDisabled(sm.getCount() < 1);
    });

});