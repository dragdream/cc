/**
 * 
 */
var datagrid;
var ctrlType = '';
var loginDeptId = '';
var loginSubId = '';
var orgSys = '';
var inspectionNumber = '';
function doInit() {
	initBaseParams();
    initPersonPermision();
    initInspectionDate();
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
        if(relation.businessSubjectId==null||relation.businessSubjectId=='null'||relation.businessSubjectId==''){
            $("#but").hide();
//            $("#delButton").hide();
        }
        orgSys = result.orgSys;
        var params = {
            departmentId : loginDeptId,
            subjectId : loginSubId,
            orgSys : orgSys
        };
        initDatagrid(params);
    }
}

function initDatagrid(params) {
    datagrid = $('#datagrid')
            .datagrid(
                    {
                        url : contextPath
                                + '/inspListRecordCtrl/inspListRecordByPage.action',
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
                        onLoadSuccess : function(data){
                        },
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
                                    field : 'inspectionNumber',
                                    title : '检查单号',
                                    halign : 'center',
                                    width : 30,
                                    formatter: function(e, rowData) {
                                        var list = "<label class='common-overflow-hidden common-table-td-full-width' title='" + rowData.inspectionNumber + "'><a href='#' onclick='recordDetail(\"" + rowData.id + "\")'>" + rowData.inspectionNumber + "</a></label>";
                                        return list;
                                    }
                                },
                                {
                                    field : 'inspectionAddr',
                                    title : '检查地点',
                                    halign : 'center',
                                    width : 30,
                                    formatter :function(value,rowData,rowIndex){
                                        if(value!=null){
                                            var text = "<span class='common-overflow-hidden common-table-td-full-width' title='"+value+"'>"+value+"</span>";
                                            return text;
                                        }
                                    },
                                },
                                {
                                    field : 'inspectionDateStr',
                                    title : '检查日期',
                                    align : 'center',
                                    halign : 'center',
                                    width : 30
                                },
                                {
                                    field : 'isInspectionPass',
                                    title : '检查结果',
                                    align : 'center',
                                    halign : 'center',
                                    formatter : function(e, rowData) {
                                        var optsStr = "";
                                        if (rowData.isInspectionPass == 1) {
                                            var optsStr = "合格";
                                        } else if (rowData.isInspectionPass == 2) {
                                            var optsStr = "不合格";
                                        }

                                        return optsStr;
                                    },
                                    width : 20
                                },
                                {
                                    field : '___',
                                    title : '操作',
                                    align : 'center',
                                    width : 10,
                                    formatter : function(e, rowData) {
                                        var optsStr = ""
                                        var json = tools.requestJsonRs("/inspListBaseCtrl/getById.action", {
                                            id : rowData.inspectionListId
                                        });
                                        if(json.rtState){
                                            if(json.rtData.currentState ==1 && json.rtData.isDelete == 0){
                                                var optsStr = "<span title='编辑'><a href=\"#\" onclick=\"inspRecordEdit('"
                                                    + rowData.id
                                                    + "')\"><i class='fa fa-pencil common-yellow'></i></a></span>&nbsp;&nbsp;&nbsp;&nbsp;" 
                                                    + "<span title='删除'><a href=\"#\" onclick=\"inspRecordDel('"
                                                    + rowData.id + "')\"><i class='fa fa-trash-o common-red'></i></a></span>";
                                            } else {
                                                var optsStr = "<span title='查看'><a href=\"#\" onclick=\"recordDetail('"
                                                    + rowData.id
                                                    + "')\"><i class='fa fa-eye'></i></a></span>"
                                                    + "&nbsp;&nbsp;&nbsp;&nbsp;" 
                                                    + "<span title='删除'><a href=\"#\" onclick=\"inspRecordDel('"
                                                    + rowData.id + "')\"><i class='fa fa-trash-o common-red'></i></a></span>";
                                            }
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
            doSearch();
        }
    }
}
/**
 * 查询条件
 */
function doSearch(){
    var params = {
        inspectionNumber : $.trim($("#recordNumber").val()),// 检查单号
        departmentId : loginDeptId,
        subjectId : loginSubId,
        orgSys : orgSys,
        beginInspectionDateStr : $('#beginInspectionDateStr').datebox('getValue'),
        endInspectionDateStr :$('#endInspectionDateStr').datebox('getValue')
    };
    initDatagrid(params);
}

/*
 * 执法检查单填报
 */
function doOpenInputPage(id) {

    var params = {
        id : id,
        departmentId : loginDeptId,
        subjectId : loginSubId,
        orgSys : orgSys,
        ctrlType : ctrlType
    }
    var url = contextPath
            + "/supervise/inspection/inspRecord/inspectionRecord_input.jsp?"
            + $.param(params);
    // var url = contextPath + "/inspListRecordCtrl/listRecordInputPage.action";
    // url = url + "?" + $.param(params);
    top.bsWindow(url, "新增", {
        width : "900",
        height : "500",
        buttons : [ {
            name : "关闭",
            classStyle : "btn-alert-gray"
        },{
            name : "保存",
            classStyle : "btn-alert-blue"
        }],
        submit : function(v, h) {
            var cw = h[0].contentWindow;
            if (v == "保存") {
                var status = cw.saveRecord();
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
 * 打开信息编辑页面
 */
function inspRecordEdit(id) {

    var params = {
        id : id,
        departmentId : loginDeptId,
        subjectId : loginSubId,
        orgSys : orgSys,
        ctrlType : ctrlType
    }
    var url = contextPath
            + "/supervise/inspection/inspRecord/inspectionRecord_input.jsp";
    url = url + "?" + $.param(params);
    top.bsWindow(url, "修改", {
        width : "900",
        height : "500",
        buttons : [  {
            name : "关闭",
            classStyle : "btn-alert-gray"
        },{
            name : "保存",
            classStyle : "btn-alert-blue"
        } ],
        submit : function(v, h) {
            var cw = h[0].contentWindow;
            if (v == "保存") {
                var status = cw.saveRecord();
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
 * 删除（isDelete状态由0改为1）
 */
// function inspRecordDel(id){
//    
// var obj = $('#datagrid').datagrid("getSelections");
// if(){
//        
// }
// if (window.confirm("是否确认删除该数据")) {
// var json = tools.requestJsonRs("/inspectionCtrl/delete.action", {
// id : obj[0].id
// });
// $('#datagrid').datagrid("reload");
// }
// }
/**
 * 批量修改isDelete状态
 */
function inspRecordDels() {
    var rows = $('#datagrid').datagrid('getSelections');
    if (rows.length == 0) {
        alert("请选择要删除的检查单！");
        return;
    }
    var ids = [];
    for ( var index in rows) {
        ids.push(rows[index].id);
    }
    var params = {
        id : ids.join(","),
        isDelete : 1
    };
    if (window.confirm("是否确认删除该数据")) {
        var json = tools.requestJsonRs("/inspListRecordCtrl/inspRecordDel.action",params);
        if (json.rtState) {
            $.MsgBox.Alert_auto("删除成功");
            $('#datagrid').datagrid("reload");
        } else {
            $.MsgBox.Alert_auto("删除失败");
        }
    }
}

/**
 * 超链接删除检查单（修改isDelete状态）
 */
function inspRecordDel(id){
    var params = {
            id : id,
            isDelete : 1
        };
    if (window.confirm("是否确认删除该数据")) {
        var json = tools.requestJsonRs("/inspListRecordCtrl/inspRecordDel.action",params);
        if (json.rtState) {
            $.MsgBox.Alert_auto("删除成功");
            $('#datagrid').datagrid("reload");
        } else {
            $.MsgBox.Alert_auto("删除失败");
        }
    }
}
/**
 * 初始化时间
 */
function initInspectionDate(){
    $("#beginInspectionDateStr").datebox().datebox('calendar').calendar({
        validator: function(date){
            var endDate = $("#endInspectionDateStr").val();
            if(!endDate){
                return true;
            }
            var r2 = endDate.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
            var d2 = new Date(r2[1], r2[3]-1, r2[4]);
            return date<=d2;
        }
    });
    $("#endInspectionDateStr").datebox().datebox('calendar').calendar({
        validator: function(date){
            var endDate = $("#beginInspectionDateStr").val();
            if(!endDate){
                return true;
            }
            var r2 = endDate.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
            var d2 = new Date(r2[1], r2[3]-1, r2[4]);
            return date>=d2;
        }
    });
}

function recordDetail(id) {

    var params = {
        id : id,
        departmentId : loginDeptId,
        subjectId : loginSubId,
        orgSys : orgSys
    }
    var url = contextPath + "/supervise/inspection/inspRecord/inspectionRecord_detail.jsp?"+$.param(params);
    top.bsWindow(url, "查看", {
        width : "900",
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




