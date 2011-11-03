Ext.onReady(function(){
	var cm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{
			header:"唯一编号",
			dataIndex:"user_id"
		},{
    		header:"用户名",
    		dataIndex:"user_name",
    		editor: new Ext.form.TextField({
               allowBlank: false,
               maxLength:20
           })
    	},{
    		header:"用户密码",
    		dataIndex:"user_password"
    	},{
    		header:"更新时间",
    		dataIndex:"updt_time",
    		renderer:formatDate,
    		editor: new Ext.form.DateField({
                format: 'Y-m-d',
                disabledDays: [0, 6],
                disabledDaysText: 'Plants are not available on the weekends'
            })

    	}
  	]);
  	function formatDate(value){
  		var dt = new Date(value);
        return dt.format("Y-m-d");
    };

  	cm.defaultSortable = true;
  	var ds = new Ext.data.Store({
    	proxy: new Ext.data.HttpProxy({url:"query.query"}),
		reader: new Ext.data.JsonReader({
			totalProperty:"totalProperty",
			root:"root"
		},[
    		{name: "user_id", type: 'string'},
    		{name: "user_name", type: 'string'},
    		{name: "user_password", type: 'string'},
    		{name: "updt_time",type:"date",dateFormat:"Y-m-d"}
 		])
  	});
  	// type so we can add records dynamically
    var Plant = Ext.data.Record.create([
           // the "name" below matches the tag name to read, except "availDate"
           // which is mapped to the tag "availability"
           {name: 'user_id', type: 'string'},
           {name: 'user_name', type: 'string'},
           {name: 'user_password', type: 'string'},
           {name: 'updt_time', type: 'date', dateFormat: 'Y-m-d'}
      ]);

  	var grid = new Ext.grid.EditorGridPanel({
    	el:"user_add_table",
    	ds:ds,
    	cm:cm,
    	autoHeight:true,
    	title:'用户添加',
    	iconCls:"icon-grid",
    	//自动填充表格宽度
    	viewConfig: {
      		forceFit: true
    	},
    	tbar:[{
            text:'添加用户',
            tooltip:'添加一条新的记录',
            iconCls:'add',
            handler:function(){
            	var p = new Plant({
                    user_id: '<font color="red">系统将自动生成Id</font>',
                    user_name: '',
                    user_password: '<font color="red">系统将自动生成初始密码</font>',
                    updt_time: (new Date()).clearTime()
                });
                grid.stopEditing();
                ds.insert(0, p);
                grid.startEditing(0, 0);
            }
        }, '-', {
            text:'保存',
            tooltip:'对添加或者修改的用户进行保存',
            iconCls:'option',
            handler:function(){
            	var records = ds.getModifiedRecords();
            	var flag = true;
				for(var i = 0 ; i < records.length; i++){
				  	var param = records[i].data;
				  	Ext.Ajax.request({
				  		url:"UserAddVo.vo",
				  		params:param,
				  		success:function(response){
				  			flag = true;
				  		},
				  		failure:function(response){
				  			flag = false;
				  		}
				  	});
				}
				if(flag){
					Ext.MessageBox.show({
			           title: '操作提示',
			           msg: '保存成功!',
			           buttons: Ext.MessageBox.OK,
			           icon: Ext.MessageBox.INFO
			       	});
			       	ds.commitChanges();
			       	ds.reload();
				}else{
					Ext.MessageBox.show({
			           title: '操作提示',
			           msg: '操作失败!',
			           buttons: Ext.MessageBox.OK,
			           icon: Ext.MessageBox.ERROR
			       	});
			       	ds.commitChanges();
			       	ds.reload();
				}
            }
        },'-',{
            text:'删除用户',
            tooltip:'删除一条用户记录',
            iconCls:'remove',
            handler:function(){
            	Ext.Ajax.request({
			  		url:"UserAddVo.do",
			  		params:[],
			  		success:function(response){
			  		},
			  		failure:function(response){
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