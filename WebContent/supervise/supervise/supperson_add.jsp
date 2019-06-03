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
<%@ include file="/header/upload.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style>
.none-border-tr {
	border: none !important;
}
</style>
<script type="text/javascript">
var id = '<%=id%>';
</script>
</head>
<body  onload="doInit();" style="padding: 10px;background-color: #fff;">
        <div >
            <form role="form" id= "form1" name = "form1"  enctype="multipart/form-data" method="post" class="easyui-form" data-options="novalidate:true">
                <input type="hidden" id="id" name = "id" value=""/>
                <table class="TableBlock" style="width: 100%;background: #fff;">
                    <tr style="border:none!important;">
                        <td class="power-table-label" style="text-align:right;">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td style="width: 250px;">
                            <input name="name" id="name" class="easyui-textbox" data-options="validType:'length[1,50]',required:true, missingMessage:'请输入姓名'" style="width: 89%;" />
                        </td>
                        <td class="power-table-label" style="text-align:right;">身份证号<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td style=" width: 250px;">
                            <input name="personId" id="personId" class="easyui-textbox" data-options="novalidate:true,required:true, missingMessage:'请输入身份证号'" style="width: 89%;"  />
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                        <td class="power-table-label" style="text-align:right;">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td style="width: 250px;">
                       		<input name="sex" id="sex" prompt="请先填写身份证号码" data-options="novalidate:true,required:true, missingMessage:'请先输入身份证号',editable:false" style="width: 89%;" class="easyui-textbox" />
                        </td>
                        <td class="power-table-label" style="text-align:right;" nowrap>出生日期<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td style=" width: 250px;">
                        	<input  class="easyui-textbox" prompt="请先填写身份证号码" id='birthStr'  name='birthStr' data-options="novalidate:true,editable:false,required:true, missingMessage:'请先输入身份证号'" style="width: 89%;" readonly />
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                    	<td class="power-table-label" style="text-align:right;">民&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;族<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td style=" width: 250px;">
							<input name="nation" id="nation" prompt="输入关键字后自动搜索" style="width: 89%;" class="easyui-combobox" data-options="novalidate:true,required:true, missingMessage:'请选择民族'"/>
		    			</td>
                        <td class="power-table-label" style="text-align:right;">所属机关全称<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td style="width: 250px;">
                        	<input class="easyui-textbox" id="departmentName" name="departmentName"  style="width: 89%;" data-options="novalidate:true,required:true, missingMessage:'请选择所属机关全称'" />
                         </td>
                    </tr>
                    <tr style="border:none!important;">
                        <td class="power-table-label" style="text-align:right;">政治面貌<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td style="width: 250px;">
                       		<input name="politive" id="politive" prompt="请选择" style="width: 89%;" class="easyui-combobox" data-options="novalidate:true,required:true, missingMessage:'请选择政治面貌',editable:false" />
                        </td>
                        <td class="power-table-label" style="text-align:right;">职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;级<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td style=" width: 250px;">
							<input name="jobClass" id="jobClass" prompt="请选择" style="width: 89%;" class="easyui-combobox" data-options="novalidate:true,required:true, missingMessage:'请选择职级',editable:false"/>
		    			</td>
                    </tr>
                    <tr style="border:none!important;">
                        <td class="power-table-label" style="text-align:right;">最高学历<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td style="width: 250px;">
                       		<input name="education" id="education" prompt="请选择" style="width: 89%;" class="easyui-combobox" data-options="novalidate:true,required:true, missingMessage:'请选择最高学历',editable:false" />
                        </td>
                        <td class="power-table-label" style="text-align:right;" nowrap>是否公职律师<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td style="width: 250px;">
							<input type="radio" name="isLawyer" id="lawYes"  style="width:14px;height:14px;"  value="1" />
							<label for="lawYes">是</label>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="isLawyer" id="lawNo"  style="width:14px;height:14px;"  value="0" />
							<label for="lawNo">否</label>
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                    	<td class="power-table-label" style="text-align:right;">联系电话&nbsp;&nbsp;：</td>
                        <td style="width: 250px;">
                            <input name="telephone" id="telephone" class="easyui-textbox" style="width: 89%;"  />
                        </td>
                    	<td class="power-table-label" style="text-align:right;" nowrap>取得过执法证&nbsp;&nbsp;：</td>
                        <td style="width: 250px;">
							<input type="radio" name="isGetcode" id="male"  style="width:14px;height:14px;"  value="1" />
							<label for="male">是</label>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="isGetcode" id="female"  style="width:14px;height:14px;"  value="0" />
							<label for="female">否</label>
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                    	<td class="power-table-label" style="text-align:right;">照&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;片&nbsp;&nbsp;：</td>
                        <td class="TableData" style="width: 180px;">
							<a id="uploadHolder2" class="add_swfupload">
								<img src="<%=systemImagePath %>/upload/batch_upload.png"/>上传照片
							</a>
							<input id="attaches" name="attaches" type="hidden"/>
 						 </td>
 						 <td class="power-table-label" ></td>
                         <td style="width: 250px;">
                         	<div id="fileContainer2"></div>
							<div id="renderContainer2" style="display:none"></div>
                             <div id="attachDiv"></div>
                         </td>
                    </tr>
                    </table>
            		
            </form>
        </div>
        <script>
           $(function () {
           $('#name').textbox('textbox').attr('maxlength', 50);
           $('#personId').textbox('textbox').attr('maxlength', 18);
           $('#telephone').textbox('textbox').attr('maxlength', 50);
           });
         </script>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/md5.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/supervise/js/supperson_add.js"></script>
</body>
</html>