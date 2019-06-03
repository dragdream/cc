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
                <span class="title">评查人员管理</span>
		</div>
		<div class="fr">
			<button class="easyui-linkbutton" onclick="addOther()"><i class="fa fa-plus"></i>&nbsp;确定其他评查人员</button>
			&nbsp;&nbsp;<button class="easyui-linkbutton" onclick="add()"><i class="fa fa-plus"></i>&nbsp;确定评查人员</button>
			&nbsp;&nbsp;<button class="easyui-linkbutton" onclick="exportCasePerson()"><i class="fa fa-download"></i>&nbsp;导出</button>
		</div>
	</div>
		<span class="basic_border"></span> 
		<div class="" style="padding-top: 5px; padding-bottom: 5px">
            <form id="form1">
           	姓名： <input class="easyui-textbox" style="width:80px" name="name"  id="name"  />
           	&nbsp;&nbsp;所属部门： <input class="easyui-textbox" style="width:120px"  name="organizationName" id="organizationName"  />
           	&nbsp;&nbsp;参评次数： <input class="easyui-textbox" style="width:50px" name="checkcountBegin" id="checkcountBegin"  />
           	&nbsp;至 &nbsp;<input class="easyui-textbox" style="width:50px" name="checkcountEnd" id="checkcountEnd"  />&nbsp;
            &nbsp;&nbsp;是否被撤销：<select name='isRevock' id='isRevock' class="easyui-combobox" style="width: 100px" panelMaxHeight="auto" data-options="editable:false,panelHeight:'auto'">
		                            <option value="">全部</option>
		                            <option value="1">是</option>
		                            <option value="0">否</option>
            					</select>
            &nbsp;&nbsp;撤销原因： <input class="easyui-textbox" style="width:100px" name="revockreasonId" data-options="editable:false" id="revockreasonId"  />
            &nbsp;&nbsp;<a class="easyui-linkbutton" onclick="queryS()"><i class="fa fa-search"></i>&nbsp;查&nbsp;询</a>
            </form>
        </div>
	</div>
	<script>
           $(function () {
           $('#organizationName').textbox('textbox').attr('maxlength', 300);
           $('#name').textbox('textbox').attr('maxlength', 60);
           $('#checkcountBegin').textbox('textbox').attr('maxlength', 3);
           $('#checkcountEnd').textbox('textbox').attr('maxlength', 3);
           });
         </script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/caseCheck/person/js/person_index.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
</body>
</html>