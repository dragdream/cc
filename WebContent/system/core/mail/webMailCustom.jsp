<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<%
	String sid = request.getParameter("sid");
	if(sid==null){
		sid = "";
	}
%>
<title>系统界面设置</title>

<script type="text/javascript">
var sid = "<%=sid%>";
var mails={};
mails['163.com']=new Array('pop.163.com',110,0,'smtp.163.com',25,0,0,0);
mails['vip.163.com']=new Array('pop.vip.163.com',110,0,'smtp.vip.163.com',25,0,0,0);
mails['188.com']=new Array('pop.188.com',110,0,'smtp.188.com',25,0,0,0);
mails['126.com']=new Array('pop.126.com',110,0,'smtp.126.com',25,0,0,0);
mails['yeah.net']=new Array('pop.yeah.net',110,0,'smtp.yeah.net',25,0,0,0);
mails['qq.com']=new Array('pop.qq.com',110,0,'smtp.qq.com',25,0,0,0);
mails['vip.qq.com']=new Array('pop.qq.com',110,0,'smtp.qq.com',25,0,1,0);
mails['sina.com']=new Array('pop.sina.com',110,0,'smtp.sina.com',25,0,0,0);
mails['vip.sina.com']=new Array('pop3.vip.sina.com',110,0,'smtp.vip.sina.com',25,0,0,0);
mails['sohu.com']=new Array('pop.sohu.com',110,0,'smtp.sohu.com',25,0,0,0);
mails['tom.com']=new Array('pop.tom.com',110,0,'smtp.tom.com',25,0,0,0);
mails['gmail.com']=new Array('pop.gmail.com',995,1,'smtp.gmail.com',465,1,1,0);
mails['yahoo.com.cn']=new Array('pop.mail.yahoo.com.cn',995,1,'smtp.mail.yahoo.com.cn',465,1,1,0);
mails['yahoo.cn']=new Array('pop.mail.yahoo.cn',995,1,'smtp.mail.yahoo.cn',465,1,1,0);
mails['21cn.com']=new Array('pop.21cn.com',110,0,'smtp.21cn.com',25,0,0,0);
mails['21cn.net']=new Array('pop.21cn.net',110,0,'smtp.21cn.net',25,0,0,0);
mails['263.net']=new Array('263.net',110,0,'smtp.263.net',25,0,0,0);
mails['x263.net']=new Array('pop.x263.net',110,0,'smtp.x263.net',25,0,0,0);
mails['263.net.cn']=new Array('263.net.cn',110,0,'263.net.cn',25,0,0,0);
mails['263xmail.com']=new Array('pop.263xmail.com',110,0,'smtp.263xmail.com',25,0,0,0);
mails['foxmail.com']=new Array('pop.foxmail.com',110,0,'smtp.foxmail.com',25,0,0,0);
mails['hotmail.com']=new Array('pop3.live.com',995,1,'smtp.live.com',25,1,1,0);
mails['live.com']=new Array('pop3.live.com',995,1,'smtp.live.com',25,1,1,0);

function FillSettings(email){
   $('#emailUser').val(email);
   if(email.trim()=="" || email.indexOf("@")<0) {
     return;
   }
   var email = email.substr(email.indexOf("@")+1).trim();
   if(!mails[email]) {
     return;
   }
   $('#popServer').val(mails[email][0]);
   $('#pop3Port').val(mails[email][1]);
   $('#smtpServer').val(mails[email][3]);
   $('#smtpPort').val(mails[email][4]);
   if(mails[email][5]==1){
	   $('#smtpSsl').attr("checked");
   }
   //$('#smtpPass').val(mails[email][7]);
}
function doInit(){
	$("[title]").tooltips();
	if(sid!=""){
		var url = "<%=contextPath %>/mail/getWebMailById.action?sid="+sid;
		var para =  {} ;
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			bindJsonObj2Cntrl(jsonRs.rtData);
		}
	}

}

/**
 * 保存
 */
function doSave(){
	 if (checkFrom()){
		var url = "<%=contextPath %>/mail/addOrUpdateWebMail.action";
		var para =  tools.formToJson($("#form1")) ;
		var jsonRs = tools.requestJsonRs(url,para);
		alert(jsonRs.rtMsg);
		returnList();
	} 
}

