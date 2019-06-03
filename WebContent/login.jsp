<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.File"%>
<%@page import="org.jdom.input.SAXBuilder"%>
<%@page import="org.jdom.Element"%>
<%@page import="org.jdom.Document"%>
<%
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
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>行政复议工作平台</title>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link href="<%=contextPath %>/common/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<link href="<%=contextPath %>/common/supervise-common/components.css" rel="stylesheet" type="text/css"/>
<link href="<%=contextPath %>/common/supervise-common/plugins.css" rel="stylesheet"  type="text/css"/>
<link href="<%=contextPath %>/common/supervise-common/layout.css" rel="stylesheet" type="text/css"/>
<link href="<%=contextPath %>/common/supervise-common/themes/default.css" rel="stylesheet" type="text/css"/>
<link href="<%=contextPath %>/common/supervise-common/custom.css" rel="stylesheet" type="text/css"/>
<link href="<%=contextPath %>/common/supervise-common/login.css" rel="stylesheet" type="text/css"/>
<script src="./style/jquery.cookie.js"></script>
<script src="<%=contextPath %>/common/js/sys.js"></script>
<script src="<%=contextPath %>/common/js/sysUtil.js"></script>
<script src="<%=contextPath %>/common/backstretch/jquery.backstretch.min.js" type="text/javascript"></script>
<script src="<%=contextPath %>/common/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script>
var UkCode="<%=UkCode%>";
var dogId="";
var dogName=""; 

var contextPath = "<%=request.getContextPath()%>";

var secUserMem = "<%=secUserMem%>";//是否允许记忆用户名  1- 记录
var loginByCode = "<%=TeeSysProps.getString("LOGIN_BY_CODE")%>";

function doInit(){  
    refreshCode($("#vimage"));
    $('#myId').backstretch([
                    /* "/common/zt_webframe/imgs/login/supervise-login-01.jpg",
                    /* "/common/zt_webframe/imgs/login/supervise-login-02.jpg", 
                    "/common/zt_webframe/imgs/login/supervise-login-03.jpg",
                    "/common/zt_webframe/imgs/login/supervise-login-04.jpg"
                    "/common/zt_webframe/imgs/login/bg_1.png",
                    "/common/zt_webframe/imgs/login/bg_2.png",
                    "/common/zt_webframe/imgs/login/bg_3.png"*/
                    "/common/zt_webframe/imgs/login/xzfy_login01.png"
                    
                ], {
                    fade: 1000,
                    duration: 8000
                }
            );
    
}
/**
 * 保存
 */
