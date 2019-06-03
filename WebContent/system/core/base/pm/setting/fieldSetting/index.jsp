<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@ include file="/header/header2.0.jsp" %>
	<%@ include file="/header/easyui2.0.jsp" %>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>自定义字段管理</title>
	
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
			url : contextPath+'/pmCustomerFieldController/datagrid.action',
			toolbar : '#toolbar',
			title : '',
			queryParams:param,
			singleSelect:true,
			view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
			iconCls : 'icon-save',
			pagination : true,
			pageSize : 10,
			//pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
			fit : true,
			fitColumns : true,
			nowrap : true,
			border : false,
			idField : 'sid',
			striped: true,
			remoteSort: false,
			columns : [ [ 
		    {
			   field : 'orderNum',
			   title : '排序号',
			   width : 100
			}, {
				field : 'extendFiledName',
				title : '字段名称',
				width : 100,
			} ,{
				field : 'filedType',
				title : '字段类型',
				width : 100,
				align : "center",
			},{
				field : 'isQuery',
				title : '查询字段',
				width : 100,
				align : "center",
				formatter:function(value,rowData,rowIndex){
					var isQueryInt =  rowData.isQuery;
					var isQueryStr = "";
					if(isQueryInt==0){
						isQueryStr = "×";
					}else{
						isQueryStr = "√";
					}
					return isQueryStr;
				}
			},{
				field : 'isShow',
				title : '显示字段',
				width : 100,
				align : "center",
				formatter:function(value,rowData,rowIndex){
					var isShowInt =  rowData.isShow;
					var isShowStr = "";
					if(isShowInt==0){
						isShowStr = "×";
					}else{
						isShowStr = "√";
					}
					return isShowStr;
				}
			},{
				field : '_manage',
				title : '操作',
				width : 100,
				align : "center",
				formatter:function(value,rowData,rowIndex){
					var render = [];
					render.push("<a href='javascript:void(0)' onclick='addOrUpdate("+rowData.sid+")'>编辑</a>");
					render.push("&nbsp;");
					render.push("<a href='javascript:void(0)' onclick='del("+rowData.sid+")'>删除</a>");
					
					return render.join("");
				}
			}
			] ]
		});
	}

	function addOrUpdate(sid){
		window.location.href=contextPath+"/system/core/base/pm/setting/fieldSetting/addOrUpdate.jsp?sid="+sid;
	}


	function del(sid){
		 $.MsgBox.Confirm ("提示", "是否删除此字段，删除后不可恢复！",function(){
				var url = contextPath + "/pmCustomerFieldController/deleteField.action";
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
<div class="topbar clearfix" id="toolbar">
   <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/core/base/pm/img/icon_zdsz.png">
		<span class="title">字段设置</span>
	</div>
   <div class="fr right">
      <input type="button" value="新增字段" class="btn-win-white" onclick="addOrUpdate(0)"/>
   </div>
</div>

</body>
</html>