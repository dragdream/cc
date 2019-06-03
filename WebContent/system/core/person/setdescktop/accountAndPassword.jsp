<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<title>云平台账号/密码设置</title>
<script type="text/javascript" src="<%=contextPath %>/system/core/person/js/person.js"></script>
<script type="text/javascript">

function doInit(){
	getCloudAccountAndPwd();
}

/**
 * 修改密码
 */
 function saveCloudAccountPwd(){
 	if (check()){
 		var url = "<%=contextPath %>/personManager/updateCloudAccountAndPwd.action";
 		var para =  tools.formToJson($("#form1")) ;
 		var jsonRs = tools.requestJsonRs(url,para);
 		//alert(jsonRs);
 		if(jsonRs.rtState){
 			$.jBox.tip("修改账号/密码成功！" ,'info',{timeout:1500});
 			/* $.messager.show({
 				msg : '修改密码成功！！',
 				title : '提示'
 			}); */
 		}else{
 			alert(jsonRs.rtMsg);

 		}
 	}	
 }


/**
 * 获取更新密码列表
 */
function getCloudAccountAndPwd(){
	var url = "<%=contextPath %>/personManager/getCloudAccountAndPwd.action";
	var para =  {} ;
	var jsonRs = tools.requestJsonRs(url,para);
	//alert(jsonRs);
	if(jsonRs.rtState){
		var data = jsonRs.rtData;
		$("#cloudUserId").val(data.accountId);
		$("#cloudPwd").val(data.accountPwd);
	}else{
		alert(jsonRs.rtMsg);
	}
}

function check() {
	return $("#form1").form('validate'); 
  }
</script>

</head>
<body onload="doInit()">


<table border="0" width="100%" cellspacing="0" cellpadding="3"
		class="small" align="center" style="margin-top:10px;">
		<tr>
		<td class="Big3">
				 &nbsp;&nbsp;云平台账号/密码设置
			</td>
		</tr>
	</table>
	<br>
	<form  method="post" name="form1" id="form1" >
<table class="TableBlock" width="95%" align="center">

   
   <tr>
    <td nowrap class="TableData" width="120">账号：<span style=""></span></td>
    <td nowrap class="TableData" >
      	   <input type="text" name="cloudUserId" id="cloudUserId"  maxlength="80"  class="easyui-validatebox BigInput"  size="30" >
   
      </td>
   </tr>
  <tr>
    <td nowrap class="TableData">密码：</td>
    <td nowrap class="TableData">
        <input type="password" name="cloudPwd" id="cloudPwd"  maxlength="80"  class="easyui-validatebox BigInput" size="30"  >
    </td>
   </tr>

   <tr>
    <td nowrap  class="TableControl" colspan="2" align="center">
        <input type="hidden" id="uuid" name="uuid"  value="">
        <input type="button" value="保存" class="btn btn-primary" title="保存" onclick="saveCloudAccountPwd()" >&nbsp;&nbsp;
    </td>

</table>
  </form>
  <br>
</body>
</html>