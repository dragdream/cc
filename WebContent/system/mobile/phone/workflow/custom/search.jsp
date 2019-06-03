<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>工作查询</title>
<%@ include file="/system/mobile/mui/header.jsp" %>
<%
	String flowTypeId = TeeStringUtil.getString(request.getParameter("flowTypeId"));
   
%>
</head>
<body>
 <header id="header"  class="mui-bar mui-bar-nav">
	<span class="mui-icon mui-icon-back" id="backBtn"></span>
	<h1 class="mui-title">工作查询</h1>
</header>

<div id="muiContent" class="mui-content">
<form action="search_list.jsp" method="method">
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>流水号</label>
		</div>
		<div class="mui-input-row">
			<input type="text" id="runId" name="runId" placeholder="根据流水号查询" />
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>工作名称</label>
		</div>
		<div class="mui-input-row">
			<input type="text" id="runName" name="runName" placeholder="根据工作名称查询" />
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>查询范围</label>
		</div>
		<div class="mui-input-row">
			<select id="type" name="type" onchange="changeType()" style="font-size: 14px">
			     <option value="0">全部</option>
			     <option value="1">我发起的</option>
			     <option value="2">我经办的</option>
			     <option value="3">我管理的</option>
			     <option value="4">我关注的</option>
			     <option value="5">我查阅的</option>
			     <option value="6">指定发起人</option>
			</select>
		</div>
		<div class="mui-input-row" id="beginUserDiv" style="display: none">
			<input type="text" id="beginUserName" name="beginUserName" readonly placeholder="请选择流程发起人" />
			<input type="hidden" id="beginUser" name="beginUser"/>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>流程状态</label>
		</div>
		<div class="mui-input-row">
			<select id="status" name="status" style="font-size: 14px">
			   <option value="0">全部</option>
			   <option value="1">执行中</option>
			   <option value="2">已结束</option>
			</select>
		</div>
    </div>
	<br/>
	<input type="hidden" name="flowTypeId" value="<%=flowTypeId%>"/>
	
	<center>
		<button class="btn btn-primary">查询</button>
	</center>
</form>
</div>


<script type="text/javascript">
var  flowTypeId="<%=flowTypeId %>";
//改点查询范围
function changeType(){
	var type=$("#type").val();
	if(type=="6"||type==6){
		$("#beginUserDiv").show();
	}else{
		$("#beginUserDiv").hide();
	}
}


mui.ready(function() {
	
	backBtn.addEventListener("tap",function(){
		var  url = "customIndex.jsp?flowTypeId="+flowTypeId;
		window.location.href = url;
	});
	
	//流程发起人
	beginUserName.addEventListener('tap', function() {
		selectSingleUser("beginUser","beginUserName");
	}, false);

});
</script>
</body>
</html>