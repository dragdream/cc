
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
		int fixedAssetsId = TeeStringUtil.getInteger(request.getParameter("fixedAssetsId"), 0);//固定资产Id
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header.jsp"%>
<%@include file="/header/easyui.jsp"%>
<%@ include file="/header/ztree.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/system/core/base/fixedAssets/js/assets.js"></script>
<script type="text/javascript" src="<%=contextPath %>/system/core/person/js/person.js"></script>

<script>
var fixedAssetsId = "<%=fixedAssetsId%>";
var deptName;
//selectByAssetsId
function doInit(){
    getAssetsInfo();
    
}
function getAssetsInfo(){
	if(fixedAssetsId!="" && fixedAssetsId!=null && fixedAssetsId!="null"){
		var url = "<%=contextPath%>/TeeFixedAssetsInfoController/getAssetsInfo.action?sid="+fixedAssetsId;
		var json = tools.requestJsonRs(url);
		if(json.rtState){
			bindJsonObj2Cntrl(json.rtData);
			var prcs = getAssetsRecordByAssetsId(fixedAssetsId , '');
			var desc = "";
			for(var i =0; i <prcs.length ; i++){
				var prc = prcs[i];
				var tr  = "<tr>"
				 + "<td>" + prc.deptName + " </td>"
				 + "<td>" + prc.userName + " </td>"
				 + "<td>" + prc.optReason + " </td>"
				 + "<td>" + prc.optRemark + " </td>"
				 + "<td>" + prc.optDateStr + " </td>"
				+ "</tr>";
				desc = desc + tr;
			}
			$("#tbody").append(desc);
		}else{
			alert(json.rtMsg);
		}
	}
}

</script>

</head>
<body onload="doInit();" style="font-size:12px">
	<table class="TableBlock" style="width:800px;font-size:12px;margin:0 auto;" >
	
		<tr>
			<td class="TableData" nowrap="nowrap">
				<b>资产编号：</b>
				</td>
			<td  class="TableData">
				<div  id="assetCode" ></div>
			</td>
		
			<td class="TableData">
				<b>资产名称：</b>
				</td>
			<td colspan="3" class="TableData">
				<div  id="assetName" ></div>
			</td>
		</tr>
		<tr>
			<td class="TableData">
				<b>所属部门：</b>
				</td>
			<td  class="TableData">
				<div id="deptName"></div>
			</td>
			<td class="TableData">
				<b>保管人：</b>
			</td>
			<td class="TableData" style="vertical-align: bottom;">
				<div id="keeperName"></div>
			</td>
		</tr>
		
	</table>
	
	
	<table class="TableBlock" style="font-size:12px;margin:0 auto;" >
		<tr>
			<td>部门</td>
			<td>操作人员</td>
			<td>原因</td>
			<td>备注</td>
			<td>附件</td>
		</tr>
		<tbody id="tbody">
			
		</tbody>
	</table>
</body>
</html>