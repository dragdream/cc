<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script>
function doInit(){
	
}

/**
 * 
 */
function queryStatistics(){
	window.location.href = "<%=contextPath%>/system/subsys/salary/report/detail.jsp";
} 

function hrSalary(id){
	openFullWindow("<%=contextPath%>/system/subsys/salary/report/personStatisticsYearCollect.jsp?reportId=" + id);
} 
</script>

</head>
<body onload="doInit()" style="x">

	<div class= "Big3" style="padding:5px 0px 10px 15px;">
		员工工资统计
	</div>
	<div " style="padding-left:15px;">
		<input type="button" name="" value="员工工资统计" class="btn btn-primary" onclick="hrSalary('4028b8814872b2f7014872f8fae00004');"/>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" name="" value="工资明细统计" class="btn btn-primary" onclick="queryStatistics(19);"/>
		&nbsp;&nbsp;&nbsp;&nbsp;
	</div>
	
	
</body>
</html>