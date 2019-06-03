
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String meetStatus = TeeStringUtil.getString(request.getParameter("meetStatus"), "0");//查询会议状态
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp"%>
<title>会议申请管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script  type="text/javascript"  src='<%=contextPath %>/system/core/base/meeting/js/meeting.js'></script>


<script type="text/javascript">
var meetStatus = "<%=meetStatus%>";
/**
 * 查询类型  meetingStatus
0、	待批
1、	已批准
2、	进行中
3、	未批准
4、已结束
*/
function  doOnload(){
	queryMeeting(meetStatus);
	
	//选中样式
	$("#option" + meetStatus).addClass("active");
}
/**
 *查询待审批记录
 */
function queryMeeting(meetStatus){
	var url =   "<%=contextPath %>/meetManage/getLeaderMeetByStatus.action";
	var para = {status:meetStatus};
	var jsonObj = tools.requestJsonRs(url , para);
	if(jsonObj.rtState){
		var prcs = jsonObj.rtData;
		$("#listDiv").empty();
		if(prcs.length > 0){
			var tableStr = "<table class='TableList' width='99%' align='center'><tbody id='listTbody'>";
		    	 tableStr = tableStr + "<tr class='TableHeader'>"
			      	 + "<td width='' align='center'>会议名称</td>"
			      	// + "<td width='' align='center'>会议主题</td>"
			     	 +"<td nowrap width='20%' align='center'>申请人</td>"
			     	 +"<td nowrap  width='10%'  align='center'>申请时间</td>"
			     	 +"<td nowrap  width='10%'  align='center'>开始时间</td>"
			     	 +"<td nowrap  width='10%'  align='center'>结束时间</td>"
			     	 +"<td nowrap width='14%' align='center'>会议室</td>"
			      	 +"<td nowrap  width='80' align='center'>操作</td>"
			         +"</tr>";
			for(var i = 0;i<prcs.length ; i++){
				var prc = prcs[i];
				var id = prc.sid;
				var userId = prc.userId;
				var status = prc.status;
				var  fontStr = "";
				var opts = "";  
				if(status == 0 || status == 3){
					opts =  opts + "<a href='javascript:void(0);'  onclick='approv(\"" + id + "\",1," + userId +");'>批准</a>"
					+  "&nbsp;&nbsp;<a href='javascript:void(0);'  onclick='approv(\"" + id + "\",3," + userId +");'>不批准</a>";
				}
				if(status == 1 || status == 2){
					opts = opts + "<a href='javascript:void(0);'  onclick='toEndMeet(\"" + id + "\"," + status +");'>结束</a>";
				}
				
				/* if(status == 3){//不批准
					opts =  opts + "<a href='javascript:void(0);'  onclick='approv(\"" + id + "\",1," + userId +");'>批准</a>";
				} */
				opts = opts + "&nbsp;&nbsp;<a href='javascript:void(0);'  onclick='toEdit(\"" + id + "\"," + status +");'>修改</a>"
					+  "&nbsp;&nbsp;<a href='javascript:void(0);'  onclick='deleteMeetingById(\"" + id + "\"," + status +");'>删除</a>";
			
				tableStr = tableStr +"<tr class=''>"
						 +"<td nowrap  align='center'><font color='" + fontStr + "'><a href='javascript:void(0);' onclick='meetingDetail("+id+")' >"+ prc.meetName +"</a></font></td>"
						// +"<td nowrap   align='center'><font color='" + fontStr + "'>" + prc.subject + "</font></td>"	
						 +"<td nowrap align='center' ><font color='" + fontStr + "'>" + prc.userName + "</font></td>"
						 +"<td nowrap   align='center'><font color='" + fontStr + "'>"+ prc.createTimeStr +"</font></td>"
				      	 +"<td nowrap align='center' ><font color='" + fontStr + "'>" +  prc.startTimeStr + "</font></td>"
				     	 +"<td nowrap align='center'  ><font color='" + fontStr + "'>"  + prc.endTimeStr + "</font></td>"
				     	 +"<td nowrap align='center'  ><font color='" + fontStr + "'>" + prc.meetRoomName + "</font></td>"
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
	    	
	    		refreshParentLeaderCount();
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
	var url = contextPath + "/system/core/base/meeting/personal/apply/addOrUpdate.jsp?id=" + id  + "&isLeaderOpt=1" ;
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
 * 批准或者不批准
 @param  sid -Id
 @param  allow -允许
 @param  userId -用户Id
 */
function approv(sid , allow , userId){
	var allowDesc  = "确定要批准该会议吗？";
	if(allow == 3){
		allowDesc = "确定不批准该会议吗？";
	}
	var submit = function (v, h, f) {
	    if (v == 'ok'){
	    	var url = contextPath + "/meetManage/approve.action";
	    	var jsonRs = tools.requestJsonRs(url,{sid:sid , status:allow , userId: userId});
	    	if(jsonRs.rtState){
	    		//alert(jsonRs.rtMsg);
	    		$.jBox.tip('审批成功','info',{timeout:1000});
	    		queryMeeting(meetStatus);
	    		refreshParentLeaderCount();
	    		return true;
	    	}else{
	    		alert(jsonRs.rtMsg);
	    	}
	    }
	};
	$.jBox.confirm(allowDesc, "提示", submit);
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


/**
 * 执行上级页面 审批数量
 */
function refreshParentLeaderCount(){
	window.parent.getLeaderCount();
}
</script>
</head>
<body class="" topmargin="5" onload="doOnload();">


<table id="datagrid" fit="true"></table>
	<div id="toolbar">
		<div class="moduleHeader">
			<b class="pull-left"><i class="glyphicon glyphicon-user"></i>&nbsp;会议管理</b>
			
			<div style="clear:both"></div>
		</div>

	<div id='listDiv'>
		

	

		<!-- <input type="hidden" name="meetRoomSid" id="meetRoomSid" /> -->
	</div>
		

</body>

</html>