function doLogin(){
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
        console.log(JSON.stringify(json));
        if (json.rtState){
               /*  //记录上次成功登陆的用户名
                if (json.rtData.saveUserName == "1") {
                  setCookie('userName',$('userName').value , 30);
                }else {
                  setCookie('userName','' , 30);
                }
                if (json.rtData.sessionToken) {
                  setCookie('SID_'  + json.rtData.seqId ,json.rtData.sessionToken , 1);
                } */
                if(secUserMem == '1'){//允许记忆卡
                    setCookie('userName',$('#userName').val() , 30);
                }
                var data = json.rtData;
                var theme = data.theme;
//              var themeIndex = 1;
//              var index = 1;
//              if(theme.length == 2){
//                  themeIndex = theme.substring(0,1);
//                  index = theme.substring(1,2);
//              }
                if(json.rtData.isAdmin == '9' || json.rtData.isAdmin == '1' ){
				    window.location.href = "<%=contextPath%>/gzpt/admin/index.jsp";						
				} else {
                	window.location.href = "<%=contextPath%>/system/frame/"+theme+"/index.jsp";
				}
        } else{ 
             switch(json.rtData.code){
                  case 0:{
                    $.MsgBox.newAlert("提示", json.rtMsg,function(){
                         $('#userName').focus();    
                    });
                    
            /*         $('loginForm').reset();
                    $('layout').show(); */
                    
                   
                   // window.location.href = "<%=contextPath%>/system/frame/default/index.jsp";
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
                    $.MsgBox.newAlert("提示", json.rtMsg,function(){
                        $('#pwd').value = '';
                        $('#layout').show();
                        $('#userName').focus();
                        
                    });
                    
                    break;
                  }
                  case 13:{
                      window.location.href = "<%=contextPath%>/system/frame/default/index.jsp";
                    break;
                  }
                  case 4:{
                    $.MsgBox.newAlert("提示", json.rtMsg,function(){
                         $('layout').show();
                    });
                   
                    break;
                  }
                  case 5:{
                    $.MsgBox.newAlert("提示", json.rtMsg,function(){
                        $('#pwd').value = '';
                        $('#layout').show();
                        $('#pwd').focus();
                        
                    });
                    
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
                    $.MsgBox.newAlert("提示", json.rtMsg,function(){
                        $('#pwd').value = '';
                        $('#layout').show();
                        $('#pwd').focus();
                        
                    });
                    break;
                  }
                  case 14: {
                    $.MsgBox.newAlert("提示", '验证码错误',function(){
                         $('#pwd').value = '';
                         $('#layout').show();
                         $('#pwd').focus();
                         refreshCode();
                    });
                   
                    break;
                  }
                  case 15: {
                     $.MsgBox.newAlert("提示", json.rtMsg);
                       break;
                  }
                  case 16: {
                      window.location = "/system/core/system/auth_info.jsp";
                      break;
                  }
                  case 21: {
                      $.MsgBox.newAlert("提示", 'Ukey认证失败！');
                      break;
                  }
                  default:{
                    $.MsgBox.newAlert("提示","登录失败!",function(){
                        $('#layout').show();
                    });
                    
                  }
             }
        }
    }
}

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
    
    if($('#userName').val() == ''){
          $.MsgBox.newAlert("提示","请输入用户名",function(){
              $('#userName').focus();
          });
          return false;
     }
     if (isValidateUserName($('#userName').val())) {
         $.MsgBox.newAlert("提示","您输入的用户名含有特殊字符！",function(){
             $('#userName').focus();
         });         
         return false;
     }
     
      
     if($("#verifyCode").val()=='' && loginByCode=="1"){
            $.MsgBox.newAlert("提示","请输入验证码",function(){
                $("#verifyCode").focus();   
            });

            return false;
        }
     return true;
}

function refreshCode(obj){
    $(obj).attr("src",contextPath+"/verifyCode/generate.action?rand="+new Date().getTime());
}

function reSet(){
    $('#userName').val('');
    $('#pwd').val('');
}

