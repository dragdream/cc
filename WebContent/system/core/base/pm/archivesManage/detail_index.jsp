<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String humanDocSid=request.getParameter("sid");
	String personName = request.getParameter("personName");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<style type="text/css">
html{
  padding: 5px;
  width: 100%;
  height: 100%;
  box-sizing: border-box;
}
</style>
<script>

function doInit(){
	//window.title0.innerHTML = personName+"的档案信息";
}

</script>
<script type="text/javascript" src="<%=request.getContextPath() %>/common/jqueryui/jquery.layout-latest.js"></script>

<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>

</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px;">
   <div class="titlebar clearfix" >
		<img class = 'tit_img' style="margin-right: 10px;" src="<%=contextPath%>/system/core/base/pm/img/icon_daxxwh.png">
		<p class="title"><%=personName %>的档案信息</p>
		<ul id = 'tab' class = 'tab clearfix'>
			
		</ul>
		<span class="basic_border_grey fl"  style="width: 98%"></span>
  </div>
	
	<div id="tab-content" style="padding-left: 10px;padding-right: 10px;"></div>
</body>
<script type="text/javascript">
var humanDocSid = '<%=humanDocSid%>';
var personName = "<%=personName%>";
 $.addTab("tab","tab-content",[{title:"信息",url:contextPath+"/system/core/base/pm/archivesManage/detail_person.jsp?humanDocSid="+humanDocSid+"&personName="+personName},
                              {title:"合同",url:contextPath+"/system/core/base/pm/archivesManage/ht.jsp?humanDocSid="+humanDocSid+"&personName="+personName},
                              {title:"奖惩",url:contextPath+"/system/core/base/pm/archivesManage/jc.jsp?humanDocSid="+humanDocSid+"&personName="+personName},
                              {title:"证书",url:contextPath+"/system/core/base/pm/archivesManage/zs.jsp?humanDocSid="+humanDocSid+"&personName="+personName},
                              {title:"培训",url:contextPath+"/system/core/base/pm/archivesManage/px.jsp?humanDocSid="+humanDocSid+"&personName="+personName},
                              {title:"学习",url:contextPath+"/system/core/base/pm/archivesManage/xxjl.jsp?humanDocSid="+humanDocSid+"&personName="+personName},
                              {title:"工作",url:contextPath+"/system/core/base/pm/archivesManage/gzjl.jsp?humanDocSid="+humanDocSid+"&personName="+personName},
                              {title:"技能",url:contextPath+"/system/core/base/pm/archivesManage/gzjn.jsp?humanDocSid="+humanDocSid+"&personName="+personName},
                              {title:"关系",url:contextPath+"/system/core/base/pm/archivesManage/shgx.jsp?humanDocSid="+humanDocSid+"&personName="+personName},
                              {title:"调动",url:contextPath+"/system/core/base/pm/archivesManage/rsdd.jsp?humanDocSid="+humanDocSid+"&personName="+personName},
                              {title:"离职",url:contextPath+"/system/core/base/pm/archivesManage/lz.jsp?humanDocSid="+humanDocSid+"&personName="+personName},
                              {title:"复职",url:contextPath+"/system/core/base/pm/archivesManage/fz.jsp?humanDocSid="+humanDocSid+"&personName="+personName}
                              ]); 

</script>
</html>