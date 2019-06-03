var _id;
var _type;
var _powerFormalId;
var _isNext;
var _editFlag;

var _mergePower = [];

var confirmGrid = null;

var mergeGrid = null;
var mergeSelectedGrid = null;

var gistLawDetails = [];
var punishLawDetails = [];
var settingLawDetails = [];
var subjectGrid = null;
var otherSubjectGrid = null;
var deputeGrid = null;
var selectedGrid = null;

var subjectIds = [];

var isCreate = false;
/**
 * 页面初始化调用入口
 * @returns
 */

var countPic = 0;
function doInit() {
    _id = $('#id').val();
    _type = $('#type').val();
    _isNext = $('#isNext').val();
    _powerFormalId = $('#powerFormalId').val();
    _editFlag = $('#editFlag').val();
    
    if(_type == '40' && _isNext == 0) {
        doInitSearchTypeCombobox();
//        $('#power_tabs').tabs('enableTab', 0);
//        $('#power_tabs').tabs('disableTab', 1);
//        $('#power_tabs').tabs('disableTab', 2);
//        $('#power_tabs').tabs('disableTab', 3);
        
        doInitMergeSelectedDatagrid({ids: 'empty'});
    } else if((_type == '20' || _type == '30' || _type == '50' ) && _isNext == 0) {
        doInitSearchTypeCombobox();
        doInitConfirmDatagrid();
//        $('#power_tabs').tabs('enableTab', 0);
//        $('#power_tabs').tabs('disableTab', 1);
//        $('#power_tabs').tabs('disableTab', 2);
//        $('#power_tabs').tabs('disableTab', 3);
    } else {
//        $('#power_tabs').tabs('disableTab', 0);
    }
    
    doInitTypeComboBox();
    doInitLevelCombobox();
    doInitMoldCombobox();
    doInitSubjectCombobox();
    countPic = 0;
/*    doInitMultipleUpload("powerAttachment", _id, "power_flowsheet_table");*/
    
    gistLawDetails = [];
    punishLawDetails = [];
    settingLawDetails = [];
    subjectIds = [];
    
    if(_isNext == 1 && _type == '30') {
        var json = tools.requestJsonRs("/powerCtrl/getById.action?id=" + _powerFormalId);
        if(json.rtState) {
            doInitPage(json.rtData);
        }
    } else {
        var json = tools.requestJsonRs("/powerTempCtrl/getById.action?id=" + _id);
        if(json.rtState) {
            doInitPage(json.rtData);
        }
    }
    
    doInitSubjectGrid();
    doInitOtherSubjectGrid();
    doInitDeputeGrid();
}

/**
 * 页面初始化加载数据方法
 * @param pageData
 * @returns
 */
function doInitPage(pageData) {
    
    bindJsonObj2Easyui(pageData, 'power_form', 'id');
    
/*    var attachIds = "";
    var flowsheetArray = pageData.flowsheetArray;
    for(var i = 0; i < flowsheetArray.length; i++) {
        attachIds = attachIds + flowsheetArray[i].fileId + ","; 
    }
    attachIds = attachIds.substring(0, attachIds.length - 1);
    initAttachmentInfo(attachIds, "power_flowsheet_table");
    doInitPowerFlowsheet(flowsheetArray);*/
    
    $('#powerDetail').combobox('setValues', pageData.powerDetail.split(','));
        
    if(pageData.powerType == "01") {
        if(pageData.gistIds != null && pageData.gistIds != '') {
            gistLawDetails = pageData.gistIds.split(",");
            doInitGistLawDetailGridByIds({ids: pageData.gistIds});
        } else{
            doInitGistLawDetailGridByIds({ids: 'empty'});
        }
        if(pageData.punishIds != null && pageData.punishIds != '') {
            punishLawDetails = pageData.punishIds.split(",");
            doInitPunishLawDetailGridByIds({ids: pageData.punishIds});
        } else{
            doInitPunishLawDetailGridByIds({ids: 'empty'});
        }
        settingLawDetails = [];
        
    } else {
        gistLawDetails = [];
        punishLawDetails = [];
        if(pageData.settingIds != null && pageData.settingIds != '') {
            settingLawDetails = pageData.settingIds.split(",");
            doInitSettingLawDetailGridByIds({ids: pageData.settingIds});
        } else{
            doInitSettingLawDetailGridByIds({ids: 'empty'});
        }
    }
    
    if(pageData.subjectIds != null && pageData.subjectIds != "") {
        subjectIds = pageData.subjectIds.split(",");
        doInitSelectedGrid({ids: pageData.subjectIds,specialFlag: 'true'});
    } else {
        subjectIds = [];
        doInitSelectedGrid({ids: "empty",specialFlag: 'true'});
    }
}

function doInitDisable() {
    $('#name').textbox('disable');
    $('#powerType').combobox('disable');
    $('#powerDetail').combobox('disable');
    $('#subjectDesc').textbox('disable');
    $('#subjectId').combobox('disable');
    $('#powerLevel').combobox('disable');
    $('#powerMold').combobox('disable');
//    $('#uploadHolder').css('display', 'none');
    
    $('#settingDatagrid_add_btn').css('display', 'none');
    $('#gistDatagrid_add_btn').css('display', 'none');
    $('#punishDatagrid_add_btn').css('display', 'none');
    
    $('#selectedSubject_add_btn').css('display', 'none');
    $('#removeSubject_add_btn').css('display', 'none');
}

function doSearchConfrim() {
    var params = {
        code: $('#search_powerCode').textbox('getValue'),
        name: $('#search_powerName').textbox('getValue'),
        powerType: $('#search_powerType').combobox('getValue')
    };
    
    doInitConfirmDatagrid(params);
}

