<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<%@ include file="/header/header.jsp" %>
	<% 
	int runId = TeeStringUtil.getInteger(request.getParameter("runId"),0);
	int flowId = TeeStringUtil.getInteger(request.getParameter("flowId"),0);
	int prcsId = TeeStringUtil.getInteger(request.getParameter("prcsId"),0);
	int flowPrcs = TeeStringUtil.getInteger(request.getParameter("flowPrcs"),0);
	String isNew = TeeStringUtil.getString(request.getParameter("isNew"),"");
	%>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>流程办理</title>
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui/themes/gray/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=cssPath%>/style.css">
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/images/workflow/index.css">
	<script type="text/javascript" src="<%=contextPath%>/common/js/tools.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.min.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/sys.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/workflowUtils.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/jquery.form.min.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/src/orgselect.js"></script>
<script>
var contextPath = "<%=contextPath%>";
var runId = <%=runId%>;
var flowId = <%=flowId%>;
var prcsId = <%=prcsId%>;
var flowPrcs = <%=flowPrcs%>;
var isNew = '<%=isNew%>';
var imgPath = '<%=imgPath%>';
var fileImgPath = imgPath +'/dll.gif';
var fileDelImgPath = imgPath +'/remove.png';
var delfeedbackPath = imgPath +'/delete.gif';

$(document).ready(function(){
	$("#layout").layout({auto:true});
});

function doInit(){
}
/**
 * 删除会签意见
 */
function delFeedBack(id){
	var url = contextPath+"/feedBack/deleteFeedBack.action";
	var para = {};
	para['fid'] = id;
	var json = tools.requestJsonRs(url,para);
	if(json.rtState){
		loadFeedBack();
	}else{
		alert(json.rtMsg);
	}
	
}
/**
 * 删除会签意见 中的某一个附件
 */
function delFeedBackAttach(fid,aid){
	var url = contextPath+"/feedBack/deleteFeedBackAttach.action";
	var para = {};
	para['fid'] = fid;
	para['aid'] = aid;
	var json = tools.requestJsonRs(url,para);
	if(json.rtState){
		//alert(json.rtMsg);
		//window.location.reload();
		loadFeedBack();
	}else{
		alert(json.rtMsg);
	}
	
}

function saveFlowRunData(){
	//组合列表数据控件
	listViewPreSaving($("#formDiv"));
	
	var url = contextPath+"/flowRun/saveFlowRunData.action";
	var para = tools.formToJson($("#form"));
	para["runId"] = runId;
	para["flowId"] = flowId;
	
	var json = tools.requestJsonRs(url,para);
	
	alert(json.rtMsg);
}

/**
 *保存会签意见 zhp
 */
function saveFeedBack(){
	var url = contextPath+"/feedBack/addFeedBack.action";
	var para = tools.formToJson("#feedBackForm");
	para["runId"] = runId;
	para["flowId"] = flowId;
	para["prcsId"] = prcsId;
	para["flowPrcs"] = flowPrcs;
	var json = tools.requestJsonRs(url,para);
	if(json.rtState){
		//alert(json.rtMsg);
		window.location.reload();
	}else{
		alert(json.rtMsg);
	}
}


/**
 *获取会签意见 zhp
 */
function loadFeedBack(){
	var url = contextPath+"/feedBack/getFeedBackList.action";
	var para  = {};
	para["runId"] = runId;
	para["flowId"] = flowId;
	para["prcsId"] = prcsId;
	para["flowPrcs"] = flowPrcs;
	//para["content"] = $("#content").val();
	var json = tools.requestJsonRs(url,para);
	if(json.rtState){
		var fdDate = json.rtData;
		var sHtml = "";
		for(var i=0;i<fdDate.length;i++){
			var attachList = fdDate[i].attachList;
			sHtml = sHtml + "<div> <div>"+new Date(fdDate[i].editTime).pattern("yyyy-MM-dd hh:mm:ss")+fdDate[i].userName+ "</div>会签意见:"+fdDate[i].content
			+"<img src='"+delfeedbackPath+"' onclick='delFeedBack("+fdDate[i].sid+")' />"+"</div>";
			var attStrHtml = "";
			for(var j=0;j<attachList.length;j++){
				var att = attachList[j];
				attStrHtml = attStrHtml +"<div> <img src='"+fileImgPath+"'>"+att.name+"<img src='"+fileDelImgPath+"' onclick='delFeedBackAttach("+fdDate[i].sid+","+att.id+")'  /></div>";
			}
			sHtml = sHtml +"<div>"+ attStrHtml+"</div>";
			}
		$("#feedBackContest").html(sHtml);
	}else{
		alert(json.rtMsg);
		
	}
	

}

/**
 * 初始化列表视图
 */
function initListView(){
	$("#formDiv").find("table[clazz='xlist_tb']").each(function(i,tb){
		
	});
}

/**
 * 保存会签意见
 */
function submitForm(){
	var para  = {};
	para["runId"] = runId;
	para["flowId"] = flowId;
	para["prcsId"] = prcsId;
	para["flowPrcs"] = flowPrcs;
	 $("#feedBackForm").ajaxSubmit({
           url: contextPath+'/feedBack/addFeedBack.action',
           iframe: true,
           data: para,
           success: function(res) {
		 window.location.reload();
                 // ... my success function (never getting here in IE)
                 },
          error: function(arg1, arg2, ex) {
                 // ... my error function (never getting here in IE)
                 alert("添加会签意见出错！");
           },
           dataType: 'json'});
		
}
function addFile(event){
	event = event || window.event;
	var dom1 = event.srcElement ? event.srcElement : event.target;
	var srcValue = dom1.value;
	var  lastIndex = srcValue.lastIndexOf('\\');
	var fileName = srcValue.substr(lastIndex+1,srcValue.length);
	var span1 = $(dom1).prev();
	$(dom1).removeClass().hide();
	$(dom1).parent().removeClass();
	$(span1).html(fileName+"<img src='"+fileDelImgPath+"' onclick='delUploadFile()' >");
	$("<a class=\"addfile\"><span>添加附件</span><input type=\"file\" name=\"file\"  class=\"addfile\"  onchange=\"addFile()\"  /><br /></a>").appendTo($("#upfile"));
}

function delUploadFile(event){
	event = event || window.event;
	var dom1 = event.srcElement ? event.srcElement : event.target;
	$(dom1).parent().parent().removeClass().remove();

	
}
</script>
<style>
</style>
</head>
<body onload="doInit()" style="overflow:hidden;margin:0px;">
<form enctype="multipart/form-data" action="personal_submit.php" target="_self" method="post" name="form3" onsubmit="return check_form();">
<table class="TableTop" align="center" width="95%">
   <tr>
      <td class="left"></td>
      <td class="center"><img src="/images/green_arrow.gif" align="absmiddle"><b> 编辑会签意见</b></td>
      <td class="right"></td>
   </tr>
</table>
<table class="TableBlock no-top-border" align="center" width="95%">
    <tr>
        <td class="TableContent">       
        <div>        	
<input type="hidden" name="CONTENT" id="CONTENT" value="按时打算的" /><iframe id="CONTENT_iframe" src="/module/ubbeditor/index.html?InstanceName=CONTENT&amp;Height=180px&amp;Width=100%25&amp;BasePath=%2Fmodule%2Fubbeditor%2F&amp;StartFocus=0&amp;CtrlEnter=&amp;Language=zh-CN" style="width:100%;height:180px;" frameborder="0" scrolling="no"></iframe>        </div>
      </tr>
        <tr class="TableData">
        	<td nowrap>
           
          </td>
        </tr>
</table>


</body>

</html>