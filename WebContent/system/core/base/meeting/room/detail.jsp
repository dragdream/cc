<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>

<%
	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);//日程Id
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"  style="background: #f4f4f4;">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header2.0.jsp"%>
<title>会议室详情</title>
<style type="text/css">
</style>



<script type="text/javascript">

var sid = <%=sid%>;


function doInit()
{
	
	if(sid > 0){
		getById(sid);
		getRecordById(sid);
	}
}



/**
 * 获取外出 by Id
 */
function getById(id){
	var url =   "<%=contextPath%>/meetRoomManage/getById.action";
	var para = {sid : id};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		if (prc && prc.sid) {
			bindJsonObj2Cntrl(prc);
		}
	} else {
		alert(jsonObj.rtMsg);
	}
}
	
function getRecordById(id){
	var url =   "<%=contextPath%>/meetRoomManage/getRecordById.action";
	var para = {sid : id};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var html ="<table class='TableBlock' style='width:100%'>";
		html+="<tr class='TableData'><td>会议名称</td><td>申请人</td><td>开始时间</td><td>结束时间</td><td>状态</td></tr>";
		var data = jsonObj.rtData;
		for(var i=0;i<data.length;i++){
			var status ="";
			if(data[i].status==0){
				status="待批";
			}else if(data[i].status==1){
				status='已批准';
			}else if(data[i].status ==2){
				status ="进行中";
			}else if(data[i].status ==3){
				status="未批准";
			}else if(data[i].status==4){
				status = '已结束';
			}
			html+="<tr class='TableData'><td>"+data[i].meetName+"</td><td>"+data[i].userName+"</td><td>"+data[i].startTimeStr+"</td><td>"+data[i].endTimeStr+"</td><td>"+status+"</td></tr>";
		}
		html+="</table>";
		$("#containor").html(html);
	} else {
		$("#containor").html("没有相关会议记录！");
	}
}
</script>

</head>
<body onload="doInit();">
<form id="form1" name="form1" method="post">
	<table class="TableBlock" width="100%" align="center" style="margin-top:10px;">
		<tr>
			<td nowrap class="TableData" width="100" style="width:120px;background:#f0f0f0">会议室名称：</td>
			<td class="TableData"  id="mrName">
				
			</td>
		</tr>
		<tr>
			<td nowrap class="TableData" width="100" style="width:120px;background:#f0f0f0">会议室描述：</td>
			<td class="TableData"  id="mrdesc">
			</tr>
		<tr class="TableData">
			<td nowrap style="width:120px;background:#f0f0f0">会议室管理员：</td>
			<td nowrap align="left" id="managerNames">
					
			</td>
		</tr>
			<tr class="TableData" id="">
			<td nowrap style="width:120px;background:#f0f0f0">申请权限部门：</td>
			<td nowrap align="left" id="postDeptNames">
			</td>
		</tr> 
		<tr class="TableData" id="">
			<td nowrap style="width:120px;background:#f0f0f0">申请权限人员：</td>
			<td nowrap align="left"  id="postUserNames">
				</td>

		<tr class="TableData">
			<td nowrap style="width:120px;background:#f0f0f0">可容纳人数：</td>
			<td nowrap align="left" id="mrCapacity">
			
		    </td>
		</tr>
		<tr>
			<td nowrap class="TableData" width="100"  style="width:120px;background:#f0f0f0">设备情况：</td>
			<td class="TableData" id="mrDevice">
				</td>
		</tr>
		<tr>
			<td nowrap class="TableData" width="100"  style="width:120px;background:#f0f0f0">地址：</td>
			<td class="TableData" id="mrPlace">
				</td>
		</tr>
		<tr>
			<td nowrap class="TableData" width="100"  style="width:120px;background:#f0f0f0">申请记录：</td>
			<td class="TableData">
				<div id='containor'>
					
				</div>
			</td>
		</tr>
	</table>
	<input id="sid" name="sid" type="hidden" value="0"> 
</form>
</body>

</html>
