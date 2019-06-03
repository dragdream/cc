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
                <table class="TableBlock" style="width: 100%;border:none;background: #fff;">
                    <tr style="border:none!important;">
                        <td class="power-table-label" style="text-align:right;">机关全称<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td colspan="3">
                            <input class="easyui-textbox" id="name" name="name" style="width: 100%;" value="" data-options="validType:'length[1,300]',required:true, missingMessage:'请输入机关全称'"/></td>
                    </tr>
                    <tr style="border:none!important;">
                        <td class="power-table-label" style="text-align:right;width:100px;">机关简称<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td style="width: 240px;">
                            <input name="simpleName" id="simpleName" class="easyui-textbox" data-options="validType:'length[1,300]',required:true, missingMessage:'请输入机关简称'" style="width: 100%;"/>
                        </td>
                        <td class="power-table-label" style="text-align:right;">统一社会信用代码<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td style=" width: 240px;">
            	            <input name="departmentCode" id="departmentCode" style="width: 100%;" data-options="novalidate:true,required:true, missingMessage:'请输入统一社会信用代码',panelHeight:'auto'" class="easyui-textbox" />
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                    	<td class="power-table-label" style="text-align:right;width:100px;">行政区划<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td style=" width: 240px;">
            	            <input name="administrativeDivision" id="administrativeDivision" style="width: 100%;" data-options="novalidate:true,required:true, missingMessage:'请选择部门地区',editable:false,panelHeight:'auto'" class="easyui-textbox" />
                        </td>
                        <td class="power-table-label" style="text-align:right;">所属层级<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td style=" width: 240px;">
                            <input class="easyui-textbox" id="deptLevel" name="deptLevel" prompt="请先选择部门地区"  style="width: 100%;"  data-options="novalidate:true,required:true, missingMessage:'请先选择部门地区',editable:false,panelHeight:'auto'"/>
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                    	<td class="power-table-label" style="text-align:right;width:100px;">机关性质<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td style="width: 240px;">
                            <input name="nature" id="nature" class="easyui-textbox" prompt="请选择" data-options="novalidate:true,editable:false,required:true, missingMessage:'请选择机关性质'" style="width: 100%;"/>
                        </td>
                    	<td class="power-table-label" style="text-align:right;">法定代表人：</td>
                        <td style="width: 240px;">
                            <input class="easyui-textbox" id="representative" name="representative"  style="width: 100%;" data-options="validType:'length[0,100]'" />
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                        <td class="power-table-label" style="text-align:right;width:100px;">邮政编码&nbsp;：</td>
                        <td style="width: 240px;">
                            <input class="easyui-textbox" data-options="validType:'length[0,6]'" id="postCode" name="postCode" style="width: 100%;" />
                        </td>
                        <td class="power-table-label" style="text-align:right;">电子邮箱&nbsp;：</td>
                        <td style="width: 240px;">
                            <input name="mail" id="mail" class="easyui-textbox" data-options="validType:'length[0,100]'" style="width: 100%;" />
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                        <td class="power-table-label" style="text-align:right;width:100px;">传&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;真&nbsp;：</td>
                        <td style="width: 240px;">
                            <input class="easyui-textbox" id="fax" name="fax" data-options="validType:'length[0,36]'" style="width: 100%;" />
                        </td>
                        <td class="power-table-label" style="text-align:right;">联系电话&nbsp;：</td>
                        <td style="width: 240px;">
                            <input class="easyui-textbox" id="phone" name="phone" data-options="validType:'length[0,36]'" style="width: 100%;" />
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                        <td class="power-table-label" style="text-align:right;width:100px;">地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址&nbsp;：</td>
                        <td colspan="3">
                            <input name="address" id="address" class="easyui-textbox" data-options="validType:'length[0,500]'" style="width: 100%;" />
                        </td>
                    </tr>
                    </table>
            </form>
        </div>
        <script>
           $(function () {
           $('#name').textbox('textbox').attr('maxlength', 300);
           $('#simpleName').textbox('textbox').attr('maxlength', 300);
           $('#representative').textbox('textbox').attr('maxlength', 100);
           $('#departmentCode').textbox('textbox').attr('maxlength', 18);
           $('#postCode').textbox('textbox').attr('maxlength', 6); 
           $('#mail').textbox('textbox').attr('maxlength', 18); 
           $('#fax').textbox('textbox').attr('maxlength', 36);
           $('#phone').textbox('textbox').attr('maxlength', 36);
           $('#address').textbox('textbox').attr('maxlength', 500);
           });
         </script>
<script type="text/javascript" src="<%=contextPath%>/supervise/supervise/js/supervise_add.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/md5.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
</body>
</html>