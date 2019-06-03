<%@page import="com.tianee.webframe.util.str.TeeStringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
   int itemId=TeeStringUtil.getInteger(request.getParameter("itemId"), 0);
   int runId=TeeStringUtil.getInteger(request.getParameter("runId"),0);

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>会签详情</title>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<script type="text/javascript">
var itemId=<%=itemId %>;
var runId=<%=runId %>;

function doInit(){
	var url=contextPath+"/feedBack/getFeedBackByRunIdAndItemId.action";
	var json=tools.requestJsonRs(url,{itemId:itemId,runId:runId});
	if(json.rtState){
		var data=json.rtData;
		var render=[];
		if(data!=null&&data.length>0){
			for(var i=0;i<data.length;i++){
				render.push("<tr class='TableData'>");
				render.push("<td style='text-indent: 15px'>"+data[i].content+"</td>");
				render.push("<td>"+data[i].createUserName+"</td>");
				render.push("<td>"+data[i].createTimeStr+"</td>");
				render.push("</tr>");
			}
			$("#tb").append(render.join(""));
		}else{
			$("#tb").append("<tr><td colspan='3'><div id='mess'></div></td></tr>");
			 messageMsg("暂无会签意见！", "mess","info");
		}
	}
	
	
}


</script>



</head>

<body onload="doInit();">
    <table id="tb" class="TableBlock_page" style='border:#dddddd 2px solid;margin-top:3px;margin-left:5px;width:98%' >
        <tr  class='TableHeader' style='background-color:#e8ecf9'>
             <td style="text-indent: 15px" width="34%" nowrap="nowrap">会签内容</td>
             <td width="33%" nowrap="nowrap">会签人</td>
             <td width="33%" nowrap="nowrap">会签时间</td>
        </tr>
        
    </table>
</body>
</html>