<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
		int sid =TeeStringUtil.getInteger(request.getParameter("sid"), 0) ;

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<title>编辑建模类别</title>

<script type="text/javascript">
var sid=<%=sid%>;
var contextPath='<%=contextPath %>';
function doInit(){
	if(sid>0){
		var url = contextPath+"/businessCatController/getBusinessCatById.action";
		var json = tools.requestJsonRs(url,{sid:sid});
		if(json.rtState){
			var data = json.rtData;
		    bindJsonObj2Cntrl(data);	
		}
	}
}

function doSaveOrUpdate(){
	if(checkForm()){
		var url =contextPath+ "/businessCatController/addOrUpdate.action";
		var para =  tools.formToJson($("#form1"));
		var jsonRs = tools.requestJsonRs(url,para);

        return jsonRs;
	}
		
	
}

function checkForm(){
	  return $("#form1").valid(); 
}


</script>

</head>
<body onload="doInit()" style="background-color: #f2f2f2">
<center style="width:100%;">
	<form  method="post" name="form1" id="form1" >
	
		<table class="TableBlock" width="100%" align="center">
		   <tr>
		    <td nowrap class="TableData" style="text-indent: 10px;">分类名称：</td>
		    <td nowrap class="TableData">
		        <input type="text" name="catName" id="catName" class="BigInput"  required="true"  size="10" maxlength="20" style="width: 250px;height: 23px" >&nbsp;
		        <input type="hidden" name="sid" id="sid" value="0" />
		    </td>
		   </tr>
		   <tr>
		    <td nowrap class="TableData" style="text-indent: 10px;">排序号：</td>
		    <td nowrap class="TableData">
		    <input type="text" name="sortNo" id="sortNo" size="10"   no_negative_number="true" maxlength="30" class="BigInput"  required="true" style="width: 250px;height: 23px">
		         &nbsp;&nbsp;</td>
		   </tr>
		   
		</table>
 	 </form>
</center>
</body>
<script>
  $("#form1").validate();
</script>
</html>
 