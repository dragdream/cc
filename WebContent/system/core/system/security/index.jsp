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
<title>系统参数</title>
<script type="text/javascript">
/* $.extend($.fn.validatebox.defaults.rules, {   
	CompareIntge: {   
	 	validator: function(value, param){   
	 		var minPassLength = param[0];
	 		var maxPassLength = value;
	 		if(minPassLength == ''){
	 			minPassLength = '0';
	 		}
	 		if(maxPassLength == ''){
	 			maxPassLength = '0';
	 		}
  			return parseInt(maxPassLength) >= parseInt(minPassLength) ; //整数
 	    },  
  	   message: '密码最大长度不能小于最小长度！'
    } ,
    CompareIntgeMin: {   
	 	validator: function(value, param){   
	 		var maxPassLength = param[0];
	 		var minPassLength = value;
	 		if(minPassLength == ''){
	 			minPassLength = '0';
	 		}
	 		if(maxPassLength == ''){
	 			maxPassLength = '0';
	 		}
  			return parseInt(maxPassLength) >= parseInt(minPassLength) ; //整数
 	    },  
  	   message: '密码最大长度不能小于最小长度！'
    } 
}); */
function doInit(){
	var url = "<%=contextPath %>/sysPara/getSysParaList.action";
	var paraNames = "USB_KEY,SEC_INIT_PASS,SEC_PASS_FLAG,SEC_PASS_TIME,SEC_PASS_MIN"
		 + ",SEC_PASS_MAX,SEC_PASS_SAFE,SEC_PASS_SAFE_SC"
		 + ",SEC_RETRY_BAN,SEC_RETRY_TIMES,SEC_BAN_TIME"
		 + ",SEC_SHOW_IP,SEC_USER_MEM,VERIFICATION_CODE,SEC_ON_STATUS,LOGIN_BY_CODE,"
		 +"SMS_PHONE_SEND_NUMBER,SMS_PHONE_SEND_TIME_OUT,SMS_PHONE_THREAD_NUMBER"
		 +",WEB_MAIL_SEND_NUMBER,WEB_MAIL_SEND_THREAD_NUMBER"
		 +",WEI_XIN_THREAD_NUMBER,WEI_XIN_TIME_OUT"
		 +",DING_DING_THREAD_NUMBER,DING_DING_TIME_OUT"
		 +",WEB_MAIL_THREAD_NUMBER,WEB_MAIL_TIME_OUT"
		 +",WEB_MAIL_REC_POOL_SIZE,WEB_MAIL_REC_DELAY,WEB_MAIL_REC_TIMEOUT,WEB_MAIL_REC_DAYS,WEB_MAIL_REC_LIMIT,WATER_MARK,GLOBAL_ATTACH_TYPE,SCAN_CARD_APP_CODE";
		 
		 ;
	var para =  {paraNames:paraNames} ;
	var jsonRs = tools.requestJsonRs(url,para);
	if(jsonRs.rtState){
		var dataList = jsonRs.rtData;
		for(var i = 0;i<dataList.length;i++){
			var data = dataList[i];
			 setValue(data);
			 if(data.paraName == 'SEC_PASS_FLAG'  && data.paraValue == '1'){//验证密码过期时间
				 teeSecPassTimeSet(data.paraValue);
			 }
			 
			 if(data.paraName == 'SEC_RETRY_BAN'  && data.paraValue == '1'){//登录密码次数
				 teeSecEntryTimes(data.paraValue);
			 }
		}
		
	}else{
		$.MsgBox.Alert_auto(jsonRs.rtMsg);
	}
//	 $("#form1").form('validate'); 
}

/**
 * 设置属性
 */
function setValue(data){
    var cntrlArray = document.getElementsByName(data.paraName);    
    var cntrlCnt = cntrlArray.length;
    var value = data.paraValue;
    for(var i = 0;i< cntrlCnt ;i++){
    	var cntrl = cntrlArray[i];
    	if (cntrl.tagName.toLowerCase() == "input" && (cntrl.type.toLowerCase() == "checkbox" || cntrl.type.toLowerCase() == "radio") ) {
    		if (cntrl.value == value) {
              cntrl.checked = true;
            }else {
              cntrl.checked = false;
            }
        }else{
        	cntrl.value = value;
        }
    }

}
/**
 * 保存
 */
