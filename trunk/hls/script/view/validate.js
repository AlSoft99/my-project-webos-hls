var progressbarIndex = 1;
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
       	    		{kind:enyo.Control,flex:2,style:"font-size:25px;font-weight:bold;",content:"XXX 医生您好："},
       	    		{kind:enyo.Control,flex:1,style:"font-weight:bold;",content:"系统正在接收信息..."},
       	    		{kind:enyo.ProgressButton,className:"check-progress-button",cancelable:false,animationPosition:true}
       	    	]},
       	    	{kind:enyo.Control,flex:1}
	    	]},
	    	{kind:enyo.Control,flex:1}
	    ]}
	]
});
function change(){
	var p = enyo.$.frameWork_validate_progressButton;
	progressbarIndex+=20;
	p.setPosition(progressbarIndex);
	
	if(progressbarIndex<=100){
		setTimeout(change,1000);
	}else{
		p.setPosition(100);
		enyo.$.frameWork.next();
	}
}