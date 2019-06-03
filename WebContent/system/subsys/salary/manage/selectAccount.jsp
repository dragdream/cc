<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/salary/js/account.js"></script>
<title>员工账套设定</title>
<script type="text/javascript" charset="UTF-8">
function doInit(){
	getAllAccount('accountId');
}
	
</script>
</head>
<body  onload="doInit()">		 
  	  <form id="form1" name="form1">
		<table class="TableBlock" width="60%" align="center" style="padding:5px;" >
   			<tr>
   				 <td nowrap class="TableData" colspan="2">
			  		工资账套:
					<select name='accountId' id="accountId" class='BigSelect'>
						<option value="0"></option>
					</select>
				<input type="button" value="查询" class="btn btn-primary" onclick="query()">
  			</tr>	
   		</table>
   		
	</form>

</body>
</html>
