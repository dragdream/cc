/*初始化*/
var datagrid;
function doInit(){
	getSysCodeByParentCodeNo("LAW_ENFORCEMENT_FIELD","ORG_SYS");
	datagrid = $('#datagrid') .datagrid( {
						url : contextPath + '/inspectionCtrl/listByPage.action',
						queryParams : { gender : -1 },
						pagination : true,
						singleSelect : true,
						view : window.EASYUI_DATAGRID_NONE_DATA_TIP,
						toolbar : '#toolbar',// 工具条对象
						border : false,
						rownumbers : false,
						fit : true,
						idField : 'id',// 主键列
						fitColumns : true,// 列是否进行自动宽度适应
						columns : [ [
								{ field : 'sid', title : '', checkbox : true},
								{ field : 'organizationId', title : '部门名称', width : 50 },
								/*{ field : 'organizationName', title : '部门名称', width : 50 },*/
								
								{ field : 'moduleName', title : '模块名称', width : 50 },
								{ field : 'orgSysName', title : '执法系统', width : 50 },
								/*{ field : 'orgSys', title : '执法系统', width : 50, formatter :
										function(data, rowData) {
										if (data == 1) {
											return "本级政府";
										}
										if (data == 2) {
											return "发展改革委";
										}
										if (data == 3) {
											return "财政局";
										}
										if (data == 5) {
											return "教育局";
										}
										if (data == 6) {
											return "商务委";
										}
									}
								},*/
								{ field : '___', title : '操作', width : 40, formatter : function(e, rowData) {
									
										var optsStr = "<a href=\"#\" style=\"margin-left:40px;\" onclick=\"edit('"
												+ rowData.id
												+ "')\">编辑</a>&nbsp;<a href=\"#\" onclick=\"del('"
												+ rowData.id + "')\">删除</a>";
									
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
 * * 查询
 */
function queryS() {	
	var params = {
		name : $("#organizationId").val(),//机构：执法单位
		modelname : $("#modelname").val(),//模块名称
		//地区？？
		/*isExamine : $("#isExamine").val(),*/
	};
	console.log(params);
	$('#datagrid').datagrid("reload", params);

}

/*
 * 跳转新增
 */
function add() {
	bsWindow(contextPath + "/supervise/inspection/inspection_add.jsp", "新增", {
		width : "500",
		height : "220",
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
function edit(id) {
	console.log(id);
	bsWindow(contextPath + "/supervise/inspection/inspection_edit.jsp?id="+id, "编辑",
			{
				width : "500",
				height : "220",
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
							return true;
						}
					} else if (v == "关闭") {
						return true;
					}
				}
			});

}

//获取选中的行内容 删除
function del() {
//	debugger;
	
	var obj = $('#datagrid').datagrid("getSelections");
	console.log(obj[0].id);
	///departmentInfoController/delete.action
//	var did = obj[0].id;
	
	if (window.confirm("是否确认删除该数据")) {
		var json = tools.requestJsonRs("/inspectionCtrl/delete.action", {id : obj[0].id});
		$('#datagrid').datagrid("reload");
	}
}


/* 超链接删除 */
function cdel(id) {
	debugger;
	console.log(id);
	if (window.confirm("是否确认删除该数据")) {
		var json = tools.requestJsonRs("/inspectionCtrl/delete.action", {
					id : id
				});
		$('#datagrid').datagrid("reload");
	}
}


/*
 * 多删
 */
function deletes() {
//	debugger;
	var checkedItems = $('#datagrid').datagrid('getChecked');
	var ids = [];
	$.each(checkedItems, function(index, item) {
		ids.push(item.id);
		alert(ids)
	});
	// alert(names.join(","));
	// console.log(ids.join(","));
	if (window.confirm("是否确认删除该数据")) {
		var json = tools.requestJsonRs("/inspectionCtrl/deletes.action",{ids:ids.join(",")});
		$('#datagrid').datagrid("reload");
	}
}

function deletes2() {
	
	var checkedItems = $('#datagrid').datagrid('getChecked');
	var ids = [];
	$.each(checkedItems, function(index, item) {
		ids.push(item.id);
//		alert(ids)
	});
	
	
//    $.ajax({
//        type:'POST',
//        data:JSON.stringify(ids),
//        contentType :'application/json',
//        dataType:'json',
//        url :"/inspectionCtrl/deletes.action",
////        url :"<%=contextPath %>/inspectionCtrl/deletes.action",
//        success :function(data) {
//            alert("OK");
//        },
//        error :function(e) {
//            alert("error");
//        }
//    });
}
