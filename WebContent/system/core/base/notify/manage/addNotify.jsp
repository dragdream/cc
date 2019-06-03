<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    String currDateStr=format.format(new Date());

	int id = TeeStringUtil.getInteger(request.getParameter("id") , 0) ;
	int runId = TeeStringUtil.getInteger(request.getParameter("runId"),0);
	int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"),0);
	int view = TeeStringUtil.getInteger(request.getParameter("view"),0);
%>
<%
String typeId = request.getParameter("typeId");
typeId=typeId==null?"":typeId;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>添加公告</title>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/upload.jsp" %>
<%@ include file="/header/userheader.jsp" %>	
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script src="<%=contextPath %>/common/ckeditor/ckeditor.js"></script>
<script src="<%=contextPath %>/common/ueditor/ueditor.config.js"></script>
<script src="<%=contextPath %>/common/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>
<style>
	.modal-test{
		width: 500px;
		height: 230px;
		position: absolute;
		display: none;
		z-index: 999;
	}
	.modal-test .modal-header{
		width: 100%;
		height: 50px;
		background-color:#8ab0e6;
	}
	.modal-test .modal-header .modal-title{
		color: #fff;
		font-size: 16px;
		line-height:50px;
		margin-left:20px;
		float: left;
	}
	.modal-test .modal-header .modal-win-close{
		color:#fff;
		font-size: 16px;
		line-height:50px;
		margin-right:20px;
		float: right;
		cursor: pointer;
	}
	.modal-test .modal-body{
		width: 100%;
		height: 80px;
		background-color:#f4f4f4;
	}
	.modal-test .modal-body ul{
		overflow: hidden;
		clear:both;
	}
	.modal-test .modal-body ul li{
		width: 510px;
		height: 30px;
		line-height: 30px;
		margin-top: 25px;
		margin-left: 20px;
	}
	.modal-test .modal-body ul li span{
		display: inline-block;
		float:left;
		vertical-align: middle;
	}
	.modal-test .modal-body ul li input{
		display: inline-block;
		float: right;
		width: 400px;
		height: 25px;
	}
	.modal-test .modal-footer{
		width: 100%;
		height: 60px;
		background-color:#f4f4f4;
	}
	.modal-test .modal-footer input{
		margin-top:12px;
		float: right;
		margin-right:20px;
	}
</style>

<script type="text/javascript">
var systemImagePath = "<%=systemImagePath%>";
var typeId = "<%=typeId%>";
var id = "<%=id%>";
var editor;
var uEditorObj;//uEditor编辑器
var runId = <%=runId%>;
var frpSid = <%=frpSid%>;
var view = <%=view%>;
function doInit(){
	//获取审批人员
	getAduingUser();
	uEditorObj = UE.getEditor('content');//获取编辑器对象
	uEditorObj.ready(function(){//初始化方法
	uEditorObj.setHeight(200);
	//如果是工作流转公告，则先获取流程数据，并加载到
	if(runId!=0 || frpSid!=0){
		var url = contextPath+"/flowRun/getFlowRunFormStream.action";
		var json = tools.requestJsonRs(url,{runId:runId,view:view,frpSid:frpSid});
		if(json.rtState){
			var form = json.rtData;
			//$("#content").text(form);
			uEditorObj.setContent(form);
		}
	}
	
	//公告类型
	var prcs = getSysCodeByParentCodeNo("NOTIFY_TYPE" , "typeId");
	
	var ttAttach = new TeeSimpleUploadRender({
		fileContainer:"upfileList"
	});
	
	
	
	

	/* editor = CKEDITOR.replace('content',{
		width : 'auto',
		height:300
	}); */
	if(id > 0){
		loadData(id);
	}else{
		$("#subject")[0].select();
	}
	showPhoneSmsForModule("sms","021",loginPersonId);
	
	
	if(typeId!=""){
		$("#typeId option").each(function(i,obj){
			if(typeId!=$(obj).attr("value")){
				$(obj).remove();
			}
		});
	}
  });
}
/*
 * 新增或者更新
 */
