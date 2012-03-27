var projectName = "hrm";
Ext.BLANK_IMAGE_URL = "/"+projectName+"/ext/resources/images/default/s.gif";
Ext.chart.Chart.CHART_URL = '/'+projectName+'/ext/resources/charts.swf';
Ext.onReady(function() {
	var myMask = new Ext.LoadMask(Ext.getBody(), {msg:"数据读取中...请稍后..."});
	myMask.show();
	console.log("==========Ext Object==================");
	console.log(Ext);
	console.log("==========Ext Object==================");
	Ext.QuickTips.init();
	var role_code = document.getElementById("role_code").value;
	var propertyGrid_base = new Ext.grid.PropertyGrid({
        title: '用户信息', 
        closable: false
    });
    propertyGrid_base.setSource(baseMsg);
    
    var modpwd = function(){
		var form = new Ext.form.FormPanel({
		  	defaultType:"textfield",
		  	labelAlign:"right",
		  	url:"checkSessionVo.do",
		  	labelWidth:80,
		  	frame:true,
		  	width:200,
		  	items:[{
		  		name:"user_pwd",
		  		fieldLabel:"原密码",
		  		inputType:"password",
		  		width:150,
		  		allowBlank:false,
		  		maxLength:50
		  	},{
		  		name:"new_pwd",
		  		fieldLabel:"新密码",
		  		inputType:"password",
		  		width:150,
		  		allowBlank:false,
		  		maxLength:50
		  	},{
		  		name:"confirm_pwd",
		  		fieldLabel:"确认密码",
		  		inputType:"password",
		  		width:150,
		  		allowBlank:false,
		  		maxLength:50
		  	}]
		});
		var win = new Ext.Window({
	        layout      : 'fit',
	        width       : 300,
	        title		: "修改密码",
	        height  	: 180,
	        modal		: false,
	        closeAction : 'hide',
	        loadMask:true,
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
	            	if(form.getComponent(1).getValue()!=form.getComponent(2).getValue()){
	            		Ext.MessageBox.show({
        					title:"错误提示",
        					msg:"请确认新密码与确认密码一致!",
        					buttons:Ext.MessageBox.OK,
        					icon:Ext.MessageBox.ERROR
        				});
        				return false;
	            	}
	            	if(basicForm.isValid()){
	            		basicForm.submit({
	            			success:function(form,action){
	            				Ext.MessageBox.show({
	            					title:"成功提示",
	            					msg:action.result.msg,
	            					buttons:Ext.MessageBox.OK,
	            					icon:Ext.MessageBox.INFO
	            				});
	            				win.hide();
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
	            				action:"modpwd"
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
	}
	var logout = function(){
		Ext.MessageBox.show({
			title:"警告提示",
			msg:"是否确定要退出该系统??",
			buttons:Ext.MessageBox.OKCANCEL,
			icon:Ext.MessageBox.WARNING,
			fn:function(btn){
				if(btn=="ok"){
					Ext.Ajax.request({
			    		url:"checkSessionVo.do",
			    		success:function(action){
			    			
			    		},
			    		failure:function(action){
			    		},
			    		params:{
			    			action:"logout"
			    		}
			    	});
			    	window.location.href="index.jsp";
				}
			}
		});
	}
    var menu = new Ext.menu.Menu({
        id: 'mainMenu',
        style: {
            overflow: 'visible'
        },
        items: [
            {
                text: '主题选择',
                iconCls:"icon-menu1",
                menu: {        
                    items: [
                        {
                            text: '天蓝色',
                            name: 'xtheme-blue',
                            id: 'xtheme-blue',
                            checked: true,
                            group: 'theme',
                            checkHandler: onItemCheck
                        }, {
                            text: '金橘橙',
                            name: 'ext-all-css01',
                            id: 'ext-all-css01',
                            checked: false,
                            group: 'theme',
                            checkHandler: onItemCheck
                        }, {
                            text: '淡紫色',
                            name: 'ext-all-css02',
                            id: 'ext-all-css02',
                            checked: false,
                            group: 'theme',
                            checkHandler: onItemCheck
                        }, {
                            text: '淡粉红',
                            name: 'ext-all-css03',
                            id: 'ext-all-css03',
                            checked: false,
                            group: 'theme',
                            checkHandler: onItemCheck
                        }, {
                            text: '全白色',
                            name: 'ext-all-css04',
                            id: 'ext-all-css04',
                            checked: false,
                            group: 'theme',
                            checkHandler: onItemCheck
                        }, {
                            text: '荧光绿',
                            name: 'ext-all-css05',
                            id: 'ext-all-css05',
                            checked: false,
                            group: 'theme',
                            checkHandler: onItemCheck
                        }, {
                            text: '纯白色',
                            name: 'xtheme-galdaka',
                            id: 'xtheme-galdaka',
                            checked: false,
                            group: 'theme',
                            checkHandler: onItemCheck
                        }, {
                            text: '灰白色',
                            name: 'xtheme-gray',
                            id: 'xtheme-gray',
                            checked: false,
                            group: 'theme',
                            checkHandler: onItemCheck
                        }, {
                            text: '青草绿',
                            name: 'xtheme-green',
                            id: 'xtheme-green',
                            checked: false,
                            group: 'theme',
                            checkHandler: onItemCheck
                        }, {
                            text: '深绿色',
                            name: 'xtheme-indigo',
                            id: 'xtheme-indigo',
                            checked: false,
                            group: 'theme',
                            checkHandler: onItemCheck
                        }, {
                            text: '深夜蓝',
                            name: 'xtheme-midnight',
                            id: 'xtheme-midnight',
                            checked: false,
                            group: 'theme',
                            checkHandler: onItemCheck
                        }, {
                            text: '粉红色',
                            name: 'xtheme-pink',
                            id: 'xtheme-pink',
                            checked: false,
                            group: 'theme',
                            checkHandler: onItemCheck
                        }, {
                            text: '水墨绿',
                            name: 'xtheme-slate',
                            id: 'xtheme-slate',
                            checked: false,
                            group: 'theme',
                            checkHandler: onItemCheck
                        }, {
                            text: '深黑色',
                            name: 'xtheme-slickness',
                            id: 'xtheme-slickness',
                            checked: false,
                            group: 'theme',
                            checkHandler: onItemCheck
                        }, {
                            text: '黑蓝色',
                            name: 'xtheme-access',
                            id: 'xtheme-access',
                            checked: false,
                            group: 'theme',
                            checkHandler: onItemCheck
                        }
                    ]
                }
            },{
                text: '日期查看',
                iconCls: 'icon-menu2',
                menu: new Ext.menu.DateMenu() // <-- submenu by reference
            },{
                text: '密码修改',
                iconCls: 'icon-menu3',
                listeners:{
                	click:modpwd
                }
            },"-",{
                text: '安全退出',
                iconCls: 'icon-menu4',
                listeners:{
                	click:logout
                }
            }
        ]
    });
	var cp = new Ext.state.CookieProvider({
		expires:new Date().clearTime().add(Date.DAY,30)
	});

	function onItemCheck(item, checked){
		Ext.util.CSS.swapStyleSheet("theme", "ext/resources/css/"+item.name+".css");
		cp.set("theme",item.name);
    }
	var readThemeFromCookie = function(){
        var themeValue = cp.get("theme");
        if(themeValue){
        	Ext.util.CSS.swapStyleSheet("theme", "ext/resources/css/"+themeValue+".css");
        	Ext.getCmp(themeValue).setChecked(true);
        }
    }();
    
    
    var scrollerMenu = new Ext.ux.TabScrollerMenu({
		maxText  : 10,
		pageSize : 5
	});

	var viewport = new Ext.Viewport({
		layout : "border",
		items : [{
			title : "人力资源管理系统, Verson 2.0!",
			region : "north",
			contentEl : "north-div",
			split : true,
			border : true,
			collapsible : true,
			height : 100,
			minSize : 50,
			maxSize : 100
		}, {
			title : "south",
			region : "south",
			id:"south-panel",
			contentEl : "south-div",
			split : true,
			border : true,
			collapsible : true,
			height : 80,
			minSize : 50,
			maxSize : 120
		}, {
			title : "您的信息",
			region : "east",
//			contentEl : "east-div",
			id:"east-panel",
			split : true,
			border : true,
			collapsible : true,
			layout:'fit',
			width: 200,
			maxSize : 200,
			items:
				new Ext.TabPanel({
                    border:false,
                    activeTab:0,
                    tabPosition:'bottom',
                    items:[
                    	propertyGrid_base,
	                    new Ext.grid.PropertyGrid({
	                        title: '角色信息',
	                        closable: false,
	                        source: roleMsg
	                    })
	                ]
                })

		}, {
			region : 'west',
			collapsible : true,
			title : '系统菜单',
			xtype : 'treepanel',
			width : 200,
			autoScroll : true,
			split : true,
			loader : new Ext.tree.TreeLoader(),
			root : new Ext.tree.AsyncTreeNode({
				expanded : true,
				loader : new Ext.tree.TreeLoader({
					/*dataUrl : "menu.menu",
					baseParams:{
						action:"main_menu",
						role_code:"0"
					}*/
					dataUrl : "treeInfoQuery.do?action=select",
					baseParams :{
						parent: "select new map(a.id.menuCode as id,b.menuName as text,b.menuName as qtip) from RoleMenuStair a ,MenuLevelStair b where a.id.roleCode='"+role_code+"' and a.id.menuCode=b.menuCode",
						child: "select new map(a.id.menuTreeCode as id,b.menuTreeName as text,b.menuTreeTips as qtip,a.id.menuCode as nodeId,b.menuPage as page) from RoleMenuBinary a,MenuLevelBinary b where a.id.menuTreeCode=b.menuTreeCode and a.id.roleCode='"+role_code+"'"
					}
				})
			}),
			tbar:[{
	            text:'功能菜单',
	            iconCls:"icon-params",
	            menu: menu
	        }],
			rootVisible : false,
			listeners : {
				click : function(n) {
					if (n.attributes.leaf
							&& viewport.getComponent(4)
									.findById(n.attributes.id) == null) {
						addTabPanel(n);
					} else if (viewport.getComponent(4)
							.findById(n.attributes.id) != null) {
						viewport.getComponent(4).activate(n.attributes.id);
					}
				}
			}
		}, {
			region : 'center',
			xtype : 'tabpanel',
			activeTab : 0,
			autoScroll : false,
			enableTabScroll:true,
			plugins : [ scrollerMenu,new Ext.ux.TabCloseMenu() ],
			items : [{
				title : '欢迎页面',
				contentEl : 'center-div',
				id : 'welcome'
			}]
		}]
	});
	Ext.getCmp("east-panel").collapse();
	Ext.getCmp("south-panel").collapse();
	
	var addTabPanel = function(n) {
		viewport.getComponent(4).add({
			id : n.attributes.id,
			title : n.attributes.text,
			autoLoad : {
				url : n.attributes.page,
				scripts : true
			},
			closable : true
		});
		viewport.getComponent(4).activate(n.attributes.id);
	}
	Ext.get("logout").on("click",logout);
	Ext.get("modpwd").on("click",modpwd);
	var task = {
	    run: function(){
	    	Ext.Ajax.request({
	    		url:"checkSessionVo.do",
	    		success:function(action){
	    			if(action.responseText==""){
	    				Ext.MessageBox.show({
	    					title:"错误提示",
	    					msg:"Session已经过期,请重新登陆!!",
	    					buttons:Ext.MessageBox.OK,
	    					icon:Ext.MessageBox.ERROR,
	    					fn:function(btn){
	    						window.location.href="index.jsp";
	    					}
	    				});
	    				runner.stop(task);
	    			}
	    		},
	    		failure:function(action){
	    			Ext.MessageBox.show({
    					title:"错误提示",
    					msg:"Session已经过期,请重新登陆!!",
    					buttons:Ext.MessageBox.OK,
    					icon:Ext.MessageBox.ERROR,
    					fn:function(btn){
    						window.location.href="index.jsp";
    					}
    				});
    				runner.stop(task);
	    		},
	    		params:{
	    			action:"check"
	    		}
	    	});
	    },
	    interval: 30000 //1 second
	}
	var runner = new Ext.util.TaskRunner();
	runner.start(task);
	
	var expander = new Ext.ux.grid.RowExpander({
        tpl : new Ext.Template(
            '<p><b>标题:</b> {name}</p><br>',
            '<p><b>内容:</b> {text}</p>'
        )
    });

    
    //============================================
    var store = new Ext.data.ArrayStore({
        proxy   : new Ext.data.MemoryProxy(),
        fields  : ["id","name","picture"],
        sortInfo: {
            field    : 'id',
            direction: 'ASC'
        }
    });  
    
    store.loadData([
        [1,"日销售图","chart-reload.JPG"],
        [2,"月销售图","charts.JPG"],
        [3,"销售库存数量图","chart-stacked.JPG"],
        [4,"库存数据校验","chart-pie.JPG"]
    ]);
/*    var store = new Ext.data.Store({
		url:'mainLoadVo.do?action=main3',
		reader:new Ext.data.JsonReader({
			root:'root',
			fields:["id","name","picture"]
		})
    });*/
    store.load();
    var dataview = new Ext.DataView({
        store: store,
        height:(Ext.getBody().getHeight()-170)*3/7,
        tpl  : new Ext.XTemplate(
            '<ul>',
                '<tpl for=".">',
                    '<li class="phone" id="abc">',
                        '<img width="180" height="180" src="/'+projectName+'/images/{picture}" />',
                        '<strong>{name}</strong>',
                    '</li>',
                '</tpl>',
            '</ul>'
        ),
        
        plugins : [
            new Ext.ux.DataViewTransition({
                duration  : 550,
                idProperty: 'id'
            })
        ],
        id: 'phones',
        
        itemSelector: 'li.phone',
        overClass   : 'phone-hover',
        singleSelect: true,
        multiSelect : true,
        autoScroll  : true
    });
	//========================
   
    
    var mainTree1_1 = new Ext.ux.tree.TreeGrid({
        width: (Ext.getBody().getWidth()-310)/2,
        enableDD: true,

        columns:[{
            header: '文件名',
            dataIndex: 'task',
            width: 230,
            renderer:function(){
            	return "12233";
            }
        },{
            header: '版本',
            width: 100,
            dataIndex: 'duration',
            align: 'center',
            sortType: 'asFloat'
        },{ 
            header: '上传时间',
            width: 100,
            dataIndex: 'user'
        }],
        dataUrl: 'mainLoadVo.do?action=main1_1'
    });

	var mainStore1 = new Ext.data.Store({
		autoLoad:true,
		url:'mainLoadVo.do?action=main1',
		reader:new Ext.data.JsonReader({
			root:'root',
			fields:["id","name","verson","page"]
		})
	});
	var welcomePanel = new Ext.Panel({
		closable:false,
		renderTo:"center-div-page",
		layout:"column",
		layoutConfig:{columns:2},
		width:Ext.getBody().getWidth()-240,
		defaults:{frame:true},//渲染面板
		items:[{
			columnWidth:.5,
			title:"最新下载",
			header:true,//是否显示title
			hideBorders:true,
			collapsible:true,//允许展开或者收缩
			bodyStyle:"background-color:#FFFFFF;",
			shadow:true,//是否显示阴影
			floating:false,//是否浮动显示
			frame:true,//是否显示圆角
			autoScroll:true,
			style:"padding:10px 10px 10px 10px",
			height:(Ext.getBody().getHeight()-170)/2,
//			items : mainTree1_1
			items:new Ext.grid.GridPanel({
				store:mainStore1,
				columns:[
					new Ext.grid.RowNumberer(),
					{
						header:'id',
						dataIndex:'id',
						hidden:true
					},{
						header:'文件名称',
						width:(Ext.getBody().getWidth()-240)/4,
						dataIndex:'name'
					},{
						header:'版本',
						dataIndex:'verson'
					},{
						header:'点击下载',
						dataIndex:'page',
						renderer:function(a,b){
							return "<b><a href='"+a+"'>下载</a></b>";
						}
					}
				],
				sm:new Ext.grid.RowSelectionModel({
					singleSelect:true
				}),
				autoHeight:true,
				autoWidth:true,
				frame:false,
				hideHeaders:false,//是否隐藏表头
				loadMask:true
			})
			 
		},{
			autoScroll:true,
			columnWidth:.5,
			title:'最新公告',
			style:"padding:10px 10px 10px 0px",
			bodyStyle:'background-color:#FFFFFF;',
			height:(Ext.getBody().getHeight()-170)/2,
			hideBorders:true,
			collapsible:true,//允许展开或者收缩
			shadow:true,//是否显示阴影
			floating:false,//是否浮动显示
			frame:true,//是否显示圆角
			items:new Ext.grid.GridPanel({
				store: new Ext.data.Store({
					autoLoad:true,
					url:'mainLoadVo.do?action=main2',
					reader:new Ext.data.JsonReader({
						root:'root',
						fields:["id","name","text","time"]
					})
				}),
				columns:[
					expander,
					{
						header:'id',
						dataIndex:"id",
						hidden:true
					},{
						header:"标题",
						dataIndex:"name",
						renderer:function(a){
							if(a.length>5){
								return a.substring(0,5)+'...';
							}
							return a;
						}
					},{
						header:"内容",  
						width:(Ext.getBody().getWidth()-240)/4,
						dataIndex:'text',
						renderer:function(a){
							if(a.length>20){
								return a.substring(0,20)+'......';
							}
							return a;
						}
					},{
						header:"更新时间",
						width:(Ext.getBody().getWidth()-240)/5,
						dataIndex:'time'
					}
				],
				sm:new Ext.grid.RowSelectionModel({
					singleSelect:true
				}),
				animCollapse: false,
				plugins: expander,
				autoHeight:true,
				autoWidth:true,
				frame:false,
				/*viewConfig:{
					forceFit:true
				},*/
				hideHeaders:false,//是否隐藏表头
				loadMask:true
			})
		},{
			columnWidth:1,
			autoScroll:true,
			style:'padding:0px 10px 10px 10px',
			title:"最新销售例图",
			height:(Ext.getBody().getHeight()-170)/2,
			items:dataview
			/*items:new Ext.grid.GridPanel({
				store:new Ext.data.Store({
//					autoLoad:true,
//					url:'',
					reader:new Ext.data.JsonReader({
						root:'data',
						id:'xxid',
						fields:[
							"xxid",
							"xxbiaot",
							"xxneirong"
						]
					})
				}),
				columns:[
					new Ext.grid.RowNumberer(),
					{
						header:'id',
						dataIndex:"xxid",
						hidden:true
					},{
						header:"标题",
						width:"100%",
						dataIndex:"xxbiaoti"
					},{
						header:"内容",
						hidden:true,
						dataIndex:'xxneirong'
					}
				],
				sm:new Ext.grid.RowSelectionModel({
					singleSelect:true
				}),
				autoHeight:true,
				autoWidth:true,
				viewConfig:{
					forceFit:true
				},
				frame:false,
				hideHeaders:false,//是否隐藏表头
				loadMask:true
			})*/
		}]
	});
	var yestoday = new Date().add(Date.DAY,-1).format("Y-m-d");
	function generateData(){
	    var data = [];
	    for(var i = 0; i < 12; ++i){
	        data.push([Date.monthNames[i], (Math.floor(Math.random() *  11) + 1) * 100]);
	    }
	    return data;
	}

	dataview.on("click",function(a,b,c,d){
		var panel ;
    	if(b==0){
    		panel = daystat();
    	}else if(b==1){
    		panel = monthstat();
    	}else if(b==2){
    		panel = storestat();
    	}else if(b==3){
    		panel = datastat();
    	}
    	var win = new Ext.Window({
	        layout      : 'fit',
	        title		: "图形展示",
	        modal		: true,
	        closeAction : 'hide',
	        loadMask	: true,
	        allowDomMove: true,
	        bodyBorder 	: false,
	        plain       : true,
	        items       : [panel]
	    });
		win.show();
    });
    
    function daystat(){
    	var storestat = new Ext.data.Store({
			url:'mainLoadVo.do?action=main4',
			autoLoad:true,
			reader:new Ext.data.JsonReader({
				root:'root',
				fields:["goodsname","sellnumber"]
			}),
			baseParams:{
				date:yestoday
			}
	    });
    	/*var storestat = new Ext.data.ArrayStore({
	        fields: ['month', 'hits'],
	        data: generateData()
	    });*/
	    return new Ext.Panel({
	        height: Ext.getBody().getHeight()-200,
	        width: Ext.getBody().getWidth()-200,
	        tbar:[
	        	"日销售数量表: ",
	        	new Ext.form.DateField({
	        		id:"main_day_sell",
					emptyText:"默认昨天"
				}),{
					text:"查询",
					iconCls:"icon-grid",
					handler:function(){
						var currentDate = "";
						if(Ext.getCmp("main_day_sell").getValue()!=""){
							currentDate = Ext.getCmp("main_day_sell").getValue().format("Y-m-d");
						}else{
							currentDate = yestoday;
						}
						storestat.baseParams.date = currentDate;
						storestat.load();
					}
				}
	        ],
	        items: {
	            xtype: 'columnchart',
	            store: storestat,
	            yField: 'sellnumber',
//		    	url: '/'+projectName+'/ext/resources/charts.swf',
	            xField: 'goodsname',
	            xAxis: new Ext.chart.CategoryAxis({
	                title: '销售货物'
	            }),
	            yAxis: new Ext.chart.NumericAxis({
	                title: '销售数量(个)'
	            }),
	            extraStyle: {
	               xAxis: {
	                    labelRotation: -90
	                }
	            }
	        }
	    });

    }
    var comboBoxStore = comboBoxList.comboBoxSql("select distinct a.id.userlist,a.id.userlist from OutUserList a where a.id.tag='1'","货物人","userlist");
	comboBoxStore.allowBlank = false;
	comboBoxStore.emptyText = "请选择仓库进行统计";
	comboBoxStore.getStore().on("load",function(a,json){
		if(json.length>0){
			var value = json[0].data.value;
			comboBoxStore.setValue(value);
		}
	});
	var comboBoxType = comboBoxList.comboBoxSql("select distinct a.id,a.typename||'['||a.storelist||']' from StoreType a ","种类","typeid");
	comboBoxType.allowBlank = false;
	comboBoxType.emptyText = "请选择种类进行统计";
	comboBoxType.getStore().on("load",function(a,json){
		if(json.length>0){
			var value = json[0].data.value;
			comboBoxType.setValue(value);
		}
		myMask.hide();
	});
	var comboBoxYn = comboBoxList.comboBoxSql("select paramscode,case paramscode when '0' then '异常' else '正常' end from ParamsList where typeid='YNTYPE'","货物人","yntype");
    comboBoxYn.allowBlank = true;
	comboBoxYn.emptyText = "默认为全部状态";
    function monthstat(){
    	/*var storestat = new Ext.data.JsonStore({
	        fields:['goodsname', 'sellnumber', 'lastnumber'],
	        data: [
	            {goodsname:'Jul 07', lastnumber: 245000, sellnumber: 3000000},
	            {goodsname:'Aug 07', lastnumber: 240000, sellnumber: 3500000},
	            {goodsname:'Sep 07', lastnumber: 355000, sellnumber: 4000000},
	            {goodsname:'Oct 07', lastnumber: 375000, sellnumber: 4200000},
	            {goodsname:'Nov 07', lastnumber: 490000, sellnumber: 4500000},
	            {goodsname:'Dec 07', lastnumber: 495000, sellnumber: 5800000},
	            {goodsname:'Jan 08', lastnumber: 520000, sellnumber: 6000000},
	            {goodsname:'Jan 081', lastnumber: 520000, sellnumber: 6000000},
	            {goodsname:'Jan 082', lastnumber: 520000, sellnumber: 6000000},
	            {goodsname:'Jan 083', lastnumber: 520000, sellnumber: 6000000},
	            {goodsname:'Jan 084', lastnumber: 520000, sellnumber: 6000000},
	            {goodsname:'Jan 085', lastnumber: 520000, sellnumber: 6000000},
	            {goodsname:'Jan 086', lastnumber: 520000, sellnumber: 6000000},
	            {goodsname:'Jan 087', lastnumber: 520000, sellnumber: 6000000},
	            {goodsname:'Jan 088', lastnumber: 520000, sellnumber: 6000000},
	            {goodsname:'Jan 089', lastnumber: 520000, sellnumber: 6000000},
	            {goodsname:'Jan 090', lastnumber: 520000, sellnumber: 6000000},
	            {goodsname:'Jan 091', lastnumber: 520000, sellnumber: 6000000},
	            {goodsname:'Jan 092', lastnumber: 520000, sellnumber: 6000000},
	            {goodsname:'Jan 093', lastnumber: 520000, sellnumber: 6000000},
	            {goodsname:'Jan 094', lastnumber: 520000, sellnumber: 6000000},
	            {goodsname:'Jan 095', lastnumber: 520000, sellnumber: 6000000},
	            {goodsname:'Jan 001', lastnumber: 520000, sellnumber: 6000000},
	            {goodsname:'Jan 002', lastnumber: 520000, sellnumber: 6000000},
	            {goodsname:'Jan 003', lastnumber: 520000, sellnumber: 6000000},
	            {goodsname:'Jan 004', lastnumber: 520000, sellnumber: 6000000},
	            {goodsname:'Jan 006', lastnumber: 520000, sellnumber: 6000000},
	            {goodsname:'Jan 007', lastnumber: 520000, sellnumber: 6000000},
	            {goodsname:'Jan 008', lastnumber: 520000, sellnumber: 6000000},
	            {goodsname:'Jan 009', lastnumber: 520000, sellnumber: 6000000},
	            {goodsname:'Jan 010', lastnumber: 520000, sellnumber: 6000000},
	            {goodsname:'Jan 011', lastnumber: 520000, sellnumber: 6000000},
	            {goodsname:'Jan 012', lastnumber: 520000, sellnumber: 6000000},
	            {goodsname:'Jan 013', lastnumber: 520000, sellnumber: 6000000},
	            {goodsname:'Jan 014', lastnumber: 520000, sellnumber: 6000000},
	            {goodsname:'Jan 015', lastnumber: 520000, sellnumber: 6000000},
	            {goodsname:'Jan 016', lastnumber: 520000, sellnumber: 6000000},
	            {goodsname:'Jan 017', lastnumber: 520000, sellnumber: 6000000},
	            {goodsname:'Jan 018', lastnumber: 520000, sellnumber: 6000000},
	            {goodsname:'Jan 019', lastnumber: 520000, sellnumber: 6000000},
	            {goodsname:'Jan 020', lastnumber: 520000, sellnumber: 6000000},
	            {goodsname:'Jan 021', lastnumber: 520000, sellnumber: 6000000},
	            {goodsname:'Jan 022', lastnumber: 520000, sellnumber: 6000000},
	            {goodsname:'Jan 023', lastnumber: 520000, sellnumber: 6000000},
	            {goodsname:'Jan 024', lastnumber: 520000, sellnumber: 6000000},
	            {goodsname:'Jan 025', lastnumber: 520000, sellnumber: 6000000},
	            {goodsname:'Jan 026', lastnumber: 520000, sellnumber: 6000000},
	            {goodsname:'Jan 027', lastnumber: 520000, sellnumber: 6000000},
	            {goodsname:'Jan 028', lastnumber: 520000, sellnumber: 6000000},
	            {goodsname:'Jan 029', lastnumber: 520000, sellnumber: 6000000},
	            {goodsname:'Jan 030', lastnumber: 520000, sellnumber: 6000000},
	            {goodsname:'Feb 096', lastnumber: 620000, sellnumber: 7500000}
	        ]
	    });*/
    	
    	var storestat = new Ext.data.Store({
			url:'mainLoadVo.do?action=main5',
			autoLoad:true,
			reader:new Ext.data.JsonReader({
				root:'root',
				fields:["goodsname","sellnumber","lastnumber"]
			}),
			baseParams:{
				userlist:comboBoxStore.getValue(),
				typeid:comboBoxType.getValue(),
				date:new Date().format("Y-m-d")
			}
	    });
	    
	    return  new Ext.Panel({
	        iconCls:'chart',
	        frame:true,
	        height: Ext.getBody().getHeight()-200,
	        width: Ext.getBody().getWidth()-200,
	        layout:'fit',
			tbar:[
	        	"月销售数量表: ",
	        	new Ext.form.DateField({
	        		id:"main_month_sell",
					emptyText:"默认当月"
				}),
				"仓库编号:",
				comboBoxStore,
				"货物种类:",
				comboBoxType,{
					text:"查询",
					iconCls:"icon-grid",
					handler:function(){
						var currentDate = "";
						if(Ext.getCmp("main_month_sell").getValue()!=""){
							currentDate = Ext.getCmp("main_month_sell").getValue().format("Y-m-d");
						}else{
							currentDate = new Date().format("Y-m-d");
						}
						storestat.baseParams.date = currentDate;
						storestat.baseParams.userlist = comboBoxStore.getValue();
						storestat.baseParams.typeid = comboBoxType.getValue();
						storestat.load();
					}
				}
	        ],
	        items: {
	            xtype: 'columnchart',
	            store: storestat,
	            xField: 'goodsname',
	            yAxis: new Ext.chart.NumericAxis({
	                displayName: 'Visits',
	                labelRenderer : Ext.util.Format.numberRenderer('0,0')
	            }),
	            tipRenderer : function(chart, record, index, series){
	                if(series.yField == 'sellnumber'){
	                    return record.data.goodsname+" 销售数量"+Ext.util.Format.number(record.data.sellnumber, '0,0');
	                }else{
	                    return record.data.goodsname+" 库存量"+Ext.util.Format.number(record.data.lastnumber, '0,0');
	                }
	            },
	            chartStyle: {
	                padding: 10,
	                animationEnabled: true,
	                font: {
	                    name: 'Tahoma',
	                    color: 0x444444,
	                    size: 11
	                },
	                dataTip: {
	                    padding: 5,
	                    border: {
	                        color: 0x99bbe8,
	                        size:1
	                    },
	                    background: {
	                        color: 0xDAE7F6,
	                        alpha: .9
	                    },
	                    font: {
	                        name: 'Tahoma',
	                        color: 0x15428B,
	                        size: 10,
	                        bold: true
	                    }
	                },
	                xAxis: {
	                    color: 0x69aBc8,
	                    majorTicks: {color: 0x69aBc8, length: 4},
	                    minorTicks: {color: 0x69aBc8, length: 2},
	                    majorGridLines: {size: 1, color: 0xeeeeee}
	                },
	                yAxis: {
	                    color: 0x69aBc8,
	                    majorTicks: {color: 0x69aBc8, length: 4},
	                    minorTicks: {color: 0x69aBc8, length: 2},
	                    majorGridLines: {size: 1, color: 0xdfe8f6}
	                }
	            },
	            series: [{
	                type: 'column',
	                displayName: '销售数量',
	                yField: 'sellnumber',
	                style: {
	                    image:'bar.gif',
	                    mode: 'stretch',
	                    color:0x99BBE8
	                }
	            },{
	                type:'line',
	                displayName: '仓库剩余数量',
	                yField: 'lastnumber',
	                style: {
	                    color: 0x15428B
	                }
	            }]
	        }
	    });


    }
    function storestat(){
    	var storestat = new Ext.data.Store({
			url:'mainLoadVo.do?action=main6',
			autoLoad:true,
			reader:new Ext.data.JsonReader({
				root:'root',
				fields:["goodsname","innumber","outnumber","lastnumber"]
			}),
			baseParams:{
				userlist:comboBoxStore.getValue(),
				typeid:comboBoxType.getValue()
			}
	    });
    	/*var store = new Ext.data.JsonStore({
	        fields: ['year', 'comedy', 'action', 'drama', 'thriller'],
	        data: [
	                {year: 2005, comedy: 34000000, action: 23890000, drama: 18450000, thriller: 20060000},
	                {year: 2006, comedy: 56703000, action: 38900000, drama: 12650000, thriller: 21000000},
	                {year: 2007, comedy: 42100000, action: 50410000, drama: 25780000, thriller: 23040000},
	                {year: 2008, comedy: 38910000, action: 56070000, drama: 24810000, thriller: 26940000}
	              ]
	    });*/

	    
	    return new Ext.Panel({
	        height: Ext.getBody().getHeight()-200,
	        width: Ext.getBody().getWidth()-200,
	        tbar:[
	        	"仓库库存量","-",
				"仓库编号:",
				comboBoxStore,
				"货物种类:",
				comboBoxType,{
					text:"查询",
					iconCls:"icon-grid",
					handler:function(){
						storestat.baseParams.userlist = comboBoxStore.getValue();
						storestat.baseParams.typeid = comboBoxType.getValue();
						storestat.load();
					}
				}
	        ],
	        items: {
	            xtype: 'stackedbarchart',
	            store: storestat,
	            yField: 'goodsname',
	            xAxis: new Ext.chart.NumericAxis({
	                stackingEnabled: true
	            }),
	            series: [{
	                xField: 'innumber',
	                displayName: '总库存'
	            },{
	                xField: 'outnumber',
	                displayName: '销售数量'
	            },{
	                xField: 'lastnumber',
	                displayName: '剩余数量'
	            }]
	        }
	    });

    }
    var expander1 = new Ext.ux.grid.RowExpander({
        tpl : new Ext.Template(
            '<p><b>货物名称:</b> {goodsname}</p><br>',
            '<p><b>总数量差异:</b> <font color="red">{storenumber}</font></p><br>',
            '<p><b>销售总数量差异:</b> <font color="red">{outnumber}</font></p><br>',
            '<p><b>剩余库存差异:</b> <font color="red">{lastnumber}</font></p><br>'
        )
    });
    function datastat(){
    	var storestat = new Ext.data.Store({
    		autoLoad:true,
			url:'mainLoadVo.do',
			reader:new Ext.data.JsonReader({
				totalProperty:"totalProperty",
				root:'root',
				fields:["goodsid","goodsname","userlist","sstorenumber","soutnumber","slastnumber","gstorenumber","goutnumber","glastnumber","storenumber","outnumber","lastnumber","equals"]
			}),
			baseParams:{
				action:"hql",
				type:"map",
				start:0,   
		  		limit:10,//
				sql:"select new map(a.id.goodsid as goodsid,b.goodsname as goodsname,a.id.userlist as userlist,a.sstorenumber as sstorenumber," +
						"a.soutnumber as soutnumber,a.sreturnnumber as sreturnnumber,a.slastnumber as slastnumber," +
						"a.gstorenumber as gstorenumber,a.goutnumber as goutnumber,a.greturnnumber as greturnnumber," +
						"a.glastnumber as glastnumber,a.equals as equals,(a.sstorenumber-a.gstorenumber) as storenumber," +
						"(a.soutnumber-a.goutnumber) as outnumber,(a.slastnumber-a.glastnumber) as lastnumber) " +
						"from StoreGoodsClear a,StoreList b where a.id.goodsid=b.id and a.id.cleardate='"+new Date().format("Y-m-d")+"'"
			}
		});
		var store_exception = new Ext.data.JsonStore({
	        fields: ['name', 'total'],
	        data: []
	    });
	    var store_number = new Ext.data.JsonStore({
	        fields: ['name', 'total'],
	        data: []
	    });
		storestat.on("load",function(){
			var root = storestat.reader.jsonData.root;
			var storenum = new Array();
			//正常数量
			var normal = 0;
			//异常数量
			var excep = 0;
			
			for(var i=0;i<root.length;i++){
				if(root[i].equals=="1"){
					normal++;
					storenum.push({
						name:root[i].goodsname,
						total:root[i].glastnumber
					})
				}else if(root[i].equals=="0"){
					excep++;
				}
				
			};
			/**
			 * [{"name":"铅笔","total":"9"},{"name":"钢笔","total":"9"},{"name":"毛笔","total":"9"},{"name":"报告纸","total":"9"},{"name":"笔记本","total":"0"}]
			   [{"name":"铅笔","total":"9"},{"name":"钢笔","total":"9"},{"name":"毛笔","total":"9"},{"name":"报告纸","total":"9"},{"name":"笔记本","total":"0"},{"name":"铅笔","total":"9"},{"name":"钢笔","total":"9"},{"name":"毛笔","total":"9"},{"name":"报告纸","total":"9"},{"name":"笔记本","total":"0"}]
			 */
			var exception = [{
				name:"正常",
				total:normal
			},{
				name:"异常",
				total:excep
			}];
		    store_exception.loadData(exception,false);
		    store_number.loadData(storenum,false);
		});
	    
	    return new Ext.Panel({
	    	autoScroll:true,
	        height: Ext.getBody().getHeight()-205,
	        width: 1000,
	        layout:"column",
			layoutConfig:{columns:2},
			defaults:{frame:true},//渲染面板
	        items: [{
	        		columnWidth:1,
					header:true,//是否显示title
					hideBorders:true,
					collapsible:true,//允许展开或者收缩
					bodyStyle:"background-color:#FFFFFF;",
					title:"查看库存数据是否出现异常",
					shadow:true,//是否显示阴影
					floating:false,//是否浮动显示
					frame:true,//是否显示圆角
					autoScroll:true,
					style:"padding:5px 5px 5px 5px",
					tbar:[
						"异常日期: ",
			        	new Ext.form.DateField({
			        		id:"main_data_stat",
							emptyText:"默认当天"
						}),"状态: ",
						comboBoxYn,{
							text:"查询",
							iconCls:"icon-grid",
							handler:function(){
								var where = "";
								var currentDate = "";
								if(Ext.getCmp("main_data_stat").getValue()!=""){
									currentDate = Ext.getCmp("main_data_stat").getValue().format("Y-m-d");
								}else{
									currentDate = new Date().format("Y-m-d");
								}
								if(comboBoxYn.getValue()!=""){
									where = " and a.equals='" + comboBoxYn.getValue()+"'";
								}
								storestat.baseParams.sql = "select new map(a.id.goodsid as goodsid,b.goodsname as goodsname,a.id.userlist as userlist,a.sstorenumber as sstorenumber," +
									"a.soutnumber as soutnumber,a.sreturnnumber as sreturnnumber,a.slastnumber as slastnumber," +
									"a.gstorenumber as gstorenumber,a.goutnumber as goutnumber,a.greturnnumber as greturnnumber," +
									"a.glastnumber as glastnumber,a.equals as equals,(a.sstorenumber-a.gstorenumber) as storenumber," +
									"(a.soutnumber-a.goutnumber) as outnumber,(a.slastnumber-a.glastnumber) as lastnumber) " +
									"from StoreGoodsClear a,StoreList b where a.id.goodsid=b.id and a.id.cleardate='"+currentDate+"' "+where;
								storestat.load();
								var root = storestat.reader.jsonData.root;
								for(var i=0;i<root.length;i++){
//									alert(root[i].equals);
								}
							}
						}
			        ],
					items:new Ext.grid.GridPanel({
			        	height: Ext.getBody().getHeight()-400,
						store: storestat,
						columns:[
							expander1,
							{
								dataIndex:"goodsid",
								header:"产品ID",
								hidden:true
							},{
								header:"货物名称",
								dataIndex:"goodsname"
							},{
								header:"仓库编号",
								dataIndex:"userlist",
								width:150
							},{
								header:"仓库表总数量",
								dataIndex:"sstorenumber"
							},{
								header:"货物表总数量",
								dataIndex:"gstorenumber"
							},{
								header:"仓库表销售总数量",
								dataIndex:"soutnumber"
							},{
								header:"货物表销售总数量",
								dataIndex:"goutnumber"
							},{
								header:"仓库表剩余库存",
								dataIndex:"slastnumber"
							},{
								header:"货物表剩余库存",
								dataIndex:"glastnumber"
							},{
								header:"是否异常",
								dataIndex:"equals",
								renderer:function(a){
									if(a=="1"){
										return "正常";
									}else{
										return "<font color='red'>异常</font>";
									}
								}
							}
						],
						sm:new Ext.grid.RowSelectionModel({
							singleSelect:true
						}),
						animCollapse: false,
						plugins: expander1,
						autoHeight:true,
						autoWidth:true,
						frame:false,
						/*viewConfig:{
							forceFit:true
						},*/
						hideHeaders:false,//是否隐藏表头
						loadMask:true,
						bbar: new Ext.PagingToolbar({
				            pageSize: 10,
				            store: storestat,
				            displayInfo: true,
				            plugins: new Ext.ux.ProgressBarPager()
				        })
					})
	        	},{
	        		columnWidth:.5,
					header:true,//是否显示title
					hideBorders:true,
					title:"正常异常分布比例",
					collapsible:true,//允许展开或者收缩
					bodyStyle:"background-color:#FFFFFF;",
					shadow:true,//是否显示阴影
					floating:false,//是否浮动显示
					frame:true,//是否显示圆角
					autoScroll:true,
					style:"padding:5px 5px 5px 5px",
					items:[
						new Ext.Panel({
					        width: 450,
					        height: 250,
					        items: {
					            store: store_exception,
					            xtype: 'piechart',
					            dataField: 'total',
					            categoryField: 'name',
					            //extra styles get applied to the chart defaults
					            extraStyle:
					            {
					                legend:
					                {
					                    display: 'bottom',
					                    padding: 5,
					                    font:
					                    {
					                        family: 'Tahoma',
					                        size: 13
					                    }
					                }
					            }
					        }
					    })
					]
	        	},{
	        		columnWidth:.5,
					header:true,//是否显示title
					hideBorders:true,
					collapsible:true,//允许展开或者收缩
					title:"货物剩余数量分布比例（不计异常部分）",
					bodyStyle:"background-color:#FFFFFF;",
					shadow:true,//是否显示阴影
					floating:false,//是否浮动显示
					frame:true,//是否显示圆角
					autoScroll:true,
					style:"padding:5px 5px 5px 5px",
					items:[new Ext.Panel({
				        width: 450,
				        height: 250,
				        items: {
				            store: store_number,
				            xtype: 'piechart',
				            dataField: 'total',
				            categoryField: 'name', 
				            //extra styles get applied to the chart defaults
				            extraStyle:
				            {
				                legend:
				                {
				                    display: 'bottom',
				                    padding: 5,
				                    font:
				                    {
				                        family: 'Tahoma',
				                        size: 13
				                    }
				                }
				            }
				        }
				    })]
	        	}
	        ]
	    });
    }
});