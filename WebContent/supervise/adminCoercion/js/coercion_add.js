/**
 * 行政强制主页面控制
 */
var courtPerformId = '';
var isCoercion = null;
var datagrid;
var ctrlType = '';
var loginDeptId = '';
var loginSubId = '';
var orgSys = '';
var coercionCaseId = '';
var measureDatagrid = null;
var performDatagrid = null;
var srcCaseId = '';
var srcCaseType = '';
var subjectId = '';
var departmentId = '';
var caseCode = '';

function doInit() {
    initBaseParams();
    isEditCoercion();
}

function initBaseParams(params) {
    var result = tools.requestJsonRs("/commonCtrl/getRelationAndOrgSys.action", params);
    if (result.rtState) {
        var relation = result.relation;
        loginDeptId = relation.businessDeptId;
        loginSubId = relation.businessSubjectId;
        departmentId = loginDeptId;
        subjectId = loginSubId;
        orgSys = result.orgSys;
        $('#measureAdd_button').hide();
        $('#performAdd_button').hide();
        $('#countPerformAdd_button').hide();
    }
}

/**************************************************************************************************/
/**
 * 编辑强制信息
 */
function isEditCoercion(id){
    srcCaseId = $("#srcCaseId").val();
    var isForce = $("#isForce").val();
    var punishDecisionExecuteWay = $("#punishDecisionExecuteWay").val();
    if(isForce != 1){
        $('#coercion_manage_page').tabs('close','强制措施');
    }
    if(punishDecisionExecuteWay == '02'){
        $('#coercion_manage_page').tabs('close','申请法院强制执行');
    }else if(punishDecisionExecuteWay == '03'){
        $('#coercion_manage_page').tabs('close','行政机关执行');
    }else{
        $('#coercion_manage_page').tabs('close','行政机关执行');
        $('#coercion_manage_page').tabs('close','申请法院强制执行');
    }
    initCoercionCaseId();
    doOpenShowPage(srcCaseId);
}

function initCoercionCaseId(){
    var params = {
            caseSourceId: srcCaseId,
            departmentId : loginDeptId,
            subjectId : loginSubId,
    };
    var result = tools.requestJsonRs("/coercionCaseCtrl/getCoercionId.action",params);
    if(result.rtState){
        coercionCaseId = result.rtData.id;
    }
}
/**
 * 加载单个案件信息
 * @param id
 */
function doOpenShowPage(srcCaseId) {
//    selectTabs();
    var params = {
            caseId: srcCaseId,
    }
    $.ajax({ 
        type:"post",
        url: "/caseCommonBaseCtrl/commonCaseBaseLook.action",//提交地址 
        data: params,//参数 
        dataType: "json",
        success: function(data){ //回调方法 
            srcCaseType = '200';
            bindJsonObj2Cntrl(data.rtData);
            caseCode = data.rtData.caseCode;
            if(data.rtData.isSelfemployed == 1){
                $('#isSelfemployed').show();
                $('#isSelfemployed_checked_table').show();
                $('#isSelfemployed_unchecked_tr').hide();
            }else{
                $('#isSelfemployed').hide();
                $('#isSelfemployed_checked_table').hide();
                $('#isSelfemployed_unchecked_tr').show();
            }
            if(data.rtData.partyType==1){
                $('#partyType1').show();
                if(data.rtData.citizenSex == 1){
                    $('#citizenSex').text("男");
                }else if(data.rtData.citizenSex == 2){
                    $('#citizenSex').text("女")
                }
                $('#partyType2').hide();
                $('#partyType9').hide();
            }else if(data.rtData.partyType==2){
                $('#partyType2').show();
                $('#partyType1').hide();
                $('#partyType9').hide();
            }else if(data.rtData.partyType==9){
                $('#partyType9').show();
                $('#partyType1').hide();
                $('#partyType2').hide();
            }
            //初始化案件基础信息
            initFiling(data.rtData);
            //初始化强制措施
            initMeasureDatagrid();
            //初始化执法部门强制行为
            initPerformDatagrid();
            //初始化法院强制行为
            initCourtPerformDatagrid();
            courtPerformId = $('#courtPerformId').val();
        },
    });  
}
/**************************************************案件基础信息*************************************************/
/*
 * 立案阶段初始化
 * @returns
 */
