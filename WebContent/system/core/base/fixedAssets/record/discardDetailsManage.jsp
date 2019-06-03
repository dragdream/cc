<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String optType = TeeStringUtil.getString(
			request.getParameter("optType"), "0");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/ztree.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript"
	src="<%=contextPath%>/system/core/base/fixedAssets/js/assets.js"></script>
<script>

var optType = <%=optType%>;
var datagrid;
function doInit(){
// 	getDeptParent();
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/teeFixedAssetsRecordController/getRecordDatagrid.action?optType=4",
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
			{field:'assetCode',title:'资产编号',width:100,formatter:function(data,rowData){
				return "<a href='javascript:void(0)' onclick='detail("+rowData.runId+")'>"+data+"</a>";
			}},
			{field:'assetName',title:'资产名称',width:200},
			{field:'typeName',title:'资产类别',width:150},
			{field:'optReason',title:'报废原因',width:200},
			{field:'optDateDesc',title:'报废日期',width:200}
		]]
	});
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
			var node = ZTreeObj.getNodeByParam("id", $("#deptId").val(), null);
			if (node) {
				ZTreeObj.selectNode(node);
			}
		}
		ZTreeTool.inputBindZtree(ZTreeTool.zTreeId, 'deptId', '');
	}

	function detail(runId) {
		openFullWindow(contextPath
				+ "/system/core/workflow/flowrun/print/index.jsp?runId="
				+ runId + "&view=31", "");
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
		datagrid.datagrid('options').queryParams = queryParams;
		datagrid.datagrid("reload");
	}
</script>
<style>
.ztree {
	margin-top: 0;
	height: 200px;
	width: 150px;
	display: none;
	background: white;
	border: 1px solid gray;
	overflow: auto;
}
</style>
</head>
<body onload="doInit()" style="overflow: hidden; font-size: 12px">
	<table id="datagrid" fit="true"></table>
	<div id="toolbar" class="clearfix">
		<form method="post" name="form1" id="form1" style="padding: 5px;">
			<div id="searchDiv">
				<table>
					<tr>
						<td class="TableData TableBG">资产编号：</td>
						<td nowrap class="TableData" align="left" style="width: 200px;">
							<input class="BigInput" type="text" id='assetCode'
							name='assetCode' style="height: 25px;font-family: MicroSoft YaHei;" />
						</td>

						<td class="TableData TableBG">资产名称：</td>
						<td nowrap class="TableData" align="left" style="width: 150px;">
							<input class="BigInput" type='text' id='assetName'
							name='assetName' style="height: 25px;font-family: MicroSoft YaHei;" />
						</td>

						<td style="padding-left: 30px;"><input type="button"
							class="btn-win-white" onclick="doSearch();" value="查询" title="查询" style="width:45px;height:25px;"></td>
					</tr>
				</table>
				<input type="hidden" name="optType" value="0">
			</div>
		</form>
	</div>
</body>

</html>