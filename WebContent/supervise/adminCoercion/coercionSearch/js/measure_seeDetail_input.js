/**
 * 
 */

var coercionDetails = [];
var coercionMeasureType = '';
var preMeasureType = '';
var optCtrl = [];
var measureId = '';
var measureType = '';
var srcCaseId = '';
var srcCaseType = '';
var coercionCaseId = '';
var departmentId = '';
var subjectId = '';
var addPower = [];
var powerJson = [];
var gistJson = [];

function measureEditInit() {
    srcCaseId = $('#caseSourceId').val();
    srcCaseType = $('#caseSourceType').val();
    coercionCaseId = $('#coercionCaseId').val();
    measureType = $('#measureType').val();
    measureId = $('#measureId').val();
    subjectId = $('#subjectId').val();
    departmentId = $('#departmentId').val();
    preMeasureType = measureType;
 // 获取强制措施信息，尝试初始化职权依据表
    if (measureId != null && measureId != '') {
        var json = tools.requestJsonRs(contextPath +"/coercionCaseCtrl/getMeasureInfo.action",{id:measureId});
        var powerJsonStr = json.rtData.powerJsonStr;
        var powerJson;
        if (powerJsonStr != null && powerJsonStr != ''
                && powerJsonStr != 'null') {
            powerJson = powerJsonStr.split(",");
            var params;
            var paramsGist;
            if (powerJson != null && powerJson.length > 0) {
                for (var i = 0; i < powerJson.length; i++) {
                    addPower.push(powerJson[i]);
                }
                params = {
                    ids : addPower.join(","),
                    actSubject : subjectId
                }
                paramsGist = {
                    powerId : addPower.join(",")
                }
                initCoercionPowerTable(params);// 违法行为信息加载
                initCoercionGistTable(paramsGist); // 违法依据信息加载
                var gistJsonStr = json.rtData.gistJsonStr;
                gistJson = [];
                if (gistJsonStr != null && gistJsonStr != ''
                        && gistJsonStr != 'null') {
                    gistJson = gistJsonStr.split(",");
                }
            }
        }else{
            var emptyPowerParams = {
                    ids: 'empty',
                    actSubject: 'empty'
                    
            }
            var emptyGistParams = {
                    powerId: 'empty'
                    
            }
            initCoercionPowerTable(emptyPowerParams);
            initCoercionGistTable(emptyGistParams);
        }
    }
}



function doOpenListPage() {
    var params = {
    	caseSourceId: srcCaseId,
        id : coercionCaseId,
        pageType: '02'
    }
    var url = contextPath + "/coercionCaseSearchCtrl/seeCoercionCaseInfoPage.action";
    url = url + "?" + $.param(params);
    window.location.href = url;
}

function doInitCoercionDetailsTableByIds(params) {
    datagrid = $('#coercion_powerDetail_table').datagrid({
        url : contextPath + '/detailController/getLawDetailByIds.action',
        queryParams : params,
        pagination : false,
        singleSelect : false,
        // pageSize : 15,
        // pageList : [ 15, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
        view : window.EASYUI_DATAGRID_NONE_DATA_TIP,
        // 工具条对象
        checkbox : true,
        border : false,
        /* idField:'formId',//主键列 */
        fitColumns : true,
        // 列是否进行自动宽度适应
        nowrap : true,
        onLoadSuccess : function(data) {
        },
        columns : [ [ {
            field : 'id',
            checkbox : true,
            title : "ID",
            width : 20
        }, {
            field : 'lawName',
            title : '依据名称',
            width : 100,
            formatter : function(value, rowData, rowIndex) {
                return "<span class='common-overflow-hidden common-table-td-full-width' title='"+value+"'>"+value+"</span>";
            }
        }, {
            field : 'detailStrip',
            title : '条',
            width : 15
        }, {
            field : 'detailFund',
            title : '款',
            width : 15,
            formatter : function(value, rowData, rowIndex) {
                if (value == 0) {
                    return "";
                } else {
                    return value;
                }
            }
        }, {
            field : 'item',
            title : '项',
            width : 15,
            formatter : function(value, rowData, rowIndex) {
                if (value == 0) {
                    return "";
                } else {
                    return value;
                }
            }
        }, {
            field : 'content',
            title : '内容',
            width : 200
        } ] ]
    });
}

/**
 * 查询实施主体下的职权
 * 
 * @returns
 */
