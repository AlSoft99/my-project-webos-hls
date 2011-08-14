enyo.kind({
	name:"enyo.Life",
	kind:enyo.HFlexBox,
	components:[
		{kind:enyo.VFlexBox,components:[
			{kind:enyo.VFlexBox,className:"blue-box box-content",flex:1,components:[
				{kind:enyo.Control,content:"血压"},
				{kind:enyo.HFlexBox,style:"padding-left:30px;font-size:17px;",components:[
					{kind:enyo.Control,content:"收缩压:"}
				]},
				{kind:enyo.HFlexBox,style:"padding-left:30px;font-size:17px;",components:[
   					{kind:enyo.Control,content:"舒张压:"}
   				]}
			]},
			{kind:enyo.VFlexBox,className:"blue-box box-content",flex:1,components:[
				{kind:enyo.Control,content:"心跳:"}
			]},
			{kind:enyo.VFlexBox,className:"blue-box box-content",flex:1,components:[
				{kind:enyo.Control,content:"体温:"}
			]},
			{kind:enyo.VFlexBox,className:"blue-box box-content",flex:1,components:[
				{kind:enyo.Control,content:"呼吸:"}
			]},
			{kind:enyo.Control,className:"blue-box box-content",style:"height:5%;width:40%;text-align:center;margin-left: auto;",content:"停止检测"}
		],flex:3},
		{kind:enyo.VFlexBox,className:"blue-box box-content",components:[
		    {kind:enyo.VFlexBox,components:[
				{kind:enyo.Control,content:"血压:",className:"life-right-title life-right-font"},
				{kind:enyo.HFlexBox,components:[
					{kind:enyo.Control,content:"140",className:"red-box box-center life-right-left-cell",flex:1},
					{kind:enyo.Control,content:"<130",className:"life-right-font box-center life-right-right-cell", style:"text-align:left;",flex:1}
				]},
				{kind:enyo.HFlexBox,components:[
					{kind:enyo.Control,content:"70",className:"box-center life-right-left-cell",flex:1},
					{kind:enyo.Control,content:"<85",className:"life-right-font box-center life-right-right-cell", style:"text-align:left;",flex:1}
				]}
		    ],flex:1},
		    {kind:enyo.VFlexBox,components:[
				{kind:enyo.Control,content:"心跳:",className:"life-right-title life-right-font"},
				{kind:enyo.HFlexBox,components:[
					{kind:enyo.Control,content:"140",className:"box-center life-right-left-cell",flex:1},
					{kind:enyo.Control,content:"<130",className:"life-right-font box-center life-right-right-cell", style:"text-align:left;",flex:1}
				]}
		    ],flex:1},
			
		    {kind:enyo.VFlexBox,components:[
				{kind:enyo.Control,content:"体温:",className:"life-right-title life-right-font"},
				{kind:enyo.HFlexBox,components:[
					{kind:enyo.Control,content:"36.5",className:"box-center life-right-left-cell",flex:1},
					{kind:enyo.Control,content:"36-37",className:"life-right-font box-center life-right-right-cell", style:"text-align:left;",flex:1}
				]}
		    ],flex:1},
		    
		    {kind:enyo.VFlexBox,components:[
				{kind:enyo.Control,content:"呼吸:",className:"life-right-title life-right-font"},
				{kind:enyo.HFlexBox,components:[
					{kind:enyo.Control,content:"18",className:"box-center life-right-left-cell",flex:1},
					{kind:enyo.Control,content:"16-30",className:"life-right-font box-center life-right-right-cell", style:"text-align:left;",flex:1}
				]}
		    ],flex:1}
		],flex:1}
	]
});