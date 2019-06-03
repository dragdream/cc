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
<link rel="stylesheet" type="text/css" href="/supervise/caseManager/commonCaseSearch/css/case.css" />
<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript">
var id = '<%=id%>';
</script>
<style type="text/css">
.datebox-calendar-inner {
    height: 180px;
}
</style>
</head>
<body  onload="doInit();" style="padding: 10px;background-color: #fff;">
    <form role="form" id= "form1" name = "form1"  enctype="multipart/form-data" method="post" class="easyui-form" data-options="novalidate:true">
		<div class="easyui-panel" title="基础信息" style="width: 100%;" align="center">
                <input type="hidden" id="id" name = "id" value=""/>
                <table class="TableBlock" style="width: 100%; background: #fff;">
                	<tr style="border:none!important;">
                        <td class="power-table-label" style="text-align:right;">所属主体<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td style="width: 260px;">
                            <input name="businessSubName" id="businessSubName" class="easyui-textbox" style="width: 100%;" data-options="novalidate:true,required:true, missingMessage:'请选择所属主体全称',panelHeight:'auto'"/>
                        </td>
                        <td class="power-table-label" style="text-align:right;">委托组织&nbsp;&nbsp;：</td>
                        <td style="width: 260px;">
                            <input name="entrustId" id="entrustId" class="easyui-textbox"  style="width: 100%;" data-options="panelHeight:'auto'"/>
                        </td>
                        <td rowspan="4">
                        
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                        <td class="power-table-label" style="text-align:right;width:100px;">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td style="width: 260px;">
                            <input name="name" id="name" class="easyui-textbox" data-options="validType:'length[1,50]',required:true, missingMessage:'请输入姓名'" style="width: 100%;"  />
                        </td>
                        <td class="power-table-label" style="text-align:right;">身份证号<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td style=" width: 260px;">
                            <input name="personId" id="personId" class="easyui-textbox" style="width: 100%;" data-options="novalidate:true,required:true, missingMessage:'请选择身份证号',panelHeight:'auto'" />
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                        <td class="power-table-label" style="text-align:right;width:100px;">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td style="width: 260px;">
                       		<input name="sex" id="sex" style="width: 100%;" prompt="请先填写身份证号码!" class="easyui-textbox" readonly/>
                        </td>
                        <td class="power-table-label" style="text-align:right;">出生日期<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td style=" width: 260px;">
                        	<input  class="easyui-textbox" id='birthStr'  name='birthStr' prompt="请先填写身份证号码!" style="width: 100%;" readonly/>
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                    	<td class="power-table-label" style="text-align:right;width:100px;">民&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;族<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td style=" width: 260px;">
							<input name="nation" id="nation" prompt="输入关键字后自动搜索" style="width: 100%;" class="easyui-combobox" data-options="novalidate:true,required:true, missingMessage:'请选择民族',panelHeight:'auto'" />
		    			</td>
                        <td class="power-table-label" style="text-align:right;width:100px;">政治面貌<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td style="width: 260px;">
                       		<input name="politive" id="politive" prompt="请选择" style="width: 100%;" class="easyui-combobox" data-options="novalidate:true,required:true, missingMessage:'请选择政治面貌',editable:false,panelHeight:'auto'"/>
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                        <td class="power-table-label" style="text-align:right;">最高学历<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td style="width: 260px;">
                       		<input name="education" id="education" prompt="请选择" style="width: 100%;" class="easyui-textbox" data-options="novalidate:true,required:true, missingMessage:'请选择最高学历',editable:false,panelHeight:'auto'"/>
                        </td>
                        <td class="power-table-label" style="text-align:right;width:100px;">职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;级<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td style=" width: 260px;">
							<input name="jobClass" id="jobClass" prompt="请选择" style="width: 100%;" class="easyui-textbox" data-options="novalidate:true,required:true, missingMessage:'请选择职级',editable:false,panelHeight:'auto'"/>
		    			</td>
                    </tr>
                    <tr style="border:none!important;">
                        <td class="power-table-label" style="text-align:right;">人员性质<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td style=" width: 260px;">
							<input name="personType" id="personType" prompt="请选择" style="width: 100%;" class="easyui-textbox" data-options="novalidate:true,required:true, missingMessage:'请选择执法人员性质',editable:false,panelHeight:'auto'" />
		    			</td>
		    			<td class="power-table-label" style="width: 150px;text-align:right;">法律职业资格<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td style="width: 260px;">
                       		<input type="radio" name="isLawcode" id="male"  style="width:14px;height:14px;" value="1" />
							<label for="male">具有</label>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="isLawcode" id="fou"  style="width:14px;height:14px;" value="0" />
							<label for="fou">不具有</label>
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                    	<td class="power-table-label" style="text-align:right;width:100px;">联系电话&nbsp;&nbsp;：</td>
                        <td style="width: 260px;">
                            <input name="telephone" id="telephone" class="easyui-textbox" data-options="validType:'length[0,50]'" style="width: 100%;" />
                        </td>
                    </tr>
<!--                     <tr style="border:none!important;"> -->
<!--                     	<td class="power-table-label" style="text-align:right;width:100px;">照&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;片&nbsp;&nbsp;：</td> -->
<!--                         <td class="TableData" style="width: 180px;"> -->
<!-- 							<a id="uploadHolder2" class="add_swfupload"> -->
<%-- 								<img src="<%=systemImagePath %>/upload/batch_upload.png"/>上传照片 --%>
<!-- 							</a> -->
<!-- 							<input id="attaches" name="attaches" type="hidden"/> -->
<!--  						 </td> -->
<!--  						 <td class="power-table-label" ></td> -->
<!--                          <td style="width: 250px;"> -->
<!--                          	<div id="fileContainer2"></div> -->
<!-- 							<div id="renderContainer2"></div> -->
<!--                              <div id="attachDiv"></div> -->
<!--                          </td> -->
<!--                     </tr> -->
                    </table>
        </div>
        <br />
        <div id="hangye" class="easyui-panel" title="执法证" style="width: 100%;height:150px" align="center" data-options="tools:'#gistTools'">
        	<div id="gistTools">
                <a onclick="code_add()" id="code_add" title="添加执法证信息"><i class="fa fa-plus"></i></a>
            </div>
            <table id="datagrid" fit="true"  >
			</table>
        </div>
    </form>
    <script>
           $(function () {
           $('#name').textbox('textbox').attr('maxlength', 50);
           $('#personId').textbox('textbox').attr('maxlength', 18);
           $('#telephone').textbox('textbox').attr('maxlength', 50);
           });
         </script>
<script type="text/javascript" src="<%=contextPath%>/supervise/officials/js/officials_add.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/md5.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>

</body>
</html>