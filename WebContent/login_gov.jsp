<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.File"%>
<%@page import="org.jdom.input.SAXBuilder"%>
<%@page import="org.jdom.Element"%>
<%@page import="org.jdom.Document"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
	String contextPath = request.getContextPath();
	String secUserMem = TeeSysProps.getString("SEC_USER_MEM");
	
	
	//读取ukcode
	   File file=new File(TeeSysProps.getRootPath()+"/system/ukey/auth_code.xml");
	   FileInputStream  in=new FileInputStream(file);
	   SAXBuilder builder = new SAXBuilder();
		 Document doc = null;
		try {
			doc = builder.build(in);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Element root = doc.getRootElement();
		String UkCode=root.getChild("authcode").getText();
%>
<title><%=TeeSysProps.getString("IE_TITLE")%></title>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<link href="./style/css/index.css" type="text/css" rel="stylesheet">
<script src="<%=contextPath %>/common/js/jquery-1.11.0.min.js"></script>
<script src="./style/jquery.cookie.js"></script>
<script src="<%=contextPath %>/common/js/tools.js"></script>
<script src="<%=contextPath %>/common/js/sys.js"></script>
<script src="<%=contextPath %>/common/js/sysUtil.js"></script>

<script>

var UkCode="<%=UkCode%>";
var contextPath = "<%=request.getContextPath()%>";

var secUserMem = "<%=secUserMem%>";//是否允许记忆用户名  1- 记录
var loginByCode = "<%=TeeSysProps.getString("LOGIN_BY_CODE")%>";
var dogId="";
var dogName=""; 
/**
 * 保存
 */
function doLogin(){
	var dogId="";
	var dogName="";
	try{
		//获取uk用户名  和  uk的dogid
		var res = AuthIE.Open("<dogscope/>", UkCode);
		if(res==0){
			AuthIE.GetDogID();
			dogId = AuthIE.DogIdStr;
			AuthIE.GetUserName();
		    dogName = AuthIE.UserNameStr;	 
		}else{
			dogId ="";
		    dogName ="";	 
		}
		AuthIE.Close();
	}catch(e){
		dogId="";
		dogName="";
	}
	
	if (checkForm()){
		var url = "<%=contextPath%>/systemAction/doLoginIn.action";
		var para =  tools.formToJson($("#form1")) ;
		para["dogId"]=dogId;
		para["dogName"]=dogName;
		
		
		
		var json = tools.requestJsonRs(url,para);
		if (json.rtState){
			    if(secUserMem == '1'){//允许记忆卡
			    	setCookie('userName',$('#userName').val() , 30);
			    }
			    var data = json.rtData;
			    var theme = data.theme;
			    window.location.href = "<%=contextPath%>/system/frame/"+theme+"/index.jsp";
		} else{ 
			 switch(json.rtData.code){
			      case 0:{
			        alert(json.rtMsg);
			        
			        $('#userName').focus();
			        break;
			      }
			      case 1:{
			      }
			      case 2:{
			      }
			      case 3:{
			      }
			      case 9:{
			      }
			      case 10:{
			      }
			      case 11:{
			      }
			      case 12:{
			        alert(json.rtMsg);
			        $('#pwd').value = '';
			        $('#layout').show();
			        $('#userName').focus();
			        break;
			      }
			      case 13:{
			    	  window.location.href = "<%=contextPath%>/system/frame/default/index.jsp";
			        break;
			      }
			      case 4:{
			        alert(json.rtMsg);
			        $('layout').show();
			        break;
			      }
			      case 5:{
			        alert(json.rtMsg);
			        $('#pwd').value = '';
			        $('#layout').show();
			        $('#pwd').focus();
			        break;
			      }
			      case 6:{
			    	  if(secUserMem == '1'){//允许记忆卡
			    		  setCookie('userName',$('#userName').val() , 1);
			          }
			        window.location = contextPath + "/system/core/system/security/editPwd.jsp?code=6";
			        break;
			      }
			      case 7:{
			    	  if(secUserMem == '1'){//允许记忆卡
			    		  setCookie('userName',$('#userName').val() , 1);
			          }
			      	  window.location = contextPath + "/system/core/system/security/editPwd.jsp?code=7";
			          break;
			      }
			      case 8:{
			    	 alert(json.rtMsg);
			        $('#pwd').value = '';
			        $('#layout').show();
			        $('#pwd').focus();
			        break;
			      }
			      case 14: {
			        alert('验证码错误');
			        $('#pwd').value = '';
			        $('#layout').show();
			        $('#pwd').focus();
			        break;
			      }
			      case 15: {
			    	  alert(json.rtMsg);
				       break;
				  }
			      case 16: {
			    	  window.location = "/system/core/system/auth_info.jsp";
				      break;
				  }
			      case 21: {
			    	 alert('Ukey认证失败！');
			    	  break;
				  }
			      default:{
			        alert("登录失败!");
			        $('#layout').show();
			      }
			 }
		}
	}
}
//回车查询 Firefox与IE兼容
function keydown(e) {

    var e=e||event;  

    if(e.keyCode==13){//回车键的键值为13

    	$("#login").click();

    } 
}
document.onkeydown=keydown;
/**
 * 校验有没有字符串
 */
function isValidateUserName(value){
	return /[\~!`@;=#\+\|\$%\^&\*\.\(\)\[\]\{\}<>\?\\\/\'\"]$/.test(value);
}
/**
 * 校验表单
 */
function checkForm(){
	if($('#user').val() == ''){
	      alert("请输入用户名");
	      $('#user').focus();
	      return false;
	 }
	 if (isValidateUserName($('#user').val())) {
	     alert("您输入的用户名含有特殊字符！");
	     $('#user').focus();
	     return false;
	 } 
	 return true;
}

$(function () {
	$("#userName").val("");
	$("#pwd").val("");
    if (getCookie('userName')){
  		  $('#userName').val(getCookie('userName'));
  		  $('#user').val(getCookie('userName'));
  	   	  $('#pwd').focus();
  	}else{
  		  $("#user").focus();
  	}
	
	var sub = $("#login");
    sub.hover(function () {
        sub.css("background-color","#ed4045");
    },function () {
        sub.css("background-color","#e54246");
    });
  	
});


</script>
</head>
<body style="overflow:hidden">
<%
if("1".equals(TeeSysProps.getString("USB_KEY"))){
	%>
	<object id="AuthIE" name="AuthIE" width="0px" height="0px"
	   codebase="/system/ukey/DogAuth.CAB"
	   classid="CLSID:05C384B0-F45D-46DB-9055-C72DC76176E3">
	</object>
	<%
}
%>
	<div class="header">
		<img src="./style/images/login1.png">
	</div>
     <div class="login_div">  
		<div class="form_title">
			<img src="./style/images/login2.png" alt="" class="img2">
		</div>
			<form class="login_form" id="form1">
				<div>
					<div class="userName_bg">
						<span class="userName_span">
							<li><img src="./style/images/icon1.png"></img></li>
						</span>
						<input type="text" class="input" name="userName" id="userName" style="line-height:40px" placeholder="用户名">
					</div>
					<div class="pwd_bg">
						<span class="pwd_span">
							<li><img src="./style/images/icon2.png"></img></li>
						</span>
						<input class="input" name="pwd" id="pwd" type="password"  style="line-height:40px" placeholder="密码">
					</div>
					<input type="button" id="login" onclick="doLogin()" value="登 录">
					<div style="margin-top:10px;width:300px;text-align:center;position:absolute">
						<img src="/common/zt_webframe/imgs/login/windows.png" title="PC协同办公程序" class= 'img1' style="cursor:pointer;height:20px" onclick="window.open('http://www.zatp.com.cn/PC_Setup.exe');">
		                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		                <span>
		                	<img src="/common/zt_webframe/imgs/login/android.png"  class="img2" id="AndroidImg" style="cursor:pointer;height:20px" onclick="window.open('http://a.app.qq.com/o/simple.jsp?pkgname=com.zatp.app');">
		                	<img id="AndroidQrCode" style="display:none;position:absolute;top:103%;left:114px;z-index:99999;width:80px;" src="<%=contextPath %>/systemAction/qrCodeDownload.action?dlType=Android"/>
		                </span>
		                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		                <span>
		                	<img src="/common/zt_webframe/imgs/login/ios.png"  id="iOSImg"   style="cursor:pointer;height:20px" onclick="window.open('https://itunes.apple.com/us/app/%E7%A7%BB%E5%8A%A8%E5%8A%9E%E5%85%AC2017/id1203190020?mt=8');">
		                	<img id="iOSQrCode" style="display:none;position:absolute;top:103%;left:180px;z-index:99999;width:80px;" src="<%=contextPath %>/systemAction/qrCodeDownload.action?dlType=iOS"/>
		                </span>
					</div>
				</div>
			</form>
    </div>
<script>
$("#AndroidImg").hover(function(){
	$("#AndroidQrCode").show();
	$("#iOSQrCode").hide();
},function(){
	$("#AndroidQrCode").hide();
	$("#iOSQrCode").hide();
});

$("#iOSImg").hover(function(){
	$("#AndroidQrCode").hide();
	$("#iOSQrCode").show();
},function(){
	$("#AndroidQrCode").hide();
	$("#iOSQrCode").hide();
});
</script>
</body></html>