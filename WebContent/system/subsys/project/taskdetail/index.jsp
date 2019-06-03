<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%
  //获取任务的主键
  int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
%>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>任务办理</title>
<script>
var sid=<%=sid %>;
var projectUuid="";
var status;//0 进行中   1已完成
function doInit(){
	var url=contextPath+"/taskController/getInfoBySid.action";
	var json=tools.requestJsonRs(url,{sid:sid});
	if(json.rtState){
		
		var data=json.rtData;
		status=data.status;
		projectUuid=data.projectId;
		
		if(status==0){//进行中
			 $.addTab("tab","tab-content",[{title:"任务详情",url:contextPath+"/system/subsys/project/taskdetail/detail.jsp?sid=<%=sid%>"},
			                               {title:"任务汇报",url:contextPath+"/system/subsys/project/taskdetail/taskReport.jsp?sid=<%=sid%>"},
			                               {title:"项目问题",url:contextPath+"/system/subsys/project/taskdetail/question.jsp?sid=<%=sid%>"},
			                               {title:"项目流程",url:contextPath+"/system/subsys/project/taskdetail/projectFlow.jsp?sid=<%=sid%>"},
			                               {title:"项目文档",url:contextPath+"/system/subsys/project/projectdetail/basic/file.jsp?uuid="+projectUuid}
			                               ]); 

		}else{//已结束
			 $.addTab("tab","tab-content",[{title:"任务详情",url:contextPath+"/system/subsys/project/taskdetail/detail.jsp?sid=<%=sid%>"},
			                               {title:"任务汇报",url:contextPath+"/system/subsys/project/taskdetail/taskReport.jsp?sid=<%=sid%>"},
			                               {title:"项目问题",url:contextPath+"/system/subsys/project/taskdetail/question.jsp?sid=<%=sid%>"},
			                               {title:"项目流程",url:contextPath+"/system/subsys/project/taskdetail/projectFlow.jsp?sid=<%=sid%>"},
			                               ]); 

		}
		
	}
}



</script>


</head>
<body onload="doInit();" style="overflow: hidden;padding-left:10px;padding-right:10px;padding-top: 5px">
	
	<div class="titlebar clearfix" >
		<img class = 'tit_img' style="margin-right:10px" src="<%=contextPath %>/system/subsys/project/img/icon_renwubanli.png">
		<p class="title">任务办理</p>
		<ul id = 'tab' class = 'tab clearfix' style='display:inline-block;'>
			
		</ul>
		<div class="right fr clearfix">
		   
		</div>
		<span class="basic_border_grey fl"></span>
	</div>
	  <div id="tab-content" style="padding-left: 10px;padding-right:10px"></div>
	  
</body>
<script>


</script>

</html>