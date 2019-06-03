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
<script type="text/javascript">
function doInit(){
	var url = contextPath+"/TeeSmsPhonePrivController/getSmsConfigs.action";
	var json = tools.requestJsonRs(url);
	bindJsonObj2Cntrl(json.rtData);
	change0(json.rtData.SMS_OPEN);
}

/**
 * 保存
 */
function doSave(){
		var param = tools.formToJson($("#form1"));
		var url = contextPath+"/TeeSmsPhonePrivController/updateSmsConfigs.action";
		var json = tools.requestJsonRs(url,param);
		if(json.rtState){
			//top.$.jBox.tip(json.rtMsg,"info");
			$.MsgBox.Alert_auto("保存成功！",function(){
				location.reload();
			});
		}else{
			$.MsgBox.Alert_auto("保存错误！");
		}
}

function change0(type){
	if(type=="0"){//关闭
		$("#t2").hide();
		$("#t3").hide();
		$("#t4").hide();
		/* $("#t5").hide();
		$("#t6").hide();
		$("#t7").hide(); */
	}else if(type=="1"){//美联软通
		$("#t2").show();
		$("#t2desc").html("APIKey：");
		$("#t3").show();
		$("#t3desc").html("用户名：");
		$("#t4").show();
		$("#t4desc").html("密码：");
		/* $("#t5").hide();
		$("#t6").hide();
		$("#t7").hide(); */
	}else if(type=="2"){//漫道
		$("#t2").show();
		$("#t2desc").html("SN：");
		$("#t3").hide();
		$("#t4").show();
		$("#t4desc").html("密码：");
		/* $("#t5").hide();
		$("#t6").hide();
		$("#t7").hide(); */
	}else if(type=="3"){//维欧科技短信猫
			
		$("#t2").hide();
		$("#t3").hide();
		$("#t4").hide();
		/* 
		$("#t5").show();
		$("#t6").show();
		$("#t7").show(); */
		
	}
}

function clear1(){
	$("#SMS_APIKEY").val("");
	$("#SMS_USERNAME").val("");
	$("#SMS_PASSWORD").val("");
}
</script>
</head>
<body onload="doInit()" topmargin="5" style="padding:10px;">
<br/>
<form  name="form1" id="form1">
  <table class="TableBlock_page" width="100%" align="center">
   <tr>
    <td nowrap class="TableData" width="100px">发送接口：</td>
    <td nowrap class="TableData" >
    	<input type="radio" name="SMS_OPEN" id="smsOpen0" value="0" onclick="change0('0');clear1();"/>&nbsp;<label for="smsOpen0">关闭</label>
    	&nbsp;&nbsp;
    	<input type="radio" name="SMS_OPEN" id="smsOpen1" value="1" onclick="change0('1');clear1();" />&nbsp;<label for="smsOpen1">MLRT</label>
    	&nbsp;&nbsp;
    	<input type="radio" name="SMS_OPEN" id="smsOpen2" value="2" onclick="change0('2');clear1();" />&nbsp;<label for="smsOpen2">MD</label>
    	&nbsp;&nbsp;
    	<input type="radio" name="SMS_OPEN" id="smsOpen3" value="3" onclick="change0('3');clear1();" />&nbsp;<label for="smsOpen3">短信猫</label>
    </td>
   </tr>
   <tr>
    <td class="TableHeader" colspan="2" nowrap="">
		<img src="/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;">
		<b style="color: #0050aa">短信平台设置</b></td>
   </tr>
<!--    <tr id="t1"> -->
<!--     <td nowrap class="TableData">平台地址：</td> -->
<!--     <td nowrap class="TableData" > -->
<!--     	<input type='text' id="SMS_URL" class="BigInput readonly" name="SMS_URL" readonly style="width:300px"/> -->
<!--     </td> -->
<!--    </tr> -->
    <tr id="t2" style="display:none">
    <td nowrap class="TableData" id="t2desc">APIKey：</td>
    <td nowrap class="TableData" >
    	<input type='text' id="SMS_APIKEY" class="BigInput" name="SMS_APIKEY" style="width:300px"/>
    </td>
   </tr>
   <tr id="t3" style="display:none">
    <td nowrap class="TableData" id="t3desc">用户名：</td>
    <td nowrap class="TableData" >
    	<input type='text' id="SMS_USERNAME" class="BigInput" name="SMS_USERNAME" style="width:300px"/>
    </td>
   </tr>
   <tr id="t4" style="display:none">
    <td nowrap class="TableData" id="t4desc">密码：</td>
    <td nowrap class="TableData" >
    	<input type='text' id="SMS_PASSWORD" class="BigInput" name="SMS_PASSWORD" style="width:300px"/>
    </td>
   </tr>
 <!--   <tr id="t5" style="display:none">
    <td nowrap class="TableData" id="t5desc">COM端口：</td>
    <td nowrap class="TableData" >
    	<input type='text' id="SMS_COMPORT" class="BigInput" name="SMS_COMPORT" style="width:300px"/>
    </td>
   </tr>
   <tr id="t6" style="display:none">
    <td nowrap class="TableData" id="t6desc">比特率：</td>
    <td nowrap class="TableData" >
    	<input type='text' id="SMS_BAUDRATE" class="BigInput" name="SMS_BAUDRATE" style="width:300px"/>
    </td>
   </tr>
   <tr id="t7" style="display:none">
    <td nowrap class="TableData" id="t7desc">波长类型：</td>
    <td nowrap class="TableData" >
    	<input type='text' id="SMS_MANUFACTURER" class="BigInput" name="SMS_MANUFACTURER" style="width:300px"/>
    </td>
   </tr> -->
   <tr>
    <td class="TableHeader" colspan="2" nowrap="">
		<img src="/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;">
		<b style="color: #0050aa">短信模版设置</b></td>
   </tr>
   <tr>
      <td nowrap class="TableData" >短信模板：</td>
    <td>
    	 <input class="BigInput" type="text" name="SMS_TEMPLATE" id="SMS_TEMPLATE" style="width:600px;" />
    	 <br/>
    	 短信内容：<%="${CONTENT}" %>
    	 <br/>
    	 日期：<%="${DATE}" %>
    	 <br/>
    	 发送人：<%="${SENDER}" %>
    </td>
   </tr>
   <tr>
    <td colspan='2' style="text-align:Center" class="TableHeader">
   	 	<button class="btn-win-white" type="button" onclick="doSave()">保存</button>
    </td>
   </tr>
</table>
</form>
</body>
</html>