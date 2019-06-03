<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>竞争对手管理</title>
<%@ include file="/system/mobile/mui/header.jsp" %>
<script type="text/javascript" src="<%=contextPath %>/system/mobile/superRaytinpaginationjs/dist/pagination-with-styles.js?v=1"></script>
</head>
<body>
	<header class="mui-bar mui-bar-nav">
		<a class="mui-pull-left app-title" href="#">竞争对手管理
		</a>
		
		<button class="mui-btn mui-btn-link mui-btn-nav mui-pull-right" onclick="returnBack();">
		    <span class="mui-icon mui-icon-left-nav"></span>返回
		</button>
	</header>
	<div class="mui-content" id="dataList" >
		<div class="mui-content-padded" id="" style="padding-bottom: 50px;;">
			<center>
				<p class="app-tip">无相关数据！</p>
			</center>
		</div>
		<ul class="mui-table-view" style="display: none;">
			<li class="mui-table-view-cell mui-media" onclick="">
				<a href="#">
					<div class="mui-media-body">
						title
						<p class='mui-ellipsis'>
							<span>2015-08-10 15:01:00</span>
						</p>
					</div>
				</a>
			</li>
		</ul>
		
	</div>
<div id="pagination-demo1" class="app-pagination"></div>
<script>
$(function(){
	getPageList();
});

function mySchedule(){
	mui('.mui-popover').popover('toggle');
}

/**
 * 获取分页数据
 */
function getPageList(){
	$('#pagination-demo1').pagination({
		dataSource: contextPath+'/teeCrmCompetitorController/manager.action',
		pageSize: 20,
		callback: function(data){
			//Alert(data.rows);
			var list = data.rows;
			if(list.length>0){
				document.getElementById("dataList").innerHTML="";
				var html = "<ul class='mui-table-view'>";
				 jQuery.each(list,function(i,sysPara){
					html+="<li class=\"mui-table-view-cell mui-media\" onclick=\"showInfoFunc('" + sysPara.sid + "');\" >"
							+"<a href=\"#\">"
							+"	<div class=\"mui-media-body\" >"
							+		"公司 ：" + sysPara.company 
							+"		<p class='mui-ellipsis'>"
							+"			<span>地址：" + sysPara.companyAddress + "</span>"
							+"		</p>"
							+"	</div>"
							+"</a>"
							+"</li>";
				});
				html+="</ul>";
				document.getElementById("dataList").innerHTML=html;
			}
		}
	});
}

/**
 * 完成情况
 * @param value
 * @returns {String}
 */
function handleStatusFunc(value){
	if(value =="0"){
		value = "未完成";
	}else{
		value = "已完成";
	}
	return value;
}

//详情界面
function showInfoFunc(sid){
	var  url = contextPath + "/system/mobile/phone/customer/competitor/detail.jsp?sid=" + sid;
	window.location.href = url;
}


//返回
function returnBack(){
	var  url = contextPath + "/system/mobile/phone/customer/index.jsp";
	window.location.href = url;
}

</script>
</body>
</html>