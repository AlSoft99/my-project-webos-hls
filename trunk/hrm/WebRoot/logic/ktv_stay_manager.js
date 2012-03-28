Ext.onReady(function(){
	Ext.form.Field.prototype.msgTarget = 'side';
	var sm = new Ext.grid.CheckboxSelectionModel({
		listeners: {
            rowselect: function(sm, row, rec) {
            	
            }
        }

	});
	var cm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sm,
		{
			header:"ID",
			dataIndex:"id",
			sortable: true,
			hidden: true,
			width:90
		},{
			header:"卡号编号",
			dataIndex:"cardid",
			sortable: true
		},{
    		header:"保存物品",
    		dataIndex:"materialname",
    		sortable: true
    	},{
    		header:"保存物品",
    		dataIndex:"materialid",
    		sortable: true,
    		hidden: true
    	},{
    		header:"附言",
    		sortable: true,
    		width:150,
    		dataIndex:"otherdesc"
    	},{
    		header:"状态",
    		sortable: true,
    		dataIndex:"state",
    		hidden: true
    	},{
    		header:"状态",
    		sortable: true,
    		dataIndex:"statename"
    	},{
    		xtype : 'numbercolumn',
    		header:"保存天数",
    		sortable: true,
    		format : "00天",
    		dataIndex:"day"
    	},{
    		header:"过期日期",
    		sortable: true,
    		dataIndex:"overtime"
    	},{
    		xtype : 'numbercolumn',
    		header:"押金",
    		sortable: true,
    		format : "￥0,0.00",
    		dataIndex:"cash"
    	},{
    		header:"客户名",
    		sortable: true,
    		dataIndex:"username"
    	},{
    		header:"手机",
    		sortable: true,
    		dataIndex:"moblie"
    	},{
    		header:"身份证",
    		sortable: true,
    		dataIndex:"idcard",
    		width:100,
    	},{
    		header:"修改人",
    		dataIndex:"updtusername",
    		sortable: true,
    		hidden: true,
    		width:50
    	},{
    		header:"更新时间",
    		dataIndex:"updttime",
    		renderer:formatDate,
    		sortable: true,
    		hidden: true,
    		width:90
    	}
  	]);
  	function formatDate(value){
  		var dt = new Date(value);
        return dt.format("Y-m-d H:i:s.u");
    };
  	cm.defaultSortable = true;
  	/*var ds = new Ext.data.Store({
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
  	});*/
  	var goods = Ext.data.Record.create([
		{ name: "id", type: 'string' },
		{ name: "cardid", type: 'string' },
		{ name: "username", type: 'string' },
		{ name: "moblie", type: 'string' },
		{ name: "otherdesc", type: 'string' },
		{ name: "idcard", type: 'string' },
		{ name: "materialname", type: 'string' },
		{ name: "materialid", type: 'string' },
		{ name: "state", type: 'string' },
		{ name: "statename", type: 'string' },
		{ name: "day", type: 'int' },
		{ name: "overtime", type: 'string' },
		{ name: "cash", type: 'float' },
		{ name: "updttime", type: 'string' },
		{ name: "updtusername", type: 'string' }
	]);
  	var ds = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: "queryInfoVo.do"
        }),
        reader: new Ext.data.JsonReader({
            totalProperty: "totalProperty",
            root: "root"
        }, goods)
    });
  	var comboBoxType = comboBoxList.comboBoxSql("select a.id,a.typename from MaterialTypeKtv a", "酒水种类", "materialtype");
    var comboBoxName = comboBoxList.comboBoxSql("select a.id,a.paramscode || '-' || a.paramsname from MaterialListKtv a", "酒水名称", "materialid");
    comboBoxType.emptyText = "过滤酒水";
    comboBoxType.allowBlank = true;
    comboBoxName.allowBlank = false;
    comboBoxType.on("change",function(obj, option){
    	if(option==""){
    		comboBoxName.getStore().load({
                params: {
                    sql: "select a.id,a.paramscode || '-' || a.paramsname from MaterialListKtv a "
                }
            });
    	}
    });
    comboBoxType.on("select", function (obj, option) {
        var roleCode = option.data.value;
        comboBoxName.getStore().load({
            params: {
                sql: "select a.id,a.paramscode || '-' || a.paramsname from MaterialListKtv a where a.typeid='" + roleCode + "'"
            }
        });
        comboBoxName.setValue("");
    });
    var cardid = new Ext.form.TextField({
    	id:"ktv-cardid",
    	name      : 'cardid',
	    fieldLabel: 'Card ID',
	    anchor    : '-20',
	    flex: 1,
	    allowBlank:false,
	    readOnly:true
    });
    var cash = new Ext.form.NumberField({
    	id:"ktv-cash",
    	name      : 'cash',
	    fieldLabel: '押金(元)',
	    minValue: 0,
        maxValue: 150000,
        value: 10,
	    allowBlank:false
    });
    var cardnumber = "";
    var currentTime = (new Date()).getTime();
    Ext.getBody().on("keydown",function(o){
    	if(maskState){
    		var nowTime = (new Date()).getTime();
    		if(nowTime-currentTime>100){
    			cardnumber = "";
    		}
    		currentTime = nowTime;
    		var key = o.keyCode;
    		var strcode=String.fromCharCode(o.keyCode);
        	if(key=="13"){
        		hideMask();
        		cardid.setValue(cardnumber);
        	}else{
        		cardnumber += strcode;
        	}
    	}
    });
    var dayspinner = new Ext.ux.form.SpinnerField({
    	id:"ktv-day",
  		xtype: 'spinnerfield',
    	fieldLabel: '保存天数',
    	name: 'day',
    	minValue: 0,
    	maxValue: 100,
    	value: 10,
    	allowDecimals: true,
    	decimalPrecision: 1,
    	allowBlank:false,
    	incrementValue: 10,
    	//alternateIncrementValue: 2.1,
    	accelerate: true
  	});
    dayspinner.on("spin",function(o){
    	cash.setValue(dayspinner.getValue());
    });
	var form = new Ext.form.FormPanel({
	  	defaultType:"textfield",
	  	labelAlign:"right",
	  	url:"ktvStayInfoVo.do",
	  	labelWidth:80,
	  	defaults: {
            width:520
        },
	  	frame:true,
	  	items:[{
            xtype:'fieldset',
            title: '基本信息',
            collapsible: true,
            autoHeight:true,
            defaults: {width: 380},
            defaultType: 'textfield',
            items :[{
            	id:"ktv-id",
    	    	name:"id",
    	    	fieldLabel:"ID",
    	    	hidden:true,
    	    	hideLabel:true
    	  	},{
    	  		xtype: 'compositefield',
    	    	name:"card-scan",
    	    	fieldLabel:"卡号扫描",
    	    	allowBlank:false,
    	    	items:[cardid,{
    	    		xtype     : 'button',
    			    name      : 'email',
    			    iconCls: 'page_find',
    			    width: 23,
    			    handler:function(){
    			    	showMask();
    			    }
    	    	}]
    	  	},{
    	  		id:"ktv-password",
    	  		name:"password",
    	  		xtype:"textfield",
    	  		inputType:"password",
    	  		fieldLabel:"创建密码",
    	  		allowBlank:false,
    	  		maxLength:50
    	  	},
    	  	comboBoxType,
    	  	comboBoxName,
    	  	dayspinner,
    	  	cash,
    	  	{
    	  		id:"ktv-otherdesc",
    	  		name:"otherdesc",
    	  		xtype:"textarea",
    	  		fieldLabel:"保存描述",
    	  		allowBlank:false,
    	  		maxLength:200
    	  	}]
        },{
            xtype:'fieldset',
            checkboxToggle:true,
            title: '补填信息',
            autoHeight:true,
            defaults: {width: 380},
            defaultType: 'textfield',
            collapsed: true,
            items :[{
            		id:"ktv-username",
                    fieldLabel: '用户姓名',
                    name: 'username',
                    maxLength:50
                },{
                	id:"ktv-moblie",
                    fieldLabel: '用户手机',
                    name: 'moblie',
                    maxLength:50
                },{
                	id:"ktv-idcard",
                    fieldLabel: '身份证',
                    name: 'idcard',
                    maxLength:50
                }
            ]
        }]
	});
	console.log(form);
	var resetForm = function(){
		form.getForm().reset();
	}
	var myMask = new Ext.LoadMask(Ext.getBody(), {msg:"正在等待刷卡....(按<font color='red'>回车</font>取消)"});
	var maskState = false;
	function hideMask(){
		myMask.hide();
		maskState = false;
	}
	function showMask(){
		myMask.show();
		maskState = true;
	}
	var winAdd = new Ext.Window({
        width       : 550,
        title		: "卡号添加",
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
            				winAdd.hide();
            			},params:{
            				action:"insert",
            				entity:"com.hrm.entity.KtvStayInfo"
            			},
            			waitMsg:"Loading"
            		});
            	}
            }
        },{
            text     : '关闭',
            handler  : function(){
            	resetForm();
            	winAdd.hide();
            }
        }]
    });
	//winAdd.show();
	var winUpdate = new Ext.Window({
		width       : 550,
        title		: "卡号修改",
        modal		: true,
        closeAction : 'hide',
        allowDomMove: true,
        bodyBorder 	: false,
        plain       : true,
        items       : [form],
        id:'winForm',
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
            				winUpdate.hide();
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
            	winUpdate.hide();
            }
        }]
    });
  	var grid = new Ext.grid.GridPanel({
    	el:"ktv_stay_manager_table",
    	ds:ds,
    	cm:cm,
    	sm:sm,
    	loadMask:true,
    	autoHeight:true,
    	title:'卡号管理',
    	iconCls:"icon-grid",
    	//自动填充表格宽度
    	viewConfig: {
      		forceFit: true,
      		enableRowBody:true,
            showPreview:true
    	},
    	tbar:[{
            text:'添加卡号',
            tooltip:'添加一条新的记录',
            iconCls:'add',
            handler:function(){
            	//resetForm();
            	winAdd.show();
            }
        }, '-', {
            text:'修改卡号',
            tooltip:'修改一条卡号记录',
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
            	cardid.setValue(selections[0].get("cardid"));
            	comboBoxName.setValue(selections[0].get("materialid"));
            	form.getComponent(0).get("ktv-id").setValue(selections[0].get("id"));
            	form.getComponent(0).get("ktv-password").setValue(1);
            	form.getComponent(0).get("ktv-day").setValue(selections[0].get("day"));
            	form.getComponent(0).get("ktv-otherdesc").setValue(selections[0].get("otherdesc"));
            	form.getComponent(0).get("ktv-cash").setValue(selections[0].get("cash"));
            	form.getComponent(1).get("ktv-username").setValue(selections[0].get("username"));
            	form.getComponent(1).get("ktv-moblie").setValue(selections[0].get("moblie"));
            	form.getComponent(1).get("ktv-idcard").setValue(selections[0].get("idcard"));
            	//form.getForm().loadRecord(selections[0]);
            	
            	console.log(winUpdate);
            	winUpdate.show();
            }
        },'-',{
            text:'删除卡号',
            tooltip:'删除一条卡号记录',
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
  	var listenerEvent = {
        loadGrid: function () {
        	ds.baseParams.sql = "SQL-4";
        	ds.baseParams.action = "hql";
        	ds.baseParams.type = "map";
            ds.load({
                params: {
                    start: 0,
                    limit: 20
                }
            });
        }
    };
  	listenerEvent.loadGrid();
  	grid.render();

});