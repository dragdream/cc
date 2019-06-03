<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


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
<title>自定义分组</title>
<%@ include file="/header/header2.0.jsp" %>
<link href="<%=cssPath %>/style.css" rel="stylesheet" type="text/css" />


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
<script type="text/javascript">
var paraValue = '<%=paraValue%>';
var puuid = "<%=Puuid%>";
var paraName = "<%=paraName%>";
function doInit(){
	//alert(paraValue);
	var url = "<%=contextPath %>/userGroup/getUserGroupList.action";
	var jsonObj = tools.requestJsonRs(url);
	//alert(jsonObj.rtState);
	if(jsonObj.rtState){
		var json = jsonObj.rtData;
		jQuery.each(json, function(i, sysPara) {
			//alert(sysPara.paraName);
			//var codeValue = sysPara.codeValue;
		 	$("#tbody").append("<tr>"
					+"<td nowrap align='center' style='width:10%'>" + sysPara.orderNo+ "</td>"
					+"<td nowrap align='center' style='width:40%'>" + sysPara.groupName + "</td>"
					+"<td nowrap align='center' style='width:30%'>"
					 +"<a href='#' id='sysPara-child-a-" + sysPara.uuid + "'>修改</a>"
					 +"&nbsp;&nbsp;<a href='javascript:deleteUserGroup(\"" + sysPara.uuid+ "\")'>删除</a>"
					 +"</td>"
		  	+ "</tr>"); 
		 	//alert(123);
		 	$("#sysPara-child-a-" + sysPara.uuid).bind("click",function(){
		 		toMenu(sysPara.uuid);
			});
		});

	}else{
		$.MsgBox.Alert_auto(jsonObj.rtMsg);
	}
}


function deleteUserGroup(uuid){
	 $.MsgBox.Confirm ("提示", "确认要删除此编码？", function(){
		 var url = "<%=contextPath %>/userGroup/delUserGroup.action";
			var jsonRs = tools.requestJsonRs(url,{uuid:uuid});
			if(jsonRs.rtState){
				$.MsgBox.Alert_auto("删除成功！");
				window.location.reload();
			}else{
				$.MsgBox.Alert_auto(jsonRs.rtMsg);
			} 
	  });
	
}

function toMenu(uuid){
	//alert(uuid);
	window.location = "<%=contextPath%>/system/core/dept/usergroup/addupdateGroup.jsp?uuid="+uuid;
	
}

function toAddGroup(){
	
	//alert(123);
	//var parent = window.parent.contentFrame;
	
	window.location.href = "<%=contextPath%>/system/core/dept/usergroup/addupdateGroup.jsp";
}



</script>

</head>
<body onload="doInit()">
   <div style="margin-left:3px;margin-right: 10px;margin-bottom: 5px;margin-top: 5px">
      <img src="<%=contextPath %>/common/zt_webframe/imgs/common_img/icon_yhcx.png" align="absMiddle">&nbsp;&nbsp; <span>分组管理</span>
      &nbsp;&nbsp;
      <input onclick="javascript:toAddGroup();"  class="btn-win-white"  type = "button" value = "新增分组"/>
   </div>

 
   <table width="80%" id="tbody" >
        <tr class="TableHeader">
      		<td nowrap align="center" style='width:10%'>排序号</td>
     	    <td nowrap align="center" style='width:40%'>用户组名称</td>
      		<td nowrap align="center" style='width:30%'>操作</td>
       </tr>
   </table>

</body>
</html>