<%@page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String id = request.getParameter("id") == null ? "0" : request.getParameter("id") ;
	String isPub = request.getParameter("isPub") == null ? "1" : request.getParameter("isPub") ;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="background-color: #f2f2f2">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>审批公告</title>
	<%@ include file="/header/header2.0.jsp" %>
	<%@ include file="/header/easyui2.0.jsp" %>
	<%@ include file="/header/validator2.0.jsp" %>
	<%@ include file="/header/upload.jsp" %>
	<%@ include file="/header/userheader.jsp" %>	
	
	<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
	<script>window.UEDITOR_HOME_URL = "<%=contextPath%>/common/ueditor/";</script>
	<script type="text/javascript" charset="utf-8" src="<%=contextPath%>/common/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8" src="<%=contextPath%>/common/ueditor/ueditor.all.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="<%=contextPath%>/common/ueditor/lang/zh-cn/zh-cn.js"></script>

<script type="text/javascript">
var systemImagePath = "<%=systemImagePath%>";

var id =<%=id%> ;
var isPub = "<%=isPub%>";
function doInit(){
	getNotifyById(id);
	initIspub(isPub);
}
/**
* 更新
*/
function doSave(pub){
	if (checkForm()){
		var url = "<%=contextPath%>/teeNotifyController/audNotify.action";
		var para =  tools.formToJson($("#form1")) ;
		para["publish"] = pub;
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			$.MsgBox.Alert_auto("更新成功！");
			 return true;
			
		}else{
			$.MsgBox.Alert_auto(jsonRs.rtMsg);
			return false;
		}
	}
}
/**
 * 获取公告信息
 */
function getNotifyById(id){
		var url = "<%=contextPath%>/teeNotifyController/getNotifyById.action?id="+id;
		var para =  {} ;
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			var data = jsonRs.rtData;
			bindJsonObj2Cntrl(data);
			var sendTime =  getFormatDateStr(data.sendTime , 'yyyy-MM-dd HH:mm');
			var beginDate = getFormatDateStr(data.beginDate , 'yyyy-MM-dd');
			var endDate = getFormatDateStr(data.endDate , 'yyyy-MM-dd');
			$("#sendTime").val(sendTime);
		/* 	$("#beginDate").val(beginDate);
			$("#endDate").val(endDate); */
		}else{
			$.MsgBox.Alert_auto(jsonRs.rtMsg);
		}
}

function checkForm(){
    return $("#form1").valid(); 
}
/* 同意 */
function doPub(){
	return doSave(1);
}
/* 不同意 */

function doNoPub(){
	return doSave(3);
}
/* 初始化 */
function initIspub(isPub){
	if(isPub == "1"){
		$("#pub").show();
		$("#noPub").hide();
		$("#reasonTR").hide();
		
	}else{
		$("#noPub").show();
		$("#pub").hide();
		$("#reasonTR").show();
		
	}
}
</script>
 
</head>
<body onload="doInit()" style="background-color: #f2f2f2">


<form  method="post" name="form1" id="form1" enctype="multipart/form-data" >
<table class="TableBlock" width="100%" align="center" >

	<tr>
    <td nowrap class="TableData" width="120" style="text-indent:15px;">
	    标题：
    </td>
    <td nowrap class="TableData" align="left">
    	<span id="subject"> </span>
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData" width="" style="text-indent:15px;">发布范围：</td>
    <td nowrap class="TableData" align="left">
    	<div><B>部门：</B><span id="toDeptNames"></span></div>
    	<div><B>角色：</B><span id="toRolesNames"></span></div>
    	<div><B>人员:</B><span id="toUserNames"></span></div>
    </td>
   </tr>
   <tr>
		<td nowrap class="TableData" width="" style="text-indent:15px;">发布时间：</td>
		<td nowrap class="TableData"><input type="text" name="sendTime"
			id="sendTime" size="20"
			onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" class="Wdate BigInput easyui-validatebox" required>
	</tr>
	<tr>
		<td nowrap class="TableData" width="" style="text-indent:15px;">生效日期：</td>
		<td nowrap class="TableData"><input type="text" name="beginDate"
			id="beginDate" size="20"
			onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate BigInput">
	</tr>
	<tr>
		<td nowrap class="TableData" width="" style="text-indent:15px;">终止日期：</td>
		<td nowrap class="TableData"><input type="text" name="endDate"
			id="endDate" size="20"
			onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate BigInput">
	</tr>
    <tr>
   		<td nowrap class="TableData" width="" style="text-indent:15px;">短信提醒：</td>
    	<td nowrap class="TableData" align="left">
    	<input name="smsRemind" id="smsRemind" type="checkbox" value="on"/>
    	使用内部短信提醒   
   	    </td>
   </tr>
       <tr>
   		<td nowrap class="TableData" width="" style="text-indent:15px;">置顶：</td>
    	<td nowrap class="TableData" align="left">
    	<input name="top" id="top" type="checkbox" value="1"/>使公告通知置顶，显示为重要 <!-- <input size="2" />天后结束置顶，0表示一直置顶  -->
   	    </td>
   </tr>
  <tr id="reasonTR">
   		<td nowrap class="TableData" width="" style="text-indent:15px;">审批意见：</td>
    	<td nowrap class="TableData" align="left">
    	<textarea name="reason" class="BigInput" id="reason" rows="4" cols="50" wrap="no"></textarea>
   	    </td>
   </tr>
   <tr style="display:none">
	    <td nowrap  class="TableData" colspan="2" align="center">
	        <input type="button" id="pub" value="批准" class="btn btn-primary" title="批准" onclick="doPub()" >&nbsp;&nbsp;
	        <input type="button" id="noPub" value="不批准" class="btn btn-primary" title="不批准" onclick="doNoPub()" >&nbsp;&nbsp;
	        <input type="button" value="关闭" class="btn btn-primary" title="关闭" onClick="CloseWindow();">
	        <input type="hidden" value="<%=id %>"  name="sid">
	    </td>
   </tr>
   
</table>
  </form>

</body>
</html>
 