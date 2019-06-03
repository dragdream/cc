<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%

	String status  = request.getParameter("status")==null?"-1":request.getParameter("status");
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
<title>任务管理</title>
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
</head>
<body>
	<header class="mui-bar mui-bar-nav">
			<a class="mui-pull-left app-title" href="#myPopover" id='statusDesc'>我的任务
				<span class="mui-icon mui-icon-arrowdown"></span>
			</a>
			<a class="mui-icon mui-icon-plusempty mui-pull-right" onclick="window.location = 'addOrUpdate.jsp';"></a>
		</header>
		<div class="mui-content">
			<!-- <div class="mui-content-padded" id="taskList" style="padding-bottom: 50px;">
				<center>
					<p class="app-tip">没有找到符合条件的记录！</p>
				</center>
			</div> -->
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
		   <li class="mui-table-view-cell" onclick="window.location = 'index.jsp';">所有任务</li>
		    <li class="mui-table-view-cell" onclick="window.location = 'index.jsp?status=0';">待接收任务</li>
		    <li class="mui-table-view-cell" onclick="window.location = 'index.jsp?status=1';">待审批任务</li>
		    <li class="mui-table-view-cell" onclick="window.location = 'index.jsp?status=4';">进行中任务</li>
		    <li class="mui-table-view-cell" onclick="window.location = 'index.jsp?status=5';">待审核任务</li>
		    <li class="mui-table-view-cell" onclick="window.location = 'index.jsp?status=8';">已完成任务</li>
		  </ul>
		</div>
		
		<script>
			var status = '<%=status%>';
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
				if(status=="0"){
					document.getElementById("statusDesc").innerHTML ="待接收任务<span class=\"mui-icon mui-icon-arrowdown\"></span>";
				}else if(status=="1"){
					document.getElementById("statusDesc").innerHTML ="待审批任务<span class=\"mui-icon mui-icon-arrowdown\"></span>";
				}else if(status=="2"){
					document.getElementById("statusDesc").innerHTML ="审批不通过任务<span class=\"mui-icon mui-icon-arrowdown\"></span>";
				}else if(status=="3"){
					document.getElementById("statusDesc").innerHTML ="拒绝接收任务<span class=\"mui-icon mui-icon-arrowdown\"></span>";
				}else if(status=="4"){
					document.getElementById("statusDesc").innerHTML ="进行中任务<span class=\"mui-icon mui-icon-arrowdown\"></span>";
				}else if(status=="5"){
					document.getElementById("statusDesc").innerHTML ="待审核任务<span class=\"mui-icon mui-icon-arrowdown\"></span>";
				}else if(status=="6"){
					document.getElementById("statusDesc").innerHTML ="审核不通过任务<span class=\"mui-icon mui-icon-arrowdown\"></span>";
				}else if(status=="7"){
					document.getElementById("statusDesc").innerHTML ="已撤销任务<span class=\"mui-icon mui-icon-arrowdown\"></span>";
				}else if(status=="8"){
					document.getElementById("statusDesc").innerHTML ="已完成任务<span class=\"mui-icon mui-icon-arrowdown\"></span>";
				}else if(status=="9"){
					document.getElementById("statusDesc").innerHTML = "已失败任务<span class=\"mui-icon mui-icon-arrowdown\"></span>";
				}else{
					document.getElementById("statusDesc").innerHTML = "所有任务<span class=\"mui-icon mui-icon-arrowdown\"></span>";
				}
				getList();
				//getMyTasks();
			})(mui);
			
			
			function mySchedule(){
				mui('.mui-popover').popover('toggle');
			}
			
			
			function getMyTasks(){
				var url = contextPath + '/coWork/datagrid.action?page=1&rows=100000000';
				mui.ajax(url,{
					type:"GET",
					dataType:"JSON",
					data:{status:status},
					timeout:10000,
					success:function(text){
						var json = eval("("+text+")");
						var data = json.rows;
						document.getElementById("taskList").innerHTML="";
						var html = "<ul class='mui-table-view'>";
						for(var i = 0 ;i<data.length;i++){
							var status = "";
							if(data[i].status==0){
								status="<span class=\"mui-badge mui-badge-primary\">等待接收</span>";
							}else if(data[i].status==1){
								status="<span class=\"mui-badge mui-badge-primary\">等待审批</span>";
							}else if(data[i].status==2){
								status="<span class=\"mui-badge mui-badge-danger\">审批不通过</span>";
							}else if(data[i].status==3){
								status="<span class=\"mui-badge mui-badge-danger\">拒绝接收</span>";
							}else if(data[i].status==4){
								status="<span class=\"mui-badge mui-badge-success\">进行中</span>";
							}else if(data[i].status==5){
								status="<span class=\"mui-badge mui-badge-purple\">提交审核</span>";
							}else if(data[i].status==6){
								status="<span class=\"mui-badge mui-badge-danger\">审核不通过</span>";
							}else if(data[i].status==7){
								status="<span class=\"mui-badge  mui-badge-danger\">任务撤销</span>";
							}else if(data[i].status==8){
								status="<span class=\"mui-badge mui-badge-warning\">已完成</span>";
							}else if(data[i].status==9){
								status="<span class=\"mui-badge mui-badge-danger\">任务失败</span>";
							}
						
							html+="<li class=\"mui-table-view-cell mui-media\">"
							+"<a href=\"detail.jsp?taskId="+data[i].sid+"\">"
							+"<div class=\"mui-media-object mui-pull-right\" >"
							+"<span>"+status+"</span>"
							+"</div>"
							+"<div class=\"mui-media-body\">"
							+ data[i].taskTitle
							+"	<p class='mui-ellipsis'><span>负责人："+data[i].chargerName+"</span>&nbsp;&nbsp;&nbsp;&nbsp;<span class=\"read\"></span></p>"
							+"</div>"
							+"</a>"
							+"</li>";
							
						}
						html+="</ul>";
						if(data.length<1){
							html="<center><p class=\"app-tip\">没有找到符合条件的记录！</p></center>";
						}
						document.getElementById("taskList").innerHTML+=html;
					},
					error:function(){
						
					}
				});
			}
			
			
			
			
			function getList(){
				var url = contextPath + "/coWork/datagrid.action?status="+status;
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
						if(item.status==0){
							status="<span class=\"mui-badge mui-badge-primary\">等待接收</span>";
						}else if(item.status==1){
							status="<span class=\"mui-badge mui-badge-primary\">等待审批</span>";
						}else if(item.status==2){
							status="<span class=\"mui-badge mui-badge-danger\">审批不通过</span>";
						}else if(item.status==3){
							status="<span class=\"mui-badge mui-badge-danger\">拒绝接收</span>";
						}else if(item.status==4){
							status="<span class=\"mui-badge mui-badge-success\">进行中</span>";
						}else if(item.status==5){
							status="<span class=\"mui-badge mui-badge-purple\">提交审核</span>";
						}else if(item.status==6){
							status="<span class=\"mui-badge mui-badge-danger\">审核不通过</span>";
						}else if(item.status==7){
							status="<span class=\"mui-badge  mui-badge-danger\">任务撤销</span>";
						}else if(item.status==8){
							status="<span class=\"mui-badge mui-badge-warning\">已完成</span>";
						}else if(item.status==9){
							status="<span class=\"mui-badge mui-badge-danger\">任务失败</span>";
						}
					
						html+="<li class=\"mui-table-view-cell mui-media\">"
						+"<a href=\"detail.jsp?taskId="+item.sid+"\">"
						+"<div class=\"mui-media-object mui-pull-right\" >"
						+"<span>"+status+"</span>"
						+"</div>"
						+"<div class=\"mui-media-body\">"
						+ item.taskTitle
						+"	<p class='mui-ellipsis'><span>负责人："+item.chargerName+"</span>&nbsp;&nbsp;&nbsp;&nbsp;<span class=\"read\"></span></p>"
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