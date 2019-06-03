/**
 * 
 * 这个是浮动 div 用jquery 写的 支持扩展 author:zhp
 */
( function($) {
	var panel;
	var focus = false;
	var yScrollLength = 0;
	var xScrollLength = 0;
	/**
	 * 处理滚动条Y轴长度-----扩展 ---syl
	 */
	$(window).scroll(
			function getPageScroll() {
				if (self.pageYOffset) {
					yScrollLength = self.pageYOffset;
					XScrollLength = self.pageXOffset;

				} else if (document.documentElement
						&& document.documentElement.scrollTop) {
					yScrollLength = document.documentElement.scrollTop;
					xScrollLength = document.documentElement.scrollLeft;
				} else if (document.body) {
					yScrollLength = document.body.scrollTop;
					xScrollLength = document.body.scrollLeft;
				}
				var arrayPageScroll = new Array(xScrollLength, yScrollLength);
				return arrayPageScroll;
			});

	

	/***
	 *判断浏览器， IE兼容模式 ---syl
	 */
	function isBrowserVersonTop() {
		if ($.browser.msie && document.documentElement.scrollTop == 0) {
			return true;
		}
		return false;
	}
	;

	function setTimeHandler() {
		setInterval( function() {
			if(!focus){
				panel.hide();
			}
		}, 300);
	}

	function showMenuHandler() {
		panel.show();
	}

	function mousePosition(ev) {
		if (!ev)
			ev = window.event;
		if (ev.pageX || ev.pageY) {
			return {
				x : ev.pageX,
				y : ev.pageY
			};
		}
		return {
			x : ev.clientX + document.documentElement.scrollLeft
					- document.body.clientLeft,
			y : ev.clientY + document.documentElement.scrollTop
					- document.body.clientTop
		};
	}
	//自訂function 結束
	
	var menuUl = undefined;
	//menus = [{name:'',action:function,extData:[]}]
	//opts = {top:x,left:x,width:x,height:x}
	$.TeeMenu=function( menus,opts){
		if(!menuUl){
			menuUl = $("<div  style='min-width:70px;z-index:100000000000;padding-left:10px;' id='mouseOverMenu'></div>");
			$("body").append(menuUl);
			menuUl.hide();
		}
		menuUl.css({position:'absolute',left:opts.left,top:opts.top,height:"30px",width:opts.width});
		
		menuUl.html("");
		for(var i=0;i<menus.length;i++){
			var menu = menus[i];
			var className = menus[i].className;
			var li = $("<span style='cursor:pointer;'  class='" + className + "'  title='"+menus[i].name+"' >&nbsp;&nbsp;&nbsp;&nbsp; <a >  </a></span>");
		
			li.data("data",menu);
			li.click(function(){
				var menu = $(this).data("data");
				menu.action.apply(null,menu.extData);
			});
			menuUl.append(li);
		}
		//menuUl.append("<div style='clear:both;'></div>");
		menuUl.show();
		//鼠标悬浮选中
		menuUl.bind("mouseover",function(){
			menuUl.show();
		});
		//鼠标悬浮出去
		menuUl.bind("mouseout",function(){
			menuUl.hide();
		});
	}
	
	$(document).click(function(){
		if(menuUl){
			menuUl.hide();
		}
	});
})(jQuery)