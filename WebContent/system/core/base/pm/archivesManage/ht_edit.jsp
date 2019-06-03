<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%
	String sid = request.getParameter("sid");
    String humanDocSid=request.getParameter("humanDocSid");
    String personName = request.getParameter("personName");
	String renew = request.getParameter("renew")==null?"0":request.getParameter("renew");
	String model = TeeAttachmentModelKeys.hrContract;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<%@ include file="/header/upload.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/js/hr.js"></script>
<script>
var sid='<%=sid%>';
var humanDocSid='<%=humanDocSid%>';
var personName='<%=personName%>';
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
		$.MsgBox.Alert_auto(json.rtMsg);
	}
}

function commit(callback){
	if($("#form1").valid()&& checkForm()){
		var param = tools.formToJson($("#form1"));
		var url = contextPath+"/TeeHumanContractController/updateHumanContract.action";
		if(<%=renew%>=='1'){
			 url = contextPath+"/TeeHumanContractController/addHumanContract.action";
		}
		var json = tools.requestJsonRs(url,param);
		if(json.rtState){
			callback(json.rtState);
		}else{
			$.MsgBox.Alert_auto(json.rtMsg);
		}
		
	}
}

function checkForm(){
	if($("#conTitle").val()=="" || $("#conTitle").val()=="null" || $("#conTitle").val()==null){
		$.MsgBox.Alert_auto("请输入合同标题！");
		return false;
	}
	if($("#validTimeDesc").val()=="" || $("#validTimeDesc").val()=="null" || $("#validTimeDesc").val()==null){
		$.MsgBox.Alert_auto("请输入合同生效时间！");
		return false;
	}
	if($("#endTimeDesc").val()=="" || $("#endTimeDesc").val()=="null" || $("#endTimeDesc").val()==null){
		$.MsgBox.Alert_auto("请输入合同结束时间！");
		return false;
	}
	  if($("#validTimeDesc").val() && $("#endTimeDesc").val()){
		    if(document.getElementById("validTimeDesc").value > document.getElementById("endTimeDesc").value){
		    	$.MsgBox.Alert_auto("合同生效时间不能大于结束时间！");
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
<style>
html{
   background-color: #f2f2f2;
}
	td{
		line-height:28px;
		min-height:28px;
	}
.ztree{
		background:white;
		border:1px solid #f0f0f0;
	}
</style>

</head>
<body onload="doInit();" style="padding-left: 10px;padding-right: 10px;background-color: #f2f2f2;">
<div class="topbar clearfix" id="toolbar">
   <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/core/base/pm/img/icon_dabj.png">
		<span class="title">新增/编辑  <%= personName %> 的合同</span>
	</div>
</div>
<form id="form1" name="form1">
	<table class="TableBlock" width="100%" align="center">
	    <tr>
		   <td class=TableHeader colSpan=2 noWrap>
		       <img src="<%=contextPath %>/common/zt_webframe/imgs/rsdagl/dagl/icon_fenzu.png" alt="" style="vertical-align:middle;"/>
		       <B style="color: #0050aa">基本信息</B>
		   </td>
	    </tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				合同标题<span style="color:red;font-weight:bold;">*</span>：
			</td>
			<td>
				<input style="height: 23px;width: 350px;"  type="text"   id="conTitle" name="conTitle" required/>
			</td>
			</tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				合同编号：
			</td>
			<td>
				<input  style="height: 23px;width: 350px;"  type="text" class="BigInput"  id="conCode" name="conCode"/>
			</td>
		</tr>
		<tr class='TableData' align="left">
			<td class="TableData" width="150" style="text-indent:15px">
				合同类型
			</td>
			<td>
				<select  style="height: 23px;width: 200px;"  class="BigSelect" id="conType" name="conType" >
				</select>
			</td>
			</tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				合同属性：
			</td>
			<td>
				<select  style="height: 23px;width: 200px;"  class="BigSelect" id="conAttr" name="conAttr">
				</select>
			</td>
		</tr>
		<tr class='TableData' align="left">
			<td class="TableData" width="150" style="text-indent:15px">
				合同状态：
			</td>
			<td>
				<select  style="height: 23px;width: 200px;"  class="BigSelect" id="conStatus" name="conStatus">
				</select>
			</td>
		</tr>
		<tr class='TableData' align="left">
			<td class="TableData" width="150" style="text-indent:15px">
				合同生效时间<span style="color:red;font-weight:bold;">*</span>：
			</td>
			<td>
				<input style="height: 23px;width: 200px;" type="text" id='validTimeDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='validTimeDesc' class="Wdate BigInput" />
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				合同结束时间 <span style="color:red;font-weight:bold;">*</span>：
			</td>
			<td>
				<input  style="height: 23px;width: 200px;"  type="text" id='endTimeDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='endTimeDesc' class="Wdate BigInput" />
			</td>
		</tr>
		<tr class='TableData' align="left">
			<td  class="TableData" width="150" style="text-indent:15px">
				合同解除时间：
			</td>
			<td>
				<input  style="height: 23px;width: 200px;"  type="text" id='invalidTimeDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='invalidTimeDesc' class="Wdate BigInput" />
			</td>
		</tr>
		<tr class='TableData' align="left">
			<td  class="TableData" width="150" style="text-indent:15px">
				签约次数：
			</td>
			<td>
				<input style="height: 23px;width: 350px;" positive_integer='true'  type="text" class="BigInput" positive_integer='true' id="signCount" name="signCount"/>
			</td>
		</tr>
<!-- 	<tr class='TableData' align="left">
			<td  class="TableData" width="150" style="text-indent:15px">
				续签日期：
			</td>
			<td>
				<input style="height: 23px;width: 200px;"  type="text" id='renewDateDesc' name='renewDateDesc' class="BigInput readonly" readonly="readonly"/>
			</td>
		</tr> --> 
		<tr class='TableData' align="left">
			<td class="TableData" width="150" style="text-indent:15px">
				最后提醒时间：
			</td>
			<td>
				<input  style="height: 23px;width: 200px;"  type="text" id='lastRemindDateDesc' name='lastRemindDateDesc' class="BigInput readonly" readonly="readonly"/>
			</td>
		</tr>
		<tr class='TableData' align="left">
			<td  class="TableData" width="150" style="text-indent:15px">
				相关附件：
			</td>
			<td>
				<div style="min-height:40px;">
		      			<span id="attachments"></span>
			      		<div id="fileContainer2"></div>
						<a id="uploadHolder2" class="add_swfupload">
							<img src="<%=systemImagePath %>/upload/batch_upload.png"/>添加附件
						</a>
						<input id="attachmentSidStr" name="attachmentSidStr" type="hidden"/>
		      		</div>
			</td>
		</tr>
		<tr class='TableData' align="left">
			<td  class="TableData" width="150" style="text-indent:15px">
				备注：
			</td>
			<td>
				<textarea style="width:410px;height:100px" class="BigTextarea" id="remark" name="remark"></textarea>
			</td>
		</tr>
	</table>
	<input type="hidden" class="BigInput" id="humanDocSid" name="humanDocSid" />
	<input type="hidden" class="BigInput" id="sid" name="sid" value="<%=sid%>"/>
</form>
</body>
</html>