function doSave(){
	if (checkFrom()){
		var url = "<%=contextPath %>/sysPara/addOrUpdateSysParaList.action";
		var para =  tools.formToJson($("#form1")) ;
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			$.MsgBox.Alert_auto("保存成功！");
			
		}else{
			$.MsgBox.Alert_auto(jsonRs.rtMsg);
			
		}
	}
	
}


function checkStr(str){ 
    var re=/[\\"']/; 
    return str.match(re); 
  }
 
function checkFrom() {
	//验证密码最小长度   最大长度
	var minPass=$("#SEC_PASS_MIN").val();
	var maxPass=$("#SEC_PASS_MAX").val();
	if(parseInt(maxPass) < parseInt(minPass)){
		$.MsgBox.Alert_auto("密码最大长度不能小于最小长度！");
		return false;
	}
	  return $("#form1").valid(); 
}
/**
 * 设置密码过期
 */
function teeSecPassTimeSet(value){
	if(value == '0'){
		$('#passTime').hide();
		/* $('#SEC_PASS_TIME').validatebox({ 
			required:false
			}); */
		$('#SEC_PASS_TIME').attr("required","false");
	}else{
		$('#passTime').show();
		/* $('#SEC_PASS_TIME').validatebox({ 
			required:true 
			});  */
		$('#SEC_PASS_TIME').attr("required","true");
	}
}


/**
 * 设置登录错误次数
 */
function teeSecEntryTimes(value){
	if(value == '0'){
		$('#retryBan').hide();
		/* $('#SEC_RETRY_TIMES').validatebox({ 
			required:false
			});
		$('#SEC_BAN_TIME').validatebox({ 
			required:false
			}); */
		$('#SEC_RETRY_TIMES').attr("required","false");
		$('#SEC_BAN_TIME').attr("required","false");
	}else{
		$('#retryBan').show();
		/* $('#SEC_RETRY_TIMES').validatebox({ 
			required:true 
			});
		$('#SEC_BAN_TIME').validatebox({ 
			required:true 
			}); */
		$('#SEC_RETRY_TIMES').attr("required","true");
		$('#SEC_BAN_TIME').attr("required","true");
		
	}
}
</script>
</head>
<body onload="doInit()" style="padding-left: 10px;padding-right: 10px;">
<div id="toolbar" class="topbar clearfix">
	   <img class="title_img" src="/system/core/system/imgs/icon_xtaqsz.png" alt="">
	   &nbsp;<span class="title">系统安全设置</span>
	   <button class="btn-win-white fr" onclick="doSave()">保存</button>	   
</div>

<div class="base_layout_center" style="padding:10px">
<form name="form1" id="form1" method="post">
<table class="TableBlock_page" width="100%">
<tr class="TableHeader" align="center">
    <td width="20%"><b>选项</b></td>
    <td width="50%"><b>参数</b></td>
    <td width="30%"><b>备注</b></td>
  </tr>
  <tr class="TableData" align="center" >
    <td width="140">初始密码登录修改密码</td>
    <td align="left">
       <input type="radio" name="SEC_INIT_PASS" id="SEC_INIT_PASS1" value="1" ><label for="SEC_INIT_PASS1">是</label>
       &nbsp;&nbsp;
       <input type="radio" name="SEC_INIT_PASS" id="SEC_INIT_PASS0" value="0" ><label for="SEC_INIT_PASS0">否</label>
    </td>
    <td width="350" align="left">
              用户用初始密码登录需修改密码
    </td>
  </tr>
  <tr class="TableData" align="center">
    <td width="140">密码定时过期</td>
    <td align="left">
       <input type="radio" name="SEC_PASS_FLAG" id="SEC_PASS_FLAG1" value="1" onclick="teeSecPassTimeSet(this.value);"><label for="SEC_PASS_FLAG1">是</label>
        &nbsp;&nbsp;
       <input type="radio" name="SEC_PASS_FLAG" id="SEC_PASS_FLAG0" value="0" onclick="teeSecPassTimeSet(this.value);"><label for="SEC_PASS_FLAG0">否</label> &nbsp;
       <span id="passTime" style="display: none;">密码有效期：<Input type="text" name="SEC_PASS_TIME" id="SEC_PASS_TIME" value="" size="3" style="text-align:center;" class="BigInput"  maxlength='3'  positive_integer="true"/> 天</span>
    </td>
    <td width="300" align="left">
             如果超过了密码的有效期，则在用户登录时将强制用户修改密码。
    </td>
  </tr>
  <tr class="TableData" align="center" >
    <td width="140">密码强度</td>
    <td align="left">
          密码长度：<Input type="text" name="SEC_PASS_MIN" id="SEC_PASS_MIN" class="BigInput"  maxlength='2'  size='2'  style="text-align:center;width: 50px"> — <Input type="text" name="SEC_PASS_MAX" id="SEC_PASS_MAX"  size='2' class="BigInput"  maxlength='2'     style="text-align:center;width: 50px"> 位
       <br>
       <input type="checkbox" name="SEC_PASS_SAFE" id="SEC_PASS_SAFE" value='1'><label for="SEC_PASS_SAFE">密码必须同时包含字母和数字</label>
       &nbsp;&nbsp;&nbsp;
       <input type="checkbox" name="SEC_PASS_SAFE_SC" id="SEC_PASS_SAFE_SC" value='1'><label for="SEC_PASS_SAFE_SC">密码必须包含特殊字符</label>
    </td>
    <td width="300" align="left">
           设置密码强度，以保证密码的安全性。
    </td>
  </tr>
  <tr class="TableData" align="center" >
    <td width="140" >登录错误次数限制</td>
    <td align="left" >
       <input type="radio" name="SEC_RETRY_BAN" id="SEC_RETRY_BAN1" value="1" onclick="teeSecEntryTimes(this.value)" ><label for="SEC_RETRY_BAN1">是</label>
        &nbsp;&nbsp;&nbsp;
       <input type="radio" name="SEC_RETRY_BAN" id="SEC_RETRY_BAN0" value="0" onclick="teeSecEntryTimes(this.value)" ><label for="SEC_RETRY_BAN0">否</label> &nbsp;
       <span id="retryBan" style="display: none;">
         登录错误重试 <Input type="text" name="SEC_RETRY_TIMES" id="SEC_RETRY_TIMES"  class="BigInput"  maxlength='2'  positive_integer="true" size="2" style="text-align:center;width: 50px"> 次后
         <Input type="text" name="SEC_BAN_TIME" id="SEC_BAN_TIME" class="easyui-validatebox BigInput"  maxlength='2'  positive_integer="true" size="2" style="text-align:center;width: 50px"> 分钟内禁止再次登录</span>
    </td>
    <td width="300" align="left" >
       如果选择“是”，则登录错误重试数次后会被限制数分钟内不能登录。

    </td>
  </tr>
  <tr class="TableData" align="center">
    <td width="140">允许登录时记忆用户名</td>
    <td align="left">
       <input type="radio" name="SEC_USER_MEM" id="SEC_USER_MEM1" value="1" ><label for="SEC_USER_MEM1">是</label>
       &nbsp;&nbsp;&nbsp;
       <input type="radio" name="SEC_USER_MEM" id="SEC_USER_MEM0" value="0" ><label for="SEC_USER_MEM0">否</label>
    </td>
    <td width="250" align="left">
            登录界面记忆上次成功登录的用户名可以方便用户登录，但可能会带来安全隐患。
    </td>
  </tr>
 
  <tr class="TableData" align="center">
    <td width="140">使用验证码登录</td>
    <td align="left">
    	<select class="BigSelect" name="LOGIN_BY_CODE" id="LOGIN_BY_CODE">
    		<option value="0">否</option>
    		<option value="1">是</option>
    	</select>
    </td>
    <td width="300" align="left">
		开启验证码登录校验，有效防止非法登录请求
    </td>
  </tr>
  <tr class="TableData" align="center">
    <td width="140">开启UsbKey登录</td>
    <td align="left">
    	<select class="BigSelect" name="USB_KEY" id="USB_KEY">
    		<option value="0">否</option>
    		<option value="1">是</option>
    	</select>
    </td>
    <td width="300" align="left">
		开启UsbKey登录，有效的对账号进行双重保护
    </td>
  </tr>
  <tr class="TableData" align="center">
    <td width="140">开启水印功能</td>
    <td align="left">
    	<select class="BigSelect" name="WATER_MARK" id="WATER_MARK">
    		<option value="0">否</option>
    		<option value="1">是</option>
    	</select>
    </td>
    <td width="300" align="left">
		
    </td>
  </tr>
  <tr class="TableData" align="center">
    <td width="140">附件上传类型</td>
    <td align="left">
    	<input type="text" name="GLOBAL_ATTACH_TYPE" id="GLOBAL_ATTACH_TYPE" />	例如：text,png,ppt
    </td>
    <td width="300" align="left">
		
    </td>
  </tr>
  <tr class="TableData" align="center">
    <td width="140">名片识别AppCode</td>
    <td align="left">
    	<Input type="text" name="SCAN_CARD_APP_CODE" id="SCAN_CARD_APP_CODE" class="BigInput"    style="width: 300px">
    </td>
    <td width="300" align="left">	
    </td>
</tr>
<!-- 手机短信 --> 
<tr class="TableData" align="center">
    <td width="140">手机短信超时次数</td>
    <td align="left">
    	<Input type="text" name="SMS_PHONE_SEND_NUMBER" id="SMS_PHONE_SEND_NUMBER" class="BigInput"    positive_integer="true"  style="text-align:center;"> 次
    </td>
    <td width="300" align="left">	
    </td>
</tr>

<tr class="TableData" align="center">
    <td width="140">手机短信发送超时时间（秒）</td>
    <td align="left">
    	<Input type="text" name="SMS_PHONE_SEND_TIME_OUT" id="SMS_PHONE_SEND_TIME_OUT" class="BigInput"    positive_integer="true"  style="text-align:center;"> 秒
    </td>
    <td width="300" align="left">	
    </td>
</tr>

<tr class="TableData" align="center">
    <td width="140">手机短信任务线程数量</td>
    <td align="left">
    	<Input type="text" name="SMS_PHONE_THREAD_NUMBER" id="SMS_PHONE_THREAD_NUMBER" class="BigInput"    positive_integer="true"  style="text-align:center;">个
    </td>
    <td width="300" align="left">	
    </td>
</tr>

<!-- 外部邮箱 -->
<tr class="TableData" align="center">
    <td width="140">外部邮箱超时次数</td>
    <td align="left">
    	<Input type="text" name="WEB_MAIL_SEND_NUMBER" id="WEB_MAIL_SEND_NUMBER" class="BigInput"    positive_integer="true"  style="text-align:center;"> 次
    </td>
    <td width="300" align="left">	
    </td>
</tr>

<tr class="TableData" align="center">
    <td width="140">外部邮箱任务线程数量</td>
    <td align="left">
    	<Input type="text" name="WEB_MAIL_SEND_THREAD_NUMBER" id="WEB_MAIL_SEND_THREAD_NUMBER" class="BigInput"    positive_integer="true"  style="text-align:center;">个
    </td>
    <td width="300" align="left">	
    </td>
</tr>

<!-- 微信推送 -->
<tr class="TableData" align="center">
    <td width="140">微信超时时间（秒）</td>
    <td align="left">
    	<Input type="text" name="WEI_XIN_TIME_OUT" id="WEI_XIN_TIME_OUT" class="BigInput"    positive_integer="true"  style="text-align:center;"> 秒
    </td>
    <td width="300" align="left">	
    </td>
</tr>

<tr class="TableData" align="center">
    <td width="140">微信推送线程数</td>
    <td align="left">
    	<Input type="text" name="WEI_XIN_THREAD_NUMBER" id="WEI_XIN_THREAD_NUMBER" class="BigInput"    positive_integer="true"  style="text-align:center;">个
    </td>
    <td width="300" align="left">	
    </td>
</tr>

<!-- 钉钉推送 -->
<tr class="TableData" align="center">
    <td width="140">钉钉超时时间（秒）</td>
    <td align="left">
    	<Input type="text" name="DING_DING_TIME_OUT" id="DING_DING_TIME_OUT" class="BigInput"    positive_integer="true"  style="text-align:center;"> 秒
    </td>
    <td width="300" align="left">	
    </td>
</tr>

<tr class="TableData" align="center">
    <td width="140">钉钉线程数</td>
    <td align="left">
    	<Input type="text" name="DING_DING_THREAD_NUMBER" id="DING_DING_THREAD_NUMBER" class="BigInput"    positive_integer="true"  style="text-align:center;">个
    </td>
    <td width="300" align="left">	
    </td>
</tr>


<!-- 系统邮件 -->
<tr class="TableData" align="center">
    <td width="140">系统邮件超时时间（秒）</td>
    <td align="left">
    	<Input type="text" name="WEB_MAIL_TIME_OUT" id="WEB_MAIL_TIME_OUT" class="BigInput"    positive_integer="true"  style="text-align:center;"> 秒
    </td>
    <td width="300" align="left">	
    </td>
</tr>

<tr class="TableData" align="center">
    <td width="140">系统邮件线程数</td>
    <td align="left">
    	<Input type="text" name="WEB_MAIL_THREAD_NUMBER" id="WEB_MAIL_THREAD_NUMBER" class="BigInput"    positive_integer="true"  style="text-align:center;">个
    </td>
    <td width="300" align="left">	
    </td>
</tr>



<!-- 外部邮件 -->
<tr class="TableData" align="center">
    <td width="140">外部邮件接收线程池大小</td>
    <td align="left">
    	<Input type="text" name="WEB_MAIL_REC_POOL_SIZE" id="WEB_MAIL_REC_POOL_SIZE" class="BigInput"   positive_integer="true"  style="text-align:center;"> 个
    </td>
    <td width="300" align="left">	
    </td>
</tr>

<tr class="TableData" align="center">
    <td width="140">外部邮件接收时间间隔  （秒）</td>
    <td align="left">
    	<Input type="text" name="WEB_MAIL_REC_DELAY" id="WEB_MAIL_REC_DELAY" class="BigInput"    positive_integer="true"  style="text-align:center;">秒
    </td>
    <td width="300" align="left">	
    </td>
</tr>
<tr class="TableData" align="center">
    <td width="140">外部邮件接收超时时间 （秒）</td>
    <td align="left">
    	<Input type="text" name="WEB_MAIL_REC_TIMEOUT" id="WEB_MAIL_REC_TIMEOUT" class="BigInput"    positive_integer="true"  style="text-align:center;"> 秒
    </td>
    <td width="300" align="left">	
    </td>
</tr>

<tr class="TableData" align="center">
    <td width="140">接收近多少天的邮件</td>
    <td align="left">
    	<Input type="text" name="WEB_MAIL_REC_DAYS" id="WEB_MAIL_REC_DAYS" class="BigInput"    positive_integer="true"  style="text-align:center;">天
    </td>
    <td width="300" align="left">	
    </td>
</tr>
<tr class="TableData" align="center">
    <td width="140">允许接收邮件的大小  （兆）</td>
    <td align="left">
    	<Input type="text" name="WEB_MAIL_REC_LIMIT" id="WEB_MAIL_REC_LIMIT" class="BigInput"    positive_integer="true"  style="text-align:center;"> 兆
    </td>
    <td width="300" align="left">	
    </td>
</tr>




  <!-- 
  <tr class="TableData" align="center" height="30">
    <td width="120"><b>启用RTX客户端</b></td>
    <td align="left">
       <input type="radio" name="SEC_USE_RTX" id="SEC_USE_RTX1" value="1" ><label for="SEC_USE_RTX1">是</label>
       <input type="radio" name="SEC_USE_RTX" id="SEC_USE_RTX2" value="0" ><label for="SEC_USE_RTX2">否</label>
    </td>
    <td width="250" align="left">
       在安装RTX客户端的情况下，选择启用RTX可以通过OA中跟RTX进行通信。

    </td>
  </tr>
   -->
 <!--   <tr class="TableData" align="center" >
    <td width="140" style="background:#f0f0f0"><b>显示用户登录IP</b></td>
    <td align="left" style="background:#f0f0f0">
       <input type="radio" name="SEC_SHOW_IP" id="SEC_SHOW_IP0" value="0" ><label for="SEC_SHOW_IP0">不显示</label>
       <input type="radio" name="SEC_SHOW_IP" id="SEC_SHOW_IP1" value="1" ><label for="SEC_SHOW_IP1">仅管理员可见</label>
       <input type="radio" name="SEC_SHOW_IP" id="SEC_SHOW_IP2" value="2" ><label for="SEC_SHOW_IP2">所有用户可见</label>
    </td>
    <td width="300" align="left" style="background:#f0f0f0">
       全部人员列表和用户资料是否显示用户最近一次登录的IP.

    </td>
  </tr>
 <tr class="TableData" align="center" >
    <td width="140"><b>记忆在线状态</b></td>
    <td align="left">
       <input type="radio" name="SEC_ON_STATUS" id="SEC_ON_STATUS1" value="1" ><label for="SEC_ON_STATUS1">是</label>
       <input type="radio" name="SEC_ON_STATUS" id="SEC_ON_STATUS0" value="0" ><label for="SEC_ON_STATUS0">否</label>
    </td>
    <td width="300" align="left">
       用户重新登录后是否记忆上次设置的在线状态(如 忙碌、离开等)。

    </td>
  </tr>
 -->

</table>
</form>
</div>
</body>

</html>