function coercionFindPower() {
    var params = {
        actSubject : subjectId,
        powerType : '03'
    }
    var url = contextPath + "/caseCommonPowerCtrl/commonCaseAddPower.action?"
            + $.param(params);
    top.bsWindow(url, "选用强制职权", {
        width : "1000",
        height : "500",
        buttons : [ {
            name : "保存",
            classStyle : "btn-alert-blue"
        }, {
            name : "关闭",
            classStyle : "btn-alert-gray"
        } ],
        submit : function(v, h) {
            var cw = h[0].contentWindow;
            if (v == "保存") {
                var formalPowers = cw.getSelectedPower();
                if (addPower == null || addPower.length == 0) {
                    for ( var index in formalPowers) {
                        addPower.push(formalPowers[index].id);
                    }
                } else {
                    for ( var index in formalPowers) {
                        if (addPower.indexOf(formalPowers[index].id) == -1) {
                            addPower.push(formalPowers[index].id);
                        }
                    }
                }
                var params = {
                    ids : addPower.join(","),
                    actSubject : subjectId
                };
                var paramsGist = {
                    powerId : addPower.join(","),
                    gistType : '01' // 依据类型（01 违法依据，02处罚依据，02设定依据）
                }
                initCoercionPowerTable(params);// 加载违法行为
                initCoercionGistTable(paramsGist);// 加载违法依据
                return true;
            } else if (v == "关闭") {
                return true;
            }
        }
    });
}

initCoercionPowerTable = function(params) {
    datagrid = $('#measure_power_table')
            .datagrid(
                    {
                        url : contextPath
                                + '/powerCtrl/getPowerByActSubject.action',
                        pagination : false,
                        // pageSize : 5,
                        // pageList : [5, 10, 20],
                        view : window.EASYUI_DATAGRID_NONE_DATA_TIP,
//                        toolbar : '#toolbar', // 工具条对象
                        checkbox : true,
                        border : false,
                        /* idField:'formId',//主键列 */
                        fitColumns : true, // 列是否进行自动宽度适应
                        singleSelect : true, // 为true只能选择一行
                        nowrap : true,
                        queryParams : params,// 查询参数
                        onLoadSuccess : function(data) {
                        },
                        columns : [ [
                                {
                                    field : 'id',
                                    title : "序号",
                                    align : 'center',
                                    formatter : function(value, rowData, rowIndex) {
                                        return rowIndex+1;
                                    }
                                },
                                {
                                    field : 'name',
                                    title : '违法行为',
                                    width : 200,
                                    halign : 'center',
                                    formatter : function(value, rowData, rowIndex) {
                                        return "<span class='common-overflow-hidden common-table-td-full-width' title='"+value+"'>"+value+"</span>";
                                    }
                                },
                                {
                                    field : 'code',
                                    title : '职权编号 ',
                                    width : 80,
                                    align : 'center'
                                } ] ]
                    });
}

initCoercionGistTable = function(params) {
    datagrid = $('#measure_gist_table').datagrid(
            {
                url : contextPath + '/powerCtrl/findGistsByPowerIds.action',
                queryParams : params,// 查询参数
                pagination : false,
                // pageSize : 5,
                // pageList : [5, 10, 20],
                view : window.EASYUI_DATAGRID_NONE_DATA_TIP,
//                toolbar : '#toolbar', // 工具条对象
                checkbox : false,
                border : false,
                /* idField:'formId',//主键列 */
                fitColumns : true, // 列是否进行自动宽度适应
                singleSelect : true, // 为true只能选择一行
                nowrap : true,
                onLoadSuccess : function(data) {
                },
                columns : [ [ {
                    field : 'id',
                    title : "序号",
                    align : 'center',
                	formatter : function(value, rowData, rowIndex) {
                        return rowIndex+1;
                    }
                }, {
                    field : 'lawName',
                    title : '法律名称',
                    width : 120,
                    halign : 'center',
                    formatter : function(value, rowData, rowIndex) {
                        return "<span class='common-overflow-hidden common-table-td-full-width' title='"+value+"'>"+value+"</span>";
                    }
                }, {
                    field : 'gistStrip',
                    title : '条',
                    width : 15,
                    align : 'center'
                }, {
                    field : 'gistFund',
                    title : '款',
                    width : 15,
                    align : 'center',
                    formatter : function(value, rowData, rowIndex) {
                        if (value == 0) {
                            return "";
                        } else {
                            return value;
                        }
                    }
                }, {
                    field : 'gistItem',
                    title : '项',
                    width : 15,
                    align : 'center',
                    formatter : function(value, rowData, rowIndex) {
                        if (value == 0) {
                            return "";
                        } else {
                            return value;
                        }
                    }
                }, {
                    field : 'content',
                    title : '内容',
                    width : 200,
                    halign : 'center',
                    formatter : function(value, rowData, rowIndex) {
                        return "<span class='common-overflow-hidden common-table-td-full-width' title='"+value+"'>"+value+"</span>";
                    }
                } ] ]
            });
}