function doInitConfirmDatagrid(params) {
    if(params == null) {
        params = {
            powerType: $('#search_powerType').combobox('getValue')
        };
    }
    confirmGrid = $('#confirm_datagrid').datagrid({
        url: contextPath + '/powerCtrl/listByPage.action',
        queryParams: params,
        pagination: true,
        singleSelect: true,
        pageSize : 20,
        pageList : [ 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
        view: window.EASYUI_DATAGRID_NONE_DATA_TIP,
        toolbar: '#toolbar',
        // 工具条对象
        checkbox: true,
        border: false,
        /* idField:'formId',//主键列 */
        fitColumns: true,
        // 列是否进行自动宽度适应
        nowrap: true,
        striped: true,
        onLoadSuccess: function(data) {
        },
        columns: [[{
            field: 'id',
            checkbox: true,
            title: "ID",
            width: 20
        },
        {
            field: '_index',
            width: 5,
            title: '序号',
            align : 'center',
            halign : 'center',
            formatter: function(value, rowData, rowIndex) {
                return rowIndex + 1;
            }
        },
        {
            field: 'code',
            title: '职权编号',
            width: 15,
            align : 'center',
            halign : 'center'
        },
        {
            field: 'name',
            title: '职权名称',
            width: 60,
            formatter: function(e, rowData) {
                var lins = "<a href='#' onclick='doOpenFormalShowPage(\"" + rowData.id + "\")'>" + rowData.name + "</a>"
                return lins;
            },
            halign : 'center'
        },
        {
            field: 'powerType',
            title: '职权类型 ',
            width: 10,
            align : 'center',
            halign : 'center'
        },
        {
            field: 'powerDetail',
            title: '职权分类名称',
            width: 30,
            halign : 'center',
            formatter: function(value, rowData, rowIndex) {
                var lins = "";
                if(value != "" && value != "") {
                    lins = "<span title='" + value + "'>" + value + "</span>";
                }
                return lins;
            }
        }]]
    });
}


function doInitPowerFlowsheet(flows) {
    for(var index in flows) {
        $('#flowsheetComboBox' + flows[index].fileId).combobox('setValue', flows[index].fileType);
    }
}

/**
 * 初始化附件列表
 * @param model
 * @param modelId
 * @param elemId
 * @returns
 */
function initAttachmentInfo(ids, elemId) {
    var params = {ids: ids};
    var json = tools.requestJsonRs("/commonCtrl/attachmentByIds.action", params);   
    if(json.rtState) {
        var attachList = json.rtData;
        $.each(attachList, function(i, o){
            // 添加附件属性
            addAttachmentProp(o, elemId);
        });
    }
}

/**
 * 职权类型下拉框加载方法
 * @returns
 */
function doInitTypeComboBox() {
    var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "POWER_TYPE"});
    if(json.rtState) {
        json.rtData.unshift({codeNo: -1, codeName: "请选择"});
        $('#powerType').combobox({
            data: json.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
            panelHeight:'200px',
            onLoadSuccess:function(){
                var powerType = $('#powerType').combobox('getValue');
                if(powerType != "") {
                    var params = {
                        parentCodeNo: "POWER_DETAIL",
                        codeNo: powerType
                    };
                    doInitDetailCombobox(params);
                    doInitChangePowerType(powerType);
                } else {
                    $('#powerType').combobox('setValue', -1);
                }
                
            },
            onChange: function() {
                var powerType = $('#powerType').combobox('getValue');
                if(powerType != "") {
                    var params = {
                        parentCodeNo: "POWER_DETAIL",
                        codeNo: powerType
                    };
                    doInitDetailCombobox(params);
                    doInitChangePowerType(powerType);
                }
            }
        });
    }
}
function doInitSearchTypeCombobox() {
    var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "POWER_TYPE"});
    if(json.rtState) {
        $('#search_powerType').combobox({
            data: json.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
            panelHeight: 'auto',
            onLoadSuccess: function(data) {
                $('#search_powerType').combobox('setValue', data[0].codeNo);
            }
        });
    }
}

/**
 * 职权分类下拉框加载方法
 * @param parentCodeNo
 * @param codeNo
 * @returns
 */
function doInitDetailCombobox(params) {
    var result = tools.requestJsonRs("/sysCode/getSysParaByParentCode.action", params);
    if(result.rtState) {
        $('#powerDetail').combobox({
            data: result.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
            panelHeight:'200px',
            formatter: function (row) {
                var opts = $(this).combobox('options');
                return '<input type="checkbox" class="combobox-checkbox" style="margin-right:5px;cursor:pointer;">' + row[opts.textField]
            },

            onShowPanel: function () {
                var opts = $(this).combobox('options');
                var target = this;
                var values = $(target).combobox('getValues');
                $.map(values, function (value) {
                    var el = opts.finder.getEl(target, value);
                    el.find('input.combobox-checkbox')._propAttr('checked', true);
                })
            },
            onLoadSuccess: function () {
                var opts = $(this).combobox('options');
                var target = this;
                var values = $(target).combobox('getValues');
                $.map(values, function (value) {
                    var el = opts.finder.getEl(target, value);
                    el.find('input.combobox-checkbox')._propAttr('checked', true);
                })
            },
            onSelect: function (row) {
                var opts = $(this).combobox('options');
                var el = opts.finder.getEl(this, row[opts.valueField]);
                el.find('input.combobox-checkbox')._propAttr('checked', true);
            },
            onUnselect: function (row) {
                var opts = $(this).combobox('options');
                var el = opts.finder.getEl(this, row[opts.valueField]);
                el.find('input.combobox-checkbox')._propAttr('checked', false);
            }
        });
    }
}
/**
 * 切换职权类型，影响依据列表展示
 * @param powerType
 * @returns
 */
function doInitChangePowerType(powerType) {
    if(powerType == '01' || powerType == -1) {
        $('#gist_panel').panel('open');
        $('#punish_panel').panel('open');
        $('#setting_panel').panel('close');
        
        gistLawDetails = [];
        punishLawDetails = [];
        settingLawDetails = [];
        
        doInitSettingLawDetailGridByIds({ids: "empty"});
        doInitPunishLawDetailGridByIds({ids: "empty"});
        doInitGistLawDetailGridByIds({ids: "empty"});
    } else {
        $('#gist_panel').panel('close');
        $('#punish_panel').panel('close');
        $('#setting_panel').panel('open');
        
        gistLawDetails = [];
        punishLawDetails = [];
        settingLawDetails = [];
        
        doInitSettingLawDetailGridByIds({ids: "empty"});
        doInitPunishLawDetailGridByIds({ids: "empty"});
        doInitGistLawDetailGridByIds({ids: "empty"});
    }
}
/**
 * 职权层级下拉框方法
 * @returns
 */
