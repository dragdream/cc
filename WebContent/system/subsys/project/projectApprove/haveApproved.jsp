<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>已审批</title>
</head>
<script>
var  datagrid;
function doInit(){
		datagrid = $('#datagrid').datagrid({
			url:contextPath + "/projectController/getHaveApprovedProjectList.action",
			view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
			pagination:true,
			singleSelect:true,
			toolbar:'#toolbar',//工具条对象
			checkbox:false,
			border:false,
			
			fitColumns:true,//列是否进行自动宽度适应
			columns:[[
				/* {field:'sid',checkbox:true,title:'ID',width:100}, */
				{field:'projectName',title:'项目名称',width:100,formatter:function(value,rowData,rowIndex){
					
					return "<a href=\"#\" onclick=\"detail('"+rowData.uuid+"')\">"+rowData.projectName+"</a>";
				}},
			    {field:'projectNum',title:'项目编码',width:100},
			    {field:'endTime',title:'计划结束日期',width:100},
			    {field:'managerName',title:'项目负责人',width:100},
			    {field:'projectMemberNames',title:'项目成员',width:400},
			    {field:'progress',title:'进度',width:100,formatter:function(value,rowData,rowIndex){
				    return rowData.progress+"%";
			    }},
			    {field:'status',title:'状态',width:100,formatter:function(value,rowData,rowIndex){
				    var status=rowData.status;
				    var desc="";
				    if(status==3){
				    	desc="办理中";
				    }else if(status==4){
				    	desc="挂起中";
				    }else if(status==5){
				    	desc="已结束";
				    }else if(status==6){
				    	desc="已拒绝";
				    }
			    	return desc;
			    }},
			    
	            
			]]
		});
		
}
//详情
function detail(uuid){
	var url=contextPath+"/system/subsys/project/projectdetail/info.jsp?uuid="+uuid;
	openFullWindow(url);
}



</script>

<body onload="doInit()" style="padding-right: 10px;padding-left: 10px">
   <table id="datagrid" fit="true"></table>
</body>
</html>