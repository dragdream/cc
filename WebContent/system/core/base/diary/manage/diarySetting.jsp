<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header2.0.jsp"%>
<%@include file="/header/easyui2.0.jsp"%>
<%@include file="/header/upload.jsp"%>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>

<script>
function doInit(){
		var url = contextPath+"/diaryController/getDiarySetting.action";
		var json = tools.requestJsonRs(url);
		if(json.rtState){
			bindJsonObj2Cntrl(json.rtData);
		}else{
			$.MsgBox.Alert_auto(json.rtMsg);
		}
}

function commit(){
	if(checkForm()){
		var param = tools.formToJson($("#form1"));
		var url = contextPath+"/diaryController/saveDiarySetting.action";
		var json = tools.requestJsonRs(url,param);
		if(json.rtState){
			$.MsgBox.Alert_auto(json.rtMsg,function(){
				
				location.reload();
			});
			
		}else{
			$.MsgBox.Alert_auto(json.rtMsg);
		}
	}
}

function checkForm(){
	  if($("#startTimeDesc").val() && $("#endTimeDesc").val()){
		    if(document.getElementById("startTimeDesc").value > document.getElementById("endTimeDesc").value){
		      $.MsgBox.Alert_auto("时间范围填写不对，开始时间大于结束时间！");
		      return false;
			 }
	  }
	return true;
}
</script>
</head>
<body onload="doInit()" style="padding-left: 10px;padding-right: 10px">
<div id="toolbar" class="topbar clearfix">
	   <img class="title_img" src="/system/core/base/diary/manage/img/icon_rzgl.png" alt="">
	   &nbsp;<span class="title">日志管理</span>
	   
	   <button class="btn-win-white fr" onClick="commit()">保存设置</button>	   
	</div>
<form method="post" name="form1" id="form1"  enctype="multipart/form-data">
	<table class="TableBlock_page" style="width:100%";>
		<tr>
			<td class="TableData" style="text-indent:10px">锁定以下时间范围的日志：</td>
			<td class="TableData">
				<input type="text" id='startTimeDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='startTimeDesc' class="Wdate BigInput " style="width: 170px"/>至
				<input type="text" id='endTimeDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='endTimeDesc' class="Wdate BigInput "  style="width: 170px"/>
				<br>说明：都为空表示不锁定，也可以只填写其中一个 
			</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent:10px">锁定指定天数的日志：</td>
			<td class="TableData">
				锁定<input type="text" id="beforeDays" name="beforeDays" class="BigInput easyui-validatebox" validType='integeZero[]' maxlength="5" value="0" style="width:60px;">天前的日志
				<br>说明：0或空表示不锁定 
			</td>
		</tr>
	</table>
</form>

</body>
</html>