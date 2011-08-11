enyo.kind({
	name:"MainItem",
	kind:enyo.VFlexBox,
	flex:1,
	components:[
		{kind:"HFlexBox",style:"position:relative;",className:"frame-cell",flex:1,onclick:"buttonClick",components:[
			{kind:enyo.Control,style:"background:url(images/logo_white_small.png) no-repeat right;",flex:1},
			{kind:"VFlexBox",flex:1,style:"position:relative;",components:[
				{content:"history",align:"center",className:"frame-state"}
			]},
			{kind:enyo.Control,content:"",className:"frame-body frame-select"}
		]},
		{kind:"HFlexBox",id:"suggest",style:"position:relative;",className:"frame-cell",flex:1,onclick:"buttonClick",components:[
			{kind:enyo.Control,style:"background:url(images/logo_white_small.png) no-repeat right;",flex:1},
			{kind:"VFlexBox",flex:1,style:"position:relative;",components:[
				{content:"suggest",align:"center",className:"frame-state"}
			]},
			{kind:enyo.Control,content:"",className:"frame-body frame-select"}
		]},
		{kind:"HFlexBox",style:"position:relative;",className:"frame-cell",flex:1,onclick:"buttonClick",components:[
			{kind:enyo.Control,style:"background:url(images/logo_white_small.png) no-repeat right;",flex:1},
			{kind:"VFlexBox",flex:1,style:"position:relative;",components:[
				{content:"lift-state",align:"center",className:"frame-state"}
			]},
			{kind:enyo.Control,content:"",className:"frame-body frame-select"}
		]},
		{kind:"HFlexBox",style:"position:relative;",className:"frame-cell",flex:1,onclick:"buttonClick",components:[
			{kind:enyo.Control,style:"background:url(images/logo_white_small.png) no-repeat right;",flex:1},
			{kind:"VFlexBox",flex:1,style:"position:relative;",components:[
				{content:"save-record",align:"center",className:"frame-state"}
			]},
			{kind:enyo.Control,content:"",className:"frame-body frame-select"}
		]},
		{kind:enyo.Control,style:"margin-top:10px;",className:"frame-bottom frame-top"},
		{kind:"HFlexBox",style:"position:relative;",className:"frame-cell",flex:1,onclick:"buttonClick",components:[
			{kind:enyo.Control,style:"background:url(images/logo_white_small.png) no-repeat right;",flex:1},
			{kind:"VFlexBox",flex:1,style:"position:relative;",components:[
				{content:"call",align:"center",className:"frame-state"}
			]},
			{kind:enyo.Control,content:"",className:"frame-body frame-select"}
		]},
		{kind:"HFlexBox",style:"position:relative;",className:"frame-cell",flex:1,onclick:"buttonClick",components:[
			{kind:enyo.Control,style:"background:url(images/logo_white_small.png) no-repeat right;",flex:1},
			{kind:"VFlexBox",flex:1,style:"position:relative;",components:[
				{content:"camera",align:"center",className:"frame-state"}
			]},
			{kind:enyo.Control,content:"",className:"frame-body frame-select"}
		]},
		{kind:enyo.Control,style:"margin-top:10px;",className:"frame-bottom frame-top"},
		{kind:"HFlexBox",style:"position:relative;",className:"frame-cell",flex:1,onclick:"buttonClick",components:[
			{kind:enyo.Control,style:"background:url(images/logo_white_small.png) no-repeat right;",flex:1},
			{kind:"VFlexBox",flex:1,style:"position:relative;",components:[
				{content:"road",align:"center",className:"frame-state"}
			]},
			{kind:enyo.Control,content:"",className:"frame-body frame-select"}
		]}
	],
	buttonClick:function(){
		enyo.$.frameWork_main_pane.selectView(enyo.$.frameWork_main_otherView);
	}
});