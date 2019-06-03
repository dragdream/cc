<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>新闻</title>
<%@ include file="/system/mobile/header.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">

<link href="<%=mobilePath%>/phone/style/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=mobilePath %>/js/iscroll.js"></script>
<script type="text/javascript" src="<%=mobilePath %>/phone/js/index.js"></script>

<style type="text/css" media="all">
p{
	font-size: 12px;
}



/**
 *
 * Pull down styles
 *
 */
#pullDown, #pullUp {
	background:#fff;
	height:40px;
	line-height:40px;
	padding:5px 10px;
	border-bottom:1px solid #ccc;
	font-weight:bold;
	font-size:14px;
	color:#888;
}
#pullDown .pullDownIcon, #pullUp .pullUpIcon  {
	display:block; float:left;
	width:40px; height:40px;
	background:url(pull-icon@2x.png) 0 0 no-repeat;
	-webkit-background-size:40px 80px; background-size:40px 80px;
	-webkit-transition-property:-webkit-transform;
	-webkit-transition-duration:250ms;	
}
#pullDown .pullDownIcon {
	-webkit-transform:rotate(0deg) translateZ(0);
}
#pullUp .pullUpIcon  {
	-webkit-transform:rotate(-180deg) translateZ(0);
}

#pullDown.flip .pullDownIcon {
	-webkit-transform:rotate(-180deg) translateZ(0);
}

#pullUp.flip .pullUpIcon {
	-webkit-transform:rotate(0deg) translateZ(0);
}

#pullDown.loading .pullDownIcon, #pullUp.loading .pullUpIcon {
	background-position:0 100%;
	-webkit-transform:rotate(0deg) translateZ(0);
	-webkit-transition-duration:0ms;

	-webkit-animation-name:loading;
	-webkit-animation-duration:2s;
	-webkit-animation-iteration-count:infinite;
	-webkit-animation-timing-function:linear;
}

@-webkit-keyframes loading {
	from { -webkit-transform:rotate(0deg) translateZ(0); }
	to { -webkit-transform:rotate(360deg) translateZ(0); }
}

</style>

<script type="text/javascript">
/**
 * 页面加载是即可
 */
function doInit(){
	loadData(0);//加载数据
	
	//获取所有已发布的新闻
	var url = "<%=contextPath%>/mobileNewsContoller/getListNews.action";
	var param = {listNewsCount: 10 , state:-1};
	$.ajax({
	  type: 'POST',
	  url: url,
	  data: param,
	  timeout: 6000,
	  success: function(data){
		 // alert(data)
		  var dataJson = eval('(' + data + ')');
      	  var rtData = dataJson.rtData;
      	  var rtMsg = dataJson.rtMsg;
      	  var rtState = dataJson.rtState;
		  if(rtState){
			  if(rtData.length > 0){
				  $.each(rtData, function(index, item){  
					  
					/* 	private String provider1 = null;// 发布者
						private Date newsTime = null;// 发布时间
						private int clickCount = 0;// 点击数
						private String attachmentId = null;// 附件ID串(逗号分隔)
						private String attachmentName = null;// 附件名称串 */
					  var subject = "<span>" + item.subject  + "</span>";
					  var info = "<p style='width:90%'>" + item.provider1  + " &nbsp;" + item.newsTimeStr  + " &nbsp; 点击次数：" +  item.clickCount   + "</p>"; 
					  var attach = "<span class='attach'>附件</span>";
					  $("#thelist").append("<li id='"+item.sid+"'>"+subject+ info+  attach+  "<span class='span_bg'>></span></li>");
				   });
				
			  }
			 //初始化
			  loaded();
			 
		  }else{
			  
		  } 
	  },
	  error: function(xhr, type){
	    alert('服务器请求超时!');
	  }
	});
	
}

var myScroll,
	pullDownEl, pullDownOffset,
	pullUpEl, pullUpOffset,
	generatedCount = 0;

/**
 * 上方获取最新
 */
function pullDownAction () {
	setTimeout(function () {	// <-- Simulate network congestion, remove setTimeout from production!
		var el, li, i;
		el = document.getElementById('thelist');
		for (i=0; i<3; i++) {
			li = document.createElement('li');
			li.innerText = 'Generated row ' + (++generatedCount);
			el.insertBefore(li, el.childNodes[0]);
		}
		
		myScroll.refresh();		// Remember to refresh when contents are loaded (ie: on ajax completion)
	}, 1000);	// <-- Simulate network congestion, remove setTimeout from production!
}

