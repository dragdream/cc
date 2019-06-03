<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<%
	String flowTypeId = request.getParameter("flowTypeId") == null ? "0" : request.getParameter("flowTypeId");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp"%>
<title>流程模版管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="<%=cssPath%>/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui/themes/gray/easyui.css">
<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/src/teeValidagteBox.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/tools.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/easyuiTools.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/workflow/workmanage/flowprinttpl/aip/flowPrintModulAip.js?v=1"></script>




<script type="text/javascript">
var flowTypeId = '<%=flowTypeId%>';	
function doInit(){
	
}

function delPrintModul(sid){
	if (confirm("确定要删除此打印模版？")) {
		var url = contextPath + "/flowPrintTemplate/delById.action"  ;
	 	var para = {sid : sid};
		var rtJson = tools.requestJsonRs(url,para);
	    if (rtJson.rtState) {
	    	alert("删除成功！");
	    	window.location.reload();
	    }else{
	    	alert(rtJson.rtMsg);
	    }
	}
}
/***
 * 跳转至编辑界面 --
 */
function toUpdate(sid){
	window.location.href = "<%=contextPath%>/system/core/workflow/workmanage/flowprinttpl/settingPriv.jsp?flowTypeId=" + flowTypeId + "&sid=" + sid;
}


/***
 * 跳转至更新模版界面 --
 */
function toChangeModul(sid){
	window.location.href = "<%=contextPath%>/system/core/workflow/workmanage/flowprinttpl/updatemodul.jsp?flowTypeId=" + flowTypeId + "&sid=" + sid;
}


/**
 * 跳转至设计界面
 */
function toModulDesigner(sid){
	window.location.href = "<%=contextPath%>/system/core/workflow/workmanage/flowprinttpl/moduldesigner.jsp?flowTypeId=" + flowTypeId + "&sid=" + sid;
}


</script>

</head>
<body onload="doInit()" style="padding:10px">
<form  method="post" name="form1" id="form1" >
	<table class="TableBlock" width="80%" align="center">
   <tr>
    <td nowrap class="TableData TableBG"  align="center">模版名称：</td>
    <td nowrap class="TableData">
      <input type="text" id="modulName" name="modulName" class="BigInput">
       
    </td>
   </tr>
<!--    <tr>
    <td nowrap class="TableData TableBG" align="center">模版类型:</td>
    <td nowrap class="TableData">
    	 <select name="modulType" class="BigSelect" id="modulType" >
      	 	 <option value="1">打印模板</option>
      		 <option value="2">手写呈批单</option>
      	</select>
      <br>说明:在主办过程中可以直接在呈批单上进行手写签批<br>
    	
    </td>
    </tr> -->
    <tr>
    <td nowrap class="TableData TableBG" align="center">选择模版文件</td>
    <td nowrap class="TableData">
    	 <input type="button" value="选择模板文件" class="btn btn-success" onClick="selFile();">(office文件、图片、pdf等等)
    </td>
   </tr>
    <tr  style="display:none">
    <td nowrap class="" align="center" colspan="2">
    	<input type="button" value="新建打印模板" class="btn btn-primary" onClick="convertAndSave();">
    	</td>

   </tr>		
    
	</table>
 </form>
 <!-- -----------------------------== 装载AIP控件 ==--------------------------------- -->
		<script src="<%=contextPath%>/system/core/aip/loadaip.js"></script>
	<!-- --------------------------------== 结束装载控件 ==----------------------------------- -->
 <div id="printModulList">
 		
 
 </div>
</body>
</html>
 