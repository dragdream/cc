<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<%@ include file="/header/upload.jsp" %>
<title>视频会议组件设置</title>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
<%
	String meetNo = TeeStringUtil.getString(request.getParameter("meetNo"));
	int userId = ((TeePerson)session.getAttribute(TeeConst.LOGIN_USER)).getUuid();
%>
var meetNo = "<%=meetNo%>";
var userId = "<%=userId%>";
var myDate = new Date();   
function doInit(){
	if(meetNo!=""){
		var url = contextPath+"/vmeeting/getVmeetingConfigs.action";
		var json = tools.requestJsonRs(url);
		var sp = json.rtData.VMT_MGR_PRIV_IDS.split(",");
		var exists = false;
		for(var i=0;i<sp.length;i++){
			if(sp[i]==userId){
				exists = true;
				break;
			}
		}
		if(!exists){
			messageMsg("您没有管理会议的权限，请联系管理员！","body","error");
			return;
		}
		
		var url = contextPath+"/vmeeting/getMeetingInfo.action?meetNo="+meetNo;
		var json = tools.requestJsonRs(url);
		bindJsonObj2Cntrl(json.rtData);
		$("#startTime").val(getFormatDateStr(json.rtData.startTime,"yyyy-MM-dd hh:mm"));
	}else{
		var url = contextPath+"/vmeeting/getVmeetingConfigs.action";
		var json = tools.requestJsonRs(url);
		var sp = json.rtData.VMT_CREATE_PRIV_IDS.split(",");
		var exists = false;
		for(var i=0;i<sp.length;i++){
			if(sp[i]==userId){
				exists = true;
				break;
			}
		}
		if(!exists){
			messageMsg("您没有创建会议的权限，请联系管理员！","body","info");
			return;
		}
	}
	
	
}

/**
 * 保存
 */
function doSave(){
	if($("#form1").valid()){
		var param = tools.formToJson($("#form1"));
		var personIds=$("#personIds").val();
/* 		  var s= new Date(document.getElementById("startT").value.replace(/-/g,'/'));
		  var e= new Date(document.getElementById("endT").value.replace(/-/g, '/'));
		if(s >= e){ 
			$.MsgBox.Alert_auto("开始时间要小于不等于结束时间");
		return false;
		}else if(e<=myDate){
			$.MsgBox.Alert_auto("结束时间要大于当前时间");
			return false;
		}else{  */
		var url = contextPath+"/vmeeting/addMeetingInfo.action?personIds="+personIds;
		var json = tools.requestJsonRs(url,param);
		if(json.rtState){
			parent.$.MsgBox.Alert_auto("保存成功！");
			window.location.reload();
		}else{
			parent.$.MsgBox.Alert_auto("保存错误！");
		}
		return true;  
		//}
	
	}
}
</script>
</head>
<body id="body" onload="doInit()" style="padding-left: 10px;padding-right: 10px;">
<div id="toolbar" class = "setHeight clearfix"  style="margin-bottom: 10px;margin-top: 10px;">
<div class=" fl">
   <h4 style="font-size: 16px;font-family:MicroSoft YaHei;margin-left: 10px;">
				新建视频会议
				</h4>
</div>
   <div class=" fr">
	     <input type="button"  value="保存" class="btn-win-white" title="保存" onclick="doSave()" style="width:45px;height:25px;" />
	 	 <input type="button" value="返回" class="btn-win-white" title="返回" onclick="history.go(-1)" style="width:45px;height:25px;" />&nbsp;
   </div> 
   <span class="basic_border" style="margin-top: 10px;"></span>
</div>

<form method="post" name="form1" id="form1" enctype="multipart/form-data">
  <table class="TableBlock_page"  align="center">
    <td nowrap class="TableData" style="width:200px;text-indent: 10px;">会议名称：</td>
    <td nowrap class="TableData" >
    	<input type='text' id="meetingName" class="BigInput" required name="meetingName" style="width:300px;font-family: MicroSoft YaHei;"/>
    	<input type="hidden" id="chairmanPwd" name="chairmanPwd"   value="111111"  />
         <input type="hidden" id="confuserPwd" name="confuserPwd" value="222222"  />
    </td>
   </tr>
    <tr>
    <td style="text-indent: 10px;" nowrap class="TableData">开始时间：</td>
    <td nowrap class="TableData" >
    	<input style="width:300px" type='text' id="startT" class="Wdate BigInput" required name="startT" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',maxDate:'#F{$dp.$D(\'endT\')}'})"/>
    </td>
   </tr>
   <tr>
   <td style="text-indent: 10px;"  nowrap class="TableData">结束时间:</td>
    <td nowrap class="TableData">
     <input style="width:300px" type='text' id="endT" class="Wdate BigInput" required name="endT" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'startT\')}'})"/>
    </td>
   </tr>
   <tr>
   <td style="text-indent: 10px;"  nowrap class="TableData">会议用户人数:</td>
    <td nowrap class="TableData">
     <input type='text' id="attendNum"  class="BigInput" required name="attendNum" style="width:300px;font-family: MicroSoft YaHei;" positive_integer="true"/>
    </td>
   </tr>
   <tr>
   <td style="text-indent: 10px;" nowrap class="TableData" style="width:100px">视屏数目:</td>
   <td nowrap class="TableData" ><input type="text" id="maxvideo" name="maxvideo" class="BigInput"  required  style="width:300px;font-family: MicroSoft YaHei;" positive_integer="true"/></td>
   </tr>
   <tr>
   <td style="text-indent: 10px;" nowrap class="TableData" style="width:100px">发言人数:</td>
   <td nowrap class="TableData" ><input type="text" id="maxPersonspeak"  name="maxPersonspeak" class="BigInput"   required style="width:300px;font-family: MicroSoft YaHei;" positive_integer="true"/></td>
   </tr>
<!--    <tr>
    <td nowrap class="TableData">主持人：</td>
    <td nowrap class="TableData" >
    	<input type='hidden' id="mainUser" class="BigInput" name="mainUser" />
    	<input type='text' readonly id="mainUserName" class="BigInput readonly easyui-validatebox" required name="mainUserName" />
    	&nbsp;<a href="#" onclick="selectSingleUser(['mainUser','mainUserName']);">选择</a>&nbsp;<a href="#" onclick="clearData('mainUser','mainUserName');">清空</a>
    </td>
   </tr> -->
   <tr>
    <td style="text-indent: 10px;" nowrap class="TableData">与会人员:</td>
    <td  class="TableData" >
    	<input type='hidden' id="personIds" class="BigInput" name="personIds" />
    	  <textarea readonly id="personNames" name="personNames" style="height:100px;width:500px;font-family: MicroSoft YaHei;resize:none;"></textarea>
          <span class='addSpan'>
			<img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/sphy/add.png" onclick="selectUser(['personIds','personNames']);" value="选择"/>
			 &nbsp;&nbsp;
			 <img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/sphy/clear.png" onclick="clearData('personIds','personNames');" value="清空"/>
		  </span>
    </td>
   </tr>
   <tr>
    <td style="text-indent: 10px;" nowrap class="TableData">会议内容:</td>
    <td nowrap class="TableData" >
    	<!-- //<textarea readonly id="subject" name="subject"></textarea> -->
    	<textarea rows="" cols=""class="BigInput" style="height:100px;width:500px;font-family: MicroSoft YaHei;" name="content" id="content"></textarea>
    	
    </td>
   </tr>
</table>
<input type="hidden" name="meetNo" value="<%=meetNo%>"/>

</form>
</body>
</html>