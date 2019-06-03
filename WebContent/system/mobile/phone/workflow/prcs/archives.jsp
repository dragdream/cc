<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
   String attaches = request.getParameter("attaches");
   String title = request.getParameter("title");
   
    String runId = request.getParameter("runId");
	String flowId = request.getParameter("flowId");
	String frpSid = request.getParameter("frpSid");
	String flowTypeId = TeeStringUtil.getString(request.getParameter("flowTypeId"));
%>
<!DOCTYPE HTML>
<html>
<head>
<title>归档</title>
<%@ include file="/system/mobile/mui/header.jsp" %>
<script src="<%=contextPath %>/common/js/tools2.0.js"></script>
<script src="<%=contextPath %>/common/js/sysUtil.js"></script>
</head>

<script>
var attaches = "<%=attaches%>";
var title = "<%=title%>";
var runId="<%=runId%>";
var frpSid="<%=frpSid%>";
var flowId="<%=flowId%>";
var flowTypeId="<%=flowTypeId%>";
function doInit(){
//日期
	sendDateDesc.addEventListener('tap', function() {
		var picker = new mui.DtPicker({type:"date"});
		picker.show(function(rs) {
			sendDateDesc.value = rs.text;
			picker.dispose(); 
		});
	}, false);
	

	getRecord();
	
	getSysCodeByParentCodeNo("DOC_AJMJ" , "fileSecret");
	getSysCodeByParentCodeNo("FILE_SORT" , "fileKind");
	getSysCodeByParentCodeNo("DOC_JJCD" , "fileUrgency");
	getSysCodeByParentCodeNo("DOC_TYPE" , "fileType");
 
    
    if(attaches!="null"){//有初始化附件
    	$("#mydiv").show();
    	$("#attaches").attr("value",attaches);
    	
    	var json = tools.requestJsonRs(contextPath+"/attachmentController/getAttachmentModelsByIds.action",{attachIds:attaches});
		var itemList = json.rtData;
		if(itemList.length > 0){
			  $.each(itemList, function(index, item){  
				  $("#fileContainer").append("<div ><a href='javascript:void(0);'  onclick=\"GetFile('"+item.sid+"','"+item.fileName+"','"+item.attachmentName+"')\" >"+item.fileName + "&nbsp;&nbsp;("+item.sizeDesc+")"+"</a><div>");
					
			  });
		  }

		
		if(title!="null"){
			$("#fileCode").attr("value",title);
			$("#fileTitle").attr("value",title);
		}
    }
    
}

function commit0(){
	var para =  tools.formToJson("#form1") ;
	para["model"] = "dam";
	
	if(checkForm()){
		var url=contextPath+"/TeeDamFilesController/addMobileFile.action";
		var json=tools.requestJsonRs(url,para);
		
		if(json.rtState){
			back();
		}
		
		<%-- $("#form1").doUpload({
			url:"<%=contextPath%>/TeeDamFilesController/addFile.action",
			success:function(json){
				alert("已完成");
				CloseWindow();
			},
			post_params:para
		}); --%>
	}
}


function checkForm(){
// 	if($("#sendDateDesc").val()=="" || $("#sendDateDesc").val()==null){
// 		alert("发文日期不能为空,请填写日期!");
// 		return false;
// 	}
	if($("#recordId").val()=="" || $("#recordId").val()==null || $("#recordId").val()==0){
		alert("所属案卷不能为空,请选择案卷!");
		$("#recordId").focus();
		return false;
	}
	return true;
}

function getRecord(){
	var url =contextPath+"/TeeRecordsController/getAllRecords.action";
	var json = tools.requestJsonRs(url);
	var html = "<option value=\"0\"></option>";
	for(var i=0;i<json.rtData.length;i++){
		html+="<option value=\""+json.rtData[i].sid+"\">"+json.rtData[i].recordName+"</option>";
	}
	$("#recordId").html(html);
}


//返回
function back(){
	var url=contextPath+"/system/mobile/phone/workflow/prcs/form.jsp?runId="+runId+"&frpSid="+frpSid+"&flowId="+flowId+"&flowTypeId="+flowTypeId;
	window.location=url;
	
}
</script>


<body onload="doInit()">
<form id="form1" name="form1">
<header id="header" class="mui-bar mui-bar-nav">
	<button type="button" class="mui-btn mui-btn-link mui-btn-nav mui-pull-left" onclick="back();">
	    <span class="mui-icon mui-icon-left-nav"></span>返回
	</button>
	<button type="button" class="mui-btn mui-btn-link mui-btn-nav mui-pull-right" onclick='commit0()'>
	    保存
	</button>
	
	<h1 class="mui-title">归档</h1>
	
</header>
<div id="muiContent" class="mui-content">
	<div class="mui-input-group">
		<div class="mui-input-row">
			<input type="text" placeholder="文件号" name="fileCode" id="fileCode">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<input type="text" placeholder="文件主题词" name="fileSubject" id="fileSubject">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<input type="text" placeholder="文件标题" name="fileTitle" id="fileTitle">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<input type="text" placeholder="文件辅标题" name="fileSubTitle" id="fileSubTitle">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<input type="text" placeholder="发文单位" name="sendUnit" id="sendUnit">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>发文日期</label>
		</div>
		<div class="mui-input-row">
			<input type="text" id="sendDateDesc" name="sendDateDesc" placeholder="选择时间" onchange="changed();"/>
		</div>
	</div>
	
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>密级</label>
		</div>
		<div class="mui-input-row">
			<select class="BigSelect" id="fileSecret" name="fileSecret">
					<option value=""></option>
			</select>
		</div>
	</div>
	
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>紧急等级</label>
		</div>
		<div class="mui-input-row">
			<select class="BigSelect" id="fileUrgency" name="fileUrgency">
					<option value=""></option>
			</select>
		</div>
	</div>
	
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>文件分类</label>
		</div>
		<div class="mui-input-row">
			<select class="BigSelect" id="fileKind" name="fileKind">
					<option value=""></option>
			</select>
		</div>
	</div>
	
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>公文类别</label>
		</div>
		<div class="mui-input-row">
			<select class="BigSelect" id="fileType" name="fileType">
					<option value=""></option>
			</select>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<input type="text" placeholder="文件页数" name="filePages" id="filePages">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<input type="text" placeholder="打印份数" name="filePrintPages" id="filePrintPages">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>所属案卷</label>
		</div>
		<div class="mui-input-row">
			<select class="BigSelect" id="recordId" name="recordId">
					<option value=""></option>
			</select>
		</div>
	</div>
	
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>备注</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
			<textarea name="remark" id='remark' rows="5" placeholder="请输入备注内容"></textarea>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>附件</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
			<div id="fileContainer" class="content_attath_list" style="margin-left: 12px"></div>
		</div>
	</div>
	<input type="hidden" id="attaches" name="attaches" />
</form>
</body>
</html>