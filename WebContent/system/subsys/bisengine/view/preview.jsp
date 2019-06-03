<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
String identity=TeeStringUtil.getString(request.getParameter("identity"));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>视图预览</title>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath %>/common/My97DatePicker/WdatePicker.js"></script>
<style>
</style>
<script>
var identity="<%=identity%>";
function doInit(){
	var url=contextPath+"/bisView/dflist.action";
	var json=tools.requestJsonRs(url,{dfid:identity});
	if(json.rows){
		var data=json.rows;
		if(data.length>0){
			//取出表头
			//alert(tools.jsonObj2String(data[0]));
			var html="";
			html+="<table id='tbody' style='border:#dddddd 2px solid;' width='100%' class='TableBlock_page'>"
					+"<tr class='TableHeader' style='background-color:#e8ecf9' >";
			var firstData = data[0];
			for(var key in firstData){
				html+="<td  style='padding:5px;' nowrap>"+key+"</td>";
			}
			html+="</tr>";
			
			for(var i=0;i<data.length;i++){
				html+="<tr class='TableData' align='center'>";
				for(var key in data[i]){
					html+="<td style='padding:5px;' nowrap>"+data[i][key]+"</td>";
				}
				
	  		    html+="</tr>";
			}
			
			$("#data").html(html);
		}else{
			messageMsg("暂无数据！", "data","info");	
		}
	}else{
		messageMsg(json.rtMsg, "data","info");
	}
	
	
}



</script>
</head>
<body onload="doInit()" style="font-size:12px;padding-left: 10px;padding-right: 10px;">
   <div id="data" style="margin-top: 10px">
   
   </div>
</body>
</html>