$(function () {
    if (getCookie('userName') && secUserMem == '1'){
          $('#userName').val(getCookie('userName'));
          $('#pwd').focus();
    }else{
          $("#userName").focus();
    }
    
    $('#pwd,#userName,#verifyCode').bind('keypress',function(event){
          if(event.keyCode == "13")    
          {
            doLogin();
          }
      });
    
    if(loginByCode=="1"){
        $("#verifyCodeLi").show();
    }
});
document.onselectstart = function(){
    return false;
}
</script> 
<style type="text/css">
   * {
        margin: 0;
        padding: 0;
    }

    a {
        text-decoration: none;
    }

    ul {
        list-style: none;
    }
        #myId{
        padding: 0;
        margin: 0;
        width: 100%;
        height: 100%;
        overflow: hidden;
        position: relative;
        z-index:1000;
    }
    
   .container {
        position: relative;
        width: 100%;
        height: 100%;
    }
    #carousel{
    height:100%;
    width:100%;
    }

    .container .bgi{
        height:100%;
    }
    
    .header{
       width:100%;
       z-index:999;
       height:80px;
       position: absolute;
       background: url("/common/zt_webframe/imgs/login/header_5.png");
       background-size:contain;
       background-position:center center;
    }
    .header i{
        display:block;
        float:left;
        width: 80px;
        height:100%;
        margin-left:20px;
        background: url("/logo.png") no-repeat;
        background-position:center 12px;
        background-size:60px 60px;
    }
    .header .newp{
        margin:0 58px;
        padding: 0;
        float:left;
        color:#fff;
        font-size: 36px;
        line-height: 80px;
    }
    .container .box {
        position: absolute;
        width: 386px;
        height: 411px;
        border-radius:5px;
        /* height: 36.3333%; */
        /*min-width: 318px; */
        /* min-height: 243px; */
        /*margin: 0 auto;*/
        background-color: rgba(255,255,255,0.4);
        top:20%;
        right: 10%;
    }
    
    
    .input-header{
         position: relative;
         height:55px;
         width:100%;
         line-height:55px;
         font-size:16px;
         text-align:center;
         font-weight: 700;
    }
    
    .input-split{
         display:block;
         width:100%;
         height:4px;
         background:url('/common/zt_webframe/imgs/login/input_split.png');
    }

    .container .box input {
        font-family: "Microsoft Yahei";
    }

    .container .box .input1 {
        border: none;
        border: 1px solid #e8e8e8;
        margin: 37px 39px 27px;
        display:block;
        height:39px;
        width:295px;
        padding: 10px 10px 10px 45px;
        line-height:27px\9;
        background-repeat: no-repeat;
        background-position: 2.9017% center;
        -webkit-background-size: 6%;
        background-size: 6%;
        outline: none;
        background-color:#f9faff;
    }

    .container .box .input2 {
        border: none;
        border: 1px solid #e8e8e8;
         margin: 0 39px 27px;
        height: 40px;
       width:295px;
        padding: 10px 10px 10px 45px;
        display:block;
        line-height:27px\9;
        background-repeat: no-repeat;
        background-position: 2.9017% center;
        -webkit-background-size: 6%;
        background-size: 6%;
        outline: none;
        font-family: "Microsoft Yahei";
        background-color:#f9faff;
    }

    .container .box .input3 {
        width: 309px;
        height: 40px;
        margin: 50px auto 0;
        border: none;
        display:block;
        outline: none;
        background-color: #3582f8;
        color: #fff;
        text-align: center;
        cursor: pointer;
        font-size:18px;
        font-family: "Microsoft YaHei";
    }

    input::-ms-input-placeholder {
        font-size: 10%;
    }

    input::-webkit-input-placeholder {
        font-size: 10%;
    }

    .container .box .check {
        background-color:#f9faff;
        width: 156px;
        height:39px;
        outline: none;
        border: none;
        border: 1px solid #e8e8e8;
        margin-left:38.5px;
        padding:10px 0px 10px 15px;
        margin-right: 10px;
    }
