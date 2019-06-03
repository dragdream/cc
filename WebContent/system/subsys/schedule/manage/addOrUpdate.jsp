<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@page import="java.text.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String scheduleId = request.getParameter("scheduleId");
	Date date = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	int type = TeeStringUtil.getInteger(request.getParameter("type"), 1);
	String model = TeeAttachmentModelKeys.schedule;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header2.0.jsp" %>
<%@include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<%@ include file="/header/upload.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<style>
table td{
vertical-align:top;
padding:5px;
}
</style>
<script>
var scheduleId = "<%=scheduleId%>";
var _type = <%=type%>;
function doInit(){
	doInitUpload();
	if(scheduleId!="" && scheduleId!="null"){
		var json = tools.requestJsonRs(contextPath+"/schedule/get.action",{uuid:scheduleId,type:0});//增加type,用于编辑和详情的区分，对附件权限进行区分
		bindJsonObj2Cntrl(json.rtData);
		if($("#managerUserId").val()=="0"){
			$("#managerUserId").val("");
		}
		if(json.rtData.attachMentModel.length>0){
			$("#attachTr").show();
			var attaches = json.rtData.attachMentModel;
			for(var i=0;i<attaches.length;i++){
				var fileItem = tools.getAttachElement(attaches[i]);
				$("#attachments").append(fileItem);
			}
		}
	}
	changed();
	$("#type").val(_type);
	
	var opts = $("#type").children("option");
	for(var i=0;i<opts.length;i++){
		if(!opts[i].selected){
			$(opts[i]).remove();
		}
	}
}

function changed(){
	var rangeType = $("#rangeType").val();
	var json = tools.requestJsonRs(contextPath+"/schedule/getRangeTypeInfo.action",{rangeType:rangeType,time:$("#crTimeDesc").val()});
	$("#time1Desc").val(json.rtData.time1Desc);
	$("#time2Desc").val(json.rtData.time2Desc);
	$("#rangeDesc").html(json.rtData.rangeDesc);
}

function save(){
	if(!$("#form").valid()){
		return;
	}
	var para = tools.formToJson($("#form"));
	var json;
	if(scheduleId!="" && scheduleId!="null"){
		json = tools.requestJsonRs(contextPath+"/schedule/update.action",para);
	}else{
		json = tools.requestJsonRs(contextPath+"/schedule/save.action",para);
	}
	$.MsgBox.Alert_auto(json.rtMsg);
	if(json.rtState){
		ret();
	}
}

