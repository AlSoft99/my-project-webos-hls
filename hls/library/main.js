enyo.kind({
		name:"enyo.Main",
		kind:"HFlexBox",
		style:"padding:10px;",
		components:[
	   		{kind:"VFlexBox",components:[
	        		{kind:"HFlexBox",style:"position:relative;",className:"frame-cell",flex:1,onclick:"buttonClick",components:[
	        			{kind:enyo.Control,style:"background:url(images/logo_white_small.png) no-repeat right;",flex:1},
	        			{kind:"VFlexBox",flex:1,style:"position:relative;",components:[
	        				{content:"history",align:"center",className:"frame-state"}
	        			]},
	        			//{kind:enyo.Control,content:"",className:"frame-body frame-select"}
	        			{kind:"VFlexBox",className:"frame-body",components:{
	        				
	        			}}
	        		]},
	        		{kind:"HFlexBox",id:"suggest",style:"position:relative;",className:"frame-cell",flex:1,onclick:"buttonClick",components:[
	        			{kind:enyo.Control,style:"background:url(images/logo_white_small.png) no-repeat right;",flex:1},
	        			{kind:"VFlexBox",flex:1,style:"position:relative;",components:[
	        				{content:"suggest",align:"center",className:"frame-state"}
	        			]},
	        			{kind:enyo.Control,content:"",className:"frame-body"}
	        		]},
	        		{kind:"HFlexBox",style:"position:relative;",className:"frame-cell",flex:1,onclick:"buttonClick",components:[
	        			{kind:enyo.Control,style:"background:url(images/logo_white_small.png) no-repeat right;",flex:1},
	        			{kind:"VFlexBox",flex:1,style:"position:relative;",components:[
	        				{content:"lift-state",align:"center",className:"frame-state"}
	        			]},
	        			{kind:enyo.Control,content:"",className:"frame-body"}
	        		]},
	        		{kind:"HFlexBox",style:"position:relative;",className:"frame-cell",flex:1,onclick:"buttonClick",components:[
	        			{kind:enyo.Control,style:"background:url(images/logo_white_small.png) no-repeat right;",flex:1},
	        			{kind:"VFlexBox",flex:1,style:"position:relative;",components:[
	        				{content:"save-record",align:"center",className:"frame-state"}
	        			]},
	        			{kind:enyo.Control,content:"",className:"frame-body"}
	        		]},
	        		{kind:enyo.Control,style:"margin-top:10px;",className:"frame-bottom frame-top"},
	        		{kind:"HFlexBox",style:"position:relative;",className:"frame-cell",flex:1,onclick:"buttonClick",components:[
	        			{kind:enyo.Control,style:"background:url(images/logo_white_small.png) no-repeat right;",flex:1},
	        			{kind:"VFlexBox",flex:1,style:"position:relative;",components:[
	        				{content:"call",align:"center",className:"frame-state"}
	        			]},
	        			{kind:enyo.Control,content:"",className:"frame-body"}
	        		]},
	        		{kind:"HFlexBox",style:"position:relative;",className:"frame-cell",flex:1,onclick:"buttonClick",components:[
  	        			{kind:enyo.Control,style:"background:url(images/logo_white_small.png) no-repeat right;",flex:1},
  	        			{kind:"VFlexBox",flex:1,style:"position:relative;",components:[
  	        				{content:"camera",align:"center",className:"frame-state"}
  	        			]},
  	        			{kind:enyo.Control,content:"",className:"frame-body"}
  	        		]},
  	        		{kind:enyo.Control,style:"margin-top:10px;",className:"frame-bottom frame-top"},
  	        		{kind:"HFlexBox",style:"position:relative;",className:"frame-cell",flex:1,onclick:"buttonClick",components:[
 	        			{kind:enyo.Control,style:"background:url(images/logo_white_small.png) no-repeat right;",flex:1},
 	        			{kind:"VFlexBox",flex:1,style:"position:relative;",components:[
 	        				{content:"road",align:"center",className:"frame-state"}
 	        			]},
 	        			{kind:enyo.Control,content:"",className:"frame-body"}
 	        		]}
	   		],flex:1},
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
		],
		buttonClick:function(){
			console.log(this.$);
			this.$.pane.selectView(this.$.otherView);
		}
});
