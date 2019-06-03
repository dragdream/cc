<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String status = TeeStringUtil.getString(request.getParameter("status"), "0");//状态
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<title>招聘计划管理</title>
<script type="text/javascript">

function doInit(){
	getInfoList();
}

var datagrid;
function getInfoList(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/recruitPlanController/getPlanApprovalList.action?planStatus=<%=status%>",
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		idField:'sid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'planNo',title:'计划编号',width:50},
			{field:'planName',title:'计划名称',width:100},
			{field:'planRecrNum',title:'招聘人数',width:40,formatter:function(value,rowData,rowIndex){
				if(value){
					value += "（人）";
				}
				return value;
			}},
			{field:'startDateStr',title:'开始日期',width:80},
			{field:'planStatus',title:'计划状态',width:40,formatter:function(value, rowData, rowIndex){
				return planStatusFunc(value);
			}},
			{field:'2',title:'操作',width:60,formatter:function(value, rowData, rowIndex){
				var str = "<a href='javascript:void(0)' onclick='showInfoFunc("+rowData.sid+")'>详细信息 </a>&nbsp;&nbsp;";
				var planStatus = rowData.planStatus
				if(planStatus !="1"){
					str += "&nbsp;&nbsp;<a href='javascript:void(0)' onclick='setPlanStatusFunc("+rowData.sid+",1)'>批准</a>";
				}
				if(planStatus !="2"){
					str += "&nbsp;&nbsp;<a href='javascript:void(0)' onclick='setPlanStatusFunc("+rowData.sid+",2)'>不批准 </a>";
				}
				
				return str;
			}}
		]]
	});
}



/**
 * 编辑信息
 */
function setPlanStatusFunc(sid,status){
  var title = "编辑招聘计划信息";
  var buttonName = "批准";
  if(status =="2"){
	  buttonName = "不批准";
  }
  var url = contextPath + "/system/core/base/hr/recruit/plan/setPlanStatus.jsp?sid=" + sid + "&status=" + status;
  bsWindow(url ,title,{width:"750",height:"320",buttons:
		[
		 {name:buttonName,classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="批准" || v=="不批准" ){
			var json=cw.doSaveOrUpdate();
			if(json.rtState){
				$.MsgBox.Alert_auto("操作成功！",function(){
					datagrid.datagrid("reload");
					parent.getLeaderCount();
				});
				return true;
			}else{
				$.MsgBox.Alert_auto("操作失败！");
			}
			
		}else if(v=="关闭"){
			return true;
		}
	}});
}


/**
 * 详情信息
 */
function showInfoFunc(sid){
  var title = "招聘计划详细信息";
  var url = contextPath + "/system/core/base/hr/recruit/plan/recruitPlanDetail.jsp?sid=" + sid;
  bsWindow(url ,title,{width:"850",height:"360",buttons:
		[
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		if(v=="关闭"){
			return true;
		}
	}});
}


/**
 * 获取选中值
 */
function getSelectItem(){
	var selections = $('#datagrid').datagrid('getSelections');
	var ids = "";
	for(var i=0;i<selections.length;i++){
		ids+=selections[i].sid;
		if(i!=selections.length-1){
			ids+=",";
		}
	}
	return ids;
}


function planStatusFunc(ids){
	var str = "";
	if(ids == "0"){
		str = "待审批";
	}else if(ids == "1"){
		str = "<font color='green'>已批准</font>";
	}else if(ids == "2"){
		str = "<font color='red'>未批准</font>";
	}
	return str;
}


function setPlanStatusFunc1(sid,status){
	$.MsgBox.Alert_auto(sid + ">>" + status);
	
}




</script>
</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px">
	<table id="datagrid" fit="true" ></table>
	<div id="toolbar">
		
	</div>
</body>
</html>