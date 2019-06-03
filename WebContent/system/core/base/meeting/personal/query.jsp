<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<title>会议查询</title>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath %>/system/core/base/meeting/js/meeting.js"></script>
	
	<script type="text/javascript" charset="UTF-8">
	var datagrid;
	var userDialog;
	var userForm;
	var passwordInput;
	var userRoleDialog;
	var userRoleForm;
	var title ="";
	$(function() {
		//userForm = $('#form1').form();
		datagrid = $('#datagrid').datagrid({
			url : contextPath + '/meetManage/queryList.action' ,
			toolbar : '#toolbar',
			title : title,
			//iconCls : 'icon-save',
			iconCls:'',
			pagination : true,
			pageSize : 20,
			pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
			fit : true,
			fitColumns : true,
			nowrap : false,
			border : false,
			idField : 'sid',
			singleSelect:true,
			columns : [ [
			    {
				field : 'meetName',
				title : '会议名称',
				width : 220,
				formatter : function(value, rowData, rowIndex) {
					return "<a  href='javascript:void(0);' onclick='meetingDetail(" +rowData.sid + ")'>" + value + "</a>" ;
			    } 
			  },  {
				field : 'userName',
				title : '申请人',
				width : 100
			  },  
			  {
				field : 'startTime',
				title : '开始时间',
				width : 120	,
				formatter : function(value, rowData, rowIndex) {
					return rowData.startTimeStr ;
			    } 
			  } ,
				{
				field : 'startTimeStr',
				title : '开始时间',
				width : 10,
				hidden:true
			  }, 
			  {
					field : 'endTime',
					title : '结束时间',
					width : 120	,
					formatter : function(value, rowData, rowIndex) {
						return rowData.endTimeStr ;
				    } 
				},
// 			 {
// 				field : 'attendeeOut',
// 				title : '出席人员',
// 				width : '240',
// 				formatter : function(value, rowData, rowIndex) {
// 					var postDesc = "<div style='line-height:20px;'><span class='ui-state-highlight ui-corner-al'  style='font-weight:bold;'>外部人员:</span>" + value  + "</div>";
// 			    	postDesc = postDesc +  "<div style='line-height:20px;'><font class='ui-state-highlight ui-corner-al'  style='font-weight:bold;'>内部人员:</font>" + rowData.attendeeName + "</div>";
				
// 					return postDesc  ;
// 			    } 
// 			  }, 
// 			  {
// 					field : 'attendeeName',
// 					title : '内部出息人员',
// 					width : 30,
// 					hidden:true
// 				  }, 
			 
			{
				field : 'endTimeStr',
				title : '结束时间',
				width : 10,
				hidden:true
			  },
			 		
		    {
				field : 'status',
				title : '会议状态',
				width : 60,
				formatter : function(value, rowData, rowIndex) {
					return MEETING_STATUS_TYPE_DESC[value] ;
			    } 
		    } ,		
		    {
				field : 'meetRoomName',
				title : '会议室',
				width : 150,
				formatter : function(value, rowData, rowIndex) {
					return "<a  href='javascript:void(0);' onclick='meetingRoomDetail(" +rowData.meetRoomId + ")'>" + value + "</a>" ;
			    } 
		    },{
				field : 'meetRoomId',
				title : '会议室Id',
				width : 10,
				hidden:true
			  }
		  
		] ],onLoadSuccess:onLoadSuccessFunc
	});
		$(".datagrid-header-row td div span").each(function(i,th){
			var val = $(th).text();
			 $(th).html("<label style='font-weight: bolder;'>"+val+"</label>");
		});

	
	//添加会议室
	var meetRooms =	getAllRoom();
	var meetRoomOptions = "";
	for(var i =0 ;i<meetRooms.length ; i++){
		meetRoomOptions = meetRoomOptions + "<option value='" + meetRooms[i].sid + "'>" + meetRooms[i].mrName + "</option>";
	}
	$("#meetRoomId").append(meetRoomOptions);
});
/**
 * 获取最大记录数
*/
function onLoadSuccessFunc(){
	var data=$('#datagrid').datagrid('getData');
}


