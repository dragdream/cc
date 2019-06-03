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
<style>
	.datagrid-cell{
		position:relative!important;
		overflow:visible;
	}
	.erweima{
		cursor:pointer;
		color:#0f92d8;
	}
	.erweima:hover{
		color:#205f92;
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
			url : contextPath + '/meetManage/getMyMeeting.action' ,
			toolbar : '#toolbar',
			title : title,
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
				width : 130,
				formatter : function(value, rowData, rowIndex) {
					return "<a  href='javascript:void(0);' onclick='meetingDetail(" +rowData.sid + ")'>" + value + "</a>" ;
			    } 
			  },  {
				field : 'userName',
				title : '申请人',
				width : 80
			  },  
			  {
				field : 'startTime',
				title : '开始时间',
				width : 110	,
				formatter : function(value, rowData, rowIndex) {
					return rowData.startTimeStr ;
			    } 
			  } ,
				{
				field : 'startTimeStr',
				title : '开始时间',
				width : 1,
				hidden:true
			  }, 
			  {
					field : 'endTime',
					title : '结束时间',
					width : 110	,
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
				width : 80,
				formatter : function(value, rowData, rowIndex) {
					return "<a  href='javascript:void(0);' onclick='meetingRoomDetail(" +rowData.meetRoomId + ")'>" + value + "</a>" ;
			    } 
		    },{
				field : 'meetRoomId',
				title : '会议室Id',
				width : 10,
				hidden:true
			  },
			    {field:'2',title:'操作',width : 150,formatter:function(e,rowData){  
			    	var str;
			    	//分状态 
			    	if(rowData.status==1){//已批准
			    		//参会情况   和   签阅情况
			    		str= "<a href='javascript:void(0);'  onclick='showMeetingAttendInfo("+ rowData.sid +");'>参会情况</a>&nbsp;&nbsp;";
			        	str += "<a href='javascript:void(0);'  onclick='showMeetingReadInfo("+ rowData.sid +");'>签阅情况</a>&nbsp;&nbsp;<br>";
			    		
			        	//判断参会缺席
			        	
			            if(rowData.isAttend==1&&rowData.isConfirm==0){//是参会人员  并且还未确认
			            	str +=  "<a href='javascript:void(0);' onclick='setMeetingConfirmFunc("+rowData.sid+",1)'>参会</a>&nbsp;&nbsp;";
					    	str +=  "<a href='javascript:void(0);' onclick='setMeetingConfirmFunc("+rowData.sid+",2)'>缺席</a>&nbsp;&nbsp;<br>";
			    	    }
			    	}
                    if(rowData.status==2){//进行中
                    	//申请人   参会人    纪要员     都可以查看会议纪要    状态（进行中  已完成）  
                    	str ="<a href='javascript:void(0);' onclick='summaryDetail("+rowData.sid+","+rowData.meetRoomId+")'>查看会议纪要</a><br>"; 
                    	if(loginPersonId==rowData.recorderId){//当前登录的用户是会议纪要员   编辑会议纪要
				    		str += "<a href='javascript:void(0);' onclick='addSummary("+rowData.sid+","+rowData.meetRoomId+")'>编辑会议纪要</a><br>";
						}
                    	//参会情况   和   签阅情况
                    	str+= "<a href='javascript:void(0);'  onclick='showMeetingAttendInfo("+ rowData.sid +");'>参会情况</a>&nbsp;&nbsp;";
			        	str += "<a href='javascript:void(0);'  onclick='showMeetingReadInfo("+ rowData.sid +");'>签阅情况</a>&nbsp;&nbsp;<br>";
			    		
			    		//判断参会缺席
			            if(rowData.isAttend==1&&rowData.isConfirm==0){//是参会人员  并且还未确认
			            	str +=  "<a href='javascript:void(0);' onclick='setMeetingConfirmFunc("+rowData.sid+",1)'>参会</a>&nbsp;&nbsp;";
					    	str +=  "<a href='javascript:void(0);' onclick='setMeetingConfirmFunc("+rowData.sid+",2)'>缺席</a>&nbsp;&nbsp;<br>";
			            }
			    	}
			    	
                    
                    if(rowData.status==4){//已完成
                    	//申请人   参会人    纪要员     都可以查看会议纪要    状态（进行中  已完成）  
                    	str ="<a href='javascript:void(0);' onclick='summaryDetail("+rowData.sid+","+rowData.meetRoomId+")'>查看会议纪要</a>&nbsp;&nbsp;<br>"; 
                    	if(loginPersonId==rowData.recorderId){//当前登录的用户是会议纪要员  编辑会议纪要
				    		str += "<a href='javascript:void(0);' onclick='addSummary("+rowData.sid+","+rowData.meetRoomId+")'>编辑会议纪要</a><br>";
						}
			    		//参会情况   和   签阅情况
                    	str+= "<a href='javascript:void(0);'  onclick='showMeetingAttendInfo("+ rowData.sid +");'>参会情况</a>&nbsp;&nbsp;";
			        	str += "<a href='javascript:void(0);'  onclick='showMeetingReadInfo("+ rowData.sid +");'>签阅情况</a>&nbsp;&nbsp;";
			    		
			        	str+="<a href='javascript:void(0);' onclick='download1("+rowData.sid+")'>导出</a>&nbsp;&nbsp;<br>";
			        	
			    	}
			    		
                    //所有状态的都加入二维码
                    var id1="Code"+rowData.sid;
                    var id2="QrCode"+rowData.sid;
                    str+="<span  class = 'erweima' id='"+id1+"'>二维码</span>&nbsp;&nbsp;";
                    
                   
                    str= str + "<img id='"+id2+"' style='display:none;width:100px;' src='<%=contextPath %>/meetManage/qrCodeDownload.action?meetingId=" + rowData.sid + "'/>";
			    	          
                    
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

	$(".erweima").mouseover(function(){
		$(this).next().css({'display':'block','position':'absolute','top':'40','left':'-100px','z-index':'999'});
	}).mouseleave(function(){
		$(this).next().css('display','none');
	});
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
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
			var isOk = cw.doSaveOrUpdate();
			return isOk;
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




/**
 * 参加会议或缺席
 */
function setMeetingConfirmFunc(sid,status){
	if(status == '1'){//参会
		var url = "<%=contextPath %>/TeeMeetingAttendConfirmController/updateAttendFlag.action?meetingId=" + sid + "&attendFlag=" + status ;
	    var para =  tools.formToJson($("#form1"));
	    var jsonRs = tools.requestJsonRs(url,para);
	    if(jsonRs.rtState){
	      	$.MsgBox.Alert_auto("确认成功！");
	      	datagrid.datagrid('reload');
	    }else{
	    	$.MsgBox.Alert_auto(jsonRs.rtMsg);
	    }
		return;
	}
  var title = "缺席说明";
  var buttonName = "缺席确认";
  var url = contextPath + "/system/core/base/meeting/personal/setMeetingConfirm.jsp?meetingId=" + sid + "&attendFlag=" + status;
  bsWindow(url ,title,{width:"750",height:"320",buttons:
		[
		 {name:buttonName,classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="缺席确认" ){
			cw.doSaveOrUpdate(function(){
				$.MsgBox.Alert_auto("保存成功！");
				datagrid.datagrid('reload');
				//BSWINDOW.modal("hide");
			});
		}else if(v=="关闭"){
			return true;
		}
	}});
}

function doSaveOrUpdate(){
	if(checkForm()){
	    var url = "<%=contextPath %>/recruitPlanController/setPlanStatus.action";
	    var para =  tools.formToJson($("#form1"));
	    var jsonRs = tools.requestJsonRs(url,para);
	    if(jsonRs.rtState){
	      callback();
	    }else{
	    	$.MsgBox.Alert_auto(jsonRs.rtMsg);
	    }
	}
}


function download1(sid){
	$("#exportIframe").attr("src",contextPath+"/meetManage/download.action?sid="+sid);

}




//显示参会情况
function showMeetingAttendInfo(sid){
	var url = contextPath + "/system/core/base/meeting/leader/showMeetingAttendInfo.jsp?meetingId=" + sid ;
	bsWindow(url ,"查看参会情况",{width:"800",height:"360",buttons:
	   [{name:"关闭",classStyle:"btn-alert-gray"}]
	,submit:function(v,h){
	  var cw = h[0].contentWindow;
	  if(v=="修改"){
	    
	  }else if(v == "删除"){
	    
	  }else if(v=="关闭"){
	    return true;
	  }
	}});
	
}
//显示会议签阅情况
function showMeetingReadInfo(sid){
	var url = contextPath + "/system/core/base/meeting/leader/showMeetingReadInfo.jsp?meetingId=" + sid ;
	bsWindow(url ,"查看会议签阅情况",{width:"800",height:"360",buttons:
	   [{name:"关闭",classStyle:"btn-alert-gray"}]
	,submit:function(v,h){
	  var cw = h[0].contentWindow;
	  if(v=="修改"){
	    
	  }else if(v == "删除"){
	    
	  }else if(v=="关闭"){
	    return true;
	  }
	}});
	
}

</script>
</head>
<body style="padding-left: 10px;padding-right: 10px;">
	<div id="toolbar" style="min-width:950px;">
	<div class="base_layout_top" style="position:static">
	    <img class = 'fl' style="margin-right: 10px; margin-top: 3px" src="<%=contextPath %>/common/zt_webframe/imgs/hygl/wdhy/我的会议.png">
		<p class="title" style="padding-top: 4px;">我的会议</p>
	</div>
	<span class="basic_border fl"></span>
   		<form id="form1" name="form1" method="post" style="padding:10px">
		<table class="none_table" width="100%" style="font-size:12px" >
   			<tr>
   				 <td nowrap class="TableData TableBG">
					会议名称:
				</td>
				 <td nowrap class="TableData ">
					<input type="text" name="meetName" id="meetName" value="" class="BigInput"  style="height:20px;" maxlength="100" > 
				 </td>
				  <td nowrap class="TableData TableBG">
				           时间: 
				   </td>
				   <td nowrap class="TableData">
   				        从 <input type="text" name="startTimeStr" id="beginTime" size="10" maxlength="20" class="BigInput Wdate" value=""  onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})">

       				 至<input type="text" name="endTimeStr" id="endTime" size="10" maxlength="20" class="BigInput Wdate" value=""  onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
      
  			
<!-- 					&nbsp;&nbsp;	  申请人: -->
<!-- 					<input type="hidden" name="userId" id="userId" value="">  -->
<!-- 					<input type="text" name="userName" id="userName" value="" readonly class='readonly BigInput'>  -->
			
<!-- 					<a href="javascript:void(0);" onClick="selectSingleUser(['userId', 'userName'])">添加</a> -->
<!-- 					<a href="javascript:void(0);" class="orgClear" onClick="clearData('userId', 'userName')">清空</a>	 -->
					</td>
					 <td nowrap class="TableData">
  				    <input type="reset" value="重置" class="btn-win-white fr" >
   					<input type="button" value="查询" class="btn-win-white fr" onclick="query()" style="margin-right:10px;">
         		</td>
				
  			</tr>
  	<!-- 		<tr>
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
   			   </td>
   			  </tr> -->
   			</table>
		</form>
	</div>
<table id="datagrid"></table>
 <iframe id="exportIframe" style="display:none"></iframe>
</body>

</html>