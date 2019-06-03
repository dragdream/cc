<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<title>加班管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="<%=contextPath%>/system/core/base/attend/js/attend.js"></script>
<script type="text/javascript">
var datagrid;
function  doOnload(){
	queryOvertime();
}
/**
 *查询管理
 */
function queryOvertime(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/attendOvertimeManage/getOvertime.action",
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
			{field:'createTimeDesc',title:'申请时间',width:120},
			{field:'leaderName',title:'审批人员',width:100},
			{field:'startTimeDesc',title:'开始时间',width:100},
			{field:'endTimeDesc',title:'结束时间',width:100},
			{field:'overHours',title:'加班时长（小时）',width:100},
			{field:'overtimeDesc',title:'加班内容',width:200,formatter:function(value,rowData,rowIndex){
				var overtimeDesc = rowData.overtimeDesc.length>20?rowData.overtimeDesc.substring(0,20)+"...":rowData.overtimeDesc;
				var str="<a href='javascript:void(0);' onclick='attendOvertimeInfo(" + rowData.sid + ")' title='"+rowData.overtimeDesc+"'>" + overtimeDesc  + "</a>";
		        return str;	
			}},
			{field:'allowDesc',title:'审批状态',width:80,formatter:function(value,rowData,rowIndex){
				var allow = rowData.allow;
				var allowDesc= "待审批";
				if(allow == '1'){
					allowDesc = "已批准";
				}else if(allow == '2'){
					allowDesc = "未批准";
				}
				return allowDesc;
			}},
			{field:'ope_',title:'操作',width:160,formatter:function(value,rowData,rowIndex){
	            var opts = "<a href='javascript:void(0);' data-toggle=\"modal\" data-target=\"#myAffairModal\" onclick='toCreateOrUpdate(\"" + rowData.sid + "\");'> 编辑 </a>";				
				opts = opts+"&nbsp;&nbsp;<a href='javascript:void(0);' onclick='deleteAttendOvertime(\"" + rowData.sid + "\",this);'>删除 </a>";
				return opts;
			}},
		]]});
}


/**
 * 填出新建日程
 */
function toCreateOrUpdate(id){
	if(id==0){//创建
		if(isOpenBisEngine(7)){
			createNewWork(52);
			return;
		}
	}
	
	var url = contextPath + "/system/core/base/attend/overtime/addOrUpdate.jsp?sid=" + id;
	bsWindow(url ,"加班申请",{width:"600",height:"240",buttons:
		[
		 {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
			//cw.commit();
			var isStatus = cw.doSaveOrUpdate();
			if(isStatus){
				//window.location.reload();
				$.MsgBox.Alert_auto("操作成功！");
				datagrid.datagrid('reload');
				return true;
			}
		}else if(v=="关闭"){
			return true;
		}
	}});
}

function toHistory(){
	window.location.href = contextPath + "/system/core/base/attend/overtime/history.jsp";
}


</script>
</head>
<body class="" onload="doOnload();">
   <div id="toolbar" class = "clearfix">
     <div class="left fl setHeight">
	     <input type="button" value="加班申请" class="btn-win-white fr" onclick="toCreateOrUpdate(0);">
	     &nbsp;
	     <input type="button" value="加班历史记录" class="btn-win-white fr" onclick="toHistory();"> 
     </div> 
     </div>
 
     <table id="datagrid" fit="true"></table>
</body>

</html>