<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@ include file="/header/header.jsp" %>
	<%@ include file="/header/easyui.jsp" %>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>个人目标查询</title>
	
	<script type="text/javascript" charset="UTF-8">
	var contextPath = '<%=contextPath%>';
	var datagrid;
	var userDialog;
	var userForm;
	var passwordInput;
	var userRoleDialog;
	var userRoleForm;
	$(function(){
		query();
	});

	function query(){
		var param = {};
		
		datagrid = $('#datagrid').datagrid({
			url : contextPath+'/crmPersonTargetController/listTeeCrmPersonTargets.action',
			toolbar : '#toolbar',
			title : '',
			queryParams:param,
			singleSelect:true,
			iconCls : 'icon-save',
			pagination : true,
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
			fit : true,
			fitColumns : true,
			nowrap : true,
			border : false,
			idField : 'id',
			sortOrder: 'desc',
			striped: true,
			remoteSort: false,
			columns : [ [ {
				field : 'year',
				title : '财年',
				width : 10
				
			}, {
				field : 'deptName',
				title : '部门',
				width : 20
			} ,{
				field : 'crUserName',
				title : '姓名',
				width : 20
			},
			{
				field : 'm1',
				title : '1月',
				width : 10
			},
			
			{
				field : 'm2',
				title : '2月',
				width : 10
			},
			{
				field : 'm3',
				title : '3月',
				width : 10
			},
			{
				field : 'm4',
				title : '4月',
				width : 10
			},
			{
				field : 'm5',
				title : '5月',
				width : 10
			},
			{
				field : 'm6',
				title : '6月',
				width : 10
			},
			{
				field : 'm7',
				title : '7月',
				width : 10
			},
			{
				field : 'm8',
				title : '8月',
				width : 10
			},
			{
				field : 'm9',
				title : '9月',
				width : 10
			},
			{
				field : 'm10',
				title : '10月',
				width : 10
			},
			{
				field : 'm11',
				title : '11月',
				width : 10
			},
			{
				field : 'm12',
				title : '12月',
				width : 10
			},
			{
				field : 'total',
				title : '总额',
				width : 20
			},
			{
				field : '_manage',
				title : '操作',
				width : 100,
				formatter:function(value,rowData,rowIndex){
					var render = [];
					render.push("<a href='javascript:void(0)' onclick='view("+rowData.sid+")'>查看</a>");
					render.push("&nbsp;");
					/* render.push("<a href='javascript:void(0)' onclick='addOrUpdate("+rowData.sid+")'>编辑</a>");
					render.push("&nbsp;");
					render.push("<a href='javascript:void(0)' onclick='del("+rowData.sid+")'>删除</a>");
					 */
					return render.join("");
				}
			}
			] ]
		});
	}


	function addOrUpdate(sid){
		var url = contextPath+"/system/subsys/crm/core/target/addOrUpdatePersonTarget.jsp?sid="+sid;
		openFullWindow(url);
// 		var title = "";
// 		if(sid==0){
// 			title = "添加公共模版";
// 		}else{
// 			title = "修改公共模版";
// 		}
// 		top.bsWindow(url,title,{submit:function(v,h){
// 			var cw = $(h)[0].contentWindow;
// 			if(cw.commit()){
// 				datagrid.datagrid('reload');
// 				datagrid.datagrid('unselectAll');
// 				return true;
// 			}
// 			return false;
// 		}});
// 		window.event.cancelBubble=true;
	}

	function view(sid){
		/* var url = contextPath+"/system/core/tpl/pubTpl/view.jsp?sid="+sid;
		top.bsWindow(url,"模版查看");
		window.event.cancelBubble=true; */
	}

	function del(sid){
		top.$.jBox.confirm("是否要删除？","确认",function(v){
			if(v=="ok"){
				var url = contextPath + "/teeCrmPersonTargetController/deleteTeeCrmPersonTarget.action";
				var json = tools.requestJsonRs(url,{sid:sid});
				if(json.rtState){
					top.$.jBox.tip(json.rtMsg,"info");
					datagrid.datagrid('reload');
					datagrid.datagrid('unselectAll');
					return true;
				}
				top.$.jBox.tip(json.rtMsg,"error");
			}
		});
	}
	
	</script>
</head>
<body>
<table id="datagrid"></table>
<div id="toolbar">
	<div class="base_layout_top" style="position:static;">
		<span class="easyui_h1">个人目标查询</span>
	</div>
	<div style="padding:5px;">
		<!-- <button class="btn btn-primary" onclick="addOrUpdate(0)"></button> -->
	</div>
</div>
</body>
</html>