<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String uuid = TeeStringUtil.getString(request.getParameter("uuid"), "");

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>论坛板块详情</title>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<script type="text/javascript">
var uuid = "<%=uuid%>";
function doInit(){
	if(uuid){
		getInfoById(uuid);
	}
}
/* 查看详情 */
function getInfoById(id){
	var url =   "<%=contextPath%>/TeeTopicSectionController/getInfoById.action";
	var para = {uuid : id};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		if (prc) {
			bindJsonObj2Cntrl(prc);
			if(prc.newTopicSmsPriv=='1'){
				$("#newTopicSmsPriv").text("是");
			}else{
				$("#newTopicSmsPriv").text("否");
			}
			if(prc.replySmsPriv=='1'){
				$("#replySmsPriv").text("是");
			}else{
				$("#replySmsPriv").text("否");
			}
			if(prc.anonymous==1){
				$("#anonymous").text("是");
			}
			if(prc.anonymous==0){
				$("#anonymous").text("否");
			}
			
			
			
		}
	} else {
		$.MsgBox.Alert_auto(jsonObj.rtMsg);
	}
}



function toReturn(){
	location.href = contextPath + "/system/subsys/topic/index.jsp?option=板块管理";
}
</script>
</head>
<body onload="doInit();" style="padding-left: 10px;padding-right: 10px">
    <div id="toolbar" class="topbar clearfix">
       <div class="fl left">
       <img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/tlq/icon_bankuaixiangqing.png">
          <span class="title">论坛板块详情</span>
       </div>
       <div class="fr right">
          <input type="button"  value="返回" class="btn-win-white" onclick="toReturn();"/>
       </div>
    </div>
    
   <form action=""  method="post" name="form1" id="form1">
<input type="hidden" name="uuid" id="uuid" value="<%=uuid%>">
<table class="TableBlock_page" >
	<tr>
		<td nowrap class="TableData"  style="text-indent:15px;width: 30%">版块名称：</td>
		<td class="TableData"  id="sectionName">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" style="text-indent:15px">版主（最多5人）：</td>
		<td class="TableData" id="managerName">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" style="text-indent:15px">可发帖人员 ：</td>
		<td class="TableData" id="crPrivName">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" style="text-indent:15px">版块可见人员 ：</td>
		<td class="TableData" id="viewPrivName">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" style="text-indent:15px">新发贴时给可见人员发送系统消息 ：</td>
		<td class="TableData" id="newTopicSmsPriv">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" style="text-indent:15px">有跟贴时给楼主发送系统消息  ：</td>
		<td class="TableData" id="replySmsPriv">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" style="text-indent:15px">是否允许匿名 ：</td>
		<td class="TableData" id="anonymous">
		</td>
	</tr>
	
	<tr>
		<td nowrap class="TableData" style="text-indent:15px">排序 ：</td>
		<td class="TableData" id="orderNo">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" style="text-indent:15px">说明 ：</td>
		<td class="TableData" id="remark">
			<!-- <textarea rows="10" cols="200"  name="remark" class="BigTextarea" disabled="disabled"></textarea> -->
		</td>
	</tr>
</table>
</form> 
    

</body>


<%-- <body onload="doInit();">
<div class="base_layout_top" style="position:static">
	<span class="easyui_h1">论坛版块详情</span>
</div>


<form action=""  method="post" name="form1" id="form1">
<input type="hidden" name="uuid" id="uuid" value="<%=uuid%>">
<table class="none-table" >
	<tr>
		<td nowrap class="TableData"  >版块名称：</td>
		<td class="TableData"  id="sectionName">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" >版主（最多5人）：</td>
		<td class="TableData" id="managerName">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" >可发帖人员 ：</td>
		<td class="TableData" id="crPrivName">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" >版块可见人员 ：</td>
		<td class="TableData" id="viewPrivName">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" >新发贴时给可见人员发送系统消息 ：</td>
		<td class="TableData" id="newTopicSmsPriv">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" >有跟贴时给楼主发送系统消息  ：</td>
		<td class="TableData" id="replySmsPriv">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" >是否允许匿名 ：</td>
		<td class="TableData" id="anonymous">
		</td>
	</tr>
	
	<tr>
		<td nowrap class="TableData" >排序 ：</td>
		<td class="TableData" id="orderNo">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" >说明 ：</td>
		<td class="TableData" id="remark">
			<textarea rows="10" cols="83"  name="remark" class="BigTextarea" disabled="disabled"></textarea>
		</td>
	</tr>
	<tr >
		<td colspan="2">
			<input type="button"  value="返回" class="btn btn-default" onclick="toReturn();"/>&nbsp;&nbsp;
		</td>
	</tr>
</table>
</form>
</body> --%>
</html>