function doSave(publicFlag){
	if (checkForm(publicFlag)){
		
		var para =  {"publish":publicFlag};//tools.formToJson($("#form1")) ;
		 $("#form1").ajaxSubmit({
	           url: "<%=contextPath%>/teeNotifyController/addUpdateNotify.action",
	           iframe: true,
	           data: para,
	           success: function(res) {
	        	 //手机短信
      				var toDeptIds=$("#toDeptIds").val();
      				var toRolesIds=$("#toRolesIds").val();
      				var toUserIds=$("#toUserIds").val();
      				var toUserId="";
	   				var urls="<%=contextPath%>/TeeSmsPhonePrivController/getUserIds.action?toDeptIds="+toDeptIds+"&toRolesIds="+toRolesIds+"&toUserIds="+toUserIds;
	   				var jsonObj = tools.requestJsonRs(urls);
	   				if(jsonObj.rtState){
	   					toUserId=jsonObj.rtData;
	   				}
      				var smsContent=userName+"发布了公告："+$("#subject").val();
      				var sendTime="";
      				/* sendPhoneSms(toUserId,smsContent,sendTime); */
	        	    
	        	   if(runId!=0 || frpSid!=0){//如果是工作流转发，则关闭窗体
	        			CloseWindow();
	        		}
	        	   window.location.href = "manageNotifyList.jsp?typeId="+typeId;
			 		
	                 // ... my success function (never getting here in IE)
	           },
	           error: function(arg1, arg2, ex) {
	                 // ... my error function (never getting here in IE)
	                 $.MsgBox.Alert_auto("添加公告出错！");
	           },
	           dataType: 'json'
	      });
	}
}

/**
 * 检查表单
 */
function checkForm(publicFlag){
   
/*     if($("#toDeptIds")[0].value == '' &&
       $("#toRolesIds")[0].value == '' &&
       $("#toUserIds")[0].value == ''){
    	alert("发布范围不能为空！");
    	return false;
    } */
    
    if($("#subject").val()==""){
    	$.MsgBox.Alert_auto("请填写公告标题");
    	$("#subject").focus();
    	return false;
    }
    if(publicFlag == '2'){
    	var auditer = $("#auditer")[0];
    	if(auditer.value == null  || auditer.value == ''){
    		$.MsgBox.Alert_auto("请选择审批人！");
    		return false;
    	}
    	
    }
    if($("#beginDate").val()==""){
    	$.MsgBox.Alert_auto("请选择公告有效开始时间");
    	//$("#beginDate").focus();
    	return false;
    }
    if(uEditorObj.getContent() == ""){
    	$("#content").focus();
    	$.MsgBox.Alert_auto("请填写公告内容！");
    	return false;
    }
    
    return true;
}

/**
 * 根据Id  获取公告对象
 */
function loadData(id){
	var url = "<%=contextPath%>/teeNotifyController/getNotifyById.action";
	var jsonRs = tools.requestJsonRs(url,{"id":id});
	if(jsonRs.rtState){
		var data = jsonRs.rtData;
		if(data){
			//alert(data.smsRemind);
			try {
				bindJsonObj2Cntrl(data);
				
			} catch (e) {
				// TODO: handle exception
			}
			
			if($("#auditer")[0]){
				$("#auditer").val(data.auditerId);
			}
			var beginDate = getFormatDateStr(data.beginDate , 'yyyy-MM-dd');

			var endDate = getFormatDateStr(data.endDate , 'yyyy-MM-dd');
		/* 	$("#beginDate").val(beginDate);
			$("#endDate").val(endDate); */
			uEditorObj.setContent(data.content);
			$.each(data.attachmentsMode,function(i,v){
				var attachElement = tools.getAttachElement(v,{});
				$("#attachList").append(attachElement);
		    });
		}
		
		
	}else{
		$.MsgBox.Alert_auto(jsonRs.rtMsg);
	}
}

/**
 * 获取审批人
 */
function getAduingUser(){
	var url = "<%=contextPath%>/teeNotifyController/getNotifyAduingPerson.action";
	var para = {};
	var jsonRs = tools.requestJsonRs(url,para);
	if(jsonRs.rtState){
		var data = jsonRs.rtData;
		initPrivButtopn(data.isNeedAdu);
		if(data.isNeedAdu){//需要审批
			createSelect(data.aduListPerson);
		}
	}else{
		$.MsgBox.Alert_auto(jsonRs.rtMsg);
	}
}

function createSelect(rtData){
	var selectObj = document.createElement("select");
	//$("<select name='groupId' ></select>");
	selectObj.setAttribute("name", "auditer");
	selectObj.setAttribute("id", "auditer");
	selectObj.setAttribute("class", "BigSelect");
	if(rtData){
		$.each(rtData,function(i,v){
		     var vOption=document.createElement("option");
		     vOption.setAttribute("value",v.value);
		     vOption.appendChild(document.createTextNode(v.name));
		     selectObj.appendChild(vOption);
		});
	}
	$("#aduingUser").append(selectObj);
}

