<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
   String sid=TeeStringUtil.getString(request.getParameter("sid"));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>印章信息</title>
</head>
<script type="text/javascript" src="<%=contextPath%>/system/core/system/seal/js/sealmanage.js"></script>
<script type="text/javascript">
var sid="<%=sid%>";
//初始化地图
function doInit(){
	var url = contextPath +  "/sealManage/selectById.action?sid=" + sid;
	var para = {};
	var jsonRs = tools.requestJsonRs(url,para);
	if(jsonRs.rtState){
		var sealData = jsonRs.rtData.sealData;
		showInfoStr(sealData);
	}else{
		$.MsgBox.Alert_auto(jsonRs.rtMsg);
	}
	
}






</script>

<body onload="doInit();" style="background-color: #f2f2f2;">
   	
<div id="sealInfo" >
  <div id="apply_body" class="body" align=""  style="">
	   <table class="TableBlock" width="100%" >
	    <tr>
	     <td class="TableData" colspan=2 align="center">
	        <div align="center" style="margin-top: 5px">
	  		 <OBJECT 
	          id=DMakeSealV61 
	          style="left: 0px; top: 0px" 
	          classid="clsid:3F1A0364-AD32-4E2F-B550-14B878E2ECB1" 
	          VIEWASTEXT 
	          width="200"
	          height="200"
	          codebase='<%=contextPath%>/system/core/system/seal/sealmaker/MakeSealV6.ocx#version=1,0,3,4'>
	  
	 		 <PARAM NAME="_Version" VALUE="65536">
	 		 <PARAM NAME="_ExtentX" VALUE="2646">
	  		 <PARAM NAME="_ExtentY" VALUE="1323">
	  		 <PARAM NAME="_StockProps" VALUE="0">
			</OBJECT>
		    </div>
	       </td>
	     </tr>
	     <tr>
	      <td class="TableContent" width=80 style="text-indent: 10px;">印章ID</td>
	      <td class="TableData"><span id="seal_id"></span></td>
	    </tr>
	     <tr>
	      <td class="TableContent" style="text-indent: 10px;">印章名称</td>
	      <td class="TableData"><span id="seal_name"></span></td>
	    </tr>    
	     <tr>
	      <td class="TableContent" style="text-indent: 10px;">印章尺寸</td>
	      <td class="TableData"><span id="seal_size"></span></td>
	    </tr> 
	     <tr>
	      <td class="TableContent" style="text-indent: 10px;">印章签名</td>
	      <td class="TableData"><span id="seal_sign"></span></td>
	    </tr> 
	     <tr>
	      <td class="TableContent" style="text-indent: 10px;">证书绑定</td>
	      <td class="TableData"><span id="seal_cert"></span></td>
	    </tr> 
	  </table>
  </div>
</div>
</body>
</html>