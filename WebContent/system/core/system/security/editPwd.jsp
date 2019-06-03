<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String code = request.getParameter("code") == null ? "" :  request.getParameter("code") ;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<title>修改密码</title>
<link href="<%=cssPath %>/style.css" rel="stylesheet" type="text/css" />
<link  rel="stylesheet" type="text/css" href="<%=cssPath %>/stylebootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui/themes/gray/easyui.css"/>
<script type="text/javascript" src="<%=contextPath %>/common/jquery/jquery-min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/src/teeValidagteBox.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/tools.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/sys.js"></script>
<script type="text/javascript" src="<%=contextPath %>/system/core/person/js/person.js"></script>



<script type="text/javascript">
var code = '<%=code %>';
function doInit(){
	//判断密码校验
	getCheckPassPara('newPassword','confirmNewPassword');
	var personObj = getPersonInfo(loginPersonId);
	if(personObj && personObj.uuid){
		 bindJsonObj2Cntrl(personObj);
	}else{
		alert("没有相关人员！");
	}
	$.extend($.fn.validatebox.defaults.rules, {   
		noEqualToPassword: {   
		 	validator: function(value, param){   
		 		return value != $(param[0]).val();
	 	    },  
	  	   message: '不能修改与上次一致密码！'
	    }
	});
	$('#oldPassword').validatebox({ 
		required:false ,
		validType: "noEqualToPassword[$('#newPassword')]"
		
	}); 
	
	
	//最后一次登录时间
	if(lastPassTime){
		//alert(lastPassTime)
		//var lastPassTimeStr = getFormatDateStr(lastPassTime , 'yyyy-MM-dd HH:mm:ss');
		$("#lastPassTimeStr").html(lastPassTime);
	}
	
	if(code == '7'){//过期期限
		$("#SEC_PASS_TIME_TR").show();
		$("#SEC_PASS_TIME").html('<%=SEC_PASS_TIME%>' + " 天");
		
	}else if(code == '6'){
		$("#lastPassTimeStr").html("初次登录请修改密码！");
	}
}


/**
 * 保存
 */

 function updatePassword(){
 	if (check()){
 		var url = "<%=contextPath %>/personManager/updatePassword.action";
 		var para =  tools.formToJson($("#form1")) ;
 		var jsonRs = tools.requestJsonRs(url,para);
 		//alert(jsonRs);
 		if(jsonRs.rtState){
 			alert("修改密码成功！");
 			window.location.href =  "/<%=contextPath%>";
 		}else{
 			alert(jsonRs.rtMsg);

 		}
 	}	
 }


function check() {

	var check = $("#form1").form('validate'); 
	if(check == true){
		if($("#oldPassword").val() == $("#newPassword").val()){
			alert("旧密码和新密码不能一致！");
			return false;
		}
		
	}
	return check;
  }
  
  function returnFunc(){
	  window.location.href = "/<%=contextPath%>";
  }
</script>

</head>
<body onload="doInit()">


<table border="0" width="100%" cellspacing="0" cellpadding="3"
		class="small" align="center">
		<tr>
			<td class="Big">
		<%-- 	<img src="<%=imgPath%>/notify_new.gif" align="center" /> --%>
				&nbsp;&nbsp;<span class="Big3"> 修改密码</span>
			</td>
		</tr>
	</table>
	<br>
	<form  method="post" name="form1" id="form1" >
<table class="TableBlock" width="95%" align="center">

   <tr>
    <td nowrap class="TableHeader" colspan="2" style='vertical-align: middle;'><img src="<%=imgPath %>/green_arrow.gif"  class="imgMiddle"> <span  class="imgMiddleSpan">修改密码</span></td>
   </tr>
   <tr>
    <td nowrap class="TableData" width="120">用户名：<span style=""></span></td>
    <td nowrap class="TableData" id="userId">
      </td>
   </tr>
   <tr>
    <td nowrap class="TableData">真实姓名：</td>
    <td nowrap class="TableData" id='userName'>
 
         </td>
   </tr>
   
  <tr>
    <td nowrap class="TableData">原密码：</td>
    <td nowrap class="TableData">
        <input type="text" name="oldPassword" id="oldPassword"  maxlength="30"  class="easyui-validatebox BigInput"  >
    </td>
   </tr>

   <tr>
    <td nowrap class="TableData" width="60" >新密码：</td>
    <td nowrap class="TableData">
        <input type="password" name="newPassword" id="newPassword" size="20" class="easyui-validatebox BigInput"  maxlength='20'  > <span id="passMessage"></span>
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData" width="120">确认新密码：</td>
    <td nowrap class="TableData">
        <input type="password" name="confirmNewPassword" id="confirmNewPassword"  size="20"  class="easyui-validatebox BigInput"  maxlength='20'> <span id="passMessage2"></span>
    </td>
   </tr>
   <tr>
   
   
	<tr>
 		 <td  class="TableData" >上次修改时间：</td>
  		<td class="TableData" id="lastPassTimeStr">
 		</td>
	</tr>
	<tr style='display:none;' id='SEC_PASS_TIME_TR'>
  		<td class="TableData" >密码过期：</td>
 		<td class="TableData" id="SEC_PASS_TIME"></td>
	</tr>
   
   <tr>
    <td nowrap  class="TableControl" colspan="2" align="center">
        <input type="hidden" id="uuid" name="uuid"  value="">
        <input type="button" value="保存" class="btn btn-primary" title="保存" onclick="updatePassword()" >&nbsp;&nbsp;
     	&nbsp;&nbsp;<input type="button" value="返回" class="btn btn-primary" title="返回" onclick="returnFunc();" >&nbsp;&nbsp;
   
    </td>

</table>
  </form>
</body>
</html>