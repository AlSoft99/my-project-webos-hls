enyo.kind({
	name:"enyo.Validate",
	kind:enyo.VFlexBox,
	components:[
	    {kind:enyo.Control,content:"车载医疗救护系统",className:"header-title"},
	    {kind:enyo.VFlexBox,flex:1,components:[
	    	{kind:enyo.Control,flex:1},
	    	{kind:enyo.HFlexBox,flex:1.5,components:[
	    		{kind:enyo.Control,flex:1},
	    		{kind:enyo.VFlexBox,flex:2.5,components:[
       	    		{kind:enyo.Control,flex:1,content:"XXX 医生您好："},
       	    		{kind:enyo.Control,flex:1,content:"系统正在接收信息"},
       	    		{kind:enyo.ProgressButton,className:"check-progress-button",animationPosition:true}
       	    	]},
       	    	{kind:enyo.Control,flex:1}
	    	]},
	    	{kind:enyo.Control,flex:1}
	    ]},
		{kind:enyo.Button,content:"Validate",onclick:"goNext"}
	],
	goNext:function(){
		//enyo.$.frameWork.next()
		var p = enyo.$.frameWork_validate_progressButton;
		p.setPosition(50);
	}
});