function initFiling(data){
    var filingApprovalDocumentPath = data.filingApprovalDocumentPath;
    if(filingApprovalDocumentPath != null && filingApprovalDocumentPath != ''){
        $("#filingApprovalDocumentName").html("");
        // 修改回显立案呈批表
        initAttachmentInfo('commonCase', filingApprovalDocumentPath, 'filingApprovalDocumentName');
    }
    var partyType = data.partyType;
    if(partyType == '02' || partyType == "03"){
       $('#adressTr').show();
    }else{
        $('#adressTr').hide();
    }
    initPerson(data);//人员初始化
}
/**
 * 修改初始化附件列表
 * @param model
 * @param modelId
 * @param elemId
 * @returns
 */
function initAttachmentInfo(model, sid, elemId) {
    var json = tools.requestJsonRs("/attachmentController/getAttachmentModelsByIds.action?attachIds="+sid);
    if(json.rtData != null && json.rtData.length > 0) {
        var punishFile = [];
        var attachList = json.rtData;
        $.each(attachList, function(i, o){
            // 添加附件属性
            punishFile.push(o.sid);//存放上传文件ID
            addAttachmentProp(o, elemId, punishFile, 1+2);
        });
    }
}
/**
 * 上传完毕后加载已经上传的文件
 * @param obj 文件对象
 * @param elemId 展示元素ID
 * @returns
 */
function addAttachmentProp(obj, elemId, punishFile) {
    var mainObj = $('#' + elemId);
    var page = "";
    page = page + '<div style="float: left; margin-right:20px" id="flowsheet' + obj.sid + '">';
    page = page + '<input type="hidden" name="'+elemId+'FileId" id="'+elemId+'FileId" value="' + obj.sid + '" />';
    page = page + '<input type="hidden" name="'+elemId+'FileName" id="'+elemId+'FileName" value="' + obj.fileName + '" />';
    page = page + '<a href="javascript:void(0);" sid="FILE' + obj.sid + '"></a>';
    page = page + '</div>';
    mainObj.append(page);
    
    obj.priv = 1+2; // 阅读、下载、删除
    var attach = tools.getAttachElement(obj, {deleteEvent:function(attachModel){
        $("#flowsheet" + attachModel.sid).remove();
        for(var index in punishFile) {
            if(punishFile[index] == attachModel.sid) {
                punishFile.splice(index, 1);
                break;
            }
        }
        
    }});
    $("[sid='FILE" + obj.sid + "']").append(attach);
}
/**
* 初始化人员
* @returns
*/
function initPerson(data){
   var personJsonStr = data.personJsonStr;
   var params;
   if(personJsonStr != null && personJsonStr != '' && personJsonStr != 'null'){
       params = {ids: personJsonStr};
   }else{
       params = {ids: 'empty'};
   }
   initPersonDatagrid(params);
}

/**
* 人员表格加载函数
* @returns
*/
function initPersonDatagrid(params){
   datagrid = $('#common_case_add_person_datagrid').datagrid({
       url: contextPath + '/caseCommonStaffCtrl/findListByPersonIds.action',
       queryParams: params,
       pagination: false,
       view: window.EASYUI_DATAGRID_NONE_DATA_TIP,
       checkbox: true,
       border: false,
       striped: true,//隔行变色
       fitColumns: true, // 列是否进行自动宽度适应
       singleSelect: true, //为true只能选择一行
       nowrap: true,
       onLoadSuccess: function(data) {
       },
       columns: [[
           {field:'ID',title:'序号',align:'center',
               formatter:function(value,rowData,rowIndex){
                   return rowIndex+1;
               }
           },
           {
               field: 'subjectName', title: '所属主体', width: 30, halign: 'center', align: 'left',
               formatter: function(value, rowData) {
                   if(value == null || value == 'null') {
                       value = "";
                   }
                   var lins = "<lable class='common-overflow-hidden common-table-td-full-width' title='"+value+"'>"+value+"</lable>"
                   return lins;
               }
           },
           {
               field: 'name', title: '执法人员 ', width: 12, halign: 'center', align: 'center'
           },
           {
               field: 'code', title: '执法证号 ', width: 15, halign: 'center', align: 'center'
           }
       ]]
   });
}

