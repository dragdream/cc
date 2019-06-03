<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String status = TeeStringUtil.getString(request.getParameter("status"), "");//查询车辆申请状态
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>车辆申请管理</title>
<script  type="text/javascript"  src='<%=contextPath %>/system/core/base/vehicle/js/vehicle.js'></script>
<script type="text/javascript">
var meetStatus = "<%=status%>";
function doOnload(){
	queryMeeting(meetStatus);
	
	//选中样式
	$("#option" + meetStatus).addClass("active");
  
}

/**
 * 查询待审批记录
 */
 function queryMeeting(meetStatus){
		datagrid = $('#datagrid').datagrid({
			url:contextPath + "/vehicleUsageManage/getPersonVehicleByStatus.action?status="+meetStatus,
			view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
			pagination:true,
			singleSelect:false,
			toolbar:'#toolbar',//工具条对象
			checkbox:false,
			border:false,
			//idField:'formId',//主键列
			fitColumns:true,//列是否进行自动宽度适应
			columns:[[
				/* {field:'sid',checkbox:true,title:'ID',width:100}, */
				{field:'vehicleName',title:'车辆名称',width:120},
				{field:'vuProposerName',title:'申请人',width:120},
				{field:'vuUserName',title:'使用人',width:120},
				{field:'vuDriver',title:'司机',width:120},
				{field:'createTimeStr',title:'申请时间',width:180},
				{field:'vuReason',title:'申请事由',width:120},
				{field:'vuStartStr',title:'开始时间',width:150},
				{field:'vuEndStr',title:'结束时间',width:150},
				{field:'vuDestination',title:'目的地',width:120},
				{field:'vuOperatorName',title:'调度员',width:120},

				{field:'ope_',title:'操作',width:200,formatter:function(value,rowData,rowIndex){
					var id = rowData.sid;
					var status = rowData.status;
					var vehicleId=rowData.vehicleId;
					var  fontStr = "";
					var opts = "";  
					if(status == 0 || status == 3){
						opts = opts + "<a href='javascript:void(0);'  onclick='editVehicleApply(" + id + ","+vehicleId+");'>修改</a>"
						+  "&nbsp;&nbsp;<a href='javascript:void(0);'  onclick='deleteVehicleApply(" + id + ");'>删除</a>";
					}
					if(status == 2 || status ==5){
						opts = opts + "<a href='javascript:void(0);'  onclick='toEnd(" + id + ");'>归还</a>";
					}
					
					if(status == 4){
						opts = opts+"<a href='javascript:void(0);' onclick='vehicleApplyDetail("+id+")' >详情</a>";
					}
					return opts;
				}}
			]]
		});
   }


/**
 * 点击查询会议类型
 */
function setMeetType(meetStatus){
	if(meetStatus || meetStatus == 0){
		queryMeeting(meetStatus);
	}else{
		window.location.href = "<%=contextPath%>/system/core/base/vehicle/personal/index.jsp";
		
	}
	
}

function toEnd(sid){
	var allowDesc  = "确定要归还该车辆吗？";
	var submit = function () {
	    	var url = contextPath + "/vehicleUsageManage/toEnd.action";
	    	var jsonRs = tools.requestJsonRs(url,{sid:sid});
	    	if(jsonRs.rtState){
	    		$.MsgBox.Alert_auto("归还成功！");
	    		queryMeeting(2);
	    		return true;
	    	}else{
	    		$.MsgBox.Alert_auto(jsonRs.rtMsg);
	    	}
	};
	 $.MsgBox.Confirm ("提示", allowDesc, submit);
}

function detail(sid){
	var url=contextPath+"/system/core/base/vehicle/vehicleinfo/detail.jsp?sid="+sid;
	bsWindow(url ,"车辆信息详情",{width:"800",height:"350",buttons:
		[
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
			if(v=="保存"){
				return true;
			}else if(v=="关闭"){
				return true;
			}
		}
	});
}
</script>


</head>
<body onload="doOnload();" style="">

<table id="datagrid" fit="true"></table>
	<div id="toolbar" style="border-width: 0 0 0px 0;!important;">
	
	
	</div>
	
	
</body>
</html>