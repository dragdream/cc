<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String deptIds=request.getParameter("deptIds");
	String startDateDesc=request.getParameter("startDateDesc");
	String endDateDesc=request.getParameter("endDateDesc");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp" %>
<title>出差历史记录</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="<%=contextPath%>/system/core/base/attend/js/attend.js"></script>
<script type="text/javascript">
function  doOnload(){
	queryAnnualLeave();
}
/**
 *查询管理
 */
var datagrid;
function queryAnnualLeave(){
<%-- 	var url =   "<%=contextPath %>/attendLeaveManage/getAnnualLeaveByCondition.action?deptId=<%=deptId%>&startDateDesc=<%=startDateDesc%>&endDateDesc=<%=endDateDesc%>"; --%>
datagrid = $('#datagrid').datagrid({	
	url:contextPath+'/attendLeaveManage/getAnnualLeaveByConditionEasyui.action?deptIds=<%=deptIds%>&startDateDesc=<%=startDateDesc%>&endDateDesc=<%=endDateDesc%>',
	view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
	pagination:false,
	singleSelect:true,
	toolbar:'#toolbar',//工具条对象
	border:false,
	//idField:'formId',//主键列
	fitColumns:true,//列是否进行自动宽度适应
	queryParams:{showFlag:1},
	nowrap:false,
	columns:[[
		//{field:'id',checkbox:true,title:'ID',width:100},
		{field:'deptName',title:'部门',width:100},
		{field:'userName',title:'姓名',width:100},
		{field:'totalAnnualLeaveDays',title:'总年假天数（今年）',width:100},
		{field:'usedAnnualLeaveDays',title:'已用年假天数 ',width:100},
		{field:'remainAnnualLeaveDays',title:'剩余年假天数',width:100}
	]]
});

$(".datagrid-header-row td div span").each(function(i,th){
	var val = $(th).text();
	 $(th).html("<label style='font-weight: bolder;'>"+val+"</label>");
});	
	/*var jsonObj = tools.requestJsonRs(url);
	if(jsonObj.rtState){
		var prcs = jsonObj.rtData;
		$("#listDiv").empty();
		if(prcs.length > 0){
			var tableStr = "<table class='TableBlock' width='98%' align='center'><tbody id='listTbody'>";
		    	 tableStr = tableStr + "<tr class='TableHeader'>"
			      	 + "<td width='100' align='center'>部门</td>"
			      	 + "<td width='100' align='center'>姓名</td>"
			      	 + "<td width='100' align='center'>总年假天数（今年）</td>"
			      	 +"<td nowrap width='100' align='center'>已用年假天数  </td>"
			     	 +"<td nowrap  width='100'  align='center'>剩余年假天数</td>"
			         +"</tr>";
			for(var i = 0;i<prcs.length ; i++){
				var prc = prcs[i];
				var id = prc.sid;
				var  fontStr = "";
				tableStr = tableStr +"<tr  class='TableData'>"
				      	 + "<td width='140' align='center'><font color='" + fontStr + "'>"+ prc.deptName +"</font></td>"
				      	 + "<td width='140' align='center'><font color='" + fontStr + "'>"+ prc.userName +"</font></td>"
				      	 + "<td width='140' align='center'><font color='" + fontStr + "'>"+ prc.totalAnnualLeaveDays +"</font></td>"
				      	 +"<td nowrap align='center'><font color='" + fontStr + "'>" + prc.usedAnnualLeaveDays + "</font></td>"
				     	 +"<td nowrap align='center'><font color='" + fontStr + "'>" + prc.remainAnnualLeaveDays + "</font></td>"
				         +"</tr>";
			}
			tableStr = tableStr + "</tbody></table>";
				
			$("#listDiv").append(tableStr);	
		}else{
		 	messageMsg("没有相关年假记录", "listDiv" ,'' ,380);
		}
	}else{
		alert(jsonObj.rtMsg);
	} */
}


function exprotAnnualLeave(){
	var url = contextPath+"/attendLeaveManage/exprotAnnualLeave.action";
	document.form1.action=url;
	document.form1.submit();
	return true;
}

function back(){
	parent.location.href=contextPath+"/system/core/base/attend/query/index.jsp";
}
</script>
</head>
<body class="" style="font-size:12px;padding-left: 10px;padding-right: 10px" onload="doOnload();">
<table id="datagrid" fit="true"></table>
<div id="toolbar" class = "topbar clearfix">	
	<form id="form1" name='form1' method="post">
		<table border="0" width="100%" cellspacing="0" cellpadding="3" class="small">
			<tr>
				<td class="Big">
					<div class="right fl clearfix">
<!-- 						<b><i class="glyphicon glyphicon-user"></i>&nbsp;年假统计结果（今年）</b> -->
<!-- 						<span style="float:right;"> -->
							<input id="deptIds" name="deptIds" type="hidden" value="<%=deptIds%>"/>
							<input type="button" id='exportAnnualLeave' name="exportAnnualLeave" class="btn-win-white fr" value="导出" onclick="exprotAnnualLeave();"/>
							<input type="button" value="返回" class="btn-win-white fr" onclick="back();"> 
<!-- 						</span> -->
					</div>
				</td> 
		</tr>
	</table>
	</form>
</div>
	<div id='listDiv'></div>
	
</body>

</html>

