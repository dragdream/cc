/**
 * 行政强制主页面控制
 */
var datagrid;
var ctrlType = '';
var loginDeptId = '';
var loginSubId = '';
var orgSys = '';

function doInit() {
    initBaseParams();
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
            departmentId : loginDeptId,
            subjectId : loginSubId,
            orgSys : orgSys
        };
        initDatagrid(params);
    }
}

function initDatagrid(params) {
    datagrid = $('#coercionSearch_datagrid').datagrid(
                    {
                        url : contextPath + '/coercionCaseSearchCtrl/coercionSearchListByPage.action',
                        queryParams : params,
                        pagination : true,
                        singleSelect : true,
                        striped: true,
                        pageSize : 20,
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
                        columns : [ [
                                {
                                    field : 'ID',
                                    title : '序号',
                                    halign : 'center',
                                    align : 'center',
                                    formatter : function(value,rowData,rowIndex){
                                        return rowIndex+1;
                                    }
                                },
                                {
                                    field : 'caseCode',
                                    title : '案件编号',
                                    halign : 'center',
                                    align : 'center',
                                    width : 20
                                },
                                {
                                    field : 'createDateStr',
                                    title : '强制记录日期',
                                    halign : 'center',
                                    align : 'center',
                                    width : 20
                                },
                                {
                                    field : 'subjectName',
                                    title : '所属主体 ',
                                    halign : 'center',
                                    align : 'center',
                                    width : 20
                                },
                                {
                                    field : 'caseSourceTypeStr',
                                    title : '案件来源',
                                    halign : 'center',
                                    align : 'center',
                                    width : 20
                                },
                                {
                                    field : '___',
                                    title : '操作',
                                    halign : 'center',
                                    align : 'center',
                                    width : 10,
                                    formatter : function(e, rowData) {
                                        var optStr = "<span title='查看'><a href='javaScript:void(0);' onclick='doOpenShowPage(\"" + rowData.id +"\",\""+rowData.caseSourceId+"\")'><i class='fa fa-eye'></i></a></span>";
                                        return optStr;
                                    }
                                } ] ]
                    });
}

/**
 * 
 * @param id
 */
function doOpenShowPage(id,caseSourceId) {
    var params = {
            id: id,
            pageType:'01',
            caseSourceId:caseSourceId,
            parentPage:"/supervise/adminCoercion/coercion_index.jsp"
    }
    var url = contextPath + "/coercionCaseSearchCtrl/seeCoercionCaseInfoPage.action";
    location.href = url + "?" + $.param(params);
//    url = url + "?" + $.param(params)
//    top.bsWindow2(url, "行政强制信息查看", {
//        width : "1000",
//        height : "500",
////        buttons : [ {
////            name : "关闭",
////            classStyle : "btn-alert-gray"
////        } ],
////        submit : function(v, h) {
////            var cw = h[0].contentWindow;
////        }
//    });
}
