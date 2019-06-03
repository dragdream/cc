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
			url : contextPath+'/CustomerFieldController/datagrid.action',
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
			columns : [ [ {
				field : 'extendFiledName',
				title : '字段名称',
				width : 100,
			}, {
				field : 'orderNum',
				title : '排序号',
				width : 100
			} ,{
				field : 'filedType',
				title : '字段类型',
				width : 100
			},{
				field : 'isQuery',
				title : '是否作为查询字段',
				width : 100,
				formatter:function(value,rowData,rowIndex){
					var isQueryInt =  rowData.isQuery;
					var isQueryStr = "";
					if(isQueryInt==0){
						isQueryStr = "否";
					}else{
						isQueryStr = "是";
					}
					return isQueryStr;
				}
			},{
				field : 'isShow',
				title : '是否作为显示字段',
				width : 100,
				formatter:function(value,rowData,rowIndex){
					var isShowInt =  rowData.isShow;
					var isShowStr = "";
					if(isShowInt==0){
						isShowStr = "否";
					}else{
						isShowStr = "是";
					}
					return isShowStr;
				}
			},{
				field : '_manage',
				title : '操作',
				width : 100,
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
		var url = contextPath+"/system/subsys/crm/setting/setting/fieldsetting/addOrUpdateExtend.jsp?sid="+sid;
		/* bsWindow(url ,"自定义字段",{width:"650", height:"300",buttons:
			[
			 {name:"保存",classStyle:"btn-alert-blue"},
		 	 {name:"关闭",classStyle:"btn-alert-gray"}
			 ]
			,submit:function(v,h,f,d){
				var cw = h[0].contentWindow;
				if(v=="保存"){
					    cw.commit(function(){
					    $.MsgBox.Alert_auto("保存成功！");
						datagrid.datagrid('reload');
						d.remove();
						return true;
					});
				}else if(v=="关闭"){
					return true;
				}
		}}); */

		
		openFullWindow(url);
	}


	function del(sid){
		 $.MsgBox.Confirm ("提示", "是否删除此字段，删除后不可恢复！",function(){
				var url = contextPath + "/CustomerFieldController/deleteField.action";
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
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/subsys/crm/img/icon_jcsz.png">
		<span class="title">字段设置</span>
	</div>
   <div class="fr right">
      <input type="button" value="添加字段" class="btn-win-white" onclick="addOrUpdate(0)"/>
   </div>
</div>

</body>
</html>