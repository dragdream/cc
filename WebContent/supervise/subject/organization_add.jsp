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
                        <td class="power-table-label" style="text-align:right;">受委托组织名称<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td colspan="3">
                            <input class="easyui-textbox" id="subName" name="subName" data-options="validType:'length[1,300]',required:true, missingMessage:'请输入受委托组织名称'" style="width: 99%;" value=""  /></td>
                    </tr>
                    <tr style="border:none!important;">
                    	<td class="power-table-label" style="text-align:right;">统一社会信用代码<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td style=" width: 260px;">
                             <input class="easyui-textbox" id="code" name="code" data-options="required:true, missingMessage:'请输入统一社会信用代码'" style="width: 98%;" value="" />
                        </td>
                        <td class="power-table-label" style="text-align:right;">受委托组织性质<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td style=" width: 260px;" align="center" valign="middle">
							<input name="entrustNature" id="entrustNature" prompt="请选择" style="width: 98%;" class="easyui-textbox" data-options="novalidate:true,required:true, missingMessage:'请选择受委托组织性质',editable:false,panelHeight:'auto'"/>
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                    	<td class="power-table-label" style="text-align:right;">委托主体<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td style=" width: 260px;">
                       		<input class="easyui-textbox" id="parentId" name="parentId" data-options="novalidate:true,required:true, missingMessage:'请选择委托主体',panelHeight:'auto'" style="width: 98%;"  />
                        </td>
                    	<td class="power-table-label" style="text-align:right;">委托方式<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td style=" width: 260px;" align="center" valign="middle">
							<input name="orgMode" id="orgMode" prompt="请选择" style="width: 98%;" class="easyui-textbox" data-options="novalidate:true,required:true, missingMessage:'请选择委托方式',editable:false,panelHeight:'auto'"/>
                        </td>
                    </tr>
                    <tr style="border:none!important;">
              		    <td class="power-table-label" style="text-align:right;">委托期限（起）：</td>
                        <td style=" width: 260px;" align="center" valign="middle">
                        	<input  class="easyui-datebox" id='termBeginStr'  name='termBeginStr' data-options="editable:false" style="width: 98%;"  />
                        </td>
                        <td class="power-table-label" style="text-align:right;">委托期限（止）：</td>
                        <td style=" width: 260px;" align="center" valign="middle">
                        	<input  class="easyui-datebox" id='termEndStr'  name='termEndStr' data-options="editable:false" style="width: 98%;"  />
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                        <td class="power-table-label" style="text-align:right;">法定代表人&nbsp;&nbsp;：</td>
                        <td style="width: 260px;">
							 <input class="easyui-textbox" id="representative" name="representative" data-options="validType:'length[0,30]'" style="width: 98%;" value=""  />
                       	</td>
                    </tr>
                    <tr style="border:none!important;">
                        <td class="power-table-label" style="text-align:right;">联系电话&nbsp;：</td>
                        <td style=" width: 260px;">
                            <input class="easyui-textbox" id="telephone" name="telephone" data-options="validType:'length[0,30]'" style="width: 98%;" />
                        </td>
                        <td class="power-table-label" style="text-align:right;">传&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;真&nbsp;：</td>
                        <td style=" width: 260px;">
							<input class="easyui-textbox" id="fax" name="fax" data-options="validType:'length[0,20]'" style="width: 98%;" />
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                    	<td class="power-table-label" style="text-align:right;">电子邮箱&nbsp;：</td>
                        <td style=" width: 260px;">
                       		<input class="easyui-textbox" id="mail" name="mail" data-options="validType:'length[0,100]'" style="width: 98%;" />
                        </td>
                         <td class="power-table-label" style="text-align:right;">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;编&nbsp;：</td>
                        <td style=" width: 260px;">
                       		<input class="easyui-textbox" id="postCode" name="postCode" data-options="validType:'length[0,6]'" style="width: 98%;" />
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                    	<td class="power-table-label" style="text-align:right;">地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址&nbsp;：</td>
                        <td colspan="3">
                        	<input class="easyui-textbox" id="address" name="address" data-options="validType:'length[0,200]'" style="width: 99%;" />
                         </td>
                    </tr>
                    </table>
            </form>
        </div>
        <script>
           $(function () {
           $('#subName').textbox('textbox').attr('maxlength', 300);
           $('#code').textbox('textbox').attr('maxlength', 18);
           $('#representative').textbox('textbox').attr('maxlength', 30);
           $('#telephone').textbox('textbox').attr('maxlength', 30);
           $('#fax').textbox('textbox').attr('maxlength', 20); 
           $('#mail').textbox('textbox').attr('maxlength', 100); 
           $('#postCode').textbox('textbox').attr('maxlength', 6);
           $('#address').textbox('textbox').attr('maxlength', 200);
           });
         </script>
<script type="text/javascript" src="<%=contextPath%>/supervise/subject/js/organization_add.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/md5.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>
</body>
</html>