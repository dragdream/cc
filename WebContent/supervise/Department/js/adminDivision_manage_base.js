/**
 * 行政区划管理填报页面管理方法
 */

function doInit() {
    initDatagrid();
//    enterKeydownForSearch();
	//获取回车事件
    document.onkeyup = function (e) {
        var code = e.charCode || e.keyCode;  //取出按键信息中的按键代码(大部分浏览器通过keyCode属性获取按键代码，但少部分浏览器使用的却是charCode)
        if (code == 13) {
        	adminDivisionSearch();
        }
    }
}

function initDatagrid(params) {
    datagrid = $('#adminDivisionManage_base_table').datagrid(
            {
                url : contextPath + '/adminDivisionManageCtrl/findListBypage.action',
                queryParams : params,
                pagination : true,
                singleSelect : true,
                striped : true,
                pageSize : 20,
                pageList : [ 10, 20, 50, 100 ],
                view : window.EASYUI_DATAGRID_NONE_DATA_TIP,
                toolbar : '#toolbar',
                // 工具条对象
                checkbox : true,
                border : false,
                /* idField:'formId',//主键列 */
                fitColumns : true,
                // 列是否进行自动宽度适应
                nowrap : true,
                columns : [ [
                        {
                            field : 'id',
                            checkbox : true,
                            align : 'center',
                            width : 10
                        },
        				{field:'ID',title:'序号',align:'center',
                            formatter:function(value,rowData,rowIndex){
                                return rowIndex+1;
                            }
                        },
                        {
                            field : 'adminDivisionName',
                            title : '行政区划名称',
                            halign : 'center',
                            align : 'left',
                            width : 60
                        },
                        {
                            field : 'adminDivisionCode',
                            title : '行政区划代码',
                            halign : 'center',
                            align : 'center',
                            width : 30
                        },
                        {
                            field : 'levelCodeStr',
                            title : '层级',
                            halign : 'center',
                            align : 'center',
                            width : 30

                        },
                        {
                            field : 'provincialName',
                            title : '所属省(直辖市)',
                            halign : 'center',
                            align : 'left',
                            width : 30
                        },
                        {
                            field : 'cityName',
                            title : '所属市(州)',
                            halign : 'center',
                            align : 'left',
                            width : 30
                        },
                        {
                            field : 'ditrictName',
                            title : '所属区(县)',
                            halign : 'center',
                            align : 'left',
                            width : 30
                        },
                        {
                            field : 'isExamine',
                            title : '审核状态',
                            halign : 'center',
                            align : 'center',
                            width : 30,
                            formatter : function(e, rowData, value) {
                                if (rowData.isExamine != null) {
                                    if (rowData.isExamine == 1) {
                                        return "已审核";
                                    } else {
                                        return "未审核";
                                    }
                                } else {
                                    return "";
                                }
                            }
                        },
                        {
                            field : 'userAccount',
                            title : '账号',
                            halign : 'center',
                            align : 'left',
                            width : 50
                        },
                        {
                            field : '__',
                            title : '操作',
                            halign : 'center',
                            align : 'center',
                            width : 40,
                            formatter : function(e, rowData) {
                                var optsStr = "";
                                if (rowData.isExamine != 1) {
                                    optsStr = "<span title='修改'><a href='javaScript:void(0);' onclick='openEditInput(\"" + rowData.id
                                            + "\")'><i class='fa fa-pencil common-yellow'></i></a></span>&nbsp;&nbsp;&nbsp;&nbsp;";
                                    optsStr = optsStr + "<span title='删除'><a href='javaScript:void(0);' onclick='deleteAdminDivision(\"" + rowData.id
                                            + "\")'><i class='fa fa-trash-o common-red'></i></a></span>";
                                }
                                return optsStr;
                            }
                        } ] ]
            });
}

/*
 * 查询
 */
function adminDivisionSearch() {
    var params = tools.formToJson($('#adminDivision_search_form'));
    initDatagrid(params);
}

/*
 * 新增
 */
