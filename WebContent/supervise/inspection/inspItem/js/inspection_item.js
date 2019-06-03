/*初始化*/

var datagrid;
var ctrlType = '';
var loginDeptId = '';
var loginSubId = '';
var orgSys = '';
var itemName = '';

function doInit() {
    initPersonPermision();
    doInitEnter();
}

// 人员权限判定方法
function initPersonPermision() {
    // 获取权限组
    var rtJson = tools
            .requestJsonRs("/commonCtrl/getMenuGroupNames.action", '');
    if (rtJson.rtState) {
        // 数据初始化
        var menuGroupsName = rtJson.rtData;
        var namesBuff = [];
        // 拆分成数组
        if (menuGroupsName != null) {
            namesBuff = menuGroupsName.split(',');
        }
        // 是否主管理权限判断标识
        var isMain = false;
        // 是否子管理权限判断标识
        var isSub = false;
        // 遍历数组，判断用户权限
        for (var i = 0; i < namesBuff.length; i++) {
            if (namesBuff[i] == '执法检查主管理') {
                isMain = true;
            }
            if (namesBuff[i] == '执法检查子管理') {
                isSub = true;
            }
        }
        // 权限判定完成，设置权限标识代码，作为控制参数，用于数据控制
        // 主、子权限都有时，按主管理权限判断
        if (isMain) {
            ctrlType = '10';
        } else {
            if (isSub) {
                ctrlType = '20';
            }
        }
        if (isMain || isSub) {
            // 在具备主管理权限或子管理权限时，进行参数初始化
            initBaseParams();
        }
    }
}

function initBaseParams() {
    var result = tools.requestJsonRs("/commonCtrl/getRelationAndOrgSys.action",
            params);
    if (result.rtState) {
        var relation = result.relation;
        loginDeptId = relation.businessDeptId;
        loginSubId = relation.businessSubjectId;
        orgSys = result.orgSys;
        var params = {
            loginDeptId : loginDeptId,
            loginSubId : loginSubId,
            ctrlType : ctrlType,
            orgSys : orgSys
        };
        initDatagrid(params);
    }
}

function initDatagrid(params) {
    datagrid = $('#datagrid').datagrid(
                    {
                        url : contextPath
                                + '/inspectitemCtrl/caseListByPage.action',
                        queryParams : params,
                        pagination : true,
                        singleSelect : false,
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
                                    width : 10,
                                    checkbox : true
                                },
                                {
                                    field : 'Id',
                                    title : '序号',
                                    align : 'center',
                                    halign : 'center',
                                    formatter : function(value,rowData,rowIndex){
                                        return rowIndex+1
                                    }
                                },
                                {
                                    field : 'itemName',
                                    title : '检查项名称',
                                    halign : 'center',
                                    width : 20,
                                    formatter : function(e, rowData) {
                                        if(rowData.itemName!=null &&rowData.itemName!=''){
                                            var list = "<label class='common-overflow-hidden common-table-td-full-width' title='" + rowData.itemName + "'><a href='#' onclick='lookInspItem(\"" + rowData.id + "\")'>" + rowData.itemName + "</a></label>";
                                            return list;
                                        }
                                    }
                                },
                                {
                                    field : 'moduleName',
                                    title : '检查模块',
                                    halign : 'center',
                                    width : 20,
                                    formatter :function(value,rowData,rowIndex){
                                        if(value!=null){
                                            var text = "<span class='common-overflow-hidden common-table-td-full-width' title='"+value+"'>"+value+"</span>";
                                            return text;
                                        }
                                    },
                                },
                                {
                                    field : 'orgSysName',
                                    title : '所属领域',
                                    align : 'center',
                                    halign : 'center',
                                    width : 15
                                },
                                {
                                    field : 'createOrganizationName',
                                    title : '创建单位',
                                    width : 20,
                                    halign : 'center',
                                    formatter :function(value,rowData,rowIndex){
                                        if(value!=null){
                                            var text = "<span class='common-overflow-hidden common-table-td-full-width' title='"+value+"'>"+value+"</span>";
                                            return text;
                                        }
                                    },
                                },
                                {
                                    field : 'createTimeStr',
                                    title : '创建时间',
                                    width : 15,
                                    align : 'center',
                                    halign : 'center'
                                },
                                {
                                    field : '___',
                                    title : '操作',
                                    align : 'center',
                                    width : 10,
                                    formatter : function(e, rowData) {
                                        var optsStr = ""
                                        if (rowData.createType == ctrlType) {
                                            var optsStr = "<span title='编辑'><a href=\"#\" style=\"margin-left:5px;\" onclick=\"edit('"
                                                    + rowData.id
                                                    + "')\"><i class='fa fa-pencil common-yellow'></i></a></span>&nbsp;&nbsp;&nbsp;&nbsp;<span title='删除'><a href=\"#\" onclick=\"inspItemDel('"
                                                    + rowData.id
                                                    + "')\"><i class='fa fa-trash-o common-red'></i></a></span>";
                                        }else {
                                            var optsStr = "<span title='查看'><a href=\"#\" style=\"margin-left:5px;\" onclick=\"lookInspItem('"
                                                + rowData.id
                                                + "')\"><i class='fa fa-eye'></i></a></span>";
                                        }

                                        return optsStr;
                                    }
                                }, ] ]
                    });
}
//回车查询
function doInitEnter() {
    document.onkeyup = function (e) {
        var code = e.charCode || e.keyCode;  //取出按键信息中的按键代码(大部分浏览器通过keyCode属性获取按键代码，但少部分浏览器使用的却是charCode)
        if (code == 13) {
            searchItem();
        }
    }
}
/**
 * 条件查新
 */
