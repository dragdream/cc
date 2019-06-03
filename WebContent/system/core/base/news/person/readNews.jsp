<%@page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%

	String id = request.getParameter("id") == null ? "0" : request.getParameter("id") ;
	String isLooked = request.getParameter("isLooked") == null ? "0" : request.getParameter("isLooked") ;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>新闻详细</title>

	<%@ include file="/header/header2.0.jsp" %>
	<%@ include file="/header/easyui2.0.jsp" %>
	<%@ include file="/header/upload.jsp" %>
	<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript">
var id = '<%=id%>';
var isLooked = "<%=isLooked%>";
var contextPath = "<%=contextPath %>";
var systemImagePath = "<%=systemImagePath%>";
function doInit(){
	getNotify();
}

function getNotify(){
	var url = "<%=contextPath%>/teeNewsController/getNews.action?isLooked="+isLooked;
	var para = {"id":id};
	var jsonRs = tools.requestJsonRs(url,para);
	//alert(jsonRs.isRead);
	if(jsonRs.rtState){
		var data = jsonRs.rtData;
		var subject =  data.subject;//标题
		var content = data.content;//内容
		var fromPersonName = data.provider1;//发布人
		var sendTime = getFormatDateTimeStr(data.newsTime , 'yyyy-MM-dd HH:mm');//发布时间
		var fromDeptName = data.fromDeptName;//发布部门
		
		if(data.format=="2"){
			window.location = data.url;
			return;
		}
		
		$("#subject").html(subject);
		$("#content").html(content);
		$("#fromPersonName").html(fromPersonName);
		$("#sendTime").html(sendTime);

		$("#fromDeptName").html(fromDeptName);
		$.each(data.attachmentsMode,function(i,v){
			v['priv'] = 3;//查看、下载
			var attachElement = tools.getAttachElement(v,{});
			$("#attachList").append(attachElement);
	    });

		$("#addFav").addFav(subject,"/system/core/base/news/person/readNews.jsp?id="+id+"&isLooked="+isLooked);
		
	}else{
		$.MsgBox.Alert_auto(jsonRs.rtMsg);
	}
}

</script>
 
</head>
<body onload="doInit()" style="margin-bottom:20px;">
<div  style="width:90%;margin:0 auto;text-align:center;margin-top: 20px">
	<span id="subject" title="新闻标题" style="font-size:18px;"></span>
</div>
<br/>

 <table width="90%" align="center"  style="max-width:1000px;margin:0 auto;" >
   <tr>
   <td  width="100%" style="padding:0px"> 
	<center style="font-size:12px;padding-bottom:10px;position:relative;">
		发布部门：</span>&nbsp;<u style="cursor:hand" id="fromDeptName"></u>
		发布人：</span>&nbsp;<u  style="cursor:hand" id="fromPersonName"></u>
		发布于：</span>&nbsp;<span id="sendTime"></span>
	<img class="pull-right" src="<%=contextPath %>/common/zt_webframe/imgs/xzbg/xwfb/icon-save.png" style="cursor:pointer;position:absolute;right:0;"  title="添加收藏夹" id="addFav"/>
	</center>
	<hr color="#d6d6d6" style="width:1000px;margin-bottom: 10px;"/>
 </td>
 </tr>
 <tr>
      <td  colspan="2" valign="top">
      <span id="content" style="font-size:14px;"></span>
	   </td>
    </tr>
    <tr>
      <td  colspan="2" valign="top">
      	<br/>
      	<fieldset>
      		<legend  style="font-size: 16px;margin: 10px 0;">附件：</legend>
      		<hr color="#d6d6d6" style="width:1000px;margin-bottom: 10px;">
      	</fieldset>
	      <div id="attachList" ></div>
      </td>
     </tr>
  </table>
  <center id="oper" style="margin-top:10px;">
  	<button class="btn-alert-gray" onclick="CloseWindow()">关闭</button>
  </center>
</body>
</html>
 