<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@ include file="/header/header.jsp" %>
	<%@ include file="/header/easyui.jsp" %>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>用户管理</title>
	
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
			url : contextPath+'/user/datagrid.action',
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
				field : 'userName',
				title : '用户名',
				width : 100,
				formatter:function(value,rowData,rowIndex){
					var render = [];
					render.push("<a href='javascript:void(0)' onclick='view("+rowData.userId+")'>"+rowData.userName+"</a>");
					
					return render.join("");
				}
			}, {
				field : 'gender',
				title : '性别',
				width : 100
			} ,{
				field : 'age',
				title : '年龄',
				width : 100
			},{
				field : 'deptName',
				title : '部门名称',
				width : 200
			},{
				field : '_manage',
				title : '操作',
				width : 250,
				formatter:function(value,rowData,rowIndex){
					var render = [];
					render.push("<a href='javascript:void(0)' onclick='view("+rowData.userId+")'>查看</a>");
					render.push("&nbsp;");
					render.push("<a href='javascript:void(0)' onclick='addOrUpdate("+rowData.userId+")'>编辑</a>");
					render.push("&nbsp;");
					render.push("<a href='javascript:void(0)' onclick='del("+rowData.userId+")'>删除</a>");
					
					return render.join("");
				}
			}
			] ]
		});
	}


	function addOrUpdate(userId){
		var url = contextPath+"/system/test/test/addOrUpdate.jsp?userId="+userId;
		openFullWindow(url);
 		var title = "";
		if(sid==0){
			title = "添加用户信息";
 		}else{
 			title = "修改用户信息";
 		}
 		top.bsWindow(url,title,{submit:function(v,h){
			var cw = $(h)[0].contentWindow;
			if(cw.commit()){
 				datagrid.datagrid('reload');
				datagrid.datagrid('unselectAll');
 				return true;
 			}
 			return false;
		}});
 		window.event.cancelBubble=true;
	}

	function view(userId){
		var url = contextPath+"/system/test/test/testview.jsp?userId="+userId;
		top.bsWindow(url,"用户信息查看");
		window.event.cancelBubble=true;
	}

	function del(userId){
		top.$.jBox.confirm("是否要删除该模版？","确认",function(v){
			if(v=="ok"){
				var url = contextPath + "/user/deleteUser.action";
				var json = tools.requestJsonRs(url,{userId:userId});
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
<table id="datagrid">




</table>
<div id="toolbar">
	<div class="base_layout_top" style="position:static;">
		<span class="easyui_h1">用户管理</span>
	</div>
	<div style="padding:5px;">
		<button class="btn btn-primary" onclick="addOrUpdate(0)">添加用户</button>
	</div>
</div>
</body>
</html>