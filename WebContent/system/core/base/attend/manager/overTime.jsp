<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<title>加班审批管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="<%=contextPath%>/system/core/base/attend/js/attend.js"></script>
<style>
	.modal-test{
		width: 564px;
		height: 230px;
		position: absolute;
		display: none;
		z-index: 999;
	}
	.modal-test .modal-header{
		width: 100%;
		height: 50px;
		background-color:#8ab0e6;
	}
	.modal-test .modal-header .modal-title{
		color: #fff;
		font-size: 16px;
		line-height:50px;
		margin-left:20px;
		float: left;
	}
	.modal-test .modal-header .modal-win-close{
		color:#fff;
		font-size: 16px;
		line-height:50px;
		margin-right:20px;
		float: right;
		cursor: pointer;
	}
	.modal-test .modal-body{
		width: 100%;
		height: auto;
		background-color:#f4f4f4;
	}
	.modal-test .modal-body ul{
		overflow: hidden;
		clear:both;
	}
	.modal-test .modal-body ul li{
		width: 510px;
		margin-top: 25px;
		margin:0 auto;
	}
	.modal-test .modal-body ul li span{
		display: inline-block;
		float:left;
		vertical-align: middle;
	}
	.modal-test .modal-body ul li input{
		display: inline-block;
		float: right;
		width: 400px;
		height: 25px;
	}
	
	.modal-test .modal-body ul li textarea{
		display: block;
		width: 450px;
		height: 100px;
		margin:0 auto;
		margin-top:20px;
	}
	.modal-test .modal-footer{
		width: 100%;
		height: 60px;
		background-color:#f4f4f4;
	}
	.modal-test .modal-footer input{
		margin-top:12px;
		float: right;
		margin-right:20px;
	}
</style>
<script type="text/javascript">
var datagrid;
function  doOnload(){
	queryOverTime();
}
/**
 *查询待审批记录
 */
function queryOverTime(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/attendOvertimeManage/getManagerOvertime.action",
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
			{field:'deptName',title:'部门',width:120},
			{field:'userName',title:'姓名',width:120},
			{field:'createTimeDesc',title:'申请时间',width:150},
			{field:'startTimeDesc',title:'开始时间',width:150},
			{field:'endTimeDesc',title:'结束时间',width:150},
			{field:'overHours',title:'加班时长（小时）',width:150},
			{field:'overtimeDesc',title:'加班内容',width:200,formatter:function(value,rowData,rowIndex){
				var overtimeDesc = rowData.overtimeDesc.length>20?rowData.overtimeDesc.substring(0,20)+"...":rowData.overtimeDesc;
				var str="<a href='javascript:void(0);' onclick='attendOvertimeInfo(" + rowData.sid + ")' title='"+rowData.overtimeDesc+"'>" + overtimeDesc  + "</a>";
		        return str;	
			}},
			{field:'ope_',title:'操作',width:160,formatter:function(value,rowData,rowIndex){
				var opts= "<a href='javascript:void(0);'  onclick='approv(\"" + rowData.sid + "\",0, "+ rowData.userId +");'>批准 </a>"
		      	 +"&nbsp;&nbsp;<a href='javascript:void(0);' onclick='$(this).modal();approv(\"" + rowData.sid + "\",1 , "+ rowData.userId +");' class='modal-menu-test'>不批准</a>";
				return opts;
			}},
		]]});
}


/**
 *查询已审批记录
 */
function queryApprovOvertime(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/attendOvertimeManage/getManagerApprovOvertime.action",
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
			{field:'deptName',title:'部门',width:120},
			{field:'userName',title:'姓名',width:120},
			{field:'createTimeDesc',title:'申请时间',width:120},
			{field:'startTimeDesc',title:'开始时间',width:100},
			{field:'endTimeDesc',title:'结束时间',width:100},
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
			}}
		]]});
}

/**
 * 批准或者不批准
 @param  sid -Id
 @param  allow -允许
 @param  userId -用户Id
 */
function approv(sid , allow , userId){
	if(allow == 0){//批准
		$.MsgBox.Confirm ("提示", "确定批准此加班申请吗？", function(){
			var url =   "<%=contextPath %>/attendOvertimeManage/approve.action";
			var para = {allow:1 , sid : sid ,userId:userId };
			var jsonObj = tools.requestJsonRs(url ,para);
			if(jsonObj.rtState){
				refreshParentLeaderCount();
				$.MsgBox.Alert_auto("操作成功！");
				datagrid.datagrid('reload');
			}else{
				$.MsgBox.Alert_auto(jsonObj.rtMsg);
			}
		});
		
	}else{
		$("#sid").val(sid);
		$("#userId").val(userId);
	}
}

function noApprow(){
	var reason = $("#reason").val();
	var sid = $("#sid").val();
	var userId = $("#userId").val();
	var url =   "<%=contextPath %>/attendOvertimeManage/approve.action";
	var para = {allow:2 , sid : sid , reason:reason , userId:userId};
	var jsonObj = tools.requestJsonRs(url ,para);
	if(jsonObj.rtState){
		refreshParentLeaderCount();
		$.MsgBox.Alert_auto("操作成功！");
		datagrid.datagrid('reload');
		$(".modal-btn-close").click();
	}else{
		$.MsgBox.Alert_auto(jsonObj.rtMsg);
	}
}

/**
 * 执行上级页面 审批数量
 */
function refreshParentLeaderCount(){
	window.parent.getLeaderAttendCount();
}

</script>
</head>
<body class="" topmargin="5" onload="doOnload();" style="padding-left: 10px;padding-right: 10px">
   <div id="toolbar" class="clearfix">
	<div class="right fr setHeight option_buttons" >
	   <button type="button" value="待审批加班" class="btn-win-white fl btn_active" onclick="queryOverTime();">待审批加班</button>
	   &nbsp;
	   <button type="button" value="已审批加班" class="btn-win-white fl" onclick="queryApprovOvertime();">已审批加班</button> 
	</div>
    <div style="clear:both"></div>
  </div>
	
<table id="datagrid" fit="true"></table>
	
<!-- Modal -->
 <div class="modal-test">
	<div class="modal-header">
		<p class="modal-title">
			不批准原因
		</p>
		<span class="modal-win-close">×</span>
	</div>
	<div class="modal-body">
		<ul>
			<li class="clearfix">
				<textarea rows="10" cols="50" name="reason" id="reason"></textarea>
   			    <input value="0" type="hidden" name="sid" id="sid">
   			    <input value="0" type="hidden" name="userId" id="userId">
			</li>

		</ul>
	</div>
	<div class="modal-footer clearfix">
		<input class = "modal-btn-close btn-alert-gray" type="button" value = '关闭'/>
		<input class = "modal-save btn-alert-blue" type="button" onclick="noApprow();" value = '保存'/>
	</div>
</div>	
	
</body>

</html>