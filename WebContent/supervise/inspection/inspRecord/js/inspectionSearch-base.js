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
    initInspectionDate();
    doInitEnter();
}
/**
 * 初始化所属领域
 */
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
    datagrid = $('#datagrid')
            .datagrid(
                    {
                        url : contextPath
                                + '/inspListRecordCtrl/findSearchListBypage.action',
                        queryParams : params,
                        pagination : true,
                        singleSelect : false,
                        striped : true,
                        pageSize : 20,
                        pageList : [ 10, 20, 50, 100 ],
                        view : window.EASYUI_DATAGRID_NONE_DATA_TIP,
                        toolbar : '#toolbar',
                        // 工具条对象
                        checkbox : false,
                        border : false,
                        /* idField:'formId',//主键列 */
                        fitColumns : true,
                        // 列是否进行自动宽度适应
                        nowrap : true,
                        columns : [ [
                                {
                                    field : 'id',
                                    title : '序号',
                                    align : 'center',
                                    formatter : function(value,rowData,rowIndex){
                                        return rowIndex+1;
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
                                        }else if (rowData.isInspectionPass == 2){
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
//                                        var optsStr = ""
//                                        if (rowData.createType == ctrlType) {
                                            var optsStr = "<span title='查看'><a href=\"#\" onclick=\"recordDetail('"
                                                    + rowData.id + "')\"><i class='fa fa-eye'></i></a></span>";
//                                        }

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
 * 查询
 */
function doSearch(){
    var params = {
        inspectionNumber: $.trim($("#recordNumber").val()),// 检查单号
        departmentId : loginDeptId,
        subjectId : loginSubId,
        orgSys : orgSys,
        beginInspectionDateStr : $('#beginInspectionDateStr').datebox('getValue'),
        endInspectionDateStr :$('#endInspectionDateStr').datebox('getValue')
    };
    initDatagrid(params);
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

/*
 * 打开信息编辑页面
 */
function recordDetail(id) {

    var params = {
        id : id,
        departmentId : loginDeptId,
        subjectId : loginSubId,
        orgSys : orgSys
    }
    var url = contextPath + "/supervise/inspection/inspRecord/inspectionRecord_detail.jsp?"+$.param(params);
    top.bsWindow(url, "检查单记录详情", {
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
// /*