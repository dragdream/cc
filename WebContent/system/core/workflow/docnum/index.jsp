<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<%@ include file="/header/header2.0.jsp" %>
	<%@ include file="/header/easyui2.0.jsp" %>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>Tenee办公自动化智能管理平台</title>
	<script>
	var contextPath = "<%=contextPath%>";
	function doInit(){
		initDataGrid();
	}

	function initDataGrid(){
		$('#datagrid').datagrid({
			url:'<%=contextPath%>/docNumController/datagrid.action',
			view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
			pagination:true,
			singleSelect:true,
			toolbar:'#toolbar',//工具条对象
			checkbox:true,
			border:false,
			idField:'sid',//主键列
			fitColumns : true,
			sortOrder: 'desc',
			striped: true,
			remoteSort: false,
			columns:[[
                {field:'sid',checkbox:true,title:'ID',width:100},
				{field:'docName',width : 100,title:'名称',sortable : true,formatter:function(data,rowData){
					return "<a href='javascript:void(0)' onclick='edit("+rowData.sid+")'>"+data+"</a>";
				}},
				{field:'orderNo',width : 100,title:'排序号',sortable : true},
				{field:'docStyle',width : 100,title:'文号样式',sortable : true},
				{field:'currNum',width : 100,title:'当前编号值',sortable : true},
				{field:'deptPrivNames',width : 100,title:'部门权限',sortable : true},
				{field:'userPrivNames',width : 100,title:'用户权限',sortable : true},
				{field:'rolePrivNames',width : 100,title:'角色权限',sortable : true},
				{field:'flowNames',width : 100,title:'绑定流程',sortable : true},
				{field:'opt',width : 100,title:'操作',formatter:function(data,rowData){
					var render = [];
					render.push("<a href='javascript:void(0)' onclick='edit("+rowData.sid+")'>编辑</a>");
					render.push("<a href='javascript:void(0)' onclick='clear0("+rowData.sid+")'>重置</a>");
					return render.join("&nbsp;&nbsp;");
				}}
			]]
		});
	}
	
	function clear0(sid){
		$.MsgBox.Confirm ("提示", "是否要重置该文号？注：重置后会清空文号的所有历史记录！", function(){
			var json = tools.requestJsonRs(contextPath+"/docNumController/clear.action",{sid:sid});
			if(json.rtState){
				$('#datagrid').datagrid("reload");
				$('#datagrid').datagrid("unselectAll");
				$.MsgBox.Alert_auto("文号已重置");
			}
		  });
	}

	function add(){
		bsWindow(contextPath+"/system/core/workflow/docnum/add.jsp","添加文号",{width:"600",height:"330",buttons:
			[{name:"保存",classStyle:"btn-alert-blue"},
			 {name:"关闭",classStyle:"btn-alert-gray"}]
			,submit:function(v,h){
			var cw = $(h)[0].contentWindow;
			if(v=="保存"){
				if(cw.commit()){
					$.MsgBox.Alert_auto("添加成功！");
					$('#datagrid').datagrid("reload");
					$('#datagrid').datagrid("unselectAll");
					return true;
				}
			}else if(v=="关闭"){
				return  true;
			}
		}});
	}

	function delete0(){
		var selection = $('#datagrid').datagrid('getSelected');
		if(!selection){
			$.MsgBox.Alert_auto("至少选择一项");
			return;
		}

		
		$.MsgBox.Confirm ("提示", "确认要删除该文号吗？", function(){
			var url = contextPath+"/docNumController/deleteDocNum.action";
			var json = tools.requestJsonRs(url,{sid:selection.sid});
			if(json.rtState){
				$.MsgBox.Alert_auto("删除成功！");
				$('#datagrid').datagrid("reload");
				$('#datagrid').datagrid("unselectAll");
				return true;
			}  
		  });

	}

	function edit(sid){
		bsWindow(contextPath+"/system/core/workflow/docnum/edit.jsp?sid="+sid,"编辑文号",{width:"600",height:"330",buttons:
			[{name:"保存",classStyle:"btn-alert-blue"},
			 {name:"关闭",classStyle:"btn-alert-gray"}]
			,submit:function(v,h){
			var cw = $(h)[0].contentWindow;
			if(v=="保存"){
				if(cw.commit()){
					$.MsgBox.Alert_auto("修改成功！");
					$('#datagrid').datagrid("reload");
					$('#datagrid').datagrid("unselectAll");
					return true;
				}
			}else if(v=="关闭"){
				return  true;
			}
		}});
	}
	
	</script>
</head>
<body onload="doInit()" style="padding-left: 10px;padding-right: 10px">
	<table id="datagrid" fit="true"></table>
	<div id="toolbar" class="topbar clearfix">
	   <div class="fl left">
	     <img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/gzl/wenhao/icon_wenhaoguanli.png">
		 <span class="title">文号管理</span>
	   </div>
	   <div class="fr right">
	      <button class="btn-win-white" onclick="add()">添加</button>
		  <button class="btn-del-red" onclick="delete0()">删除</button>
	   </div>
	
	</div>
</body>

</html>