<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	TeePerson loginPerson = (TeePerson) session
			.getAttribute(TeeConst.LOGIN_USER);
    int year=TeeStringUtil.getInteger(request.getParameter("year"), 0);
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
	var date=new Date();
	var nowYear=date.getFullYear();
	var beginYear=nowYear-3;
	var year=<%=year%>;
	//初始化
	function doInit() {
		//动态加option
		var selector=$('#year');  
		for(var i=0;i<9;i++){
			 if(year==0||year=="undefined"){	
				 if(i==3){
					 selector.append('<option class="op" selected = "selected" value="'+(beginYear+i)+'">'+(beginYear+i)+'财年</option>');   
				 }else{
					 selector.append('<option class="op"  value="'+(beginYear+i)+'">'+(beginYear+i)+'财年</option>');   
				 }	 	 
			 }else{
				 if((beginYear+i)==year){
					 selector.append('<option class="op" selected = "selected" value="'+(beginYear+i)+'">'+(beginYear+i)+'财年</option>');   
				 }else{
					 selector.append('<option class="op"  value="'+(beginYear+i)+'">'+(beginYear+i)+'财年</option>');   
				 }		 
			 }
			  
		 }
		
		//获取年份
		var year1 = $("#year").val();
		getInfoList(year1);

		getSum();
	}

	//获取各部门某一年的目标列表
	function getInfoList(year) {
		datagrid = $('#datagrid')
				.datagrid(
						{
							url : contextPath
									+ '/crmDeptTargetController/getDeptTargetList.action?year='
									+ year,
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
										formatter : function(value, rowData,
												rowIndex) {
											//var str = "&nbsp;&nbsp;<a href='/system/subsys/crm/core/target/personTargetList.jsp?deptId='"+rowData.deptId+"'&&year='"+year+"'&&deptName='"+rowData.deptName+"'>"+rowData.deptName+"</a>";
											/* var str = "&nbsp;&nbsp;<a href='#' onclick='goPersonTarget("
													+ year + ","+rowData.deptId+","+rowData.deptName+")'>"+rowData.deptName+"</a>"; */
											var str = "&nbsp;&nbsp;<a href='#' onclick=\"goPersonTarget("
														+ year +",'"+rowData.deptName+"',"+rowData.deptId+")\">"+rowData.deptName+"</a>";
											return str;
										}
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
										field : 'createTimeStr',
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
											str += "&nbsp;&nbsp;<a href='#' onclick='deleteDeptTarget("
													+ rowData.sid + ")'>删除</a>";
											return str;
										}
									} ] ]
						});	

	}

	//给下拉选框添加onchange事件
	function change() {
		//获取年份
		var year = $("#year").val();
// 		//年份改变  列表发生变化
// 		getInfoList(year);
		
// 		getSum();
		window.location = "deptTargetList.jsp?year="+year;
	}

	
	//获取公司目标  和部门目标
    function getSum(){
		
    	//获取年份
		var year = $("#year").val();
    	
    	var url = contextPath+"/crmDeptTargetController/getSumTarget.action";
		var json = tools.requestJsonRs(url,{year:year});
		if(json.rtState){
			$("#message").html(year + "财年公司目标总额："+json.rtData.sumCompany+"元，其中部门目标总额："+json.rtData.sumDept+"元");
		} 
	
	}
	
	
	//删除目标
	function deleteDeptTarget(sid) {
		$.jBox
				.confirm(
						"是否确认删除？",
						"确认",
						function(v) {
							if (v == "ok") {
								var url = contextPath
										+ "/crmDeptTargetController/deleteDeptTarget.action";
								var json = tools.requestJsonRs(url, {
									sid : sid
								});
								if (json.rtState) {
									$.jBox.tip(json.rtMsg, "info");
									datagrid.datagrid('reload');
									datagrid.datagrid('unselectAll');
									getSum();
									//doInit();
									return true;
								}
								$.jBox.tip(json.rtMsg, "error");
							}
						});
	}

	//增加目标
	function addDeptTarget() {
		var year = $("#year").val();
		window.location = contextPath
				+ "/system/subsys/crm/core/target/addOrUpdateDeptTarget.jsp?year="+year;
	}

	//编辑
	function edit(sid) {
		window.location = contextPath
				+ "/system/subsys/crm/core/target/addOrUpdateDeptTarget.jsp?sid="
				+ sid+"&year="+year;
	}
	
	//跳转到部门的人员目标页面
	function goPersonTarget(year,deptName,deptId){
		window.location = contextPath
		+ "/system/subsys/crm/core/target/personTargetList.jsp?year="+year+"&deptName="+encodeURI(deptName)+"&deptId="+deptId;	
		
		
	}
</script>
</head>
<body onload="doInit();">
	<div id="toolbar">
		<div class="base_layout_top" style="position: static;">
			<table width="100%">
				<tr>
					<td><span class="easyui_h1"><i
							class="glyphicon glyphicon-list-alt"></i>&nbsp;部门目标</span></td>

					<td align=right>

						<button class="btn btn-info" onclick="datagrid.datagrid('reload')">
							<i class="glyphicon glyphicon-refresh"></i>&nbsp;刷新
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
					<td align=left><select name="year" id="year"
						class="BigInput easyui-validatebox" onchange="change();">
							

					</select> &nbsp;&nbsp;&nbsp;&nbsp; <span id="message"></span></td>
				</tr>
			</table>
		</div>
	</div>

	<table id="datagrid" fit="true"></table>



</body>
</html>