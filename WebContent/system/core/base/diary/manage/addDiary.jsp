<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String sid = request.getParameter("sid");
	String day = request.getParameter("day")==null?"":request.getParameter("day");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header2.0.jsp"%>
<%@include file="/header/upload.jsp"%>
<%@ include file="/header/validator2.0.jsp"%>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script src="<%=contextPath %>/common/ueditor/ueditor.config.js"></script>
<script src="<%=contextPath %>/common/ueditor/ueditor.all.min.js"></script>
<style>
.diaryTable {
	border-collapse: collapse;
}

.diaryTable td {
	border: 1px solid #e2e2e2;
	font-size: 14px;
	padding: 5px;
}
</style>
<script>
var sid='<%=sid%>';
var day = '<%=day%>';
var editor;
function doInit(){
	var editor = UE.getEditor('content');
	//对编辑器的操作最好在编辑器ready之后再做
	editor.ready(function() {
		if(sid!="" && sid!=null && sid!="null"){
			var url = "<%=contextPath%>/diaryController/getDiaryById.action?sid="+sid;
			var json = tools.requestJsonRs(url);
			if(json.rtState){
				bindJsonObj2Cntrl(json.rtData);
				changeType(json.rtData.type);
				if(json.rtData.writeTimeDesc=="null" || json.rtData.writeTimeDesc==null){
					$("#writeTimeDesc").val("");
				}
				var attaches = json.rtData.attacheModels;
				for(var i=0;i<attaches.length;i++){
					var fileItem = tools.getAttachElement(attaches[i]);
					$("#attachs").append(fileItem);
				}
				editor.setContent(json.rtData.content);
				
			}else{
				$.MsgBox.Alert_auto(json.rtMsg);
			}
		}
	});
		
	new TeeSimpleUpload({
	 	fileContainer:"fileContainer",//文件列表容器
		uploadHolder:"uploadHolder",//上传按钮放置容器
		valuesHolder:"valuesHolder",//附件主键返回值容器，是个input
		showUploadBtn:false,
		form:"form1",
		post_params:{model:"diary"}//后台传入值，model为模块标志
	});
}
function doSave(callback){
	if (checkForm()){
		var para =  tools.formToJson($("#form1")) ;
		var editor = UE.getEditor('content');
		para["content"] = editor.getContent();
		para["model"] = "diary";
		//$.jBox.tip("正在保存，请稍候","loading");
		$("#form1").doUpload({
				url:"<%=contextPath%>/diaryController/addOrUpdateDiary.action",
				success:function(json){
					//$.jBox.closeTip();
					if(xparent.doSearch){
						xparent.doSearch();
					}
					CloseWindow();
				},
				post_params:para
			});
	}
	
}

function checkForm() {
	var check= $("#form1").valid(); 
    if(!check){
    	return false;
    }
    return true;
}

function changeType(value){
	/* if(value==1){//个人日志
		$("#ranges").hide();
	}else{
		$("#ranges").show();
	} */
}
</script>
</head>
<body onload="doInit();" style="margin:10px;overflow-x:hidden">
<div id="layout">
<form method="post" name="form1" id="form1"  enctype="multipart/form-data">
<table class="TableBlock_page">
	<tr>
		<td style="width:70px">标题：</td>
		<td>
			<input style="font-family: MicroSoft YaHei;font-size: 12px;" required type="text" id='title' name='title' class="" />
		</td>
	</tr>
	<tr>
		<td>类型：</td>
		<td>
			<select style="height: 25px;font-family: MicroSoft YaHei;font-size: 12px;" id='type' name='type' class="" onchange="changeType(this.value)">
				<option value="2">工作日志</option>
				<option value="1">个人日志</option>
			</select>
		</td>
	</tr>
	<tr>
		<td>日期：</td>
		<td>
			<input type="text" id='writeTimeDesc' value="<%=day %>" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='writeTimeDesc' class="Wdate" required/>
		</td>
	</tr>
	<tr  id="ranges">
		<td>共享范围：</td>
		<td>
			<div><a href="javascript:void()" onclick="$(this).hide();$('#ranges_ext').show();">选择共享人</a></div>
			<div id="ranges_ext" style="display:none">
				<textarea id="shareRangesNames" style="height:50px;background:#f0f0f0;padding:5px;font-family: MicroSoft YaHei;font-size: 12px;" class="BigTextarea" readonly></textarea>
				<input type="hidden" id="shareRangesIds" name="shareRangesIds"/>
				<br/>
				<a href="javascript:void(0)" onclick="selectUser(['shareRangesIds','shareRangesNames'],'<%=TeeModelIdConst.DIARY_SEND_PRIV%>' , '1')">选择</a>&nbsp;&nbsp;<a href="javascript:void(0)" onclick="clearData('shareRangesIds','shareRangesNames')">清除</a>
				<div>
					<input type="checkbox" id="remaind" name="remaind"/>内部短信提醒共享人员
				</div>
			</div>
		</td>
	</tr>
	<tr>
		<td>附件：</td>
		<td>
			<div id ='attachs'></div>
			<div id="fileContainer"></div> <a id="uploadHolder"class="add_swfupload">附件上传</a> 
			<input id="valuesHolder" type="hidden" />
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<textarea name="content" id="content" style="margin-right:20px;"></textarea>
		</td>
	</tr>
	<tr>
	  <td colspan="2">
	    <input type="hidden" id='sid' name="sid" value="0" /> 
		<center>
			<button class="btn-win-white" type="button" onclick="doSave()">确定</button>
			&nbsp;&nbsp;
			<button class="btn-win-white" type="button" onclick="CloseWindow()">关闭</button>
		</center>
	  </td>
	
	</tr>
</table>
</form>
</div>
</body>

<script>
	$("#form1").validate();
</script>
</html>