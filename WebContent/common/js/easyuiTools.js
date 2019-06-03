var targetsTabs = {};
var easyuiTools = {
/**
 * 添加tab的方法
 * opts：参数
 * target：目标元素
 */
addTab:function(opts,target){
	var centerTabs = targetsTabs[target.attr("id")];
	if(!centerTabs){
		centerTabs = target.tabs({
			border : false,
			fit : true,
			onSelect:function(title){
				if(opts.reload){
					var tab = target.tabs('getTab',title);
					tab.panel("refresh");
				}
			}
		});
		targetsTabs[target.attr("id")]=centerTabs;
	}
	var options = $.extend({
		title : '',
		content : '<iframe src="' + opts.src + '" frameborder=""  style="border:0;width:100%;height:100%;"></iframe>',
		closable : true,
		iconCls : ''
	}, opts);
	if (centerTabs.tabs('exists', options.title)) {
		centerTabs.tabs('close', options.title);
	}
	centerTabs.tabs('add', options);
}
};

