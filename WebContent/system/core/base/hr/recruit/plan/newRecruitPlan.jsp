<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String model = TeeAttachmentModelKeys.RECRUIT_PLAN;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<%@include file="/header/upload.jsp"%>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<title>新建招聘计划</title>
<script type="text/javascript">

function doInit(){
	//初始化fck
/* 	CKEDITOR.replace('requRequires',{
		width : 'auto',
		height:300
	}); */
	//初始化附件
	new TeeSWFUpload({
		fileContainer:"fileContainer",//文件列表容器
		uploadHolder:"uploadHolder",//上传按钮放置容器
		valuesHolder:"valuesHolder",//附件主键返回值容器，是个input
		queueComplele:function(){//队列上传成功回调函数，可有可无
			
		},
		renderFiles:true,//渲染附件
		post_params:{model:"<%=model%>"}//后台传入值，model为模块标志
	});
	
}

function checkForm(){
	var check = $("#form1").form('validate'); 
	if(!check){
		return false; 
	}
	if($("#planDitch").val() == ""){
		alert("招聘渠道不能为空！");
		return false; 
	}
	return true;
}


function doSaveOrUpdate(callback){
	if(checkForm()){
	    var url = "<%=contextPath %>/recruitPlanController/addOrUpdate.action";
	    var para =  tools.formToJson($("#form1"));
	    var jsonRs = tools.requestJsonRs(url,para);
	    if(jsonRs.rtState){
	      callback();
	      /* setTimeout(function(){
	        //刷新父页面
	        parent.location.reload();
			//return true;
	      },1800); */
	    }else{
	      alert(jsonRs.rtMsg);
	    }
	}
}


</script>
</head>
<body onload="doInit()" >
<form action=""  method="post" name="form1" id="form1">
<input type="hidden" name="sid" id="sid" value="">
<table align="center" width="96%" class="TableBlock" >
	<tr>
		<td nowrap class="TableData"  width="15%;" >计划编号：<font style='color:red'>*</font></td>
		<td class="TableData" width="35%;" >
			<input type="text" name="planNo" id="planNo" size="" class="BigInput  easyui-validatebox" required="true" value="">
		</td>
		<td nowrap class="TableData"  width="15%;" >计划名称：<font style='color:red'>*</font></td>
		<td class="TableData" width="60%;" >
			<input type="text" name="planName" id="planName" size="" class="BigInput  easyui-validatebox" required="true" value="">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="15%;" >招聘渠道：<font style='color:red'>*</font></td>
		<td class="TableData" width="35%;" >
			<select id="planDitch" name="planDitch" class="BigSelect">
				<option value="">请选择</option>
				<option value="01">网络招聘</option>
				<option value="02">招聘会招聘</option>
				<option value="03">人才猎头推荐</option>
			</select>
		</td>
		<td nowrap class="TableData"  width="15%;" >预算费用：<font style='color:red'>*</font></td>
		<td class="TableData" width="60%;" >
			<input type="text" name="planCost" id="planCost" size="9"  maxlength ="9" class="BigInput  easyui-validatebox" required="true" value="" validType ='pointTwoNumber[]'>元
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="15%;" >开始日期：<font style='color:red'>*</font></td>
		<td class="TableData" width="35%;" >
			<input type="text" name="startDateStr" id="startDateStr" size="" class="BigInput  easyui-validatebox" onClick="WdatePicker({maxDate:'#F{$dp.$D(\'endDateStr\',{d:0});}',dateFmt:'yyyy-MM-dd'})" required="true" value="">
		</td>
		<td nowrap class="TableData"  width="15%;" >结束日期：<font style='color:red'>*</font></td>
		<td class="TableData" width="60%;" >
			<input type="text" name="endDateStr" id="endDateStr" size="" class="BigInput  easyui-validatebox" onClick="WdatePicker({minDate:'#F{$dp.$D(\'startDateStr\',{d:0});}',dateFmt:'yyyy-MM-dd'})" required="true" value="">
		</td>
		
				
	</tr>
	<tr>
		<td nowrap class="TableData"  width="15%;" >招聘人数：<font style='color:red'>*</font></td>
		<td class="TableData" width="35%;" >
			<input type="text" name="planRecrNum" id="planRecrNum" size="" maxlength="9" class="BigInput easyui-validatebox"  required="true"   validType ='positivIntege[]'>（人）
		</td>
		<td nowrap class="TableData"  width="15%;" >审批人：<!-- <font style='color:red'>*</font> --></td>
		<td class="TableData" width="60%;" >
			<input type=hidden name="approvePersonId" id="approvePersonId" value="">
			<input  name="approvePersonName" id="approvePersonName" class="BigStatic BigInput" size="10"  readonly value=""></input>
			<span>
				<a href="javascript:void(0);" class="orgAdd" onClick="selectSingleUser(['approvePersonId', 'approvePersonName']);">添加</a>
				<a href="javascript:void(0);" class="orgClear" onClick="$('#approvePersonId').val('');$('#approvePersonName').val('');">清空</a>
			</span>
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="">招聘说明：</td>
		<td class="TableData" width="" colspan="3">
			<textarea rows="4" cols="80" id="recruitDescription" name="recruitDescription" maxlength="200" ></textarea>
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="">招聘备注：</td>
		<td class="TableData" width="" colspan="3">
			<textarea rows="4" cols="80" id="recruitRemark" name="recruitRemark" maxlength="200" ></textarea>
		</td>
	</tr>
	
	<tr>
		<td nowrap class="TableData" width="" >附件文档：</td>
		<td class="TableData"  colspan="3">
			<div id="fileContainer"></div> 
		</td>
	</tr>
	
	<tr>
		<td nowrap class="TableData" width="" >附件上传：</td>
		<td class="TableData"  colspan="3">
			
			<a id="uploadHolder"class="add_swfupload"><img src="<%=systemImagePath %>/upload/batch_upload.png"/>批量上传</a> 
			<input id="attacheIds" name="attacheIds" type="hidden" />
		</td>
	</tr>
	
</table>
</form>

</body>
</html>