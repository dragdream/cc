<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="priv" uri="/WEB-INF/tags/priv.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<priv:init />
<html>
<head>
	<%@ include file="/header/header2.0.jsp" %>
	<%@ include file="/header/easyui2.0.jsp" %>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>测试页面</title>
	
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
	
	function getPagePriv(data){
		var url = contextPath +  "/menuButton/getBtnPrivByMenuUuid.action";
		var para = {menuId:menuId};
		var jsonRs = tools.requestJsonRs(url,para);
		var btnArr = jsonRs.rtData;
		for(var i=0;i<btnArr.length;i++){
			if(btnArr[i].IS_PRIV == '0'){
				var inputs = $("input[data-" + btnArr[i].BUTTON_NO + "]");
				inputs.hide();
				var as = $("a[data-" + btnArr[i].BUTTON_NO + "]");
				as.hide();			
			}
		}
	}

	function query(){
		var param = {};
		datagrid = $('#datagrid').datagrid({
			url : contextPath+'/DemoCaseInfoController/datagrid.action',
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
			columns : [ [  {
				field : 'id',
				title : '序号',
				width : 20
			} ,{
				field : 'name',
				title : '案件名称',
				width : 200,
				formatter:function(value,rowData,rowIndex){
					var render = [];
					render.push("<a href='javascript:void(0)' onclick='view("+rowData.sid+")'>"+rowData.name+"</a>");
					
					return render.join("");
				}
			},{
				field : 'status',
				title : '案件状态',
				width : 80
			},{
				field : 'memo',
				title : '备注',
				width : 150
			},{
				field : 'adduser',
				title : '添加人',
				width : 50
			},{
				field : 'addtime',
				title : '添加时间',
				width : 50,
				formatter:function(value,rowData,rowIndex){
					 var unixTimestamp = new Date(value);  
					 return unixTimestamp.toLocaleDateString(); 
				}
			},{
				field : '_manage',
				title : '操作',
				width : 100,
				formatter:function(value,rowData,rowIndex){
					var render = [];
					render.push('<priv:a_button code="viewBtn" value="查看" priv="${btnPriv.viewBtn}" onclick="view('+rowData.id+')"/>');
					if(${btnPriv.editBtn} || rowData.status== '未提交'){
						render.push("&nbsp;&nbsp;");
						render.push("<a data-editBtn href='javascript:void(0)' onclick='edit("+rowData.id+")'>编辑</a>");
					}
					<priv:if test="${btnPriv.deleteBtn}">
						render.push("&nbsp;&nbsp;");
						render.push("<a data-deleteBtn href='javascript:void(0)' onclick='del("+rowData.id+")'>删除</a>");
					</priv:if>
					return render.join("");
				}
			}
			] ],
			onLoadSuccess:function(data){
				//getPagePriv(data);
			}
		});
	}
	
	function view(value){
		alert("查看案件：" + value)
	}
	
	function del(value){
		alert("删除案件：" + value)
	}
	
	function edit(value){
		alert("编辑案件：" + value)
	}
	
	function add(value){
		alert("新增案件：" + value)
	}

	</script>
</head>
<body style="padding-left: 10px;padding-right: 10px">
<table id="datagrid"></table>
<div id="toolbar" class="topbar clearfix">
    <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/core/tpl/img/ggmb.png"/>
		<span class="title">自定义按钮 </span>
	</div>
	<div class = "right fr clearfix">
		<textarea style="" rows="10" cols="120">${btnPriv.addBtn} || ${btnPriv.editBtn} || ${btnPriv.deleteBtn} || ${btnPriv.viewBtn} || ${btnPriv}</textarea>
    	<priv:if test="${btnPriv.addBtn}"> 
    		<input type="button" class="fr btn-win-white"  value="新增"  onclick="add(0);" id="addBtn"/>
    	</priv:if>
    	<priv:button code="editBtn" value="修改" priv="${btnPriv.editBtn}" onclick="edit(0);" id="editBtn"/>
    	<priv:button code="deleteBtn" value="删除" priv="${btnPriv.deleteBtn}" onclick="edit(0);" id="deleteBtn"/>
    	<priv:button code="viewBtn" value="查看" priv="${btnPriv.viewBtn}" onclick="edit(0);" id="viewBtn"/>
    </div>
</div>
</body>
</html>