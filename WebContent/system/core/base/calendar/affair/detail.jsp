<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	int id = TeeStringUtil.getInteger(request.getParameter("id"), 0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header2.0.jsp" %>
<title>查看日程</title>
<script type="text/javascript">

function doInit(){
	var id = "<%=id%>";
	var url =  contextPath +  "/affairManage/selectById.action";
	var para = {sid:id};
	var jsonObj = tools.requestJsonRs(url,para);
	//alert(jsonObj.rtState);
	if(jsonObj.rtState){
		  var prc = jsonObj.rtData;
		  var seqId = prc.seqId;
		  var type = prc.remindType;
		  var beginTime = prc.startTimeStr;
		  var endTime = prc.endTimeStr;
		  var remindTime = prc.remindTimeStr;
		  var managerName = prc.managerName;
		  var content = prc.content;
		  var typeNames = ["每日","每周","每月","每年"];
		  var weekNames = ["一","二","三","四","五","六","日"];
		  var week_day_month = '';
		  var time = beginTime.substr(0,19)+" 至 " + endTime;
		  if(type=='3'){
		      week_day_month = weekNames[parseInt(prc.remindDate , 10)-1];  
		  }
		  if(type=='4'){
		      week_day_month= prc.remindDate+'日';
		  }
		  if(type=='5'){
		      week_day_month = prc.remindDate.split('-')[0]+'月'+prc.remindDate.split('-')[1]+'日';
		  }
		  if(managerName && managerName!=''){
		      managerName = "<b><br>安排人：("+managerName+")</b>";
		  }
		    document.getElementById("time").innerHTML = time;
		   document.getElementById("remindTime").innerHTML = typeNames[type-2] + week_day_month +" " + remindTime;
		    document.getElementById("content").innerHTML = (content || "").replace(/\r\n/g, "<br/>").replace(/\n/g, "<br/>").replace(/\r/g, "<br/>");
		    document.getElementById("managerName").innerHTML = managerName;
/* 		    document.getElementById("calLevel").innerHTML = calLevelNames[calLevel];
		    document.getElementById("calLevel").className = 'CalLevel'+calLevel; */
		    document.getElementById("content").innerHTML = (content || "").replace(/\r\n/g, "<br/>").replace(/\n/g, "<br/>").replace(/\r/g, "<br/>");
			if(prc.actorNames){
				$("#actorNames").html("<b>参与人:</b> " + prc.actorNames);
			}
		    
		    
		//callback(json);
	}else{
		$.MsgBox.Alert_auto(jsonObj.rtMsg);
	}
	
}	
</script>
</head>
<body onload="doInit();" style="padding:10px 10px 0px 10px;background-color: #f2f2f2;">
    <table class="TableBlock" width="100%" align="center">
	<tr><span id="time" style="font-size:14px;"></span><br></br></tr>
	<tr><span id="remindTime" style="font-size:14px;"></span><br></br></tr>
	
	<div id="managerName"></div>
	
	<div id="actorNames"></div>
	
	<span id="userName"></span>
	 <!-- <span id="calLevel" >不重要/紧急</span>&nbsp; -->
	<font id="fontcolor" color='' style="margin-left:20px;"><b id="status"></b></font><hr>
	
	<tr><span id="content" style="font-size:14px;"></span></tr>

    </table>
	<!-- <div align="center" style="margin-top:20px;">
		<input type="button" value="关闭" class="btn btn-primary" onclick="top.$.jBox.close();">&nbsp;&nbsp;
	</div>				 -->
</body>
</html>
