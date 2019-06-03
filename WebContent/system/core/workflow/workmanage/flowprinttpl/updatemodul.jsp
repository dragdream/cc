<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<%
	String flowTypeId = request.getParameter("flowTypeId") == null ? "0" : request.getParameter("flowTypeId");
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
    	return rtJson.rtData.modulContent;
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


	

</script>




<SCRIPT LANGUAGE=javascript FOR=TeeHWPostil EVENT=NotifyCtrlReady> 
var content = loadPrintModul();

if (content) {
  // 控件"HWPostil1"的NotifyCtrlReady事件，一般在这个事件中完成初始化的动作
	obj = document.getElementById("TeeHWPostil");//$("#TeeHWPostil")[0];
    obj.ShowDefMenu = false; //隐藏菜单
    obj.ShowToolBar = false; //隐藏工具条
 
   obj.ShowScrollBarButton = 1;
//  obj.InDesignMode = true;
   obj.LoadFileBase64(content);
   
  userLoginAip();//系统登录
 
}
</SCRIPT>

</head>
<body onload="">
<table border="0" width="95%" cellspacing="0" cellpadding="3"class="small" align="center" >
		<tr>
			<td class="Big">
				<span class="Big3"> 更换 "<b id="modulName" ></b> "打印模版</span><font color='red' style="padding-left:10px;">(只更换底层模版，节点属性保留)</font>
			</td>
			<td align="right">
				&nbsp;<input type="button" value="选择更换模版"  class="btn btn-primary" onclick="selFile();">
				&nbsp;
				<input type="button" value="保存"  class="btn btn-primary" onclick="saveUpdateModul();"> 
			   &nbsp;<input type="button" value="返回"  class="btn btn-primary" onclick="history.go(-1);">
			</td>
		</tr>
	</table>
	<table class="TableBlock" width="100%" align="center">
 
 <tr>
  <td nowrap class="TableData" colspan="4">
    
 <script src="<%=contextPath%>/system/core/aip/loadaip.js"></script>
  </td>

 </tr>
  
	</table>

 
</body>
</html>
 
 
 