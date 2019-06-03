<%@page import="com.tianee.webframe.util.str.TeeStringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
   //任务发布记录项
   int taskPubRecordItemId=TeeStringUtil.getInteger(request.getParameter("taskPubRecordItemId"),0);
   //任务模板名称
   String taskTemplateName=TeeStringUtil.getString(request.getParameter("taskTemplateName"));
   //任务模板主键
   int taskTemplateId=TeeStringUtil.getInteger(request.getParameter("taskTemplateId"),0);
   //该条数据 创建人主键
   int createUserId=TeeStringUtil.getInteger(request.getParameter("createUserId"),0);
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>汇报详情</title>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<script type="text/javascript">
var taskPubRecordItemId=<%=taskPubRecordItemId %>;
var taskTemplateName="<%=taskTemplateName %>";
var taskTemplateId="<%=taskTemplateId %>";
var  createUserId=<%=createUserId %>;
//初始化
function doInit(){
	//获取汇报基础信息
	getRepDataByRecordItemId();
	//获取历史汇报信息
	getHistoryReportList();
	
	
}


//获取汇报的基础信息
function getRepDataByRecordItemId(){
	var url=contextPath+"/TeeTaskPubRecordItemController/getRepDataByRecordItemId.action";
	var json=tools.requestJsonRs(url,{taskPubRecordItemId:taskPubRecordItemId});
	if(json.rtState){
		var dataList=json.rtData[0];
		var attList=json.rtData[1];
		//基础信息
		if(dataList!=null&&dataList.length>0){
			var render=[];
			for(var i=0;i<dataList.length;i++){
				render.push("<tr><td style=\"text-indent:15px;\" nowrap >"+dataList[i].fieldName+":</td><td>"+dataList[i].fieldValue+"</td></tr>");
			}
			$("#table1").append(render.join(""));
		}else{
			var render=[];
			render.push("<tr><td colspan=\"2\"><div id=\"mess\"></div></td></tr>");
			$("#table1").append(render.join(""));
		    messageMsg("该任务模板暂且没有基础信息！", "mess","info");
		}
		//附件信息
		if(attList!=null&&attList.length>0){
			$.each(attList,function(i,v){
				v['priv'] = 3;//查看、下载
				var attachElement = tools.getAttachElement(v,{});
				$("#attachList").append(attachElement);
		    });
		}else{
			$("#attTr").hide();
			var render=[];
			render.push("<tr><td><div id=\"mess1\"></div></td></tr>");
			$("#table2").append(render.join(""));
		    messageMsg("暂无附件信息！", "mess1","info");
		}
	}
}


//获取历史汇报信息
function getHistoryReportList(){
    var url=contextPath+"/TeeTaskPubRecordItemController/getHistoryReportListByUserId.action?taskTemplateId="+taskTemplateId+"&taskPubRecordItemId="+taskPubRecordItemId+"&createUserId="+createUserId;
    var json=tools.requestJsonRs(url);
    if(json.rtState){
    	var data=json.rtData;
    	if(data!=null&&data.length>0){
    		var render=[];
    		for(var i=0;i<data.length;i++){
    			render.push("<tr><td style=\"text-indent:15px;\"><a href=\"#\" onclick=\"detail("+data[i].sid+")\">"+data[i].createTimeStr+"</a></td></tr>");
    		}
    		$("#table3").append(render.join(""));
    	}else{
    		var render=[];
			render.push("<tr><td><div id=\"mess2\"></div></td></tr>");
			$("#table3").append(render.join(""));
		    messageMsg("暂无历史汇报信息！", "mess2","info");
    	}
    }	
}


//历史信息  查看详情
function detail(sid){
	window.location.href=contextPath+"/system/subsys/informationReport/query/info.jsp?taskPubRecordItemId="+sid+"&taskTemplateName="+taskTemplateName+"&taskTemplateId="+taskTemplateId+"&createUserId="+createUserId;
}
</script>
</head>
<body onload="doInit();" style="padding-left: 10px;padding-right: 10px;">
    <div class="topbar clearfix" id="toolbar">
	   <div class="fl" style="position:static;">
			<img id="img1" class = 'title_img' src="<%=contextPath %>/system/subsys/informationReport/imgs/icon_hbxq.png">
			<span class="title"><%=taskTemplateName %>&nbsp;-&nbsp;汇报详情</span>
		</div>
	</div> 
	<table class="TableBlock_page" id="table1">
        <tr>
           <td colspan="2">
              <img src="/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;">
		      <b style="color: #0050aa">基础信息</b>
           </td>
        </tr> 
     </table>
	<table class="TableBlock_page" id="table2">
        <tr>
           <td>
              <img src="/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;">
		      <b style="color: #0050aa">附件信息</b>
           </td>
        </tr>
        <tr id="attTr">
           <td>
              <div id="attachList" ></div>
           </td>
        </tr>
     </table>
	 <table class="TableBlock_page" id="table3">
        <tr>
           <td>
              <img src="/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;">
		      <b style="color: #0050aa">历史汇报信息</b>
           </td>
        </tr>
       
     </table>
	
	
	
	
	
	 
</body>
<script>
	$("#attachList").css("margin-left","15px");
</script>
</html>