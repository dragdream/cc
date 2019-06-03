/*
 *  SliderNav - A Simple Content Slider with a Navigation Bar
 *  Copyright 2010 Monjurul Dolon, http://mdolon.com/
 *  Download by http://www.codefans.net
 *  Released under the MIT, BSD, and GPL Licenses.
 *  More information: http://devgrow.com/slidernav
 */
$.fn.sliderNav = function(options) {
	var defaults = { items: ["a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"], debug: false, height: null, arrows: true};
	var opts = $.extend(defaults, options); 
	var o = $.meta ? $.extend({}, opts, $$.data()) : opts;
	var slider = $(this); 
	$(slider).addClass('slider');
	
	$('.slider-content li:first', slider).addClass('selected');
	$("div").remove(".slider-nav");//先删除 字母导航
	$("table").remove("#titleTable");//先删除标题
	$("div").remove(".slide-up");//先删除 上移动
	$("div").remove(".slide-down");//先删除 下移动
	$("div").remove("#debug");//先删除BUG
	$(slider).append('<div class="slider-nav"><ul></ul></div>');
	
	//$('.slider-nav').insertBefore(".slider-content");
	
	for(var i in o.items){
		var  tempItems = o.items[i] ;
		if(tempItems == "#"){//处理#号特殊字符串
			tempItems = "AA";
		}
		$('.slider-nav ul', slider).append("<li><a alt='#"+tempItems+"'>"+o.items[i]+"</a></li>");

	} 
	var height = $('.slider-nav', slider).height();
	if(o.items.length > o.height/23){ //当a-z字母过多，高度是否被超过，如果超过则不设置固定高度，自动滚动条
		o.height = 0;
	}
	if(o.height) height = o.height;
	$('.slider-content, .slider-nav', slider).css('height',height+40);
	if(o.debug) $(slider).append('<div id="debug">Scroll Offset: <span>0</span></div>');
	$('.slider-nav a', slider).mousedown(function(event){
		var target = $(this).attr('alt');
		var cOffset = $('.slider-content', slider).offset().top;
		var tOffsetObj = $('.slider-content '+target, slider).offset();
		var tOffset ;
		if(tOffsetObj ){
			 tOffset = $('.slider-content '+target, slider).offset().top;
		}else{
			return ;
		}
		
		var height = $('.slider-nav', slider).height(); if(o.height) height = o.height;
		var pScroll = (tOffset - cOffset) - height/8;
		$('.slider-content li', slider).removeClass('selected');
		$(target).addClass('selected');
		$('.slider-content', slider).stop().animate({scrollTop: '+=' + pScroll + 'px'});
		if(o.debug) $('#debug span', slider).html(tOffset);
	});
	if(o.arrows){
		$('.slider-nav',slider).css('top','0px');

		$(slider).prepend('<table class="" id="titleTable" width="100%" style="color:#FFF;font-size:16px;background:#888 none repeat scroll 0% 0%"><tr>'
//			+'<td style="width:8%;padding:8px 10px;" nowrap>姓名</td>'
//			+'<td style="width:16%;" nowrap>部门</td>'	
//			+'<td style="width:8%;" nowrap>职务</td>'
//			+'<td style="width:5%;" nowrap>性别</td>'	
//			+'<td style="width:12%;" nowrap>工作电话</td>'	
//			+'<td style="width:12%;" nowrap>手机 </td>'	
//			+'<td style="width:18%;" nowrap>邮箱 </td>'	
//			+'<td style="width:18%;" nowrap>家庭住址</td> '
		+'</tr></table>');
	/*	$(slider).prepend('<div class="slide-up end"><span class="arrow up"></span></div>');
		
		$(slider).append('<div class="slide-down"><span class="arrow down"></span></div>');
	*/
		$('.slide-down',slider).click(function(){
			$('.slider-content',slider).animate({scrollTop : "+="+height+"px"}, 500);
		});
		$('.slide-up',slider).click(function(){
			$('.slider-content',slider).animate({scrollTop : "-="+height+"px"}, 500);
		});
	}
};