/**************************************************强制实施信息*************************************************/
/*
 * 初始化强制实施信息
 */
function initMeasureDatagrid() {
    measureDatagrid = $('#measureSee_datagrid').datagrid(
                    {
                        url : contextPath + '/coercionCaseCtrl/measureListByPage.action',
                        queryParams : {
                            caseSourceType : srcCaseType,
                            caseSourceId : srcCaseId,
                        },
                        pagination : false,
                        singleSelect : true,
                        view : window.EASYUI_DATAGRID_NONE_DATA_TIP,
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
//                                        var optStr = "&nbsp;&nbsp;&nbsp;&nbsp;<a href='#' onclick='editOpenMeasurePage(\""
//                                            + rowData.id
//                                            + "\")'>编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;";
                                        var optStr = 
                                        "<span title='编辑'><a href=\"#\" onclick=\"editOpenMeasurePage('"
                                        + rowData.id +"','"+'修改'
                                        + "')\"><i class='fa fa-pencil common-yellow'></i></a></span>&nbsp;&nbsp;&nbsp;&nbsp;" +
                                        
                                        "<span title='删除'><a href=\"#\" onclick=\"deleteMeasurePage('"
                                        + rowData.id
                                        + "')\"><i class='fa fa-trash-o common-red'></i></a></span>";
                                        
                                        return optStr;
                                    }
                                } ] ]
                    });
}
/**
 * 对强制措施编辑
 * @param id
 */
function editOpenMeasurePage(id,name) {
    var params = {
            id: id,
            caseSourceId: srcCaseId,
            caseSourceType: srcCaseType,
            subjectId: subjectId,
            departmentId: departmentId,
            coercionCaseId: coercionCaseId,
            caseCode: caseCode,
            sourcePage:"measure"
    };
    var url = contextPath + "/coercionCaseCtrl/measuresEditInput.action";
    url = url + "?" + $.param(params);
    top.bsWindow(url, name, {
        width : "1000",
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
                var status = cw.saveMeasureInfo();
                $("#measureSee_datagrid").datagrid("reload");
                if (status) {
                    return true;
                }
            } else if(v == "关闭") {
                $('#measureSee_datagrid').datagrid('reload');
                return true;
            }
        },
        
    });
}


/**
 * 删除强制措施
 */
function deleteMeasurePage(id){
    var result = tools.requestJsonRs("/coercionCaseCtrl/deleteMeasureById.action",{id:id});
    if(result.rtState){
        $.MsgBox.Alert_auto("删除成功！");
        $('#measureSee_datagrid').datagrid('reload');
    }else {
        $.MsgBox.Alert_auto("删除失败！");
    }
}
/*function doOpenMeasureShowPage(id) {
    var params = {
            id: id,
            coercionCaseId: coercionCaseId
    };
    location.href = contextPath + "/coercionCaseSearchCtrl/measuresSeeInfoInput.action?" + $.param(params);
}*/


/**************************************************行政机关强制执行*************************************************/
/**
 *  行政机关强制执行 
 */
