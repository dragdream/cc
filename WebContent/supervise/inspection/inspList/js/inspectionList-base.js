/**
 * 
 */
var datagrid;
var ctrlType = '';
var loginDeptId = '';
var loginSubId = '';
var orgSys = '';
var listName = '';

function doInit() {
    initPersonPermision();
    doInitEnter();
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
            ctrlType = '10';
        } else {
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
    datagrid = $('#datagrid')
            .datagrid(
                    {
                        url : contextPath + '/inspListBaseCtrl/inspListByPage.action',
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
                                    field : 'listName',
                                    title : '模版名称',
                                    halign : 'center',
                                    width : 20,
                                    formatter: function(e, rowData) {
                                        var list = "<label class='common-overflow-hidden common-table-td-full-width' title='"+rowData.listName+"'><a href='#' onclick='listDetail(\"" + rowData.id + "\")'>" + rowData.listName + "</a></label>";
                                        return list;
                                    }
                                },
                                {
                                    field : 'applyHierarchyStr',
                                    title : '适用层级',
                                    halign : 'center',
                                    align : 'center',
                                    width : 10
                                },
                                {
                                    field : 'listClassifyStr',
                                    title : '检查分类',
                                    align : 'center',
                                    halign : 'center',
                                    width : 10
                                },
                                {
                                    field : 'orgSysName',
                                    title : '所属领域',
                                    align : 'center',
                                    halign : 'center',
                                    width : 10
                                },
                                {
                                    field : 'currentState',
                                    title : '状态',
                                    align : 'center',
                                    halign : 'center',
                                    width : 10,
                                    formatter : function(e, rowData) {
                                        var optsStr = "";
                                        if (rowData.currentState == 0) {
                                            var optsStr = "未提交";
                                        } else if (rowData.currentState == 1) {
                                            var optsStr = "已提交";
                                        } else if (rowData.currentState == 2) {
                                            var optsStr = "已停用";
                                        }

                                        return optsStr;
                                    },
                                },
                                {
                                    field : '___',
                                    title : '操作',
                                    align : 'center',
                                    width : 10,
                                    formatter : function(e, rowData) {
                                        var optsStr = ""
                                        if(rowData.createType == ctrlType){
                                            if(rowData.currentState==0){         
                                                var optsStr =
                                                    "<span title='编辑'><a href=\"#\" onclick=\"doOpenEditPage('"
                                                    + rowData.id
                                                    + "')\"><i class='fa fa-pencil common-yellow'></i></a></span>&nbsp;&nbsp;&nbsp;&nbsp;" +
                                                    
                                                    "<span title='删除'><a href=\"#\" onclick=\"inspListDelete('"
                                                    + rowData.id
                                                    + "')\"><i class='fa fa-trash-o common-red'></i></a></span>";
                                                return optsStr;
                                            } else if (rowData.currentState==1 || rowData.currentState==2){
                                                var optsStr =
                                                    "<span title='查看'><a href=\"#\" onclick=\"listDetail('"
                                                    + rowData.id
                                                    + "')\"><i class='fa fa-eye '></i></a></span>&nbsp;&nbsp;&nbsp;&nbsp;" 
                                                    +"<span title='删除'><a href=\"#\" onclick=\"inspListDel('"
                                                    + rowData.id
                                                    + "')\"><i class='fa fa-trash-o common-red'></i></a></span>";
                                                return optsStr;
                                            } /*else if (rowData.currentState==2){
                                                //检查模块已删除，检查单停用
                                                var optsStr = ""
                                                    if (rowData.createType == ctrlType) {
                                                        var optsStr =
                                                            "<span title='删除'><a href=\"#\" onclick=\"inspListDel('"
                                                            + rowData.id
                                                            + "')\"><i class='fa fa-trash-o common-red'></i></a></span>";
                                                    }
                                                 return optsStr;
                                            }*/
                                        } else {
                                            if (rowData.createType != ctrlType) {
                                                var optsStr =
                                                    "<span title='查看'><a href=\"#\" onclick=\"listDetail('"
                                                    + rowData.id
                                                    + "')\"><i class='fa fa-eye '></i></a></span>";
                                            }
                                            return optsStr;
                                        }
//                                        if(rowData.currentState==0){
//                                        var optsStr = ""
//                                                if (rowData.createType == ctrlType) {
//                                                    
//                                                    var optsStr =
//                                                        "<span title='编辑'><a href=\"#\" onclick=\"doOpenEditPage('"
//                                                        + rowData.id
//                                                        + "')\"><i class='fa fa-pencil common-yellow'></i></a></span>&nbsp;&nbsp;&nbsp;&nbsp;" +
//                                                        
//                                                        "<span title='删除'><a href=\"#\" onclick=\"inspListDelete('"
//                                                        + rowData.id
//                                                        + "')\"><i class='fa fa-trash-o common-red'></i></a></span>";
//                                                    }
//                                                return optsStr;
//                                        } else if (rowData.currentState==1){
//                                                var optsStr = ""
//                                                    if (rowData.createType == ctrlType) {
//                                                        
//                                                        var optsStr =
//                                                            "<span title='查看'><a href=\"#\" onclick=\"listDetail('"
//                                                            + rowData.id
//                                                            + "')\"><i class='fa fa-eye '></i></a></span>";
//                                                    }
//                                                 return optsStr;
//                                        } else if (rowData.currentState==2){
//                                            //检查模块已删除，检查单停用
//                                            var optsStr = ""
//                                                if (rowData.createType == ctrlType) {
//                                                    var optsStr =
//                                                        "<span title='删除'><a href=\"#\" onclick=\"inspListDel('"
//                                                        + rowData.id
//                                                        + "')\"><i class='fa fa-trash-o common-red'></i></a></span>";
//                                                }
//                                             return optsStr;
//                                        }
                                    }
                                }, ] ]
                    });
}
//回车查询
function doInitEnter() {
    document.onkeyup = function (e) {
        var code = e.charCode || e.keyCode;  //取出按键信息中的按键代码(大部分浏览器通过keyCode属性获取按键代码，但少部分浏览器使用的却是charCode)
        if (code == 13) {
            searchList();
        }
    }
}