function checkFrom(){
	var loginType = document.getElementById("loginType");
	if(!loginType.value){
  	    alert("邮件地址不能为空！");
  	    loginType.focus();
  		loginType.select();
		return false;
	}
	var emailUser = document.getElementById("emailUser");
	if(!emailUser.value){
  	    alert("邮件账户不能为空！");
  	    emailUser.focus();
  		emailUser.select();
		return false;
	}
	var emailPass = document.getElementById("emailPass");
	if(!emailPass.value){
  	    alert("邮件密码不能为空！");
  	 	emailPass.focus();
  		emailPass.select();
		return false;
	}
	return true;
}
function checkStr(str){ 
    var re=/[\\"']/; 
    return str.match(re); 
  }
  
function returnList(){
	var url = contextPath + "/mail/setWebMailIndex.action";
	window.location = url;
}
 
</script>
</head>
<body onload="doInit()" topmargin="5" style="padding:8px;">
<form enctype="multipart/form-data" method="post" name="form1" id="form1">
  <table class="TableBlock" width="500" align="center">
   <tr>
    <td colspan=2 class="TableHeader">配置邮件账户</td>
   </tr>
   <tr>
    <td nowrap class="TableData">电子邮件地址：</td>
    <td nowrap class="TableData" >
        <input type="text" name="loginType" id="loginType" class="BigInput" size="40" maxlength="100" value="" style="" onkeyup='FillSettings(this.value);'>&nbsp;<br>
        如：123@qq.com
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData">接收服务器(POP3)：</td>
    <td nowrap class="TableData">
        <input type="text" name="popServer" id="popServer" class="BigInput" size="25" maxlength="100" value="">&nbsp;
        	端口号：<input type="text" name="pop3Port" id="pop3Port" class="BigInput" size="5" maxlength="100" value="110"><br>
        	  <div class="checkbox">
			    <label>
			      <input type="checkbox" id="pop3Ssl" name="pop3Ssl" value="1">此服务器要求安全连接(SSL)
			    </label>
			  </div>
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData">发送服务器(SMTP)：</td>
    <td nowrap class="TableData">
        <input type="text" name="smtpServer" id="smtpServer" class="BigInput" size="25" maxlength="100" value="">&nbsp;
                        端口号：<input type="text" name="smtpPort" id="smtpPort" class="BigInput" size="5" maxlength="100" value="25"><br>
              <div class="checkbox">
			    <label>
			      <input type="checkbox" id="smtpSsl" name="smtpSsl"  value="1">此服务器要求安全连接(SSL)
			    </label>
			  </div>
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData">登录账户：</td>
    <td nowrap class="TableData" >
        <input type="text" name="emailUser" id="emailUser" class="BigInput" size="40" maxlength="100" value="" style="">&nbsp;
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData">登录密码：</td>
    <td nowrap class="TableData" >
        <input type="password" name="emailPass" id="emailPass" class="BigInput" size="40" maxlength="100" value="" style="">&nbsp;
    </td>
   </tr>
     <tr>
    <td nowrap class="TableData">是否检查外部邮件：</td>
    <td nowrap class="TableData" >
        <select type="text" name="checkFlag" id="checkFlag" class="BigInput">
        	<option value="1">是</option>
        	<option value="0">否</option>
        </select>
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData">默认邮箱：</td>
    <td nowrap class="TableData" >
      <div class="checkbox">
	    <label>
	      <input type="checkbox" id="isDefault" name="isDefault"  value="1">作为内部邮件外发默认邮箱
	    </label>
	  </div>
    </td>
   </tr>
      <tr>
    <td nowrap class="TableData">收信设置：</td>
    <td nowrap class="TableData" >
      <div class="checkbox">
	    <label>
	      <input type="checkbox" id="recvDel" name="recvDel" value="1">收取邮件时，从服务器上删除
	    </label>
	  </div>
    </td>
   </tr>
   <tr>
    <td nowrap  class="" colspan="2" align="center">
        <input type="hidden" name="sid" id="sid" value="">
        <input type="button" id="" value="保存" class="btn btn-primary" onclick="doSave();">&nbsp;
        <input type="button" id="" value="返回" class="btn btn-warning" onclick="returnList();">
    </td>
   </tr>
</table>
</form>
</body>
</html>