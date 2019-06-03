<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<%@ include file="/header/header2.0.jsp" %>
	<%@ include file="/header/easyui2.0.jsp" %>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>历史日志</title>
	<script>
	var  datagrid;
	var contextPath = "<%=contextPath%>";
	function doInit(){
		var json = tools.requestJsonRs(contextPath+"/docNumController/datagrid.action");
		var render = [];
		for(var i=0;i<json.rows.length;i++){
			render.push("<option value="+json.rows[i].sid+">"+json.rows[i].docName+"</option>");
		}
		$("#docNumId").append(render.join(""));
		initDataGrid();
	}

	function initDataGrid(){
		datagrid=$('#datagrid').datagrid({
			url:'<%=contextPath%>/docNumController/listHistory.action?docNumId='+$("#docNumId").val(),
			view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
			pagination:true,
			singleSelect:true,
			toolbar:'#toolbar',//工具条对象
			checkbox:true,
			border:false,
			idField:'sid',//主键列
			fitColumns : false,
			sortOrder: 'desc',
			striped: true,
			remoteSort: false,
			columns:[[
				{field:'docName',title:'所属文号',width:150},
				{field:'word',title:'文件字',width:150},
				{field:'year',title:'文件年',width:100},
				{field:'num',title:'当前文号值',width:100},
				{field:'runName',title:'所属流程',width:300,formatter:function(data,rowData){
					return "<a href='javascript:void(0)' onclick='detail("+rowData.runId+")'>"+data+"</a>";
				}},
				{field:'crUser',title:'创建人',width:120},
				{field:'crTime',title:'创建时间',width:130},
				{field:'opt_',title:'操作',width:100,formatter:function(value,rowData,rowIndex){
					return "<a href=\"#\" onclick=\"delBySid("+rowData.sid+")\">删除</a>";
				}},
			]]
		});
	}
	
	//根据主键删除
	function delBySid(sid){
		
		  $.MsgBox.Confirm ("提示", "删除将无法恢复，是否确认删除？", function(){
			  var  url=contextPath+"/docNumController/delHistoryBySid.action";
			  var  json=tools.requestJsonRs(url,{sid:sid});
			  if(json.rtState){
				  $.MsgBox.Alert_auto("删除成功！");
				  datagrid.datagrid("reload");
			  }else{
				  $.MsgBox.Alert_auto(json.rtMsg);
			  }
		  });
		
	}
	
	
	
	function change0(obj){
		initDataGrid();
	}

	function detail(runId){
		openFullWindow(contextPath+"/system/core/workflow/flowrun/print/index.jsp?runId="+runId+"&view=1","工作预览");
	}
	
	</script>
</head>
<body onload="doInit()" style="padding-left: 10px;padding-right: 10px">
	<table id="datagrid" fit="true"></table>
	<div id="toolbar" class="topbar clearfix">
	   <div class="fl left">
	     <img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/gzl/wenhao/icon_wenhaorizhi.png">
		 <span class="title">文号日志</span>
	   </div>
	   <div class="fr right">选择文号：
	      <select id="docNumId" class="BigSelect" onchange="change0(this)">
					<option value="0">全部</option>
		  </select>
	   </div>
	
	</div>
</body>

</html>