function doInitLevelCombobox() {
    var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "POWER_LEVEL"});
    if(json.rtState) {
        $('#powerLevel').combobox({
            data: json.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
            panelHeight:'200px',
            onChange: function() {
                var powerLevel = $('#powerLevel').combobox('getValues');
                var powerLevelText = $('#powerLevel').combobox('getText');
                if(powerLevelText != "") {
                    powerLevelText = powerLevelText.split(',');
                }
                var level = [];
                for(var i = 0; i < powerLevel.length; i++) {
                    var temp = {
                        id: powerLevel[i],
                        text: powerLevelText[i]
                    }
                    level.push(temp);
                }
            }
        });
    }
}
/**
 * 职权领域下拉框方法
 * @returns
 */
function doInitMoldCombobox() {
    var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "LAW_ENFORCEMENT_FIELD"});
    if(json.rtState) {
        $('#powerMold').combobox({
            data: json.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
            panelHeight:'200px'
        });
    }
}
/**
 * 职权管理主体下拉框方法
 * @returns
 */
function doInitSubjectCombobox() {
    var json = tools.requestJsonRs("/commonCtrl/getRelations.action");
    if(json.rtState) {
        $('#subjectId').combobox({
            data: json.rtData,
            valueField: 'businessSubjectId',
            textField: 'businessSubjectName',
            panelHeight:'200px',
            onLoadSuccess:function(data){
              //默认选中第一个
                var array = $('#subjectId').combobox("getData");
                $('#subjectId').combobox('setValue', array[0]);
            }
        });
    }
}
/**
 * 附件上传
 * @param model 后台文件路径文件夹
 * @param modelId 查询文件关键字
 * @param elemId 前台展示元素ID
 * @returns
 */
function doInitMultipleUpload(model, modelId, elemId) {
  //多附件快速上传
    swfUploadObj = new TeeSWFUpload({
        fileContainer:"fileContainer",//文件列表容器
        uploadHolder:"uploadHolder",//上传按钮放置容器
        valuesHolder:"attachmentSidStr",//附件主键返回值容器，是个input
        quickUpload:true,//快速上传
        showUploadBtn:false,//不显示上传按钮
        queueComplele:function(){//队列上传成功回调函数，可有可无
        },
        uploadSuccess:function(files){//上传成功
            addAttachmentProp(files, elemId);
        },
        uploadStart:function(file,progress){//刚开始上传
            if(file.size <= 102400) {
                countPic++;
                if(countPic > 5){
                    $.MsgBox.Alert_auto("最多上传5个附件");
                    swfUploadObj.swf.cancelUpload(file.id);//取消对指定文件的上传
                }
            } else {
                $.MsgBox.Alert_auto("附件：" + file.name + "超出最大范围");
                swfUploadObj.swf.cancelUpload(file.id);//取消对指定文件的上传
            }
        },
        file_types:"*.JPG;*.GIF;*.JPEG;*.png;*.jpg;*.gif;*.jpeg;*.PNG;*.doc;*.docx;*.pdf;*.txt",
        renderFiles:false,//渲染附件
        post_params:{model:model, modelId: modelId}//后台传入值，model为模块标志
    });
}
/**
 * 上传完毕后加载已经上传的文件
 * @param obj 文件对象
 * @param elemId 展示元素ID
 * @returns
 */
function addAttachmentProp(obj, elemId) {
    var mainObj = $('#' + elemId);
    var page = "";
    page = page + '<tr id="flowsheet' + obj.sid + '">';
    page = page + '<td class="power-table-label">流程图类型：</td>';
    page = page + '<td style=" width: 300px;">';
    page = page + '<input type="hidden" name="fileId" value="' + obj.sid + '" />';
    page = page + '<input type="hidden" name="fileName" value="' + obj.fileName + '" />';
    page = page + '<input class="easyui-combobox" id="flowsheetComboBox' + obj.sid + '" name="fileType" style="width: 90%;" data-options="required:true, novalidate:true, missingMessage:\'请选择流程图类型\'"/>';
    page = page + '</td>';
    page = page + '<td class="power-table-label">文件名称：</td>';
    page = page + '<td style="width: 300px;">' + '<a href="javascript:void(0);" sid="FILE' + obj.sid + '"></a>'+ '</td>';
    page = page + '</tr><tr class="power-line-height"></tr>';
    
    mainObj.append(page);
    
    obj.priv = 1+2 // 阅读、下载、删除
    var attach = tools.getAttachElement(obj, {deleteEvent:function(attachModel){
        $("#flowsheet" + attachModel.sid).remove();
    }});
    $("[sid='FILE" + obj.sid + "']").append(attach);
    
    doInitFlowsheetCombobox("flowsheetComboBox" + obj.sid, "");
}
/**
 * 加载流程图类型下拉框方法
 * @param id 元素ID
 * @param value 初始化值
 * @returns
 */
function doInitFlowsheetCombobox(id, value) {
    var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "POWER_FLOW"});
    $('#' + id).combobox({
        data: json.rtData,
        valueField: 'codeNo',
        textField: 'codeName',
        panelHeight: 'auto'
    });
}
/**
 * 打开添加职权依据页面
 * @returns
 */
function doAddGistLawDetail() {
    var url=contextPath+"/supervise/power/addLawDetail.jsp";
    top.bsWindow(url ,"添加违法依据",{width:"1000",height:"400",buttons:
        [
         	{name:"关闭",classStyle:"btn-alert-gray"},
            {name:"保存",classStyle:"btn-alert-blue"}
            
        ]
        ,submit:function(v,h){
            var cw = h[0].contentWindow;
            if( v == "保存") {
                var lawDetails = cw.saveLawDetails();
                if(lawDetails.length == 0) {
                    $.MsgBox.Alert_auto("请选择依据");
                    return false;
                } else {
                    if(gistLawDetails == null || gistLawDetails.length == 0) {
                        for(var index in lawDetails) {
                            gistLawDetails.push(lawDetails[index].id);
                        }
                    } else {
                        for(var index in lawDetails) {
                            if(gistLawDetails.indexOf(lawDetails[index].id) == -1) {
                                gistLawDetails.push(lawDetails[index].id);
                            }
                        }
                    }
                    var params = {
                        ids: gistLawDetails.join(",")
                    };
                    doInitGistLawDetailGridByIds(params);  
                    
                    return true;
                }
                
            } else if(v=="关闭"){
                return true;
            }
        }
    });
}
/**
 * 根据依据ID查询依据
 * @param params
 * @returns
 */
