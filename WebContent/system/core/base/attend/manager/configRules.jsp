<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<title>固定排班</title>
<script type="text/javascript">
//初始化
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath + "/attendConfigRulesController/datagrid.action",
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:200},
			{field:'configName',title:'排班名称',width:120},
			{field:'userNames',title:'应用人员',width:200},
			{field:'deptNames',title:'应用部门',width:200},
			{field:'roleNames',title:'应用角色',width:200},
			{field:'ope_',title:'操作',width:80,formatter:function(value,rowData,rowIndex){
				var  opt="<a href=\"#\" onclick=\"addOrUpdate("+rowData.sid+")\">编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"#\" onclick=\"del("+rowData.sid+")\">删除</a>"
			    return opt;
			}}
		]]
	});
}



//新建/编辑
function addOrUpdate(sid){
	var url=contextPath+"/system/core/base/attend/manager/editConfigRules.jsp?sid="+sid;
	openFullWindow(url);
}



//刪除
function del(sid){
	  $.MsgBox.Confirm ("提示", "是否确认删除该固定排班？", function(){
		 var url=contextPath+"/attendConfigRulesController/delBySid.action";
		 var json=tools.requestJsonRs(url,{sid:sid});
		 if(json.rtState){
			 $.MsgBox.Alert_auto("删除成功！",function(){
				 datagrid.datagrid("reload");
			 });
		 }else{
			 $.MsgBox.Alert_auto("删除失败！");
		 }
	  });
}
</script>
</head>
<body onload="doInit()">
   <div id="toolbar" class="topbar clearfix" >
       <div class="fl left">
           <input type="button"  class="btn-win-white"  value="新增排班" onclick="addOrUpdate(0);"/>
       </div>
   </div>
   
   <table id="datagrid" fit="true"></table>
</body>
</html>