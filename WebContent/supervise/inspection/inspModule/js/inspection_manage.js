var datagrid;
var ctrlType = '';
var loginDeptId = '';
var loginSubId = '';
var orgSys = '';

function doInit() {
    initPersonPermision();
    doInitEnter();
}
//回车查询
function doInitEnter() {
    document.onkeyup = function (e) {
        var code = e.charCode || e.keyCode;  //取出按键信息中的按键代码(大部分浏览器通过keyCode属性获取按键代码，但少部分浏览器使用的却是charCode)
        if (code == 13) {
            doInit();
        }
    }
}
function initPersonPermision() {
    var rtJson = tools.requestJsonRs("/commonCtrl/getMenuGroupNames.action", '');
    if (rtJson.rtState) {
        var menuGroupsName = rtJson.rtData;
        var namesBuff = [];
        if (menuGroupsName != null) {
            namesBuff = menuGroupsName.split(',');
        }
        var isMain = false;
        var isSub = false;
        for (var i = 0; i < namesBuff.length; i++) {
            if (namesBuff[i] == '执法检查主管理') {
                isMain = true;
            }
            if (namesBuff[i] == '执法检查子管理') {
                isSub = true;
            }
        }
        if (isMain) {
            $('#button_div').show();
            ctrlType = '10';
        } else {
            $('#button_div').hide();
            if (isSub) {
                ctrlType = '20';
            }
        }
        if (isMain || isSub) {
            initBaseParams();
        }
    }
}

