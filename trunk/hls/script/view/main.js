enyo.kind({
	name:"enyo.Main",
	kind:"HFlexBox",
	style:"padding:10px;",
	components:[
   		{kind:"MainItem",flex:1},
   		{kind:"VFlexBox",components:[
        		{kind:enyo.Control,content:"车载医疗救护系统",className:"header-title"},
        		{kind: "Pane", transitionKind: "enyo.transitions.LeftRightFlyin",style:"margin-left:2%;", components: [
        			{kind: "enyo.History"},
        			{kind: "enyo.Suggest"},
        			{kind: "enyo.Life"}
        		],flex:15},
        		{kind:"HFlexBox",style:"text-align:right;",flex:1,components:[
          			{kind:enyo.Control,content:"正在检测目的地路况",className:"frame-footer frame-select",flex:1},
          			{kind:enyo.Control,content:"正在拨号中：123-456-78",className:"frame-footer",flex:1},
          			{kind:enyo.Control,content:"",className:"frame-footer frame-select",flex:1}
          		]}
  		],flex:4.5}
	]
});
var main = {
	select:function(){
		
	},
	changeOnClass:function(className){
		var number = className.substring(className.length-1);
		var start = className.substring(0,9);
		return start+"-on-"+number;
	},
	changeOffClass:function(className){
		var number = className.substring(className.length-1);
		var start = className.substring(0,9);
		return start+"-off-"+number;
	},
	checkState:function(){
		$(".frame-body").each(function(i){
			var className = $(this).prev().prev().attr("class");
			if($(this).hasClass("frame-select")){
				$(this).prev().prev().removeClass().addClass(main.changeOnClass(className));
			}else{
				$(this).prev().prev().removeClass().addClass(main.changeOffClass(className));
			}
		});
	}
}