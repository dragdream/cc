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
<%@ include file="/header/easyui2.0.jsp" %>
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
<%-- /**
 *查询待审批记录
 */
function queryMeeting(meetStatus){
	var url =   "<%=contextPath %>/vehicleUsageManage/getApprovalVehicleByStatus.action";
	var para = {status:meetStatus};
	var jsonObj = tools.requestJsonRs(url , para);
	if(jsonObj.rtState){
		var prcs = jsonObj.rtData;
		$("#listDiv").empty();
		if(prcs.length > 0){
			var tableStr = "<table class='TableList' width='99%' align='center'><tbody id='listTbody'>";
		    	 tableStr = tableStr + "<tr class='TableHeader'>"
			      	 + "<td width='100' align='center'>车辆名称</td>"
			    	 + "<td width='100' align='center'>申请人</td>"
			    	 +"<td nowrap width='80' align='center'>使用人</td>"
			    	 +"<td nowrap width='80' align='center'>司机</td>"
			     	 +"<td nowrap  width='100'  align='center'>申请时间</td>"
			     	 +"<td nowrap  width='100'  align='center'>申请事由</td>"
			     	 +"<td nowrap  width='90'  align='center'>开始时间</td>"
			     	 +"<td nowrap  width='90'  align='center'>结束时间</td>"
			     	 +"<td nowrap width='80' align='center'>目的地</td>"
			     	 +"<td nowrap width='80' align='center'>调度员</td>"
			      	 +"<td nowrap  width='60' align='center'>操作</td>"
			         +"</tr>";
			for(var i = 0;i<prcs.length ; i++){
				var prc = prcs[i];
				var id = prc.sid;
				var status = prc.status;
				var  fontStr = "";
				var opts = "";  
				if(status == 0 || status == 3){
					opts = opts + "<a href='javascript:void(0);'  onclick='approvalFunc(" + id + "," + 1 + ");'>批准</a>"
					+  "&nbsp;&nbsp;<a href='javascript:void(0);'  onclick='approvalFunc(" + id + "," + 3 + ");'>不批准</a>";
				}
				tableStr = tableStr +"<tr class=''>"
						 +"<td nowrap  align='center'><font color='" + fontStr + "'><a href='javascript:void(0);' onclick='vehicleApplyDetail("+id+")' >"+ prc.vehicleName +"</a></font></td>"
						 +"<td nowrap   align='center'><font color='" + fontStr + "'>" + prc.vuProposerName + "</font></td>"	
						 +"<td nowrap   align='center'><font color='" + fontStr + "'>"+ prc.vuUserName +"</font></td>"
				      	 +"<td nowrap align='center' ><font color='" + fontStr + "'>" + prc.vuDriver + "</font></td>"
				     	 +"<td nowrap align='center'  ><font color='" + fontStr + "'>" + prc.createTimeStr + "</font></td>"
				     	+"<td nowrap align='center'  ><font color='" + fontStr + "'>" + prc.vuReason + "</font></td>"
				     	 +"<td nowrap align='center'  ><font color='" + fontStr + "'>" + prc.vuStartStr + "</font></td>"
				     	 +"<td nowrap align='center'  ><font color='" + fontStr + "'>" + prc.vuEndStr + "</font></td>"
				     	 +"<td nowrap align='center'  ><font color='" + fontStr + "'>" + prc.vuDestination + "</font></td>"
				     	 +"<td nowrap align='center' ><font color='" + fontStr + "'>" + prc.vuOperatorName + "</font></td>"
					     +"<td nowrap align='center'  >"
					     +opts
					     +"</td>"
				         +"</tr>";
			}
			tableStr = tableStr + "</tbody></table>";	
			$("#listDiv").append(tableStr);	
		}else{
		 	messageMsg("暂无" + VEHICLE_STAUS_TYPE[meetStatus] + "车辆申请记录", "listDiv" ,'' ,380);
		}
	}else{
		alert(jsonObj.rtMsg);
	}
} --%>

/**
 *查询待审批记录
 */
function queryMeeting(meetStatus){
	var url =   "<%=contextPath %>/vehicleUsageManage/getApprovalVehicleByStatus.action?status="+meetStatus;
	
	datagrid = $('#datagrid').datagrid({
		url:url,
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
			/* {field:'vuReason',title:'申请事由',width:120}, */
			{field:'vuStartStr',title:'开始时间',width:150},
			{field:'vuEndStr',title:'结束时间',width:150},
			{field:'vuDestination',title:'目的地',width:120},
			{field:'vuOperatorName',title:'调度员',width:120},

			{field:'ope_',title:'操作',width:200,formatter:function(value,rowData,rowIndex){
				var id = rowData.sid;
				var status = rowData.status;
				var  fontStr = "";
				var opts = "";  
				if(status == 0 || status == 3){
					opts = opts + "<a href='javascript:void(0);'  onclick='approvalFunc(" + id + "," + 1 + ");'>批准</a>"
					+  "&nbsp;&nbsp;<a href='javascript:void(0);'  onclick='approvalFunc(" + id + "," + 3 + ");'>不批准</a>";
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

</script>


</head>
<body onload="doOnload();">

<table id="datagrid" fit="true"></table>
	<div id="toolbar" class="topbar clearfix">
        <div class="fl" style="position:static;">
		    <img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/xzbg/clgl/clsygl1.png">
		    <span class="title">车辆使用管理</span>
	    </div>
    </div>
	<table id="datagrid" fit="true"></table>
</body>
</html>