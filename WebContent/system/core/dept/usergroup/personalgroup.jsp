<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/header/header2.0.jsp" %>
<%@include file="/header/easyui2.0.jsp"%>

<%
	String paraValue = request.getParameter("paraValue") == null ? "" : request.getParameter("paraValue");
	String Puuid = request.getParameter("uuid") == null ? "" : request.getParameter("uuid");
	String paraName = request.getParameter("paraName") == null ? "" : request.getParameter("paraName");
	paraName = paraName.replace("\'", "\\\'");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>个人自定义分组</title>
<style>
table{
	border-collapse: collapse;
    border: 1px solid #f2f2f2;
    width:100%;
}
table tr{
	line-height:35px;
	border-bottom:1px solid #f2f2f2;
}
table tr td:first-child{
	text-indent:10px;
}
table tr:first-child td{
	font-weight:bold;
}
table tr:first-child{
	border-bottom:2px solid #b0deff!important;
	background-color: #f8f8f8; 
}
</style>
<script type="text/javascript" src="<%=contextPath %>/common/js/tools.js"></script>
<script type="text/javascript">
var paraValue = '<%=paraValue%>';
var puuid = "<%=Puuid%>";
var paraName = "<%=paraName%>";
function doInit(){
	var url = "<%=contextPath %>/userGroup/getUserGroupByPersonList.action";
	var jsonObj = tools.requestJsonRs(url);
	if(jsonObj.rtState){
		var data = jsonObj.rtData;
		if(data.length >0){
			var tableStr = "<table  width='90%' align='center' style='font-size:12px;'>"
			      + " <tbody id='tbody'>"
			      + "<tr  class='TableHeader'>"
			      + "<td >排序号 </td>"
			      + "<td > 用户组名称</td>"
			      + "<td > 操作</td>"
			      +"</tr>";
				for(var i = 0;i<data.length;i++){
					var obj = data[i];
					//var id = obj.sid;
					tableStr = tableStr + "<tr  align='center'>"
					+"<td width='200px;'>" + obj.orderNo + "</td>"
					+"<td width='200px;'>" + obj.groupName+ "</td>"
					+"<td width='200px' >" +"<a href='javascript:toMenu(\"" + obj.uuid+ "\")'>修改</a>"
					 +"&nbsp;&nbsp;<a href='javascript:deleteUserGroup(\"" + obj.uuid+ "\")'>删除</a>"
					 +"</td>"
				
					+ "</tr>";		
				/* $("#sysPara-child-a-" + obj.uuid).bind("click",function(){
			 		toMenu(obj.uuid);
				}); */
				}
			    tableStr = tableStr + " </tbody></table>";
				$("#content").append(tableStr);
		
	}else{
		messageMsg("没有相关数据！", "content" ,'info',280);
	}

	}else{
		$.MsgBox.Alert_auto(jsonObj.rtMsg);
	}
}

function deleteUserGroup(uuid){ 
	 $.MsgBox.Confirm("提示", "确认要删除此分组？",function(){
		var url = contextPath +"/userGroup/delUserGroup.action";
		var jsonRs = tools.requestJsonRs(url,{uuid:uuid});
		if(jsonRs.rtState){
			$.MsgBox.Alert_auto("删除成功！");
			window.location.reload();
		}else{
			//$.MsgBox.Alert_auto(jsonRs.rtMsg);
			$.MsgBox.Alert_auto(jsonRs.rtMsg);
		}
		
	});
	
}

function toMenu(uuid){
	window.location = "<%=contextPath%>/system/core/dept/usergroup/addupdatePersonalGroup.jsp?uuid="+uuid;
	
}

function toAddGroup(){
	window.location = "<%=contextPath%>/system/core/dept/usergroup/addupdatePersonalGroup.jsp";
}



</script>

</head>
<body onload="doInit()" style="overflow:hidden;padding-left: 10px;padding-right: 10px">
<div id="toolbar" class="topbar clearfix">
     <div class="fl">
         <img class="title_img" src="<%=contextPath %>/system/frame/classic/imgs/icon/desktop/icon_zdyyhz.png" alt="" />
         &nbsp;<span class="title">个人用户分组管理</span>
     </div>
     <div class="fr">
         <input onclick="javascript:toAddGroup();" class="btn-win-white" type = "button" value = "新增分组"/>
     </div>
</div>

  <div id="content"> 
</body>
</html>