Ext.onReady(function() {

	Ext.QuickTips.init();
	/**
	 * 最新的福利参数Json对象
	 */
	var currentWalfareParams;
	/**
	 * 表格统计
	 */
	var summary = new Ext.ux.grid.GroupSummary();
	/**
	 * 表格构造
	 */
	var curentStartDate = (new Date()).clearTime().format("Y-m-d");
	var curentEndDate = (new Date()).clearTime().format("Y-m-d");
	var combo = comboBoxList
			.comboBoxSql(
					"select paramscode,paramsname from ParamsList where typeid='CONFIRM_STATUS'",
					"仓库状态", "store_status");
	combo.allowBlank = true;
	combo.setWidth(120);
	combo.emptyText = "默认为全状态统计";
	var where = "";
	var toolbar = new Ext.Toolbar({
		items : ["-", "开始:", new Ext.form.DateField({
			emptyText : "默认当天"
		}), "结束:", new Ext.form.DateField({
			emptyText : "默认当天"
		}), "确认状态:", combo, {
			text : "查询",
			iconCls : "icon-grid",
			tooltip : '默认为当天查询',
			handler : function(event, mouse) {
				if (toolbar.getComponent(2).getValue() != "") {
					curentStartDate = toolbar.getComponent(2).getValue()
							.format("Y-m-d");
				}
				if (toolbar.getComponent(4).getValue() != "") {
					curentEndDate = toolbar.getComponent(4).getValue()
							.format("Y-m-d");
				}

				var store_status = toolbar.getComponent(6).getValue();
				if (store_status != "") {
					where = " and a.confirmstatus='" + store_status + "'"
				} else {
					where = "";
				}
				listenerEvent.loadGrid();
			}
		}, "-"]
	});
	var store = new Ext.data.GroupingStore({
		proxy : new Ext.data.HttpProxy({
			url : "queryInfoVo.do"
		}),
		reader : new Ext.data.JsonReader({
			totalProperty : "totalProperty",
			root : "root"
		}, [{
			name : "id",
			type : 'string'
		}, {
			name : "applydate",
			type : "date",
			dateFormat : "Y-m-d H:i:s.u"
		}, {
			name : "applyusername",
			type : 'string'
		}, {
			name : "disprice",
			type : 'int'
		}, {
			name : "preprice",
			type : 'int'
		}, {
			name : "autprice",
			type : 'int'
		}, {
			name : "mangusername",
			type : 'string'
		}, {
			name : "accousername",
			type : 'string'
		}, {
			name : "storeusername",
			type : 'string'
		}, {
			name : "paramsname",
			type : 'string'
		}, {
			name : "updttime",
			type : "date",
			dateFormat : "Y-m-d H:i:s.u"
		}]),
		groupField: 'paramsname',
		sortInfo : {
			field : 'id',
			direction : 'DESC'
		}
	});

	var grid_info = new Ext.grid.GridPanel({
		store : store,
		autoWidth : true,
		region : 'center',
		margins : '0 5 5 5',
		autoExpandColumn : 'goodsname',
		plugins : [summary],
		view : new Ext.grid.GroupingView({
			markDirty : false

		}),
		view : new Ext.grid.GroupingView({
			forceFit : true,
			showGroupName : false,
			enableNoGroups : false,
			enableGroupingMenu : false,
			hideGroupedColumn : true
		}),

		tbar : toolbar,
		columns : [new Ext.grid.RowNumberer(), {
			id : 'id',
			header : '申购单号',
			width : 150,
			dataIndex : 'id',
			sortable : true,
			summaryType : 'count',
			summaryRenderer : function(v, params, data) {
				return ((v === 0 || v > 1) ? '(货物种类' + v + ' 名)' : '(货物种类1 个)');
			}
		}, {
			xtype : 'datecolumn',
			format : 'Y-m-d H:i:s.u',
			id : 'applydate',
			header : '申购日期',
			dataIndex : 'applydate',
			sortable : true,
			summaryType : 'count',
			summaryRenderer : function(v, params, data) {
				return ((v === 0 || v > 1) ? '(申购日期' + v + ' 个)' : '(申购日期1 个)');
			}
		}, {
			header : '申购状态',
			dataIndex : 'paramsname',
			summaryType : 'count',
			summaryRenderer : function(v, params, data) {
				return ((v === 0 || v > 1) ? '(申购人' + v + ' 个)' : '(申购人1 个)');
			},
			sortable : true
		}, {
			xtype : 'numbercolumn',
			header : '预计金额',
			dataIndex : 'preprice',
			summaryType : 'sum',
			sortable : true,
			format : "￥0,0.00"
		}, {
			xtype : 'numbercolumn',
			header : '实际金额',
			dataIndex : 'autprice',
			summaryType : 'sum',
			sortable : true,
			format : "￥0,0.00"
		}, {
//			xtype : 'numbercolumn',
			header : '差额(￥)',
			dataIndex : 'disprice',
			summaryType : 'sum',
			sortable : true,
			format : "0,0.00",
			renderer:function change(data){
				var format = Ext.util.Format;
				if(data<=0){
					data = format.number(data,"0,0.00");
					return "<font color='red'>￥"+data+"</font>";
				}else{
					data = format.number(data,"0,0.00");
					return "￥"+data;
				}
			}
		}, {
			header : '申报主管',
			dataIndex : 'mangusername',
			summaryType : 'count',
			summaryRenderer : function(v, params, data) {
				return ((v === 0 || v > 1) ? '(申报主管' + v + ' 个)' : '(申报主管1 个)');
			},
			sortable : true,
			renderer : function(data,obj,record) {
				if (data == "null" || record.json.confirmstatus=="1") {
					return "<font color='blue'>等待申报</font>";
				}
				return data;
			}
		}, {
			header : '申报财务',
			dataIndex : 'accousername',
			summaryType : 'count',
			summaryRenderer : function(v, params, data) {
				return ((v === 0 || v > 1) ? '(申报财务' + v + ' 个)' : '(申报财务1 个)');
			},
			sortable : true,
			renderer : function(data) {
				if (data == "null") {
					return "<font color='blue'>等待申报</font>";
				}
				return data;
			}
		}, {
			header : '申报仓库',
			dataIndex : 'storeusername',
			summaryType : 'count',
			summaryRenderer : function(v, params, data) {
				return ((v === 0 || v > 1) ? '(申报仓库' + v + ' 个)' : '(申报仓库1 个)');
			},
			sortable : true,
			renderer : function(data) {
				if (data == "null") {
					return "<font color='blue'>等待申报</font>";
				}
				return data;
			}
		}, {
			xtype : 'datecolumn',
			format : 'Y-m-d H:i:s.u',
			id : 'updttime',
			header : '最后更新日期',
			dataIndex : 'applydate',
			sortable : true,
			summaryType : 'count',
			summaryRenderer : function(v, params, data) {
				return ((v === 0 || v > 1)
						? '(最后更新日期' + v + ' 个)'
						: '(最后更新日期1 个)');
			}
		}

		],
		/**
		 * {name: "id", type: 'string'}, {name:
		 * "applydate",type:"date",dateFormat:"Y-m-d H:i:s.u"}, {name:
		 * "applyusername", type: 'string'}, {name: "preprice", type: 'int'},
		 * {name: "autprice", type: 'int'}, {name: "mangusername", type:
		 * 'string'}, {name: "accousername", type: 'string'}, {name:
		 * "storeusername", type: 'string'}, {name:
		 * "updttime",type:"date",dateFormat:"Y-m-d H:i:s.u"}
		 */
		bbar : new Ext.PagingToolbar({
			pageSize : 200,
			store : store,
			displayInfo : true,
			plugins : new Ext.ux.ProgressBarPager()
		})

	});

	var layout = new Ext.Panel({
		title : '申购单列表',
		layout : 'border',
		layoutConfig : {
			columns : 1
		},
		autoWidth : true,
		height : Ext.getBody().getHeight() - 148 - 20,
		items : [grid_info]
	});

	var listenerEvent = {
		loadGrid : function() {
			store.baseParams.sql = "select new map(a.id as id,a.preprice as preprice, a.autprice as autprice,a.preprice-a.autprice as disprice,a.applyuser as applyuser,a.applyuser as applyuser,a.applydate as applydate,a.manguser as manguser,a.confirmstatus as confirmstatus,a.ismang as ismang,a.accouser as accouser,a.isacco as isacco,a.storeuser as storeuser,a.isstore as isstore,b.userName as applyusername,c.userName as mangusername,(select userName from UserInfo where userId = a.accouser) as accousername,(select userName from UserInfo where userId = a.storeuser) as storeusername,d.paramsname as paramsname) from StoreInputInfo a,UserInfo b,UserInfo c,ParamsList d where d.paramscode=a.confirmstatus and d.typeid='CONFIRM_STATUS' and b.userId = a.applyuser and c.userId = a.manguser and (a.applyuser='"+Ext.getDom("user_id").value+"' or a.manguser='"+Ext.getDom("user_id").value+"' or a.accouser='"+Ext.getDom("user_id").value+"' or a.storeuser='"+Ext.getDom("user_id").value+"') and a.applydate>'{1}' and a.applydate<'{2}' "
					+ where + "";
			store.baseParams.startDate = curentStartDate;
			store.baseParams.endDate = curentEndDate;
			store.baseParams.action = "hql";
			store.baseParams.type = "mapdate";
			store.load({
				params : {
					start : -1,
					limit : -1
				}
			});
		}
	}
	listenerEvent.loadGrid();
	layout.render("person_stock_list_div");
	grid_info.getSelectionModel().on('selectionchange', function(sm) {
		// grid_info.removeBtn.setDisabled(sm.getCount() < 1);
	});
});