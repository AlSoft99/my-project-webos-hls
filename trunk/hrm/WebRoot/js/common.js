/**
 * 
 * @type {}
 */
var loading = {
	loadMask:function(obj,msg){
		if(obj==null || typeof(obj)=="undefined"){
			return new Ext.LoadMask(Ext.getBody(), {msg:typeof(msg)=="undefined"?"请稍后...":msg});
		}else{
			return new Ext.LoadMask(obj, {msg:typeof(msg)=="undefined"?"请稍后...":msg});
		}
	}
}
var comboBoxList = {
	goodsType : new Ext.form.ComboBox({
  		name: "goodstype",
  		fieldLabel: '货物种类',
	  	store: new Ext.data.Store({
            proxy:new Ext.data.HttpProxy({url:"optionServlet.do?option=storeType"}),
            autoLoad:true,
			reader:new Ext.data.ArrayReader({},[
			    {name:"value"},
			    {name:"text"}
			])
        }),
        width:150,
	  	emptyText: "请选择",
	  	mode: "local",
	  	forceSelection :true,
	  	triggerAction: "all",
	  	valueField: "value",
	  	displayField: "text",
	  	hiddenName: "goodstype"
	}),
	goodsList :new Ext.form.ComboBox({
  		name: "goodsid",
  		fieldLabel: '货物名称',
	  	store: new Ext.data.Store({
            proxy:new Ext.data.HttpProxy({
            	url:"optionsChangeServlet.do?option=storeList"
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
	  	hiddenName: "goodsid"
	}),
	roleList:new Ext.form.ComboBox({
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
	  		/*select:function(obj,option){
	  			var roleCode = option.data.value;
	  			form.getComponent(1).getStore().load({
	  				params:{option:"userInfo",change:roleCode}
	  			});
	  			form.getComponent(1).setValue("");
	  		}*/
	  	}
	}),
	userList:new Ext.form.ComboBox({
  		name: "recuser",
  		fieldLabel: '仓库人',
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
	  	hiddenName: "recuser"
	}),
	comboBoxSql:function(sql,fieldLabel,fieldName,fnt){
		return new Ext.form.ComboBox({
	  		name: fieldName,
	  		fieldLabel: fieldLabel,
		  	store: new Ext.data.Store({
	            proxy:new Ext.data.HttpProxy({
	            	url:"optionSqlServlet.do"
	            }),
	            baseParams:{
            		sql:sql
            	},
	            autoLoad:true,
				reader:new Ext.data.ArrayReader({},[
				    {name:"value"},
				    {name:"text"}
				]),
				listeners:{
					load:function(store,record,opts){
						if(typeof(fnt)!="undefined"){
							new fnt(store,record,opts);
						}
					}
				}
	        }),
	        width:150,
		  	emptyText: "请选择",
		  	allowBlank:false,
		  	mode: "local",
		  	forceSelection :true,
		  	triggerAction: "all",
		  	valueField: "value",
		  	displayField: "text",
		  	hiddenName: fieldName
		});
	},
	getValue:function(array,key){
		for(var i = 0 ; i < array.length;i++ ){
			var temp = array[i];
			if(temp[0]==key){
				return temp[1];
			}
		}
		return "";
	},
	getKey:function(array,value){
		for(var i = 0 ; i < array.length;i++ ){
			var temp = array[i];
			if(temp[1]==value){
				return temp[0];
			}
		}
		return "";
	},
	getArray:function(combobox){
		return combobox.getStore().reader.arrayData;
	}
}
var trans = {
	//翻译下拉框
	comboBox:function(comboBox,record){
		var items = comboBox.getStore().data.items;
		for(var i = 0 ; i < items.length ; i++ ){
			if(items[i].data.value==record){
				return items[i].data.text;
			}
		}
		return "";
	}
}

function fxMultiply(a,b) { 
	var   f1   =   String(a).split( ". ").length> 1   ?   String(a).split( ". ")[1].length   :   0; 
	var   f2   =   String(b).split( ". ").length> 1   ?   String(b).split( ". ")[1].length   :   0; 
	var   aa   =   String(a).replace(/^0*|\./g, ' '); 
	var   bb   =   String(b).replace(/^0*|\./g, ' '); 
	return   Number(aa)*Number(bb)/Math.pow(10,f1+f2); 
} 
