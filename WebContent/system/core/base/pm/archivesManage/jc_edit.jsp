<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String sid = request.getParameter("sid");
	String humanDocSid=request.getParameter("humanDocSid");
	String personName = request.getParameter("personName");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/js/hr.js"></script>
<script>
var sid='<%=sid%>';
var humanDocSid='<%=humanDocSid%>';
var personName='<%=personName%>';
function doInit(){
	getHrCodeByParentCodeNo("PM_SANCTION_TYPE","sanType");
	var url = contextPath+"/TeeHumanSanctionController/getModelById.action?sid="+sid;
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		bindJsonObj2Cntrl(json.rtData);
	}else{
		$.MsgBox.Alert_auto(json.rtMsg);
	}
}

function commit(callback){
	if($("#form1").valid()){
		var param = tools.formToJson($("#form1"));
		var url = contextPath+"/TeeHumanSanctionController/updateHumanSanction.action";
		var json = tools.requestJsonRs(url,param);
		if(json.rtState){
			callback(json.rtState);
		}else{
			$.MsgBox.Alert_auto(json.rtMsg);
		}
		
	}
}
</script>
<style>
html{
   background-color: #f2f2f2;
}
	td{
		line-height:28px;
		min-height:28px;
	}
</style>
</head>
<body onload="doInit()" style="padding-left: 10px;padding-right: 10px;background-color: #f2f2f2;">
<div class="topbar clearfix" id="toolbar">
   <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/core/base/pm/img/icon_dabj.png">
		<span class="title">新增/编辑  <%= personName %> 的奖惩记录</span>
	</div>
</div>
<form id="form1" name="form1">
	<table class="TableBlock" width="100%" align="center">
	    <tr>
		   <td class=TableHeader colSpan=2 noWrap>
		       <img src="<%=contextPath %>/common/zt_webframe/imgs/rsdagl/dagl/icon_fenzu.png" alt="" style="vertical-align:middle;"/>
		       <B style="color: #0050aa">基本信息</B>
		   </td>
	    </tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				奖罚项目：
			</td>
			<td>
				<select style="height: 23px;width: 250px;" class="BigSelect" id="sanType" name="sanType">
				</select>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				奖罚日期：
			</td>
			<td>
				<input style="height: 23px;width: 200px;" type="text" id='sanDateDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='sanDateDesc' class="Wdate BigInput" />
			</td>
		 </tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				生效工资月份：
			</td>
			<td>
				<input style="height: 23px;width: 200px;" type="text" id='validDateDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='validDateDesc' class="Wdate BigInput" />
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				奖罚金额：
			</td>
			<td>
				<input style="height: 23px;width: 200px;" type="text" nonnegative="true" class="BigInput" validType='intege[]' id="pays" name="pays"/>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				奖罚说明：
			</td>
			<td>
				<textarea style="width:410px;height:100px" class="BigTextarea" id="content" name="content"></textarea>
			</td>
		 </tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				备注：
			</td>
			<td>
				<textarea style="width:410px;height:100px" class="BigTextarea" id="remark" name="remark" ></textarea>
			</td>
		</tr>
	</table>
	<input type="hidden" class="BigInput" id="humanDocSid" name="humanDocSid" />
	<input type="hidden" class="BigInput" id="sid" name="sid" value="<%=sid%>"/>
	</form>
</body>
</html>