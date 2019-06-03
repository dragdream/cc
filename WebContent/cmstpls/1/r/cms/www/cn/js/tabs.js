var initTabs = function(tabs, contents, cls, event, initIndex, extCls, initSubIndex){
	var cls = cls || "focus";
	var event = event || "mouseover";
	tabs.bind(event, function(){
		var tab = $(this);
		var index = tab.index();
		if(!tab.hasClass(cls)){
			tabs.filter("." + cls).removeClass(cls);
			tab.addClass(cls);
			if(contents) {
				contents.filter("." + cls).removeClass(cls);
				contents.eq(index).addClass(cls);
			}
		}
	});
	if(initIndex || initIndex===0) {
		var tab = tabs.eq(initIndex);
		tab.trigger(event);
		if(extCls) {
			tab.addClass(extCls);
			if(initSubIndex || initSubIndex===0) {
				var dds = tab.find("dd");
				dds.eq(initSubIndex).addClass(extCls);
			}
		}
	} else {
		tabs.eq(0).trigger(event);
	}
}
