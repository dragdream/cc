/**
 * 
 */

var coercionDetails = [];
var coercionMeasureType = '';
var preMeasureType = '';
var optCtrl = [];
var measureId = '';
var srcCaseId = '';
var srcCaseType = '';
var coercionCaseId = '';
var subjectId = '';
var departmentId = '';

function doInit() {
    srcCaseId = $('#caseSourceId').val();
    srcCaseType = $('#caseSourceType').val();
    subjectId = $('#subjectId').val();
    departmentId = $('#departmentId').val();
    coercionCaseId = $('#coercionCaseId').val();
    initDatagrid();
}

function initDatagrid() {
    datagrid = $('#measureInput_datagrid')
            .datagrid(
                    {
                        url : contextPath
                                + '/coercionCaseCtrl/measureListByPage.action',
                        queryParams : {
                            caseSourceType : srcCaseType,
                            caseSourceId : srcCaseId,
                        },
                        pagination : true,
                        singleSelect : true,
                        pageSize : 10,
                        pageList : [ 10, 20, 50, 100 ],
                        view : window.EASYUI_DATAGRID_NONE_DATA_TIP,
                        toolbar : '#toolbar',
                        // 工具条对象
                        // checkbox: true,
                        border : false,
                        /* idField:'formId',//主键列 */
                        fitColumns : true,
                        // 列是否进行自动宽度适应
                        nowrap : true,
                        onLoadSuccess : function(data) {
                        },
                        columns : [ [
                                {
                                    field : 'measureTypeStr',
                                    title : '行政强制措施种类',
                                    halign : 'center',
                                    align : 'center',
                                    width : 30
                                },
                                {
                                    field : 'createDateStr',
                                    title : '录入日期',
                                    halign : 'center',
                                    align : 'center',
                                    width : 20

                                },
                                {
                                    field : 'applyDateStr',
                                    title : '申请日期 ',
                                    halign : 'center',
                                    align : 'center',
                                    width : 20
                                },
                                {
                                    field : 'approveDateStr',
                                    title : '批准日期',
                                    halign : 'center',
                                    align : 'center',
                                    width : 20
                                },
                                {
                                    field : '____',
                                    title : '流程状态',
                                    halign : 'center',
                                    align : 'center',
                                    width : 15,
                                    formatter : function(e, rowData) {
                                        var textStr = "";
                                        if (rowData.enforceStep == 0) {
                                            textStr = "未知";
                                        }
                                        if (rowData.enforceStep == 1) {
                                            textStr = "确定强制措施种类";
                                        }
                                        if (rowData.enforceStep == 2) {
                                            textStr = "已批准申请";
                                        }
                                        if (rowData.enforceStep == 3) {
                                            textStr = "已作出处理决定";
                                        }
                                        return textStr;
                                    }
                                },
                                {
                                    field : '___',
                                    title : '操作',
                                    halign : 'center',
                                    align : 'center',
                                    formatter : function(e, rowData) {
                                        var optStr = "&nbsp;&nbsp;&nbsp;&nbsp;<a href='#' onclick='doOpenShowPage(\""
                                                + rowData.id
                                                + "\")'>编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;";
                                        return optStr;
                                    }
                                } ] ]
                    });
}

function doOpenShowPage(id) {
    var params = {
            id: id,
            caseSourceId: srcCaseId,
            caseSourceType: srcCaseType,
            subjectId: subjectId,
            departmentId: departmentId,
            coercionCaseId: coercionCaseId
    };
    location.href = contextPath
            + "/coercionCaseCtrl/measuresEditInput.action?" + $.param(params);
}
function doOpenNewMeasurePage(){
    var params = {
            caseSourceId: srcCaseId,
            caseSourceType: srcCaseType,
            subjectId: subjectId,
            departmentId: departmentId,
            coercionCaseId: coercionCaseId
    };
    location.href = contextPath
    + "/coercionCaseCtrl/measuresEditInput.action?" + $.param(params);
}