function initPrivButtopn(priv){
	//需要审批 隐藏 发布按钮
	if(priv){
		$("#sub_shenpi").show();
		$("#sub_pub").hide();
	}else{
		$("#sub_pub").show();
		$("#sub_shenpi").hide();
	}
}


 function commitAudi(object){
	if(checkForm()){
		$(object).modal();
	}
	
} 

</script>
 
</head>

<body style="margin:0px;" onload="doInit();">
<div id="toolbar" class = "clearfix" style="margin-top: 5px;margin-bottom: 5px">
   <div class="fl" style="margin-left: 10px">
       <% 
		  if(id>0){
	   %>
			<h4 style="font-size: 12px;font-weight: bold;vertical-align: middle;">修改信息&nbsp;<font color="red">(修改后需重新发布，进入未发布状态)</font></h4>
		<%}else{ %>
			<h4 style="font-size: 12px;font-weight: bold;vertical-align: middle;">新建信息</h4>
		<%} %>
   </div>
   <div class="right fr">            
			<%if(id>0){%>
	         <input type="button" value="返回" class="btn-win-white" title="返回" onClick="history.go(-1);"/>
	        <% } %>      
			<input type="button" id="sub_pub" value="发布" class="btn-win-white" title="发布" onclick="doSave(1)" />
	        <input type="button" id="sub_save" value="保存" class="btn-win-white" title="保存" onclick="doSave(0)" />
	        <input type="button" id="sub_shenpi" value="提交审批" class="btn-win-white modal-menu-test" title="提交审批"  onclick="commitAudi(this);" />
   </div> 
   <span class="basic_border"></span>
