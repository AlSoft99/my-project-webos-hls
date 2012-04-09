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
		//sm,
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
    		dataIndex:"statename",
    		renderer: function(value,data,o){
    			var state = o.data.state;
    			if(state=="3"){
    				return "<font color=red>"+value+"</font>";
    			}else if(state=="1"){
    				return "<font color=green>"+value+"</font>";
    			}
    			return value;
    		}
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
    	name      : 'cardid',
	    fieldLabel: 'Card ID',
	    anchor    : '-20',
	    flex: 1,
	    allowBlank:false,
	    //readOnly:true
    });
    var cardbtn = new Ext.Button({
		xtype     : 'button',
	    name      : 'email',
	    iconCls: 'page_find',
	    width: 23,
	    handler:function(){
	    	scanCard(function(cardnumber){
	    		cardid.setValue(cardnumber);
	    		console.log(form.getComponent(0).getComponent(2));
	    		form.getComponent(0).getComponent(2).focus();
	    	});
	    }
	});
    var cardidQuery = new Ext.form.TextField({
    	name      : 'cardid',
	    fieldLabel: 'Card ID',
	    anchor    : '-20',
	    width:150,
	    allowBlank:true,
	    emptyText:"查询卡号",
	    readOnly:true
    });
    var cardbtnQuery = new Ext.Button({
		xtype     : 'button',
	    name      : 'email',
	    iconCls: 'page_find',
	    width: 23,
	    handler:function(){
	    	scanCard(function(cardnumber){
	    		cardidQuery.setValue(cardnumber);
	    	});
	    }
	});
    
    var cash = new Ext.form.NumberField({
    	name      : 'cash',
	    fieldLabel: '押金(元)',
	    minValue: 0,
        maxValue: 150000,
        value: 10,
	    allowBlank:false
    });
    var cardnumber = "";
    var currentTime = (new Date()).getTime();
    
    var dayspinner = new Ext.ux.form.SpinnerField({
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
    	    	name:"id",
    	    	fieldLabel:"ID",
    	    	hidden:true,
    	    	hideLabel:true
    	  	},{
    	  		xtype: 'compositefield',
    	    	fieldLabel:"卡号扫描",
    	    	items:[cardid,cardbtn]
    	  		
    	  	},{
    	  		name:"password",
    	  		xtype:"textfield",
    	  		inputType:"password",
    	  		fieldLabel:"创建密码",
    	  		allowBlank:false,
    	  		maxLength:50,
    	  		listeners: {
    	  			specialkey: {
    	  				fn: function(o, evt) {
    	  					comboBoxType.focus();
    	  				},
    	  				scope: this
    	  			}
    	  		}
    	  	},
    	  	comboBoxType,
    	  	comboBoxName,
    	  	dayspinner,
    	  	cash,
    	  	{
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
                    fieldLabel: '用户姓名',
                    name: 'username',
                    maxLength:50
                },{
                    fieldLabel: '用户手机',
                    name: 'moblie',
                    maxLength:50
                },{
                    fieldLabel: '身份证',
                    name: 'idcard',
                    maxLength:50
                }
            ]
        }]
	});
	(function(){
    	comboBoxType.on("specialkey",function(field, e){
    		if (e.getKey() == Ext.EventObject.ENTER) {    //触发了listener后，如果按回车，执行相应的方法
    			comboBoxName.focus();
            }
        });
        comboBoxName.on("specialkey",function(field, e){
        	if (e.getKey() == Ext.EventObject.ENTER) {
        		dayspinner.focus();
        	}
        	
        });
        dayspinner.on("specialkey",function(field, e){
        	if (e.getKey() == Ext.EventObject.ENTER) {
        		cash.focus();
        	}
        	
        });
        cash.on("specialkey",function(field, e){
        	if (e.getKey() == Ext.EventObject.ENTER) {
        		setTimeout(function(){
        			form.getComponent(0).getComponent(7).focus();
        		},50);
        		
        	}
        });
    })();
	var resetForm = function(){
		cardbtn.setDisabled(false);
		form.getComponent(0).getComponent(2).setDisabled(false);
		form.getForm().reset();
		/*cardid.setValue("");
    	comboBoxName.setValue("");
    	form.getComponent(0).getComponent(0).setValue("");
    	form.getComponent(0).getComponent(2).setValue("");
    	form.getComponent(0).getComponent(2).setDisabled(false);
    	form.getComponent(0).getComponent(5).setValue(10);
    	form.getComponent(0).getComponent(6).setValue(10);
    	form.getComponent(0).getComponent(7).setValue("");
    	form.getComponent(1).getComponent(0).setValue("");
    	form.getComponent(1).getComponent(1).setValue("");
    	form.getComponent(1).getComponent(2).setValue("");*/
	}
	var myMask = new Ext.LoadMask(Ext.getBody(), {msg:"正在等待刷卡....(按<font color='red'>回车</font>取消)"});
	
	function scanCard(callback,title){
		if(typeof(title)=="undefined"){
			title = "";
		}
		//myMask.show();
		Ext.MessageBox.show({
			   title: title,
	           msg: "正在等待刷卡....(按<font color='red'>回车</font>取消)",
	           width:300,
	           wait:true,
	           waitConfig: {interval:200},
	           animEl: 'mb7'
	    });
		function eventKey(o){
    		var nowTime = (new Date()).getTime();
    		if(nowTime-currentTime>100){
    			cardnumber = "";
    		}
    		currentTime = nowTime;
    		var key = o.keyCode;
    		var strcode=String.fromCharCode(o.keyCode);
        	if(key=="13"){
        		//myMask.hide();
        		Ext.MessageBox.hide();
        		Ext.getBody().un("keydown",eventKey);
        		if(Ext.isFunction(callback)){
        			callback(cardnumber);
        		}
        	}else{
        		cardnumber += strcode;
        	}
	    }
		Ext.getBody().on("keydown",eventKey);
	}
	var submit = function(){
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
    			waitMsg:"Loading",
    			failure:function(form,action){
    				console.log(form);
    				console.log(action);
    				Ext.MessageBox.show({
    					title:"操作提示",
    					msg:action.result.msg,
    					buttons:Ext.MessageBox.OK,
    					icon:Ext.MessageBox.INFO
    				});
    			}
    		});
    	}
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
            	submit();
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
            				action:"update",
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
            	winUpdate.hide();
            }
        }]
    });
	var comboBoxState = comboBoxList.comboBoxSql("select paramscode,paramsname from ParamsList where typeid='KTV_STATE' order by paramscode", "", "");
	comboBoxState.allowBlank = true;
	comboBoxState.emptyText = "默认为全部";
	var test = true;
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
            iconCls:'page_new',
            handler:function(){
            	winAdd.add(form);
            	winAdd.show();
            	resetForm();
            }
        }, {
            text:'修改卡号',
            tooltip:'修改一条卡号记录',
            iconCls:'page_edit',
            handler:function(){
            	/*var selections = grid.getSelectionModel().getSelections();//
            	if(selections.length==0){
					Ext.MessageBox.show({
    					title:"错误提示",
    					msg:"请选择一条需要修改的记录",
    					buttons:Ext.MessageBox.OK,
    					icon:Ext.MessageBox.ERROR
    				});
    				return;
            	}*/
            	scanCard(function(number){
            		if(number=="" && !test){
            			return;
            		}
            		Ext.Ajax.request({
            			url: 'ktvStayInfoVo.do',
            			success: function(o){
        					var data = eval("("+o.responseText+")");
        					if(data.length>0){
        						winUpdate.add(form);
        						winUpdate.show();
        						//console.log(form.getComponent(0).getComponent(0));
        						var Record = Ext.data.Record.create([
	                                 {name: 'cardid',     type: 'string'},
	                                 {name: 'materialid',     type: 'string'},
	                                 {name: 'id', type: 'string'},
	                                 {name: 'password',  type: 'string'},
	                                 {name: 'day',   type: 'string'},
	                                 {name: 'otherdesc',   type: 'string'},
	                                 {name: 'cash',   type: 'string'},
	                                 {name: 'username',   type: 'string'},
	                                 {name: 'moblie',   type: 'string'},
	                                 {name: 'idcard',   type: 'string'}
	                            ]);
        						form.form.loadRecord(new Record({
        	                        'cardid'    : data[0].cardid,
        	                        'materialid'    : data[0].materialid,
        	                        'id': data[0].id,
        	                        'password' : '1',
        	                        'day': data[0].day,
        	                        'otherdesc'  : data[0].otherdesc,
        	                        'cash'  : data[0].cash,
        	                        'username'  : data[0].username,
        	                        'moblie'  : data[0].moblie,
        	                        'idcard'    : data[0].idcard
        	                    }));
        						cardbtn.setDisabled(true);
        						form.getComponent(0).getComponent(2).setDisabled(true);
        						/*cardid.setValue(data[0].cardid);
        						cardid.originalValue="";
        		            	comboBoxName.setValue(data[0].materialid);
        		            	comboBoxName.originalValue="";
        		            	form.getComponent(0).getComponent(0).setValue(data[0].id);
        		            	form.getComponent(0).getComponent(2).setValue(1);
        		            	form.getComponent(0).getComponent(2).setDisabled(true);
        		            	form.getComponent(0).getComponent(5).setValue(data[0].day);
        		            	form.getComponent(0).getComponent(7).setValue(data[0].otherdesc);
        		            	form.getComponent(0).getComponent(6).setValue(data[0].cash);
        		            	form.getComponent(1).getComponent(0).setValue(data[0].username);
        		            	form.getComponent(1).getComponent(1).setValue(data[0].moblie);
        		            	form.getComponent(1).getComponent(2).setValue(data[0].idcard);*/
        		            	//form.getForm().loadRecord(data[0]);
        		            	
        		            	
        					}else{
        						Ext.MessageBox.show({
        	    					title:"错误提示",
        	    					msg:"未找到卡号为<font color=red>"+number+"</font>的记录!",
        	    					buttons:Ext.MessageBox.OK,
        	    					icon:Ext.MessageBox.ERROR
        	    				});
        					}
            			},
            			failure: function(o){
        					
            			},
            			params: { cardid:"111111111" , action:"queryCard" }
        			});
            	});
            	
            }
        },{
            text:'删除卡号',
            tooltip:'删除一条卡号记录',
            iconCls:'page_delete',
            handler:function(){
            	scanCard(function(number){
            		if(number=="" && !test){
            			return;
            		}
            		Ext.Ajax.request({
            			url: 'ktvStayInfoVo.do',
            			success: function(o){
            				Ext.MessageBox.show({
    	    					title:"操作提示",
    	    					msg:o.responseText,
    	    					buttons:Ext.MessageBox.OK,
    	    					icon:Ext.MessageBox.INFO
    	    				});
            				ds.reload();
            			},
            			failure: function(o){
            				Ext.MessageBox.show({
    	    					title:"错误提示",
    	    					msg:o.responseText,
    	    					buttons:Ext.MessageBox.OK,
    	    					icon:Ext.MessageBox.ERROR
    	    				});
            			},
            			params: { cardid:"111111111" , action:"delete" }
        			});
            	});
            }
        },"-",{
        	text:'归还卡号',
            tooltip:'取出一条卡号记录',
            iconCls:'icon_package_open',
            handler:function(){
            	scanCard(function(number){
            		if(number=="" && !test){
            			return;
            		};
            		inputPsdCard = number;
            		winPsd.show();
            		setTimeout(function(){
            			inputPsd.focus();
            		},300);
            		
            	});
            }
        },"-",cardidQuery,cardbtnQuery,comboBoxState,{
			text:"查询",
			iconCls:"icon-grid",
			tooltip:'默认为当天查询',
			handler:function(event,mouse){
				
				listenerEvent.loadGrid();
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
  	var submitPsd = function(){
    	var basicForm = formPsd.getForm();
    	if(basicForm.isValid()){
    		basicForm.submit({
    			success:function(form,action){
    				Ext.MessageBox.show({
    					title:"成功提示",
    					msg:action.result.msg,
    					buttons:Ext.MessageBox.OK,
    					icon:Ext.MessageBox.INFO
    				});
    				winPsd.hide();
    				basicForm.reset();
    				ds.reload();
    			},
    			failure:function(form,action){
    				Ext.MessageBox.show({
    					title:"错误提示",
    					msg:action.result.msg,
    					buttons:Ext.MessageBox.OK,
    					icon:Ext.MessageBox.ERROR
    				});
    			},
    			params:{
    				action:"confirmPsd",
    				password:inputPsd.getValue(),
    				//cardid: inputPsdCard
    				cardid: "111111111"
    			},
    			waitMsg:"Loading"
    		});
    	}
  	}
  	var inputPsdCard = "";
  	var inputPsd = new Ext.form.TextField({
  		name:"password",
  		fieldLabel:"输入密码",
  		inputType:"password",
  		width:150,
  		allowBlank:false,
  		maxLength:50
  	});
  	inputPsd.on("specialkey",function(field,e){
  		if (e.getKey() == Ext.EventObject.ENTER) {
  			submitPsd();
  		}
  	});
  	var formPsd = new Ext.form.FormPanel({
	  	defaultType:"textfield",
	  	labelAlign:"right",
	  	url:"ktvStayInfoVo.do",
	  	labelWidth:80,
	  	frame:true,
	  	width:200,
	  	items:[inputPsd]
	});
	var winPsd = new Ext.Window({
        layout      : 'fit',
        width       : 300,
        title		: "输入密码",
        height  	: 120,
        modal		: false,
        closeAction : 'hide',
        loadMask:true,
        allowDomMove: true,
        bodyBorder 	: false,
        plain       : true,
        items       : [
            formPsd
		],
        buttons: [{
            text     : '保存',
            handler	 : function(){
            	submitPsd();
            }
        },{
            text     : '关闭',
            handler  : function(){
            	winPsd.hide();
            	formPsd.getForm().reset();
            }
        }]
    });
  	
  	
  	var listenerEvent = {
        loadGrid: function () {
        	var where1 = "";
        	if(comboBoxState.getValue()!="" && typeof(comboBoxState.getValue())!="undefined"){
        		where1 = " and a.state='"+comboBoxState.getValue()+"' ";
        	}else{
        		where1 = "";
        	}
        	var where2 = "";
        	if(cardidQuery.getValue()!="" && typeof(cardidQuery.getValue())!="undefined"){
        		where2 = " and a.cardid='"+cardidQuery.getValue()+"' ";
        	}else{
        		where2 = "";
        	}
        	ds.baseParams.sql = "SQL-4";
        	ds.baseParams.action = "hql";
        	ds.baseParams.type = "map";
        	ds.baseParams["{0}"] = where1;
        	ds.baseParams["{1}"] = where2;
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