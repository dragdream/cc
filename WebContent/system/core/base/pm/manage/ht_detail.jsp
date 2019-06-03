<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%

	String sid = request.getParameter("sid");
	String model = TeeAttachmentModelKeys.hrContract;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<%@ include file="/header/upload.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" ></meta>
<link rel="stylesheet" href="<%=contextPath%>/system/core/base/pm/css/style.css" type="text/css">
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>

<script>
var sid='<%=sid%>';
function doInit(){
	doInitUpload();
	var url = contextPath+"/TeeHumanContractController/getModelById.action?sid="+sid+"&type=1";
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		bindJsonObj2Cntrl(json.rtData);
		if(json.rtData.attachMentModel.length>0){
			$("#attachTr").show();
			var attaches = json.rtData.attachMentModel;
			for(var i=0;i<attaches.length;i++){
				var fileItem = tools.getAttachElement(attaches[i]);
				$("#attachments").append(fileItem);
			}
		}
	}else{
		alert(json.rtMsg);
	}
}

/**
 * 初始化附件上传
 */
function doInitUpload(){
	//多附件快速上传
	swfUploadObj = new TeeSWFUpload({
		fileContainer:"fileContainer2",//文件列表容器
		uploadHolder:"uploadHolder2",//上传按钮放置容器
		valuesHolder:"attachmentSidStr",//附件主键返回值容器，是个input
		quickUpload:true,//快速上传
		showUploadBtn:false,//不显示上传按钮
		queueComplele:function(){//队列上传成功回调函数，可有可无
			
		},
		renderFiles:true,//渲染附件
		post_params:{model:"<%=model%>"}//后台传入值，model为模块标志
	});
}

</script>

</head>
<body onload="doInit()" >
<form id="form1" name="form1">
	<table  style="width:100%;font-size:12px">
		<tr>
			<td>
				<b>合同标题：</b>
			</td>
			<td>
				<div id="conTitle" ></div>
			</td>
			<td>
				<b>合同编号：</b>
			</td>
			<td>
				<div  id="conCode"></div>
			</td>
		</tr>
		<tr>
			<td>
				<b>合同类型：</b>
			</td>
			<td>
				<div id="conTypeDesc"  >
				</div>
			</td>
			<td>
				<b>合同属性：</b>
			</td>
			<td>
				<div id="conAttrDesc">
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<b>合同状态：</b>
			</td>
			<td colspan="3">
				<div  id="conStatusDesc">
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<b>合同生效时间：</b>
			</td>
			<td>
				<div id='validTimeDesc'></div>
			</td>
			<td>
				<b>合同结束时间：</b>
			</td>
			<td>
				<div  id='endTimeDesc' ></div>
			</td>
		</tr>
		<tr>
			<td>
				<b>合同解除时间：</b>
			</td>
			<td  colspan="3">
				<div id='invalidTimeDesc' ></div>
			</td>
		</tr>
		<tr>
			<td>
				<b>签约次数：</b>
			</td>
			<td colspan="3">
				<div id="signCount" ></div>
			</td>
		</tr>
		<tr class='TableData'>
			<td>
				<b>续签日期：</b>
			</td>
			<td colspan="3">
				<span id='renewDateDesc' name='renewDateDesc' ></span>
			</td>
		</tr>
		<tr class='TableData'>
			<td>
				<b>最后提醒时间：</b>
			</td>
			<td colspan="3">
				<span id='lastRemindDateDesc'  name='lastRemindDateDesc' ></span>
			</td>
		</tr>
		<tr class='TableData'>
			<td>
				<b>相关附件：</b>
			</td>
			<td colspan="3">
				<div style="min-height:50px;">
		      			<span id="attachments"></span>
			      		<div id="fileContainer2"></div>
						<a id="uploadHolder2" class="add_swfupload">
						</a>
						<input id="attachmentSidStr" name="attachmentSidStr" type="hidden"/>
		      		</div>
			</td>
		</tr>
		<tr>
			<td>
				<b>备注：</b>
			</td>
			<td colspan="3">
				<div id="remark" style="width:425px;height:100px" ></div>
			</td>
		</tr>
	</table>
</form>
</body>
</html>