$(document).ready(function(){
	//获取菜单
	getMenuList();
	//获取皮肤
	getSkinFunc();
	//获取提前或延后考核时间
	getTimesTips();
	//获取当前用户当天已经签到的次数（从1至6）
	getRegisterStatue();
	
	//获取考核时间段
	getDutyConfigByUser();
	
	//显示时间
	showTimeFunc();
	//**************屏幕自适应
	function adjustWidthHeight() {
	//加载时适应浏览器高度
	var width = $(window).outerWidth(); 
    var height = $(window).outerHeight();
	$('#content').css('height', height - 88);
	$('.two_menu').css('height', height - 144);
	$('.sidebar_nav').css('height', height - 160);
	$('.sidebar_ul').css('height', height - 180);
	$('.zm_box').css('height', height - 88 );
	$(".structure .container").css("height", height-88);
	if(width>1000){
		$(".header_nav").css("width",width - 600);
	}else{
		$(".header_nav").css("width",400);
	}
	
	$(".hads").css("width",width - 750);
	}
	adjustWidthHeight();
	$(window).resize(function() {
	//改变窗体大小时适应浏览器高度
	adjustWidthHeight();
	});
	
	//**************头部 开始
	//搜索
	$(".search").click(function (){
		$(".quick_search_div").toggle();
	});
	//签到&&换肤
	$(".sign_in p,.skin_peeler p").click(function (){
		$(this).next(".container").slideToggle();
		$(this).parent().siblings('div').children('.container').slideUp();
	});
	$(".structure p").click(function (){
		if($(this).attr("hd")==undefined){
			$(this).next(".container").show().animate({"right":0});
			$(this).attr("hd","");
		}else{
			$(this).next(".container").show().animate({"right":-240});
			$(this).removeAttr("hd");
		}
		
		$(this).parent().siblings('div').children('.container').slideUp();
	});
	
	//组织架构
	$(".structure .cu").click(function (){
		$(this).next("ul").slideToggle("fast");
		$(this).parent(".options").toggleClass("on");
	});
	//换肤
	$(".skin_peeler li").click(function (){
		$(".skin_peeler li").removeClass("on");
		$(this).addClass("on");
	});
	$(".skin_peeler li.blue").click(function (){
		$("#header,#tabs,#footer,.sidebar_nav").removeClass("green").removeClass("orange");
		setCookie("skinChange" , "blue" , 365);
		setCookie("skin_new" , "1" , 365);
	});
	$(".skin_peeler li.orange").click(function (){
		$("#header,#tabs,#footer,.sidebar_nav").removeClass("green").addClass("orange");
		setCookie("skinChange" , "orange" , 365);
		setCookie("skin_new" , "0" , 365);
	});
	$(".skin_peeler li.green").click(function (){
		$("#header,#tabs,#footer,.sidebar_nav").removeClass("orange").addClass("green");
		setCookie("skinChange" , "green" , 365);
		setCookie("skin_new" , "2" , 365);
	});
	$(".sms").click(function(){
		$('.sign_in_tid').hide();
		smsAlert();
	});
		
	//**************头部 结束
	
	//**************左侧导航 开始
	//左侧导航展开收起
	$(".start_up").click(function (){
		$(".sidebar").toggleClass("sidebar_w");
		var className = $("#sidebar").attr("class");
		setCookie("className",className);
    });
	//二级菜单鼠标滑过显示隐藏
				
				
				$(function(){
				var t_li = $('.sidebar_ul').children('li')
				var c_li = $('.sidebar_nav .bd').children('.two_menu')
				t_li.hover(function(){
				var i = t_li.index($(this));
				function way(){
				t_li.removeClass("on").eq(i).addClass("on");
				var childs = c_li.hide().eq(i).find("li");
				if(childs.length>0){
					c_li.hide().eq(i).show();
				}
				}
				timer=setTimeout(way,100);
				},function(){
				clearTimeout(timer);
				});
				});
				
				$(".two_menu").hover(function(){
					$(this).show(0);
				},function(){
					$(this).delay(100).hide(0);
				});
	//阻止事件冒泡
	$(".n14 a,.n15 a").mouseenter(function (e){
		e.stopPropagation();
    });
	//三级菜单
//	$(".two_menu .cu p").click(function (){
//		$(this).next(".three_menu").slideToggle("fast");
//		$(this).parent(".cu").siblings().children(".three_menu:visible").slideUp();
//		$(this).parent(".cu").toggleClass("current");
//		$(this).parent(".cu").siblings("li").removeClass("current");
//    });
	
	//**************左侧导航 结束
	
		$(".downward").click(function (){
			$(".sidebar_ul").animate({"scrollTop":$(".sidebar_ul").scrollTop()-200});	
		});
	
		$(".upward").click(function (){
			$(".sidebar_ul").animate({"scrollTop":$(".sidebar_ul").scrollTop()+200});
		});
		//********************** 签到提示
		$(window).load(function(){
//			function moveRocket(){
//				$('.sign_in_tid').animate({'top':'+=4'},250)
//							.animate({'top':'-=4'},250)
//							setTimeout(moveRocket);
//			}
//			moveRocket();
		});
	$(".sign_in p").click(function (){
		$(".sign_in_tid").stop().hide();
	});
});

