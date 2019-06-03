<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<%@ include file="/header/header2.0.jsp"%>
	<%@ include file="/header/easyui2.0.jsp"%>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<style>
.modal-test {
	width: 500px;
	height: 230px;
	position: absolute;
	display: none;
	z-index: 999;
}

.modal-test .modal-header {
	width: 100%;
	height: 50px;
	background-color: #8ab0e6;
}

.modal-test .modal-header .modal-title {
	color: #fff;
	font-size: 16px;
	line-height: 50px;
	margin-left: 20px;
	float: left;
}

.modal-test .modal-header .modal-win-close {
	color: #fff;
	font-size: 16px;
	line-height: 50px;
	margin-right: 20px;
	float: right;
	cursor: pointer;
}

.modal-test .modal-body {
	width: 100%;
	height: 120px;
	background-color: #f4f4f4;
}

.modal-test .modal-body ul {
	overflow: hidden;
	clear: both;
}

.modal-test .modal-body ul li {
	width: 510px;
	height: 30px;
	line-height: 30px;
	margin-top: 25px;
	margin-left: 20px;
}

.modal-test .modal-body ul li span {
	display: inline-block;
	float: left;
	vertical-align: middle;
}

.modal-test .modal-body ul li input {
	display: inline-block;
	/* float: left; */
	width: 400px;
	height: 25px;
}

.modal-test .modal-footer {
	width: 100%;
	height: 60px;
	background-color: #f4f4f4;
}

.modal-test .modal-footer input {
	margin-top: 12px;
	float: right;
	margin-right: 20px;
}
</style>
	<script>
var datagrid;
function doInit(){
	getAssetsType();
// 	getDeptParent();
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/TeeFixedAssetsInfoController/datagrid.action",
		pagination:true,
		singleSelect:false,
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		idField:'sid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'assetCode',title:'资产编号',width:100},
			{field:'assetName',title:'资产名称',width:100},
			{field:'typeName',title:'资产类别',width:150},
			{field:'deptName',title:'所属部门',width:100},
			{field:'assetVal',title:'资产原值',width:100},
			{field:'valideTimeDesc',title:'启用时间',width:100},
			{field:'keeperName',title:'保管人',width:100},
			{field:'useState',title:'状态',width:80 ,
				formatter:function(e,rowData,index){
					return rowData.useStateDesc;
				}
			},
			{field:'useStateDesc',title:'状态',width:200,hidden:true},

			{field:'2',title:'操作',width:150,formatter:function(e,rowData,index){
				return "<a href='#' onclick='edit("+index+")'>修改</a>"
						+ "&nbsp;&nbsp;<a href='#' onclick='detail("+index+")'>详情</a>"
						+ "&nbsp;&nbsp;<a href='#' onclick='reZhejiu("+index+")'>重新折旧</a>"
						+ "&nbsp;&nbsp;<a href='javascript:void();' onclick='del("+index+")'>删除</a>";
			}}
		]]
	});
}

function edit(index){
	datagrid.datagrid("unselectAll");
	if(index>=0){
		datagrid.datagrid("selectRow",index);
	}
	var selection = datagrid.datagrid("getSelected");
	if(selection==null || selection==undefined){
		$.MsgBox.Alert_auto("请选择需要修改的内容！");
		return;
	}
	var sid = selection.sid;
	var url = contextPath+"/system/core/base/fixedAssets/manage/fixedAssets_edit.jsp?sid="+sid;
	location.href=url;
}

function del(index){
	datagrid.datagrid("unselectAll");
	if(index>=0){
		datagrid.datagrid("selectRow",index);
	}
	var selection = datagrid.datagrid("getSelected");
	if(selection==null || selection==undefined){
		$.MsgBox.Alert_auto("请选择需要删除的信息");
		return;
	}
	var sid = selection.sid;
	$.MsgBox.Confirm ("提示", "确认删除该资产信息吗？",function(){
			var url = contextPath+"/TeeFixedAssetsInfoController/delAssetsInfo.action";
			var json = tools.requestJsonRs(url,{sid:sid});
			if(json.rtState){
				$.MsgBox.Alert_auto(json.rtMsg);
				datagrid.datagrid("unselectAll");
				datagrid.datagrid("reload");
			}else{
				$.MsgBox.Alert_auto(json.rtMsg);
			}
	});
}

