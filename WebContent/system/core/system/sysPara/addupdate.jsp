<%@page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp" %>
<title>系统参数</title>
<link href="<%=cssPath %>/style.css" rel="stylesheet" type="text/css" />
<link  rel="stylesheet" type="text/css" href="<%=cssPath %>/stylebootstrap.css" />
<script type="text/javascript" src="<%=contextPath %>/common/jquery/jquery-min.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/tools.js"></script>
<script type="text/javascript">


function doInit(){

	
}

/**
 * 保存
 */
function doSave(){
	if (check()){
		var url = "<%=contextPath %>/sysPara/addOrUpdateSysPara.action";
		var para =  tools.formToJson($("#form1")) ;
		var jsonRs = tools.requestJsonRs(url,para);
		//alert(jsonRs);
		if(jsonRs.rtState){
			alert(jsonRs.rtMsg);
			
		}else{
			alert(jsonRs.rtMsg);
			
		}
	}
	
}


function checkStr(str){ 
    var re=/[\\"']/; 
    return str.match(re); 
  }
 
function check() {
	return true;
}

</script>

</head>
<body onload="doInit()">
   <table border="0" width="100%" cellspacing="0" cellpadding="3" class="small" align="center">
    <tr>
      <td class="Big"><img src="<%=imgPath%>/infofind.gif" align="absmiddle"><span class="big3">
      </td>
    </tr>
  </table><br>
  <form   method="post" name="form1" id="form1">
<table class="TableBlock" width="80%" align="center">
			<tr class="TableLine1">
				<td nowrap>参数值：</td>
				<td nowrap><INPUT type=text name="paraValue" id="paraValue" size="10" maxlength="3" ></td>
		</tr>
			<tr class="TableLine2">
				<td nowrap>参数名称：：</td>
				<td nowrap><input type="text" name="paraName" id="paraName"
					>&nbsp;</td>
			</tr>


			<tr class="TableControl">
				<td colspan="2" align="center"><input type="button" value="保存"
					class="BigButtonA" onclick="doSave();">&nbsp;&nbsp;
								
					</td>
			</tr>
		</table>
   </form>
</body>
</html>