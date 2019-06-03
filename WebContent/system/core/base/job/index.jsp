<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%
	TeePerson loginUser = (TeePerson)session.getAttribute(TeeConst.LOGIN_USER);
	int uuid = loginUser.getUuid();
%>
<title>定时任务</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<script type="text/javascript" charset="UTF-8">
function doInit(){
	getInfoList();
}
var datagrid;
function getInfoList(){
	datagrid = $('#datagrid').datagrid({
		url : contextPath + '/job/datagrid.action' ,
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		toolbar : '#toolbar',
		//iconCls : 'icon-save',
		iconCls:'',
		pagination : true,
		pageSize : 20,
		/* pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ], */
		fit : true,
		fitColumns : true,
		nowrap : true,
		border : false,
		idField : 'sid',
		singleSelect:true,
		columns : [ [
			{
				field : 'id',
				title : '任务标识',
				width : 10,
				formatter : function(value, rowData, rowIndex) {
					return "<span>" + value + "</span>" ;
				}
			},
			{
				field : 'body2',
				title : '任务名称',
				width : 10,
				formatter : function(value, rowData, rowIndex) {
					return "<span>" + value + "</span>" ;
				}
			},{
				field : 'type',
				title : '任务类型',
				width : 10,
				formatter : function(value, rowData, rowIndex) {
					if(value==1){
						return "Java类";
					}else if(value==2){
						return "Http接口";
					}
				}
			},
			{
				field : 'expDesc',
				title : '定时描述',
				width : 10
			},
			{
				field : 'runNode',
				title : '执行节点',
				width : 10
			},
			{
				field : 'status',
				title : '状态',
				width : 10,
				formatter : function(value, rowData, rowIndex) {
					if(value==1){
						return "<span style='color:green'>运行</span>";
					}else if(value==0){
						return "<span style='color:red'>停止</span>";
					}
				}
			},
			{
				field : '_',
				title : '操作',
				width : 10,
				formatter : function(value, rowData, rowIndex) {
					var render = ["<a href='#' onclick=\"edit('"+rowData.id+"')\">编辑</a>"];
					if(rowData.status==0){
						render.push("<a href='#' onclick=\"changeStatus('"+rowData.id+"',1)\">开启</a>");
					}else{
						render.push("<a href='#' onclick=\"changeStatus('"+rowData.id+"',0)\">关闭</a>");
					}
					render.push("<a href='#' onclick=\"del('"+rowData.id+"')\">删除</a>");
					
					return render.join("&nbsp;&nbsp;");
				}
			}
		] ]
	});		
}

function edit(id){
	window.location = "addOrUpdate.jsp?id="+id;
}

function del(id){
	if(window.confirm("是否确定删除该任务")){
		tools.requestJsonRs(contextPath+"/job/delete.action?id="+id);
		 $('#datagrid').datagrid("reload");
	}
}

function changeStatus(id,status){
	tools.requestJsonRs(contextPath+"/job/changeStatus.action?id="+id+"&status="+status);
	$('#datagrid').datagrid("reload");
}
</script>
<style>
	.t_btns>button{
		padding:5px 8px;
		padding-left:22px;
		text-align:right;
		background-repeat:no-repeat;
		background-position:6px center;
		background-size:17px 17px;
		border-radius:5px;
		background-color:#e6f3fc;
		border:none;
		color:#000;
		outline:none;
		font-size: 12px;
		border: #abd6ea solid 1px ;
	}
</style>
</head>
<body onload="doInit();" style="padding-left: 10px;padding-right: 10px">
<table id="datagrid" fut="true"></table>
<div id="toolbar" class = "topbar clearfix">
	<div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/xzbg/tpgl/icon_toupiaochaxun.png">
		<span class="title">定时任务</span>
	</div>
	<div class = "right fr">
		<button type="button" onclick="window.location='addOrUpdate.jsp'" class="modal-menu-test btn-win-white">新建</button>
    </div>
</div>
</body>
</html>