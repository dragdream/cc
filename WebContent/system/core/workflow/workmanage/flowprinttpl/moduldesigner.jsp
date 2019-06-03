<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<%
	String flowTypeId = request.getParameter("flowTypeId") == null ? "0" : request.getParameter("flowTypeId");
	String sid = request.getParameter("sid") == null ? "0" : request.getParameter("sid");
%>
<html>
<head>
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
var flowTypeId = '<%=flowTypeId%>';	
var sid = "<%=sid%>";
var _userName = "sys_admin";
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
	var h = document.body.clientHeight - 45 ;
	obj.style.height = h + "px";
	obj.style.width = "100%";
    //$(obj).attr("width","100%");
    //$(obj).attr("height","100%");
    //obj.style.height = 400;
}

/***
 * 如果是更新过模版，则需要把原来的字段加上
 */
function setModulField(printModulFields){
	jQuery.each(printModulFields, function(i, filed) {
		//alert(filed.fieldName);
		var vret = insertNote(filed);
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
    //obj.ShowDefMenu = false; //隐藏菜单
   // obj.ShowToolBar = false; //隐藏工具条
 
   obj.ShowScrollBarButton = 1;
//  obj.InDesignMode = true;
   obj.LoadFileBase64(content);
   
   userLoginDesignerAip();//系统登录
   printModulFields  = jQuery.parseJSON(modulField);
   if(isUpdate == 1){//更换过模版
	   setModulField(printModulFields);
   }
}
</SCRIPT>

</head>
<body onload="">


<table border="0" width="95%" cellspacing="0" cellpadding="3"
		class="small" align="center" >
		<tr>
			<td class="Big">
				 <img src="<%=stylePath%>/imgs/print.gif" align="center" /><span
				class="big3"> 设计打印模版</span>
				
				&nbsp;&nbsp;模版名称:<b id="modulName" ></b>
				&nbsp;&nbsp;模版类型:<b id="modelTypeDesc" ></b>
	
 					
			</td>
			
			<td align="right">
				<input type="button" value="保存"  class="btn btn-primary" onclick="saveModulDesigner();" >
				<input type="button" value="选择更换模版"  class="btn btn-primary" onclick="selFileUpdate();">
			</td>
		</tr>
	</table>


	<table class="TableBlock" width="100%" align="center">
   <!-- <tr>
    <td nowrap class="TableData"  align="center">模版名称：</td>
    <td nowrap class="TableData">
      <input type="text" id="modulName" name="modulName" >
       
    </td>

    <td nowrap class="TableData" align="center">模版类型:</td>
    <td nowrap class="TableData">
    	 <select name="modulType" class="" id="modulType">
       <option value="1">打印模板</option>
       <option value="2">手写呈批单</option>
      </select>
    	
    </td>
    </tr> -->
 <tr>
  <td nowrap class="TableData" colspan="4">
    
 <script src="<%=contextPath%>/system/core/aip/loadaip.js"></script>
  </td>

 </tr>
  
	</table>


	<div id='vseaf'></div>
	
	
<SCRIPT LANGUAGE=javascript FOR=TeeHWPostil EVENT=NotifyLineAction(lPage,lStartPos,lEndPos)> 
NotifyLineAction(lPage,lStartPos,lEndPos);
</SCRIPT>
 
 
</body>
</html>
 
 
 