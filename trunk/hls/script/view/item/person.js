enyo.kind({
	name:"enyo.PersonBlue",
	kind:enyo.VFlexBox,
	components:[
     	{kind:enyo.VFlexBox,className:"blue-box box-content",components:[
			{kind:enyo.Control,content:"姓名:",className:"history-content-title"},
			{kind:enyo.Scroller,flex:1,components:[
				{kind:enyo.Control,content:"内容"}
			]}
		],flex:1},
		{kind:enyo.VFlexBox,className:"blue-box box-content",components:[
			{kind:enyo.Control,content:"禁用药:",className:"history-content-title"},
			{kind:enyo.Scroller,flex:1,components:[
				{kind:enyo.Control,content:"内容"}
			]}
		],flex:1}
	]
});
enyo.kind({
	name:"enyo.PersonRed",
	kind:enyo.VFlexBox,
	components:[
     	{kind:enyo.VFlexBox,className:"red-box box-content",components:[
			{kind:enyo.Control,content:"姓名:",className:"history-content-title"},
			{kind:enyo.Scroller,flex:1,components:[
				{kind:enyo.Control,content:"内容"}
			]}
		],flex:1},
		{kind:enyo.VFlexBox,className:"red-box box-content",components:[
			{kind:enyo.Control,content:"禁用药:",className:"history-content-title"},
			{kind:enyo.Scroller,flex:1,components:[
				{kind:enyo.Control,content:"内容"}
			]}
		],flex:1}
	]
});