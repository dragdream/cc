<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
//项目主键
  String  projectId=TeeStringUtil.getString(request.getParameter("projectId"));
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>问题追踪</title>
</head>
<script>
 var projectId="<%=projectId%>";
 var datagrid;
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/projectQuestionController/getQuestionListByProjectId.action?projectId="+projectId,
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			/* {field:'sid',checkbox:true,title:'ID',width:100}, */
			{field:'questionName',title:'问题名称',width:100,formatter:function(value,rowData,rowIndex){
				return "<a href=\"#\" onclick=\"detail("+rowData.sid+")\">"+rowData.questionName+"</a>";
			}},
			{field:'createrName',title:'提交人',width:60},
			{field:'operatorName',title:'处理人',width:60},
			{field:'questionLevel',title:'优先级',width:50},
			{field:'status',title:'状态',width:50,formatter:function(value,rowData,rowIndex){
				var status=rowData.status;
				if(status==0){
					return "待处理";
				}
				if(status==1){
					return "已处理";
				}
			}},
			{field:'result',title:'处理结果',width:100},
            
		]]
	});
	
}

//问题详情
function detail(sid){
	var url=contextPath+"/system/subsys/project/taskdetail/questionDetail.jsp?sid="+sid;
	bsWindow(url ,"问题详情",{width:"600",height:"290",buttons:
		[
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		 if(v=="关闭"){
			return true;
		}
	}});
	
}
</script>
<body onload="doInit()">
   <table id="datagrid" fit="true"></table>
</body>
</html>