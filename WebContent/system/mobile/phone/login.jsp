<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/system/mobile/header.jsp" %>
<title>登录</title>
<meta charset="utf-8" />
<link href="<%=mobilePath%>/phone/templete/style.css" rel="stylesheet" type="text/css" />

</head>

<body>
<div id="logo">
   <div id="form">
      <form name="form1" method="post" action="">
      <div id="form_input">
         <div class="user"><input class="text" name="userName" id="userName" maxlength="20" value="" type="text"></div>
         <div class="pwd"><input class="text" name="password" id="password" value="" type="password"></div>
      </div>
      <div id="form_submit">
         <input class="submit" title="登录" value=" " type="button" onclick="doLogin();">
      </div>
		<input name="SaveDevType" value="" type="hidden">
		<input name="DevType" value="mobi" type="hidden">
      </form>
   </div>
   <div id="msg">
   </div>
</div>

</body>
</html>


<script type="text/javascript">

/**
 * 登录处理
 */
function doLogin(){
	$("#msg").html("");
	if(checkForm()){
		var userName = $('#userName').val();
		var password = $('#password').val();
		var param = {userName:userName , password: password};
		var url = "<%=contextPath%>/mobileSystemAction/doLoginIn.action";
		$.ajax({
		  type: 'GET',
		  url: url,
		  // data to be added to query string:
		  data: param,
		  // type of data we are expecting in return:
		 // dataType: 'text',
		  timeout: 300,
		  async :true,
		  timeout :6000,
		  success: function(data){
			 // alert(data)
			  var dataJson = eval('(' + data + ')');
          	  var rtData = dataJson.rtData;
          	  var rtMsg = dataJson.rtMsg;
          	  var rtState = dataJson.rtState;
			  if(rtState){
				  window.location.href = "<%=mobilePath%>/phone/system/index.jsp"; 
			  }else{
				  $("#msg").html(rtMsg);
			  } 
		  },
		  error: function(xhr, type){
		  	  alert('服务器请求超时');
		  }
		});
	}

}
/**
 * 校验表单
 */
function checkForm(){
	var userName = $('#userName').val();
	if(userName == ""){
		$("#msg").html("请输入用户名");
		return false;
	}
	return true;
}
</script>
