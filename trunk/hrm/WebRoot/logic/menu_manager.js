Ext.onReady(function() {
	//总菜单的RoleCode标示
	var roleCode = "0";
	var currentRoleCode = "0";
	Ext.QuickTips.init();
	var height = Ext.getBody().getHeight();
	//第一个Tree
	var tree_role = new Ext.tree.TreePanel({
		width : 300,
		height : height-100,
		autoScroll: true,
		loader : new Ext.tree.TreeLoader({
			dataUrl : "menuRoleVo.do?action=role"
		})
	});
	var root = new Ext.tree.AsyncTreeNode({
		id : "0",
		text : "角色选择"
	});
	tree_role.setRootNode(root);
	tree_role.getRootNode().expand();// 2315
	tree_role.on("click",function(node){
		currentRoleCode = node.attributes.id;
		tree_role_menu.getRootNode().setText(node.attributes.text+"菜单");
		tree_role_menu.getRootNode().attributes.id = node.attributes.id;
		tree_role_menu.getLoader().baseParams = {role_code:currentRoleCode};
		tree_role_menu.getRootNode().reload();
		tree_role_menu.expandAll(); 
		if(currentRoleCode=="0"){
			tree_menu.getLoader().baseParams = {role_code:currentRoleCode,action:"main_menu"};
		}else{
			tree_menu.getLoader().baseParams = {role_code:currentRoleCode,action:"role_main_menu"};
		}
		tree_menu.getRootNode().reload();
		tree_menu.expandAll();
	});
	
	/****/
	var contextmenu_menurole = new Ext.menu.Menu({
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
  		contextmenu_menurole.showAt(e.getXY());
	});
	/****/
	new Ext.Panel({
        title: '角色选择项',
        collapsible:true,
        renderTo: 'menu_manager_role',
        width : 300,
        height : height-100,
        items:[tree_role]
    });
	//第二个Tree
	var tree_menu = new Ext.tree.TreePanel({
		enableDrag : true,
		enableDrop:true,
		autoScroll: true,
		width : 300,
		height : height-100,
		loader : new Ext.tree.TreeLoader({
			dataUrl : "menuRoleVo.do",
			baseParams :{
				role_code:roleCode,
				action:"main_menu"
			}
		})
	});
	var root = new Ext.tree.AsyncTreeNode({
		id : "0", 
		text : "主菜单"
	});
	tree_menu.setRootNode(root);
	tree_menu.expandAll();// 2315
	new Ext.Panel({
        title: '主菜单选项 (拖拽)',
        collapsible:true,
        renderTo: 'menu_manager_menu',
        width : 300,
        height : height-100,
        items:[tree_menu]
    });
	/**
	 * 右键菜单
	 */
    var form = new Ext.form.FormPanel({
	  	defaultType:"textfield",
	  	labelAlign:"right",
	  	labelWidth:80,
	  	url:"menuRoleVo.do",
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
	    	allowBlank:true,
	    	width:150,
	    	maxLength:100,
	    	emptyText:"默认和节点名称一致"
	  	},{
	    	name:"tree_page",
	    	fieldLabel:"跳转页面",
	    	allowBlank:true,
	    	width:150,
	    	maxLength:100
	  	}]
	});
	var node_menu = null;
	var contextmenu = new Ext.menu.Menu({
  		items:[{
    		text:"添加新节点",
    		iconCls:"add",
    		handler:function(event,mouse){//bcescvr,110400
    			if(node_menu.isRoot){
    				form.getComponent(2).disable();
    			}else{
    				form.getComponent(2).enable();
    			}
    			var win = new Ext.Window({
	                layout      : 'fit',
	                width       : 300,
	                title		: "节点添加",
	                height  	: 180,
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
		                    		tree_node = new Ext.tree.TreeNode({id:Ext.id(),text:tree_name,qtip:tree_tips,leaf:false,iconCls:"tree-add"});
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
	                    				roleCode:roleCode
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
    			if(!node_menu.leaf){
    				form.getComponent(2).disable();
    			}else{
    				form.getComponent(2).enable();
    			}
    			form.getComponent(0).setValue(node_menu.attributes.text);
            	form.getComponent(1).setValue(node_menu.attributes.qtip);
            	form.getComponent(2).setValue(node_menu.attributes.page);
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
	                    		var tree_page = form.getComponent(2).getValue();
	                    		form.getForm().submit({
	                    			success:function(form,action){
				                    	node_menu.attributes.text = tree_name;
				                    	node_menu.attributes.qtip = tree_tips;
				                    	node_menu.attributes.page = tree_page;
				                    	node_menu.setText(tree_name);
				                    	form.reset();
				                        win.hide();
	                    			},params:{
	                    				action:"updateWin",
	                    				menu_id:node_menu.attributes.id,
	                    				leaf:node_menu.leaf,
	                    				role_code:roleCode
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
					msg:"请确认是否要删除?如果删除节点,那子节点数据将会一并删除!!",
					buttons:Ext.MessageBox.OKCANCEL,
					icon:Ext.MessageBox.WARNING,
					fn:function(btn){
						if(btn=="ok"){
							var isRoot = false;
							var childMenu_code = "";
							if(node_menu.isRoot){
		                    	isRoot = true;
							}
							if(!node_menu.leaf){
								for(var i = 0 ; i < node_menu.childNodes.length; i ++){
									childMenu_code += node_menu.childNodes[i].attributes.id+",";
								}
								if(childMenu_code!=""){
									childMenu_code = childMenu_code.substring(0,childMenu_code.length-1);
								}
							}
							Ext.Ajax.request({
								url: "menuRoleVo.do",
							   	success: function(action){
//							   		tree_menu.remove(node_menu);
							   		tree_menu.getRootNode().removeChild(node_menu);
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
							   		menu_code:node_menu.attributes.id,
							   		roleCode:roleCode,
							   		childMenu_code:childMenu_code
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
  		}else if(!node.attributes.leaf){
  			node_menu = node;
  			contextmenu.items.items[0].show();
  			contextmenu.items.items[1].show();
  			contextmenu.items.items[2].show();
  			contextmenu.items.items[4].show();
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
	 * 修改树节点
	 */
	var treeMenuEditor = new Ext.tree.TreeEditor(tree_menu,{
	  	allowBlank:false
	});
//	treeMenuEditor.on("beforestartedit",function(treeEditor){
//	  	return treeEditor.editNode.isLeaf();
//	});
	treeMenuEditor.on("complete",function(treeEditor,editorData,beforeEditorData){
		if(editorData!=beforeEditorData){
			Ext.Ajax.request({
				url: "menuRoleVo.do",
			   	success: function(action){
	//		   		tree_menu.remove(node_menu);
			   	},
			   	failure: function(action){
			   		Ext.MessageBox.show({
						title:"错误提示",
						msg:"修改失败,请查看日志寻找问题!!",
						buttons:Ext.MessageBox.OK,
						icon:Ext.MessageBox.ERROR
			   		});
			   	},
			   	params: { 
			   		menu_id: treeEditor.editNode.id,
			   		menu_name: treeEditor.editNode.text,
			   		leaf: treeEditor.editNode.leaf,
			   		roleCode:roleCode,
			   		action:"update"
			   	},
			   	waitMsg:"Loading"
			});
		}
	});
	tree_menu.getRootNode().disable();

	//第三个Tree
	var tree_role_menu = new Ext.tree.TreePanel({
		enableDrag : false,
		enableDrop:true,
		autoScroll: true,
		width : 300,
		height : height-100,
		loader : new Ext.tree.TreeLoader({
			dataUrl : "menuRoleVo.do?action=role_menu",
			baseParams :{
				role_code:currentRoleCode
			}
		})
	});
	var root = new Ext.tree.AsyncTreeNode({
		id : "0", 
		text : "角色选择菜单"
	});
	tree_role_menu.setRootNode(root);
	tree_role_menu.getRootNode().draggable=false;
	tree_role_menu.getRootNode().expand();// 2315
	tree_role_menu.getRootNode().disable();
	var contextmenu_role = new Ext.menu.Menu({
		items:[{
  			text:"刷新",
  			iconCls:"option",
  			handler:function(event,mouse){
  				tree_role_menu.getRootNode().reload();
  			}
  		},"-",{
  			text:"删除",
  			iconCls:"remove",
  			handler:function(event,mouse){
  				Ext.MessageBox.show({
					title:"警告提示",
					msg:"请确认是否要删除?如果删除节点,那子节点数据将会一并删除!!",
					buttons:Ext.MessageBox.OKCANCEL,
					icon:Ext.MessageBox.WARNING,
					fn:function(btn){
						if(btn=="ok"){
							var isRoot = false;
							var childMenu_code = "";
							if(node_menu.isRoot){
		                    	isRoot = true;
							}
							if(!node_menu.leaf){
								for(var i = 0 ; i < node_menu.childNodes.length; i ++){
									childMenu_code += node_menu.childNodes[i].attributes.id+",";
								}
								if(childMenu_code!=""){
									childMenu_code = childMenu_code.substring(0,childMenu_code.length-1);
								}
							}
							Ext.Ajax.request({
								url: "menuRoleVo.do",
							   	success: function(action){
//							   		tree_menu.remove(node_menu);
							   		tree_menu.getRootNode().removeChild(node_menu);
							   		tree_menu.getRootNode().reload();
									tree_menu.expandAll();
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
							   		action:"deleteRole",
							   		parent_node:node_menu.parentNode.attributes.id,
							   		menu_code:node_menu.attributes.id,
							   		role_code:currentRoleCode,
							   		childMenu_code:childMenu_code
							   	}
							});
						}
					}
				});
  			}
  		}]
	});
	tree_role_menu.on("contextmenu",function(node,e){
  		e.preventDefault();
  		node.select();
		node_menu = node;
//		contextmenu_role.items.items[0].show();
		if(node.isRoot){
  			contextmenu_role.items.items[2].hide();
  		}else{
  			contextmenu_role.items.items[2].show();
  		}
  		contextmenu_role.showAt(e.getXY());
	});
	new Ext.Panel({
        title: '角色菜单选择项 (拖拽)',
        collapsible:true,
        renderTo: 'menu_manager_role_menu',
        width : 300,
        height : height-100,
        items:[tree_role_menu]
    });
    tree_role_menu.addListener("beforemovenode",function(tree,node,oldParent,newParent,index){
    	var child_code = "";
    	for(var i = 0; i<node.childNodes.length;i++){
    		child_code += node.childNodes[i].attributes.id+","
    	}
    	child_code = child_code.substring(0,child_code.length-1);
    	var menu_code = node.attributes.id;
//    	alert("menu:"+menu_code+"  child_code:"+child_code);
    });
    tree_menu.addListener("beforemovenode",function(tree,node,oldParent,newParent,index){
    	var child_code = "";
    	var action = "";
    	if(!fnt.checkNodes(tree,node,oldParent,newParent,index)){
    		return false;
    	}
    	if(oldParent.parentNode!=null && newParent.parentNode!=null){
	    	if(oldParent.parentNode.attributes.id != newParent.parentNode.attributes.id){
	    		action = "subtree";
	    	}else{
	    		action = "selftree";
	    	}
    	}else{
    		action = "subtree";
    	}
    	for(var i = 0; i<node.childNodes.length;i++){
    		child_code += node.childNodes[i].attributes.id+","
    	}
    	child_code = child_code.substring(0,child_code.length-1);
    	var menu_code = node.attributes.id;
    	Ext.Ajax.request({
			url: "menuRoleVo.do",
		   	success: function(action){
		   	},
		   	failure: function(action){
		   		Ext.MessageBox.show({
					title:"错误提示",
					msg:"拖拽失败,请查看日志寻找问题!!",
					buttons:Ext.MessageBox.OK,
					icon:Ext.MessageBox.ERROR,
					fn:function(){
						return false;
					}
		   		});
		   	},
		   	params: { 
		   		menu_code: menu_code,
		   		child_code: child_code,
		   		role_code: currentRoleCode,
		   		old_parent_code: oldParent.attributes.id,
		   		new_parent_code: newParent.attributes.id,
		   		leaf:node.leaf,
		   		action:action
		   	},
		   	waitMsg:"Loading"
		});
    	return true;
    });
    /**
     * 方法集合
     */
    var fnt = {
		checkNodes:function(tree,node,oldParent,newParent,index){
			if(node.isRoot){
	    		Ext.MessageBox.show({
					title:"错误提示",
					msg:"请不要拖动根节点!!",
					buttons:Ext.MessageBox.OK,
					icon:Ext.MessageBox.ERROR
		   		});
		   		return false;
	    	}
	    	if(node.leaf){
	    		if(oldParent.attributes.id != newParent.attributes.id && oldParent.parentNode.attributes.id != newParent.parentNode.attributes.id){
	    			Ext.MessageBox.show({
						title:"错误提示",
						msg:"拖动的子节点的父节点必须和原子节点一致!!",
						buttons:Ext.MessageBox.OK,
						icon:Ext.MessageBox.ERROR
			   		});
			   		return false;
	    		}
	    		for(var i = 0; i < newParent.childNodes.length;i++){
	    			if(node.attributes.id == newParent.childNodes[i].attributes.id){
	    				Ext.MessageBox.show({
							title:"错误提示",
							msg:"拖动的节点已经存在!!请确认!!",
							buttons:Ext.MessageBox.OK,
							icon:Ext.MessageBox.ERROR
				   		});
				   		return false;
	    			}
	    		}
	    	}
	    	if(newParent.attributes.id=="0"){
	    		Ext.MessageBox.show({
					title:"错误提示",
					msg:"请选择一个角色再进行拖动!!",
					buttons:Ext.MessageBox.OK,
					icon:Ext.MessageBox.ERROR
		   		});
		   		return false;
	    	}
	    	for(var i = 0 ; i < newParent.childNodes.length;i++){
	    		if(node.attributes.id == newParent.childNodes[i].attributes.id){
	    			Ext.MessageBox.show({
						title:"错误提示",
						msg:"请不要拖动已存在的节点!!",
						buttons:Ext.MessageBox.OK,
						icon:Ext.MessageBox.ERROR
			   		});
			   		return false;
	    		}
	    	}
			return true;
		}
	};
});
