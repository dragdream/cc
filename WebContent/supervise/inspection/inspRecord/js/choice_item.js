
var recordId = '';
var orgSys = '';
var datagrid = '';

function doInit() {
	// ctrlType = $('#ctrlType').val();
	// loginDeptId = $('#loginDeptId').val();
	// loginSubId = $('#loginSubId').val();
	// recordId = $('#recordId').val();
	// orgSys = $('#orgSysCtrl').val();
	var params = {
		inspListId : $('#inspListId').val(),
	}
	initItemsDatagrid(params);
	doInitEnter();
}
/**
 * 初始化检查项表格
 * 
 * @returns
 */
function initItemsDatagrid(params) {
	var itemResult = tools.requestJsonRs(
			"/inspListBaseCtrl/getItemsRelatedList.action", params);

	datagrid = $('#record_items_table').datagrid({
		data : itemResult.rtData,
		pagination : false,
		view : window.EASYUI_DATAGRID_NONE_DATA_TIP,
		toolbar : '#toolbar', // 工具条对象
		checkbox : true,
		border : false,
		/* idField:'formId',//主键列 */
		fitColumns : true, // 列是否进行自动宽度适应
		singleSelect : false, // 为true只能选择一行
		// checkOnSelect: false,
		// selectOnCheck: false,
		striped : true,
		nowrap : true,
		onLoadSuccess : function(data) {

		},
		columns : [ [ {
			field : 'id',
			checkbox : true,
			title : "ID",
			width : 20,
			align : 'center'
		}, {
			field : 'ID',
			title : '序号',
			align : 'center',
			formatter : function(value, rowData, rowIndex) {
				return rowIndex + 1;
			}
		}, {
			field : 'inspItemName',
			title : '检查项名称',
			width : 60,
			halign : 'center'
		} ] ]
	});
}
/**
 * 获取回车事件
 * 
 * @returns
 */
function doInitEnter() {
	document.onkeyup = function(e) {
		var code = e.charCode || e.keyCode; // 取出按键信息中的按键代码(大部分浏览器通过keyCode属性获取按键代码，但少部分浏览器使用的却是charCode)
		if (code == 13) {
			searchItems();
		}
	}
}

function searchItems() {
	var params = {
		inspItemName : $('#inspItemName').val(),
		inspListId : $('#inspListId').val()
	}
	initItemsDatagrid(params);
}

function getSelectedItems() {
	var items = $('#record_items_table').datagrid('getChecked');
	return items;
}
