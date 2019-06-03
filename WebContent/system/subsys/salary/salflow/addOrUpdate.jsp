 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	Calendar cal = Calendar.getInstance();
	int year = cal.get(Calendar.YEAR);
	int month = cal.get(Calendar.MONTH) + 1;
	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crmCode.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/salary/js/account.js"></script>
<title>工资流程新建/更新</title>
<script type="text/javascript" charset="UTF-8">
var sid = <%=sid%>
$(function() {
	getAllAccount('accountId');	
	getById(sid)
});

/**
 * 编辑
 */
function getById(sid){
	if(sid){
		var url = "<%=contextPath%>/teeSalFlowController/getById.action";
	    var para =  {sid:sid};
	    var jsonRs = tools.requestJsonRs(url,para);
	    if(jsonRs.rtState){
	        var json = jsonRs.rtData;
	        bindJsonObj2Cntrl(json);
	    }
	}
}


/**
 * 新建或者更新
 */
function doSaveOrUpdate(callback){
	if(checkForm()){
		var url = contextPath + "/teeSalFlowController/addOrUpdate.action";
		var para =  tools.formToJson($("#form1")) ;
		var jsonObj = tools.requestJsonRs(url, para);
		if (jsonObj.rtState) {
			callback(jsonObj);
			parent.BSWINDOW.modal("hide");
		} else {
			alert(jsonObj.rtMsg);
		}
	}
	return false;
}
function checkForm(){
	if($("#accountId").val() == ''){
		alert("工资账套是必填项！");
		return false;
	}
	return true;
}
</script>
</head>
<body >		

<form id="form1" name="form1">
	<table class="TableBlock" width="90%" align="center" style="padding:5px;" >
  		<tr>
  			<td nowrap class="TableData" >工资账套:<font color="red">*</font></td>
			<td nowrap class="TableData" >
				<select name='accountId' id="accountId" class='BigSelect'>
		
				</select>
			</td>	
			</tr>
			<tr>
				<td nowrap class="TableData" >工资月份:<font color="red">*</font></td>
				<td nowrap class="TableData" >
					<select name="salYear" class="BigSelect">
	      	  	<%
	      	  	for(int i = 2000 ; i <2100 ; i++){
	      	  		if(year == i){	
	      	  		
	      	  	%>
	      	  	<option value="<%=i %>" selected="selected"><%=i %>年</option>
	      	  	<%}else{ %>
	      	  	<option value="<%=i %>"><%=i %>年</option>
	      	  	<%	}}
	      	  	%>
	      	  </select>
	      	  
	      	  <select name="salMonth"  class="BigSelect">
	      	  	<%
	      	  	for(int i = 1 ; i <13 ; i++){
	      	  		if(month == i){	
	      	  	%>
	      	  		<option value="<%=i %>" selected="selected"><%=i %>月</option>
	      	  	<%
	      	  		}else{
	      	  	%>       
	      	  		<option value="<%=i %>"><%=i %>月</option>
	      	  	<%	}}
	      	  	%>
	      	  </select>
			</td>
			</tr>
			<tr>
				<td nowrap class="TableData" >备注</td>
				<td nowrap class="TableData" >	
					<textarea rows="5" cols="60" name="content" ></textarea>
				</td>
			</tr>	
  	</table>
   		<input type="hidden" name="sid"></input>
</form>
</body>
</html>