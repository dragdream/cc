<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%

int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/training/js/training.js"></script>

<title>培训记录详情</title>
<script type="text/javascript">
var sid = "<%=sid%>";
function doInit(){
	if(sid > 0){
		getInfoById(sid);
	}
}
/* 查看详情 */
function getInfoById(id){
	var url =   "<%=contextPath%>/trainingRecordController/getById.action";
	var para = {sid : id};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		if (prc && prc.sid) {
			bindJsonObj2Cntrl(prc);
			
		}
	} else {
		alert(jsonObj.rtMsg);
	}
}



</script>
</head>
<body onload="doInit()" >
<form action=""  method="post" name="form1" id="form1">
<input type="hidden" name="sid" id="sid" value="<%=sid%>">
<table align="center" width="96%" class="TableBlock" >
	<tr>
		<td class="TableData">培训计划：</td>
		<td class="TableData" colspan="3" id="planName" >
		

	
		</td>
		
	</tr>
	<tr>
		<td class="TableData">受训人员：</td>
		<td class="TableData"  colspan="3" id="recordUserName">
		
		
		</td>
	</tr>
	
	<tr>
		<td nowrap class="TableData"  width="15%;" >培训机构：<font style='color:red'>*</font></td>
		<td class="TableData" width="35%;" id = "recordInstitution" >	
		
		</td>
		<td nowrap class="TableData"  width="15%;" >培训费用：<font style='color:red'>*</font></td>
		<td class="TableData" width="60%;"  id = "recordCost">
		</td>
	</tr>
	
	
		<tr>
		<td nowrap class="TableData"  width="15%;" >培训考核成绩：<font style='color:red'>*</font></td>
		<td class="TableData" width="35%;" id = "examResults">
			
		</td>
		<td nowrap class="TableData"  width="15%;" >培训考核等级：<font style='color:red'>*</font></td>
		<td class="TableData" width="60%;" id = "examLevel">
		
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="">出勤情况：</td>
		<td class="TableData" width="" colspan="3" id="dutySituation" >
			
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="">总结完成情况：</td>
		<td class="TableData" width="" colspan="3"  id="trainningSituation" >
			
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="">评论：</td>
		<td class="TableData" width="" colspan="3" id="recordComment" >
		
		</td>
	</tr>
	
	<tr>
		<td nowrap class="TableData"  width="">备注：</td>
		<td class="TableData" width="" colspan="3" id="remark" >
		
		</td>
	</tr>
		

</table>
</form>

</body>
</html>