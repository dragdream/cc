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

<script type="text/javascript">
var id = '<%=id%>';
</script>
</head>
<body  onload="doInit();" style="padding: 10px;background-color: #fff;">
        <div >
            <form role="form" id= "form1" name = "form1"  enctype="multipart/form-data" method="post" class="easyui-form" data-options="novalidate:true">
                <input type="hidden" id="id" name = "id" value=""/>
                <table class="TableBlock" style="width: 100%; background: #fff;">
                    <tr style="border:none!important;">
                        <td class="power-table-label" style="text-align:right;width: 100px;">主体名称<span style="color:red;font-weight:bold;">*</span>:&nbsp;&nbsp;</td>
                        <td colspan="3">
                            <input class="easyui-textbox" id="subName" name="subName" data-options="validType:'length[0,300]',required:true, missingMessage:'请输入主体名称'" style="width: 100%;" value=""/></td>
                    </tr>
                    <tr style="border:none!important;">
                        <td class="power-table-label" style="width: 100px;text-align:right;">所属机关<span style="color:red;font-weight:bold;">*</span>:&nbsp;&nbsp;</td>
                        <td style="width: 250px;">
                        	<input class="easyui-textbox" id="departmentCode" name="departmentCode"  style="width: 98%;" data-options="novalidate:true,required:true, missingMessage:'请选择所属部门',panelHeight:'auto'"/>
                         </td>
                        <td class="power-table-label" style="width: 80px;text-align:right;">所属领域<span style="color:red;font-weight:bold;">*</span>:&nbsp;&nbsp;</td>
                        <td  id="div2" name="div2" >
                       		<input name="orgSys" id="orgSys" prompt="请先选择所属部门" data-options="novalidate:true,required:true, missingMessage:'请选择所属领域',editable:false,panelHeight:'auto'" style="width: 250px;" class="easyui-textbox"/>
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                    	<td class="power-table-label" style="text-align:right;width: 100px;">所属地区<span style="color:red;font-weight:bold;">*</span>:&nbsp;&nbsp;</td>
                        <td style=" width: 250px;">
                        	<input name="area" id="area" style="width: 98%;" class="easyui-textbox" data-options="novalidate:true,required:true, missingMessage:'请选择所属地区',editable:false,panelHeight:'auto'"/>
                        </td>
                        <td class="power-table-label" style="text-align:right;width: 80px;">主体层级<span style="color:red;font-weight:bold;">*</span>:&nbsp;&nbsp;</td>
                        <td >
							<input name="subLevel" id="subLevel" prompt="请先选择所属地区" style="width: 250px;"  class="easyui-textbox" data-options="novalidate:true,required:true, missingMessage:'请选择部门地区',editable:false"/>
		    			</td>
                    </tr>
                    <tr style="border:none!important;">
                    	<td class="power-table-label" style="text-align:right;width: 100px;">主体类别<span style="color:red;font-weight:bold;">*</span>:&nbsp;&nbsp;</td>
                        <td style="width: 250px;">
							<input name=nature id="nature" prompt="请选择" style="width: 98%;" class="easyui-combobox" data-options="novalidate:true,required:true, missingMessage:'请选择主体类别',editable:false,panelHeight:'auto'"/>
                        </td>
                    	<td class="power-table-label" style="text-align:right;width: 80px;">编制人数<span style="color:red;font-weight:bold;">*</span>:&nbsp;&nbsp;</td>
                        <td >
                            <input class="easyui-textbox" id="innerSupOrgPostNum" name="innerSupOrgPostNum" data-options="validType:'length[1,4]',novalidate:true,required:true, missingMessage:'请输入编制人数'" style="width: 250px;" />
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                    	<td class="power-table-label" style="text-align:right;width: 100px;">设定依据<span style="color:red;font-weight:bold;">*</span>:&nbsp;&nbsp;</td>
                        <td colspan="3">
                            <input class="easyui-textbox" id="basis" name="basis" data-options="validType:'length[1,2000]',novalidate:true,required:true, missingMessage:'请输入设定依据'" style="width: 99%;" />
                        </td>
                    </tr>
                    </table>
            </form>
        </div>
        <script>
           $(function () {
           $('#subName').textbox('textbox').attr('maxlength', 300);
           $('#innerSupOrgPostNum').textbox('textbox').attr('maxlength', 4);
           $('#basis').textbox('textbox').attr('maxlength', 2000);
           });
         </script>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/md5.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/subject/js/subject_add.js"></script>
</body>
</html>