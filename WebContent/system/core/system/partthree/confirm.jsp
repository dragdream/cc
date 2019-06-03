<%@page import="com.tianee.webframe.util.auth.TeeFunctionControl"%>
<%TeeFunctionControl.distinguishTheVersion(request,response);%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/header/header2.0.jsp" %>
<title></title>
<script type="text/javascript">
//初始化
function doInit(){
	var pwdFlag=<%=session.getAttribute("pwdFlag")%>;
	if(pwdFlag=="1"||pwdFlag==1){
		window.location.href=contextPath+"/system/core/system/partthree/index.jsp";
	}
}

//确定
function doConfirm(){
	var  pwd=$("#pwd").val();
	var url=contextPath+"/teePartThreeController/checkPwd.action";
	var json=tools.requestJsonRs(url,{pwd:pwd});
	if(json.rtState){
		window.location.href=contextPath+"/system/core/system/partthree/index.jsp";
	}else{
		$.MsgBox.Alert_auto("超级密码输入错误！");
	}
}

</script>
</head>
<body onload="doInit()" style="padding-left: 10px;padding-right: 10px">
   <div id="toolbar" class = "topbar clearfix">
	<div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/core/system/partthree/imgs/icon_pwd.png">
		<span class="title">输入超级密码</span>
	</div>
   </div>
   <div style="width: 100%" >
      <table class="TableBlock_page">
       <tr>
         <td colspan="2" style="text-indent: 15px">
            <span>说明：第一次进入时密码为123456，进入后即可看到“超级密码设置”</span>
         </td>
       </tr>
       <tr>
         <td style="text-indent: 15px;width: 12%">请输入超级密码：</td>
         <td>
            <input type="password" name="pwd" id="pwd" style="width: 250px;height: 25px;border: 1px solid #dadada;" />
         </td>
       </tr>
       <tr>
         <td colspan="2">
           <div style="margin-left: 200px">
              <input type="button" class="btn-win-white" value="确定" onclick="doConfirm();"/>
           </div>
         </td>
       </tr>
   </table>
   </div>
   
</body>
</html>