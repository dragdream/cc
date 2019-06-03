<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<%
TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
int userSid = person.getUuid();
String deposId = request.getParameter("deposId");
%>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>库存报表</title>
	<script type="text/javascript" charset="UTF-8">
	$(function() {
		//获取所有仓库
		var json = tools.requestJsonRs(contextPath+"/repDepository/depositoryList.action");
		for(var i=0;i<json.rows.length;i++){
			<%
				if(!TeePersonService.checkIsAdminPriv(person)){//如果不是管理员，则判断分库权限
					%>
					if(json.rows[i].managerId==<%=userSid%>){
				<%
				}else{
					%>
					if(true){
					<%
				}
			%>
				$("#deposId").append("<option value='"+json.rows[i].sid+"'>"+json.rows[i].name+"</option>");
			}
		}
		
		<%
			if(deposId!=null){
				%>
				$("#deposId").val("<%=deposId%>");
				<%
			}
		%>
		
		var para = {deposId:$("#deposId").val()};
		
		$('#datagrid').datagrid({
			url:contextPath+'/repDepository/list.action',
			singleSelect:true,
			toolbar:'#toolbar',//工具条对象
			border:false,
			queryParams:para,
			idField:'sid',//主键列
			fitColumns:false,
			striped: true,
			remoteSort: true,
			columns:[[
				{field:'proName',title:'商品名称',width:100},
				{field:'proCode',title:'商品编号',width:100},
				{field:'proModel',title:'规格',width:100},
				{field:'unit',title:'单位',width:100},
				{field:'minStock',title:'存货下限',width:100},
// 				{field:'maxStock',title:'存货上限',width:100},
				{field:'total',title:'实存总数',width:100,formatter:function(data,row,index){
					return row.inCount-row.outCount;
				}},
				{field:'delta',title:'应补数量',width:100,formatter:function(data,row,index){
					var total = row.inCount-row.outCount;
					if(total<row.minStock){
						return (row.minStock-total)+"";
					}
					
// 					if(total>row.maxStock){
// 						return (total-row.maxStock)+"(调拨)";
// 					}
					
					return "";
				}},
				{field:'amount',title:'实存金额',width:100},
				{field:'_oper',title:'操作',width:50,formatter:function(data,row,index){
					var render = [];
					render.push("<a href='#' onclick='detail("+row.proId+")'>详细</a>");
					return render.join("&nbsp;&nbsp;");
				}}
			]]
		});
	});
	
	function changeRepos(){
		refresh();
	}
	
	function refresh(){
		var para = {deposId:$("#deposId").val(),time1:$("#time1").val(),time2:$("#time2").val()};
		$('#datagrid').datagrid("reload",para);
	}
	
	function detail(proId){
		window.location = "detaillist.jsp?proId="+proId+"&reposId="+$("#deposId").val()+"&time1="+$("#time1").val()+"&time2="+$("#time2").val();
	}
	
	</script>
</head>
<body style="overflow:hidden">
<div id="toolbar">
	<div class="base_layout_top" style="position:static">
		<span class="easyui_h1">库存报表</span>
	</div>
	<div style="padding:10px">
		所属仓库：
	<select id="deposId" name="deposId" class="BigSelect" onchange="changeRepos()">
<!-- 		<option value="0" selected>所有仓库</option> -->
	</select>
	</div>
</div>
<table id="datagrid" fit="true"></table>
</body>
</html>