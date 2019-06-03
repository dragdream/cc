<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<%
TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>报表维护设置管理</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath %>/system/core/base/fileNetdisk/dialog/css/dialog.css"/>
<script type="text/javascript">
var datagrid;
function doInit(){
	getFolderData();
}

//获取文件夹列表
function getFolderData(){
	datagrid = $('#datagrid').datagrid({
		url : contextPath + '/quieeReportController/getFolderList.action',
		toolbar : '#toolbar',
		//title : title,
		//iconCls : 'icon-save',
		iconCls:'',
		pagination : true,
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
		fit : true,
		fitColumns : true,
		nowrap : true,
		border : false,
		idField : 'sid',
		singleSelect:false,

		frozenColumns : [ [
		    {field:'sid',checkbox:true},{
			field : 'reportName',
			title : '名称',
			width : 300,
			sortable : true
		} ] ],
		columns : [ [  {
			field : 'crTimeStr',
			title : '创建日期',
			width : 300,
			sortable : true
		 } ,  {
			field : '_optmanage',
			title : '操作',
			width : 300,
			formatter : function(value, rowData, rowIndex) {
		        var optStr="";
				optStr += "<a href='javascript:void(0);' onclick='editReportFolder(\"" +rowData.sid + "\");'>编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;";	
				optStr += "<a href='javascript:void(0);' onclick='deleteSingleFunc(\"" +rowData.sid + "\");'>删除</a>&nbsp;";	
				
				return "<div>" + optStr + "</span>";
			} 
	 } ] ],onLoadSuccess:function(rowIndex, rowData){}
	 ,
     onClickRow: function(rowIndex, rowData){
         $("input[type='checkbox']").each(function(index, el){
             if (el.disabled == true) {
             	datagrid.datagrid('unselectRow', index - 1);
             }
         });
     },
     onSelectAll:function(rowIndex, rowData){
     	  $("input[type='checkbox']").each(function(index, el){
               if (el.disabled == true) {
               	datagrid.datagrid('unselectRow', index - 1);
               	el.checked=false;
               }
           });
     }
	});
	
  
}


//新建文件夹
function newFolder(){	
	var url = contextPath + "/system/subsys/report/quiee/addOrUpdateFolder.jsp?parentId=0";
	bsWindow(url ,"新建文件夹",{width:"650",height:"440",buttons:
		[
		 {name:"保存",classStyle:"btn btn-primary"},
	 	 {name:"关闭",classStyle:"btn btn-primary"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
			//cw.commit();
			var isStatus = cw.doSaveOrUpdate();
			if(isStatus){
				window.location.reload();
				//刷新树页面
				 //parent.$("#file_main").zTreeObj.refresh();
				return true;
			}
		}else if(v=="关闭"){
			return true;
		}
	}});	
}

/**
 * 批量删除文件
 */
function batchDelete(){
	var selections = $('#datagrid').datagrid('getSelections');
	if(selections.length==0){
		alert("至少选择一项");
		return ;
	}
	if(confirm("确定要删除所选文件或文件夹？")){
		var ids = "";
		for(var i=0;i<selections.length;i++){
			ids+=selections[i].sid;
			if(i!=selections.length-1){
				ids+=",";
			}
		}
		deleteReportsFunc(ids);
	}
}

/**
 * 批量删除文件
 */
function deleteReportsFunc(ids){
  var url = contextPath +  "/quieeReportController/deleteReportsBySid.action?sids=" + ids;
	var para = {};
	var jsonRs = tools.requestJsonRs(url,para);
	if(jsonRs.rtState){
		$("#datagrid").datagrid("reload"); 
	}else{
		alert(jsonRs.rtMsg);
	}
}


/**
 * 单个删除
 */
 function deleteSingleFunc(ids){
	 if(confirm("确定要删除所选文件夹？")){
	    var url = contextPath +  "/quieeReportController/deleteReportsBySid.action?sids=" + ids;
		var para = {};
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			$("#datagrid").datagrid("reload"); 
		}else{
			alert(jsonRs.rtMsg);
		}
	}
}
/**
 * 编辑文件夹
 */
 function editReportFolder(sid){
	 var url = contextPath + "/system/subsys/report/quiee/addOrUpdateFolder.jsp?currentFolderSid=" + sid;
		bsWindow(url ,"编辑文件夹",{width:"650",height:"440",buttons:
			[
			 {name:"保存",classStyle:"btn btn-primary"},
		 	 {name:"关闭",classStyle:"btn btn-primary"}
			 ]
			,submit:function(v,h){
			var cw = h[0].contentWindow;
			if(v=="保存"){
				//cw.commit();
				var isStatus = cw.doSaveOrUpdate();
				if(isStatus){
					window.location.reload();
					//刷新树页面
					//parent.$("#file_main").zTreeObj.refresh();
					return true;
				}
			}else if(v=="关闭"){
				return true;
			}
		}});	
}
</script>
</head>
<body onload="doInit()" style="background:#f6f7f9;">
 <table id="datagrid"></table>
<div id="toolbar">
  <div class="moduleHeader">
	<b>报表维护管理</b>
  </div>
     <center>
     <input onclick="newFolder();" class="btn btn-primary" type = "button" value = "新建报表文件夹"/>
     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     <input onclick="batchDelete();" class="btn btn-primary" type = "button" value = "批量删除文件夹"/>
     </center>
     <br>
</div>
</body>
</html>