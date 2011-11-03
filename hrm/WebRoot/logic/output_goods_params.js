Ext.onReady(function() {
	Ext.QuickTips.init();
	var height = Ext.getBody().getHeight()-20;
	var width = Ext.getBody().getWidth();
	/**
	 * 树形
	 */
	var currentId = "";
	var currentSalaryList ;
	var comboBoxName = comboBoxList.comboBoxSql("","","");;
	var tree_menu = new Ext.tree.TreePanel({
		title:"货物类型选择",
		width : 350, 
		autoScroll:true,
		height : height-500,
		loader : new Ext.tree.TreeLoader({
			dataUrl : "treeInfoQuery.do?action=single",
			baseParams :{
				parent: "select distinct new map(id.userlist as id,id.userlist as text,id.userlist as qtip) from OutUserList where id.tag='1'"
//				child: "select new map(id as id,goodsname as text,goodsdesc as qtip,typeid as nodeId,goodsnumber as number,price as price) from GoodsList"
			}
		})
	});
	var root = new Ext.tree.AsyncTreeNode({
		id : "0",
		text : "仓库名称"
	});
	tree_menu.setRootNode(root);
	tree_menu.getRootNode().expand();// 2315
	tree_menu.on("click",function(node){
		currentId = node.attributes.id;
		if(node.leaf){
			loading.loadMask().show();
			listenerEvent.loadGrid();
			
		}else{
//			grid.addBtn.setDisabled(true);
		}
		Ext.getCmp("output_goods_params_role_code").setValue("");
		Ext.getCmp("output_goods_params_save").setDisabled(currentId=="0");
		Ext.getCmp("output_goods_params_query").setDisabled(currentId=="0");
	});
	/**
	 * 右键菜单
	 */
    var form = new Ext.form.FormPanel({
	  	defaultType:"textfield",
	  	labelAlign:"right",
	  	labelWidth:80,
	  	url:"storeOutuserVo.do",
	  	frame:true,
	  	width:250,
	  	items:[
	  		new Ext.form.ComboBox({
		  		name: "roleCode",
			  	store: new Ext.data.Store({
		            proxy:new Ext.data.HttpProxy({url:"optionServlet.do?option=roleInfo"}),
		            autoLoad:true,
					reader:new Ext.data.ArrayReader({},[
					    {name:"value"},
					    {name:"text"}
					])
		        }),
		        fieldLabel: '角色选择',
			  	emptyText: "过滤名称",
			  	mode: "local",
			  	width:150,
			  	forceSelection :true,
			  	triggerAction: "all",
			  	valueField: "value",
			  	displayField: "text",
			  	hiddenName: "roleCode",
			  	listeners:{
			  		select:function(obj,option){
			  			var value = option.data.value;
			  			form.getComponent(1).getStore().load({
			  				params:{sql:"select userId,userName from UserInfo where userId not in (select id.outuser from OutUserList where id.tag='1') and roleInfo.roleCode='"+value+"'"}
			  			});
			  			form.getComponent(1).setValue("");
			  		}
			  	}
			}),new Ext.form.ComboBox({
		  		name: "outuser",
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
		        fieldLabel: '用户选择',
			  	emptyText: "请选择",
			  	width:150,
			  	mode: "local",
			  	forceSelection :true,
			  	triggerAction: "all",
			  	valueField: "value",
			  	displayField: "text",
			  	hiddenName: "outuser"
			  	/*,
			  	listeners:{
			  		select:function(obj,option){
			  			tree_menu.loader.baseParams.parent = "select new map(id as id,typename as text,typedesc as qtip) from StoreType where outuser='"+toolbar.getComponent(3).getValue()+"'"
						tree_menu.getRootNode().reload();
						currentId = "";
						listenerEvent.loadGrid();
			  		}
			  	}*/
			})
	  	]
	});
	var node_menu = null;
	var contextmenu = new Ext.menu.Menu({
  		items:[{
    		text:"添加新仓库",
    		iconCls:"add",
    		handler:function(event,mouse){//bcescvr,110400
    			//select userId,userName from UserInfo where userId not in (select id.outuser from OutUserList)
    			form.getComponent(1).getStore().load({
	  				params:{sql:"select userId,userName from UserInfo where userId not in (select id.outuser from OutUserList where id.tag='1')"}
	  			});
	  			form.getComponent(1).setValue("");
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
		                    	var tree_node = null;
		                    	var isRoot = false;
		                    	if(node_menu.isRoot){
		                    		isRoot = true;
		                    		tree_node = new Ext.tree.TreeNode({id:Ext.id(),text:"",qtip:Ext.id(),leaf:true});
		                    	}else{
		                    		tree_node = new Ext.tree.TreeNode({id:Ext.id(),text:"",qtip:Ext.id(),leaf:true});
		                    	}
		                    	var parentNodeId = node_menu.attributes.id;
	                    		form.getForm().submit({
	                    			success:function(form,action){
	                    				tree_node.attributes.id = action.result.msg;
	                    				tree_node.setText(action.result.msg);
				                    	node_menu.appendChild(tree_node);
				                    	form.reset();
				                        win.hide();
				                        listenerEvent.loadGrid();
	                    			},params:{
	                    				action:"insert",
	                    				tag:"1",
	                    				isRoot:isRoot,
	                    				userlist:currentId,
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
	                    				tag:"1",
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
								url: "storeOutuserVo.do",
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
							   		tag:"1",
							   		isLeaf: node_menu.leaf,
							   		action:"delete",
							   		userlist:node_menu.attributes.id
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
  			contextmenu.items.items[1].hide();
  			contextmenu.items.items[2].show();
  			contextmenu.items.items[4].show();
  			contextmenu.showAt(e.getXY());
  		}
	});
	
	/**
	 * 参数明细表
	 */
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

  	function transform(value,record){
  		return trans.comboBox(comboBoxName,value);
  	}
  	
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
    	loadGrid:function(){
    		/*ds.baseParams.sql = "select new map(a.id as id,a.goodsname as goodsname,a.outnumber as outnumber,a.innumber as innumber,(select b.goodsnumber from GoodsList b where b.id.id=a.id) as goodsnumber,a.goodsdesc as goodsdesc,a.price as price) from StoreList a where a.typeid='"+currentId+"'";
    		ds.load({
		  		params:{  
		  			start:-1, 
		  			limit:-1
		  		}
		  	});*/
    		form1.getComponent(0).multiselects[0].store.load({
  				params:{sql:"select userId,userName from UserInfo where userId not in (select id.outuser from OutUserList where id.tag='1')"}
  			});
  			form1.getComponent(0).multiselects[1].store.load({
  				params:{sql:"select a.id.outuser,b.userName from OutUserList a,UserInfo b where a.id.tag='1' and a.id.outuser=b.userId and a.id.userlist='"+currentId+"'"}
  			});
  			form1.getComponent(0).multiselects[1].store.on("load",function(){
  				loading.loadMask().hide();
  			});
    	}
    	
    }
	/*
     * Ext.ux.form.ItemSelector Example Code
     */
    var form1 = new Ext.form.FormPanel({
        bodyStyle: 'padding:10px;',
        url:"storeOutuserVo.do",
        items:[{
            xtype: 'itemselector',
            name: 'itemselector',
	        imagePath: "/"+projectName+'/ext/ux/images/',
            multiselects: [{
                width: 250,
                height: height-200,
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
		        tbar:["-",new Ext.form.ComboBox({
			  		name: "roleCode",
			  		id:"output_goods_params_role_code",
				  	store: new Ext.data.Store({
			            proxy:new Ext.data.HttpProxy({url:"optionServlet.do?option=roleInfo"}),
			            autoLoad:true,
						reader:new Ext.data.ArrayReader({},[
						    {name:"value"},
						    {name:"text"}
						])
			        }),
			        fieldLabel: '角色选择',
				  	emptyText: "过滤用户",
				  	mode: "local",
				  	width:80,
				  	forceSelection :true,
				  	triggerAction: "all",
				  	valueField: "value",
				  	displayField: "text",
				  	hiddenName: "roleCode"
				}),{
					text:"查询",
					iconCls:"icon-grid",
					disabled: true,
					id:"output_goods_params_query",
					handler:function(){
						var hql = "select userId,userName from UserInfo where userId not in (select id.outuser from OutUserList where id.tag='1')";
						if(Ext.getCmp("output_goods_params_role_code").getValue()!=""){
							hql += " and roleInfo.roleCode='"+Ext.getCmp("output_goods_params_role_code").getValue()+"'";
						}
						loading.loadMask().show();
						form1.getComponent(0).multiselects[0].store.load({
			  				params:{sql:hql}
			  			});
			  			form1.getComponent(0).multiselects[1].store.load({
			  				params:{sql:"select a.id.outuser,b.userName from OutUserList a,UserInfo b where a.id.tag='1' and a.id.outuser=b.userId and a.id.userlist='"+currentId+"'"}
			  			});
			  			form1.getComponent(0).multiselects[1].store.on("load",function(){
			  				loading.loadMask().hide();
			  			});
					}
				}],
                displayField: 'text',
                valueField: 'value'
            },{
                width: 250,
                height: height-200,
                store: new Ext.data.Store({
		            proxy:new Ext.data.HttpProxy({
		            	url:"optionSqlServlet.do"
		            }),
		            baseParams:{
	            		sql:"select a.id.outuser,b.userName from OutUserList a,UserInfo b where a.id.tag='1' and a.id.outuser=b.userId and a.id.userlist='"+currentId+"'"
	            	},
		            autoLoad:true,
					reader:new Ext.data.ArrayReader({},[
					    {name:"value"},
					    {name:"text"}
					])
		        }),
		        tbar:[{
                    text: '保存',
                    iconCls:"save",
                    id:"output_goods_params_save",
                    disabled: true,
                    handler:function(){
                    	Ext.MessageBox.show({
							title:"警告提示",
							msg:"请确认是否要保存?",
							buttons:Ext.MessageBox.OKCANCEL,
							icon:Ext.MessageBox.WARNING,
							fn:function(btn){
								if(btn=="ok"){
									form1.getForm().submit({
		                    			success:function(form,action){
		                    				action.result.msg;
		                    			},params:{
		                    				userlist:currentId,
		                    				action:"save",
		                    				tag:"1"
		                    			},
		                    			waitMsg:"Loading"
		                    		});
								}
							}
                    	});
	                }
                }],
                displayField: 'text',
                valueField: 'value'
            }]
        }]
        /*,
        buttons: [{
            text: 'Save',
            handler: function(){
                if(isForm.getForm().isValid()){
                    Ext.Msg.alert('Submitted Values', 'The following will be sent to the server: <br />'+
                        form1.getForm().getValues(true));
                }
            }
        }]*/
    });

    
	new Ext.Panel({
        title: '仓库查询',
        collapsible:true,
        renderTo: 'output_goods_params_name',
        width : 300,
        height : height-100,
        items:[
	        new Ext.TabPanel({
		        border:false,
		        activeTab:0,
		        width : 300,
		        height : height-180,
		        tabPosition:'bottom',
		        items:[
		        	tree_menu
		        ]
		    })
		]
    });
    
    new Ext.Panel({
        title: '销售人员选择',
        collapsible:true,
        width : width-545,
        renderTo: 'output_goods_params_list',
        height : height-100,
        items:[form1]
    });
    
});