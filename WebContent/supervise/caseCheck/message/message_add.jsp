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
		<div class="easyui-panel" title="评分细则" style="width: 100%;" align="center">
                <input type="hidden" id="id" name = "id" value=""/>
                <table class="TableBlock" style="width: 100%; background: #fff;">
                    <tr style="border:none!important;">
                        <td class="power-table-label" style="text-align:right;">资料类型<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td colspan="2">
							<input name="messageType" id="messageType" prompt="请选择资料类型" style="width: 95%;" class="easyui-textbox" data-options="novalidate:true,required:true, missingMessage:'请选择资料类型',editable:false,panelHeight:'auto'" />
		    			</td>
                    </tr>
                    <tr style="border:none!important;">
                    	<td class="power-table-label" style="text-align:right;width:100px;">上传文件&nbsp;&nbsp;：</td>
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
 						<td colspan="2"><span class="tip">请上传Word、Excel或者pdf文档</span></td>
                    </tr>
                    </table>
        </div>
    </form>
<script type="text/javascript" src="<%=contextPath%>/supervise/caseCheck/message/js/message_add.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/md5.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>

</body>
</html>