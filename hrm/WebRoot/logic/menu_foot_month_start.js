Ext.onReady(function() {
	Ext.QuickTips.init();
	var height = Ext.getBody().getHeight()-20;
	var width = Ext.getBody().getWidth();
	/**
	 * 树形
	 */
	var date = new Date();
	var currentId = "";
	var currentSalaryList ;
	var currentDate = date.format('Ym');
	var tree_menu = new Ext.tree.TreePanel({
		title:"参数类型选择",
		width : 300,
		autoScroll:true,
		height : height-500,
		loader : new Ext.tree.TreeLoader({
			dataUrl : "treeInfoQuery.do?action=single",
			baseParams :{
				parent: "select new map(id as id,typename as text,typedesc as qtip) from MaterialType"
//				child: "select new map(id as id,goodsname as text,goodsdesc as qtip,typeid as nodeId,goodsnumber as number,price as price) from GoodsList"
			}
		})
	});
	var root = new Ext.tree.AsyncTreeNode({
		id : "0",
		text : "种类"
	});
	function checkStore(){
		Ext.Ajax.request({
		   url: 'materialStoreVo.do?storedate='+currentDate,
		   success: function(o){
			   if(Ext.util.Format.trim(o.responseText)!=""){
				   Ext.MessageBox.show({
						title:"错误提示",
						msg: o.responseText,
						buttons:Ext.MessageBox.OK,
						icon:Ext.MessageBox.ERROR
			   		});
			   }
				if(typeof(suCallback)!="undefined"){
					suCallback(o);
				}
		   },
		   failure: function(o){
				if(typeof(faCallback)!="undefined"){
					faCallback(o);
				}
		   },
		   params: { storedate: currentDate, action:"check" }
		});
	};
	checkStore();
	tree_menu.setRootNode(root);
	tree_menu.getRootNode().expand();// 2315
	tree_menu.on("click",function(node){
		if(node.leaf){
			//grid.addBtn.setDisabled(false);
			currentId = node.attributes.id;
			/*
			ds.load({
		  		params:{
		  			start:0, 
		  			limit:10,
		  			action:"hql",
					type:"entity",
					sql:"from MaterialList where typeid='"+currentId+"'"
		  		}
		  	});
		  	*/
			listenerEvent.loadGrid();
		}else{
			//grid.addBtn.setDisabled(true);
		}
	});
	/**
	 * 右键菜单
	 */
    var form = new Ext.form.FormPanel({
	  	defaultType:"textfield",
	  	labelAlign:"right",
	  	labelWidth:80,
	  	url:"materialTypeVo.do",
	  	frame:true,
	  	width:200,
	  	items:[{
	    	name:"tree_name",
	    	fieldLabel:"种类名称",
	    	allowBlank:false,
	    	width:150,
	    	maxLength:50
	  	},{
	    	name:"tree_tips",
	    	fieldLabel:"种类描述",
	    	allowBlank:false,
	    	width:150,
	    	maxLength:100,
	    	emptyText:"默认和种类名称一致"
	  	}]
	});
	var node_menu = null;
	var contextmenu = new Ext.menu.Menu({
  		items:[{
  			text:"刷新",
  			iconCls:"option",
  			handler:function(event,mouse){
  				tree_menu.getRootNode().reload();
  			}
  		}]
	});
	tree_menu.on("contextmenu",function(node,e){
  		e.preventDefault();
  		node.select();
  		node_menu = node;
  		contextmenu.items.items[0].show();
	});
	
	/**
	 * 参数明细表
	 */
	var editor = new Ext.ux.grid.RowEditor({
        saveText: 'Update'
    });
	var params = Ext.data.Record.create([
		{name: "id", type: 'string'},
		{name: "typeid", type: 'string'},
		{name: "paramscode", type: 'string'},
		{name: "paramsname", type: 'string' },
		{name: "initsum", type: 'float' },
		{name: "unitname", type: 'string' },
        {name: "cost", type: 'float' },
		{name: "updtuser", type: 'float'},
    	{name: "updttime",type:"date",dateFormat:"Y-m-d H:i:s.u"}
    ]);

	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sm,
		{
			header:"ID",
			dataIndex:"id",
			hidden:true,
			sortable: true
		},{
			header:"父节点参数编号",
			width:150,
			sortable: true,
			hidden:true,
			dataIndex:"typeid"
		},{
    		header:"编号",
    		dataIndex:"paramscode",
    		sortable: true,
    		width:150,
    		hidden:true
    	},{
    		header:"名称",
    		dataIndex:"paramsname",
    		sortable: true,
    		width:150
        }, {
            xtype: 'numbercolumn',
            header: "月末实际库存",
            dataIndex: "initsum",
            sortable: true,
            width: 150,
            format : "0,0.00",
            editor: {
                xtype: 'numberfield',
                minValue: 0,
                maxValue: 150000,
                allowBlank: false
            }
        },{
    		header:"单位",
    		dataIndex:"unitname",
    		sortable: true,
    		width:150
        }, {
            xtype: 'numbercolumn',
            header: "价格",
            dataIndex: "cost",
            sortable: true,
            width: 150,
            format : "￥0,0.00"
        },{
			xtype: 'datecolumn',
			header:"最后更新时间",
			sortable: true,
			dataIndex:"updttime",
			width:150,
			format: 'Y-m-d H:i:s.u',
			hidden:true
		},{
			header:"最后更新人",
			sortable: true,
			width:150,
			hidden:true,
			dataIndex:"updtuser"
		}
  	]);
  	cm.defaultSortable = true;
  	var ds = new Ext.data.Store({
    	proxy: new Ext.data.HttpProxy({
    		url:"queryInfoVo.do"
    	}),
		reader: new Ext.data.JsonReader({
			totalProperty:"totalProperty",
			root:"root"
		},params)
  	});
  	
  	var grid = new Ext.grid.GridPanel({
    	ds:ds,
    	cm:cm,
    	sm:sm,
    	loadMask:true,
    	height : height-180,
    	plugins: [editor],
    	iconCls:"icon-grid",
    	bbar:new Ext.PagingToolbar({
		    pageSize:10,
		    store:ds,
		    displayInfo:true,
		    displayMsg:"显示第{0}条到{1}条记录,一共{2}条记录",
		    emptyMsg:"无记录"
		})

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

	var listenerEvent = {
    	update:function(updateObj,jsonRecord){
    		var recordData = editor.record.data;
    		loading.loadMask().show();
    		Ext.Ajax.request({
    			url:"materialStoreVo.do?action=updateRecord&storedate="+currentDate,
    			params:recordData,
    			success:function(o){
    				if(Ext.util.Format.trim(o.responseText)!=""){
					   Ext.MessageBox.show({
							title:"错误提示",
							msg: o.responseText,
							buttons:Ext.MessageBox.OK,
							icon:Ext.MessageBox.ERROR
				   		});
				   }
    				loading.loadMask().hide();
    			},failure: function(o){
    				loading.loadMask().hide();
    				
    			}
    		});
    	},
    	loadGrid:function(){
    		ds.load({
		  		params:{
		  			start:0, 
		  			limit:10,
		  			action:"hql",
					type:"map",
					sql:"select new map(a.id.id as id,a.typeid as typeid,a.initsum as initsum,b.cost as cost,b.paramscode as paramscode,b.paramsname as paramsname,a.updtuser as updtuser,a.updttime as updttime,b.unit as unit,c.paramsname as unitname) from MaterialStoreList a,MaterialList b,ParamsList c where c.paramscode=b.unit and c.typeid='UNIT' and a.id.id=b.id and a.typeid='"+currentId+"' and a.id.storedate='"+currentDate+"'"
		  		}
		  	});
    	}
    	
    }
	editor.addListener("afteredit",listenerEvent.update);
	var comboBoxDate = new Ext.form.ComboBox({
  		name: "month",
  		fieldLabel: '月份',
	  	store: new Ext.data.Store({
            proxy:new Ext.data.HttpProxy({url:"optionServlet.do?option=month"}),
            autoLoad:true,
			reader:new Ext.data.ArrayReader({},[
			    {name:"value"},
			    {name:"text"}
			])
        }),
        dateFormat:'Y-m',
        width:150,
	  	emptyText: "默认为当月",
	  	mode: "local",
	  	forceSelection :true,
	  	triggerAction: "all",
	  	valueField: "value",
	  	displayField: "text",
	  	hiddenName: "goodstype"
	});
	var monthBtn = new Ext.Button({
		text:"查询",
		iconCls:"icon-grid",
		tooltip:'请选择月份查询',
		handler:function(event,mouse){
			currentDate = comboBoxDate.getValue();
			tree_menu.getRootNode().reload();
			checkStore();
		}
	});
	var toolbar = new Ext.Toolbar({
		items:[
			comboBoxDate,
			monthBtn
		]
	});
	new Ext.Panel({
        title: '种类',
        collapsible:true,
        renderTo: 'menu_foot_month_start_name',
        width : 250,
        height : height-100,
        tbar:toolbar,
        items:[
	        new Ext.TabPanel({
		        border:false,
		        activeTab:0,
		        width : 250,
		        height : height-180,
		        tabPosition:'bottom',
		        items:[
		        	tree_menu
		        ]
		    })
		]
    });
    
    new Ext.Panel({
        title: '原材料明细列表',
        collapsible:true,
        width : width-495,
        renderTo: 'menu_foot_month_start_list',
        height : height-100,
        items:[grid]
    });
    grid.getSelectionModel().on('selectionchange', function(sm){
        //grid.removeBtn.setDisabled(sm.getCount() < 1);
    });

});