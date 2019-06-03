<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp"%>
	<title>群组管理</title>
	
	
<script type="text/javascript" >

var datagrid;
/**
   初始化列表
 */
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/messageGroupManage/selectListByLoginPerson.action",
		pagination:true,
		singleSelect:true,
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			/* {field:'sid',checkbox:true,title:'ID',width:100}, */
			{field:'orderNo',title:'排序号',width:120},
			{field:'groupName',title:'群组名称',width:100},
			{field:'groupSubject',title:'群组主题',width:100},
			{field:'groupIntroduction',title:'群组简介',width:100},
			{field:'toName',title:'群组成员',width:200},
			{field:'groupCreatorName',title:'创建人',width:80},
			{field:'ope_',title:'操作',width:160,formatter:function(value,rowData,rowIndex){
				var flag = rowData.groupFlag;
				var flagDesc = "取消激活";
				if(flag == 1){
					flagDesc = "激活";
					flag = 0;
				}else{
					flag = 1;
				}
				var opts = "<a href='#' onclick='updateFlagById(" + rowData.sid + "," + flag + ")'> " + flagDesc + "  </a>";      
					opts =opts+ "&nbsp;&nbsp;<a href='#' onclick='toEdit(" + rowData.sid + ")'>编辑  </a>";
					opts = opts+"&nbsp;&nbsp;<a href='#' onclick='deleteById(" + rowData.sid + ")'>删除  </a>";
				return opts;
			}},
		]]});
	
}

/**
 * 跳转去新增页面
 */
function toAddUpdate()
{
	window.location.href = "<%=contextPath%>/system/core/base/message/group/addUpdate.jsp";
}

/**
 * 删除
 */
function deleteById(id){
	$.MsgBox.Confirm("提示","确定删除所选记录,删除后将不可恢复！",function(){
		var url = "<%=contextPath %>/messageGroupManage/deleteById.action";
		var jsonRs = tools.requestJsonRs(url,{id:id});
		if(jsonRs.rtState){
			parent.$.MsgBox.Alert_auto("删除成功！");
			datagrid.datagrid('unselectAll');
			datagrid.datagrid('reload');
		}else{
			$.MsgBox.Alert_auto(jsonRs.rtMsg);
		}
	
	});
}

/**
 * 更新激活装填
 */
function updateFlagById(id ,flag)
{
	var url = "<%=contextPath %>/messageGroupManage/updateFlagById.action";
	var jsonRs = tools.requestJsonRs(url,{id:id ,groupflag: flag});
	if(jsonRs.rtState){
		parent.$.MsgBox.Alert_auto(jsonRs.rtMsg);
		datagrid.datagrid('reload');
		//window.location.reload();
	}else{
		$.MsgBox.Alert_auto(jsonRs.rtMsg);
	}
	
}
/**
 * 跳转去编辑页面
 */
function toEdit(id)
{
	window.location.href = "<%=contextPath%>/system/core/base/message/group/addUpdate.jsp?id=" + id;
}
</script>
</head>
<body style="margin:0px;overflow:hidden" onload="doInit();">

<div  id="toolbar" class = "clearfix">
   <div  class="fl setHeight clearfix ">
	   <span style="font-size: 16px;font-family: MicroSoft YaHei;" class="fl">
        	  群组列表
       </span>
   </div> 
   <div class="fr setHeight">
     <input style="height: 25px;margin-right: 10px;"  type='button' value='新增群组' class='btn-win-white' onclick='toAddUpdate();'/>

	</div>
</div>
 
   <table id="datagrid" fit="true"></table>

</body>

</html>