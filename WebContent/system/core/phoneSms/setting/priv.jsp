<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/upload.jsp" %>
<title>手机短信设置</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/colorPicker/css/colorPicker.css"/>
<style type="text/css">

</style>
<script type="text/javascript" src="<%=contextPath %>/common/colorPicker/colorPicker.js"></script>
<script type="text/javascript">
function doInit(){
	var url = "<%=contextPath %>/TeeSmsPhonePrivController/getPhonePriv.action";
	var jsonRs = tools.requestJsonRs(url);
	if(jsonRs.rtState){
		bindJsonObj2Cntrl(jsonRs.rtData);
		var outToSelf = jsonRs.rtData.outToSelf;
		if(outToSelf==1){
			$("#outToSelf").attr("checked",true);
		}
	}
}
/**
 * 保存
 */
function doSave(){
	
		var param = tools.formToJson($("#form1"));
		var url = contextPath+"/TeeSmsPhonePrivController/addOrUpdate.action";
		var json = tools.requestJsonRs(url,param);
		if(json.rtState){
			$.MsgBox.Alert_auto("保存成功！",function(){
				location.reload();
			});
			
		}else{
			$.MsgBox.Alert_auto(json.rtMsg);
		}
	
}
</script>
</head>
<body onload="doInit()" style="padding:10px">
<form enctype="multipart/form-data" method="post" name="form1" id="form1">
  <table class="TableBlock_page" width="100%" align="center">
   <tr>
       <td class="TableHeader"  nowrap="">
		<img src="/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;">
		<b style="color: #0050aa">模块权限</b>（指定允许发送手机短信提醒的模块）</td>
   </tr>
   <tr>
    <td  class="TableData"  style="text-indent: 20px;">
    	<input type='hidden' id="typePriv" name="typePriv"/>
        <textarea id="typePrivNames" name = "typePrivNames" class='BigTextarea' style='width:600px;height:80px;' readonly="readonly"></textarea>
        <span class='addSpan'>
		   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectSysModule(['typePriv','typePrivNames'],'' , '')" value="选择"/>
		   &nbsp;&nbsp;
		   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('typePriv','typePrivNames')" value="清空"/>
		</span>
        
    </td>
   </tr>
   <tr>
       <td class="TableHeader"  nowrap="">
		<img src="/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;">
		<b style="color: #0050aa">被提醒权限</b>（以下指定的用户可以接收到手机短信提醒，例如来自工作流的手机短信提醒）</td>
   </tr>
   <tr>
    <td  class="TableData" style="text-indent: 20px;">
    	<input type='hidden' id="remindPriv" name="remindPriv"/>
        <textarea id="remindPrivUserNames" name = "remindPrivUserNames" class='BigTextarea' style='width:600px;height:80px;' readonly="readonly"></textarea>
        <span class='addSpan'>
		   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectUser(['remindPriv','remindPrivUserNames'],'' , '')" value="选择"/>
		   &nbsp;&nbsp;
		   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('remindPriv','remindPrivUserNames')" value="清空"/>
		</span>
        
    </td>
   </tr>
    <tr>
       <td class="TableHeader"  nowrap="">
		<img src="/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;">
		<b style="color: #0050aa">外发权限</b>（指定允许向OA系统外人员发手机短信的用户）</td>
   </tr>
    <tr>
    <td  class="TableData" style="text-indent: 20px;">
    	<input type='hidden' id="outPriv" name="outPriv"/>
        <textarea id="outPrivUserNames" name = "outPrivUserNames" class='BigTextarea' style='width:600px;height:80px;' readonly="readonly"></textarea>
         <span class='addSpan'>
		   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectUser(['outPriv','outPrivUserNames'],'' , '')" value="选择"/>
		   &nbsp;&nbsp;
		   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('outPriv','outPrivUserNames')" value="清空"/>
		</span>
        
    </td>
   </tr>
   <tr>
       <td class="TableHeader"  nowrap="">
		<img src="/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;">
		<b style="color: #0050aa">提醒权限</b>（以下指定的用户，可以发送手机短信提醒其他用户）</td>
   </tr>
    <tr>
    <td  class="TableData" style="text-indent: 20px;">
    	<input type='hidden' id="smsRemindPriv" name="smsRemindPriv"/>
        <textarea id="smsRemindPrivUserNames" name = "smsRemindPrivUserNames" class='BigTextarea' style='width:600px;height:80px;' readonly="readonly"></textarea>
        <span class='addSpan'>
		   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectUser(['smsRemindPriv','smsRemindPrivUserNames'],'' , '')" value="选择"/>
		   &nbsp;&nbsp;
		   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('smsRemindPriv','smsRemindPrivUserNames')" value="清空"/>
		</span>
        
    </td>
   </tr>
   <tr style="display:none">
	   	<td nowrap class="TableData">
	   		是否允许给自己短信：
	   </td>
	   <td nowrap class="TableData">
	   		<input type="checkbox" id='outToSelf' name ='outToSelf' />是
	   </td>
   </tr>
   <tr>
    <td   colspan="2" align="center" style="text-align:Center">
    	<input id='sid' name='sid' type='hidden' />
        <input type="button" id="" value="确定" class="btn-win-white" onclick="doSave();">
    </td>
   </tr>
</table>
</form>
</body>
</html>