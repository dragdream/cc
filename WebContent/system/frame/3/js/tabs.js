(function($){
	$.addTab=function(c1,c2,opts){
		var _c1 = $("#"+c1);
		var _c2 = $("#"+c2);
		if(opts instanceof Array){
			for(var i=0;i<opts.length;i++){
				renderTab(_c1,_c2,opts[i]);
			}
		}else{
			if(opts.active==undefined){
				opts.active = true;
			}
			var exist = tabsExist(_c1,opts);
			if(exist){//该菜单已经存在
				canceling(_c1);
				activing(exist);
			    //当前激活的菜单左侧被部分遮挡
				if($(".tee_tab_select").position().left<0){
					$("#nav_width_a").animate({scrollLeft:($(".tee_tab_select").index() * 111)},600)
				}
				//当前激活的菜单右侧被部分遮挡
				if($(".tee_tab_select").position().left >8 * 111 && $(".tee_tab_select").position().left <10 * 111){
					$("#nav_width_a").animate({scrollLeft:(($(".tee_tab_select").index()-1) * 111)},600)
				}
				return;
			}
			renderTab(_c1,_c2,opts);
		}
		renderComplete(_c1,_c2);
		initOutofBoundaryListener(_c1);
		initInnerContentWidth(_c1);
	};

	function renderTab(c1,c2,opts){
		var title = opts.title;
		var url = opts.url;
		var active = opts.active;
		var cache = opts.cache;
		var closable = opts.closable;	
		var outerTabContainer = c1[0].outerTabContainer;
		if(!outerTabContainer){//如果一个菜单都没有，重新创建盒子。此函数仅初始化时候执行一次
			var left = $("<div style='float:left;' class='tee_tab_pointer_left'><i class=\"glyphicon glyphicon-chevron-left\"></i></div>");
			var right = $("<div style='float:right;' class='tee_tab_pointer_right'><i class=\"glyphicon glyphicon-chevron-right\"></i></div>");
			c1[0].tab_left = left[0];
			c1[0].tab_right = right[0];
			var center = $("<div id='nav_width_a' style='overflow:hidden;position:relative;border:white 1px solid;border-radius:4px;'></div>");
			c1.append(left).append(right).append(center);
			c1[0].tab_center = center[0];	
			outerTabContainer = $("<div id='nav_width' style='min-width:3600px;'></div>");
			c1[0].outerTabContainer = outerTabContainer[0];
			center.append(outerTabContainer);

			//注册按钮事件，注意这里是注册到全局，外部可触发
			left.click(function(){
				var innerContentWidth = c1[0].innerContentWidth;
				var delta = center.width();
				var scrollLeftDelta = center.scrollLeft()-delta;
				scrollLeftDelta = scrollLeftDelta<=0?0:scrollLeftDelta;
				center.animate({scrollLeft:scrollLeftDelta},600);
			});
			
			right.click(function nav_right(){
				var innerContentWidth = c1[0].innerContentWidth;
				var delta = center.width();
				var scrollLeftDelta = center.scrollLeft()+delta;
				scrollLeftDelta = scrollLeftDelta>=innerContentWidth?innerContentWidth-delta:scrollLeftDelta;
				center.animate({scrollLeft:scrollLeftDelta},600);
			});
		}
		$("#nav_width_a").animate({scrollLeft:($("#nav_width").children().length * 111)},10);
		var tab = $("<div class='tee_tab'>"+title+"</div>");
		var closeBtn = $("<i class='tee_close glyphicon'></i>").hide();
		tab.append(closeBtn);
		tab[0].url = url;
		tab[0]._title = title;
		tab[0].active = active;
		tab[0].cache = cache;
		tab[0].closable = closable;
		var tabContent = $("<iframe style='height:100%;width:100%;dispaly:none;' frameborder=0></iframe>");
		tab[0].tabContent = tabContent[0];
		c2.append(tabContent);
		$(outerTabContainer).append(tab);
//鼠标上去显示关闭按钮
		tab.hover(function(){
			if(closable){
				closeBtn.show();
			}
		},function(){
			if(closable){
				closeBtn.hide();
			}
		});
//点击tab显示对应的区域	
		tab.click(function(){
			canceling(c1);
			activing(tab);
			if($(".tee_tab_select").position().left<0){
				$("#nav_width_a").animate({scrollLeft:($(".tee_tab_select").index() * 111)},600)
			}
			if($(".tee_tab_select").position().left >8 * 111 && $(".tee_tab_select").position().left <10 * 111){
				$("#nav_width_a").animate({scrollLeft:(($(".tee_tab_select").index()-1) * 111)},600)
			}
			if(closable){
				closeBtn.show();
			}
		});
//注测，关闭按钮相关操作		
		closeBtn.click(function(){
				if($(".tee_tab").hasClass("tee_tab_select")){ // 一般情况下都是有这个类的
					if(tab.index() == $(".tee_tab_select").index()){ // 如果关闭的是激活菜单，关闭后激活前面或者后面的
						var preTab = tab.prev();
						var nextTab = tab.next();
						if(preTab.length!=0){
							activing(preTab);
						}else{
							if(nextTab.length!=0){
								activing(nextTab);
							}
						}
						tab.remove();
						tabContent.remove();
						initInnerContentWidth(c1);
					} else {//关闭的是非激活菜单
					tab.remove();
					tabContent.remove();
					initInnerContentWidth(c1);
					//阻止冒泡
					if(window.event){//IE下阻止冒泡
						event.cancelBubble  = true;
					}else{
						event.stopPropagation();
					}
				}
				} 
			// var preTab = tab.prev();
			// if(preTab.length!=0){
			// 	activing(preTab);
			// }else{
			// 	var nextTab = tab.next();
			// 	if(nextTab.length!=0){
			// 		activing(nextTab);
			// 	}
			// }
			// tab.remove();
			// tabContent.remove();
			// initInnerContentWidth(c1);
		});
		
		if(active){
			$($(tab)[0].tabContent).attr("src",$(tab)[0].url);
			canceling(c1);
			activing(tab);
		}
	}
	
//筛选下一个要出现的tab和区域
	function activing(tab){
		$($(tab)[0].tabContent).show();
		if(!$(tab)[0].active || !$(tab)[0].cache){
			$($(tab)[0].tabContent).attr("src",$(tab)[0].url);
		}
		$(tab).addClass("tee_tab_select");
		$(tab)[0].active = true;
		if($(tab)[0].closable){
			$(tab).find(".tee_close:first").show();
		}
	}

	//关闭点击按钮的tab和对应的区域
	function canceling(c1){
		$(c1[0].outerTabContainer).children().each(function(i,obj){
			$(obj.tabContent).hide();
				$(obj).removeClass("tee_tab_select");
		});
		$(c1[0].outerTabContainer).find(".tee_close").each(function(i,obj){
			$(obj).hide();
		});
	}
	function renderComplete(c1,c2){
		var lastActivedTab;
		$(c1[0].outerTabContainer).children().each(function(i,obj){
			if(obj.active){
				lastActivedTab = $(obj);
			}
			$(obj.tabContent).hide();
		});
		if(lastActivedTab){
			$(lastActivedTab[0].tabContent).show();
		}
	}

	function initOutofBoundaryListener(c1){
		if(!c1[0].listener){
			setInterval(function(){
				floatCtrlButtonDetach(c1);
			},1000);
			c1[0].listener = true;
		}
		floatCtrlButtonDetach(c1);
	}

	function floatCtrlButtonDetach(c1){
		var outerTabContainer = $(c1[0].outerTabContainer);
		var contentDelta = 0;
		var left = $(c1[0].tab_left);
		var right = $(c1[0].tab_right);
		contentDelta = c1[0].innerContentWidth;
		if(contentDelta>c1.outerWidth()){
			left.show();
			right.show();
		}else{
			left.hide();
			right.hide();
			$(c1[0].tab_center).scrollLeft(0);
		}
	}

	function initInnerContentWidth(c1){
		var outerTabContainer = $(c1[0].outerTabContainer);
		var contentDelta = 0;
		outerTabContainer.children().each(function(i,obj){
			contentDelta += N($(obj).outerWidth())+N($(obj).css("marginLeft"))+N($(obj).css("marginRight"));
		});
		c1[0].innerContentWidth = contentDelta;
	}

	function N(val){
		try{
			var o = parseInt(val);
			return (o+0)==o?o:-1;
		}catch(e){
			return -1;
		}
	}
	
	//该标签是否存在
	function tabsExist(_c1,opts){
		var exist;
		$(_c1[0].outerTabContainer).children().each(function(i,obj){
			if(obj._title==opts.title){
				
				exist = $(obj);
			}
		});
		return exist;
	};
	
function sunWidth(){ //取头部导航滚动宽度总和 函数
				var w1 = 0;
				$("#nav_width").children("div").each(function(i){
				 w1 += $(this).outerWidth(true);
				});
				return w1;
		};

$(document).ready(function(){
	$('.sidebar_box').on('click', 'a', function () {
		$("#nav_width_a").animate({scrollLeft:sunWidth()},10);
	});
});
})(jQuery);



