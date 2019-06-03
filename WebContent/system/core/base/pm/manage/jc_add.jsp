<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String humanDocSid=request.getParameter("humanDocSid");
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/js/hr.js"></script>
<script>
function doInit(){
	getHrCodeByParentCodeNo("PM_SANCTION_TYPE","sanType");
}
function commit(){
	if($("#form1").form("validate")){
		var param = tools.formToJson($("#form1"));
		var url = contextPath+"/TeeHumanSanctionController/addHumanSanction.action";
		var json = tools.requestJsonRs(url,param);
		if(json.rtState){
			top.$.jBox.tip(json.rtMsg,"info");
			return true;
		}
		top.$.jBox.tip(json.rtMsg,"error");
		return false;
	}
}
</script>

</head>
<body onload="doInit();">
<form id="form1" name="form1">
	<table  style="width:100%;font-size:12px" class="TableBlock">
		<tr class='TableData'>
			<td>
				<b>奖罚项目：</b>
			</td>
			<td>
				<select class="BigSelect" id="sanType" name="sanType">
				</select>
			</td>
			<td>
				<b>奖罚日期：</b>
			</td>
			<td>
				<input type="text" id='sanDateDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='sanDateDesc' class="Wdate BigInput" />
			</td>
		</tr>
		<tr class='TableData'>
			<td>
				<b>生效工资月份：</b>
			</td>
			<td>
				<input type="text" id='validDateDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='validDateDesc' class="Wdate BigInput" />
			</td>
			<td>
				<b>奖罚金额：</b>
			</td>
			<td>
				<input type="text" class="BigInput easyui-validatebox" validType='intege[]' id="pays" name="pays"/>
			</td>
		</tr>
		<tr class='TableData'>
			<td>
				<b>奖罚说明：</b>
			</td>
			<td colspan="3">
				<textarea style="width:410px;height:100px" class="BigTextarea" id="content" name="content"></textarea>
			</td>
		</tr>
		<tr class='TableData'>
			<td>
				<b>备注：</b>
			</td>
			<td colspan="3">
				<textarea style="width:410px;height:100px" class="BigTextarea" id="remark" name="remark" ></textarea>
			</td>
		</tr>
	</table>
	<input type="hidden" class="BigInput" id="humanDocSid" name="humanDocSid" value="<%=humanDocSid%>"/>
	</form>
</body>
</html>