/* 
    div.container .box .pic {
        float:right;
                width: 70px;
        margin-top:32px;
        margin-right:97px;
        border-color: gray;
    } */
    #inputArea a{
    display:inline;
        margin-left: 10px;
    }

    .container .box .img {
        width: 225px;
        height: 32px;
        margin: 0 auto;
        margin-top: 10px;
    }

    .container .box .img img {
        width: 17px;
    }
    .container .box .img1{
        margin-left:16px;
    }
    .container .box .img .img2 {
        padding-left: 68px;
        padding-right: 61px;
    }

    .container p {
        width: auto;
        position: absolute;
        left: 37px;
        margin: 5px 0 10px;
    }

    .forgetpsw {
        margin-left: 195px;
    }
    
    .footer{
        position:fixed;
        bottom:0px;
        left:0px;
        color:rgb(0,0,0);
        text-align:center;
        line-height:98px;
        font-size:12px;
        width:100%;
        background-color:#fff;
    }
    .carousel-inner{overflow:visible;}
   /*重置默认样式*/
   .container{padding:0;}
   .item {
     height: auto; 
    line-height: normal; 
     margin: 0; 
    }   
    .item img{display:block;height:800px;}
    @media screen and (min-width: 768px){
        .carousel-indicators {
            bottom: 100px;
        }
        
    }
    @media screen and (max-width: 1366px) {     
        .header {
            height:65px;
        }
        .header i{
            background-size:45px 45px;
            background-position:center 13px;
            margin-left:0;
        }
        .header .newp{
            font-size: 26px;
            line-height: 65px;
            margin: 0px 40px;
        }
        .footer {
            height: 50px;
            line-height: 50px;
        }
        .container .box {
            width: 336px;
            height: 358px;
        }
        .input-header{
            height: 50px;
        }
        .container .box .input1 {
            width:265px;
            margin: 33px 34px 27px;
            padding: 5px 0 5px 45px;
            line-height: 30px;
        }
        .container .box .input2 {
            width:265px;
            margin: 0 34px 27px;
            padding: 5px 0 5px 45px;
            line-height: 30px;
        }
        .container .box .input3 {
            width: 270px;
            height: 35px;
            margin: 40px auto 0;
        }
        .container .box .check {
            width: 114px;
            margin-left: 34.5px;
            height: 33px;
            padding: 4px 0px 10px 15px;
        }
        .container p {
            left: 33px;
        }
        .forgetpsw {
            margin-left: 154px;
        }
        .input-split {
            background: url("/common/zt_webframe/imgs/login/input_split1.png");
        }
        .carousel-indicators {
            bottom: 50px;
        }
        .box #inputArea .eye_close {
            top: 75px;
            right: 45px;
        }
        .box #inputArea .login_user {
            top: 9px;
            left: 50px;
        }
        .box #inputArea .login_psw {
            top: 75px;
            left: 50px;
        }
    }
    #inputArea{
        display: block;
        position: relative;
    }
    #inputArea i{
        position:absolute;
    }
    #inputArea i img{
        width: 18px;
    }
    #inputArea .login_user{
        top: 10px;
        left: 53px;
    }
    #inputArea .login_psw{
        top: 75px;
        left: 53px;
    }
    #inputArea .eye_close{
        top: 75px;
        right: 57px;
    }
    #inputArea .eye_close img{
        width: 20px;
        height: 13px;
    }
     /* @media screen and (max-width: 1366px) {
        .div.container .box {
            top:175px;
            right: 200px;
        }
    } */
 .carousel-indicators li {
    display: inline-block;
    width: 10px;
    height: 10px;
    margin: 1px;
    text-indent: -999px;
    border-radius: 10px;
    cursor: pointer;
    background-color: #C1BFBE;
} 

</style>
</head>

<body id="myId" onload="doInit();" class="login-back login">
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

<div class="container">

    <!-- 头部 -->
    <div class="header">
    <i></i>
    <p class="newp" >行政复议工作平台</p>
    </div>  
  <!-- BEGIN LOGIN -->
    
  
    <!-- BEGIN LOGIN FORM -->
        <form id="form1" >
         <div class="box">
            <!-- <img state="scan"  onclick="switchScan(this)" id="scanImg" style="cursor:pointer;z-index:9999;width:30px;position:absolute;top:0px;right:0px;" src="/common/zt_webframe/imgs/login/codeMode.png" />
             -->
             <div class="input-header">账号登录</div>
             <span class="input-split"></span>
             <span id="inputArea">
                <input type="text" placeholder="用户名" class="input1" name="userName" id="userName" onmouseover="$(this).select()">
                <input type="password" placeholder="密码" class="input2" name="pwd" id="pwd" onmouseover="$(this).select()">
                <i class="login_user"><img src="/common/zt_webframe/imgs/login/input1.gif" /></i>
                <i class="login_psw"><img src="/common/zt_webframe/imgs/login/input2.gif" /></i>
                <i class="eye_close" ><img src="/common/zt_webframe/imgs/login/eye_close.png" /></i>
                <!-- <input type="hidden" id="pwdCrypt" name="pwdCrypt"/> -->
                <input type="text" id="verifyCode" name="verifyCode" class="check" placeholder="验证码" onmouseover="$(this).select()"autocomplete="off">
                <img  id="vimage" class="pic" src="<%=contextPath %>/verifyCode/generate.action" title="点击图片切换验证码" alt="点击刷新" onclick="refreshCode(this)">
                <%-- /common/zt_webframe/imgs/login/input4.gif --%>
                <a id="codeReplace" onclick="refreshCode()">换一组</a>
                <script>
                    function refreshCode(){
                        $("#vimage").attr("src",contextPath+"/verifyCode/generate.action?rand="+new Date().getTime());
                    }
                </script>
            
                <input type="button"  name="submit" id="login" value="登&nbsp;&nbsp;&nbsp;录" class="input3"  onclick="doLogin()" >
                <p><span>立即注册</span> <span class="forgetpsw">忘记密码</span></p>
               <div class="img">
                    <!-- <img src="/common/zt_webframe/imgs/login/win.gif" title="PC协同办公程序" class= 'img1' style="cursor:pointer" onclick="window.open('http://www.zatp.com.cn/PC_Setup.exe');">
                     --><span>
                        <!-- <img src="/common/zt_webframe/imgs/login/andr_09.gif"  class="img2" id="AndroidImg" style="cursor:pointer" onclick="window.open('http://a.app.qq.com/o/simple.jsp?pkgname=com.zatp.app');">
                         --><img id="AndroidQrCode" style="display:none;position:absolute;top:103%;left:123px;z-index:99999;width:80px;" src="<%=contextPath %>/systemAction/qrCodeDownload.action?dlType=Android"/>
                    </span>
                    <span>
                        <!-- <img src="/common/zt_webframe/imgs/login/apple_11.gif"  id="iOSImg"   style="cursor:pointer" onclick="window.open('https://itunes.apple.com/us/app/%E7%A7%BB%E5%8A%A8%E5%8A%9E%E5%85%AC2017/id1203190020?mt=8');">
                         --><img id="iOSQrCode" style="display:none;position:absolute;top:103%;left:204px;z-index:99999;width:80px;" src="<%=contextPath %>/systemAction/qrCodeDownload.action?dlType=iOS"/>
                    </span>
                </div>
                <div>
                    
                    
                </div>
            </span>
            <span id="scanArea" style="display:none;">
                <center><img onclick="refreshScanCode(this)" title="点击重新生成二维码" id="codeImg" src="" style="width:150px;z-index:9999;position:inherit;margin:20px;cursor:pointer" /><center>
            </span>
        </div>      
        </form>


  <!-- END LOGIN -->
