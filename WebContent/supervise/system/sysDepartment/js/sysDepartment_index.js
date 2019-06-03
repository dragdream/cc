/*初始化*/
var datagrid;
function doInit() {
	datagrid = $('#datagrid') .datagrid( {
						url : contextPath + '/departmentCtrl/listByPage.action',
						queryParams : { gender : -1 },
						pagination : true,
						singleSelect : true,
						pageSize : 20,
				        pageList : [ 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
						view : window.EASYUI_DATAGRID_NONE_DATA_TIP,
						toolbar : '#toolbar',// 工具条对象
						border : false,
						striped: true,
						rownumbers : false,
						fit : true,
						idField : 'uuid',// 主键列
						fitColumns : true,// 列是否进行自动宽度适应
						columns : [ [
								{ field : 'uuid', title : '', checkbox : true},
								{ field : 'deptName', title : '机构名称', width : 50,align:'left' , halign: 'center' },
								{ field : 'businessDeptName', title : '执法部门名称', width : 50,align:'left' , halign: 'center' , formatter : 
									function(data, rowData) {
									if (rowData.businessDeptName == "null") {
										return "";
									}else{
										return rowData.businessDeptName;
									}
								}},
								{ field : 'businessSupDeptName', title : '监督部门名称', width : 50,align:'left' , halign: 'center', formatter : 
									function(data, rowData) {
									if (rowData.businessSupDeptName == "null") {
										return "";
									}else{
										return rowData.businessSupDeptName;
									}
								} },
								{ field : 'businessSubjectName', title : '主体名称', width : 50,align:'left' , halign: 'center' , formatter : 
									function(data, rowData) {
									if (rowData.businessSubjectName == "null") {
										return "";
									}else{
										return rowData.businessSubjectName;
									}
								}},
								{ field : '___', title : '操作', width : 40, align:'center' , halign: 'center',formatter : function(e, rowData) {
										var optsStr = "<a href=\"#\"  onclick=\"edit('"
												+ rowData.uuid
												+ "')\">编辑</a>&nbsp;<a href=\"#\" onclick=\"cdel('"
												+ rowData.uuid + "')\">删除</a>";
										return optsStr;
									}
								}, ] ],
						singleSelect : false,
						selectOnCheck : true,
						checkOnSelect : true,
						onLoadSuccess : function(data, rowData) {
							if (data) {
								$.each(data.rows, function(index, item) {
									if (item.checked) {
										$('#datagrid').datagrid('checkRow',
												index);
									}
								});
							}
						}

					});
}


/*
 * 新增
 */
function add() {

	bsWindow(contextPath + "/supervise/system/sysDepartment/sysDepartment_add.jsp", "新增", {
		width : "800",
		height : "450",
		buttons : [ {
			name : "保存",
			classStyle : "btn-alert-blue"
		}, {
			name : "关闭",
			classStyle : "btn-alert-gray"
		} ],
		submit : function(v, h) {
			var cw = h[0].contentWindow;
			if (v == "保存") {
				var status = cw.save();
				if (status == true) {
					$("#datagrid").datagrid("reload");
					$('#datagrid').datagrid("clearSelections");
					return true;
				}
			} else if (v == "关闭") {
				return true;
			}
		}
	});

}

/*
 * 编辑
 */
function edit(uuid) {
	bsWindow(contextPath + "/supervise/system/sysDepartment/sysDepartment_add.jsp?uuid="+uuid, "编辑",
			{
				width : "800",
				height : "300",
				buttons : [ {
					name : "保存",
					classStyle : "btn-alert-blue"
				}, {
					name : "关闭",
					classStyle : "btn-alert-gray"
				} ],
				submit : function(v, h) {
					var cw = h[0].contentWindow;
					if (v == "保存") {
						var status = cw.save();
						if (status == true) {
							$("#datagrid").datagrid("reload");
							$('#datagrid').datagrid("clearSelections");
							return true;
						}
					} else if (v == "关闭") {
						return true;
					}
				}
			});

}
/**
 * 删除
 */
function cdel(uuid){
	if (window.confirm("是否确认删除该数据")) {
		var json = tools.requestJsonRs("/departmentCtrl/delete.action",{uuid:uuid});
		$('#datagrid').datagrid("reload");
		$('#datagrid').datagrid("clearSelections");
	}
}

/*
 * * 查询
 */
function doSearch() {	
	var params = {deptName:$("#deptName").val(),businessDeptName:$("#businessDeptName").val(),businessSupDeptName:$("#businessSupDeptName").val(),businessSubjectName:$("#businessSubjectName").val()};
	$('#datagrid').datagrid("reload",params);
	$('#datagrid').datagrid("clearSelections");
}
