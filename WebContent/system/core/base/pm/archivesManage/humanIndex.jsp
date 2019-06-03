<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<%
String uuid = request.getParameter("uuid");
String name = request.getParameter("name");
String pa = request.getParameter("pa");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>档案管理</title>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style type="text/css">
html{
  padding: 5px;
  width: 100%;
  height: 100%;
  box-sizing: border-box;
}
</style>
<!-- jQuery 布局器 -->
<script type="text/javascript" src="<%=request.getContextPath() %>/common/jqueryui/jquery.layout-latest.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/js/hr.js"></script>
<script type="text/javascript">

function doInit(){
	
}


function advancedSearch(){
	var url = "search.jsp?uuid=<%=uuid%>&name=<%=name%>";
	window.location.href = url;
}
//导出
function except(){
	$("#ifm")[0].contentWindow().except();
}
</script>
</head>
<body onload="doInit();"  style="overflow:hidden;">

 <div class="titlebar clearfix" >
		<img class = 'tit_img' style="margin-right: 10px;" src="<%=contextPath%>/system/core/base/pm/img/icon_dagl.png">
		<p class="title">档案管理</p>
		<ul id = 'tab' class = 'tab clearfix' style='display:inline-block;'>
		</ul>
		
   <div id="as" class="right fr clearfix" style="padding-top: -1px;">
      <button type="button" onclick="advancedSearch();" class="advancedSearch btn-win-white">高级查询</button>
<!--       <button type="button" onclick="except();" class="advancedSearch btn-win-white">导出</button>
 -->   </div>
		<span class="basic_border_grey fl"></span>
  </div>
	
	<div id="tab-content" style="padding-left: 10px;padding-right: 10px;"></div>
<iframe id="ifm"></iframe>
</body>
<script type="text/javascript">
var uuid = "<%=uuid%>";
var name = "<%=name%>";
var page = "<%=pa%>";
 $.addTab("tab","tab-content",[{title:"人员列表",url:contextPath+"/system/core/base/pm/archivesManage/viewByDept.jsp?deptName="+encodeURI(name)+"&deptId="+uuid},
                               {title:"新建人员",url:contextPath+"/system/core/base/pm/archivesManage/add_person.jsp?deptName="+encodeURI(name)+"&deptId="+uuid}]); 
 
 if(page == 2){
	 $($(".tab").find("li")[1]).click();
	 $("#as").show();
 }
	
	//控制高级查询在人员列表tab也签下显示
	$("body").on("click","#tab li",function(){
		if($(this).text() == "人员列表"){
			$("#as").show();
		}else{
			$("#as").hide();
		}
	});
</script>
</html>
