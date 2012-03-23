Ext.onReady(function(){
	Ext.form.Field.prototype.msgTarget = 'side';
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sm,
		{
			header:"角色编号",
			dataIndex:"role_code",
			sortable: true,
			width:90
		},{
    		header:"角色名称",
    		dataIndex:"role_name",
    		sortable: true,
    		width:50
    	},{
    		header:"角色描述",
    		sortable: true,
    		dataIndex:"role_desc"
    	},{
    		header:"修改人",
    		dataIndex:"user_name",
    		sortable: true,
    		width:50
    	},{
    		header:"更新时间",
    		dataIndex:"updt_time",
    		renderer:formatDate,
    		sortable: true,
    		width:90
    	}
  	]);
  	function formatDate(value){
  		var dt = new Date(value);
        return dt.format("Y-m-d H:i:s.u");
    };
  	cm.defaultSortable = true;
  	var ds = new Ext.data.Store({
    	proxy: new Ext.data.HttpProxy({url:"roleInfoQueryVo.do"}),
		reader: new Ext.data.JsonReader({
			totalProperty:"totalProperty",
			root:"root"
		},[
    		{name: "role_code", type: 'string'},
    		{name: "role_name", type: 'string'},
    		{name: "role_desc", type: 'string'},
    		{name: "user_name", type: 'string'},
    		{name: "updt_time",type:"date",dateFormat:"Y-m-d H:i:s.u"}
 		])
  	});
  	
	var form = new Ext.form.FormPanel({
	  	defaultType:"textfield",
	  	labelAlign:"right",
	  	url:"roleInfoVo.do",
	  	labelWidth:80,
	  	frame:true,
	  	width:200,
	  	items:[{
	    	name:"role_code",
	    	fieldLabel:"角色代码",
	    	hidden:true,
	    	hideLabel:true
	  	},{
	    	name:"role_name",
	    	fieldLabel:"角色名称",
	    	allowBlank:false,
	    	width:150,
	    	maxLength:50
	  	},{
	  		name:"role_desc",
	  		xtype:"textarea",
	  		fieldLabel:"角色描述",
	  		width:150,
	  		allowBlank:false,
	  		maxLength:200
	  	}]
	});
	var resetForm = function(){
		form.getForm().reset();
		form.getComponent(0).setRawValue("");
    	form.getComponent(1).setRawValue("");
    	form.getComponent(2).setRawValue("");
    	
	}
  	var grid = new Ext.grid.GridPanel({
    	el:"ktv_stay_manager_table",
    	ds:ds,
    	cm:cm,
    	sm:sm,
    	loadMask:true,
    	autoHeight:true,
    	title:'角色管理',
    	iconCls:"icon-grid",
    	//自动填充表格宽度
    	viewConfig: {
      		forceFit: true,
      		enableRowBody:true,
            showPreview:true
    	},
    	tbar:[{
            text:'添加角色',
            tooltip:'添加一条新的记录',
            iconCls:'add',
            handler:function(){
            	var win = new Ext.Window({
	                layout      : 'fit',
	                width       : 300,
	                title		: "角色添加",
	                height  	: 180,
	                modal		: true,
	                closeAction : 'hide',
	                allowDomMove: true,
	                bodyBorder 	: false,
	                plain       : true,
	                items       : [form],
	
	                buttons: [{
	                    text     : '保存',
	                    handler	 : function(){
	                    	var basicForm = form.getForm();
	                    	if(basicForm.isValid()){
	                    		basicForm.submit({
	                    			success:function(form,action){
	                    				Ext.MessageBox.show({
	                    					title:"操作提示",
	                    					msg:action.result.msg,
	                    					buttons:Ext.MessageBox.OK,
	                    					icon:Ext.MessageBox.INFO
	                    				});
	                    				resetForm();
	                    				ds.reload();
	                    				win.hide();
	                    			},params:{
	                    				action:"insert"
	                    			},
	                    			waitMsg:"Loading"
	                    		});
	                    	}
	                    }
	                },{
	                    text     : '关闭',
	                    handler  : function(){
	                    	resetForm();
	                        win.hide();
	                    }
	                }]
	            });
				win.show();
            }
        }, '-', {
            text:'修改角色',
            tooltip:'修改一条角色记录',
            iconCls:'option',
            handler:function(){
            	var selections = grid.getSelectionModel().getSelections();//
            	if(selections.length==0){
					Ext.MessageBox.show({
    					title:"错误提示",
    					msg:"请选择一条需要修改的记录",
    					buttons:Ext.MessageBox.OK,
    					icon:Ext.MessageBox.ERROR
    				});
    				return;
            	}
            	form.getComponent(0).setValue(selections[0].get("role_code"));
            	form.getComponent(1).setValue(selections[0].get("role_name"));
            	form.getComponent(2).setValue(selections[0].get("role_desc"));
            	var win = new Ext.Window({
	                layout      : 'fit',
	                width       : 300,
	                title		: "角色修改",
	                height  	: 180,
	                modal		: true,
	                closeAction : 'hide',
	                allowDomMove: true,
	                bodyBorder 	: false,
	                plain       : true,
	                items       : [form],
	
	                buttons: [{
	                    text     : '保存',
	                    handler	 : function(){
	                    	var basicForm = form.getForm();
	                    	if(basicForm.isValid()){
	                    		basicForm.submit({
	                    			success:function(form,action){
	                    				Ext.MessageBox.show({
	                    					title:"操作提示",
	                    					msg:action.result.msg,
	                    					buttons:Ext.MessageBox.OK,
	                    					icon:Ext.MessageBox.INFO
	                    				});
	                    				resetForm();
	                    				ds.reload();
	                    				win.hide();
	                    			},params:{
	                    				action:"update"
	                    			},
	                    			waitMsg:"Loading"
	                    		});
	                    	}
	                    }
	                },{
	                    text     : '关闭',
	                    handler  : function(){
	                    	resetForm();
	                        win.hide();
	                    }
	                }]
	            });
				win.show();
            }
        },'-',{
            text:'删除角色',
            tooltip:'删除一条角色记录',
            iconCls:'remove',
            handler:function(){
            	var selections = grid.getSelectionModel().getSelections();//
            	if(selections.length==0){
					Ext.MessageBox.show({
    					title:"错误提示",
    					msg:"请选择至少一条需要删除的记录!",
    					buttons:Ext.MessageBox.OK,
    					icon:Ext.MessageBox.ERROR
    				});
    				return;
            	}
            	var deleteCode = "";
            	for(var i = 0 ; i < selections.length; i++ ){
            		deleteCode += selections[i].get("role_code")+",";
            	}
            	var deleteCode = deleteCode.substring(0,deleteCode.length-1);
            	Ext.MessageBox.show({
					title:"删除警告",
					msg:"是否删除该记录!请确认编号:</br>"+deleteCode+"!",
					buttons:Ext.MessageBox.OKCANCEL,
					icon:Ext.MessageBox.WARNING,
					fn:function(btn){
						if(btn=="ok"){
			            	Ext.Ajax.request({
			            		url:"roleInfoVo.do",
			            		params:{
			            			action:"delete",
			            			role_code:deleteCode
			            		},
			            		success:function(action){
			            			var json = eval("["+action.responseText+"]");
                    				Ext.MessageBox.show({
                    					title:"操作提示",
                    					msg:json[0].msg,
                    					buttons:Ext.MessageBox.OK,
                    					icon:Ext.MessageBox.INFO
                    				});
                    				ds.reload();
                    			},
                    			waitMsg:"Loading"
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
  	ds.load({params:{start:0, limit:10}});
  	grid.render();

});