function initPerformDatagrid() {
    performDatagrid = $('#self_performSee_datagrid')
            .datagrid(
                    {
                        url : contextPath
                                + '/coercionCaseCtrl/performListByPage.action',
                        queryParams : {
                            caseSourceType : 200,
                            caseSourceId : srcCaseId,
                        },
                        pagination : false,
                        singleSelect : true,
                        view : window.EASYUI_DATAGRID_NONE_DATA_TIP,
                        border : false,
                        /* idField:'formId',//主键列 */
                        fitColumns : true,
                        // 列是否进行自动宽度适应
                        nowrap : true,
                        
                        striped:true,
                        /* idField:'formId',//主键列 */
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
                                        if (rowData.enforceStep == 4) {
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
                                        /*var optStr = "&nbsp;&nbsp;<span title='查看'><a href='javaScript:void(0);' onclick='doOpenPerformShowPage(\"" + rowData.id + "\")'><i class='fa fa-eye'></i></a></span>&nbsp;&nbsp;";
                                    return optStr;*/
//                                        var optStr = "&nbsp;&nbsp;&nbsp;&nbsp;<a href='#' onclick='editOpenPerformPage(\""
//                                            + rowData.id
//                                            + "\")'>编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;";
                                        var optStr = 
                                            "<span title='编辑'><a href=\"#\" onclick=\"editOpenPerformPage('"
                                            + rowData.id+"','"+'修改'
                                            + "')\"><i class='fa fa-pencil common-yellow'></i></a></span>&nbsp;&nbsp;&nbsp;&nbsp;" +
                                            
                                            "<span title='删除'><a href=\"#\" onclick=\"deletePerformPage('"
                                            + rowData.id
                                            + "')\"><i class='fa fa-trash-o common-red'></i></a></span>";
                                        return optStr;
                                    }
                                } ] ]
                    });
}

function editOpenPerformPage(id,name) {
    var params = {
            caseSourceId : srcCaseId,
            caseSourceType : srcCaseType,
            departmentId: departmentId,
            subjectId : subjectId,
            id: id,
            caseCode: caseCode,
            coercionCaseId: coercionCaseId,
            sourcePage:"perform"
    };
    var url = contextPath + "/coercionCaseCtrl/performsEditInput.action?"+ $.param(params);
    top.bsWindow(url, name, {
      width : "1000",
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
              var status = cw.savePerformInfo();
              $("#self_performSee_datagrid").datagrid("reload");
              if (status == true) {
                  return true;
              }
          } else if (v == "关闭") {
              $('#self_performSee_datagrid').datagrid('reload');
              return true;
          }
      }
  });
}

/**
 * 填报行政强制执行方式
 *//*
function doOpenNewPerformPage() {
    var params = {
        caseSourceId : srcCaseId,
        caseSourceType : srcCaseType,
        subjectId : subjectId,
        departmentId: departmentId,
        coercionCaseId : coercionCaseId,
        sourcePage:"perform"
    };
    var url = contextPath + "/coercionCaseCtrl/performsEditInput.action?"+ $.param(params);
    top.bsWindow(url, "行政强制案件填报", {
      width : "1000",
      height : "500",
      buttons : [ {
          name : "关闭",
          classStyle : "btn-alert-gray"
      }],
      submit : function(v, h) {
          var cw = h[0].contentWindow;
          if (v == "关闭") {
              $('#self_performSee_datagrid').datagrid('reload');
              return true;
          }
      }
  });
    
}*/
/**
 * 删除直接强制行为
 * @param id
 */
function deletePerformPage(id){
    var result = tools.requestJsonRs("/coercionCaseCtrl/deletePerformById.action",{id:id});
    if(result.rtState){
        $.MsgBox.Alert_auto("删除成功！");
        $('#self_performSee_datagrid').datagrid('reload');
    }else {
        $.MsgBox.Alert_auto("删除失败！");
    }
}


/**************************************************申请法院强制执行*************************************************/
/**
 * 加载法院强制行为基础信息
 */
