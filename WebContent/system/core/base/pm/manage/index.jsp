<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/tabs/tabs.js"></script>
<script>

function doInit(){
	
	//$("#layout").layout({auto:true});
	$.addTab("tabs","tabs-content",[{title:"人事档案(在职)",url:contextPath+"/system/core/base/pm/manage/doc_manage.jsp?searchType=1",active:true},
	                                {title:"人事档案(离职)",url:contextPath+"/system/core/base/pm/manage/doc_manage.jsp?searchType=2",active:false},
	                                {title:"档案查询",url:contextPath+"/system/core/base/pm/manage/search.jsp",active:false},
	                                {title:"到期合同",url:contextPath+"/system/core/base/pm/public/contract.jsp",active:false}]);
}

</script>

</head>
<body onload="doInit()" style="padding:5px 0px 0px 1px;">
<div id="tabs" class="base_layout_top"></div>
<div id="tabs-content" class="base_layout_center"></div>

</body>
</html>