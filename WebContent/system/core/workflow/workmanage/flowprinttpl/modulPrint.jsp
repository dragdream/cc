<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<%
	String runId = request.getParameter("runId") == null ? "0" : request.getParameter("runId");
	String sid = request.getParameter("sid") == null ? "0" : request.getParameter("sid");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp"%>
<title>流程模版管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="<%=cssPath%>/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/jquery/json2.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/tools.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sys.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/aip/designer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/workflow/workmanage/flowprinttpl/aip/flowPrintModulAip.js"></script>


<script type="text/javascript">
var runId = '<%=runId%>';	
var sid = "<%=sid%>";


var _userName = "HWSEALDEMO";
function loadPrintModul(){
    setHWPostil();
    

	var url = contextPath + "/flowPrintTemplate/getById.action"  ;
 	var para = {sid:sid};
	var rtJson = tools.requestJsonRs(url,para);
    if (rtJson.rtState) {
    	var data = rtJson.rtData;
  		var modulName= data.modulName;
  		var modulType= data.modulType;
  		var modelTypeDesc = "打印模板";
    	if(modulType == '2'){
    		modelTypeDesc =  "手写呈批单";
    	}
    	$("#modulName").html(modulName);
    	$("#modelTypeDesc").html(modelTypeDesc);
    	return rtJson.rtData;
    }else {
       alert(rtJson.rtMsg);
       return false;
   }

}

/**
 * 设置控件高度
 */
function setHWPostil() {
	obj = document.getElementById("TeeHWPostil");//$("#TeeHWPostil")[0];
	var h = window.screen.height - 170 ;
	obj.style.height = h + "px";
	obj.style.width = "100%";
    //$(obj).attr("width","100%");
    //$(obj).attr("height","100%");
    //obj.style.height = 400;
}

/***
 * 表单赋值
 */
function setModulField(printModulFields){
	var url = contextPath+"/flowRun/getFlowRunDatas.action";
	var json = tools.requestJsonRs(url,{runId:runId});
	var datas = json.rtData;
	if(!json.rtState){
		messageMsg(json.rtMsg,"body","info");
		return;
	}
	jQuery.each(printModulFields, function(i, field) {
		//alert(filed.fieldName);
		var fieldName = field.fieldName;//字段名称 DATA_1
		var noteName = field.noteName;//节点名称   唯一 DATA_1或者DATA_1_1
		var fieldPage = field.fieldPage;
		for(var key in datas){
			if(key==fieldName){
				obj.SetValue("page" + fieldPage + "." + noteName, datas[key]);
				break;
			}
		}
	});
}

/**
 * 更换模版后设置节点
 */
function selFileUpdate(){
	obj = document.getElementById("TeeHWPostil");//$("#TeeHWPostil")[0];
    obj.LoadFile("");
	//设置节点 
    setModulField(printModulFields);
}

/**
 * 导出文件
 */
function exportFile()
{
    var obj = $("#TeeHWPostil")[0];
    obj.SaveTo("","aip",0);
}
</script>




<SCRIPT LANGUAGE=javascript FOR=TeeHWPostil EVENT=NotifyCtrlReady> 
var printModul = loadPrintModul();

var modulField = printModul.modulField;
var isUpdate = printModul.isUpdate;
var content = printModul.modulContent;

if (content) {
  // 控件"HWPostil1"的NotifyCtrlReady事件，一般在这个事件中完成初始化的动作
	obj = document.getElementById("TeeHWPostil");//$("#TeeHWPostil")[0];
    obj.ShowDefMenu = false; //隐藏菜单
    obj.ShowToolBar = false; //隐藏工具条
   obj.ShowScrollBarButton = 1;
   obj.LoadFileBase64(content);
   userLoginAip();//登录
   obj.InDesignMode = false;
   printModulFields  = jQuery.parseJSON(modulField);
   setModulField(printModulFields);
   
}
</SCRIPT>

</head>
<body onload="" id="body">
<script src="<%=contextPath%>/system/core/aip/loadaip.js"></script>
<div id='vseaf'></div>
</body>
</html>
 
 
 