function doInitGistLawDetailGridByIds(params) {
    datagrid = $('#gistDatagrid').datagrid({
        url: contextPath + '/detailController/getLawDetailByIds.action',
        queryParams: params,
        pagination: false,
        singleSelect: false,
//        pageSize : 20,
//        pageList : [ 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
        view: window.EASYUI_DATAGRID_NONE_DATA_TIP,
        // 工具条对象
        checkbox: true,
        border: false,
        /* idField:'formId',//主键列 */
        fitColumns: true,
        // 列是否进行自动宽度适应
        nowrap: true,
        striped: true,
        onLoadSuccess: function(data) {
        },
        columns: [[{
            field: 'id',
            checkbox: true,
            title: "ID",
            width: 20
        },
        {
            field: '_index',
            width: 15,
            title: '序号',
            align : 'center',
            halign : 'center',
            formatter: function(value, rowData, rowIndex) {
                return rowIndex + 1;
            }
        },
        {
            field: 'lawName',
            title: '依据名称',
            width: 60,
            halign : 'center',
            formatter: function(value, rowData, rowIndex) {
                if(value == null || value == "") {
                    return "";
                } else {
                    var lins = "<span title='" + value + "'>" + value + "</span>";
                    return lins;
                }
            }
        },
        {
            field: 'detailStrip',
            title: '条',
            width: 15,
            align : 'center',
            halign : 'center'
        },
        {
            field: 'detailFund',
            title: '款',
            width: 15,
            formatter: function(value, rowData, rowIndex) { 
                if(value == 0) {
                    return "";
                } else {
                    return value;
                }
            },
            align : 'center',
            halign : 'center'
        },
        {
            field: 'item',
            title: '项',
            width: 15,
            formatter: function(value, rowData, rowIndex) { 
                if(value == 0) {
                    return "";
                } else {
                    return value;
                }
            },
            align : 'center',
            halign : 'center'
        },
        {
            field: 'content',
            title: '内容',
            width: 80,
            halign : 'center',
            formatter: function(value, rowData, rowIndex) {
                if(value == null || value == "") {
                    return "";
                } else {
                    var lins = "<span title='" + value + "'>" + value + "</span>";
                    return lins;
                }
            }
        },
        {
            field: '_opt',
            title: '操作',
            width: 20,
            formatter: function(value, rowData, rowIndex) { 
                var lins = "<span title='删除'><a href='javaScript:void(0);' onclick='deleteGist(\"" + rowData.id + "\")' ><i class='fa fa-trash-o common-red'></i></a></span>"
                return lins;
            },
            align : 'center',
            halign : 'center'
        }]]
    });
}
/**
 * 删除已选依据
 * @param id
 * @returns
 */
function deleteGist(id) {
    for(var index in gistLawDetails) {
        if(gistLawDetails[index] == id) {
            gistLawDetails.splice(index, 1);
            break;
        }
    }
    var params;
    if(gistLawDetails.length == 0) {
        params = {ids: "empty"};
    }else {
        params = {ids: gistLawDetails.join(",")};
    }
    doInitGistLawDetailGridByIds(params);
}
/**
 * 打开添加处罚依据页面
 * @returns
 */
function doAddPunishLawDetail() {
    var url=contextPath+"/supervise/power/addLawDetail.jsp";
    top.bsWindow(url ,"添加处罚依据",{width:"1000",height:"400",buttons:
        [
         	{name:"关闭",classStyle:"btn-alert-gray"},
            {name:"保存",classStyle:"btn-alert-blue"}
            
        ]
        ,submit:function(v,h){
            var cw = h[0].contentWindow;
            if( v == "保存") {
                var lawDetails = cw.saveLawDetails();
                if(lawDetails.length == 0) {
                    $.MsgBox.Alert_auto("请选择依据");
                    return false;
                } else {
                    if(punishLawDetails == null || punishLawDetails.length == 0) {
                        for(var index in lawDetails) {
                            punishLawDetails.push(lawDetails[index].id);
                        }
                    } else {
                        for(var index in lawDetails) {
                            if(punishLawDetails.indexOf(lawDetails[index].id) == -1) {
                                punishLawDetails.push(lawDetails[index].id);
                            }
                        }
                    }
                    var params = {
                        ids: punishLawDetails.join(",")
                    };
                    doInitPunishLawDetailGridByIds(params);  
                    
                    return true;
                }
                
            } else if(v=="关闭"){
                return true;
            }
        }
    });
}
/**
 * 根据依据ID查询处罚依据ID
 * @param params
 * @returns
 */
