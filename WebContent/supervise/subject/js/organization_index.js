	/*初始化*/
var datagrid;
function doInit() {
	entrustNature()
//	getSysCodeByParentCodeNo("SYSTEM_SUBJECT_NATURE","entrustNature");
	listDatagrid();
	//回车搜索
	document.onkeyup = function (e) {
        var code = e.charCode || e.keyCode;  //取出按键信息中的按键代码(大部分浏览器通过keyCode属性获取按键代码，但少部分浏览器使用的却是charCode)
        if (code == 13) {
        	queryS();
        }
    }
}

function listDatagrid(){
	datagrid = $('#datagrid') .datagrid( {
		url : contextPath + '/subjectCtrl/findListBypageOrg.action',
		queryParams : { gender : -1,isDepute:1 },
		pagination : true,
		singleSelect : true,
		striped: true,
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
				{ field : 'subName', title : '受委托组织名称',width : '20%',align:'left' , halign: 'center', formatter: 
					function(value,rowData,rowIndex){
                    var optsStr = "<a href=\"#\" title = " + value + " onclick=\"look('" + rowData.id + "')\">" + value + "</a>";
                    return optsStr;
	            },},
	            { field : 'code', title : '统一社会信用代码', width : '16%' ,align:'center' , halign: 'center', formatter: 
					function(value,rowData,rowIndex){
					if(rowData.code == null){
						return "";
					}else{
						var optsStr = "<span title=" + rowData.code + ">" + rowData.code + "</span>";
	                    return optsStr;
					}
	            },},
				{ field : 'entrustNature', title : '受委托组织性质', width : 50 ,align:'center' , halign: 'center', formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
						return optsStr;
					}
		            },},
	            { field : 'parentName', title : '委托主体', width : 50,align:'left' , halign: 'center', formatter: 
					function(e, rowData) {
	                var lins = "<span title=" + rowData.parentName + ">" + rowData.parentName + "</span>"
	                return lins;
	            }, },
				{ field : 'orgMode', title : '委托方式', width : 50 ,align:'center' , halign: 'center', formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
						return optsStr;
					}
		            },},
				{ field : 'createName', title : '创建人', width : 50 ,align:'center' , halign: 'center', formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
						return optsStr;
					}
		            },},
				{ field : 'createTimeStr', title : '创建时间', width : 50 ,align:'center' , halign: 'center', formatter: 
					function(value,rowData,rowIndex){
					if(value == null){
						return "";
					}else{
						var optsStr = "<span title=" + value + ">" + value + "</span>";
						return optsStr;
					}
		            },},
				{ field : '___', title : '操作', width : '6%',align:'center', halign: 'center',formatter : function(e, rowData) {
                    if (rowData.isExamine == 1) {
                        var optsStr = "<span title='查看'><a href='javaScript:void(0);' onclick='look(\"" + rowData.id + "\")'><i class='fa fa-eye'></i></a></span>";
                    }else{
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

// 获取选中的行内容 删除
function del() {
	var obj = $('#datagrid').datagrid("getSelections");
	///departmentInfoController/delete.action
	top.$.MsgBox.Confirm("提示","确定删除该数据？",function(){
		var json = tools.requestJsonRs("/departmentInfoController/delete.action", {id : obj[0].id});
		$('#datagrid').datagrid("reload");
		$('#datagrid').datagrid("clearSelections");
	});
}

/*
 * 新增
 */
function add() {

	top.bsWindow(contextPath + "/supervise/subject/organization_add.jsp", "新增", {
		width : "800",
		height : "340",
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
 * 修改
 */
function edit(id) {
	top.bsWindow(contextPath + "/supervise/subject/organization_add.jsp?id="+id, "修改",
			{
				width : "800",
				height : "340",
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
function dels() {
	var checkedItems = $('#datagrid').datagrid('getChecked');
	if(checkedItems == ""){
		$.MsgBox.Alert_auto("请至少选择一条数据");
	}else{
		var ids = [];
		$.each(checkedItems, function(index, item) {
			ids.push(item.id);
		});
		top.$.MsgBox.Confirm("提示","确定删除该数据？",function(){
			var json = tools.requestJsonRs("/subjectCtrl/deletes.action",{ids:ids.join(",")});
			$('#datagrid').datagrid("reload");
			$('#datagrid').datagrid("clearSelections");
		});
	}
}

/* 超链接删除 */
function cdel(id) {
	top.$.MsgBox.Confirm("提示","确定删除该数据？",function(){
		var json = tools.requestJsonRs(
				"/subjectCtrl/delete.action", {
					id : id
				});
		$('#datagrid').datagrid("reload");
		$('#datagrid').datagrid("clearSelections");
	});
}

/** 审核
 * 0 改 1
*      */
function  auditing(){
	var obj = $('#datagrid').datagrid("getSelections");
	if(obj.length == 1){
		if (obj[0].examineStr==1){
			$.MsgBox.Alert_auto("您已审核该数据");
		}else{
			top.$.MsgBox.Confirm("提示","确认审核？",function(){
				var json = tools.requestJsonRs("/subjectCtrl/examine.action",{id:obj[0].id});
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
	if(obj.length == 1){
		if (obj[0].examineStr==0){
			$.MsgBox.Alert_auto("该数据已在取消审核状态");
		}else{
			top.$.MsgBox.Confirm("提示","是否取消审核？",function(){
				var json = tools.requestJsonRs("/subjectCtrl/examine.action",{id:obj[0].id});
				$('#datagrid').datagrid("reload");
				$('#datagrid').datagrid("clearSelections");
			});
		}
	}else{
		$.MsgBox.Alert_auto("请选择一条数据");
	}
}


/*
 * * 查询
 */
function queryS() {
	var params = {
		subName : $("#subName").val(),
		code : $("#code").val(),
		entrustNature : $("#entrustNature").val(),
	};
	if(params.entrustNature == -1){
		params.entrustNature = "";
	}
	$('#datagrid').datagrid("reload", params);
	$('#datagrid').datagrid("clearSelections");
}

//性质
function entrustNature(){
	var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "ORGANIZATION_NATURE"});
    if(json.rtState) {
        json.rtData.unshift({codeNo: -1, codeName: "全部"});
        $('#entrustNature').combobox({
            data: json.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
            panelHeight:'auto',
            onLoadSuccess:function(){
                $('#entrustNature').combobox('setValue',-1);
            },
        });
    }
}
/*
 * 查看
 */
function look(id) {
    top.bsWindow(contextPath + "/supervise/subject/organization_search_look.jsp?id="+id, "查看",
            {
                width : "800",
                height : "420",
                buttons : [ {
                    name : "关闭",
                    classStyle : "btn-alert-gray"
                } ],
                submit : function(v, h) {
                    var cw = h[0].contentWindow;
                    if (v == "关闭") {
                        return true;
                    }
                }
            });

}