function delAll(){
	var selections = datagrid.datagrid("getSelections");
	if(selections==null || selections==undefined || selections.length<=0){
		$.MsgBox.Alert_auto("请选择需要删除项目");
		return;
	}
	$.MsgBox.Confirm ("提示", "确认删除所选中信息？",function(){
			for(var i=0;i<selections.length;i++){
			    var sid = selections[i].sid;
					var url = contextPath+"/TeeFixedAssetsInfoController/delAssetsInfo.action";
					var json = tools.requestJsonRs(url,{sid:sid});
					if(json.rtState){
						$.MsgBox.Alert_auto(json.rtMsg);
					}else{
						$.MsgBox.Alert_auto(json.rtMsg);
					}
				}
			 datagrid.datagrid("unselectAll");
			 datagrid.datagrid("reload");
	});
}
function detail(index){
	datagrid.datagrid("unselectAll");
	if(index>=0){
		datagrid.datagrid("selectRow",index);
	}
	var selection = datagrid.datagrid("getSelected");
	if(selection==null || selection==undefined){
		$.MsgBox.Alert_auto("请选择需要查看的信息！");
		return;
	}
	var sid = selection.sid;
	openFullWindow(contextPath+"/system/core/base/fixedAssets/manage/fixedAssets_detail.jsp?sid="+sid,"资产详情",900,900);
}

function optDetail(sid){
	window.location.href = "<%=contextPath%>/system/core/base/fixedAssets/record/recordDetail.jsp?fixedAssetsId=" + sid; 
}


function getAssetsType(){
	var url =contextPath+"/TeeFixedAssetsCategoryController/datagrid.action"
	var json = tools.requestJsonRs(url);
	var typeNames = json.rows;
	var html = "<option value=\"0\"></option>";
	for(var i=0;i<typeNames.length;i++){
		html+="<option value=\""+typeNames[i].sid+"\">"+typeNames[i].name+"</option>";
	}
	$("#typeId").html(html);
}
/**
 * 获取部门
 */
function getDeptParent(){
	var url =  "<%=contextPath%>/deptManager/getDeptTreeAll.action";
			var config = {
				zTreeId : "deptIdZTree",
				requestURL : url,
				onClickFunc : onclickDept,
				async : false,
				onAsyncSuccess : setDeptParentSelct
			};
			zTreeObj = ZTreeTool.config(config);
			//setTimeout('setDeptParentSelct()',500);
		}
		/**
		 * 初始化后选中节点,上级部门
		 */
		function setDeptParentSelct() {
			ZTreeObj = $.fn.zTree.getZTreeObj(ZTreeTool.zTreeId);
			if (ZTreeObj == null) {
				setTimeout('setDeptParentSelct()', 500);
			} else {
				ZTreeObj.expandAll(true);
				var node = ZTreeObj.getNodeByParam("id", $("#deptId").val(),
						null);
				if (node) {
					ZTreeObj.selectNode(node);
				}
			}
			ZTreeTool.inputBindZtree(ZTreeTool.zTreeId, 'deptId', '');
		}

		//点击树执行事件
		function onclickDept(event, treeId, treeNode) {
			$("#deptIdName").val(treeNode.name);
			$("#deptId").val(treeNode.id);
			ZTreeTool.hideZtreeMenu();
		}

		//根据条件查询
		function doSearch() {
			/* var queryParams =datagrid.datagrid('options').queryParams; 
			  queryParams.asstesCode=$("#assetsCode").val();
			  queryParams.assetsName=$("#assetsName").val();
			  queryParams.deptId=$("#deptId").val();
			  queryParams.typeId=$("#typeId").val(); */
			var queryParams = tools.formToJson($("#form1"));
			$(".modal-win-close").click();
			datagrid.datagrid('options').queryParams = queryParams;
			datagrid.datagrid("reload");
			// $("#myModal").modal("hide");
		}

		function reZhejiu(index) {
			datagrid.datagrid("unselectAll");
			if (index >= 0) {
				datagrid.datagrid("selectRow", index);
			}
			var selection = datagrid.datagrid("getSelected");
			if (selection == null || selection == undefined) {
				$.MsgBox.Alert_auto("请选择需要折旧的信息！");
				return;
			}
			var sid = selection.sid;
			$.MsgBox.Confirm("提示", "确认要对当前资产重新折旧吗？", function() {
				var url = contextPath
						+ "/TeeFixedAssetsInfoController/reZhejiu.action";
				var json = tools.requestJsonRs(url, {
					sid : sid
				});
				if (json.rtState) {
					$.MsgBox.Alert_auto(json.rtMsg);
				} else {
					$.MsgBox.Alert_auto("没有找到相关固定资产信息！");
				}
				datagrid.datagrid("unselectAll");
				datagrid.datagrid("reload");
			});
		}
	</script>
	<style>
