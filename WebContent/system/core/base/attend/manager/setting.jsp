<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<title>借阅记录</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />


<script>

function doInit(){
	$.addTab("tab","tab-content",[{url:contextPath+"/system/core/base/attend/settings/duty/index.jsp",title:"排班类型"},
	                              {url:contextPath+"/system/core/base/attend/manager/configRules.jsp",title:"固定排班"},
	                              {url:contextPath+"/system/core/base/attend/manager/tables.jsp",title:"自由排班"},
	                                {url:contextPath+"/system/core/base/attend/settings/times/index.jsp",title:"时间段"},
	                                {url:contextPath+"/system/core/base/attend/settings/noDuty/person.jsp",title:"免签人员"},
	                                {url:contextPath+"/system/core/base/attend/settings/noDuty/holiday.jsp",title:"免签节日"},
	                                {url:contextPath+"/system/core/base/attend/settings/data/index.jsp?type=1",title:"数据管理"},
	                                {url:contextPath+"/system/core/base/attend/leaderRule/index.jsp",title:"审批规则"},
	                                {url:contextPath+"/system/core/base/attend/manager/refreshDutyRecord.jsp",title:"数据刷新"}
	                                ]);
}

</script>

</head>
<body onload="doInit()"  style="overflow: hidden;padding-left:10px;padding-right:10px;padding-top: 5px;">
<!--<div id="tabs" class="base_layout_top"></div>
<div id="tabs-content" class="base_layout_center"></div>-->

<div class="titlebar clearfix" >
		    <img id="img1" class = 'tit_img' style="margin-right: 10px;" src="<%=contextPath %>/common/zt_webframe/imgs/kqgl/icon_考勤设置-.png">
		    <span class="title">考勤设置</span>
		<ul id = 'tab' class = 'tab clearfix'>
			
		</ul>
		<span class="basic_border_grey fl"></span>
	</div>
	
	<div id="tab-content" style="padding-left: 10px;padding-right:10px"></div>
	

</body>
</html>

