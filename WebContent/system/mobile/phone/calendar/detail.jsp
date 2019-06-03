<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%

	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);//获取Id
%>
<!DOCTYPE HTML>
<html>
<head>
<title>日程详情</title>
<script>
var DING_APPID = "<%=com.tianee.oa.oaconst.TeeModuleConst.MODULE_SORT_DD_APP_ID.get("022")%>";
</script>
<%@ include file="/system/mobile/mui/header.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">

<link href="<%=contextPath%>/system/mobile/phone/style/style.css?v=1" rel="stylesheet" type="text/css" />
<style>
.app-row{
	margin-left:14px;
	padding-top:10px;
	padding-right:10px;
	padding-bottom:10px;
	border-bottom:1px solid #f0f0f0;
}
.app-row br{
	margin:0px;
}
.app-row-content{
	margin-left:14px;
	padding-top:10px;
	padding-right:10px;
	padding-bottom:10px;
	border-bottom:1px solid #f0f0f0;
	color:gray;
}
.app-row-right{
	padding:8px;
}
</style>

<script type="text/javascript">
/**
 * 页面加载是即可
 */
var sid = <%=sid%>;
function doInit(){
	history.forward(1);
	var url = contextPath+"/mobileCalendarController/getById.action";
	$.ajax({
	  type: 'POST',
	  url: url,
	  data: {sid:sid},
	  timeout: 10000,
	  success: function(json){
			json = eval('(' + json + ')');
			if(json.rtData.calType==1){
				$("#calType").html("工作事务");
			}else{
				$("#calType").html("个人事务");
			}
			
			
			switch(json.rtData.calLevel){
			case "0":
				$("#calLevel").html("未指定");
				break;
			case "1":
				$("#calLevel").html("重要/紧急");
				break;
			case "2":
				$("#calLevel").html("重要/不紧急");
				break;
			case "3":
				$("#calLevel").html("不重要/紧急");
				break;
			case "4":
				$("#calLevel").html("不重要/不紧急");
				break;
			default:
				break;
			}
			//alert(json.rtData.actorIds)
			//$("#actorNames").html(json.rtData.actorNames);
			 if(json.rtData.actorNames){
			  	  $("#actorIds").show();
			  	  $("#actorNames").append(json.rtData.actorNames);
			  }
			$("#calTime").html(json.rtData.calTime);
			$("#endTime").html(json.rtData.endTime);
			$("#content").html(json.rtData.content);
			
	  },
	  error: function(xhr, type){
	    alert('服务器请求超时!');
	  }
	});
	
	a1.addEventListener("tap",function(){
		window.location = "index.jsp";
	});
	a2.addEventListener("tap",function(){
		window.location = "addOrUpdate.jsp?sid="+sid;
	});
	a3.addEventListener("tap",function(){//  
		var btnArray = ['是', '否'];
		mui.confirm('是否要删除该日程？', '确认', btnArray, function(e) {
			if (e.index == 0) {
				$.ajax({
					  type: 'POST',
					  url: contextPath+'/calendarManage/deleteById.action',
					  data: {sid:sid},
					  timeout: 10000,
					  success: function(json){
							window.location = "index.jsp";
					  },
					  error: function(xhr, type){
					    alert('服务器请求超时!');
					  }
					});
			} else {
				
			}
		})
	});
	
}

function toAddOrUpdateDiary(sid){
	window.location.href = mobilePath + "/phone/email/addOrUpdate.jsp?sid=" + sid;
}

</script>


</head>
<body onload="doInit();">
<div id="muiContent" class="mui-content">
<form id="form" enctype="multipart/form-data" method="post">
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>日程类型</label>
			<p class="app-row-right mui-ellipsis" id="calType"></p>
		</div>
		<div class="mui-input-row">
			<label>优先级</label>
			<p class="app-row-right mui-ellipsis" id="calLevel"></p>
		</div>
		<div class="mui-input-row">
			<label>起始时间</label>
			<p class="app-row-right mui-ellipsis" id="calTime"></p>
		</div>
		<div class="mui-input-row">
			<label>结束时间</label>
			<p class="app-row-right mui-ellipsis" id="endTime"></p>
		</div>
		<div class="mui-input-row">
			<label>参与人</label>
			<p class="app-row-right mui-ellipsis" id="actorNames"></p>
			<p class="app-row-right mui-ellipsis" id="actorIds"></p>
			<!-- <input type="hidden" id="userListIds" name="userListIds" placeholder="选择收件人" />
			<input type="text" id="userListNames" readonly name="userListNames" placeholder="选择收件人" onclick="selectUser('userListIds','userListNames');"/> -->
		</div>
	</div>
</div>
<div class="mui-input-group">
	<div class="mui-input-row">
		<label>日程内容</label>
	</div>
	<div id="content" class="mui-input-row" style="overflow:auto;padding:10px;height:200px">
		
	</div>
</div>
<br/>
<br/>
<br/>
</form>
<!-- 底部操作栏 -->
<nav class="mui-bar mui-bar-tab">
	<a id="a1" class="mui-tab-item mui-active">
		<span class="mui-icon mui-icon-undo"></span>
		<span  class="mui-tab-label" >返回</span>
	</a>
 	<a id="a2" class="mui-tab-item mui-active">
		<span class="mui-icon mui-icon-compose"></span>
		<span  class="mui-tab-label" >编辑</span>
	</a>
	<a id="a3" class="mui-tab-item mui-active">
		<span class="mui-icon mui-icon-trash"></span>
		<span  class="mui-tab-label" >删除</span>
	</a>
</nav>
</body>
</html>