function initCourtPerformDatagrid() {
    performDatagrid = $('#court_performSee_datagrid')
            .datagrid(
                    {  
                        
                        url : contextPath+ '/coercionCaseCtrl/courtPerformsSearch.action',
                        queryParams : {
                            subjectId : subjectId,
                            id : coercionCaseId,
                            caseSourceType : 200,
                            caseSourceId : srcCaseId
                        },
                        pagination : false,
                        singleSelect : true,
                        view : window.EASYUI_DATAGRID_NONE_DATA_TIP,
                        border : false,
                        idField:'formId',//主键列 
                        fitColumns : true,
                        // 列是否进行自动宽度适应
                        nowrap : true,
                        striped:true,
                        onLoadSuccess : function(data) {
                            if(data.total==1){
                                $('#countPerformAdd_button').hide();
                            }else{
                                $('#countPerformAdd_button').show();
                            }
                        },
                        columns : [ [
                                {
                                    field : 'punishCodeBefore',
                                    title : '原决定书文号',
                                    halign : 'center',
                                    align : 'center',
                                    width : 30
                                },
                                {
                                    field : 'punishDateBeforeStr',
                                    title : '原决定书日期',
                                    halign : 'center',
                                    align : 'center',
                                    width : 20

                                },
                                {
                                    field : 'applyDateStr',
                                    title : '申请法院强制执行日期 ',
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
                                    field : 'performType',
                                    title : '申请法院强制执行方式',
                                    halign : 'center',
                                    align : 'left',
                                    width : 15,
                                    formatter : function(e, rowData) {
                                        var textStr;
                                        if (rowData.performType == '01') {
                                            textStr = "加处罚款或滞纳金";
                                        }
                                        if (rowData.performType == '02') {
                                            textStr = "划拨存款、汇款";
                                        }
                                        if (rowData.performType == '03') {
                                            textStr = "拍卖或者依法处理查封、扣押的场所、设施或者财物";
                                        }
                                        if (rowData.performType == '04') {
                                            textStr = "排除妨碍、恢复原状";
                                        }
                                        if (rowData.performType == '05') {
                                            textStr = "代履行";
                                        }
                                        if (rowData.performType == '06') {
                                            textStr = "其他强制执行方式";
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
                                        var optStr = "" +
                                            "<span title='编辑'><a href=\"#\" onclick=\"doOpenCountPerformPage('"
                                            + rowData.id+"','"+'修改'
                                            + "')\"><i class='fa fa-pencil common-yellow'></i></a></span>";
                                        return optStr;
                                    }
                                } ] ]
                    });
}

/**
 * 是否已申请法院强制执行
 */
/*function applyCourtPerform(){
    var params = {
            caseSourceId : srcCaseId,
            caseSourceType : srcCaseType,
            subjectId : subjectId,
            id : coercionCaseId
        }
    var result = tools.requestJsonRs("/coercionCaseCtrl/isCourtPerforms.action",params);
    if(result){
        $('#isApplyCourtPerform').text("是");
    }else {
        $('#isApplyCourtPerform').text("否");
    }
}*/
/*saveCourtPerform
 * 法院强制执行维护（新增）
 */
function doOpenCountPerformPage(id,name){
    var params = {
        caseSourceId : srcCaseId,
        caseSourceType : srcCaseType,
        subjectId : subjectId,
        id : coercionCaseId,
        courtPerformId:id,
        caseCode: caseCode,
        departmentId:departmentId,
    }
    var url = contextPath + "/supervise/adminCoercion/perform_court_input.jsp?"+ $.param(params);
    top.bsWindow(url, name, {
      width : "1000",
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
              var status = cw.saveCourtPerform();
              $("#court_performSee_datagrid").datagrid("reload");
              if (status) {
                  return true;
              }
          } else if (v == "关闭") {
              $('#court_performSee_datagrid').datagrid('reload');
              return true;
          }
      }
  });
}

/**
 * 删除法院强制行为
 * @param id
 */
function deleteCourtPerformPage(id){
    var result = tools.requestJsonRs("/coercionCaseCtrl/deleteCourtPerformById.action",{id:id});
    if(result.rtState){
        $('#court_performSee_datagrid').datagrid('reload');
        $.MsgBox.Alert_auto("删除成功！");
    }else {
        $.MsgBox.Alert_auto("删除失败！");
    }
}

/*
 * 选择标签卡
 */
/*function selectTabs(){
    var tab = $('#coercion_manage_page').tabs('getSelected');
    var index = $('#coercion_manage_page').tabs('getTabIndex',tab);
    if(coercionCaseId == null ||coercionCaseId == ''){
        initCoercionCaseId();
    }
    $('#coercion_manage_page').tabs({
        onSelect: function(title,index){
            if(coercionCaseId == null ||coercionCaseId == ''){
                initCoercionCaseId();
            }
        }
    });  // 获取选择的面板
}*/

/**
 * 返回维护页
 */
function back(){
//    top.$.MsgBox.Confirm("提示","离开后未保存数据将丢失，确定返回？",function(){
        location.href = contextPath+"/supervise/adminCoercion/coercion_manage.jsp?coercionCaseId="+coercionCaseId;
//    })
}