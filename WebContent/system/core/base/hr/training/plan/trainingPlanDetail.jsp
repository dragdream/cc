<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);//

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<title>培训计划详细信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<%@include file="/header/upload.jsp"%>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/training/js/training.js"></script>
<script type="text/javascript">

function doInit(){
	getInfoById("<%=sid%>");
}

/* 查看详情 */
function getInfoById(id){
	var url =   "<%=contextPath%>/trainingPlanController/getInfoById.action";
	var para = {sid : id};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		if (prc && prc.sid) {
			bindJsonObj2Cntrl(prc);
			var attaches = prc.attacheModels;
			for(var i=0;i<attaches.length;i++){
				var attach = attaches[i];
				attach["priv"] = 3;
				var fileItem = tools.getAttachElement(attach);
				$("#attachment").append(fileItem);
			}
			$("#planStatus").html(planStatusFunc(prc.planStatus));
		}
	} else {
		alert(jsonObj.rtMsg);
	}
}




</script>
</head>
<body onload="doInit();">
<table align="center" width="96%" class="TableBlock" >
	<tr>
		<td nowrap class="TableData"  width="15%;">培训计划编号：</td>
		<td class="TableData" width="30%;"  id="planNo">
		</td>
		<td nowrap class="TableData" width="15%;" > 培训计划名称 ：</td>
		<td class="TableData" id="planName" >
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="">培训渠道：</td>
		<td class="TableData" width="" id="planChannelName">
		</td>
		<td nowrap class="TableData" width="" >培训形式 ：</td>
		<td class="TableData" id="courseTypesName">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="">主办部门：</td>
		<td class="TableData" width="" id="hostDepartmentsName">
		</td>
		<td nowrap class="TableData" width="" > 负责人：</td>
		<td class="TableData" id="chargePersonName">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="">计划参与培训人数：</td>
		<td class="TableData" width="" id="joinNum">
		</td>
		<td nowrap class="TableData" width="" >  培训地点 ：</td>
		<td class="TableData" id="address">
		</td>
	</tr>
	
	<tr>
		<td nowrap class="TableData"  width="">培训机构名称：</td>
		<td class="TableData" width="" id="institutionName">
		</td>
		<td nowrap class="TableData" width="" >培训机构联系人  ：</td>
		<td class="TableData" id="institutionContact">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="">培训机构相关信息：</td>
		<td class="TableData" width="" id=institutionInfo>
		</td>
		<td nowrap class="TableData" width="" >培训机构联系人相关信息 ：</td>
		<td class="TableData" id="instituContactInfo">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="">培训课程名称：</td>
		<td class="TableData" width="" id="courseName">
		</td>
		<td nowrap class="TableData" width="" >总课时：</td>
		<td class="TableData" id="courseHours">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="">开课时间：</td>
		<td class="TableData" width="" id="courseStartTimeStr">
		</td>
		<td nowrap class="TableData" width="" >结课时间  ：</td>
		<td class="TableData" id="courseEndTimeStr">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="">培训预算：</td>
		<td class="TableData" width="" id="planCost">
		</td>
		<td nowrap class="TableData" width="" >审批人 ：</td>
		<td class="TableData" id="approvePersonName">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="">审批时间：</td>
		<td class="TableData" width="" id="approveTimeStr">
		</td>
		<td nowrap class="TableData" width="" >审批状态：</td>
		<td class="TableData" id="planStatus">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="">审批意见：</td>
		<td class="TableData" width="" colspan="3" id="approveComment">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="">参与培训部门：</td>
		<td class="TableData" width="" colspan="3" id="joinDeptNameStr">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="">参与培训人员：</td>
		<td class="TableData" width="" colspan="3" id="joinPersonNameStr">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="">培训要求：</td>
		<td class="TableData" width="" colspan="3" id="planRequires">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="">培训说明：</td>
		<td class="TableData" width="" colspan="3" id="description">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="">培训内容：</td>
		<td class="TableData" width="" colspan="3" id="content">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="">备注：</td>
		<td class="TableData" width="" colspan="3" id="remark">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="">附件文档：</td>
		<td class="TableData" width="" colspan="3" id="attachment">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="">登记时间：</td>
		<td class="TableData" width="" colspan="3" id="createTimeStr">
		</td>
	</tr>
</table>
		
</body>
</html>