
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header.jsp"%>
<title>已自评</title>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/examine/js/examine.js"></script>
<script type="text/javascript">
function  doOnload(){
	query();
}
/**
 *查询管理
 */
function query(){
	var url =   "<%=contextPath %>/TeeExamineSelfDataManage/getCurrUserSelf.action";
	var jsonObj = tools.requestJsonRs(url , {selfType:2});
	if(jsonObj.rtState){
		var prcs = jsonObj.rtData;
		$("#listDiv").empty();
		if(prcs.length > 0){
			var tableStr = "<table class='TableList' width='90%' align='center'><tbody id='listTbody'>";
		    	 tableStr = tableStr + "<tr class='TableHeader'>"
			      	 +"<td nowrap align='center'>考核项目名称</td>"
			      	 +"<td nowrap align='center'>考核人  </td>"
			     	 +"<td nowrap  width='80px'  align='center'>状态</td>"
			      	 +"<td nowrap  width='80px' align='center'>操作</td>"
			         +"</tr>";
			         
			for(var i = 0;i<prcs.length ; i++){
				var prc = prcs[i];
				var id = prc.sid;
				var groupId= prc.groupId;
				var  fontStr  = "";
				tableStr = tableStr + "<tr class=''>"
				      	 +"<td width='' nowrap align='center'><font color='" + fontStr + "'>"+ prc.taskTitle +"</font></td>"
				      	 +"<td nowrap align='center'><font color='" + fontStr + "'>" + prc.rankmanNames + "</font></td>"
				      	 +"<td nowrap align='center' width='50px'><font color='" + fontStr + "'>已自评</font></td>"
						 +"<td nowrap align='center' width='50px'>"
				      	 + "<a href='javascript:void(0);' data-toggle=\"modal\" data-target=\"#myAffairModal\" onclick='toSelfInfo(" + id  + "," + groupId+ ");'> 详细信息</a>"
				      	 +"</td>"
				         +"</tr>";
			}
			tableStr = tableStr + "</tbody></table>";
			$("#listDiv").append(tableStr);	
		}else{
		 	messageMsg("没有相关已自评考核任务信息", "listDiv" ,'' ,380);
		}
	}else{
		alert(jsonObj.rtMsg);
	}
}


</script>
</head>
<body class="" topmargin="5" onload="doOnload();">
	<table border="0" width="100%" cellspacing="0" cellpadding="3"
		class="small">
		<tr>
			 <td class="Big"><span class="Big3">&nbsp;&nbsp; 已自评的考核任务</span><br>
			</td> 
			
		</tr>
	</table>
	<br>
	<div id='listDiv'></div>
	

</body>

</html>