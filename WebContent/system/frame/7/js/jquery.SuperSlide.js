$(document).ready(function(){
	//获取菜单
	getMenuList();
	
	var url = contextPath + "/personManager/queryOnlineUserCount.action?";
	tools.requestJsonRs(url,{},true,function(jsonRs){
		if(jsonRs.rtState){
			var data  = jsonRs.rtData;
			$("#onlineUserCount").html(data.onlineUserCount);
		}
	});
	
	
	//**************屏幕自适应
	function adjustWidthHeight() {
		//加载时适应浏览器高度
		var width = $(window).outerWidth(); 
		var height = $(window).outerHeight();
		$('#content').css('height', height - 50);
		$('.zm_box').css('height', height - 130 );
		$('.sidebar_nav').css('height', height - 130 );
		$('.two_menu').css('height', height - 91 );
		$('#tabs').css('width',width - 200 );
	}
	adjustWidthHeight();
	$(window).resize(function() {
		//改变窗体大小时适应浏览器高度
		adjustWidthHeight();
	});
	$(window).load(function(){
//		function moveRocket(){
//			$('.sign_in_tid').animate({'top':'+=4'},250)
//						.animate({'top':'-=4'},250)
//						setTimeout(moveRocket);
//		}
//		moveRocket();
	});
	
	//**************头部 开始
	//搜索
	$(".search").click(function (){
		//$(this).children(".container").show();
		$(".quick_search_div").toggle();
	});
	
	//组织架构
	$(".structure .cu").click(function (){
		$(this).next("ul").slideToggle("fast");
		$(this).parent(".options").toggleClass("on");
	});
	$(".sms").click(function(){
		$('.sign_in_tid').hide();
		smsAlert();
	});
	//**************头部 结束
	
	//**************左侧导航 开始
	//**************侧栏导航
		//侧栏导航展开收起
	var bOk=true;
	$('.sidebar_toggle').click(function(){
		if(bOk){
			$('.sidebar').animate({width:'54px'},500);
			$('.sidebar_nav').animate({width:'54px'},500);
			$(this).addClass('on');
			bOk=false;
		}else{
			$('.sidebar').animate({width:'170px',left:'0px'},500);
			$('.sidebar_nav').animate({width:'170px'},500);
			$(this).removeClass('on');
			bOk=true;
		}
		
	});
	 
	$(function(){
		var n_li = $('.sidebar_nav_ul').children('li');
		var aDiv = $('.sidebar_box').children('.two_menu');
		n_li.hover(function(){
			var i = n_li.index($(this));
			var top_1 = $(this).offset().top-80;
			var oH2 = aDiv.eq(i).outerHeight();
			var oH1 = $('.sidebar').outerHeight();
			function way(){
				n_li.removeClass("on").eq(i).addClass("on");
				aDiv.hide().eq(i).show().css('overflow','auto');
				$('.sidebar').css('overflow','inherit');
			};
			timer=setTimeout(way,200);
		},function(){
			clearTimeout(timer);
		});
		$(".two_menu").hover(function(){
			$(this).show(0);
			$(this).css('overflow','auto');
		},function(){
			$(this).delay(200).hide(0);
		});
		$(document).bind("mouseover",function(e){
			var target  = $(e.target);//表示当前对象，切记，如果没有e这个参数，即表示整个BODY对象
			if(target.closest(".sidebar_nav_ul,.two_menu").length == 0){
				$(".two_menu").hide();
				$('.sidebar_nav_ul').children('li').removeClass('on');
			}
		});
	});
	//侧栏上下滚动
	$('.sidebar_top').click(function(){
		$('.sidebar_nav_ul').animate({scrollTop:0},500); 
	});
	
	$('.sidebar_bottom').click(function(){
		$('.sidebar_nav_ul').animate({scrollTop:$('.sidebar_nav_ul')[0].offsetHeight},1000); 
	});
	//侧栏导航展开收起
	var bOk=true;
	$('.sidebar_spread').click(function(){
		if(bOk){
			$('#sidebar').animate({width:'60px',left:'-22px'},500);
			$('.sidebar_nav').animate({width:'80px'},500);
			$(this).addClass('out');
			bOk=false;
		}else{
			$('#sidebar').animate({width:'200px',left:'0px'},500);
			$('.sidebar_nav').animate({width:'200px'},500);
			$(this).removeClass('out');
			bOk=true;
		}
		
	});
	//三级菜单
	$(".submenu h6").click(function (){
		$(this).next(".three_menu").slideToggle("fast");
		$(this).parent(".submenu").siblings().children(".three_menu:visible").slideUp();
		$(this).parent(".submenu").toggleClass("on");
		$(this).parent(".submenu").siblings(".submenu").removeClass("on");
    });
	
	//**************左侧导航 结束
	//**************头部
	$(function(){
		$('.search').children('span').click(function(){
			//$(this).next('.search_content').show();
		});
	});
	$(document).bind("click",function(e){
			var target  = $(e.target);//表示当前对象，切记，如果没有e这个参数，即表示整个BODY对象
			if(target.closest(".search_content,.search").length == 0){
				$(".search_content").hide();
			}
	});
	//**************桌面
	$(function(){
		$('.modular').find('.toggle').click(function(){
			$('.sidebar').css('overflow','inherit');
			$(this).toggleClass('on');
			$(this).parent('.modular_hd').nextAll('.modular_bd').slideToggle();
		});
	});
	//人员信息
	$('.user_information').find('h3').click(function(){
		$(this).next('.user_content').toggle();
	});
	$(document).bind("click",function(e){
			var target  = $(e.target);//表示当前对象，切记，如果没有e这个参数，即表示整个BODY对象
			if(target.closest(".user_information").length == 0){
				$(".user_content").hide();
			}
	});
	//组织机构
	var aOk=true;
	$('.message>span').click(function(){
		if(aOk){
			$('#message_box').animate({right:'0px'},500);
			aOk=false;
		}else{
			$("#message_box").animate({right:'-240px'},500);
			aOk=true;
		}
		
	});
	$(document).bind("click",function(e){
			var target  = $(e.target);//表示当前对象，切记，如果没有e这个参数，即表示整个BODY对象
			if(target.closest(".message,#message_box").length == 0){
				$("#message_box").animate({right:'-240px'},500);
				aOk=true;
			}
	});
});

