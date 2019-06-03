<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/ztree.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath %>/common/My97DatePicker/WdatePicker.js"></script>

<script>
function doInit(){
	var url = contextPath+"/flowArchiveController/getArchiveList.action";

	var datagrid = $('#datagrid').datagrid({
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		url : url,
		toolbar : '#toolbar',
		title : '',
		iconCls : 'icon-save',
		pagination : true,
		singleSelect:false,
		pageSize : 10,
		checkbox:true,
		pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
		fit : true,
		fitColumns : true,
		nowrap : true,
		border : false,
		idField : 'sid',
		striped: true,
		remoteSort: false,
		columns : [ [
		 {
			title : '归档描述',
			field : 'archiveDesc',
			width : 100
		}, {
			field : 'version',
			title : '归档数据版本',
			width : 80
		},{
			field : 'crTimeStr',
			title : '归档时间',
			width : 100
		},{
			field : 'status',
			title : '归档状态',
			width : 100,
			formatter:function(value,rowData,rowIndex){
				var statusDesc="";
				var status=rowData.status;
				if(status==1){
					statusDesc="归档成功";
				}else{
					statusDesc="归档失败";
				}
				return statusDesc;
			}
		},{
			field : 'ope_',
			title : '操作',
			width : 50,
			formatter:function(value,rowData,rowIndex){
				var renderStr = [];
				renderStr.push("<a href='javascript:void()' onclick='del(\""+rowData.sid+"\")'>刪除</a>");
				return renderStr.join("&nbsp;&nbsp;");
			}
		}
		]]
	});
}




function del(sid){
	$.MsgBox.Confirm ("提示", "是否确认删除该归档数据？", function(){
		var url = contextPath+"/flowArchiveController/del.action";
		var json = tools.requestJsonRs(url,{sid:sid});
		if(json.rtState){
			$.MsgBox.Alert_auto("删除成功！");
			$("#datagrid").datagrid("reload");
		}else{
			$.MsgBox.Alert_auto("删除失败！");
		}	
	});
}



//我要归档
function  archive(){
	var url=contextPath+"/system/core/workflow/flowrun/archive/archive.jsp";
	bsWindow(url ,"流程归档",{width:"600",height:"270",buttons:
		[
         {name:"确定",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="确定"){
		    cw.commit();
		}else if(v=="关闭"){
			return true;
		}
	}});
}
</script>

</head>
<body onload="doInit()"  style="font-size:12px;padding-left: 10px;padding-right: 10px">
<div id="toolbar" class="topbar clearfix" style=""> 
	
	
	<div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/gzl/guidang.png">
		<span class="title">数据归档 </span>
	</div>
	<div class = "right fr clearfix">
		<input type="button" value="我要归档" onclick="archive();" class="btn-win-white"/>
	</div>
</div>
<table id="datagrid"></table>
</body>
</html>