function  toSealDataInfo(sid){
	var url = contextPath +  "/sealManage/selectById.action?sid=" + sid;
	var para = {};
	var jsonRs = tools.requestJsonRs(url,para);
	if(jsonRs.rtState){
		var sealData = jsonRs.rtData.sealData;
		showInfoStr(sealData);
		
	   $('#sealInfo').dialog({  
		  title: '查看印章',
          width: 400,
   		  height: 340,
          closed: false,
          cache: false,
          modal: true
        });
	}else{
		$.MsgBox.Alert_auto(jsonRs.rtMsg);
	}
	
	
}

function toSettingPriv(sid){
	window.location.href = "<%=contextPath%>/system/core/system/seal/manager/setPriv.jsp?sid=" + sid;
}

/**
 * 条件查询
 */
function query(){
	var para =  tools.formToJson($("#form1")) ;
	$('#datagrid').datagrid('load', para);	
}

</script>
</head>
<body style="padding-left: 10px;padding-right: 10px">
	<div id="toolbar" style="min-width:950px;">
		<div class="base_layout_top" style="position:static">
			<img class = 'fl' style="margin-right: 10px; margin-top: 3px" src="<%=contextPath %>/common/zt_webframe/imgs/hygl/hycx/icon-会议查询.png">
			<p class="title" style="padding-top: 4px;">会议查询</p>
		</div>
		<span class="basic_border fl"></span>
		<form id="form1" name="form1" method="post" style="padding:10px;">
		<table class="none_table" width="100%"  style="font-size:12px">
   			<tr>
   				 <td nowrap class="TableData TableBG">
					名称:
				</td>
				<td class="TableData">
					<input type="text" name="meetName" id="meetName" value="" class="BigInput"  maxlength="100" style="height:20px;"/> 
				</td>
				 <td nowrap class="TableData TableBG">
					时间: 
				<td class="TableData">
				             从&nbsp;<input type="text" name="startTimeStr" id="beginTime" size="10" maxlength="20" class="BigInput Wdate" value=""  onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
       				 至&nbsp;<input type="text" name="endTimeStr" id="endTime" size="10" maxlength="20" class="BigInput Wdate" value=""  onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
					&nbsp;&nbsp;	 
				</td>
				 <td nowrap class="TableData TableBG">
					 申请人:
				</td>
				 <td nowrap class="TableData">
					<input type="hidden" name="userId" id="userId" value=""> 
					<input type="text" name="userName" id="userName" value="" style="height:20px;" class='Static BigInput readonly' readonly> 
			
					<a href="javascript:void(0);" onClick="selectSingleUser(['userId', 'userName'])">添加</a>
					<a href="javascript:void(0);" class="orgClear" onClick="clearData('userId', 'userName')">清空</a>	
   			
         		</td>
  			</tr>
  			<tr>
   			   <td nowrap class="TableData TableBG">
   					 状态:
   			  </td>
   			   <td nowrap class="TableData">
					<select name="status"  class="BigSelect">
					 	<option value="0">全部</option>
      					<option value="1">待审批</option>
				      	<option value="2">已审批</option>
				      	<option value="3">进行中</option>
				      	<option value="4">未批准</option>
				      	<option value="5">已结束</option>      
  
					</select>
				</td>
				 <td nowrap class="TableData TableBG">
						 会议室:
				 </td>
				  <td nowrap class="TableData">
					<select name="meetRoomId"  id="meetRoomId" class="BigSelect">
					 	<option value="">全部</option>
  
					</select>	
				  </td>
				   <td nowrap class="TableData" colspan="2" align="center">
  				      <input type="reset" value="重置" class="btn-win-white fr" onclick="$('#userId').val('')" >
					  <input type="button" value="查询" class="btn-win-white fr" onclick="query()" style="margin-right:10px;">
   			      </td>
   			</table>
		</form>
	</div>
	<table id="datagrid"></table>
</body>
</html>