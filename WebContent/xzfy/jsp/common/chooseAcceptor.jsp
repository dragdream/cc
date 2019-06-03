<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/training/js/training.js"></script>

<title>选择承办人</title>
<script type="text/javascript">
var caseId = "<%=request.getParameter("caseId")%>";
var count = 0;

$(function(){
	var para = {'caseId':caseId};
	var url = "<%=contextPath%>/xzfy/common/selectPersonDept.action";
	json = tools.requestJsonRs(url,para);
	//给两个select框赋同样的值
	var html = "";
	for(var i=0;i<json.rtData.length;i++){
		var uuid = json.rtData[i].uuid;
		var userName = json.rtData[i].userName;
		html+="<option value='"+uuid+"'>"+userName+"</option>";
		$("#firstAcceptancerId").html(html);
		$("#secondAcceptancerId").html(html);
	}
	//回显操作
	var para = {'caseId':caseId};
	var url = "<%=contextPath%>/xzfy/common/selectAcceptancer.action";
	json = tools.requestJsonRs(url,para);
	var dealMan1Id = json.rtData.dealMan1Id;
	var dealMan2Id = json.rtData.dealMan2Id
	 $("#firstAcceptancerId option").each(function(){
		if($(this).val() == dealMan1Id ){
			 this.selected = 'selected';
		} 
	});
	 $("#secondAcceptancerId option").each(function(){
		if($(this).val() == dealMan2Id ){
			 this.selected = 'selected';
		} 
	});
});
	
	
//新增或更新
function commit(){
	var para = {'firstAcceptancerId':$("#firstAcceptancerId").val(),"firstAcceptancerName":$("#firstAcceptancerId option:selected").html(),
			'secondAcceptancerId':$("#secondAcceptancerId").val(),"secondAcceptancerName":$("#secondAcceptancerId option:selected").html()};
	var url = "<%=contextPath%>/xzfy/common/saveAcceptancer.action?caseId="+caseId;
	json = tools.requestJsonRs(url,para);
	return true;
	
}


</script>
</head>
<body>
<form id="form1"  method="post" name="form1" action="/acceptancerController/saveAcceptancer.action">
	<table align="center" width="90%" class="TableBlock">
		
		<tr>
			<td nowrap class="TableData" >第一承办人:</td>
			<td>
				<select id="firstAcceptancerId" name="firstAcceptancerId"> 
				</select>
			</td>
		</tr>
		<tr>
			<td nowrap class="TableData" >第二承办人:</td>
			<td>
				<select id="secondAcceptancerId" name="secondAcceptancerId">
				</select>
			</td>
		</tr>	
	</table>
</form>
</body>
</html>