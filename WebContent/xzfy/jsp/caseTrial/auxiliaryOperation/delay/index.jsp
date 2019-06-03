<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/upload.jsp" %>
<link rel="stylesheet" type="text/css" href="/xzfy/css/common/table.css" />
<link rel="stylesheet" type="text/css" href="/xzfy/css/common/supervise.css" />
<!-- 中腾按钮框架 -->
<link rel="stylesheet" type="text/css" href="/common/zt_webframe/css/package1.css" />
<%
	String caseId = request.getParameter("caseId");
%>

<title>延期</title>
<style> 
	textarea.textstyle {
		font-size: 13px;
	    color: #555555;
	    /* border: 1px solid #C0BBB4; */
    	border: 1px solid #cccccc;
    	border-radius: 3px;
	}
</style>

</head>
<body style="padding-left: 10px;padding-right: 10px;" onload="doInit();">
<!-- 菜单栏 -->

<div class="base_layout_top" style="position:static">
    <img class = 'fl' style="margin-right: 10px; margin-top: 3px" src="<%=contextPath %>/common/zt_webframe/imgs/hygl/wdhy/我的会议.png">
	<span class="title" style="padding-top: 4px;">延期</span>
	<span style="float:right">
		<input type="button" value="延期审批" class="btn-win-white" onclick="bqsp()" style="margin-right:10px;">
		<input type="button" value="生成延期通知书" class="btn-win-white" onclick="noticeBook()" style="margin-right:10px;">
		<input type="button" value="送达回证" class="btn-win-white" onclick="receipt()" style="margin-right:10px;">
		<input type="button" value="提交" class="btn-win-white" onclick="save()" style="margin-right:10px;">
		<input type="button" value="完成" class="btn-win-white" onclick="done()" style="margin-right:10px;">
	</span>
</div>

<form  method="post" name="form1" id="form1" >
<input type="hidden" id="caseId" name="caseId" value="<%=caseId %>">
<input type="hidden" id="id" name="id" value="<%=caseId %>">
<div class="easyui-panel" title="案件信息" style="width: 100%;" align="center" id="baseDiv">
<table align="center" width="100%" class="TableBlock_page">
	<tr calss="common-tr-border">
		<td nowrap class="case-common-filing-td-class1"  width="15%;" >申请人信息：</td>
		<td colspan="5" class="case-common-filing-td-class2">
			<textarea class="textstyle" style="height:50px;width:98%" data-options="multiline:true" name="" id="" disabled></textarea>
		</td>
	</tr>
	<tr calss="common-tr-border">
		<td nowrap class="case-common-filing-td-class1"  width="15%;" >被申请人信息：</td>
		<td colspan="5" class="case-common-filing-td-class2">
			<textarea class="textstyle" style="height:50px;width:98%" data-options="multiline:true" name="respondentName" id="respondentName" disabled></textarea>
		</td>
	</tr>
	<tr calss="common-tr-border">
		<td nowrap class="case-common-filing-td-class1"  width="15%;" >具体行政行为：</td>
		<td colspan="5" class="case-common-filing-td-class2">
			<textarea class="textstyle" style="height:50px;width:98%" data-options="multiline:true" name="specificAdministrativeDetail" id="specificAdministrativeDetail" disabled></textarea>
		</td>
	</tr>
	<tr calss="common-tr-border">
		<td nowrap class="case-common-filing-td-class1"  width="15%;" >行政复议请求：</td>
		<td colspan="5" class="case-common-filing-td-class2">
			<textarea class="textstyle" style="height:50px;width:98%" data-options="multiline:true" name="requestForReconsideration" id="requestForReconsideration" disabled></textarea>
		</td>
	</tr>
	<tr calss="common-tr-border">
		<td nowrap class="case-common-filing-td-class1" >延期日期：</td>
		<td colspan="1" style="padding-left:30px">
			从：<input type="text" onChange="getDelayDays();" name="caseSubRestoreStartTime" id="caseSubRestoreStartTime" class="BigInput easyui-validatebox" data-options="required:true,missingMessage:'请选择延期开始时间' "  style="height:25px;width: 180px;" maxlength="100" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"  readonly>
		</td>
		<td colspan="1" class="">
			到：<input type="text" onChange="getDelayDays();" name="caseSubRestoreEndTime" id="caseSubRestoreEndTime" class="BigInput easyui-validatebox" data-options="required:true,missingMessage:'请选择延期结束时间' "  style="height:25px;width: 180px;" maxlength="100" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'caseSubRestoreStartTime\',{d:0});}',dateFmt:'yyyy-MM-dd'})"  readonly>
		</td>
		<td class="">
			共计：<input type="text" id="delayDays" name="delayDays" class="BigInput" readonly style="width:30px" value="30">天
		</td>
	</tr>
	<tr calss="common-tr-border">
		<td nowrap class="case-common-filing-td-class1" >延期理由：</td>
		<td colspan="5" class="case-common-filing-td-class2">
			<textarea class="textstyle" style="height:60px;width:98%" data-options="multiline:true" name="caseSubExtensionReason" id="caseSubExtensionReason"></textarea>
		</td>
	</tr>
	<!-- 这两行是文件上传和回显必须用到的tr -->
	<tr class="common-tr-border">
        <td class="case-common-filing-td-class1">上传材料&nbsp;：</td>
        <td class="case-common-filing-td-class2">
            <div id="fileContainerIntroduce" style="display:none"></div> 
            <a id="uploadHolderIntroduce" class="add_swfupload" href="javascript: void(0);">
                <img src="<%=systemImagePath%>/upload/batch_upload.png" />上传附件
            </a> 
            <input id="attachmentSidStrIntroduce" name="attachmentSidStrIntroduce" type="hidden" />
        </td>
        <td colspan="2" id="filingApprovalDocument"></td>
    </tr>
    <tr class="common-tr-border">
        <td class="case-common-filing-td-class1"></td>
        <td class="" colspan="3" id="fyDocumentName"></td>
        <td class="case-common-filing-look-td-class3"></td>
    </tr>
</table>
</div>
</form>
<script type="text/javascript" src="<%=contextPath %>/xzfy/js/common/fileupload.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/xzfy/js/jquery.tips.js"></script>
<script type="text/javascript" src="<%=contextPath %>/xzfy/js/caseTrial/auxiliaryOperation/delay/delay.js"></script>
<script type="text/javascript">

//计算延期总时长
function getDelayDays(){
	//获得开始日期
	debugger
	var startTime = $("#caseSubRestoreStartTime").val();
	startTime = startTime.getTime();
	//获得结束日期
	var endTime = $("#caseSubRestoreEndTime").val();
	endTime = endTime.getTime();
	//比较日期大小
	var day = (endTime - startTime) / (1000 * 60 * 60 * 24);
	alert(day);
}
</script>
</body>
</html>