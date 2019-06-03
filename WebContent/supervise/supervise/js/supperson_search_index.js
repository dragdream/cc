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
		url : contextPath + '/SupPersonController/findSearchListBypage.action',
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
//				{ field : 'id', title : '', checkbox : true},
				{field:'ID',title:'序号',align:'center',
                    formatter:function(value,rowData,rowIndex){
                        return rowIndex+1;
                    }
                },
				{ field : 'name', title : '姓名',align:'left' ,width:'8%', halign: 'center' ,
                	formatter:function(e,rowData){
                		var optsStr = "<a href=\"#\" title=" + e + " onclick=\"edit('" + rowData.id + "')\">" + e + "</a>";
						return optsStr;
                	}},
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
//				{ field : 'examine', title : '审核状态', align:'center' ,width:'8%', halign: 'center' , formatter : 
//					function(data, rowData) {
//					if (rowData.examine == 0) {
//						return "未审核";
//					}else {
//						return "已审核";
//					}
//				}},
				{ field : '___', title : '操作',align:'center' ,width:'6%', halign: 'center', formatter : function(e, rowData) {
						var optsStr = "<span title='查看'><a href='javaScript:void(0);' onclick='edit(\"" + rowData.id + "\")'><i class='fa fa-eye'></i></a></span>";
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
		width : "670",
		height : "300",
		buttons : [ {
			name : "保存",
			style : "margin-right:44px;float: left;",
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

/** 审核
 * 0 改 1
*      */
function  auditing(){
	var obj = $('#datagrid').datagrid("getSelections");
	if(obj == ""){
		$.MsgBox.Alert_auto("请先选择一条数据");
	}else{
		if (obj[0].examine==1){
			$.MsgBox.Alert_auto("您已审核该数据");
		}else{
			if (window.confirm("是否确认审核")) {
				var json = tools.requestJsonRs("/SupPersonController/examine.action",{id:obj[0].id});
				$('#datagrid').datagrid("reload");
				$('#datagrid').datagrid("clearSelections");
		}
		}
	}
	
	
}
	
/** 取消审核
 * 1改0
*      */
function  RemoveAuditing(){
	var obj = $('#datagrid').datagrid("getSelections");
	if (obj == ""){
		$.MsgBox.Alert_auto("请先选择一条数据");
	}else{
		if (obj[0].examine==0){
			$.MsgBox.Alert_auto("该数据已在取消审核状态");
		}else{
			if (window.confirm("是否取消审核")) {
				var json = tools.requestJsonRs("/SupPersonController/examine.action",{id:obj[0].id});
				$('#datagrid').datagrid("reload");
				$('#datagrid').datagrid("clearSelections");
		}
		}
	}
	

}

/*
 * 编辑
 */
function edit(id) {
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

/*
 * 多删
 */
function deletes(){
//	debugger;
	var checkedItems = $('#datagrid').datagrid('getChecked');
	if (checkedItems == ""){
		$.MsgBox.Alert_auto("请至少选择一条数据");
	}else{
		var ids = [];
		$.each(checkedItems, function(index, item) {
			ids.push(item.id);
		});
		if (window.confirm("是否确认删除该数据")) {
			var json = tools.requestJsonRs("/SupPersonController/deletes.action",{ids:ids.join(",")});
			$('#datagrid').datagrid("reload");
			$('#datagrid').datagrid("clearSelections");
		}
	}
	
}

/* 超链接删除 */
function cdel(id) {
	if (window.confirm("是否确认删除该数据")) {
		var json = tools.requestJsonRs(
				"/SupPersonController/delete.action", {
					id : id
				});
		$('#datagrid').datagrid("reload");
		$('#datagrid').datagrid("clearSelections");
	}
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


