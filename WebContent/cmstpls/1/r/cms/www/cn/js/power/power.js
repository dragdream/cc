(function($) {
	$.getUrlParam = function(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if(r != null) return decodeURIComponent(r[2]); return null;
	}
})(jQuery);
var Power = $.extend(Power || {}, {
	wrap: {
		SELECTED: $("#power-selected-list"),
		OPTIONS: $("#power-option-list"),
		ITEMS: $("#power-item-list"),
		SEARCH: $("#power-search"),
		DETAIL: $("#power-detail")
	},
	event: {
		SELECT: "select",
		CHANGE: "change",
		QUERY: "query",
		PAGE: "page",
		LIST: "list",
		HANDLER: {}
	},
	mask: $("<div><table><tr><td><img src=" + Power.url.LOADING_IMAGE + " width=100 /></td></tr></table></div>").addClass("power-mask").appendTo("#power-mask-wrap")
});
Power.Selection = function(wrap){
	wrap.parentsUntil(".tile-group").last().show();
	this.wrap = wrap;
	this.nodes = [];
	$(Power.event.HANDLER).bind(Power.event.SELECT, $.proxy(this.onSelectOption, this));
	$(Power.event.HANDLER).bind(Power.event.CHANGE, $.proxy(this.onSelectChange, this));
}
Power.Selection.prototype = {
	keys: ["c", "a", "d", "t"],
	appendNode: function(node){
		this.nodes.push(node);
		node.option.group.nav.appendNextGroup(node.option, this.nodes);
	},
	removeNode: function(node){
		node.option.group.nav.removeNextGroup(node.option);
		node.remove();
	},
	removeNodes: function(node){
		var removed = this.nodes.splice($.inArray(node,this.nodes), this.nodes.length);
		for (var i = removed.length - 1; i >= 0; i--) {
			this.removeNode(removed[i]);
		}
	},
	onSelectOption: function(event, option, noquery){
		var node;
		for (var i = 0; i < this.nodes.length; i++) {
			if(this.nodes[i].option.group == option.group)
				node = this.nodes[i];
		}
		if(node) this.removeNodes(node);
		node = new Power.Selection.Node(option, this);
		this.appendNode(node);
		$(Power.event.HANDLER).trigger(Power.event.CHANGE, [node, noquery]);
	},
	unSelectOption: function(node){
		this.removeNodes(node);
		$(Power.event.HANDLER).trigger(Power.event.CHANGE, node);
	},
	onSelectChange: function(event, node, noquery){
		var lastNode = this.nodes[this.nodes.length -1];
		var trigger = false;
		var eventData = {}
		if(this.queried){
			this.queried = false;
			trigger = true;
		}
		if(!noquery && lastNode && lastNode.option.group.data.query){
			this.queried = true;
			trigger = true;
			eventData.query = true;
			eventData.params = {};
			for (var i = 0; i < this.nodes.length; i++) {
				eventData.params[this.keys[i]] = this.nodes[i].option.id;
			}
		}
		if(lastNode && lastNode.option.fn) this.queried = true;
		if(trigger) $(Power.event.HANDLER).trigger(Power.event.QUERY, eventData);
	}
}
Power.Selection.Node = function(option, selection){
	var el = $("<dd><b></b><em></em><i></i></ld>").hide().appendTo(selection.wrap).delay(100).animate({
		width: "show",
		paddingLeft: "show",
		paddingRight: "show",
		marginLeft: "show",
		marginRight: "show"
	}, 100);
	el.find("b").text(option.group.name);
	el.find("em").text(option.name);
	this.el = el;
	this.option = option;
	this.selection = selection;
	el.find("i").bind("click", $.proxy(this.onClick, this));
}
Power.Selection.Node.prototype = {
	onClick: function(){
		this.selection.unSelectOption(this);
	},
	remove: function(){
		this.option.unSelected();
		this.el.animate({
			width: "hide",
			paddingLeft: "hide",
			paddingRight: "hide",
			marginLeft: "hide",
			marginRight: "hide"
		}, 100, function(){
			$(this).remove();
		});
	}
}
Power.Nav = function(data, wrap){
	wrap.parentsUntil(".tile-group").last().show();
	this.data = data;
	this.groupContainer = wrap;
	this.groups = [];
	this.appendGroup(data[0]);
}
Power.Nav.prototype = {
	appendGroup: function(data){
		this.groups.push(new Power.Nav.Group(data, this));
	},
	appendNextGroup: function(option, nodes){
		var nextGroupData = option.group.getNextData(option, nodes);
		if(nextGroupData){
			if(!(nextGroupData instanceof Array)) nextGroupData = [nextGroupData];
			for (var i = 0; i < nextGroupData.length; i++) {
				var group = new Power.Nav.Group(nextGroupData[i], this);
				if(!option.nextGroups) option.nextGroups = [];
				option.nextGroups.push(group);
				this.groups.push(group);
			}
		}
	},
	removeGroup: function(group){
		this.groups.splice($.inArray(group,this.groups),1);
		group.remove();
	},
	removeNextGroup: function(option){
		if(option.nextGroups) {
			for (var i = 0; i < option.nextGroups.length; i++) {
				this.removeGroup(option.nextGroups[i]);
			}
			delete option.nextGroups;
		}
	},
	initByParams: function(){
		var params = [];
		params.push($.getUrlParam("c"));
		params.push($.getUrlParam("a"));
		params.push($.getUrlParam("d"));
		//到部门一级,noquery=false，表示进行查询动作
		var t = $.getUrlParam("t");
		if(t) params.push(t);
		for (var i = 0; i < params.length; i++) {
			var id = params[i];
			if(id){
				for (var j = 0; j < this.groups[i].options.length; j++) {
					if(this.groups[i].options[j].id == id)
						this.groups[i].options[j].el.trigger("click", (i != params.length - 1));
				}
			}
		}
	}
}
Power.Nav.Group = function(data, nav){
	var group = $("<li><em></em><dl></dl></li>").hide().appendTo(nav.groupContainer).slideDown(100);
	group.find("em").text(data.name);
	this.nav = nav;
	this.data = data;
	this.name = data.name;
	this.optionsWrap = group.find("dl");
	this.el = group;
	this.options = [];
	for (var i = 0; i < data.data.length; i++) {
		this.options.push(new Power.Nav.Group.Option(data.data[i], this));
	}
	if(this.options[0].el.offset().top < this.options[this.options.length -1].el.offset().top){
		this.expander = $("<span>").attr("title","展开/收起").appendTo(this.optionsWrap).click(function(event, force){
			if(force)
				$(this).parent().removeClass("expand");
			else
				$(this).parent().toggleClass("expand");
		});
		//默认展开
		this.optionsWrap.addClass("expand");
	}
}
Power.Nav.Group.prototype = {
	remove: function(){
		this.el.slideUp(100, function(){
			$(this).remove();
		});
	},
	getNextData: function(option, nodes){
		if(this.data.next)
			return this.data.next.call(this.nav.data, option, nodes);
		else
			return null;
	}
}
Power.Nav.Group.Option = function(data, group){
	var el = $("<dd>").appendTo(group.optionsWrap).text(data.n);
	if(group.data.isLink) el.addClass("link");
	el.bind("click", $.proxy(this.onClick, this));
	this.el = el;
	this.group = group;
	this.id = data.i;
	this.name = data.n;
	this.p = data.p;
	this.s = data.s;
	if(data.fn) this.fn = data.fn;
	el.mouseenter(function(){
		$(this).addClass("hover");
	}).mouseleave(function(){
		$(this).removeClass("hover");
	});
}
Power.Nav.Group.Option.prototype = {
	onClick: function(event, noquery){
		if(this.group.data.isLink) {
			window.open("?c=" + this.group.nav.groups[0].selected.id + "&a=" + this.p + "&d=" + this.id);
		} else {
			if(!this.group.selected || this.group.selected != this) {
				if(this.group.selected)
					this.group.selected.unSelected();
				this.doSelected(noquery);
				if(this.fn) {
					this.fn.call(this);
				}
			}
		}
	},
	doSelected: function(noquery){
		this.el.addClass("selected");
		this.group.selected = this;
		var firstOption = this.group.optionsWrap.find("dd:first");
		if(firstOption.offset().top < this.el.offset().top){
			this.el.insertBefore(firstOption);
			this.el.removeClass("hover");
		}
		if(this.group.expander) this.group.expander.trigger("click", true);
		$(Power.event.HANDLER).trigger(Power.event.SELECT, [this, noquery]);
	},
	unSelected: function(){
		if(this.group.selected == this){
			delete this.group.selected;
			this.el.removeClass("selected");
		}
	}
}
Power.List = function(wrap){
	wrap.parentsUntil(".tile-group").last().show();
	this.wrap = wrap;
	$(Power.event.HANDLER).bind(Power.event.QUERY, $.proxy(this.onQuery, this));
	$(Power.event.HANDLER).bind(Power.event.PAGE, $.proxy(this.onPage, this));
	$(Power.event.HANDLER).bind(Power.event.LIST, $.proxy(this.onListStatic, this));
}
Power.List.prototype = {
	pageSize: 10,
	onQuery: function(event, eventData){
		this.clear();
		this.eventData = eventData;
		if(this.eventData.params){
			if(!this.eventData.params.p) this.eventData.params.p = 1;
			if(!this.eventData.params.s) this.eventData.params.s = this.pageSize;
		}
		if(eventData.query) this.doQuery(eventData.params);
	},
	onListStatic: function(event, data){
		this.success(data);
	},
	doQuery: function(params){
		$.ajax({
			type: "get",
			url: Power.url.QUERY,
			dataType: "jsonp",
			jsonp: false,
			jsonpCallback:"Power_jsonp",
			data: params,
			beforeSend: $.proxy(this.beforeSend, this),
			success: $.proxy(this.success, this),
			error: $.proxy(this.error, this),
			complete: $.proxy(this.complete, this)
		});
	},
	beforeSend: function(){
		Power.mask.show();
	},
	success: function(data){
		if(data.list && data.list.length > 0){
			this.data = data;
			this.showList();
		} else {
			this.clear();
			$("<div>").addClass("power-none").appendTo(this.wrap);
		}
	},
	error: function(){
		this.clear();
		$("<div>").addClass("power-error").appendTo(this.wrap);
	},
	complete: function(){
		Power.mask.hide();
	},
	onPage: function(event, p){
		this.eventData.params.p = p + 1;
		$(Power.event.HANDLER).trigger(Power.event.QUERY, this.eventData);
	},
	showList: function(){
		this.clear();
		for (var i = 0; i < this.data.list.length; i++) {
			var even = i % 2;
			new Power.List.Item(this.wrap, this.data.list[i], even);
		}
		if(this.data.page && this.data.page.count > 1){
			this.page = new Power.List.Page(this.data.page, this.wrap);
		}
	},
	clear: function(){
		this.wrap.empty();
		if(this.page) this.page.clear();
	}
}
Power.List.Page = function(page, wrap){
	this.total = page.total;
	this.count = page.count;
	this.wrap = wrap;
	this.show(page.p - 1, page.count);
}
Power.List.Page.prototype = {
	pageRange: 3,
	show: function(current){
		this.el = $("<div>").addClass("page-tile relative clearfix").insertAfter(this.wrap);
		var _min = current - this.pageRange, _max = current + this.pageRange;
		var min = _min<0?0:_min, max = _max>=this.count?this.count-1:_max;
		if(_min < 0){
			max -= _min;
			if(max >= this.count) max = this.count - 1;
		}
		if(_max >= this.count){
			min -= _max - (this.count - 1);
			if(min < 0) min = 0;
		}
		if(min > 0) this.appendBtn(0, this.el, true);
		if(min == 2) this.appendBtn(1, this.el, true);
		if(min > 2) this.appendBtn(null, this.el, false, true);
		for (var i = min; i <= max; i++) {
			this.appendBtn(i, this.el, current!=i);
		}
		if(max < this.count - 3) this.appendBtn(null, this.el, false, true);
		if(max == this.count - 3) this.appendBtn((this.count - 2), this.el, true);
		if(max < this.count - 1) this.appendBtn((this.count - 1), this.el, true);

		$("<em>").text("共 " + this.total + " 条").appendTo(this.el);
	},
	appendBtn: function(p, wrap, link, more){
		if(link){
			$("<a href='javascript:;'></a>").click($.proxy(this.change, this, p)).text(p + 1).appendTo(wrap);
		} else {
			if(more){
				$("<span>").addClass("more").appendTo(wrap);
			} else {
				$("<span>").addClass("focus").text(p + 1).appendTo(wrap);
			}
		}
	},
	change: function(pageNo){
		$(Power.event.HANDLER).trigger(Power.event.PAGE, pageNo);
	},
	clear: function(){
		if(this.el) this.el.remove();
	}
}
Power.List.Item = function(wrap, data, even){
	var el = $("<li><em></em><dl><a target='_blank'></a><div><span class='category' title='分类'></span><span class='code' title='编号'></span><span class='type' title='类别'></span><span class='dept' title='部门'></span></div></dl></li>").appendTo(wrap);
	if(even) el.addClass("even");
	el.find("a").text(data.n);
	if(data.href) {
		el.find("a").attr("href", data.href);
	} else {
		el.find("a").attr("href", Power.url.DETAIL_PAGE[data.c] + "?i=" + data.i);
	}
	if(data.m){
		el.find("span.code").text(data.m);
	} else {
		el.find("span.code").remove();
	}
	if(data.c){
		for (var i = 0; i < powerindex.category.length; i++) {
			if(data.c == powerindex.category[i].i)
			el.find("span.category").text(powerindex.category[i].n);
		}
	} else {
		el.find("span.category").remove();
	}
	if(data.t){
		el.find("span.type").text(data.t);
	} else {
		el.find("span.type").remove();
	}
	if(data.d){
		el.find("span.dept").text(data.d);
	} else {
		el.find("span.dept").remove();
	}
}
Power.Search = function(wrap, data, init){
	var area = wrap.find(".area-wrap");
	area.selected = area.find(">em");
	this.selected = area.selected;
	area.list = area.find(">ul");
	var input = wrap.find(".input-wrap>input").focus(function(){
		$(this).select();
	});
	this.input = input;
	var button = wrap.find(".button");
	area.mouseenter(function(){
		area.list.stop(true, true).show(100);
	}).mouseleave(function(){
		area.list.stop(true, true).hide(100);
	});
	var params = {
		a: $.getUrlParam("a"),
		q: $.getUrlParam("q")
	}
	if(init && params.a && params.q){
		input.val(params.q);
		this.renderArea(area.selected, area.list, data, params.a);
		$(Power.event.HANDLER).trigger(Power.event.QUERY, {
			query: true,
			params: params
		});
	} else {
		this.renderArea(area.selected, area.list, data);
	}
	var self = this;
	area.list.on("click", ">li", function(){
		area.list.stop().hide();
		self.renderArea(area.selected, area.list, data, $(this).attr("data-id"));
		input.focus();
	}).on("mouseenter", ">li", function(){
		$(this).addClass("hover");
	}).on("mouseleave", ">li", function(){
		$(this).removeClass("hover");
	});
	input.keypress($.proxy(function(event){
		if(event.keyCode == 13) this.doQuery();
	}, this));
	button.click($.proxy(this.doQuery, this));
}
Power.Search.prototype = {
	renderArea: function(selected, list, data, id){
		list.empty();
		if(data){
			if(id){
				selected.attr("data-id", id);
				for (var i = 0; i < data.length; i++) {
					if(id == data[i].i){
						selected.text(data[i].n);
						break;
					}
				}
			} else if(data.length > 0){
				selected.attr("data-id", data[0].i).text(data[0].n);
			}
			for (var i = 0; i < data.length; i++) {
				if(!id || id!=data[i].i)
					$("<li>").attr("data-id", data[i].i).text(data[i].n).appendTo(list);
			}
		}
	},
	doQuery: function(){
		var id = this.selected?this.selected.attr("data-id"):null;
		var key = $.trim(this.input.val());
		if(id && key){
			window.open(Power.url.SEARCH_PAGE + "?a=" + id + "&q=" + encodeURIComponent(key));
		}
	}
}
Power.reIndex = function(powerindex){
	powerindex.category.splice(2, 0, {
		i: 3,
		n: "项目管理负面清单",
		fn: function(){
			$(Power.event.HANDLER).trigger(Power.event.LIST, {
				list: [{
					c: 3,
					i: 1,
					n: "航空港经济综合实验区企业投资项目管理负面清单（试行）"
				}, {
					c: 3,
					i: 2,
					n: "荥阳市产业集聚区企业投资项目管理负面清单（试行）"
				}, {
					c: 3,
					i: 3,
					n: "新郑市中原食品工业园企业投资项目管理负面清单（试行）"
				}]
			});
		}
	});
	// by cdl for (用以下载word文档)
	// powerindex.category.push({
	// 	i: 9,
	// 	n: "相关下载",
	// 	fn: function(){
	// 		$(Power.event.HANDLER).trigger(Power.event.LIST, {
	// 			list: [{
	// 				c: 9,
	// 				i: 1,
	// 				n: "aaa",
	// 				href: "#"
	// 			}, {
	// 				c: 9,
	// 				i: 2,
	// 				n: "bbb",
	// 				href: "#"
	// 			}, {
	// 				c: 9,
	// 				i: 3,
	// 				n: "cc",
	// 				href: "#"
	// 			}]
	// 		});
	// 	}
	// });
	var data = [{
		name:"分类",
		data:powerindex.category,
		next: function(option, nodes){
			var dst = [];
			var as = {};
			var c = nodes[0].option.id;
			var ds = this[2].data;
			for (var i = 0; i < ds.length; i++) {
				if(ds[i].s && ds[i].s[c])
					as[ds[i].p] = true;
			}
			for (var i = 0; i < this[1].data.length; i++) {
				if(as[this[1].data[i].i])
					dst.push(this[1].data[i]);
			}
			if(dst.length > 0){
				return {
					name: this[1].name,
					next: this[1].next,
					data: dst
				}
			}
		}
	}, {
		name:"地区",
		// by cdl for (只显示第一个地区：郑州)
		// by hjfzrj for (显示两个地区：郑州 新密  2015.09.08)
		data:[powerindex.area[0], powerindex.area[3]],
		//data:[powerindex.area[0]],
		// data:powerindex.area,
		next: function(option, nodes){
			var src = this[2];
			var dst = {
				name: src.name,
				data: [],
				next: src.next
			}
			var dst = [];
			var c = nodes[0].option.id;
			var ds = this[2].data;
			for (var i = 0; i < ds.length; i++) {
				if(ds[i].p == option.id && ds[i].s && ds[i].s[c])
					dst.push(ds[i]);
			}
			if(dst.length > 0){
				return {
					name: this[2].name,
					query: this[2].query,
					next: this[2].next,
					data: dst
				}
			}
		}
	}, {
		name:"部门",
		query: true,
		data:powerindex.department,
		next: function(option, nodes){
			var dst = [];
			var c = nodes[0].option.id;
			//匹配清单类别
			var cts = option.s[c];
			if(option.s && cts && cts instanceof Array && cts.length > 0) {
				var data = [];
				for (var i = 0; i < cts.length; i++) {
					var ts = this[3].data[nodes[0].option.id];
					for (var j = 0; j < ts.length; j++) {
						if(cts[i] == ts[j].i){
							data.push(ts[j]);
						}
					}
				}
				if(data.length > 0){
					dst.push({
						name: this[3].name,
						query: this[3].query,
						data: data
					});
				}
			}
			//匹配相关部门
			var relatives = [];
			var area = nodes[1].option.name;
			var department = option.name;
			var id =  option.id;
			department = department.replace(new RegExp("^郑州市"), "");
			department = department.replace(new RegExp("^" + area), "");
			department = department.replace(new RegExp("^(市|区|县|管城区|经开区)"), "");
			department = department.replace(new RegExp("(委|局|中心|办|所|处|队|大队|支队|站|分局)$"), "");
			department = department.replace(new RegExp("(（|\\().*(）|\\))$"), "");	//去掉括号及其内部文字
			var ds = this[2].data;
			for (var i = 0; i < ds.length; i++) {
				if(ds[i].s && ds[i].s[c] && id != ds[i].i && ds[i].n.indexOf(department) >= 0)
					relatives.push(ds[i]);
			}
			if(relatives.length > 0){
				dst.push({
					name: this[4].name,
					isLink: this[4].isLink,
					data: relatives
				});
			}
			if(dst.length > 0) return dst;
		}
	}, {
		name:"类别",
		query: true,
		data:powerindex.type
	}, {
		name:"相关部门",
		isLink:true
	}];
	return data;
}
Power.Detail = function(wrap, layout){
	this.wrap = wrap;
	this.layout = layout;
	this.relativeWrap = wrap.find(".power-detail-relative");
}
Power.Detail.prototype = {
	loadDetail: function(){
		$.ajax({
			type: "get",
			url: Power.url.DETAIL,
			dataType: "jsonp",
			jsonp: false,
			jsonpCallback:"Power_jsonp",
			data: {
				i: $.getUrlParam("i"),
				c: Power.url.DETAIL_CATEGORY_ID
			},
			beforeSend: $.proxy(this.beforeSend, this),
			success: $.proxy(this.success, this),
			error: $.proxy(this.error, this),
			complete: $.proxy(this.complete, this)
		});
	},
	beforeSend: function(){
		Power.mask.show();
	},
	error: function(){
		this.wrap.empty();
		$("<div>").addClass("power-error").appendTo(this.wrap);
	},
	complete: function(){
		Power.mask.hide();
	},
	success: function(data){
		if(data && data.res) {
			this.render(data);
		} else {
			this.wrap.empty();
			$("<div>").addClass("power-none").appendTo(this.wrap);
		}
	},
	render: function(data){
		for (var i = 0; i < this.layout.length; i++) {
			var field = this.layout[i];
			var d = (field.fn && field.fn=="relative")?{relative:data.relative}:data.res;
			if(field.hideBlock && (!d[field.key] || (d[field.key] instanceof Array && d[field.key].length <= 0))){
				this.hideBlock(field);
			} else {
				var fn = field.fn?((field.fn instanceof Function)?field.fn:this.renders[field.fn]):this.renders.text;
				fn.call(this, field, d);
			}
		}
	},
	hideBlock: function(field){
		$("#" + field.id).parent().parent().parent().hide();
	},
	renders: {
		//文本
		text: function(field, data){
			if(data[field.key]) {
				$("#" + field.id).text(data[field.key]);
			}
		},
		//办理时限
		limit: function(field, data){
			var value = null;
			for (var i = 0; i < field.key.length; i++) {
				if(data[field.key[i]] || data[field.key[i]] == 0) {
					value = data[field.key[i]];
					break;
				}
			}
			if(value == 0) {
				value = "即办";
			} else if(value) {
				value += "工作日";
			}
			if(value)
				$("#" + field.id).text(value);
		},
		//列表
		list: function(field, data) {
			var wrap = $("#" + field.id);
			var list = data[field.key];
			var listKey = field.listKey;
			for (var i = 0; i < list.length; i++) {
				$("<p>").appendTo(wrap).text(list[i][listKey]);
			}
		},
		//流程图
		pics: function(field, data) {
			var wrap = $("#" + field.id);
			var list = data[field.key];
			var listKey = field.listKey;
			for (var i = 0; i < list.length; i++) {
				$("<img>").appendTo(wrap).addClass("power-flow-img").attr("src", Power.url.DETAIL_IMAGE + list[i][listKey]);
			}
		},
		//表格
		table: function(field, data) {
			var wrap = $("#" + field.id);
			var thead = wrap.find("thead>tr");
			var tbody = wrap.find("tbody");
			for (var i = 0; i < field.thead.length; i++) {
				$("<td>").appendTo(thead).text(field.thead[i]);
			}
			var list = data[field.key];
			for (var i = 0; i < list.length; i++) {
				var tr = $("<tr>").appendTo(tbody);
				for (var j = 0; j < field.tbody.length; j++) {
					var txt = (field.tbody[j] instanceof Object && field.tbody[j].fn)?field.tbody[j].fn.call(this, list[i][field.tbody[j].key]):list[i][field.tbody[j]];
					$("<td>").appendTo(tr).html(txt);
				}
			}
		},
		//相关清单
		relative: function(field, data) {
			var wrap = $("#" + field.id);
			var list = data[field.key];
			for (var i = 0; i < list.length; i++) {
				var even = i % 2;
				new Power.List.Item(wrap, list[i], even);
			}
		}
	}
}
Power_jsonp = function(data){}