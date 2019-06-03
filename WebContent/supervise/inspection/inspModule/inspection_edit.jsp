<%@page import="com.raq.ide.common.Console"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String id = request.getParameter("id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<%@ include file="/header/upload.jsp" %>
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
<link rel="stylesheet" type="text/css" href="/supervise/inspection/inspRecord/css/inspection.css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/md5.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/inspection/inspModule/js/inspection_edit.js"></script>


</head>
<body onload="doInit();" style="padding: 10px;">
    <div class="easyui-panel tabs-header-border" style="width: 100%; overflow: auto;">
            <form  id= "form1" name = "form1" data-options="novalidate:true">
                <table class="TableBlock" style="width: 95%;background: #fff;">
                    <tr class="none-border-tr">
                        <td class="insp-td-right3" >模块名称<span class="required">*</span>：</td>
                        <td class="insp-td-left9">
                            <input name="moduleName" id="moduleName" class="easyui-textbox" 
                            data-options="validType:'length[0,500]', novalidate:true,required:true, missingMessage:'请输入模块名称' "
                             style="width: 95%;" />
                        </td>
                    </tr>
                    <tr class="none-border-tr">
                        <td class="insp-td-right3" >所属领域<span class="required">*</span>：</td>
                        <td class="insp-td-left9">
                        <input name="orgSys" id="orgSys" style="width: 95%;" class="easyui-combobox"
                        data-options="validType:'length[0,60]', novalidate:true,required:true, missingMessage:'请选择执法系统'"
                         />
                        </td>
                    </tr>
                    </table>
                <div id="control" style='margin:0 auto;height:28px;line-height:28px;width:"90%"'>
				<input id="id" name="id" type="hidden" value="${param.id}"/>
				<input id="org" name="org" type="hidden" value="${param.orgSys}"/>
				</div> 
            </form>
     </div>
     <script>
         $(function (){
             $('#moduleName').textbox('textbox').attr('maxlength',500);
         });
     </script>
</body>
</html>