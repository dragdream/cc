<%@page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String id = request.getParameter("id") == null ? "0" : request.getParameter("id") ;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>公告通知设置</title>
	<%@ include file="/header/header2.0.jsp" %>
	<%@ include file="/header/easyui2.0.jsp" %>
	<%@ include file="/header/upload.jsp" %>
	<%@ include file="/header/userheader.jsp" %>	
	
	<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
	<script>window.UEDITOR_HOME_URL = "<%=contextPath%>/common/ueditor/";</script>
	<script type="text/javascript" charset="utf-8" src="<%=contextPath%>/common/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8" src="<%=contextPath%>/common/ueditor/ueditor.all.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="<%=contextPath%>/common/ueditor/lang/zh-cn/zh-cn.js"></script>


<script type="text/javascript">
var systemImagePath = "<%=systemImagePath%>";
function doInit(){
	initSysParaData();
	
}
function initSysParaData(){
	var para = {};
	var url = "<%=contextPath%>/teeNotifyController/getNotifySysPara.action";
	var jsonRs = tools.requestJsonRs(url,para);
	if(jsonRs.rtState){
		var data  = jsonRs.rtData;
		bindJsonObj2Cntrl(data);
	}else{
		 $.MsgBox.Alert_auto(jsonRs.rtMsg);
	}
}
/**
 * 保存
 */
function doSave(){
		var url = "<%=contextPath%>/teeNotifyController/updateNotifySysPara.action";
		var para =  {};
		para["NOTIFY_AUDITING_ALL"] =$("#rangeAllId").val();
		para["NOTIFY_AUDITING_EXCEPTION"] =$("#rangeExceptionId").val();
		var isAuditing = $("#isAuditing").prop("checked");
		if(isAuditing == "true"||isAuditing==true){		
			para["NOTIFY_AUDITING_SINGLE"] =1;
		}else{		
			para["NOTIFY_AUDITING_SINGLE"] = 0;
		}
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			 $.MsgBox.Alert_auto("更新参数成功！");
		}else{
			 $.MsgBox.Alert_auto(jsonRs.rtMsg);
		}
}



</script>
 
</head>




<body onload="doInit()" style="font-size:12px;padding-left: 10px;padding-right: 10px">

<div id="toolbar" class = "topbar clearfix">
	<div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/xzbg/tzgg/icon_shenpishezhi.png">
		<span class="title">审批设置 <span id="totalMail" class="attach"></span></span>
	</div>
	<div class = "right fr clearfix">
		<input type="button" class="btn-win-white fl" onclick="doSave();" value="保存"/>
	</div>
</div>
<form  method="post" name="form1" id="form1" method="post" enctype="multipart/form-data" >
<table class="TableBlock_page">
	<tr>
   		<td nowrap class="TableData" style="text-indent:10px">发布公告是否需要审批：</td>
    	<td nowrap class="TableData" align="left">
    		<input name="isAuditing" id="isAuditing" type="checkbox" value="1" />
   	    </td>
    </tr>
    <tr>
        <td nowrap class="TableData" style="text-indent:10px">指定可审批公告人员：
        </td>
        <td nowrap class="TableData" align="left" >
          <input type="hidden" name="rangeAllId" id="rangeAllId" value="">
          <textarea readonly cols=50 name="rangeAllIdDesc" id="rangeAllIdDesc" rows=6 class="BigTextarea" style="" ></textarea>
          <span class="addSpan">
              <img src="<%=contextPath %>/common/zt_webframe/imgs/xzbg/tzgg/icon_select.png" onClick="selectUser(['rangeAllId', 'rangeAllIdDesc'])"/>
              <img src="<%=contextPath %>/common/zt_webframe/imgs/xzbg/tzgg/icon_cancel.png" onClick="clearData('rangeAllId','rangeAllIdDesc')"/>
          </span>         
        </td>
    </tr>
    <tr>
        <td nowrap class="TableData" style="text-indent:10px">指定无需审批人员：
        </td>
        <td nowrap class="TableData" align="left">
          <input type="hidden" name="rangeExceptionId" id="rangeExceptionId" value="">
          <textarea readonly cols=50 name="rangeExceptionIdDesc" id="rangeExceptionIdDesc" rows=6 class="" wrap="yes"  style="" ></textarea>
          <span class="addSpan">
          <img src="<%=contextPath %>/common/zt_webframe/imgs/xzbg/tzgg/icon_select.png" onClick="selectUser(['rangeExceptionId', 'rangeExceptionIdDesc'])"/>
          <img src="<%=contextPath %>/common/zt_webframe/imgs/xzbg/tzgg/icon_cancel.png" onClick="clearData('rangeExceptionId','rangeExceptionIdDesc')"/>
        </td>
    </tr>
</table>
</form>
</body>
</html>
 