<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/upload.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" type="text/css" href="/xzfy/css/common/table.css" />
<link rel="stylesheet" type="text/css" href="/xzfy/css/common/supervise.css" />
<!-- 中腾按钮框架 -->
<link rel="stylesheet" type="text/css" href="/common/zt_webframe/css/package1.css" />
<%
	String caseId = request.getParameter("caseId");
	String id = UUID.randomUUID().toString();
	id = id.replace("-", "");
%>

<title>调查管理</title>
<style> 
	
</style>

</head>
<body style="padding-left: 10px;padding-right: 10px;" onload="documentCtralInit('trial_investigation');">
<!-- 菜单栏 -->

<div class="base_layout_top" style="position:static">
    <img class = 'fl' style="margin-right: 10px; margin-top: 3px" src="<%=contextPath %>/common/zt_webframe/imgs/hygl/wdhy/我的会议.png">
	<span class="title" style="padding-top: 4px;">
		<input type="button" value="生成委托调查函" class="btn-win-white" onclick="entrust();" style="margin-right:10px;width:110px">
		<input type="button" value="生成调查笔录" class="btn-win-white" onclick="record();" style="margin-right:10px;width:90px">
	</span>
	<span style="float:right">
		<input type="button" value="新增" class="btn-win-white" onclick="toAdd()" style="margin-right:10px;">
		<input type="button" value="保存" class="btn-win-white" onclick="save()" style="margin-right:10px;">
		<input type="button" value="审批" class="btn-win-white" onclick="report()" style="margin-right:10px;">
	</span>
</div>
<!-- 这是列表页 -->	
<div class="easyui-panel case-common-panel-body1" title="调查管理列表" style="width: 100%; margin-bottom: 10px; height: 180px!important;" id="common_case_add_person_div"
         align="center" data-options="tools:'#common_case_add_person'">
	<table class="TableBlock" style="height:300px" id="datagrid"></table>
</div>
<form  method="post" name="form1" id="form1" >
<input type="hidden" id="caseId" name="caseId" value="<%=caseId %>">
<input type="hidden" id="id" name="id" value="<%=id %>">
<div class="easyui-panel" title="基础信息" style="width: 100%;" align="center" id="baseDiv">
	<table align="center" width="100%;height:100%" class="TableBlock_page">
		<tr calss="common-tr-border">
			<td nowrap class="case-common-filing-td-class1" >调查人姓名：<font style='color:red'>*</font></td>
			<td class="case-common-filing-td-class2">
				<input value="" id="investName" name="investName" class="BigInput" title="调查人姓名"/>
			</td>
			<td nowrap class="case-common-filing-td-class1"  >调查时间：<font style='color:red'>*</font></td>
			<td class="TableData" width="35%;" >
				<input type="text" name="startTime" id="startTime" value="" class="BigInput easyui-validatebox" data-options="editable:false,required:true,missingMessage:'请选择调查时间' "  style="height:25px;width: 174px;" maxlength="100" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" title="调查时间">
			</td>
			<td nowrap class="case-common-filing-td-class1"  >调查方式：</td>
			<td class="case-common-filing-td-class2" style="padding-right:20px">
				<select style="width: 174px" name="investType" id="investType" class="BigSelect" title="调查方式">
					<option></option>
				</select>
			</td>
	</tr>
			<tr calss="common-tr-border">
				<td nowrap class="case-common-filing-td-class1"  >
					被调查人姓名：
				</td> 
				<td class="case-common-filing-td-class2" width="35%;" >
					<input style="width: 180px" type="text" name="respondent" id="respondent" size="" class="BigInput  easyui-validatebox" data-options="required:true,missingMessage:'请输入被调查人姓名'"  value="" title="被调查人姓名">
				</td>
				<td nowrap class="case-common-filing-td-class1"  >
					被调查人电话：
				</td> 
				<td class="case-common-filing-td-class2" width="35%;" >
					<input style="width: 174px" type="text" name="respondentPhoneNum" id="respondentPhoneNum" size="" class="BigInput  easyui-validatebox" value="" title="被调查人电话">
				</td>
				<td nowrap class="case-common-filing-td-class1"  >
					被调查人性别：
				</td> 
				<!-- respondentSex -->
				<td class="case-common-filing-td-class2" width="35%;" >
					<input name="respondentSex" type="radio" value="1" />男
					<input name="respondentSex" type="radio" value="0" />女
				</td>
			</tr>
			<tr calss="common-tr-border">
				<td nowrap class="case-common-filing-td-class1"  >
					调查地址：
				</td> 
				<td class="case-common-filing-td-class2" colspan="5">
					<input style="width: 98%" type="text" name="investPlace" id="investPlace" size="" class="BigInput  easyui-validatebox" value="" title="被调查人电话">
				</td>
			</tr>
			<tr>
				<td nowrap class="case-common-filing-td-class1"  >
					告知权利与义务：
				</td> 
				<td class="case-common-filing-td-class2" colspan="5">
					<input style="width: 98%" type="text" name="notice" id="notice"  class="BigInput  easyui-validatebox" value="" title="告知权力与义务">
				</td>
			</tr>
			<tr>
				<td nowrap class="case-common-filing-td-class1"  >
					调 查 情 况：
				</td> 
				<td class="case-common-filing-td-class2" colspan="5">
					<input style="width: 98%" type="text" name="investDetail" id="investDetail"  class="BigInput  easyui-validatebox" value="" title="调查情况">
				</td>
			</tr>
			<tr>
				<td nowrap class="case-common-filing-td-class1"  >
					审查意见：
				</td> 
				<td class="case-common-filing-td-class2" colspan="5">
					<input style="width: 98%" type="text" name="result" id="result"  class="BigInput  easyui-validatebox" value="" title="审查意见">
				</td>
			</tr>
			<!-- 这两行是文件上传和回显必须用到的tr -->
			<tr class="common-tr-border">
                <td class="case-common-filing-td-class1">证明材料&nbsp;：</td>
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
<script type="text/javascript" src="<%=contextPath%>/xzfy/js/jquery.tips.js"></script>
<script type="text/javascript" src="<%=contextPath %>/xzfy/js/caseTrial/investigation/investigation.js"></script>
<script type="text/javascript">

</script>
</body>
</html>