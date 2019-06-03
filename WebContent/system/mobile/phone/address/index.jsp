<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>通讯簿</title>
<%@ include file="/system/mobile/header.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<link rel="stylesheet" type="text/css" href="<%=contextPath %>/common/bootstrap/css/bootstrap.css"/>

<script type="text/javascript">

function doInit(){
	var url = contextPath +  "/teeAddressController/getAddressLastName.action";
	var para = {};
	var jsonRs = tools.requestJsonRs(url,para);
	if(jsonRs.rtState){
		var dataList = jsonRs.rtData;
		var render = [];
		for(var i=0;i<dataList.length;i++){
			render.push("<option value='"+dataList[i].seqId+"'>"+dataList[i].nameStrs+"</option>");
		}
		$("#seqIds").append(render.join(""));
	}else{
		//alert(jsonRs.rtMsg);
	} 
}

function doSearch(){
	var name = encodeURI($("#name").val());
	var sex = encodeURI($("#sex").val());
	var phoneNo = encodeURI($("#phoneNo").val());
	window.location = "list.jsp?name="+name+"&sex="+sex+"&phoneNo="+phoneNo+"&seqIds="+$("#seqIds").val();
}

</script>


</head>
<body onload="doInit()" style="padding:10px;overflow:hidden">
<form class="form-horizontal" role="form">
<div class="form-group">
   <label class="col-sm-2 control-label">姓氏：</label>
    <div class="col-sm-10">
      <select class="form-control" id="seqIds">
      	<option value="">全部</option>
      </select>
    </div>
  </div>
  <div class="form-group">
    <label class="col-sm-2 control-label">姓名：</label>
    <div class="col-sm-10">
      <input type="text" class="form-control" id="name">
    </div>
  </div>
  <div class="form-group">
    <label class="col-sm-2 control-label">性别：</label>
    <div class="col-sm-10">
      <select class="form-control" id="sex">
      	<option value="">全部</option>
      	<option value="0">男</option>
      	<option value="1">女</option>
      </select>
    </div>
  </div>
  <div class="form-group">
    <label class="col-sm-2 control-label">手机：</label>
    <div class="col-sm-10">
      <input type="text" class="form-control" id="phoneNo">
    </div>
  </div>
  <button type="button" class="btn btn-default btn-lg btn-block" onclick="doSearch()">查&nbsp;&nbsp;询</button>
</form>
</body>
</html>