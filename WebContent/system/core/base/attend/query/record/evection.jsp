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
<title>出差历史记录</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="<%=contextPath%>/system/core/base/attend/js/attend.js"></script>

<script type="text/javascript">
function  doOnload(){
	queryEvection();
}
/**
 *查询管理
 */
var datagrid;
function queryEvection(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+'/attendEvectionManage/getEvectionEasyui.action?status=9&userId=<%=userId%>',
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		queryParams:{showFlag:1},
		nowrap:false,
		columns:[[
			//{field:'id',checkbox:true,title:'ID',width:100},
			{field:'createTimeStr',title:'申请时间',width:100},
			{field:'leaderName',title:'审批人员',width:100},
			{field:'startTimeStr',title:'开始时间',width:100},
			{field:'endTimeStr',title:'结束时间',width:100},
			{field:'days',title:'出差天数',width:80},
			{field:'evectionAddress',title:'出差地址',width:120},
			{field:'status',title:'出差状态',width:100,formatter:function(value,rowData,rowIndex){
				var status=rowData.status;
				if(status==1){//已归来
					return "<span style='color:green'>已归来</span>";
				}else{//未归来
					return "<span style='color:red'>未归来</span>";
				}
			}},
			{field:'evectionDesc',title:'出差原因',width:100,formatter:function(value,rowData,rowIndex){
				var opt = "<a href='javascript:void(0);' onclick='attendEvectionInfo(" + rowData.sid + ")' title='"+rowData.evectionDesc+"'>" +rowData.evectionDesc  + "</a>";
				return opt;
			}}
		]]
	});
	
	$(".datagrid-header-row td div span").each(function(i,th){
		var val = $(th).text();
		 $(th).html("<label style='font-weight: bolder;'>"+val+"</label>");
	});
	/* var jsonObj = tools.requestJsonRs(url);
	if(jsonObj.rtState){
		var prcs = jsonObj.rtData;
		$("#listDiv").empty();
		if(prcs.length > 0){
			var tableStr = "<table class='TableBlock' width='98%' align='center'><tbody id='listTbody'>";
		    	 tableStr = tableStr + "<tr class='TableHeader'>"
			      	 + "<td width='100' align='center'>申请时间</td>"
			      	 +"<td nowrap width='100' align='center'>审批人员  </td>"
			     	 +"<td nowrap  width='100'  align='center'>开始时间</td>"
			     	 +"<td nowrap  width='60'  align='center'>结束时间</td>"
			     	 +"<td nowrap  width=''  align='center'>出差地址</td>"
			      	 +"<td nowrap  width=''  align='center'>出差原因</td>"
			      	// +"<td nowrap  width='80' align='center'>操作</td>"
			         +"</tr>";
			for(var i = 0;i<prcs.length ; i++){
				var prc = prcs[i];
				var id = prc.sid;
				var  fontStr = "";
			    var evectionDesc = prc.evectionDesc.length>20?prc.evectionDesc.substring(0,20)+"...":prc.evectionDesc;
				tableStr = tableStr +"<tr  class='TableData'>"
				      	 + "<td width='140' align='center'><font color='" + fontStr + "'>"+ prc.createTimeStr +"</font></td>"
				      	 +"<td nowrap align='center'><font color='" + fontStr + "'>" + prc.leaderName + "</font></td>"
				     	 +"<td nowrap align='center'><font color='" + fontStr + "'>" + prc.startTimeStr + "</font></td>"
				     	 +"<td nowrap align='center'><font color='" + fontStr + "'>" + prc.endTimeStr + "</font></td>"
				     	 +"<td nowrap align='center'><font color='" + fontStr + "'>" + prc.evectionAddress + "</font></td>"
				     	 +"<td nowrap align='center'><font color='" + fontStr + "'><a href='javascript:void(0);' onclick='attendEvectionInfo(" + id + ")' title='"+prc.evectionDesc+"'>" + evectionDesc  + "</a></font></td>"
				      	/*木有  +"<td nowrap align='center'>"
				      	 +"<a href='javascript:void(0);' data-toggle=\"modal\" data-target=\"#myAffairModal\" onclick='toCreateOrUpdate(\"" + id + "\");'> 编辑 </a>"
				      	 +"&nbsp;&nbsp;<a href='javascript:void(0);' onclick='deleteAttendOut(\"" + id + "\",this);'>删除 </a>"
				      	 +"</td>" */
				      	/*+"</tr>";
			}
			tableStr = tableStr + "</tbody></table>";
				
			$("#listDiv").append(tableStr);	
		}else{
		 	messageMsg("没有相关出差历史记录", "listDiv" ,'' ,380);
		}
	}else{
		alert(jsonObj.rtMsg);
	} */
}


</script>
</head>
<body class="" style="overflow:hidden;font-size:12px" onload="doOnload();">
	<table id="datagrid" fit="true"></table>
	<div id='listDiv'></div>
	
</body>

</html>

