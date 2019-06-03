<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<title>外出管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
var datagrid;
/**
   初始化列表
 */
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/TeeFootPrintRangeController/getAllList.action",
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
			{field:'rangeName',title:'围栏名称',width:120},
			{field:'userNames',title:'用户权限',width:200},
			{field:'ope_',title:'操作',width:160,formatter:function(value,rowData,rowIndex){
				var opts = "<a href='javascript:void(0);'  onclick='toCreateOrUpdate(\"" + rowData.sid + "\");'> 编辑 </a>&nbsp;&nbsp;"; 
				opts += "<a href='javascript:void(0);'  onclick='design(\"" + rowData.sid + "\");'> 设计 </a>&nbsp;&nbsp;"; 
				opts += "<a href='javascript:void(0);'  onclick='del(\"" + rowData.sid + "\");'> 删除 </a>"; 	
				return opts;
			}},
		]]});
	
}


/**
 * 新建/编辑
 */
function toCreateOrUpdate(id){
	var title="";
	var mess="";
	if(id==0){//创建
		title="新建电子围栏";
	    mess="新建成功！";
	}else{
		title="编辑电子围栏";
		 mess="编辑成功！";
	}
	
	var url = contextPath + "/system/subsys/footprintrange/addOrUpdate.jsp?sid=" + id;
	bsWindow(url ,title,{width:"600",height:"180",buttons:[
		 {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ],submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
			var json= cw.doSaveOrUpdate();
			if(json.rtState){
				$.MsgBox.Alert_auto(mess);
				datagrid.datagrid('reload');
				return true;
			}
		}else if(v=="关闭"){
			return true;
		}
	}});
}



/**
 * 删除
 */
function del(sid){
	  $.MsgBox.Confirm ("提示", "是否确定删除该电子围栏信息？", function(){
		  var url = contextPath + "/TeeFootPrintRangeController/delBySid.action";
			var para = {sid:sid};
			var json = tools.requestJsonRs(url,para);
			if(json.rtState){					
				$.MsgBox.Alert_auto("删除成功！");
				datagrid.datagrid('reload');
			}   
	  });
}


//设计
function design(sid){
	var url=contextPath+"/system/subsys/footprintrange/design.jsp?sid="+sid;
	openFullWindow(url);
}
</script>
</head>
<body style="margin:0px;overflow:hidden" onload="doInit();">
<div id="toolbar" class = "clearfix">
   <div class="left fl setHeight">
	   <input type="button" value="新建围栏"  class="btn-win-white fr" onclick="toCreateOrUpdate(0);">
   </div> 
</div>
 
   <table id="datagrid" fit="true"></table>

</body>

</html>