function pullUpAction () {
	setTimeout(function () {	// <-- Simulate network congestion, remove setTimeout from production!
		var el, li, i;
		el = document.getElementById('thelist');

		for (i=0; i<3; i++) {
			li = document.createElement('li');
			li.innerText = 'Generated row ' + (++generatedCount);
			el.appendChild(li, el.childNodes[0]);
		}
		
		myScroll.refresh();		// Remember to refresh when contents are loaded (ie: on ajax completion)
	}, 1000);	// <-- Simulate network congestion, remove setTimeout from production!
}

/**
 * 加载数据
 */
function loaded() {
	pullDownEl = document.getElementById('pullDown');//往上拉获取最新数据.
	pullDownOffset = pullDownEl.offsetHeight;//获取高度
	pullUpEl = document.getElementById('pullUp');//往下拉获取更多数据	
	pullUpOffset = pullUpEl.offsetHeight;//获取高度
	
	myScroll = new iScroll('wrapper', {
		useTransition: true,
		topOffset: pullDownOffset,
		onRefresh: function () {
			if (pullDownEl.className.match('loading')) {//上方下拉获取数据后，触发事件
				pullDownEl.className = '';
				
				pullDownEl.querySelector('.pullDownLabel').innerHTML =  "已全部加载完毕";
				loadData(1);//加载数据完成隐藏
			} else if (pullUpEl.className.match('loading')) {//下方下拉获取数据后，触发事件
				pullUpEl.className = '';
				pullUpEl.querySelector('.pullUpLabel').innerHTML = '获取历史记录成功';
				loadData(1);//加载数据完成隐藏
			}
		},
		onScrollMove: function () {
			if (this.y > 5 && !pullDownEl.className.match('flip')) {
				pullDownEl.className = 'flip';
				pullDownEl.querySelector('.pullDownLabel').innerHTML = tenee_lang.pda["msg_5"];
				this.minScrollY = 0;
			} else if (this.y < 5 && pullDownEl.className.match('flip')) {
				pullDownEl.className = '';
				pullDownEl.querySelector('.pullDownLabel').innerHTML = 'Pull down to refresh...';
				this.minScrollY = -pullDownOffset;
			} else if (this.y < (this.maxScrollY - 5) && !pullUpEl.className.match('flip')) {
				pullUpEl.className = 'flip';
				pullUpEl.querySelector('.pullUpLabel').innerHTML = tenee_lang.pda["msg_5"];
				this.maxScrollY = this.maxScrollY;
			} else if (this.y > (this.maxScrollY + 5) && pullUpEl.className.match('flip')) {
				pullUpEl.className = '';
				pullUpEl.querySelector('.pullUpLabel').innerHTML = 'Pull up to load more...';
				this.maxScrollY = pullUpOffset;
			}
		},
		onScrollEnd: function () {
			
			if (pullDownEl.className.match('flip')) {//上面下拉，获取最新
				pullDownEl.className = 'loading';
				pullDownEl.querySelector('.pullDownLabel').innerHTML = tenee_lang.pda["msg_2"];	
				loadData(0);//加载数据
				pullDownAction();	// Execute custom function (ajax call?)加载数据
			} else if (pullUpEl.className.match('flip')) {//上面下来，获取之前历史记录
				pullUpEl.className = 'loading';
				pullUpEl.querySelector('.pullUpLabel').innerHTML = tenee_lang.pda["msg_2"];		
				loadData(0);//加载数据
				pullUpAction();	// Execute custom function (ajax call?)
	
			}
		}
	});
	document.getElementById('wrapper').style.left = '0';
	loadData(1);//加载数据完成隐藏  
	//绑定li 点击事件
	$("#thelist li").live("click", function(e){ 
		toNewInfo(this ,$(this).attr("id"));
	});
	 
	//setTimeout(function () { document.getElementById('wrapper').style.left = '0'; }, 10);
}
/**
 * 跳转至查看详情界面
 */
function toNewInfo(obj , id){
	window.location.href = mobilePath + "/phone/news/newsInfo.jsp?sid=" + id;
}
document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);

//document.addEventListener('DOMContentLoaded', function () { setTimeout(loaded, 200); }, false);
</script>


</head>
<body onload="doInit();">

<div class="header">
	<button type="button" class="button" value="返回首页"  onclick="goHome();" style="position:absolute;left:15px;margin-top:4px;">返回首页</button>
	
	<a href="javascript:void();">新闻</a>
</div>
<div id="wrapper">
	<div id="scroller">
		<div id="pullDown">
			<span class="pullDownIcon"></span><span class="pullDownLabel">下拉更新</span>
		</div>

		<ul id="thelist">
			
			<!-- <li>Pretty row 1</li> -->
	
		</ul>
		<div id="pullUp">
			<span class="pullUpIcon"></span><span class="pullUpLabel">下拉更新</span>
		</div>
	</div>
</div>
<div class="footer"></div>

</body>
</html>