</div>
<!-- table -->
<form method="post" name="form1" id="form1" enctype="multipart/form-data">
<div>
    <table class="TableBlock_page">
	<tr>
       <td  class="TableData"  style="text-indent:10px;width: 180px">
	    <select name="typeId" id="typeId" class="BigSelect">
	    	<option value="">选择类型</option>
	    </select>
      </td>
      <td  class="TableData" align="left" >
       	 <input type='text' name="subject" id="subject" class="easyui-validatebox BigInput"  size='35' required  maxlength="150" />
      </td>
   </tr>
   <tr title="发布范围取部门、人员和角色的并集" >
      <td  class="TableData" style="text-indent:10px" >按部门发布：</td>
      <td  class="TableData" align="left" >
       <input type="hidden" name="toDeptIds" id="toDeptIds" value=""/>
        <textarea readonly cols=60 name="toDeptNames" id="toDeptNames" rows=3 class="SmallStatic BigTextarea"></textarea>
        <span class='addSpan'>
			<img src="<%=contextPath %>/common/zt_webframe/imgs/xzbg/tzgg/icon_select.png" onClick="selectDept(['toDeptIds','toDeptNames'],'<%=TeeModelIdConst.NOTIFY_SEND_PRIV%>')" value="选择"/>
			 &nbsp;&nbsp;
			 <img src="<%=contextPath %>/common/zt_webframe/imgs/xzbg/tzgg/icon_cancel.png" onClick="clearData('toDeptIds','toDeptNames')" value="清空"/>
		</span>
      </td>
   </tr>
   <tr title="发布范围取部门、人员和角色的并集" >
      <td  class="TableData" style="text-indent:10px">按角色发布：</td>
      <td  class="TableData" align="left">
        <input type="hidden" name="toRolesIds" id="toRolesIds" value="">
        <textarea readonly cols=60 name="toRolesNames" id="toRolesNames" rows=3 class="SmallStatic BigTextarea"  ></textarea>
        
         <span class='addSpan'>
			<img src="<%=contextPath %>/common/zt_webframe/imgs/xzbg/tzgg/icon_select.png" onClick="selectRole(['toRolesIds','toRolesNames'],'<%=TeeModelIdConst.NOTIFY_SEND_PRIV%>','1')" value="选择"/>
			 &nbsp;&nbsp;
			 <img src="<%=contextPath %>/common/zt_webframe/imgs/xzbg/tzgg/icon_cancel.png" onClick="clearData('toRolesIds','toRolesNames')" value="清空"/>
		</span>
      </td>
   </tr>
   <tr title="发布范围取部门、人员和角色的并集" >
       <td  class="TableData"  style="text-indent:10px">按人员发布：
       </td>
       <td  class="TableData" align="left" >
       <input type="hidden" name="toUserIds" id="toUserIds" value="">
        <textarea readonly cols=60 name="toUserNames" id="toUserNames" rows=3 class="SmallStatic BigTextarea"  ></textarea>
        <span class='addSpan'>
			<img src="<%=contextPath %>/common/zt_webframe/imgs/xzbg/tzgg/icon_select.png" onClick="selectUser(['toUserIds', 'toUserNames'],'<%=TeeModelIdConst.NOTIFY_SEND_PRIV%>' , '1')" value="选择"/>
			 &nbsp;&nbsp;
			 <img src="<%=contextPath %>/common/zt_webframe/imgs/xzbg/tzgg/icon_cancel.png" onClick="clearData('toUserIds','toUserNames')" value="清空"/>
		</span>
       </td>
   </tr>
   <tr>
      	<td style="text-indent:10px">提&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;示：</td>
   		<td class="TableData"><font color='red'>
   			当用户，部门，角色都为空时，默认全部门发布
   		</font></td>
   </tr>
   	<tr>
		<td  class="TableData"  style="text-indent:10px">有&nbsp;&nbsp;&nbsp;效&nbsp;&nbsp;&nbsp;期：</td>
		<td class="TableData">
		  开始时间：<input type="text" name="beginDate" id="beginDate" size="15" 
		    style="width:130px"  value="<%=currDateStr %>"
			onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endDate\')}'})" class="Wdate">
		&nbsp;&nbsp;&nbsp;
		终止时间：<input type="text" name="endDate" id="endDate" size="15"
		     style="width:130px"
			onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'beginDate\')}'})" class="Wdate">&nbsp;&nbsp;（注：终止时间为空代表永久有效）
	
	</tr>
   <tr>
   		<td  class="TableData"  style="text-indent:10px">附&nbsp;件&nbsp;选&nbsp;择：</td>
    	<td  class="TableData" align="left">
    		<div id="attachList"></div>
			<div id="upfileList"></div>
   	    </td>
   </tr>
    <tr>
   		<td  class="TableData"  style="text-indent:10px">短&nbsp;信&nbsp;提&nbsp;醒：</td>
    	<td  class="TableData" align="left"  id="sms">
    	<input name="smsRemind" id="smsRemind" type="checkbox" value="1" checked="checked" />
    	使用内部短信提醒  &nbsp;&nbsp; 
   	    </td>
   </tr>
       <tr>
   		<td  class="TableData" " style="text-indent:10px">重&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;要：</td>
    	<td  class="TableData" align="left">
    	<input name="top" id="top" type="checkbox" value="1"/>使公告通知显示为重要<!--  <input size="2" />天后结束置顶，0表示一直置顶  -->
   	    </td>
   </tr>
   <tr style="display:none">
   		<td style="text-indent:10px;"  class="TableData">是否发送到微信企业号：</td>
    	<td   class="TableData" align="left">
    		<input type="checkbox" name="wechat" value="1"/>选择发送
   	    </td>
   </tr>
     <tr>
    	<td class="TableData" align="left" style="text-indent:10px" >内&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;容：
    	</td>
    	<td>
	    	<textarea style="width: 900px" id="content" name="content"  class="BigTextarea" ></textarea>
   	    </td>
   </tr>
   <tr>
	    <td  class="TableControl" colspan="2" align="center">
	    <input type="hidden" name="sid" id="sid" value="<%=id%>">
	    </td>
   </tr>
</table>

</div>


<!-- Modal -->
 <div class="modal-test">
	<div class="modal-header">
		<p class="modal-title">
			提交审批
		</p>
		<span class="modal-win-close">×</span>
	</div>
	<div class="modal-body" id="mailBoxForm" style="background-color: #f2f2f2">
		<table class="TableBlock" width="100%" align="center">
			<tr>
				<td nowrap class="TableData" width="130"  style="text-indent:20px">
					   审批人
				</td>
				<td nowrap class="TableData" align="left" id="aduingUser">
			    </td>
			</tr>
			<tr>
				<td nowrap class="TableData" width=""   style="text-indent:20px">短信提醒：</td>
				<td nowrap class="TableData" align="left">
					<input name="audSmsRemind" id="audSmsRemind" type="checkbox" checked value="1"/>
					使用内部短信提醒   
				 </td>
			</tr>
		</table>
	</div>
	<div class="modal-footer clearfix">
		<input class = "modal-btn-close btn-alert-gray" type="button" value = '关闭'/>
		<input class = "modal-save btn-alert-blue" type="button" onclick="doSave(2);" value = '提交'/>
	</div>
</div>
</form>




</body>

</html>
 