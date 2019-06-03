<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String humanDocSid=request.getParameter("humanDocSid");
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script>
function commit(){
	parent.$.MsgBox.Confirm ("提示", "确认要进行手动折旧吗？",function(){
		var json = tools.requestJsonRs(contextPath+"/TeeFixedAssetsInfoController/depreciateBatch.action");
		$.MsgBox.Alert_auto("折旧完成！");
	});
}

function goBack(){
	window.location = "assetsType.jsp";
}
</script>

</head>
<body style="margin-top:20px;font-size:12px">
        <div style="text-indent: 10px;">
		<span style="color: #fd7900">注：自动折旧日期为每月的1日，如要修改折旧定时任务，请修改配置文件。</span>
		</div>
		<br />
		<br />
		<div  style="text-indent: 150px;">
		<img src="<%=contextPath %>/common/zt_webframe/imgs/xzbg/gdzc/zhejiu.gif" />
		<br />
		<br />
		</div>
		<div style="text-indent: 180px;">
		<a href="javascript:void(0)" onclick="commit()">手动折旧</a>
		</div>
</body>
</html>