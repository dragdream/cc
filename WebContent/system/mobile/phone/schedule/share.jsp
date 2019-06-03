<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String ten_lang_msg_1 = "暂无更多信息";
	String ten_lang_msg_2 = "加载数据中...";
	String ten_lang_msg_3 = "页面加载错误";
	String ten_lang_msg_4 = "上拉刷新...";
	String ten_lang_msg_5 = "下拉刷新...";
	String ten_lang_msg_6 = "释放立即刷新...";
	String ten_lang_msg_7 = "上拉加载更多...";
	String ten_lang_msg_8 = "下拉加载更多...";
	String ten_lang_msg_9 = "释放加载更多...";
	String ten_lang_msg_10 = "已全部加载完毕";
	String ten_lang_msg_11 = "读取附件中...";

%>
<!DOCTYPE HTML>
<html>
<head>
<title>计划</title>
<%@ include file="/system/mobile/mui/header.jsp" %>
<script type="text/javascript" src="<%=contextPath %>/system/mobile/js/iscroll.js"></script>
<script type="text/javascript" src="<%=contextPath %>/system/mobile/phone/js/index.js"></script>
<script type="text/javascript" src="<%=contextPath %>/system/mobile/phone/js/listview.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/lazyloader.js"></script>
<style>

/* 列表通用样式 */
#wrapper {
	position:absolute; z-index:1;
	top:45px; bottom:48px; left:-9999px;
	width:100%;
	overflow:auto;
}

#scroller {
	position:absolute; z-index:1;
/*	-webkit-touch-callout:none;*/
	-webkit-tap-highlight-color:rgba(0,0,0,0);
	width:100%;
	padding:0;
}


#scroller li span.span_bg{
	position:absolute;
	right: 5px;
	top:0px;
	height:50px;
	line-height:50px;
	width:15px;
	display: block;
	left: auto;
	/* left: auto;
	top:20px;
	padding:12px 0px 12px 0px;
	display: block; */
	
}

#scroller li .list_ellipsis{
	text-overflow:ellipsis; overflow:hidden; white-space:nowrap; 
	width:230px; 
/* 	background:#ccc; */
}

#scroller li span.attach{
	position:absolute;
	right: 20px;
	top:0px;
	height:50px;
	line-height:50px;
	width:25px;
	display: block;
	left: auto;
/* 	left: auto;
	display: block;
	padding:12px 0px 12px 0px; */

}