function doInitPunishLawDetailGridByIds(params) {
    datagrid = $('#punishDatagrid').datagrid({
        url: contextPath + '/detailController/getLawDetailByIds.action',
        queryParams: params,
        pagination: false,
        singleSelect: false,
//        pageSize : 15,
//        pageList : [ 15, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
        view: window.EASYUI_DATAGRID_NONE_DATA_TIP,
        // 工具条对象
        checkbox: true,
        border: false,
        /* idField:'formId',//主键列 */
        fitColumns: true,
        // 列是否进行自动宽度适应
        nowrap: true,
        striped: true,
        onLoadSuccess: function(data) {
        },
        columns: [[{
            field: 'id',
            checkbox: true,
            title: "ID",
            width: 20
        },
        {
            field: '_index',
            width: 15,
            title: '序号',
            align : 'center',
            halign : 'center',
            formatter: function(value, rowData, rowIndex) {
                return rowIndex + 1;
            }
        },
        {
            field: 'lawName',
            title: '依据名称',
            width: 60,
            halign : 'center',
            formatter: function(value, rowData, rowIndex) {
                if(value == null || value == "") {
                    return "";
                } else {
                    var lins = "<span title='" + value + "'>" + value + "</span>";
                    return lins;
                }
            }
        },
        {
            field: 'detailStrip',
            title: '条',
            width: 15,
            align : 'center',
            halign : 'center'
        },
        {
            field: 'detailFund',
            title: '款',
            width: 15,
            align : 'center',
            halign : 'center',
            formatter: function(value, rowData, rowIndex) { 
                if(value == 0) {
                    return "";
                } else {
                    return value;
                }
            }
        },
        {
            field: 'item',
            title: '项',
            width: 15,
            align : 'center',
            halign : 'center',
            formatter: function(value, rowData, rowIndex) { 
                if(value == 0) {
                    return "";
                } else {
                    return value;
                }
            }
        },
        {
            field: 'content',
            title: '内容',
            width: 80,
            halign : 'center',
            formatter: function(value, rowData, rowIndex) {
                if(value == null || value == "") {
                    return "";
                } else {
                    var lins = "<span title='" + value + "'>" + value + "</span>";
                    return lins;
                }
            }
        },
        {
            field: '_opt',
            title: '操作',
            width: 20,
            align : 'center',
            halign : 'center',
            formatter: function(value, rowData, rowIndex) { 
                var lins = "<span title='删除'><a href='javaScript:void(0);' onclick='deletePunish(\"" + rowData.id + "\")' ><i class='fa fa-trash-o common-red'></i></a></span>"
                return lins;
            }
        }]]
    });
}
/**
 * 删除已选处罚依据
 * @param id
 * @returns
 */
function deletePunish(id) {
    for(var index in punishLawDetails) {
        if(punishLawDetails[index] == id) {
            punishLawDetails.splice(index, 1);
            break;
        }
    }
    var params;
    if(gistLawDetails.length == 0) {
        params = {ids: "empty"};
    }else {
        params = {ids: punishLawDetails.join(",")};
    }
    doInitPunishLawDetailGridByIds(params);
}
/**
 * 打开设定依据页面
 * @returns
 */
function doAddSettingLawDetail() {
    var url=contextPath+"/supervise/power/addLawDetail.jsp";
    top.bsWindow(url ,"添加设定依据",{width:"1000",height:"400",buttons:
        [
			{name:"关闭",classStyle:"btn-alert-gray"},
            {name:"保存",classStyle:"btn-alert-blue"}
            
        ]
        ,submit:function(v,h){
            var cw = h[0].contentWindow;
            if( v == "保存") {
                var lawDetails = cw.saveLawDetails();
                if(lawDetails.length == 0) {
                    $.MsgBox.Alert_auto("请选择依据");
                    return false;
                } else {
                    if(settingLawDetails == null || settingLawDetails.length == 0) {
                        for(var index in lawDetails) {
                            settingLawDetails.push(lawDetails[index].id);
                        }
                    } else {
                        for(var index in lawDetails) {
                            if(settingLawDetails.indexOf(lawDetails[index].id) == -1) {
                                settingLawDetails.push(lawDetails[index].id);
                            }
                        }
                    }
                    var params = {
                        ids: settingLawDetails.join(",")
                    };
                    doInitSettingLawDetailGridByIds(params);  
                    
                    return true;
                }
                
            } else if(v=="关闭"){
                return true;
            }
        }
    });
}
/**
 * 根据依据ID查询设定依据
 * @param params
 * @returns
 */
function doInitSettingLawDetailGridByIds(params) {
    datagrid = $('#settingDatagrid').datagrid({
        url: contextPath + '/detailController/getLawDetailByIds.action',
        queryParams: params,
        pagination: false,
        singleSelect: false,
//        pageSize : 15,
//        pageList : [ 15, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
        view: window.EASYUI_DATAGRID_NONE_DATA_TIP,
        // 工具条对象
        checkbox: true,
        border: false,
        /* idField:'formId',//主键列 */
        fitColumns: true,
        // 列是否进行自动宽度适应
        nowrap: true,
        striped: true,
        onLoadSuccess: function(data) {
        },
        columns: [[{
            field: 'id',
            checkbox: true,
            title: "ID",
            width: 20
        },
        {
            field: '_index',
            width: 15,
            title: '序号',
            align : 'center',
            halign : 'center',
            formatter: function(value, rowData, rowIndex) {
                return rowIndex + 1;
            }
        },
        {
            field: 'lawName',
            title: '依据名称',
            width: 60,
            halign : 'center',
            formatter: function(value, rowData, rowIndex) {
                if(value == null || value == "") {
                    return "";
                } else {
                    var lins = "<span title='" + value + "'>" + value + "</span>";
                    return lins;
                }
            }
        },
        {
            field: 'detailStrip',
            title: '条',
            width: 15,
            align : 'center',
            halign : 'center'
        },
        {
            field: 'detailFund',
            title: '款',
            width: 15,
            align : 'center',
            halign : 'center',
            formatter: function(value, rowData, rowIndex) { 
                if(value == 0) {
                    return "";
                } else {
                    return value;
                }
            }
        },
        {
            field: 'item',
            title: '项',
            width: 15,
            align : 'center',
            halign : 'center',
            formatter: function(value, rowData, rowIndex) { 
                if(value == 0) {
                    return "";
                } else {
                    return value;
                }
            }
        },
        {
            field: 'content',
            title: '内容',
            width: 80,
            halign : 'center',
            formatter: function(value, rowData, rowIndex) {
                if(value == null || value == "") {
                    return "";
                } else {
                    var lins = "<span title='" + value + "'>" + value + "</span>";
                    return lins;
                }
            }
        },
        {
            field: '_opt',
            title: '操作',
            width: 20,
            align : 'center',
            halign : 'center',
            formatter: function(value, rowData, rowIndex) { 
                var lins = "<span title='删除'><a href='javaScript:void(0);' onclick='deleteSetting(\"" + rowData.id + "\")' ><i class='fa fa-trash-o common-red'></i></a></span>"
                return lins;
            }
        }]]
    });
}
/**
 * 删除已选处罚依据
 * @param id
 * @returns
 */