</div>

        <form id="form13" > 
        </form>
    <div class="footer">&copy;2001-2018 &nbsp;&nbsp;&nbsp; 北京北大软件工程股份有限公司 &nbsp;&nbsp;&nbsp; 版权所有 &nbsp;&nbsp;&nbsp; 京ICP备09003427号  </div>
 <!--  <div style="position:fixed;bottom:25px;height:20px;left:-2px;color:#aaa;text-align:center;right:0px;font-size:12px">
    	<label class="text-right" style="vertical-align:middle;height:20spx;">版权所有：司法部行政执法协调监督局</label>
    </div>
    <div style="position:fixed;bottom:4px;height:20px;left:-2px;color:#aaa;text-align:center;right:0px;font-size:12px">
    	<label class="text-right" style="vertical-align:top;height:20px;">技术支持：司法部信息中心       北京北大软件工程股份有限公司</label>
<!--        Copyright © 2001-2015 All Rights Reserved. &nbsp;&nbsp; -->
       <!-- <a href="http://zatp.com.cn/oarelations.html" target="_blank">OA常用工具下载</a> -->
    </div>-->   
   
 
<script>
$("#inputArea .eye_close").click(function(){
    if(($("#inputArea .eye_close img").attr("src")) == "/common/zt_webframe/imgs/login/eye.png"){
      $("#inputArea .eye_close img").attr("src","/common/zt_webframe/imgs/login/eye_close.png");
      $("#pwd").attr("type","password");
    }else{
    $("#inputArea .eye_close img").attr("src","/common/zt_webframe/imgs/login/eye.png");
     $("#pwd").attr("type","text");
    }
});
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

var newImg;
var newCode;
function switchScan(obj){
    var state = $(obj).attr("state");
    if(state=="scan"){
        $(obj).attr("state","input");
        $("#inputArea").hide();
        $("#scanArea").show();
        $(obj).attr("src","/common/zt_webframe/imgs/login/inputMode.png");
        newImg = new GUID();
        newCode = newImg.newGUID();
        $("#codeImg").attr("src","/systemAction/qrCodeLoginImage.action?guid=" + newCode);
    }else{
        $(obj).attr("state","scan");
        $("#inputArea").show();
        $("#scanArea").hide();
        $(obj).attr("src","/common/zt_webframe/imgs/login/codeMode.png");
    }
}

