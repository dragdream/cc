<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
//项目主键
  String  uuid=TeeStringUtil.getString(request.getParameter("uuid"));
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>汇总报表</title>
</head>
<script>
var uuid="<%=uuid%>";
//初始化 
function doInit(){
	getBasicInfo();
	getTaskList();
	getQuestionList();
	getNotationList();
}

//获取项目的基本信息
function getBasicInfo(){
	var url=contextPath+"/projectController/getBasicInfoBySid.action";
	var json=tools.requestJsonRs(url,{uuid:uuid});
	if(json.rtState){
		var data=json.rtData;
		if(data.approverName!=""&&data.approverName!=null){
			$("#approverTr").show();
		}else{
			$("#approverTr").hide();
		}
		bindJsonObj2Cntrl(data);
	}
}

//根据项目主键 获取项目任务
function getTaskList(){
	var url=contextPath+"/taskController/getTasksByProjectId.action";
	var json=tools.requestJsonRs(url,{projectId:uuid});
	if(json.rtState){
		var data=json.rtData;
		if(data.length>0){
			for(var i=0;i<data.length;i++){	
			    var html="<div style=\"width:100%\">"
			    +"<table width=\"100%\">"
			    +"<tr><td width=\"15%\" style=\"text-indent:20px;font-weight:bold;\">任务名称：</td><td style=\"font-weight:bold;\">"+data[i].taskName+"</td></tr>"
				+"<tr><td style=\"text-indent:20px\">任务执行人：</td><td >"+data[i].managerName+"</td></tr>"
				+"<tr><td style=\"text-indent:20px\">计划开始时间：</td><td>"+data[i].beginTimeStr+"</td></tr>"
				+"<tr><td style=\"text-indent:20px\">计划结束时间：</td><td>"+data[i].endTimeStr+"</td></tr>"
				+"<tr><td style=\"text-indent:20px\">任务工期：</td><td>"+data[i].days+"</td></tr>"
				+"<tr><td style=\"text-indent:20px\">任务等级：</td><td>"+data[i].taskLevel+"</td></tr>"
				+"<tr><td style=\"text-indent:20px\">任务状态：</td><td>"+data[i].status+"</td></tr>"
				+"</table>";
			    if(i==(data.length-1)){
			    	html+="</div><br>";
			    }else{
			    	html+="</div><br><br>";
			    }
			    
			    $("#taskList").append(html);
			}
		}else{
			messageMsg("暂无任何数据！","taskList","info" );
		}
		
	}
}


//获取项目问题列表
function  getQuestionList(){
	var url=contextPath+"/projectQuestionController/getQuestionsByProjectId.action";
	var json=tools.requestJsonRs(url,{projectId:uuid});
	var  data=json.rtData;
	if(data.length>0){
		for(var i=0;i<data.length;i++){	
		    var html="<div style=\"width:100%\">"
		    +"<table width=\"100%\">"
		    +"<tr><td width=\"15%\" style=\"text-indent:20px;font-weight:bold;\">问题名称：</td><td style=\"font-weight:bold;\">"+data[i].questionName+"</td></tr>"
			+"<tr><td style=\"text-indent:20px\">提交人：</td><td >"+data[i].createrName+"</td></tr>"
			+"<tr><td style=\"text-indent:20px\">处理人：</td><td>"+data[i].operatorName+"</td></tr>"
			+"<tr><td style=\"text-indent:20px\">问题描述：</td><td>"+data[i].questionDesc+"</td></tr>";
			
			var status=data[i].status;
			var statusDesc="";
			if(status==0){
				statusDesc="待处理";
			}else{
				statusDesc="已处理";
			}
			
			html+="<tr><td style=\"text-indent:20px\">问题状态：</td><td>"+statusDesc+"</td></tr>"
			+"<tr><td style=\"text-indent:20px\">创建时间：</td><td>"+data[i].createTimeStr+"</td></tr>"
			
			var handleTime=data[i].handleTimeStr;
			var result=data[i].result;
			if(handleTime==null){
				handleTime="";
			}
			if(result==null){
				result="";
			}
			html+="<tr><td style=\"text-indent:20px\">处理时间：</td><td>"+handleTime+"</td></tr>"
			+"<tr><td style=\"text-indent:20px\">处理结果：</td><td>"+result+"</td></tr>"
			+"</table>";
		    if(i==(data.length-1)){
		    	html+="</div><br>";
		    }else{
		    	html+="</div><br><br>";
		    }
		    $("#questionList").append(html);
		}
	}else{
		messageMsg("暂无任何数据！","questionList","info" );
	}
	
	
}

