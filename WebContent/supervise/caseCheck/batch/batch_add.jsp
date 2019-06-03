<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// 	Object tempModel = request.getAttribute("tempModel");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<%@ include file="/header/upload.jsp" %>
<link rel="stylesheet" type="text/css" href="/supervise/caseManager/commonCaseSearch/css/case.css" />
<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript">
</script>
<style type="text/css">
.datebox-calendar-inner {
    height: 180px;
}
</style>
</head>
<body  onload="doInit();" style="padding: 10px;background-color: #fff;">
    <form role="form" id= "form1" name = "form1"  enctype="multipart/form-data" method="post" class="easyui-form" data-options="novalidate:true">
		<div class="easyui-panel" style="width: 100%;" align="center" data-options="border:false">
                <input type="hidden" id="id" name = "id" value="<c:out value ='${tempModel.id}'/>"/>
                <div>
                	<img src="<%=systemImagePath %>/upload/ceshi.png"/>
                </div>
                <table class="TableBlock" id="table" style="width: 100%; background: #fff;">
                	<tr style="border:none!important;">
                        <td class="power-table-label" style="width:20%;text-align:right;" nowrap>任务名称&nbsp;&nbsp;：</td>
                        <td colspan="2" style="width:16%;">
							<input name="name" id="name" style="width: 98%;" class="easyui-textbox"  />
		    			</td>
		    			<td class="power-table-label" style="width:20%;text-align:right;" nowrap>案卷类型<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td colspan="2" style="width:16%;">
							<input name="caseType" id="caseType" prompt="请选择" style="width: 98%;" class="easyui-textbox" data-options="novalidate:true,required:true, missingMessage:'请选择案卷类型',editable:false,panelHeight:'auto'" />
		    			</td>
		    			<td style="width:8%;"></td>
		    			<td style="width:20%;"></td>
                    </tr>
                    <tr style="border:none!important;">
              		    <td class="power-table-label" style="width:20%;text-align:right;" nowrap>评查开始日期<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td colspan="2" style="width:16%;" align="center" valign="middle">
                        	<input  class="easyui-datebox" id='beginDateStr'  name='beginDateStr' data-options="novalidate:true,required:true, missingMessage:'请选择评查开始日期',editable:false" style="width: 98%;"  />
                        </td>
                        <td class="power-table-label" style="width:20%;text-align:right;" nowrap>评查结束日期<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td colspan="2" style="width:16%;" align="center" valign="middle">
                        	<input  class="easyui-datebox" id='endDateStr'  name='endDateStr' data-options="novalidate:true,required:true, missingMessage:'请选择评查结束日期',editable:false" style="width: 98%;"  />
                        </td>
                        <td style="width:8%;"></td>
		    			<td style="width:20%;"></td>
                    </tr>
                </table>
                <table class="TableBlock" id="table1" style="width: 100%; background: #fff;">
                    <tr style="border:none!important;">
                    	<td class="power-table-label" style="width:20%;text-align:right;" nowrap>评查范围<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td style="width:8%;text-align:right;">
							按区域：
		    			</td>
		    			<td style="width:10%;">
			    			<c:if test="${tempModel.userLevel == '0300'}">
		                		<select id="batchArea" name="batchArea" class="easyui-combobox" data-options="editable:false,panelHeight:'auto',multiple:true">
			    					<option value="1">市直部门</option>
			    					<option value="2">区级</option>
			    				</select>
	                		</c:if>
	                		
		    				<c:if test="${tempModel.userLevel == '0400'}">
		                		<select id="batchArea" name="batchArea" class="easyui-combobox" data-options="editable:false,panelHeight:'auto',multiple:true">
			    					<option value="2">区级</option>
			    				</select>
	                		</c:if>
		    			</td>
		    			<td style="width:15%;" >
							<div id="quji" style="display:none"><input name="batchQuji" id="batchQuji" class="easyui-combobox" data-options="editable:false" /></div>
		    			</td>
		    			<td class="power-table-label" style="width:15%;text-align:right;">按行业&nbsp;&nbsp;：</td>
                        <td style="width:32%;">
                        	<input name="batchOrgsys" id="batchOrgsys" prompt="请选择" style="width: 60%;" class="easyui-combobox" data-options="multiple:true,novalidate:true,required:true,editable:false,panelHeight:'auto'" />
		    			</td>
                    </tr>
                </table>
                <table class="TableBlock" id="table2" style="width: 100%; background: #fff;">
                    <tr style="border:none!important;">
                    	<td class="power-table-label" style="width:20%;text-align:right;" nowrap>案卷日期类型<span style="color:red;font-weight:bold;">*</span>：</td>
                    	<td colspan="5"></td>
                    </tr>
                    <tr style="border:none!important;display:none" id="jiancha">
                    	<td style="width:20%;"></td>
                    	<td class="power-table-label" style="width:13%;text-align:right;" nowrap>检查日期&nbsp;&nbsp;：</td>
                    	<td class="power-table-label" style="text-align:right;width:13%;" nowrap>起始日期<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td style=" width: 20%;" align="center" valign="middle">
                        	<input  class="easyui-datebox" id='inspBeginDateStr'  name='inspBeginDateStr' data-options="editable:false"   />
                        </td>
                        <td class="power-table-label" style="text-align:right;width:13%;" nowrap>截止日期<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td style=" width: 20%;" align="center" valign="middle">
                        	<input  class="easyui-datebox" id='inspEndDateStr'  name='inspEndDateStr' data-options="editable:false"   />
                        </td>
                    </tr>
                    <tr style="border:none!important;display:none" class="qiangzhi">
                    	<td style="width:20%;"></td>
                    	<td class="power-table-label" style="width:13%;text-align:right;" nowrap>强制措施实施日期&nbsp;&nbsp;：</td>
                    	<td class="power-table-label" style="text-align:right;width:13%;" nowrap>起始日期<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td style=" width:20%;" align="center" valign="middle">
                        	<input  class="easyui-datebox" id='coercionMeasureBeginDateStr'  name='coercionMeasureBeginDateStr' data-options="editable:false"   />
                        </td>
                        <td class="power-table-label" style="text-align:right;width:13%;" nowrap>截止日期<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td style=" width:20%;" align="center" valign="middle">
                        	<input  class="easyui-datebox" id='coercionMeasureEndDateStr'  name='coercionMeasureEndDateStr' data-options="editable:false"   />
                        </td>
                    </tr>
                    <tr style="border:none!important;display:none" class="qiangzhi">
                    	<td style="width:20%;"></td>
                    	<td class="power-table-label" style="width:13%;text-align:right;" nowrap>行政机关强制执行完成日期&nbsp;&nbsp;：</td>
                    	<td class="power-table-label" style="text-align:right;width:13%;" nowrap>起始日期<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td style=" width:20%;" align="center" valign="middle">
                        	<input  class="easyui-datebox" id='coercionPerformBeginDateStr'  name='coercionPerformBeginDateStr' data-options="editable:false"   />
                        </td>
                        <td class="power-table-label" style="text-align:right;width:13%;" nowrap>截止日期<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td style=" width:20%;" align="center" valign="middle">
                        	<input  class="easyui-datebox" id='coercionPerformEndDateStr'  name='coercionPerformEndDateStr' data-options="editable:false"   />
                        </td>
                    </tr>
                    <tr style="border:none!important;display:none" class="qiangzhi">
                    	<td style="width:20%;"></td>
                    	<td class="power-table-label" style="width:13%;text-align:right;" nowrap>申请法院强制执行日期&nbsp;&nbsp;：</td>
                    	<td class="power-table-label" style="text-align:right;width:13%;" nowrap>起始日期<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td style=" width:20%;" align="center" valign="middle">
                        	<input  class="easyui-datebox" id='faForceBeginDateStr'  name='faForceBeginDateStr' data-options="editable:false"   />
                        </td>
                        <td class="power-table-label" style="text-align:right;width:13%;" nowrap>截止日期<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td style=" width:20%;" align="center" valign="middle">
                        	<input  class="easyui-datebox" id='faForceEndDateStr'  name='faForceEndDateStr' data-options="editable:false"   />
                        </td>
                    </tr>
                    <tr style="border:none!important;display:none" class="chufa">
                    	<td style="width:20%;"></td>
                    	<td class="power-table-label" style="width:13%;text-align:right;" nowrap>立案日期&nbsp;&nbsp;：</td>
                    	<td class="power-table-label" style="text-align:right;width:13%;" nowrap>起始日期<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td style=" width:20%;" align="center" valign="middle">
                        	<input  class="easyui-datebox" id='filingBeginDateStr'  name='filingBeginDateStr' data-options="editable:false"   />
                        </td>
                        <td class="power-table-label" style="text-align:right;width:13%;" nowrap>截止日期<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td style=" width:20%;" align="center" valign="middle">
                        	<input  class="easyui-datebox" id='filingEndDateStr'  name='filingEndDateStr' data-options="editable:false"   />
                        </td>
                    </tr>
                    <tr style="border:none!important;display:none" class="chufa">
                    	<td style="width:20%;"></td>
                    	<td class="power-table-label" style="width:13%;text-align:right;" nowrap>决定做出日期&nbsp;&nbsp;：</td>
                    	<td class="power-table-label" style="text-align:right;width:13%;" nowrap>起始日期<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td style=" width:20%;" align="center" valign="middle">
                        	<input  class="easyui-datebox" id='desBeginDateStr'  name='desBeginDateStr' data-options="editable:false"   />
                        </td>
                        <td class="power-table-label" style="text-align:right;width:13%;" nowrap>截止日期<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td style=" width:20%;" align="center" valign="middle">
                        	<input  class="easyui-datebox" id='desEndDateStr'  name='desEndDateStr' data-options="editable:false"   />
                        </td>
                    </tr>
                    <tr style="border:none!important;display:none" class="chufa">
                    	<td style="width:20%;"></td>
                    	<td class="power-table-label" style="width:13%;text-align:right;" nowrap>执行完成日期&nbsp;&nbsp;：</td>
                    	<td class="power-table-label" style="text-align:right;width:13%;" nowrap>起始日期<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td style=" width:20%;" align="center" valign="middle">
                        	<input  class="easyui-datebox" id='executionBeginDateStr'  name='executionBeginDateStr' data-options="editable:false"   />
                        </td>
                        <td class="power-table-label" style="text-align:right;width:13%;" nowrap>截止日期<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td style=" width:20%;" align="center" valign="middle">
                        	<input  class="easyui-datebox" id='executionEndDateStr'  name='executionEndDateStr' data-options="editable:false"   />
                        </td>
                    </tr>
                    <tr style="border:none!important;display:none" class="chufa">
                    	<td style="width:20%;"></td>
                    	<td class="power-table-label" style="width:13%;text-align:right;" nowrap>结案日期&nbsp;&nbsp;：</td>
                    	<td class="power-table-label" style="text-align:right;width:13%;" nowrap>起始日期<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td style="width:20%;" align="center" valign="middle">
                        	<input  class="easyui-datebox" id='closeBeginDateStr'  name='closeBeginDateStr' data-options="editable:false"  />
                        </td>
                        <td class="power-table-label" style="text-align:right;width:13%;" nowrap>截止日期<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td style="width:20%;" align="center" valign="middle">
                        	<input  class="easyui-datebox" id='closeEndDateStr'  name='closeEndDateStr' data-options="editable:false"   />
                        </td>
                    </tr>
                </table>
                <table class="TableBlock" id="table3" style="width: 100%; background: #fff;">
                    <tr style="border:none!important;" >
                    	<td class="power-table-label" style="width:20%;text-align:right;">受委托单位<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td style="width:30%;">
                       		<input type="radio" name="isOrg" id="male"  style="width:14px;height:14px;" value="1" />
							<label for="male">有</label>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="isOrg" id="fou"  style="width:14px;height:14px;" value="0" />
							<label for="fou">无</label>
                        </td>
                        <td class="power-table-label" style="width:20%;text-align:right;" nowrap>包含委托单位&nbsp;&nbsp;：</td>
                        <td style="width:30%;">
							<input name="orgId" id="orgId" prompt="请选择" style="width: 89%;" class="easyui-textbox" data-options="editable:false,panelHeight:'auto'" />
		    			</td>
                    </tr>
                    <tr style="border:none!important;line-height:20px;" >
                    	<td class="power-table-label" style="width: 150px;text-align:right;">法律法规授权的下属单位<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td style="width: 260px;">
                       		<input type="radio" name="isLawDept" id="yes"  style="width:14px;height:14px;" value="1" />
							<label for="yes">有</label>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="isLawDept" id="no"  style="width:14px;height:14px;" value="0" />
							<label for="no">无</label>
                        </td>
                        <td class="power-table-label" style="text-align:right;">法律法规授权的下属单位&nbsp;&nbsp;：</td>
                        <td >
							<input name="lawDeptName" id="lawDeptName" prompt="请选择" style="width: 89%;" class="easyui-textbox" data-options="editable:false,panelHeight:'auto'" />
		    			</td>
                    </tr>
                </table>
    		<div id="batchAddBtn" align="center"  style="border-bottom-right-radius: 4px;border-bottom-left-radius: 4px;text-align: center;width:100%;padding: 8px 0;background-color:#fff;float: right;position:fixed; bottom:0;">
	        	<button class="easyui-linkbutton" title="保存" onclick="save()">
	            <span style="padding-right: 2px; width: 40px;"><i class="fa fa-save"></i>&nbsp;保存</span>
		        </button>
		        &nbsp;&nbsp;
		        <button class="easyui-linkbutton" title="返回" onclick="back()">
		            <span style="padding-right: 2px; width: 40px;"><i class="fa fa-reply"></i>&nbsp;返回</span>
		        </button>
		        &nbsp;&nbsp;
		        <button class="easyui-linkbutton" title="下一步" onclick="next()">
		            <span style="padding-right: 2px; width: 40px;"><i class="fa fa-reply"></i>&nbsp;下一步</span>
		        </button>
        	</div>
        </div>
    </form>
	  <script>
	     $(function () {
	     $('#name').textbox('textbox').attr('maxlength', 300);
	     });
	   </script>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/md5.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/caseCheck/batch/js/batch_add.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/caseCheck/batch/js/batch.js"></script>
</body>
</html>