<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String userId = request.getParameter("userId");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp" %>
<title>加班历史记录</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="<%=contextPath%>/system/core/base/attend/js/attend.js"></script>

<script type="text/javascript">
function  doOnload(){
	queryOvertime();
}
/**
 *查询管理
 */
var datagrid;
function queryOvertime(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+'/attendOvertimeManage/getOvertimeEasyui.action?status=9&userId=<%=userId%>',
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
			{field:'createTimeDesc',title:'申请时间',width:100},
			{field:'leaderName',title:'审批人员',width:100},
			{field:'startTimeDesc',title:'开始时间',width:100},
			{field:'endTimeDesc',title:'结束时间',width:100},
			{field:'overHours',title:'加班时长（小时）',width:100},
			{field:'overtimeDesc',title:'加班内容',width:100,formatter:function(value,rowData,rowIndex){
				var opt = "<a href='javascript:void(0);' onclick='attendOvertimeInfo(" + rowData.sid + ")' title='"+rowData.overtimeDesc+"'>" +rowData.overtimeDesc  + "</a>";
				return opt;
			}}
		]]
	});
	
	$(".datagrid-header-row td div span").each(function(i,th){
		var val = $(th).text();
		 $(th).html("<label style='font-weight: bolder;'>"+val+"</label>");
	});
	// 	var jsonObj = tools.requestJsonRs(url);
// 	if(jsonObj.rtState){
// 		var prcs = jsonObj.rtData;
// 		$("#listDiv").empty();
// 		if(prcs.length > 0){
// 			var tableStr = "<table class='TableBlock' width='98%' align='center'><tbody id='listTbody'>";
// 		    	 tableStr = tableStr + "<tr class='TableHeader'>"
// 			      	 + "<td width='100' align='center'>申请时间</td>"
// 			      	 +"<td nowrap width='100' align='center'>审批人员  </td>"
// 			     	 +"<td nowrap  width='100'  align='center'>开始时间</td>"
// 			     	 +"<td nowrap  width='60'  align='center'>结束时间</td>"
// 			      	 +"<td nowrap  width=''  align='center'>加班内容</td>"
// 			      	// +"<td nowrap  width='80' align='center'>操作</td>"
// 			         +"</tr>";
// 			for(var i = 0;i<prcs.length ; i++){
// 				var prc = prcs[i];
// 				var id = prc.sid;
// 				var  fontStr = "";
// 				var overtimeDesc = prc.overtimeDesc.length>20?prc.overtimeDesc.substring(0,20)+"...":prc.overtimeDesc;
// 				tableStr = tableStr +"<tr  class='TableData'>"
// 				      	 + "<td width='140' align='center'><font color='" + fontStr + "'>"+ prc.createTimeDesc +"</font></td>"
// 				      	 +"<td nowrap align='center'><font color='" + fontStr + "'>" + prc.leaderName + "</font></td>"
// 				     	 +"<td nowrap align='center'><font color='" + fontStr + "'>" + prc.startTimeDesc + "</font></td>"
// 				     	 +"<td nowrap align='center'><font color='" + fontStr + "'>" + prc.endTimeDesc + "</font></td>"
// 				      	 +"<td nowrap align='center'><font color='" + fontStr + "' title='"+prc.overtimeDesc+"'>" + overtimeDesc  + "</font></td>"
// 				      	/*  +"<td nowrap align='center'>"
// 				      	 +"<a href='javascript:void(0);' data-toggle=\"modal\" data-target=\"#myAffairModal\" onclick='toCreateOrUpdate(\"" + id + "\");'> 编辑 </a>"
// 				      	 +"&nbsp;&nbsp;<a href='javascript:void(0);' onclick='deleteAttendOut(\"" + id + "\",this);'>删除 </a>"
// 				      	 +"</td>" */
// 				         +"</tr>";
// 			}
// 			tableStr = tableStr + "</tbody></table>";
				
// 			$("#listDiv").append(tableStr);	
// 		}else{
// 		 	messageMsg("没有相关加班历史信息", "listDiv" ,'' ,380);
// 		}
// 	}else{
// 		alert(jsonObj.rtMsg);
// 	}
}


/**
 * 填出新建日程
 */
function toCreateOrUpdate(id){
	var url = contextPath + "/system/core/base/attend/out/addOrUpdate.jsp?sid=" + id;
	bsWindow(url ,"外出申请",{width:"600",height:"240",buttons:
		[
		 {name:"保存",classStyle:"btn btn-primary"},
	 	 {name:"关闭",classStyle:"btn btn-primary"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
			//cw.commit();
			var isStatus = cw.doSaveOrUpdate();
			if(isStatus){
				window.location.reload();
				return true;
			}
		}else if(v=="关闭"){
			return true;
		}
	}});
}
</script>
</head>
<body class="" style="overflow:hidden;font-size:12px" onload="doOnload();">
	<table id="datagrid" fit="true"></table>
	<div id='listDiv'></div>
</body>

</html>

