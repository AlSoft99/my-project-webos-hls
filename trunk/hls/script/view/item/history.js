enyo.kind({
	name:"enyo.History",
	kind:enyo.HFlexBox,
	style:"padding-bottom:5%;",
	components:[
		{kind:enyo.VFlexBox,components:[
			{kind:enyo.VFlexBox,className:"blue-box box-content",components:[
				{kind:enyo.Control,content:"120求救记录:",className:"history-content-title"},
				{kind:enyo.Scroller,flex:1,components:[
					{kind:enyo.Control,content:"内容"}
				]}
			],flex:1},
			{kind:enyo.VFlexBox,className:"blue-box box-content",components:[
 				{kind:enyo.Control,content:"就诊记录:",className:"history-content-title"},
				{kind:enyo.Scroller,flex:1,components:[
					{kind:enyo.Control,content:"内容"}
				]}
 			],flex:1},
 			{kind:enyo.VFlexBox,className:"blue-box box-content",components:[
 				{kind:enyo.Control,content:"体检记录:",className:"history-content-title"},
				{kind:enyo.Scroller,flex:1,components:[
					{kind:enyo.Control,content:"内容"}
				]}
 			],flex:1}
		],flex:3},
		{kind:enyo.VFlexBox,style:"padding-left:2%;",components:[
        	{kind:"enyo.PersonBlue",flex:1}
		],flex:1}
	]
});