<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp"%>
<%@ include file="/header/easyui.jsp"%>
<title>会议查询</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<style type="text/css">
.ui-state-highlight {
	border-width: 1px; border-style: solid; border-color: rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.25); border-radius: 4px; color: rgb(64, 64, 64); margin-bottom: 18px; filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#fceec1', endColorstr='#eedc94', GradientType=0); position: relative; box-shadow: inset 0px 1px 0px rgba(255,255,255,0.25); background-repeat: repeat-x; background-color: rgb(238, 220, 148); -moz-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.25); -webkit-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.25); -webkit-border-radius: 4px; -moz-border-radius: 4px; text-shadow: 0 1px 0 rgba(255, 255, 255, 0.5);
}
.ui-widget-content .ui-state-highlight {
	border-width: 1px; border-style: solid; border-color: rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.25); border-radius: 4px; color: rgb(64, 64, 64); margin-bottom: 18px; filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#fceec1', endColorstr='#eedc94', GradientType=0); position: relative; box-shadow: inset 0px 1px 0px rgba(255,255,255,0.25); background-repeat: repeat-x; background-color: rgb(238, 220, 148); -moz-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.25); -webkit-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.25); -webkit-border-radius: 4px; -moz-border-radius: 4px; text-shadow: 0 1px 0 rgba(255, 255, 255, 0.5);
}
.ui-widget-header .ui-state-highlight {
	border-width: 1px; border-style: solid; border-color: rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.25); border-radius: 4px; color: rgb(64, 64, 64); margin-bottom: 18px; filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#fceec1', endColorstr='#eedc94', GradientType=0); position: relative; box-shadow: inset 0px 1px 0px rgba(255,255,255,0.25); background-repeat: repeat-x; background-color: rgb(238, 220, 148); -moz-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.25); -webkit-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.25); -webkit-border-radius: 4px; -moz-border-radius: 4px; text-shadow: 0 1px 0 rgba(255, 255, 255, 0.5);
}

</style>
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
		
		
		
		userForm = $('#form1').form();
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
			fitColumns : false,
			nowrap : false,
			border : false,
			idField : 'sid',
			singleSelect:false,
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
			 {
				field : 'attendeeOut',
				title : '出席人员',
				width : '240',
				formatter : function(value, rowData, rowIndex) {
					var postDesc = "<div style='line-height:20px;'><span class='ui-state-highlight ui-corner-al'  style='font-weight:bold;'>外出人员:</span>" + value  + "</div>";
			    	postDesc = postDesc +  "<div style='line-height:20px;'><font class='ui-state-highlight ui-corner-al'  style='font-weight:bold;'>内部人员:</font>" + rowData.attendeeName + "</div>";
				
					return postDesc  ;
			    } 
			  }, 
			  {
					field : 'attendeeName',
					title : '内部出息人员',
					width : 30,
					hidden:true
				  }, 
			 
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
	//改变列表样式左边线
	$(".datagrid-view,.datagrid-pager").css({"border-left":'1px solid #d3d3d3'});
    $(".datagrid-toolbar").css({"padding":"0px"});   
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
		alert(jsonRs.rtMsg);
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
<body class="easyui-layout" fit="true">
	<div region="center" border="false" style="overflow: hidden;">
	
		<div id="toolbar" class="datagrid-toolbar" style="height: auto;display: none;background: #ffffff"> 
			<!-- <div id="toolbar" style="height:auto"> -->
  
		  <div> 
		 <div >
 	
   		  <form id="form1" name="form1">
		<table class="TableBlock" width="80%" align="center" style="margin-top:6px;margin-bottom:10px;" >
   			<tr>
   				 <td nowrap class="TableData" colspan="2" >
   		
					名称:
					<input type="text" name="meetName" id="meetName" value="" class="BigInput"  maxlength="100" > 
					&nbsp;&nbsp;时间: 
   				 从 <input type="text" name="beginTime" id="beginTime" size="10" maxlength="20" class="BigInput" value=""  onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})">

       				 至<input type="text" name="endTime" id="endTime" size="10" maxlength="20" class="BigInput" value=""  onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
      
  			
					&nbsp;&nbsp;	  申请人:
					<input type="hidden" name="userId" id="userId" value=""> 
					<input type="text" name=""userName"" id="userName" value="" class='Static BigInput'> 
			
					<a href="javascript:void(0);" onClick="selectSingleUser(['userId', 'userName'])">添加</a>
					<a href="javascript:void(0);" class="orgClear" onClick="clearData('userId', 'userName')">清空</a>	
   			
         		</td>
				
  			</tr>
  			<tr>
   			   <td nowrap class="TableData" colspan="2" >
   					 状态:
					<select name="status"  class="BigSelect">
					 	<option value="0">全部</option>
      					<option value="1">待审批</option>
				      	<option value="2">已审批</option>
				      	<option value="3">进行中</option>
				      	<option value="4">未批准</option>
				      	<option value="5">已结束</option>      
  
					</select>
						&nbsp;&nbsp; 会议室:
					<select name="meetRoomId"  id="meetRoomId" class="BigSelect">
					 	<option value="">全部</option>
  
					</select>	
					
						&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="查询" class="btn btn-primary" onclick="query()">
  				      &nbsp;&nbsp;<input type="reset" value="重置" class="btn" >
  			
   			   </td>
   			</table>
		</form>
		
		</div>
		
	   </div>
		
		  <!--   <div style="padding-top:10px;padding-left:10px;">

		       </div> -->
		</div>
		<table id="datagrid"></table>
		
	</div>

</body>
</html>