//获取项目批注
function  getNotationList(){
	var url=contextPath+"/projectNotationController/getNotationsByProjectId.action";
	var json=tools.requestJsonRs(url,{projectId:uuid});
	if(json.rtState){
		var data=json.rtData;
		if(data.length>0){
			for(var i=0;i<data.length;i++){	
			    var html="<div style=\"width:100%\">"
			    +"<table width=\"100%\">"
				+"<tr><td width=\"15%\" style=\"text-indent:20px\">批注人：</td><td >"+data[i].createrName+"</td></tr>"
				+"<tr><td style=\"text-indent:20px\">批注时间：</td><td>"+data[i].createTimeStr+"</td></tr>"
				+"<tr><td style=\"text-indent:20px\">批注内容：</td><td>"+data[i].content+"</td></tr>"
				+"</table>";
			    if(i==(data.length-1)){
			    	html+="</div><br>";
			    }else{
			    	html+="</div><br><br>";
			    }
			    
			    $("#notationList").append(html);
			}
		  }else{
				messageMsg("暂无任何数据！","notationList","info" );
			}
		}
		
}
</script>
<body onload="doInit()">
    <div style="width: 80%;margin:0 auto;" >
    
     <div align="center" style="margin-top: 15px">
        <span id="projectName" name="projectName" style="font-size: 14px;font-weight: bold;text-align: center;"></span>
        <br></br>
        <span  style="font-size: 12px;text-align: center;">负责人：<span id="managerName" name="managerName"></span></span>
     </div>
     <table class="TableBlock_page" cellpadding="0" cellspacing="0" style="background-color: #e8ecf9;margin-top: 10px;border:1px solid #DDDDDD;border-collapse: initial;margin-bottom: 10px">
	   <tr>
		<TD class=TableHeader colSpan=2 noWrap style="text-indent:5px">
		  <img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;"/>
		  <B style="color: #0050aa">基本信息</B></TD>
	   </tr>
	   <tr>
	      <td style="text-indent: 20px" width="15%">项目编号：</td>
	      <td id="projectNum" name="projectNum"></td>
	   </tr>
	   <tr>
	      <td style="text-indent: 20px">项目类别：</td>
	      <td id="projectTypeName" name="projectTypeName"></td>
	   </tr>
	   <tr>
	      <td style="text-indent: 20px">项目级别：</td>
	      <td id="projectLevel" name="projectLevel"></td>
	   </tr>
	   <tr>
	      <td style="text-indent: 20px">项目计划周期：</td>
	      <td id="time" name="time"></td>
	   </tr>
	   
	   
       <tr>
		 <TD class=TableHeader colSpan=2 noWrap style="text-indent:5px">
		 <img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;"/>
		 <B style="color: #0050aa">干系人</B></TD>
	   </tr>
	   <tr>
	      <td style="text-indent: 20px">项目创建人：</td>
	      <td id="createrName" name="createrName"></td>
	   </tr>
	   
	   <tr id="approverTr" style="display: none">
	      <td style="text-indent: 20px">项目审批人：</td>
	      <td id="approverName" name="approverName"></td>
	   </tr>
	   <tr>
	      <td style="text-indent: 20px">项目成员：</td>
	      <td id="projectMemberNames" name="projectMemberNames"></td>
	   </tr>
	   <tr>
	      <td style="text-indent: 20px">项目观察者：</td>
	      <td id="projectViewNames" name="projectViewNames"></td>
	   </tr>
	   <tr>
		  <TD class=TableHeader colSpan=2 noWrap style="text-indent:5px">
		  <img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;"/>
		  <B style="color: #0050aa">总预算资金</B></TD>
	   </tr>
	   <tr>
	      <td style="text-indent: 20px">预算金额：</td>
	      <td><span id="projectBudget" name="projectBudget">&nbsp;</span>元</td>
	   </tr>
	   <tr>
		  <TD class=TableHeader colSpan=2 noWrap style="text-indent:5px">
		  <img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;"/>
		  <B style="color: #0050aa">任务信息</B></TD>
	   </tr>
	   <tr>
          <td colspan="2">
             <div id="taskList">
             </div>
          </td>
	   </tr>
	   
	   <tr>
		  <TD class=TableHeader colSpan=2 noWrap style="text-indent:5px">
		  <img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;"/>
		  <B style="color: #0050aa">问题追踪</B></TD>
	   </tr>
	   <tr>
          <td colspan="2">
             <div id="questionList">
             </div>
          </td>
	   </tr>
	   <tr>
		  <TD class=TableHeader colSpan=2 noWrap style="text-indent:5px">
		  <img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;"/>
		  <B style="color: #0050aa">项目批注</B></TD>
	   </tr>
	   <tr>
          <td colspan="2">
             <div id="notationList">
             </div>
          </td>
	   </tr>
    </table>      
   </div>
    
</body>
</html>