/**
 * by cdl371@126.com
 * <span data-href="#" data-title="献唱公益节目讲述梦想" data-img="images/decorations/deco_view.jpg" data-target="_blank"></span>
 * 必须用span,否则ie6下inline-block会有问题
 */
var initFocusImage = function(option) {
	var styles = {
		focus: {
			"width": "100%",
			"height": "100%",
			"overflow": "hidden",
			"position": "relative"
		},
		wrap: {
			"white-space":"nowrap",
			"position":"absolute",
			"list-style":"none",
			"margin":0,
			"padding":0,
			"font-size":0,
			"width":"100%",
			"height":"100%"
		},
		item: {
			"width": "100%",
			"height": "100%",
			"display": "inline-block",
			"overflow": "hidden",
			"position": "relative",
			"background": "black",
			"list-style": "none",
			"margin": 0,
			"padding": 0
		},
		img: {
			"position": "absolute",
			"display": "block",
			"border": 0,
			"margin": 0,
			"padding": 0
		},
		titlebg: {
			"width": "100%",
			"display": "block",
			"background-color": "black",
			"position": "absolute",
			"bottom": 0
		},
		title: {
			"width": "100%",
			"color": "white",
			"position": "absolute",
			"bottom": 0
		},
		btnwrap: {
			"position": "absolute",
			"right": 0,
			"bottom": 0,
			"text-align": "right",
			"font-size": 0,
			"margin-right": "10px"
		},
		btn: {
			"display": "inline-block",
			"font-size": 0,
			"cursor": "pointer",
			"background": "white"
		}
	}
	var defaultOption = {
		stretch: false,		//拉伸
		cut: false,			//裁切(stretch=true时生效)，=true时，按比例拉伸到满屏，裁切掉多余的部分
		interval: 4000,		//切换间隔
		titleHeight: 40,	//标题栏高度
		btnSize: 6,			//指示器大小
		speed: 300,			//图片滑动速度
		titleStyle: {		//标题样式
			"margin-left": "15px",
			"font-size": "14px"
		}
	}
	var option = $.extend(defaultOption, option);
	var focus = $(option.el).css(styles.focus);
	var wrap = focus.find(">div").css(styles.wrap);
	var list = focus.find(">div>span").css(styles.item);
	var index = 0;
	var picTimer;

	var resizeImg = function(){
		var width = focus.width();
		var height = focus.height();
		var img = $(this);
		var tmp = new Image();
		tmp.src = img.attr("src");
		var w = tmp.width;
		var h = tmp.height;
		if(!w || !h) return;
		if(option.stretch){
			if(!option.cut){
				img.width("100%");
				img.height("100%");
				w = width;
				h = height;
			} else {
				var sRate = height / width;
				var rate = h / w;
				if(rate < sRate){
					img.height("100%");
					img.width("auto");
					h = height;
					w = h / rate;
				} else {
					img.width("100%");
					img.height("auto");
					w = width;
					h = w * rate;
				}
			}
		} else {
			var sRate = height / width;
			var rate = h / w;
			if(w > width || h > height){
				if(rate > sRate){
					img.height("100%");
					img.width("auto");
					h = height;
					w = h / rate;
				} else {
					img.width("100%");
					img.height("auto");
					w = width;
					h = w * rate;
			}
			}
		}
		img.css("left", (width - w) / 2);
		img.css("top", (height - h) / 2);
	}
	var resizeAllImg = function(imgs){
		for (var i = 0; i < imgs.length; i++) {
			var img = imgs[i];
			resizeImg.call(img);
		}
	}
	var showPics = function(index) {
		resizeAllImg(imgs);
		var nowLeft = -index * focus.width(); //根据index值计算ul元素的left值
		wrap.stop(true,false).animate({"left":nowLeft}, option.speed); //通过animate()调整ul元素滚动到计算出的position
		btns.stop(true,false).animate({"opacity":"0.4"}, option.speed).eq(index).stop(true,false).animate({"opacity":"1"}, option.speed); //为当前的按钮切换到选中的效果
	}
	var btnWrap = $("<div>").css(styles.btnwrap).css("padding", (((option.titleHeight) - option.btnSize) / 2 + "px") + " 0");
	var btns = $();
	var imgs = $();
	for (var i = 0; i < list.length; i++) {
		var li = list.eq(i);
		var href = li.attr("data-href");
		var target = li.attr("data-target");
		var img = li.attr("data-img");
		var title = li.attr("data-title");
		var imgwrap;
		if(href){
			var el = $("<a>");
			el.attr("href", href);
			target && el.attr("target", target);
			li.append(el);
			imgwrap = el;
		} else {
			imgwrap = li;
		}
		if(img){
			var el = $("<img>").css(styles.img);
			el.get(0).onload = resizeImg;
			el.attr("src", img);
			el.attr("width", null);
			el.attr("height", null);
			imgwrap.append(el);
			imgs = imgs.add(el);
		}
		if(title){
			var bg = $("<div>").css(styles.titlebg).css("opacity",0.5).height(option.titleHeight);
			var el = $("<div>").text(title).css(styles.title).css("line-height", (option.titleHeight) + "px").css(option.titleStyle);
			li.append(bg);
			li.append(el);
		}
		btns = btns.add($("<span>").css(styles.btn).width(option.btnSize).height(option.btnSize).css("margin-left", option.btnSize + "px"));
	}
	focus.append(btns);
	btns.wrapAll(btnWrap);

	btns.css("opacity",0.4).mouseenter(function() {
		showPics($(this).index());
	}).eq(0).trigger("mouseenter");


	focus.hover(function() {
		clearInterval(picTimer);
	},function() {
		picTimer = setInterval(function() {
			index++;
			if(index == list.length) {index = 0;}
			showPics(index);
		}, option.interval);
	}).trigger("mouseleave");
}
