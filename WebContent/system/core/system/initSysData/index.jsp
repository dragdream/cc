<%@page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%

	String id = request.getParameter("id") == null ? "0" : request.getParameter("id") ;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>数据初始化</title>
	<%@ include file="/header/header.jsp" %>
	<%@ include file="/header/easyui.jsp" %>
	<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
	<script src="<%=contextPath %>/common/js/tools.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/TeeMenu.js"></script>
<script type="text/javascript">
var id = '<%=id%>';
var contextPath = "<%=contextPath %>";
var systemImagePath = "<%=systemImagePath%>";
function doInit(){
	getAllSysPara();
}

/**
 * 初始化系统 paraSys 参数 ....请将参数写入 teeInitSysDataController.initSysParaData 的map中 zhp 20140117 ！@#￥%……&*
 */
function initSysParaData(){
	var para = {};
	var url = "<%=contextPath%>/teeInitSysDataController/initSysParaData.action";
	var jsonRs = tools.requestJsonRs(url,para);
	if(jsonRs.rtState){
		//jsonRs.rtMsg
		 top.$.jBox.tip(jsonRs.rtMsg);
		$("#foorer").html(jsonRs.rtMsg);
		 getAllSysPara();
		
	}else{
		alert(jsonRs.rtMsg);
	}
}
/**
 * 获取参数列表 zhp 20140117 ！@#￥%……&*
 */
function getAllSysPara(){
	var para = {};
	var url = "<%=contextPath%>/sysPara/getAllSysPara.action";
	var jsonRs = tools.requestJsonRs(url,para);
	if(jsonRs.rtState){
		var rtData = jsonRs.rtData;
		var trTem0 = '<table class="TableBlock" width="90%" align="center" ><tr>'+
		'<td nowrap class="TableData">参数名称</td>'+
		'<td nowrap class="TableData">参数值</td></tr>';
		
		var trTem1 = '<tr>'+
		'<td nowrap class="TableData">{paraName}</td>'+
		'<td nowrap class="TableData">'+
		'{paraValue}'+
		'</td>'+
	    '</tr>';
		var liArray = new Array();
		liArray.push(trTem0);
		if(rtData){
			$.each(rtData,function(key, val){
					var str = FormatModel(trTem1,val);
					liArray.push(str);
			});
		}
		liArray.push("</table>");
		$("#info").html(liArray.join(''));
		
	}else{
		alert(jsonRs.rtMsg);
	}
}


</script>

</head>
<body onload="doInit()">

 <table width="90%" align="center" >
   <tr>
   <td  width="100%" style="padding:0px"> 
    <div class="moduleHeader">
	<b>系统初始化<span id="subject"></span></b>
	</div>
 </td>
 </tr>
    <tr>
      <td  colspan="2" valign="top">
      <div class="btn-group">
	  <button type="button" class="btn btn-default" onclick="initSysParaData()">初始化公系统数据</button>
	  <button type="button" class="btn btn-default">其他数据初始化..</button>
	  <button type="button" class="btn btn-default">其他数据初始化..</button>
	</div>
      <div class="panel panel-success">
		  <div class="panel-body" style="min-height: 200px; ">
		     <div id="info">
  </div>
		  </div>
		  <div class="panel-footer" id="foorer">初始化数据....</div>
	 </div>
		 </td>
    </tr>
  </table>
 
  



</body>
</html>
 