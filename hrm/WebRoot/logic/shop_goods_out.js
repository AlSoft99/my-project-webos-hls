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
	var recuser = "";
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
		height : (height-200)/2,
		autoScroll: true,
		tbar:toolbar,
		loader : new Ext.tree.TreeLoader({
			dataUrl : "treeInfoQuery.do?action=singledate",
			baseParams :{
				parent: "select new map(a.id as id,a.id||' ['||b.userName||']' as text,a.actualprice as qtip,a.shouldprice as shouldprice,a.actualprice as actualprice) from MemberGoodsInfo a,UserInfo b where a.memberid=b.userId and a.updttime>'{1}' and a.updttime<'{2}'",
//				child: "select new map(userId as id,userName as text,userName as qtip,roleInfo.roleCode as nodeId) from UserInfo"
				start:curentStartDate,
				end:curentEndDate
			}
		})
	});
	var root = new Ext.tree.AsyncTreeNode({
		id : "0",
		text : "销售账单"
	});
	tree_role.setRootNode(root);
	tree_role.getRootNode().expand();// 2315
	tree_role.on("click",function(node){
		if(node.leaf){
			grid.addBtn.setDisabled(false);
			currentId = node.attributes.id;
			Ext.Ajax.request({
        		url:"memberInfoVo.do",
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
	  	url:"memberInfoVo.do",
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
		  	emptyText: "过滤名称",
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
		}),
	  	new Ext.form.ComboBox({
	  		name: "memberid",
	  		fieldLabel: '会员名称',
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
		  	hiddenName: "memberid",
		  	listeners:{
		  		select:function(obj,option){
		  			var memberid = option.data.value;
		  			Ext.Ajax.request({
						url: "memberInfoVo.do",
						success: function(action){
//							tree_menu.remove(node_menu);
							var responseText = action.responseText;
							var list = eval(responseText);
							if(list.length!=0){
								form.getComponent(2).setValue(list[0]);
								form.getComponent(3).setValue(list[1]);
								form.getComponent(4).setValue(list[2]);
							}else{
								Ext.MessageBox.show({
									title:"错误提示",
									msg:"未找到相应的会员等级，请到【会员管理->会员参数维护】中设置好相应的等级!!",
									buttons:Ext.MessageBox.OK,
									icon:Ext.MessageBox.ERROR
								});
							}
							
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
							memberid: memberid,
							action:"select"
						}
					});
		  		}
		  	}
		}),{
	    	name:"memberlevel",
	    	fieldLabel:"会员等级",
	    	allowBlank:false,
	    	hidden:true,
	    	hideLabel:true,
	    	width:150,
	    	maxLength:50
	  	},{
	    	name:"memberlevelname",
	    	fieldLabel:"会员等级",
	    	allowBlank:false,
	    	disabled:true,
	    	width:150,
	    	maxLength:50
	  	},{
	    	name:"rate",
	    	xtype:"numberfield",
	    	minValue:0,
	    	maxValue:1,
	    	fieldLabel:"折扣比例",
	    	allowBlank:false,
	    	width:150,
	    	maxLength:50,
	    	listeners:{
	    		change:function(a,value){
	    			var tmp = 0;
	    			if(form.getComponent(5).getValue()!=""){
	    				tmp = form.getComponent(5).getValue();
	    			}
	    			form.getComponent(6).setValue(tmp*value);
	    		}
	    	}
	  	},{
	    	name:"shouldprice",
	    	xtype:"numberfield",
	    	fieldLabel:"应付价钱",
	    	allowBlank:false,
	    	width:150,
	    	maxLength:50,
	    	listeners:{
	    		change:function(a,value){
	    			var tmp = 0;
	    			if(form.getComponent(4).getValue()!=""){
	    				tmp = form.getComponent(4).getValue();
	    			}
	    			form.getComponent(6).setValue(tmp*value);
	    		}
	    	}
	  	},{
	    	name:"actualprice",
	    	xtype:"numberfield",
	    	fieldLabel:"实付价钱",
	    	allowBlank:false,
	    	width:150,
	    	maxLength:100,
	    	emptyText:"默认自动计算"
	  	},{
	    	name:"id",
	    	fieldLabel:"会员id",
	    	hidden:true,
	    	hideLabel:true,
	    	width:150,
	    	maxLength:50
	  	}]
	});
	var node_menu = null;
	var contextmenu = new Ext.menu.Menu({
  		items:[{
    		text:"添加新申请单",
    		iconCls:"add",
    		handler:function(event,mouse){//bcescvr,110400
    			var win = new Ext.Window({
	                layout      : 'fit',
	                width       : 300,
	                title		: "申请单添加",
	                height  	: 260,
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
		                    	var treeName = " ["+form.getComponent(1).getRawValue()+"]";
		                    	var parentNodeId = node_menu.attributes.id;
	                    		form.getForm().submit({
	                    			success:function(form,action){
	                    				var tree_name = action.result.msg;
	                    				var tree_tips = action.result.msg;
	                    				if(node_menu.isRoot){
				                    		isRoot = true;
				                    		tree_node = new Ext.tree.TreeNode({id:tree_name,text:tree_name+treeName,qtip:tree_tips,leaf:true});
				                    	}else{
				                    		tree_node = new Ext.tree.TreeNode({id:tree_name,text:tree_name+treeName,qtip:tree_tips,leaf:true});
				                    	}
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
	                    				entity:"com.hrm.entity.MemberGoodsInfo"
	                    			},
	                    			waitMsg:"Loading"
	                    		});
		                    	
	                    	}
	                    }
	                },{
	                    text     : '关闭',
	                    handler  : function(){
	                    	listenerEvent.resetForm();
	                        win.hide();
	                    }
	                }]
	            });
				win.show();
      			return true;
    		}
  		},{
    		text:"修改申请单",
    		iconCls:"save",
    		handler:function(event,mouse){//bcescvr,110400
    			form.getComponent(1).getStore().load();
    			form.getComponent(1).setValue(node_menu.attributes.recuser);
    			var win = new Ext.Window({
	                layout      : 'fit',
	                width       : 300,
	                title		: "申请单修改",
	                height  	: 260,
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
	                    		form.getForm().submit({
	                    			success:function(form,action){
				                    	listenerEvent.resetForm();
				                        win.hide();
	                    			},params:{
	                    				action:"update",
	                    				menu_id:node_menu.attributes.id,
	                    				parent_id:node_menu.parentNode.attributes.id,
	                    				leaf:node_menu.leaf
	                    			},
	                    			waitMsg:"Loading"
	                    		});
		                    	node_menu.attributes.recuser = form.getComponent(1).getValue();
	                    	}
	                    }
	                },{
	                    text     : '关闭',
	                    handler  : function(){
	                    	listenerEvent.resetForm();
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
  				tree_role.getRootNode().reload();
  				salaryList.setSource({});
    			currentId = '';
    			listenerEvent.loadGrid();
  			}
  		},"-",{
  			text:"删除申请单",
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
								url: "storeOutputInfoVo.do",
							   	success: function(action){
//							   		tree_role.remove(node_menu);
							   		tree_role.getRootNode().removeChild(node_menu);
							   		currentId = "";
    								salaryList.setSource({});
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
	tree_role.on("contextmenu",function(node,e){
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
	 * 员工工资明细表
	 */
	var editor = new Ext.ux.grid.RowEditor({
        saveText: 'Update'
    });
	var Employee = Ext.data.Record.create([
		{name: "id", type: 'string'},
		{name: "supid", type: 'string'},
		{name: "goodstype", type: 'string'},
		{name: "goodsid", type: 'string'},
		{name: "count", type: 'int'},
		{name: "price", type: 'string'},
    	{name: "updttime",type:"date",dateFormat:"Y-m-d H:i:s.u"}
    ]);
    var comboBoxType = comboBoxList.comboBoxSql("select distinct a.id,a.typename || '[' || a.storelist || ']' from StoreType a,OutUserList c where a.storelist=c.id.userlist and c.id.tag='2'","","");
	var comboBoxName = comboBoxList.comboBoxSql("select b.id.id,c.goodsname from GoodsList b,StoreList c where b.id.id=c.id and (select d.id.userlist from OutUserList d where d.id.userlist=b.id.userlist and d.id.tag='1' and d.id.outuser='"+Ext.getDom("user_id").value+"') is not null","","");
	comboBoxType.emptyText = "过滤货物";
	comboBoxType.allowBlank = true;
	comboBoxName.allowBlank = false;
	var goodsListBox = comboBoxList.goodsList;
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
			header:"帐单ID",
			dataIndex:"supid",
			sortable: true,
			hidden:true
		},{
    		header:"货物种类",
    		dataIndex:"goodstype",
    		width:150,
    		sortable: true,
    		renderer:filter,
    		editor:comboBoxType

    	},{
    		header:"货物名称",
    		dataIndex:"goodsid",
    		width:150,
    		sortable: true,
    		editor:comboBoxName,
    		renderer:transform

    	},{
			xtype: 'numbercolumn',
    		header:"货物数量",
    		dataIndex:"count",
    		sortable: true,
    		editor: {
                xtype: 'numberfield',
                minValue: 0,
                maxValue: 150000,
                allowBlank: false
            }
    	},{
			xtype: 'numbercolumn',
    		header:"货物价格（单价）",
    		dataIndex:"price",
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
  	comboBoxType.on("select",function(obj,option){
  		var roleCode = option.data.value;
		comboBoxName.getStore().load({
			params:{
				sql:"select b.id.id,c.goodsname from GoodsList b,StoreList c where b.id.id=c.id and b.id.userlist in (select d.id.userlist from OutUserList d where d.id.tag='1' and d.id.outuser='"+Ext.getDom("user_id").value+"') and c.typeid='"+roleCode+"'"
			}
		});
		comboBoxName.setValue("");
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
    	plugins: [editor],
    	iconCls:"icon-grid",
    	tbar:[{
    		ref: '../addBtn',
            text:'增加货物明细',
            tooltip:'增加帐货物明细单',
            iconCls:'add',
            disabled: true,
            handler:function(){
            	loading.loadMask().show();
            	editor.setDisabled(true);
            	var record = {
            		id:'',
                    supid: currentId,
                    goodstype:'',
                    goodsid:'',
                    count:0,
                    price:0,
                    updtuser:Ext.getDom("user_name").value,
                    updttime: (new Date()).clearTime().format('Y-m-d')
                };
                Ext.Ajax.request({
	        		url:"memberInfoVo.do?action=insertRecord&entity=com.hrm.entity.MemberGoodsList",
	        		params:record,
	        		success:function(action){
	        			var json = eval("("+action.responseText+")");
	        			loading.loadMask().hide();
	        			editor.setDisabled(false);
	        			record.id = json.msg;
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
            	var e = new Employee(record);
                editor.stopEditing();
                ds.insert(0, e);
                grid.getView().refresh();
                grid.getSelectionModel().selectRow(0);
                editor.startEditing(0);
            }
        }, '-',{
        	ref: '../removeBtn',
            text:'删除货物明细',
            tooltip:'删除一条帐单货物明细单',
            iconCls:'remove',
            disabled: true,
            handler:function(){
            	Ext.MessageBox.show({
					title:"警告提示",
					msg:"请确认是否要删除?",
					buttons:Ext.MessageBox.OKCANCEL,
					icon:Ext.MessageBox.WARNING,
					fn:function(btn){
						if(btn=="ok"){
							editor.stopEditing();
			                var s = grid.getSelectionModel().getSelections();
			                var id = "";
			                for(var i = 0, r; r = s[i]; i++){
			                    ds.remove(r);
			                    id += r.data.id+",";
			                }
							id = id.substring(0,id.length-1);
							Ext.Ajax.request({
				        		url:"memberInfoVo.do?action=deleteRecord",
				        		params:{
				        			id:id,
				        			supid:currentId
				        		},
				        		success:function(action){
				        			loading.loadMask().hide();
				        			editor.setDisabled(false);
				        			listenerEvent.loadGrid();
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
        title: '单笔帐单明细', 
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
    			url:"memberInfoVo.do?action=updateRecord&entity=com.hrm.entity.MemberGoodsList",
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
    		comboBoxName.getStore().load({
				params:{
					sql:"select b.id,b.goodsname from StoreType a,StoreList b,OutUserList c where a.storelist=c.id.userlist and c.id.tag='2' and a.id = b.typeid and c.id.outuser='"+recuser+"'"
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
					type:"entity",
					sql:"from MemberGoodsList where supid='"+currentId+"'"
		  		}
		  	});
    	}
    }
	editor.addListener("afteredit",listenerEvent.update);
	editor.addListener("canceledit",function(){
		comboBoxName.getStore().load({
			params:{
				sql:"select b.id,b.goodsname from StoreType a,StoreList b,OutUserList c where a.storelist=c.id.userlist and c.id.tag='2' and a.id = b.typeid and c.id.outuser='"+recuser+"'"
			}
		});
	});
	
	new Ext.Panel({
        title: '帐单查询',
        collapsible:true,
        hideBorders:true,
        renderTo: 'shop_goods_out_name',
        width : 350,
        height : height-100,
        items:[
        	tree_role,
        	salaryList
		]
    });
    
    new Ext.Panel({
        title: '帐单货物明细',
        collapsible:true,
        width : width-595,
        renderTo: 'shop_goods_out_list',
        height : height-100,
        items:[grid]
    });
    grid.getSelectionModel().on('selectionchange', function(sm){
        grid.removeBtn.setDisabled(sm.getCount() < 1);
    });

});