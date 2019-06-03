<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String model = TeeAttachmentModelKeys.RECRUIT_REQUIREMENTS;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<%@include file="/header/upload.jsp"%>
<script src="<%=contextPath %>/common/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<title>新建招聘信息</title>
<script type="text/javascript">
var editor;
function doInit(){
	//初始化fck
	editor = CKEDITOR.replace('requRequires',{
		width : 'auto',
		height:200
	});
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
	return true;
}


function doSaveOrUpdate(callback){
	if(checkForm()){
	    var url = "<%=contextPath %>/recruitRequirementsController/addOrUpdate.action";
	    var para =  tools.formToJson($("#form1"));
	    para["requRequires"] = editor.getData();
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
		<td nowrap class="TableData"  width="15%;" >需求编号：<font style='color:red'>*</font></td>
		<td class="TableData" width="35%;" >
			<input type="text" name="requNo" id="requNo" size="" class="BigInput  easyui-validatebox" required="true" value="">
		</td>
		<td nowrap class="TableData"  width="15%;" >需求岗位：<font style='color:red'>*</font></td>
		<td class="TableData" width="60%;" >
			<input type="text" name="requJob" id="requJob" size="" class="BigInput  easyui-validatebox" required="true" value="">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="">用工日期：</td>
		<td class="TableData" width="" >
			<input type="text" name="requTimeStr" id="requTimeStr" size="" class="BigInput  easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" required="true" value="">
		</td>
		<td nowrap class="TableData"  width="">需求人数：</td>
		<td class="TableData" >
			<input type="text" name="requNum" id="requNum" size="" maxlength="9" class="BigInput easyui-validatebox"  required="true"   validType ='positivIntege[]'>（人）
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" width="" >需求部门：</td>
		<td class="TableData"  colspan="3">
			<input type='hidden' id='requDeptStr' name='requDeptStr' />
			<textarea cols='45' name='requDeptStrName' id='requDeptStrName' rows='4' style='overflow-y: auto;' class='SmallStatic BigTextarea' wrap='yes' readonly >
			</textarea>
			<a href='javascript:void(0);' class='orgAdd' onClick="selectDept(['requDeptStr','requDeptStrName'],'1')">选择</a>
			&nbsp;&nbsp;<a href='javascript:void(0);' class='orgClear' onClick="clearData('requDeptStr','requDeptStrName')">清空</a>
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="">备　　注：</td>
		<td class="TableData" width="" colspan="3">
			<textarea rows="3" cols="60" id="remark" name="remark" maxlength="200" ></textarea>
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" width="" >附件文档：</td>
		<td class="TableData"  colspan="3">
		</td>
	</tr>
	
	<tr>
		<td nowrap class="TableData" width="" >附件上传：</td>
		<td class="TableData"  colspan="3">
			<div id="fileContainer"></div> 
			<a id="uploadHolder"class="add_swfupload"><img src="<%=systemImagePath %>/upload/batch_upload.png"/>批量上传</a> 
			<input id="valuesHolder" name="valuesHolder" type="hidden" />
		</td>
	</tr>
	<tr>
		<td class="TableData" width="" colspan="4">
			岗位要求：<font style='color:red'>*</font><br>
			<DIV>
				<textarea  id="requRequires" name="requRequires" class="BigTextarea" required="true" ></textarea>
			</DIV>
		</td>
	</tr>
	
</table>
</form>

</body>
</html>