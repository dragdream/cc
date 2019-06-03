<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<%
	String flowId = request.getParameter("flowId") == null ? "0" : request.getParameter("flowId");
	String sid = request.getParameter("sid") == null? "": request.getParameter("sid");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<title>Word打印模版</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header.jsp"%>
<%@ include file="/header/upload.jsp"%>
<style type="text/css">
.imgMiddle{
	float:left;
	margin-top:5px;
}
.imgMiddleSpan{
	float:left;
	margin-top:4px;
}
</style>
<script type="text/javascript" src="<%=contextPath %>/common/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript">
var flowId = '<%=flowId%>';
var sid = '<%=sid%>';	
function doInit(){
	if(sid != "" && sid != '0'){
// 		var url = contextPath+"/flowPrivManage/getFlowPrivById.action";
// 		var para = {sid:sid};
// 		var jsonObj = tools.requestJsonRs(url,para);
// 		if(jsonObj.rtState){
// 			var json = jsonObj.rtData;
// 			if(json && json.sid){
// 			   bindJsonObj2Cntrl(json);
// 			   deptPostSet(json.privScope);
// 			   privTypeSet(json.privType);
// 			}
// 		}else{
// 			alert(jsonObj.rtMsg);
// 		}
	}
	
}

/**
 * 保存
 */
function doSave(callback){
	if (check()){
		var para =  {};
		para["modelName"] = $("#modelName").val();
		para["flowId"] = flowId;
		$("#form1").doUpload({
			url:contextPath+"/ntkoPrintTemplate/addOrUpdate.action",
			success:function(json){
				callback(json);
			},
			post_params:para
		});
	}
	return false;
}

function check() {
	var modelName = $("#modelName").val();
	if(modelName == ''){
		alert("请输入模版名称");
		return false ;
	}
	
	if(!fileValidator(document.all.file,["xml"])){
		alert("只允许上传xml类型的文件");
		return false;
	}
	
	return true;
	
}
</script>

</head>
<body onload="doInit()">

<form  method="post" name="form1" id="form1" enctype="multipart/form-data">
<table class="none-table">
   <tr>
    <td nowrap class="TableData">模板名称：</td>
    <td nowrap class="TableData">
    	<input type="text" id="modelName" name="modelName" class="BigInput"/>
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData">模版文件：</td>
    <td nowrap class="TableData">
    	<input type="file" name="file" id="file" />
    </td>
    </tr>
</table>
  </form>
</body>
</html>
 