function deleteSetting(id) {
    for(var index in settingLawDetails) {
        if(settingLawDetails[index] == id) {
            settingLawDetails.splice(index, 1);
            break;
        }
    }
    
    var params;
    if(gistLawDetails.length == 0) {
        params = {ids: "empty"};
    }else {
        params = {ids: settingLawDetails.join(",")};
    }
    doInitSettingLawDetailGridByIds(params);
}
/**
 * 查询本系统主体
 * @returns
 */
function searchSubject() {
    var params = {
        isDepute: 0,
        id: $('#subjectId').combobox('getValue'),
        type: '01',
        subName: $('#subject_name_query').val(),
        selectedSubjectId: subjectIds.join(",")
    };
    
    doInitSubjectGrid(params);
}

/**
 * 初始化本系统实施主体列表
 * @returns
 */
function doInitSubjectGrid(params) {
    if(params == null) {
        params = {
            isDepute: 0,
            id: $('#subjectId').combobox('getValue'),
            type: '01',
            selectedSubjectId: subjectIds.join(","),
            specialFlag: 'true'
        };
    }
    
    var subjectGrid = $('#subject_datagrid').datagrid({
        url: contextPath + '/subjectCtrl/listByPage.action',
        queryParams: params,
        pagination: true,
        singleSelect: false,
        pageSize : 8,
        pageList : [ 8, 10, 20 ],
        view: window.EASYUI_DATAGRID_NONE_DATA_TIP,
        // 工具条对象
        toolbar: "#subject_toolbar",
        checkbox: true,
        border: false,
        /* idField:'formId',//主键列 */
        fitColumns: true,
        // 列是否进行自动宽度适应
        nowrap: true,
        striped: true,
        onLoadSuccess: function(data) {
        },
        columns: [[{
            field: 'id',
            checkbox: true,
            title: "ID",
            width: 20
        },
        {
            field: '_index',
            width: 10,
            title: '序号',
            align : 'center',
            halign : 'center',
            formatter: function(value, rowData, rowIndex) {
                return rowIndex + 1;
            }
        },
        {
            field: 'subName',
            title: '主体名称',
            width: 70,
            halign : 'center',
            formatter: function(value, rowData, rowIndex) {
                if(value == null || value == "") {
                    return "";
                } else {
                    var lins = "<span title='" + value + "'>" + value + "</span>";
                    return lins;
                }
            }
        }]]
    });
}

/**
 * 查询其他系统实施主体
 * @returns
 */
function searchOtherSubject() {
    var params = {
        isDepute: 0,
        id: $('#subjectId').combobox('getValue'),
        type: '02',
        subName: $('#other_subject_name_query').val(),
        selectedSubjectId: subjectIds.join(",")
    };
    
    doInitOtherSubjectGrid(params);
}

/**
 * 初始化其他系统实施主体
 * @returns
 */
function doInitOtherSubjectGrid(params) {
    if(params == null) {
        params = {
            isDepute: 0,
            id: $('#subjectId').combobox('getValue'),
            type: '02',
            selectedSubjectId: subjectIds.join(","),
            specialFlag: 'true'
        };
    }
    
    var subjectGrid = $('#other_subject_datagrid').datagrid({
        url: contextPath + '/subjectCtrl/listByPage.action',
        queryParams: params,
        pagination: true,
        singleSelect: false,
        pageSize : 8,
        pageList : [ 8, 10, 20 ],
        view: window.EASYUI_DATAGRID_NONE_DATA_TIP,
        // 工具条对象
        toolbar: '#other_subject_toolbar',
        checkbox: true,
        border: false,
        /* idField:'formId',//主键列 */
        fitColumns: true,
        // 列是否进行自动宽度适应
        nowrap: true,
        striped: true,
        onLoadSuccess: function(data) {
        },
        columns: [[{
            field: 'id',
            checkbox: true,
            title: "ID",
            width: 20
        },
        {
            field: '_index',
            width: 10,
            title: '序号',
            align : 'center',
            halign : 'center',
            formatter: function(value, rowData, rowIndex) {
                return rowIndex + 1;
            }
        },
        {
            field: 'subName',
            title: '其他主体名称',
            width: 70,
            halign : 'center',
            formatter: function(value, rowData, rowIndex) {
                if(value == null || value == "") {
                    return "";
                } else {
                    var lins = "<span title='" + value + "'>" + value + "</span>";
                    return lins;
                }
            }
        }]]
    });
}

/**
 * 查询其他系统实施主体
 * @returns
 */
function searchDepute() {
    var params = {
        isDepute: 1,
        subName: $('#depute_name_query').val(),
        selectedSubjectId: subjectIds.join(",")
    };
    
    doInitDeputeGrid(params);
}

/**
 * 初始化委托组织列表
 * @returns
 */
function doInitDeputeGrid(params) {
    if(params == null) {
        params = {
            isDepute: 1,
            selectedSubjectId: subjectIds.join(","),
            specialFlag: 'true'
        };
    }
    
    var subjectGrid = $('#depute_datagrid').datagrid({
        url: contextPath + '/subjectCtrl/listByPage.action',
        queryParams: params,
        pagination: true,
        singleSelect: false,
        pageSize : 8,
        pageList : [ 8, 10, 20 ],
        view: window.EASYUI_DATAGRID_NONE_DATA_TIP,
        // 工具条对象
        toolbar: '#depute_toolbar',
        checkbox: true,
        border: false,
        /* idField:'formId',//主键列 */
        fitColumns: true,
        // 列是否进行自动宽度适应
        nowrap: true,
        striped: true,
        onLoadSuccess: function(data) {
        },
        columns: [[{
            field: 'id',
            checkbox: true,
            title: "ID",
            width: 20
        },
        {
            field: '_index',
            width: 10,
            title: '序号',
            align : 'center',
            halign : 'center',
            formatter: function(value, rowData, rowIndex) {
                return rowIndex + 1;
            }
        },
        {
            field: 'subName',
            title: '受委托组织名称',
            width: 70,
            halign : 'center',
            formatter: function(value, rowData, rowIndex) {
                if(value == null || value == "") {
                    return "";
                } else {
                    var lins = "<span title='" + value + "'>" + value + "</span>";
                    return lins;
                }
            }
        }]]
    });
}
/**
 * 初始化选中的主体列表
 * @param params
 * @returns
 */
