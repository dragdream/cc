/*初始化*/
var datagrid;
function doInit() {
	listDatagrid();
	document.onkeyup = function (e) {
        var code = e.charCode || e.keyCode;  //取出按键信息中的按键代码(大部分浏览器通过keyCode属性获取按键代码，但少部分浏览器使用的却是charCode)
        if (code == 13) {
        	queryS();
        }
    }
}

/*
 * 分页
 */
function listDatagrid(){
	datagrid = $('#datagrid') .datagrid( {
		url : contextPath + '/SupPersonController/findListBypage.action',
		queryParams : { gender : -1 },
		pagination : true,
		striped: true,
		singleSelect : true,
		pageSize : 20,
        pageList : [ 10, 20, 50, 100 ],
		view : window.EASYUI_DATAGRID_NONE_DATA_TIP,
		toolbar : '#toolbar',// 工具条对象
		border : false,
		rownumbers : false,
		fit : true,
		idField : 'id',// 主键列
		fitColumns : true,// 列是否进行自动宽度适应
		columns : [ [
				{ field : 'id', title : '', checkbox : true},
				{field:'ID',title:'序号',align:'center',
                    formatter:function(value,rowData,rowIndex){
                        return rowIndex+1;
                    }
                },
				{ field : 'name', title : '姓名',align:'left' ,width:'8%', halign: 'center', formatter: 
					function(e,rowData){
            		var optsStr = "<a href=\"#\" title=" + e + " onclick=\"look('" + rowData.id + "')\">" + e + "</a>";
					return optsStr;
	            },},
				{ field : 'sex', title : '性别', align:'center' ,width:'6%', halign: 'center' , formatter : 
					function(data, rowData) {
					if (rowData.sex == "01") {
						return "男";
					}else {
						return "女";
					}
				}},
				{ field : 'personId', title : '身份证号', align:'center' ,width:'15%', halign: 'center' , formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
						return optsStr;
					}
		            },},
				{ field : 'jobClass', title : '职级', align:'center' ,width:'14%', halign: 'center' , formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
						return optsStr;
					}
		            },},
				{ field : 'telephone', title : '联系电话', align:'center' ,width:'11%', halign: 'center' , formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
						return optsStr;
					}
		            },},
				{ field : 'departmentName', title : '所属机关', align:'left' ,width:50, halign: 'center' , formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
						return optsStr;
					}
		            },},
				{ field : 'username', title : '账号', width : '9%',align:'center' , halign: 'center' , formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
						return optsStr;
					}
		            },},
				{ field : 'examine', title : '审核状态', align:'center' ,width:'8%', halign: 'center' , formatter : 
					function(data, rowData) {
					if (rowData.examine == 0) {
						return "未审核";
					}else {
						return "已审核";
					}
				}},
				{ field : '___', title : '操作',align:'center' ,width:'6%', halign: 'center', formatter : function(e, rowData) {
					if (rowData.examine == 1) {
						var optsStr = "<span title='查看'><a href='javaScript:void(0);' onclick='look(\"" + rowData.id + "\")'><i class='fa fa-eye'></i></a></span>";
					} else { 
						var optsStr = "<span title='修改'><a href='javaScript:void(0);' onclick='edit(\"" + rowData.id + "\")'><i class='fa fa-pencil common-yellow'></i></a></span>" +
								"&nbsp;&nbsp;<span title='删除'><a href='javaScript:void(0);' onclick='cdel(\"" + rowData.id + "\")' ><i class='fa fa-trash-o common-red'></i></a></span>";
					}
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
	top.bsWindow(contextPath + "/supervise/supervise/supperson_add.jsp", "新增", {
		width : "750",
		height : "300",
		buttons : [ {
			name : "关闭",
			classStyle : "btn-alert-gray"
		} ,{
			name : "保存",
			classStyle : "btn-alert-blue"
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

/** 审核
 * 0 改 1
*      */
function  auditing(){
	var obj = $('#datagrid').datagrid("getSelections");
	if(obj.length == 1){
		if (obj[0].examine==1){
			$.MsgBox.Alert_auto("您已审核该数据");
		}else{
			top.$.MsgBox.Confirm("提示","确认审核？",function(){
				var json = tools.requestJsonRs("/SupPersonController/examine.action",{id:obj[0].id});
				$('#datagrid').datagrid("reload");
				$('#datagrid').datagrid("clearSelections");
			});
		}
	}else{
		$.MsgBox.Alert_auto("请选择一条未审核记录");
	}
	
	
}
	
/** 取消审核
 * 1改0
*      */
function  RemoveAuditing(){
	var obj = $('#datagrid').datagrid("getSelections");
	if (obj.length == 1){
		if (obj[0].examine==0){
			$.MsgBox.Alert_auto("该数据已在取消审核状态");
		}else{
			top.$.MsgBox.Confirm("提示","是否取消审核？",function(){
				var json = tools.requestJsonRs("/SupPersonController/examine.action",{id:obj[0].id});
				$('#datagrid').datagrid("reload");
				$('#datagrid').datagrid("clearSelections");
			});
		}
	}else{
		$.MsgBox.Alert_auto("请选择一条数据");
	}
}

/*
 * 编辑
 */
function edit(id) {
	top.bsWindow(contextPath + "/supervise/supervise/supperson_add.jsp?id="+id, "修改",
			{
				width : "750",
				height : "300",
				buttons : [ {
					name : "关闭",
					classStyle : "btn-alert-gray"
				} ,{
					name : "保存",
					classStyle : "btn-alert-blue"
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
 * 多删
 */
function deletes(){
//	debugger;
	var checkedItems = $('#datagrid').datagrid('getChecked');
	if (checkedItems == ""){
		$.MsgBox.Alert_auto("请至少选择一条数据");
	}else{
		var examines = [];
		var ids = [];
		$.each(checkedItems, function(index, item) {
			ids.push(item.id);
			examines.push(item.examine);
		});
		for(var i=0;i<examines.length;i++){
			if(examines[i]==1){
				$.MsgBox.Alert_auto("已审核的数据不能删除");
				return false;
			}
		}
		top.$.MsgBox.Confirm("提示","确定删除该数据？",function(){
			var json = tools.requestJsonRs("/SupPersonController/deletes.action",{ids:ids.join(",")});
			$('#datagrid').datagrid("reload");
			$('#datagrid').datagrid("clearSelections");
		});
	}
	
}

/* 超链接删除 */
function cdel(id) {
	top.$.MsgBox.Confirm("提示","确定删除该数据？",function(){
		var json = tools.requestJsonRs(
				"/SupPersonController/delete.action", {
					id : id
				});
		$('#datagrid').datagrid("reload");
		$('#datagrid').datagrid("clearSelections");
	});
}

/*
 * * 查询
 */
function queryS() {	
	var params = {
		name : $("#name").val(),
		personId : $("#personId").val(),
	};
	$('#datagrid').datagrid("reload", params);
	$('#datagrid').datagrid("clearSelections");
}

/*
 * 密码重置
 */
function password(){
	var checkedItems = $('#datagrid').datagrid('getChecked');
	if(checkedItems.length == 1){
		var username = checkedItems[0].username;
		if(username =="" || username == null){
			$.MsgBox.Alert_auto("请先分配账号");
		}else{
			top.$.MsgBox.Confirm("提示","重置后密码为：zfjd123456，确定重置？",function(){
				var json = tools.requestJsonRs("/SupPersonController/resetPassword.action",{id:checkedItems[0].id});
				$('#datagrid').datagrid("reload");
				$('#datagrid').datagrid("clearSelections");
				$.MsgBox.Alert_auto("重置密码成功，新密码为：zfjd123456");
			});
		}
	}else{
		$.MsgBox.Alert_auto("请选择一条数据");
	}
}
/*
 * 回收账号
 */
function reUser(){
	var checkedItems = $('#datagrid').datagrid('getChecked');
	if(checkedItems.length == 1){
		var username = checkedItems[0].username;
		if(username =="" || username == null){
			$.MsgBox.Alert_auto("请先分配账号");
		}else{
			top.$.MsgBox.Confirm("提示","确定回收该账号？",function(){
				var json = tools.requestJsonRs("/SupPersonController/resetUser.action",{id:checkedItems[0].id});
				$('#datagrid').datagrid("reload");
				$('#datagrid').datagrid("clearSelections");
			});
		}
	}else{
		$.MsgBox.Alert_auto("请选择一条数据");
	}
}
/*
 *分配账号 
 */
function user(){
	var checkedItems = $('#datagrid').datagrid('getChecked');
	if(checkedItems.length == 1){
		var examine = checkedItems[0].examine;
		if(examine == 1){
			var username = checkedItems[0].username;
			if(username =="" || username == null){
				var id = checkedItems[0].id;
				top.bsWindow(contextPath + "/supervise/supervise/supperson_login.jsp?id="+id, "分配账号", {
					width : "420",
					height : "130",
					buttons : [ {
						name : "关闭",
						classStyle : "btn-alert-gray"
					} ,{
						name : "保存",
						classStyle : "btn-alert-blue"
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
			}else{
				$.MsgBox.Alert_auto("该用户已经分配账号");
			}
		}else{
			$.MsgBox.Alert_auto("请先进行审核");
		}
	}else{
		$.MsgBox.Alert_auto("请选择一条数据");
	}
}

/*
 * 查看
 */
function look(id) {
	top.bsWindow(contextPath + "/supervise/supervise/supperson_search_look.jsp?id="+id, "查看",
			{
				width : "700",
				height : "360",
				buttons : [{
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
