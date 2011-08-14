enyo.kind({
	name:"MainItem",
	kind:enyo.VFlexBox,
	flex:1,
	components:[
		{kind:"HFlexBox",style:"position:relative;",className:"frame-cell",flex:1,onclick:"buttonClick",components:[
			{kind:enyo.Control,className:"frame-tab-on-1",flex:1},
			{kind:"VFlexBox",flex:.8,style:"position:relative;",components:[
				{content:"诊疗历史",align:"center",className:"frame-state"}
			]},
			{kind:enyo.Control,content:"",className:"frame-body frame-select"}
		]},
		{kind:"HFlexBox",id:"suggest",style:"position:relative;",className:"frame-cell",flex:1,onclick:"buttonClick",components:[
			{kind:enyo.Control,className:"frame-tab-off-2",flex:1},
			{kind:"VFlexBox",flex:.8,style:"position:relative;",components:[
				{content:"专家建议",align:"center",className:"frame-state"}
			]},
			{kind:enyo.Control,content:"",className:"frame-body"}
		]},
		{kind:"HFlexBox",style:"position:relative;",className:"frame-cell",flex:1,onclick:"buttonClick",components:[
			{kind:enyo.Control,className:"frame-tab-off-3",flex:1},
			{kind:"VFlexBox",flex:.8,style:"position:relative;",components:[
				{content:"生命体征",align:"center",className:"frame-state"}
			]},
			{kind:enyo.Control,content:"",className:"frame-body"}
		]},
		{kind:"HFlexBox",style:"position:relative;",className:"frame-cell",flex:1,onclick:"buttonClick",components:[
			{kind:enyo.Control,className:"frame-tab-off-4",flex:1},
			{kind:"VFlexBox",flex:.8,style:"position:relative;",components:[
				{content:"救护记录",align:"center",className:"frame-state"}
			]},
			{kind:enyo.Control,content:"",className:"frame-body"}
		]},
		{kind:enyo.Control,style:"margin-top:10px;",className:"frame-bottom frame-top"},
		{kind:"HFlexBox",style:"position:relative;",className:"frame-cell",flex:1,onclick:"buttonClick",components:[
			{kind:enyo.Control,className:"frame-tab-on-5",flex:1},
			{kind:"VFlexBox",flex:.8,style:"position:relative;",components:[
				{content:"拨号",align:"center",className:"frame-state"}
			]},
			{kind:enyo.Control,content:"",className:"frame-body frame-select"}
		]},
		{kind:"HFlexBox",style:"position:relative;",className:"frame-cell",flex:1,onclick:"buttonClick",components:[
			{kind:enyo.Control,className:"frame-tab-off-6",flex:1},
			{kind:"VFlexBox",flex:.8,style:"position:relative;",components:[
				{content:"摄像",align:"center",className:"frame-state"}
			]},
			{kind:enyo.Control,content:"",className:"frame-body"}
		]},
		{kind:enyo.Control,style:"margin-top:10px;",className:"frame-bottom frame-top"},
		{kind:"HFlexBox",style:"position:relative;",className:"frame-cell",flex:1,onclick:"buttonClick",components:[
			{kind:enyo.Control,className:"frame-tab-off-7",flex:1},
			{kind:"VFlexBox",flex:.8,style:"position:relative;",components:[
				{content:"路况检测",align:"center",className:"frame-state"}
			]},
			{kind:enyo.Control,content:"",className:"frame-body frame-select"}
		]}
	],
	buttonClick:function(o){
		var id = o.id;
		var index = $("#"+id).index();
		var className = $("#"+id).children(":eq(0)").attr("class");
		var number = className.substring(className.length-1);
		var start = className.substring(0,9);
		if(index<4){
			$(".frame-body:lt(4)").removeClass("frame-select");
			$("#"+id).children(":eq(0)").removeClass().addClass(main.changeOnClass(className));
			$("#"+id).children(".frame-body").removeClass("frame-select").addClass("frame-select");
		}else{
			if($("#"+id).children(".frame-body").toggleClass("frame-select"));
		}
		main.checkState();
		enyo.$.frameWork_main_pane.selectViewByIndex(eval(number-1));
	}
});