Ext.BLANK_IMAGE_URL = "/hrm/ext/resources/images/default/s.gif";
Ext.onReady(function(){
	Ext.QuickTips.init();
	var cp = new Ext.state.CookieProvider({
		expires:new Date().clearTime().add(Date.DAY,30)
	});
	var sysStore = new Ext.data.ArrayStore({
        fields: ['code', 'text'],
        data : [
        	["main.jsp","进存销管理系统"],
        	["desktop.jsp","Shirley家族系统"]
        ] // from states.js
    });

	var form = new Ext.form.FormPanel({
	  	defaultType:"textfield",
	  	labelAlign:"right",
	  	url:"login.login",
	  	labelWidth:80,
	  	frame:true,
	  	width:200,
	  	items:[{ 
	    	name:"user_code",
	    	fieldLabel:"用户名称",
	    	allowBlank:false,
	    	emptyText:"记住账号",
	    	width:150,
	    	maxLength:50
	  	},{
	  		name:"user_pwd",
	  		fieldLabel:"用户密码",
	  		inputType:"password",
	  		width:150,
	  		allowBlank:false,
	  		maxLength:50
	  	},new Ext.form.ComboBox({
	  		name: "sys_code",
	  		fieldLabel: '系统名称',
		  	store: sysStore,
	        width:150,
		  	emptyText: "请选择",
		  	allowBlank:false,
		  	mode: "local",
		  	forceSelection :true,
		  	triggerAction: "all",
		  	valueField: "code",
		  	displayField: "text",
		  	hiddenName: "role_code"
		})]
	});
	var win = new Ext.Window({
        layout      : 'fit',
        width       : 300,
        title		: "登陆窗口",
        height  	: 170,
        modal		: false,
        closeAction : 'hide',
        allowDomMove: true,
        bodyBorder 	: false,
        plain       : true,
        items       : [
			form
		],
        buttons: [{
            text     : '保存',
            handler	 : function(){
            	var basicForm = form.getForm();
            	if(basicForm.isValid()){
            		basicForm.submit({
            			success:function(obj,action){
            				window.location.href = form.getComponent(2).getValue();
            				cp.set("username",form.getComponent(0).getValue());
            				win.hide();
            			},
            			failure:function(obj,action){
            				Ext.MessageBox.show({
            					title:"错误提示",
            					msg:action.result.msg,
            					buttons:Ext.MessageBox.OK,
            					icon:Ext.MessageBox.INFO
            				});
            			},
            			waitMsg:"Loading"
            		});
            	}
            }
        },{
            text     : '关闭',
            handler  : function(){
                win.hide();
            }
        }]
    });
	win.show();
	if(cp.get("username","")!=""){
		form.getComponent(0).setValue(cp.get("username",""));
	}

});