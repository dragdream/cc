<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>任务发布</title>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<script type="text/javascript">
//初始化方法
var datagrid ;
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath + "/TeeTaskTemplateController/getMyPubTask.action",
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:200},
			{field:'taskName',title:'任务名称',width:200},
			{field:'crUserName',title:'创建人',width:150},
			{field:'crTimeStr',title:'创建时间',width:150},
			{field:'type',title:'上报类型',width:100,formatter:function(value,rowData,rowIndex){
                var taskType=rowData.taskType;
                if(taskType==1){
                	return "日报";
                }else if(taskType==2){
                	return "周报";
                }else if(taskType==3){
                	return "月报";
                }else if(taskType==4){
                	return "季报";
                }else if(taskType==5){
                	return "年报";
                }else if(taskType==6){
                	return "一次性";
                }
			}},
			{field:'modelDesc',title:'上报频次',width:200},
			{field:'pubStatus_',title:'发布状态',width:100,formatter:function(value,rowData,rowIndex){
				var pubStatus=rowData.pubStatus;
                if(pubStatus==0){
                	return "<span style=\"color:red\">未发布</span>";
                }else{
                	return "<span style=\"color:green\">已发布</span>";
                }
			}},
			{field:'opt_',title:'操作',width:250,formatter:function(value,rowData,rowIndex){
                var pubStatus=rowData.pubStatus;
				var opt="<a href=\"#\" onclick=\"addOrUpdate("+rowData.sid+")\">编辑</a>&nbsp;&nbsp;&nbsp;";
                opt+="<a href='#' onclick=\"setItems("+rowData.sid+")\">设置模板项</a>&nbsp;&nbsp;&nbsp;";
                if(pubStatus==0){//未发布
                	opt+="<a href='#' onclick=\"pubTask("+rowData.sid+")\">发布</a>&nbsp;&nbsp;&nbsp;";
                }else{
                	opt+="<a href='#' onclick=\"cancelPub("+rowData.sid+")\">取消发布</a>&nbsp;&nbsp;&nbsp;";
                }   
                opt+="<a href='#' onclick=\"delBySid("+rowData.sid+")\">删除</a>&nbsp;&nbsp;&nbsp;";
                return opt;
			}},
		]]
	});
	
	
}
//设置模板项目
function setItems(taskTemplateSid){
	var url=contextPath+"/system/subsys/informationReport/taskPublic/item/index.jsp?taskTemplateSid="+taskTemplateSid;
    openFullWindow(url);
}
//任务发布
function pubTask(sid){
	$.MsgBox.Confirm ("提示", "是否确认发布该任务？", function(){
		var url=contextPath+"/TeeTaskTemplateController/pubTask.action?sid="+sid;
		var json=tools.requestJsonRs(url,{});
		if(json.rtState){
			$.MsgBox.Alert_auto("发布成功！",function(){
				datagrid.datagrid("reload");
			});	
		}else{
			$.MsgBox.Alert_auto("发布失败！");
		}
	});
}
//取消发布
function cancelPub(sid){
	$.MsgBox.Confirm ("提示", "是否确认取消发布该任务？", function(){
		var url=contextPath+"/TeeTaskTemplateController/cancelPub.action?sid="+sid;
		var json=tools.requestJsonRs(url,{});
		if(json.rtState){
			$.MsgBox.Alert_auto("取消成功！",function(){
				datagrid.datagrid("reload");
			});	
		}else{
			$.MsgBox.Alert_auto("取消失败！");
		}
	});
}
//删除
function delBySid(sid){
	$.MsgBox.Confirm ("提示", "是否确认刪除该任务？", function(){
		var url=contextPath+"/TeeTaskTemplateController/delBySid.action";
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
//新增/编辑
function addOrUpdate(sid){
	window.location.href=contextPath+"/system/subsys/informationReport/taskPublic/addOrUpdate.jsp?sid="+sid;
}
</script>
</head>
<body  onload="doInit()" style="font-size:12px;padding-left: 10px;padding-right: 10px">
<div id="toolbar" class = "topbar clearfix">
	<div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/subsys/informationReport/imgs/icon_rwfb.png">
		<span class="title">任务发布</span>
	</div>
	<div class = "right fr clearfix">
		<input type="button" class="btn-win-white fl" onclick="addOrUpdate(0);" value="新建任务"/>
    </div>
</div>
<table id="datagrid" fit="true"></table>
</body>
</html>