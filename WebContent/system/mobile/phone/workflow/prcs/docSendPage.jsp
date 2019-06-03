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
<title>公文分发</title>
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
	var sendDeptIds = $("#sendDeptIds");
	if(sendDeptIds.val()==""){
		Alert("请选择分发部门");
		return;
	}
	
	
	//获取发送的内容权限
	var content=$(".sendContent");
	var contentPriv=0;
	for(var i=0;i<content.length;i++){
		if($(content[i]).prop("checked")==true){
			contentPriv=contentPriv+parseInt($(content[i]).val());
		}
	}
	if(contentPriv==0){
		Alert("请至少选择一项发送内容！");
		return;
	}

	//是否消息提醒
	var isRecRemind="";
	if($("#isRecRemind").attr("checked")=="checked"){
		isRecRemind="1";	
	}else{
		isRecRemind="0";
	}
	
	//公文分发的 时候拼接部门打印份数和是否允许下载的json字符串
	var deptArray=$(".deptClazz");
	var str="[";
	var deptId=0;
	var printNum=0;
	var isDownLoad=0;
	for(var i=0;i<deptArray.length;i++){
		deptId=deptArray[i].children[0].title;
		if(deptArray[i].children[1].children[0].value!=null&&deptArray[i].children[1].children[0].value!=""){
			printNum=deptArray[i].children[1].children[0].value;
		}else{
			printNum=0;
		}
		
		if(deptArray[i].children[2].children[0].checked==true){
			isDownLoad=1;	
		}else{
			isDownLoad=0;	
		} 
		if(i==deptArray.length-1){
			str+="{deptId:"+deptId+",printNum:"+printNum+",isDownLoad:"+isDownLoad+"}";	
		}else{
			str+="{deptId:"+deptId+",printNum:"+printNum+",isDownLoad:"+isDownLoad+"},";
		}
		
	}
	   str+="]";
	
	var url = contextPath+"/doc/sendDoc.action";
	$("button").attr("disabled","");
	$.ajax({
		  type: 'POST',
		  url: url,
		  data: {runId:runId,sendDeptIds:sendDeptIds.val(),isRecRemind:isRecRemind,jsonStr:str,contentPriv:contentPriv},
		  timeout: 6000,
		  success: function(data){
			  var json = eval('(' + data + ')');
			  if(json.rtState){
				  	Alert("公文分发完毕");
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


function showDeptTab(id,name,optType){
	if(optType=="ADD_ITEM"){//增加
		var dId="deptTr_"+id;
		$("#deptTabdelivery").append("<tr id="+dId+" class='deptClazz'>"+
				"<td class='TableData' style='text-align: center;' title="+id+">"+name+"</td>"+
				"<td class='TableData' style='text-align: center;'><input type='number' class='delivertPrintNum' style='width: 40px;height: 20px;font-size:12px;border:1px solid gray;margin-top:16px' /></td>"+
				"<td class='TableData' style='text-align: center;'><input type='checkbox' class='deliveryDownLoad'/></td>"+
		        "</tr>");	
	}else if(optType=="REMOVE_ITEM"){//移除
	   $("#deptTr_"+id).remove();
	}
}


//批量设置打印的份数
function setBatchDeliveryPrintNum(){
	//获取批量打印数量的数值
	var batchPrintNum=$("#deliveryBatchPrintNum")[0].value;
	if(batchPrintNum==""||batchPrintNum==null){
		batchPrintNum=0;
	}
	//获取所有部门打印分数的input标签
	var deliveryPrintNumArray=$(".delivertPrintNum");
	for(var i=0;i<deliveryPrintNumArray.length;i++){
		deliveryPrintNumArray[i].value=batchPrintNum;
	}
}


//批量设置是否下载
function  setBatchDeliveryDownLoad(){
	//获取所有部门是否可以下载的复选框数组
	var deliveryDownLoadArry=$(".deliveryDownLoad");
	//获取是否批量下载
	if($("#deliveryBatchDownLoad")[0].checked==true){
		for(var i=0;i<deliveryDownLoadArry.length;i++){			
			deliveryDownLoadArry[i].checked=true;
		}	
	}else{
		for(var i=0;i<deliveryDownLoadArry.length;i++){			
			deliveryDownLoadArry[i].checked=false;
		}		
	}
	
}
</script>
<style>

</style>
</head>
<body onload="doInit()" style="font-size:12px">
<div style='padding:5px;margin-top:5px;border-top:1px solid #e2e2e2;border-bottom:1px solid #e2e2e2;color:#428bca;font-weight:bold;background:white'>
	
	<p>选择要发送的内容:
	    <div style="margin-top: 10px;margin-bottom: 10px">
	    <input type="checkbox" value="1" class="sendContent"/>表单&nbsp;&nbsp;&nbsp;
	    <input type="checkbox" value="16" class="sendContent"/>签批单&nbsp;&nbsp;&nbsp;
	    <input type="checkbox" value="2" class="sendContent"/>正文&nbsp;&nbsp;&nbsp;
	    <input type="checkbox" value="4" class="sendContent"/>版式正文&nbsp;&nbsp;&nbsp;
	    <input type="checkbox" value="8" class="sendContent"/>附件
	    </div>
	</p>
	
	<p>请选择分发部门：
	<textarea type="text" id="sendDeptNames" style="height:100px" name="sendDeptNames" readonly placeholder="选择分发部门" onclick="selectDept('sendDeptIds','sendDeptNames','showDeptTab')"></textarea>
	<input type="hidden" id="sendDeptIds" name="sendDeptIds"/>
	<input type="checkbox"  id="isRecRemind" name="isRecRemind"/>&nbsp;&nbsp;签收是否进行消息提醒</br>
	</p>
	
	<table border="1" style="width: 100%;border-top:1px solid #e2e2e2;border-bottom:1px solid #e2e2e2;border-left:1px solid #e2e2e2;border-right: 1px solid #e2e2e2; ">
	   <thead>
	     <tr>
	       <td align="center" valign="middle">部门名称</td>
	       <td align="center" valign="middle">
	       		打印份数&nbsp;&nbsp;<input type="number" id="deliveryBatchPrintNum" style="width: 40px;height: 20px;font-size:12px;border:1px solid gray;margin-top:16px"/>
		        <a  onclick="setBatchDeliveryPrintNum();"/>批量设置</a>
		   </td>
	       <td align="center" valign="middle">是否可以下载&nbsp;&nbsp;<input type="checkBox" id="deliveryBatchDownLoad" onclick="setBatchDeliveryDownLoad();"</td>
	     </tr>
	   </thead>
	   <tbody id="deptTabdelivery">
		   
	   </tbody>
	</table>
</div>
<center>
	<button class="btn btn-default"  onclick="history.go(-1)">返回</button>
	<button class="btn btn-primary"  onclick="commit()">确定</button>
</center>
</body>
</html>
