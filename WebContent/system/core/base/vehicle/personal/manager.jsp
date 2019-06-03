
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String status = TeeStringUtil.getString(request.getParameter("status"), "");//查询会议状态
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp"%>
<title> 车辆申请管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script  type="text/javascript"  src='<%=contextPath %>/system/core/base/meeting/js/meeting.js'></script>


<script type="text/javascript">
var status = "<%=status%>";
function  doOnload(){
	queryMeeting(status);
	
	//选中样式
	$("#option" + status).addClass("active");
}
/**
 *查询待审批记录
 */
function queryMeeting(status){
	var url =   "<%=contextPath %>/meetManage/getPersonalMeetByStatus.action";
	var para = {status:status};
	var jsonObj = tools.requestJsonRs(url , para);
	if(jsonObj.rtState){
		var prcs = jsonObj.rtData;
		$("#listDiv").empty();
		if(prcs.length > 0){
			var tableStr = "<table class='TableList' width='99%' align='center'><tbody id='listTbody'>";
		    	 tableStr = tableStr + "<tr class='TableHeader'>"
			      	 + "<td width='100' align='center'>会议名称</td>"
			    	 + "<td width='100' align='center'>会议标题</td>"
			     	 +"<td nowrap  width='100'  align='center'>申请时间</td>"
			     	 +"<td nowrap  width='90'  align='center'>开始时间</td>"
			     	 +"<td nowrap  width='90'  align='center'>结束时间</td>"
			     	 +"<td nowrap width='80' align='center'>会议室</td>"
			      	 +"<td nowrap width='80' align='center'>审批人</td>"
			      	 +"<td nowrap width='' align='center'>出席人</td>"
			      	 +"<td nowrap  width='60' align='center'>操作</td>"
			         +"</tr>";
			for(var i = 0;i<prcs.length ; i++){
				var prc = prcs[i];
				var id = prc.sid;
				var userId = prc.userId;
				var status = prc.status;
				var  fontStr = "";
				var opts = "";  
				if(status == 0 || status == 3){
					opts = opts + "<a href='javascript:void(0);'  onclick='toEdit(\"" + id + "\"," + status +");'>修改</a>"
					+  "&nbsp;&nbsp;<a href='javascript:void(0);'  onclick='deleteMeetingById(\"" + id + "\"," + status +");'>删除</a>";
				}
				
				
		
				
				var attendDesc = "<span class='ui-state-highlight ui-corner-al'  style='font-weight:bold;'>外部人员:</span>" + prc.attendeeOut ;
				attendDesc = attendDesc +  "<br><font class='ui-state-highlight ui-corner-al'  style='font-weight:bold;'>内部人员:</font>" + prc.attendeeName;
			
				tableStr = tableStr +"<tr class=''>"
						 +"<td nowrap  align='center'><font color='" + fontStr + "'><a href='javascript:void(0);' onclick='meetingDetail("+id+")' >"+ prc.meetName +"</a></font></td>"
						 +"<td nowrap   align='center'><font color='" + fontStr + "'>" + prc.subject + "</font></td>"	
						 +"<td nowrap   align='center'><font color='" + fontStr + "'>"+ prc.createTimeStr +"</font></td>"
				      	 +"<td nowrap align='center' ><font color='" + fontStr + "'>" + prc.startTimeStr + "</font></td>"
				     	 +"<td nowrap align='center'  ><font color='" + fontStr + "'>" + prc.endTimeStr + "</font></td>"
				     	 +"<td nowrap align='center'  ><font color='" + fontStr + "'>" + prc.meetRoomName + "</font></td>"
				    	
				     	 +"<td nowrap align='center' ><font color='" + fontStr + "'>" + prc.managerName + "</font></td>"
					     	+"<td  align='left'  ><font color='" + fontStr + "'>" + attendDesc + "</font></td>"
					     +"<td nowrap align='center'  >"
					     +opts
					     +"</td>"
				         +"</tr>";
			}
			tableStr = tableStr + "</tbody></table>";	
			$("#listDiv").append(tableStr);	
		}else{
		 	messageMsg("暂无" + MEETING_STATUS_TYPE_DESC[meetStatus] + "会议记录", "listDiv" ,'' ,380);
		}
	}else{
		alert(jsonObj.rtMsg);
	}
}


/**
 * 删除会议记录 ById
 * @param id
 */
function deleteMeetingById(id){
	var submit = function (v, h, f) {
	    if (v == 'ok'){
	    	var url = contextPath + "/meetManage/deleteById.action";
	    	var jsonRs = tools.requestJsonRs(url,{sid:id});
	    	if(jsonRs.rtState){
	    		$.jBox.tip('删除成功','info',{timeout:1000});
	    		queryMeeting(meetStatus);
	    	
	    	}else{
	    		alert(jsonRs.rtMsg);
	    	}
	    }
	};
	$.jBox.confirm("确定要删除吗,删除后不可恢复？", "提示", submit);
	
}


/*
 * 弹出修改会议
 */
function toEdit(id){
	var title = "编辑会议申请";
	bsWindow(url ,title,{width:"700",height:"360",buttons:
		[
		 {name:"保存",classStyle:"btn btn-primary"},
	 	 {name:"关闭",classStyle:"btn btn-primary"}
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

/**
 * 点击查询会议类型
 */
function setMeetType(meetStatus){
	if(meetStatus || meetStatus == 0){
		queryMeeting(meetStatus);
	}else{
		window.location.href = "<%=contextPath%>/system/core/base/meeting/personal/index.jsp";
		
	}
	
}

</script>
</head>
<body class="" topmargin="5" onload="doOnload();">


<table id="datagrid" fit="true"></table>
	<div id="toolbar">
		<div class="moduleHeader">
			<b class="pull-left"><i class="glyphicon glyphicon-user"></i>&nbsp;会议申请管理</b>
			
			<div class="btn-group pull-right" data-toggle="buttons">
			  <label class="btn btn-default " onclick="setMeetType();">
			    <input type="radio" name="options" id="option" >会议申请
			  </label>
			   <label class="btn btn-default"  onclick="setMeetType(0);"  id="option0">
			    <input type="radio" name="options">待审批外会议
			  </label>
			  <label class="btn btn-default"  onclick="setMeetType(1);" id="option1">
			    <input type="radio" name="options" id="option1">已审批外会议
			  </label>
			   <label class="btn btn-default"  onclick="setMeetType(2);"  id="option2">
			    <input type="radio" name="options">进行中会议
			  </label>
			   <label class="btn btn-default"  onclick="setMeetType(3);"  id="option3">
			    <input type="radio" name="options" >未批会议
			  </label>
			</div>
			<div style="clear:both"></div>
		</div>

	<div id='listDiv'>
		

	

		<!-- <input type="hidden" name="meetRoomSid" id="meetRoomSid" /> -->
	</div>
		

</body>

</html>