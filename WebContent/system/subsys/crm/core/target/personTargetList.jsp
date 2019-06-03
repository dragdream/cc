<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	TeePerson loginPerson = (TeePerson) session
			.getAttribute(TeeConst.LOGIN_USER);
    int deptId = TeeStringUtil.getInteger(request.getParameter("deptId"), 0);
    int year = TeeStringUtil.getInteger(request.getParameter("year"), 0);
    String deptName=request.getParameter("deptName");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header.jsp"%>
<%@include file="/header/easyui.jsp"%>
<script type="text/javascript"
	src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/system/subsys/crm/js/crmCode.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/system/subsys/crm/js/crm.js"></script>
<style>
</style>
<script>
	var datagrid;
	var userId =<%=loginPerson.getUuid()%>;
	var deptId=<%=deptId%>;
	var year=<%=year%>;
	var deptName="<%=deptName%>";
	//初始化
	function doInit() {
		//alert(deptName);
		$("#s1").append(year+"财年"+deptName+"个体目标设置");
		getInfoList(year,deptId);
		getSum();
	}

	//获取某部门某一年的个体目标列表
	function getInfoList(year,deptId) {
		datagrid = $('#datagrid')
				.datagrid(
						{
							url : contextPath
									+ '/crmPersonTargetController/getPersonTargetListByDept.action?year='
									+ year+'&deptId='+deptId,
							pagination : true,
							singleSelect : false,
							toolbar : '#toolbar',//工具条对象
							checkbox : true,
							border : false,
							idField : 'sid',//主键列
							fitColumns : true,//列是否进行自动宽度适应
							pageSize : 20,
							pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90,
									100 ],
							columns : [ [
									{
										field : 'year',
										title : '财年',
										width : 80
									},
									{
										field : 'deptName',
										title : '部门名称',
										width : 200,
									},
									{
										field : 'targetUserName',
										title : '人员',
										width : 60
									},
									{
										field : 'm1',
										title : '1月',
										width : 60
									},
									{
										field : 'm2',
										title : '2月',
										width : 60
									},
									{
										field : 'm3',
										title : '3月',
										width : 60
									},
									{
										field : 'm4',
										title : '4月',
										width : 60
									},
									{
										field : 'm5',
										title : '5月',
										width : 60
									},
									{
										field : 'm6',
										title : '6月',
										width : 60
									},
									{
										field : 'm7',
										title : '7月',
										width : 60
									},
									{
										field : 'm8',
										title : '8月',
										width : 60
									},
									{
										field : 'm9',
										title : '9月',
										width : 60
									},
									{
										field : 'm10',
										title : '10月',
										width : 60
									},
									{
										field : 'm11',
										title : '11月',
										width : 60
									},
									{
										field : 'm12',
										title : '12月',
										width : 60
									},
									{
										field : 'total',
										title : '总额',
										width : 80
									},
									{
										field : 'crTimeStr',
										title : '创建时间',
										width : 80
									},
									{
										field : 'crUserName',
										title : '创建人',
										width : 80
									},
									{
										field : '2',
										title : '操作',
										width : 80,
										formatter : function(value, rowData,
												rowIndex) {
											var str = "&nbsp;&nbsp;<a href='#' onclick='edit("
													+ rowData.sid + ")'>编辑</a>";
											str += "&nbsp;&nbsp;<a href='#' onclick='deletePersonTarget("
													+ rowData.sid + ")'>删除</a>";
											return str;
										}
									} ] ]
						});	

	}


	
	//获取公司目标  和部门目标
    function getSum(){	
    	
    	var url = contextPath+"/crmPersonTargetController/getSumTarget.action";
		var json = tools.requestJsonRs(url,{year:year,deptId:deptId});
		if(json.rtState){
			$("#message").html(year + "财年部门目标总额："+json.rtData.sumDept+"元，部门下所有个体目标总额："+json.rtData.sumPerson+"元");
		} 
	
	}
	
	
	//删除目标
	function deletePersonTarget(sid) {
		top.$.jBox
				.confirm(
						"是否确认删除？",
						"确认",
						function(v) {
							if (v == "ok") {
								var url = contextPath
										+ "/crmPersonTargetController/deletePersonTarget.action";
								var json = tools.requestJsonRs(url, {
									sid : sid
								});
								if (json.rtState) {
									top.$.jBox.tip(json.rtMsg, "info");
									//datagrid.datagrid('reload');
									datagrid.datagrid('unselectAll');
									doInit();
									return true;
								}
								top.$.jBox.tip(json.rtMsg, "error");
							}
						});
	}

	//增加目标
	function addDeptTarget() {
		window.location = contextPath
				+ "/system/subsys/crm/core/target/addOrUpdatePersonTarget.jsp?year="+year+"&deptId="+deptId+"&deptName="+deptName;
	}

	//编辑
	function edit(sid) {
		window.location = contextPath
				+ "/system/subsys/crm/core/target/addOrUpdatePersonTarget.jsp?sid="
				+ sid+"&year="+year+"&deptId="+deptId+"&deptName="+deptName;
	}
	//返回
	function  goback(){
		window.location = contextPath
		+ "/system/subsys/crm/core/target/deptTargetList.jsp?year="
		+year;
	
	}
</script>
</head>
<body onload="doInit();">
	<div id="toolbar">
		<div class="base_layout_top" style="position: static;">
			<table width="100%">
				<tr>
					<td><span id="s1" class="easyui_h1"><i
							class="glyphicon glyphicon-list-alt"></i>&nbsp;</span></td>

					<td align=right>

						<button class="btn btn-info" onclick="goback();">
							&nbsp;返回
						</button> &nbsp;
						<button class="btn btn-info" onclick="addDeptTarget()">新增目标</button>
						&nbsp;&nbsp;
					</td>
				</tr>
			</table>
		</div>

		<div class="base_layout_top" style="position: static;">
			<table width="100%">
				<tr>
					<td align=left>
					 &nbsp;&nbsp;&nbsp;&nbsp; <span id="message"></span></td>
				</tr>
			</table>
		</div>
	</div>

	<table id="datagrid" fit="true"></table>



</body>
</html>