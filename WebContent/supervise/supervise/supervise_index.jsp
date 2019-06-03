<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>

<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />

<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
</head>

<body onload="doInit()" style="padding-right: 10px;padding-left: 10px;padding-top: 5px">
    <div id="toolbar" class="titlebar clearfix">
        <div id="outwarp">
        <div class="fl left">
            <img id="img1" class='title_img'
                src="<%=contextPath%>/common/zt_webframe/imgs/xzbg/clgl/clsqcx.png" />
                <span class="title">监督机关维护 </span>
        </div>
        <div class="fr">
            <button class="easyui-linkbutton" onclick="add()"><i class="fa fa-plus"></i>&nbsp;新&nbsp;增</button>
            &nbsp;&nbsp;
        	<button class="easyui-linkbutton" onclick="auditing()"><i class="fa fa-check"></i>&nbsp;审&nbsp;核</button>
			&nbsp;&nbsp;
			<button class="easyui-linkbutton" onclick="deletes()"><i class="fa fa-trash-o"></i>&nbsp;删&nbsp;除</button>
			&nbsp;&nbsp;
			<button class="easyui-linkbutton" onclick="user()"><i class="fa fa-user"></i>&nbsp;分配账号</button>
			&nbsp;&nbsp;
			<button class="easyui-linkbutton" onclick="password()"><i class="fa fa-refresh"></i>&nbsp;重置密码</button>
			&nbsp;&nbsp;
			<button class="easyui-linkbutton" onclick="reUser()"><i class="fa fa-recycle"></i>&nbsp;回收账号</button>
        </div>
        </div>
        <span class="basic_border"></span> 
        <div class="" style="padding-top: 5px; padding-bottom: 5px">
            <form id="form1">
                              机关名称：<input class="easyui-textbox" name="name" prompt="机关名称或简称" data-options="validType:'length[0,300]'" id="name"  />
            &nbsp;&nbsp;机关性质：<input type="text" name="nature" id="nature"  data-options="editable:false" class="easyui-textbox"  />
            &nbsp;&nbsp;统一社会信用代码：<input type="text" name="departmentCode" id="departmentCode" class="easyui-textbox"  />
            &nbsp;&nbsp;<a class="easyui-linkbutton"  onclick="doSearch()"><i class="fa fa-search"></i>&nbsp;查&nbsp;询</a>
            </form>
        </div>
    </div>
    <table id="datagrid" fit="true"></table>
<script type="text/javascript" src="<%=contextPath%>/supervise/supervise/js/supervise_index.js"></script>
</body>
</html>