<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="css/reset.css" />
<link rel="stylesheet" href="css/pto.css?v=5">
<%@ include file="/header/header2.0.jsp" %>
<title>快捷菜单页面</title>
</head>
<body onload="doInit();">

<div class="a">

	<ul class="b" id="menuUl">
	</ul>
</div>
<script type="text/javascript">

    //初始化方法
    function doInit(){
    	//获取当前登陆人所有的快捷菜单
    	var url=contextPath+"/personManager/getQuickMenus.action";
    	var json=tools.requestJsonRs(url);
    	if(json.rtState){
    		var data=json.rtData;
    		var html=[];
    		if(data!=null&&data.length>0){
    			for(var i=0;i<data.length;i++){
    				html.push("<li onclick=\"clickMenu('"+data[i].menuCode+"','"+data[i].menuName+"');\" class=\"c\" style=\"background: url('img/"+data[i].icon+"')\"><span class=\"d\">"+data[i].menuName+"</span></li>");
    			}
    		}
    		$("#menuUl").append(html.join(""));
    	}
    	
    	resizeFunc();
    }


    //点击菜单调用的方法
    function clickMenu(menuCode,menuName){
    	parent.parent.NewPageINfo(menuName,menuCode);
    }
    
    
	var clickMore = false;
	function resizeFunc(){
		//清空父元素中的“更多”的li
		$(".a li.more").remove();
		$(".a").css("overflow","hidden");
		var list = getClassNames('c' , 'li');
		function getClassNames(classStr,tagName){
			if (document.getElementsByClassName) {
				return document.getElementsByClassName(classStr)
			}else {
				var nodes = document.getElementsByTagName(tagName),ret = [];
				for(i = 0; i < nodes.length; i++) {
					if(hasClass(nodes[i],classStr)){
						ret.push(nodes[i])
					}
				}
				return ret;
			}
		}
		function hasClass(tagStr,classStr){
			var arr=tagStr.className.split(/\s+/ );  //这个正则表达式是因为class可以有多个,判断是否包含
			for (var i=0;i<arr.length;i++){
				if (arr[i]==classStr){
					return true ;
				}
			}
			return false ;
		}
		var heights='';
		var heights2='';
		var ccc=0;
		var hasOverflow = false;
		for(var i = 0; i < list.length; i++) {
			heights2=$('.c').eq(i).index();
			heights=$('.c').eq(i).height() + $('.c').eq(i).offset().top;
			list[i].index = i;
			//console.log( list[i].index + 1 + " " +heights);
			x =  $('.a').height();
			//console.log(xx);
			if( heights>x){
				ccc++;
				hasOverflow = true;

				$(".a").css("overflow","hidden");
			}
		};
		//console.log(ccc);
		//console.log(heights2 - ccc);

		//如果不存在更多按钮的话
		if($(".a li.more").length==0 && hasOverflow){
			//var obj = $("<li class='more c'><span class='d'>更多</span></li>");
			var obj = $("<li class='more c' style='background: url(css/gengduo.png)'><span class='d'>更多</span></li>");
			obj.click(function(){
				$(".a").css("overflow-y","scroll");
				clickMore = true;
				$(this).remove();
			});
			$(".a li").eq(heights2 - ccc).before(obj);
		}

	}

	$(window).resize(function(){
		if(!clickMore){
			resizeFunc();
		}
		
	});
	resizeFunc();



</script>
</body>

</html>