<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<title>车辆查询</title>
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
<script type="text/javascript" src="<%=contextPath%>/system/core/base/vehicle/js/vehicle.js"></script>
	
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
		userForm = tools.formToJson("#form1");
		datagrid = $('#datagrid').datagrid({
			url : contextPath + '/vehicleUsageManage/queryVehicleUsageList.action' ,
			view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
			toolbar : '#toolbar',
			title : title,
			//iconCls : 'icon-save',
			iconCls:'',
			pagination : true,
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
			fit : true,
			fitColumns : false,
			nowrap : false,
			border : false,
			idField : 'sid',
			singleSelect:true,
			columns : [ [
			    {
				field : 'vehicleName',
				title : '车辆名称',
				width : 220,
				formatter : function(value, rowData, rowIndex) {
					return "<a  href='javascript:void(0);' onclick='vehicleApplyDetail(" +rowData.sid + ")'>" + value + "</a>" ;
			    } 
			  },  {
				field : 'vuProposerName',
				title : '申请人',
				width : 100
			  },  
			  {
				field : 'vuStartStr',
				title : '开始时间',
				width : 200	,
				formatter : function(value, rowData, rowIndex) {
					return rowData.vuStartStr ;
			    } 
			  } ,
			  {
				field : 'vuEndStr',
				title : '结束时间',
				width : 200	,
				formatter : function(value, rowData, rowIndex) {
					return rowData.vuEndStr ;
			    } 
			},
		    {
				field : 'status',
				title : '车辆审批状态',
				width : 100,
				formatter : function(value, rowData, rowIndex) {
					return VEHICLE_STAUS_TYPE[value] ;
			    } 
		    } ,		
		    {
				field : 'vuDriver',
				title : '司机',
				width : 150,
				formatter : function(value, rowData, rowIndex) {
					//return "<a  href='javascript:void(0);' onclick='meetingRoomDetail(" +rowData.vehicleId + ")'>" + value + "</a>" ;
				  return value ;
			    } 
		    },		
		    {
				field : 'vuOperatorName',
				title : '调度员',
				width : 150,
				formatter : function(value, rowData, rowIndex) {
				  return value ;
			    } 
			 }
		  
		] ],onLoadSuccess:onLoadSuccessFunc
	});		
	
	//添加车辆室
	var meetRooms =	selectPostVehicle();
	var meetRoomOptions = "";
	for(var i =0 ;i<meetRooms.length ; i++){
		meetRoomOptions = meetRoomOptions + "<option value='" + meetRooms[i].sid + "'>" + meetRooms[i].vModel + "</option>";
	}
	$("#vehicleId").append(meetRoomOptions);
});
/**
 * 获取最大记录数
*/
function onLoadSuccessFunc(){
	var data=$('#datagrid').datagrid('unselectAll');
	
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

//重置
function cz(){
	$("#form1")[0].reset();
}
</script>
</head>
<body  style="padding-right: 10px;padding-left: 10px;padding-top: 5px">
    <div id="toolbar" class="titlebar clearfix">
      <div class="fl left">
         <img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/xzbg/clgl/clsqcx.png">
		 <span class="title">车辆申请查询</span>	 
      </div>
      <div class="fr right" style="margin-top: 5px">
			 <button type="button" onclick="query()" class="btn-win-white">查询</button>
			 <button type="button" onclick="cz();" class="btn-win-white">重置</button>
	 </div>
         <span class="basic_border"></span>
         <br/>
      <div class="" style="padding-top: 5px;padding-bottom: 5px">
         <!-- form表单 -->
         <form id="form1">
             <div>
                <span>名称：</span><input type="text" name="vehicleName" id="vehicleName"  class="BigInput"  style="height:20px" > &nbsp;&nbsp;&nbsp;&nbsp;
                <span>时间：</span>
                  <input type="text" name="vuStartStr" id="vuStartStr" size="16" maxlength="20" class="BigInput Wdate" value=""  onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})">
				          至
		           <input type="text" name="vuEndStr" id="vuEndStr" size="16" maxlength="20" class="BigInput Wdate" value=""  onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})">&nbsp;&nbsp;&nbsp;&nbsp;
               
                 <span> 申请人：</span>
                    <input type="hidden" name="vuProposerId" id="vuProposerId" value=""> 
					<input type="text" name="vuProposerName" id="vuProposerName"  class='BigInput' readonly="readonly" style="height:20px"> 
					<a href="javascript:void(0);" onClick="selectSingleUser(['vuProposerId', 'vuProposerName'])">添加</a>
					<a href="javascript:void(0);" class="orgClear" onClick="clearData('vuProposerId', 'vuProposerName')">清空</a>	
                     &nbsp;&nbsp;&nbsp;&nbsp;
                  <span> 状态：</span>
                  <select name="status"  class="BigSelect" style="height:20px">
					 	<option value="0">全部</option>
      					<option value="1">待审批</option>
				      	<option value="2">已审批</option>
				      	<option value="3">进行中</option>
				      	<option value="4">未批准</option>
				      	<option value="5">已结束</option>      
  
				  </select>
				 &nbsp;&nbsp;&nbsp;&nbsp;
                  <span>车牌号：</span>
                  <select name="vehicleId"  id="vehicleId" class="BigSelect" style="height: 20px">
					 <option value="">全部</option>
				  </select>	
             </div>	
		</tr>
	  </table>
	  </form>
      </div>
   </div>

	<table id="datagrid" fit="true"></table>
</body>
</html>