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
<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/caseManager/commonCaseSearch/css/case.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript">
var id = '<%=id%>';
</script>
<style>
#representative{height: 32px; line-height: 32px;border:1px solid #DDDDDD;}
</style>
</head>
<body  onload="doInit();" style="padding: 10px;background-color: #fff;">
    <form role="form" id= "form1" name = "form1"  enctype="multipart/form-data" method="post" class="easyui-form" data-options="novalidate:true">
		<div class="easyui-panel" title="基础信息" style="width: 100%;" align="center">
                <input type="hidden" id="id" name = "id" value=""/>
                <table class="TableBlock" frame="void" style="width: 100%; background: #fff;" rules="none">
                     <tr style="border:none!important;">
                        <td class="power-table-label" style="text-align:right;">机关全称<span style="color:red;font-weight:bold;">*</span>：&nbsp;&nbsp;</td>
                        <td colspan="3">
                            <input class="easyui-textbox" id="name" data-options="validType:'length[0,300]',required:true, missingMessage:'请输入机关全称'" name="name" style="width: 98%;" value=""/>
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                    	<td class="power-table-label" style="text-align:right;width: 100px;">机关简称<span style="color:red;font-weight:bold;">*</span>：&nbsp;&nbsp;</td>
                        <td>
                            <input class="easyui-textbox" id="simpleName" style="width: 300px" name="simpleName" data-options="validType:'length[1,300]',required:true, missingMessage:'请输入机关简称'"  />
                        </td>
                        <td class="power-table-label" style="text-align:right;width: 160px;">统一社会信用代码<span style="color:red;font-weight:bold;">*</span>：&nbsp;&nbsp;</td>
                        <td style="width: 300px;">
                            <input name="departmentCode" id="departmentCode" style="width: 300px;" class="easyui-textbox" data-options="validType:'length[1,18]',required:true, missingMessage:'请输入统一社会信用代码'"  />
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                    	<td class="power-table-label" style="text-align:right;width: 100px;">成立日期&nbsp;&nbsp;：&nbsp;&nbsp;</td>
                        <td style=" width: 300px;">
                        	<input  class="easyui-datebox" id='establishDateStr' style="width: 300px" name='establishDateStr'  data-options="editable:false,panelHeight:'auto'"  />
                    	</td>
                    	<td class="power-table-label" style="text-align:right;width: 160px;">法定代表人&nbsp;&nbsp;：&nbsp;&nbsp;</td>
                        <td>
                            <input class="easyui-textbox" id="representative" style="width: 300px" name="representative" data-options="validType:'length[0,100]'"  /> 
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                        <td class="power-table-label" style="text-align:right;width: 100px;">行政区划<span style="color:red;font-weight:bold;">*</span>：&nbsp;&nbsp;</td>
                        <td style=" width: 300px;">
            	            <input name="administrativeDivision" id="administrativeDivision"  style="width: 300px" data-options="novalidate:true,required:true,editable:false,panelHeight:'auto'" class="easyui-textbox" />
                        </td>
                        <td class="power-table-label" style="text-align:right;width: 160px;">机关层级<span style="color:red;font-weight:bold;">*</span>：&nbsp;&nbsp;</td>
                        <td style=" width: 300px;">
                            <input class="easyui-textbox" id="deptLevel" name="deptLevel" style="width: 300px"  data-options="novalidate:true,required:true,editable:false,panelHeight:'auto'"></input>
                    	</td>
                    </tr>
                    <tr style="border:none!important;">
                    	<td class="power-table-label" style="text-align:right;width: 100px;">机关性质<span style="color:red;font-weight:bold;">*</span>：&nbsp;&nbsp;</td>
                        <td style=" width: 300px;">
                        	<input name="nature" id="nature" prompt="请选择" style="width: 300px"  class="easyui-combobox" data-options="novalidate:true,required:true, missingMessage:'请选择机关性质',editable:false,panelHeight:'auto'" />
		    			</td>
		    			<td  style="text-align:right;">所属领域<span style="color:red;font-weight:bold;width: 160px">*</span>：&nbsp;&nbsp;</td>
                        <td style=" width: 300px;">
                         	 <input name="orgSys" id="orgSys"  style="width: 300px" required="required"  multiple="multiple" data-options="novalidate:true,required:true, missingMessage:'请选择所属领域',editable:false,multiple:true" class="easyui-combobox"  />
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                    	<td class="power-table-label" style="text-align:right;width: 100px;">监督机关<span style="color:red;font-weight:bold;">*</span>：&nbsp;&nbsp;</td>
                       	<td>
                       		<input class="easyui-textbox" id="superviceDepartmentId" style="width: 300px" name="superviceDepartmentId" data-options="novalidate:true,required:true, missingMessage:'请选择监督部门',panelHeight:'auto'" />
                    	</td>
                    	<td class="power-table-label" style="text-align:right;width: 100px;">是否垂管<span style="color:red;font-weight:bold;">*</span>：&nbsp;&nbsp;</td>
                        <td>							
							<input type="radio" name="isManubrium" id="male"  style="width:14px;height:14px;" onclick="show(this)" value="1" />
							<label for="male">是</label>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="isManubrium" id="fou"  style="width:14px;height:14px;" onclick="show(this)" value="0" />
							<label for="fou">否</label>
						</td>
                    </tr>
                    <tr style="border:none!important;">
                    	<td class="power-table-label" id="div1" name="div1" style="display: none;text-align:right;width: 100px;" >垂管部门<span style="color:red;font-weight:bold;">*</span>：&nbsp;&nbsp;</td>
                        <td id="div2" name="div2" style="display: none;">
                        	<input class="easyui-textbox" id="droopId" style="width: 300px" name="droopId"  />
						</td>
                    	<td class="power-table-label" id="div3" name="div3" style="display: none;text-align:right;width: 100px;">垂管层级<span style="color:red;font-weight:bold;">*</span>：&nbsp;&nbsp;</td>
                        <td id="div4" name="div4" style="display: none; width: 300px;">
                        	<input name="manubriumLevel" id="manubriumLevel" prompt="请选择"  style="width: 300px" class="easyui-textbox" data-options="editable:false,panelHeight:'auto'" />
		    			</td>
		    		</tr>
                    </table>
        </div>
        <br />
        <div class="easyui-panel" title="内设监督机构" style="width: 100%;" align="center">
            <table class="TableBlock" frame="void" style="width: 100%; background: #fff;" rules="none">
            	<tr style="border:none!important;">
                        <td class="power-table-label" style="text-align:right;width: 100px;">机构名称<span style="color:red;font-weight:bold;">*</span>：&nbsp;&nbsp;</td>
                        <td style=" width: 300px;">
                            <input class="easyui-textbox" id="innerSupOrgName" style="width: 300px" name="innerSupOrgName" data-options="validType:'length[1,300]',required:true, missingMessage:'请输入机构名称'" />
                        </td>
                        <td class="power-table-label" style="text-align:right;width: 160px;">机构级别<span style="color:red;font-weight:bold;">*</span>：&nbsp;&nbsp;</td>
                        <td >
                        	<input name="innerSupOrgLevel" id="innerSupOrgLevel" style="width: 300px" prompt="请选择"   class="easyui-textbox" data-options="novalidate:true,required:true, missingMessage:'请选择机构层级',editable:false,panelHeight:'auto'" />
		    			</td>
		    	</tr>
		    	<tr style="border:none!important;">
		    			<td class="power-table-label" style="text-align:right;width: 100px;">编制数<span style="color:red;font-weight:bold;">*</span>：&nbsp;&nbsp;</td>
                        <td style=" width: 300px;">
                            <input class="easyui-textbox" id="innerSupOrgPostNum" style="width: 300px" name="innerSupOrgPostNum" data-options="validType:'length[1,4]',required:true, missingMessage:'请输入编制数'"  />
                        </td>
                        <td class="power-table-label" style="text-align:right;width: 160px;">负责人&nbsp;&nbsp;：&nbsp;&nbsp;</td>
                        <td>
                            <input class="easyui-textbox" id="innerSupOrgLoader" style="width: 300px" name="innerSupOrgLoader" data-options="validType:'length[0,100]'" />
                        </td>
                </tr>
        	</table>
        </div>
        <br />
        <div class="easyui-panel" title="联系信息" style="width: 100%;" align="center">
            <table class="TableBlock" frame="void" style="width: 100%; background: #fff;" rules="none">
        			<tr style="border:none!important;">
                        <td class="power-table-label" style="text-align:right;width: 100px;">邮政编码&nbsp;&nbsp;：&nbsp;&nbsp;</td>
                        <td >
                            <input class="easyui-textbox" id="postCode" name="postCode" style="width: 300px" data-options="validType:'length[0,6]'"  />
                        </td>
                        <td class="power-table-label" style="text-align:right;width: 160px;">传&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;真&nbsp;&nbsp;：&nbsp;&nbsp;</td>
                        <td>
                            <input class="easyui-textbox" id="fax" name="fax" style="width: 300px" data-options="validType:'length[0,36]'"  />
                        </td>
                    </tr>
                    <tr style="border:none!important;">
		    			<td class="power-table-label" style="text-align:right;width: 100px;">联系电话&nbsp;&nbsp;：&nbsp;&nbsp;</td>
                        <td >
                            <input class="easyui-textbox" id="phone" name="phone" data-options="validType:'length[0,36]'" style="width: 300px" />
                        </td>
                        <td class="power-table-label" style="text-align:right;width: 160px;">电子邮箱&nbsp;&nbsp;：&nbsp;&nbsp;</td>
                        <td>
                            <input class="easyui-textbox" id="mail" name="mail" data-options="validType:'length[0,36]'" style="width: 300px" />
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                        <td class="power-table-label" style="text-align:right;width: 100px;">地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址&nbsp;&nbsp;</span>：&nbsp;&nbsp;</td>
                        <td colspan="3">
                            <input name="address" id="address" class="easyui-textbox" data-options="validType:'length[1,500]'" style="width: 99%;" />
                        </td>
                    </tr>
         	</table>
        </div>
    </form>
        <script>
           $(function () {
           $('#representative').textbox('textbox').attr('maxlength', 100);
           $('#postCode').textbox('textbox').attr('maxlength', 6);
           $('#address').textbox('textbox').attr('maxlength', 500);
           $('#phone').textbox('textbox').attr('maxlength', 36);
           $('#fax').textbox('textbox').attr('maxlength', 36); 
           $('#departmentCode').textbox('textbox').attr('maxlength', 18); 
           $('#innerSupOrgName').textbox('textbox').attr('maxlength', 300);
           $('#innerSupOrgPostNum').textbox('textbox').attr('maxlength', 4);
           $('#innerSupOrgLoader').textbox('textbox').attr('maxlength', 100);
           $('#mail').textbox('textbox').attr('maxlength', 36);
           });
         </script>
<script type="text/javascript" src="<%=contextPath%>/supervise/Department/js/department_add.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/md5.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>
</body>

</html>