function add() {
    url = contextPath + "/adminDivisionManageCtrl/adminDivisionEditInput.action";
    top.bsWindow(url, "新增", {
        width : "740",
        height : "150",
        buttons : [ {
            name : "关闭",
            classStyle : "btn-alert-gray"
        }, {
            name : "保存",
            classStyle : "btn-alert-blue"
        } ],
        submit : function(v, h) {
            var cw = h[0].contentWindow;
            if (v == "保存") {
                var status = cw.save();
                if (status == true) {
                    $("#adminDivisionManage_base_table").datagrid("reload");
                    $('#adminDivisionManage_base_table').datagrid("clearSelections");
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
function openEditInput(id) {
    var params = {
        id : id
    };
    url = contextPath + "/adminDivisionManageCtrl/adminDivisionEditInput.action?" + $.param(params);
    top.bsWindow(url, "修改", {
        width : "740",
        height : "150",
        buttons : [ {
            name : "关闭",
            classStyle : "btn-alert-gray"
        }, {
            name : "保存",
            classStyle : "btn-alert-blue"
        } ],
        submit : function(v, h) {
            var cw = h[0].contentWindow;
            if (v == "保存") {
                var status = cw.save();
                if (status == true) {
                    $("#adminDivisionManage_base_table").datagrid("reload");
                    $('#adminDivisionManage_base_table').datagrid("clearSelections");
                    return true;
                }
            } else if (v == "关闭") {
                return true;
            }
        }
    });
}
/*
 * 删除
 */
function deleteAdminDivision(id) {
    top.$.MsgBox.Confirm("提示", "确定删除？", function() {
        var json = tools.requestJsonRs("/adminDivisionManageCtrl/deleteAdminDivisionInfo.action", {
            id : id
        });
        $('#adminDivisionManage_base_table').datagrid("reload");
        $('#adminDivisionManage_base_table').datagrid("clearSelections");
        $.MsgBox.Alert_auto("数据已删除");
    });
}

/*
 * 审核
 */
function examine() {
    var checkedItems = $('#adminDivisionManage_base_table').datagrid('getChecked');
    if (checkedItems.length == 1) {
        var isExamine = checkedItems[0].isExamine;
        if (isExamine != null && isExamine == 0) {
            var params = {
                id : checkedItems[0].id,
                adminDivisionCode : checkedItems[0].adminDivisionCode
            };
            top.$.MsgBox.Confirm("提示", "审核完成后该数据将不可修改，确定审核？", function() {
                var json = tools.requestJsonRs("/adminDivisionManageCtrl/examinePass.action", {
                    id : checkedItems[0].id
                });
                $('#adminDivisionManage_base_table').datagrid("reload");
                $('#adminDivisionManage_base_table').datagrid("clearSelections");
                $.MsgBox.Alert_auto("已审核！");
            });
        } else {
            $.MsgBox.Alert_auto("该区划信息不是待审核的数据！");
        }
    } else {
        $.MsgBox.Alert_auto("请选择一条数据！");
    }
}

/*
 * 分配账号
 */
function account() {
    var checkedItems = $('#adminDivisionManage_base_table').datagrid('getChecked');
    if (checkedItems.length == 1) {
        var userAccount = checkedItems[0].userAccount;
        var isExamine = checkedItems[0].isExamine;
        if(isExamine != null && isExamine != 1){
            $.MsgBox.Alert_auto("请先确认该区划信息是否通过审核！");
            return false;
        }
        if (userAccount == "" || userAccount == null) {
            var params = {
                id : checkedItems[0].id,
                adminDivisionCode : checkedItems[0].adminDivisionCode
            }
            top.bsWindow(contextPath + "/adminDivisionManageCtrl/openAccountInput.action?" + $.param(params), "分配账号", {
                width : "450",
                height : "140",
                buttons : [ {
                    name : "关闭",
                    classStyle : "btn-alert-gray"
                }, {
                    name : "保存",
                    classStyle : "btn-alert-blue"
                } ],
                submit : function(v, h) {
                    var cw = h[0].contentWindow;
                    if (v == "保存") {
                        var status = cw.save();
                        if (status == true) {
                            $("#adminDivisionManage_base_table").datagrid("reload");
                            $('#adminDivisionManage_base_table').datagrid("clearSelections");
                            return true;
                        }
                    } else if (v == "关闭") {
                        return true;
                    }
                }
            });
        } else {
            $.MsgBox.Alert_auto("该用户已经分配账号！");
        }
    } else {
        $.MsgBox.Alert_auto("请选择一条数据！");
    }
}

/*
 * 重置密码
 */
function reSetAccount() {
    var checkedItems = $('#adminDivisionManage_base_table').datagrid('getChecked');
    if (checkedItems.length == 1) {
        var userAccount = checkedItems[0].userAccount;
        var isExamine = checkedItems[0].isExamine;
        if (isExamine != null && isExamine != 1){
            $.MsgBox.Alert_auto("请先确认该区划信息是否通过审核！");
            return false;
        }
        if (userAccount != null && userAccount != "") {
            var params = {
                id : checkedItems[0].id,
                adminDivisionCode : checkedItems[0].adminDivisionCode
            }
            top.$.MsgBox.Confirm("提示", "重置后的密码为:zfjd123456,重置后请提醒使用者及时修改密码。确定重置密码？", function() {
                var json = tools.requestJsonRs("/adminDivisionManageCtrl/reSetPassword.action", {
                    id : checkedItems[0].id
                });
                $('#adminDivisionManage_base_table').datagrid("reload");
                $('#adminDivisionManage_base_table').datagrid("clearSelections");
                $.MsgBox.Alert_auto("重置密码成功！新密码为：zfjd123456");
            });
        } else {
            $.MsgBox.Alert_auto("该用户尚未分配账号！");
        }
    } else {
        $.MsgBox.Alert_auto("请选择一条数据！");
    }
}

/*
 * 回收账号
 */
function releaseAccount() {
    var checkedItems = $('#adminDivisionManage_base_table').datagrid('getChecked');
    if (checkedItems.length == 1) {
        var userAccount = checkedItems[0].userAccount;
        var isExamine = checkedItems[0].isExamine;
        if (isExamine != null && isExamine != 1){
            $.MsgBox.Alert_auto("请先确认该区划信息是否通过审核！");
            return false;
        }
        if (userAccount != null && userAccount != "") {
            var params = {
                id : checkedItems[0].id,
                adminDivisionCode : checkedItems[0].adminDivisionCode
            }
            top.$.MsgBox.Confirm("提示", "回收后该账号不可用，且不可恢复！确认回收已分配的分级管理员帐号？", function() {
                var json = tools.requestJsonRs("/adminDivisionManageCtrl/releaseAccount.action", {
                    id : checkedItems[0].id
                });
                $('#adminDivisionManage_base_table').datagrid("reload");
                $('#adminDivisionManage_base_table').datagrid("clearSelections");
                $.MsgBox.Alert_auto("帐号已回收！");
            });
        } else {
            $.MsgBox.Alert_auto("该用户尚未分配账号！");
        }
    } else {
        $.MsgBox.Alert_auto("请选择一条数据！");
    }
}

function enterKeydownForSearch() {
    $('#adminDivision_search_div').bind('keypress', function(event) {
        if (event.keyCode == "13") {
            var params = tools.formToJson($('#adminDivision_search_form'));
            initDatagrid(params);
        }
    });
}