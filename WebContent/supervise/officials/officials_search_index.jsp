<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String id = request.getParameter("id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/ztree.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/officials/js/officials_search_index.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />

<script type="text/javascript">
</script>
</head>
<body onload="doInit()" style="padding-right: 10px;padding-left: 10px;padding-top: 5px">
	<table id="datagrid" fit="true"></table>
	<div id="toolbar" class="titlebar clearfix">
            <div id="outwarp">
		<div class="fl left">
			<img id="img1" class='title_img'
                src="<%=contextPath%>/common/zt_webframe/imgs/xzbg/clgl/clsqcx.png" />
                <span class="title">执法人员查询</span>
        </div>
    </div>
		<span class="basic_border"></span>
		<div class="" style="padding-top: 5px; padding-bottom: 5px">
            <form id="form1">
           	 姓名： <input class="easyui-textbox" data-options="validType:'length[0,50]'" name="name" id="name"  />
           	 &nbsp;&nbsp;执法证号： <input class="easyui-textbox" data-options="validType:'length[0,50]'" name="code" id="code"  />
           	 &nbsp;&nbsp;人员性质： <input class="easyui-textbox" name="personType" data-options="editable:false" id="personType"  />
<!--            	 &nbsp;&nbsp;审核状态：<select name='examine' id='examine' class="easyui-combobox" style="width:150px" panelMaxHeight="150px" data-options="editable:false,panelHeight:'auto'"  > -->
<!-- 								<option value="">全部</option> -->
<!-- 								<option value="0">未审核</option> -->
<!-- 								<option value="1">已审核</option> -->
<!-- 							</select> -->
            &nbsp;&nbsp;<a class="easyui-linkbutton" onclick="queryS()"><i class="fa fa-search"></i>&nbsp查&nbsp询</a>
            </form>
        </div>
	</div>
	
</body>
</html>