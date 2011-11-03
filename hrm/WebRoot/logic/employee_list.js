Ext.onReady(function() {
	Ext.QuickTips.init();
	var height = Ext.getBody().getHeight()-20;
	var width = Ext.getBody().getWidth();
	/**
	 * 树形
	 */
	var currentUserId = "";
	var currentSalaryList ;
	var tree_role = new Ext.tree.TreePanel({
		title:"员工选择",
		width : 300,
		height : height-500,
		autoScroll:true,
		loader : new Ext.tree.TreeLoader({
			dataUrl : "treeInfoQuery.do?action=select",
			baseParams :{
				parent: "select new map(roleCode as id,roleName as text,roleDesc as qtip) from RoleInfo",
				child: "select new map(userId as id,userName as text,userName as qtip,roleInfo.roleCode as nodeId) from UserInfo"
			}
		})
	});
	var root = new Ext.tree.AsyncTreeNode({
		id : "0",
		text : "角色分类"
	});
	tree_role.setRootNode(root);
	tree_role.getRootNode().expand();// 2315
	tree_role.on("click",function(node){
		if(node.leaf){
			grid.addBtn.setDisabled(false);
			currentUserId = node.attributes.id;
			var user_name = node.attributes.text;
			Ext.Ajax.request({
        		url:"employeeListVo.do",
        		params:{
        			action:"welfare",
        			user_id:currentUserId,
        			user_name:user_name
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
	 * 员工工资明细表
	 */
	var editor = new Ext.ux.grid.RowEditor({
        saveText: 'Update'
    });
	var Employee = Ext.data.Record.create([
		{name: "userId", type: 'string'},
    	{name: "salarydate",type:"date",dateFormat:"Y-m-d H:i:s.u"},
    	{name: "othersmart", type: 'float'},
    	{name: "smartreason", type: 'string'},
    	{name: "factsalary", type: 'float'},
    	{name: "issend", type: 'bool'}
    ]);

	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sm,
		{
			header:"员工id",
			dataIndex:"userId",
			sortable: true,
			hidden:true
		},{
			header:"发薪日期",
			dataIndex:"salarydate",
			renderer:formatDate,
			sortable: true,
			format: 'Y-m-d',
			editor: {
                xtype: 'datefield',
                allowBlank: false
            }
		},{
			xtype: 'numbercolumn',
    		header:"其他罚款",
    		dataIndex:"othersmart",
    		format: '￥0,0.00',
    		sortable: true,
    		editor: {
                xtype: 'numberfield',
                minValue: -100,
                maxValue: 150000,
                listeners:{
                	change:function(itemObj,inputData){
                		var factsalary = currentSalaryList.基本工资*1 + currentSalaryList.上岗工资*1+currentSalaryList.福利补贴*1-currentSalaryList.个人所得税*1-currentSalaryList.公积金*1-currentSalaryList.养老保险金*1-currentSalaryList.医疗保险金*1-currentSalaryList.综合保险金*1-currentSalaryList.其他福利保险*1;
                		editor.items.items[6].setValue(factsalary-inputData*1);
                	}
                },
                allowBlank: false
            }

    	},{
    		header:"罚款理由",
    		dataIndex:"smartreason",
    		width:200,
    		sortable: true,
    		editor: {
                xtype: 'textfield',
                maxLength:500,
                allowBlank: false
            }

    	},{
    		xtype: 'numbercolumn',
    		header:"实发工薪",
    		format: '￥0,0.00',
    		dataIndex:"factsalary",
    		sortable: true,
    		editor: {
                xtype: 'numberfield',
                minValue: 1,
                maxValue: 150000,
                allowBlank: false
            }

    	},{
    		xtype: 'booleancolumn',
    		header:"是否已发工薪",
    		align: 'center',
    		dataIndex:"issend",
    		trueText: 'Yes',
            falseText: 'No',
            sortable: true,
            editor: {
                xtype: 'checkbox'
            }

    	}
  	]);
  	function formatDate(value){
  		var dt = new Date(value);
  		var current = new Date();
  		var returnDate = dt.format("Y-m-d");
  		var currentDate = current.format("Y-m-d");
  		if(returnDate==currentDate){
  			grid.addBtn.setDisabled(true);
  		}
        return returnDate;
    };
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
            text:'增加月工资',
            tooltip:'添加一条新的记录',
            iconCls:'icon-user-add',
            disabled: true,
            handler:function(){
            	loading.loadMask().show();
            	editor.setDisabled(true);
            	var factsalary = currentSalaryList.基本工资*1 + currentSalaryList.上岗工资*1+currentSalaryList.福利补贴*1-currentSalaryList.个人所得税*1-currentSalaryList.公积金*1-currentSalaryList.养老保险金*1-currentSalaryList.医疗保险金*1-currentSalaryList.综合保险金*1-currentSalaryList.其他福利保险*1;
            	var record = {
                    userId: currentUserId,
                    salarydate: (new Date()).clearTime().format('Y-m-d'),
                    othersmart: 0,
                    smartreason:'暂无记录',
                    factsalary:factsalary,
                    issend: false
                };
                Ext.Ajax.request({
	        		url:"employeeListVo.do?action=insert",
	        		params:record,
	        		success:function(action){
	        			loading.loadMask().hide();
	        			editor.setDisabled(false);
	        			grid.addBtn.setDisabled(true);
	    			},
	    			failure:function(action){
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
            text:'删除记录',
            tooltip:'删除一条工资记录',
            iconCls:'icon-user-delete',
            disabled: true,
            handler:function(){
            	editor.stopEditing();
                var s = grid.getSelectionModel().getSelections();
                var userId = "";
                var salarydate = "";
                for(var i = 0, r; r = s[i]; i++){
                    ds.remove(r);
                    userId += r.data.userId+",";
                    salarydate += (new Date(r.data.salarydate)).format('Y-m-d')+",";
                }
				userId = userId.substring(0,userId.length-1);
				salarydate = salarydate.substring(0,salarydate.length-1);
				Ext.Ajax.request({
	        		url:"employeeListVo.do?action=delete",
	        		params:{
	        			userId:userId,
	        			salarydate:salarydate
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
        }],
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
  	var propertySource = {};
	var salaryList = new Ext.grid.PropertyGrid({
        title: '员工福利明细', 
        closable: false
    });
    salaryList.setSource(propertySource);
	salaryList.on("beforeedit",function(e){
	  	e.cancel = true;
	  	return false;
	});
	var listenerEvent = {
    	update:function(updateObj,jsonRecord){
    		var recordData = editor.record.data;
    		recordData.salarydate = recordData.salarydate.format('Y-m-d');;
    		Ext.Ajax.request({
    			url:"employeeListVo.do?action=update",
    			params:recordData
    		});
    	},
    	loadGrid:function(){
    		ds.baseParams.sql = "from EmployeeList where id.userId='"+currentUserId+"' order by salarydate desc";
    		ds.baseParams.action = "hql";
    		ds.baseParams.type = "entity";
    		ds.load({
		  		params:{   
		  			start:0, 
		  			limit:10
		  		}
		  	});
    	}
    	
    }
    listenerEvent.loadGrid();
	editor.addListener("afteredit",listenerEvent.update);
	new Ext.Panel({
        title: '员工选择项',
        collapsible:true,
        renderTo: 'employee_list_name',
        width : 250,
        height : height-100,
        items:[
	        new Ext.TabPanel({
		        border:false,
		        activeTab:0,
		        width : 250,
		        height : height-180,
		        tabPosition:'bottom',
		        items:[
		        	tree_role,
		            salaryList
		        ]
		    })
		]
    });
    
    new Ext.Panel({
        title: '员工工资明细',
        collapsible:true,
        width : width-495,
        renderTo: 'employee_list_salary',
        height : height-100,
        items:[grid]
    });
    grid.getSelectionModel().on('selectionchange', function(sm){
        grid.removeBtn.setDisabled(sm.getCount() < 1);
    });

});