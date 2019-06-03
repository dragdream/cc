<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<script src="<%=contextPath%>/system/frame/default/js/mainForSimple.js"></script>
<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body onload="initTree()" style="padding-right: 10px;padding-left: 10px;padding-top: 5px">
    <div class="easyui-layout" data-options="fit:true,border:false">
			<div data-options="region:'west',split:true,border:false" style="width:40%;">
				<div class="easyui-panel" style="width: 100%;height:100%;">
					<div class="panel-header" style="background-color:#E6E6E6;">全部功能</div>
			    	<ul class="easyui-tree" id="testTree"></ul>
				</div>
			</div>
			<div data-options="region:'east',split:true,border:false" style="width:48%;">
				<div class="easyui-panel" style="width: 100%;height:100%;" align="center">
					<table id="menuDatagrid" fit="true"></table>
				</div>
			</div>
			<div data-options="region:'center',border:false" >
                <div style="text-align: center; margin-top: 180px;">
                    <a class="easyui-linkbutton" id="selectedCasecheck_add_btn" style="width: 80%; height: 20px;'" onclick="selectedMenu();"><i class="fa fa-arrow-right"></i> </a>
                </div>
                <div style="text-align: center; margin-top: 15px;">
                    <a class="easyui-linkbutton" id="removeCasecheck_add_btn" style="width: 80%; height: 20px;" onclick="removeMenu();"><i class="fa fa-arrow-left"></i> </a>
                </div>
            </div>
        </div>
<!-- <form role="form" id= "form1" name = "form1"  enctype="multipart/form-data" method="post" class="easyui-form"> -->
<!-- </form> -->
<script type="text/javascript">
$(function () {
	// 找到 #treeBox 树形 id， 再移除图标
    $('#testTree').find("span.tree-icon tree-folder").removeClass('tree-icon tree-folder tree-folder-open');
    $('#testTree').find("span.tree-hit").removeClass('tree-expanded');
});
</script>
</body>
</html>