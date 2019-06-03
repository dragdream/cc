<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"),0);
	int runId = TeeStringUtil.getInteger(request.getParameter("runId"),
			0);
	int flowId = TeeStringUtil.getInteger(request
			.getParameter("flowId"), 0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/system/mobile/mui/header.jsp" %>
<title>公文传阅</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<script type="text/javascript">
var frpSid = <%=frpSid%>;
var runId = <%=runId%>;
var flowId = <%=flowId%>;

function doInit(){
	
}


function commit(){
	var sendUserIds = $("#sendUserIds");
	if(sendUserIds.val()==""){
		Alert("请选择传阅人员");
		return;
	}

	//获取发送的内容权限
	 var content=$(".viewContent");
	 var contentPriv=0;
	 for(var i=0;i<content.length;i++){
		if($(content[i]).prop("checked")==true){
			contentPriv=contentPriv+parseInt($(content[i]).val());
		}
	}
	if(contentPriv==0){
		Alert("请至少选择一项传阅内容！");
		return;
	}
	
	
	//是否消息提醒
	var isReadRemind="";
	if($("#isReadRemind").attr("checked")=="checked"){
		isReadRemind="1";	
	}else{
		isReadRemind="0";
	}
	
	//公文传阅的 时候拼接部门打印份数和是否允许下载的json字符串
	var userArray=$(".userClazz");
	var str="[";
	var userId=0;
	var printNum=0;
	var isDownLoad=0;
	for(var i=0;i<userArray.length;i++){
		userId=userArray[i].children[0].title;
		if(userArray[i].children[1].children[0].value!=null&&userArray[i].children[1].children[0].value!=""){
			printNum=userArray[i].children[1].children[0].value;
		}else{
			printNum=0;
		}
		
		if(userArray[i].children[2].children[0].checked==true){
			isDownLoad=1;	
		}else{
			isDownLoad=0;	
		} 
		if(i==userArray.length-1){
			str+="{userId:"+userId+",printNum:"+printNum+",isDownLoad:"+isDownLoad+"}";	
		}else{
			str+="{userId:"+userId+",printNum:"+printNum+",isDownLoad:"+isDownLoad+"},";
		}
		
	}
	   str+="]";
	
	
	 

	var url = contextPath+"/doc/viewDoc.action";
	$("button").attr("disabled","");
	$.ajax({
		  type: 'POST',
		  url: url,
		  data: {runId:runId,sendUserIds:sendUserIds.val(),isReadRemind:isReadRemind,jsonStr:str,contentPriv:contentPriv},
		  timeout: 6000,
		  success: function(data){
			  var json = eval('(' + data + ')');
			  if(json.rtState){
				  	Alert("公文传阅完毕");
					history.go(-1);
				}else{
					Alert(json.rtMsg);
				}
			  $("button").removeAttr("disabled");
		  },
		  error: function(xhr, type){
		    
		  }
		});
}


function showUserTab(id,name,optType){
	 if(optType=="ADD_ITEM"){//增加
		var uid="userTr_"+id;
		$("#userTabView").append("<tr id="+uid+" class='userClazz'>"+
				"<td class='TableData' style='text-align: center;' title="+id+">"+name+"</td>"+
				"<td class='TableData' style='text-align: center;'><input type='number' class='viewPrintNum' style='width: 40px;height: 20px;font-size:12px;border:1px solid gray;margin-top:15px'/></td>"+
				"<td class='TableData' style='text-align: center;'><input type='checkbox' class='viewDownLoad'/></td>"+
		        "</tr>");	
	}else if(optType=="REMOVE_ITEM"){//移除
	   $("#userTr_"+id).remove();
	} 
}


//批量设置打印的份数
function setBatchViewPrintNum(){
	//获取批量打印数量的数值
	var batchPrintNum=$("#viewBatchPrintNum")[0].value;
	if(batchPrintNum==""||batchPrintNum==null){
		batchPrintNum=0;
	}
	//获取所有部门打印分数的input标签
	var viewPrintNumArray=$(".viewPrintNum");
	for(var i=0;i<viewPrintNumArray.length;i++){
		viewPrintNumArray[i].value=batchPrintNum;
	}
}




//批量设置是否下载
function  setBatchViewDownLoad(){
	//获取所有部门是否可以下载的复选框数组
	var viewDownLoadArry=$(".viewDownLoad");
	//获取是否批量下载
	if($("#viewBatchDownLoad")[0].checked==true){
		for(var i=0;i<viewDownLoadArry.length;i++){			
			viewDownLoadArry[i].checked=true;
		}	
	}else{
		for(var i=0;i<viewDownLoadArry.length;i++){			
			viewDownLoadArry[i].checked=false;
		}		
	}
	
}
</script>
<style>

</style>
</head>
<body onload="doInit()" style="font-size:12px">
<div style='padding:5px;margin-top:5px;border-top:1px solid #e2e2e2;border-bottom:1px solid #e2e2e2;color:#428bca;font-weight:bold;background:white'>
	<p>选择要传阅的内容:
	   <div>
		 <input type="checkbox" value="1" class="viewContent"/>表单&nbsp;&nbsp;&nbsp;
		 <input type="checkbox" value="16" class="viewContent"/>签批单&nbsp;&nbsp;&nbsp;
	     <input type="checkbox" value="2" class="viewContent"/>正文&nbsp;&nbsp;&nbsp;
	     <input type="checkbox" value="4" class="viewContent"/>版式正文&nbsp;&nbsp;&nbsp;
	     <input type="checkbox" value="8" class="viewContent"/>附件
	    </div>
	</p>
	
	<p>请选择传阅人员：
	<textarea type="text" id="sendUserNames" style="height:100px" name="sendUserNames" readonly placeholder="选择传阅人员" onclick="selectUser('sendUserIds','sendUserNames','showUserTab')"></textarea>
	<input type="hidden" id="sendUserIds" name="sendUserIds"/>
	<input type="checkbox"  id="isReadRemind" name="isReadRemind"/>&nbsp;&nbsp;签收是否进行消息提醒</br>
	</p>
	
	
	<table border="1" style="width: 100%;border-top:1px solid #e2e2e2;border-bottom:1px solid #e2e2e2;border-left:1px solid #e2e2e2;border-right: 1px solid #e2e2e2; ">
	   <thead>
	     <tr>
	       <td align="center" valign="middle">部门名称</td>
	       <td align="center" valign="middle">打印份数&nbsp;&nbsp;
	          <input type="number" id="viewBatchPrintNum" style="width: 40px;height:20px;font-size:12px;border:1px solid gray;margin-top: 16px"/> 
		       &nbsp;&nbsp;<a  onclick="setBatchViewPrintNum();"/>批量设置</a>
		        </td>
	       <td align="center" valign="middle">是否可以下载&nbsp;&nbsp;<input type="checkBox" id="viewBatchDownLoad" onclick="setBatchViewDownLoad();"</td>
	     </tr>
	   </thead>
	   <tbody id="userTabView">
		   
	   </tbody>
	</table>
</div>
<center>
	<button class="btn btn-default"  onclick="history.go(-1)">返回</button>
	<button class="btn btn-primary"  onclick="commit()">确定</button>
</center>
</body>
</html>
