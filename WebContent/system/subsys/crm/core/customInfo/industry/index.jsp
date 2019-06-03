<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String type=request.getParameter("type")==null?"":request.getParameter("type");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crmCode.js"></script>
<script>
function doInit(){
	$("#layout").layout({auto:true});
	var industryList = getCrmCodeByParentCodeNo("INDUSTRY_TYPE","");// 计量单位
	var html="";
	for(var i = 0; i <industryList.length ; i++){
		var prcTemp = industryList[i];
	 	html = html +  "<a href=\"#\" class=\"list-group-item\" onClick=getCustomerList(\""+prcTemp.codeNo+"\")>"
	  				+  "<i class=\"glyphicon glyphicon-play-circle\" style=\"float:left;\"></i>"
					+  "<i class=\"glyphicon glyphicon-chevron-right pull-right\"></i>&nbsp;"+prcTemp.codeName+"<i style=\"clear:both;\"></i></a>"; 
	}
	$("#industryList").html(html);
	$("#industryList").group();
}

function getCustomerList(industryType){
	$(this).find("a").addClass("active");
	$("#frame0").attr("src",contextPath+"/system/subsys/crm/core/customInfo/industry/customerList.jsp?industryType="+industryType+"&type=<%=type%>");
}
</script>
</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px;margin:5px 0px 0px 5px;">
<div id="layout">
	<div layout="west" width="200" style='overflow-y:scroll;'>
		<div id="industryList" class="list-group">
		</div>
	</div>
	<div layout="center" style="padding-left:10px;padding-bottom:10px;">
		<iframe id="frame0" frameborder="0" style="width:100%;height:100%" src="<%=contextPath %>/system/subsys/crm/core/customInfo/industry/customerList.jsp?type=<%=type%>"></iframe>
	</div>
</div>
</body>
</html>