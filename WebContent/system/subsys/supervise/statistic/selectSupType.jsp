<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="background-color: #f2f2f2">
<head>

<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src = '<%=contextPath %>/common/zt_webframe/js/jquery.tree.js'></script>
<script type="text/javascript" src = '<%=contextPath %>/common/zt_webframe/js/jquery.treegrid.js'></script>
<title>选择父分类 </title>
</head>
<script>
function doInit(){
	getList();
}

//获取分类列表数据
function getList(){
	var url=contextPath+"/supTypeController/getAllSupTypeList.action";

	$("#treeGrid").treegrid({
		url:url,
		method: 'get',
        idField: 'sid',
        treeField: 'typeName',
        pagination:false,
        border:false,
        columns:[[
                {field:'sid',checkbox:true,title:'ID',width:100},
      			{field:'typeName',title:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;分类名称',width:250},
      		]]
        
	});
	
}

</script>
<body onload="doInit()" style="background-color: #f2f2f2">
   
   <table id="treeGrid" class="easyui-treegrid" fit="true" class="TableBlock"></table>
</body>
</html>