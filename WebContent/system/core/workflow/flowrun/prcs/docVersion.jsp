<%@page import="com.tianee.webframe.util.str.TeeStringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
   int runId=TeeStringUtil.getInteger(request.getParameter("runId"),0);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/header/header2.0.jsp" %>
<title>正文版本</title>
<script type="text/javascript">
var runId=<%=runId %>;
 //初始化
 function doInit(){
	 var url=contextPath+"/flowRunDocController/getAllDocVersionByRunId.action";
	 var json=tools.requestJsonRs(url,{runId:runId});	
	 if(json.rtState){
		 var data=json.rtData;
		 var html=[];
		 if(data!=null&&data.length>0){
			 for(var i=0;i<data.length;i++){
				 html.push("<tr class=\"TableData\">");
				 html.push("<td style=\"text-indent:3px\">"+data[i].versionNo+"</td>");
				 html.push("<td>"+data[i].createUserName+"</td>");
				 html.push("<td>"+data[i].createTimeStr+"</td>");
				 html.push("<td><a href=\"#\" onclick=\"view("+data[i].attachId+")\">查看</a></td>");
				 html.push("</tr>");
			 }
		 }
		 
		 $("#tb").append(html.join(""));
	 }
 }
 
 
 //查看
 function view(attachId){
	var  url="/system/core/ntko/indexNtko.jsp?attachmentId="+attachId+"&&op=7";
	openFullWindow(url);
 }
</script>
</head>

<body onload="doInit();" style="padding-left: 10px;padding-right: 10px">
   <div class="topbar clearfix">
      <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/core/workflow/flowrun/prcs/images/icon_lsbb.png">
		<span class="title">正文历史版本</span>
	</div>
   </div>
   <table  width="90%" class="TableBlock_page"  style="border: 2px solid rgb(221, 221, 221); border-image: none;margin-top: 10px" id="tb">
      <tr class="TableHeader" style="background-color: rgb(232, 236, 249);" >
         <td style="text-indent: 3px">版本号</td>
         <td>创建人</td>
         <td>创建时间</td>
         <td>操作</td>
      </tr>
   </table>
</body>
</html>