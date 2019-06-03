<%@page import="com.tianee.webframe.util.str.TeeStringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
   String weekday=TeeStringUtil.getString(request.getParameter("weekday"));
   int attendConfig= TeeStringUtil.getInteger(request.getParameter("attendConfig"),0);
   //编辑的行Id 
   String trId=TeeStringUtil.getString(request.getParameter("trId"));
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<title>设置班次</title>
<script type="text/javascript">
var weekday="<%=weekday %>";
var attendConfig=<%=attendConfig %>;
var trId="<%=trId %>";
//初始化
function doInit(){
	//渲染排班类型
	renderAttendConfig();
	if(attendConfig!=0){
		$("#attendConfig").val(attendConfig);
		changeAttendConfig();
	}
	
	
}

//渲染排班类型
function renderAttendConfig(){
	var json = tools.requestJsonRs(contextPath+"/TeeAttendConfigController/getConfig.action");
	for(var i=0;i<json.rtData.length;i++){
		$("#attendConfig").append("<option value="+json.rtData[i].sid+" >"+json.rtData[i].dutyName+"</option>");
	}
	
	changeAttendConfig();
}

//修改排班类型
function changeAttendConfig(){
	var attendConfig=$("#attendConfig").val();
	
    var url=contextPath+"/TeeAttendConfigController/getById.action?sid="+attendConfig;
    var json=tools.requestJsonRs(url,null);
    
    if(json.rtState){
    	var data=json.rtData;
    	var attendConfigTime1="";
    	var attendConfigTime2="";
    	var attendConfigTime3="";
    	if(data.dutyTimeDesc1!=""&&data.dutyTimeDesc1!=null&&data.dutyTimeDesc2!=""&&data.dutyTimeDesc2!=null){
    		attendConfigTime1=data.dutyTimeDesc1+"-"+data.dutyTimeDesc2;
    	}
    	if(data.dutyTimeDesc3!=""&&data.dutyTimeDesc3!=null&&data.dutyTimeDesc4!=""&&data.dutyTimeDesc4!=null){
    		attendConfigTime2=data.dutyTimeDesc3+"-"+data.dutyTimeDesc4;
    	}
    	if(data.dutyTimeDesc5!=""&&data.dutyTimeDesc5!=null&&data.dutyTimeDesc6!=""&&data.dutyTimeDesc6!=null){
    		attendConfigTime3=data.dutyTimeDesc5+"-"+data.dutyTimeDesc6;
    	}
    	
    	$("#attendConfigTime1").text(attendConfigTime1);
    	$("#attendConfigTime2").text(attendConfigTime2);
    	$("#attendConfigTime3").text(attendConfigTime3);
    }
}


//保存
function doSave(){
	var attendConfig=$("#attendConfig").val();
	var attendConfigTime1=$("#attendConfigTime1").text();
	var attendConfigTime2=$("#attendConfigTime2").text();
	var attendConfigTime3=$("#attendConfigTime3").text();
	
	parent.$("#"+trId).find("input:eq(0)").val(attendConfig);
	parent.$("#"+trId).find("td:eq(1)").html(attendConfigTime1);
	parent.$("#"+trId).find("td:eq(2)").html(attendConfigTime2);
	parent.$("#"+trId).find("td:eq(3)").html(attendConfigTime3);
}
</script>
</head>
<body onload="doInit()" style="background-color: #f2f2f2">
    <table class="TableBlock" width="100%">
        <tr>
            <td style="text-indent: 10px;width: 30%">考勤周期：</td>
            <td>
               <span><%=weekday %></span>
            </td>
        </tr>
        <tr>
            <td style="text-indent: 10px">排班类型：</td>
            <td>
               <select id="attendConfig" name="attendConfig" onchange="changeAttendConfig()">
               
               </select>
            </td>
        </tr>
        <tr>
            <td style="text-indent: 10px">班次时间：</td>
            <td>
               <span id="attendConfigTime1" name="attendConfigTime1"></span>
               &nbsp;&nbsp;
               <span id="attendConfigTime2" name="attendConfigTime2"></span>
               &nbsp;&nbsp;
               <span id="attendConfigTime3" name="attendConfigTime3"></span>
            </td>
        </tr>
    </table>  
</body>
</html>