<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<title>请假审批管理</title>
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
	queryLeave();
}
/**
 *查询待审批记录
 */
function queryLeave(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/attendLeaveManage/getLeaderLeave.action",
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
			{field:'createTimeStr',title:'申请时间',width:150},
			{field:'startTimeStr',title:'开始时间',width:150},
			{field:'endTimeStr',title:'结束时间',width:150},
			{field:'annualLeave',title:'请假天数',width:100},
			{field:'leaveTypeDesc',title:'请假类型',width:100},
			{field:'leaveDesc',title:'请假原因',width:200,formatter:function(value,rowData,rowIndex){
				var leaveDesc = rowData.leaveDesc.length>20?rowData.leaveDesc.substring(0,20)+"...":rowData.leaveDesc;
				var str="<a href='javascript:void(0);' onclick='attendLeaveInfo(" + rowData.sid + ")' title='"+rowData.leaveDesc+"'>" + leaveDesc  + "</a>";
		        return str;	
			}},
			{field:'ope_',title:'操作',width:160,formatter:function(value,rowData,rowIndex){
				var status = rowData.status;
				var opts =  "<a href='javascript:void(0);'  onclick='approv(\"" + rowData.sid + "\",0 , " + rowData.userId + " ,"+rowData.leaveType+","+rowData.annualLeave+");'>批准 </a>"
		      	 +"&nbsp;&nbsp;<a href='javascript:void(0);' class='modal-menu-test' onclick='$(this).modal();approv(\"" + rowData.sid  + "\",1 ," + rowData.userId + ","+rowData.leaveType+");'>不批准</a>";
		      	if(status == 1){//申请销假批准
		      		 opts =  "<a href='javascript:void(0);'  onclick='destroyLeave(\"" + rowData.sid  + "\"," + rowData.userId + ");'>销假 </a>";
			      	
		      	}
				return opts;
			}},
		]]});

}


/**
 *查询已审批记录
 */
function queryApprovLeave(){
	
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/attendLeaveManage/getLeaderApprovLeave.action",
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
			{field:'createTimeStr',title:'申请时间',width:120},
			{field:'startTimeStr',title:'开始时间',width:100},
			{field:'endTimeStr',title:'结束时间',width:100},
			{field:'leaveTypeDesc',title:'请假类型',width:100},
			{field:'leaveDesc',title:'请假原因',width:200,formatter:function(value,rowData,rowIndex){
				var leaveDesc = rowData.leaveDesc.length>20?rowData.leaveDesc.substring(0,20)+"...":rowData.leaveDesc;
				var str="<a href='javascript:void(0);' onclick='attendLeaveInfo(" + rowData.sid + ")' title='"+rowData.leaveDesc+"'>" + leaveDesc  + "</a>";
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
 */
function approv(sid , allow , userId,leaveType,days){
	if(allow == 0){//批准
		if(leaveType=='3'){
			var remianDays = getAnnualLeaveInfo(userId);
			if(days>remianDays){
				$.MsgBox.Alert_auto("其请年假天数大于剩余年假");
				return;
			}
		}
		$.MsgBox.Confirm ("提示", "确定批准此请假申请吗？", function(){
			var url =   "<%=contextPath %>/attendLeaveManage/approve.action";
			var para = {allow:1 , sid : sid , userId:userId};
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
/**
 * 不批准
 */
function noApprow(){
	var reason = $("#reason").val();
	var sid = $("#sid").val();
	var userId = $("#userId").val();
	var url =   "<%=contextPath %>/attendLeaveManage/approve.action";
	var para = {allow:2 , sid : sid , reason:reason , userId :userId};
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
 * 销假
 */
function destroyLeave(id , userId){
	$.MsgBox.Confirm ("提示", "确定要销假所选记录！", function(){
		var url =   "<%=contextPath %>/attendLeaveManage/destroyLeave.action";
		var para = {status:2 , sid : id ,userId : userId};
		var jsonObj = tools.requestJsonRs(url ,para);
		if(jsonObj.rtState){
			refreshParentLeaderCount();
			$.MsgBox.Alert_auto("操作成功！");
			datagrid.datagrid('reload');			
			//window.location.reload();
			
		}else{
			$.MsgBox.Alert_auto(jsonObj.rtMsg);
		}	
	});
}



/**
 * 执行上级页面 审批数量
 */
function refreshParentLeaderCount(){
	window.parent.getLeaderAttendCount();
}


/**
 * 年假提醒信息
 */
function getAnnualLeaveInfo(userId){
		var url = "<%=contextPath%>/attendLeaveManage/getUsedAnnualLeaveDays.action?userId="+userId;
		var jsonObj = tools.requestJsonRs(url);
		if (jsonObj.rtState) {
			return jsonObj.rtData.remainLeaveDays;
		} else {
			$.MsgBox.Alert_auto(jsonObj.rtMsg);
		}
}
</script>
</head>
<body class=""  onload="doOnload();" style="padding-left: 10px;padding-right: 10px">
   <div id="toolbar" class="clearfix">
	<div class="right fr setHeight option_buttons" >
	   <button type="button" value="待审批请假" class="btn-win-white fl btn_active" onclick="queryLeave();">待审批请假</button>
	   &nbsp;
	   <button type="button" value="已审批请假" class="btn-win-white fl" onclick="queryApprovLeave();">已审批请假</button> 
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