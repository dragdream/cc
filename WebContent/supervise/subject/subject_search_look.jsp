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
<link rel="stylesheet" type="text/css" href="/supervise/common/css/pureText-lookInfo.css" />
<script type="text/javascript">
var id = '<%=id%>';
</script>
</head>
<body  onload="doInit();" style="padding: 10px;background-color: #fff;">
            <form role="form" id= "form1" name = "form1"  enctype="multipart/form-data" method="post" class="easyui-form" data-options="novalidate:true">
                <input type="hidden" id="id" name = "id" value="${param.id }"/>
                <div class="easyui-panel" data-options="fit:true,border:false">
                <table class="TableBlock_page lookInfo-lowHeight-table" style="width: 100%; background: #fff;">
                    <tr style="border:none!important;">
                        <td class="power-table-label font-bold-label-td" style="text-align:right;">主体名称：</td>
                        <td colspan="3" class="power-table-label" style="text-align:left;" name="subName" id="subName">
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                    	<td class="power-table-label font-bold-label-td" style="text-align:right;">所属机关：</td>
                        <td class="power-table-label" style="text-align:left;width: 260px;" name="departmentCode" id="departmentCode">
                        </td>
                        <td class="power-table-label font-bold-label-td" style="text-align:right;">所属领域：</td>
                        <td class="power-table-label" style="text-align:left;width: 260px;" name="orgSys" id="orgSys">
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                        <td class="power-table-label font-bold-label-td" style="text-align:right;">所属地区：</td>
                        <td class="power-table-label" style="text-align:left;width: 260px;" name="area" id="area">
                        </td>
                        <td class="power-table-label font-bold-label-td" style="text-align:right;">主体层级：</td>
                        <td class="power-table-label" style="text-align:left;width: 260px;" name="subLevel" id="subLevel">
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                        <td class="power-table-label font-bold-label-td" style="text-align:right;">主体类别：</td>
                        <td class="power-table-label" style="text-align:left;width: 260px;" name="nature" id="nature">
                        </td>
                        <td class="power-table-label font-bold-label-td" style="text-align:right;">编制人数：</td>
                        <td class="power-table-label" style="text-align:left;width: 260px;" name="innerSupOrgPostNum" id="innerSupOrgPostNum">
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                        <td class="power-table-label font-bold-label-td" style="text-align:right;">设定依据：</td>
                        <td class="power-table-label" colspan="3" style="text-align:left;width: 260px;" name="basis" id="basis">
                        </td>
                    </tr>
                    </table>
                    </div>
            </form>

<script type="text/javascript" src="<%=contextPath%>/supervise/subject/js/subject_search_look.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/md5.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>
</body>
</html>