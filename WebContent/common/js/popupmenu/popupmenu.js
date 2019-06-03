/**
**--------------------
**  嵌入式菜单插件    |
**--------------------
**/
(function(){
	//[{alt:xxx,text:xxx,func:function}]
	var menu;
	var menuBody;
	var menuTop;
	var randId = "embedMenu"+new Date().getTime();
	function init(){
		if(!menu || !menuBody){
			var menuHtml = "<table class='embed-menu'>";
			menuHtml+="<tr class='embed-menu-head'><td class='embed-menu-top-left'></td><td id='"+randId+"top' class='embed-menu-top'></td><td class='embed-menu-top-right'></td></tr>";
			menuHtml+="<tr><td class='embed-menu-left'></td><td class='embed-menu-body' id='"+randId+"'></td><td class='embed-menu-right'></td></tr>";
			menuHtml+="<tr class='embed-menu-foot'><td class='embed-menu-bottom-left'></td><td class='embed-menu-bottom'></td><td class='embed-menu-bottom-right'></td></tr>";
			menuHtml+="</table>";
			menu = $(menuHtml);
			menu.css({opacity:0});
			menu.appendTo($("body"));
			menuBody = $("#"+randId);
			menuTop = $("#"+randId+"top");
			$(document).mousedown(function(){
				hide();
			});
			
		}
	}

	$.fn.popupEmbedMenu=function(menuItem){
		var obj = $(this);
		obj[0].menuItem = menuItem;
		init();
		
		var event = function(obj){
			var left = obj.offset().left;
			var top = obj.offset().top;
			var width = obj.width();
			var height = obj.height();
			
			var windowHeight = $(window).height();
			var windowWidth = $(window).width();

			menuBody.html("");
			var items = obj[0].menuItem;
			for(var i=0;i<items.length;i++){
				var itm = $("<div title='"+items[i].title+"' class='embed-item'>"+items[i].text+"</div>");
				itm[0].func = items[i].func;
				itm.appendTo(menuBody).click(function(){
					if(this.func){
						this.func(obj);
					}
					hide();
				});
			}
			
			var nleft = left+width/2-menu.width()+20;
			if(nleft<0){
				menuTop.removeClass("embed-menu-top").addClass("embed-menu-top-reverse");
				nleft = left+width/2-20;
			}else{
				menuTop.removeClass("embed-menu-top-reverse").addClass("embed-menu-top");
			}
			
			menu.css({left:nleft,top:top+height});
			show();
		}
		
		event(obj);
		window.event.cancelBubble = true;
	}

	function hide(){
		menu.animate({opacity:0},200,"swing",function(){
			menuBody.html("");
		});
		
	}

	function show(){
		menu.animate({opacity:1},200,"swing",function(){
			
		});
	}
})(jQuery);