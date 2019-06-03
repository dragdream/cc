<%@page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<%@ include file="/header/header2.0.jsp" %>
	<%@ include file="/header/easyui2.0.jsp" %>
<script type="text/javascript">
var datagrid;
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/favoriteRecord/list.action",
	    pagination:true,
	    singleSelect:false,
	    view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
	    toolbar:'#toolbar',//工具条对象
    	checkbox:true,
	    border:false,
	    idField:'sid',//主键列
	    fitColumns:true,//列是否进行自动宽度适应
	    columns:[[
	  			{field:'title',title:'标题（说明）',width:100,formatter:function(value,rowData,rowIndex){
					return "<a onclick=\"detail('"+rowData.url+"')\" href='javascript:void(0)'>"+rowData.title+"</a>";
				}},
				
	  			{field:'optTimeStr',title:'收藏时间',width:200,formatter:function(value,rowData,rowIndex){
	  				var prc = rowData;
		    		var optTime = prc.optTime;
		    		var optTimeStr = "";
		    		if(optTime > 0){
		    			optTimeStr = getFormatDateStr(optTime,'yyyy-MM-dd HH:mm');
		    		}
		    		return optTimeStr;
				}},
	  			{field:'opt_',title:'操作',width:80,formatter:function(value,rowData,rowIndex){
					return "<a style='color:red;' href='javascript:void(0);' onclick='del("+ rowData.sid + ",this)'>删除</a>";
				}}
	  		]]
	  	});
}   

function del(sid,obj){
	 $.MsgBox.Confirm ("提示", "确定删除此记录？",function(){
		 var url = contextPath+"/favoriteRecord/deleteFavoriteRecord.action";
			var json = tools.requestJsonRs(url,{sid:sid});
			if(json.rtState){
				parent.$.MsgBox.Alert_auto("删除成功！");
				doInit();
			}
	 });
}

function detail(url){
	openFullWindow(url);
}
</script>
 
</head>
<body onload="doInit()" style="overflow:hidden;padding-left: 10px;padding-right: 10px">
	<table id="datagrid" fit="true"></table>
	<div id="toolbar" class="topbar clearfix">
   <img class="title_img" src="<%=contextPath %>/system/frame/classic/imgs/icon/desktop/icon_scj.png" alt="" />
   &nbsp;<span class="title">收藏夹</span>
</div>
</body>
</html>
 