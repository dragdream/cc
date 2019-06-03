<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
Date date = new Date();
SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
String dateStr = dateFormat.format(date);
int year = Integer.parseInt(dateStr.substring(0,4));
String yearStr = request.getParameter("year");
if(!TeeUtility.isNullorEmpty(yearStr)){
	year = Integer.parseInt(yearStr);
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<title>部门预算执行情况汇总</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>

<script>
var year = "<%=year%>";
function doInit(){
	getDeptBudget();
}
function My_Submit(){
	  var year = document.getElementById("year").value;
	  window.location="<%=contextPath%>/system/subsys/budget/statics/dept.jsp?year="+year;
}

function set_year(index){
	  var year = document.getElementById("year").value;
	  if(parseInt(year)<=2000){
	    year = parseInt(year);
	  }else if(parseInt(year)>=2049){
	    year = parseInt(year);
	  }else{
	    year = parseInt(year)+parseInt(index);
	  }
	  window.location="<%=contextPath%>/system/subsys/budget/statics/dept.jsp?year="+year;
}
function getDeptBudget(){
	var url = "<%=contextPath%>/teeBudgetReportController/getDeptBudget.action?year="+year;
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		var data = json.rtData;
		var budgetAmount=0;
		var costAmount=0;
		var html="<table class='TableBlock' style='width:80%;margin:0 auto;'><tr class='TableHeader'><td>部门</td><td>年份</td><td>预算</td><td>支出</td><td>剩余</td></tr>";
		for(var i=0;i<data.length;i++){
			budgetAmount = budgetAmount+data[i].AMOUNT;
			costAmount = costAmount+data[i].COSTAMOUNT;
			html+="<tr class='TableData'><td>"+data[i].DEPTNAME+"</td><td>"+data[i].YEAR+"</td><td  align='right'>"+parseFloat(data[i].AMOUNT).toFixed(2)+"</td><td  align='right'>"+parseFloat(data[i].COSTAMOUNT).toFixed(2)+"</td><td  align='right'>"+parseFloat(data[i].AMOUNT-data[i].COSTAMOUNT).toFixed(2)+"</td></tr>";
		}
		html+="<tr class='TableData'><td colspan='2'>合计</td><td align='right'>"+parseFloat(budgetAmount).toFixed(2)+"</td><td align='right'>"+parseFloat(costAmount).toFixed(2)+"</td><td align='right'>"+parseFloat(budgetAmount-costAmount).toFixed(2)+"</td></tr>";
		html+="</table>";
		$("#containor").html(html);
		if(data.length<1){
			messageMsg("没有相关数据！","containor","info");
			$("#control").hide();
		}
	}
}



function exportDeptBudget(){
	var url = "<%=contextPath%>/teeBudgetReportController/exportDeptBudget.action?year="+year;;
	document.form1.action=url;
	document.form1.submit();
	return true;
}

</script>
</head>
<body onload="doInit();" style="margin:0 auto;padding:0 auto;text-align:center;font-size:12px;margin-top:10px;">
<div class="moduleHeader" style='text-align:left;'>
			<div style='text-align:center;font-size:18px;'><strong>公司各部门<%=year %>年预算执行情况统计</strong></div>
</div>
<form id="form1" name="form1" method="post">
	<div style='text-align:left;width:80%;margin:0 auto;'>
			<a href="javascript:set_year(-1)";  title="上一年"><img  src="<%=contextPath%>/common/images/attend/previous.gif"></img></a>
			   <select id="year" name="year" style="height:22px;FONT-SIZE: 11pt;" onchange="My_Submit();">
			     <%
			       for(int i = 2000; i < 2050; i++){
			         if(i == year){
			     %>
			     <option value="<%=i %>" selected="selected"><%=i %>年</option>
			       <%}else{ %>
			     <option value="<%=i %>"><%=i %>年</option>
			       <%
			           }
			        }
			       %>
			   </select>
			  <a href="javascript:set_year(1);" class="ArrowButtonR" title="下一年"><img src="<%=contextPath%>/common/images/attend/next.gif"></img></a>
	</div>
	<br/>
	<div id="containor">
	
	</div>
	<div id="control" style='margin:0 auto;text-align:center; height:28px;line-height:28px;width:"90%";margin-top:20px;'>
		<input type='button' class="btn btn-primary" value='导出' onclick='exportDeptBudget();'/>
	</div>
	<br/><br/>
</form>
</body>
</html>