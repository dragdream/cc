/**
 * 
 */
var datagrid;
var loginDeptId = '';
var loginSubId = '';
var subLevel = '';
var orgSys = '';
var listName = '';

function doInit() {
	initBaseParams();
    doInitEnter();
}

function initBaseParams() {
    var result = tools.requestJsonRs("/commonCtrl/getRelationAndOrgSys.action",
            params);
    if (result.rtState) {
        var relation = result.relation;
        loginDeptId = relation.businessDeptId;
        loginSubId = relation.businessSubjectId;
        subLevel = relation.subLevel;
        orgSys = result.orgSys;
        var params = {
            loginDeptId : loginDeptId,
            loginSubId : loginSubId,
            orgSys : orgSys
        };
        initDatagrid(params);
    }
}


function initDatagrid(params) {
    datagrid = $('#datagrid').datagrid(
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
                                    title : '事项名称',
                                    halign : 'center',
                                    width : 20,
                                    formatter: function(e, rowData) {
                                        var list = "<label class='common-overflow-hidden common-table-td-full-width' title='"+rowData.listName+"'><a href='#' onclick='doOpenDetailPage(\"" + rowData.id + "\")'>" + rowData.listName + "</a></label>";
                                        return list;
                                    }
                                },
                                {
                                    field : 'applyHierarchyStr',
                                    title : '行使层级',
                                    halign : 'center',
                                    align : 'center',
                                    width : 10
                                },
                                {
                                    field : 'inspTypeStr',
                                    title : '检查类别',
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
                                        if (rowData.currentState == '01') {
                                            var optsStr = "在用";
                                        } else if (rowData.currentState == '02') {
                                            var optsStr = "取消";
                                        } else if (rowData.currentState == '03') {
                                            var optsStr = "暂停";
                                        } 
                                        return optsStr;
                                    },
                                },
                                {
                                    field : 'isSuperviseList',
                                    title : '是否国家监管',
                                    align : 'center',
                                    halign : 'center',
                                    width : 10,
                                    formatter : function(e, rowData) {
                                        var optsStr = "";
                                        if (rowData.isSuperviseList == 0) {
                                            var optsStr = "否";
                                        } else if (rowData.isSuperviseList == 1) {
                                            var optsStr = "是";
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
                                    	var optsStr =
                                              "<span title='编辑'><a href=\"#\" onclick=\"doOpenInputPage('"
                                            + rowData.id +"','"+'修改'
                                            + "')\"><i class='fa fa-pencil common-yellow'></i></a></span>&nbsp;&nbsp;&nbsp;&nbsp;" 
                                            + "<span title='删除'><a href=\"#\" onclick=\"inspListDelete('"
                                            + rowData.id
                                            + "')\"><i class='fa fa-trash-o common-red'></i></a></span>";
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
 * 打开信息编辑页面
 */
function doOpenInputPage(id,name) {

    var param = {
        id : id,
        loginSubId : loginSubId,
        loginDeptId: loginDeptId,
        orgSys : orgSys
    }
    var url = contextPath + "/supervise/inspection/inspList/insp_list_input.jsp";
//    var url = contextPath + "/inspListBaseCtrl/inspListInput.action";
    url = url + "?" + $.param(param);
    top.bsWindow(url, name, {
        width : "1000",
        height : "500",
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
                var status = cw.saveInspList();
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
function doOpenDetailPage(id){
	var param = {
	        id : id,
	        loginSubId : loginSubId,
	        loginDeptId: loginDeptId,
	        orgSys : orgSys
	    };
    top.bsWindow(contextPath + "/supervise/inspection/inspList/insp_list_detail.jsp?"+ $.param(param), "查看", {
        width : "1000",
        height : "500",
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