.t_btns>button {
	padding: 5px 8px;
	padding-left: 22px;
	text-align: right;
	background-repeat: no-repeat;
	background-position: 6px center;
	background-size: 17px 17px;
	border-radius: 5px;
	background-color: #e6f3fc;
	border: none;
	color: #000;
	outline: none;
	font-size: 12px;
	border: #abd6ea solid 1px;
}
</style>
</head>
<body onload="doInit()"
	style="overflow: hidden; font-size: 12px; padding-left: 10px; padding-right: 10px;">
	<table id="datagrid" fit="true"></table>
	<div id="toolbar" class="topbar clearfix">
		<div class="fl" style="position: static;">
			<img id="img1" class='title_img'
				src="<%=contextPath %>/common/zt_webframe/imgs/xzbg/gdzc/icon_zcgl.png">&nbsp;&nbsp;
				<span class="title">固定资产管理</span>
		</div>
		<div class="right fr t_btns">
			<button style="cursor: pointer;"
				onclick="window.location = 'fixedAssets_add.jsp';">
				<span style="padding-right: 13px;">新建资产</span>
			</button>
			&nbsp;&nbsp;
			<button style="cursor: pointer;" onclick="delAll()">
				<span style="padding-right: 13px; color: red;">批量删除</span>
			</button>
			<button type="button" onclick="$(this).modal();" value="高级检索"
				class="modal-menu-test"
				style="background-image: url(/common/zt_webframe/imgs/xzbg/gdzc/icon_search1.png); cursor: pointer;">&nbsp;高级查询</button>
		</div>

	</div>

	<form id="form1" name="form1">
		<div class="modal-test">
			<div class="modal-header">
				<p class="modal-title">查询</p>
				<span class="modal-win-close">×</span>
			</div>

			<div class="modal-body">
				<table class="TableBlock" width="100%">
					<tr>
						<td style="text-indent: 10px; font-size: 12px;" class="TableData">资产编号：</td>
						<td class="TableData"><input type="text" id="assetsCode"
							name='assetsCode' style="height: 20px" /></td>
					</tr>
					<tr>
						<td class="TableData" style="text-indent: 10px; font-size: 12px;">资产名称：</td>
						<td class="TableData" colspan='3'><input class="BigInput"
							type='text' id='assetsName' name='assetsName'
							style="height: 20px" /></td>
					</tr>
					<tr>
						<td style="text-indent: 10px; font-size: 12px;" class="TableData">事件类型：</td>
						<td class="TableData"><select id='typeId' name='typeId'>
						</select></td>
					</tr>
				</table>
			</div>
			<div class="modal-footer clearfix">
				<input type="reset" class="btn-alert-gray" value="重置" onclick="" />
				<input type="button" class="modal-save btn-alert-blue"
					onclick="doSearch();" value="查询" />
			</div>
		</div>
	</form>
</body>
</html>