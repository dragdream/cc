<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%
 	int groupId = TeeStringUtil.getInteger(request.getParameter("groupId"), 0);//指标集Id
 	int taskId = TeeStringUtil.getInteger(request.getParameter("taskId"), 0);//考核任务Id
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<title >自评 </title>
<script type="text/javascript">


var taskId = <%=taskId%>;
var groupId = <%=groupId%>;
function doInit(){

	var url =   "<%=contextPath%>/TeeExamineItemManage/getAllByGroupId.action";
	var para = {groupId:groupId};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		$("#tbody").empty();
		var prcs = jsonObj.rtData;
		$(prcs).each(function(i,item){
			
			var temp = item.itemName  + "<font color='red'>(" + item.itemMin +" ~ " + item.itemMax + ")</font><br>分值说明 ：" + item.itemDesc;
			var tempStr = '<tr class="TableData">'
			   + '<td width="140">'
			   + temp
			   +  '</td>'
			   + '<td align="left" id="item_score_' + item.sid + '">'
			    	
			   + ' </td>'
			   + ' <td align="left" id="itemDesc_' + item.sid + '">'
			   +' </td>'
			   + '</tr>';
			   $("#tbody").append(tempStr);
			   //validType:validTypeStr
		});
		
		//获取自评信息;
		getSelfInfo(groupId , taskId);
	} else {
		alert(jsonObj.rtMsg);
	}
}

/**
 * 获取每个考核信息
 */
function getSelfInfo(groupId ,taskId ){
	
	
	var url =   "<%=contextPath%>/TeeExamineSelfDataManage/getSelfDataByTask.action";
	var para = {groupId: groupId , taskId:taskId} ;
	var jsonObj = tools.requestJsonRs(url, para);
	
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		var selfData = prc.selfData; 
		var itemArray = new Array();
		$("#selfDate").html(prc.selfDateStr);
		/**
		 * 将字符串转化为Json
		 */
		itemArray = tools.string2JsonObj(selfData);
		
		$.each(itemArray,function(i , e){
			var itemId = e.itemId;
			var score = e.score ;
			var itemDesc =  e.itemDesc;
			$("#item_score_" + itemId).html(score);
			$("#itemDesc_" + itemId).html(itemDesc);
		});
		  
	}else{
		alert(jsonObj.rtMsg);
	} 
	
	
}
</script>

</head>

<body onload="doInit();">
<table border="0" width="100%" cellspacing="0" cellpadding="3"   style="margin:8px 0px;"  class="small">
  <tr>
    <td class="Big3"><span class="big3">&nbsp;自评(<%=userName %>) &nbsp; 自评时间：<font id="selfDate" color="red"></font> </span>
    </td>
  </tr>
</table>

<div style="margin:0 auto;">
	<form name="form1" id="form1" method="post">
		<table class="TableList" width="85%" align="center">
			<tr class="TableHeader" align="center">
			    <td width="350">自评考核项目</td>
			    <td width="100">分数</td>
			    <td width=>自评说明</td>
			</tr>
			<tbody id="tbody">
				
			</tbody>
			<tr class="TableData" align="center" >
				<td colspan="3">
					
					<input type="button" class="btn btn-primary" value="返回" onclick="history.go(-1);">
				</td>
				
			</tr>	   
		</table>
	
	</form>
</div>

</body>
</html>


