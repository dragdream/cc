<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@ include file="/header/header2.0.jsp" %>
	<%@ include file="/header/easyui2.0.jsp" %>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>公共模版管理</title>
	
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
			url : contextPath+'/pubTemplate/datagrid.action',
			view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
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
				field : 'tplName',
				title : '模版名称',
				width : 200,
				formatter:function(value,rowData,rowIndex){
					var render = [];
					render.push("<a href='javascript:void(0)' onclick='view("+rowData.sid+")'>"+rowData.tplName+"</a>");
					
					return render.join("");
				}
			}, {
				field : 'tplDesc',
				title : '模版描述',
				width : 300
			} ,{
				field : 'sortNo',
				title : '排序号',
				width : 100
			},{
				field : '_manage',
				title : '操作',
				width : 100,
				formatter:function(value,rowData,rowIndex){
					var render = [];
					render.push("<a href='javascript:void(0)' onclick='view("+rowData.sid+")'>查看</a>");
					render.push("&nbsp;&nbsp;");
					render.push("<a href='javascript:void(0)' onclick='addOrUpdate("+rowData.sid+")'>编辑</a>");
					render.push("&nbsp;&nbsp;");
					render.push("<a href='javascript:void(0)' onclick='del("+rowData.sid+")'>删除</a>");
					
					return render.join("");
				}
			}
			] ]
		});
	}


	function addOrUpdate(sid){
		var url = contextPath+"/system/core/tpl/pubTpl/addOrUpdate.jsp?sid="+sid;
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
		var url = contextPath+"/system/core/tpl/pubTpl/view.jsp?sid="+sid;
		bsWindow(url,"模版查看",{width:"700",height:"300",buttons:
			[
		 	 {name:"关闭",classStyle:"btn-alert-gray"}
			 ]
			,submit:function(v,h){
			   if(v=="关闭"){
				   return true;
			   }
		   }});
		window.event.cancelBubble=true;
	}

	function del(sid){
		$.MsgBox.Confirm ("提示", "是否要删除该模版？", function(){
			var url = contextPath + "/pubTemplate/deleteTemplate.action";
			var json = tools.requestJsonRs(url,{sid:sid});
			if(json.rtState){
				$.MsgBox.Alert_auto(json.rtMsg);
				datagrid.datagrid('reload');
				datagrid.datagrid('unselectAll');
				return true;
			}
			$.MsgBox.Alert_auto(json.rtMsg);   
		  });

	}
	
	</script>
</head>
<body style="padding-left: 10px;padding-right: 10px">
<table id="datagrid"></table>
<div id="toolbar" class="topbar clearfix">
    <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/core/tpl/img/ggmb.png">
		<span class="title">公共模板管理 </span>
	</div>
	<div class = "right fr clearfix">
		<input type="button" class="btn-win-white fl" onclick="addOrUpdate(0)" value="添加模板"/>
    </div>
</div>
</body>
</html>