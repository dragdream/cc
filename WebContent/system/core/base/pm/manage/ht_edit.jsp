<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%

	String sid = request.getParameter("sid");
	String renew = request.getParameter("renew")==null?"0":request.getParameter("renew");
	String model = TeeAttachmentModelKeys.hrContract;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<%@ include file="/header/upload.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/js/hr.js"></script>
<script>
var sid='<%=sid%>';
function doInit(){
	doInitUpload();
	getHrCodeByParentCodeNo("PM_CONTRACT_TYPE" , "conType");
	getHrCodeByParentCodeNo("PM_CONTRACT_ATTR" , "conAttr");
	getHrCodeByParentCodeNo("PM_CONTRACT_STATUS" , "conStatus");
	
	
	
	var url = contextPath+"/TeeHumanContractController/getModelById.action?sid="+sid+"&renew=<%=renew%>";
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

function commit(){
	if($("#form1").form("validate") && checkForm()){
		var param = tools.formToJson($("#form1"));
		var url = contextPath+"/TeeHumanContractController/updateHumanContract.action";
		if(<%=renew%>=='1'){
			 url = contextPath+"/TeeHumanContractController/addHumanContract.action";
		}
		var json = tools.requestJsonRs(url,param);
		if(json.rtState){
			top.$.jBox.tip(json.rtMsg,"success");
			return true;
		}
		top.$.jBox.tip(json.rtMsg,"error");
		return false;
	}
}

function checkForm(){
	  if($("#validTimeDesc").val() && $("#endTimeDesc").val()){
		    if(document.getElementById("validTimeDesc").value > document.getElementById("endTimeDesc").value){
		      alert("合同生效时间不能大于结束时间！");
		      //$("#validTimeDesc").focus();
		      //$("#validTimeDesc").select();
		      return false;
			 }
	  }
	return true;
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
	<table  style="width:100%;font-size:12px" class="TableBlock">
		<tr class='TableData'>
			<td>
				<b>合同标题：</b>
			</td>
			<td>
				<input type="text"  id="conTitle" name="conTitle" required="true" class="easyui-validatebox BigInput"/>
			</td>
			<td>
				<b>合同编号：</b>
			</td>
			<td>
				<input type="text" class="BigInput"  id="conCode" name="conCode"/>
			</td>
		</tr>
		<tr class='TableData'>
			<td>
				<b>合同类型：</b>
			</td>
			<td>
				<select class="BigSelect" id="conType" name="conType" >
				</select>
			</td>
			<td>
				<b>合同属性：</b>
			</td>
			<td>
				<select class="BigSelect" id="conAttr" name="conAttr">
				</select>
			</td>
		</tr>
		<tr class='TableData'>
			<td>
				<b>合同状态：</b>
			</td>
			<td colspan="3">
				<select class="BigSelect" id="conStatus" name="conStatus">
				</select>
			</td>
		</tr>
		<tr class='TableData'>
			<td>
				<b>合同生效时间：</b>
			</td>
			<td>
				<input type="text" id='validTimeDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='validTimeDesc' class="Wdate BigInput" />
			</td>
			<td>
				<b>合同结束时间：</b>
			</td>
			<td>
				<input type="text" id='endTimeDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='endTimeDesc' class="Wdate BigInput" />
			</td>
		</tr>
		<tr class='TableData'>
			<td>
				<b>合同解除时间：</b>
			</td>
			<td  colspan="3">
				<input type="text" id='invalidTimeDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='invalidTimeDesc' class="Wdate BigInput" />
			</td>
		</tr>
		<tr class='TableData'>
			<td>
				<b>签约次数：</b>
			</td>
			<td colspan="3">
				<input type="text" class="BigInput easyui-validatebox" validType='intege[]' id="signCount" name="signCount"/>
			</td>
		</tr>
		<!-- <tr class='TableData'>
			<td>
				<b>续签日期：</b>
			</td>
			<td colspan="3">
				<input type="text" id='renewDateDesc' name='renewDateDesc' class="BigInput readonly" readonly="readonly" />
			</td>
		</tr> -->
		<tr class='TableData'>
			<td>
				<b>最后提醒时间：</b>
			</td>
			<td colspan="3">
				<input type="text" id='lastRemindDateDesc'  name='lastRemindDateDesc' class=" BigInput readonly" readonly="readonly"/>
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
							<img src="<%=systemImagePath %>/upload/batch_upload.png"/>添加附件
						</a>
						<input id="attachmentSidStr" name="attachmentSidStr" type="hidden"/>
		      		</div>
			</td>
		</tr>
		<tr class='TableData'>
			<td>
				<b>备注：</b>
			</td>
			<td colspan="3">
				<textarea style="width:410px;height:100px" class="BigTextarea" id="remark" name="remark"></textarea>
			</td>
		</tr>
	</table>
	<input type="hidden" class="BigInput" id="humanDocSid" name="humanDocSid" />
	<input type="hidden" class="BigInput" id="sid" name="sid" value="<%=sid%>"/>
</form>
</body>
</html>