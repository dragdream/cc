<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="background-color: #f2f2f2">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>已办结</title>
</head>
<script>
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath + "/projectController/getMyProjectListByStatus.action?status=5",
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			/* {field:'sid',checkbox:true,title:'ID',width:100}, */
			{field:'projectName',title:'项目名称',width:100},
			{field:'projectNum',title:'项目编码',width:100},
			{field:'endTime',title:'计划结束日期',width:80},
			{field:'managerName',title:'项目负责人',width:80},
			{field:'projectMemberNames',title:'项目成员',width:400},
			{field:'progress',title:'进度',width:60,formatter:function(value,rowData,rowIndex){
				return rowData.progress+"%";
			}},
			{field:'opt_',title:'操作',width:200,formatter:function(value,rowData,rowIndex){
				var isManagerOrCreater=rowData.isManagerOrCreater;
				if(isManagerOrCreater==1){
					return "<a href=\"#\" onclick=\"detail('"+rowData.uuid+"')\">详情</a>&nbsp;&nbsp;&nbsp;<a href=\"#\" onclick=\"copyTo('"+rowData.uuid+"')\">抄送</a>";
				}else{
					return "<a href=\"#\" onclick=\"detail('"+rowData.uuid+"')\">详情</a>";
				}
				
			}}
		]]
	});
	
}

//详情
function detail(uuid){
	var url=contextPath+"/system/subsys/project/projectdetail/index.jsp?uuid="+uuid;
	openFullWindow(url);
}

//抄送
function  copyTo(uuid){
	var url=contextPath+"/system/subsys/project/myproject/copyTo.jsp?uuid="+uuid;
	bsWindow(url ,"项目抄送",{width:"550",height:"120",buttons:
		[
		 {name:"确定",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="确定"){	
			var state=cw.commit();
			if(state){
				$.MsgBox.Alert_auto("抄送成功！");
				return true;
			}
           
		}else if(v=="关闭"){
			return true;
		}
	}});
}
</script>
<body onload="doInit()">
    <table id="datagrid" fit="true"></table>
</body>
</html>