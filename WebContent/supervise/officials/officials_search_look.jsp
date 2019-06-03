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

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="/supervise/caseManager/commonCaseSearch/css/case.css" />
<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/pureText-lookInfo.css" />
<script type="text/javascript">
var id = '<%=id%>';
</script>
</head>
<body  onload="doInit();" style="padding: 10px;background-color: #fff;">
	<form role="form" id= "form1" name = "form1"  enctype="multipart/form-data" method="post" class="easyui-form" data-options="novalidate:true">
		<div class="easyui-panel" title="基础信息" style="width: 100%;" align="center">
            <input type="hidden" id="id" name = "id" value="${param.id }"/>
            <table class="TableBlock_page lookInfo-lowHeight-table" frame="void" style="width: 100%; background: #fff; " rules="none">
                <tr style="border:none!important;">
                	<td class="power-table-label font-bold-label-td" style="text-align:right;">所属主体：</td>
                    <td style="width: 180px;" name="businessSubName" id="businessSubName">
                    </td>
                	<td class="power-table-label font-bold-label-td" style="text-align:right;">委托组织：</td>
                    <td style="width: 180px;" id="entrustId" name="entrustId">
                    </td>
                </tr>
                <tr style="border:none!important;">
                    <td class="power-table-label font-bold-label-td" style="width: 180px;text-align:right;">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</td>
                    <td class="power-table-label" style="width: 300px;" name="name" id="name" >
                 	</td>
                    <td class="power-table-label font-bold-label-td" style="width: 180px;text-align:right;">身份证号：</td>
                    <td style=" width: 180px;" name="personId" id="personId">
                    </td>
                </tr>
                <tr style="border:none!important;">
                    <td class="power-table-label font-bold-label-td" style="width: 180px;text-align:right;">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</td>
                    <td style="width: 180px;" name="sex" id="sex">
                    </td>
                    <td class="power-table-label font-bold-label-td" style="width: 180px;text-align:right;">出生日期：</td>
                    <td style=" width: 180px;" id='birthStr'  name='birthStr'>
                    </td>
                </tr>
                <tr style="border:none!important;">
                	<td class="power-table-label font-bold-label-td" style="text-align:right;">民&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;族：</td>
                    <td style=" width: 180px;" name="nation" id="nation">
  					</td>
                    <td class="power-table-label font-bold-label-td" style="text-align:right;">政治面貌：</td>
                    <td style="width: 180px;" name="politive" id="politive">
                    </td>
                </tr>
                <tr style="border:none!important; ">
                    <td class="power-table-label font-bold-label-td" style="text-align:right;">最高学历：</td>
                    <td style="width: 180px;" name="education" id="education">
                    </td>
                    <td class="power-table-label font-bold-label-td" style="text-align:right;">职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;级：</td>
                    <td style=" width: 180px;" name="jobClass" id="jobClass">
  					</td>
                </tr>
                <tr style="border:none!important; ">
                    <td class="power-table-label font-bold-label-td" style="text-align:right;">人员性质：</td>
                    <td style="width: 180px;" name="personType" id="personType">
                    </td>
                    <td class="power-table-label font-bold-label-td" style="text-align:right;">法律职业资格：</td>
                    <td style=" width: 180px;" name="isLawcode" id="isLawcode">
  					</td>
                </tr>
                <tr style="border:none!important;">
                    <td class="power-table-label font-bold-label-td" style="text-align:right;">联系电话：</td>
                    <td style="width: 180px;" name="telephone" id="telephone">
                    </td>
                </tr>
             </table>
        </div>
        <br />
        <div id="hangye" class="easyui-panel" title="执法证" style="width: 98.5%;height:150px" align="center" >
            <table id="datagrid" fit="true"  >
			</table>
        </div>
	</form>
<script type="text/javascript" src="<%=contextPath%>/supervise/officials/js/officials_search_look.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/md5.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>
</body>
</html>