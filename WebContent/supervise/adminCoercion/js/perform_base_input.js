/**
 * 
 */

var srcCaseId = '';
var srcCaseType = '';
var coercionCaseId = '';
var subjectId = '';
var departmentId = '';
var courtPerformId = '';
var isSecondPress = null;

function performBaseInit() {
    initComboBox();
    initCourtPerform();
    srcCaseId = $('#caseSourceId').val();
    srcCaseType = $('#caseSourceType').val();
    subjectId = $('#subjectId').val();
    departmentId = $('#departmentId').val();
    coercionCaseId = $('#coercionCaseId').val();
    courtPerformId = $('#courtPerformId').val();
    initDatagrid();
}

/**
 * 初始化送达方式combobox
 */
function initComboBox() {

    var sendTypeParams = {
        parentCodeNo : "COMMON_SENT_WAY",
        codeNo : "",
        panelHeight : 'auto'

    };
    var result = tools.requestJsonRs("/sysCode/getSysParaByParentCode.action",
            sendTypeParams);
    $('.sendType-select').combobox({
        data : result.rtData,
        valueField : 'codeNo',
        textField : 'codeName',
        panelHeight : 'auto',
        prompt : '请选择',
        editable : false
    });
    
    var enfroceTypeParams = {
            parentCodeNo : "COURT_ENFORCE_TYPE",
            codeNo : "",
            panelHeight : 'auto'
        };
        var result = tools.requestJsonRs("/sysCode/getSysParaByParentCode.action",
                enfroceTypeParams);
        $('.enforceType-select').combobox({
            data : result.rtData,
            valueField : 'codeNo',
            textField : 'codeName',
            panelHeight : 'auto',
            prompt : '请选择',
            editable : false
        });
}

function initCourtPerform(){
    if (1 == $("#court_perform_press_panel input[name='isSecondPress']:checked").val()) {
        $('.secondPress-info-tr').show();
        $.parser.parse($('.secondPress-info-tr'));
    }
    $('#court_perform_press_panel .isSecondPress')
            .checkbox(
                    {
                        onChange : function() {
                            var choiceValue = $("#court_perform_press_panel input[name='isSecondPress']:checked").val();
                            isSecondPress = choiceValue;
                            if (choiceValue == '1') {
                                $('.secondPress-info-tr').show();
                                $.parser.parse($('.secondPress-info-tr'));
                            } else {
                                $('.secondPress-info-tr').hide();
                            }
                        }
                    });
}

function initDatagrid() {
    datagrid = $('#performsInput_datagrid')
            .datagrid(
                    {
                        url : contextPath
                                + '/coercionCaseCtrl/performListByPage.action',
                        queryParams : {
                            caseSourceType : 200,
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
                                    field : 'performTypeStr',
                                    title : '行政强制执行方式',
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
                                    formatter : function(e, rowData) {
                                        var textStr = "";
                                        if (rowData.applyDateStr != 'null') {
                                            textStr = rowData.applyDateStr;
                                        } else {
                                            textStr = "未申请";
                                        }
                                        return textStr;
                                    },
                                    width : 20
                                },
                                {
                                    field : 'approveDateStr',
                                    title : '批准日期',
                                    halign : 'center',
                                    align : 'center',
                                    formatter : function(e, rowData) {
                                        var textStr = "";
                                        if (rowData.applyDateStr != 'null') {
                                            textStr = rowData.approveDateStr;
                                        } else {
                                            textStr = "未批准";
                                        }
                                        return textStr;
                                    },
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
                                            textStr = "已确定强制执行方式";
                                        }
                                        if (rowData.enforceStep == 2) {
                                            textStr = "已作出催告";
                                        }
                                        if (rowData.enforceStep == 3) {
                                            textStr = "已作出申请与批准";
                                        }
                                        if (rowData.enforceStep == 3) {
                                            textStr = "已作出强制执行事项管理";
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
                                        var optStr = "&nbsp;&nbsp;&nbsp;&nbsp;<a href='#' onclick='doOpenSelfPerformPage(\""
                                                + rowData.id
                                                + "\")'>编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;";
                                        return optStr;
                                    }
                                } ] ]
                    });
}

function doOpenSelfPerformPage(id) {
    var params = {
        id : id,
        caseSourceId : srcCaseId,
        caseSourceType : srcCaseType,
        subjectId : subjectId,
        departmentId: departmentId,
        coercionCaseId : coercionCaseId
    };
    location.href = contextPath + "/coercionCaseCtrl/performsEditInput.action?"
            + $.param(params);
}

function doOpenNewPerformPage() {
    var params = {
        caseSourceId : srcCaseId,
        caseSourceType : srcCaseType,
        subjectId : subjectId,
        departmentId: departmentId,
        coercionCaseId : coercionCaseId
    };
    location.href = contextPath + "/coercionCaseCtrl/performsEditInput.action?"
            + $.param(params);
}

function saveCourtPerform() {
    var pressParams = tools.formToJson($('#court_perform_press_form'));
    var applyParams = tools.formToJson($('#court_perform_Apply_form'));
    // 获取强制执行类型
    // var performType = '';
    // $('#performInput_performType input[name="performType"]').each(function()
    // {
    // if ($(this).is(':checked')) {
    // performType = $(this).val();
    // }
    // });
    // if (performType == '') {
    // alert("无法获取强制执行方式，请刷新页面尝试重新保存！");
    // return false;
    // }
    var finalParams = {
        id : courtPerformId,
        subjectId : subjectId,
        caseSourceId : srcCaseId,
        caseSourceType : srcCaseType,
        departmentId: departmentId,
        coercionCaseId : coercionCaseId
    }
    // 催告
    finalParams.punishCodeBefore = pressParams.punishCodeBefore;
    finalParams.punishDateBeforeStr = pressParams.punishDateBeforeStr;
    finalParams.pressSendDateStr = pressParams.pressSendDateStr;
    finalParams.pressSendType = pressParams.pressSendType;
    finalParams.isSecondPress = isSecondPress;
    finalParams.secondPressDateStr = pressParams.secondPressDateStr;
    finalParams.secondPressType = pressParams.secondPressType;
    // 申请与批准
    finalParams.applyDateStr = applyParams.applyDateStr;
    finalParams.approveDateStr = applyParams.approveDateStr;
    finalParams.performType = applyParams.performType;
    finalParams.enforceElementCondition = applyParams.enforceElementCondition;
    var resultInfo = tools.requestJsonRs("/coercionCaseCtrl/saveCourtPerforminfo.action", finalParams);
    if (resultInfo.rtState) {
        $.MsgBox.Alert_auto("保存成功");
        var data = resultInfo.rtData;
        coercionCaseId = data.coercionCaseId;
        courtPerformId = data.id;
    } else if (resultInfo.rtData == -1) {
        $.MsgBox.Alert_auto("保存失败！");
    } else {
        $.MsgBox.Alert_auto("发生未知错误！");
    }
}
