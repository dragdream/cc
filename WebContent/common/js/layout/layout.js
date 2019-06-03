(function(){
	var globalLayoutTimeFlag = 0;
	$.fn.layout = function(options,colsValue){
		var cur = this;
		
		if(typeof(options)=== "string"){
			execCommand(cur,options,colsValue);
		}else{
			if(!options) options={};
			var auto = options.auto;
			cur.attr("layoutcontainer",true);
			if(auto){
				cur.css({position:"absolute"});
				cur.css({'height':getClientHeight(),'width':getClientWidth()});
				renderChildsLayout(cur);
				$(window).resize(function(){
					if(globalLayoutTimeFlag==0){
						globalLayoutTimeFlag = 1;
						setTimeout(function(){
							cur.css({'height':getClientHeight(),'width':getClientWidth()});
							renderChildsLayout(cur);
							globalLayoutTimeFlag = 0;
						},100);
					}
				});
			}
			renderChildsLayout(cur);
		}
	}
	
	//获取视窗高度
	function getClientHeight(){
		var height = $(window).height();
		if(height==0){
			height = document.body.offsetHeight;
		}
        return height;
	}
	
	//获取视窗宽度
	function getClientWidth(){
		var width = $(window).width();
		if(width==0){
			width = document.body.offsetWidth;
		}
        return width;
	}
	
	//执行命令
	function execCommand(cur,options){
		var root = findRootLayoutDom(cur);
		
		if(options=="toggle"){
			var min = cur.attr("min");
			var max = cur.attr("max");
			var state = cur.attr("state");
			
			if(!state){
				state = "open";
			}
			
			var colsValue = 0;
			if(state=="open"){
				colsValue = min;
				cur.attr("state","close");
			}else{
				colsValue = max;
				cur.attr("state","open");
			}

			cur.css("z-index",1);

			if(cur.attr("layout")=="west"){
				cur.animate({"width":colsValue},300,function(){
					renderChildsLayout(root);
				});
			}else if(cur.attr("layout")=="north"){
				cur.animate({"height":colsValue},300,function(){
					renderChildsLayout(root);
				});
			}else if(cur.attr("layout")=="east"){
				var parent = cur.parent();
				cur.animate({"left":N(parent.css("width"))-colsValue,"width":colsValue},300,function(){
					renderChildsLayout(root);
				});
			}else if(cur.attr("layout")=="south"){
				var parent = cur.parent();
				cur.animate({"top":N(parent.css("height"))-colsValue,"height":colsValue},300,function(){
					renderChildsLayout(root);
				});
			}
		}
	}
	
	//始化可渲染边界
	function calculateRenderableBorder(cur){
		cur.attr('top',0);
		cur.attr('left',0);
		cur.attr('right',N(cur.css('width')));
		cur.attr('bottom',N(cur.css('height')));
		
		if(!cur.attr('width')){
			cur.attr('width',N(cur.css('width')));
		}
		if(!cur.attr('height')){
			cur.attr('height',N(cur.css('height')));
		}
		if(!cur.attr("min")){
			cur.attr("min",20);
		}
		if(!cur.attr("max")){
			if(cur.attr("layout")=="north" || cur.attr("layout")=="south"){
				cur.attr("max",N(cur.css('height')));
			}else{
				cur.attr("max",N(cur.css('width')));
			}
		}
	}
	
	//渲染子布局
	function renderChildsLayout(cur){
		calculateRenderableBorder(cur);
		var divs = cur.find('div[layout]');
		divs.each(function(i,obj){
			$(obj).css({position:'absolute'});
			renderChildsLayout0(cur,$(obj));
		});
		divs.each(function(i,obj){
			renderChildsLayout($(obj));
		});
	}
	//渲染子布局0
	function renderChildsLayout0(parent,node){
		var top = N(parent.attr('top'));
		var left = N(parent.attr('left'));
		var right = N(parent.attr('right'));
		var bottom = N(parent.attr('bottom'));
		
		var layout = node.attr('layout');
		var width = N(node.attr("width"));
		var height = N(node.attr("height"));
		

		if(layout=='north'){
			height==-1?100:height;
			node.css({'top':top,'left':left,'height':height-N(node.css("paddingTop"))-N(node.css("paddingBottom")),'width':right-left-N(node.css("paddingLeft"))-N(node.css("paddingRight"))});
			top+=height;
		}else if(layout=='south'){
			height==-1?100:height;
			node.css({'top':bottom-height,'left':left,'height':height-N(node.css("paddingTop"))-N(node.css("paddingBottom")),'width':right-left-N(node.css("paddingLeft"))-N(node.css("paddingRight"))});
			bottom-=height;
		}else if(layout=='west'){
			width==-1?100:width;
			node.css({'top':top,'left':left,'height':bottom-top-N(node.css("paddingTop"))-N(node.css("paddingBottom")),'width':width-N(node.css("paddingLeft"))-N(node.css("paddingRight"))});
			left+=width;
		}else if(layout=='east'){
			width==-1?100:width;
			node.css({'top':top,'left':right-width,'height':bottom-top-N(node.css("paddingTop"))-N(node.css("paddingBottom")),'width':width-N(node.css("paddingLeft"))-N(node.css("paddingRight"))});
			right-=width;
		}else if(layout=='center'){
			node.css({'top':top,'left':left,'height':bottom-top-N(node.css("paddingTop"))-N(node.css("paddingBottom")),'width':right-left-N(node.css("paddingLeft"))-N(node.css("paddingRight"))});
		}

		parent.attr({'top':top,'bottom':bottom,'left':left,'right':right});
	}

	function N(val){
		try{
			var o = parseInt(val);
			return (o+0)==o?o:-1;
		}catch(e){
			return -1;
		}
	}

	function findRootLayoutDom(obj){
		var top=obj;
		do{
			top=top.parent();
		}while(!top || !top.attr("layoutcontainer"));
		return top;
	}
})(jQuery);