function ret(){
	if(_type==1){
		window.location = "../personal_list.jsp";
	}else if(_type==2){
		window.location = "../department_list.jsp";
	}else if(_type==3){
		window.location = "../company_list.jsp";
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
<style>
#timesTips{
	color:#ffa800;
	font-weight:bold;
	margin-left:5px;
}
.time_info{
	line-height:30px;
	font-weight:bold;
}
.info img,.info span{
	vertical-align:middle;
}
table{
	border-collapse: collapse;
/*     border: 1px solid #f2f2f2; */
    width:100%;
}
table tr{
	line-height:35px;
	border-bottom:1px solid #f2f2f2;
}
table tr td:first-child{
	text-indent:10px;
}
table tr:first-child td{
	/*font-weight:bold;*/
}
table tr:first-child{
	/* background-color: #f8f8f8;  */
}
</style>
</head>
<body onload="doInit();" style="overflow-y:auto;overflow-x:hidden;padding-left: 10px;padding-right: 10px;" >
<div id="time_info">
	<div layout="center" style="overflow:auto">
	<form id="form">
		<table class="none-table" style="font-size:12px;width:100%;">
			<tr class="TableHeader" style="font-size:16px;border-bottom: 2px solid #b0deff;">
				<td colspan="2">
					计划基本信息
				</td>
			</tr>
			<tr>
				<td align="right" class="TableData">
					标题<span style="color:red">*</span>：
				</td>
				<td class="TableData">
					<input type="text" class="BigInput easyui-validatebox" required name="title" style="width:500px;height:25px;"/>
				</td>
			</tr>
			<tr>
				<td align="right" class="TableData">
					计划类型：
				</td>
				<td class="TableData">
					<select class="BigSelect" id="type" name="type">
						<option value="1">个人计划</option>
						<option value="2">部门计划</option>
						<option value="3">公司计划</option>
					</select>
				</td>
			</tr>
			<tr>
				<td align="right" class="TableData">计划周期：</td>
				<td class="TableData">
					<select class="BigSelect" id="rangeType" name="rangeType" onchange="changed()">
						<option value="1">日计划</option>
						<option value="2">周计划</option>
						<option value="3">月计划</option>
						<option value="4">季度计划</option>
						<option value="5">半年计划</option>
						<option value="6">年计划</option>
					</select>
				</td>
			</tr>
			<tr>
				<td align="right" class="TableData">添加时间<span style="color:red">*</span>：</td>
				<td class="TableData">
					<input type="text" class="BigInput easyui-validatebox Wdate" required onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:function(dp){changed()}})" value="<%=sdf.format(date) %>" id="crTimeDesc" name="crTimeDesc"/>
				</td>
			</tr>
			<tr>
				<td align="right" class="TableData">时间描述：</td>
				<td id="rangeDesc" class="TableData">
					
				</td>
			</tr>
			<tr>
				<td align="right" class="TableData">负责人：</td>
				<td class="TableData">
					<input type="hidden" name="managerUserId" id="managerUserId"/>
					<input class="BigInput  readonly easyui-validatebox" required name="managerUserName"  id="managerUserName" readonly/>
					<a href="javascript:void(0)" onclick="selectSingleUser(['managerUserId','managerUserName'])">添加</a>&nbsp;&nbsp;
					<a href="javascript:void(0);" class="orgClear" onClick="clearData('managerUserId','managerUserName')">清空</a>
				</td>
			</tr>
			<tr>
				<td align="right" class="TableData">计划内容<span style="color:red">*</span>：</td>
				<td class="TableData">
					<textarea required style="height:200px;width:500px" name="content" class="BigTextarea easyui-validatebox"></textarea>
				</td>
			</tr>
			<tr>
				<td align="right" class="TableData">批示人：</td>
				<td class="TableData">
					<input type="hidden" name="reportedRangesIds" id="reportedRangesIds"/>
					<textarea class="BigTextarea  readonly" name="reportedRangesNames"  id="reportedRangesNames" style="height:60px;width:500px" readonly></textarea>
					<a href="javascript:void(0)" onclick="selectUser(['reportedRangesIds','reportedRangesNames'])">添加</a>
					&nbsp;
					<a href="javascript:void(0)" onclick="clearData('reportedRangesIds','reportedRangesNames')">清空</a>
				</td>
			</tr>
			<tr>
				<td align="right" class="TableData">分享人：</td>
				<td class="TableData">
					<input type="hidden" name="sharedRangesIds" id="sharedRangesIds"/>
					<textarea class="BigTextarea  readonly" name="sharedRangesNames"  id="sharedRangesNames" style="height:60px;width:500px" readonly></textarea>
					<a href="javascript:void(0)" onclick="selectUser(['sharedRangesIds','sharedRangesNames'])">添加</a>
					&nbsp;
					<a href="javascript:void(0)" onclick="clearData('sharedRangesIds','sharedRangesNames')">清空</a>
				</td>
			</tr>
			<tr>
				<td align="right" class="TableData">附件：</td>
				<td class="TableData">
					<div style="min-height:100px;">
		      			<span id="attachments"></span>
			      		<div id="fileContainer2"></div>
						<a id="uploadHolder2" class="add_swfupload">
							<img src="<%=systemImagePath %>/upload/batch_upload.png"/>添加附件
						</a>
						<input id="attachmentSidStr" name="attachmentSidStr" type="hidden"/>
		      		</div>
				</td>
			</tr>
			<tr style="border-bottom:none;">
				<td colspan="2" class="TableData" align="center">
					<center>
					<button type="button" class="btn-alert-gray fr" onclick="ret()">返回</button>&nbsp;&nbsp;
					<button type="button" class="btn-alert-blue fr" onclick="save()" style="margin-right:15px;">保存</button>
					</center>
				</td>
			</tr>
		</table>
		<input type="hidden" id="time1Desc" name="time1Desc"/>
		<input type="hidden" id="time2Desc" name="time2Desc"/>
		<input type="hidden" id="uuid" name="uuid"/>
		</form>
	</div>
</div>
</div>
</body>
</html>