function doInitSelectedGrid(params) {
    debugger;
    var subjectGrid = $('#selected_datagrid').datagrid({
        url: contextPath + '/subjectCtrl/listByPage.action',
        queryParams: params,
        pagination: true,
        singleSelect: false,
        pageSize : 8,
        pageList : [ 8, 10, 20 ],
        view: window.EASYUI_DATAGRID_NONE_DATA_TIP,
        // 工具条对象
        checkbox: true,
        border: true,
        /* idField:'formId',//主键列 */
        fitColumns: true,
        // 列是否进行自动宽度适应
        nowrap: true,
        striped: true,
        onLoadSuccess: function(data) {
        },
        columns: [[{
            field: 'id',
            checkbox: true,
            title: "ID",
            width: 20
        },
        {
            field: '_index',
            width: 10,
            title: '序号',
            align : 'center',
            halign : 'center',
            formatter: function(value, rowData, rowIndex) {
                return rowIndex + 1;
            }
        },
        {
            field: 'subName',
            title: '已选主体',
            width: 70,
            halign : 'center',
            formatter: function(value, rowData, rowIndex) {
                if(value == null || value == "") {
                    return "";
                } else {
                    var lins = "<span title='" + value + "'>" + value + "</span>";
                    return lins;
                }
            }
        }]]
    });
}
/**
 * 选中主体
 * @returns
 */
function selectedSubject() {
    var tab = $('#subject_tabs').tabs('getSelected');
    var index = $('#subject_tabs').tabs('getTabIndex',tab);
    
    var subjects = [];
    if(index == 0) {
        subjects = $('#subject_datagrid').datagrid('getSelections');
    } else if(index == 1) {
        subjects = $('#other_subject_datagrid').datagrid('getSelections');
    } else if(index == 2) {
        subjects = $('#depute_datagrid').datagrid('getSelections');
    }
    
    if(subjectIds == null || subjectIds.length == 0) {
        for(var i = 0; i < subjects.length; i++) {
            subjectIds.push(subjects[i].id);
        }
    } else {
        for(var i = 0; i < subjects.length; i++) {
            if(subjectIds.indexOf(subjects[i].id) == -1) {
                subjectIds.push(subjects[i].id);
            }
        }
    }
    
    var params = {
        ids: subjectIds.join(","),
        specialFlag:'true'
    };
    
    debugger;
    doInitSelectedGrid(params);
    
    doInitSubjectGrid();
    doInitOtherSubjectGrid();
    doInitDeputeGrid();
}
/**
 * 移除主体
 * @returns
 */
function removeSubject() {
    var subjects = $('#selected_datagrid').datagrid('getSelections');
   
    if(subjectIds != null && subjectIds.length != 0) {
        for(var i = 0; i < subjects.length; i++) {
            subjectIds.splice($.inArray(subjects[i].id, subjectIds), 1);
            //$.inArray(subjects[index].id, subjectIds)
        }
        var params;
        if(subjectIds.length == 0) {
            params = {
                ids: 'empty'
            };
        } else {
            params = {
                ids: subjectIds.join(","),
                specialFlag: "true"
            };
        }
        
        doInitSelectedGrid(params);
        
        doInitSubjectGrid();
        doInitOtherSubjectGrid();
        doInitDeputeGrid();
    }
   
    
}

function doSelectedConfirmPower() {
    var powerFormal = $('#confirm_datagrid').datagrid('getSelected');
    if(powerFormal != null && powerFormal != '') {
        
        _powerFormalId = powerFormal.id;

//        doInitMultipleUpload("powerAttachment", _powerFormalId, "power_flowsheet_table");
        
        if(_type == "50") {
            doInitDisable();
        }
        
        $('#power_tabs').tabs('disableTab', 0);
        $('#power_tabs').tabs('enableTab', 1);
        $('#power_tabs').tabs('enableTab', 2);
        $('#power_tabs').tabs('enableTab', 3);
        $('#power_tabs').tabs('select', 1);
        var json = tools.requestJsonRs("/powerCtrl/getById.action?id=" + _powerFormalId);
        if(json.rtState) {
            doInitPage(json.rtData);
        }
    }
}

/**
 * 保存方法
 * @returns
 */
function save() {
    if($('#power_form').form('enableValidation').form('validate')) {
        var params = tools.formToJson($('#power_form'));
        var operations = [];
        if(_type == '10' && _editFlag == 0) {
            operations.push({
                powerTempId: _id,
                powerFormalId: '',
                optType: _type
            });
        } else if(_type == '20' && _editFlag == 0){
            operations.push({
                powerTempId: _id,
                powerFormalId: _powerFormalId,
                optType: _type
            });
        } else if(_type == '30' && _editFlag == 0){
            operations.push({
                powerTempId: _id,
                powerFormalId: _powerFormalId,
                optType: _type
            });
        } else if(_type == '40' && _editFlag == 0) {
            
            for(var i = 0; i < _mergePower.length; i++) {
                operations.push({
                    powerTempId: _id,
                    powerFormalId: _mergePower[i],
                    optType: _type
                });
            }
        } else if(_type == '50' && _editFlag == 0){
            operations.push({
                powerTempId: _id,
                powerFormalId: _powerFormalId,
                optType: _type
            });
        }
        params.operationArray = tools.parseJson2String(operations);
        params.powerDetail = $('#powerDetail').combobox('getValues').join(",");
        params.powerLevel = $('#powerLevel').combobox('getValues').join(",");
        
        var powerLevelJson = [];
        var levelGroups = $('#powerLevel').combobox('getValues');
        for(var i = 0; i < levelGroups.length; i++){
        	powerLevelJson.push({strage: levelGroups[i]});
        }
        params.powerLevelJson = tools.parseJson2String(powerLevelJson);
       
        
        var powerFlowsheet = [];
        var flowsheetFlag = false;
        $('#power_flowsheet_table tbody').find('tr').each(function() {
            var fileType = $(this).find('input[name="fileType"]').val();
            var fileId = $(this).find('input[name="fileId"]').val();
            var fileName = $(this).find('input[name="fileName"]').val();
            
            if(fileType == -1) {
                flowsheetFlag = true;
            }
            
            powerFlowsheet.push({
                id: fileId,
                type: fileType,
                name: fileName
            });
        });
        if(flowsheetFlag) {
            $.MsgBox.Alert_auto("请选择流程图类型！");
            return false;
        }
        params.powerFlowsheet = tools.parseJson2String(powerFlowsheet);
        
        if(_type != '50') {
            if(params.powerType == "01") {
                if(gistLawDetails == null || gistLawDetails.length == 0) {
                    $.MsgBox.Alert_auto("请选择违法依据！");
                    return false;
                }else {
                    params.gistIds = gistLawDetails.join(",");
                }
                
                if(punishLawDetails == null || punishLawDetails.length == 0) {
                    $.MsgBox.Alert_auto("请选择处罚依据！");
                    return false;
                } else {
                    params.punishIds = punishLawDetails.join(",");
                }
            } else {
                if(settingLawDetails == null || settingLawDetails.length == 0) {
                    $.MsgBox.Alert_auto("请选择设定依据！");
                    return false;
                } else {
                    params.settingIds = settingLawDetails.join(",");
                }
            }
            
            if(subjectIds == null || subjectIds.length == 0) {
                $.MsgBox.Alert_auto("请选择实施主体！");
                return false;
            } else {
                params.subjectIds = subjectIds.join(",");
            }
        }
        
        var resultInfo = tools.requestJsonRs("/powerTempCtrl/save.action", params);
        return resultInfo;
    }
}