function searchItem() {
    var params = {
        loginDeptId : loginDeptId,
        loginSubId : loginSubId,
        ctrlType : ctrlType,
        orgSys : orgSys
    };
    params.itemName = $('#itemName').val();
    initDatagrid(params);
}
// /*
// * 跳转新增、编辑
// */
function doOpenInputPage(id) {
    var params = {
        id : id,
        loginDeptId : loginDeptId,
        loginSubId : loginSubId,
        ctrlType : ctrlType,
        orgSys : orgSys
    }
    var url = contextPath
            + "/supervise/inspection/inspItem/inspection_input.jsp?id=" + id, url = url
            + "?" + $.param(params);
    top.bsWindow(url, "检查项编辑", {
        width : "600",
        height : "300",
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
                var status = cw.saveItemInfo();
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
/**
 * 增加
 */
function add() {
    var params = {
        id : 0,
        loginDeptId : loginDeptId,
        loginSubId : loginSubId,
        ctrlType : ctrlType,
        orgSys : orgSys
    };
    var url = contextPath
            + "/supervise/inspection/inspItem/inspection_item_input.jsp";
    url = url + "?" + $.param(params);
    top.bsWindow(url, "新增", {
        width : "600",
        height : "300",
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
                var status = cw.saveItemInfo();
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
//
// /*
// * 编辑
// */
function edit(id) {
    var params = {
        id : id,
        loginDeptId : loginDeptId,
        loginSubId : loginSubId,
        ctrlType : ctrlType,
        orgSys : orgSys
    }
    var url = contextPath
            + "/supervise/inspection/inspItem/inspection_item_input.jsp";
    url = url + "?" + $.param(params);
    top.bsWindow(url, "修改", {
        width : "600",
        height : "300",
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
                var status = cw.saveItemInfo();
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
/**
 * 查看检查项详情
 */
function lookInspItem(id){
    var params = {
            id : id
        }
        var url = contextPath
                + "/supervise/inspection/inspItem/inspection_item_detail.jsp";
        url = url + "?" + $.param(params);
        top.bsWindow(url, "查看", {
            width : "600",
            height : "300",
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

// 获取选中的行内容 多删除
function inspItemDels() {
    var rows = $('#datagrid').datagrid('getSelections');
    if (rows.length == 0) {
        alert("请选择要删除的检查项！");
        return;
    }
    var ids = [];
    for ( var index in rows) {
        ids.push(rows[index].id);
    }
    var params = {
        id : ids.join(","),
        orgSys : orgSys,
        loginDeptId : loginDeptId,
        loginSubId : loginSubId,
        ctrlType : ctrlType,
        isDelete : 1
    };

    if (window.confirm("是否确认删除该数据")) {
        var isId = tools.requestJsonRs("/inspectitemCtrl/idsCtrl.action",
                params);
        if (isId.rtState) {
            var json = tools.requestJsonRs(
                    "/inspectitemCtrl/inspItemDel.action", params);
            if (json.rtState) {
                $.MsgBox.Alert_auto("删除成功");
                $('#datagrid').datagrid("reload");
            } else {
                $.MsgBox.Alert_auto("删除失败");
            }
        } else {
            $.MsgBox.Alert_auto("权限不足，删除失败");
        }

    }
}

/* 超链接删除 */
function inspItemDel(id) {
    if (window.confirm("是否确认删除该数据")) {
        var json = tools.requestJsonRs("/inspectitemCtrl/inspItemDel.action", {
            id : id
        });
        if (json.rtState) {
            $.MsgBox.Alert_auto("删除成功");
            $('#datagrid').datagrid("reload");
        } else {
            $.MsgBox.Alert_auto("删除失败");
        }
    }
}

/*
 * 多删
 */
// function deletes() {
// var checkedItems = $('#datagrid').datagrid('getChecked');
// var ids = [];
// $.each(checkedItems, function(index, item) {
// ids.push(item.id);
// });
// if (window.confirm("是否确认删除该数据")) {
// var json = tools.requestJsonRs("/inspectionCtrl/deletes.action", {
// ids : ids.join(",")
// });
// $('#datagrid').datagrid("reload");
// }
// }
//
// function deletes2() {
//
// var checkedItems = $('#datagrid').datagrid('getChecked');
// var ids = [];
// $.each(checkedItems, function(index, item) {
// ids.push(item.id);
// // alert(ids)
// });
//
// // $.ajax({
// // type:'POST',
// // data:JSON.stringify(ids),
// // contentType :'application/json',
// // dataType:'json',
// // url :"/inspectionCtrl/deletes.action",
// // // url :"<%=contextPath %>/inspectionCtrl/deletes.action",
// // success :function(data) {
// // alert("OK");
// // },
// // error :function(e) {
// // alert("error");
// // }
// // });
// }
