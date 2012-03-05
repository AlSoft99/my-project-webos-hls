Ext.onReady(function() {
	Ext.QuickTips.init();
	var height = Ext.getBody().getHeight()-20;
	var width = Ext.getBody().getWidth();
	/**
	 * 树形
	 */
	var currentId = "";
	var currentSalaryList ;
	var currentDate = "";
	var tree_menu = new Ext.tree.TreePanel({
		title:"参数类型选择",
		width : 300,
		autoScroll:true,
		height : height-500,
		loader : new Ext.tree.TreeLoader({
			dataUrl : "treeInfoQuery.do?action=single",
			baseParams :{
				parent: "select new map(id as id,typename as text,typedesc as qtip) from MaterialType"
//				child: "select new map(id as id,goodsname as text,goodsdesc as qtip,typeid as nodeId,goodsnumber as number,price as price) from GoodsList"
			}
		})
	});
	var root = new Ext.tree.AsyncTreeNode({
		id : "0",
		text : "种类"
	});
	function checkStore(){
		Ext.Ajax.request({
		   url: 'materialStoreVo.do',
		   success: function(o){
				if(typeof(suCallback)!="undefined"){
					suCallback(o);
				}
		   },
		   failure: function(o){
				if(typeof(faCallback)!="undefined"){
					faCallback(o);
				}
		   },
		   params: { storedate: currentDate, action:"check" }
		});
	};
	checkStore();
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
					sql:"from MaterialList where typeid='"+currentId+"'"
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
	  	url:"materialTypeVo.do",
	  	frame:true,
	  	width:200,
	  	items:[{
	    	name:"tree_name",
	    	fieldLabel:"种类名称",
	    	allowBlank:false,
	    	width:150,
	    	maxLength:50
	  	},{
	    	name:"tree_tips",
	    	fieldLabel:"种类描述",
	    	allowBlank:false,
	    	width:150,
	    	maxLength:100,
	    	emptyText:"默认和种类名称一致"
	  	}]
	});
	var node_menu = null;
	var contextmenu = new Ext.menu.Menu({
  		items:[{
    		text:"添加新种类",
    		iconCls:"add",
    		handler:function(event,mouse){//bcescvr,110400
    			form.getComponent(0).setDisabled(false);
    			var win = new Ext.Window({
	                layout      : 'fit',
	                width       : 300,
	                title		: "种类添加",
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
		                    		tree_node = new Ext.tree.TreeNode({id:form.getComponent(0).getValue(),text:tree_name,qtip:tree_tips,leaf:true});
		                    	}else{
		                    		tree_node = new Ext.tree.TreeNode({id:form.getComponent(0).getValue(),text:tree_name,qtip:tree_tips,leaf:true});
		                    	}
		                    	var parentNodeId = node_menu.attributes.id;
	                    		form.getForm().submit({
	                    			success:function(form,action){
	                    				tree_node.attributes.id = action.result.msg;
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
    		text:"修改种类",
    		iconCls:"save",
    		handler:function(event,mouse){//bcescvr,110400
    			form.getComponent(0).setValue(node_menu.attributes.text);
            	form.getComponent(1).setValue(node_menu.attributes.qtip);
    			var win = new Ext.Window({
	                layout      : 'fit',
	                width       : 300,
	                title		: "种类修改",
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
  			text:"删除种类",
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
								url: "materialTypeVo.do",
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
		{ name: "paramsname", type: 'string' },
        { name: "cost", type: 'float' },
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
			hidden:true,
			dataIndex:"typeid"
		},{
    		header:"编号",
    		dataIndex:"paramscode",
    		sortable: true,
    		width:150,
    		hidden:true
    	},{
    		header:"名称",
    		dataIndex:"paramsname",
    		sortable: true,
    		width:150,
    		editor: {
                xtype: 'textfield',
                maxLength:50,
                allowBlank: false
            }
        }, {
            xtype: 'numbercolumn',
            header: "价格",
            dataIndex: "cost",
            sortable: true,
            width: 150,
            format : "￥0,0.00",
            editor: {
                xtype: 'numberfield',
                minValue: 0,
                maxValue: 150000,
                allowBlank: false
            }
        }, {
    		header:"原材料描述",
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
            text:'增加原材料',
            tooltip:'添加一条新的原材料记录',
            iconCls:'add',
            disabled: true,
            handler:function(){
            	loading.loadMask().show();
            	editor.setDisabled(true);
            	var record = {
                    id: '',
                    typeid: currentId,
                    paramscode: '0',
                    cost: '0',
                    paramsname:'',
                    paramsdesc:''
                };
                Ext.Ajax.request({
	        		url:"materialTypeVo.do?action=insertRecord",
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
            text:'删除原材料',
            tooltip:'删除一条原材料记录',
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
				        		url:"materialTypeVo.do?action=deleteRecord",
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
											sql:"from MaterialList where typeid='"+currentId+"'"
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
    			url:"materialTypeVo.do?action=updateRecord",
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
					sql:"from MaterialList where typeid='"+currentId+"'"
		  		}
		  	});
    	}
    	
    }
	editor.addListener("afteredit",listenerEvent.update);
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
	var monthBtn = new Ext.Button({
		text:"查询",
		iconCls:"icon-grid",
		tooltip:'请选择月份查询',
		handler:function(event,mouse){
			currentDate = comboBoxDate.getValue();
			console.log("currentDate:"+currentDate);
			tree_menu.getRootNode().reload();
		}
	});
	var toolbar = new Ext.Toolbar({
		items:[
			comboBoxDate,
			monthBtn
		]
	});
	new Ext.Panel({
        title: '种类',
        collapsible:true,
        renderTo: 'menu_foot_month_start_name',
        width : 250,
        height : height-100,
        tbar:toolbar,
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
        title: '原材料明细列表',
        collapsible:true,
        width : width-495,
        renderTo: 'menu_foot_month_start_list',
        height : height-100,
        items:[grid]
    });
    grid.getSelectionModel().on('selectionchange', function(sm){
        grid.removeBtn.setDisabled(sm.getCount() < 1);
    });

});