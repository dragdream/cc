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
<link rel="stylesheet" type="text/css" href="/supervise/caseManager/commonCaseSearch/css/case.css" />
<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/pureText-lookInfo.css" />
<script type="text/javascript">
var id = '<%=id%>';
</script>
</head>
<body  onload="doInit();" style="padding: 10px;background-color: #fff;">
        <div id="law_tabs" class="easyui-tabs" style="width:100%;" data-options="fit:true,border:false" >
          <div  title="基础信息"  >
              <form role="form" id= "form1" name = "form1"  enctype="multipart/form-data" method="post" class="easyui-form" data-options="novalidate:true">
                <input type="hidden" id="id" name = "id" value="${param.id }"/>
                <table class="TableBlock_page lookInfo-lowHeight-table" frame="void" style="width: 100%; background: #fff; " rules="none">
                    <tr style="border:none!important;">
                        <td class="power-table-label font-bold-label-td" style="width: 80px;text-align:right;">法律法规名称：</td>
                        <td colspan="3"  name="name" id="name" >
	                    </td>
                    </tr>
                    <tr style="border:none!important;">
                    	<td class="power-table-label font-bold-label-td" style="text-align:right;width: 80px;">发布机关：</td>
                        <td style="width: 200px;" name="organ" id="organ">
                        </td>
                        <td class="power-table-label font-bold-label-td" style="width: 80px;text-align:right;">发文字号：</td>
                        <td style="width: 200px;" name="word" id="word">
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                        <td class="power-table-label font-bold-label-td" style="text-align:right;width: 80px;">发布日期：</td>
                        <td style="width: 200px;" id="promulgationStr"  name="promulgation">
                        </td>
                        <td class="power-table-label font-bold-label-td" style="text-align:right;width: 80px;">生效日期：</td>
                        <td style="width: 200px;" name="implementationStr" id="implementation">
                        </td>
                    </tr>
                    
                    <tr style="border:none!important;">
                    	<td class="power-table-label font-bold-label-td" style="text-align:right;width: 80px;">时效性：</td>
                        <td style="width: 200px;" name="timeliness" id="timeliness">
                        </td>
                        <td class="power-table-label font-bold-label-td" style="text-align:right;width: 80px;">效力级别：</td>
                        <td style="width: 200px;" name="submitlawLevel" id="submitlawLevel">
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                        <td class="power-table-label font-bold-label-td" style="text-align:right;width: 80px;">备注：</td>
                        <td colspan="3" name="remark" id="remark">
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                        <td class="power-table-label font-bold-label-td" style="text-align:right;" >法律原文：</td>
                        <td colspan="3" class="TableData" >
 							<div id="attachDiv"></div>
							<div id="fileContainer2"></div>
							<div id="renderContainer2"></div> 
							<input id="attaches" name="attaches" type="hidden"/>
 						 </td>
                    </tr>
                    </table>
          </form>
        </div>
        <div title="法律条文" style="height:400px;padding:10px">
          <div class="easyui-panel" style="width:100%;height:100%;" data-options="border:false">
        	<table id="datagrid" fit="true">
			</table>
<!-- 		    <div style="clear:both"></div> -->
<!-- 		    </div> -->
			</div>
   </div>
<script type="text/javascript" src="<%=contextPath%>/supervise/lawList/js/law_look.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/md5.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>
</body>
</html>