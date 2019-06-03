/**
 * 查看页面初始化方法
 * @returns
 */

var flowJson;
var gistGrid;
var subjectGrid;

function doInit() {
    var id = $('#id').val();
//    flowJson = $('#flowsheetArray').val();
//    if(flowJson != null && flowJson != '') {
//        flowJson = tools.strToJson(flowJson);
//    }
    
//    initAttachmentInfo("powerAttachment", id, "flowsheetTable");
    
    doInitGistgrid({powerId: id});
    doInitSubjectgrid({powerId: id});
}

/**
 * 初始化附件列表
 * @param model
 * @param modelId
 * @param elemId
 * @returns
 */
function initAttachmentInfo(model, modelId, elemId) {
    var params = {model:model, modelId:modelId};
    var json = tools.requestJsonRs("/commonCtrl/attachmentList.action", params);   
    if(json.rtState) {
        var attachList = json.rtData;
        $.each(attachList, function(i, o){
            // 添加附件属性
            addAttachmentProp(o, elemId);
        });
    }
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
    page = page + '<tr class="common-tr-border" id="flowsheet' + obj.sid + '">';
    page = page + '<td class="power-table-label">流程图类型：</td>';
    page = page + '<td style=" width: 350px;">';
    for(var index in flowJson) {
        if(flowJson[index].fileId == obj.sid) {
            page = page + flowJson[index].flowsheetType;
        }
    }
    page = page + '</td>';
    page = page + '<td class="power-table-label">文件名称：</td>';
    page = page + '<td style="width: 300px;">' + '<a href="javascript:void(0);" sid="FILE' + obj.sid + '"></a>'+ '</td>';
    page = page + '</tr>';
    
    mainObj.append(page);
    
    obj.priv = 1+2+4; // 阅读、下载、删除
    var attach = tools.getAttachElement(obj, {deleteEvent:function(attachModel){
        $("#flowsheet" + attachModel.sid).remove();
    }});
    $("[sid='FILE" + obj.sid + "']").append(attach);
}

function doInitGistgrid(params) {
    gistGrid = $('#gistGrid').datagrid({
        url: contextPath + '/powerCtrl/findGistsByPowerId.action',
        queryParams: params,
        pagination: true,
        singleSelect: false,
        pageSize : 10,
        pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
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
            field: 'gistStrip',
            title: '条',
            width: 10,
            align : 'center',
            halign : 'center'
        },
        {
            field: 'gistFund',
            title: '款',
            width: 10,
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
            field: 'gistItem',
            title: '项',
            width: 10,
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
        }]]
    });
}

function doInitSubjectgrid(params) {
    subjectGrid = $('#subjectGrid').datagrid({
        url: contextPath + '/powerCtrl/findSubjectsByPowerId.action',
        queryParams: params,
        pagination: true,
        singleSelect: false,
        pageSize : 10,
        pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
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
            field: 'subjectName',
            title: '主体名称',
            width: 150,
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
            field: 'deputeType',
            title: '主体类型',
            width: 20,
            align : 'center',
            halign : 'center'
        }]]
    });
}