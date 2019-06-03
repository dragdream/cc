<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String id = request.getParameter("uuid")==null?"0":request.getParameter("uuid");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<%@ include file="/header/upload.jsp" %>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/supervise/system/sysDepartment/js/sysDepartment_add.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/md5.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
<script type="text/javascript">
var id = '<%=id%>';
</script>

</head>
<body  onload="doInit();" style="padding: 10px;background-color: #f2f2f2;">
        <div title="新增">
            <form role="form" id= "form1" name = "form1"  enctype="multipart/form-data" method="post">
                <input type="hidden" id="uuid" name = "uuid" class="easyui-textbox" value=""/>
                <input type="hidden" id="businessDeptName" name = "businessDeptName" class="easyui-textbox" value=""/>
                <input type="hidden" id="businessSubjectName" name = "businessSubjectName" class="easyui-textbox" value=""/>
                <table class="TableBlock" style="width: 100%; background: #fff;">
                    <tr>
                        <td class="power-table-label">部门名称：<span style="color:red;font-weight:bold;">*</span></td>
                        <td style="width: 300px;">
                            <input name="deptName" id="deptName" class="easyui-textbox" style="width: 95%;" readonly/>
                        </td>
                        <td class="power-table-label">部门类型：<span style="color:red;font-weight:bold;">*</span></td>
                        <td style="width: 300px;">
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="orgType"   style="width:14px;height:14px;" onclick="show(this)" value="10" >执法
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="orgType"   style="width:14px;height:14px;" onclick="show(this)" value="20" >监督
		    			</td>
                    </tr>
                    <tr id="div1" name="div1" style="display: none;">
                    	<td class="power-table-label">监督部门:</td>
                    	<td style="width: 300px;">
                    	 <input name="businessSupDeptId" id="businessSupDeptId" class="easyui-textbox" style="width: 95%;" />
                    	</td>
                    </tr>
                    <tr id="div2" name="div2" style="display: none;">
                    		<td class="power-table-label">执法部门:</td>
                    		<td style="width: 300px;">
                    		    <input name="businessDeptId" id="businessDeptId" class="easyui-textbox" style="width: 95%;"/>
                    		</td>
                    		<td class="power-table-label">执法主体:</td>
                    		<td style="width: 300px;">
                    		<input name="businessSubjectId" id="businessSubjectId" class="easyui-textbox" style="width: 95%;"/>
                   			</td>
                    </tr>
                    </table>
                    <div id="div2" name="div2" style="display: none;width: 440px;">
                    	<div >
                    	</div>
                    	<div >
                    	</div>
                    </div>
            </form>
        </div>
</body>
</html>