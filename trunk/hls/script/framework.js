enyo.kind({
	name:"enyo.FrameWork",
	kind:enyo.Pane,
	transitionKind: "enyo.transitions.LeftRightFlyin",
	onSelectView: "viewSelected",
	onCreateView: "addPane",
	components: [
        {kind: "enyo.Welcome"},
        {kind: "enyo.Validate"},
        {kind: "enyo.Main"}
    ],
    viewSelected: function(inSender, inView, inPreviousView) {
    	console.log("inPreviousView.name:"+inPreviousView.name);
    	inView.startProcessing();
        inPreviousView.endProcessing();
        /*if (inPreviousView.name == "searchView") {
            this.cancelSearch();
        }*/
    },
    addPane: function(){
    	console.log("addpane");
    }
});