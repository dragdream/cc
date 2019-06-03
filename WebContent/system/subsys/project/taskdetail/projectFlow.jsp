<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
  //任务主键
  int  taskId=TeeStringUtil.getInteger(request.getParameter("sid"), 0);

  //获取当前登录人
  TeePerson  loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>项目流程</title>
</head>
<script>
var taskId=<%=taskId%>;
var loginUserId=<%=loginUser.getUuid()%>;
//初始化
function doInit(){
	isCreaterAndOnProgress();
	//初始化相关流程
	getFlowTypeListByTaskId();
    //获取流程相关数据	
	getFlowRunData();
	
}


//获取与任务相关的流程
function getFlowTypeListByTaskId(){
	var url=contextPath+"/taskController/getFlowTypeListByTaskId.action";
	var json=tools.requestJsonRs(url,{sid:taskId});
	if(json.rtState){
		var data=json.rtData;
		if(data.length>0){
			for(var i=0;i<data.length;i++){
				if(i!=0&&i%5==0){
					$("#flowDiv").append("<br/><br/>");
				}
				$("#flowDiv").append("<input type=\"button\" class=\"btn-win-white\" value="+data[i].flowName+" onclick=\"create("+data[i].sid+")\" />&nbsp;&nbsp;&nbsp&nbsp;&nbsp;");
			}
		}else{
			messageMsg("暂无关联流程！", "mess1","info" );
		}
		
	}
	
	
	
}


//新建流程
function  create(sid){
	  $.MsgBox.Confirm ("提示", "是否确认新建工作？", function(){
		  var url = contextPath + "/flowRun/createNewWork.action";
			var json = tools.requestJsonRs(url, {
				fType :sid,
			});
			if (json.rtState) {
				xparent.openFullWindow(contextPath
						+ "/system/core/workflow/flowrun/prcs/index.jsp?runId="
						+ json.rtData.runId + "&frpSid=" + json.rtData.frpSid
						+ "&flowId=" + sid + "&isNew=1", "流程办理");
				var url1=contextPath+"/projectFlowController/add.action";
				var json1=tools.requestJsonRs(url1,{taskId:taskId,runId:json.rtData.runId});
				if(json1.rtState){
					window.location.reload();
				}
				
			} else {
				$.MsgBox.Alert_auto(json.rtMsg);
			}  
	  });
}


//获取流程数据
function getFlowRunData(){
	//获取流程数据
	var url=contextPath+"/projectFlowController/getFlowRunData.action";
	var json=tools.requestJsonRs(url,{taskId:taskId});
	if(json.rtState){
		var data=json.rtData;
		if(data.length>0){
			for(var i=0;i<data.length;i++){
				var  html="<tr style='line-height:35px;border-bottom:1px solid #f2f2f2;'>"
						+"<td nowrap align='center' ><a href=\"#\" onclick=\"view("+data[i].runId+")\">"+data[i].runName+"</a></td>"
						+"<td nowrap align='center'>" + data[i].flowTypeName + "</td>"
						+"<td nowrap align='center' >" + data[i].createrName+ "</td>"
						+"<td nowrap align='center' >" + data[i].createTimeStr+"</td>"
						+"<td nowrap align='center' >" + data[i].currStep+"</td>";
						
				if(loginUserId==data[i].createrId){
					html+="<td nowrap align='center' >" + "<a href=\"#\" onclick=\"delFlow("+data[i].runId+")\">删除</a>"+"</td>";
				}else{
					html+="<td nowrap align='center' ></td>";
				}		
			  	html+="</tr>";
			  	$("#tbody").append(html);
			}
		}else{
			
			messageMsg("暂无流程数据！", "mess2","info" );
		}
		
	}	
}

//查看流程详情
function view(runId){
	var url=contextPath+"/workflow/view.action?runId="+runId;
	openFullWindow(url);
}

//删除流程数据
function delFlow(runId){	
		$.MsgBox.Confirm ("提示", "确认删除此流程数据吗？", function(){
			var url = contextPath+"/workflowManage/delRun.action";
			var json = tools.requestJsonRs(url,{runId:runId});
			if(json.rtState){
				$.MsgBox.Alert_auto("删除成功！");
				window.location.reload();
			}
		});
}



//判断当前登陆人是不是任务负责人  并且  当前任务的状态是不是进行中 （status=0）
function isCreaterAndOnProgress(){
	var url=contextPath+"/taskController/getInfoBySid.action";
	var json=tools.requestJsonRs(url,{sid:taskId});
	if(json.rtState){
		var data=json.rtData;
		var status=data.status;
		var managerId=data.managerId;
		if(status==0&&managerId==loginUserId){
			$("#flowBtn").show();
		}else{
			$("#flowBtn").hide();
		}
	}

}
</script>



<body onload="doInit()" style="padding-left: 10px;padding-right: 10px;">
<div class="clearfix" style="padding-top: 5px;display: none" id="flowBtn">
	<b style="font-size: 14px">相关流程</b>
	<span class="basic_border" style="padding-top: 5px;"></span>
	<div style="padding-top: 15px;" id="flowDiv">
        
   </div>
   <div id="mess1" style="margin-top: 10px">
   </div>
   <br />
   <br />
</div>

<div class="clearfix" style="padding-top: 10px;" >
	<b style="font-size: 14px">流程数据</b>
	<span class="basic_border" style="padding-top: 5px;"></span>
	<div style="padding-top: 10px;">
        <table class="TableBlock_page" width="100%">
           <tr style="line-height:35px;border-bottom:2px solid #b0deff!important;background-color: #f8f8f8; ">
     	    <td style="text-indent:10px;width:20%;">标题</td>
     	    <td style="width:15%;">类型</td>
      		<td style="width:10%;">创建人</td>
      		<td style="width:10%;">创建日期</td>
      		<td style="width:25%;">流程状态</td>
      		<td style="width:20%;">操作</td>
          </tr>
          <tbody id="tbody">
          
          </tbody>  
        </table>
   </div>
   <div id="mess2" style="margin-top: 20px"></div>
</div>
</body>
</html>