</style>
<style>
.mui-icon-chatboxes{
font-size:13px;
}
.read{
color:green;
}
.no-read{
color:orange;
}
</style>
</head>
<body>
	<header class="mui-bar mui-bar-nav">
			<a class="mui-pull-left app-title" href="#myPopover">共享计划
				<span class="mui-icon mui-icon-arrowdown"></span>
			</a>
		</header>
		<div class="mui-content" id="scheduleList">
			<div id="wrapper">
				<div id="scroller">
					<div id="pullDown" style='text-align:center;'>
						<span class="pullDownIcon"></span><span class="pullDownLabel app-tip"><%=ten_lang_msg_5 %></span>
					</div>
			
					<ul class='mui-table-view' id="thelist">
						
				
					</ul>
					<div id="pullUp" style='text-align:center;'>
						<span class="pullUpIcon"></span><span class="pullUpLabel app-tip"><%=ten_lang_msg_4 %></span>
					</div>
				</div>
			</div>
		</div>
		
		<div id="myPopover" class="mui-popover" style="width:120px">
		  <ul class="mui-table-view">
		    <li class="mui-table-view-cell" onclick="window.location = 'index.jsp';">我的计划</li>
		    <li class="mui-table-view-cell" onclick="window.location = 'share.jsp';">共享计划</li>
		    <li class="mui-table-view-cell" onclick="window.location = 'piyue.jsp';">批阅计划</li>
		  </ul>
		</div>
		
		<script>
			var ten_lang = {};
			ten_lang.pda = {
	            msg_1:'<%=ten_lang_msg_1%>',
	            msg_2:'<%=ten_lang_msg_2%>',
	            msg_3:'<%=ten_lang_msg_3%>',
	            msg_4:'<%=ten_lang_msg_4%>',
	            msg_5:'<%=ten_lang_msg_5%>',
	            msg_6:'<%=ten_lang_msg_6%>',
	            msg_7:'<%=ten_lang_msg_7%>',
	            msg_8:'<%=ten_lang_msg_8%>',
	            msg_9:'<%=ten_lang_msg_9%>',
	            msg_10:'<%=ten_lang_msg_10%>',
	            msg_11:'<%=ten_lang_msg_11%>'
	         };
	         
				var loader;
				var currOptType = 0;
				var isFirstClick = true;
				var ss = false;
			(function($) {
				//启动加载完毕的逻辑	
				getList();
				//getShareSchedules();
			})(mui);
			
			function detail(){
				window.location = "detail.jsp";
			}
			function getShareSchedules(){
				var url = contextPath+"/scheduleShared/getShareSchedules.action";
				mui.ajax(url,{
					type:"GET",
					dataType:"JSON",
					data:{},
					timeout:10000,
					success:function(text){
						var json = eval("("+text+")");
						if(json.rtState){
							var data = json.rtData;
							document.getElementById("scheduleList").innerHTML="";
							var html = "<ul class='mui-table-view'>";
							for(var i = 0 ;i<data.length;i++){
								var status = "";
								if(data[i].scheduleFlag==0){
									status="<span class=\"mui-badge mui-badge-success\">进行中</span>";
								}else if(data[i].scheduleFlag==1){
									status="<span class=\"mui-badge mui-badge-danger\">已完成</span>";
								}else{
									status="<span class=\"mui-badge mui-badge-warning\">超时完成</span>";
								}
								html+="<li class=\"mui-table-view-cell mui-media\">"
								+"<a href=\"detail.jsp?scheduleId="+data[i].scheduleId+"\">"
								+"<div class=\"mui-media-object mui-pull-right\" >"
								+"<span>"+status+"</span>"
								+"</div>"
								+"<div class=\"mui-media-body\">"
								+ data[i].scheduleName
								+"	<p class='mui-ellipsis'><span>"+data[i].scheduleTime+"</span>&nbsp;&nbsp;&nbsp;&nbsp;<span class=\"read\"></span></p>"
								+"</div>"
								+"</a>"
								+"</li>";
								
							}
							html+"</ul>";
							document.getElementById("scheduleList").innerHTML+=html;
						}
					},
					error:function(){
						
					}
				});
			}
			function getList(){
				var url = contextPath+"/scheduleShared/datagrid.action";
				loader = null;

				isFirstClick = false;
				$("#thelist").empty();
				
				$("#scroller").attr("style","");
				loader = new lazyLoader({
					url:url,
					placeHolder:"",
					contentHolder:'thelist',
					param:{state:-1},
					pageSize:10,//初始化分页大小
					rowRender:function(item){
						var status = "";
						var html="";
						if(item.scheduleFlag==0){
							status="<span class=\"mui-badge mui-badge-success\">进行中</span>";
						}else if(item.scheduleFlag==1){
							status="<span class=\"mui-badge mui-badge-danger\">已完成</span>";
						}else{
							status="<span class=\"mui-badge mui-badge-warning\">超时完成</span>";
						}
						html+="<li class=\"mui-table-view-cell mui-media\">"
						+"<a href=\"detail.jsp?scheduleId="+item.scheduleId+"\">"
						+"<div class=\"mui-media-object mui-pull-right\" >"
						+"<span>"+status+"</span>"
						+"</div>"
						+"<div class=\"mui-media-body\">"
						+ item.scheduleName
						+"	<p class='mui-ellipsis'><span>"+item.scheduleTime+"</span>&nbsp;&nbsp;&nbsp;&nbsp;<span class=\"read\"></span></p>"
						+"</div>"
						+"</a>"
						+"</li>";
						return html;
					},
					onNoData:function(){
						$(".pullUpLabel").html("无更多数据!");
					},
					onLoadSuccess:function(){
						myScroll.refresh();
					}
				});
				loaded();
				
			}
			/**
			 * 上方获取最新
			 */
			function pullDownAction () {
				setTimeout(function () {
					loader.reload();
					myScroll.refresh();
				}, 500);
			}

			function pullUpAction () {
				setTimeout(function () {
					loader.load();
					myScroll.refresh();
				}, 1000);
			}
		</script>
</body>
</html>