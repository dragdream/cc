<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import="java.util.*,java.text.SimpleDateFormat"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>

<script type="text/javascript" src="<%=contextPath%>/supervise/inspection/inspList/js/inspectionList_input.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/power/css/power.css" />
<link rel="stylesheet" type="text/css" href="/supervise/inspection/inspRecord/css/inspection.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body onload="doInit()">
    <div id="inspectionItem_input_panel" class="easyui-panel tabs-header-border" style="width: 100%; overflow: auto;">
        <input type="hidden" id="listId" value="<c:out value='${basicInfo.id}'/>" />
        <input type="hidden" id="orgSysCtrl" value="<c:out value='${basicInfo.orgSys}'/>" />
        <input type="hidden" id="loginSubId" value="<c:out value='${basicInfo.loginSubId}'/>" />
        <input type="hidden" id="loginDeptId" value="<c:out value='${basicInfo.loginDeptId}'/>" />
        <input type="hidden" id="ctrlType" value="<c:out value='${basicInfo.ctrlType}'/>" />
        <form role="form" id="inspection_list_input_form" name="form1" enctype="multipart/form-data" method="post" data-options="novalidate:true">
            <table class="TableBlock" style="width: 100%; background: #fff; margin: 5px 0px;">
                <tr class="none-border-tr">
                    <td class="power-table-label" style="width: 100px;">模版名称<span class="required">*</span>：</td>
                    <td colspan="2" style="width: 200px;">
                        <input type="text" style="width: 90%;" id='listName' name='inspListName' class="easyui-textbox"
                        data-options="validType:'length[0,120]', novalidate:true,required:true, missingMessage:'请数入检查单模版名称'"
                        />
                    </td>
                </tr>
                <tr class="none-border-tr">
                    <td class="power-table-label" style="width: 110px;">所属领域<span class="required">*</span>：</td>
                    <td colspan="2" style="width: 200px;">
                    <select class="easyui-combobox" style="width: 90%;" id="inspList_orgSys" name="orgSys"
                        data-options="validType:'length[0,60]', novalidate:true,required:true, missingMessage:'请选择执法系统'">
                        <c:forEach items="${orgSysList}" var="orgSys">
                        <option value="${orgSys.codeNo}">${orgSys.codeName}</option>
                        </c:forEach> 
                        </select>
                        </td>
                </tr>
                <tr class="none-border-tr">
                    <td class="power-table-label" style="width: 110px;">适用层级<span class="required">*</span>：</td>
                    <td colspan="2" style="width: 200px;"><input class="easyui-combobox" style="width: 90%;"
                        id="inspList_applyHierarchy" name="applyHierarchy"
                        data-options="validType:'length[0,60]', novalidate:true, required:true, missingMessage:'请选择适用层级'"/></td>
                </tr>
                <tr class="none-border-tr">
                    <td class="power-table-label" style="width: 110px;">检查分类<span class="required">*</span>：</td>
                    <td colspan="2" style="width: 200px;"><input class="easyui-combobox" style="width: 90%;"
                        id="inspList_listClassify" name="listClassify"
                        data-options="validType:'length[0,60]', novalidate:true,required:true, missingMessage:'请选择检查单分类'"/></td>
                </tr>
                <tr class="none-border-tr">
                    <td class="power-table-label" style="width: 110px;">检查模块<span class="required">*</span>：</td>
                    <td colspan="2" style="width: 200px;"><input class="easyui-combobox" style="width: 90%;"
                        id="inspList_module" name="module" 
                        data-options="novalidate:true,required:true, missingMessage:'请选择检查模块'"/></td>
                </tr>
            </table>
        </form>
    </div>
    <script>
    $(function(){
    	$('#listName').textbox('textbox').attr('maxlength', 120);
    });
    </script>
</body>
</html>