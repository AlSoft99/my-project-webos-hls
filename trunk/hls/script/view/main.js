enyo.kind({
	name:"enyo.Main",
	kind:"HFlexBox",
	style:"padding:10px;",
	components:[
   		{kind:"ViewItem"},
   		{kind:"VFlexBox",components:[
        		{kind:enyo.Control,content:"hp header",style:"text-align:right;"},
        		{kind: "Pane", transitionKind: "enyo.transitions.LeftRightFlyin", components: [
        			{kind: "mainView"},
        			{kind: "otherView"}
        		],flex:6},
        		{kind:"HFlexBox",style:"text-align:right;",flex:1,components:[
        			{kind:enyo.Control,content:"foot1",className:"frame-cell frame-select",flex:1},
        			{kind:enyo.Control,content:"foot2",className:"frame-cell frame-select",flex:1}
        		]}
  		],flex:3}
	]
});
