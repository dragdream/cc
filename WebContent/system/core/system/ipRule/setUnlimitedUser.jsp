<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp" %>
	<title>不受限制IP规则人员</title>

	
<script type="text/javascript" >
function doInit(){
	var url = "<%=contextPath %>/sysPara/getParaIsUser.action";
	var para =  {paraName:'IP_UNLIMITED_USER'} ;
	var jsonRs = tools.requestJsonRs(url ,para);
	if(jsonRs.rtState){
		var data = jsonRs.rtData;
		$("#paraValue").val(data.paraValue);
		$("#IP_UNLIMITED_USER_DESC").val(data.paraValueDesc);
	}else{
		alert(jsonRs.rtMsg);
	}
}

/**
 * 保存
 */
function doSave(){
	var url = "<%=contextPath %>/sysPara/addOrUpdateSysPara.action";
	var para =  tools.formToJson($("#form1")) ;
	var jsonRs = tools.requestJsonRs(url,para);
	//alert(jsonRs);
	if(jsonRs.rtState){
		//alert();
		$.jBox.tip(jsonRs.rtMsg,'info' , {timeout:1500});
	}else{
		alert(jsonRs.rtMsg);
		
	}
}



</script>
</head>

<body onload="doInit()" style="padding-top:10px;">
   <table border="0" width="100%" cellspacing="0" cellpadding="3"  align="">
    <tr>
      <td class="">
<%--         <img src="<%=imgPath %>/notify_new.gif" align="absmiddle"> --%>
        <span class="Big3">
        不限制IP用户设置 
         </span>
          ( 指定登录和考勤不限制IP的用户 )
       </td>
    </tr>
  </table>

<br>
	<form action="" method="post" id="form1" name="form1">
		<div  style="" align="center">
			<input type="hidden" id="paraName" name="paraName" value="IP_UNLIMITED_USER">
		
	  	<input type="hidden" id="paraValue" name="paraValue">
	    <textarea rows="10"  class="SmallStatic BigTextarea" cols="70"  name="IP_UNLIMITED_USER_DESC" id="IP_UNLIMITED_USER_DESC" readonly="readonly"></textarea>
	     <a href="javascript:void(0);" onClick="selectUser(['paraValue', 'IP_UNLIMITED_USER_DESC'])">添加</a> 
    	<a href="javascript:void(0);" class="orgClear" onClick="clearData('paraValue', 'IP_UNLIMITED_USER_DESC')">清空</a>
	    </div>
	    <div align="center" style="padding-top:10px;">
	    <input type="button" value="保存" class='btn btn-primary' onclick="doSave();"/>
	    </div>
	  </form>

</body>
</html>