function refreshScanCode(obj){
    newImg = new GUID();
    newCode = newImg.newGUID();
    $(obj).attr("src","/systemAction/qrCodeLoginImage.action?guid=" + newCode)
}

function GUID() {
    this.date = new Date();
    /* 判断是否初始化过，如果初始化过以下代码，则以下代码将不再执行，实际中只执行一次 */
    if (typeof this.newGUID != 'function') {   /* 生成GUID码 */
        GUID.prototype.newGUID = function () {
            this.date = new Date();
            var guidStr = '';
            sexadecimalDate = this.hexadecimal(this.getGUIDDate(), 16);
            sexadecimalTime = this.hexadecimal(this.getGUIDTime(), 16);
            for (var i = 0; i < 9; i++) {
                guidStr += Math.floor(Math.random() * 16).toString(16);
            }
            guidStr += sexadecimalDate;
            guidStr += sexadecimalTime;
            while (guidStr.length < 32) {
                guidStr += Math.floor(Math.random() * 16).toString(16);
            }
            return this.formatGUID(guidStr);
        }
        /* * 功能：获取当前日期的GUID格式，即8位数的日期：19700101 * 返回值：返回GUID日期格式的字条串 */
        GUID.prototype.getGUIDDate = function () {
            return this.date.getFullYear() + this.addZero(this.date.getMonth() + 1) + this.addZero(this.date.getDay());
        }
        /* * 功能：获取当前时间的GUID格式，即8位数的时间，包括毫秒，毫秒为2位数：12300933 * 返回值：返回GUID日期格式的字条串 */
        GUID.prototype.getGUIDTime = function () {
            return this.addZero(this.date.getHours()) + this.addZero(this.date.getMinutes()) + this.addZero(this.date.getSeconds()) + this.addZero(parseInt(this.date.getMilliseconds() / 10));
        }
        /* * 功能: 为一位数的正整数前面添加0，如果是可以转成非NaN数字的字符串也可以实现 * 参数: 参数表示准备再前面添加0的数字或可以转换成数字的字符串 * 返回值: 如果符合条件，返回添加0后的字条串类型，否则返回自身的字符串 */
        GUID.prototype.addZero = function (num) {
            if (Number(num).toString() != 'NaN' && num >= 0 && num < 10) {
                return '0' + Math.floor(num);
            } else {
                return num.toString();
            }
        }
        /*  * 功能：将y进制的数值，转换为x进制的数值 * 参数：第1个参数表示欲转换的数值；第2个参数表示欲转换的进制；第3个参数可选，表示当前的进制数，如不写则为10 * 返回值：返回转换后的字符串 */
        GUID.prototype.hexadecimal = function (num, x, y) {
            if (y != undefined) {
                return parseInt(num.toString(), y).toString(x);
            }
            else {
                return parseInt(num.toString()).toString(x);
            }
        }
        /* * 功能：格式化32位的字符串为GUID模式的字符串 * 参数：第1个参数表示32位的字符串 * 返回值：标准GUID格式的字符串 */
        GUID.prototype.formatGUID = function (guidStr) {
            var str1 = guidStr.slice(0, 8) + '-', str2 = guidStr.slice(8, 12) + '-', str3 = guidStr.slice(12, 16) + '-', str4 = guidStr.slice(16, 20) + '-', str5 = guidStr.slice(20);
            return str1 + str2 + str3 + str4 + str5;
        }
    }
}

function loginByImg() {
    $.ajax({
        type: "post",
        url: "/systemAction/qrCodeLoginCheck.action",
        data: {
            guid: newCode
        },
        dataType: "json",
        success: function (data) {
            if (data.rtState) {
                var id = data.rtData.userId;
                var pwd = data.rtData.pwdCrypt;
                $("#userName").val(id);
                $("#pwdCrypt").val(pwd);
                doLogin();
            }
            setTimeout("loginByImg()",2000);
        },
        error: function (err) {
            setTimeout("loginByImg()",2000);
        }
    });
}
loginByImg();

</script>
</body>

</html>