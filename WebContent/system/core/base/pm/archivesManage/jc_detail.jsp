<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String sid = request.getParameter("sid");
    String personName = request.getParameter("personName");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" ></meta>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script>
var sid='<%=sid%>';
var personName='<%=personName%>';
function doInit(){
	var url = contextPath+"/TeeHumanSanctionController/getModelById.action?sid="+sid;
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		bindJsonObj2Cntrl(json.rtData);
	}else{
		$.MsgBox.Alert_auto(json.rtMsg);
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
<body onload="doInit();" style="padding-left: 10px;padding-right: 10px;background-color: #f2f2f2;" >
<div class="topbar clearfix" id="toolbar">
   <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/core/base/pm/img/icon_detail.png">
		<span class="title">  <%= personName %> 的奖惩记录</span>
	</div>
</div>
	<table class="TableBlock" width="100%" align="center">
	 <tr>
		   <td class=TableHeader colSpan=2 noWrap style="padding-top: 10px;">
		       <img src="<%=contextPath %>/common/zt_webframe/imgs/rsdagl/dagl/icon_fenzu.png" alt="" style="vertical-align:text-top;"/>
		       &nbsp;<span style="font-size: 16px;line-height: 16px;color: #0050aa;font-family: 'MicroSoft YaHei';">基本信息</span>
		       <span style="padding-bottom: 5px;" class="basic_border_grey fl"></span>
		   </td>
	    </tr>
		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				奖罚项目：
			</td>
			<td>
				<div  id="sanTypeDesc" >
				</div>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				奖罚日期：
			</td>
			<td>
				<div  id='sanDateDesc' ></div>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				生效工资月份：
			</td>
			<td>
				<div id='validDateDesc'  ></div>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				奖罚金额：
			</td>
			<td>
				<div  id="pays" ></div>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				奖罚说明：
			</td>
			<td>
				<div id="content" style="width:425px;height:100px" ></div>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				备注：
			</td>
			<td>
				<div  id="remark" style="width:425px;height:100px" ></div>
			</td>
		</tr>
	</table>
</body>
</html>