function initBaseParams() {
    var result = tools.requestJsonRs("/commonCtrl/getRelationAndOrgSys.action",params);
    
    if (result.rtState) {
        var relation = result.relation;
        loginDeptId = relation.businessDeptId;
        loginSubId = relation.businessSubjectId;
        orgSys = result.orgSys;
        var params = {
            moduleName : $.trim($("#moduleName").val()),
            gender : -1,
            loginDeptId : loginDeptId,
            loginSubId : loginSubId,
            ctrlType : ctrlType,
            orgSys : orgSys
        };
        initDatagrid(params);
    }
}
/*初始化*/
function initDatagrid(params) {
//    debugger;
    datagrid = $('#datagrid').datagrid(
                    {
                        url : contextPath + '/inspectionCtrl/listByPage.action',
                        queryParams: params,
                        /*queryParams : {
                            gender : -1,
                        },*/
                        pagination : true,
                        singleSelect : true,
                        view : window.EASYUI_DATAGRID_NONE_DATA_TIP,
                        toolbar : '#toolbar',// 工具条对象
                        border : false,
                        rownumbers : false,
                        pageSize : 20,
                        pageList : [ 10, 20, 50, 100 ],
                        fit : true,
                        idField : 'id',// 主键列
                        fitColumns : true,// 列是否进行自动宽度适应

                        columns : [ [
                                {
                                    field : 'id',
                                    title : '--',
                                    checkbox : true
                                },
                                {
                                    field : 'ID',
                                    title : '序号',
                                    align:'center',
                                    formatter:function(value,rowData,rowIndex){
                                        return rowIndex+1;
                                    }
                                },
                                {
                                    field : 'moduleName',
                                    title : '模块名称',
                                    width : 60,
                                    halign : 'center',
                                    formatter :function(value,rowData,rowIndex){
                                        if(value!=null){
                                            var text = "<label class='common-overflow-hidden common-table-td-full-width'><span title='"+value+"'>"+value+"</span></label>";
                                            return text;
                                        }
                                    },
                                },
                                {
                                    field : 'orgSys',
                                    title : '所属领域',
                                    width : 30,
                                    align : 'center',
                                    halign : 'center'
                                },
                                {
                                    field : 'organizationName',
                                    title : '创建单位',
                                    width : 50,
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
                                    width : 30,
                                    align : 'center',
                                    halign : 'center'
                                },
                                {
                                    field : 'operation',
                                    title : '操作',
                                    width : 20,
                                    halign : 'center',
                                    align : 'center',
                                    formatter : function(e, rowData) {
                                        var optsStr = "";
                                        if(ctrlType == '10'){
                                            optsStr = "<span title='编辑'><a href=\"#\" onclick=\"edit('" + rowData.id + "','" + rowData.orgSys + "')\"><i class='fa fa-pencil common-yellow'></i></a></span>&nbsp;&nbsp;&nbsp;&nbsp;"
                                            + "<span title='删除'><a href=\"#\" onclick=\"cdel('" + rowData.id + "')\"><i class='fa fa-trash-o common-red'></i></a></span>";
                                        }
                                        return optsStr;
                                    }
                                }, ] ],
                        singleSelect : false,
                        selectOnCheck : true,
                        checkOnSelect : true,
                        onLoadSuccess : function(data, rowData) {
                            if(ctrlType == '20'){
                                $(this).datagrid('hideColumn','operation');
                            }else if(ctrlType == '10'){
                                $(this).datagrid('showColumn','operation');
                            }
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
    var result = tools.requestJsonRs("/commonCtrl/getRelationAndOrgSys.action",params);
    if (result.rtState) {
        var relation = result.relation;
        loginDeptId = relation.businessDeptId;
        loginSubId = relation.businessSubjectId;
        orgSys = result.orgSys;
        var params = {
            moduleName : $.trim($("#moduleName").val()),
            gender : -1,
            loginDeptId : loginDeptId,
            loginSubId : loginSubId,
            ctrlType : ctrlType,
            orgSys : orgSys
        };
    }
    $('#datagrid').datagrid("reload", params);
}

/*
 * 跳转新增
 */
function add() {
    top.bsWindow(contextPath + "/supervise/inspection/inspModule/inspection_add.jsp", "新增", {
        width : "500",
        height : "220",
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
function edit(id,orgSys) {
    top.bsWindow(
            contextPath + "/supervise/inspection/inspModule/inspection_edit.jsp?id=" + id+"&orgSys=" + orgSys,"修改", {
                width : "500",
                height : "220",
                buttons : [ {
                    name : "关闭",
                    classStyle : "btn-alert-gray"
                },{
                    name : "保存",
                    classStyle : "btn-alert-blue"
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


//function edit(id) {
//top.bsWindow(
//        contextPath + "/supervise/inspection/inspection_edit.jsp?id=" + id,
//        "编辑", {
//            width : "500",
//            height : "220",
//            buttons : [ {
//                name : "保存",
//                classStyle : "btn-alert-blue"
//            }, {
//                name : "关闭",
//                classStyle : "btn-alert-gray"
//            } ],
//            submit : function(v, h) {
//                var cw = h[0].contentWindow;
//                if (v == "保存") {
//                    var status = cw.save();
//                    if (status == true) {
//                        $("#datagrid").datagrid("reload");
//                        return true;
//                    }
//                } else if (v == "关闭") {
//                    return true;
//                }
//            }
//        });
//
//}
// 获取选中的行内容 删除
function del() {
    // debugger;

    var obj = $('#datagrid').datagrid("getSelections");

    if (window.confirm("是否确认删除该数据")) {
        var json = tools.requestJsonRs("/inspectionCtrl/delete.action", {
            id : obj[0].id
        });
        $('#datagrid').datagrid("reload");
    }
}

/* 超链接删除 */
function cdel(id) {
    if (window.confirm("删除检查模块将删除相关联的检查项，是否确认删除该数据")) {
        var json = tools.requestJsonRs("/inspectionCtrl/delete.action", {
            id : id
        });
        $.MsgBox.Alert_auto("删除成功");
        $('#datagrid').datagrid("reload");
    }
}

/*
 * 多删
 */
function deletes() {
    // debugger;
    var checkedItems = $('#datagrid').datagrid('getChecked');
    if($.isEmptyObject(checkedItems)) {
        alert("请至少选中一项要删除的数据!");
        return;
    }
    var ids = [];
    $.each(checkedItems, function(index, item) {
        ids.push(item.id);
    });
    if (window.confirm("删除检查模块将删除相关联的检查项，是否确认删除该数据")) {
        var json = tools.requestJsonRs("/inspectionCtrl/deletes.action", {
            ids : ids.join(",")
        });
        $.MsgBox.Alert_auto("删除成功");
        $('#datagrid').datagrid("reload");
    }
}

function deletes2() {

    var checkedItems = $('#datagrid').datagrid('getChecked');
    var ids = [];
    $.each(checkedItems, function(index, item) {
        ids.push(item.id);
    });
}
