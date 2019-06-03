<%@page import="com.tianee.webframe.util.str.TeeStringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%

   int runId=TeeStringUtil.getInteger(request.getParameter("runId"),0);
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<title>历史步骤</title>
<script type="text/javascript">
var runId=<%=runId %>;
//初始化方法
function doInit(){
	
	//渲染历史步骤表格
	render();
	
	
}


//渲染历史步骤表格
function render(){
	var url=contextPath+"/flowRun/getMyHistoryStepByRunId.action";
	var json=tools.requestJsonRs(url,{runId:runId});
	var render=[];
	if(json.rtState){
		var data=json.rtData;
		if(data!=null&&data.length>0){
			for(var i=0;i<data.length;i++){
				render.push("<tr class='TableData' align='center'>");
				render.push("<td style='text-indent:5px'><a href=\"#\" onclick=\"view("+data[i].sid+")\">第"+data[i].prcsId+"步&nbsp;&nbsp;"+data[i].prcsName+"</a></td>");
				render.push("<td>"+data[i].endTimeDesc+"</td>");
				//1：未接收  2：办理中  3和4：已办结  5：待处理
				if(data[i].flag==1){//未接收
					render.push("<td style='color:gray'>未接收</td>");
				}else if(data[i].flag==2){//办理中 
					render.push("<td style='color:green'>办理中</td>");
				}else if(data[i].flag==3||data[i].flag==4){//已办结
					render.push("<td style='color:red'>已办结</td>");
				}else if(data[i].flag==5){//待处理
					render.push("<td style='color:gray'>待处理</td>");
				}
			}
			
			$("#tb").append(render.join(""));
		}
	}
}


//查看详情
function view(frpSid){
	var url = contextPath+"/workflow/toView.action?runId="+runId+"&view=1&frpSid="+frpSid;
	openFullWindow(url,"工作详情");
}

</script>
</head>
<body onload="doInit();" style="padding-top: 10px;background-color: #f2f2f2">
   <table id="tb" style="border:#dddddd 2px solid;" width="100%" class="TableBlock_page">
       <tr class='TableHeader' style='background-color:#e8ecf9'>
           <td style='text-indent:5px'>步骤</td>
           <td>办结时间</td>
           <td>状态</td> 
       </tr>
   </table>
</body>
</html>