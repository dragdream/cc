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
	String id = UUID.randomUUID().toString();
	id = id.replace("-", "");
%>

<title>听证管理</title>
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
<body style="padding-left: 10px;padding-right: 10px;" onload="documentCtralInit('trial_casehearing');">
<!-- 菜单栏 -->

<div class="base_layout_top" style="position:static">
    <img class = 'fl' style="margin-right: 10px; margin-top: 3px" src="<%=contextPath %>/common/zt_webframe/imgs/hygl/wdhy/我的会议.png">
	<span class="title" style="padding-top: 4px;">
		<input type="button" value="生成听证笔录" class="btn-win-white" onclick="record()" style="margin-right:10px;width:100px">
	</span>
	<span style="float:right">
		<input type="button" value="新增" class="btn-win-white" onclick="toAdd()" style="margin-right:10px;">
		<input type="button" value="保存" class="btn-win-white" onclick="save()" style="margin-right:10px;">
		<input type="button" value="审批" class="btn-win-white" onclick="report()" style="margin-right:10px;">
	</span>
</div>
<!-- 这是列表页 -->	
<div class="easyui-panel case-common-panel-body1" title="听证管理列表" style="width: 100%; margin-bottom: 10px; height: 180px!important;" id="common_case_add_person_div"
         align="center" data-options="tools:'#common_case_add_person'">
	<table class="TableBlock" style="height:300px" id="datagrid"></table>
</div>
<form  method="post" name="form1" id="form1" >
<input type="hidden" id="caseId" name="caseId" value="<%=caseId %>">
<input type="hidden" id="id" name="id" value="<%=id %>">
<div class="easyui-panel" title="基础信息" style="width: 100%;" align="center" id="baseDiv">
	<table align="center" width="100%" class="TableBlock_page">
				<tr calss="common-tr-border">
					<td nowrap class="case-common-filing-td-class1"  >听证人：</td>
					<td class="case-common-filing-td-class2" width="35%;" >
						<input value="" id="hearingWitness" name="hearingWitness" class="BigInput"  title="听证人"/>
					</td>
					<td nowrap class="case-common-filing-td-class1"  >主持人：</td>
					<td class="case-common-filing-td-class2" width="35%;" >
						<input type="text" name="presenter" id="presenter" value="" class="BigInput" style="height:25px;width: 174px;" maxlength="100" title="主持人">
					</td>
					<td nowrap class="case-common-filing-td-class1" >开始时间：<font style='color:red'>*</font></td>
					<td class="case-common-filing-td-class2" style="padding-right:20px">
						<input type="text" name="startTime" id="startTime" value="" class=" BigInput easyui-validatebox" data-options="required:true,missingMessage:'请选择开始时间' "  style="height:25px;width: 175px;" maxlength="100" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" title="开始时间">
					</td>
				</tr>
				<tr calss="common-tr-border">
					<td nowrap class="case-common-filing-td-class1"  >结束时间：</td>
					<td class="case-common-filing-td-class2" width="35%;" >
						<input type="text" name="endTime" id="endTime" value="" class=" BigInput" data-options="editable:false," style="height:25px;width: 180px;" maxlength="100" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startTime\',{d:0});}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"  title="结束时间">
					</td>
					<td nowrap class="case-common-filing-td-class1"  >记录人：</td>
					<td class="case-common-filing-td-class2" >
						<input value="" id="clerk" name="clerk" class="BigInput" style="width:175px" title="记录人"/>
					</td>
					<td nowrap class="case-common-filing-td-class1" >听证地点：<font style='color:red'>*</font></td>
					<td class="case-common-filing-td-class2" style="padding-right:20px">
						<input value="" id="place" name="place" class="BigInput easyui-validatebox" data-options="required:true,missingMessage:'请输入听证地点' " title="听证地点" style="height:25px;width: 174px;"/>
					</td>
				</tr>
				<tr calss="common-tr-border">
					<td nowrap class="case-common-filing-td-class1"  >参加人名：</td>
					<td class="case-common-filing-td-class2" width="35%;" >
						<input value="" id="participant" name="participant" class="BigInput" title="参加人名"/>
					</td>
					<td nowrap class="case-common-filing-td-class1"  >告知权利与义务：</td>
					<td class="case-common-filing-td-class2" width="35%;" >
						<input type="text" name="notice" id="notice" value="" class="BigInput" style="height:25px;width: 174px;" maxlength="100" title="告知权利与义务">
					</td>
					<td nowrap class="case-common-filing-td-class1"  >是否申请回避：</td>
					<td class="case-common-filing-td-class2">
						<input type="radio" name="avoid" value="1">是
						<input type="radio" name="avoid" value="0">否
					</td>
				</tr>
				<tr calss="common-tr-border">
					<td nowrap class="case-common-filing-td-class1"  >被申请人阐述：</td>
					<td colspan="5" class="case-common-filing-td-class2">
						<textarea class="textstyle" style="height:40px;width:98%" data-options="multiline:true" name="respondentExposition" id="respondentExposition" placeholder="被申请人阐述"></textarea>
					</td>
				</tr>
				<tr calss="common-tr-border">
					<td nowrap class="case-common-filing-td-class1"  >申请人阐述：</td>
					<td colspan="5" class="case-common-filing-td-class2">
						<textarea class="textstyle" style="height:40px;width:98%" data-options="multiline:true" name="applicantExplanation" id="applicantExplanation" placeholder="申请人阐述"></textarea>
					</td>
				</tr>
				<tr calss="common-tr-border">
					<td nowrap class="case-common-filing-td-class1"  >辩论：</td>
					<td colspan="5" class="case-common-filing-td-class2">
						<textarea class="textstyle" style="height:40px;width:98%" data-options="multiline:true" name="debate" id="debate" placeholder="辩论"></textarea>
					</td>
				</tr>
				<tr calss="common-tr-border">
					<td nowrap class="case-common-filing-td-class1"  >质证：</td>
					<td colspan="5" class="case-common-filing-td-class2">
						<textarea class="textstyle" style="height:40px;width:98%" data-options="multiline:true" name="witness" id="witness" placeholder="质证"></textarea>
					</td>
				</tr>
				<tr calss="common-tr-border">
					<td nowrap class="case-common-filing-td-class1"  >审查意见：</td>
					<td colspan="5" class="case-common-filing-td-class2">
						<textarea class="textstyle" style="height:40px;width:98%" data-options="multiline:true" name="result" id="result" placeholder="审查意见"></textarea>
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
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/xzfy/js/jquery.tips.js"></script>
<script type="text/javascript" src="<%=contextPath %>/xzfy/js/caseTrial/caseHearing/caseHearing.js"></script>
<script type="text/javascript">

</script>
</body>
</html>