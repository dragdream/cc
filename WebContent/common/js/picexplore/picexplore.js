(function(){
	var target;
	var panel;
	var closeBtn;
	var draggable;
	var oOverflowX;
	var oOverflowY;
	var toolbar;
	var scrollTop;
	$.picExplore=function(opts){
		scrollTop = $(window).scrollTop();
		document.onselectstart = function(){return false;};
		var src = opts.src;
		
		panel = $("<div class=\"picexplore_panel\"></div>").css({opacity:0,top:($(document).scrollTop()),width:$(document).width(),height:$(window).height()});
		target = $("<img class=\"picexplore_img\" src=\""+src+"\"/>");
		panel.appendTo($("body"));
		target.appendTo(panel).css({opacity:0});
		
		//渲染关闭按钮
		closeBtn = $("<div class='picexplore_close_btn'></div>").appendTo($("body"));
		
		
		target[0].onload=function(){
			
			var width = target.width();
			var height = target.height();
			
			var screenHeight = $(panel).height();
			var screenWidth = $(panel).width();
			//w/W=h/H
			if(height>screenHeight){
				height = screenHeight*3/4;
				target.css({height:height});
			}else if(width>screenWidth){
				width = screenWidth*3/4;
				target.css({width:width});
			}
			width = target.width();
			height = target.height();
			
			target.css({height:0,width:0,left:screenWidth/2,top:screenHeight/2});
			target.animate({left:(screenWidth-width)/2,
				top:(screenHeight-height)/2,opacity:1,width:width,
				height:height},500);
		}
		
		target.mousedown(function(event){
			panel.blur();
			target.data("offsetX",event.pageX-target.offset().left);
			target.data("offsetY",scrollTop+event.pageY-target.offset().top);
			draggable = true;
		}).mouseup(function(){
			draggable = false;
		}).mousewheel(function(event, delta){
			if(delta>0){
				zoomUp(event);
			}else{
				zoomDown(event);
			}
		});
		target[0].onmousemove = function(){return false;}
		target[0].onclick=function(){
			window.event.cancelBubble = true;
		}

		panel.click(function(){
			panel.animate({opacity:0},300,function(){
			$("body").css({"overflowX":oOverflowX});
			$("body").css({"overflowY":oOverflowY});
				panel.remove();
				closeBtn.remove();
			});
		});	
		
		closeBtn.click(function(){
			panel.animate({opacity:0},300,function(){
			$("body").css({"overflowX":oOverflowX});
			$("body").css({"overflowY":oOverflowY});
				panel.remove();
				closeBtn.remove();
			});
		});	
		
		panel.animate({opacity:1},500);
		oOverflowX = $("body").css("overflowX");
		oOverflowY = $("body").css("overflowY");
		$("body").css({overflowX:"hidden",overflowY:"hidden"});
	}

	function zoomUp(event){
		var width = parseInt(target.css("width"));
		var height = parseInt(target.css("height"));
		var centerX = target.position().left+width/2;
		var centerY = target.position().top+height/2;
		var delta = width/height;
		width +=50;
		height = width/delta;
		target.css({left:centerX-width/2,
						top:centerY-height/2,
						width:width,height:height});
	}

	function zoomDown(event){
		var width = parseInt(target.css("width"));
		var height = parseInt(target.css("height"));
		var centerX = target.position().left+width/2;
		var centerY = target.position().top+height/2;
		var delta = width/height;
		width-=50;
		height = width/delta;
		target.css({left:centerX-width/2,
			top:centerY-height/2,
			width:width,height:height});
	}

	$(document).mousemove(function(event){
		if(draggable){//
			target.css({left:event.pageX-target.data("offsetX"),top:event.pageY-target.data("offsetY")});
		}
	}).mouseup(function(){
		draggable = false;
	});
	
	$(window).resize(function(){
		$(panel).css({height:$(this).height(),width:$(this).width()});
		setTimeout(function(){
			if(target){
				var width = target.width();
				var height = target.height();
				var screenHeight = $(panel).height();
				var screenWidth = $(panel).width();
				target.animate({left:(screenWidth-width)/2,top:(screenHeight-height)/2},100);
			}
		},300);
	});

})(jQuery);
