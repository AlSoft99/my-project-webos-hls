Ext.onReady(function(){
	Ext.form.Field.prototype.msgTarget = 'side';
	var sm = new Ext.grid.CheckboxSelectionModel({
		listeners: {
            rowselect: function(sm, row, rec) {
                member_params_info_form.getForm().loadRecord(rec);
            }
        }
	});
	var cm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sm,
		{
			header:"标识号",
			dataIndex:"id",
			sortable: true
		},{
			header:"用户代码",
			sortable: true,
			dataIndex:"updtuser",
			hidden:true
		},{
			header:"会员等级代码",
			sortable: true,
			dataIndex:"memberlevel",
			hidden:true
		},{
			header:"会员等级",
			sortable: true,
			dataIndex:"memberlevelname"
		},{
			header:"会员折扣",
			sortable: true,
			dataIndex:"rate"
		},{
			header:"最小值(<font color='red'>>=</font>)",
			sortable: true,
			dataIndex:"min",
			xtype: 'numbercolumn',
			format: '￥0,0.00'
		},{
			header:"最大值(<font color='red'><</font>)",
			sortable: true,
			dataIndex:"max",
			xtype: 'numbercolumn',
			format: '￥0,0.00'
		},{
			header:"更新时间",
			sortable: true,
			dataIndex:"updttime"
		}
  	]);
  	cm.defaultSortable = true;
  	var ds = new Ext.data.GroupingStore({
    	proxy: new Ext.data.HttpProxy({url:"queryInfoVo.do"}),
		reader: new Ext.data.JsonReader({
			totalProperty:"totalProperty",
			root:"root"
		},[
    		{name: "id", type: 'string'},
    		{name: "updtuser", type: 'string'},
    		{name: "username", type: 'string'},
    		{name: "memberlevel", type: 'string'},
    		{name: "memberlevelname", type: 'string'},
    		{name: "rate", type: 'string'},
    		{name: "min", xtype: 'numbercolumn',format: '￥0,0.00'},
    		{name: "max", xtype: 'numbercolumn',format: '￥0,0.00'},
    		{name: "updttime",xtype:"datecolumn",dateFormat:"Y-m-d H:i:s.u"}
 		]),
 		sortInfo:{field: 'id', direction: "ASC"}
  	});
	var form = new Ext.form.FormPanel({
	  	defaultType:"textfield",
	  	labelAlign:"right",
	  	url:"memberParamsInfoVo.do",
	  	labelWidth:80,
	  	frame:true,
	  	width:200,
	  	items:[{
	    	name:"id",
	    	fieldLabel:"标识号",
	    	hidden:true,
	    	hideLabel:true
	  	},
		comboBoxList.comboBoxSql("select paramscode,paramsname from ParamsList where typeid='MEMBER_LEVEL'","会员等级","memberlevel"),
	  	{
	    	name:"rate",
	    	fieldLabel:"会员折扣",
	    	xtype:"numberfield",
	    	allowBlank:false,
	    	width:150,
	    	maxLength:50
	  	},{
	    	name:"min",
	    	fieldLabel:"最小值(<font color='red'>>=</font>)",
	    	xtype:"numberfield",
	    	allowBlank:false,
	    	width:150,
	    	maxLength:50
	  	},{
	    	name:"max",
	    	fieldLabel:"最大值(<font color='red'><</font>)",
	    	xtype:"numberfield",
	    	allowBlank:false,
	    	width:150,
	    	maxLength:50
	  	}]
	});
	var resetForm = function(){
		form.getForm().reset();
	}
  	var grid = new Ext.grid.GridPanel({
    	el:"member_params_info_table",
    	ds:ds,
    	cm:cm,
    	sm:sm,
    	loadMask:true,
    	height:Ext.getBody().getHeight()-170,
    	width:Ext.getBody().getWidth()-300-245,
    	title:'申报记录管理',
    	iconCls:"icon-grid",
    	view: new Ext.grid.GroupingView({
            forceFit:true,
            groupTextTpl: '{text} ({[values.rs.length]} {[values.rs.length > 1 ? "Items" : "Item"]})'
        }),
    	//自动填充表格宽度
    	viewConfig: {
      		forceFit: true,
      		enableRowBody:true,
            showPreview:true
    	},
    	tbar:[{
            text:'添加记录',
            tooltip:'添加一条新的记录',
            iconCls:'add',
            handler:function(){
            	var win = new Ext.Window({
	                layout      : 'fit',
	                width       : 300,
	                title		: "记录添加",
	                height  	: 200,
	                modal		: true,
	                closeAction : 'hide',
	                allowDomMove: true,
	                bodyBorder 	: false,
	                plain       : true,
	                items       : [form],
	
	                buttons: [{
	                    text     : "保存",
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
	                    				member_params_info_form.getForm().reset();
	                    				ds.reload();
	                    				win.hide();
	                    			},params:{
	                    				action:"insert",
	                    				entity:"com.hrm.entity.MemberParamsInfo"
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
        }, '-',{
        	ref: '../removeBtn',
            text:'删除记录',
            tooltip:'删除一条记录记录',
            disabled: true,
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
            	var deleteid = "";
            	for(var i = 0 ; i < selections.length; i++ ){
            		deleteid += selections[i].get("id")+",";
            	}
            	deleteid = deleteid.substring(0,deleteid.length-1);
            	Ext.MessageBox.show({
					title:"删除警告",
					msg:"是否删除该记录!请确认编号:</br>"+deleteid+"!",
					buttons:Ext.MessageBox.OKCANCEL,
					icon:Ext.MessageBox.WARNING,
					fn:function(btn){
						if(btn=="ok"){
			            	Ext.Ajax.request({
			            		url:"memberParamsInfoVo.do",
			            		params:{
			            			action:"delete",
			            			id:deleteid
			            		},
			            		success:function(action){
			            			var json = eval("("+action.responseText+")");
                    				Ext.MessageBox.show({
                    					title:"操作提示",
                    					msg:json.msg,
                    					buttons:Ext.MessageBox.OK,
                    					icon:Ext.MessageBox.INFO
                    				});
                    				ds.reload();
                    				member_params_info_form.getForm().reset();
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
  	var listenerEvent = {
    	loadGrid:function(){
    		ds.baseParams.sql = "select new map(a.id as id,a.updtuser as updtuser,a.min as min,a.max as max,a.rate as rate,a.updttime as updttime,b.userName as username,a.memberlevel as memberlevel,c.paramsname as memberlevelname) from MemberParamsInfo a,UserInfo b,ParamsList c where a.updtuser = b.userId and c.paramscode = a.memberlevel and c.typeid='MEMBER_LEVEL'";
    		ds.baseParams.action = "hql";
    		ds.baseParams.type = "map";
    		ds.load({
		  		params:{  
		  			start:-1, 
		  			limit:-1
		  		}
		  	});
    	}
    }
    listenerEvent.loadGrid();
  	grid.render();
  	var member_params_info_height = Ext.getBody().getHeight();
  	var member_params_info_form = new Ext.form.FormPanel({
	  	defaultType:"textfield",
	  	labelAlign:"right",
	  	url:"memberParamsInfoVo.do", 
	  	labelWidth:80,
	  	height:member_params_info_height,
	  	frame:true,
	  	width:300,
	  	items:[{
	    	name:"id",
	    	fieldLabel:"标识号",
	    	hidden:true,
	    	hideLabel:true
	  	},
	  	comboBoxList.comboBoxSql("select paramscode,paramsname from ParamsList where typeid='MEMBER_LEVEL'","会员等级","memberlevel"),
	  	{
	    	name:"rate",
	    	fieldLabel:"会员折扣",
	    	xtype:"numberfield",
	    	allowBlank:false,
	    	width:150,
	    	maxLength:50
	  	},{
	    	name:"min",
	    	fieldLabel:"最小值",
	    	xtype:"numberfield",
	    	allowBlank:false,
	    	width:150,
	    	maxLength:50
	  	},{
	    	name:"max",
	    	fieldLabel:"最大值",
	    	xtype:"numberfield",
	    	allowBlank:false,
	    	width:150,
	    	maxLength:50
	  	}]
	});
	new Ext.Panel({
        title: '申报记录详细信息',
        collapsible:true,
        renderTo: 'member_params_info_panel',
        height:member_params_info_height,
        width:300,
        draggable:true,
        items:[member_params_info_form],
        tbar:[{
            text:'修改申报记录',
            tooltip:'修改一条申报记录',
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
            	var basicForm = member_params_info_form.getForm();
            	if(basicForm.isValid()){
            		basicForm.submit({
            			success:function(form,action){
            				Ext.MessageBox.show({
            					title:"操作提示",
            					msg:action.result.msg,
            					buttons:Ext.MessageBox.OK,
            					icon:Ext.MessageBox.INFO
            				});
            				ds.reload();
            			},params:{
            				action:"update",
            				entity:"com.hrm.entity.MemberParamsInfo"
            			}
            		});
            	}
            }
        },'-']
    });
    grid.getSelectionModel().on('selectionchange', function(sm){
        grid.removeBtn.setDisabled(sm.getCount() < 1);
    });
});