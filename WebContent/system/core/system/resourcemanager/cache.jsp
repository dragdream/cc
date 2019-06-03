<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp" %>
<script>
function Refresh(flag){
	var json = tools.requestJsonRs(contextPath+"/systemAction/refreshCache.action?cacheFlag="+flag);
	alert("更新成功");
}
</script>
</head>
<body style="margin:10px">
<fieldset>
	<legend>缓存管理</legend>
</fieldset>
<input type="button" name="" value="刷新Redis组织机构数据" class="btn btn-default" onclick="Refresh(2)"/>
</body>
</html>

