enyo.kind({
	name:"enyo.FrameWork",
	kind:enyo.Pane,
	transitionKind: "enyo.transitions.LeftRightFlyin",
	components: [
        {kind: "enyo.Welcome"},
        {kind: "enyo.Validate"},
        {kind: "enyo.Main"}
    ]
});