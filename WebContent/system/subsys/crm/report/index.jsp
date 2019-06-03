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
 * 弹出客户分区图
 */
function customerScale(id){
	openFullWindow("<%=contextPath%>/system/subsys/report/flow_report_senior_show.jsp?reportId=" + id);
} 

function contractStatisicsYearCollect(id){
	openFullWindow("<%=contextPath%>/system/subsys/crm/report/statisticsYearCollect.jsp?reportId=" + id);
} 
</script>

</head>
<body onload="doInit()" style="">
<div class="base_layout_top" style="position:static">
	<span class="easyui_h1">CRM报表统计</span>
</div>
<div style="padding:10px;">
<fieldset>
	<legend>客户统计</legend>
</fieldset>
<input type="button" name="" value="客户规模分布" class="btn btn-default" onclick="customerScale(17);"/>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" name="" value="客户来源分布" class="btn btn-default" onclick="customerScale(19);"/>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" name="" value="客户区域分布" class="btn btn-default" onclick="customerScale(15);"/>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" name="" value="客户行业分布" class="btn btn-default" onclick="customerScale(20);"/>
<br/><br/>
<fieldset>
	<legend>合同统计</legend>
</fieldset>
<input type="button" name="" value="合同状态统计" class="btn btn-default" onclick="customerScale(25);"/>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" name="" value="合同类型统计" class="btn btn-default" onclick="customerScale(24);"/>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" name="" value="合同年度销售统计" class="btn btn-default" onclick="contractStatisicsYearCollect(31);"/>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" name="" value="合同人员年度销售统计" class="btn btn-default" onclick="contractStatisicsYearCollect(34);"/>
<br/><br/>
<fieldset>
	<legend>其他销售</legend>
</fieldset>
<input type="button" name="" value="售后服务统计" class="btn btn-default" onclick="customerScale(27);"/>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" name="" value="跟单统计" class="btn btn-default" onclick="customerScale(28);"/>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" name="" value="产品年度销售汇总" class="btn btn-default" onclick="contractStatisicsYearCollect(33);"/>
</div>
</body>
</html>