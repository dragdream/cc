<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String sid = request.getParameter("sid")==null?"0":request.getParameter("sid");
	String timelineUuid = request.getParameter("timelineUuid")==null?"0":request.getParameter("timelineUuid");
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
<style>
	td{
		line-height:28px;
		min-height:28px;
	}
.ztree{
		background:white;
		border:1px solid #f0f0f0;
	}
</style>

<script>
var sid = "<%=sid%>";
var timelineUuid = "<%=timelineUuid%>";
function doInit(){
 	getSysCodeByParentCodeNo("TIMELINE_TYPE","type");
 	doInitUpload();
	if(sid!="" && sid!=null && sid!="null"){
		var url = "<%=contextPath%>/TeeTimelineEventController/getById.action?uuid="+sid;
		var json = tools.requestJsonRs(url);
		if(json.rtState){
			bindJsonObj2Cntrl(json.rtData);
			/**
			*处理附件
			*/
			var  attachmodels = json.rtData.attacheModels;
			for(var i=0;i<attachmodels.length;i++){
				var fileItem = tools.getAttachElement(attachmodels[i]);
				$("#attachments").append(fileItem);
			}
		}
	}
}

function commit(){
	if($("#form1").valid()){
		var param = tools.formToJson($("#form1"));
		var url = contextPath+"/TeeTimelineEventController/addOrUpdate.action";
		var json = tools.requestJsonRs(url,param);
		if(json.rtState){
			 $.MsgBox.Alert_auto(json.rtMsg);
// 			var url = contextPath+"/system/subsys/timeline/manage/eventList.jsp?timelineUuid="+timelineUuid;
// 			location.href=url;
// 			history.go(-1);
			return json;
		}else{
			 $.MsgBox.Alert_auto(json.rtMsg);
		}
	}
}

function checkForm(){
    var check= $("#form1").valid(); 
    if(!check){
    	return false;
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
		post_params:{model:"timelineEvent"}//后台传入值，model为模块标志
	});
}
</script>
</head>
<body onload="doInit();" style="padding: 10px;background-color: #f2f2f2;">
<fieldset style="margin:10px;display:none">
	<legend>事件信息</legend>
</fieldset>
<form id="form1" name="form1">
	<table class='TableBlock' width="100%">
		<tr align='left'>
			<td class='TableData' style="text-indent: 10px;" width="100px">
				标&nbsp;&nbsp;&nbsp;&nbsp;题&nbsp;<span style="color:red;font-weight:bold;">*</span>：
			</td>
			<td class='TableData'>
				<input type="text" id="title" name="title" required style="font-family: MicroSoft YaHei;width:280px;height: 25px;margin-bottom: 5px;" validType="maxLength[100]"/>
			</td>
		</tr>
		<tr> 
			<td class='TableData' style="text-indent: 10px;">
				开始时间：
				</td>
			<td class='TableData'>
				<input type="text" id='startTimeDesc' required onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTimeDesc\')}'})" name='startTimeDesc' class="Wdate BigInput" />	
			</td>
		</tr >
		<tr>
			<td class='TableData' style="text-indent: 10px;">
				结束时间：
				</td>
			<td class='TableData'>
				<input type="text" id='endTimeDesc' required onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTimeDesc\')}'})" name='endTimeDesc' class="Wdate BigInput" value="<%=TeeDateUtil.format(new Date())%>" />
			</td>
		</tr>
		<tr>
			<td class='TableData' style="text-indent: 10px;">
				内&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;容：
				</td>
			<td class='TableData'>
				<textarea style="width:280px;height: 150px;font-family: MicroSoft YaHei;" id="content" name="content" class="BigTextarea" cols='60' rows='5'></textarea>
				</textarea>
			</td>
		</tr >
		<tr style="display:none">
			<td class='TableData' style="text-indent: 10px;">附件上传：</td>
			<td class='TableData'>
		       	<span id="attachments"></span>
					<input type="hidden" id="dbAttachSid" name="dbAttachSid" value="">
	
					<div id="fileContainer2"></div>
					<a id="uploadHolder2" class="add_swfupload">
						<img src="<%=systemImagePath %>/upload/batch_upload.png"/>添加附件
					</a>
					<input id="attachmentSidStr" name="attachmentSidStr" type="hidden"/>
		    </td>
		</tr>
	</table>
	<br/>
	<div id="control" style='margin:0 auto;height:28px;line-height:28px;padding:10px;display:none'>
		<input id="uuid" name="uuid" type='hidden'value="<%=sid %>"/>
		<input id="timelineUuid" name="timelineUuid" type='hidden'value="<%=timelineUuid %>"/>
		<input id="saveInfo" name="saveInfo" type='button' class="btn-win-white" value="保存" onclick='commit();'/>&nbsp;&nbsp;
		<input id="back" name="back" type='button' class="btn-win-white" value='返回' onclick='history.go(-1);'/>
	</div>
</form>
</body>
</html>