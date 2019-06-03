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
<title>外出历史记录</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="<%=contextPath%>/system/core/base/attend/js/attend.js"></script>

<script type="text/javascript">
function  doOnload(){
	queryOut();
}
/**
 *查询管理
 */
var datagrid;
function queryOut(){
<%-- 	var url =   "<%=contextPath %>/attendOutManage/getOutByCondition.action?status=1&deptId=<%=deptId%>&startDateDesc=<%=startDateDesc%>&endDateDesc=<%=endDateDesc%>"; --%>
	datagrid = $('#datagrid').datagrid({
		url:contextPath+'/attendOutManage/getOutByConditionEasyui.action?status=1&deptIds=<%=deptIds%>&startDateDesc=<%=startDateDesc%>&endDateDesc=<%=endDateDesc%>',
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		queryParams:{showFlag:1},
		columns:[[
			//{field:'id',checkbox:true,title:'ID',width:100},
			{field:'deptName',title:'部门',width:100},
			{field:'userName',title:'姓名',width:100},
			{field:'createTimeStr',title:'申请时间',width:100},
			{field:'leaderName',title:'审批人员',width:100},
			{field:'startTimeStr',title:'开始时间',width:100},
			{field:'endTimeStr',title:'结束时间',width:100},
			{field:'status',title:'外出状态',width:100,formatter:function(value,rowData,rowIndex){
				var status =rowData.status ;
				if(status==0){//未归来
					return "<span style='color:red'>未归来</span>";
				}else{//已归来
					return "<span style='color:green'>已归来</span>";
				}
			}},
			{field:'outDesc',title:'外出原因',width:120,formatter:function(value,rowData,rowIndex){
				var opt = "<a href='javascript:void(0);' onclick='attendOutInfo(" + rowData.sid + ")' title='"+rowData.outDesc+"'>" +rowData.outDesc  + "</a>";
				return opt;
			}},
			/* {field:'leaveDesc',title:'请假原因',width:100,formatter:function(value,rowData,rowIndex){
				var opt = "<a href='javascript:void(0);' onclick='attendLeaveInfo(" + rowData.sid + ")' title='"+rowData.leaveDesc+"'>" +rowData.leaveDesc  + "</a>";
				return opt;
			}}*/
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
			      	 +"<td nowrap width='100' align='center'>姓名 </td>"
			      	 + "<td width='100' align='center'>申请时间</td>"
			      	 +"<td nowrap width='100' align='center'>审批人员  </td>"
			     	 +"<td nowrap  width='100'  align='center'>开始时间</td>"
			     	 +"<td nowrap  width='60'  align='center'>结束时间</td>"
			      	 +"<td nowrap  width=''  align='center'>外出原因</td>"
			         +"</tr>";
			for(var i = 0;i<prcs.length ; i++){
				var prc = prcs[i];
				var id = prc.sid;
				var  fontStr = "";
				tableStr = tableStr +"<tr  class='TableData'>"
				      	 + "<td width='140' align='center'><font color='" + fontStr + "'>"+ prc.deptName +"</font></td>"
				      	 + "<td width='140' align='center'><font color='" + fontStr + "'>"+ prc.userName +"</font></td>"
				      	 + "<td width='140' align='center'><font color='" + fontStr + "'>"+ prc.createTimeStr +"</font></td>"
				      	 +"<td nowrap align='center'><font color='" + fontStr + "'>" + prc.leaderName + "</font></td>"
				     	 +"<td nowrap align='center'><font color='" + fontStr + "'>" + prc.startTimeStr + "</font></td>"
				     	 +"<td nowrap align='center'><font color='" + fontStr + "'>" + prc.endTimeStr + "</font></td>"
				      	 +"<td nowrap align='center'><font color='" + fontStr + "'>" + prc.outDesc  + "</font></td>"
				         +"</tr>";
			}
			tableStr = tableStr + "</tbody></table>";
				
			$("#listDiv").append(tableStr);	
		}else{
		 	messageMsg("没有相关外出信息", "listDiv" ,'' ,380);
		}
	}else{
		alert(jsonObj.rtMsg);
	} */
}

function back(){
	parent.location.href=contextPath+"/system/core/base/attend/query/index.jsp";
}

function exportOut(){
	var url = contextPath+"/attendOutManage/exportOut.action?status=1&deptIds=<%=deptIds%>&startDateDesc=<%=startDateDesc%>&endDateDesc=<%=endDateDesc%>";
	document.form1.action=url;
	document.form1.submit();
	return true;
}
</script>
</head>
<body class="" style="font-size:12px;padding-left: 10px;padding-right: 10px" onload="doOnload();">
<table id="datagrid" fit="true"></table>
<div id="toolbar" class = "topbar clearfix">
<form id="form1" name='form1' method="post">
		<table border="0" width="100%" cellspacing="0" cellpadding="3"
		class="small">
		<tr>
			<td class="Big">
				<div class = "right fl clearfix">
<%-- 					<b><i class="glyphicon glyphicon-user"></i>&nbsp;外出统计结果</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(<%=startDateDesc %>~<%=endDateDesc %>)<span style="float:right;"> --%>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>(<%=startDateDesc %>~<%=endDateDesc %>)</span>
					<input type="button" id='exportOuts' name="exportOuts" class="btn-win-white fr" value="导出" onclick="exportOut();"/>
					<input type="button" value="返回" class="btn-win-white fr" onclick="back();"> </span>
				</div>
			</td> 
		
		</tr>
	</table>
	</form>
	</div>
	<div id='listDiv'></div>
</body>

</html>

