<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/userheader.jsp" %>
<title>会议查询</title>
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
.datagrid-btable{
    width:100%;
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
		//userForm = $('#form1').form();
		datagrid = $('#datagrid').datagrid({
			url : contextPath + '/meetManage/getSummary.action' ,
			toolbar : '#toolbar',
			title : title,
			iconCls:'',
			pagination : true,
			pageSize : 20,
			//pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
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
				width : 150,
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
					field : 'endTime',
					title : '结束时间',
					width : 120	,
					formatter : function(value, rowData, rowIndex) {
						return rowData.endTimeStr ;
				    } 
				},
				  {
					field : 'isUpLoadSummary',
					title : '会议纪要状态',
					width : 120	,
					 formatter : function(value, rowData, rowIndex) {
						return rowData.isUpLoadSummary ;
				    }  
				},
			  {
					field : '2',
					title : '操作',
					width : 100,
					formatter : function(e,rowData) {
						var str = "<a href='javascript:void(0);' onclick='summaryDetail("+rowData.sid+","+rowData.meetRoomId+")'>查看纪要</a>";
						if(loginPersonId==rowData.recorderId){
							str += "<br><a href='javascript:void(0);' onclick='addSummary("+rowData.sid+","+rowData.meetRoomId+")'>编辑会议纪要</a>";
						}
						//str += "<br><a href='javascript:void(0);' onclick='addSummary("+rowData.sid+","+rowData.meetRoomId+")'>编辑会议纪要</a>";
						return str;
						
				    } 
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

function addSummary(id,meetingRoomId){
	var url = contextPath + "/system/core/base/meeting/summary/addOrUpdate.jsp?id=" + id  + "&meetRoomId=" + meetingRoomId;
	bsWindow(url ,"会议纪要",{width:"800",height:"360",buttons:
		[
		 {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h,f,d){
		var cw = h[0].contentWindow;
		if(v=="保存"){
			cw.doSaveOrUpdate(function(){
			    $.MsgBox.Alert_auto("保存成功！");
				datagrid.datagrid('reload');
				d.remove();
			});
		
		}else if(v=="关闭"){
			return true;
		}
	}});
}


function summaryDetail(id,meetingRoomId){
	var url = contextPath + "/system/core/base/meeting/summary/detail.jsp?id=" + id  + "&meetRoomId=" + meetingRoomId;
	bsWindow(url ,"会议纪要",{width:"800",height:"360",buttons:
		[
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="关闭"){
			return true;
		}
	}});
}

</script>
</head>
<body style="font-size:12px;padding-left: 10px;padding-right: 10px;">
<!-- 	<div region="center" border="false" style="overflow: hidden;"> -->
<table id="datagrid" fit="true"></table>
		<div id="toolbar" class="topbar clearfix"> 
			<%-- <div class="base_layout_top" style="position:static">
				<img class = 'fl' style="margin-right: 10px; margin-top: 3px" src="<%=contextPath %>/common/zt_webframe/imgs/hygl/hyjy/icon_会议纪要-.png">
				<p class="title" style="padding-top: 4px;">会议纪要</p>
			</div>
			<span class="basic_border fl"></span> --%>
			<div class="fl" style="position:static;">
		    <img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/hygl/hyjy/icon_会议纪要-.png">
		    <span class="title">会议纪要</span>
	        </div>
	        <div style="clear:both"></div>
		</div>
		
<!-- 	</div> -->
</body>
</html>