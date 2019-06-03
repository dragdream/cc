<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<%
	String flowTypeId = request.getParameter("flowTypeId") == null ? "0" : request.getParameter("flowTypeId");
	String sid = request.getParameter("sid") == null ? "0" : request.getParameter("sid");
    int type=TeeStringUtil.getInteger(request.getParameter("type"),0);//1=固定流程  2=自由流程
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp"%>
<title>设置打印模版权限</title>
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
var sid = "<%=sid%>";
var type=<%=type %>;
function doInit(){
	if(type==1){//固定流程
		$("#prcsTr").show();
	}else if(type==2){//自由流程
		$("#prcsTr").hide();
	}
	
	
	var url = contextPath + "/flowPrintTemplate/getByIdInfo.action"  ;
 	var para = {sid:sid,flowTypeId:flowTypeId};
	var rtJson = tools.requestJsonRs(url,para);
    if (rtJson.rtState) {
    	var data = rtJson.rtData;
    	var printModul = data.printModul;
    	var prcsList = data.prcsList;
    	bindJsonObj2Cntrl(printModul);
    	var checkBox = "";
    	jQuery.each(prcsList, function(i, prcs) {
    		//alert(filed.fieldName);
    		//var vret = insertNote(filed);
    		var isPrivStr = "";
    		if(prcs.isPriv){
    			isPrivStr = "checked";
    		}
    		checkBox = checkBox + "<input type='checkbox' name='prcsId' value='" + prcs.sid + "' "  + isPrivStr + " />" + prcs.prcsName + "&nbsp;&nbsp;";
    	});
    	$("#prcsIds").append(checkBox);
    }else {
       alert(rtJson.rtMsg);
       return false;
   }

}

</script>

</head>
<body onload="doInit()">

<div style="padding:5px 0px 0px 10px">
</div>
<form  method="post" name="form1" id="form1" >
	<table class="TableBlock" width="80%" align="center">
   <tr>
    <td nowrap class="TableData"  align="center">模版名称：</td>
    <td nowrap class="TableData">
      <input type="text" id="modulName" name="modulName" class="BigInput">
       
    </td>
   </tr>

    <tr id="prcsTr" style="display: none;">
    <td nowrap class="TableData" align="center">设置流程步骤签批权限</td>
    <td  class="TableData" id="prcsIds">
    	
    </td>
   </tr>
    <tr style="display:none">
    <td nowrap class="" align="center" colspan="2">
    	<input type="button" value="保存" class="btn btn-primary" onClick="updateModulInfo();">
    	&nbsp;&nbsp;<input type="button" value="返回" class="btn btn-primary" onClick="history.go(-1);">
    	</td>

   </tr>		
    
	</table>
 </form>
 
 
</body>
</html>
 