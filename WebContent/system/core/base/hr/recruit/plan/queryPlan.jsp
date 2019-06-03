

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
<title>招聘需求详细信息</title>
<script type="text/javascript">

function doInit(){
	getApprovPlanList();
}

/* 查看详情 */
function getApprovPlanList(){
	var url =   "<%=contextPath%>/recruitPlanController/getApprovPlanList.action";
	var para =  tools.formToJson($("#form1"));
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prcs = jsonObj.rtData;
		$("#tbody").empty();
		if(prcs.length > 0){
			var temp  = "";
			for(var i =0; i<prcs.length ; i++){
				var prc = prcs[i];
				 temp = temp + "<tr align='center'><td class='TableData' style='cursor:pointer' itemId='"+prc.sid+"' itemName='"+prc.planName+"'>" + prc.planName + "</td></tr>";
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
		parent.$("#planId").val(sid);
		parent.$("#planName").val(name);
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
		<td nowrap class="TableData"  width="" align="center">招聘计划名称：
			<input type="text" name="planName"  class="BigInput">
		

			<input type="button" value="模糊查询" class="btn btn-primary" onclick="getApprovPlanList();">
		</td>

	</tr>
	<tr class="TableHeader">
		 	<td align='center'>选择招聘计划</td>
	</tr>
	<tbody id="tbody" >
	

	</tbody>
</table>
</form>	
</body>
</html>