Ext.onReady(function(){
	Ext.form.Field.prototype.msgTarget = 'side';
	var sm = new Ext.grid.CheckboxSelectionModel({
		listeners: {
            rowselect: function(sm, row, rec) {
                member_manager_form.getForm().loadRecord(rec);
            }
        }

	});
	var cm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sm,
		{
			header:"用户编号",
			dataIndex:"user_id",
			sortable: true,
			hidden:true
		},{
			header:"角色名称",
			sortable: true,
			dataIndex:"role_name",
			hidden:true
		},{
			header:"角色编号",
			sortable: true,
			dataIndex:"role_code",
			hidden:true
		},{
			header:"用户账号",
			sortable: true,
			dataIndex:"user_code"
		},{
			header:"用户名称",
			sortable: true,
			dataIndex:"user_name"
		},{
			header:"用户密码",
			sortable: true,
			dataIndex:"user_pwd",
			hidden:true
		},{
			header:"用户电话",
			sortable: true,
			dataIndex:"user_phone",
			hidden:true
		},{
    		header:"用户邮箱",
    		dataIndex:"user_mail",
    		sortable: true,
    		hidden:true
    	},{
    		header:"身份证",
    		dataIndex:"user_qq",
    		sortable: true,
    		hidden:true
    	},{
    		header:"系统冻结",
    		dataIndex:"syscd",
    		sortable: true,
    		renderer:transCd
    	},{
    		header:"用户冻结",
    		dataIndex:"usercd",
    		sortable: true,
    		renderer:transCd
    	},{
    		header:"系统冻结时间",
    		dataIndex:"syscdtime",
    		sortable: true
    	},{
    		header:"更新时间",
    		dataIndex:"updt_time",
    		sortable: true,
    		renderer:formatDate
    	}
  	]);
  	function formatDate(value){
  		var dt = new Date(value);
        return dt.format("Y-m-d H:i:s.u");
    };
    function transCd(value){
    	if(value=="0"){
    		return "否";
    	}else{
    		return "<font color='red'>是</font>";
    	}
    }
  	cm.defaultSortable = true;
  	var ds = new Ext.data.GroupingStore({
    	proxy: new Ext.data.HttpProxy({url:"userInfoQueryVo.do"}),
		reader: new Ext.data.JsonReader({
			totalProperty:"totalProperty",
			root:"root"
		},[
    		{name: "user_id", type: 'string'},
    		{name: "role_name", type: 'string'},
    		{name: "role_code", type: 'string'},
    		{name: "user_code", type: 'string'},
    		{name: "user_name", type: 'string'},
    		{name: "user_pwd", type: 'string'},
    		{name: "user_phone", type: 'string'},
    		{name: "user_mail", type: 'string'},
    		{name: "user_qq", type: 'string'},
    		{name: "syscd", type: 'string'},
    		{name: "usercd", type: 'string'},
    		{name: "syscdtime", type: 'string',dateFormat:"Y-m-d H:i:s.u"},
    		{name: "usercdtime", type: 'string',dateFormat:"Y-m-d H:i:s.u"},
    		{name: "updt_time",type:"date",dateFormat:"Y-m-d H:i:s.u"}
 		]),
 		sortInfo:{field: 'user_code', direction: "ASC"},
        groupField:'role_name'

  	});
  	var comboBox1 = comboBoxList.comboBoxSql("select paramscode,paramsname from ParamsList where typeid='YNTYPE'","系统冻结","syscd");
  	var comboBox2 = comboBoxList.comboBoxSql("select paramscode,paramsname from ParamsList where typeid='YNTYPE'","用户冻结","usercd");
	var form = new Ext.form.FormPanel({
	  	defaultType:"textfield",
	  	labelAlign:"right",
	  	url:"userInfoVo.do",
	  	labelWidth:80,
	  	frame:true,
	  	width:200,
	  	items:[{
	    	name:"user_id",
	    	fieldLabel:"用户编号",
	    	hidden:true,
	    	hideLabel:true
	  	},{
	    	name:"user_code",
	    	fieldLabel:"用户账号",
	    	allowBlank:false,
	    	width:150,
	    	minLength:5,
	    	maxLength:50
	  	},{
	    	name:"user_pwd",
	    	fieldLabel:"用户密码",
	    	allowBlank:false,
	    	width:150,
	    	maxLength:50
	  	},new Ext.form.ComboBox({
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
		}),
		{
	    	name:"user_name",
	    	fieldLabel:"用户姓名",
	    	allowBlank:false,
	    	width:150,
	    	maxLength:50
	  	},{
	    	name:"user_phone",
	    	fieldLabel:"用户电话",
	    	allowBlank:false,
	    	width:150,
	    	maxLength:50
	  	},{
	    	name:"user_mail",
	    	fieldLabel:"用户邮箱",
	    	allowBlank:false,
	    	vtype:"email",
	    	width:150,
	    	maxLength:50
	  	},{
	    	name:"user_qq",
	    	fieldLabel:"身份证",
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
    	el:"member_manager_table",
    	ds:ds,
    	cm:cm,
    	sm:sm,
    	loadMask:true,
    	autoHeight:true,
    	width:Ext.getBody().getWidth()-300-245,
    	title:'用户管理',
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
            text:'添加用户',
            tooltip:'添加一条新的记录',
            iconCls:'add',
            handler:function(){
            	var win = new Ext.Window({
	                layout      : 'fit',
	                width       : 300,
	                title		: "用户添加",
	                height  	: 300,
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
	                    				member_manager_form.getForm().reset();
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
        }, '-',{
            text:'删除用户',
            tooltip:'删除一条用户记录',
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
            	var deleteRoleCode = "";
            	for(var i = 0 ; i < selections.length; i++ ){
            		deleteCode += selections[i].get("user_id")+",";
            		deleteRoleCode += selections[i].get("role_code")+",";
            	}
            	deleteCode = deleteCode.substring(0,deleteCode.length-1);
            	deleteRoleCode = deleteRoleCode.substring(0,deleteRoleCode.length-1);
            	Ext.MessageBox.show({
					title:"删除警告",
					msg:"是否删除该记录!请确认编号:</br>"+deleteCode+"!",
					buttons:Ext.MessageBox.OKCANCEL,
					icon:Ext.MessageBox.WARNING,
					fn:function(btn){
						if(btn=="ok"){
			            	Ext.Ajax.request({
			            		url:"userInfoVo.do",
			            		params:{
			            			action:"delete",
			            			user_id:deleteCode,
			            			role_code:deleteRoleCode
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
                    				member_manager_form.getForm().reset();
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
  	var member_manager_height = Ext.getBody().getHeight();
  	var member_manager_form = new Ext.form.FormPanel({
	  	defaultType:"textfield",
	  	labelAlign:"right",
	  	url:"userInfoVo.do",
	  	labelWidth:80,
	  	height:member_manager_height,
	  	frame:true,
	  	width:300,
	  	items:[{
	    	name:"user_id",
	    	fieldLabel:"用户编号",
	    	hidden:true,
	    	hideLabel:true
	  	},{
	    	name:"user_code",
	    	fieldLabel:"用户账号",
	    	allowBlank:false,
	    	width:150,
	    	minLength:5,
	    	maxLength:50
	  	},{
	    	name:"user_pwd",
	    	fieldLabel:"用户密码",
	    	allowBlank:false,
	    	width:150,
	    	maxLength:50
	  	},new Ext.form.ComboBox({
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
		}),
		comboBox1,
		comboBox2,
		{
	    	name:"user_name",
	    	fieldLabel:"用户姓名",
	    	allowBlank:false,
	    	width:150,
	    	maxLength:50
	  	},{
	    	name:"user_phone",
	    	fieldLabel:"用户电话",
	    	allowBlank:false,
	    	width:150,
	    	maxLength:50
	  	},{
	    	name:"user_mail",
	    	fieldLabel:"用户邮箱",
	    	allowBlank:false,
	    	width:150,
	    	maxLength:50
	  	},{
	    	name:"user_qq",
	    	fieldLabel:"身份证",
	    	allowBlank:false,
	    	width:150,
	    	maxLength:50
	  	}]
	});
	new Ext.Panel({
        title: '用户详细信息',
        collapsible:true,
        renderTo: 'member_manager_panel',
        height:member_manager_height,
        width:300,
        draggable:true,
        items:[member_manager_form],
        tbar:[{
            text:'修改用户',
            tooltip:'修改一条用户记录',
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
            	var basicForm = member_manager_form.getForm();
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
            				action:"update"
            			}
            		});
            	}
            }
        },'-']
    });
});