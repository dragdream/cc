<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<title>选择人才库</title>
<script type="text/javascript">

function doInit(){
	getApprovPoolList();
}

/* 查看详情 */
function getApprovPoolList(){
	var url =   "<%=contextPath%>/hrPoolController/selectPool.action";
	var para =  tools.formToJson($("#form1"));
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prcs = jsonObj.rtData;
		$("#tbody").empty();
		if(prcs.length > 0){
			var temp  = "";
			for(var i =0; i<prcs.length ; i++){
				var prc = prcs[i];
				 temp = temp + "<tr align='center'><td class='TableData' style='cursor:pointer' employeeMajorDesc='" + prc.employeeMajorDesc + "'  employeePhone='" + prc.employeePhone + "' positionDesc='" + prc.positionDesc + " 'itemId='" + prc.sid + "' itemName='"+prc.employeeName + " 'planId='" + prc.planId + "' planName='" + prc.planName + "'>" + prc.employeeName + "</td></tr>";
			}
			$("#tbody").append(temp);
		}else{
			messageMsg("没有相关数据", "tbody" ,'' ,280);
		}
	} else {
		alert(jsonObj.rtMsg);
	}
	
	$("#tbody").find("td").click(function(){
		var sid = $(this).attr('itemId');
		var name = $(this).attr('itemName');
		parent.$("#hrPoolId").val(sid);
		parent.$("#hrPoolName").val(name);
		
		parent.$("#employeeMajor").val($(this).attr('employeeMajorDesc'));//专业
		parent.$("#position").val($(this).attr('positionDesc'));//岗位
		parent.$("#employeePhone").val($(this).attr('employeePhone'));//电话
		
		parent.$("#planId").val($(this).attr('planId'));//计划Id
		parent.$("#planName").val($(this).attr('planName'));//计划名称
		//parent.BSWINDOW.modal("hide");
		parent.$("#win_ico").click();
	});
}


</script>
</head>
<body onload="doInit();">

<form id="form1" name="form1">
<table align="center" width="96%" class="TableList" >
	<tr>
		<td nowrap class="TableData"  width="" align="center">应聘者姓名：
			<input type="text" id="employeeName" name="employeeName"  class="BigInput">
			<input type="button" value="模糊查询" class="btn btn-primary" onclick="getApprovPoolList();">
		</td>

	</tr>
	<tr class="TableHeader">
		 	<td align='center'>选择应聘者</td>
	</tr>
	<tbody id="tbody" >
	

	</tbody>
</table>
</form>	
</body>
</html>