function doInitMergeSelectedDatagrid(params) {
    mergeSelectedGrid = $('#mergeSelectedDatagrid').datagrid({
        url: '/powerCtrl/findPowerByIds.action',
        queryParams: params,
        pagination: true,
        singleSelect: true,
        pageSize : 10,
        pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
        view: window.EASYUI_DATAGRID_NONE_DATA_TIP,
        toolbar: '#mergeToolbar',
        // 工具条对象
        checkbox: true,
        border: false,
        /* idField:'formId',//主键列 */
        fitColumns: true,
        // 列是否进行自动宽度适应
        nowrap: true,
        striped: true,
        onLoadSuccess: function(data) {
        },
        columns: [[{
            field: 'id',
            checkbox: true,
            title: "ID",
            width: 20
        },
        {
            field: '_index',
            width: 5,
            title: '序号',
            align : 'center',
            halign : 'center',
            formatter: function(value, rowData, rowIndex) {
                return rowIndex + 1;
            }
        },
        {
            field: 'code',
            title: '职权编号',
            width: 15,
            align : 'center',
            halign : 'center'
        },
        {
            field: 'name',
            title: '职权名称',
            width: 60,
            halign : 'center',
            formatter: function(e, rowData) {
                var lins = "<a href='#' onclick='doOpenFormalShowPage(\"" + rowData.id + "\")'>" + rowData.name + "</a>"
                return lins;
            }
        },
        {
            field: 'powerType',
            title: '职权类型 ',
            width: 10,
            align : 'center',
            halign : 'center'
        },
        {
            field: 'powerDetail',
            title: '职权分类名称',
            width: 30,
            halign : 'center',
            formatter: function(value, rowData, rowIndex) {
                if(value == null || value == "") {
                    return "";
                } else {
                    var lins = "<span title='" + value + "'>" + value + "</span>";
                    return lins;
                }
            }
        },
        {
            field: '_opt',
            title: '操作',
            width: 10,
            align : 'center',
            halign : 'center',
            formatter: function(e, rowData) {
                var lins = "<span title='删除'><a href='javaScript:void(0);' onclick='deleteSelectedPower(\"" + rowData.id + "\")' ><i class='fa fa-trash-o common-red'></i></a></span>"
                return lins;
            }
        }]]
    });
}

function doOpenFormalShowPage(id) {
    var url=contextPath+"/powerCtrl/showById.action?id=" + id;
    top.bsWindow(url ,"职权查看",{width:"900",height:"400",buttons:
        [
          {name:"关闭",classStyle:"btn-alert-gray"}
        ]
        ,submit:function(v,h){
            var cw = h[0].contentWindow;
            if(v=="关闭"){
                return true;
            }
        }
    });
}

function deleteSelectedPower(id) {
    
    for(var index in _mergePower) {
        if(_mergePower[index] == id) {
            _mergePower.splice(index, 1);
        }
    }
    
    var params = null;
    if(_mergePower.length == 0) {
        params = {ids: 'empty'};
    } else {
        params = {ids: _mergePower.join(",")};
    }
    doInitMergeSelectedDatagrid(params)
}

function doAddPower() {
    var url=contextPath+"/supervise/power/addFormalPower.jsp";
    top.bsWindow(url ,"添加职权",{width:"1000",height:"400",buttons:
        [
			{name:"关闭",classStyle:"btn-alert-gray"},
            {name:"保存",classStyle:"btn-alert-blue"}
            
        ]
        ,submit:function(v,h){
            var cw = h[0].contentWindow;
            if( v == "保存") {
                var formalPowers = cw.getSelectedPower();
                if(formalPowers.length == 0) {
                    $.MsgBox.Alert_auto("请选择职权");
                    return false;
                }else {

                    if(_mergePower == null || _mergePower.length == 0) {
                        for(var index in formalPowers) {
                            _mergePower.push(formalPowers[index].id);
                        }
                    } else {
                        for(var index in formalPowers) {
                            if(_mergePower.indexOf(formalPowers[index].id) == -1) {
                                _mergePower.push(formalPowers[index].id);
                            }
                        }
                    }
                    var params = {
                        ids: _mergePower.join(",")
                    };
                    doInitMergeSelectedDatagrid(params);  
                    
                    return true;
                }
            } else if(v=="关闭"){
                return true;
            }
        }
    });
}

function doSelectedMergePower() {
    
    if(_mergePower != null && _mergePower.length > 0) {
        
        $('#power_tabs').tabs('disableTab', 0);
        $('#power_tabs').tabs('enableTab', 1);
        $('#power_tabs').tabs('enableTab', 2);
        $('#power_tabs').tabs('enableTab', 3);
        $('#power_tabs').tabs('select', 1);
    }
}