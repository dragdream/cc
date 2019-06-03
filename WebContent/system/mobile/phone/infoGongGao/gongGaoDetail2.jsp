<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
   //项目主键
   String uuid=TeeStringUtil.getString(request.getParameter("uuid"));

   //审批状态
   int status=TeeStringUtil.getInteger(request.getParameter("status"),0);// 0未审批   1已审批
%>

<!DOCTYPE HTML>
<html>
<head>
<title>公告审批</title>
<%@ include file="/system/mobile/mui/header.jsp" %>
<style>
#topPopover {
	position: fixed;
	top: 16px;
	right: 6px;
	width: 120px;
}
#topPopover .mui-popover-arrow {
	left: auto;
	right: 6px;
}

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

</style>
</head>

<script type="text/javascript">
//项目主键
var uuid="<%=uuid%>";
var status=<%=status%>;
//初始化方法
function doInit(){
	getInfoByUuid(uuid);
	if(status==0){//未审批
		$("#moreOpt").show();
	}

	
	backBtn.addEventListener("tap",function(){
		window.location.href="index.jsp";
	});//返回
}




//根据主键获取详情
function getInfoByUuid(uuid){
	var url=contextPath+"/teeNotifyController/getNotify.action?isLooked="+status;
	mui.ajax(url,{
		type:"POST",
		dataType:"JSON",
		data:{id:uuid},
		timeout:10000,
		async:false,
		success:function(text){
			var json = eval("("+text+")");
			if(json.rtState){
				var data=json.rtData;
				var sendTime =  getFormatDateStr(data.sendTime , 'yyyy-MM-dd HH:mm');
				bindJsonObj2Cntrl(data);
				$("#sendTime").html(sendTime);
				//立项中1       审批中2      办理中3        挂起中4       已办结5     已拒绝6
			}
		}
	});
}

//同意
function approve(uuid){
	if(window.confirm("是否确认批准该公告？")){
		var param=formToJson("#form1");;
		param["publish"] = 1;
		var url=contextPath+"/teeNotifyController/audNotify.action";
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:param,
			timeout:10000,
			success:function(json){
				json = eval("("+json+")");
				if(json.rtState){
					alert("已批准！");
				}else{
					alert("操作失败！");
				}
			}
		});
	}
}





//拒绝
function noApprove(uuid){ 
	window.location.href="refusedReason.jsp?uuid="+uuid;
	
}

</script>
<body onload="doInit()">
	 <header class="mui-bar mui-bar-nav">
		<span class="mui-icon mui-icon-back" id="backBtn" onclick=""></span>
		<h1 class="mui-title">发布人</h1>
		<a class="mui-action-menu mui-icon mui-icon-more mui-pull-right" href="#topPopover" id="moreOpt" style="display: none"></a>
	</header>
<div id="muiContent" class="mui-content">
<form id="form1" name="form1">
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>发布人</label>
		</div>
		<div class="app-row-content" id="fromPersonName">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>类型</label>
		</div>
		<div class="app-row-content" id="typeDesc">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>标题</label>
		</div>
		<div class="app-row-content" id="subject">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>发布内容</label>
		</div>
		<div class="app-row-content" id='content'>
			
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label style="width: 38%;">发布范围（部门）</label>
		</div>
		<div class="app-row-content" id="toDeptNames">
			
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label style="width: 38%;">发布范围（角色）</label>
		</div>
		<div class="app-row-content" id="toRolesNames">
			
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label style="width: 38%;">发布范围（人员）</label>
		</div>
	<div class="app-row-content" id="toUserNames">
			
		</div>
	</div>
    <div class="mui-input-group">
		<div class="mui-input-row">
			<label>发布时间</label>
		</div>
		 <div class="app-row-content" id="sendTime">
			
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>生效日期</label>
		</div>
		 <div class="app-row-content" id="beginDate" >
		
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>终止日期</label>
		</div>
		<div class="app-row-content" id="endDate">
		</div>
	</div>
		<div class="mui-input-group">
		<div class="mui-input-row">
			<label>短信提醒</label>
		</div>
		<!-- <div class="app-row-content" id="endDate">
		</div> -->
		<input name="smsRemind" id="smsRemind" type="checkbox" value="on"/>使用内部短信提醒   
	</div>
		<div class="mui-input-group">
		<div class="mui-input-row">
			<label>置顶</label>
		</div>
		<!-- <div class="app-row-content" id="endDate">
		</div> -->
		<input name="top" id="top" type="checkbox" value="1"/>使公告通知置顶，显示为重要 <!-- <input size="2" />天后结束置顶，0表示一直置顶  -->
	</div>
		<div class="mui-input-group">
		<div class="mui-input-row">
			<label>审批意见</label>
		</div>
		<!-- <div class="app-row-content" id="endDate">
		</div> -->
		<textarea name="reason" class="BigInput" id="reason" rows="4" cols="50" wrap="no"></textarea>
	</div>
  </form>	
</div>

	<!--右上角弹出菜单-->
	<div id="topPopover" class="mui-popover" >
		<ul class="mui-table-view">
		      <li class="mui-table-view-cell" onclick="approve('<%=uuid %>')" >批准</li>
		      <li class="mui-table-view-cell" onclick="noApprove('<%=uuid%>')">不批准</li>
		
		  </ul>
	</div>

</body>
</html>