function searchList(){
    var params = {
        listName : $.trim($("#inspListName").val()),
        loginDeptId : loginDeptId,
        loginSubId : loginSubId,
        ctrlType : ctrlType,
        orgSys : orgSys
    };
    initDatagrid(params);
}

/*
 * * 查询
 */
//function doInit1() {
//var result = tools.requestJsonRs("/commonCtrl/getRelationAndOrgSys.action",
//            params);
//    if (result.rtState) {
//        var relation = result.relation;
//        loginDeptId = relation.businessDeptId;
//        loginSubId = relation.businessSubjectId;
//        orgSys = result.orgSys;
//        var params = {
//        listName : $.trim($("#inspListName").val()),
//            loginDeptId : loginDeptId,
//            loginSubId : loginSubId,
//            ctrlType : ctrlType,
//            orgSys : orgSys
//        };
//    }
//    $('#datagrid').datagrid("reload", params);
//
//}

/*
 * 打开信息编辑页面
 */
function doOpenInputPage(id) {

    var params = {
        id : id,
        loginDeptId : loginDeptId,
        loginSubId : loginSubId,
        ctrlType : ctrlType,
        orgSys : orgSys
    }
    var url = contextPath + "/inspListBaseCtrl/listInputPage.action";
    url = url + "?" + $.param(params);
    top.bsWindow(url, "新增", {
        width : "600",
        height : "300",
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
                var status = cw.saveListInfo();
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


function doOpenEditPage(id) {

    var params = {
        id : id,
        loginDeptId : loginDeptId,
        loginSubId : loginSubId,
        ctrlType : ctrlType,
        orgSys : orgSys
    }
    var url = contextPath + "/inspListBaseCtrl/listInputPage.action";
    url = url + "?" + $.param(params);
    top.bsWindow(url, "修改", {
        width : "600",
        height : "300",
        buttons : [{
            name : "关闭",
            classStyle : "btn-alert-gray"
        }, {
            name : "保存",
            classStyle : "btn-alert-blue"
        } ],
        submit : function(v, h) {
            var cw = h[0].contentWindow;
            if (v == "保存") {
                var status = cw.saveListInfo();
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
//检查单模版查看
function listDetail(id){
    top.bsWindow(contextPath + "/supervise/inspection/inspList/inspection_list_detail.jsp?id="+id, "查看", {
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
// /*
//提交检查单模版--改变状态
function inspListSubmit(){
    var rows = $('#datagrid').datagrid('getSelections');
    if(rows.length == 0) {
        alert("请选择要提交的检查单模版！");
        return;
    } 
    var ids = [];
    for(var index in rows) {
        ids.push(rows[index].id);
    }
    
    var params = {
        id: ids.join(","),
        orgSys: orgSys,
        loginDeptId: loginDeptId,
        loginSubId: loginSubId,
        ctrlType: ctrlType,
        currentState: '1'
    };
    var isId = tools.requestJsonRs("/inspListBaseCtrl/idsCtrl.action", params);
    if(isId.rtState){
        var rtInfo = tools.requestJsonRs("/inspListBaseCtrl/updateListState.action", params);
        if(rtInfo.rtState) {
            $.MsgBox.Alert_auto("提交成功");
            initDatagrid();
        }else {
            $.MsgBox.Alert_auto("提交失败");
        }
    }else {
        $.MsgBox.Alert_auto("权限不足，提交失败");
    }
}
/**
 * 检查模版未提交删除（彻底从数据库中删除）
 */
function inspListDelete(id) {
    if (window.confirm("是否确认删除？")) {
        var json = tools.requestJsonRs("/inspListBaseCtrl/dataBaseDelete.action",{id:id});
        if(json.rtState){
            $.MsgBox.Alert_auto("删除成功");
            $('#datagrid').datagrid("reload");
        }else{
            $.MsgBox.Alert_auto("删除失败");
        }
    }
}
/**
 * 检查单模版删除（已提交:is_delete=1,未提交：数据库删除）
 */
//function inspListDel(id){
//
//    var params = {
//        id: id,
//        orgSys: orgSys,
//        loginDeptId: loginDeptId,
//        loginSubId: loginSubId,
//        ctrlType: ctrlType,
//        isDelete: 1
//    };
//    if (window.confirm("是否确认删除？")) {
//        var json = tools.requestJsonRs("/inspListBaseCtrl/inspListDel.action", params);
//        if(json.rtState) {
//            $.MsgBox.Alert_auto("删除成功");
//            $('#datagrid').datagrid("reload");
//        }else {
//            $.MsgBox.Alert_auto("删除失败");
//        }
//    }
//}


/**
 * 检查单模版批量删除（已提交:is_delete=1,未提交：数据库删除）
 */
function inspListDel(){
    var rows = $('#datagrid').datagrid('getSelections');
    if(rows.length == 0) {
        alert("请选择要删除的检查单模版！");
        return;
    } 
    var ids = [];
    for(var index in rows) {
        ids.push(rows[index].id);
    }
    var params = {
        id: ids.join(","),
        orgSys: orgSys,
        loginDeptId: loginDeptId,
        loginSubId: loginSubId,
        ctrlType: ctrlType,
        isDelete: 1
    };
    
    if (window.confirm("是否确认删除？")) {
        
        var isId = tools.requestJsonRs("/inspListBaseCtrl/idsCtrl.action", params);
        if(isId.rtState){
            var json = tools.requestJsonRs("/inspListBaseCtrl/inspListDel.action", params);
            if(json.rtState) {
                $.MsgBox.Alert_auto("删除成功");
                $('#datagrid').datagrid("reload");
            }else {
                $.MsgBox.Alert_auto("删除失败");
            }
        }else {
            $.MsgBox.Alert_auto("权限不足，删除失败");
        }
    }
}

