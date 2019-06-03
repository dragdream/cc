<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<%@ include file="/header/upload.jsp" %>
<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body  onload="doInit();" style="padding: 10px;background-color: #fff;">
	<div class="easyui-layout" data-options="fit:true,border:false">
		<form role="form" id= "form1" name = "form1"  enctype="multipart/form-data" method="post" class="easyui-form" data-options="novalidate:true">
			<table class="TableBlock" frame="void" style="width: 100%; background: #fff;" rules="none">
	            <tr style="border:none!important;">
	            	<td class="power-table-label" style="text-align:right;width: 200px;">评分版本<span style="color:red;font-weight:bold;">*</span>：&nbsp;&nbsp;</td>
	               	<td>
	                	<input class="easyui-textbox" id="name" style="width: 250px" name="name" data-options="required:true, missingMessage:'请输入版本名称'"  />
	               	</td>
	           	</tr>
	            <tr style="border:none!important;">
	            	<td class="power-table-label" style="text-align:right;width: 200px;">生效日期<span class="required">*</span>：&nbsp;&nbsp;</td>
	               	<td>
	                	<select id="startDateStr" name="startDateStr"  class="easyui-datebox" 
                            style="width: 100%; height: 30px; max-width: 250px;" data-options="validType:'date',required:true, novalidate:true, missingMessage:'请选择生效日期' ">
                        </select>
	               	</td>
	           	</tr>
	            <tr style="border:none!important;">
	            	<td class="power-table-label" style="text-align:right;width: 200px;">废止日期：&nbsp;&nbsp;</td>
	               	<td>
	                	<select id=""endDateStr"" name="endDateStr" class="easyui-datebox" 
                            style="width: 100%; height: 30px; max-width: 250px;" data-options="validType:'date', novalidate:true ">
                        </select>
	               	</td>
	           	</tr>
                <tr style="border:none!important;">
                    	<td class="power-table-label" style="text-align:right;width:200px;">上传文件：&nbsp;&nbsp;</td>
                        <td class="TableData" >
							<a id="uploadHolder2" class="add_swfupload">
								<img src="<%=systemImagePath %>/upload/batch_upload.png"/>上传
							</a>
							<input id="attaches" name="attaches" type="hidden"/>
						</td>
						<td>
							<div id="fileContainer2"></div>
							<div id="renderContainer2" style="display:none"></div>
                             <div id="attachDiv"></div>
						</td>
                    </tr>
                    
                    <tr style="border:none!important;">
                    	<td></td>
 						<td colspan="2">上传文件格式请参照模板下载</td>
                    </tr>
        	</table>
		</form>
	</div>
<script type="text/javascript" src="<%=contextPath%>/supervise/caseCheck/rules/js/rules_addVersion.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/md5.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>
</body>
</html>