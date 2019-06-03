<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp" %>
<title>部门导入导出</title>
<link href="<%=cssPath %>/style.css" rel="stylesheet" type="text/css" />
<link  rel="stylesheet" type="text/css" href="<%=cssPath %>/stylebootstrap.css" />
<script type="text/javascript" src="<%=contextPath %>/common/jquery/jquery-min.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/tools.js"></script>

<script type="text/javascript">

function importDept(){
	parent.deptinput.location='import.jsp';
}
/**
 * 导出
 */
function exportDept(){
	var url = "<%=contextPath%>/orgImportExport/exportDept.action";
	window.location.href = url;
	
}
</script>
</head>
<body topmargin="5">
<table border="0" style="padding-left:10px" width="100%" cellspacing="0" cellpadding="3" class="">
  <tr>
    <td class="Big"><span class="Big3">&nbsp;部门/成员单位管理</span>
    </td>
    <td align="right">
      <input type="button" value="新建部门/成员单位" class="btn btn-primary" onClick="parent.deptinput.location='addupdate.jsp';" title="新建部门/成员单位">&nbsp;&nbsp;
      <input type="button" value="导入" class="btn btn-primary" onClick="importDept();" title="导入部门/成员单位">&nbsp;&nbsp;
      <input type="button" value="导出" class="btn btn-primary" onClick="exportDept();" title="导出部门/成员单位">&nbsp;&nbsp;&nbsp;&nbsp;
    </td>
    </tr>
</table>
</body>
</html>