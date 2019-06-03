var initAccessible = function(container, trigger) {
	/**
	 * @brief 获取元素的计算样式（最终的样式）
	 * @author kenshinlin 2011/11/30
	 * @param elem 要计算样式的元素,dom对象或字符串（id号）
	 * @pro 要获取的样式属性,这个字符串是骆驼型的，如marginLeft而不是margin-left
	 */
	var getStyle = function (elem,pro) {
		elem = ('string'==typeof elem)?document.getElementById('elem'):elem;
		if(!elem) return null;
		if(elem.style[pro])  //内联
			return elem.style[pro];
		else if(elem.currentStyle) {	 //IE
			return elem.currentStyle[pro];
		} else if(window.getComputedStyle){  //W3C标准
			var s=window.getComputedStyle(elem,null);
			return s[pro];
		} else if(document.defaultView&&document.defaultView.getComputedStyle) {	//FF,CHROME等
			pro=pro.replace(/([A-Z])/g,"-$1"); //如marginLeft转为margin-Left
			pro=pro.toLowerCase();   //再转为小写margin-left
			var s=document.defaultView.getComputedStyle(elem,"");
			return s&&s.getPropertyValue(pro);
		} else {
			return null;
		}
	}
	var prefix = "accessible-";
	var fns = [{
		// disabled: false,
		order: 0,
		name: "字体缩小",
		cookieName: prefix + "fontZoom",
		cookieStep: -1,
		minStep: -3,
		run: function(){
			container.style.fontSize = parseInt(getStyle(container, "fontSize").replace("px","")) - 2 + "px";
		},
		cancel: function(){
			container.style.fontSize = "";
		}
	}, {
		order: 1,
		name: "字体放大",
		cookieName: prefix + "fontZoom",
		cookieStep: 1,
		maxStep: 3,
		run: function(){
			container.style.fontSize = parseInt(getStyle(container, "fontSize").replace("px","")) + 2 + "px";
		},
		cancel: function(){
			container.style.fontSize = "";
		}
	}, {
		order: 2,
		name: "页面缩小",
		cookieName: prefix + "zoom",
		cookieStep: -1,
		minStep: -2,
		run: function(){
			if(window.getComputedStyle && !window.getComputedStyle(container,null)["zoom"]){
				container.style.transformOrigin = "top center";
				var scale = parseFloat(container.style.transform?/scale\((.*)\)/.exec(container.style.transform)[1]:1);
				container.style.transform = "scale(" + (scale - 0.25) + ")";
			} else {
				container.style.zoom = (parseFloat(getStyle(container, "zoom")) || 1) - 0.25;
			}
		},
		cancel: function(){
			if(window.getComputedStyle && !window.getComputedStyle(container,null)["zoom"]){
				container.style.transform = "";
			} else {
				container.style.zoom = "";
			}
		}
	}, {
		order: 3,
		name: "页面放大",
		cookieName: prefix + "zoom",
		cookieStep: 1,
		maxStep: 2,
		run: function(){
			if(window.getComputedStyle && !window.getComputedStyle(container,null)["zoom"]){
				container.style.transformOrigin = "top center";
				var scale = parseFloat(container.style.transform?/scale\((.*)\)/.exec(container.style.transform)[1]:1);
				container.style.transform = "scale(" + (scale + 0.25) + ")";
			} else {
				container.style.zoom = (parseFloat(getStyle(container, "zoom")) || 1) + 0.25;
			}
		},
		cancel: function(){
			if(window.getComputedStyle && !window.getComputedStyle(container,null)["zoom"]){
				container.style.transform = "";
			} else {
				container.style.zoom = "";
			}
		}
	}, {
		order: 4,
		name: "高对比度",
		cookieName: prefix + "contrast",
		cookieToggle: true,
		cls: "contrast",
		run: function(){
			container.className += " " + this.cls;
		},
		cancel: function(){
			container.className = container.className.replace(this.cls, "");
		}
	}, {
		order: 5,
		name: "辅助线",
		cookieName: prefix + "cross",
		cookieToggle: true,
		color: "red",
		width: 2,
		zIndex: 1000,
		event: document.addEventListener?"mousemove":"onmousemove",
		run: function(){
			if(!this.mousemove){
				var body = document.querySelector("body");
				var wrap = document.createElement("div");
				wrap.style.position = "relative";
				body.appendChild(wrap);
				var elx = document.createElement("div");
				var ely = document.createElement("div");
				elx.style.pointerEvents = ely.style.pointerEvents = "none";
				elx.style.position = ely.style.position = "fixed";
				elx.style.backgroundColor = ely.style.backgroundColor = this.color;
				elx.style.zIndex = ely.style.zIndex = this.zIndex;
				elx.style.left = elx.style.top = ely.style.left = ely.style.top = 0;
				elx.style.width = ely.style.height = this.width + "px";
				elx.style.height = ely.style.width = "100%";
				wrap.appendChild(elx);
				wrap.appendChild(ely);
				var self = this;
				this.elx = elx;
				this.ely = ely;
				this.mousemove = function(e){
					var zoom = parseFloat(getStyle(body, "zoom")) || 1;
					elx.style.left = ((e.clientX) + self.width) + "px";
					ely.style.top = ((e.clientY) + self.width) + "px";
				}
				var addEvent = document.addEventListener || document.attachEvent;
				addEvent(this.event, this.mousemove);
			}
		},
		cancel: function(){
			if(this.mousemove) {
				var removeEvent = document.removeEventListener || document.detachEvent;
				removeEvent(this.event, this.mousemove);
				this.elx.parentNode.removeChild(this.elx);
				this.ely.parentNode.removeChild(this.ely);
				delete this.mousemove;
			}
		}
	}, {
		order: 6,
		name: "恢复正常",
		run: function(){
			for(var k in fns) {
				var fn = fns[k];
				if(fn.cancel){
					fn.cancel.call(fn);
					if(fn.cookieName)
						jQuery.cookie(fn.cookieName, null);
				}
			}
		}
	}, {
		order: 7,
		name: "关闭",
		run: function(){
			fns.wrap.style.display = "none";
		}
	}];
	var wrap = (function(){
		var wrap = document.createElement("div");
		wrap.className = "accessible-wrap";
		var body = document.querySelector("body");
		body.insertBefore(wrap, body.firstChild);
		var body = document.createElement("div");
		body.className = "accessible-body";
		wrap.appendChild(body);
		var fix = document.createElement("div");
		fix.className = "accessible-body-fixed";
		wrap.appendChild(fix);
		var main = document.createElement("div");
		main.className = "main-wrap";
		body.appendChild(main);
		wrap.btnWrap = main;
		return wrap;
	})();

	fns.wrap = wrap;
	var hasCookie = false;
	for(var i=0; i<fns.length; i++) {
		var fn = fns[i];
		if(!fn.disabled && fn.name) {
			var btn = document.createElement("span");
			btn.innerHTML = fn.name;
			wrap.btnWrap.appendChild(btn);
			btn.fn = fn;
			btn.onclick = function(){
				var fn = this.fn;
				if(fn.cookieName) {
					var count = parseInt(jQuery.cookie(fn.cookieName) || 0);
					var step = fn.cookieStep || 1;
					if(fn.cookieToggle && count > 0) step *= -1;
					step = ((fn.maxStep && count >= fn.maxStep) || (fn.minStep && count <= fn.minStep))?0:step;
					count += step;
					jQuery.cookie(fn.cookieName, count?count:null);
					if(step)
						if(fn.cookieToggle && count <= 0)
							fn.cancel.call(fn);
						else
							fn.run.call(fn);
				} else {
					fn.run.call(fn);
				}
			}
			if(fn.cookieName && fn.run){
				var count = parseInt(jQuery.cookie(fn.cookieName));
				if(count) {
					hasCookie = true;
					if(count * (fn.cookieStep || 1) > 0)
						for (var j = 0; j < Math.abs(count); j++) {
							fn.run.call(fn);
						}
				}
			}
		}
	}
	wrap.onselectstart = function(){
		return false;
	}
	if(trigger){
		trigger.onclick = function(){
			if(wrap.style.display && wrap.style.display == "block") {
				wrap.style.display = "none";
			} else {
				wrap.style.display = "block";
			}
		}
	}
	if(hasCookie) {
		wrap.style.display = "block";
	}
}