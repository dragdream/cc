<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
    
    <%
      int flowId=TeeStringUtil.getInteger(request.getParameter("flowId"), 0);
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style type="text/css">
html{
  padding: 5px;
  width: 100%;
  height: 100%;
  box-sizing: border-box;
}

</style>
<script type="text/javascript" src="<%=request.getContextPath() %>/common/jqueryui/jquery.layout-latest.js"></script>

<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
</head>
<body style="overflow:hidden;">
<div class="titlebar clearfix">
		<img class = 'tit_img' style="margin-right: 10px;" src="<%=contextPath%>/common/zt_webframe/imgs/gzl/gzwt/icon_zgwt.png">
		<p class="title">工作委托</p>
		<ul id = 'tab' class = 'tab clearfix'>
			
		</ul>
		<span class="basic_border_grey fl"></span>
</div>
	
	<div id="tab-content" style="padding-left: 10px;padding-right: 10px;"></div>
	
</body>
<script>
 $.addTab("tab","tab-content",[{title:"设定规则",url:contextPath+"/system/core/workflow/workmanage/flowRule/addRule.jsp?flowId=<%=flowId %>"},
                              {title:"规则查询",url:contextPath+"/system/core/workflow/workmanage/flowRule/manageRule.jsp?flowId=<%=flowId %>"},
                              {title:"已委托记录",url:contextPath+"/system/core/workflow/workmanage/flowRule/entrustRecord.jsp?flowId=<%=flowId %>"},
                              {title:"被委托记录",url:contextPath+"/system/core/workflow/workmanage/flowRule/entrustedRecord.jsp?flowId=<%=flowId %>"}
                              ]); 

</script>

</html>