<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    	int fixedAssetsId = TeeStringUtil.getInteger(request.getParameter("fixedAssetsId"), 0);//固定资产Id
    		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);//固定资产Id
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header.jsp"%>
<%@ include file="/header/userheader.jsp" %>
<%@include file="/header/upload.jsp"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath %>/system/core/person/js/person.js"></script>

<script>
var fixedAssetsId = "<%=fixedAssetsId%>";
var sid  = <%=sid%>;

function doInit(){
	getRecordInfo();

}

/**
 * 获取领用记录详情记录
 */
function getRecordInfo(){
	if(sid > 0){
		var url = "<%=contextPath%>/teeFixedAssetsRecordController/getById.action?sid="+sid;
		var json = tools.requestJsonRs(url);
		if(json.rtState){
			bindJsonObj2Cntrl(json.rtData);
			var attaches = json.rtData.attacheModels;
			for(var i=0;i<attaches.length;i++){
				var fileItem = tools.getAttachElement(attaches[i]);
				$("#attachs").append(fileItem);
			}
		}else{
			alert(json.rtMsg);
		}
	}
}

</script>

</head>
<body onload="doInit();" style="font-size:12px">
<form method="post" name="form1" id="form1"  >
	<table class="TableBlock" style="width:100%;font-size:12px;margin:0 auto;" >
	
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
				<div  id="fixedAssetsName"></div>
			</td>
		</tr>
		<tr>
			<td class="TableData">
				<b>报废部门：</b>
				</td>
			<td  class="TableData">
				<div id="deptName"></div>
			</td>
			<td class="TableData">
				<b>报废人：</b>
			</td>
			<td class="TableData" style="vertical-align: bottom;">
				<div id="userName"></div>
			</td>
		</tr>
		
		<tr>
			<td class="TableData">
				<b>报废类型：</b>
				</td>
			<td  class="TableData">
				<div id="optTypeDesc"></div>
			</td>
			<td class="TableData">
				<b>报废日期：</b>
			</td>
			<td class="TableData" style="vertical-align: bottom;">
				<div id="optDateStr"></div>
			</td>
		</tr>
		<tr>
			<td class="TableData">
				<b>报废凭证：</b>
				</td>
			<td colspan="3" class="TableData">
				<div id ='attachs'></div>
			</td>
		</tr>
		
		<tr>
			<td class="TableData">
				<b>报废原因：</b>
				</td>
			<td colspan="3" class="TableData">
				<div id="optReason"></div>
			</td>
		</tr>
		
		<tr>
			<td class="TableData">
				<b>备注：</b>
				</td>
			<td colspan="3" class="TableData">
				<div id="optRemark"></div>
			</td>
		</tr>
		
	</table>
	
</form>
</body>
</html>