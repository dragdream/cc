<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String id = request.getParameter("id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/ztree.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/subject/js/subject_index.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />

<script type="text/javascript">
</script>
<script type="text/javascript">
	$(document).ready(function(e) {
	    $(".but").click(function(e) {
	        $(".abc").toggle();
	    });
	});
</script>
</head>
<body onload="doInit()" style="padding-right: 10px;padding-left: 10px;padding-top: 5px">
	<table id="datagrid" fit="true"></table>
	<div id="toolbar" class="titlebar clearfix">
			<div id="outwarp">
		<div class="fl left">
			<img id="img1" class='title_img'
				src="<%=contextPath%>/common/zt_webframe/imgs/gzl/gzcx/icon_gongzuochaxun.png"/>
			<span class="title">执法主体维护</span>
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
		主体名称：<input type="text" id='subName' name='subName' data-options="validType:'length[0,300]'" class="easyui-textbox"  />
		&nbsp;&nbsp;所属领域：<input name="orgSys" id="orgSys" style="width: 190px" prompt="输入关键字后自动搜索" data-options="panelHeight:'auto'" class="easyui-combobox" /> 
		&nbsp;&nbsp;主体类别：<select name="nature" id="nature" style="width: 145px" data-options="editable:false,panelHeight:'auto'" class="easyui-combobox" > 
				<option value="">全部</option>
				</select>
		&nbsp;&nbsp;审核状态：<select name='examine' id='examine' class="easyui-combobox" style="width: 145px" panelMaxHeight="150px" data-options="editable:false,panelHeight:'auto'" >
					<option value="">全部</option>
					<option value="0">未审核</option>
					<option value="1">已审核</option>
				</select>  
        &nbsp;&nbsp;<button  class="easyui-linkbutton" onclick="queryS()" ><i class="fa fa-search"></i>&nbsp;查&nbsp;询</button>
		</div>
	</div>
	
</body>
</html>