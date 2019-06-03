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
<script type="text/javascript" src="<%=contextPath%>/supervise/inspection/inspItem/js/inspection_item_input.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/inspection/inspModule/js/inspection.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/power/css/power.css" />
<link rel="stylesheet" type="text/css" href="/supervise/inspection/inspRecord/css/inspection.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body onload="doInit()">
    <div id="inspectionItem_input_panel" class="easyui-panel tabs-header-border" style="width: 100%; overflow: auto;">
        <input type="hidden" id="itemId" value="<c:out value='${param.id}'/>" />
        <form role="form" id="inspection_item_input_form" name="form1" enctype="multipart/form-data" method="post" data-options="novalidate:true">
            <table class="TableBlock" style="width: 100%; background: #fff; margin: 5px 0px;">
                <tr class="none-border-tr">
                    <td class="power-table-label" style="width: 120px;">所属领域<span class="required">*</span>：</td>
                    <td colspan="2" style="width: 200px;">
                    <input class="easyui-combobox" style="width: 90%;" id="inspItem_orgSys" name="orgSys" 
                    data-options="novalidate:true, required:true, missingMessage:'执法系统' "/>
                    </td>
                </tr>
                <tr class="none-border-tr">
                    <td class="power-table-label" style="width: 120px;">检查模块<span class="required">*</span>：</td>
                    <td colspan="2" style="width: 200px;">
                    <select class="easyui-combobox" style="width: 90%;"
                        id="inspItem_module" name="moduleId"
                        data-options="novalidate:true, required:true, missingMessage:'请选择输入检查模块'">
                    </select></td>
                </tr>
                <tr class="none-border-tr">
                    <td class="power-table-label" style="width: 120px;">检查项名称<span class="required">*</span>：</td>
                    <td colspan="2" style="width: 200px;"><input class="easyui-textbox" id="itemName" name="itemName" 
                    	data-options="validType:'length[0,1000]', novalidate:true,required:true, missingMessage:'请输入检查项内容'"
                        style="width: 90%;" /></td>
                </tr>
                <tr>
            </table>
        </form>
        <script>
        $(function(){
        	$('#itemName').textbox('textbox').attr('maxlength', 2000);
        });
        </script>
    </div>
</body>
</html>