<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String id = request.getParameter("id")==null?"0":request.getParameter("id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<%@ include file="/header/upload.jsp" %>
<link rel="stylesheet" type="text/css" href="/supervise/caseManager/commonCaseSearch/css/case.css" />
<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript">
var id = '<%=id%>';
</script>
<style type="text/css">
.datebox-calendar-inner {
    height: 180px;
}
</style>
</head>
<body  onload="doInit();" style="padding: 10px;background-color: #fff;">
<!-- 	<form role="form" id= "form1" name = "form1"  enctype="multipart/form-data" method="post" class="easyui-form" data-options="novalidate:true"> -->
       <div class="easyui-layout" data-options="fit:true,border:false">
			<div data-options="region:'west',split:true,border:false" style="width:48%;">
				<div class="easyui-tabs" style="width:100%;height:100%;padding:5px;" >
					<div title="执法人员">
	           			<div style="height:8%;padding:5px;" >
	           				所属部门： <input class="easyui-textbox" style="width:120px"  name="perDeptName" id="perDeptName" />
							&nbsp;&nbsp;姓名： <input class="easyui-textbox" style="width:80px" name="perName"  id="perName"  />
						    &nbsp;&nbsp;<a class="easyui-linkbutton" onclick="queryPer()"><i class="fa fa-search"></i>&nbsp;查&nbsp;询</a>
	           			</div>
	           			<div style="height:92%;padding:5px;">
	           				<table id="checkDatagrid" fit="true" ></table>
	           			</div>
	           		</div>
	           		<div title="监督人员">
	           			<div style="height:8%;padding:5px;" >
	           				所属部门： <input class="easyui-textbox" style="width:120px"  name="supDeptName" id="supDeptName" />
							&nbsp;&nbsp;姓名： <input class="easyui-textbox" style="width:80px" name="supName"  id="supName"  />
						    &nbsp;&nbsp;<a class="easyui-linkbutton" onclick="querySup()"><i class="fa fa-search"></i>&nbsp;查&nbsp;询</a>
	           			</div>
	           			<div style="height:92%;padding:5px;">
	           				<table id="checkSupDatagrid" fit="true" ></table>
	           			</div>
	           		</div>
           		</div>
			</div>
			<div data-options="region:'east',split:true,border:false" style="width:48%;">
				<div class="easyui-tabs" style="width:100%;height:100%;padding:5px;" >
					<div title="已选人员" >
						<table id="putDatagrid" fit="true"></table>
					</div>
				</div>
			</div>
            <div data-options="region:'center',border:false" >
                <div style="text-align: center; margin-top: 180px;">
                    <a class="easyui-linkbutton" id="selectedCasecheck_add_btn" style="width: 80%; height: 20px;'" onclick="selectedCasecheck();"><i class="fa fa-arrow-right"></i> </a>
                </div>
                <div style="text-align: center; margin-top: 15px;">
                    <a class="easyui-linkbutton" id="removeCasecheck_add_btn" style="width: 80%; height: 20px;" onclick="removeCasecheck();"><i class="fa fa-arrow-left"></i> </a>
                </div>
            </div>
        </div>
	<script>
           $(function () {
           $('#perDeptName').textbox('textbox').attr('maxlength', 300);
           $('#perName').textbox('textbox').attr('maxlength', 50);
           $('#supDeptName').textbox('textbox').attr('maxlength', 300);
           $('#supName').textbox('textbox').attr('maxlength', 50);
           });
    </script><script type="text/javascript" src="<%=contextPath%>/supervise/caseCheck/person/js/person_add.js"></script>
<%-- <script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script> --%>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/md5.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>

</body>
</html>