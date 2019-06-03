<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>权限设置</title>
</head>
<body onload="" style="overflow: hidden;padding-left:10px;padding-right:10px;padding-top: 5px">
	
	<div class="titlebar clearfix" >
		<img class = 'tit_img' style="margin-right:10px" src="<%=contextPath %>/system/subsys/project/img/icon_setting.png">
		<p class="title">权限设置</p>
		<ul id = 'tab' class = 'tab clearfix'>
			
		</ul>
		<span class="basic_border_grey fl"></span>
	</div>
	
	<div id="tab-content" style="padding-left: 10px;padding-right:10px"></div>
	
</body>
<script>
 $.addTab("tab","tab-content",[{title:"新建权限",url:contextPath+"/system/subsys/project/setting/privsetting/createPriv.jsp"},
                              {title:"项目审批权限",url:contextPath+"/system/subsys/project/setting/privsetting/approvalRule.jsp"},
                              {title:"费用审批权限",url:contextPath+"/system/subsys/project/setting/privsetting/costApproval.jsp"},
                              ]); 

</script>
</html>